package com.aks.stockpile.models.dtos;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;

import lombok.Data;

@Data
public class AggregatedArticle {

    @Embedded
    private ArticleEntity article;

    @Relation(parentColumn = "category_id", entityColumn = "id")
    private CategoryEntity category;

}
