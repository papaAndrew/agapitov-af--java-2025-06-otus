package ru.otus.model;

import jakarta.persistence.*;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
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

    @SuppressWarnings("this-escape")
    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        setAddress(address);
        setPhones(phones);
    }

    public void setAddress(Address address) {
        this.address = address;
        if (this.address != null) {
            this.address.setClient(this);
        }
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
        if (this.phones != null) {
            phones.forEach(phone -> phone.setClient(this));
        }
    }

    @Override
    public Client clone() {
        var clonedAddress = this.address == null ? null : (Address) this.address.clone();
        if (this.phones != null) {
            var clonedPhones =
                    this.phones.stream().map(phone -> (Phone) phone.clone()).toList();
            return new Client(this.id, this.name, clonedAddress, clonedPhones);
        }
        return new Client(this.id, this.name, clonedAddress, null);
    }

    @Override
    public String toString() {
        var delim = "," + '\n';
        return "Client{id=" + id + delim + "    name='" + name + '\'' + delim
                + "    address=" + address + delim
                + "    phones=" + phones
                + delim
                + '}';
    }
}
