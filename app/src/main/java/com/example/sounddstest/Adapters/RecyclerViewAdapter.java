package com.example.sounddstest.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.DownloadAndPlayAsyncTask;
import com.example.sounddstest.DownloadTaskListener;
import com.example.sounddstest.Fragments.RainFragment;
import com.example.sounddstest.JSONStringFileAudiomix;
import com.example.sounddstest.Overlay.FloatingWidgetShowService;
import com.example.sounddstest.R;
import com.example.sounddstest.Services.MediaPlayerService;
import com.example.sounddstest.Services.MediaPlayerServiceSecond;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.MODE_PRIVATE;

//import static com.example.sounddstest.Activitys.MainActivity.PlaypuaseImage;
//import static com.example.sounddstest.Activitys.MainActivity.PlayRelative;
//import static com.example.sounddstest.Activitys.MainActivity.PuaseRelative;

//import static com.example.sounddstest.Activitys.MainActivity.cardView;
//import static com.example.sounddstest.Activitys.MainActivity.countDownTimer;
import static com.example.sounddstest.Adapters.customListviewAdapter.progresssy;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements customListviewAdapter.AdapterCallback {

    Context context;
    public static MediaPlayerService player;
    public static boolean serviceBound = false;
    public static List<JSONStringFileAudiomix> formList = new ArrayList<>();
    private List<String> seekbarlist = new ArrayList<>();
    private List<String> seekbarlistsub = new ArrayList<>();
    boolean seekbarBoolean=false;
    SharedPreferences shared;
    SharedPreferences sharedChange;
    SharedPreferences prefs;
    Gson gson;
    Intent floatingIntent;
    private int row_index=-1;
    int noofChips = 0;
    public static   ArrayList<String>chipNames1=new ArrayList<String>();
    public static   ArrayList<String>chipurl=new ArrayList<String>();
    String nameliast;
    ProgressDialog progressDialog;

    AudioManager audioManager;
    private int MAX_VOLUME;
    private  int seekvolume1,seekvolume2;
    public static float volume1, volume2;
    MyViewHolder viewHolder;
    SharedPreferences sharedPreferences;
    Drawable wrappedDrawable;
    private SharedPreferences.OnSharedPreferenceChangeListener listner;

    public static ArrayList<Float> RainvolumeSeekbar=new ArrayList<>();
    public static ArrayList<Integer> Rainvolume=new ArrayList<>();
    Activity activity;
    public RecyclerViewAdapter(Context mcontext, List<JSONStringFileAudiomix> formList,Activity activity) {
        this.context = mcontext;
        this.formList = formList;
        this.activity=activity;
    }

    public RecyclerViewAdapter(Context context) {

    }
    public void setItems(List<String> seekbarlistsub) {
        this.seekbarlistsub = seekbarlistsub;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.row_item, parent, false);
         viewHolder = new MyViewHolder(view);

        sharedChange =view.getContext().getSharedPreferences("Appsettings", MODE_PRIVATE);
         prefs = context.getSharedPreferences("test", MODE_PRIVATE);
         gson = new Gson();
        shared = context.getSharedPreferences("App_settings", MODE_PRIVATE);

       /* IconColorChange(context.getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_volume_up_black_list);
        //MainActivity.listimageView.setBackground(wrappedDrawable);
        ((MainActivity)getActivity()).listimageView.setBackground(wrappedDrawable);
        IconColorChange(context.getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_play_arrow_black);
        MainActivity.playImageView.setBackground(wrappedDrawable);
        IconColorChange(context.getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_pause_black_24dp);
        MainActivity.pauseImageView.setBackground(wrappedDrawable);
        IconColorChange(context.getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_timer_start);
        MainActivity.timerStartImageView.setBackground(wrappedDrawable);
        IconColorChange(context.getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_timer_black);
        MainActivity.timerImageView.setBackground(wrappedDrawable);
        // IconColorChange(Color.RED,R.drawable.);
        MainActivity.timerTextView.setTextColor(context.getResources().getColor(R.color.RainFragmentColor));
        IconColorChange(context.getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_music_video_black_24dp);*/
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {



        //holder.withseekBar.setBackgroundColor(context.getResources().getColor(R.color.RelaxingFragmentColor));
        holder.withseekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));
        holder.withseekBar.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));

        Log.d("ololoo","ok");
        for (int i = 0; i < formList.size(); i++) {
            chipNames1.add(formList.get(i).getSubsound1Name());
            chipNames1.add(formList.get(i).getSubsound2Name());
            chipurl.add(formList.get(i).getSubsound1url());
            chipurl.add(formList.get(i).getSubsound2url());
        }
        Object[] st = chipNames1.toArray();
        for (Object s : st) {
            if (chipNames1.indexOf(s) != chipNames1.lastIndexOf(s)) {
                chipNames1.remove(chipNames1.lastIndexOf(s));
            }
        }
        Object[] stt = chipurl.toArray();
        for (Object s : stt) {
            if (chipurl.indexOf(s) != chipurl.lastIndexOf(s)) {
                chipurl.remove(chipurl.lastIndexOf(s));
            }
        }

//        holder.withtextView.setText(chipNames1.get(position));
  //      holder.withOuttextView.setText(chipNames1.get(position));

        Set<String> set = shared.getStringSet("RAIN_SOUNDS", null);
        if (set != null) {
            seekbarlist.clear();
            seekbarlist.addAll(set);
        }
        Object[] stg = seekbarlist.toArray();
        for (Object s : stg) {
            if (seekbarlist.indexOf(s) != seekbarlist.lastIndexOf(s)) {
                seekbarlist.remove(seekbarlist.lastIndexOf(s));
            }
        }
        audioManager = (AudioManager)context. getSystemService(AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        MAX_VOLUME = currentVolume * 10;
        MAX_VOLUME=100;
        seekvolume1 = MAX_VOLUME/2;
        seekvolume2 = MAX_VOLUME/4;
        // volumeA = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME) / Math.log(MAX_VOLUME)));
        volume1 = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME / 2) / Math.log(MAX_VOLUME)));
        volume2 = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME / 4) / Math.log(MAX_VOLUME)));
        holder.withseekBar.setProgress(seekvolume1);
        Log.d("qwertyu", "seekbarlist" + seekbarlist+"qwer"+chipNames1.size());
        for (int j = 0; j < seekbarlist.size(); j++) {

            if (seekbarlist.get(j).equals(chipNames1.get(position))) {

                holder.RL_withSeekbar.setVisibility(View.VISIBLE);
                holder.RL_withoutSeekbar.setVisibility(View.GONE);
                //if (volumeSeekbar.size()>0)
                for (int i=0;i<RainFragment.Volumevalues.size();i++) {
                    float vf = RainFragment.Volumevalues.get(i);
                    Log.d("bbbcsd",""+vf);
                }
//                Log.d("bbbcsd",RainvolumeSeekbar+""+RainvolumeSeekbar.get(0));
               // holder.withseekBar.setProgress(RainFragment.Volumevalues.get(position));
              /*  String storedHashMapString = prefs.getString("hashString", "oopsDintWork");
                java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                HashMap<String, String> testHashMap2 = gson.fromJson(storedHashMapString, type);
*/
                //use values
              //  Float toastString = testHashMap2.get("key1") + " | " + testHashMap2.get("key2");
            //    Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();

                if (RainFragment.Volumevposition.size()>0) {
                    for (int h=0;h<RainFragment.Volumevposition.size();h++) {
                        if (position==RainFragment.Volumevposition.get(h)) {
                            int progress=RainFragment.Volumevalues.get(h);
                            holder.withseekBar.setProgress(progress);
                            //RainFragment.Volumevalues.set(progress, position);
                            //RainFragment.Volumevposition.add(position);
                        }
                    }
                }
               /* Log.d("fyhjf",""+testHashMap2.get(seekbarlist.get(j)));
                if (testHashMap2.get(seekbarlist.get(j))!=null) {
                    String asd=testHashMap2.get(seekbarlist.get(j));
                    float jnk=Float.parseFloat(asd.toString());
                    int sd=Math.round( jnk);
                    Log.d("fyhjf",jnk+""+sd);
                    holder.withseekBar.setProgress(sd);
                }*//*else {
                    holder.withseekBar.setProgress(MainActivity.volumeSeekbar.get(j));
                }*/

                Log.d("qwertyu", "visible" + seekbarlist);

            }
        }

        MainActivity.Companion.setStringImageName(formList.get(position).getSecondPageImage());
       /* listner = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
               *//* Log.d("bottomsheetdata", "isible "+ s );
                //   if (s.equals("bottomsheetvalue"))
               // Unselected();
                Log.d("bottomsheetdata", "isib" );

                Set<String> set = shared.getStringSet("RAIN_SOUNDS", null);
                if (set != null) {
                    seekbarlist.clear();
                    seekbarlist.addAll(set);
                }
                Object[] stg = seekbarlist.toArray();
                for (Object ss : stg) {
                    if (seekbarlist.indexOf(ss) != seekbarlist.lastIndexOf(ss)) {
                        seekbarlist.remove(seekbarlist.lastIndexOf(ss));
                    }
                }
                for (int j = 0; j < seekbarlist.size(); j++) {

                    if (seekbarlist.get(j).equals(chipNames1.get(position))) {

                        holder.RL_withSeekbar.setVisibility(View.VISIBLE);
                        holder.RL_withoutSeekbar.setVisibility(View.GONE);
                        //if (volumeSeekbar.size()>0)
                        holder.withseekBar.setProgress(MainActivity.volumeSeekbar.get(j));
                        Log.d("qwertyu", "visible" + seekbarlist);

                    }
                }*//*
               if (s.equals("bottomsheetvalue")) {
                  // notifyItemChanged(1);
                   notifyDataSetChanged();
               }
            }
        };*/

        sharedChange.registerOnSharedPreferenceChangeListener(listner);

        progressDialog = new ProgressDialog(context);
        // progressDialog.setTitle("");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //  progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(false);
        noofChips = chipNames1.size();
        Log.d("qwertyuiop",""+noofChips);
        int i;
      //  for (i = 0; i < noofChips; i++) {
        //    int finalI = i;
        //FirstDownload(position);
       holder. RL_withSeekbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  boolean seekShow = false;
                Set<String> set = shared.getStringSet("DATE_LIST", null);
                if (set != null)
                    seekbarlist.addAll(set);
                Object[] stg = seekbarlist.toArray();
                for (Object s : stg) {
                    if (seekbarlist.indexOf(s) != seekbarlist.lastIndexOf(s)) {
                        seekbarlist.remove(seekbarlist.lastIndexOf(s));
                    }
                }

                //holder.seekBar.setVisibility(View.VISIBLE);
                for (int j = 0; j < seekbarlist.size(); j++) {
                    if (seekbarlist.get(j).equals(chipNames1.get(position))) {
                        //holder.seekBar.setVisibility(View.VISIBLE);
                        Log.d("qwertyu", "visible");
                        seekShow = true;
                    }
                }
                if (seekShow) {

                    String name = chipNames1.get(position);
                    Log.d("seekbarlistttt1", "name" + name);
                    seekbarlist.remove(name);
                    SharedPreferences.Editor editor = shared.edit();
                    Set<String> set3 = new HashSet<String>();
                    set3.addAll(seekbarlist);
                    editor.putStringSet("DATE_LIST", set3);
                    editor.apply();
                    seekShow = false;
                } else {
                    PlayAndDownload(position);
                    imageView.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    String fin = chipNames1.get(position);
                    seekbarlist.add(fin);
                    SharedPreferences.Editor editor = shared.edit();
                    Set<String> set3 = new HashSet<String>();
                    set3.addAll(seekbarlist);
                    editor.putStringSet("DATE_LIST", set3);
                    editor.apply();
                }


                notifyDataSetChanged();*/
                if (((MainActivity)context).getCountDownTimer()!=null) {
                    ((MainActivity)activity).countdown(MainActivity.Companion.getSecoundsleftPause(),(MainActivity)activity);
                    //MainActivity.Companion.countdown(MainActivity.Companion.getSecoundsleftPause());
                    ((MainActivity)activity).relativeTimerStopp.setVisibility(View.GONE);
                    ((MainActivity)activity).relativeTimerStartt.setVisibility(View.VISIBLE);
                }
                else {
                    ((MainActivity)activity).relativeTimerStopp.setVisibility(View.VISIBLE);
                    ((MainActivity)activity).relativeTimerStartt.setVisibility(View.GONE);
                }
                if (MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")) != null)
                    MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).stop();
                String name = chipNames1.get(position);
                Log.d("seekbarlistttt1", "name" + name);
                seekbarlist.remove(name);
              //  seekbarlistsub.remove(name);
                SharedPreferences.Editor editor = shared.edit();
                Set<String> set3 = new HashSet<String>();
                set3.addAll(seekbarlist);
                editor.putStringSet("RAIN_SOUNDS", set3);
                editor.apply();

                Set<String> setL = new HashSet<String>();
                 setL = shared.getStringSet("LIST_SOUNDS", null);
                seekbarlistsub.clear();
                if (setL!=null)
                    seekbarlistsub.addAll(setL);
                seekbarlistsub.remove(name);
                Set<String> set6 = new HashSet<String>();
                set6.addAll(seekbarlistsub);
                editor.putStringSet("LIST_SOUNDS", set6);
                editor.apply();

                Log.d("innin",""+seekbarlistsub);
                MainActivity.VolumeHashMap.remove(chipNames1.get(position));
              //  notifyDataSetChanged();
                holder.RL_withSeekbar.setVisibility(View.GONE);
                holder.RL_withoutSeekbar.setVisibility(View.VISIBLE);
                if (RainFragment.Volumevposition.size()>0) {
                    for (int h=0;h<RainFragment.Volumevposition.size();h++) {
                        if (position==RainFragment.Volumevposition.get(h)) {

                            RainFragment.Volumevalues.remove( h);
                            RainFragment.Volumevposition.remove(h);
                        }
                    }
                }
                Set<String> set5 = shared.getStringSet("LIST_SOUNDS", null);
                if (set5!=null)
                    seekbarlistsub.addAll(set5);

                Log.d("inniinn",""+seekbarlistsub);
                if (seekbarlistsub.size()>0)
                {
                    ((MainActivity)context).cardView.setVisibility(View.VISIBLE);
                   // cardView.setVisibility(View.VISIBLE);
                }
                else {
                    ((MainActivity)context).cardView.setVisibility(View.GONE);
                    ((MainActivity)context).relativeTimerStopp.setVisibility(View.VISIBLE);
                    ((MainActivity)context).relativeTimerStart.setVisibility(View.GONE);
                    if (((MainActivity)context).getCountDownTimer()!=null)
                        ((MainActivity)context).getCountDownTimer().cancel();
                }

            }

        });
   // }

        holder.RL_withoutSeekbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                seekbarlistsub.clear();
                Set<String> set6 = shared.getStringSet("LIST_SOUNDS", null);
                if (set6!=null)
                    seekbarlistsub.addAll(set6);
                if (seekbarlistsub.size()<5) {
                    PlayAndDownload(position);
                    //showCard();
                    MediaPlayerService.resumeMedia();

                    if (((MainActivity)activity).getCountDownTimer()!=null) {
                     //   ((MainActivity)activity).countdown(MainActivity.Companion.getSecoundsleftPause(),(MainActivity)activity);
                        //MainActivity.Companion.countdown(MainActivity.Companion.getSecoundsleftPause());
                        ((MainActivity)activity).relativeTimerStopp.setVisibility(View.GONE);
                        ((MainActivity)activity).relativeTimerStartt.setVisibility(View.VISIBLE);
                    }
                    //PlaypuaseImage.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                   // ((MainActivity)).PlayRelative$1.setVisibility(View.GONE);
                    //((MainActivity)getActivity()).listimageView.setBackground(wrappedDrawable);
                   // MainActivity.PlayRelativee.setVisibility(View.GONE);
                    ((MainActivity)activity).PuaseRelativee.setVisibility(View.VISIBLE);
                  //  MainActivity. PuaseRelativee.setVisibility(View.VISIBLE);


                    ((MainActivity)activity).PlayRelativee.setVisibility(View.GONE);
                   // ((MainActivity)context.getApplicationContext()).PuaseRelative$1.setVisibility(View.GONE);
                    String fin = chipNames1.get(position);
                    seekbarlist.add(fin);


                    SharedPreferences.Editor editor = shared.edit();
                    Set<String> set3 = new HashSet<String>();
                    set3.addAll(seekbarlist);
                    editor.putStringSet("RAIN_SOUNDS", set3);
                    editor.apply();

                    seekbarlistsub.clear();
                    Set<String> set5 = shared.getStringSet("LIST_SOUNDS", null);
                    if (set5 != null)
                        seekbarlistsub.addAll(set5);
                    SharedPreferences.Editor editor4 = shared.edit();
                    Set<String> set4 = new HashSet<String>();
                    seekbarlistsub.add(fin);
                    set4.addAll(seekbarlistsub);
                    editor4.putStringSet("LIST_SOUNDS", set4);
                    editor4.apply();
                    Log.d("qwerty", "qaz" + seekbarlistsub);
                    // notifyDataSetChanged();
                    holder.RL_withoutSeekbar.setVisibility(View.GONE);
                    holder.RL_withSeekbar.setVisibility(View.VISIBLE);
                    // MainActivity.volumeSeekbar.add(seekvolume1);
                    // holder.withseekBar.setProgress(MAX_VOLUME/2);

                    if (seekbarlistsub.size() > 0) {
                        ((MainActivity)context).cardView.setVisibility(View.VISIBLE);

                    } else {
                        ((MainActivity)context). cardView.setVisibility(View.GONE);
                        ((MainActivity)context).relativeTimerStopp.setVisibility(View.VISIBLE);
                        ((MainActivity)context).relativeTimerStart.setVisibility(View.GONE);

                      //  MainActivity.relativeTimerStop.setVisibility(View.VISIBLE);
                       // MainActivity.relativeTimerStart.setVisibility(View.GONE);
                        if (((MainActivity)context).getCountDownTimer()!=null)
                            ((MainActivity)context).getCountDownTimer().cancel();

                    }
                }
                else {
                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view1 = inflater.inflate(R.layout.customtoast, null);
                    toast.setView(view1);
                    toast.show();
                }
            }

        });

       // holder.withseekBar.setProgress(seekvolume1);
        holder.withseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("SeekBarChangedy", Integer.toString(progress));
                Log.i("SeekBarChangedy",Integer.toString(position));
                nameliast=chipNames1.get(position);
                // progresssy = progress * 0.10f;
                progresssy = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                MediaPlayerService.mediaPlayerHashMap.get(nameliast.replaceAll("_", " ")).setVolume(progresssy,progresssy);
//                MainActivity.volumeSeekbar.set(position,progress);
                //String fin = chipNames1.get(position);
                MainActivity.VolumeHashMap.put(nameliast,progress);
                if (RainFragment.Volumevposition.size()>0) {
                    for (int h=0;h<RainFragment.Volumevposition.size();h++) {
                        if (position==RainFragment.Volumevposition.get(h)) {
                            RainFragment.Volumevalues.set(h, progress);
                           // RainFragment.Volumevposition.set(position);
                        }
                    }
                }
                /*RainFragment.volumeHashMap.put(chipNames1.get(position),progress);
                float sfd= RainFragment.volumeHashMap.get(chipNames1.get(position));
                Log.d("asdfghjkjhvhn","abc"+sfd);
                String hashMapString = gson.toJson(RainFragment.volumeHashMap);
                prefs.edit().putString(chipNames1.get(position), hashMapString).apply();*/
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void seekbarlistAdd(int position)
    {
        for (int f=0;f<seekbarlist.size();f++){
            //   for (int s=0;s<formList.size();s++){
            if (seekbarlist.get(f).equals(formList.get(position).getPackName())) {
                //  holder.seekBar.setVisibility(View.VISIBLE);
                Log.d("qwertyu","visible");
/*
                String name = formList.get(position).getPackName();
                Log.d("seekbarlistttt1", "name" + name);
                seekbarlist.remove(name);
                SharedPreferences.Editor editor = shared.edit();
                Set<String> set = new HashSet<String>();
                set.addAll(seekbarlist);
                editor.putStringSet("DATE_LIST", set);
                editor.apply();*/
            }else {
                String fin = formList.get(position).getPackName();
                seekbarlist.add(fin);
                SharedPreferences.Editor editor = shared.edit();
                Set<String> set = new HashSet<String>();
                set.addAll(seekbarlist);
                editor.putStringSet("DATE_LIST", set);
                editor.apply();
                // holder.seekBar.setVisibility(View.GONE);
                Log.d("qwertyu","gone");
            }
        }
    }
    @Override
    public int getItemCount() {
        return formList.size();

    }

    @Override
    public void onMethodCallback( ) {
     //notifyDataSetChanged();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView withimageView;
        SeekBar withseekBar;
        TextView withtextView;

        ImageView withOutimageView;
        TextView withOuttextView;

        RelativeLayout RL_withSeekbar;
        RelativeLayout RL_withoutSeekbar;
        AudioManager audioManager;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            withimageView = itemView.findViewById(R.id.imageid2);
           // withtextView = itemView.findViewById(R.id.textView2);
            withseekBar=itemView.findViewById(R.id.seekbarid2);

            withOutimageView = itemView.findViewById(R.id.imageid);
            withOuttextView = itemView.findViewById(R.id.textView);

            RL_withSeekbar=itemView.findViewById(R.id.WithSeekbar);
            RL_withoutSeekbar=itemView.findViewById(R.id.WithOutSeekbar);
        }
    }

    public void PlayAndDownload(final int position){
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        MAX_VOLUME = currentVolume * 10;
        MAX_VOLUME=100;
        seekvolume1 = MAX_VOLUME/2;
        seekvolume2 = MAX_VOLUME/4;
       // volumeA = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME) / Math.log(MAX_VOLUME)));
        volume1 = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME / 2) / Math.log(MAX_VOLUME)));
        volume2 = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME / 4) / Math.log(MAX_VOLUME)));


        final File file = new File(context.getFilesDir().getAbsolutePath() + chipNames1.get(position).replaceAll("_", " ") + ".mp3");
        if (!file.exists()) {
            Log.d("popopopopop", " file" + position+file);
            try {
                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    Log.d("popopopopop", " file2" + position);
                    //   setProgressDialog(ProgressDialog.show(getContext(), "", "Downloading...", true, true));
                    //   set
                    new DownloadAndPlayAsyncTask(new DownloadTaskListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDownloadComplete() {

                              /*  MediaPlayerService.mediaPlayerA = MediaPlayer.create(context, Uri.fromFile(file));
                                //new File(path+formList.get(position).getPackName()+ ".mp3")
                                MediaPlayerService.mediaPlayerA.setLooping(true);
                                MediaPlayerService.mediaPlayerA.start();*/
                           // playing = true;
                            MediaPlayerService.mediaPlayerHashMap.put(chipNames1.get(position).replaceAll("_", " "), MediaPlayer.create(context, Uri.fromFile(file)));
                            if (MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")) != null) {
                                MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).setLooping(true);
                                MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).setVolume(volume1,volume1);
                                MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).start();
                                //   Float vl=  MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(finalI).replaceAll("_", " ")).setVolume(RecyclerViewAdapter.volume1,RecyclerViewAdapter.volume1);
                               //.add(SecondActivity.seekvolumeA / 2);
                               /* String fin = chipNames1.get(position);
                                arrPackage.add(fin);
                                SharedPreferences.Editor editor = shared.edit();
                                Set<String> set = new HashSet<String>();
                                set.addAll(arrPackage);
                                editor.putStringSet("DATE_LIST", set);
                                editor.apply();
                                Log.d("storesharedPreferences", "" + set);
                                */

                                Intent intent = new Intent(context, MediaPlayerServiceSecond.class);
                                intent.setAction(MediaPlayerServiceSecond.ACTION_NEXT);
                                context.startService(intent);
                                //showCard();
                               RainFragment.Volumevalues.add(seekvolume1);
                               RainFragment.Volumevposition.add(position);
                               MainActivity.VolumeHashMap.put(chipNames1.get(position),seekvolume1);
//                               MainActivity.ListvolumeSeekbar.add(seekvolume1);
                               // Set<String> set4 = new HashSet<String>();
                               // seekbarlistsub.add(fin);
                                /*set4.addAll(seekbarlistsub);
                                editor4.putStringSet("LIST_SOUNDS", set4);
                                editor4.apply();*/
                           /* RainFragment.volumeHashMap.put(chipNames1.get(position),seekvolume1);
                            float sfd= RainFragment.volumeHashMap.get(chipNames1.get(position));
                                Log.d("asdfghjkjhvhn",""+sfd);

                                String hashMapString = gson.toJson(RainFragment.volumeHashMap);

                                //save in shared prefs

                                prefs.edit().putString(chipNames1.get(position), hashMapString).apply();*/
                           // RainFragment.volumeHashMap.merge(chipNames1.get(position).replaceAll("_", " "),volume1,Float::sum);
                               // RainvolumeSeekbar.add(volume1);
                                //Log.d("asdfghjkjhvhn",volume1+"/"+RainvolumeSeekbar.size());

                                //RainFragment.Volumevalues.add(position,volume1);

                               /// RecyclerViewAdapter.volumeSeekbar.add(SecondActivity.seekvolumeA / 2);
                               // adaptercall();
                                //
                              //  myTextViews[finalI].changeBackgroundColor(Color.parseColor(SecondActivity.chipcoloron));

                            }
                        }
                    }, context, progressDialog).execute("url", context.getFilesDir().getAbsolutePath(),
                            context.getString(R.string.RainSoundpath) + chipurl.get(position) + ".mp3",

                            chipNames1.get(position).replaceAll("_", " "));
                    Log.d("popopopopop", position+" file3" + chipurl.get(position));

                } else {
                    //  Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.customtoastinternet,null );
                    toast.setView(view);
                    toast.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {


            Log.d("popopopopop", " file2" + position);
            MediaPlayerService.mediaPlayerHashMap.put(chipNames1.get(position).replaceAll("_", " "), MediaPlayer.create(context, Uri.fromFile(file)));
            if (MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")) != null) {

                MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).setLooping(true);
             //   MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).setVolume(RecyclerViewAdapter.volume1, RecyclerViewAdapter.volume1);

                MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).start();
/*
                RainFragment.volumeHashMap.put(chipNames1.get(position),seekvolume1);
                float sfd= RainFragment.volumeHashMap.get(chipNames1.get(position));
                Log.d("asdfghjkjhvhn","NoD"+sfd);*/

                RainFragment.Volumevalues.add(seekvolume1);
                RainFragment.Volumevposition.add(position);
//                MainActivity.ListvolumeSeekbar.add(seekvolume1);
                MainActivity.VolumeHashMap.put(chipNames1.get(position),seekvolume1);


                MainActivity.Companion.setStringImageName(formList.get(position).getSecondPageImage());
                Intent intent = new Intent(context, MediaPlayerServiceSecond.class);
                intent.setAction(MediaPlayerServiceSecond.ACTION_NEXT);
                context.startService(intent);

               /* String hashMapString = gson.toJson(RainFragment.volumeHashMap);
                prefs.edit().putString(chipNames1.get(position), hashMapString).apply();*/
             /*   String fin = chipNames1.get(position);
                arrPackage.add(fin);
                SharedPreferences.Editor editor = shared.edit();
                Set<String> set = new HashSet<String>();
                set.addAll(arrPackage);
                editor.putStringSet("DATE_LIST", set);
                editor.apply();
                Log.d("storesharedPreferences", "" + set);

                */
                //RecyclerViewAdapter.volumeSeekbar.add(SecondActivity.seekvolumeA / 2);
               // adaptercall();
                //

                //myTextViews[finalI].changeBackgroundColor(Color.parseColor(SecondActivity.chipcoloron));
            }
        }
    }
    public void FirstDownload(final int position){
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        MAX_VOLUME = currentVolume * 10;
        MAX_VOLUME=100;
        seekvolume1 = MAX_VOLUME/2;
        seekvolume2 = MAX_VOLUME/4;
        // volumeA = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME) / Math.log(MAX_VOLUME)));
        volume1 = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME / 2) / Math.log(MAX_VOLUME)));
        volume2 = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME / 4) / Math.log(MAX_VOLUME)));


        final File file = new File(context.getFilesDir().getAbsolutePath() + chipNames1.get(position).replaceAll("_", " ") + ".mp3");
        if (!file.exists()) {
            Log.d("popopopopop", " file" + position+file);
            try {
                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    Log.d("popopopopop", " file2" + position);
                    //   setProgressDialog(ProgressDialog.show(getContext(), "", "Downloading...", true, true));
                    //   set
                    new DownloadAndPlayAsyncTask(new DownloadTaskListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDownloadComplete() {

                              /*  MediaPlayerService.mediaPlayerA = MediaPlayer.create(context, Uri.fromFile(file));
                                //new File(path+formList.get(position).getPackName()+ ".mp3")
                                MediaPlayerService.mediaPlayerA.setLooping(true);
                                MediaPlayerService.mediaPlayerA.start();*/
                            // playing = true;
                            MediaPlayerService.mediaPlayerHashMap.put(chipNames1.get(position).replaceAll("_", " "), MediaPlayer.create(context, Uri.fromFile(file)));
                            if (MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")) != null) {
                              //  MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).setLooping(true);
                               // MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).setVolume(volume1,volume1);
                               // MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).start();
                                //   Float vl=  MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(finalI).replaceAll("_", " ")).setVolume(RecyclerViewAdapter.volume1,RecyclerViewAdapter.volume1);
                                //.add(SecondActivity.seekvolumeA / 2);
                               /* String fin = chipNames1.get(position);
                                arrPackage.add(fin);
                                SharedPreferences.Editor editor = shared.edit();
                                Set<String> set = new HashSet<String>();
                                set.addAll(arrPackage);
                                editor.putStringSet("DATE_LIST", set);
                                editor.apply();
                                Log.d("storesharedPreferences", "" + set);
                                */
                            //    RainFragment.Volumevalues.add(seekvolume1);
                             //   RainFragment.Volumevposition.add(position);
                           /* RainFragment.volumeHashMap.put(chipNames1.get(position),seekvolume1);
                            float sfd= RainFragment.volumeHashMap.get(chipNames1.get(position));
                                Log.d("asdfghjkjhvhn",""+sfd);

                                String hashMapString = gson.toJson(RainFragment.volumeHashMap);

                                //save in shared prefs

                                prefs.edit().putString(chipNames1.get(position), hashMapString).apply();*/
                                // RainFragment.volumeHashMap.merge(chipNames1.get(position).replaceAll("_", " "),volume1,Float::sum);
                                // RainvolumeSeekbar.add(volume1);
                                //Log.d("asdfghjkjhvhn",volume1+"/"+RainvolumeSeekbar.size());

                                //RainFragment.Volumevalues.add(position,volume1);

                                /// RecyclerViewAdapter.volumeSeekbar.add(SecondActivity.seekvolumeA / 2);
                                // adaptercall();
                                //
                                //  myTextViews[finalI].changeBackgroundColor(Color.parseColor(SecondActivity.chipcoloron));

                            }
                        }
                    }, context, progressDialog).execute("url", context.getFilesDir().getAbsolutePath(),
                            context.getString(R.string.RainSoundpath) + chipurl.get(position) + ".mp3",

                            chipNames1.get(position).replaceAll("_", " "));
                    Log.d("popopopopop", position+" file3" + chipurl.get(position));

                } else {
                    //  Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.customtoastinternet,null );
                    toast.setView(view);
                    toast.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            //   MainActivity.playerService = player;
            serviceBound = true;

            //  Toast.makeText(context, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    public void Unselected(){


        Log.d("qwertyukoko", "visib" );
        Set<String> set = shared.getStringSet("RAIN_SOUNDS", null);
        if (set != null) {
            seekbarlist.clear();
            seekbarlist.addAll(set);
        }
        Object[] stg = seekbarlist.toArray();
        for (Object s : stg) {
            if (seekbarlist.indexOf(s) != seekbarlist.lastIndexOf(s)) {
                seekbarlist.remove(seekbarlist.lastIndexOf(s));
            }
        }

        viewHolder.RL_withSeekbar.setVisibility(View.GONE);
        viewHolder.RL_withoutSeekbar.setVisibility(View.VISIBLE);

       // notifyDataSetChanged();

     /*   Log.d("qwertyukoko", "visi" +seekbarlist);
            for (int j = 0; j < seekbarlist.size(); j++) {
                for (int i=0;i<chipNames1.size();i++) {
                if (seekbarlist.get(j).equals(chipNames1.get(i))) {

                    viewHolder.RL_withSeekbar.setVisibility(View.GONE);
                    viewHolder.RL_withoutSeekbar.setVisibility(View.VISIBLE);
                    //if (volumeSeekbar.size()>0)
                    viewHolder.withseekBar.setProgress(MainActivity.volumeSeekbar.get(j));
                    Log.d("qwertyukoko", "visible" + seekbarlist);

                }
            }
        }*/


    }
    private void showCard(){
        Log.d("bhjn","donme");
        try {
            //  GetPremium getPremium = new GetPremium(this,this);
            floatingIntent = new Intent(context, FloatingWidgetShowService.class);
         //   floatingIntent.putExtra("exercises",Exercise.Companion.toExerciseArrayString(exercises));
            //floatingIntent.putExtra("loop",loop);
            //floatingIntent.putExtra("imageUrl",imageUrl);
          //  floatingIntent.putExtra("currentExercisePosition",sharedPreferences.getInt("currentExercisePosition",0));
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                shared.edit().putBoolean("cardIsShown",true).apply();
                context.startService(floatingIntent);

            } else if (Settings.canDrawOverlays(context)) {
                //stopService();
                shared.edit().putBoolean("cardIsShown",true).apply();
                context.startService(floatingIntent);
            }
            Log.d("bhjn","donme");
            /*else
                RuntimePermissionForUser();*/
        } catch (Exception e) {
            Log.d("bhjn","error : "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void IconColorChange(Integer color,Integer integer){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context,integer );
        wrappedDrawable= DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
    }


}