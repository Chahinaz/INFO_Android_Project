package fr.upem.soundroid.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import fr.upem.soundroid.databaseComponents.model.Music;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                    MediaStore.Audio.Media.DURATION,
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
                    m.setTitle(cursor.getString(2));
                    m.setDuration(cursor.getString(6));
                    m.setHash(md5(m.Author+m.Title+m.MusicPath));
                    m.setMusicPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));

                    String cover = getAlbumUri(ctx,cursor.getString(0));
                    if(cover.isEmpty() ){
                        m.setImg("unimplemented");
                    }else{
                        m.setImg(cover);
                    }
                    musicInStorage.add(m);
                }
            }
        }
        return musicInStorage;
    }

    public static String getAlbumUri(Context mContext, String album_id){
        if(mContext!=null) {
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart/");
            Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
            return imageUri.toString();
        }
        return null;
    }

    //Credits to https://stackoverflow.com/questions/3934331/how-to-hash-a-string-in-android
    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



}
