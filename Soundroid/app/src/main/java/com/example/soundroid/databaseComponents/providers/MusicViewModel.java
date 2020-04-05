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

    public void insert(String title,String author, String name) {
        Music m = new Music();
        m.setTittle(title);
        m.setName(name);
        m.setAuthor(author);
        mRepository.insert(m);
    }



}
