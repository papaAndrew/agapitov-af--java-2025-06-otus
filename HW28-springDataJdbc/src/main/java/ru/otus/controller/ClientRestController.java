package ru.otus.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

@RestController
public class ClientRestController {

    private final DBServiceClient dbServiceClient;

    public ClientRestController(DBServiceClient clientService) {
        this.dbServiceClient = clientService;
    }

    @GetMapping("/client")
    public List<Client> getClientList() {
        return dbServiceClient.findAll();
    }

    //    @PostMapping("/client")
    //    public Client saveClient(@RequestBody Client client) {
    //        dbServiceClient.saveClient(client);
    //        return client;
    //    }
}
