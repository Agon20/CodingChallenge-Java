package com.example.codingchallengejava.application.service;

import com.example.codingchallengejava.application.repository.ApplicationRepository;
import com.example.codingchallengejava.application.dtos.ApplicationRequest;
import com.example.codingchallengejava.application.dtos.ApplicationResponse;
import com.example.codingchallengejava.application.entity.InsuranceApplication;
import com.example.codingchallengejava.calculation.dtos.CalculationRequest;
import com.example.codingchallengejava.calculation.dtos.CalculationResponse;
import com.example.codingchallengejava.calculation.service.CalculationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CalculationService calculationService;

    public ApplicationService(ApplicationRepository applicationRepository, CalculationService calculationService) {
        this.applicationRepository = applicationRepository;
        this.calculationService = calculationService;
    }

    public ApplicationResponse createApplication(ApplicationRequest applicationRequest) {
        CalculationRequest calculationRequest = new CalculationRequest(
                applicationRequest.getPostalCode(),
                applicationRequest.getAnnualMileage(),
                applicationRequest.getVehicleTypeFactor()
        );

        CalculationResponse calculationResponse = calculationService.calculatePremium(calculationRequest);

        InsuranceApplication insuranceApplication = new InsuranceApplication();
        insuranceApplication.setPostalCode(applicationRequest.getPostalCode());
        insuranceApplication.setAnnualMileage(applicationRequest.getAnnualMileage());
        insuranceApplication.setVehicleTypeFactor(applicationRequest.getVehicleTypeFactor());
        insuranceApplication.setPremium(calculationResponse.getPremium());
        insuranceApplication.setCreatedDate(LocalDateTime.now());

        InsuranceApplication saved = applicationRepository.save(insuranceApplication);

        return new ApplicationResponse(saved.getId(), saved.getPremium(), saved.getCreatedDate());
    }
}
