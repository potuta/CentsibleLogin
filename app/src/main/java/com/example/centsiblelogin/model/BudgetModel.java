package com.example.centsiblelogin.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Budgets")
public class BudgetModel {
    @PrimaryKey (autoGenerate = false)
    private int id;

    private double Budget;
    private double TargetSavings;

    public BudgetModel() {

    }

    public BudgetModel(int id, double budget, double targetSavings) {
        this.id = id;
        this.Budget = budget;
        this.TargetSavings = targetSavings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBudget() {
        return Budget;
    }

    public void setBudget(double budget) {
        this.Budget = budget;
    }

    public double getTargetSavings() {
        return TargetSavings;
    }

    public void setTargetSavings(double targetSavings) {
        this.TargetSavings = targetSavings;
    }
}
