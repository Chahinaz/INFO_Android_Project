package fr.upem.soundroid.databaseComponents.model.playlist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist_table")
public class PlayList {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String Title;

    @ColumnInfo(name = "music_hash")
    public String mHash;

}
