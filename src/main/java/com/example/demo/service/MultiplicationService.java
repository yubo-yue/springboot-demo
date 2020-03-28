package com.example.demo.service;

import com.example.demo.domain.Multiplication;
import com.example.demo.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {
    /**
     * Creates a Multiplication object with two randomly-generated factors
     * between 11 and 99.
     *
     * @return a Multiplication object with random factors
     */
    Multiplication createRandomMultiplication();

    MultiplicationResultAttempt checkAttempt(MultiplicationResultAttempt multiplicationResultAttempt);

    List<MultiplicationResultAttempt> getStatsForUser(final String userAlias);

    MultiplicationResultAttempt getResultById(final Long resultId);
}
