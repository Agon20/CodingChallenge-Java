package com.example.codingchallengejava.calculation;

public class KilometerFactor {

    public static double getFactor(int kilometer){
        if (kilometer <= 5000){
            return 0.5;
        } else if (kilometer <= 10000){
            return 1.0;
        } else if (kilometer <= 20000){
            return 1.5;
        }
        return 2.0;
    }
}
