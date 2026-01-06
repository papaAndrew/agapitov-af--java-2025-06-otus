package ru.aaf.finshop.client.domain;

import java.util.Map;

public record LoanClaimDto(Long id, Long clientId, String status, Integer period, Integer amount) {

    public static final Map<Integer, String> statusMap = Map.of(
            0, "Новый",
            1, "На рассмотрении",
            2, "Одобрено");

    public static LoanClaimDto createMapped(LoanClaimDto fromDto) {
        var status = statusMap.get(Integer.valueOf(fromDto.status()));
        return new LoanClaimDto(fromDto.id(), fromDto.clientId(), status, fromDto.period(), fromDto.amount());
    }
}
