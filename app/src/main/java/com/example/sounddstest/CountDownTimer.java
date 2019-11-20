package com.example.sounddstest;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.Services.MediaPlayerService;

import static com.example.sounddstest.TImerListDialog.stime;

public class CountDownTimer extends AppCompatActivity {
     android.os.CountDownTimer countDownTimer;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void countdown(MainActivity activity) {
        countDownTimer = new android.os.CountDownTimer((stime * 60) * 1000 + 59000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("bbbbbb", "" + stime);

                updateTimer( (int) millisUntilFinished / 1000,activity);

            }


            @Override
            public void onFinish() {


                // textView.setText(stime);
                MediaPlayerService.pauseMedia();
               // buttonContinue.setText("Continue");

            }

        }.start();
    }
    private void updateTimer(int secondsLeft,MainActivity activity) {

        /*val minute = secondsLeft
         val minuteString = Integer.toString(minute)
         Log.d("minutemmm", "$minute--$secondsLeft")

         timerTextView.setText(minuteString)*/
        /*   val linearLayout = window.decorView.findViewWithTag(ultraViewPager.getCurrentItem())
           val textView = linearLayout.findViewById(R.id.pager_textview)
           textView.setText(minuteString)*/
        int hour = secondsLeft / 3600;
        int minute = (secondsLeft - hour * 3600) / 60;
        int seconds = secondsLeft - (minute * 60 + hour * 3600);
        int dur = minute * 62000;
        int send = seconds * 1050;
        //String durString=Integer.toString(dur);
        String secondString = Integer.toString(seconds);
        String minuteString = Integer.toString(minute);
        String hoursString = Integer.toString(hour);
        if (seconds <= 9) {
            secondString = "0$secondString";
        }
        if (minute <= 9) {
            minuteString = "0$minuteString";
        }
        if (hour <= 9) {
            hoursString = "0$hoursString";
        }

        //timerTextView.setText(Integer.toString(hour)+":"+Integer.toString(minute)+":"+secondString);
        // timerTextView.text = "$minuteString:$secondString"

        // timerTextVieww.text="$minuteString:$secondString"
        //val textView=fin


        ((MainActivity)activity).timerTextVieww.setText(minuteString+":"+secondString);
        MainActivity.timerView.setText(minuteString+":"+secondString);
        // timerTextVieww.text="$minuteString:$secondString"

        // timerTextVieww= getActivity(conte)findViewById(R.id.timerTextView) as TextView
        // timerTextVieww.setText("$minuteString:$secondString")
        // timertextView.setText("$minuteString:$secondString")
        // context=this
        //activity.startActivity(getIntent())
        // (this as MainActivity).timerTextVieww.text = "$minuteString:$secondString"
      /* var textView=activity.findViewById(R.id.timerTextView)as TextView
       Log.d("dnfi","gone")
       textView.setText("$minuteString:$secondString")*/
      //\\\\\  MainActivity.Companion.timeString ="$minuteString:$secondString"
        // timefun( Companion.timeString)
        // fl.exerciseTimeUpTextView.text="$minuteString:$secondString"

    }
}
