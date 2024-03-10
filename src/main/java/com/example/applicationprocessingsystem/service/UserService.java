package com.example.applicationprocessingsystem.service;

import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUser(String username);
    Application getApplicationById(Long applicationId);
    List<Application> getApplications(User user, int page, String sortDirection);
    Application createApplication(String name, String text, String phoneNumber, User user);
    Application createDraft(String name, String text, String phoneNumber, User user);
    Application editDraft(Application sourceApplication, String newName, String newText, String newPhoneNumber);
    Application sendDraft(Application draft);
}
