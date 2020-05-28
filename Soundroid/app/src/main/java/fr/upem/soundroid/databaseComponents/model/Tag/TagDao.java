package fr.upem.soundroid.databaseComponents.model.Tag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMusic(Tag t);

    @Query("SELECT * FROM tag_table")
    LiveData<List<Tag>> getAll();
}
