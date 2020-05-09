package fr.upem.soundroid.databaseComponents.providers;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.upem.soundroid.databaseComponents.model.PlayList;

public class PlayListViewModel extends AndroidViewModel {
    private MusicRepository mRepository;
    public PlayListRepository plRepository;

    public PlayListViewModel (Application application) {
        super(application);
        mRepository = new MusicRepository(application);
        plRepository = new PlayListRepository(application);
    }


    public LiveData<List<String>> getAllplaylist(){
        return plRepository.getallPlayList();
    }

    public void insert(PlayList pl) {
        plRepository.insert(pl);
    }

}
