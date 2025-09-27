package ru.otus.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    public Phone(Long id, String number) {

        this.id = id;
        this.number = number;
    }
}
