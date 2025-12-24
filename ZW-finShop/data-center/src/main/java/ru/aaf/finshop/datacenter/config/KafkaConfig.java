package ru.aaf.finshop.datacenter.config;

import static org.apache.kafka.clients.CommonClientConfigs.*;
import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;

@Configuration
@EnableKafka
public class KafkaConfig {
    public static final int MAX_POLL_INTERVAL_MS = 300;

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(senderProps());
    }

    private Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(LINGER_MS_CONFIG, 10);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        //        props.put(ACKS_CONFIG, "1");
        //        props.put(RETRIES_CONFIG, 1);
        //        props.put(BATCH_SIZE_CONFIG, 16384);
        //        props.put(BUFFER_MEMORY_CONFIG, 33_554_432); // bytes
        //        props.put(MAX_BLOCK_MS_CONFIG, 1_000); // ms

        return props;
    }

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate(ProducerFactory<Integer, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
