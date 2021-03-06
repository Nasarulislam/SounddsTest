package relaxing.sounds.sleeping.Overlay;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import relaxing.sounds.sleeping.Activitys.MainActivity;
import relaxing.sounds.sleeping.OnBoarding.MainActivityOnboarding;
//import com.example.Activitys.R;
import relaxing.sounds.sleeping.R;
import relaxing.sounds.sleeping.Services.MediaPlayerService;
import relaxing.sounds.sleeping.Services.MediaPlayerServiceSecond;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class FloatingWidgetShowService extends Service {

    WindowManager windowManager;
    View floatingView, collapsedView, expandedView;
    WindowManager.LayoutParams params;
    ImageView playPauseImageView;
    //public static TextView exerciseTimeUpTextView;
    TextView exerciseNameTextView;
    Timer serviceTimer;
    SharedPreferences sharedPreferences;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer youTubePlayerMain = null;
    ImageView youtubeImageView;
    YouTubePlayerTracker tracker = new YouTubePlayerTracker();

    boolean warnpremiumTts = false;
    ImageView closeButton;
    ImageView nextImageView;
    RelativeLayout premiumLayout;
    RelativeLayout workoutLayout;
    TextView laterTextView;
    TextView getPremiumTestView;
    String videoId = "YFa-wOEt7AE";
    int min = 0;
    int sec = 0;
    int totalMin = 0;
    int totalSec = 0;
    String countUpTimer = "";
    CountDownTimer countDownTimer;
    int count = 1;
    int premiumCount = 0;
    String imageUrl = "";
    String timerText = null;
    int ttsCount = 3;
    TextToSpeech tts;
    Timer ttsTimer;
    boolean startTts = false;
    boolean timerInPlaypause = false;
    String[] langKeyWords = {"fr", "de", "es", "it", "pt", "id", "tr", "th", "ru", "ja"};
    Locale[] langValues = {Locale.FRENCH, Locale.GERMAN, Locale.US, Locale.ITALIAN, Locale.US, Locale.US, Locale.US, Locale.US, Locale.UK, Locale.JAPANESE};
    private int loop = 0;
    private long duration = 0;
    private String exerciseVal = "";
    // private List<Exercise> exercises = null;
    private String currentExercise = "";
    private int currentExercisePosition = 0;
    private SharedPreferences.OnSharedPreferenceChangeListener listner;

    public FloatingWidgetShowService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        loop = intent.getIntExtra("loop", 0);
        Log.d("intentdatas", "loop : " + loop + " exercise : " + intent.getStringExtra("exercises"));
        imageUrl = intent.getStringExtra("imageUrl");
        timerText = intent.getStringExtra("exercise");
        //   exercises = Exercise.Companion.parseExerciseArrayString(intent.getStringExtra("exercises"));
        currentExercisePosition = intent.getIntExtra("currentExercisePosition", 0);
        // duration = exercises.get(currentExercisePosition).getDuration();
        //  exerciseNameTextView.setText(exercises.get(currentExercisePosition).getTitle());
        if (imageUrl != null && !imageUrl.equals("")) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.pack_image_placeholder)
                    .into(youtubeImageView);
        }
      /*  if (currentExercisePosition>=(exercises.size()-1)){
            nextImageView.setVisibility(View.INVISIBLE);
        }
        else
            nextImageView.setVisibility(View.VISIBLE);
*/
       /* if (sharedPreferences.getBoolean("isPlaying",false)){
            sharedPreferences.edit().putBoolean("isPlaying",false).apply();
            sharedPreferences.edit().putBoolean("isPlayingService",true).apply();
            playPauseImageView.setImageResource(R.drawable.ic_pause_black_24dp);
            getTime();
        }*/


        if (sharedPreferences.getBoolean("purchased", false) || sharedPreferences.getBoolean("monthlySubscribed", false) || sharedPreferences.getBoolean("sixMonthSubscribed", false)) {
            workoutLayout.setVisibility(View.VISIBLE);
            premiumLayout.setVisibility(View.INVISIBLE);
            if (sharedPreferences.getBoolean("isPlaying", false)) {
                sharedPreferences.edit().putBoolean("isPlaying", false).apply();
                sharedPreferences.edit().putBoolean("isPlayingService", true).apply();
                playPauseImageView.setImageResource(R.drawable.ic_pause_black_24dp);
                getTime();
            }
        } else {
            //  premiumLayout.setVisibility(View.VISIBLE);
            // workoutLayout.setVisibility(View.INVISIBLE);
            if (countDownTimer != null)
                countDownTimer.cancel();

            countdown();
        }
        //getTime();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
        Log.d("calendartime", "inside ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

        } else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_TOAST,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }


        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        try {
            windowManager.addView(floatingView, params);
        } catch (NullPointerException npe) {
            Log.d("calendartime", "the error is : " + npe.getMessage());
            npe.printStackTrace();
        }


        sharedPreferences = getSharedPreferences("prefs.xml", MODE_PRIVATE);
        expandedView = floatingView.findViewById(R.id.cardViewExpand);
        playPauseImageView = floatingView.findViewById(R.id.playPauseImageView);
        //  exerciseTimeUpTextView = floatingView.findViewById(R.id.exercise_time_textView);
        youTubePlayerView = floatingView.findViewById(R.id.youtube_player_view);
        //lifecycle.addObserver(youTubePlayerView);
        closeButton = floatingView.findViewById(R.id.closeButton);
        nextImageView = floatingView.findViewById(R.id.nextImageView);
       // exerciseNameTextView = floatingView.findViewById(R.id.currentExerciseTextView);
        sharedPreferences.edit().putBoolean("isPlayingService", false).apply();
        premiumLayout = floatingView.findViewById(R.id.premiumLayout);
        workoutLayout = floatingView.findViewById(R.id.workoutLayout);
        laterTextView = floatingView.findViewById(R.id.laterTextView);
        getPremiumTestView = floatingView.findViewById(R.id.getPremiumTestView);
        youtubeImageView = floatingView.findViewById(R.id.youtube_imageView);
        premiumCount = 0;
        countdown();
        getTime();
        listner = (prefs, key) -> {
            //implementation goes here

            if (key.equals("changeVisibility")) {
                Log.d("changeVisibility", "here" + " , " + sharedPreferences.getBoolean("changeVisibility", true));
                changeVisibility();
            }
        };

        sharedPreferences.registerOnSharedPreferenceChangeListener(listner);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (Arrays.asList(langKeyWords).contains(Locale.getDefault().getLanguage())) {
                    tts.setLanguage(langValues[Arrays.asList(langKeyWords).indexOf(Locale.getDefault().getLanguage())]);
                } else
                    tts.setLanguage(Locale.UK);
            }
        });
        getPremiumTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent premiumIntent = new Intent(getApplicationContext(), MainActivityOnboarding.class);
                premiumIntent.putExtra("fromOverlayPage", "fromOverlayPage");
                premiumIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(premiumIntent);
                stopSelf();
                //  GetPremium getPremium = new GetPremium();
            }
        });

        laterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hghvh", "click " + warnpremiumTts);
                if (startTts) {
                    Log.d("hghvh", "done");
                    startTts = false;
                    tts.stop();
                    ttsTimer.cancel();
                    ttsTimer = null;
                    ttsCount = 3;
                    timerInPlaypause = false;
                }
                if (countDownTimer != null)
                    countDownTimer.cancel();
                if (warnpremiumTts) {
                    Log.d("hghvh", "haha");
                    warnpremiumTts = false;
                    tts.stop();
                }
                if (timerInPlaypause) {
                    serviceTimer.cancel();
                    serviceTimer = null;
                    timerInPlaypause = false;
                }
                Intent premiumIntent = new Intent(getApplicationContext(), MainActivity.class);
                premiumIntent.putExtra("exercise", timerText);
                premiumIntent.putExtra("fromwidget", true);
                //premiumIntent.putExtra("imageUrl",imageUrl);
                // premiumIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                premiumIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                sharedPreferences.edit().putBoolean("fromOverlayPage",true).apply();
                startActivity(premiumIntent);
                Log.d("carddestroy", "close : " + sharedPreferences.getBoolean("isPlayingService", false));

                stopSelf();

               /* sharedPreferences.edit().putBoolean("cardIsShown",true).apply();
                sharedPreferences.edit().putBoolean("isPlayingService",true).apply();
                Intent premiumIntent = new Intent(getApplicationContext(), MainActivity.class);
               // premiumIntent.putExtra("exercises",Exercise.Companion.toExerciseArrayString(exercises));
                premiumIntent.putExtra("fromwidget",true);
                //premiumIntent.putExtra("imageUrl",imageUrl);
                premiumIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                sharedPreferences.edit().putBoolean("fromOverlayPage",true).apply();
                startActivity(premiumIntent);
                stopSelf();*/

            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startTts) {
                    startTts = false;
                    tts.stop();
                    ttsTimer.cancel();
                    ttsTimer = null;
                    ttsCount = 3;
                    timerInPlaypause = false;
                }
                if (countDownTimer != null)
                    countDownTimer.cancel();
                if (warnpremiumTts) {
                    Log.d("hghvh", "timer not null");
                    warnpremiumTts = false;
                    tts.stop();

                }
                if (timerInPlaypause) {
                    serviceTimer.cancel();
                    serviceTimer = null;
                    timerInPlaypause = false;
                }
                Intent premiumIntent = new Intent(getApplicationContext(), MainActivity.class);
                premiumIntent.putExtra("exercise", timerText);
                premiumIntent.putExtra("fromwidget", true);
                //premiumIntent.putExtra("imageUrl",imageUrl);
                // premiumIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                premiumIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                sharedPreferences.edit().putBoolean("fromOverlayPage",true).apply();
                startActivity(premiumIntent);

                MainActivity.Companion.setPlayingBolean(false);
                MediaPlayerService.PlayingMedia();
                Log.d("hkjjkbjkj", "ok" + MainActivity.Companion.getPlayingBolean());
                if (MainActivity.Companion.getPlayingBolean()) {
                    Log.d("hkjjkbjkj", "yes" + MainActivity.Companion.getPlayingBolean());
                    //MainActivity.Companion.getPlayRelativ().setVisibility(View.GONE);
                    //MainActivity.Companion.getPuaseRelativ().setVisibility(View.VISIBLE);

                    // MainActivity.Companion.getPlayRelativ().setVisibility(View.VISIBLE);
                    //MainActivity.Companion.getPuaseRelativ().setVisibility(View.GONE);

                    //((MainActivity)MainActivity.class).PuaseRelativee.setVisibility(View.VISIBLE);
                } else {
                    Log.d("hkjjkbjkj", "no" + MainActivity.Companion.getPlayingBolean());
                    //  MainActivity.Companion.getPlayRelativ().setVisibility(View.VISIBLE);
                    // MainActivity.Companion.getPuaseRelativ().setVisibility(View.GONE);

                    MainActivity.Companion.getPlayRelativ().setVisibility(View.GONE);
                    MainActivity.Companion.getPuaseRelativ().setVisibility(View.VISIBLE);
                }

                Log.d("carddestroy", "close : " + sharedPreferences.getBoolean("isPlayingService", false));

                stopSelf();
            }
        });


        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayerMain = youTubePlayer;

            }


            @Override
            public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState state) {

                super.onStateChange(youTubePlayer, state);
            }
        });


        playPauseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("isplayingservice", sharedPreferences.getBoolean("isPlayingService", false) + "");
                if (sharedPreferences.getBoolean("isPlayingService", false)) {
                    sharedPreferences.edit().putBoolean("isPlayingService", false).apply();
                    playPauseImageView.setImageResource(R.drawable.ic_play_arrow_black);

                    MediaPlayerService.pauseMedia(getApplicationContext());

                    //\\MainActivity.PuaseRelative.setVisibility(View.GONE);
                    //\\MainActivity.PlayRelative.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), MediaPlayerServiceSecond.class);
                    intent.setAction(MediaPlayerServiceSecond.ACTION_PREVIOUS);
                    startService(intent);

                    if (timerInPlaypause) {
                        serviceTimer.cancel();
                        serviceTimer = null;
                        timerInPlaypause = false;
                    }
                   /* if (countDownTimer!=null)
                        countDownTimer.cancel();*/
                    if (startTts) {
                        startTts = false;
                        tts.stop();
                        ttsTimer.cancel();
                        ttsTimer = null;
                        ttsCount = 3;
                    }
                   /* if (warnpremiumTts){
                        Log.d("hghvh","timer not null");
                        warnpremiumTts=false;
                        tts.stop();
                    }*/
                } else {

                    sharedPreferences.edit().putBoolean("isPlayingService", true).apply();
                    playPauseImageView.setImageResource(R.drawable.ic_pause_black_24dp);
                    getTime();
                    // announceNext();
                    MediaPlayerService.resumeMedia(getApplicationContext());
                    //\\MainActivity.PuaseRelative.setVisibility(View.VISIBLE);
                    //\\MainActivity.PlayRelative.setVisibility(View.GONE);

                    Intent intent = new Intent(getApplicationContext(), MediaPlayerServiceSecond.class);
                    intent.setAction(MediaPlayerServiceSecond.ACTION_NEXT);
                    startService(intent);
                }

            }
        });

        nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // MediaPlayerService.stopMedia();
                if (startTts) {
                    startTts = false;
                    tts.stop();
                    ttsTimer.cancel();
                    ttsTimer = null;
                    ttsCount = 3;
                    timerInPlaypause = false;
                }
                if (countDownTimer != null)
                    countDownTimer.cancel();
                if (warnpremiumTts) {
                    Log.d("hghvh", "timer not null");
                    warnpremiumTts = false;
                    tts.stop();

                }
                stopSelf();

            }
        });

       /* exerciseTimeUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getBoolean("changeTime",false)){
                    sharedPreferences.edit().putBoolean("changeTime",false).apply();
                    exerciseTimeUpTextView.setText(countUpTimer);
                }
                else {
                    sharedPreferences.edit().putBoolean("changeTime",true).apply();
                    exerciseTimeUpTextView.setText(MainActivity.timeString);
                }
            }
        });*/

        //  exerciseTimeUpTextView.setText(sharedPreferences.getString("countUpTimer","0:00/0 min"));


        floatingView.findViewById(R.id.cardViewExpand).setOnTouchListener(new View.OnTouchListener() {
            int X_Axis, Y_Axis;
            float TouchX, TouchY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        X_Axis = params.x;
                        Y_Axis = params.y;
                        TouchX = motionEvent.getRawX();
                        TouchY = motionEvent.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:

                        //   collapsedView.setVisibility(View.GONE);
                        //  expandedView.setVisibility(View.VISIBLE);
                        return true;

                    case MotionEvent.ACTION_MOVE:

                        params.x = X_Axis + (int) (motionEvent.getRawX() - TouchX);
                        params.y = Y_Axis + (int) (motionEvent.getRawY() - TouchY);
                        windowManager.updateViewLayout(floatingView, params);
                        return true;
                }
                return false;
            }


        });

        MediaPlayerService.PlayingMedia();
        if (MainActivity.Companion.getPlayingBolean()) {
            playPauseImageView.setImageResource(R.drawable.ic_pause_black_24dp);
            sharedPreferences.edit().putBoolean("isPlayingService", true).apply();
        } else {
            playPauseImageView.setImageResource(R.drawable.ic_play_arrow_black);
            sharedPreferences.edit().putBoolean("isPlayingService", false).apply();
        }
    }

    public void getTime() {
        count = sharedPreferences.getInt("timeCount", 1);
        totalMin = (int) duration / 60;
        totalSec = (int) duration % 60;
        min = sharedPreferences.getInt("currentMinUp", 0);
        sec = sharedPreferences.getInt("currentSecUp", 0);


        serviceTimer = new Timer();

        timerInPlaypause = true;
        serviceTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Log.d("exercisevaltimeser", "time : " + min + ":" + sec + " / " + totalMin + " min , count : " + sharedPreferences.getInt("timeCount", 1) + "duration : " + duration);
               /* if (totalSec==0)
                    countUpTimer = min+":"+sec+" / "+totalMin+" min";
                else
                    countUpTimer = min+":"+sec+" / "+totalMin+"m "+totalSec+"s";
                sharedPreferences.edit().putString("countUpTimer",countUpTimer).apply();
                exerciseTimeUpTextView.setText(countUpTimer);*/

                if (totalSec == 0) {
                    if (sec < 10)
                        countUpTimer = min + ":0" + sec + " / " + totalMin + " min";
                    else
                        countUpTimer = min + ":" + sec + " / " + totalMin + " min";
                } else {
                    if (sec < 10)
                        countUpTimer = min + ":0" + sec + " / " + totalMin + "m " + totalSec + "s";
                    else
                        countUpTimer = min + ":" + sec + " / " + totalMin + "m " + totalSec + "s";
                }
              /*  if ((60-sec)<10)
                  //  countDownTimer = (totalMin-min-1)+":0"+(60-sec)+" min";
                else
                    countDownTimer = (totalMin-min-1)+":"+(60-sec)+" min";
                sharedPreferences.edit().putString("countUpTimer",countUpTimer).apply();
                if (sharedPreferences.getBoolean("changeTime",false)){
                    exerciseTimeUpTextView.setText(countUpTimer);
                }
                else {
                    exerciseTimeUpTextView.setText(countDownTimer);
                }*/

                sec++;
                // premiumCount++;
                if (premiumCount >= 5)
                    if (sharedPreferences.getBoolean("purchased", false) || sharedPreferences.getBoolean("monthlySubscribed", false) || sharedPreferences.getBoolean("sixMonthSubscribed", false)) {
                        workoutLayout.setVisibility(View.VISIBLE);
                        premiumLayout.setVisibility(View.INVISIBLE);
                        //premiumCount=0;
                    } else {
                        //premiumCount=0;
                        // youTubePlayerView.release();
                        Log.d("hvygvh", "okok");
                        warnpremiumTts = true;
                        // tts.speak(getResources().getString(R.string.workout_has_been_paused)+" "+getResources().getString(R.string.wish_workouts_kept_playing_when_you_closed_the_app)+" "+getResources().getString(R.string.get_background_play_with_premium),TextToSpeech.QUEUE_ADD,null,"workout_paused");
                        warnpremiumTts = true;
                        if (serviceTimer != null)
                            serviceTimer.cancel();
                        serviceTimer = null;
                        timerInPlaypause = false;
                        boolean change = sharedPreferences.getBoolean("changeVisibility", true);
                        //   Log.d("bottomsheetname","name : "+exercises.get(position).getTitle()+" , position : "+position+" , " +sharedPreferences.getBoolean("bottomSheetChange",true));
                        sharedPreferences.edit().putBoolean("changeVisibility", !change).apply();
                    }
                sharedPreferences.edit().putInt("currentSecUp", sec).apply();
                if (sec >= 60) {
                    sec = 0;
                    min += 1;
                    sharedPreferences.edit().putInt("currentMinUp", min).apply();
                }
                if (sharedPreferences.getInt("timeCount", 1) >= duration) {
                    if (serviceTimer != null)
                        serviceTimer.cancel();
                    //   Log.d("exercisevaltime","currentExercisePosition : "+sharedPreferences.getInt("currentExercisePosition",0)+" size : "+exercises.size());

                }
                count++;
                sharedPreferences.edit().putInt("timeCount", count).apply();

            }
        }, 0, 1000);

    }

    public void changeVisibility() {
        premiumLayout.setVisibility(View.VISIBLE);
        workoutLayout.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onDestroy() {
        //  Log.d("carddestroy","yes "+sharedPreferences.getBoolean("isPlayingService",false));
        if (startTts) {
            Log.d("hghvh", "timer not dfsf");
            startTts = false;
            tts.stop();
            ttsTimer.cancel();
            ttsTimer = null;
            ttsCount = 3;
            timerInPlaypause = false;
        }
        if (countDownTimer != null)
            countDownTimer.cancel();
        if (warnpremiumTts) {
            Log.d("hghvh", "timer not null");
            warnpremiumTts = false;
            tts.stop();

        }
        if (serviceTimer != null) {
            Log.d("carddestroy", "timer not null");
            serviceTimer.cancel();
            serviceTimer = null;
        }
        youTubePlayerView.release();
        Log.d("carddestroy", "is shown : " + sharedPreferences.getBoolean("isPlayingService", false));
        sharedPreferences.edit().putBoolean("cardIsShown", true).apply();
        //   Log.d("carddestroy","is shown : "+sharedPreferences.getBoolean("isPlayingService",false));
        super.onDestroy();
        if (floatingView != null) windowManager.removeView(floatingView);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void registerBroadcastReceiver() {

        try {
            final IntentFilter theFilter = new IntentFilter();
            /** System Defined Broadcast */
            theFilter.addAction(Intent.ACTION_SCREEN_ON);
            theFilter.addAction(Intent.ACTION_SCREEN_OFF);
            theFilter.addAction(Intent.ACTION_USER_PRESENT);

            BroadcastReceiver screenOnOffReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String strAction = intent.getAction();

                    KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                    if (strAction.equals(Intent.ACTION_USER_PRESENT) || strAction.equals(Intent.ACTION_SCREEN_OFF) || strAction.equals(Intent.ACTION_SCREEN_ON))
                        if (myKM.inKeyguardRestrictedInputMode()) {
                            //System.out.println("Screen off " + "LOCKED");
                            Log.d("screenonoroff", "locked ");
                        } else {
                            Log.d("screenonoroff", "unlocked ");
                        }

                }
            };

            getApplicationContext().registerReceiver(screenOnOffReceiver, theFilter);
        } catch (Exception e) {
            Log.d("screenonoroff", "error " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void countdown() {
        countDownTimer = new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Log.d("bbbbbb", "" + stime);
                // updateTimer((int) millisUntilFinished / 1000);
                premiumCount++;
            }

            @Override
            public void onFinish() {
                if (sharedPreferences.getBoolean("purchased", false) || sharedPreferences.getBoolean("monthlySubscribed", false) || sharedPreferences.getBoolean("sixMonthSubscribed", false)) {
                    workoutLayout.setVisibility(View.VISIBLE);
                    premiumLayout.setVisibility(View.INVISIBLE);
                    premiumCount = 0;
                } else {
                    Log.d("bbbojbbb", "kookok");
//                    tts.speak(getResources().getString(R.string.workout_has_been_paused) + " " + getResources().getString(R.string.wish_workouts_kept_playing_when_you_closed_the_app) + " " + getResources().getString(R.string.get_background_play_with_premium), TextToSpeech.QUEUE_ADD, null, "workout_paused");
                    tts.speak(  getResources().getString(R.string.wish_workouts_kept_playing_when_you_closed_the_app) + " " + getResources().getString(R.string.get_background_play_with_premium), TextToSpeech.QUEUE_ADD, null, "workout_paused");

                    warnpremiumTts = true;
                    //   serviceTimer.cancel();
                    //  serviceTimer = null;
                    timerInPlaypause = false;
                    boolean change = sharedPreferences.getBoolean("changeVisibility", true);
                    //   Log.d("bottomsheetname","name : "+exercises.get(position).getTitle()+" , position : "+position+" , " +sharedPreferences.getBoolean("bottomSheetChange",true));
                    sharedPreferences.edit().putBoolean("changeVisibility", !change).apply();
                }
            }

          /*  @Override
            public void onFinish() {
//UltraPagerAdapter.countDownextview.setText("0");

                // textView.setText(stime);

               // buttonContinue.setText("Continue");
              //  MediaPlayerService.pauseMedia();
            }*/

        }.start();
    }
}
