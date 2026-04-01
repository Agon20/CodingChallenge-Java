package com.example.codingchallengejava.calculation.dtos;

public class CalculationResponse {

    private double premium;

    public CalculationResponse(double premium) {
        this.premium = premium;
    }
    public double getPremium() {
        return premium;
    }
}
