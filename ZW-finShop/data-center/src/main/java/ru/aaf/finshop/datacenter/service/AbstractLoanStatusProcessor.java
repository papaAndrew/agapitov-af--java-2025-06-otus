package ru.aaf.finshop.datacenter.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.aaf.finshop.datacenter.model.LoanClaim;

@EnableScheduling
public abstract class AbstractLoanStatusProcessor {
    protected static final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    abstract void process();

    protected static String makeMessage(LoanClaim loanClaim) {
        try {
            return objectMapper.writeValueAsString(loanClaim);
        } catch (Exception e) {
            return String.format("{\"status\":\"%s\",\"trace\":\"%s\"}", "Failed to serialize LoanClaim", e);
        }
    }
}
