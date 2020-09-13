package com.aks.stockpile.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.aks.stockpile.models.dtos.AggregatedInventory;
import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.models.entities.InventoryEntity;

import java.util.List;

@Dao
public interface StockpileDao {

    @Query("SELECT * FROM category")
    List<CategoryEntity> findAllCategories();

    @Insert
    void saveCategories(List<CategoryEntity> entities);

    @Query("SELECT * FROM category WHERE id = :id")
    CategoryEntity findCategoryById(Integer id);

    @Insert
    void saveArticles(List<ArticleEntity> entities);

    @Transaction
    @Query("SELECT * FROM inventory WHERE id = :inventoryId")
    AggregatedInventory getInventoryById(Integer inventoryId);

    @Transaction
    @Query("SELECT * FROM inventory WHERE category_id = :categoryId")
    List<AggregatedInventory> getInventoryByCategoryId(Integer categoryId);

    @Transaction
    @Query("SELECT * FROM article WHERE category_id = :categoryId")
    List<ArticleEntity> getArticleByCategoryId(Integer categoryId);

    @Transaction
    @Query("SELECT * FROM inventory")
    List<AggregatedInventory> findAllInventory();

    @Transaction
    @Query("SELECT * FROM inventory WHERE quantity = 0.0")
    List<AggregatedInventory> findOOSInventory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long saveInventory(InventoryEntity entity);

    @Delete
    void deleteInventory(InventoryEntity entity);

    @Query("SELECT * FROM inventory WHERE quantity <= 0")
    List<AggregatedInventory> getOutOfStockInventory();
}
