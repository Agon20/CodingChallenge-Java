package com.example.codingchallengejava.application.dtos;

import com.example.codingchallengejava.calculation.VehicleTypeFactor;

public class ApplicationRequest {

    private String postalCode;
    private int annualMileage;
    private VehicleTypeFactor vehicleTypeFactor;

    public ApplicationRequest(String postalCode, int annualMileage, VehicleTypeFactor vehicleTypeFactor) {
        this.postalCode = postalCode;
        this.annualMileage = annualMileage;
        this.vehicleTypeFactor = vehicleTypeFactor;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public int getAnnualMileage() {
        return annualMileage;
    }
    public void setAnnualMileage(int annualMileage) {
        this.annualMileage = annualMileage;
    }
    public VehicleTypeFactor getVehicleTypeFactor() {
        return vehicleTypeFactor;
    }
    public void setVehicleTypeFactor(VehicleTypeFactor vehicleTypeFactor) {
        this.vehicleTypeFactor = vehicleTypeFactor;
    }
}
