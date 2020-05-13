package fr.upem.soundroid.ui.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.soundroid.R;

import fr.upem.soundroid.databaseComponents.providers.Music.MusicViewModel;
import fr.upem.soundroid.ui.playlist.playListContentDisplay.PlayListContentDisplay;
import fr.upem.soundroid.ui.playlist.playlistDisplay.PlayListDisplay;

public class MusicPlaylistFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.playlistwrapper, container, false);
        return root;
    }



    public void onStart() {
        super.onStart();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Fragment childFragment = new PlayListDisplay();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();
    }

    public void FromPlayListToContent(String name){

        MusicViewModel mvm = new ViewModelProvider(this).get(MusicViewModel.class);
        Fragment childFragment = new PlayListContentDisplay();
        Bundle args = new Bundle();
        args.putString("playList", name);
        childFragment.setArguments(args);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.addToBackStack("PlaylistContentView");
        transaction.replace(R.id.child_fragment_container, childFragment).commit();

    }
}
