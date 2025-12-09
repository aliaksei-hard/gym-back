package com.learning.gymback.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorObject> handleSlotFullException(RuntimeException ex) {
        ErrorObject error = new ErrorObject(
                500,
                ex.getMessage(),
                "some details if needed" // Add details if needed
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
