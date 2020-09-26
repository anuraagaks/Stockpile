package com.aks.stockpile.convertors;

import androidx.room.TypeConverter;

import com.aks.stockpile.models.enums.EntityType;
import com.aks.stockpile.models.enums.InventoryUpdateType;
import com.aks.stockpile.models.enums.QuantityType;

import java.util.Date;

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
    public static String convertInventoryUpdateType(InventoryUpdateType quantityType) {
        return quantityType.name();
    }

    @TypeConverter
    public static InventoryUpdateType convertInventoryUpdateType(String quantityType) {
        return InventoryUpdateType.valueOf(quantityType);
    }

    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

}
