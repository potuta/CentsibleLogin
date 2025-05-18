package com.example.centsiblelogin.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categories")
public class CategoriesModel {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String Category;

    public CategoriesModel(){ }

    public CategoriesModel(String category){
        this.Category = category;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }
}
