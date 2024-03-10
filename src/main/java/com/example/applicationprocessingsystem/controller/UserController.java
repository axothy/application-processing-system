package com.example.applicationprocessingsystem.controller;

import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.User;
import com.example.applicationprocessingsystem.security.AuthUtils;
import com.example.applicationprocessingsystem.service.UserService;
import com.example.applicationprocessingsystem.user.UserApi;
import com.example.applicationprocessingsystem.user.model.dto.ApplicationComponent;
import com.example.applicationprocessingsystem.user.model.dto.CreateApplicationRequest;
import com.example.applicationprocessingsystem.user.model.dto.CreateDraftRequest;
import com.example.applicationprocessingsystem.user.model.dto.EditDraftRequest;
import com.example.applicationprocessingsystem.user.model.dto.GetApplicationsResponse;
import jakarta.annotation.security.RolesAllowed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RolesAllowed({"ROLE_USER"})
public class UserController implements UserApi {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AuthUtils utils;

    @Override
    public ResponseEntity<String> apiUserApplicationsApplicationIdEditDraftPut(Long applicationId, EditDraftRequest editDraftRequest) {
        Application application = userService.getApplicationById(applicationId);

        if (!utils.isValidUser(application.getUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Application edited = mapper.map(editDraftRequest, Application.class);
        userService.editDraft(application, edited);
        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<ApplicationComponent> apiUserApplicationsApplicationIdGet(Long applicationId) {
        Application application = userService.getApplicationById(applicationId);

        if (!utils.isValidUser(application.getUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        ApplicationComponent response = mapper.map(application, ApplicationComponent.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> apiUserApplicationsApplicationIdSendDraftPut(Long applicationId) {
        Application draft = userService.getApplicationById(applicationId);

        if (!utils.isValidUser(draft.getUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        userService.sendDraft(draft);
        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<String> apiUserApplicationsCreateDraftPost(CreateDraftRequest createDraftRequest) {
        Application application = mapper.map(createDraftRequest, Application.class);

        application.setUser(utils.getUserDetails());
        userService.createDraft(application);

        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<String> apiUserApplicationsCreatePost(CreateApplicationRequest createApplicationRequest) {
        Application application = mapper.map(createApplicationRequest, Application.class);

        application.setUser(utils.getUserDetails());
        userService.createApplication(application);

        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<GetApplicationsResponse> apiUserApplicationsGet(Integer page, String sortByDate) {
        User user = utils.getUserDetails();
        List<Application> applications = userService.getApplications(user, page, sortByDate);

        List<ApplicationComponent> response = applications.stream()
                .map(application -> mapper.map(application, ApplicationComponent.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new GetApplicationsResponse(response));
    }

}
