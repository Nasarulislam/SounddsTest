package relaxing.sounds.sleeping.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import relaxing.sounds.sleeping.Activitys.MainActivity;
import relaxing.sounds.sleeping.Adapters.RecyclerViewAdapter;
//import com.example.Activitys.R;
import relaxing.sounds.sleeping.Services.MediaPlayerService;
import relaxing.sounds.sleeping.sleepJSONFile;
import relaxing.sounds.sleeping.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static relaxing.sounds.sleeping.Activitys.MainActivity.timerView;
//import static com.example.sounddstest.Activitys.MainActivity.timertextView;


public class RainFragment extends Fragment {


    public static List<Integer> Volumevalues = new ArrayList<Integer>();
    public static List<Integer> Volumevposition = new ArrayList<Integer>();
    //  lateinit var mediaPlayerHashMap: HashMap<String, MediaPlayer>
    public static HashMap<String, Integer> volumeHashMap;
    public static List<sleepJSONFile> formList1;
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
    private Boolean onBoardLoaded = false;


    private List<String> seekbarlistsub = new ArrayList<>();
    public RainFragment(List<sleepJSONFile> formList1) {
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
        sharedPreferences=getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
        set = shared.getStringSet("RAIN_SOUNDS", null);
        rainPlayList = new ArrayList<>();

        onBoardLoaded = sharedPreferences.getBoolean("LoadDataOnBoard", false);

        ProgressBar progressBar=rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

       // refreshingView();
        try {
            if (onBoardLoaded) {
                progressBar.setVisibility(View.VISIBLE);
                //progressBar.setBackgroundColor(getContext().getResources().getColor(R.color.RainFragmentColor));
                Log.d("loaddataR","LoadDataOnBoard true");
                sharedPreferences.edit().putBoolean("LoadDataOnBoard", false).apply();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                        recyclerView = rootView.findViewById(R.id.recyclerView);
                        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), formList1, (MainActivity) getActivity());
                        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_music_video_black_24dp);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        progressBar.setVisibility(View.GONE);

                        Toast toast = new Toast(getContext());
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0, 0);
                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view1 = inflater.inflate(R.layout.addsoundstoast, null);
                        toast.setView(view1);
                        toast.show();

                        MainActivity.Companion.setRecyclerViewAdapter(recyclerViewAdapter);

                    }
                }, 3000);
            }
            else {
                Log.d("loaddataR","LoadDataOnBoard false");
                loadData();
                recyclerView = rootView.findViewById(R.id.recyclerView);
                recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), formList1, (MainActivity) getActivity());
                IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_music_video_black_24dp);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);

                MainActivity.Companion.setRecyclerViewAdapter(recyclerViewAdapter);
                MainActivity.Companion.setListLayoutManager(mLayoutManager);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


        Log.d("loaddataR","done");
      /*  recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), formList1, (MainActivity) getActivity());
        IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_music_video_black_24dp);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);*/

        // Mian Activity Color change
        try {
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

            ((MainActivity) getActivity()).linearLayoutMain.setBackgroundColor(getActivity().getResources().getColor(R.color.RainFragmentBG));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        //  holder.withseekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));

//        customListviewAdapter.seekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));
  //      customListviewAdapter.seekBar.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));

      //  holder.withseekBar.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));

        try {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.RainFragmentColor));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        try {
            Set<String> set5 = shared.getStringSet("LIST_SOUNDS", null);
            if (set5 != null)
                seekbarlistsub.addAll(set5);

            Log.d("inniinn", "" + seekbarlistsub);
            if (seekbarlistsub.size() > 0) {
                ((MainActivity) getActivity()).cardView.setVisibility(View.VISIBLE);
              //  MediaPlayerService.hashmapget(getContext());
                // cardView.setVisibility(View.VISIBLE);
            } else {
                ((MainActivity) getActivity()).cardView.setVisibility(View.GONE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public void setRecyclerView() {

    }
    public void refreshingView(){

    }

// Data load from saved data
    public void loadData() {
        // SharedPreferences
        try {
            Log.d("formList", "here rain frag" );
            Log.d("loaddataR", "l" + formList1);
            sharedPreferences = getContext().getSharedPreferences("sharedpreferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(getResources().getString(R.string.Rain), null);
        /*if (json!=null)
            Log.d("shared",""+json);*/
            Type type = new TypeToken<List<sleepJSONFile>>() {
            }.getType();
            formList1 = gson.fromJson(json, type);
            Log.d("loaddataR", "" + formList1);

            if (formList1 == null) {
                formList1 = new ArrayList<>();
                Log.d("loaddataR", "lololo");
            }else {
               // IconColorChange(getResources().getColor(R.color.RainFragmentColor), R.drawable.ic_music_video_black_24dp);
               // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
    //            recyclerView.setLayoutManager(mLayoutManager);
              //  recyclerView.setAdapter(recyclerViewAdapter);
              //  recyclerViewAdapter.notifyDataSetChanged();


            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
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
        try {
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
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        // recyclerView.setAdapter(recyclerViewAdapter);
//        recyclerViewAdapter.notifyDataSetChanged();
        super.onResume();
    }
}
