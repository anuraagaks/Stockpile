package com.aks.stockpile.models.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class InventoryWithInstance {

    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    public InventoryEntity instance;

    @Embedded
    private ArticleEntity inventory;

}
