package fr.upem.soundroid.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundroid.R;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

    private static ClickListener clickListener;
    private List<String> playListList;
    private FragmentActivity ctx;
    private final boolean isForDialog;

    public PlayListAdapter(List<String> musicList, FragmentActivity ctx, boolean isForDialog) {
        super();
        this.playListList = musicList;
        this.ctx = ctx;
        this.isForDialog = isForDialog;
    }

    public void setList(List<String> newList){
        this.playListList = newList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(!isForDialog){
            return new PlayListAdapter.ViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.playlist_entry, parent, false));
        }
        else{
            return new PlayListAdapter.ViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.playlist_entry_2, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.update(playListList.get(position));
    }

    @Override
    public int getItemCount() {
        return playListList.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        PlayListAdapter.clickListener = clickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private ImageView albumArt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            LinearLayout itemWrapper = itemView.findViewById(R.id.playlistWrapper);
            itemWrapper.setOnClickListener(this);
            this.name = itemView.findViewById(R.id.name_view_playlist_entry);
            this.albumArt = itemView.findViewById(R.id.album_art_playlist_entry);
        }

        private void update(String t) {
            this.name.setText(t);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
