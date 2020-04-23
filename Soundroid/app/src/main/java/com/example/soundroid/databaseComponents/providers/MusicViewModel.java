package com.example.soundroid.databaseComponents.providers;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.soundroid.databaseComponents.model.Music;

import java.util.List;

public class MusicViewModel extends AndroidViewModel {

    private MusicRepository mRepository;

    private LiveData<List<Music>> mAllMusics;

    public MusicViewModel (Application application) {
        super(application);
        mRepository = new MusicRepository(application);
        mAllMusics = mRepository.getAllMusic();
    }

    public LiveData<List<Music>> getAllMusic() { return mAllMusics; }

    public void insert(Music m) {
        mRepository.insert(m);
    }

    public void nuke(){
        mRepository.nuke();
    }

    public void insertAll(List<Music> mlist){
        for(Music m : mlist){
            insert(m);
        }
    }



}
