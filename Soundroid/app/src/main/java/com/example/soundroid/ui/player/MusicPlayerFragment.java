package com.example.soundroid.ui.player;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.soundroid.MainActivity;
import com.example.soundroid.R;
import com.example.soundroid.databaseComponents.model.Music;

import java.util.concurrent.TimeUnit;
import android.os.Handler;
import java.util.logging.Level;

/**
 * Created by cloud on 14/04/2020.
 */
public class MusicPlayerFragment extends Fragment {

    private ImageView album_art;
    private TextView title;
    private TextView album;
    private TextView artist;
    private TextView duration;

    private Button playButton;
    private TextView playTime;
    private boolean isPlaying = false;
    private int playingTime;
    private int songDuration;
    private Handler timeHandler;
    private int pauseTime;

    private Button toggleMute;
    private boolean isMute = false;

    private Button previousSong;
    private Button nextSong;
    private Button shuffleSongs;

    private ProgressBar songTimeProgressBar;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MusicPlayerViewModel musicPlayerViewModel = new ViewModelProvider(this).get(MusicPlayerViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_musicplayer, container, false);

        ((MainActivity) requireActivity()).mMusicViewModel.getMusicByName("Money Made")
                .observe(getViewLifecycleOwner(), new Observer<Music>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onChanged(@Nullable final Music music) {
                if(music == null) {
                    Log.d(String.valueOf(Level.INFO), "music not found");
                    return;
                }
                Log.d(String.valueOf(Level.INFO), "music <" + music.getTitle() +"> found");
                displayMusic(music);
                setProgressBar(root, music);
            }
        });

        title = root.findViewById(R.id.title_player);
        artist = root.findViewById(R.id.artist_name_player);
        album = root.findViewById(R.id.album_name_player);
        album_art = root.findViewById(R.id.album_art_player);
        duration = root.findViewById(R.id.song_duration_text);

        playTime = root.findViewById(R.id.played_time_text);
        timeHandler = new Handler();

        setButtonListeners(root);

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n")
    private void displayMusic(Music music) {
        album_art.setImageBitmap(music.getThumbnail(getContext()));
        title.setText(music.getTitle());
        artist.setText(music.getAuthor());
        album.setText("unknown album");
        duration.setText(getTime(Integer.parseInt(music.getDuration())));
    }

    private String getTime(int timeInSeconds) {
        int seconds = timeInSeconds / 1000;
        int minutes = seconds / 60;
        seconds -= (minutes*60);
        return minutes + ":" + seconds;
    }

    private void setButtonListeners(View root) {
        playButton = root.findViewById(R.id.toggle_play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlayButtonClick(v);
            }
        });

        toggleMute = root.findViewById(R.id.toggle_sound_button);
        toggleMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleMuteClick(v);
            }
        });

        previousSong = root.findViewById(R.id.previous_song_button);
        previousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviousSongClick(v);
            }
        });

        nextSong = root.findViewById(R.id.next_song_button);
        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextSongClick(v);
            }
        });

        shuffleSongs = root.findViewById(R.id.shuffle_song_list_button);
        shuffleSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShuffleClick(v);
            }
        });
    }

    private void onPlayButtonClick(View v) {
        if(!isPlaying) {
            playingTime = pauseTime;
            startPlayTime();
            Toast toast = Toast.makeText(v.getContext(), "Play music", Toast.LENGTH_SHORT);
            toast.show();
            playButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_media_pause,
                    0, 0, 0);
            isPlaying = true;
        } else {
            Toast toast = Toast.makeText(v.getContext(), "Pause music", Toast.LENGTH_SHORT);
            toast.show();
            playButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_media_play,
                    0, 0, 0);
            isPlaying = false;
        }
    }

    private void onToggleMuteClick(View v) {
        if(!isMute) {
            Toast toast = Toast.makeText(v.getContext(), "Mute", Toast.LENGTH_SHORT);
            toast.show();
            toggleMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_media_unmute,
                    0, 0, 0);
            isMute = true;
        } else {
            Toast toast = Toast.makeText(v.getContext(), "Unmute", Toast.LENGTH_SHORT);
            toast.show();
            toggleMute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_media_mute,
                    0, 0, 0);
            isMute = false;
        }
    }

    private void onPreviousSongClick(View v) {
        Toast toast = Toast.makeText(v.getContext(), "Play previous song", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void onNextSongClick(View v) {
        Toast toast = Toast.makeText(v.getContext(), "Play next song", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void onShuffleClick(View v) {
        Toast toast = Toast.makeText(v.getContext(), "Shuffle songs", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setProgressBar(View root, Music music) {
        songDuration = Integer.parseInt(music.getDuration())/1000;

        songTimeProgressBar = root.findViewById(R.id.music_player_progressBar);
        songTimeProgressBar.setMax(255); // max value supported by progressbar
        songTimeProgressBar.setProgress(pauseTime);
    }

    private void setProcessBarProgress(int playingTime, int songDuration) {
        // règle de trois
        int progress = playingTime * songTimeProgressBar.getMax() / songDuration;
        songTimeProgressBar.setProgress(progress);
    }

    private Runnable playingSongTime = new Runnable() {
        @Override
        public void run() {
            playingTime += 1;
            playTime.setText(getTime(playingTime));
            setProcessBarProgress(playingTime, songDuration);
            Log.d("Level.INFO", "time : " + playingTime + "out of " + songTimeProgressBar.getMax());
            timeHandler.postDelayed(getPlayingSongTime(), 1000);
        }
    };

    private Runnable getPlayingSongTime() {
        return playingSongTime;
    }

    private void startPlayTime() {
        playingSongTime.run();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}