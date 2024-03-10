package com.example.applicationprocessingsystem.controller;

import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.model.db.User;
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
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<String> apiUserApplicationsApplicationIdEditDraftPut(Long applicationId, EditDraftRequest editDraftRequest) {
        Application application = userService.getApplicationById(applicationId);
        //TODO check that SecurityContextHolder.getContext().getAuthentication().getPrincipal() equals application.getUser()

        Application edited = mapper.map(editDraftRequest, Application.class);
        userService.editDraft(application, edited);
        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<ApplicationComponent> apiUserApplicationsApplicationIdGet(Long applicationId) {
        Application application = userService.getApplicationById(applicationId);
        ApplicationComponent response = mapper.map(application, ApplicationComponent.class);
        //TODO check that SecurityContextHolder.getContext().getAuthentication().getPrincipal() equals application.getUser()

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> apiUserApplicationsApplicationIdSendDraftPut(Long applicationId) {
        userService.sendDraft(applicationId);

        //TODO check that SecurityContextHolder.getContext().getAuthentication().getPrincipal() equals application.getUser()
        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<String> apiUserApplicationsCreateDraftPost(CreateDraftRequest createDraftRequest) {
        Application application = mapper.map(createDraftRequest, Application.class);

        //TODO bind user to application here with SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        userService.createDraft(application);

        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<String> apiUserApplicationsCreatePost(CreateApplicationRequest createApplicationRequest) {
        Application application = mapper.map(createApplicationRequest, Application.class);

        //TODO bind user to application here with SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        userService.createApplication(application);

        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<GetApplicationsResponse> apiUserApplicationsGetApplicationsGet(Integer page, String sortByDate) {
        User user = userService.getUser("xxx"); //TODO get current user SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        List<Application> applications = userService.getApplications(user, page, sortByDate);

        List<ApplicationComponent> response = applications.stream()
                .map(application -> mapper.map(application, ApplicationComponent.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new GetApplicationsResponse(response));
    }

}
