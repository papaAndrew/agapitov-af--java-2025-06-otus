package ru.aaf.finshop.client.processor;

import java.time.Duration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.client.controllers.StringValue;

@Service
public class DataProcessorStringReactor implements DataProcessor<StringValue> {
    private static final Logger log = LoggerFactory.getLogger(DataProcessorStringReactor.class);

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
}
