package com.example.soundroid.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;

public class MusicModel {

    private long id;

    private String title;
    private String artist;
    private String album;
    private String genre;
    private String artPath;

    private transient Bitmap cachedBitmap;

    public MusicModel(long id, String title, String artist, String album, String genre, String path) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.artPath = path;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Bitmap getBitmap(Context context) {
        if(cachedBitmap == null) {
            try (InputStream inputStream = context.getAssets().open(artPath)) {
                cachedBitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                Log.e(String.valueOf(Log.ERROR), "IO exception while opening inputStream");
            }
        }
        return cachedBitmap;
    }
}
