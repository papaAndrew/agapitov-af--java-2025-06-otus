package ru.aaf.finshop.client.domain;

public record LoanClaimDto(Long id, Long clientId, String status, Integer period, Integer amount) {}
