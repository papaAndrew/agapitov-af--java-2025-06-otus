package ru.aaf.finshop.datacenter.service;

import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;

public interface DataService {

    Profile getProfileByCredential(String credential);

    Client getClientByProfileId(Long profileId);
}
