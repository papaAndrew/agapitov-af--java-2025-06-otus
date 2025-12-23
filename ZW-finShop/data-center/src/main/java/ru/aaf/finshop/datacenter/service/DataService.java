package ru.aaf.finshop.datacenter.service;

import java.util.Optional;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;

public interface DataService {

    Optional<Profile> getProfileByName(String name);

    Optional<Profile> getProfileById(long id);

    Profile saveProfile(Profile profile);

    Client saveClient(Client client);

    Client findOrCreateClient(Client client);

    Profile updateProfile(Profile profile);
}
