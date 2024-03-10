package com.example.applicationprocessingsystem.exceptions;

import org.springframework.http.HttpStatus;

public class UnableToGetDraftApplications extends ApplicationException {
    public UnableToGetDraftApplications() {
        super(HttpStatus.BAD_REQUEST, "Unable to get drafts");
    }
}