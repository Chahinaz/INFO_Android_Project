package fr.upem.soundroid.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import fr.upem.soundroid.databaseComponents.model.music.Music;

public class PlayerService extends Service {
    private MediaPlayer player = new MediaPlayer();
    private final IBinder mBinder = new LocalBinder();
    private BlockingQueue<Music> playing = new LinkedBlockingDeque<>();
    private Stack<Music> last5 = new Stack<>();
    private boolean play = false;
    private int position = 0;

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
        //what if we finished playing song
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setNext();
                // TODO Auto-generated method stub
            }
        });
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

    public void pause(){
        if (player.isPlaying()) {
            this.position = player.getCurrentPosition();
            player.pause();
        }
    }

    public void unPause(){
        if (!player.isPlaying()) {
            player.seekTo(this.position);
            player.start();
        }
    }

    private void setNext(){
        player.stop();
        player.reset();
        if(playing.peek()!= null && play){
            Music m = playing.poll();
            try {
                player.setDataSource(m.MusicPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            last5.add(m);
        }
    }

    public void addSongNow(Music m){
        player.stop();
        player.release();
        player = MediaPlayer.create(getApplicationContext(), Uri.parse(m.getMusicPath()));
        last5.add(m);
        player.start();
    }

    public void addSongNext(Music m){
        try {
            playing.put(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPlaylist(List<Music> mlist, boolean isShuffled){
        if(isShuffled) Collections.shuffle(mlist);
        playing = new LinkedBlockingDeque<>();
        playing.addAll(mlist);
        setNext();
    }

    public Music currentlyPlayed(){
        return last5.peek();
    }

    public long getActualTimeInMusic() {
        return player.getCurrentPosition();
    }

}
