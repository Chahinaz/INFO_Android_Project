package fr.upem.soundroid.databaseComponents.providers.tag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import fr.upem.soundroid.databaseComponents.model.Tag.Tag;

public class TagViewModel extends AndroidViewModel {
    public TagRepository tRepository;

    public TagViewModel(@NonNull Application application) {
        super(application);
        tRepository = new TagRepository(application);
    }

    public void insert(Tag t) {
        tRepository.insert(t);
    }
}