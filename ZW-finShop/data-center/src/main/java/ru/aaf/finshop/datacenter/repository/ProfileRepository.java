package ru.aaf.finshop.datacenter.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.aaf.finshop.datacenter.model.Profile;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, Long> {
    Flux<Profile> findByName(String name);

    //    @Modifying
    //    @Query(value = "update profile set client_id = :clientId where id = :id")
    //    void updateProfileClientId(@Param("id") long id, @Param("clientId") long clientId);
}
