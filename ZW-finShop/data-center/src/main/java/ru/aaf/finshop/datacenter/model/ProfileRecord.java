package ru.aaf.finshop.datacenter.model;

import org.springframework.data.relational.core.mapping.Table;

@Table("client_profile")
public record ProfileRecord(Long id, String name, Long clientId) {

    @Override
    public String toString() {
        return "Profile{id=" + id + "; name=\"" + name + "\"; clientId:" + clientId + "\n}'";
    }
}
