package com.aks.stockpile.models.enums;

public enum InventoryUpdateType {

    ADDED("Added"),
    CONSUMED("Consumed");

    private String value;

    InventoryUpdateType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
