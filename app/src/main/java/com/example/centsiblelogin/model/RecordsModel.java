package com.example.centsiblelogin.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.DateTimeException;
import java.time.LocalDateTime;

@Entity(tableName = "Records")
public class RecordsModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String Category;
    private int Value;

    public RecordsModel(){

    }

    public RecordsModel(String category, int value){
        this.Category = category;
        this.Value = value;
    }

    public int getId() {
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

    public int getValue() {
        return Value;
    }

    public void setValue(int value){
        this.Value = value;
    }
}
