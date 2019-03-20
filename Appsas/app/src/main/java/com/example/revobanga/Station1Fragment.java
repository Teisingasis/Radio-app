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


public class Station1Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stotis1, container, false);
        StationsFragment stationsFragment = new StationsFragment();
        MediaPlayer mediaPlayer = stationsFragment.mediaPlayer;
        firstStation(view,mediaPlayer);
        return view;
    }
    private void firstStation(View view, final MediaPlayer mediaPlayer){
        Button playPause = view.findViewById(R.id.button5);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                }
                else{
                    mediaPlayer.pause();
                }

            }
        });
    }
}
