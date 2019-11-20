package com.example.sounddstest;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.Adapters.customListviewAdapter;
import com.example.sounddstest.Fragments.RainFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class PlayListDialog extends Dialog {

    Context context;
    List<JSONStringFileAudiomix> formList1;
   com.example.sounddstest.Adapters.customListviewAdapter customListviewAdapter;
    private List<String> seekbarlist = new ArrayList<>();
    SharedPreferences shared;
    ListView recyclerViewPLayList;
    MainActivity activity;
    Button reloadButton;
    SharedPreferences sharedchange;
    public PlayListDialog(@NonNull Context context,MainActivity activity, List<JSONStringFileAudiomix> formList1 ) {
        super(context);
        this.context = context;
        this.formList1=formList1;
        this.activity=activity;


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_premium_dialog);
        setContentView(R.layout.play_list);

        recyclerViewPLayList=(ListView) findViewById(R.id.play_list_id);

        sharedchange = getContext().getSharedPreferences("Appsettings", MODE_PRIVATE);
       /* reloadButton=(Button)findViewById(R.id.reload);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("sxcghm","qwerty");
                //activity.rainFragment.reloadRecyclerview(activity);
                boolean change = sharedchange.getBoolean("bottomsheetvalue",true);
                Log.d("bottomsheetdata"," position in adapter : "+change);
                sharedchange.edit().putBoolean("bottomsheetvalue",!change).apply();
            }
        });*/


        shared = context.getSharedPreferences("App_settings", MODE_PRIVATE);
        Set<String> set = shared.getStringSet("RAIN_SOUNDS", null);
        Set<String> set2 = shared.getStringSet("RELAXING_SOUNDS", null);
        if (set!=null||set2!=null) {

            seekbarlist.clear();
            if (set!=null)
            seekbarlist.addAll(set);
            if (set2!=null)
            seekbarlist.addAll(set2);
        }


        Log.d("asdfghjk","10"+seekbarlist);
        Object[] stg = seekbarlist.toArray();
        for (Object s : stg) {
            if (seekbarlist.indexOf(s) != seekbarlist.lastIndexOf(s)) {
                seekbarlist.remove(seekbarlist.lastIndexOf(s));
            }
        }
        Set<String> set4 = shared.getStringSet("LIST_SOUNDS", null);
        if (set4!=null) {

            seekbarlist.clear();
                seekbarlist.addAll(set4);

        }
        Log.d("asdfghjk","15"+seekbarlist);
        /*Object[] stg4 = seekbarlist.toArray();
        for (Object s : stg4) {
            if (seekbarlist.indexOf(s) != seekbarlist.lastIndexOf(s)) {
                seekbarlist.remove(seekbarlist.lastIndexOf(s));
            }
        }*/
        Log.d("asdfghjk","25"+seekbarlist);


        customListviewAdapter=new customListviewAdapter(seekbarlist,  this,activity);
            recyclerViewPLayList.setAdapter(customListviewAdapter);

    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        Log.d("sdvhjjhbvh","15");
       // activity.rainFragment.reloadRecyclerview();
        super.setOnDismissListener(listener);

    }



}
