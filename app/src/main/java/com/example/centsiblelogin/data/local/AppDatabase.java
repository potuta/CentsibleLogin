package com.example.centsiblelogin.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.centsiblelogin.data.BudgetDao;
import com.example.centsiblelogin.data.CategoriesDao;
import com.example.centsiblelogin.data.RecordsDao;
import com.example.centsiblelogin.model.BudgetModel;
import com.example.centsiblelogin.model.CategoriesModel;
import com.example.centsiblelogin.model.RecordsModel;

@Database(entities = {BudgetModel.class, CategoriesModel.class, RecordsModel.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase{
    private static volatile AppDatabase INSTANCE;
    public abstract BudgetDao budgetDao();
    public abstract CategoriesDao categoriesDao();
    public abstract RecordsDao recordsDao();

    public static AppDatabase getAppDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "AppDB")
                            .fallbackToDestructiveMigration() // ⚠️ dev only: nukes & recreates DB
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
