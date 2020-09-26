package com.aks.stockpile.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.aks.stockpile.models.dtos.AggregatedExpenditure;
import com.aks.stockpile.models.dtos.AggregatedInventory;
import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.models.entities.ExpenditureEntity;
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

    @Insert
    void saveExpenditure(ExpenditureEntity entity);

    @Query("DELETE FROM inventory WHERE id = :id")
    void deleteInventory(Integer id);

    @Query("SELECT * FROM inventory WHERE quantity <= 0")
    @Transaction
    List<AggregatedInventory> getOutOfStockInventory();

    @Query("SELECT * FROM inventory WHERE category_id = :categoryId AND article_id = :articleId")
    InventoryEntity getInventoryByCategoryAndName(Integer categoryId, Integer articleId);

    @Query("SELECT * FROM expenditure WHERE inventory_id = :inventoryId")
    @Transaction
    List<AggregatedExpenditure> getExpenditureByInventoryId(Integer inventoryId);

    @Query("SELECT * FROM expenditure")
    @Transaction
    List<AggregatedExpenditure> findAllExpenditure();

    @Query("SELECT * FROM article WHERE LOWER(name) LIKE '%' || :name || '%' ORDER BY name")
    List<ArticleEntity> searchArticleByName(String name);

    @Query("SELECT * FROM inventory WHERE LOWER(name) LIKE '%' || :name || '%' ORDER BY name")
    @Transaction
    List<AggregatedInventory> searchInventoryByName(String name);

    @Query("SELECT * FROM inventory ORDER BY name")
    @Transaction
    List<AggregatedInventory> findAllInventoryOrderNameAZ();

    @Query("SELECT * FROM inventory ORDER BY name DESC")
    @Transaction
    List<AggregatedInventory> findAllInventoryOrderNameZA();

    @Query("SELECT * FROM inventory ORDER BY quantity")
    @Transaction
    List<AggregatedInventory> findAllInventoryOrderCategoryLH();

    @Query("SELECT * FROM inventory ORDER BY quantity DESC")
    @Transaction
    List<AggregatedInventory> findAllInventoryOrderCategoryHL();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long saveArticle(ArticleEntity article);
}
