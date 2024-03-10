package com.example.applicationprocessingsystem.controller;

import com.example.applicationprocessingsystem.admin.AdminApi;
import com.example.applicationprocessingsystem.admin.model.dto.ApplicationComponent;
import com.example.applicationprocessingsystem.admin.model.dto.GetApplicationsResponse;
import com.example.applicationprocessingsystem.admin.model.dto.GetUsersResponse;
import com.example.applicationprocessingsystem.admin.model.dto.UserComponent;
import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.ApplicationStatus;
import com.example.applicationprocessingsystem.model.db.User;
import com.example.applicationprocessingsystem.security.Role;
import com.example.applicationprocessingsystem.service.AdminService;
import jakarta.annotation.security.RolesAllowed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RolesAllowed({"ROLE_ADMIN"})
public class AdminController implements AdminApi {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ResponseEntity<String> apiAdminUserIdRolePut(String role, Long userId) {
        adminService.changeRole(userId, Role.valueOf(role));

        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<GetApplicationsResponse> apiAdminApplicationsGet(String status, Integer page, String sortByDate, String filterByName) {
        List<Application> applications;
        if (filterByName != null) {
            applications = adminService.getApplications(ApplicationStatus.valueOf(status), page, sortByDate, filterByName);
        } else {
            applications = adminService.getApplications(ApplicationStatus.valueOf(status), page, sortByDate);
        }

        return ResponseEntity.ok(new GetApplicationsResponse(responseFromApplications(applications)));
    }

    @Override
    public ResponseEntity<GetUsersResponse> apiAdminUsersGet(Integer page) {
        List<User> users = adminService.getUsers(page);

        return ResponseEntity.ok(new GetUsersResponse(responseFromUsers(users)));
    }

    private List<UserComponent> responseFromUsers(List<User> users) {
        return users.stream()
                .map(user -> mapper.map(user, UserComponent.class))
                .collect(Collectors.toList());
    }

    private List<ApplicationComponent> responseFromApplications(List<Application> applications) {
        return applications.stream()
                .map(application -> mapper.map(application, ApplicationComponent.class))
                .collect(Collectors.toList());
    }
}
