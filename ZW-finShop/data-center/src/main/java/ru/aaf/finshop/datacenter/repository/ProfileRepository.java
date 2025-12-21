package ru.aaf.finshop.datacenter.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.aaf.finshop.datacenter.model.Profile;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, Long> {
    Flux<Profile> findByName(String name);
}
