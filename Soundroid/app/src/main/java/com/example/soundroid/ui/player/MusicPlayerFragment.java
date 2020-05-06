package com.example.soundroid.ui.player;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n")
    private void displayMusic(Music music) {
        album_art.setImageBitmap(music.getThumbnail(getContext()));
        title.setText(music.getTitle());
        artist.setText(music.getAuthor());
        album.setText("unknown album");
    }


    public void onStart() {
        super.onStart();
    }
}