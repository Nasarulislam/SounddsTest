package com.example.sounddstest.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.Adapters.RecyclerViewAdapter;
import com.example.sounddstest.Adapters.customListviewAdapter;
import com.example.sounddstest.JSONStringFileAudiomix;
import com.example.sounddstest.PlayListDialog;
import com.example.sounddstest.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.example.sounddstest.Activitys.MainActivity.timerView;
//import static com.example.sounddstest.Activitys.MainActivity.timertextView;


public class RainFragment extends Fragment {


    public static List<Integer> Volumevalues = new ArrayList<Integer>();
    public static List<Integer> Volumevposition = new ArrayList<Integer>();
    //  lateinit var mediaPlayerHashMap: HashMap<String, MediaPlayer>
    public static HashMap<String, Integer> volumeHashMap;
    public static List<JSONStringFileAudiomix> formList1;
    public static List<String> rainPlayList;
    Button soundbutton;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    SharedPreferences sharedPreferences;
    View rootView;
    SharedPreferences shared;
    Set<String> set;
    Drawable wrappedDrawable;
    TextView rainFragmentText;

    public RainFragment(List<JSONStringFileAudiomix> formList1) {
        // Required empty public constructor
        this.formList1 = formList1;
    }

    public RainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formList1 = new ArrayList<>();
        shared = getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
        set = shared.getStringSet("RAIN_SOUNDS", null);
        rainPlayList = new ArrayList<>();
        volumeHashMap = new HashMap<>();

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_rain, container, false);

        shared = getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
        set = shared.getStringSet("RAIN_SOUNDS", null);
        rainPlayList = new ArrayList<>();
        loadData();

        // Recyclerview
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), formList1, (MainActivity) getActivity());
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_music_video_black_24dp);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        // Mian Activity Color change
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_volume_up_black_list);
        ((MainActivity) getActivity()).listimageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_play_arrow_black);
        ((MainActivity) getActivity()).playImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_pause_black_24dp);
        ((MainActivity) getActivity()).pauseImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_timer_start);
        ((MainActivity) getActivity()).timerStartImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_timer_black);
        ((MainActivity) getActivity()).timerImageView.setBackground(wrappedDrawable);
        ((MainActivity) getActivity()).timerTextVieww.setTextColor(getResources().getColor(R.color.RainFragmentColor));
        rainFragmentText = (TextView) rootView.findViewById(R.id.rainFragmentText);
        rainFragmentText.setTextColor(getResources().getColor(R.color.RainFragmentColor));
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_grain_black_24dp);
      //  holder.withseekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));

//        customListviewAdapter.seekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));
  //      customListviewAdapter.seekBar.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));

      //  holder.withseekBar.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));

        return rootView;
    }

    public void setRecyclerView() {

    }
// Data load from saved data
    private void loadData() {
        // SharedPreferences
        sharedPreferences = getContext().getSharedPreferences("sharedpreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getResources().getString(R.string.RainJsonData), null);
        /*if (json!=null)
            Log.d("shared",""+json);*/
        Type type = new TypeToken<List<JSONStringFileAudiomix>>() {
        }.getType();
        formList1 = gson.fromJson(json, type);
        Log.d("loaddata", "" + formList1);

        if (formList1 == null) {
            formList1 = new ArrayList<>();
            Log.d("loaddata", "lololo");
        }
        // setuprecycleview(formList);

    }

    // Icon color change Function
    public void IconColorChange(Integer color, Integer integer) {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), integer);
        wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(wrappedDrawable, color);
            Log.d("hghgcghv", "o");
        } else {
            Log.d("hghgcghv", "p");
            unwrappedDrawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void onResume() {
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_volume_up_black_list);
        ((MainActivity) getActivity()).listimageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_play_arrow_black);
        ((MainActivity) getActivity()).playImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_pause_black_24dp);
        ((MainActivity) getActivity()).pauseImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_timer_start);
        ((MainActivity) getActivity()).timerStartImageView.setBackground(wrappedDrawable);
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_timer_black);
        ((MainActivity) getActivity()).timerImageView.setBackground(wrappedDrawable);

        timerView.setTextColor(getResources().getColor(R.color.RainFragmentColor));
        ((MainActivity) getActivity()).Companion.setTimerView(timerView);

        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_grain_black_24dp);
        super.onResume();
    }
}
