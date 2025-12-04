package ru.otus.finshop.datacenter.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DataController {

    @GetMapping(value = "/bank/client/{clientId}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public ClientDto getClientById(@PathVariable("clientId") String roomId) {
        return null;
    }
}
