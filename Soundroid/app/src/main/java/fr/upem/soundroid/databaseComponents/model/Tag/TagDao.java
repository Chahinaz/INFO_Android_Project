package fr.upem.soundroid.databaseComponents.model.Tag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.upem.soundroid.databaseComponents.model.music.Music;

@Dao
public interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMusic(Tag t);

    @Query("SELECT * FROM tag_table")
    LiveData<List<Tag>> getAll();

    @Query("SELECT * FROM music_table WHERE title LIKE '%' || :name  || '%'  AND hash IN (SELECT DISTINCT(music_hash) FROM tag_table WHERE tag_value LIKE '%' || :value  || '%' AND isMark == 'true')")
    LiveData<List<Music>> getTagMusicMark(String name, String value);

    @Query("SELECT * FROM music_table WHERE hash IN (SELECT DISTINCT(music_hash) FROM tag_table WHERE tag_value LIKE '%' || :value  || '%' AND isMark == 'true')")
    LiveData<List<Music>> getTagMusicMark(String value);

    @Query("SELECT * FROM music_table WHERE title LIKE '%' || :name  || '%'  AND hash IN (SELECT DISTINCT(music_hash) FROM tag_table WHERE tag_value LIKE '%' || :value  || '%')")
    LiveData<List<Music>> getTagMusicValue(String name,String value);

    @Query("SELECT * FROM music_table WHERE hash IN (SELECT DISTINCT(music_hash) FROM tag_table WHERE tag_value LIKE '%' || :value  || '%')")
    LiveData<List<Music>> getTagMusicValue(String value);
}
