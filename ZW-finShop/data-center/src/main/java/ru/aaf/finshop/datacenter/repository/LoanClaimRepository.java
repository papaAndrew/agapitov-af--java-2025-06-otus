package ru.aaf.finshop.datacenter.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.LoanClaim;

public interface LoanClaimRepository extends ReactiveCrudRepository<LoanClaim, Long> {

    Flux<LoanClaim> findByStatus(int status);

    @Modifying
    @Query("UPDATE claim SET status = :status WHERE id = :id")
    Mono<Void> updateStatus(@Param("id") long id, @Param("status") int status);

    default Mono<LoanClaim> updateStatusAndReturn(long id, int status) {
        return updateStatus(id, status).then(findById(id));
    }
}
