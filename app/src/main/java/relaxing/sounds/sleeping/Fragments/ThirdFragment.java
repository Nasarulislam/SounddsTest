package relaxing.sounds.sleeping.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import relaxing.sounds.sleeping.Activitys.MainActivity;
import relaxing.sounds.sleeping.Adapters.NatureRecyclerViewAdapter;
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


public class ThirdFragment extends Fragment {

    Button soundbutton;

    // Button soundbutton;
    RecyclerView recyclerView;
    NatureRecyclerViewAdapter recyclerViewAdapter;
    SharedPreferences sharedPreferences;
    Drawable wrappedDrawable;
    public static List<sleepJSONFile> formList1;
    TextView relaxingFragmenttext;
    //public  static MediaPlayer mediaPlayer2;
    public static List<Integer> Volumevalues = new ArrayList<Integer>();
    public static List<Integer> Volumevposition = new ArrayList<Integer>();

    public static HashMap<String, Integer> volumeHashMap;
    public static List<String> rainPlayList;

    private List<String> seekbarlistsub = new ArrayList<>();
    SharedPreferences shared;
    public ThirdFragment(List<sleepJSONFile> formList1) {
        // Required empty public constructor
        this.formList1=formList1;
    }
    public ThirdFragment() {
        // Required empty public constructor

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formList1= new ArrayList<>();

        rainPlayList = new ArrayList<>();
        volumeHashMap = new HashMap<>();

        try {
            if (((MainActivity) getActivity()).getOpenPurchase()) {
                if (((MainActivity) getActivity()).getMInterstitialAd() != null)
                    ((MainActivity) getActivity()).getMInterstitialAd().show();

                ((MainActivity) getActivity()).getMInterstitialAd().setAdListener(new AdListener(){
                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                        ((MainActivity)getActivity()).stopService(((MainActivity)getActivity()).floatingIntent);
                    }});

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_city_sounds, container, false);

        shared = getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
        try {
            loadData();
            recyclerView=rootview.findViewById(R.id.thirdRecyclerView);
            recyclerViewAdapter=new NatureRecyclerViewAdapter(getActivity(),formList1,(MainActivity) getActivity());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);

            MainActivity.Companion.setNatureRecyclerViewAdapter(recyclerViewAdapter);
            MainActivity.Companion.setListLayoutManager(mLayoutManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_volume_up_black_list);
            ((MainActivity)getActivity()).listimageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_play_arrow_black);
            ((MainActivity)getActivity()).playImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_pause_black_24dp);
            ((MainActivity)getActivity()).pauseImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_timer_start);
            ((MainActivity)getActivity()).timerStartImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_timer_black);
            ((MainActivity)getActivity()).timerImageView.setBackground(wrappedDrawable);
            ((MainActivity) getActivity()).timerTextVieww.setTextColor(getResources().getColor(R.color.CityFragmentColor));
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_music_video_black_24dp);
            relaxingFragmenttext=(TextView)rootview.findViewById(R.id.CityFragmentColor);
            relaxingFragmenttext.setTextColor(getResources().getColor(R.color.CityFragmentColor));
            ((MainActivity) getActivity()).linearLayoutMain.setBackgroundColor(getActivity().getResources().getColor(R.color.CityFragmentBG));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        try {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.CityFragmentColor));
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
        return rootview;
    }
    private void loadData() {
        // SharedPreferences
        try {
            sharedPreferences = getContext().getSharedPreferences("sharedpreferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(getResources().getString(R.string.Nature), null);
        /*if (json!=null)
            Log.d("shared",""+json);*/
            Type type = new TypeToken<List<sleepJSONFile>>() {}.getType();
            formList1 = gson.fromJson(json, type);
            Log.d("loaddata",""+formList1);

            if (formList1 == null) {
                formList1 = new ArrayList<>();
                Log.d("loaddata","lololo");
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
       /* RecyclerViewAdapter myadapter=new RecyclerViewAdapter(this,lstJSONStringFile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);*/
        // setuprecycleview(formList);

    }
    public void IconColorChange(Integer color,Integer integer){
        try {
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(),integer );
            wrappedDrawable= DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_volume_up_black_list);
            ((MainActivity)getActivity()).listimageView.setBackground(wrappedDrawable);

            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_play_arrow_black);
            ((MainActivity)getActivity()).playImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_pause_black_24dp);
            ((MainActivity)getActivity()).pauseImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_timer_start);
            ((MainActivity)getActivity()).timerStartImageView.setBackground(wrappedDrawable);
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_timer_black);
            ((MainActivity)getActivity()).timerImageView.setBackground(wrappedDrawable);

            ((MainActivity)getActivity()).Companion.setTimerView(timerView);
            timerView.setTextColor(getResources().getColor(R.color.CityFragmentColor));
            IconColorChange(getResources().getColor(R.color.CityFragmentColor),R.drawable.ic_music_video_black_24dp);
            ((MainActivity) getActivity()).linearLayoutMain.setBackgroundColor(getActivity().getResources().getColor(R.color.CityFragmentBG));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }
}
