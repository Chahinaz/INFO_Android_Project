package fr.upem.soundroid.databaseComponents.providers.tag;

import android.app.Application;

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
}
