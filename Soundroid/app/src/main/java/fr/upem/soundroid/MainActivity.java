package fr.upem.soundroid;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.soundroid.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import fr.upem.soundroid.databaseComponents.model.music.Music;
import fr.upem.soundroid.databaseComponents.providers.Music.MusicViewModel;
import fr.upem.soundroid.service.PlayerService;
import fr.upem.soundroid.utils.ExternalStorageScanner;

public class MainActivity<handler> extends AppCompatActivity {
    public MusicViewModel mMusicViewModel;
    private AppBarConfiguration mAppBarConfiguration;
    private int REQUEST_EXTERNAL = 0;
    private boolean permission = false;
    public PlayerService mBoundService;
    public boolean mBound = false;
    boolean ispaused = true;
    private Handler batteriehandler;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mBoundService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
            mBound = false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        AccessMediaStorage(this,null);
        WriteExternalStorage(this,null);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.nav_music_player, R.id.nav_playlist,R.id.nav_music_list)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.menu_settings)
                {
                    navController.navigate(R.id.setting);
                    toolbar.setTitle("Settings");
                }
                return true;
            }
        });

        mMusicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);

        while(!permission){
            if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                permission = true;
            }
        }
        initDB();
        this.batteriehandler = new Handler();
        incrementRunnable.run();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, PlayerService.class), mConnection,
                Context.BIND_AUTO_CREATE);
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
            //nothing we're cool
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(this,"Need permissions to access your storage (music browsing)", Toast.LENGTH_SHORT).show();
                //terminate activity
            }
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void WriteExternalStorage(Context ctx, View view){
        if(ContextCompat.checkSelfPermission(ctx,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            //nothing we're cool
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(this,"Need permissions to access your storage (music browsing)", Toast.LENGTH_SHORT).show();
                //terminate activity
            }
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(REQUEST_EXTERNAL == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "no permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void displayMusicList(List<Music> mlist) {
        for(Music m : mlist){
            Log.i("MUSIC",m.toString());
            Bitmap b = m.getThumbnail(this);
            b.getWidth();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initDB(){
        //check if right OK
        //do thing
        mMusicViewModel.nuke();
        mMusicViewModel.insertAll(ExternalStorageScanner.resolve(this.getApplicationContext()));
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    private Runnable incrementRunnable = new Runnable() {
        @Override
        public void run() {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
            batteriehandler.postDelayed(MainActivity.this.getIncrementRunnable(), 6000);
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level * 100 / (float)scale;
            if(batteryPct <= 20){
                MainActivity.this.stopPlaying();
            }
        }

    };



    private Runnable getIncrementRunnable() {
        return incrementRunnable;
    }

    //====== Exposed funct for service ==============================//

    public void togglePause(){
        if (mBound) {
            if(!ispaused){
                mBoundService.pause();
                ispaused = true;
            }else{
                mBoundService.unPause();
                ispaused = false;
            }
        }
    }

    public void AddSongNow(Music m){
        if (mBound) {
            mBoundService.addSongNow(m);
        }
    }

    public void AddSongForLater(Music m){
        if (mBound) {
            mBoundService.addSongNext(m);
        }
    }

    public Music currentlyPlayed(){
        if (mBound) {
            return mBoundService.currentlyPlayed();
        }
        return null;
    }

    public int TimeofMusic(){
        if (mBound) {
            return mBoundService.currentTime();
        }
        return -1;
    }

    public int currentDuration(){
        if (mBound) {
            return mBoundService.duration();
        }
        return -1;
    }

    private void stopPlaying() {
        if (mBound) {
            mBoundService.stopPlaying();
        }
    }
}