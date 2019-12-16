package relaxing.sounds.sleeping.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import relaxing.sounds.sleeping.Activitys.MainActivity;
import relaxing.sounds.sleeping.OnBoarding.MainActivityOnboarding;
import relaxing.sounds.sleeping.OnBoarding.PrivacyAndTerms;
//import com.example.Activitys.R;
import relaxing.sounds.sleeping.R;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {


    private static final String TAG = "SettingsFragment";
    View rootView;
  //  BaseValues mBaseValues;
    Boolean automayBool = true;
    TextView highqualityText1;
    SharedPreferences sharedPreferences;
    boolean isPurchased =false;
    TextView notificationOnOff;
    boolean notification = true;
    SharedPreferences sharedPrefOverlay;
    boolean hdout;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("prefs.xml",MODE_PRIVATE);
        isPurchased = sharedPreferences.getBoolean("purchased",false);
         sharedPrefOverlay=getContext().getSharedPreferences("App_settings", MODE_PRIVATE);
        notification = sharedPreferences.getBoolean("notification",true);

        CardView tellFriends = (CardView) rootView.findViewById(R.id.tellFriendRelative);
        CardView privacy = (CardView) rootView.findViewById(R.id.privacyPolicy);
        CardView terms = (CardView) rootView.findViewById(R.id.TermsOfUse);
        CardView instagramShare = (CardView) rootView.findViewById(R.id.InstagramRelative);
        final CardView rateApp = (CardView) rootView.findViewById(R.id.RateApp);
        CardView help = (CardView) rootView.findViewById(R.id.NeedHelpRelative);
        CardView moreApps = (CardView) rootView.findViewById(R.id.moreappsRelative);
        CardView fbPage = (CardView) rootView.findViewById(R.id.fbShare);
        CardView removeAds = (CardView) rootView.findViewById(R.id.RemoveAds);
        CardView autoMay = (CardView) rootView.findViewById(R.id.autoMay);
        CardView changePrefs = rootView.findViewById(R.id.changePrefs);
        CardView stepTrack = rootView.findViewById(R.id.stepTrack);
        CardView highQuality = null;
        try {
            highQuality = (CardView) rootView.findViewById(R.id.highquality);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PackageInfo pInfo = null;
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            String versionNumber = "Version " + pInfo.versionName;
            TextView version = (TextView) rootView.findViewById(R.id.version);
            version.setText(versionNumber);
            version.setTypeface(regular_getTypeface(getContext()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((MainActivity) getActivity()).linearLayoutMain.setBackgroundColor(getActivity().getResources().getColor(R.color.settingscolor));

        ((MainActivity) getActivity()).cardView.setVisibility(View.GONE);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.onboarding));

        try {
            TextView privacyPolicyText1 = (TextView) rootView.findViewById(R.id.privacyPolicyText1);
            privacyPolicyText1.setTypeface(regular_getTypeface(getContext()));

            TextView TermsOfUseText1 = (TextView) rootView.findViewById(R.id.TermsOfUseText1);
            TermsOfUseText1.setTypeface(regular_getTypeface(getContext()));

            TextView text1 = (TextView) rootView.findViewById(R.id.text1);
            text1.setTypeface(regular_getTypeface(getContext()));

            TextView fbSharetext1 = (TextView) rootView.findViewById(R.id.fbSharetext1);
            fbSharetext1.setTypeface(regular_getTypeface(getContext()));

            TextView text2 = (TextView) rootView.findViewById(R.id.text2);
            text2.setTypeface(regular_getTypeface(getContext()));

           /* TextView textTag = (TextView) rootView.findViewById(R.id.textTag);
            textTag.setTypeface(regular_getTypeface(getContext()));*/

            TextView text5 = (TextView) rootView.findViewById(R.id.text5);
            text5.setTypeface(regular_getTypeface(getContext()));

            TextView text3 = (TextView) rootView.findViewById(R.id.text3);
            text3.setTypeface(regular_getTypeface(getContext()));

            TextView text4 = (TextView) rootView.findViewById(R.id.text4);
            text4.setTypeface(regular_getTypeface(getContext()));

            TextView RemoveAdsText1 = (TextView) rootView.findViewById(R.id.RemoveAdsText);
            RemoveAdsText1.setTypeface(regular_getTypeface(getContext()));

            TextView autoMayText1 = (TextView) rootView.findViewById(R.id.autoMayText);
            autoMayText1.setTypeface(regular_getTypeface(getContext()));

            TextView changePrefsText1 = (TextView) rootView.findViewById(R.id.changePrefsText);
            changePrefsText1.setTypeface(regular_getTypeface(getContext()));

            TextView stepTrackText1 = (TextView) rootView.findViewById(R.id.stepTrackText);
            stepTrackText1.setTypeface(regular_getTypeface(getContext()));


            notificationOnOff = rootView.findViewById(R.id.autoMayarrow);
            notificationOnOff.setTypeface(regular_getTypeface(getContext()));

           /* try {
                if (BaseValues.premium)
                    RemoveAdsText1.setText(getString(R.string.premium_user));
            } catch (Exception e) {
                e.printStackTrace();
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isPurchased){
            removeAds.setVisibility(View.GONE);
        }
        removeAds.setOnClickListener(v -> {
            /*Intent intent = new Intent(getContext(), MainActivity.class);
            intent.putExtra("fromCardView","fromCardView");
            getContext().startActivity(intent);
            Log.d("clickedcard","ads");*/
            sharedPrefOverlay.edit().putBoolean("sharedPrefOverlay", true).apply();

            Intent premiumIntent = new Intent(getContext(), MainActivityOnboarding.class);
            premiumIntent.putExtra("fromOverlayPage", "fromOverlayPage");
            premiumIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(premiumIntent);
            //getContext().stopSelf();
            ((MainActivity)getActivity()).stopService(((MainActivity)getActivity()).floatingIntent);
        });

        if (notification){
            notificationOnOff.setText("OFF");
        }
        else {
            notificationOnOff.setText("ON");
        }

        /*stepTrack.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),StepCalculator.class);
            intent.putExtra("privacy","privacy");
            startActivity(intent);
        });*/

        autoMay.setOnClickListener(v -> {

            if (!sharedPreferences.getBoolean("notification",true)){
                notificationOnOff.setText("OFF");
                sharedPreferences.edit().putBoolean("notification",true).apply();

               // Log.d("notificatonis","f "+sharedPreferences.getBoolean("notification",true));
            }
            else if (sharedPreferences.getBoolean("notification",true)){
                notificationOnOff.setText("ON");
                sharedPreferences.edit().putBoolean("notification",false).apply();

             //   Log.d("notificatonis","t "+sharedPreferences.getBoolean("notification",true));
            }
            Log.d("clickedcard","notification");
        });

        changePrefs.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),MainActivityOnboarding.class);
            intent.putExtra("changePrefs","changePrefs");
            getContext().startActivity(intent);
        });

        privacy.setOnClickListener(v -> {

            sharedPrefOverlay.edit().putBoolean("sharedPrefOverlay", true).apply();

            Intent intent = new Intent(getContext(), PrivacyAndTerms.class);
            intent.putExtra("privacy","privacy");
            startActivity(intent);

            Log.d("clickedcard","pirvacy policy");
          //  sharedPrefOverlay.edit().putBoolean("sharedPrefOverlay", false).apply();
        });

        terms.setOnClickListener(v -> {
            sharedPrefOverlay.edit().putBoolean("sharedPrefOverlay", true).apply();
            Intent intent = new Intent(getContext(),PrivacyAndTerms.class);
            intent.putExtra("termsofuse","termsofuse");
            startActivity(intent);
            Log.d("clickedcard","terms of use");
        });

        tellFriends.setOnClickListener(v -> {
            sharedPrefOverlay.edit().putBoolean("sharedPrefOverlay", true).apply();
            Log.d("clickedcard","tell friends");

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Enjoy this awesome app");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id="+getContext().getResources().getString(R.string.application_id));
            startActivity(Intent.createChooser(sharingIntent, "Enjoy this awesome app"));
        });

        rateApp.setOnClickListener(v -> {
            sharedPrefOverlay.edit().putBoolean("sharedPrefOverlay", true).apply();
            Log.d("clickedcard","rate app");
            Toast.makeText(getContext(), "Please add review in Google Play.", Toast.LENGTH_SHORT).show();


            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getContext().getResources().getString(R.string.application_id));// + getContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id="+getContext().getResources().getString(R.string.application_id))));
                      //  Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
            }
        });

        moreApps.setOnClickListener(v -> {
            try {
                sharedPrefOverlay.edit().putBoolean("sharedPrefOverlay", true).apply();
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id=Riafy+Technologies")));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            Log.d("clickedcard","more apps");
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("rippleadding","outside");
            try {
                Log.d("rippleadding","inside");
                privacy.setForeground(getResources().getDrawable(R.drawable.ripple));
                terms.setForeground(getResources().getDrawable(R.drawable.ripple));
                tellFriends.setForeground(getResources().getDrawable(R.drawable.ripple));
                instagramShare.setForeground(getResources().getDrawable(R.drawable.ripple));
                help.setForeground(getResources().getDrawable(R.drawable.ripple));
                moreApps.setForeground(getResources().getDrawable(R.drawable.ripple));
                rateApp.setForeground(getResources().getDrawable(R.drawable.ripple, null));
                fbPage.setForeground(getResources().getDrawable(R.drawable.ripple, null));
                removeAds.setForeground(getResources().getDrawable(R.drawable.ripple));
                try {
                    autoMay.setForeground(getResources().getDrawable(R.drawable.ripple));
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    highQuality.setForeground(getResources().getDrawable(R.drawable.ripple));
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Log.d("rippleadding","error");
                e.printStackTrace();
            }

        }

        return rootView;
    }

    public Typeface regular_getTypeface(Context mContext) {

        return Typeface.createFromAsset(mContext.getAssets(),
                "Roboto-Regular.ttf");

    }

}
