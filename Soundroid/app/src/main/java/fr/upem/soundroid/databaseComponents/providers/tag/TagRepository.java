package fr.upem.soundroid.databaseComponents.providers.tag;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.upem.soundroid.databaseComponents.model.MusicDatabase;
import fr.upem.soundroid.databaseComponents.model.Tag.Tag;
import fr.upem.soundroid.databaseComponents.model.Tag.TagDao;
import fr.upem.soundroid.databaseComponents.model.music.Music;

public class TagRepository {

    private TagDao tdao;

    public TagRepository(Application application) {
        MusicDatabase db = MusicDatabase.getDatabase(application);
        tdao = db.TagDao();
    }

    void insert(final Tag t) {
        MusicDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                tdao.insertMusic(t);
            }
        });
    }

     LiveData<List<Tag>> getAll(){
        return tdao.getAll();
    }

    LiveData<List<Music>> getTagMusicMark(String value){
        LiveData<List<Music>> tagMusicMark = tdao.getTagMusicMark(value);
        return tagMusicMark;
    }

    LiveData<List<Music>> getTagMusicMark(String name, String value){
        LiveData<List<Music>> tagMusicMark = tdao.getTagMusicMark(name, value);
        return tagMusicMark;
    }

    LiveData<List<Music>> getTagMusicValue(String value){
        LiveData<List<Music>> tagMusicValue = tdao.getTagMusicValue(value);
        return tagMusicValue;
    }

    LiveData<List<Music>> getTagMusicValue(String name, String value){
        LiveData<List<Music>> tagMusicValue = tdao.getTagMusicValue(name, value);
        return tagMusicValue;
    }
}
