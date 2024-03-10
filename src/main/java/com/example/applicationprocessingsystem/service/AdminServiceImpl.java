package com.example.applicationprocessingsystem.service;

import com.example.applicationprocessingsystem.exceptions.UnableToGetDraftApplications;
import com.example.applicationprocessingsystem.exceptions.UserNotFoundException;
import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.ApplicationStatus;
import com.example.applicationprocessingsystem.model.db.User;
import com.example.applicationprocessingsystem.repository.ApplicationRepository;
import com.example.applicationprocessingsystem.repository.UserRepository;
import com.example.applicationprocessingsystem.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private static final int PAGE_SIZE = 5;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public User changeRole(long userId, Role role) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (role != Role.ROLE_ADMIN) {
            user.setRole(role);
        }

        return userRepository.save(user);
    }


    @Override
    public List<User> getUsers(int page) {
        return userRepository.findAll(PageRequest.of(page, PAGE_SIZE)).getContent();
    }

    @Override
    public List<Application> getApplications(ApplicationStatus status, int page, String sortDirection, String filterByName) {
        if (status == ApplicationStatus.DRAFT) {
            throw new UnableToGetDraftApplications();
        }

        Sort sort = getDateSort(sortDirection);

        List<Application> result = applicationRepository.findByNameContainingAndStatus(
                filterByName,
                status,
                PageRequest.of(page, PAGE_SIZE, sort)
        ).getContent();

        return result;
    }

    @Override
    public List<Application> getApplications(ApplicationStatus status, int page, String sortDirection) {
        Sort sort = getDateSort(sortDirection);

        List<Application> result = applicationRepository.findByStatus(
                status,
                PageRequest.of(page, PAGE_SIZE, sort)
        ).getContent();

        return result;
    }

    private static Sort getDateSort(String sortDirection) {
        return sortDirection.equalsIgnoreCase("asc")
                ? Sort.by("creationDate").ascending() : Sort.by("creationDate").descending();
    }
}
