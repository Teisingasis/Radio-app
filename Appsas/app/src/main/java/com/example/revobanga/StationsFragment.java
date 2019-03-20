package com.example.revobanga;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StationsFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_radio_stations, container, false);
    firstStation(view);
    secondStation(view);
    return  view;
    }
    private void firstStation(View view){
        Button pirmStotis = view.findViewById(R.id.button3);
        pirmStotis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new Station1Fragment()).commit();
            }
        });
    }
    private void secondStation(View view){
        Button antrStotis = view.findViewById(R.id.button4);
        antrStotis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new Station2Fragment()).commit();
            }
        });
    }


}
