package ru.otus.crm.service;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    ClientRec saveClient(ClientRec client);

    Optional<ClientRec> getClient(long id);

    List<ClientRec> findAll();
}
