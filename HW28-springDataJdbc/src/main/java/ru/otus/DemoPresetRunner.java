package ru.otus;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.crm.service.DBServiceClient;

@Component
public class DemoPresetRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoPresetRunner.class);
    private final DBServiceClient dbServiceClient;

    public DemoPresetRunner(ClientRepository clientRepository, DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public void run(String... args) throws Exception {
        findOrCreateClient(
                "admin", new Address(null, "Квантовой сингулярности ул"), List.of(new Phone(null, null, "223-322")));
        findOrCreateClient(
                "Аналитик",
                new Address(null, "Сущности тупик"),
                List.of(new Phone(null, null, "888-111"), new Phone(null, null, "777-222")));
        findOrCreateClient(
                "Директор",
                new Address(null, "Капуцинов блв"),
                List.of(new Phone(null, null, "000-333"), new Phone(null, null, "555-666")));
    }

    private void findOrCreateClient(String name, Address address, List<Phone> phones) {

        if (dbServiceClient.findByName(name).isEmpty()) {
            log.info("Create new Client {}", name);
            dbServiceClient.saveClient(new Client(null, name, address, phones, true));
        }
    }
}
