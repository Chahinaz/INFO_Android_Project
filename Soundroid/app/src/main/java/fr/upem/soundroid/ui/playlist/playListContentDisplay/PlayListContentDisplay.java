package fr.upem.soundroid.ui.playlist.playListContentDisplay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundroid.R;

import java.util.ArrayList;
import java.util.List;

import fr.upem.soundroid.databaseComponents.model.Music;
import fr.upem.soundroid.databaseComponents.providers.MusicViewModel;
import fr.upem.soundroid.utils.MusicAdapter;

public class PlayListContentDisplay extends Fragment {

    private  MusicAdapter mA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_playlist2, container, false);
        initRCView(root);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    public void initRCView(@NonNull View view){
        mA = new MusicAdapter(new ArrayList<Music>(),requireActivity());
        RecyclerView rc = view.findViewById(R.id.my_recycler_view_for_playlist_content);
        rc.setAdapter(mA);
        rc.setLayoutManager(new LinearLayoutManager(requireActivity()));
        Bundle args = getArguments();
        if(args != null){
            String plname = args.getString("playList");
            MusicViewModel mvm = new ViewModelProvider(this).get(MusicViewModel.class);
            mvm.getAllMusicForPlaylist(plname).observe(getViewLifecycleOwner(), new Observer<List<Music>>() {
                @Override
                public void onChanged(List<Music> music) {
                    mA.SetList(music);
                    mA.notifyDataSetChanged();
                }
            });
        }
    }


}
