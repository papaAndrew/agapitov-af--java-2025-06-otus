package ru.aaf.finshop.datacenter.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@SuppressWarnings("java:S125")
@Getter
@Setter
@Table("client")
public class Client implements Cloneable, Persistable<Long> {

    @Id
    private final Long id;

    @NonNull
    private final String name;

    private final String passport;

    @Transient
    private final boolean isNew;

    public Client(Long id, String name, String passport, boolean isNew) {
        this.id = id;
        this.name = name;
        this.passport = passport;
        this.isNew = isNew;
    }

    @PersistenceCreator
    private Client(Long id, String name, String passport) {
        this(id, name, passport, false);
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return id;
    }

    @Override
    public String toString() {
        return "Client {\n" + "   id: "
                + id + ";\n" + "   name: \""
                + name + "\";\n" + "   passport: \""
                + passport + "\";\n" + "   isNew="
                + isNew + ";\n" + "}";
    }

    @Override
    public Object clone() {
        return new Client(id, name, passport, isNew);
    }
}
