package com.example.centsiblelogin.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.centsiblelogin.data.local.AppDatabase;
import com.example.centsiblelogin.data.repository.BudgetRepository;
import com.example.centsiblelogin.model.BudgetModel;

import java.util.List;

public class BudgetViewModel extends AndroidViewModel {
    private final BudgetRepository budgetRepository;
    private final LiveData<List<BudgetModel>> allBudgets;

    public BudgetViewModel(Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        budgetRepository = new BudgetRepository(appDatabase);
        allBudgets = budgetRepository.getAllBudgets();
    }

    public LiveData<List<BudgetModel>> getAllBudgets() {
        return allBudgets;
    }

    public void insert(BudgetModel budget) {
        budgetRepository.insert(budget);
    }

    public void update(BudgetModel budget) {
        budgetRepository.update(budget);
    }

    public LiveData<BudgetModel> getBudget(int id) {
        return budgetRepository.getBudget(id);
    }

}
