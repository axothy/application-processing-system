package com.example.applicationprocessingsystem.exceptions;

import org.springframework.http.HttpStatus;

public class UnableToEditDraftException extends ApplicationException {
    public UnableToEditDraftException() {
        super(HttpStatus.BAD_REQUEST, "Unable to edit application because it is not a draft");
    }
}