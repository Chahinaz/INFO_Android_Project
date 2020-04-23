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

    @ColumnInfo(name = "ImpPath")
    public String Img;

    @ColumnInfo(name = "MusicPath")
    public String MusicPath;

    @ColumnInfo(name = "Duration")
    public String Duration;

    @ColumnInfo(name = "Hash")
    public String hash;

    public String getHash() {
        return hash;
    }

    public int getUid() {
        return uid;
    }

    public String getImg() {
        return Img;
    }

    public String getMusicPath() {
        return MusicPath;
    }

    public String getDuration() {
        return Duration;
    }

    public String getTittle() {
        return Tittle;
    }

    public String getAuthor() {
        return Author;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public void setMusicPath(String musicPath) {
        MusicPath = musicPath;
    }

    public void setImg(String img) {
        Img = img;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Music{" +
                "uid=" + uid +
                ", Tittle='" + Tittle + '\'' +
                ", Author='" + Author + '\'' +
                ", Img='" + Img + '\'' +
                ", MusicPath='" + MusicPath + '\'' +
                ", Duration='" + Duration + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
