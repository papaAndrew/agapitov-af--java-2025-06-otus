package ru.aaf.finshop.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import ru.aaf.finshop.client.domain.LoanClaimDto;

@Service
public class LoanClaimProcessorReactor implements DataProcessor<LoanClaimDto> {
    private static final Logger log = LoggerFactory.getLogger(LoanClaimProcessorReactor.class);

    private final Sinks.Many<LoanClaimDto> claimStatusSink;

    public LoanClaimProcessorReactor() {
        this.claimStatusSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @Override
    public Flux<LoanClaimDto> ackClaimStatus() {
        return claimStatusSink.asFlux();
    }

    @Override
    public void putClaimStatus(LoanClaimDto loanClaim) {
        claimStatusSink.tryEmitNext(loanClaim);
    }
}
