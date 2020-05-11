package fr.upem.soundroid.ui.playlist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.upem.soundroid.databaseComponents.model.Music;
import fr.upem.soundroid.databaseComponents.providers.PlayListViewModel;
import fr.upem.soundroid.utils.MusicAdapter;
import fr.upem.soundroid.utils.PlayListAdapter;

public class DialogPlaylist extends DialogFragment {
    private RecyclerView mRecyclerView;
    private PlayListAdapter plad;
    private List<String> playlist;

    public DialogPlaylist(List<String> lst) {
        super();
        this.playlist = lst;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRecyclerView = new RecyclerView(requireActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        this.plad = new PlayListAdapter(playlist,getActivity(),true);
        plad.setOnItemClickListener(new PlayListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //add to bdd
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mRecyclerView.setAdapter(plad);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(mRecyclerView)
                .setMessage("testing")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }
}
