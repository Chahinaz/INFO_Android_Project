package fr.upem.soundroid.ui.playlist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import fr.upem.soundroid.databaseComponents.model.Music;
import fr.upem.soundroid.databaseComponents.model.PlayList;
import fr.upem.soundroid.databaseComponents.providers.PlayListViewModel;
import fr.upem.soundroid.utils.PlayListAdapter;

public class DialogPlaylist extends DialogFragment {
    private RecyclerView mRecyclerView;
    private List<String> playlist;
    private Music m;
    private DialogFragment df;

    public DialogPlaylist(List<String> lst,Music m) {
        super();
        this.playlist = lst;
        this.m = m;
        df = this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRecyclerView = new RecyclerView(requireActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));


        final PlayListAdapter plad = new PlayListAdapter(playlist, getActivity(), true);
        plad.setOnItemClickListener(new PlayListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                PlayListViewModel plvm = new ViewModelProvider(requireActivity()).get(PlayListViewModel.class);
                PlayList pl = new PlayList();
                pl.Title = plad.getItem(position);
                pl.mHash = m.hash;
                plvm.insert(pl);
                df.dismiss();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mRecyclerView.setAdapter(plad);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
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
