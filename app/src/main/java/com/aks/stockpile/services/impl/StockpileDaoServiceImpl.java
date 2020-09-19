package com.aks.stockpile.services.impl;

import android.content.Context;

import com.aks.stockpile.dao.StockpileDao;
import com.aks.stockpile.dao.StockpileDatabase;
import com.aks.stockpile.models.PreDataDto;
import com.aks.stockpile.models.dtos.AggregatedInventory;
import com.aks.stockpile.models.dtos.GroceryCategoryCardDto;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.models.dtos.InventoryDto;
import com.aks.stockpile.models.entities.AbstractEntity;
import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.models.entities.InventoryEntity;
import com.aks.stockpile.models.entities.InventoryHistory;
import com.aks.stockpile.models.enums.InventoryUpdateType;
import com.aks.stockpile.services.StockpileDaoService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    public List<ArticleEntity> getArticlesByCategory(Integer categoryId) {
        return dao.getArticleByCategoryId(categoryId);
    }

    @Override
    public void deleteInventory(Integer inventoryId) {
        dao.deleteInventory(inventoryId);
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

    @Override
    public Map<String, CategoryEntity> getCategoriesForDropdown() {
        return buildAbstractModel(dao.findAllCategories());
    }

    @Override
    public Map<String, ArticleEntity> getArticlesForDropdown(Integer categoryId) {
        return buildAbstractModel(dao.getArticleByCategoryId(categoryId));
    }

    @Override
    public AggregatedInventory getInventoryById(Integer inventoryId) {
        return dao.getInventoryById(inventoryId);
    }

    @Override
    public void saveInventory(InventoryDto dto) {
        InventoryEntity entity = null;
        if (dto.getId() != null) {
            AggregatedInventory inventory = dao.getInventoryById(dto.getId());
            if (inventory != null && inventory.getInventory() != null)
                entity = inventory.getInventory();
        }
        if (entity == null) {
            entity = new InventoryEntity();
            entity.setCategoryId(dto.getCategoryId());
            if (dto.getArticleId() != 0)
                entity.setArticleId(dto.getArticleId());
            entity.setName(dto.getName());
        }
        if (dto.getDescription() != null && !dto.getDescription().trim().isEmpty()) {
            entity.setDescription(dto.getDescription());
        }
        addQuantity(entity, dto);
        dao.saveInventory(entity);
    }

    @Override
    public void updateInventory(InventoryDto dto) {
        InventoryEntity entity = dao.getInventoryById(dto.getId()).getInventory();
        reduceQuantity(entity, dto);
        dao.saveInventory(entity);
    }

    private void reduceQuantity(InventoryEntity entity, InventoryDto dto) {
        Double factor = dto.getQuantityType().getWeightMap().get(dto.getQuantityTypeName());
        InventoryHistory history = new InventoryHistory();
        history.setQuantityType(dto.getQuantityTypeName());
        history.setQuantity(dto.getQuantity());
        history.setUpdateType(InventoryUpdateType.CONSUMED);
        history.setUpdatedAt(new Date());
        entity.getHistory().add(history);
        entity.setQuantity(entity.getQuantity() - factor * dto.getQuantity());
    }

    private void addQuantity(InventoryEntity entity, InventoryDto dto) {
        Double factor = dto.getQuantityType().getWeightMap().get(dto.getQuantityTypeName());
        InventoryHistory history = new InventoryHistory();
        history.setQuantityType(dto.getQuantityTypeName());
        history.setQuantity(dto.getQuantity());
        history.setGrocerySource(dto.getSource());
        history.setPrice(dto.getPrice());
        history.setUpdateType(InventoryUpdateType.ADDED);
        history.setUpdatedAt(new Date());
        if (entity.getHistory() == null) {
            entity.setHistory(Collections.singletonList(history));
        } else {
            entity.getHistory().add(history);
        }
        if (entity.getQuantity() != null)
            entity.setQuantity(entity.getQuantity() + factor * dto.getQuantity());
        else
            entity.setQuantity(factor * dto.getQuantity());
    }

    @Override
    public Integer getInventoryIdByCategoryAndArticle(Integer categoryId, String name) {
        InventoryEntity entity = dao.getInventoryByCategoryAndName(categoryId, name);
        if (entity != null) {
            return entity.getId();
        }
        return null;
    }

    private <T extends AbstractEntity> Map<String, T> buildAbstractModel(List<T> entities) {
        Map<String, T> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (T entity : entities) {
            map.put(entity.getName(), entity);
        }
        return map;
    }

    private List<GroceryDetailsDto> mapToDto(List<AggregatedInventory> entities) {
        List<GroceryDetailsDto> dtos = new ArrayList<>();
        for (AggregatedInventory entity : entities) {
            dtos.add(GroceryDetailsDto.of(entity));
        }
        return dtos;
    }
}
