package fr.upem.soundroid.databaseComponents.providers.playlist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.upem.soundroid.databaseComponents.model.playlist.PlayList;
import fr.upem.soundroid.databaseComponents.providers.Music.MusicRepository;

public class PlayListViewModel extends AndroidViewModel {
    public PlayListRepository plRepository;

    public PlayListViewModel (Application application) {
        super(application);
        plRepository = new PlayListRepository(application);
    }


    public LiveData<List<String>> getAllplaylist(){
        return plRepository.getallPlayList();
    }

    public void insert(PlayList pl) {
        plRepository.insert(pl);
    }



}
