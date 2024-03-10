package com.example.applicationprocessingsystem.controller;

import com.example.applicationprocessingsystem.exceptions.ApplicationException;
import com.example.applicationprocessingsystem.model.dto.ApiErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiErrorDto> handleApiError(ApplicationException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ApiErrorDto(exception.getStatus().getReasonPhrase(), exception.getMessage()));
    }
}
