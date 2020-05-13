package fr.upem.soundroid.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.upem.soundroid.databaseComponents.model.Tag.Tag;
import fr.upem.soundroid.databaseComponents.model.music.Music;
import fr.upem.soundroid.databaseComponents.model.playlist.PlayList;
import fr.upem.soundroid.databaseComponents.providers.Music.MusicViewModel;
import fr.upem.soundroid.databaseComponents.providers.playlist.PlayListViewModel;
import fr.upem.soundroid.databaseComponents.providers.tag.TagViewModel;

public class ExternalStorageWritter {
    private ArrayList<Music> mList = new ArrayList<>();
    private ArrayList<PlayList> pList = new ArrayList<>();
    private ArrayList<Tag> tList = new ArrayList<>();


    public void PrepareExportBdd(ViewModelStoreOwner lifecycle,LifecycleOwner ctx){
        MusicViewModel mvm = new ViewModelProvider(lifecycle).get(MusicViewModel.class);
        PlayListViewModel pvm = new ViewModelProvider(lifecycle).get(PlayListViewModel.class);
        TagViewModel tvm = new ViewModelProvider(lifecycle).get(TagViewModel.class);

        mvm.getAllMusic().observe(ctx, new Observer<List<Music>>() {
            @Override
            public void onChanged(List<Music> music) {
                mList = (ArrayList<Music>) music;
            }
        });

    }


    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
