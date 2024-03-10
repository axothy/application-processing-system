package com.example.applicationprocessingsystem.service;

import com.example.applicationprocessingsystem.model.db.Application;

import java.util.List;

public interface OperatorService {
    Application getApplication(Long applicationId);
    List<Application> getApplicationsByUsername(String username);
    List<Application> getApplications(int page, String sortDirection, String filterByName);
    List<Application> getApplications(int page, String sortDirection);
    Application accept(Long applicationId);
    Application reject(Long applicationId);
}
