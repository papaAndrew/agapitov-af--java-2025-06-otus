package ru.aaf.finshop.datacenter.repository;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import ru.aaf.finshop.datacenter.model.Client;

public interface ClientRepository extends ListCrudRepository<Client, Long> {

    Optional<Client> findByName(String name);
}
