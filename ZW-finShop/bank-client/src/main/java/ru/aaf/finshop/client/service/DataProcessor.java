package ru.aaf.finshop.client.service;

import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.client.controllers.StringValue;

public interface DataProcessor<T> {

    Mono<T> processMono(T data);

    Flux<T> processFlux(List<T> data);

    Flux<T> ackClaimStatus();

    void putClaimStatus(StringValue stringValue);
}
