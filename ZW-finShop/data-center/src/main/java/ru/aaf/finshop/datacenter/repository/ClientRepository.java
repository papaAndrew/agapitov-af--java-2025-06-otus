package ru.aaf.finshop.datacenter.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.aaf.finshop.datacenter.model.Client;

public interface ClientRepository extends ReactiveCrudRepository<Client, Long> {}
