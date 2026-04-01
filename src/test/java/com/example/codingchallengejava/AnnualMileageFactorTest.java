package com.example.codingchallengejava;

import com.example.codingchallengejava.calculation.KilometerFactor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnnualMileageFactorTest {

    @ParameterizedTest
    @CsvSource({
            "0, 0.5",
            "2500, 0.5",
            "5000, 0.5",
            "5001, 1.0",
            "7500, 1.0",
            "10000, 1.0",
            "10001, 1.5",
            "15000, 1.5",
            "20000, 1.5",
            "20001, 2.0",
            "50000, 2.0"
    })
    void shouldReturnCorrectFactor(int annualMileage, double expectedFactor){
        assertEquals(expectedFactor, KilometerFactor.getFactor(annualMileage));
    }
}
