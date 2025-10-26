package ru.otus.crm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Client;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

@Service
public class DbServiceClientImpl implements DBServiceClient {

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public DbServiceClientImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public void saveClient(Client client) {
        transactionManager.doInTransaction(() -> clientRepository.save(client));
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clientRepository.findAll());
    }

    @Override
    public Optional<Client> findByName(String name) {
        return clientRepository.findByName(name);
    }
}
