package ru.aaf.finshop.client.service;

import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.client.controllers.StringValue;

public interface DataProcessor<T> {

    Flux<T> ackClaimStatus();

    void putClaimStatus(T stringValue);
}
