package com.example.codingchallengejava.application.entity;

import com.example.codingchallengejava.calculation.VehicleTypeFactor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class InsuranceApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String postalCode;
    private int annualMileage;

    @Enumerated(EnumType.STRING)
    private VehicleTypeFactor vehicleTypeFactor;
    private double premium;
    private LocalDateTime createdDate;

    public InsuranceApplication() {}

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
    public double getPremium() {
        return premium;
    }
    public void setPremium(double premium) {
        this.premium = premium;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
