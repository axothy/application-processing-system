package com.example.applicationprocessingsystem.exceptions;

import org.springframework.http.HttpStatus;

public class UnableToSendDraftException extends ApplicationException {
    public UnableToSendDraftException() {
        super(HttpStatus.BAD_REQUEST, "Unable to send application. It is not draft");
    }
}
