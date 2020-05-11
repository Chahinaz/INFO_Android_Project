package fr.upem.soundroid.databaseComponents.providers;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.upem.soundroid.databaseComponents.model.Music;
import fr.upem.soundroid.databaseComponents.model.MusicDatabase;
import fr.upem.soundroid.databaseComponents.model.PlayListDao;
import fr.upem.soundroid.databaseComponents.model.PlayList;

public class PlayListRepository {
    private PlayListDao pldao;

    public PlayListRepository(Application application) {
        MusicDatabase db = MusicDatabase.getDatabase(application);
        pldao = db.PlayListDao();
    }

    void insert(final PlayList pl) {
        MusicDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                pldao.insertPlayList(pl);
            }
        });
    }

    public LiveData<List<String>> getallPlayList(){
        LiveData<List<String>> allPlayList = pldao.getAllPlayList();
        return allPlayList;
    }

}
