package com.example.codingchallengejava.application.controller;

import com.example.codingchallengejava.application.service.ApplicationService;
import com.example.codingchallengejava.application.dtos.ApplicationRequest;
import com.example.codingchallengejava.application.dtos.ApplicationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody ApplicationRequest applicationRequest) {
        ApplicationResponse applicationResponse = applicationService.createApplication(applicationRequest);
        return ResponseEntity.ok(applicationResponse);
    }
}
