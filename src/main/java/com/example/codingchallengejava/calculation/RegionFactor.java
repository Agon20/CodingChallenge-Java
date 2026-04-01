package com.example.codingchallengejava.calculation;

import java.util.HashMap;
import java.util.Map;

public enum RegionFactor {
    BERLIN(1.5, "Berlin"),
    HAMBURG(1.4, "Hamburg"),
    BAYERN(1.2, "Bayern"),
    BRANDENBURG(0.9, "Brandenburg"),
    BADEN_WUERTTEMBERG(1.1, "Baden-Württemberg"),
    BREMEN(1.3, "Bremen"),
    HESSEN(1.1, "Hessen"),
    MECKLENBURG_VORPOMMERN(0.8, "Mecklenburg-Vorpommern"),
    NIEDERSACHSEN(1.0, "Niedersachsen"),
    NORDRHEIN_WESTFALEN(1.3, "Nordrhein-Westfalen"),
    RHEINLAND_PFALZ(1.0, "Rheinland-Pfalz"),
    SAARLAND(1.0, "Saarland"),
    SACHSEN(1.0, "Sachsen"),
    SACHSEN_ANHALT(0.9, "Sachsen-Anhalt"),
    SCHLESWIG_HOLSTEIN(1.0, "Schleswig-Holstein"),
    THUERINGEN(0.9, "Thüringen");

    private final double factor;
    private final String region;

    private static final Map<String, RegionFactor> BY_REGION = new HashMap<>();

    static {
        for (RegionFactor region : values()) {
            BY_REGION.put(region.region, region);
        }
    }

    RegionFactor(double factor, String region) {
        this.factor = factor;
        this.region = region;
    }

    public double getFactor() {
        return factor;
    }

    public static RegionFactor getByRegion(String region) {
        RegionFactor result = BY_REGION.get(region);
        if (result == null) {
            throw new IllegalArgumentException("Unbekanntes Bundesland: " + region);
        }
        return result;
    }
}
