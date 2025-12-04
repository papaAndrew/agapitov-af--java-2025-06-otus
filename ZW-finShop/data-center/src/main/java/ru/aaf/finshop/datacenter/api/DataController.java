package ru.aaf.finshop.datacenter.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.aaf.finshop.datacenter.model.Client;

public class DataController {

    @GetMapping(value = "/bank/client/{clientId}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Client getClientById(@PathVariable("clientId") String roomId) {
        return null;
    }
}
