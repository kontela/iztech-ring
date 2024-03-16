package com.iztech.ringtracker._common_.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ApiErrorResponse {

    private String error;
    private int status;
    private String message;
    private Instant timestamp;

}
