package com.example.applicationprocessingsystem.exceptions;

import org.springframework.http.HttpStatus;

public class UnableToAcceptApplicationException extends ApplicationException {
    public UnableToAcceptApplicationException() {
        super(HttpStatus.BAD_REQUEST, "Unable to accept this application");
    }
}
