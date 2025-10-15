package ru.otus.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "acc")
public class User {

    @Id
    @SequenceGenerator(name = "acc_gen", sequenceName = "acc_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "passw")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Client client;

    public User(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{id=" + id + " password='" + password + "'}'";
    }

    @Override
    protected Object clone() {
        return new User(id, password);
    }
}
