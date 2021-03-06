package relaxing.sounds.sleeping.Activitys;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import relaxing.sounds.sleeping.OnBoarding.GetPremium;
import relaxing.sounds.sleeping.OnBoarding.MainActivityOnboarding;

import relaxing.sounds.sleeping.Services.MediaPlayerService;
import relaxing.sounds.sleeping.showNotification;

import java.util.Calendar;

public class SplashScreen extends AppCompatActivity {

    // internal var sharedPref: SharedPreferences? = null
    final Handler handler = new Handler();
    SharedPreferences sharedPreferences;
    SharedPreferences shared;
    GetPremium getPremium;
    private Boolean onBoardLoaded = false;
    private Boolean openPurchase = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("App_settings", Context.MODE_PRIVATE);
        shared = getSharedPreferences("prefs.xml", Context.MODE_PRIVATE);

        //>> checking app is purchsed or not
        try {
            if (sharedPreferences.getBoolean("purchased", false) || sharedPreferences.getBoolean("monthlySubscribed", false) || sharedPreferences.getBoolean("sixMonthSubscribed", false)) {
                openPurchase = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //>> Remove List Data
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
           /* editor.remove("RAIN_SOUNDS");
            editor.remove("RELAXING_SOUNDS");
            editor.remove("LIST_SOUNDS");
            editor.remove("NATURE_SOUNDS");
            editor.remove("WATER_SOUNDS");
            editor.remove("position");
            editor.commit();*/
           // MediaPlayerService.hashmapget(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        onBoardLoaded = sharedPreferences.getBoolean("onBoard", false);
        try {
            if (!onBoardLoaded && openPurchase) {
                sharedPreferences.edit().putBoolean("onBoard", true).apply();
                sharedPreferences.edit().putBoolean("LoadDataOnBoard", true).apply();
                Intent intent = new Intent(this, MainActivityOnboarding.class);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        finish();
                    }
                }, 1000);

            } else {
                //sharedPref.edit().putBoolean("onBoardLoaded", true).apply()
                Intent i = new Intent(this, MainActivity.class);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, 20);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        Log.d("calendartime", "morning " + calendar.getTimeInMillis() + " , " + calendar.getTime());

                        Intent notifyIntent = new Intent(getApplicationContext(), showNotification.class);
                        notifyIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager dawnalarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        dawnalarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                        startActivity(i);
                        finish();
                    }
                }, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
