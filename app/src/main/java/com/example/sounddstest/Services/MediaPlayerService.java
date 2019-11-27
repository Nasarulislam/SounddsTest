package com.example.sounddstest.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.Adapters.RecyclerViewAdapter;
import com.example.sounddstest.Adapters.RelaxingRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";

  /*  public static MediaPlayer mediaPlayer1;
    public static MediaPlayer mediaPlayer2;
    public static MediaPlayer mediaPlayer3;
    public static MediaPlayer mediaPlayer4;
    public static MediaPlayer mediaPlayer5;
    public static MediaPlayer mediaPlayer6;
    public static MediaPlayer mediaPlayer7;
    public static MediaPlayer mediaPlayer8;
    public static MediaPlayer mediaPlayer9;
    public static MediaPlayer mediaPlayer10;
    public static MediaPlayer mediaPlayer11;
    public static MediaPlayer mediaPlayer12;
    public static MediaPlayer mediaPlayer13;
    public static MediaPlayer mediaPlayer14;
    public static MediaPlayer mediaPlayer15;
    public static MediaPlayer mediaPlayer16;
    public static MediaPlayer mediaPlayer17;
    public static MediaPlayer mediaPlayer18;
    public static MediaPlayer mediaPlayer19;
    public static MediaPlayer mediaPlayer20;*/
    public static final String ACTION_REWIND = "action_rewind";
    public static final String ACTION_FAST_FORWARD = "action_fast_foward";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";
    //AudioPlayer notification ID
    private static final int NOTIFICATION_ID = 101;
    private final static String APP_PACKAGE = "com.rstream.relaxingsounds";
    private final static String CITIES_CHANEL_ID = "101";
    public static Handler mHandler = new Handler();
    public static MediaPlayer mediaPlayerA;
    public static boolean done = false;
    public static boolean stop = false;
    public static boolean start = false;
    public static boolean stop1 = false;
    public static boolean start1 = false;
    public static boolean stop2 = false;
    public static boolean start2 = false;
    public static int currentDuration = 0;
    public static int maxDuration = 0;
    public static HashMap<String, MediaPlayer> mediaPlayerHashMap = new HashMap();
    //    lat mediaPlayerHashMap: HashMap<String, MediaPlayer>
///Notification
//         public static final String ACTION_PLAY = "com.valdioveliu.valdio.audioplayer.ACTION_PLAY";
//         public static final String ACTION_PAUSE = "com.valdioveliu.valdio.audioplayer.ACTION_PAUSE";
//         public static final String ACTION_PREVIOUS = "com.valdioveliu.valdio.audioplayer.ACTION_PREVIOUS";
//         public static final String ACTION_NEXT = "com.valdioveliu.valdio.audioplayer.ACTION_NEXT";
//         public static final String ACTION_STOP = "com.valdioveliu.valdio.audioplayer.ACTION_STOP";

    //MediaSession
    public static MediaSessionManager mediaSessionManager;
    public static MediaControllerCompat.TransportControls transportControls;
    public static ArrayList<String> nowplaysounds = new ArrayList<>();
    /* public static Runnable mUpdateTimeTask=new Runnable() {
         @Override
         public void run() {
             int  maxDurationA=mediaPlayerA.getDuration();
             int currentDurationA=mediaPlayerA.getCurrentPosition();
            int maxDurationB=mediaPlayerB.getDuration();
             int currentDurationB=mediaPlayerB.getCurrentPosition();

             long  maxDuraction1 = mediaPlayer1_1.getDuration();
             long  currentDuration1 = mediaPlayer1_1.getCurrentPosition();
             long maxDuraction2=mediaPlayer1_2.getDuration();
             long currentDuration2=mediaPlayer1_2.getCurrentPosition();

             long maxDuraction3=mediaPlayer2_1.getDuration();
             long currentDuration3 = mediaPlayer2_1.getCurrentPosition();
             long maxDuraction4=mediaPlayer2_2.getDuration();
             long currentDuration4=mediaPlayer2_2.getCurrentPosition();

             if (mediaPlayerA.isPlaying()){
             if (!stop) {
                 if (currentDurationA >= (maxDurationA - 5000)) {
                     Log.d("gggggg","done");
                     start = false;
                     stop = true;
                     startFadeOut(mediaPlayerA,SecondActivity.seekvolumeA);
                     startFadeIn(mediaPlayerB,SecondActivity.seekvolumeA);
                     mediaPlayerB.start();


                 }
             }
             }
             if (mediaPlayerB.isPlaying()) {
                 if (!start) {
                     if (currentDurationB >= (maxDurationB - 5000)) {
                         stop = false;
                         start = true;
                         startFadeOut(mediaPlayerB,SecondActivity.seekvolumeA);
                         startFadeIn(mediaPlayerA,SecondActivity.seekvolumeA);
                         mediaPlayerA.start();
                         Log.d("gggggg", "done33");
                     }
                 }
             }

             if (!stop1)
             {
                 if (currentDuration1 >= (maxDuraction1-5000)) {
                     start1 = false;
                     stop1 = true;
                     //volume1=1;
                   //  volume = 0;
                     Log.d("wwwww", currentDuration3 + "Done" + stop);
                     startFadeOut(mediaPlayer1_1,SecondActivity.seekvolume1);


                     startFadeIn(mediaPlayer1_2,SecondActivity.seekvolume1);
                     mediaPlayer1_2.start();
                 }




             }
             if (!start1)
             {
                 if (currentDuration2 >= (maxDuraction2-5000)) {
                     stop1 = false;
                     start1 = true;
                    // volume = 0;
                     // volume1=1;
                     Log.d("yyyy", currentDuration4 + "Done" + stop1);
                     startFadeOut(mediaPlayer1_2,SecondActivity.seekvolume1);


                     startFadeIn(mediaPlayer1_1,SecondActivity.seekvolume1);
                     mediaPlayer1_1.start();




                 }

             }
             if (!stop2)
             {
                 if (currentDuration3 >= (maxDuraction3-5000))
                 {
                     start2=false;
                     stop2 = true;
                     //volume1=1;
                     //volume=0;
                     Log.d("wwwww", currentDuration3 + "Done" + stop);
                     startFadeOut(mediaPlayer2_1,SecondActivity.seekvolume2);


                     startFadeIn(mediaPlayer2_2,SecondActivity.seekvolume2);
                     mediaPlayer2_2.start();




                 }
             }
             if (!start2)
             {
                 if (currentDuration4 >= (maxDuraction4-5000))
                 {
                     stop2=false;
                     start2 = true;
                    // volume=0;
                     //volume1=1;
                     Log.d("yyyy", currentDuration4 + "Done" + stop1);
                     startFadeOut(mediaPlayer2_2,SecondActivity.seekvolume2);


                     startFadeIn(mediaPlayer2_1,SecondActivity.seekvolume2);
                     mediaPlayer2_1.start();




                 }
             }







             mHandler.postDelayed(this, 1000);



         }

     };
     public static void updateProgressBar() {
         mHandler.postDelayed(mUpdateTimeTask, 1000);
     }*/
    public static float volume1, volumeV;
    //\\\\
    public static float volume;
    private static MediaSessionCompat mediaSession;
    AudioManager audioManager;
    MediaController mController;
    MediaMetadataCompat mediaMetadata;
    MediaDescriptionCompat description;
    // private int mediaFile;
    private String mediaFileA;
    private String mediaFileB;
    private String mediaFile1_1;
    private String mediaFile1_2;
    private String mediaFile2_1;
    private String mediaFile2_2;
    //Handle incoming phone calls
    private boolean ongoingCall = false;
    private PhoneStateListener phoneStateListener;
    private TelephonyManager telephonyManager;
    private MediaPlayer mMediaPlayer;
    private androidx.media.MediaSessionManager mManager;
    private MediaSession mSession;
    private MediaSessionCompat mediaSessionCompat;
    /*private boolean requestAudioFocus() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Focus gained
            return true;
        }
        substopMedia();
        pauseMedia();
        //Could not gain focus
        return false;
    }

    private boolean removeAudioFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                audioManager.abandonAudioFocus(this);
    }
*/
   /* @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange){
            case AudioManager.AUDIOFOCUS_GAIN:
                playMedia(this);
                resumeMedia();
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                substopMedia();
                pauseMedia();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                pauseMedia();
                substopMedia();
            break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                pauseMedia();
                substopMedia();
                break;
        }
    }*/
    //   private MediaController mController;
    private RemoteViews remoteView;

    /* public static void playMedia(Context context) {

     *//*  // startFadeIn(mediaPlayerA,SecondActivity.seekvolumeA);
            mediaPlayerA.start();
      //  startFadeIn(mediaPlayer1_1,SecondActivity.seekvolume1);
            mediaPlayer1_1.start();
      // startFadeIn(mediaPlayer2_1,SecondActivity.seekvolume2);
            mediaPlayer2_1.start();
*/
   /*

        if (!SecondActivity.chipboolean1)
        { mediaPlayer1 = MediaPlayer.create(context, R.raw.fire);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume1 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer1.setVolume(volumeV, volumeV);
            mediaPlayer1.setLooping(true);

            mediaPlayer1.start();
        }
        if (!SecondActivity.chipboolean2)
        { mediaPlayer2 = MediaPlayer.create(context, R.raw.owl_v);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume2 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer2.setVolume(volumeV, volumeV);
            mediaPlayer2.setLooping(true);

            mediaPlayer2.start();

        }
        if (!SecondActivity.chipboolean3)
        {
            mediaPlayer3= MediaPlayer.create(context, R.raw.cow);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume3 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer3.setVolume(volumeV, volumeV);
            mediaPlayer3.setLooping(true);

            mediaPlayer3.start();
        }

        if (!SecondActivity.chipboolean4)
        { mediaPlayer4 = MediaPlayer.create(context, R.raw.flute);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume4 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer4.setVolume(volumeV, volumeV);
            mediaPlayer4.setLooping(true);

            mediaPlayer4.start();
        }
        if (!SecondActivity.chipboolean5)
        {mediaPlayer5 = MediaPlayer.create(context, R.raw.birds);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume5 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer5.setVolume(volumeV, volumeV);
            mediaPlayer5.setLooping(true);

            mediaPlayer5.start();

        } if (!SecondActivity.chipboolean6)
        {
            mediaPlayer6 = MediaPlayer.create(context, R.raw.wind);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume6 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer6.setVolume(volumeV, volumeV);
            mediaPlayer6.setLooping(true);

            mediaPlayer6.start();
        } if (!SecondActivity.chipboolean7)
        {    mediaPlayer7 = MediaPlayer.create(context, R.raw.rainonglass);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume7 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer7.setVolume(volumeV, volumeV);
            mediaPlayer7.setLooping(true);

            mediaPlayer7.start();

        } if (!SecondActivity.chipboolean8)
        {mediaPlayer8 = MediaPlayer.create(context, R.raw.thunder);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume8 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer8.setVolume(volumeV, volumeV);
            mediaPlayer8.setLooping(true);

            mediaPlayer8.start();

        } if (!SecondActivity.chipboolean9)
        {

            mediaPlayer9 = MediaPlayer.create(context, R.raw.fish);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume9 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer9.setVolume(volumeV, volumeV);
            mediaPlayer9.setLooping(true);

            mediaPlayer9.start();
        } if (!SecondActivity.chipboolean10)
        {  mediaPlayer10 = MediaPlayer.create(context, R.raw.frogs);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume10 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer10.setVolume(volumeV, volumeV);

            mediaPlayer10.setLooping(true);

            mediaPlayer10.start();
        }
        if (!SecondActivity.chipboolean11)
        {  mediaPlayer11 = MediaPlayer.create(context, R.raw.wavebig);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume11 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer11.setVolume(volumeV, volumeV);

            mediaPlayer11.setLooping(true);

            mediaPlayer11.start();
        }
        if (!SecondActivity.chipboolean12)
        {mediaPlayer12 = MediaPlayer.create(context, R.raw.flowingwater);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume12 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer12.setVolume(volumeV, volumeV);
            mediaPlayer12.setLooping(true);

            mediaPlayer12.start();
        }
        if (!SecondActivity.chipboolean13)
        {
            mediaPlayer13 = MediaPlayer.create(context, R.raw.recordplayer);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume13 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer13.setVolume(volumeV, volumeV);
            mediaPlayer13.setLooping(true);

            mediaPlayer13.start();
        }
        if (!SecondActivity.chipboolean14)
        {mediaPlayer14 = MediaPlayer.create(context, R.raw.tvsports);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume14 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer14.setVolume(volumeV, volumeV);
            mediaPlayer14.setLooping(true);

            mediaPlayer14.start();
        }
        if (!SecondActivity.chipboolean15)
        {mediaPlayer15 = MediaPlayer.create(context, R.raw.crows);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume15 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer15.setVolume(volumeV, volumeV);
            mediaPlayer15.setLooping(true);

            mediaPlayer15.start();

        } if (!SecondActivity.chipboolean16)
        {mediaPlayer16 = MediaPlayer.create(context, R.raw.dolphin);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume16 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer16.setVolume(volumeV, volumeV);
            mediaPlayer16.setLooping(true);

            mediaPlayer16.start();

        } if (!SecondActivity.chipboolean17)
        {mediaPlayer17 = MediaPlayer.create(context, R.raw.beachgame);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume17 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer17.setVolume(volumeV, volumeV);
            mediaPlayer17.setLooping(true);

            mediaPlayer17.start();
        }
        if (!SecondActivity.chipboolean18)
        {
            mediaPlayer18 = MediaPlayer.create(context, R.raw.seagull);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume18 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer18.setVolume(volumeV, volumeV);
            mediaPlayer18.setLooping(true);

            mediaPlayer18.start();
        } if (!SecondActivity.chipboolean19)
        {mediaPlayer19 = MediaPlayer.create(context, R.raw.wolf);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume19 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer19.setVolume(volumeV, volumeV);
            mediaPlayer19.setLooping(true);

            mediaPlayer19.start();
        } if (!SecondActivity.chipboolean20)
        {mediaPlayer20 = MediaPlayer.create(context, R.raw.sheep);
            volumeV = (float) (1 - (Math.log(SecondActivity.MAX_VOLUME -SecondActivity.seekvolume20 ) / Math.log(SecondActivity.MAX_VOLUME)));
            MediaPlayerService.mediaPlayer20.setVolume(volumeV, volumeV);
            mediaPlayer20.setLooping(true);

            mediaPlayer20.start();
        }
    }*/
    public static void playSubMedia() {
      /*  mediaPlayer3A.start();
        mediaPlayer4A.start();
        mediaPlayer5A.start();
        mediaPlayer6A.start();
        mediaPlayer7A.start();
        mediaPlayer8A.start();
        mediaPlayer9A.start();
        mediaPlayer10A.start();
        mediaPlayer11A.start();
        mediaPlayer12A.start();*/

    }

    public static void muteVolume() {
  /*      mediaPlayer3A.setVolume(0,0);
        mediaPlayer4A.setVolume(0,0);
        mediaPlayer5A.setVolume(0,0);
        mediaPlayer6A.setVolume(0,0);
        mediaPlayer7A.setVolume(0,0);
        mediaPlayer8A.setVolume(0,0);
        mediaPlayer9A.setVolume(0,0);
        mediaPlayer10A.setVolume(0,0);
        mediaPlayer11A.setVolume(0,0);
        mediaPlayer12A.setVolume(0,0);*/

    }

    public static void stopMedia() {
        Log.d("nowplaysounds", "click");
        for (int i = 0; i < RecyclerViewAdapter.formList.size(); i++) {
            if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")) != null) {
                if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).isPlaying()) {
                    mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).stop();

                }
            }
            if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")) != null) {

                if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).isPlaying()) {
                    mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).stop();
                    MainActivity.Companion.setPlayingBolean(true);
                }
            }
        }

        for (int i = 0; i < RelaxingRecyclerViewAdapter.formList.size(); i++) {
            if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")) != null) {
                if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).isPlaying()) {
                    mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).stop();

                }
            }
            if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")) != null) {

                if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).isPlaying()) {
                    mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).stop();
                }
            }
        }
        MainActivity.Companion.setPlayingBolean(false);
    }

    public static void pauseMedia() {
        Log.d("nowplaysounds", "click");
        nowplaysounds.clear();
        /* if (mediaPlayerA==null)
            return;
        else if (mediaPlayerA.isPlaying())
        {
            mediaPlayerA.pause();
           // SecondActivity.resumePositionA = mediaPlayerA.getCurrentPosition();
        }*/
        for (int i = 0; i < RecyclerViewAdapter.formList.size(); i++) {
            if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")) != null) {
                if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).isPlaying()) {
                    mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).pause();
                    nowplaysounds.add(RecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " "));
                    Log.d("nowplaysounds", "" + nowplaysounds);
                }
            }
            if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")) != null) {

                if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).isPlaying()) {
                    mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).pause();
                    nowplaysounds.add(RecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " "));
                    Log.d("nowplaysounds", "" + nowplaysounds);
                }
            }
        }

        for (int i = 0; i < RelaxingRecyclerViewAdapter.formList.size(); i++) {
            if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")) != null) {
                if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).isPlaying()) {
                    mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).pause();
                    nowplaysounds.add(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " "));
                    Log.d("nowplaysounds", "" + nowplaysounds);
                }
            }
            if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")) != null) {

                if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).isPlaying()) {
                    mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).pause();
                    nowplaysounds.add(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " "));
                    Log.d("nowplaysounds", "" + nowplaysounds);
                }
            }
        }
    }

  /*  @Override
    public void onSeekComplete(MediaPlayer mp) {

    }*/

   /* public void seekTo(int progress) {
        mediaPlayerA.seekTo(progress);
    }*/
   /* @Override
    public void onAudioFocusChange(int focusChange) {
        //Invoked when the audio focus of the system is updated.
    }*/

    public static void PlayingMedia() {

        for (int i = 0; i < RecyclerViewAdapter.formList.size(); i++) {
            if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")) != null) {
                if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).isPlaying()) {
                    //  mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).stop();
                    MainActivity.Companion.setPlayingBolean(true);
                }

            }
            if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")) != null) {

                if (mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).isPlaying()) {
                    //  mediaPlayerHashMap.get(RecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).stop();
                    MainActivity.Companion.setPlayingBolean(true);
                }
            }
        }

        for (int i = 0; i < RelaxingRecyclerViewAdapter.formList.size(); i++) {
            if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")) != null) {
                if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).isPlaying()) {
                    //  mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound1Name().replaceAll("_", " ")).stop();
                    MainActivity.Companion.setPlayingBolean(true);
                }
            }
            if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")) != null) {

                if (mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).isPlaying()) {
                    //  mediaPlayerHashMap.get(RelaxingRecyclerViewAdapter.formList.get(i).getSubsound2Name().replaceAll("_", " ")).stop();
                    MainActivity.Companion.setPlayingBolean(true);
                }
            }
        }

    }

    public static void resumeMedia() {
       /* if (mediaPlayerA==null)
            return;
        else if (!mediaPlayerA.isPlaying()&&SecondActivity.resumePositionA!=0) {
            mediaPlayerA.seekTo(SecondActivity.resumePositionA);
            mediaPlayerA.start();
        }*/

        for (int i = 0; i < nowplaysounds.size(); i++) {
            if (mediaPlayerHashMap.get(nowplaysounds.get(i)) != null) {
                //  if (mediaPlayerHashMap.get(MainActivity.formList.get(i).getSubsound1Name().replaceAll("_", " ")).isPlaying()) {
                mediaPlayerHashMap.get(nowplaysounds.get(i)).start();
                mediaPlayerHashMap.get(nowplaysounds.get(i)).setLooping(true);
                // nowplaysounds.add(MainActivity.formList.get(i).getSubsound1Name().replaceAll("_", " "));
                Log.d("nowplaysounds", "" + nowplaysounds);

                // }
            }

        }
        MainActivity.Companion.setPlayingBolean(true);

    }

    public static void startFadeIn(final MediaPlayer media, final int seekVolume) {
        volume = 0;
        // media.setVolume(volume,volume);
        //long mDuraction= media.getDuration();

        final long FADE_DURATION = 5000;
        //final long FADE_DURATION = (mDuraction*25/100);//The duration of the fade
        //The amount of time between volume changes. The smaller this is, the smoother the fade
        final int FADE_INTERVAL = 10;
        //final float MAX_VOLUME = 1.5f;//The volume will increase from 0 to 1
        final int MAX_VOLUME = seekVolume;
        long numberOfSteps = FADE_DURATION / FADE_INTERVAL; //Calculate the number of fade steps
        //Calculate by how much the volume changes each step
        final float deltaVolume = MAX_VOLUME / (float) numberOfSteps;

        //Create a new Timer and Timer task to run the fading outside the main UI thread
        final Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                fadeInStep(deltaVolume, media); //Do a fade step
                Log.d("volume33", "" + volume);
                //Cancel and Purge the Timer if the desired volume has been reached
                if (volume >= seekVolume) {
                    Log.d("jjjvolume44", "" + volume);
                    timer.cancel();
                    timer.purge();

                }
            }
        };

        timer.schedule(timerTask, FADE_INTERVAL, FADE_INTERVAL);
    }

    public static void startFadeOut(final MediaPlayer media, int seekVolume) {
        // volume1=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        // long mDuraction= media.getDuration();
        volume1 = seekVolume;
        Log.d("seeeeeeeee", "" + seekVolume);
        // Log.d("pppvolume111z",correntValue+""+progresssy);
        //  media.setVolume(volume,volume);
        final long FADE_DURATION = 5000;
        //final long FADE_DURATION = (mDuraction*25/100); //The duration of the fade
        Log.d("bbbb", "mkmkmkmk");
        //The amount of time between volume changes. The smaller this is, the smoother the fade
        final int FADE_INTERVAL = 10;
        final int MAX_VOLUME = seekVolume; //The volume will increase from 0 to 1
        long numberOfSteps = FADE_DURATION / FADE_INTERVAL; //Calculate the number of fade steps
        //Calculate by how much the volume changes each step
        final float deltaVolume = MAX_VOLUME / (float) numberOfSteps;

        //Create a new Timer and Timer task to run the fading outside the main UI thread
        final Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d("volume222", "" + volume1);
                fadeOutStep(deltaVolume, media); //Do a fade step
                //Cancel and Purge the Timer if the desired volume has been reached
                if (volume1 <= 0) {
                    Log.d("kkkvolume111z", "" + volume1);
                    timer.cancel();
                    timer.purge();
                    done = true;
                    //media.stop();

                }
            }


        };

        timer.schedule(timerTask, FADE_INTERVAL, FADE_INTERVAL);
    }

    public static void fadeInStep(float deltaVolume, MediaPlayer media) {
        media.setVolume(volume, volume);
        volume += deltaVolume;

    }

    public static void fadeOutStep(float deltaVolume, MediaPlayer media) {

        media.setVolume(volume1, volume1);
        volume1 -= deltaVolume;

    }

    @Override
    public IBinder onBind(Intent intent) {
        mSession.release();
        return null;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // stopMedia();
        // stopSelf();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.d("MediaPlayer Error", "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.d("MediaPlayer Error", "MEDIA ERROR SERVER DIED " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.d("MediaPlayer Error", "MEDIA ERROR UNKNOWN " + extra);
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        maxDuration = mp.getDuration();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        final int maxValue = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int correntValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        // ExampleBottomSheetDialog.progresssy=(float)correntValue;
        //playMedia();
        //updateProgressBar();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            //An audio file is passed to the service through putExtra();
            mediaFileA = intent.getExtras().getString("media");
            mediaFileB = intent.getExtras().getString("media");
            mediaFile1_1 = intent.getExtras().getString("media1");
            mediaFile1_2 = intent.getExtras().getString("media1");
            mediaFile2_1 = intent.getExtras().getString("media2");
            mediaFile2_2 = intent.getExtras().getString("media2");
        } catch (NullPointerException e) {
            stopSelf();
        }

        //Request audio focus
       /* if (requestAudioFocus() == false) {
            //Could not gain focus
            stopSelf();
        }*/
        //if (mediaFile != null && mediaFile != "")
        //mediaPlayerHashMap = new HashMap<>();
        initMediaPlayer();

      /*  try {
            initMediaSession();
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/
        //playMedia();
//callStateListener();
       /* try {


            if (mediaSessionManager == null) {
                try {
                    initMediaSession();
                    initMediaPlayer();
                } catch (RemoteException e) {
                    e.printStackTrace();
                    stopSelf();
                }
                buildNotification(PlaybackStatus.PLAYING);
            }
        }catch (NullPointerException e) {
            stopSelf();
        }
        //Handle Intent action from MediaSession.TransportControls
        handleIncomingActions(intent);*/
        //initMediaPlayer();
        // if( mManager == null ) {

        //   initMediaSessions();

        //  }
        // Intent intent = new Intent( getApplicationContext(), MediaPlayerServiceSecond.class );
        intent.setAction(MediaPlayerServiceSecond.ACTION_PLAY);
        intent.setAction(MediaPlayerServiceSecond.ACTION_PAUSE);
        // startService( intent1 );

        //  handleIntent( intent );
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
       /* if (mediaPlayerA != null) {
           // stopMedia();
            mediaPlayerA.release();
        }*/
        //removeAudioFocus();

    }

    private void initMediaPlayer() {


        //Set up MediaPlayer event listeners
        ///MAIN SOUNDS
        mediaPlayerA = new MediaPlayer();
        mediaPlayerA.setOnCompletionListener(this);
        mediaPlayerA.setOnErrorListener(this);
        mediaPlayerA.setOnPreparedListener(this);
        mediaPlayerA.setOnBufferingUpdateListener(this);
        // mediaPlayerA.setOnSeekCompleteListener(this);
        mediaPlayerA.setOnInfoListener(this);
        mediaPlayerA.reset();
        //  mediaPlayerA=MediaPlayer.create(this, Uri.parse(mediaFileA));

        /* try {

            mediaPlayerA.setDataSource(mediaFileA);
            //mediaPlayerA=MediaPlayer.create(mediaFileA);
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }*/
        //mediaPlayerA.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //mediaPlayerA=MediaPlayer.create(this,R.raw.dheera);
        // mediaPlayerA.start();
       /* try {
            // Set the data source to the mediaFile location
//            AssetFileDescriptor afd = getResources().openRawResourceFd(mediaFile);

            String RES_PREFIX = "android.resource://com.my.package/";
            mediaPlayerA.setDataSource(getApplicationContext(),
                    Uri.parse(RES_PREFIX + mediaFileA));
            mediaPlayerA.setDataSource(mediaFileA);
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }*/
//        mediaPlayerA.prepareAsync();

        ////\\\\
     /*   mediaPlayerB = new MediaPlayer();
        //Set up MediaPlayer event listeners
        mediaPlayerB.setOnCompletionListener(this);
        mediaPlayerB.setOnErrorListener(this);
        mediaPlayerB.setOnPreparedListener(this);
        mediaPlayerB.setOnBufferingUpdateListener(this);
        mediaPlayerB.setOnSeekCompleteListener(this);
        mediaPlayerB.setOnInfoListener(this);
       // mediaPlayerB.reset();

        //mediaPlayerB.setAudioStreamType(AudioManager.STREAM_MUSIC);
       // mediaPlayerB=MediaPlayer.create(this,R.raw.forestdaytimeambience);
       *//* try {

            mediaPlayerB.setDataSource(mediaFileB);
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }*//*
      //  mediaPlayerB.prepareAsync();
 ///FIRST SOUNDS

        mediaPlayer1_1=new MediaPlayer();
        mediaPlayer1_1.setOnCompletionListener(this);
        mediaPlayer1_1.setOnErrorListener(this);
        mediaPlayer1_1.setOnPreparedListener(this);
        mediaPlayer1_1.setOnBufferingUpdateListener(this);
        mediaPlayer1_1.setOnSeekCompleteListener(this);
        mediaPlayer1_1.setOnInfoListener(this);

        /////\\\\
        mediaPlayer1_2=new MediaPlayer();

        mediaPlayer1_2.setOnCompletionListener(this);
        mediaPlayer1_2.setOnErrorListener(this);
        mediaPlayer1_2.setOnPreparedListener(this);
        mediaPlayer1_2.setOnBufferingUpdateListener(this);
        mediaPlayer1_2.setOnSeekCompleteListener(this);
        mediaPlayer1_2.setOnInfoListener(this);

///SECOND SOUNDS

        mediaPlayer2_1=new MediaPlayer();
        mediaPlayer2_1.setOnCompletionListener(this);
        mediaPlayer2_1.setOnErrorListener(this);
        mediaPlayer2_1.setOnPreparedListener(this);
        mediaPlayer2_1.setOnBufferingUpdateListener(this);
        mediaPlayer2_1.setOnSeekCompleteListener(this);
        mediaPlayer2_1.setOnInfoListener(this);

        /////\\\\
        mediaPlayer2_2=new MediaPlayer();

        mediaPlayer2_2.setOnCompletionListener(this);
        mediaPlayer2_2.setOnErrorListener(this);
        mediaPlayer2_2.setOnPreparedListener(this);
        mediaPlayer2_2.setOnBufferingUpdateListener(this);
        mediaPlayer2_2.setOnSeekCompleteListener(this);
        mediaPlayer2_2.setOnInfoListener(this);*/
///3
/*
       mediaPlayer1=new MediaPlayer();
       mediaPlayer2=new MediaPlayer();
        mediaPlayer3 = new MediaPlayer();
        mediaPlayer4 = new MediaPlayer();
        mediaPlayer5 = new MediaPlayer();
        mediaPlayer6 = new MediaPlayer();
        mediaPlayer7 = new MediaPlayer();
        mediaPlayer8 = new MediaPlayer();
        mediaPlayer9 = new MediaPlayer();
        mediaPlayer10 = new MediaPlayer();
        mediaPlayer11 = new MediaPlayer();
        mediaPlayer12 = new MediaPlayer();
        mediaPlayer13 = new MediaPlayer();
        mediaPlayer14 = new MediaPlayer();
        mediaPlayer15 = new MediaPlayer();
        mediaPlayer16 = new MediaPlayer();
        mediaPlayer17 = new MediaPlayer();
        mediaPlayer18= new MediaPlayer();
        mediaPlayer19= new MediaPlayer();
        mediaPlayer20 = new MediaPlayer();*/











       /* requestAudioFocus();
        removeAudioFocus();*/

    }

    //Handle incoming phone calls
    private void callStateListener() {
        // Get the telephony manager
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Starting listening for PhoneState changes
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    //if at least one call exists or the phone is ringing
                    //pause the MediaPlayer
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        pauseMedia();
                        //substopMedia();
                    case TelephonyManager.CALL_STATE_RINGING:
                        // if (mediaPlayer != null) {
                        pauseMedia();
                        //substopMedia();
                        ongoingCall = true;
                        // }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        // Phone idle. Start playing.
                        // if (mediaPlayer != null) {
                        if (ongoingCall) {
                            ongoingCall = false;
                            // resumeMedia();
                            //resumeMedia();
                            // playMedia(MediaPlayerService.this);
                        }
                        //}
                        break;
                }
            }
        };
        // Register the listener with the telephony manager
        // Listen for changes to the device call state.
        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_CALL_STATE);
    }
   /* private void allSounds()
    {


        MediaPlayerService.mediaPlayer1 = MediaPlayer.create(context, R.raw.dheera);
        MediaPlayerService.mediaPlayer2 = MediaPlayer.create(context, R.raw.dheera);
        MediaPlayerService.mediaPlayer3 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer4 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer5 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer6 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer7 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer8 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer9 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer10 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer11 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer12 = MediaPlayer.create(context, R.raw.locustsound);
        MediaPlayerService.mediaPlayer13 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer14 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer15 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer16 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer17 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer18 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer19 = MediaPlayer.create(context, R.raw.sea);
        MediaPlayerService.mediaPlayer20 = MediaPlayer.create(context, R.raw.sea);




    }*/
  /* public static void substopMedia() {

       if (mediaPlayer1!=null) {
           mediaPlayer1.stop();
           mediaPlayer1.release();
           mediaPlayer1=null;
       }
       if (mediaPlayer2!=null) {
           mediaPlayer2.stop();
           mediaPlayer2.release();
           mediaPlayer2=null;
       }
       if (mediaPlayer3!=null) {
           mediaPlayer3.stop();
           mediaPlayer3.release();
           mediaPlayer3=null;
       }
       if (mediaPlayer4!=null) {
           mediaPlayer4.stop();
           mediaPlayer4.release();
           mediaPlayer4=null;
       }
       if (mediaPlayer5!=null) {
           mediaPlayer5.stop();
           mediaPlayer5.release();
           mediaPlayer5=null;
       }
       if (mediaPlayer6!=null) {
           mediaPlayer6.stop();
           mediaPlayer6.release();
           mediaPlayer6=null;
       }
       if (mediaPlayer7!=null) {
           mediaPlayer7.stop();
           mediaPlayer7.release();
           mediaPlayer7=null;
       }
       if (mediaPlayer8!=null) {
           mediaPlayer8.stop();
           mediaPlayer8.release();
           mediaPlayer8=null;
       }
       if (mediaPlayer9!=null) {
           mediaPlayer9.stop();
           mediaPlayer9.release();
           mediaPlayer9=null;
       }
       if (mediaPlayer10!=null) {
           mediaPlayer10.stop();
           mediaPlayer10.release();
           mediaPlayer10=null;
       }
       if (mediaPlayer11!=null) {
           mediaPlayer11.stop();
           mediaPlayer11.release();
           mediaPlayer11=null;
       }
       if (mediaPlayer12!=null) {
           mediaPlayer12.stop();
           mediaPlayer12.release();
           mediaPlayer12=null;
       }
       if (mediaPlayer13!=null) {
           mediaPlayer13.stop();
           mediaPlayer13.release();
           mediaPlayer13=null;
       }
       if (mediaPlayer14!=null) {
           mediaPlayer14.stop();
           mediaPlayer14.release();
           mediaPlayer14=null;
       }
       if (mediaPlayer15!=null) {
           mediaPlayer15.stop();
           mediaPlayer15.release();
           mediaPlayer15=null;
       }
       if (mediaPlayer16!=null) {
           mediaPlayer16.stop();
           mediaPlayer16.release();
           mediaPlayer16=null;
       }
       if (mediaPlayer17!=null) {
           mediaPlayer17.stop();
           mediaPlayer17.release();
           mediaPlayer17=null;
       }
       if (mediaPlayer18!=null) {
           mediaPlayer18.stop();
           mediaPlayer18.release();
           mediaPlayer18=null;
       }
       if (mediaPlayer19!=null) {
           mediaPlayer19.stop();
           mediaPlayer19.release();
           mediaPlayer19=null;
       }
       if (mediaPlayer20!=null) {
           mediaPlayer20.stop();
           mediaPlayer20.release();
           mediaPlayer20=null;
       }
   }*/

    @Override
    public boolean onUnbind(Intent intent) {
        mSession.release();
        return super.onUnbind(intent);
    }
///Notification media stop

    public class LocalBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }


}
