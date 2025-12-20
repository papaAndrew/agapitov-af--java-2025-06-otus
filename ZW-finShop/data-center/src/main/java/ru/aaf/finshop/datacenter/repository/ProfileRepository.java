package ru.aaf.finshop.datacenter.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.aaf.finshop.datacenter.model.Profile;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, Long> {
    Flux<Profile> findByName(String name);

    @Modifying
    @Query("update profile set client_id = :clientId where id = :id")
    void updateClientId(@Param("id") long id, @Param("clientId") Long clientId);
}
