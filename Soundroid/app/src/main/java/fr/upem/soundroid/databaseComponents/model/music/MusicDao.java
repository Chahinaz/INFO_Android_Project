package fr.upem.soundroid.databaseComponents.model.music;

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

    @Query("DELETE FROM music_table")
    public void nukeTable();

    @Query("SELECT * FROM music_table WHERE title = :name LIMIT 1")
    LiveData<Music> getMusicByName(String name);

    @Query("SELECT * FROM music_table WHERE title LIKE '%' || :name  || '%' ")
    LiveData<List<Music>> getMusicLikeName(String name);

    @Query("SELECT * FROM music_table WHERE uid = :id LIMIT 1")
    LiveData<Music> getMusicByID(int id);

    @Query("SELECT * FROM music_table WHERE Hash IN (SELECT music_hash FROM playlist_table WHERE title = :name)")
    LiveData<List<Music>> getAllMusicHashForPlaylist(String name);
}
