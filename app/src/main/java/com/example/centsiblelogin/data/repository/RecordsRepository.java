package com.example.centsiblelogin.data.repository;

import androidx.lifecycle.LiveData;

import com.example.centsiblelogin.data.RecordsDao;
import com.example.centsiblelogin.data.local.AppDatabase;
import com.example.centsiblelogin.model.RecordsModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecordsRepository {
    private final RecordsDao recordsDao;
    private final LiveData<List<RecordsModel>> allRecords;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public RecordsRepository(AppDatabase appDatabase) {
        this.recordsDao = appDatabase.recordsDao();
        this.allRecords = recordsDao.getAllRecords();
    }

    public LiveData<List<RecordsModel>> getAllRecords() {
        return allRecords;
    }

    public void insert(RecordsModel records){
        executorService.execute(() -> recordsDao.insert(records));
    }

    public void update(RecordsModel records) {
        executorService.execute(() -> recordsDao.update(records));
    }
}
