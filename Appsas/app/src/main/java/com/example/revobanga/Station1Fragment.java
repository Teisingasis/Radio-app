package com.example.revobanga;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class Station1Fragment extends Fragment {
    Player player = MainActivity.player;
    Player player2= MainActivity.player2;
    static Button play;
    View view;
    static TextView info;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stotis1, container, false);
        info = view.findViewById(R.id.ready);
        MainActivity.fragment= (Station1Fragment) getFragmentManager().findFragmentByTag("station1");
        state(player.state);
        playPause();
              TextView scrollingText = view.findViewById(R.id.stations1playing);
      scrollingText.setSelected(true);
        return view;
    }

    public void playPause() {
         play = view.findViewById(R.id.button5);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!player2.mediaPlayer.isPlaying()) {
                    int status = player.Clicked();
                    state(status);


                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please turn off second station", Toast.LENGTH_LONG)
                            .show();

                }
                if(!player.mediaPlayer.isPlaying())
                {
                    play.setBackgroundResource(R.drawable.play3);
                }
                if(player.mediaPlayer.isPlaying())
                {
                    play.setBackgroundResource(R.drawable.play2);
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
                info.setText("Not Ready");
                break;
        }
    }

}

