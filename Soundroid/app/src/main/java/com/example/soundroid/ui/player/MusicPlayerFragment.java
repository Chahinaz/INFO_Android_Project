package com.example.soundroid.ui.player;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.soundroid.R;

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

        album_art = root.findViewById(R.id.album_art);
        album = root.findViewById(R.id.Album);
        artist = root.findViewById(R.id.artist_name);
        genre = root.findViewById(R.id.genre);

        return root;
    }


    public void onStart() {
        super.onStart();
    }
}

/*

 */