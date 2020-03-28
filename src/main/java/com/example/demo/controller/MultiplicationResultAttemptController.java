package com.example.demo.controller;

import com.example.demo.domain.MultiplicationResultAttempt;
import com.example.demo.service.MultiplicationService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/results")
public class MultiplicationResultAttemptController {
    private final MultiplicationService multiplicationService;
    private final int serverPort;

    @Autowired
    public MultiplicationResultAttemptController(@NonNull final MultiplicationService multiplicationService
            , @Value("${server.port}") final int serverPort) {
        this.multiplicationService = multiplicationService;
        this.serverPort = serverPort;
    }

    @PostMapping
    public ResponseEntity<MultiplicationResultAttempt> postResult(@NonNull @RequestBody final MultiplicationResultAttempt multiplicationResultAttempt) {
        return ResponseEntity.ok(multiplicationService.checkAttempt(multiplicationResultAttempt));
    }

    @GetMapping
    public ResponseEntity<List<MultiplicationResultAttempt>> getStats(@NonNull @RequestParam("alias") final String alias) {
        log.info("alias is {}", alias);
        return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
    }

    @GetMapping("/{resultId}")
    public ResponseEntity<MultiplicationResultAttempt> getResultById(@NonNull @PathVariable("resultId") final Long resultId) {
        log.info("Retrieving result {} from server @{}", resultId, serverPort);
        return ResponseEntity.ok(multiplicationService.getResultById(resultId));
    }
}
