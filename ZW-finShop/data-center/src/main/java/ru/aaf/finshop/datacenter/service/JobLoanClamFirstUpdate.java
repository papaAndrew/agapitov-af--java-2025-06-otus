package ru.aaf.finshop.datacenter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JobLoanClamFirstUpdate extends AbstractLoanStatusProcessor {
    private static final Logger log = LoggerFactory.getLogger(JobLoanClamFirstUpdate.class);

    private static final int STATUS_NEW = 0;

    private final DataService dataService;
    private final BusProducer busProducer;

    public JobLoanClamFirstUpdate(DataService dataService, BusProducer busProducer) {
        this.dataService = dataService;
        this.busProducer = busProducer;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 40000)
    @Override
    protected void process() {
        log.info("process: fromStatus : {}", STATUS_NEW);

        Mono.just(STATUS_NEW)
                .flatMapMany(dataService::loadClaimsByStatus)
                .doOnNext(loanClaim -> log.info("process: fetch: {}", loanClaim))
                .flatMap(loanClaim -> dataService.updateClaimStatus(loanClaim.id(), loanClaim.status() + 1))
                .doOnNext(loanClaim -> log.info("process: send: {}", loanClaim))
                .doOnNext(loanClaim -> busProducer.send(makeMessage(loanClaim)))
                .subscribe(
                        loanClaim -> log.debug("process: sent: {}", loanClaim),
                        error -> log.error("Status delivery or updating error", error),
                        () -> log.info("Scheduled claim processing completed"));
    }
}
