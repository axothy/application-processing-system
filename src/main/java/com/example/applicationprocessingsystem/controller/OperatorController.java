package com.example.applicationprocessingsystem.controller;

import com.example.applicationprocessingsystem.model.db.Application;
import com.example.applicationprocessingsystem.operator.OperatorApi;
import com.example.applicationprocessingsystem.operator.model.dto.ApplicationComponent;
import com.example.applicationprocessingsystem.operator.model.dto.GetApplicationsResponse;
import com.example.applicationprocessingsystem.security.AuthUtils;
import com.example.applicationprocessingsystem.service.OperatorService;
import jakarta.annotation.security.RolesAllowed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RolesAllowed({"ROLE_OPERATOR"})
public class OperatorController implements OperatorApi {
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ResponseEntity<String> apiOperatorApplicationsApplicationIdAcceptPut(Long applicationId) {
        operatorService.accept(applicationId);
        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<ApplicationComponent> apiOperatorApplicationsApplicationIdGet(Long applicationId) {
        Application application = operatorService.getApplication(applicationId);

        ApplicationComponent response = mapper.map(application, ApplicationComponent.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> apiOperatorApplicationsApplicationIdRejectPut(Long applicationId) {
        operatorService.reject(applicationId);
        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<GetApplicationsResponse> apiOperatorApplicationsGet(String name) {
        List<Application> applications = operatorService.getApplicationsByUsername(name);

        List<ApplicationComponent> response = responseFromApplications(applications);
        return ResponseEntity.ok(new GetApplicationsResponse(response));
    }

    @Override
    public ResponseEntity<GetApplicationsResponse> apiOperatorApplicationsAllGet(Integer page, String sortByDate, String filterByName) {
        List<Application> applications;
        if (filterByName == null) {
            applications = operatorService.getApplications(page, sortByDate);
        } else {
            applications = operatorService.getApplications(page, sortByDate, filterByName);
        }

        List<ApplicationComponent> response = responseFromApplications(applications);
        return ResponseEntity.ok(new GetApplicationsResponse(response));
    }

    private List<ApplicationComponent> responseFromApplications(List<Application> applications) {
        return applications.stream()
                .map(application -> mapper.map(application, ApplicationComponent.class))
                .collect(Collectors.toList());
    }
}
