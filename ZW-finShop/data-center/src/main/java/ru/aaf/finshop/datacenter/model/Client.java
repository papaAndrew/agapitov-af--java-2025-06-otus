package ru.aaf.finshop.datacenter.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@SuppressWarnings("java:S125")
@Table("client")
@Getter
public class Client implements Persistable<String> {

    @Id
    private final Long id;

    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final String passport;

    //    @MappedCollection(idColumn = "client_id")
    //    private final Profile profile;

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
    public String getId() {
        return id != null ? String.valueOf(id) : null;
    }

    public Long getClientId() {
        return id;
    }

    @Override
    public String toString() {
        var delim = ",\n";
        return "Class Client{id=" + id + delim + "    name='" + name + '\'' + delim
                + "    passport=" + passport + delim
                + "    isNew=" + isNew + '}';
    }
}
