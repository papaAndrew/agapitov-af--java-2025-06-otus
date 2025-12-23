package ru.aaf.finshop.datacenter.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("profile")
public class Profile implements Persistable<Long> {

    @Id
    private Long id;

    private String name;

    @Column("client_id")
    @MappedCollection(idColumn = "client_id")
    private Client client;

    @Transient
    private final boolean isNew;

    public Profile(Long id, String name, Client client, boolean isNew) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.isNew = isNew;
    }

    @PersistenceCreator
    private Profile(Long id, String name, Client client) {
        this(id, name, client, false);
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getProfileId() {
        return id;
    }

    @Override
    public String toString() {
        return "Profile{id=" + id + ";\nname=\"" + name + "\";\nclient:" + client + "\n}'";
    }
}
