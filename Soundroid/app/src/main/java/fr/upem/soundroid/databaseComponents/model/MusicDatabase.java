package fr.upem.soundroid.databaseComponents.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.upem.soundroid.databaseComponents.model.Tag.Tag;
import fr.upem.soundroid.databaseComponents.model.Tag.TagDao;
import fr.upem.soundroid.databaseComponents.model.music.Music;
import fr.upem.soundroid.databaseComponents.model.music.MusicDao;
import fr.upem.soundroid.databaseComponents.model.playlist.PlayList;
import fr.upem.soundroid.databaseComponents.model.playlist.PlayListDao;

@Database(entities = {Music.class, PlayList.class, Tag.class}, version = 1)
public abstract class MusicDatabase extends RoomDatabase {

  public abstract MusicDao MusicDao();
  public abstract PlayListDao PlayListDao();
  public abstract TagDao TagDao();

  private static volatile MusicDatabase INSTANCE;
  private static final int NUMBER_OF_THREADS = 4;
  public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  public static MusicDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (MusicDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                  MusicDatabase.class, "music_database")
                  .build();
        }
      }
    }
    return INSTANCE;
  }
}