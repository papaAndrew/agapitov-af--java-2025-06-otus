package ru.aaf.finshop.datacenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.aaf.finshop.datacenter.model.Profile;
import ru.aaf.finshop.datacenter.service.DataService;

@Component
public class DemoPreset implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoPreset.class);
    private final DataService dataService;

    public DemoPreset(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void run(String... args) throws Exception {
        findOrCreateProfile("admin");
    }

    private void findOrCreateProfile(String name) {
        if (dataService.getProfileByCredential(name).isEmpty()) {
            log.info("Create new Profile: {}", name);
            dataService.saveProfile(new Profile(null, name, null, true));
        } else {
            log.info("Profile already exists: {}", name);
        }
    }
}
