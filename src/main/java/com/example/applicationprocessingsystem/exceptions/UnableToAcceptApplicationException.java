package com.example.applicationprocessingsystem.exceptions;

import org.springframework.http.HttpStatus;

public class UnableToAcceptApplication extends ApplicationException {
    public UnableToAcceptApplication() {
        super(HttpStatus.BAD_REQUEST, "Unable to accept this application");
    }
}
