package fr.upem.soundroid.ui.musicfromplaylist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class musicfromplaylist extends Fragment {
    private MusicAdapter mA;
    private RecyclerView recyclerView;
    private String playlist;
    private Button play;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.music_from_playlist, container, false);
        recyclerView = root.findViewById(R.id.my_recycler_view_for_playlist_display);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.mA = new MusicAdapter(new ArrayList<Music>(),requireActivity());
        recyclerView.setAdapter(mA);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        Bundle args = getArguments();
        if(args != null){
            this.playlist = args.getString("playlist");
            MusicViewModel mvm = new ViewModelProvider(this).get(MusicViewModel.class);
            mvm.getAllMusicForPlaylist(this.playlist).observe(getViewLifecycleOwner(),new Observer<List<Music>>() {
                @Override
                public void onChanged(List<Music> music) {
                    mA.SetList(music);
                    mA.notifyDataSetChanged();
                }
            });

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
