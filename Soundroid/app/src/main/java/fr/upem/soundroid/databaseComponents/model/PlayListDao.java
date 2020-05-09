package fr.upem.soundroid.databaseComponents.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlayList(PlayList l);

    @Query("SELECT DISTINCT(title) FROM playlist_table")
    LiveData<List<String>> getAllPlayList();

}
