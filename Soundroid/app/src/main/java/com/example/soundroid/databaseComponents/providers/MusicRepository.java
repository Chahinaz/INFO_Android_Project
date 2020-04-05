package com.example.soundroid.databaseComponents.providers;
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.soundroid.databaseComponents.model.Music;
import com.example.soundroid.databaseComponents.model.MusicDao;
import com.example.soundroid.databaseComponents.model.MusicDatabase;

import java.util.List;

public class MusicRepository {

    private MusicDao mMusicDao;

    public MusicRepository(Application application) {
        MusicDatabase db = MusicDatabase.getDatabase(application);
        mMusicDao = db.MusicDao();
    }

    void insert(final Music musi) {
        MusicDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mMusicDao.insertMusic(musi);
            }
        });
    }

    public LiveData<List<Music>> getAllMusic(){
        LiveData<List<Music>> ms = mMusicDao.getAll();
        return ms;
    }

}
