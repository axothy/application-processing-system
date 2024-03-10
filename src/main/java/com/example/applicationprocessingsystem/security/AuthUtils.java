package com.example.applicationprocessingsystem.security;

import com.example.applicationprocessingsystem.model.db.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public final class AuthUtils {
    private AuthUtils() {

    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getName() {
        return getAuthentication().getName();
    }

    public User getUserDetails() {
        return (User) getAuthentication().getPrincipal();
    }

    public boolean isValidUser(User user) {
        return getUserDetails().getId() == user.getId();
    }
}
