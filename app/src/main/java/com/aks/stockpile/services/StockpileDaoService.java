package com.aks.stockpile.services;

import com.aks.stockpile.models.PreDataDto;
import com.aks.stockpile.models.dtos.AggregatedInventory;
import com.aks.stockpile.models.dtos.GroceryCategoryCardDto;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;

import java.util.List;
import java.util.Map;

public interface StockpileDaoService {

    void addCategoryArticle(PreDataDto preDataDto);

    List<GroceryCategoryCardDto> getAllCategories();

    CategoryEntity getCategory(Integer categoryId);

    List<ArticleEntity> getArticlesByCategory(Integer categoryId);

    List<GroceryDetailsDto> getInventoryByCategory(Integer categoryId);

    List<GroceryDetailsDto> getAllInventory();

    List<GroceryDetailsDto> getOutOfStock();

    Map<String, CategoryEntity> getCategoriesForDropdown();

    Map<String, ArticleEntity> getArticlesForDropdown(Integer categoryId);

    AggregatedInventory getInventoryById(Integer inventoryId);
}
