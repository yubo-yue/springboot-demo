package com.example.demo.service;

import com.example.demo.domain.Multiplication;
import com.example.demo.domain.MultiplicationResultAttempt;
import com.example.demo.domain.User;
import com.example.demo.repository.MultiplicationResultAttemptRepository;
import com.example.demo.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private final RandomGeneratorService randomGeneratorService;

    private final MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;

    private final UserRepository userRepository;

    @Autowired
    public MultiplicationServiceImpl(@NonNull final RandomGeneratorService randomGeneratorService
            , @NonNull final MultiplicationResultAttemptRepository multiplicationResultAttemptRepository
            , @NonNull final UserRepository userRepository) {
        this.randomGeneratorService = randomGeneratorService;
        this.multiplicationResultAttemptRepository = multiplicationResultAttemptRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Transactional
    @Override
    public MultiplicationResultAttempt checkAttempt(@NonNull final MultiplicationResultAttempt attempt) {
        final Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());
        Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");

        boolean isCorrect = attempt.getResultAttempt() ==
                attempt.getMultiplication().getFactorA() *
                        attempt.getMultiplication().getFactorB();

        final MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
                user.orElse(attempt.getUser()),
                attempt.getMultiplication(),
                attempt.getResultAttempt(),
                isCorrect
        );

        MultiplicationResultAttempt storedAttempt = multiplicationResultAttemptRepository.save(checkedAttempt);

        return storedAttempt;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(@NonNull final String userAlias) {

        return multiplicationResultAttemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }

    @Override
    public MultiplicationResultAttempt getResultById(@NonNull final Long resultId) {
        return multiplicationResultAttemptRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "The requested resultId [" + resultId +
                                "] does not exist."));
    }
}
