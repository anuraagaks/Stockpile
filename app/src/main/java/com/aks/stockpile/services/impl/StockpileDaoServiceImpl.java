package com.aks.stockpile.services.impl;

import android.content.Context;

import com.aks.stockpile.dao.StockpileDao;
import com.aks.stockpile.dao.StockpileDatabase;
import com.aks.stockpile.models.PreDataDto;
import com.aks.stockpile.models.dtos.AggregatedInventory;
import com.aks.stockpile.models.dtos.GroceryCategoryCardDto;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.services.StockpileDaoService;

import java.util.ArrayList;
import java.util.List;

public class StockpileDaoServiceImpl implements StockpileDaoService {

    private StockpileDao dao;

    public StockpileDaoServiceImpl(Context mContext) {
        dao = StockpileDatabase.getInstance(mContext).getDao();
    }

    @Override
    public void addCategoryArticle(PreDataDto preDataDto) {
        dao.saveCategories(preDataDto.getCategories());
        dao.saveArticles(preDataDto.getArticles());
    }

    @Override
    public List<GroceryCategoryCardDto> getAllCategories() {
        List<CategoryEntity> entities = dao.findAllCategories();
        List<GroceryCategoryCardDto> dtos = new ArrayList<>();
        for (CategoryEntity entity : entities) {
            dtos.add(GroceryCategoryCardDto.of(entity));
        }
        return dtos;
    }

    @Override
    public CategoryEntity getCategory(Integer categoryId) {
        return dao.findCategoryById(categoryId);
    }

    @Override
    public List<GroceryDetailsDto> getInventoryByCategory(Integer categoryId) {
        List<AggregatedInventory> entities = dao.getInventoryByCategoryId(categoryId);
        return mapToDto(entities);
    }

    @Override
    public List<GroceryDetailsDto> getAllInventory() {
        List<AggregatedInventory> entities = dao.findAllInventory();
        return mapToDto(entities);
    }

    @Override
    public List<GroceryDetailsDto> getOutOfStock() {
        List<AggregatedInventory> entities = dao.getOutOfStockInventory();
        return mapToDto(entities);
    }

    private List<GroceryDetailsDto> mapToDto(List<AggregatedInventory> entities) {
        List<GroceryDetailsDto> dtos = new ArrayList<>();
        for (AggregatedInventory entity : entities) {
            dtos.add(GroceryDetailsDto.of(entity));
        }
        return dtos;
    }
}
