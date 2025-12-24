package ru.aaf.finshop.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.aaf.finshop.client.controllers.StringValue;

import java.util.function.Consumer;

@Slf4j
@Service
public class KafkaReceiver {

//    private final Consumer<StringValue> valueConsumer;
    private final DataProcessor<StringValue> dataProcessor;

    public KafkaReceiver(DataProcessor<StringValue> dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

//    public KafkaReceiver(Consumer<StringValue> valueConsumer) {
//        this.valueConsumer = valueConsumer;
//    }

    @KafkaListener(id = "bcl-00", topics = "bank_client")
    public void listen(String msg) {
        log.info("listen: {}", msg);
        dataProcessor(new StringValue(msg));
    }
}
