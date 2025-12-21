package ru.aaf.finshop.datacenter.service;

import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.Client;
import ru.aaf.finshop.datacenter.model.Profile;

public interface DataService {

    Mono<Profile> getProfileByName(String name);

    Mono<Client> getClientById(Long clientId);

    Mono<Profile> saveProfile(Profile profile);

    Mono<Client> saveClient(Client client);

    Mono<Void> updateProfileByClient(Long profileId, Long clientId);
}
