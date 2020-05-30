package fr.upem.soundroid.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.soundroid.R;

import fr.upem.soundroid.utils.ExternalStorageWritter;

public class setting  extends Fragment {
    private Button btnexport;
    private Button submit;
    private SwitchCompat switchPrf;
    private EditText url_pref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.seting_layout, container, false);
        btnexport = root.findViewById(R.id.exportDBButton);
        submit = root.findViewById(R.id.button_save_settings);
        switchPrf = root.findViewById(R.id.switch_pause_batterie);
        url_pref = root.findViewById(R.id.url_pref);
        setListener();
        return root;
    }

    private void setListener(){
        btnexport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExternalStorageWritter esw = new ExternalStorageWritter();
                esw.PrepareExportBdd(requireActivity(),requireActivity(),requireActivity());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean pauseIsACTIVE = switchPrf.isChecked();
                String url = url_pref.getText().toString();
                SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("url",url);
                editor.putBoolean("batterie",pauseIsACTIVE);
                editor.apply();
            }
        });
    }
}
