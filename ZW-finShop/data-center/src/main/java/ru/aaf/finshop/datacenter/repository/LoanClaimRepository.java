package ru.aaf.finshop.datacenter.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.aaf.finshop.datacenter.model.LoanClaim;

public interface LoanClaimRepository extends ReactiveCrudRepository<LoanClaim, Long> {

    Flux<LoanClaim> findByStatus(long status);
}
