package fr.upem.soundroid.utils.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import fr.upem.soundroid.databaseComponents.model.Tag.Tag;
import fr.upem.soundroid.databaseComponents.model.music.Music;
import fr.upem.soundroid.databaseComponents.providers.tag.TagViewModel;

public class DialogTag extends DialogFragment {
    private Music m;
    private String datag;

    public DialogTag(Music m) {
        this.m = m;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Add a tag");


        final EditText input = new EditText(requireActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                datag = input.getText().toString();
                TagViewModel tvm =  new ViewModelProvider(requireActivity()).get(TagViewModel.class);
                tvm.insert(new Tag(m.hash,datag,false));
                dialog.dismiss();
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
