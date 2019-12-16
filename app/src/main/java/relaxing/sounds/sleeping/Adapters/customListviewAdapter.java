package relaxing.sounds.sleeping.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import relaxing.sounds.sleeping.Activitys.MainActivity;
import relaxing.sounds.sleeping.Fragments.RainFragment;
import relaxing.sounds.sleeping.Fragments.RelaxingFragment;
import relaxing.sounds.sleeping.Fragments.ThirdFragment;
import relaxing.sounds.sleeping.Fragments.waterFragment;
import relaxing.sounds.sleeping.PlayListDialog;
//import com.example.Activitys.R;
import relaxing.sounds.sleeping.R;
import relaxing.sounds.sleeping.Services.MediaPlayerService;
import relaxing.sounds.sleeping.sleepJSONFile;
//import relaxing.sounds.sleeping.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class customListviewAdapter extends BaseAdapter  {
    private AudioManager audioManager;
    String[] data;
    List<String> listSeek=new ArrayList();
    PlayListDialog context;
    public static float progresssy;
    private  static int MAX_VOLUME ;
    String nameliast;
    String nameChip1;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedchange;
    Set<String> set;
    Set<String> set2;
    Set<String> set4;
    private List<String> seekbarlist = new ArrayList<>();
    private List<String> seekbarlistSub = new ArrayList<>();
    private List<sleepJSONFile> formList1 = new ArrayList<>();
    SharedPreferences shared;
    private  int seekvolume1,seekvolume2;
    public static float volume1, volume2;
    MainActivity activity;
    public static SeekBar seekBar;

    private List<String> Rainlist = new ArrayList<>();
    private List<String> Relaxlist = new ArrayList<>();
    private List<String> Naturelist = new ArrayList<>();
    private List<String> Waterlist = new ArrayList<>();
    public customListviewAdapter(List<String> data, PlayListDialog context, MainActivity activity) {
        this.data = data.toArray(new String[0]);
        this.listSeek=data;
        this.seekbarlist=data;
        this.context=context;
        this.activity=activity;
      //  this.context =(PlayListDialog) context;

    }
    public interface AdapterCallback {
        void onMethodCallback();
    }
    @Override
    public int getCount() {

        return seekbarlist.size();
    }

    @Override
    public Object getItem(int position) {
        return seekbarlist.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Holder holder = new Holder();
        View view = convertView;
        final Context mContext=parent.getContext();


        try {


            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customview_row_item, parent, false);
            //   view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customview_row_item, parent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.textView = (TextView) view.findViewById(R.id.textView);
        holder.seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar=(SeekBar)view.findViewById(R.id.seekBar);

        sharedPreferences =view. getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
        shared = view.getContext().getSharedPreferences("App_settings", MODE_PRIVATE);

        sharedchange = view.getContext().getSharedPreferences("Appsettings", MODE_PRIVATE);
        audioManager = (AudioManager)mContext.getSystemService(AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        MAX_VOLUME = currentVolume * 10;
        seekvolume1 = MAX_VOLUME/2;
        seekvolume2 = MAX_VOLUME/4;
        // volumeA = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME) / Math.log(MAX_VOLUME)));
        volume1 = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME / 2) / Math.log(MAX_VOLUME)));
        volume2 = (float) (1 - (Math.log(MAX_VOLUME - MAX_VOLUME / 4) / Math.log(MAX_VOLUME)));

        try {
            set= shared.getStringSet("RAIN_SOUNDS", null);
            set2 = shared.getStringSet("RELAXING_SOUNDS", null);
            if (set!=null||set2!=null) {

                seekbarlist.clear();
                if (set!=null)
                    seekbarlist.addAll(set);
                if (set2!=null)
                    seekbarlist.addAll(set2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("asdfghjk","1"+seekbarlist);
       /* Object[] stg = seekbarlist.toArray();
        for (Object s : stg) {
            if (seekbarlist.indexOf(s) != seekbarlist.lastIndexOf(s)) {
                seekbarlist.remove(seekbarlist.lastIndexOf(s));
            }
        }*/
       /* SharedPreferences.Editor editor = shared.edit();
        Set<String> set3 = new HashSet<String>();
        set3.addAll(seekbarlist);
        editor.putStringSet("LIST_SOUNDS", set3);
        editor.apply();*/

        try {
            set4 = shared.getStringSet("LIST_SOUNDS", null);
            if (set4!=null) {

                seekbarlist.clear();
                    seekbarlist.addAll(set4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("asdfghjk","4"+seekbarlist);
        /*Object[] stg1 = seekbarlist.toArray();
        for (Object s : stg1) {
            if (seekbarlist.indexOf(s) != seekbarlist.lastIndexOf(s)) {
                seekbarlist.remove(seekbarlist.lastIndexOf(s));
            }
        }*/
        try {
            if (seekbarlist.size()>0) {
                view.setVisibility(View.VISIBLE);
                holder.textView.setText(seekbarlist.get(position));

            }
            else
                view.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            nameliast = holder.textView.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*  holder.imageView=(ImageView)view.findViewById(R.id.closeIcon);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  listSeek.remove(position);
                //data=listSeek.toArray(new String[0]);
                Log.d("zxcvbn","click"+data[position]);
                set= shared.getStringSet("RAIN_SOUNDS", null);
                set2 = shared.getStringSet("RELAXING_SOUNDS", null);
                Log.d("zxcvbn",set+"click"+set2);
                if (seekbarlist.size()>0) {
                    view.setVisibility(View.VISIBLE);
                    holder.textView.setText(seekbarlist.get(position));
                }
                else
                    view.setVisibility(View.GONE);

                if (set!=null) {
                    Log.d("zxcvbn", "set"+set);
                    for (int i = 0; i < set.size(); i++) {

                        if (set.contains(seekbarlist.get(position))) {
                            Log.d("zxcvbn", "set"+set.contains(seekbarlist.get(position)));

                           // seekbarlist.clear();
                            seekbarlistSub.addAll(set);

                            Object[] stg = seekbarlistSub.toArray();
                            for (Object s : stg) {
                                if (seekbarlistSub.indexOf(s) != seekbarlistSub.lastIndexOf(s)) {
                                    seekbarlistSub.remove(seekbarlistSub.lastIndexOf(s));
                                }
                            }
                            if (MediaPlayerService.mediaPlayerHashMap.get(seekbarlistSub.get(i).replaceAll("_", " ")) != null)
                                MediaPlayerService.mediaPlayerHashMap.get(seekbarlistSub.get(i).replaceAll("_", " ")).stop();

                            Log.d("zxcvbn", seekbarlistSub+"set" + seekbarlist+position);
                            seekbarlistSub.remove(seekbarlistSub.get(i));
                            SharedPreferences.Editor editor = shared.edit();
                            Set<String> set3 = new HashSet<String>();
                            set3.addAll(seekbarlistSub);
                            editor.putStringSet("RAIN_SOUNDS", set3);
                            editor.apply();

                            */
      /*RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(mContext,formList1);
                            recyclerViewAdapter.notifyDataSetChanged();*/
      /*
                           // activity.rainFragment.reloadRecyclerview();

                           */
        /* try {
                                activity.rainFragment.reloadRecyclerview();
                            } catch (NullPointerException e) {
                                Log.d("rainFragmenterror",e.getMessage());
                                e.printStackTrace();
                            }*/
        /*
                boolean change = sharedchange.getBoolean("bottomsheetvalue",true);
                Log.d("bottomsheetdata"," position in adapter : "+change);
                sharedchange.edit().putBoolean("bottomsheetvalue",!change).apply();
                            //RecyclerViewAdapter.unSelected(position);

                        }
                        //  notifyDataSetChanged();
                    }
                  //  notifyDataSetChanged();
                }
                if (set2!=null) {

                for (int i=0;i<set2.size();i++) {


                        if (set2.contains(seekbarlist.get(position))) {
                            Log.d("zxcvbn", "set2");

                            seekbarlistSub.clear();
                            seekbarlistSub.addAll(set2);

                            Object[] stg = seekbarlistSub.toArray();
                            for (Object s : stg) {
                                if (seekbarlistSub.indexOf(s) != seekbarlistSub.lastIndexOf(s)) {
                                    seekbarlistSub.remove(seekbarlistSub.lastIndexOf(s));
                                }
                            }
                            if (MediaPlayerService.mediaPlayerHashMap.get(seekbarlistSub.get(i).replaceAll("_", " ")) != null)
                                MediaPlayerService.mediaPlayerHashMap.get(seekbarlistSub.get(i).replaceAll("_", " ")).stop();

                            seekbarlistSub.remove(i);
                            SharedPreferences.Editor editor = shared.edit();
                            Set<String> set3 = new HashSet<String>();
                            set3.addAll(seekbarlistSub);
                            editor.putStringSet("RELAXING_SOUNDS", set3);
                            editor.apply();

                        }
                         // notifyDataSetChanged();
                    }
                   // notifyDataSetChanged();
                }

              seekbarlist.remove(seekbarlist.get(position));
               // seekbarlist.remove(seekbarlist.get(position));
                SharedPreferences.Editor editor = shared.edit();
                Set<String> set3 = new HashSet<String>();
                set3.addAll(seekbarlist);
                editor.putStringSet("LIST_SOUNDS", set3);
                editor.apply();

                notifyDataSetChanged();

                Log.d("zxcvbn", "qwerty"+seekbarlist.size());
                if (seekbarlistSub.size()==0) {
                    view.setVisibility(View.GONE);
                    Log.d("rghnmfghny", "qwerty"+seekbarlist.size());
                   */
        /* PlayListDialog playListDialog=new PlayListDialog(mContext,formList1);
                    playListDialog.dismiss();*/
        /*
                   //activity.

                }
            }
        });*/

        Log.d("jiijkmm",""+MainActivity.VolumeHashMap.get(nameliast));
        if (MainActivity.VolumeHashMap.get(nameliast)!=null)
        holder.seekBar.setProgress(MainActivity.VolumeHashMap.get(nameliast));

       holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               try {
                   activity.seek(position, progress, nameliast);
                   Log.i("SeekBarChangedy", Integer.toString(progress));
                   Log.i("SeekBarChangedy",Integer.toString(position));
                   // nameliast=seekbarlistSub.get(position);
                   nameliast=holder.textView.getText().toString();
                   MainActivity.VolumeHashMap.put(nameliast,progress);
                   // progresssy = progress * 0.10f;
                   progresssy = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                   MediaPlayerService.mediaPlayerHashMap.get(nameliast.replaceAll("_", " ")).setVolume(progresssy,progresssy);
                   //MainActivity.volumeSeekbar.set(position,progress);
                   //String fin = chipNames1.get(position);
                   Log.d("ibnnbtom",""+nameliast);
               } catch (Exception e) {
                   e.printStackTrace();
               }

               try {
                   Set<String> setRain = shared.getStringSet("RAIN_SOUNDS", null);
                   if (setRain != null) {
                       Rainlist.clear();
                       Rainlist.addAll(setRain);

                       for (int i=0;i<Rainlist.size();i++) {
                           if (Rainlist.get(i).equals(nameliast)) {
                               RainFragment.Volumevalues.set(i,progress);
                           }
                       }
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
               try {
                   Set<String> setRelax = shared.getStringSet("RELAXING_SOUNDS", null);
                   if (setRelax != null) {
                       Relaxlist.clear();
                       Relaxlist.addAll(setRelax);

                       for (int i=0;i<Relaxlist.size();i++) {
                           if (Relaxlist.get(i).equals(nameliast)) {
                               RelaxingFragment.Volumevalues.set(i,progress);
                           }
                       }
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
               try {
                   Set<String> setNature = shared.getStringSet("NATURE_SOUNDS", null);
                   if (setNature != null) {
                       Naturelist.clear();
                       Naturelist.addAll(setNature);

                       for (int i=0;i<Naturelist.size();i++) {
                           if (Naturelist.get(i).equals(nameliast)) {
                               ThirdFragment.Volumevalues.set(i,progress);
                           }
                       }
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
               try {
                   Set<String> setWater = shared.getStringSet("WATER_SOUNDS", null);
                   if (setWater != null) {
                       Waterlist.clear();
                       Waterlist.addAll(setWater);

                       for (int i=0;i<Waterlist.size();i++) {
                           if (Waterlist.get(i).equals(nameliast)) {
                               waterFragment.Volumevalues.set(i,progress);
                           }
                       }
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }


           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });
        //  Toast.makeText(context, ""+nameChip, Toast.LENGTH_SHORT).show();
        Log.d("klklklklklk", nameliast + "" + nameChip1);


        //final SeekBar valumeControl = (SeekBar) view.findViewById(R.id.firstSeekBar);


       // if (nameliast.equalsIgnoreCase(nameChip2))
      //  {
        //audioManager=(AudioManager)SecondActivity..getSystemService(AUDIO_SERVICE);
       // int correntValue=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
      //  MAX_VOLUME=correntValue*10;
       /*     holder.seekBar.setMax(SecondActivity.MAX_VOLUME);

           // holder.seekBar.getProgressDrawable().setColorFilter(Color.parseColor(SecondActivity.seekbarcolor), PorterDuff.Mode.SRC_IN);
           // holder.seekBar.getThumb().setColorFilter(Color.parseColor(SecondActivity.seekbarcolor),PorterDuff.Mode.SRC_IN);
        holder.seekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor(SecondActivity.seekbarcolor)));
        holder.seekBar.setThumbTintList(ColorStateList.valueOf(Color.parseColor(SecondActivity.seekbarcolor)));
       // holder.seekBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD8D8D8")));
        //holder.seekBar.setSecondaryProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFD8D8D8")));

        if (ExampleBottomSheetDialog.arrPackage!=null) {

            for (int i=0;i<ExampleBottomSheetDialog.arrPackage.size();i++){

                if (nameliast.equalsIgnoreCase(ExampleBottomSheetDialog.arrPackage.get(i))) {
                    holder.seekBar.setProgress(RecyclerViewAdapter.volumeSeekbar.get(i));
                }
            }
        }*/


           /* Log.d("klklklklklk","qwerty");
            holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.i("SeekBarChangedy", Integer.toString(progress));
                    Log.i("SeekBarChangedy", Integer.toString(position));
                    nameliast=holder.textView.getText().toString();
                   // progresssy = progress * 0.10f;
                     progresssy = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                    MediaPlayerService.mediaPlayerHashMap.get(nameliast.replaceAll("_", " ")).setVolume(progresssy,progresssy);
                    RecyclerViewAdapter.volumeSeekbar.set(position,progress);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            });*/
     //   }


        return view;
    }



    public class Holder
    {
        TextView textView;
        SeekBar seekBar;
        ImageView imageView;


    }
    private void loadData(Context context) {
        // SharedPreferences
        sharedPreferences = context.getSharedPreferences("sharedpreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(context.getResources().getString(R.string.RainJsonData), null);
        /*if (json!=null)
            Log.d("shared",""+json);*/
        Type type = new TypeToken<List<sleepJSONFile>>() {}.getType();
        formList1 = gson.fromJson(json, type);
        Log.d("loaddata",""+formList1);

        if (formList1 == null) {
            formList1 = new ArrayList<>();
            Log.d("loaddata","lololo");
        }

       /* RecyclerViewAdapter myadapter=new RecyclerViewAdapter(this,lstJSONStringFile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);*/
        // setuprecycleview(formList);

    }

}
