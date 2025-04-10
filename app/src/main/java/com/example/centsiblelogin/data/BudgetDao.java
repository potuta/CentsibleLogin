package com.example.centsiblelogin.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.centsiblelogin.model.BudgetModel;

import java.util.List;

@Dao
public interface BudgetDao {
    @Insert
    void insert(BudgetModel budget);

    @Update
    void update(BudgetModel budget);

    @Query("SELECT * FROM Budgets")
    LiveData<List<BudgetModel>> getAllBudgets();

    @Query("SELECT * FROM Budgets WHERE Id = :id")
    LiveData<BudgetModel> getBudget(int id);
}
