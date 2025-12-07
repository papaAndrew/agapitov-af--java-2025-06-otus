package ru.aaf.finshop.datacenter.service;

import java.util.Optional;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;

public interface DataService {

    Optional<Profile> getProfileByCredential(String credential);

    Client getClientByProfileId(Long profileId);
}
