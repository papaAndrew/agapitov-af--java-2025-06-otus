package ru.otus.hw21.crm.service;

import java.util.List;
import java.util.Optional;
import ru.otus.hw21.crm.model.Client;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
