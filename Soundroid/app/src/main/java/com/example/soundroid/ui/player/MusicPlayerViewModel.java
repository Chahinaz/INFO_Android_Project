package com.example.soundroid.ui.player;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MusicPlayerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MusicPlayerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is music player fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
