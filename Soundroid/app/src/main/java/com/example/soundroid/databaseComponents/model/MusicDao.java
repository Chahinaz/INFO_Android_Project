package com.example.soundroid.databaseComponents.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMusic(Music m);

    @Query("SELECT * FROM music_table")
    LiveData<List<Music>> getAll();

}
