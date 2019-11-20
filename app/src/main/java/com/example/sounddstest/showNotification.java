package com.example.sounddstest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.sounddstest.Activitys.MainActivity;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class showNotification extends BroadcastReceiver {

SharedPreferences sharedPreferences;
    public showNotification() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        sendNotification(context);
      //  Log.d("calendartime","from the class ");
        sharedPreferences = context.getSharedPreferences("prefs.xml",MODE_PRIVATE);
        Log.d("calendartime","from the class  , "+sharedPreferences.getBoolean("walked",false));
        if (!sharedPreferences.getBoolean("walked",false)){
         //   Log.d("calendartime","walked  ");

            if (sharedPreferences.getBoolean("sendNotification",false))
                sendNotification(context);
            else
                sharedPreferences.edit().putBoolean("sendNotification",true).apply();
        }
    }
    private void sendNotification(Context context) {
       /* Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(largeIcon);*/
        final int randomTitle = new Random().nextInt(1); // [0, 60] + 20 => [20, 80]
        final int randomSubText = new Random().nextInt(1);

        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> subText = new ArrayList<>();
        title.add(0,context.getString(R.string.notification_title_one));
       /* title.add(1,context.getString(R.string.notification_title_two));
        title.add(2,context.getString(R.string.notification_title_three));
        title.add(3,context.getString(R.string.notification_title_four));*/
        subText.add(0,context.getString(R.string.notification_subtext_one));
        /*subText.add(1,context.getString(R.string.notification_subtext_two));
        subText.add(2,context.getString(R.string.notification_subtext_three));
        subText.add(3,context.getString(R.string.notification_subtext_four));*/
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "101";

       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);
            //Configure Notification Channel
            notificationChannel.setDescription("Game Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{200});
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title.get(randomTitle))
                .setContentText(subText.get(randomSubText))
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX);

        notificationManager.notify(1, notificationBuilder.build());
    }
}
