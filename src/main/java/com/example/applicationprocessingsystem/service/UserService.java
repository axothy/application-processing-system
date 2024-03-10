package com.example.applicationprocessingsystem.service;

import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.User;

import java.util.List;

public interface UserService {
    User getUser(Long userId);
    User getUser(String username);
    Application getApplicationById(Long applicationId);
    List<Application> getApplications(User user, int page, String sortDirection);
    Application createApplication(Application newApplication);
    Application createDraft(Application newDraft);
    Application editDraft(Application sourceApplication, Application edited);
    Application sendDraft(Long applicationId);
}
