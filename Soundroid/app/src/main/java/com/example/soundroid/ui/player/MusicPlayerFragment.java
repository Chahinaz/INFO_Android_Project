package com.example.soundroid.ui.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return root;
    }
}