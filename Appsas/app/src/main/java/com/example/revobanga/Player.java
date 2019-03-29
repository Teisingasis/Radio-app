package com.example.revobanga;

import android.media.MediaPlayer;

public class Player {
    MediaPlayer mediaPlayer;
    boolean stop = false;
    boolean ready = false;
    int state = 0;
    String link;

    public Player(MediaPlayer mediaPlayer, String link) {
        this.mediaPlayer = mediaPlayer;
        this.link = link;
    }

    public int Clicked() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
            stop = true;
        } else {
            if (ready) {
                state = 2;
                mediaPlayer.start();
            }
        }
        if (stop) {

            stationInitialize(link);
            state = 0;
            stop = false;
            ready = false;

        }
        return state;
    }

    public void stationInitialize(String link) {
        try {
            mediaPlayer.setDataSource(link);
            mediaPlayer.prepareAsync();

        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                ready = true;
                state = 1;
            }
        });
    }
}
