package com.example.revobanga;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Station2Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MediaPlayer mediaPlayer = new MediaPlayer();
        String url = "http://sigi2ko.asuscomm.com:8000";
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();

        }
        catch (Exception e){

        }
        return inflater.inflate(R.layout.fragment_stotis2, container, false);
    }
}
