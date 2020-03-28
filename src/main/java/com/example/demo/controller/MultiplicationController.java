package com.example.demo.controller;

import com.example.demo.domain.Multiplication;
import com.example.demo.service.MultiplicationService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(MultiplicationController.MAPPING_URL)
public class MultiplicationController {
    public static final String MAPPING_URL = "multiplications";

    private final MultiplicationService multiplicationService;
    private final int serverPort;

    @Autowired
    public MultiplicationController(@NonNull final MultiplicationService multiplicationService
            , @Value("${server.port}") int serverPort) {
        this.multiplicationService = multiplicationService;
        this.serverPort = serverPort;
    }

    @GetMapping("/random")
    public Multiplication getRandomMultiplication() {
        log.info("Generating a random multiplication from server @{}", serverPort);
        return multiplicationService.createRandomMultiplication();
    }
}
