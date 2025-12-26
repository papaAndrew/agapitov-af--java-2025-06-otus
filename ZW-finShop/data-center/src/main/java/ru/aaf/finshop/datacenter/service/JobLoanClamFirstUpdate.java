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
public class JobLoanClamFirstUpdate extends AbstractLoanStatusProcessor {
    private static final Logger log = LoggerFactory.getLogger(JobLoanClamFirstUpdate.class);

    private static final

    private final DataService dataService;
    private final BusProducer busProducer;

    public JobLoanClamFirstUpdate(DataService dataService, BusProducer busProducer) {
        this.dataService = dataService;
        this.busProducer = busProducer;
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 60000)
    @Override
    protected void process() {
        log.info("process: fromStatus : {}", 0);

        Mono.just(0)
                .flatMapMany(dataService::loadClaimsByStatus)
                .doOnNext(loanClaim -> log.info("updateClaimsFirst: Processing claim: {}", loanClaim))
                .doOnNext(loanClaim -> busProducer.send(makeMessage(loanClaim)))
                .flatMap(loanClaim -> dataService.updateClaimStatus(loanClaim.id(), 1))
                .doOnNext(loanClaim -> log.info("updateClaimsFirst: after update: {}", loanClaim))
                .subscribe(
                        result -> log.debug("Processed claim: {}", result),
                        error -> log.error("Status delivery or updating error", error),
                        () -> log.info("Scheduled claim processing completed"));
    }

}
