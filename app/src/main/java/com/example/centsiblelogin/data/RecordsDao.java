package com.example.centsiblelogin.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.centsiblelogin.model.RecordsModel;

import java.util.List;

@Dao
public interface RecordsDao {
    @Insert
    void insert(RecordsModel records);

    @Update
    void update(RecordsModel records);

    @Query("SELECT * FROM Records")
    LiveData<List<RecordsModel>> getAllRecords();

}
