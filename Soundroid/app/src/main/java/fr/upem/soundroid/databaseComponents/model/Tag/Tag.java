package fr.upem.soundroid.databaseComponents.model.Tag;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tag_table")
public class Tag {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "music_hash")
    public String mHash;

    @ColumnInfo(name = "tag_value")
    public String value;

    @ColumnInfo(name = "isMark")
    public Boolean isMark;
}
