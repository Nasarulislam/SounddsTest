package relaxing.sounds.sleeping.OnBoarding;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import relaxing.sounds.sleeping.Activitys.MainActivity;
//import com.example.Activitys.R;
import relaxing.sounds.sleeping.R;

import android.provider.Settings.Secure;

import java.util.HashMap;
import java.util.Locale;

public class MainActivityOnboarding extends AppCompatActivity {

    WebView webView;
    Button button;
    TextView connectInternetText;
    public static Context mContext;
    public static Activity mActivity;
    GetPremium getPremium;
    SharedPreferences sharedPreferences;
    //ArrayList<prices> premiumPrices;
    HashMap<String,String> premiumPrices;
    public static boolean fromOverlay=false;
    PackageInfo pInfo = null;
    int versionCode=0;
    String version="";
    String countryCodeValue="";
    String devid="";
    String country="";
    //String urlVal="file:///android_asset/onboarding.html?lang="+ Locale.getDefault().getLanguage()+"&appname="+this.getResources().getString(R.string.application_id)+"&versioncode=16";
    /*String urlVal="file:///android_asset/onboarding.html?lang="+ Locale.getDefault().getLanguage();

    String urlPremium = "file:///android_asset/premium.html?lang="+ Locale.getDefault().getLanguage();
    String urlChangePref = "file:///android_asset/changePref.html?lang="+ Locale.getDefault().getLanguage();*/
    String urlVal="file:///android_asset/onboarding.html";
    String urlPremium = "file:///android_asset/premium.html";
    String urlChangePref = "file:///android_asset/changePref.html";
    BrowserData browserData;

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
             webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mainonbording);

        sharedPreferences = getSharedPreferences("prefs.xml",MODE_PRIVATE);
        premiumPrices = new HashMap<String, String>();
        button = findViewById(R.id.checkInternetButton);
        connectInternetText = findViewById(R.id.checkInternetText);
      //  premiumPrices = new ArrayList<>();

        /*Intent intent = getIntent();
        String purchase = intent.getStringExtra("fromCardView");

        if (purchase== null)
            purchase = intent.getStringExtra("changePrefs");
        Log.d("itsheretogo","true ,"+purchase);*/
       /// sharedPreferences.edit().putBoolean("onBoard", true).apply();
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode= pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        getPremium = new GetPremium(this,this);
        mContext=this;
        mActivity=this;

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            countryCodeValue = tm.getNetworkCountryIso();
        }
        else
            countryCodeValue = "in";

        try {
            devid = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        country = Locale.getDefault().getCountry();

        Log.d("localevalue","country ,"+country);
       /* urlVal = urlVal+"&appname="+this.getResources().getString(R.string.application_id)+"&versioncode="+versionCode+"&simcountry="+countryCodeValue;
        urlPremium = urlPremium+"&appname="+this.getResources().getString(R.string.application_id)+"&versioncode="+versionCode+"&simcountry="+countryCodeValue;
        urlChangePref = urlChangePref+"&appname="+this.getResources().getString(R.string.application_id)+"&versioncode="+versionCode+"&simcountry="+countryCodeValue;*/
        urlVal = urlVal+getUrlParameters();
        urlPremium = urlPremium+getUrlParameters();
        urlChangePref = urlChangePref+getUrlParameters();
        Log.d("jvjvhdbjcdc",""+urlVal);
        runApp();


    }

    public void runApp(){
        Intent intent = getIntent();

        String overlayPurchase = intent.getStringExtra("fromOverlayPage");
        if (overlayPurchase!=null && overlayPurchase.trim().equals("fromOverlayPage")){
            fromOverlay=true;
            webView.loadUrl(urlPremium);
            getPremium.callIAP(mContext,mContext.getString(R.string.premium_sub_sixmonth),"6month");
        }
        else {
        String purchase = intent.getStringExtra("fromCardView");

        if (purchase== null)
            purchase = intent.getStringExtra("changePrefs");
        Log.d("itsheretogo","true ,"+purchase);


        if (purchase!=null && purchase.trim().equals("fromCardView")){
            webView.loadUrl(urlPremium);
            Log.d("itsheretogo","hahayes first, ");
        }
        else if (purchase!=null && purchase.trim().equals("changePrefs")){
            webView.loadUrl(urlChangePref);
            Log.d("itsheretogo","hahayes second, ");
        }
        else {

            Log.d("appisopenedbbb",sharedPreferences.getBoolean("appOpened",false)+" running " + sharedPreferences.getBoolean("purchased",false));
            if (sharedPreferences.getBoolean("purchased",false)||sharedPreferences.getBoolean("monthlySubscribed",false)||sharedPreferences.getBoolean("sixMonthSubscribed",false)|| sharedPreferences.getBoolean("appOpened",false)){
                checkSubscription();
                Log.d("appisopened",sharedPreferences.getBoolean("appOpened",false)+"");
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
              //  finish();
                MainActivityOnboarding.this.finish();

            }
            else{
               // sharedPreferences.edit().putBoolean("appOpened",true).apply();
                Log.d("dcdhckjdjcj",""+urlVal);
                webView.loadUrl(urlVal);
            }

            Log.d("itsheretogo","hahayes third, ");
        }

        }
        boolean notification = sharedPreferences.getBoolean("notification",true);


        if (!sharedPreferences.getBoolean("purchased",false)){
            getPremium.getPrice(this,"lifetime" ,getString(R.string.premium_sku), new GetPremium.PriceListener() {
                @Override
                public void gotPrice(String price) {
                    premiumPrices.put("lifetime",price);
                    //webView.loadUrl("javascript:setIAPValues('lifetime','"+ price+"')");
                    sharedPreferences.edit().putString("lifetime", price).apply();
                   webView.post(new Runnable() {
                       @Override
                       public void run() {
                           webView.loadUrl("javascript:setIAPValues('lifetime','"+ price+"')");
                       }
                   });


                    Log.d("pricewhensending","lifetime : "+price);
                }
            });

            getPremium.getPrice(this, "monthly",getString(R.string.premium_sub_monthly), new GetPremium.PriceListener() {
                @Override
                public void gotPrice(String price) {
                    //webView.loadUrl("javascript:setIAPValues('monthly','"+ price+"')");
                    sharedPreferences.edit().putString("monthly", price).apply();
                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:setIAPValues('monthly','"+ price+"')");
                        }
                    });
                    Log.d("pricewhensending","monthly : "+price);
                }
            });

            getPremium.getPrice(this, "6month",getString(R.string.premium_sub_sixmonth), new GetPremium.PriceListener() {
                @Override
                public void gotPrice(String price) {
                    //webView.loadUrl("javascript:setIAPValues('6month','"+ price+"')");
                    sharedPreferences.edit().putString("6month", price).apply();
                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:setIAPValues('6month','"+ price+"')");
                        }
                    });
                    Log.d("pricewhensending","monthly : "+price);
                }
            });

            getPremium.getSubsPeriod(this, "monthly", getString(R.string.premium_sub_monthly), new GetPremium.SubscriptionPeriodListener() {
                @Override
                public void gotSubsPeriod(String subsPeriod) {
                    sharedPreferences.edit().putString("monthly_period", subsPeriod).apply();
                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:setIAPValues('monthly_period','"+ subsPeriod+"')");
                            Log.d("datathatsending","monthly_period : "+subsPeriod);
                        }
                    });
                }
            });

            getPremium.getSubsPeriod(this, "6month", getString(R.string.premium_sub_sixmonth), new GetPremium.SubscriptionPeriodListener() {
                @Override
                public void gotSubsPeriod(String subsPeriod) {
                    sharedPreferences.edit().putString("6month_period", subsPeriod).apply();
                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:setIAPValues('6month_period','"+ subsPeriod+"')");
                            Log.d("datathatsending","6month_period : "+subsPeriod);
                        }
                    });
                }
            });

            getPremium.getTrialPeriod(this, "6month", getString(R.string.premium_sub_sixmonth), new GetPremium.FreeTrialPeriod() {
                @Override
                public void gotTrialPeriod(String trialPeriod) {
                    sharedPreferences.edit().putString("6month_trial", trialPeriod).apply();
                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:setIAPValues('6month_trial','"+ trialPeriod+"')");
                            Log.d("datathatsending","6month_trial : "+trialPeriod);
                        }
                    });
                }
            });
        }



        browserData = new BrowserData(this,this,sharedPreferences);
        webView.setWebViewClient(browserData);
    }

    public void checkSubscription(){
        if (!sharedPreferences.getBoolean("purchased",false)){
            getPremium.getPrice(this,"lifetime" ,getString(R.string.premium_sku), price -> Log.d("checksubs","lifetime : "+price));

            getPremium.getPrice(this, "monthly",getString(R.string.premium_sub_monthly), price -> Log.d("checksubs","monthly : "+price));

            getPremium.getPrice(this, "6month",getString(R.string.premium_sub_sixmonth), price -> Log.d("checksubs","monthly : "+price));
        }
    }

    public String getUrlParameters() {
        String params;
        params = "?";
        if (sharedPreferences.getBoolean("purchased",false)||sharedPreferences.getBoolean("monthlySubscribed",false)||sharedPreferences.getBoolean("sixMonthSubscribed",false))
            params += "data=1&";

        params += "appname="
                + "relaxing.sounds.sleeping"
                + "&country="
                + country
                + "&devid="
                + devid
                + "&simcountry="
                + countryCodeValue
                + "&version="
                + version
                + "&versioncode="
                + versionCode
                + "&lang="
                + Locale.getDefault().getLanguage()
                + "&inputlang="
                + Locale.getDefault().getLanguage()
        ;
        return params;
    }
}




