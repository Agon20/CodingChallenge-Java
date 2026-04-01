package com.example.codingchallengejava;


import com.example.codingchallengejava.calculation.RegionFactor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegionFactorTest {

    @ParameterizedTest
    @CsvSource({
            "Berlin, 1.5",
            "Hamburg, 1.4",
            "Bayern, 1.2",
            "Brandenburg, 0.9",
            "Baden-Württemberg, 1.1",
            "Bremen, 1.3",
            "Hessen, 1.1",
            "Mecklenburg-Vorpommern, 0.8",
            "Niedersachsen, 1.0",
            "Nordrhein-Westfalen, 1.3",
            "Rheinland-Pfalz, 1.0",
            "Saarland, 1.0",
            "Sachsen, 1.0",
            "Sachsen-Anhalt, 0.9",
            "Schleswig-Holstein, 1.0",
            "Thüringen, 0.9",
    })
    void shouldReturnCorrectFactorForRegion(String region, double expectedFactor){
        assertEquals(expectedFactor, RegionFactor.getByRegion(region).getFactor());
    }

    @Test
    void shouldThrowExceptionForUnknownRegion(){
        assertThrows(IllegalArgumentException.class, () -> RegionFactor.getByRegion("Mallorca"));
    }
}
