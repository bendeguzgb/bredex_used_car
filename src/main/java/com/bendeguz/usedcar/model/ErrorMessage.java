package com.bendeguz.usedcar.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.UUID;

@Data
public class ErrorMessage {

    private Instant timestamp = Instant.now();
    private String errorCode = UUID.randomUUID().toString();
    private int status;
    private String message;


    public static ErrorMessage of(String message) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(message);
        errorMessage.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorMessage;
    }
}
