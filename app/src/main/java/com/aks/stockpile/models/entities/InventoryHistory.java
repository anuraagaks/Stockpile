package com.aks.stockpile.models.entities;

import androidx.room.ColumnInfo;

import com.aks.stockpile.models.enums.InventoryUpdateType;

import java.util.Date;

import lombok.Data;

@Data
public class InventoryHistory {

    @ColumnInfo(name = "quantity_type")
    private String quantityType;

    private Double quantity;

    @ColumnInfo(name = "update_type")
    private InventoryUpdateType updateType;

    @ColumnInfo(name = "grocery_source")
    private String grocerySource;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    private Double price;
}
