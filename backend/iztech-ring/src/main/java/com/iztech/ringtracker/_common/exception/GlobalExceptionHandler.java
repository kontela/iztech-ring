package com.iztech.ringtracker._common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiErrorResponse r = new ApiErrorResponse();

        r.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        r.setStatus(HttpStatus.NOT_FOUND.value());
        r.setMessage(ex.getMessage());
        r.setTimestamp(Instant.now());

        return new ResponseEntity<>(r, HttpStatus.NOT_FOUND);
    }
}
