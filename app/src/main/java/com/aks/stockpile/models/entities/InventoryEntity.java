package com.aks.stockpile.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

import lombok.Data;

@Data
@Entity(tableName = "inventory", indices = @Index(value = {"article_id", "category_id"}, unique = true),
        foreignKeys = {@ForeignKey(entity = ArticleEntity.class, parentColumns = "id", childColumns = "article_id"),
                @ForeignKey(entity = CategoryEntity.class, parentColumns = "id", childColumns = "category_id")})
public class InventoryEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "article_id")
    private Integer articleId;

    @ColumnInfo(name = "category_id")
    private Integer categoryId;

    private String name;

    @ColumnInfo(name = "history")
    private List<InventoryHistory> history;

    private Double quantity;

    private String description;

}
