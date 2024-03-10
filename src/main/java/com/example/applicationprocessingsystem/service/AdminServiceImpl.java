package com.example.applicationprocessingsystem.service;

import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.ApplicationStatus;
import com.example.applicationprocessingsystem.model.db.User;
import com.example.applicationprocessingsystem.repository.ApplicationRepository;
import com.example.applicationprocessingsystem.repository.UserRepository;
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
    public List<User> getUsers(int page) {
        return userRepository.findAll(PageRequest.of(page, PAGE_SIZE)).getContent();
    }

    @Override
    public List<Application> getApplications(ApplicationStatus status, int page, String sortDirection, String filterByName) {
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
