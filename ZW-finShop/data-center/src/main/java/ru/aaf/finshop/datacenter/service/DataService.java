package ru.aaf.finshop.datacenter.service;

import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.Profile;

public interface DataService {

    Mono<Profile> getProfileByName(String name);

    Mono<Profile> saveProfile(Profile profile);
}
