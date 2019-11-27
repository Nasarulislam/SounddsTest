package com.example.sounddstest.Services;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.media.MediaSessionManager;
import androidx.media.session.MediaButtonReceiver;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.R;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

//import static com.example.sounddstest.Activitys.MainActivity.PlayRelative;
//import static com.example.sounddstest.Activitys.MainActivity.PuaseRelative;


//import static com.rstream.relaxingsounds.Services.MediaPlayerService.mediaSession;


public class MediaPlayerServiceSecond extends Service {
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_REWIND = "action_rewind";
    public static final String ACTION_FAST_FORWARD = "action_fast_foward";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";
    private final static String APP_PACKAGE = "com.rstream.relaxingsounds";
    private final static String CITIES_CHANEL_ID ="101";
    private static final String CHANNEL_NAME = "Media playback";
    private MediaPlayer mMediaPlayer;
    private MediaSessionManager mManager;
    private MediaSession mSession;
    private MediaSessionCompat mediaSessionCompat;
 //   private MediaController mController;
    private RemoteViews remoteView;
    PendingIntent pendingIntent;
    MediaController mController ;
    MediaMetadataCompat mediaMetadata ;
    MediaDescriptionCompat description ;
    SharedPreferences shared;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;

    public static Boolean playpause=false;
    public static Long secondspause= Long.valueOf(0);

    boolean isPlaying=false;
   // Activity context=MainActivity.class;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Context context;


    private void handleIntent(Intent intent ) {
        if( intent == null || intent.getAction() == null )
            return;

        String action = intent.getAction();

        if( action.equalsIgnoreCase( ACTION_PLAY ) ) {
            mController.getTransportControls().play();
        } else if( action.equalsIgnoreCase( ACTION_PAUSE ) ) {
            mController.getTransportControls().pause();
        } else if( action.equalsIgnoreCase( ACTION_FAST_FORWARD ) ) {
            mController.getTransportControls().fastForward();
        } else if( action.equalsIgnoreCase( ACTION_REWIND ) ) {
            mController.getTransportControls().rewind();
        } else if( action.equalsIgnoreCase( ACTION_PREVIOUS ) ) {
            mController.getTransportControls().skipToPrevious();
        } else if( action.equalsIgnoreCase( ACTION_NEXT ) ) {
            mController.getTransportControls().skipToNext();
        } else if( action.equalsIgnoreCase( ACTION_STOP ) ) {
            mController.getTransportControls().stop();
        }
    }

    private NotificationCompat.Action generateAction(int icon, String title, String intentAction ) {
        Intent intent = new Intent( getApplicationContext(), MediaPlayerServiceSecond.class );
        intent.setAction( intentAction );

        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        return new NotificationCompat.Action.Builder( icon, title, pendingIntent ).build();
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildNotification(NotificationCompat.Action action ) {

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        Intent intent = new Intent( this, MainActivity.class );
        intent.setAction( ACTION_PAUSE );

        intent.putExtra("MediaPlayerServiceSecond","MainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
       pendingIntent  = PendingIntent.getBroadcast(this, 0, intent, 0);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


 ///\\\

        // Given a media session and its context (usually the component containing the session)
// Create a NotificationCompat.Builder

// Get the session's metadata
      //  mediaSessionCompat = new MediaSessionCompat(this, LOG_TAG);
      //  MediaControllerCompat controller = mediaSessionCompat.getController();
      //  MediaMetadataCompat mediaMetadata = controller.getMetadata();
      //  MediaDescriptionCompat description = mediaMetadata.getDescription();
        notificationManager=  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String NOTIFICATION_CHANNEL_ID = "101";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManagerCompat.IMPORTANCE_LOW);
           // @SuppressLint("WrongConstant")
            //Configure Notification Channel
            notificationChannel.setDescription("Game Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationChannel.setShowBadge(false);

            notificationManager.createNotificationChannel(notificationChannel);
            //notificationManager.cancelAll();

            Log.d("looooog","o");
        }
        String imageNoti=null;
        ImageView imageView;
      //  notificationManager.cancelAll();
       // RemoteViews mContentView = new RemoteViews(getPackageName(), R.layout.notification_layout);

        /*Picasso.with(this)
                .load(url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                        *//* Save the bitmap or do something with it here *//*

                        //Set it in the ImageView
                        theView.setImageBitmap(bitmap);
                    }
                });*/


         builder   = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Picasso.get().load(getString(R.string.imageUrl)+"rain1night.webp").into(new Target() {
        //Picasso.get().load(getString(R.string.imageUrl)+MainActivity.Companion.getStringImageName()+".webp").into(new Target() {
                                                                                     @Override
                                                                                     public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                                                        // mContentView.setImageViewBitmap(R.id.notifimage,bitmap);
                                                                                     builder.setLargeIcon(bitmap);
                                                                                     }

                                                                                     @Override
                                                                                     public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                                                                     }

                                                                                     @Override
                                                                                     public void onPrepareLoad(Drawable placeHolderDrawable) {

                                                                                     }
                                                                                 });
             //   mContentView.setImageViR.id.imageView, Picasso.get().load(getString(R.string.imageUrl) + themeimage + ".webp").into());
       // mContentView.setTextViewText(R.id.notiftitle, SecondActivity.packname);
       // mContentView.setTextViewText(R.id.notiftext, "This is a custom layout");
      //  mContentView.setImageViewBitmap(R.id.notifimage,largeIcon);


        builder


               // .setContentTitle(SecondActivity.packname)
                //.setLargeIcon(largeIcon)
//                .setLargeIcon(MusicLibrary.getAlbumBitmap(this, description.getMediaId()))

                .setContentIntent(mController.getSessionActivity())
                .setVisibility(Notification.VISIBILITY_PRIVATE)
              //  .setColor(ContextCompat.getColor(this, R.color.splash_screen_color))
                .setColorized(true)
               // .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(
                 //       MediaPlayerServiceSecond.this, PlaybackStateCompat.STATE_PAUSED))
                //.setDeleteIntent()
                //.setAutoCancel(true)
                .setOngoing(true)
                .setShowWhen(true)
                //.setCustomContentView(mContentView)
                .setContentIntent(contentIntent)
                //.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .extend(new NotificationCompat.WearableExtender().addAction(action));
      //  builder.setContent(mContentView);
                builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(MediaSessionCompat.Token.fromToken(mSession.getSessionToken()))
                        .setShowActionsInCompactView(0)
                        // Add a cancel button
                                .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(
                                this, PlaybackStateCompat.STATE_PAUSED))
                       // .setCancelButtonIntent(pendingIntent)
                );

                builder.setOngoing(true);
                builder.setUsesChronometer(false);
                builder.setDeleteIntent(getDeleteIntent());

        builder.setContentTitle(getResources().getString(R.string.app_name));
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.extend(new NotificationCompat.WearableExtender().addAction(action));
        //builder.setSmallIcon(android.R.drawable.stat_sys_headset);
        builder.addAction(action);
        // builder.addAction( generateAction( android.R.drawable.ic_media_ff, "Fast Foward", ACTION_FAST_FORWARD ) );
        //builder.addAction( generateAction( android.R.drawable.ic_media_next, "Next", ACTION_NEXT ) );
        builder.setAutoCancel(true);
        //builder.setDeleteIntent(pendingIntent);
        //builder.setPriority(NotificationCompat.PRIORITY_MAX);
        //builder.setDeleteIntent(contentIntent);
        //startForeground(101, builder.build());
        //startService(pendingIntent)

        Notification notification = builder.build();

        if (isPlaying ) {
            Intent intente = new Intent(this, MediaPlayerServiceSecond.class);
            ContextCompat.startForegroundService(this, intente);
            startForeground(101, notification);
            //mStarted = true;
        } else {
            if (!isPlaying) {
                stopForeground(false);
               // mStarted = false;
            }
            notificationManager.notify(101, notification);
        }
        Log.d("looooog","p");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context=this;

        if( mManager == null ) {

            isPlaying=true;
                initMediaSessions();

        }

        handleIntent( intent );
        shared=getSharedPreferences("preference.xml", MODE_PRIVATE);
        return super.onStartCommand(intent, flags, startId);
    }
    private void initMediaSessions()  {
        mMediaPlayer = new MediaPlayer();

        mSession = new MediaSession(getApplicationContext(), "simple");
        mController =new MediaController(getApplicationContext(), mSession.getSessionToken());

        mSession.setCallback(new MediaSession.Callback(){
                                 @RequiresApi(api = Build.VERSION_CODES.O)
                                 @Override
                                 public void onPlay() {
                                     super.onPlay();
                                     isPlaying=true;
                                     Log.e( "MediaPlayerServicekkk", "onPlay");
                                     buildNotification( generateAction( android.R.drawable.ic_media_pause, "Pause", ACTION_PAUSE ) );
                                    // MediaPlayerService.playMedia(getApplicationContext());
                                     MediaPlayerService.resumeMedia();
                                    // MediaPlayerService.resumeMedia1();
                                   // SecondActivity.buttonContinue.setText(getString(R.string.Pause));
                                     // context=this;

                                    // MainActivity.Companion..setVisibility(View.VISIBLE);
                                     playpause=true;
                                     MainActivity.Companion.getPlayRelativ().setVisibility(View.GONE);
                                     MainActivity.Companion.getPuaseRelativ().setVisibility(View.VISIBLE);
//                                   ((MainActivity)context.getApplicationContext()).PuaseRelativee.setVisibility(View.VISIBLE);
//                                   ((MainActivity)context.getApplicationContext()).PlayRelativee.setVisibility(View.GONE);

//                                     if (secondspause>0&&secondspause!=null)

                                     if (secondspause!=0) {
                                         MainActivity mainActivity=new MainActivity();
                                                 mainActivity.countdown(secondspause,(MainActivity)mainActivity);
                                                // mainActivity.notify();
                                         //MainActivity.Companion.countdown(secondspause);
                                     }
                                     shared.edit().putBoolean("puaseIcon",true).apply();
                                 }

                                 @RequiresApi(api = Build.VERSION_CODES.O)
                                 @Override
                                 public void onPause() {
                                     super.onPause();
                                     isPlaying=false;
                                     Log.e( "MediaPlayerServicekkk", "onPause");
                                     buildNotification(generateAction(android.R.drawable.ic_media_play, "Play", ACTION_PLAY));
                                    // MediaPlayerService.substopMedia();
                                     /*builder.build().flags|= Notification.FLAG_AUTO_CANCEL;
                                     startForeground(101, builder.build());*/
                                    // stopSelf();

                                    // stopForeground(STOP_FOREGROUND_DETACH);
                                       // stopForeground(true);
                                    // MediaPlayerService.pausemedia1();
                                     MediaPlayerService.pauseMedia();
                                     playpause=false;
                                 //    if (((MainActivity)getApplicationContext()).getCountDownTimer()!=null)
                                  ///       ((MainActivity)getApplicationContext()).getCountDownTimer().cancel();

                                   // notificationManager.cancelAll();
                                   // SecondActivity.buttonContinue.setText(getString(R.string.Continue));
                                     shared.edit().putBoolean("puaseIcon",false).apply();
                                    // MainActivity.PlaypuaseImage.setBackgroundResource(R.drawable.ic_play_arrow_black);
                                     //if (PuaseRelative!=null)
                                    MainActivity.Companion.getPlayRelativ().setVisibility(View.VISIBLE);
                                    MainActivity.Companion.getPuaseRelativ().setVisibility(View.GONE);
//                                     ((MainActivity)context).PuaseRelativee.setVisibility(View.GONE);
  //                                   ((MainActivity)context).PlayRelativee.setVisibility(View.VISIBLE);

                                    Intent intent=new Intent(getApplicationContext(),MediaPlayerService.class);
                                    startService(intent);
                                     //startForeground(101, builder.build());
                                   // builder.setDeleteIntent()

                                 }

                                 @RequiresApi(api = Build.VERSION_CODES.O)
                                 @Override
                                 public void onSkipToNext() {
                                     super.onSkipToNext();
                                     Log.e( "MediaPlayerServicekkk", "onSkipToNext");
                                     //Change media here
                                      buildNotification( generateAction( android.R.drawable.ic_media_pause, "Pause", ACTION_PAUSE ) );
                                 }
                                 @RequiresApi(api = Build.VERSION_CODES.O)
                                 @Override
                                 public void onSkipToPrevious() {
                                     super.onSkipToPrevious();
                                     Log.e( "MediaPlayerServicekkk", "onSkipToPrevious");
                                     //Change media here
                                     buildNotification( generateAction( android.R.drawable.ic_media_play, "Pause", ACTION_PLAY ) );

                                 }

                                 @Override
                                 public void onFastForward() {
                                     super.onFastForward();
                                     Log.e( "MediaPlayerService", "onFastForward");
                                     //Manipulate current media here
                                 }

                                 @Override
                                 public void onRewind() {
                                     super.onRewind();
                                     Log.e( "MediaPlayerService", "onRewind");
                                     //Manipulate current media here
                                 }

                                 @Override
                                 public void onStop() {
                                     super.onStop();
                                     Log.e( "MediaPlayerService", "onStop");
                                     //Stop media player here
                                     NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                     notificationManager.cancel( 1 );
                                     Intent intent = new Intent( getApplicationContext(), MediaPlayerServiceSecond.class );
                                     stopService( intent );
                                 }
                                 @Override
                                 public void onSeekTo(long pos) {
                                     super.onSeekTo(pos);
                                 }

                                 @Override
                                 public void onSetRating(Rating rating) {
                                     super.onSetRating(rating);
                                 }
                             }
        );
        mSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        mSession.release();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel mChannel = new NotificationChannel(CITIES_CHANEL_ID, "Notification", NotificationManager.IMPORTANCE_LOW);
        mChannel.setDescription("CHANNEL_DESCRIPTION");
        mChannel.setShowBadge(false);
        mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationManager.createNotificationChannel(mChannel);
    }

   /* private PendingIntent createContentIntent() {
        Intent openUI = new Intent(this, MediaPlayerServiceSecond.class);
        openUI.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(
                this, 1, openUI, PendingIntent.FLAG_CANCEL_CURRENT);
    }*/
   protected PendingIntent getDeleteIntent () {
       Intent intent = new Intent( this, MediaPlayerServiceSecond. class ) ;

       intent.setAction( "notification_cancelled" ) ;
       return PendingIntent. getBroadcast ( this, 0 , intent , PendingIntent. FLAG_CANCEL_CURRENT ) ;
   }
}
