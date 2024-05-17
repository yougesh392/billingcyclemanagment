package com.usmobile.billingcyclemanagment.controller;

import com.usmobile.billingcyclemanagment.util.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatusException(ResponseStatusException ex) {
        ApiError errorDetails = new ApiError(ex.getStatusCode().value(), ex.getReason());
        return new ResponseEntity<>(errorDetails, ex.getStatusCode());
    }
}
