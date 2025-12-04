package ru.aaf.finshop.datacenter.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("client_profile")
public record Passport(@Id @Column("client_id") Long clientId, @Nonnull String serialNumber) {
    @Override
    public String toString() {
        return "Passport{clientId=" + clientId + "; serialNumber='" + serialNumber + "'}'";
    }
}
