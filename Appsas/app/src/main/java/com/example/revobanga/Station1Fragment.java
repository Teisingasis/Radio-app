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
    Player player = MainActivity.player;
    View view;
    TextView info;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stotis1, container, false);
        info = view.findViewById(R.id.ready);

        state(player.state);
        playPause();
        return view;
    }

    public void playPause() {
        Button play = view.findViewById(R.id.button5);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = player.Clicked();
                state(status);
            }
        });
    }

    public void state(int state) {
        switch (state) {
            case 1:
                info.setText("Ready");
                break;
            case 2:
                info.setText("Playing");
                break;
            default:
                info.setText("Not Ready");
                break;
        }
    }

}

