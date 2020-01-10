package relaxing.sounds.sleeping.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import relaxing.sounds.sleeping.Activitys.MainActivity;
//import com.example.Activitys.R;

import relaxing.sounds.sleeping.CustomTimePickerDialog;
import relaxing.sounds.sleeping.CustomTimerDialog;
import relaxing.sounds.sleeping.RangeTimePickerDialog;
import relaxing.sounds.sleeping.TImerListDialog;
import relaxing.sounds.sleeping.R;

import java.util.ArrayList;
import java.util.Calendar;

public class TimerListAdapter extends BaseAdapter {

    public static SharedPreferences dismissSharedPreferences;
    ArrayList<String> timerList = new ArrayList<>();
    Dialog context;
    int selectedPosition = 0;
    AlertDialog dialog;
    Dialog activity;
    TImerListDialog mContextt;
    AlertDialog alertDialog = null;
    MainActivity mactivity;
    Context mcontext;
    final Calendar myCalender = Calendar.getInstance();
    int hour,minute;
    public TimerListAdapter(ArrayList<String> timerList, Dialog cotext, TImerListDialog mContextt, MainActivity mactivity) {
        this.timerList = timerList;
        this.mContextt = mContextt;
        this.context = cotext;
        this.activity = activity;
        this.mactivity = mactivity;
    }

    @Override
    public int getCount() {
        return timerList.size();
    }

    @Override
    public Object getItem(int i) {
        return timerList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();
        View view = convertView;
        final Context mContext = parent.getContext();
        MainActivity CmainActivity = new MainActivity();
        dismissSharedPreferences = mContext.getSharedPreferences("App_settings", Context.MODE_PRIVATE);
        // dialog = (AlertDialog)getDialog();
        try {

            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.timer_row_item, parent, false);
            //   view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customview_row_item, parent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        context=new AlertDialog.Builder(mContext).create();
        // holder.textView=(TextView)view.findViewById(R.id.textViewTime);
        holder.radioButton = (RadioButton) view.findViewById(R.id.radioButton);
        //  holder.textView.setText(timerList.get(i));
        holder.radioButton.setText(timerList.get(i));

        int pos = dismissSharedPreferences.getInt("position", 0);

        Log.d("poooosi", "" + pos);
        //  holder.radioButton.setChecked(i==selectedPosition);
        holder.radioButton.setTag(i);
        if (i == pos) {
            holder.radioButton.setChecked(true);
            //holder.radioButton.setChecked(i == selectedPosition);
            holder.radioButton.setTag(i);
        }
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    selectedPosition = (Integer) view.getTag();
                    String selectString = timerList.get(i);
                    Log.d("selectString", selectedPosition + "" + selectString);
                    if (selectString.equals("No Timer")) {
                        if (((MainActivity) mactivity).getCountDownTimer() != null) {
                            ((MainActivity) mactivity).getCountDownTimer().cancel();
                            ((MainActivity) mactivity).setCountDownTimer(null);
                        }

                        //stime=9;
                        // MainActivity.Companion.countdown(stime);
                        // MainActivity.relativeTimerStop.setVisibility(View.VISIBLE);
                        ((MainActivity) mactivity).relativeTimerStopp.setVisibility(View.VISIBLE);
                        // MainActivity.relativeTimerStart.setVisibility(View.GONE);
                        ((MainActivity) mactivity).relativeTimerStartt.setVisibility(View.GONE);
                        dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                        int pos = dismissSharedPreferences.getInt("position", 0);

                        Log.d("poooosi", "" + pos);
                        // context.dismiss();
                       /* Dialog dialog=new Dialog(mContext);
                        dialog.dismiss();*/
                       /* TImerListDialog tImerListDialog=new TImerListDialog(mContext);
                        tImerListDialog.dismiss();*/
                    }
                    if (selectString.equals("Custom Duration")) {
                        hour = dismissSharedPreferences.getInt("hour", 0);
                         minute = dismissSharedPreferences.getInt("minute", 0);
                    /*    // ShowTimePicker(view);
                      *//*  RangeTimePickerDialog tpd = new RangeTimePickerDialog(mactivity, new RangeTimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            }
                        }, hour, minute, true);
                        tpd.setMin(11, 20);
                        tpd.setMin(14, 20);
                        tpd.show();*//*

                        //int hour = myCalender.get(Calendar.HOUR_OF_DAY);
                        //int minute = myCalender.get(Calendar.MINUTE);


                        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                     //   TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (view.isShown()) {
                                   // ShowTimePicker(view);
                                    hideAmPmLayout(view);
                                    Log.d("ugdvuin",""+hourOfDay);
                                    */
                        /*if(hourOfDay>12) {
                                        hour=hourOfDay-12;
                                        Log.d("ugdvuin","1"+hourOfDay);
                                       updateDialogTitle(view,hourOfDay-12);
                                    }else {

                                        Log.d("ugdvuin","2"+hourOfDay);
                                        myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        myCalender.set(Calendar.MINUTE, minute);
                                        //  myCalender.set(Calendar.AM_PM,0,0);
                                    }*//*

                                 //   Toast.makeText(mactivity, hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
                                    if (hourOfDay>0)
                                    {  int hur=0;
                                        if (hourOfDay>12){
                                            hur=(hourOfDay%12)*60;
                                        }else {
                                             hur=hourOfDay*60;
                                        }
                                        // hur=hourOfDay*60;
                                        int hrmin=hur+minute;

                                        if (((MainActivity) mactivity).getCountDownTimer() != null) {
                                            ((MainActivity) mactivity).getCountDownTimer().cancel();
                                            ((MainActivity) mactivity).setCountDownTimer(null);
                                        }
                                        try {
                                            TImerListDialog.stime = hrmin * 60 * 1000;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else {

                                        if (((MainActivity) mactivity).getCountDownTimer() != null) {
                                            ((MainActivity) mactivity).getCountDownTimer().cancel();
                                            ((MainActivity) mactivity).setCountDownTimer(null);
                                        }
                                        try {
                                            TImerListDialog.stime = minute * 60 * 1000;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    SharedPreferences.Editor editor = dismissSharedPreferences.edit();
                                    editor.putInt("hour",hourOfDay);
                                    editor.putInt("minute",minute);
                                    editor.commit();

                                    ((MainActivity) mactivity).relativeTimerStopp.setVisibility(View.GONE);
                                    ((MainActivity) mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                                    mactivity.countdown(TImerListDialog.stime, CmainActivity);
                                    MainActivity.Companion.dismisTimer(mContext, mactivity);
                                }
                            }



                        };



                        TimePickerDialog timePickerDialog = new TimePickerDialog(mactivity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute,false );
                        timePickerDialog.setTitle("Custom Timer Duration");
                        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        timePickerDialog.show();
                       */
                        /* CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(mactivity, myTimeListener, hour, minute,true );
                        timePickerDialog.setTitle("Custom Timer Duration");
                        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        timePickerDialog.show();
*/
                        /*
                        *//*RangeTimePickerDialog customTimePickerDialog=new RangeTimePickerDialog(mactivity, myTimeListener, hour, minute,true );
                        //customTimePickerDialog.setCancelable(false);
                        //customTimePickerDialog.setMin(11,20);
                        //customTimePickerDialog.setMin(14,20);
                        customTimePickerDialog.show();*/

                     //   CustomTimeDialog
                        CustomTimerDialog customTimerDialog=new CustomTimerDialog(mContext,mactivity,hour,minute);
                        customTimerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        customTimerDialog.show();
                        dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();

                        TImerListDialog tImerListDialog = new TImerListDialog(mContext, mactivity);
                        tImerListDialog.dismiss();

                    }
                    if (selectString.equals("5 minutes")) {
                        if (((MainActivity) mactivity).getCountDownTimer() != null) {
                            ((MainActivity) mactivity).getCountDownTimer().cancel();
                            ((MainActivity) mactivity).setCountDownTimer(null);
                        }


                       /* if (CmainActivity.getCountDownTimer()!=null){
                            CmainActivity.getCountDownTimer().cancel();
                            Log.d("jvkjvn","jhfjh");
                        }*/
                        dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                        //MainActivity.relativeTimerStop.setVisibility(View.GONE);
                        ((MainActivity) mactivity).relativeTimerStopp.setVisibility(View.GONE);
                        //MainActivity.relativeTimerStart.setVisibility(View.VISIBLE);
                        ((MainActivity) mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                        TImerListDialog.stime = 5 * 60 * 1000;
                        //MainActivity.Companion.countdown(stime);
                        //  CmainActivity.countdown(stime,CmainActivity);
                        mactivity.countdown(TImerListDialog.stime, CmainActivity);


    //                    context.dismiss();
    //                    context.dismiss();
                        MainActivity.Companion.dismisTimer(mContext, mactivity);
                        TImerListDialog tImerListDialog = new TImerListDialog(mContext, mactivity);
                        tImerListDialog.dismiss();

                        // mContext.get
                    }
                    if (selectString.equals("10 minutes")) {
                        if (((MainActivity) mactivity).getCountDownTimer() != null) {
                            ((MainActivity) mactivity).getCountDownTimer().cancel();
                            ((MainActivity) mactivity).setCountDownTimer(null);
                        }
                        dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                        ((MainActivity) mactivity).relativeTimerStopp.setVisibility(View.GONE);
                        ((MainActivity) mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);

                        TImerListDialog.stime = 10 * 60 * 1000;

                        // CmainActivity.countdown(stime,CmainActivity);
                        mactivity.countdown(TImerListDialog.stime, CmainActivity);
                        //MainActivity.Companion.countdown(stime);
                        TImerListDialog tImerListDialog = new TImerListDialog(mContext, mactivity);
                        tImerListDialog.dismiss();

                        tImerListDialog.closeDialog();
                    }
                    if (selectString.equals("15 minutes")) {
                        if (((MainActivity) mactivity).getCountDownTimer() != null) {
                            ((MainActivity) mactivity).getCountDownTimer().cancel();
                            ((MainActivity) mactivity).setCountDownTimer(null);
                        }
                        dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                        ((MainActivity) mactivity).relativeTimerStopp.setVisibility(View.GONE);
                        ((MainActivity) mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                        TImerListDialog.stime = 15 * 60 * 1000;
                        // MainActivity.Companion.countdown(stime);
                        // CmainActivity.countdown(stime,CmainActivity);
                        mactivity.countdown(TImerListDialog.stime, CmainActivity);
                    }
                    if (selectString.equals("30 minutes")) {
                        if (((MainActivity) mactivity).getCountDownTimer() != null) {
                            ((MainActivity) mactivity).getCountDownTimer().cancel();
                            ((MainActivity) mactivity).setCountDownTimer(null);
                        }
                        dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                        ((MainActivity) mactivity).relativeTimerStopp.setVisibility(View.GONE);
                        ((MainActivity) mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                        TImerListDialog.stime = 30 * 60 * 1000;
                        //MainActivity.Companion.countdown(stime);
                        //CmainActivity.countdown(stime,CmainActivity);
                        mactivity.countdown(TImerListDialog.stime, CmainActivity);
                    }
                    if (selectString.equals("45 minutes")) {
                        if (((MainActivity) mactivity).getCountDownTimer() != null) {
                            ((MainActivity) mactivity).getCountDownTimer().cancel();
                            ((MainActivity) mactivity).setCountDownTimer(null);
                        }
                        dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                        ((MainActivity) mactivity).relativeTimerStopp.setVisibility(View.GONE);
                        ((MainActivity) mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                        TImerListDialog.stime = 45 * 60 * 1000;
                        // MainActivity.Companion.countdown(stime);
                        //CmainActivity.countdown(stime,CmainActivity);
                        mactivity.countdown(TImerListDialog.stime, CmainActivity);
                    }
                    if (selectString.equals("1 hour")) {
                        if (((MainActivity) mactivity).getCountDownTimer() != null) {
                            ((MainActivity) mactivity).getCountDownTimer().cancel();
                            ((MainActivity) mactivity).setCountDownTimer(null);
                        }
                        dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                        ((MainActivity) mactivity).relativeTimerStopp.setVisibility(View.GONE);
                        ((MainActivity) mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                        TImerListDialog.stime = 60 * 60 * 1000;
                        // MainActivity.Companion.countdown(stime);
                        // CmainActivity.countdown(stime,CmainActivity);
                        mactivity.countdown(TImerListDialog.stime, CmainActivity);
                    }
                    if (selectString.equals("3 hour")) {
                        if (((MainActivity) mactivity).getCountDownTimer() != null) {
                            ((MainActivity) mactivity).getCountDownTimer().cancel();
                            ((MainActivity) mactivity).setCountDownTimer(null);
                        }
                        dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                        ((MainActivity) mactivity).relativeTimerStopp.setVisibility(View.GONE);
                        ((MainActivity) mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                        TImerListDialog.stime = 180 * 60 * 1000;
                        //MainActivity.Companion.countdown(stime);
                        // CmainActivity.countdown(stime,CmainActivity);
                        mactivity.countdown(TImerListDialog.stime, CmainActivity);
                    }
                    notifyDataSetChanged();
///               context.dismiss();
                    mContextt.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        return view;
    }
    private void updateDialogTitle(TimePicker timePicker, int hourOfDay) {
        myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
        //calendar.set(Calendar.MINUTE, minute);

    }
    private void hideAmPmLayout(TimePicker picker) {
        final int id = Resources.getSystem().getIdentifier("layout", "id", "android");
        final View amPmLayout = picker.findViewById(id);
        Toast.makeText(mactivity, "view", Toast.LENGTH_SHORT).show();
        if(amPmLayout != null) {
            Toast.makeText(mactivity, "if", Toast.LENGTH_SHORT).show();
            amPmLayout.setVisibility(View.GONE);
        }
    }
    public void ShowTimePicker(View view){
        RangeTimePickerDialog tpd = new RangeTimePickerDialog(mactivity, new RangeTimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Toast.makeText(mactivity, "", Toast.LENGTH_SHORT).show();
            }
        }, hour, minute, true);
        tpd.setMin(11, 20);
        tpd.setMin(14, 20);
        tpd.show();
    }
    public class Holder {
        TextView textView;
        RadioButton radioButton;


    }
}
