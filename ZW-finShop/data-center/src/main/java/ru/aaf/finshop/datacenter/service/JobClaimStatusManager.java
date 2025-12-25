package ru.aaf.finshop.datacenter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.aaf.finshop.datacenter.model.LoanClaim;

@Component
@EnableScheduling
public class JobClaimStatusManager {
    private static final Logger log = LoggerFactory.getLogger(JobClaimStatusManager.class);

    private final DataService dataService;
    private final BusProducer busProducer;

    public JobClaimStatusManager(DataService dataService, BusProducer busProducer) {
        this.dataService = dataService;
        this.busProducer = busProducer;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void processClaims() {
        Mono.just(1)
                .doOnNext(s -> log.info("Loading claims with status: {}", s))
                .flatMapMany(dataService::loadClaimsByStatus)
                .doOnNext(loanClaim -> log.info("Processing claimId: {}", loanClaim.id()))
                .doOnNext(loanClaim -> busProducer.send(makeMessage(loanClaim)))
                .flatMap(loanClaim -> dataService.updateClaimStatus(loanClaim.id(), 2))
                .subscribe(
                        result -> log.info("Updated claim: {}", result),
                        error -> log.error("Error processing claims", error),
                        () -> log.info("All claims processed"));
    }

    private String makeMessage(LoanClaim loanClaim) {
        // Your message creation logic
        return "Status " + loanClaim.status();
    }
}
