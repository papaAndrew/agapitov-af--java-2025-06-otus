package ru.aaf.finshop.datacenter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@SuppressWarnings("java:S125")
@Component
public class KafkaBusProducer implements BusProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaBusProducer.class);

    private static final String TOPIC_NAME = "bank_client";

    //    private final KafkaProducer<Integer, String> kafkaProducer;
    private final KafkaTemplate<Integer, String> kafkaTemplate;
    //    private final Consumer<String> sendAsk;

    public KafkaBusProducer(KafkaTemplate<Integer, String> kafkaTemplate) {
        //        this.kafkaProducer = kafkaProducer;
        this.kafkaTemplate = kafkaTemplate;
        //        this.sendAsk = sendAsk;
        log.info("KafkaBusProducer created OK");
    }

    @Override
    public void send(String value) {
        log.info("value:{}", value);
        try {
            kafkaTemplate.send(TOPIC_NAME, value);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
