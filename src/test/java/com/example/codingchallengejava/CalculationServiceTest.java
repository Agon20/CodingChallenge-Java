package com.example.codingchallengejava;

import com.example.codingchallengejava.calculation.PostcodeLoader;
import com.example.codingchallengejava.calculation.VehicleTypeFactor;
import com.example.codingchallengejava.calculation.dtos.CalculationRequest;
import com.example.codingchallengejava.calculation.dtos.CalculationResponse;
import com.example.codingchallengejava.calculation.service.CalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculationServiceTest {

    @Mock
    private PostcodeLoader postcodeLoader;

    @InjectMocks
    private CalculationService calculationService;

    @ParameterizedTest
    @CsvSource({
            // postalCode, region,              mileage, vehicleType,   expected
            "79189, Baden-Württemberg,          8000,    PKW,           1.1",
            "10115, Berlin,                     25000,   LKW,           5.4",
            "14467, Brandenburg,                3000,    ELEKTROAUTO,   0.405",
            "20095, Hamburg,                    5000,    PKW,           0.7",
            "80331, Bayern,                     10001,   MOTORRAD,      2.34",
            "01067, Sachsen,                    500,     ELEKTROAUTO,   0.45"
    })

    void shouldCalculatePremiumCorrectly(String postalCode, String region, int annualMileage,
                                         VehicleTypeFactor vehicleTypeFactor, double expectedPremium){
        when(postcodeLoader.getRegion(postalCode)).thenReturn(region);

        CalculationRequest calculationRequest = new CalculationRequest(postalCode, annualMileage, vehicleTypeFactor);
        CalculationResponse calculationResponse = calculationService.calculatePremium(calculationRequest);

        assertEquals(expectedPremium, calculationResponse.getPremium(), 0.001);
    }

    @Test
    void shouldThrowExceptionForUnknownPostalCode(){
        when(postcodeLoader.getRegion("99999")).thenReturn(null);

        CalculationRequest calculationRequest = new CalculationRequest("99999", 5000, VehicleTypeFactor.PKW);

        assertThrows(IllegalArgumentException.class, () -> calculationService.calculatePremium(calculationRequest));
    }

    @Test
    void shouldThrowExceptionForNegativeMileage(){
        CalculationRequest calculationRequest = new CalculationRequest("10115",-1, VehicleTypeFactor.MOTORRAD);

        assertThrows(IllegalArgumentException.class, () -> calculationService.calculatePremium(calculationRequest));
    }
}
