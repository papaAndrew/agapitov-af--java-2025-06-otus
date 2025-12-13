package ru.aaf.finshop.datacenter.repository;

import java.util.Optional;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.aaf.finshop.datacenter.model.Profile;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, Long> {
    Optional<Profile> findByName(String name);
}
