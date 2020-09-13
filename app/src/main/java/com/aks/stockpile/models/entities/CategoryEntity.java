package com.aks.stockpile.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.aks.stockpile.models.enums.EntityType;
import com.aks.stockpile.models.enums.QuantityType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(tableName = "category")
public class CategoryEntity extends AbstractEntity {

    @ColumnInfo(name = "entity_type")
    private EntityType entityType;

    @ColumnInfo(name = "quantity_type")
    private QuantityType quantityType;

    @ColumnInfo(name = "image_resource")
    private Integer imageResource;

}
