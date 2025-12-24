package ru.aaf.finshop.datacenter.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.Client;

public interface ClientRepository extends ReactiveCrudRepository<Client, Long> {

    Mono<Client> getById(long id);

    Flux<Client> findByName(String name);
}
