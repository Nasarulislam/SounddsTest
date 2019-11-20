package com.example.sounddstest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.Adapters.TimerListAdapter;
import com.example.sounddstest.R;

import java.util.ArrayList;

public class TImerListDialog extends Dialog {
    Context context;
    ArrayList<String>timerlist=new ArrayList<>();
    ListView listView;
    TimerListAdapter timerListAdapter;
    public static int stime=0;
  TImerListDialog tImerListDialog;
MainActivity activity;
    public TImerListDialog(@NonNull Context context,MainActivity activity) {
        super(context);
        this.context=context;
        this.tImerListDialog=tImerListDialog;
        this.activity=activity;

    }

    public TImerListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

   /* protected TImerListDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        this.dismiss();
    }

    public  void closeDialog(){

        Log.d("iji","ok");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.timer_list_dialog);

        timerlist=new ArrayList<>();
        timerlist.add("No Timer");
        timerlist.add("5 minutes");
        timerlist.add("10 minutes");
        timerlist.add("15 minutes");
        timerlist.add("30 minutes");
        timerlist.add("45 minutes");
        timerlist.add("1 hour");


        Log.d("asdfghjkhgghjg","dane"+timerlist);
      listView =(ListView)findViewById(R.id.timer_list_id);
         timerListAdapter =new TimerListAdapter(timerlist,tImerListDialog,this,activity);

        listView.setAdapter(timerListAdapter);

    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
       // MainActivity.Companion.countdown(stime);

    }

}

