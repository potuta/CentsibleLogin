package com.example.centsiblelogin.ui;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.centsiblelogin.data.local.AppDatabase;
import com.example.centsiblelogin.data.repository.RecordsRepository;
import com.example.centsiblelogin.model.RecordsModel;

import java.util.List;

public class RecordsViewModel extends AndroidViewModel {
    private final RecordsRepository recordsRepository;
    private final LiveData<List<RecordsModel>> allRecords;

    public RecordsViewModel(Application application){
        super(application);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        recordsRepository = new RecordsRepository(appDatabase);
        allRecords = recordsRepository.getAllRecords();
    }

    public LiveData<List<RecordsModel>> getAllRecords() {
        return allRecords;
    }

    public void insert(RecordsModel records){
        recordsRepository.insert(records);
    }

    public void update(RecordsModel records) {
        recordsRepository.update(records);
    }
}
