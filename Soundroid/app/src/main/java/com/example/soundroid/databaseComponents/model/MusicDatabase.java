package com.example.soundroid.databaseComponents.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Music.class}, version = 1)
public abstract class MusicDatabase extends RoomDatabase {

  public abstract MusicDao MusicDao();

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