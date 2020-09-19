package com.aks.stockpile.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.aks.stockpile.models.enums.QuantityType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Entity(tableName = "article", indices = {@Index("name"), @Index("category_id")},
        foreignKeys = @ForeignKey(entity = CategoryEntity.class, parentColumns = "id", childColumns = "category_id"))
public class ArticleEntity extends AbstractEntity {

    @ColumnInfo(name = "category_id")
    private Integer categoryId;

    @ColumnInfo(name = "threshold_percentage")
    private Double thresholdPercentage;

    @ColumnInfo(name = "quantity_type")
    private QuantityType quantityType;

}
