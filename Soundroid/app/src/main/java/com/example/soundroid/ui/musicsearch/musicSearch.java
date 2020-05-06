package com.example.soundroid.ui.musicsearch;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundroid.MainActivity;
import com.example.soundroid.R;
import com.example.soundroid.databaseComponents.model.Music;
import com.example.soundroid.databaseComponents.providers.MusicViewModel;
import com.example.soundroid.utils.MusicAdapter;

import java.util.ArrayList;
import java.util.List;

public class musicSearch extends Fragment {

    private MusicSearchViewModel mViewModel;
    private RecyclerView rc;
    private MusicAdapter mA;
    public MusicViewModel mMusicViewModel;

    public static musicSearch newInstance() {
        return new musicSearch();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.music_search_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rc = getView().findViewById(R.id.my_recycler_view_for_music_search_frag);
        mA = new MusicAdapter(new ArrayList<Music>());
        rc.setAdapter(mA);

        mMusicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        mMusicViewModel.getAllMusic().observe(getViewLifecycleOwner(),new Observer<List<Music>>() {
            @Override
            public void onChanged(List<Music> music) {
                mA.SetList(music);
                mA.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
