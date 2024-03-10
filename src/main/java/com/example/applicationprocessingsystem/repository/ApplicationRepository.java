package com.example.applicationprocessingsystem.repository;

import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.ApplicationStatus;
import com.example.applicationprocessingsystem.model.db.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findApplicationById(Long id);
    Page<Application> findByUser(User user, Pageable pageable);
    List<Application> findByUserAndStatus(User user, ApplicationStatus status);
    Page<Application> findByStatus(ApplicationStatus status, Pageable pageable);
    Page<Application> findByNameContainingAndStatus(String infix, ApplicationStatus status, Pageable pageable);
}
