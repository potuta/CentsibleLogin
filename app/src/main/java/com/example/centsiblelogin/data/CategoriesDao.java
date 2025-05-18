package com.example.centsiblelogin.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.centsiblelogin.model.CategoriesModel;

import java.util.List;

@Dao
public interface CategoriesDao {
    @Insert
    void insert(CategoriesModel categories);

    @Update
    void update(CategoriesModel categories);

    @Query("SELECT * FROM Categories")
    LiveData<List<CategoriesModel>> getAllCategories();
}
