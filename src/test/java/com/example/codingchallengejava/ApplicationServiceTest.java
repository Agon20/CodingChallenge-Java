package com.example.codingchallengejava;

import com.example.codingchallengejava.application.dtos.ApplicationRequest;
import com.example.codingchallengejava.application.dtos.ApplicationResponse;
import com.example.codingchallengejava.application.entity.InsuranceApplication;
import com.example.codingchallengejava.application.repository.ApplicationRepository;
import com.example.codingchallengejava.application.service.ApplicationService;
import com.example.codingchallengejava.calculation.VehicleTypeFactor;
import com.example.codingchallengejava.calculation.dtos.CalculationResponse;
import com.example.codingchallengejava.calculation.service.CalculationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private CalculationService calculationService;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void shouldCreateApplicationAndPersist(){
        when(calculationService.calculatePremium(any())).thenReturn(new CalculationResponse(1.65));
        when(applicationRepository.save(any())).thenAnswer(invocation -> {
            InsuranceApplication insuranceApplication = invocation.getArgument(0);
            insuranceApplication.setId(1L);
            return insuranceApplication;
        });

        ApplicationRequest applicationRequest = new ApplicationRequest("79189", 15000, VehicleTypeFactor.MOTORRAD);
        ApplicationResponse applicationResponse = applicationService.createApplication(applicationRequest);

        assertEquals(1L, applicationResponse.getId());
        assertEquals(1.65, applicationResponse.getPremium(), 0.001);
        assertNotNull(applicationResponse.getCreatedDate());
    }

    @Test
    void shouldPassCorrectDataToEntity(){
        when(calculationService.calculatePremium(any())).thenReturn(new CalculationResponse(2.0));
        when(applicationRepository.save(any())).thenAnswer(invocation -> {
            InsuranceApplication insuranceApplication = invocation.getArgument(0);
            insuranceApplication.setId(1L);
            return insuranceApplication;
        });

        ApplicationRequest applicationRequest = new ApplicationRequest("10115", 25000, VehicleTypeFactor.LKW);
        applicationService.createApplication(applicationRequest);

        ArgumentCaptor<InsuranceApplication> captor = ArgumentCaptor.forClass(InsuranceApplication.class);
        verify(applicationRepository).save(captor.capture());

        InsuranceApplication saved = captor.getValue();
        assertEquals("10115", saved.getPostalCode());
        assertEquals(25000, saved.getAnnualMileage());
        assertEquals(VehicleTypeFactor.LKW, saved.getVehicleTypeFactor());
        assertEquals(2.0, saved.getPremium(), 0.001);
        assertNotNull(saved.getCreatedDate());
    }

    @Test
    void shouldPropagateCalculationException(){
        when(calculationService.calculatePremium(any()))
                .thenThrow(new IllegalArgumentException("Unbekannte Postleitzahl: 99999"));

        ApplicationRequest applicationRequest = new ApplicationRequest("99999", 5000, VehicleTypeFactor.ELEKTROAUTO);

        assertThrows(IllegalArgumentException.class, () -> applicationService.createApplication(applicationRequest));

        verify(applicationRepository, never()).save(any());
    }
}
