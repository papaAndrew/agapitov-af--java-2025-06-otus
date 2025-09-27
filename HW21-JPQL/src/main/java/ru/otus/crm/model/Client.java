package ru.otus.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private Address address;

    @Column(name = "phones")
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
        this.address = null;
        this.phones = null;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
        this.address = null;
        this.phones = null;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        if (this.phones != null) {
            List<Phone> phones = new ArrayList<>(this.phones);
            return new Client(this.id, this.name, this.address, phones);
        }
        return new Client(this.id, this.name, this.address, null);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id
                + ", name='" + name
                + ", address ='" + address.getStreet()
                + ", phones = [" + String.join(", ", phones.stream().map(Phone::getNumber).toList()) + "]"
                + '\'' + '}';
    }
}
