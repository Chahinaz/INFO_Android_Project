package fr.upem.soundroid.databaseComponents.providers.playlist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.upem.soundroid.databaseComponents.model.MusicDatabase;
import fr.upem.soundroid.databaseComponents.model.playlist.PlayListDao;
import fr.upem.soundroid.databaseComponents.model.playlist.PlayList;

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
