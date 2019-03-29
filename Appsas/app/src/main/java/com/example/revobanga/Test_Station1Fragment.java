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

import java.io.IOException;


public class Test_Station1Fragment extends Fragment {

    private String url_radio= "http://sigi2ko.asuscomm.com:8000";
    private MediaPlayer player;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_ui_stotis1, container, false);

        firstStation(view,player);
        initializeMediaPlayer();
        return view;
    }
    private void firstStation(View view, final MediaPlayer mediaPlayer){
        final Button play = view.findViewById(R.id.Play);
        final Button stop = view.findViewById(R.id.Stop);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    play.setEnabled(false);
                    stop.setEnabled(true);
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    initializeMediaPlayer();
                    play.setEnabled(true);
                    stop.setEnabled(false);
                }
            }
        });
    }
    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource(url_radio);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

/*        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playSeekBar.setIndeterminate(false);
                playSeekBar.setSecondaryProgress(100);
                Log.i("Buffering", "" + percent);
            }/
        });*/
    }
}


/*import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

//public class MainActivity extends AppCompatActivity implements OnClickListener {

    private String url_radio= "http://sigi2ko.asuscomm.com:8000";
    private ProgressBar playSeekBar;

    private TextView tvRadioUrl;
    private Button buttonPlay;

    private Button buttonStopPlay;

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIElements();
        initializeMediaPlayer();
    }

    private void initializeUIElements() {

        playSeekBar = (ProgressBar) findViewById(R.id.progressBar1);
        playSeekBar.setMax(100);
        playSeekBar.setVisibility(View.INVISIBLE);
        playSeekBar.setIndeterminate(true);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);

        buttonStopPlay = (Button) findViewById(R.id.buttonStop);
        buttonStopPlay.setEnabled(false);
        buttonStopPlay.setOnClickListener(this);
        tvRadioUrl = (TextView) findViewById(R.id.textViewRadioUrl);
        tvRadioUrl.setText("Radio url : "+url_radio);
    }

    public void onClick(View v) {
        if (v == buttonPlay) {
            startPlaying();
        } else if (v == buttonStopPlay) {
            stopPlaying();
        }
    }

    private void startPlaying() {
        buttonStopPlay.setEnabled(true);
        buttonPlay.setEnabled(false);

        playSeekBar.setVisibility(View.VISIBLE);

        player.prepareAsync();

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {

                player.start();

            }
        });

    }

    private void stopPlaying() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
            initializeMediaPlayer();
        }

        buttonPlay.setEnabled(true);
        buttonStopPlay.setEnabled(false);
        playSeekBar.setIndeterminate(true);
        playSeekBar.setVisibility(View.INVISIBLE);

    }

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource(url_radio);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playSeekBar.setIndeterminate(false);
                playSeekBar.setSecondaryProgress(100);
                Log.i("Buffering", "" + percent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player.isPlaying()) {
            //  player.stop();
        }
    }
} */
