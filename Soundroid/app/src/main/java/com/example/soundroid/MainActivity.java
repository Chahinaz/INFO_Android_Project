package com.example.soundroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.soundroid.databaseComponents.model.Music;
import com.example.soundroid.databaseComponents.providers.MusicViewModel;
import com.example.soundroid.ui.MusicModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MainActivity extends AppCompatActivity {
    private MusicViewModel mMusicViewModel;
    private AppBarConfiguration mAppBarConfiguration;
    private int REQUEST_EXTERNAL = 0;

    List<MusicModel> musicList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AccessMediaStorage(getApplicationContext(),null);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_music_player)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        mMusicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        mMusicViewModel.getAllMusic().observe(this, new Observer<List<Music>>() {
            @Override
            public void onChanged(@Nullable final List<Music> Music) {
                String t = "";
                for(Music m : Music){
                    t+= m.name+" ";
                }
                Toast toast = Toast.makeText(getApplicationContext(),  t, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        //testDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void AccessMediaStorage(Context ctx, View view){
        if(ContextCompat.checkSelfPermission(ctx,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            //do thing on storage
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(this,"Need permissions to access your storage (music browsing)", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(REQUEST_EXTERNAL == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //do thing on storage
                Toast.makeText(this, "Permission granted",Toast.LENGTH_SHORT).show();
                displayMusicList();
            }
        } else {
            Toast.makeText(this, "no permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayMusicList() {
        getMusic();
        for(MusicModel music : musicList) {
            Log.d(String.valueOf(Level.INFO), "\n\ttitle : " + music.getTitle() +
                    "\n\tartist : " + music.getArtist() +
                    "\n\talbum : " + music.getAlbum() +
                    "\n\tgenre : " + music.getGenre());
        }
    }

    public void getMusic() {
        int counter = 0;
        ContentResolver contentResolver = getContentResolver();
        @SuppressLint("Recycle") Cursor musicCursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        //int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);

        Log.d(String.valueOf(Level.INFO), "\t\tCURSOR = " + musicCursor.toString());

        int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        // gives - 1 so problem :/
        //int genreColumn = musicCursor.getColumnIndex(MediaStore.Audio.Genres.NAME);
        //
        // int artColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);

        Log.d(String.valueOf(Level.INFO), "\t\tCURSOR = " +
                "\ntitle column = " + titleColumn +
                "\nartist column = " + artistColumn);
        musicCursor.moveToFirst();
        do {
            String title = musicCursor.getString(titleColumn);
            String artist = musicCursor.getString(artistColumn);
            String album = musicCursor.getString(albumColumn);
            String genre = "default";
            String path = "default";
            musicList.add(new MusicModel(counter, title, artist, album, genre, path));
            counter++;
        } while (musicCursor.moveToNext());
    }

    private void testDB(){
        mMusicViewModel.insert("test","test","test");
    }

}