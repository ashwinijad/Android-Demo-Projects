package com.example.contactapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactapp.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Viewmodel extends AndroidViewModel {

    DbHelper repository;
  public   LiveData<ArrayList<HashMap<String, String>>> allNotesLivedata;
    public Viewmodel(@NonNull Application application) {
        super(application);
        repository = new DbHelper(application);
        allNotesLivedata = repository.getAllData();
    }

    public void insert(String name, String Address) {
        repository.insert(name, Address);
    }

   public void update(int id, String name, String address) {
        repository.update(id, name, address);
    }

  public   void delete(int id) {
        repository.delete(id);
    }

    public LiveData<ArrayList<HashMap<String, String>>> getAllNotes() {
        return repository.getAllData();
    }

}


