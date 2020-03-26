package com.example.demo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Multiplication {
    private Long id;

    private final int factorA;
    private final int factorB;

    // Empty constructor for JSON/JPA
    Multiplication() {
        this(0, 0);
    }
}
