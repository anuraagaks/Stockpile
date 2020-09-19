package com.aks.stockpile.models.enums;

import java.util.Map;
import java.util.TreeMap;

public enum QuantityType {

    UNITS(new TreeMap<String, Double>() {{
        put(" Units", 1.0);
    }}, " Units"),
    TABLETS(new TreeMap<String, Double>() {{
        put(" Strip(s) - 5 Tablets", 5.0);
        put(" Strip(s) - 10 Tablets", 10.0);
        put(" Strip(s) - 20 Tablets", 20.0);
        put(" Tablet(s)", 1.0);
    }}, " Tablet(s)"),
    NONE(new TreeMap<String, Double>() {{
        put("", 1.0);
    }}, ""),
    WEIGHT_KG(new TreeMap<String, Double>() {{
        put(" Gram(s)", 0.001);
        put(" Kg(s)", 1.0);
    }}, " Kg(s)"),
    WEIGHT_L(new TreeMap<String, Double>() {{
        put(" Litre(s)", 1.0);
        put(" Millilitre(s)", 0.001);
    }}, " Litre(s)");

    private Map<String, Double> weightMap;

    private String value;

    QuantityType(Map<String, Double> weightMap, String value) {
        this.weightMap = weightMap;
        this.value = value;
    }

    public Map<String, Double> getWeightMap() {
        return weightMap;
    }

    public String getValue() {
        return value;
    }
}
