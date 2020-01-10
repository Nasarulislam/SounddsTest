package relaxing.sounds.sleeping;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import relaxing.sounds.sleeping.Activitys.MainActivity;

import static relaxing.sounds.sleeping.Adapters.TimerListAdapter.dismissSharedPreferences;

public class CustomTimerDialog extends Dialog {

    Context context;
    int hour;
    int minute;
    MainActivity mainActivity;
    NumberPicker minuteTimePicker;
    private final static int TIME_PICKER_INTERVAL = 1;
    public CustomTimerDialog(@NonNull Context context,MainActivity mainActivity,int hour,int minute) {
        super(context);
        this.context=context;
        this.hour=hour;
        this.minute=minute;
        this.mainActivity=mainActivity;

    }

    public CustomTimerDialog(@NonNull Context context) {
        super(context);
    }

    protected CustomTimerDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_timer_activity);

        NumberPicker hourTimePicker=findViewById(R.id.hour);
        hourTimePicker.setMaxValue(12);
        hourTimePicker.setMinValue(0);
        hourTimePicker.setValue(hour);
        // hourTimePicker.setOnValueChangedListener(MainActivity.this);
        hourTimePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int j) {

              //  Toast.makeText(context, j+"change"+i, Toast.LENGTH_SHORT).show();
                Log.d("ifienocn",""+i);
                hour=j;

            }
        });

         minuteTimePicker=findViewById(R.id.minute);
        minuteTimePicker.setMaxValue(59);
        minuteTimePicker.setMinValue(0);
        minuteTimePicker.setValue(minute);
        minuteTimePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int j) {

              //  Toast.makeText(context, j+"change"+i, Toast.LENGTH_SHORT).show();
                Log.d("ifienocn",""+i);
                minute=j;

            }
        });


        Button button=findViewById(R.id.startbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = dismissSharedPreferences.edit();
                editor.putInt("hour",hour);
                editor.putInt("minute",minute);
                editor.commit();

                if (hour>0)
                {
                    //Toast.makeText(context, "if", Toast.LENGTH_SHORT).show();
                    int hur=0;
                    /*if (hour>12){
                        hur=(hour%12)*60;
                    }else {
                        hur=hour*60;
                    }*/
                     hur=hour*60;
                    int hrmin=hur+minute;

                    if (((MainActivity) mainActivity).getCountDownTimer() != null) {
                        ((MainActivity) mainActivity).getCountDownTimer().cancel();
                        ((MainActivity) mainActivity).setCountDownTimer(null);
                    }
                    try {
                        TImerListDialog.stime = hrmin * 60 * 1000;
                      //  Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else {
                 //   Toast.makeText(context, "else", Toast.LENGTH_SHORT).show();
                    if (((MainActivity) mainActivity).getCountDownTimer() != null) {
                        ((MainActivity) mainActivity).getCountDownTimer().cancel();
                        ((MainActivity) mainActivity).setCountDownTimer(null);
                    }
                    try {
                        TImerListDialog.stime = minute * 60 * 1000;
                     //   Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ((MainActivity) mainActivity).relativeTimerStopp.setVisibility(View.GONE);
                ((MainActivity) mainActivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                mainActivity.countdown(TImerListDialog.stime, mainActivity);
                MainActivity.Companion.dismisTimer(context, mainActivity);



                dismiss();
            }
        });

    }

    /*
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field timePickerField = classForid.getField("timePicker");
             minuteTimePicker= (NumberPicker) findViewById(timePickerField.getInt(null));
            Field field = classForid.getField("minute");

            NumberPicker minuteSpinner = (NumberPicker) minuteTimePicker
                    .findViewById(field.getInt(null));
            minuteSpinner.setMinValue(0);
            minuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<>();
           *//* for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }*//*
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minuteSpinner.setDisplayedValues(displayedValues
                    .toArray(new String[displayedValues.size()]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}
