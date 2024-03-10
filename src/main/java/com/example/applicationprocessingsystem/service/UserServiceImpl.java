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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final int PAGE_SIZE = 5;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

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
    public Application createApplication(Application newApplication) {
        newApplication.setStatus(ApplicationStatus.SENT);
        newApplication.setCreationDate(LocalDateTime.now());

        return applicationRepository.save(newApplication);
    }

    @Override
    public Application createDraft(Application newDraft) {
        newDraft.setStatus(ApplicationStatus.DRAFT);
        //TODO: creation date of draft is current time or when draft is sent to operators?
        newDraft.setCreationDate(LocalDateTime.now());

        return applicationRepository.save(newDraft);
    }

    @Override
    public Application editDraft(Application sourceApplication, Application edited) {
        if (sourceApplication.getStatus() != ApplicationStatus.DRAFT) {
            throw new UnableToEditDraftException();
        }

        sourceApplication.setText(edited.getText());
        sourceApplication.setName(edited.getName());
        sourceApplication.setPhoneNumber(edited.getPhoneNumber());

        return applicationRepository.save(sourceApplication);
    }

    @Override
    public Application sendDraft(Long applicationId) {
        Application application = getApplicationById(applicationId);

        if (application.getStatus() == ApplicationStatus.DRAFT) {
            application.setCreationDate(LocalDateTime.now());
            application.setStatus(ApplicationStatus.SENT);
        } else {
            throw new UnableToSendDraftException();
        }

        return applicationRepository.save(application);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

    }
}
