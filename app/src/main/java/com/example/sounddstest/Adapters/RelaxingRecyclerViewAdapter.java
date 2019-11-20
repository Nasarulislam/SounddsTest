package com.example.sounddstest.Adapters;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.IBinder;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.DownloadAndPlayAsyncTask;
import com.example.sounddstest.DownloadTaskListener;
import com.example.sounddstest.Fragments.RainFragment;
import com.example.sounddstest.Fragments.RelaxingFragment;
import com.example.sounddstest.JSONStringFileAudiomix;
import com.example.sounddstest.R;
import com.example.sounddstest.Services.MediaPlayerService;
import com.example.sounddstest.Services.MediaPlayerServiceSecond;

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
import static com.example.sounddstest.Activitys.MainActivity.cardView;
//import static com.example.sounddstest.Activitys.MainActivity.countDownTimer;
import static com.example.sounddstest.Adapters.customListviewAdapter.progresssy;


public class RelaxingRecyclerViewAdapter extends RecyclerView.Adapter<RelaxingRecyclerViewAdapter.MyViewHolder> {

    Context context;
    public static MediaPlayerService player;
    public static boolean serviceBound = false;
    public static List<JSONStringFileAudiomix> formList = new ArrayList<>();
    private List<String> seekbarlist = new ArrayList<>();
    private List<String> seekbarlistsub = new ArrayList<>();
    boolean seekbarBoolean=false;
    SharedPreferences shared;
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
    public RelaxingRecyclerViewAdapter(Context mcontext, List<JSONStringFileAudiomix> formList) {
        this.context = mcontext;
        this.formList = formList;

    }

    public RelaxingRecyclerViewAdapter(Context context) {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.row_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        shared = context.getSharedPreferences("App_settings", MODE_PRIVATE);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.withseekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#23C098")));
        holder.withseekBar.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#23C098")));
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

        Set<String> set = shared.getStringSet("RELAXING_SOUNDS", null);
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
        Log.d("qwertyu", "seekbarlist" + seekbarlist+"qwer"+chipNames1.size());


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
        for (int j = 0; j < seekbarlist.size(); j++) {

            if (seekbarlist.get(j).equals(chipNames1.get(position))) {
                holder.RL_withSeekbar.setVisibility(View.VISIBLE);
                holder.RL_withoutSeekbar.setVisibility(View.GONE);
                Log.d("qwertyu", "visible" + seekbarlist);

                if (RelaxingFragment.Volumevposition.size()>0) {
                    for (int h=0;h<RelaxingFragment.Volumevposition.size();h++) {
                        if (position==RelaxingFragment.Volumevposition.get(h)) {
                            int progress=RelaxingFragment.Volumevalues.get(h);
                            holder.withseekBar.setProgress(progress);
                            Log.d("jhkjjkbjbkj", "visible" +progress);
                            //RainFragment.Volumevalues.set(progress, position);
                            //RainFragment.Volumevposition.add(position);
                        }
                    }
                }
            }
        }

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
        holder.RL_withSeekbar.setOnClickListener(new View.OnClickListener() {
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
                if (MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")) != null)
                    MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).stop();
                String name = chipNames1.get(position);
                Log.d("innin", "name" + name);
                seekbarlist.remove(name);
                seekbarlistsub.remove(name);
                SharedPreferences.Editor editor = shared.edit();
                Set<String> set3 = new HashSet<String>();
                set3.addAll(seekbarlist);
                editor.putStringSet("RELAXING_SOUNDS", set3);
                editor.apply();
                set3.addAll(seekbarlistsub);
                editor.putStringSet("LIST_SOUNDS", set3);
                editor.apply();
               // Log.d("innin",""+seekbarlistsub);
                Log.d("innin",""+seekbarlistsub);
                MainActivity.VolumeHashMap.remove(chipNames1.get(position));
                //  notifyDataSetChanged();
                holder.RL_withSeekbar.setVisibility(View.GONE);
                holder.RL_withoutSeekbar.setVisibility(View.VISIBLE);
                if (RelaxingFragment.Volumevposition.size()>0) {
                    for (int h=0;h<RelaxingFragment.Volumevposition.size();h++) {
                        if (position==RelaxingFragment.Volumevposition.get(h)) {

                            RelaxingFragment.Volumevalues.remove( h);
                            RelaxingFragment.Volumevposition.remove(h);
                        }
                    }
                }

                Set<String> set5 = shared.getStringSet("LIST_SOUNDS", null);
                if (set5!=null)
                    seekbarlistsub.addAll(set5);
                if (seekbarlistsub.size()>0)
                {
                    cardView.setVisibility(View.VISIBLE);
                }
                else {
                    cardView.setVisibility(View.GONE);
                    ((MainActivity)context).relativeTimerStop.setVisibility(View.VISIBLE);
                    ((MainActivity)context).relativeTimerStart.setVisibility(View.GONE);
                    if (((MainActivity)context).getCountDownTimer()!=null)
                        ((MainActivity)context).getCountDownTimer().cancel();
                      //  MainActivity.countDownTimer.cancel();
                }
            }

        });
   // }

        holder.RL_withoutSeekbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekbarlistsub.clear();
                Set<String> set6 = shared.getStringSet("LIST_SOUNDS", null);
                if (set6 != null)
                    seekbarlistsub.addAll(set6);
                if (seekbarlistsub.size() < 5) {
                    PlayAndDownload(position);
                    //PlaypuaseImage.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                   // PlayRelative.setVisibility(View.GONE);
                    //PuaseRelative.setVisibility(View.VISIBLE);
                    String fin = chipNames1.get(position);
                    seekbarlist.add(fin);
                    seekbarlistsub.add(fin);
                    SharedPreferences.Editor editor = shared.edit();
                    Set<String> set3 = new HashSet<String>();
                    set3.addAll(seekbarlist);
                    editor.putStringSet("RELAXING_SOUNDS", set3);
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
                    // notifyDataSetChanged();
                    holder.RL_withoutSeekbar.setVisibility(View.GONE);
                    holder.RL_withSeekbar.setVisibility(View.VISIBLE);

                    if (seekbarlistsub.size() > 0) {
                        cardView.setVisibility(View.VISIBLE);
                    } else {
                        cardView.setVisibility(View.GONE);
                        ((MainActivity)context).relativeTimerStop.setVisibility(View.VISIBLE);
                        ((MainActivity)context).relativeTimerStart.setVisibility(View.GONE);
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
                if (RelaxingFragment.Volumevposition.size()>0) {
                    for (int h=0;h<RelaxingFragment.Volumevposition.size();h++) {
                        if (position==RelaxingFragment.Volumevposition.get(h)) {

                            RelaxingFragment.Volumevalues.set(h, progress);
                            // RainFragment.Volumevposition.set(position);
                        }
                    }
                }

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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView withimageView;
        SeekBar withseekBar;
        TextView withtextView;

        ImageView withOutimageView;
        TextView withOuttextView;

        RelativeLayout RL_withSeekbar;
        RelativeLayout RL_withoutSeekbar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            withimageView = itemView.findViewById(R.id.imageid2);
          //  withtextView = itemView.findViewById(R.id.textView2);
            withseekBar=itemView.findViewById(R.id.seekbarid2);

            withOutimageView = itemView.findViewById(R.id.imageid);
            withOuttextView = itemView.findViewById(R.id.textView);

             RL_withSeekbar=itemView.findViewById(R.id.WithSeekbar);
             RL_withoutSeekbar=itemView.findViewById(R.id.WithOutSeekbar);

        }
    }

    public void PlayAndDownload(final int position){
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
                                MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).setVolume(volume1, volume1);
                                MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).start();
                                //   Float vl=  MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(finalI).replaceAll("_", " ")).setVolume(RecyclerViewAdapter.volume1,RecyclerViewAdapter.volume1);

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

                                RelaxingFragment.Volumevalues.add(seekvolume1);
                                RelaxingFragment.Volumevposition.add(position);
                                MainActivity.VolumeHashMap.put(chipNames1.get(position),seekvolume1);
                               /// RecyclerViewAdapter.volumeSeekbar.add(SecondActivity.seekvolumeA / 2);
                               // adaptercall();
                                //
                              //  myTextViews[finalI].changeBackgroundColor(Color.parseColor(SecondActivity.chipcoloron));

                            }
                        }
                    }, context, progressDialog).execute("url", context.getFilesDir().getAbsolutePath(),
                            context.getString(R.string.RelaxingSoundpath) + chipurl.get(position) + ".mp3",
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
                MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).setVolume(volume1, volume1);

                MediaPlayerService.mediaPlayerHashMap.get(chipNames1.get(position).replaceAll("_", " ")).start();

                Intent intent = new Intent(context, MediaPlayerServiceSecond.class);
                intent.setAction(MediaPlayerServiceSecond.ACTION_NEXT);
                context.startService(intent);

                RelaxingFragment.Volumevalues.add(seekvolume1);
                RelaxingFragment.Volumevposition.add(position);
                MainActivity.VolumeHashMap.put(chipNames1.get(position),seekvolume1);
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

}
