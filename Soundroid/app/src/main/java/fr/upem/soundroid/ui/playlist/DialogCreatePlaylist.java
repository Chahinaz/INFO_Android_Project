package fr.upem.soundroid.ui.playlist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import fr.upem.soundroid.databaseComponents.model.PlayList;
import fr.upem.soundroid.databaseComponents.providers.PlayListViewModel;

public class DialogCreatePlaylist extends DialogFragment {
    private PlayListViewModel plvm;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Title");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                plvm = plvm = new ViewModelProvider(requireActivity()).get(PlayListViewModel.class);
                String result = input.getText().toString();
                PlayList emptyPL = new PlayList();
                emptyPL.mHash = "";
                emptyPL.Title = result;
                plvm.insert(emptyPL);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
