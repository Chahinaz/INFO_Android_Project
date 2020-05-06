package com.example.soundroid.ui.player;

import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
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

    private MusicPlayerViewModel musicPlayerViewModel;

    private ImageView album_art;
    private TextView album;
    private TextView artist;
    private TextView genre;

    MediaMetadataRetriever metadataRetriever;
    byte[] art;

    private ListView songView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        musicPlayerViewModel = new ViewModelProvider(this).get(MusicPlayerViewModel.class);

        View root = inflater.inflate(R.layout.fragment_musicplayer, container, false);

        final TextView textView = root.findViewById(R.id.text_music_player);
        musicPlayerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ((MainActivity) requireActivity()).mMusicViewModel.getMusicById(2)
                .observe(getViewLifecycleOwner(), new Observer<Music>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChanged(@Nullable final Music music) {
                if(music == null) {
                    Log.d(String.valueOf(Level.INFO), "music not found");
                } else {
                    displayMusic(music);

                }
            }
        });

        album_art = root.findViewById(R.id.album_art);
        album = root.findViewById(R.id.Album);
        artist = root.findViewById(R.id.artist_name);
        genre = root.findViewById(R.id.genre);

        return root;
    }

    private void displayMusic(Music music) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            album_art.setImageBitmap(music.getThumbnail(getContext()));
        }
        artist.setText(music.getAuthor());
        album.setText("default album");
    }


    public void onStart() {
        super.onStart();
    }
}