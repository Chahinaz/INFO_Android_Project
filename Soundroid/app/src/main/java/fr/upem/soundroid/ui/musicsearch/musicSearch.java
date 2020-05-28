package fr.upem.soundroid.ui.musicsearch;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundroid.R;

import java.util.ArrayList;
import java.util.List;

import fr.upem.soundroid.MainActivity;
import fr.upem.soundroid.databaseComponents.model.music.Music;
import fr.upem.soundroid.databaseComponents.providers.Music.MusicViewModel;
import fr.upem.soundroid.databaseComponents.providers.tag.TagViewModel;
import fr.upem.soundroid.utils.MusicAdapter;

public class musicSearch extends Fragment {
    private SearchView searchView;
    private TextView tagValue;
    private TextView tagMark;

    private MusicAdapter mA;
    public MusicViewModel mMusicViewModel;
    private RecyclerView recyclerView;

    private TagViewModel tagViewModel;

    public static musicSearch newInstance() {
        return new musicSearch();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.music_search_fragment, container, false);
        searchView = root.findViewById(R.id.searchView);
        tagValue = root.findViewById(R.id.tag);
        tagMark = root.findViewById(R.id.mark);
        recyclerView = root.findViewById(R.id.my_recycler_view_for_music_search_frag);
        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        return root;
    }

    public void initRecycle(final Context ctx){
        mA = new MusicAdapter(new ArrayList<Music>(),getActivity());
        mA.setOnItemClickListener(new MusicAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ((MainActivity) getActivity()).AddSongNow(mA.getMusicAtPos(position));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setAdapter(mA);

        this.mMusicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        mMusicViewModel.getAllMusic().observe(getViewLifecycleOwner(), new Observer<List<Music>>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onChanged(@Nullable final List<Music> musicList) {
                assert musicList != null;
                //displayMusicList(musicList);
                if(recyclerView == null) {
                    Log.d("Level.INFO", "recycler view null ??");
                    return;
                }
               mA.SetList(musicList);
                mA.notifyDataSetChanged();
            }
        });
    }

    public void setListenerForSearchView(){
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()) {
                    mMusicViewModel.getMusicLikeName(query).observe(getViewLifecycleOwner(), new Observer<List<Music>>() {
                        @Override
                        public void onChanged(List<Music> music) {
                            mA.SetList(music);
                            mA.notifyDataSetChanged();
                        }
                    });
                } else {
                    mMusicViewModel.getAllMusic().observe(getViewLifecycleOwner(), new Observer<List<Music>>() {
                        @Override
                        public void onChanged(List<Music> music) {
                            mA.SetList(music);
                            mA.notifyDataSetChanged();
                        }
                    });
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return onQueryTextSubmit(newText);
            }
        });

        tagValue.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(!(searchView.equals(null))){
                    String t = v.getText().toString();
                    tagViewModel.getTagMusicValue(t).observe(requireActivity(), new Observer<List<Music>>() {
                        @Override
                        public void onChanged(List<Music> music) {
                            mA.SetList(music);
                            mA.notifyDataSetChanged();
                        }
                    });
                } else {
                    tagViewModel.getTagMusicValue(searchView.getTransitionName(), v.getTransitionName()).observe(getViewLifecycleOwner(), new Observer<List<Music>>() {
                        @Override
                        public void onChanged(List<Music> music) {
                            mA.SetList(music);
                            mA.notifyDataSetChanged();
                        }
                    });
                }
                return false;
            }
        });

//        tagMark.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(!(searchView.equals(null))){
//                    tagDao.getTagMusicMark(v.getTransitionName()).observe(getViewLifecycleOwner(), new Observer<List<Music>>() {
//                        @Override
//                        public void onChanged(List<Music> music) {
//                            mA.SetList(music);
//                            mA.notifyDataSetChanged();
//                        }
//                    });
//                } else {
//                    tagDao.getTagMusicMark(searchView.getTransitionName(), v.getTransitionName()).observe(getViewLifecycleOwner(), new Observer<List<Music>>() {
//                        @Override
//                        public void onChanged(List<Music> music) {
//                            mA.SetList(music);
//                            mA.notifyDataSetChanged();
//                        }
//                    });
//                }
//                return false;
//            }
//        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecycle(view.getContext());
        setListenerForSearchView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
