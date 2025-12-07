package ru.aaf.finshop.datacenter.repository;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import ru.aaf.finshop.datacenter.model.Profile;

public interface ProfileRepository extends ListCrudRepository<Profile, Long> {

    Optional<Profile> findByName(String name);
}
