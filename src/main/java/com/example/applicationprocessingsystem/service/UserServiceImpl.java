package com.example.applicationprocessingsystem.service;

import com.example.applicationprocessingsystem.exceptions.ApplicationNotFoundException;
import com.example.applicationprocessingsystem.exceptions.UnableToEditDraftException;
import com.example.applicationprocessingsystem.exceptions.UnableToSendDraftException;
import com.example.applicationprocessingsystem.exceptions.UserNotFoundException;
import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.ApplicationStatus;
import com.example.applicationprocessingsystem.model.db.User;
import com.example.applicationprocessingsystem.repository.ApplicationRepository;
import com.example.applicationprocessingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;

@Service
public class UserServiceImpl implements UserService {
    private static final int PAGE_SIZE = 5;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Application getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(ApplicationNotFoundException::new);
    }

    @Override
    public List<Application> getApplications(User user, int page, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by("creationDate").ascending() : Sort.by("creationDate").descending();

        return applicationRepository.findByUser(
                user,
                PageRequest.of(page, PAGE_SIZE, sort)
        ).getContent();
    }

    @Override
    public Application createApplication(String name, String text, String phoneNumber, User user) {
        Application application = new Application();
        application.setName(name);
        application.setText(text);
        application.setUser(user);
        application.setStatus(ApplicationStatus.SENT);
        application.setCreationDate(LocalDateTime.now());
        application.setPhone(phoneNumberService.getPhoneNumber(phoneNumber));

        return applicationRepository.save(application);
    }

    @Override
    public Application createDraft(String name, String text, String phoneNumber, User user) {
        Application draft = new Application();
        draft.setName(name);
        draft.setText(text);
        draft.setUser(user);
        draft.setStatus(ApplicationStatus.DRAFT);
        draft.setCreationDate(LocalDateTime.now());
        draft.setPhone(phoneNumberService.getPhoneNumber(phoneNumber));

        return applicationRepository.save(draft);
    }

    @Override
    public Application editDraft(Application sourceApplication, String newName, String newText, String newPhoneNumber) {
        if (sourceApplication.getStatus() != ApplicationStatus.DRAFT) {
            throw new UnableToEditDraftException();
        }

        sourceApplication.setText(newText);
        sourceApplication.setName(newName);
        sourceApplication.setPhone(phoneNumberService.getPhoneNumber(newPhoneNumber));

        return applicationRepository.save(sourceApplication);
    }

    @Override
    public Application sendDraft(Application draft) {
        if (draft.getStatus() == ApplicationStatus.DRAFT) {
            draft.setCreationDate(LocalDateTime.now());
            draft.setStatus(ApplicationStatus.SENT);
        } else {
            throw new UnableToSendDraftException();
        }

        return applicationRepository.save(draft);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }
}
