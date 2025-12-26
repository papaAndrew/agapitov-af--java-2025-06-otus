package ru.aaf.finshop.client.controllers;

import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.aaf.finshop.client.domain.LoanClaimDto;
import ru.aaf.finshop.client.service.DataProcessor;
import ru.aaf.finshop.proto.LoanClaimProto;
import ru.aaf.finshop.proto.RemoteServiceGrpc;

@RestController
public class BankClientRestController {
    private static final Logger logger = LoggerFactory.getLogger(BankClientRestController.class);

    private final DataProcessor<LoanClaimDto> dataProcessor;
    private final ManagedChannel channel;

    public BankClientRestController(DataProcessor<LoanClaimDto> dataProcessor, ManagedChannel channel) {
        this.dataProcessor = dataProcessor;
        this.channel = channel;
    }

    @PostMapping(value = "/claim")
    public LoanClaimDto claim(@RequestBody LoanClaimDto loanClaim) {
        logger.info("claim: loanClaim: {}", loanClaim);
        var stub = RemoteServiceGrpc.newBlockingStub(channel);
        var loanClaimProto = LoanClaimProto.newBuilder()
                .setClientId(loanClaim.clientId())
                .setPeriod(loanClaim.period())
                .setAmount(loanClaim.amount())
                .setStatus(0)
                .build();
        var response = stub.saveClaim(loanClaimProto);
        return new LoanClaimDto(
                response.getId(),
                response.getClientId(),
                LoanClaimDto.statusMap.get(response.getStatus()),
                response.getPeriod(),
                response.getAmount());
    }

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<LoanClaimDto> loanClaimDtoFlux() {
        logger.info("request for data");
        return dataProcessor.ackClaimStatus();
    }
}
