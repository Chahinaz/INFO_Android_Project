package com.example.soundroid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.soundroid.MainActivity;
import com.example.soundroid.R;
import com.example.soundroid.databaseComponents.model.Music;

public class HomeFragment extends Fragment {

    private boolean isStarted;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ImageButton b = root.findViewById(R.id.play);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStarted) {
                    ((MainActivity) getActivity()).palyring();
                    isStarted = true;
                } else {
                    ((MainActivity) getActivity()).togglePause();
                }
            }
        });
        return root;
    }
}
