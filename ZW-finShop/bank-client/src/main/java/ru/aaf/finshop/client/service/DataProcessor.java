package ru.aaf.finshop.client.service;

import reactor.core.publisher.Flux;

public interface DataProcessor<T> {

    Flux<T> ackClaimStatus();

    void putClaimStatus(T stringValue);
}
