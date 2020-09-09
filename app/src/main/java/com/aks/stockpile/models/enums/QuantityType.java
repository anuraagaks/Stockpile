package com.aks.stockpile.models.enums;

import java.util.Map;
import java.util.TreeMap;

public enum QuantityType {

    UNITS(new TreeMap<Double, String>() {{
        put(1.0, " Units");
    }}, " Units"),
    TABLETS(new TreeMap<Double, String>() {{
        put(5.0, " Strip(s) - 5 Tablets");
        put(10.0, " Strip(s) - 10 Tablets");
        put(20.0, " Strip(s) - 20 Tablets");
        put(30.0, " Strip(s) - 30 Tablets");
        put(50.0, " Strip(s) - 50 Tablets");
        put(1.0, " Tablet(s)");
    }}, " Tablet(s)"),
    NONE(new TreeMap<Double, String>() {{
        put(1.0, "");
    }}, ""),
    WEIGHT_KG(new TreeMap<Double, String>() {{
        put(0.001, " Gram(s)");
        put(1.0, " Kg(s)");
        put(0.000001, " Milligram(s)");
    }}, " Kg(s)"),
    WEIGHT_L(new TreeMap<Double, String>() {{
        put(1.0, " Litre(s)");
        put(0.001, " Millilitre(s)");
    }}, " Litre(s)");

    private Map<Double, String> weightMap;

    private String value;

    QuantityType(Map<Double, String> weightMap, String value) {
        this.weightMap = weightMap;
        this.value = value;
    }

    public Map<Double, String> getWeightMap() {
        return weightMap;
    }

    public String getValue() {
        return value;
    }
}
