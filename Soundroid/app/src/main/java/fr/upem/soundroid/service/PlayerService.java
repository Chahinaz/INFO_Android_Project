package fr.upem.soundroid.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

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

    public void playPrev(){
        Music m = last5.pop(); // removing current
        if(last5.peek() != null){
            BlockingQueue<Music> backup = playing;
            playing = new LinkedBlockingDeque<>();
            try {
                playing.put(last5.pop());
                playing.put(m);
                playing.addAll(backup);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setNext();
        }
    }

    public void setNext(){
        if(playing.peek()!= null){
            addSongNow(playing.poll());
        }
    }

    public void addSongNow(Music m){
        player.stop();
        player.release();
        player = MediaPlayer.create(getApplicationContext(), Uri.parse(m.getMusicPath()));
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setNext();
            }
        });
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
        if(last5.isEmpty()) return null;
        if(last5.peek() != null){
            return last5.peek();
        }
        return null;
    }

    public long getActualTimeInMusic() {
        return player.getCurrentPosition();
    }


    public int currentTime(){
        if( player != null){
            return player.getCurrentPosition();
        }
        return -1;
    }

    public int duration(){
        if(last5.isEmpty()) return -1;
        if(last5.peek() != null){
            return Integer.parseInt(last5.peek().Duration);
        }
        return -1;
    }


    public void stopPlaying() {
        if( player != null){
            player.pause();
        }
    }

}
