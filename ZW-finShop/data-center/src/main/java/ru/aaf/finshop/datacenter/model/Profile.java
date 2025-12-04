package ru.aaf.finshop.datacenter.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("client_profile")
public record Profile(@Id Long id, @Column("client_id") Long clientId, @Nonnull String credential) {
    @Override
    public String toString() {
        return "Profile{id=" + id + "; clientId=" + clientId + "; credential='" + credential + "'}'";
    }
}
