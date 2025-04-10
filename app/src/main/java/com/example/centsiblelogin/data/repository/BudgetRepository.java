package com.example.centsiblelogin.data.repository;

import androidx.lifecycle.LiveData;

import com.example.centsiblelogin.data.BudgetDao;
import com.example.centsiblelogin.data.local.AppDatabase;
import com.example.centsiblelogin.model.BudgetModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BudgetRepository {
    private final BudgetDao budgetDao;
    private final LiveData<List<BudgetModel>> allBudgets;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public BudgetRepository(AppDatabase appDatabase) {
        this.budgetDao = appDatabase.budgetDao();
        this.allBudgets = budgetDao.getAllBudgets();
    }

    public LiveData<List<BudgetModel>> getAllBudgets() {
        return allBudgets;
    }

    public void insert(BudgetModel budget) {
        executorService.execute(() -> budgetDao.insert(budget));
    }

    public void update(BudgetModel budget) {
        executorService.execute(() -> budgetDao.update(budget));
    }

    public LiveData<BudgetModel> getBudget(int id) {
        return budgetDao.getBudget(id);
    }
}
