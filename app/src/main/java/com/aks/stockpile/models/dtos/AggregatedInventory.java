package com.aks.stockpile.models.dtos;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.models.entities.InventoryEntity;

import lombok.Data;

@Data
public class AggregatedInventory {

    @Embedded
    private InventoryEntity inventory;

    @Relation(parentColumn = "article_id", entityColumn = "id")
    private CategoryEntity category;

    @Relation(parentColumn = "article_id", entityColumn = "id")
    private ArticleEntity article;


}
