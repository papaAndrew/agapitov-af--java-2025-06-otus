package ru.aaf.finshop.datacenter.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("client_profile")
public class Profile implements Persistable<String> {

    @Id
    private Long id;

    private String name;

    private Long clientId;

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
    public String getId() {
        return id != null ? String.valueOf(id) : null;
    }

    public Long getProfileId() {
        return id;
    }

    @Override
    public String toString() {
        return "Profile{id=" + id + ";\nname=\"" + name + "\";\nclientId:" + clientId + "\n}'";
    }
}
