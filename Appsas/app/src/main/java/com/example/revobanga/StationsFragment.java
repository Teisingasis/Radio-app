package com.example.revobanga;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class StationsFragment extends Fragment {

    public MediaPlayer mediaPlayer;
    public String url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radio_stations, container, false);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        firstStation(view);
        secondStation(view);
        return view;
    }

    private void firstStation(View view) {
        final TextView currentsong = view.findViewById(R.id.current);
        Button pirmStotis = view.findViewById(R.id.button3);
        url = "http://www.kozco.com/tech/32.mp3";
        pirmStotis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Station1Fragment()).commit();
                try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    currentsong.setText("First Station");
                } catch (IOException e) {
                    e.printStackTrace();
                    currentsong.setText("Current Station");
                }
            }
        });
    }

    private void secondStation(View view) {
        Button antrStotis = view.findViewById(R.id.button4);
        antrStotis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Station2Fragment()).commit();
            }
        });
    }
}
