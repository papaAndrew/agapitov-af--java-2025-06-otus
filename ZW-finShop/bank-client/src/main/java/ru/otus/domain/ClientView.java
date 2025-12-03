package ru.otus.domain;

import java.math.BigDecimal;

public record ClientView(String name, String passport, BigDecimal income, BigDecimal amount) {}
