package ru.aaf.finshop.client.processor;

import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DataProcessor<T> {

    Mono<T> processMono(T data);

    Flux<T> processFlux(List<T> data);
}
