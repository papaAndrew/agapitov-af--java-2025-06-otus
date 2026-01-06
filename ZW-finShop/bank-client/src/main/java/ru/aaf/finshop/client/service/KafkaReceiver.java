package ru.aaf.finshop.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.aaf.finshop.client.domain.LoanClaimDto;


@Slf4j
@Service
public class KafkaReceiver {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final DataProcessor<LoanClaimDto> dataProcessor;

    public KafkaReceiver(DataProcessor<LoanClaimDto> dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    @KafkaListener(id = "bcl-00", topics = "bank_client")
    public void listen(String msg) {
        log.info("listen: {}", msg);
        try {
            var mappedDto = LoanClaimDto.createMapped(mapper.readValue(msg, LoanClaimDto.class));
            dataProcessor.putClaimStatus(mappedDto);
        } catch (JsonProcessingException e) {
            dataProcessor.putClaimStatus(new LoanClaimDto(null, null, "Error!!", null, null));
        }
    }
}
