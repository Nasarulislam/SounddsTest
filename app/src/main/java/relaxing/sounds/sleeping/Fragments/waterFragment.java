package relaxing.sounds.sleeping.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import relaxing.sounds.sleeping.Activitys.MainActivity;
import relaxing.sounds.sleeping.Adapters.WaterRecyclerViewAdapter;
//import com.example.Activitys.R;
import relaxing.sounds.sleeping.sleepJSONFile;
import relaxing.sounds.sleeping.R;
import com.google.android.gms.ads.AdListener;
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


public class waterFragment extends Fragment {


    public static List<Integer> Volumevalues = new ArrayList<Integer>();
    public static List<Integer> Volumevposition = new ArrayList<Integer>();
    //  lateinit var mediaPlayerHashMap: HashMap<String, MediaPlayer>
    public static HashMap<String, Integer> volumeHashMap;
    public static List<sleepJSONFile> formList1;
    public static List<String> rainPlayList;
    Button soundbutton;
    RecyclerView recyclerView;
    WaterRecyclerViewAdapter recyclerViewAdapter;
    SharedPreferences sharedPreferences;
    View rootView;
    SharedPreferences shared;
    Set<String> set;
    Drawable wrappedDrawable;
    TextView rainFragmentText;

    private List<String> seekbarlistsub = new ArrayList<>();

    public waterFragment(List<sleepJSONFile> formList1) {
        // Required empty public constructor
        this.formList1 = formList1;
    }

    public waterFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formList1 = new ArrayList<>();
        shared = getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
        sharedPreferences = getContext().getSharedPreferences("prefs.xml",MODE_PRIVATE);
        set = shared.getStringSet("WATER_SOUNDS", null);
        rainPlayList = new ArrayList<>();
        volumeHashMap = new HashMap<>();
       // try {
            Boolean ispurchased=true;
            //((MainActivity)getActivity()).getOpenPurchase();
            if (sharedPreferences.getBoolean("purchased", false) || sharedPreferences.getBoolean("monthlySubscribed", false) || sharedPreferences.getBoolean("sixMonthSubscribed", false)) {
                ispurchased = false;
            }
            else {
                Log.d("jhjhbjhbjhbb", "" + ispurchased);
                // if (ispurchased) {
                if (((MainActivity) getActivity()).getMInterstitialAd() != null)
                    ((MainActivity) getActivity()).getMInterstitialAd().show();

                ((MainActivity) getActivity()).getMInterstitialAd().setAdListener(new AdListener() {
                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                        ((MainActivity) getActivity()).stopService(((MainActivity) getActivity()).floatingIntent);
                    }
                });
            }

        /*} catch (Exception e) {
                e.printStackTrace();
            }*/

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_water_sounds, container, false);

        shared = getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
        set = shared.getStringSet("WATER_SOUNDS", null);
        rainPlayList = new ArrayList<>();

        try {
            loadData();

            // Recyclerview
            recyclerView = rootView.findViewById(R.id.forthRecyclerView);
            recyclerViewAdapter = new WaterRecyclerViewAdapter(getActivity(), formList1, (MainActivity) getActivity());
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_music_video_black_24dp);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);

            MainActivity.Companion.setWaterRecyclerViewAdapter(recyclerViewAdapter);
            MainActivity.Companion.setListLayoutManager(mLayoutManager);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        // Mian Activity Color change
        try {
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_volume_up_black_list);
            ((MainActivity) getActivity()).listimageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_play_arrow_black);
            ((MainActivity) getActivity()).playImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_pause_black_24dp);
            ((MainActivity) getActivity()).pauseImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_timer_start);
            ((MainActivity) getActivity()).timerStartImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_timer_black);
            ((MainActivity) getActivity()).timerImageView.setBackground(wrappedDrawable);
            ((MainActivity) getActivity()).timerTextVieww.setTextColor(getResources().getColor(R.color.waterFragmentColor));
            rainFragmentText = (TextView) rootView.findViewById(R.id.waterFragmentText);
            rainFragmentText.setTextColor(getResources().getColor(R.color.waterFragmentColor));
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_grain_black_24dp);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        //  holder.withseekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));

//        customListviewAdapter.seekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));
        //      customListviewAdapter.seekBar.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));

        //  holder.withseekBar.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#CA5324")));
        ((MainActivity) getActivity()).linearLayoutMain.setBackgroundColor(getActivity().getResources().getColor(R.color.waterFragmentBG));

        try {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.waterFragmentColor));
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
    // Data load from saved data
    private void loadData() {
        // SharedPreferences
        try {
            Log.d("loaddata", "l" + formList1);
            sharedPreferences = getContext().getSharedPreferences("sharedpreferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(getResources().getString(R.string.Water), null);
        /*if (json!=null)
            Log.d("shared",""+json);*/
            Type type = new TypeToken<List<sleepJSONFile>>() {
            }.getType();
            formList1 = gson.fromJson(json, type);
            Log.d("loaddajjbjbtawater", json+"///" + formList1);

            if (formList1 == null) {
                formList1 = new ArrayList<>();
                Log.d("loaddata", "lololo");
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
        try {
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), integer);
            wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                DrawableCompat.setTint(wrappedDrawable, color);
                Log.d("hghgcghv", "o");
            } else {
                Log.d("hghgcghv", "p");
                unwrappedDrawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        try {
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_volume_up_black_list);
            ((MainActivity) getActivity()).listimageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_play_arrow_black);
            ((MainActivity) getActivity()).playImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_pause_black_24dp);
            ((MainActivity) getActivity()).pauseImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_timer_start);
            ((MainActivity) getActivity()).timerStartImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_timer_black);
            ((MainActivity) getActivity()).timerImageView.setBackground(wrappedDrawable);

            timerView.setTextColor(getResources().getColor(R.color.waterFragmentColor));
            ((MainActivity) getActivity()).Companion.setTimerView(timerView);

            IconColorChange(getResources().getColor(R.color.waterFragmentColor), R.drawable.ic_grain_black_24dp);
            ((MainActivity) getActivity()).linearLayoutMain.setBackgroundColor(getActivity().getResources().getColor(R.color.waterFragmentBG));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        super.onResume();
    }
}
