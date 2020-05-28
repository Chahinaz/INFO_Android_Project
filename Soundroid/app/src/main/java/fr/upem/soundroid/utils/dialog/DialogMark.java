package fr.upem.soundroid.utils.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import fr.upem.soundroid.databaseComponents.model.Tag.Tag;
import fr.upem.soundroid.databaseComponents.model.music.Music;
import fr.upem.soundroid.databaseComponents.providers.playlist.PlayListViewModel;
import fr.upem.soundroid.databaseComponents.providers.tag.TagViewModel;

public class DialogMark extends DialogFragment {
    private Music m; //it's a secret tool who will help us latter
    private int theChosenOne;

    public DialogMark(Music m) {
        this.m = m;
        theChosenOne = 0;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Add a mark");
        final CharSequence[] list = new CharSequence[]{"1","2","3","4","5"};
        builder.setSingleChoiceItems(list,0,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                theChosenOne = which;
            }
        })
        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TagViewModel tvm =  new ViewModelProvider(requireActivity()).get(TagViewModel.class);
                tvm.insert(new Tag(m.hash,list[theChosenOne].toString(),true));
                dialog.dismiss();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nothing
            }
        });

        return builder.create();
    }
}
