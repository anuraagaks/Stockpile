package com.aks.stockpile.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aks.stockpile.convertors.TypeConverters;
import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.models.entities.ExpenditureEntity;
import com.aks.stockpile.models.entities.InventoryEntity;

@Database(entities = {CategoryEntity.class, ArticleEntity.class, InventoryEntity.class, ExpenditureEntity.class}, version = 1)
@androidx.room.TypeConverters(TypeConverters.class)
public abstract class StockpileDatabase extends RoomDatabase {

    private static StockpileDatabase db;

    public static StockpileDatabase getInstance(Context context) {
        if (null == db) {
            db = buildDatabaseInstance(context);
        }
        return db;
    }

    private static StockpileDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, StockpileDatabase.class, "stockpile-inventory")
                .allowMainThreadQueries()
                .build();
    }

    public abstract StockpileDao getDao();

}
