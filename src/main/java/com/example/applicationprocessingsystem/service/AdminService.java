package com.example.applicationprocessingsystem.service;

import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.ApplicationStatus;
import com.example.applicationprocessingsystem.model.db.User;

import java.util.List;

public interface AdminService {
    List<User> getUsers(int page);
    List<Application> getApplications(ApplicationStatus status, int page, String sortDirection, String filterByName);
    List<Application> getApplications(ApplicationStatus status, int page, String sortDirection);
}
