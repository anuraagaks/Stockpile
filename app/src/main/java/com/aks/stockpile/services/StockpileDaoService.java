package com.aks.stockpile.services;

import com.aks.stockpile.models.PreDataDto;
import com.aks.stockpile.models.dtos.GroceryCategoryCardDto;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.models.entities.CategoryEntity;

import java.util.List;

public interface StockpileDaoService {

    void addCategoryArticle(PreDataDto preDataDto);

    List<GroceryCategoryCardDto> getAllCategories();

    CategoryEntity getCategory(Integer categoryId);

    List<GroceryDetailsDto> getInventoryByCategory(Integer categoryId);

    List<GroceryDetailsDto> getAllInventory();

    List<GroceryDetailsDto> getOutOfStock();
}
