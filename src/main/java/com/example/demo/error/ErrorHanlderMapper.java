package com.example.demo.error;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHanlderMapper {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleInvalidArgumentException(@NonNull final IllegalArgumentException e) {
        final ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND.value(), "IllegalArguments");
        return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
    }
}
