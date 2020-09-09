package com.aks.stockpile.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.aks.stockpile.models.enums.EntityType;
import com.aks.stockpile.models.enums.QuantityType;

import lombok.Data;

@Data
@Entity(tableName = "category")
public class CategoryEntity {

    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "entity_type")
    private EntityType entityType;

    @ColumnInfo(name = "quantity_type")
    private QuantityType quantityType;

    private String name;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @ColumnInfo(name = "image_resource")
    private Integer imageResource;

}
