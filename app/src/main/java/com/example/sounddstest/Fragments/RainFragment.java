package com.example.sounddstest.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.JSONStringFileAudiomix;
import com.example.sounddstest.R;
import com.example.sounddstest.Adapters.RecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kotlin.Unit;

import static android.content.Context.MODE_PRIVATE;
import static com.example.sounddstest.Activitys.MainActivity.bottomNavigationView;

import static com.example.sounddstest.Activitys.MainActivity.linearLayout;
import static com.example.sounddstest.Activitys.MainActivity.timerView;
//import static com.example.sounddstest.Activitys.MainActivity.timertextView;


public class RainFragment extends Fragment {


    Button soundbutton;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    SharedPreferences sharedPreferences;
    View rootView;
    SharedPreferences shared;
    Set<String> set;
    Drawable wrappedDrawable;
    TextView rainFragmentText;
    public static List<Integer> Volumevalues = new ArrayList<Integer>();
    public static List<Integer> Volumevposition = new ArrayList<Integer>();

  //  lateinit var mediaPlayerHashMap: HashMap<String, MediaPlayer>
    public static HashMap<String, Integer>volumeHashMap;

    public static List<JSONStringFileAudiomix> formList1;
    public static List<String> rainPlayList;
    public RainFragment(List<JSONStringFileAudiomix> formList1) {
        // Required empty public constructor
        this.formList1=formList1;
    }
    public RainFragment(){

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formList1= new ArrayList<>();
       shared= getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
     set = shared.getStringSet("RAIN_SOUNDS", null);
     rainPlayList=new ArrayList<>();
        volumeHashMap=new HashMap<>();
       /* formList1.add("Red");
        formList1.add("Green");
        formList1.add("blue");
        formList1.add("Red");
        formList1.add("Green");
        formList1.add("blue");
        formList1.add("Red");
        formList1.add("Green");
        formList1.add("blue");
        formList1.add("Red");
        formList1.add("Green");
        formList1.add("blue");*/

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView=inflater.inflate(R.layout.fragment_rain, container, false);
      //  soundbutton = rootView.findViewById(R.id.sound1);
      //  mediaPlayer1 = MediaPlayer.create(rootView.getCoRntext(), R.raw.ambience_campfire);
       // bottomNavigationView.setBackground(getResources().getDrawable(R.color.RainFragmentColor));
        //linearLayout.setBackground(getResources().getDrawable(R.color.RainFragmentColor));
        //imageView.setColorFilter(R.color.RainFragmentColor);
        //imageView1.setColorFilter(R.color.RainFragmentColor);
        //imageView2.setColorFilter(R.color.RainFragmentColor);
        shared= getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
        set = shared.getStringSet("RAIN_SOUNDS", null);
        rainPlayList=new ArrayList<>();
       // rootView.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
        loadData();
        //reloadRecyclerview((MainActivity) getActivity());
        recyclerView=rootView.findViewById(R.id.recyclerView);
        recyclerViewAdapter=new RecyclerViewAdapter(getActivity(),formList1,(MainActivity)getActivity());

      //  recyclerViewAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

       /* Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(),R.drawable.ic_volume_up_black_list );
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.RED);*/
        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_volume_up_black_list);
      //  MainActivity.listimageView.setBackground(wrappedDrawable);
        ((MainActivity)getActivity()).listimageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_play_arrow_black);
        ((MainActivity)getActivity()).playImageView.setBackground(wrappedDrawable);
       // MainActivity.playImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_pause_black_24dp);
        //MainActivity.pauseImageView.setBackground(wrappedDrawable);
        ((MainActivity)getActivity()).pauseImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_timer_start);
        //MainActivity.timerStartImageView.setBackground(wrappedDrawable);
        ((MainActivity)getActivity()).timerStartImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_timer_black);
       // MainActivity.timerImageView.setBackground(wrappedDrawable);
        ((MainActivity)getActivity()).timerImageView.setBackground(wrappedDrawable);
       // IconColorChange(Color.RED,R.drawable.);
        //MainActivity.timerTextView.setTextColor(getResources().getColor(R.color.RainFragmentColor));
        ((MainActivity) getActivity()).timerTextVieww.setTextColor(getResources().getColor(R.color.RainFragmentColor));
      //  timertextView.setTextColor(getResources().getColor(R.color.RainFragmentColor));
       /* int colorInt = getResources().getColor(R.color.RelaxingFragmentColor);
        TextView textView=rootView.findViewById(R.id.relaxingFragmentText);
        textView.setText();
        textView.setTextColor(R.color.RainFragmentColor);
        ((MainActivity)getActivity()).Companion.setTimertextView(textView);*/

        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_music_video_black_24dp);
        rainFragmentText=(TextView)rootView.findViewById(R.id.rainFragmentText);
        rainFragmentText.setTextColor(getResources().getColor(R.color.RainFragmentColor));

        //RecyclerViewAdapter.MyViewHolder holder=new RecyclerViewAdapter.MyViewHolder(rootView);
       // holder.itemView.setBackground(wrappedDrawable);
       // RecyclerViewAdapter.
        /*soundbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            MainActivity.mediaPlayer.start();
            }
        });*/

        return rootView;
    }

    public void setRecyclerView(){

    }
    private void loadData() {
        // SharedPreferences
        sharedPreferences = getContext().getSharedPreferences("sharedpreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getResources().getString(R.string.RainJsonData), null);
        /*if (json!=null)
            Log.d("shared",""+json);*/
        Type type = new TypeToken<List<JSONStringFileAudiomix>>() {}.getType();
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
    public void reloadRecyclerview(MainActivity activity){

      //  recyclerViewAdapter.notifyDataSetChanged();
        rainPlayList=new ArrayList<>();
        shared= activity.getSharedPreferences("App_settings", MODE_PRIVATE);
        Set<String> set = new HashSet<String>();
        set= shared.getStringSet("RAIN_SOUNDS", null);
        Log.d("thtyty","1"+set);
        rainPlayList.addAll(set);
        Log.d("thtyty",rainPlayList+"1"+set);
        //recyclerViewAdapter.setItems(rainPlayList);
        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(activity,formList1,(MainActivity)getActivity());
        recyclerViewAdapter.notifyDataSetChanged();

       // recyclerViewAdapter.set
        /*RecyclerView recyclerView=rootView.findViewById(R.id.recyclerView);
        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(activity,formList1);
        recyclerViewAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);*/

    }

    public void IconColorChange(Integer color,Integer integer){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(),integer );
         wrappedDrawable= DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
    }

    @Override
    public void onResume() {
        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_volume_up_black_list);
        //  MainActivity.listimageView.setBackground(wrappedDrawable);
        ((MainActivity)getActivity()).listimageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_play_arrow_black);
        ((MainActivity)getActivity()).playImageView.setBackground(wrappedDrawable);
        // MainActivity.playImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_pause_black_24dp);
        //MainActivity.pauseImageView.setBackground(wrappedDrawable);
        ((MainActivity)getActivity()).pauseImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_timer_start);
        //MainActivity.timerStartImageView.setBackground(wrappedDrawable);
        ((MainActivity)getActivity()).timerStartImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor),R.drawable.ic_timer_black);
        // MainActivity.timerImageView.setBackground(wrappedDrawable);
        ((MainActivity)getActivity()).timerImageView.setBackground(wrappedDrawable);

        // IconColorChange(Color.RED,R.drawable.);
        //((MainActivity)getActivity()).timerTextVieww.setTextColor(getResources().getColor(R.color.RainFragmentColor));
       timerView.setTextColor(getResources().getColor(R.color.RainFragmentColor));
        ((MainActivity)getActivity()).Companion.setTimerView(timerView);

        super.onResume();
    }
}
