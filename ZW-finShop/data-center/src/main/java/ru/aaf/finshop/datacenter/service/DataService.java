package ru.aaf.finshop.datacenter.service;

import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;

public interface DataService {

    Mono<Profile> getProfileByCredential(String name);

    Mono<Client> getClientByProfileId(Long profileId);

    Mono<Profile> saveProfile(Profile profile);
}
