package com.aks.stockpile.models.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.aks.stockpile.models.enums.InventoryUpdateType;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(tableName = "expenditure", foreignKeys = {@ForeignKey(entity = InventoryEntity.class, parentColumns = "id", childColumns = "inventory_id"),
        @ForeignKey(entity = CategoryEntity.class, parentColumns = "id", childColumns = "category_id")}, indices = {@Index("inventory_id"), @Index("category_id")})
public class ExpenditureEntity extends AbstractEntity {

    @ColumnInfo(name = "inventory_id")
    private Integer inventoryId;

    @ColumnInfo(name = "category_id")
    private Integer categoryId;

    @ColumnInfo(name = "entry_date")
    private Date entryDate;

    private Integer price;

    private Double quantity;

    @ColumnInfo(name = "quantity_type")
    private String quantityType;

    private String source;

    @ColumnInfo(name = "update_type")
    private InventoryUpdateType updateType;

}
