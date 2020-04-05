package com.example.soundroid.databaseComponents.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "music_table")
public class Music {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "tittle")
    public String Tittle;

    @ColumnInfo(name = "author")
    public String Author;

    @ColumnInfo(name = "name")
    public String name;

    public int getUid() {
        return uid;
    }

    public String getTittle() {
        return Tittle;
    }

    public String getAuthor() {
        return Author;
    }

    public String getName() {
        return name;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setName(String name) {
        this.name = name;
    }
}
