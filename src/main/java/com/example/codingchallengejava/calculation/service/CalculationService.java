package com.example.codingchallengejava.calculation.service;

import com.example.codingchallengejava.calculation.KilometerFactor;
import com.example.codingchallengejava.calculation.PostcodeLoader;
import com.example.codingchallengejava.calculation.RegionFactor;
import com.example.codingchallengejava.calculation.dtos.CalculationRequest;
import com.example.codingchallengejava.calculation.dtos.CalculationResponse;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

    private final PostcodeLoader postcodeLoader;

    public CalculationService(PostcodeLoader postcodeLoader) {
        this.postcodeLoader = postcodeLoader;
    }

    public CalculationResponse calculatePremium(CalculationRequest calculationRequest) {
        if(calculationRequest.getAnnualMileage() < 0){
            throw new IllegalArgumentException("Kilometerleistung darf nicht negativ sein.");
        }

        double mileageFactor = KilometerFactor.getFactor(calculationRequest.getAnnualMileage());
        double vehicleTypeFactor = calculationRequest.getVehicleTypeFactor().getFactor();
        double regionFactor = getRegionFactor(calculationRequest.getPostalCode());

        return new CalculationResponse(Math.round((mileageFactor * vehicleTypeFactor * regionFactor) * 1000.0) / 1000.0);
    }

    private double getRegionFactor(String postalCode){
        String region = postcodeLoader.getRegion(postalCode);
        if(region == null){
            throw new IllegalArgumentException("Unbekannte Postleitzahl: " + postalCode);
        }
        return RegionFactor.getByRegion(region).getFactor();
    }
}
