package com.example.applicationprocessingsystem.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationNotFoundException extends ApplicationException {
    public ApplicationNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User application not found");
    }
}
