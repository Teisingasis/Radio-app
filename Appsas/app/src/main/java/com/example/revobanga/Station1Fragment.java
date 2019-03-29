package com.example.revobanga;

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


public class Station1Fragment extends Fragment {
    MediaPlayer mediaPlayer = MainActivity.mediaPlayer;
    View view;
    boolean stop = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stotis1, container, false);
        playPause();
        return view;
    }

    public void playPause() {
        Button play = view.findViewById(R.id.button5);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    stop = true;
                } else {
                    if(MainActivity.ready) {
                        mediaPlayer.start();
                    }
                }
                if (stop) {
                    MainActivity.station1Initialize();
                    stop=false;
                }
            }
        });
    }

}

