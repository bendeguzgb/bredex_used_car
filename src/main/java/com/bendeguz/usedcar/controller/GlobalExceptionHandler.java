package com.bendeguz.usedcar.controller;

import com.bendeguz.usedcar.exception.CommonServiceException;
import com.bendeguz.usedcar.model.ErrorMessage;
import com.bendeguz.usedcar.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleCommonServiceException(UserAlreadyExistsException e) {
        return defaultError(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return defaultError(e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(CommonServiceException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(CommonServiceException e) {
        return defaultError(e.getMessage());
    }


    private ResponseEntity<ErrorMessage> defaultError(String message) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(message));
    }
}
