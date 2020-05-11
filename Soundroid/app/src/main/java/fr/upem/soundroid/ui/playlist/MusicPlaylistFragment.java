package fr.upem.soundroid.ui.playlist;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import fr.upem.soundroid.databaseComponents.model.Music;
import fr.upem.soundroid.databaseComponents.model.PlayList;
import fr.upem.soundroid.databaseComponents.providers.MusicViewModel;
import fr.upem.soundroid.databaseComponents.providers.PlayListViewModel;
import fr.upem.soundroid.utils.PlayListAdapter;

public class MusicPlaylistFragment extends Fragment {

    private MusicPlaylistViewModel musicPlaylistViewModel;
    private ImageView cover;
    private ListView songsView;
    private RecyclerView recyclerView;
    private PlayListAdapter plad;
    private PlayListViewModel plvm;
    private MusicViewModel mvm;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        musicPlaylistViewModel = new ViewModelProvider(this).get(MusicPlaylistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_playlist, container, false);
        plvm = new ViewModelProvider(this).get(PlayListViewModel.class);
        mvm =  new ViewModelProvider(this).get(MusicViewModel.class);
        plad = new PlayListAdapter(new ArrayList<String>(),getActivity(),false);

        recyclerView = root.findViewById(R.id.my_recycler_view_for_playlist_frag);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        plad.setOnItemClickListener(new PlayListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String pl = plad.getItem(position);
                mvm.getAllMusicForPlaylist(pl).observe(requireActivity(), new Observer<List<Music>>() {
                    @Override
                    public void onChanged(List<Music> music) {
                      ArrayList<Music> uniqueMusic = new ArrayList<>();
                      for(Music m : music){
                          if(!uniqueMusic.contains(m)) {
                              uniqueMusic.add(m);
                          }
                      }
                      Log.d("test",uniqueMusic.toString());
                    }
                });

            }
        });
        recyclerView.setAdapter(plad);
        plvm.getAllplaylist().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> playLists) {
                plad.setList(playLists);
                plad.notifyDataSetChanged();
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCreatePlaylist dcpl = new DialogCreatePlaylist();
                dcpl.show(getActivity().getSupportFragmentManager(),"");
            }
        });


        return root;
    }

    public void onStart() {
        super.onStart();
    }
}
