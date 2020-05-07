package com.example.soundroid.ui.musicsearch;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.soundroid.MainActivity;
import com.example.soundroid.R;
import com.example.soundroid.databaseComponents.model.Music;
import com.example.soundroid.databaseComponents.providers.MusicViewModel;
import com.example.soundroid.utils.MusicAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class musicSearch extends Fragment {

    private MusicSearchViewModel mViewModel;
    private RecyclerView rc;
    private MusicAdapter mA;
    public MusicViewModel mMusicViewModel;

    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;

    public static musicSearch newInstance() {
        return new musicSearch();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.music_search_fragment, container, false);

        MusicViewModel mMusicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        mMusicViewModel.getAllMusic().observe(getViewLifecycleOwner(), new Observer<List<Music>>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onChanged(@Nullable final List<Music> musicList) {
                assert musicList != null;
                //displayMusicList(musicList);
                recyclerView = root.findViewById(R.id.my_recycler_view_for_music_search_frag);
                if(recyclerView == null) {
                    Log.d("Level.INFO", "recycler view null ??");
                    return;
                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
                recyclerView.setAdapter(new MusicAdapter(new ArrayList<Music>()));
                musicAdapter = new MusicAdapter(musicList);
                musicAdapter.setOnItemClickListener(new MusicAdapter.ClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.d(String.valueOf(Level.INFO), "Go to Player Fragment with song <" + musicList.get(position).getTitle() + ">");
                        Toast toast = Toast.makeText(v.getContext(), "Go to Player Fragment with song <" + musicList.get(position).getTitle() + ">", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                recyclerView.setAdapter(musicAdapter);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rc = requireView().findViewById(R.id.my_recycler_view_for_music_search_frag);
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
