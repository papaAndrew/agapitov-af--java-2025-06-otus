package ru.aaf.finshop.datacenter.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("claim")
public record LoanClaim(
        @Id Long id, @Column("client_id") Long clientId, Integer status, Integer period, Integer amount) {}
