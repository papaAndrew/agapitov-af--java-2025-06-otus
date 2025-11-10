package ru.otus.services.processors;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;

    private final BlockingQueue<SensorData> queue;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        queue = new PriorityBlockingQueue<>(bufferSize, Comparator.comparing(SensorData::getMeasurementTime));
    }

    @Override
    public void process(SensorData data) {
        queue.add(data);
        if (queue.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        if (queue.isEmpty()) {
            log.info("flush: Буфер пуст");
            return;
        }
        try {
            log.info("flush: Размер буфера = {} of {}", queue.size(), bufferSize);
            List<SensorData> chunk = new ArrayList<>();
            queue.drainTo(chunk);
            writer.writeBufferedData(chunk);
        } catch (Exception e) {
            log.error("flush: Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
