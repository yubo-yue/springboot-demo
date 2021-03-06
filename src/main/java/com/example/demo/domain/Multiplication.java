package com.example.demo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class Multiplication {
    @Id
    @GeneratedValue
    @Column(name = "MULTIPLICATION_ID")
    private Long id;

    private final int factorA;
    private final int factorB;

    // Empty constructor for JSON/JPA
    Multiplication() {
        this(0, 0);
    }
}
