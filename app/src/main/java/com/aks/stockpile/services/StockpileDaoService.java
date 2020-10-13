package com.aks.stockpile.services;

import com.aks.stockpile.models.PreDataDto;
import com.aks.stockpile.models.dtos.AggregatedExpenditure;
import com.aks.stockpile.models.dtos.AggregatedInventory;
import com.aks.stockpile.models.dtos.GroceryCategoryCardDto;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.models.dtos.InventoryDto;
import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;

import java.util.List;
import java.util.Map;

public interface StockpileDaoService {

    void addCategoryArticle(PreDataDto preDataDto);

    List<GroceryCategoryCardDto> getAllCategories();

    CategoryEntity getCategory(Integer categoryId);

    List<ArticleEntity> getArticlesByCategory(Integer categoryId);

    void deleteInventory(Integer inventoryId);

    List<GroceryDetailsDto> getInventoryByCategory(Integer categoryId);

    List<GroceryDetailsDto> getAllInventory();

    List<GroceryDetailsDto> getOutOfStock();

    Map<String, CategoryEntity> getCategoriesForDropdown();

    Map<String, ArticleEntity> getArticlesForDropdown(Integer categoryId);

    AggregatedInventory getInventoryById(Integer inventoryId);

    void saveInventory(InventoryDto dto);

    void updateInventory(InventoryDto dto);

    Integer getInventoryIdByCategoryAndArticle(Integer categoryId, Integer name);

    List<AggregatedExpenditure> getExpenditureByInventoryId(Integer inventoryId);

    List<AggregatedExpenditure> getExpenditureByCategoryId(Integer inventoryId);

    List<AggregatedExpenditure> getAllExpenditure();

    List<ArticleEntity> searchArticle(String name);

    List<GroceryDetailsDto> searchInventory(String name);

    List<GroceryDetailsDto> getAllInventoryNameAZ();

    List<GroceryDetailsDto> getAllInventoryNameZA();

    List<GroceryDetailsDto> getAllInventoryQuantityHL();

    List<GroceryDetailsDto> getAllInventoryQuantityLH();

    Integer saveArticle(ArticleEntity buildArticle);

    List<AggregatedInventory> findInventoryIn(List<Integer> shoppingIds);
}
