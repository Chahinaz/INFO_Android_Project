package fr.upem.soundroid.ui.playlist.playlistDisplay;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import fr.upem.soundroid.databaseComponents.providers.playlist.PlayListViewModel;
import fr.upem.soundroid.ui.playlist.DialogCreatePlaylist;
import fr.upem.soundroid.ui.playlist.MusicPlaylistFragment;
import fr.upem.soundroid.utils.PlayListAdapter;

public class PlayListDisplay extends Fragment {

    private PlayListAdapter plad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        PlayListViewModel plvm = new ViewModelProvider(this).get(PlayListViewModel.class);
        plad = new PlayListAdapter(new ArrayList<String>(),getActivity(),false);

        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view_for_playlist_frag);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        plad.setOnItemClickListener(new PlayListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String pl = plad.getItem(position);
                ((MusicPlaylistFragment) getParentFragment()).FromPlayListToContent(pl);
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

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCreatePlaylist dcpl = new DialogCreatePlaylist();
                dcpl.show(getActivity().getSupportFragmentManager(),"");
            }
        });
    }
}
