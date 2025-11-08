package ru.otus.services.processors;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;

    private final Set<SensorData> bufferedData =
            new ConcurrentSkipListSet<>(Comparator.comparing(SensorData::getMeasurementTime));

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public synchronized void process(SensorData data) {
        bufferedData.add(data);
        if (bufferedData.size() >= bufferSize) {
            flush();
        }
    }

    public synchronized void flush() {
        synchronized (bufferedData) {
            if (bufferedData.isEmpty()) {
                log.info("flush: Буфер пуст");
                return;
            }
            try {
                log.info("flush: Размер буфера = {} of {}", bufferedData.size(), bufferSize);
                writer.writeBufferedData(new ArrayList<>(bufferedData));
                bufferedData.clear();
            } catch (Exception e) {
                log.error("flush: Ошибка в процессе записи буфера", e);
            }
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
