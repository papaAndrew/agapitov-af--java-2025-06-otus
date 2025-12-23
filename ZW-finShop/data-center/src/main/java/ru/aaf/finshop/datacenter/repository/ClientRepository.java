package ru.aaf.finshop.datacenter.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.aaf.finshop.datacenter.model.Client;

public interface ClientRepository extends ReactiveCrudRepository<Client, Long> {
    Flux<Client> findByName(String name);
}
