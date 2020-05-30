package fr.upem.soundroid.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.io.File;
import java.io.FileOutputStream;
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


    public void PrepareExportBdd(final ViewModelStoreOwner lifecycle, final LifecycleOwner ctx, final Context ctx2){
        final MusicViewModel mvm = new ViewModelProvider(lifecycle).get(MusicViewModel.class);
        final PlayListViewModel pvm = new ViewModelProvider(lifecycle).get(PlayListViewModel.class);
        final TagViewModel tvm = new ViewModelProvider(lifecycle).get(TagViewModel.class);

        mvm.getAllMusic().observe(ctx, new Observer<List<Music>>() {
            @Override
            public void onChanged(List<Music> music) {
                mList = (ArrayList<Music>) music;
                pvm.getAll().observe(ctx, new Observer<List<PlayList>>() {
                    @Override
                    public void onChanged(List<PlayList> strings) {
                        pList = (ArrayList<PlayList>) strings;
                        tvm.getAll().observe(ctx, new Observer<List<Tag>>() {
                            @Override
                            public void onChanged(List<Tag> tags) {
                                tList = (ArrayList<Tag>)  tags;
                                generateBackup(ctx2);
                            }
                        });
                    }
                });

            }
        });
    }


    private void generateBackup(Context context) {
        if(mList == null || pList == null || tList == null){
            return;
        }

        String sBody = "";
        for(Music m : mList){ sBody += m.toString()+"\n"; }
        for(PlayList t : pList){ sBody += t.toString()+"\n"; }
        for(Tag t : tList){ sBody += t.toString(); }

        long time = System.currentTimeMillis();
        String name = "Backup"+time+".txt";


        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"backup");
        if(!file.exists()){
            boolean t = file.mkdir();
            if(!t){
                Toast.makeText(context, "cannot create file, please grante permission", Toast.LENGTH_SHORT).show();
            }
        }
        try{
            File towrite = new File(file, name);
            FileOutputStream fileoutputstream = new FileOutputStream(towrite);
            fileoutputstream.write(sBody.getBytes());
            fileoutputstream.close();
            Toast.makeText(context, "file created", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
