package com.example.centsiblelogin.data.repository;

import androidx.lifecycle.LiveData;

import com.example.centsiblelogin.data.CategoriesDao;
import com.example.centsiblelogin.data.local.AppDatabase;
import com.example.centsiblelogin.model.CategoriesModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoriesRepository {
    private final CategoriesDao categoriesDao;
    private final LiveData<List<CategoriesModel>> allCategories;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public CategoriesRepository(AppDatabase appDatabase){
        this.categoriesDao = appDatabase.categoriesDao();
        this.allCategories = categoriesDao.getAllCategories();
    }

    public LiveData<List<CategoriesModel>> getAllCategories() {
        return allCategories;
    }

    public void insert(CategoriesModel categories){
        executorService.execute(() -> categoriesDao.insert(categories));
    }

    public void update(CategoriesModel categories){
        executorService.execute(() -> categoriesDao.update(categories));
    }
}
