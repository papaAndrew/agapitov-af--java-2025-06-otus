package ru.otus.domain;

import jakarta.annotation.Nonnull;

public record ProfileDto(@Nonnull Long id, @Nonnull Long clientId, String credential) {}
