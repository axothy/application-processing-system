package com.example.applicationprocessingsystem.service;

import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.ApplicationStatus;
import com.example.applicationprocessingsystem.model.db.User;
import com.example.applicationprocessingsystem.security.Role;

import java.util.List;

public interface AdminService {
    User changeRole(long userId, Role role);
    List<User> getUsers(int page);
    List<Application> getApplications(ApplicationStatus status, int page, String sortDirection, String filterByName);
    List<Application> getApplications(ApplicationStatus status, int page, String sortDirection);
}
