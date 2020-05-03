package com.example.soundroid.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerService extends Service {
    private MediaPlayer player = new MediaPlayer();
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
        //add funct to communicate beetween service and app here

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //init media player here
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    public void playRingTone(){
        player = MediaPlayer.create(getApplicationContext(),Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.start();

    }
}
