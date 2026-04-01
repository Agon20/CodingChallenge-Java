package com.example.codingchallengejava;

import com.example.codingchallengejava.calculation.controller.CalculationController;
import com.example.codingchallengejava.calculation.dtos.CalculationResponse;
import com.example.codingchallengejava.calculation.service.CalculationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculationController.class)
public class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculationService calculationService;

    @Test
    void shouldReturnCalculatedPremium() throws Exception {
        when(calculationService.calculatePremium(any())).thenReturn(new CalculationResponse(1.65));

        mockMvc.perform(post("/api/calculation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "postalCode": "79189",
                        "annualMileage": 15000,
                        "vehicleTypeFactor": "MOTORRAD"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium").value(1.65));
    }

    @Test
    void shouldReturnBadRequestForInvalidInput() throws Exception {
        mockMvc.perform(post("/api/calculation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "postalCode": "79189",
                        "annualMileage": 15000,
                        "vehicleTypeFactor": "FAHRRAD"
                    }
                """))
                .andExpect(status().isBadRequest());
    }
}
