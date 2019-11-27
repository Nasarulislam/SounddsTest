package com.example.sounddstest.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.Adapters.RecyclerViewAdapter;
import com.example.sounddstest.Adapters.RelaxingRecyclerViewAdapter;
import com.example.sounddstest.JSONStringFileAudiomix;
import com.example.sounddstest.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.sounddstest.Activitys.MainActivity.bottomNavigationView;
import static com.example.sounddstest.Activitys.MainActivity.linearLayout;
import static com.example.sounddstest.Activitys.MainActivity.timerView;
//import static com.example.sounddstest.Activitys.MainActivity.timertextView;


public class RelaxingFragment extends Fragment {

    Button soundbutton;

   // Button soundbutton;
    RecyclerView recyclerView;
    RelaxingRecyclerViewAdapter recyclerViewAdapter;
    SharedPreferences sharedPreferences;
    Drawable wrappedDrawable;
    public static List<JSONStringFileAudiomix> formList1;
    TextView relaxingFragmenttext;
    //public  static MediaPlayer mediaPlayer2;
    public static List<Integer> Volumevalues = new ArrayList<Integer>();
    public static List<Integer> Volumevposition = new ArrayList<Integer>();
    public RelaxingFragment(List<JSONStringFileAudiomix> formList1) {
        // Required empty public constructor
        this.formList1=formList1;
    }
    public RelaxingFragment() {
        // Required empty public constructor

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formList1= new ArrayList<>();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_relaxing, container, false);


        loadData();
        recyclerView=rootview.findViewById(R.id.recyclerViewRelaxing);
        recyclerViewAdapter=new RelaxingRecyclerViewAdapter(getActivity(),formList1);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_volume_up_black_list);
        ((MainActivity)getActivity()).listimageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_play_arrow_black);
        ((MainActivity)getActivity()).playImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_pause_black_24dp);
        ((MainActivity)getActivity()).pauseImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_timer_start);
        ((MainActivity)getActivity()).timerStartImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_timer_black);
        ((MainActivity)getActivity()).timerImageView.setBackground(wrappedDrawable);
        ((MainActivity) getActivity()).timerTextVieww.setTextColor(getResources().getColor(R.color.RelaxingFragmentColor));
        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_music_video_black_24dp);
        relaxingFragmenttext=(TextView)rootview.findViewById(R.id.relaxingFragmentText);
        relaxingFragmenttext.setTextColor(getResources().getColor(R.color.RelaxingFragmentColor));

        return rootview;
    }
    private void loadData() {
        // SharedPreferences
        sharedPreferences = getContext().getSharedPreferences("sharedpreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getResources().getString(R.string.RelaxingJsonData), null);
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
    public void IconColorChange(Integer color,Integer integer){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(),integer );
        wrappedDrawable= DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
    }

    @Override
    public void onResume() {
        super.onResume();
        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_volume_up_black_list);
        ((MainActivity)getActivity()).listimageView.setBackground(wrappedDrawable);

        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_play_arrow_black);
        ((MainActivity)getActivity()).playImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_pause_black_24dp);
        ((MainActivity)getActivity()).pauseImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_timer_start);
        ((MainActivity)getActivity()).timerStartImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_timer_black);
        ((MainActivity)getActivity()).timerImageView.setBackground(wrappedDrawable);

        ((MainActivity)getActivity()).Companion.setTimerView(timerView);
        timerView.setTextColor(getResources().getColor(R.color.RelaxingFragmentColor));
        IconColorChange(getResources().getColor(R.color.RelaxingFragmentColor),R.drawable.ic_music_video_black_24dp);
    }
}
