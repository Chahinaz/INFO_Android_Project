package fr.upem.soundroid.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundroid.R;
import fr.upem.soundroid.databaseComponents.model.Music;

import java.util.List;

/**
 * Created by cloud on 06/05/2020.
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private List<Music> musicList;

    public MusicAdapter(List<Music> musicList) {
        super();
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.music_entry, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.update(musicList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MusicAdapter.clickListener = clickListener;
    }

    public void SetList(List<Music> mlist){
        musicList = mlist;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView artistName;
        private TextView albumName;

        private ImageView albumArt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            LinearLayout itemWrapper = itemView.findViewById(R.id.item_wrapper);
            itemWrapper.setOnClickListener(this);

            this.title = itemView.findViewById(R.id.name_view_list_entry);
            this.artistName = itemView.findViewById(R.id.artist_view_list_entry);
            this.albumName = itemView.findViewById(R.id.album_view_list_entry);
            this.albumArt = itemView.findViewById(R.id.album_art_list_entry);
        }

        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.Q)
        private void update(Music music) {
            this.title.setText(music.getTitle());
            this.artistName.setText(music.getAuthor());
            this.albumName.setText("Unknown Album");
            this.albumArt.setImageBitmap(music.getThumbnail(itemView.getContext()));
        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }

    }
}
