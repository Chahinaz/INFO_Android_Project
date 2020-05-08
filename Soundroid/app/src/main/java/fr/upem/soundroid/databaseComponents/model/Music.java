package fr.upem.soundroid.databaseComponents.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.IOException;
import java.io.InputStream;

@Entity(tableName = "music_table")
public class Music {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String Title;

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

    private transient Bitmap cachedDefaultBitmap;

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

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setTitle(String title) {
        Title = title;
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
                ", Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                ", Img='" + Img + '\'' +
                ", MusicPath='" + MusicPath + '\'' +
                ", Duration='" + Duration + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Bitmap getThumbnail(Context context)  {

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(MusicPath);
        byte [] data = mmr.getEmbeddedPicture();

        if(data != null) {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        } else {
            return getDefaultThumbnail(context);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Bitmap getDefaultThumbnail(Context context) {
        if (cachedDefaultBitmap == null) {
            try (InputStream inputStream = context.getAssets().open("pikawhat.png")) {
                cachedDefaultBitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                Log.e(String.valueOf(Log.ERROR), "IO exception while opening inputStream");
            }
        }
        return cachedDefaultBitmap;
    }
}
