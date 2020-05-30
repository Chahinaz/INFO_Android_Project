package fr.upem.soundroid.ui.playlist.playListContentDisplay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundroid.R;

import java.util.ArrayList;
import java.util.List;

import fr.upem.soundroid.MainActivity;
import fr.upem.soundroid.databaseComponents.model.music.Music;
import fr.upem.soundroid.databaseComponents.providers.Music.MusicViewModel;
import fr.upem.soundroid.utils.MusicAdapter;

public class PlayListContentDisplay extends Fragment {

    private  MusicAdapter mA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_playlist2, container, false);
        initRCView(root);
        Button runplaylist = root.findViewById(R.id.button_play_playlist);
        runplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Music> mlist = mA.getList();
                if(mlist != null) ((MainActivity) getActivity()).AddPlaylist(mlist);
            }
        });
        return root;
    }

    public void initRCView(@NonNull View view){
        mA = new MusicAdapter(new ArrayList<Music>(),requireActivity());
        RecyclerView rc = view.findViewById(R.id.my_recycler_view_for_playlist_content);
        rc.setAdapter(mA);
        rc.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mA.setOnItemClickListener(new MusicAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ((MainActivity) getActivity()).AddSongNow(mA.getMusicAtPos(position));
            }
        });
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
