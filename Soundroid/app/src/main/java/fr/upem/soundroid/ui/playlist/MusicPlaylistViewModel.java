package fr.upem.soundroid.ui.playlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MusicPlaylistViewModel extends ViewModel {
    private MutableLiveData<String> mName;

    public MusicPlaylistViewModel() {
        this.mName = new MutableLiveData<>();
        mName.setValue("This is music playlist fragment");
    }

    public LiveData<String> getName() {
        return mName;
    }
}
