package com.aks.stockpile.models.enums;

import java.util.Map;
import java.util.TreeMap;

public enum SortOption {

    NAME_A_Z("Name - A->Z"),
    NAME_Z_A("Name - Z->A"),
    CATEGORY_A_Z("Category - A->Z"),
    CATEGORY_Z_A("Category - Z->A"),
    QUANTITY_H_L("Quantity - High->Low"),
    QUANTITY_L_H("Quantity - Low->High");

    private static final Map<String, SortOption> nameMap = new TreeMap<>();

    static {
        for (SortOption opt : SortOption.values()) {
            nameMap.put(opt.getDisplayName(), opt);
        }
    }

    private String displayName;

    SortOption(String displayName) {
        this.displayName = displayName;
    }

    public static String[] getValues() {
        return nameMap.keySet().toArray(new String[0]);
    }

    public static SortOption ofName(String name) {
        return nameMap.get(name);
    }

    public String getDisplayName() {
        return displayName;
    }
}
