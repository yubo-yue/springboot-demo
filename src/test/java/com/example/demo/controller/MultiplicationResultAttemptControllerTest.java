package com.example.demo.controller;

import com.example.demo.domain.Multiplication;
import com.example.demo.domain.MultiplicationResultAttempt;
import com.example.demo.domain.User;
import com.example.demo.service.MultiplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<MultiplicationResultAttempt> jsonResultAttempt;
    private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttempts;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void postResultReturnCorrect() throws Exception {
        final MultiplicationResultAttempt attempt = getCorrectMultiplicationResultAttemp();

        given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class)))
                .willReturn(attempt);

        log.info(jsonResultAttempt.write(attempt).getJson());
        final MockHttpServletResponse response = mvc.perform(
                post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResultAttempt.write(attempt).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonResultAttempt.write(
                        getCorrectMultiplicationResultAttemp()
                        ).getJson()
                );
    }

    @Test
    public void getUserStats() throws Exception {
        final List<MultiplicationResultAttempt> recentAttempts = Arrays.asList(getCorrectMultiplicationResultAttemp(), getCorrectMultiplicationResultAttemp());

        given(multiplicationService.getStatsForUser("john")).willReturn(recentAttempts);

        final MockHttpServletResponse response = mvc.perform(
                get("/results")
                        .param("alias", "john")
        ).andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonResultAttempts.write(recentAttempts).getJson());
    }

    @Test
    public void getUserById() throws Exception {

        final MultiplicationResultAttempt attempt = getCorrectMultiplicationResultAttemp();
        given(multiplicationService.getResultById(4L)).willReturn(attempt);

        final MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/results/4"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonResultAttempt.write(getCorrectMultiplicationResultAttemp())
                        .getJson())
        ;
    }

    private User getUserJohn() {
        return new User("john");
    }

    private Multiplication getMultiplication50x70() {
        return new Multiplication(50, 70);
    }

    private MultiplicationResultAttempt getCorrectMultiplicationResultAttemp() {
        return new MultiplicationResultAttempt(getUserJohn(), getMultiplication50x70(), 3500, true);
    }
}