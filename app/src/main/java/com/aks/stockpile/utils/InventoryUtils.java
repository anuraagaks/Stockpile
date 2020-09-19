package com.aks.stockpile.utils;

import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.models.enums.QuantityType;

public final class InventoryUtils {

    private InventoryUtils() {
    }

    public static QuantityType getQuantityType(CategoryEntity category, ArticleEntity article) {
        if (article != null && article.getQuantityType() != null) {
            return article.getQuantityType();
        }
        return category.getQuantityType();
    }

}
