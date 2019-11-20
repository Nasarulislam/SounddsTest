package com.example.sounddstest.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.PlayListDialog;
import com.example.sounddstest.R;
import com.example.sounddstest.TImerListDialog;

import java.util.ArrayList;

import static com.example.sounddstest.TImerListDialog.stime;

public class TimerListAdapter extends BaseAdapter {

    ArrayList<String>timerList=new ArrayList<>();
    Dialog context;
    int selectedPosition=0;
    AlertDialog dialog;
    Dialog activity;
    TImerListDialog mContextt;
    AlertDialog alertDialog=null;
    MainActivity mactivity;
   public static SharedPreferences dismissSharedPreferences;
    public TimerListAdapter(ArrayList<String> timerList, Dialog cotext,TImerListDialog mContextt,MainActivity mactivity ) {
        this.timerList = timerList;
        this.mContextt=mContextt;
        this.context = cotext;
        this.activity=activity;
        this.mactivity=mactivity;
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
        View view =convertView ;
        final Context mContext=parent.getContext();
        MainActivity CmainActivity=new MainActivity();
        dismissSharedPreferences=mContext.getSharedPreferences("App_settings",Context.MODE_PRIVATE);
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
        holder.radioButton=(RadioButton)view.findViewById(R.id.radioButton);
      //  holder.textView.setText(timerList.get(i));
        holder.radioButton.setText(timerList.get(i));

        int pos = dismissSharedPreferences.getInt("position",0);

        Log.d("poooosi",""+pos);
      //  holder.radioButton.setChecked(i==selectedPosition);
        holder.radioButton.setTag(i);
        if (i==pos) {
            holder.radioButton.setChecked(true);
            //holder.radioButton.setChecked(i == selectedPosition);
            holder.radioButton.setTag(i);
        }
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedPosition = (Integer)view.getTag();
                String selectString=timerList.get(i);
                Log.d("selectString",selectedPosition+""+selectString);
                if (selectString.equals("No Timer")){
                    if (((MainActivity)mactivity).getCountDownTimer() != null)
                    ((MainActivity)mactivity).getCountDownTimer().cancel();

                    //stime=9;
                   // MainActivity.Companion.countdown(stime);
                   // MainActivity.relativeTimerStop.setVisibility(View.VISIBLE);
                    ((MainActivity)mactivity).relativeTimerStopp.setVisibility(View.VISIBLE);
                   // MainActivity.relativeTimerStart.setVisibility(View.GONE);
                    ((MainActivity)mactivity).relativeTimerStartt.setVisibility(View.GONE);
                    dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                    int pos = dismissSharedPreferences.getInt("position",0);

                    Log.d("poooosi",""+pos);
                   // context.dismiss();
                   /* Dialog dialog=new Dialog(mContext);
                    dialog.dismiss();*/
                   /* TImerListDialog tImerListDialog=new TImerListDialog(mContext);
                    tImerListDialog.dismiss();*/
                }
                if (selectString.equals("5 minutes")){
                    if (((MainActivity)mactivity).getCountDownTimer() != null) {
                        ((MainActivity) mactivity).getCountDownTimer().cancel();
                      //  ((MainActivity) mactivity).setCountDownTimer();

                        Log.d("jvkjvn","if");
                    }
                    else {
                        Log.d("jvkjvn","else");
                    }


                   /* if (CmainActivity.getCountDownTimer()!=null){
                        CmainActivity.getCountDownTimer().cancel();
                        Log.d("jvkjvn","jhfjh");
                    }*/
                    dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                    //MainActivity.relativeTimerStop.setVisibility(View.GONE);
                    ((MainActivity)mactivity).relativeTimerStopp.setVisibility(View.GONE);
                    //MainActivity.relativeTimerStart.setVisibility(View.VISIBLE);
                    ((MainActivity)mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                    stime=5* 60 * 1000;
                    //MainActivity.Companion.countdown(stime);
                  //  CmainActivity.countdown(stime,CmainActivity);
                    mactivity.countdown(stime,CmainActivity);


//                    context.dismiss();
//                    context.dismiss();
                    MainActivity.Companion.dismisTimer(mContext,mactivity);
                    TImerListDialog tImerListDialog=new TImerListDialog(mContext,mactivity);
                    tImerListDialog.dismiss();

                   // mContext.get
                }
                if (selectString.equals("10 minutes")){
                    if (((MainActivity)mactivity).getCountDownTimer() != null)
                        ((MainActivity)mactivity).getCountDownTimer().cancel();
                    dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                    ((MainActivity)mactivity).relativeTimerStopp.setVisibility(View.GONE);
                    ((MainActivity)mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);

                    stime=10* 60 * 1000;

                   // CmainActivity.countdown(stime,CmainActivity);
                    mactivity.countdown(stime,CmainActivity);
                    //MainActivity.Companion.countdown(stime);
                    TImerListDialog tImerListDialog=new TImerListDialog(mContext,mactivity);
                    tImerListDialog.dismiss();

                    tImerListDialog.closeDialog();
                }
                if (selectString.equals("15 minutes")){
                    if (((MainActivity)mactivity).getCountDownTimer() != null)
                        ((MainActivity)mactivity).getCountDownTimer().cancel();
                    dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                    ((MainActivity)mactivity).relativeTimerStopp.setVisibility(View.GONE);
                    ((MainActivity)mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                    stime=15* 60 * 1000;
                   // MainActivity.Companion.countdown(stime);
                   // CmainActivity.countdown(stime,CmainActivity);
                    mactivity.countdown(stime,CmainActivity);
                }
                if (selectString.equals("30 minutes")){
                    if (((MainActivity)mactivity).getCountDownTimer() != null)
                        ((MainActivity)mactivity).getCountDownTimer().cancel();
                    dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                    ((MainActivity)mactivity).relativeTimerStopp.setVisibility(View.GONE);
                    ((MainActivity)mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                    stime=30* 60 * 1000;
                    //MainActivity.Companion.countdown(stime);
                    //CmainActivity.countdown(stime,CmainActivity);
                    mactivity.countdown(stime,CmainActivity);
                }
                if (selectString.equals("45 minutes")){
                    if (((MainActivity)mactivity).getCountDownTimer() != null)
                        ((MainActivity)mactivity).getCountDownTimer().cancel();
                    dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                    ((MainActivity)mactivity).relativeTimerStopp.setVisibility(View.GONE);
                    ((MainActivity)mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                    stime=45* 60 * 1000;
                   // MainActivity.Companion.countdown(stime);
                    //CmainActivity.countdown(stime,CmainActivity);
                    mactivity.countdown(stime,CmainActivity);
                }
                if (selectString.equals("1 hour")){
                    if (((MainActivity)mactivity).getCountDownTimer() != null)
                        ((MainActivity)mactivity).getCountDownTimer().cancel();
                    dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                    ((MainActivity)mactivity).relativeTimerStopp.setVisibility(View.GONE);
                    ((MainActivity)mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                    stime=60* 60 * 1000;
                   // MainActivity.Companion.countdown(stime);
                   // CmainActivity.countdown(stime,CmainActivity);
                    mactivity.countdown(stime,CmainActivity);
                }
                if (selectString.equals("2 hours")){
                    if (((MainActivity)mactivity).getCountDownTimer() != null)
                        ((MainActivity)mactivity).getCountDownTimer().cancel();
                    dismissSharedPreferences.edit().putInt("position", selectedPosition).commit();
                    ((MainActivity)mactivity).relativeTimerStopp.setVisibility(View.GONE);
                    ((MainActivity)mactivity).relativeTimerStartt.setVisibility(View.VISIBLE);
                    stime=120* 60 * 1000;
                    //MainActivity.Companion.countdown(stime);
                   // CmainActivity.countdown(stime,CmainActivity);
                    mactivity.countdown(stime,CmainActivity);
                }
                notifyDataSetChanged();
///               context.dismiss();
                mContextt.dismiss();


            }
        });





        return view;
    }

    public class Holder
    {
        TextView textView;
       RadioButton radioButton;



    }
}
