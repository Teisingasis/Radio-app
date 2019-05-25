package com.example.revobanga;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

public class MediaPlayerService extends Service {

    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_STOP = "action_stop";
private Player player = MainActivity.player;
    private MediaPlayer mMediaPlayer = MainActivity.mediaPlayer;
    boolean stop = false;
    boolean ready = false;
    private MediaSessionManager mManager;
    private MediaSession mSession;
    private MediaController mController;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void handleIntent(Intent intent ) {
        if( intent == null || intent.getAction() == null )
            return;

        String action = intent.getAction();

        if( action.equalsIgnoreCase( ACTION_PLAY ) ) {
            mController.getTransportControls().play();
        } else if( action.equalsIgnoreCase( ACTION_PAUSE ) ) {
            mController.getTransportControls().pause();
        } else if( action.equalsIgnoreCase( ACTION_STOP ) ) {
            mController.getTransportControls().stop();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private Notification.Action generateAction(int icon, String title, String intentAction ) {
        Intent intent = new Intent( getApplicationContext(), MediaPlayerService.class );
        intent.setAction( intentAction );
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        return new Notification.Action.Builder( icon, title, pendingIntent ).build();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void buildNotification(Notification.Action action ) {

        Notification.MediaStyle style = new Notification.MediaStyle();

        Intent intent = new Intent( getApplicationContext(), MediaPlayerService.class );
        intent.setAction( ACTION_STOP );
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        Notification.Builder builder = new Notification.Builder( this )
                .setSmallIcon(R.mipmap.ic_launcheris_round)
                .setContentTitle( "RevoBanga" )
                .setContentText( "Media Artist" )
                .setDeleteIntent( pendingIntent )
                .setStyle(style);


            builder.addAction(action);
               style.setShowActionsInCompactView(0);


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
        }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if( mManager == null ) {
            initMediaSessions();
        }

        handleIntent( intent );
        return super.onStartCommand(intent, flags, startId);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initMediaSessions() {



            mSession = new MediaSession(getApplicationContext(), "simple player session");


            mController =new MediaController(getApplicationContext(), mSession.getSessionToken());

            mSession.setCallback(new MediaSession.Callback(){
                                     @Override
                                     public void onPlay() {
                                         super.onPlay();
                                         player.Clicked();
                                             buildNotification( generateAction( android.R.drawable.ic_media_pause, "Pause", ACTION_PAUSE ) );

                                   //      Log.e( "MediaPlayerService", "onPlay");
                                     }

                                     @Override
                                     public void onPause() {
                                         super.onPause();
                                        // Log.e( "MediaPlayerService", "onPause");
                                         player.Clicked();
                                         buildNotification(generateAction(android.R.drawable.ic_media_play, "Play", ACTION_PLAY));
                                     }


                                     @Override
                                     public void onStop() {
                                         super.onStop();
                                       //  Log.e( "MediaPlayerService", "onStop");
                                         //Stop media player here
                                         NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                         notificationManager.cancel( 1 );
                                         Intent intent = new Intent( getApplicationContext(), MediaPlayerService.class );
                                         stopService( intent );
                                     }

                                     @Override
                                     public void onSetRating(Rating rating) {
                                         super.onSetRating(rating);
                                     }
                                 }
            );
        }

    @Override
    public boolean onUnbind(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSession.release();
        }
        return super.onUnbind(intent);
    }
}