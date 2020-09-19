package com.aks.stockpile.convertors;

import androidx.room.TypeConverter;

import com.aks.stockpile.models.entities.InventoryHistory;
import com.aks.stockpile.models.enums.EntityType;
import com.aks.stockpile.models.enums.QuantityType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public final class TypeConverters {

    @TypeConverter
    public static String convertEntityType(EntityType entityType) {
        return entityType.name();
    }

    @TypeConverter
    public static EntityType convertEntityType(String entityType) {
        return EntityType.valueOf(entityType);
    }

    @TypeConverter
    public static String convertQuantityType(QuantityType quantityType) {
        return quantityType.name();
    }

    @TypeConverter
    public static QuantityType convertQuantityType(String quantityType) {
        return QuantityType.valueOf(quantityType);
    }

    @TypeConverter
    public static String convertQuantitySplitUp(List<InventoryHistory> histories) {
        return new Gson().toJson(histories);
    }

    @TypeConverter
    public static List<InventoryHistory> convertQuantitySplitUp(String quantityType) {
        Type listType = new TypeToken<List<InventoryHistory>>() {
        }.getType();
        return new Gson().fromJson(quantityType, listType);
    }

}
