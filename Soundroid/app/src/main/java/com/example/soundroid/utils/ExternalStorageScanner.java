package com.example.soundroid.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.soundroid.databaseComponents.model.Music;

import java.util.ArrayList;
import java.util.List;

public class ExternalStorageScanner {

    public static List<Music> resolve(Context ctx){
        List<Music> musicInStorage = new ArrayList<>();
        android.net.Uri[] listSearchzone = {MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,MediaStore.Audio.Media.INTERNAL_CONTENT_URI};
        for( android.net.Uri search : listSearchzone){
            String[] projection = {
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.DURATION
            };
            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
            Cursor cursor = ctx.getContentResolver().query(
                    search,
                    projection,
                    selection,
                    null,
                    null);

            while (cursor.moveToNext()) {

                if(!cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)).contains("ogg")) { //manage here unwanted extension
                    Music m = new Music();
                    m.setAuthor(cursor.getString(1));
                    m.setTittle(cursor.getString(2));
                    m.setDuration(cursor.getString(6));
                    m.setImg("unimplemented");
                    m.setMusicPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                    musicInStorage.add(m);
                }
            }
        }
        return musicInStorage;
    }



}
