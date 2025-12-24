package ru.aaf.finshop.client.service;

import java.time.Duration;
import java.util.Deque;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.expression.EqualsExpression;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.client.controllers.StringValue;

@Service
public class DataProcessorStringReactor implements DataProcessor<StringValue> {
    private static final Logger log = LoggerFactory.getLogger(DataProcessorStringReactor.class);

    private Deque deque = new ;
//    private final KafkaReceiver kafkaReceiver;
//
//    public DataProcessorStringReactor(KafkaReceiver kafkaReceiver) {
//        this.kafkaReceiver = kafkaReceiver;
//        claimStatusFlux = Flux.from(kafkaReceiver::listen);
//
//    }

    @Override
    public Mono<StringValue> processMono(StringValue data) {
        log.info("processMono: {}", data);
        return Mono.just(data)
                .delayElement(Duration.ofSeconds(5))
                .map(val -> new StringValue(val.value().toUpperCase()))
                .doOnNext(val -> log.info("out val:{}", val));
    }

    @Override
    public Flux<StringValue> processFlux(List<StringValue> dataflow) {
        log.info("processFlux: {}", dataflow);

        return Flux.fromIterable(dataflow)
                .delayElements(Duration.ofSeconds(5))
                .map(val -> new StringValue(val.value().toUpperCase()))
                .doOnNext(val -> log.info("out val:{}", val));
    }

    @Override
    public Flux<StringValue> ackClaimStatus() {
        return Flux.from((org.reactivestreams.Publisher<? extends StringValue>) this::putClaimStatus);
    }

    @Override
    public void putClaimStatus(StringValue stringValue) {
        claimStatusFlux.map(queue?);
    }

}
