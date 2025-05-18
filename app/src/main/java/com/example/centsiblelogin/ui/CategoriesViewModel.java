package com.example.centsiblelogin.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.centsiblelogin.data.local.AppDatabase;
import com.example.centsiblelogin.data.repository.CategoriesRepository;
import com.example.centsiblelogin.model.CategoriesModel;

import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {
    private final CategoriesRepository categoriesRepository;
    private final LiveData<List<CategoriesModel>> allCategories;

    public CategoriesViewModel(Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        categoriesRepository = new CategoriesRepository(appDatabase);
        allCategories = categoriesRepository.getAllCategories();
    }

    public LiveData<List<CategoriesModel>> getAllCategories() {
        return allCategories;
    }

    public void insert(CategoriesModel categories) {
        categoriesRepository.insert(categories);
    }

    public void update(CategoriesModel categories) {
        categoriesRepository.update(categories);
    }
}
