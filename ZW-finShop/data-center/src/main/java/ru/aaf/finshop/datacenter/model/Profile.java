package ru.aaf.finshop.datacenter.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@SuppressWarnings({"java:S125", "java:S4144"})
@Getter
@Setter
@Table("profile")
public class Profile implements Persistable<Long> {

    @Id
    private Long id;

    private String name;

    @Column("client_id")
    private Long clientId;

    @Transient
    private Client client;

    @Transient
    private final boolean isNew;

    public Profile(Long id, String name, Long clientId, boolean isNew) {
        this.id = id;
        this.name = name;
        this.clientId = clientId;
        this.isNew = isNew;
    }

    @PersistenceCreator
    private Profile(Long id, String name, Long clientId) {
        this(id, name, clientId, false);
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

    public Profile client(Client client) {
        this.client = client;
        this.clientId = client == null ? null : client.getClientId();
        return this;
    }

    @Override
    public String toString() {
        return "Profile {\n" + "   id: "
                + id + ";\n" + "   name: \""
                + name + "\";\n" + "   clientId: "
                + clientId + ";\n" + "   client: "
                + client + ";\n" + "   isNew: "
                + isNew + ";\n" + "}";
    }
}
