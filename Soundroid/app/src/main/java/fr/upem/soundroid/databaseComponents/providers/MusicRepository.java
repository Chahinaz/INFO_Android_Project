package fr.upem.soundroid.databaseComponents.providers;
import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.upem.soundroid.databaseComponents.model.Music;
import fr.upem.soundroid.databaseComponents.model.MusicDao;
import fr.upem.soundroid.databaseComponents.model.MusicDatabase;

public class MusicRepository {

    private MusicDao mMusicDao;

    public MusicRepository(Application application) {
        MusicDatabase db = MusicDatabase.getDatabase(application);
        mMusicDao = db.MusicDao();
    }

    void insert(final Music m) {
        MusicDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mMusicDao.insertMusic(m);
            }
        });
    }

    void nuke(){
        MusicDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mMusicDao.nukeTable();
            }
        });
    }

    public LiveData<List<Music>> getAllMusic(){
        LiveData<List<Music>> ms = mMusicDao.getAll();
        return ms;
    }

    public LiveData<Music> getMusicByName(String name) {
        return mMusicDao.getMusicByName(name);
    }

    public LiveData<Music> getMusicById(int id) {
        return mMusicDao.getMusicByID(id);
    }

    public LiveData<List<Music>> getMusicLikeName(String name){
        LiveData<List<Music>> ms = mMusicDao.getMusicLikeName(name);
        return ms;
    }

    public LiveData<List<Music>> getallMusicHashForPlayList(String name){
        return mMusicDao.getAllMusicHashForPlaylist(name);
    }
}
