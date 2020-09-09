package com.aks.stockpile.models;

import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;

import java.util.List;

import lombok.Data;

@Data
public class PreDataDto {

    List<CategoryEntity> categories;

    List<ArticleEntity> articles;

}
