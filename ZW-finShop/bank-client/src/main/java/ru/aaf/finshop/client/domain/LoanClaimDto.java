package ru.aaf.finshop.client.domain;

import jakarta.annotation.Nonnull;

public record LoanClaimDto(Long id, @Nonnull Long clientId, String status, Integer period, Integer amount) {}
