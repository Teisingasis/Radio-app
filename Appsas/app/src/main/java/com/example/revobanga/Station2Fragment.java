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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Station2Fragment extends Fragment {
    Player player = MainActivity.player2;
    Player player2 = MainActivity.player;
    View view;
    Button play;
    static TextView info;
    static ProgressBar spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stotis2, container, false);
        info = view.findViewById(R.id.ready2);
        MainActivity.fragment2= (Station2Fragment) getFragmentManager().findFragmentByTag("station2");
        state(player.state);
        playPause();
        spinner= view.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        return view;
    }

    public void playPause() {
         play = view.findViewById(R.id.button6);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!player2.mediaPlayer.isPlaying()){
                int status = player.Clicked();
                state(status);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please turn off first station", Toast.LENGTH_LONG)
                            .show();
                }
                if(!player.mediaPlayer.isPlaying())
                {
                    play.setBackgroundResource(R.drawable.play3);
                    spinner.setVisibility(View.GONE);
                }
                if(player.mediaPlayer.isPlaying())
                {
                    play.setBackgroundResource(R.drawable.play2);
                    spinner.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static void state(int state) {
        switch (state) {
            case 1:
                info.setText("Ready");
                break;
            case 2:
                info.setText("Playing");
                break;
            default:
                info.setText("Loading");
                break;
        }
    }
}
