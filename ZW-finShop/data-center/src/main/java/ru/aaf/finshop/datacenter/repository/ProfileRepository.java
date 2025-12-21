package ru.aaf.finshop.datacenter.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.Profile;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, Long> {
    Flux<Profile> findByName(String name);

    @Modifying
    @Query("UPDATE profile SET client_id = :clientId WHERE id = :id")
    Mono<Void> updateProfileClientId(@Param("id") long id, @Param("clientId") Long clientId);
}
