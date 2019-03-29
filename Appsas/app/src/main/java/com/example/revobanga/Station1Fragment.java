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

    TextView info;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stotis1, container, false);
        info = view.findViewById(R.id.ready);
        ;
        switch (MainActivity.red) {
            case 0:
                info.setText("Ready");
                break;
            case 1:
                info.setText("Playing");
                break;
            default:
                info.setText("Not Ready");
                break;
        }
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
                    if (MainActivity.ready) {
                        info.setText("Playing");
                        MainActivity.red = 1;
                        mediaPlayer.start();
                    }
                }
                if (stop) {
                    ((MainActivity) getActivity()).station1Initialize();
                    info.setText("Not Ready");
                    MainActivity.red = 2;
                    stop = false;
                    MainActivity.ready = false;
                }
            }
        });
    }

}

