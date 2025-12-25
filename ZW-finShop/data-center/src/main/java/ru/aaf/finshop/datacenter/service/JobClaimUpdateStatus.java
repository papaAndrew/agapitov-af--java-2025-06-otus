package ru.aaf.finshop.datacenter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@EnableScheduling
public class JobClaimUpdateStatus {
    private static final Logger log = LoggerFactory.getLogger(JobClaimUpdateStatus.class);

    private final DataService dataService;
    private final BusProducer busProducer;

    public JobClaimUpdateStatus(DataService dataService, BusProducer busProducer) {
        this.dataService = dataService;
        this.busProducer = busProducer;
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 60000)
    private void updateClaimsFirst() {
        log.info("updateClaimsFirst: fromStatus : {}", 0);

        Mono.just(0)
                .flatMapMany(dataService::loadClaimsByStatus)
                .doOnNext(loanClaim -> log.info("updateClaimsFirst: Processing claim: {}", loanClaim))
                .flatMap(loanClaim -> dataService.updateClaimStatus(loanClaim.id(), 1))
                .doOnNext(loanClaim -> log.info("updateClaimsFirst: after update: {}", loanClaim))
                .subscribe(
                        result -> log.debug("Processed claim: {}", result),
                        error -> log.error("Status delivery or updating error", error),
                        () -> log.info("Scheduled claim processing completed"));
    }
}
