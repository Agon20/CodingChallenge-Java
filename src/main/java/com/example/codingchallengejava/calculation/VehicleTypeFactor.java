package com.example.codingchallengejava.calculation;


public enum VehicleTypeFactor {
    PKW(1.0),
    MOTORRAD(1.3),
    LKW(1.8),
    ELEKTROAUTO(0.9);

    private final double factor;

    VehicleTypeFactor(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}