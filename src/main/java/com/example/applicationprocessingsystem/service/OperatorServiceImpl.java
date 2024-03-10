package com.example.applicationprocessingsystem.service;

import com.example.applicationprocessingsystem.exceptions.ApplicationNotFoundException;
import com.example.applicationprocessingsystem.exceptions.UnableToAcceptApplicationException;
import com.example.applicationprocessingsystem.exceptions.UnableToGetApplicationException;
import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.ApplicationStatus;
import com.example.applicationprocessingsystem.model.db.User;
import com.example.applicationprocessingsystem.repository.ApplicationRepository;
import com.example.applicationprocessingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {
    private static final int PAGE_SIZE = 5;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Application getApplication(Long applicationId) {
        var application = applicationRepository.findApplicationById(applicationId).orElseThrow(ApplicationNotFoundException::new);
        if (!isSent(application)) {
            throw new UnableToGetApplicationException();
        }

        return application;
    }

    @Override
    public List<Application> getApplicationsByUsername(String username) {
        List<User> users = userRepository.findByUsernameContaining(username);
        List<Application> result = new ArrayList<>();

        for(User user : users) {
            result.addAll(applicationRepository.findByUserAndStatus(user, ApplicationStatus.SENT));
        }

        return result;
    }

    @Override
    public List<Application> getApplications(int page, String sortDirection, String filterByName) {
        Sort sort = getDateSort(sortDirection);

        List<Application> result = applicationRepository.findByNameContainingAndStatus(
                filterByName,
                ApplicationStatus.SENT,
                PageRequest.of(page, PAGE_SIZE, sort)
        ).getContent();

        return result;
    }

    @Override
    public List<Application> getApplications(int page, String sortDirection) {
        Sort sort = getDateSort(sortDirection);

        List<Application> result = applicationRepository.findByStatus(
                ApplicationStatus.SENT,
                PageRequest.of(page, PAGE_SIZE, sort)
        ).getContent();

        return result;
    }

    private static Sort getDateSort(String sortDirection) {
        return sortDirection.equalsIgnoreCase("asc")
                ? Sort.by("creationDate").ascending() : Sort.by("creationDate").descending();
    }

    @Override
    public Application accept(Long applicationId) {
        var application = getApplication(applicationId);
        
        if (!isSent(application)) {
            throw new UnableToAcceptApplicationException();
        }
        
        application.setStatus(ApplicationStatus.ACCEPTED);
        return applicationRepository.save(application);
    }

    @Override
    public Application reject(Long applicationId) {
        var application = getApplication(applicationId);

        if (!isSent(application)) {
            throw new UnableToAcceptApplicationException();
        }

        application.setStatus(ApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }

    private static boolean isSent(Application application) {
        return application.getStatus() == ApplicationStatus.SENT;
    }
}
