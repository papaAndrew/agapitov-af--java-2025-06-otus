package ru.otus.crm.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public record Phone(@Id Long id, @Column("client_id") Long clientId, @Nonnull String number) {
    @Override
    public String toString() {
        return "Phone{id=" + id + " number='" + number + "'}'";
    }
}
