package com.aks.stockpile.models.dtos;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.models.entities.ExpenditureEntity;
import com.aks.stockpile.models.entities.InventoryEntity;

import lombok.Data;

@Data
public class AggregatedExpenditure {

    @Embedded
    private ExpenditureEntity expenditure;

    @Relation(parentColumn = "category_id", entityColumn = "id")
    private CategoryEntity category;

    @Relation(parentColumn = "inventory_id", entityColumn = "id")
    private InventoryEntity inventory;

}
