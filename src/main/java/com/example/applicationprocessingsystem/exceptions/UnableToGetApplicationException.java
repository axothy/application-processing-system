package com.example.applicationprocessingsystem.exceptions;

import org.springframework.http.HttpStatus;

public class UnableToGetApplicationException extends ApplicationException {
    public UnableToGetApplicationException() {
        super(HttpStatus.BAD_REQUEST, "Unable to get this application");
    }
}
