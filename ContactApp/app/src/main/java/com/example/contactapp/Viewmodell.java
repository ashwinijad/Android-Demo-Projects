/*
package com.example.contactapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private DbHelper repository ;
    private LiveData<List<Data>> allNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new DbHelper(application);
        allNotes = repository.getAllData();
    }



    public void insert(Note note) {
        repository.insert(note);
    }
    public void update(Note note) {
        repository.update(note);
    }
    public void delete(Note note) {
        repository.delete(note);
    }
    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
*/
