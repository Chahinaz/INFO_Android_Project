package fr.upem.soundroid.databaseComponents.model.Tag;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMusic(Tag t);

}
