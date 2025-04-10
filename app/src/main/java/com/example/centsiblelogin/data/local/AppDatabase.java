package com.example.centsiblelogin.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.centsiblelogin.data.BudgetDao;
import com.example.centsiblelogin.model.BudgetModel;

@Database(entities = {BudgetModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static volatile AppDatabase INSTANCE;
    public abstract BudgetDao budgetDao();

    public static AppDatabase getAppDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "AppDB").build();
                }
            }
        }
        return INSTANCE;
    }
}
