package com.example.soundroid.ui.player;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    private boolean isPlaying = false;

    private Button toggleMute;
    private boolean isMute = false;

    private Button previousSong;
    private Button nextSong;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MusicPlayerViewModel musicPlayerViewModel = new ViewModelProvider(this).get(MusicPlayerViewModel.class);

        View root = inflater.inflate(R.layout.fragment_musicplayer, container, false);

        ((MainActivity) requireActivity()).mMusicViewModel.getMusicByName("Money Made")
                .observe(getViewLifecycleOwner(), new Observer<Music>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onChanged(@Nullable final Music music) {
                if(music == null) {
                    Log.d(String.valueOf(Level.INFO), "music not found");
                } else {
                    Log.d(String.valueOf(Level.INFO), "music <" + music.getTitle() +"> found");
                    displayMusic(music);

                }
            }
        });

        title = root.findViewById(R.id.title_player);
        artist = root.findViewById(R.id.artist_name_player);
        album = root.findViewById(R.id.album_name_player);
        album_art = root.findViewById(R.id.album_art_player);
        duration = root.findViewById(R.id.song_duration_text);

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
        duration.setText(getTime(music.getDuration()));
    }

    private String getTime(String duration) {
        int seconds = Integer.parseInt(duration) / 1000;
        int minutes = seconds / 60;
        seconds -= (minutes*60);
        return minutes + ":" + seconds;
    }

    private void setButtonListeners(View root) {
        playButton = root.findViewById(R.id.play);
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
    }

    private void onPlayButtonClick(View v) {
        if(!isPlaying) {
            ((MainActivity) getActivity()).palyring();
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
            ((MainActivity) getActivity()).togglePause();
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}