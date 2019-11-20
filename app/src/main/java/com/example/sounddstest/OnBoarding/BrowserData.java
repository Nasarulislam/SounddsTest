package com.example.sounddstest.OnBoarding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.sounddstest.Activitys.MainActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class BrowserData extends WebViewClient {

    Context mContext;
    Activity activity;
    SharedPreferences sharedPreferences;

    public BrowserData(Context mContext, Activity activity,SharedPreferences sharedPreferences) {
        this.mContext = MainActivityOnboarding.mContext;
        this.activity = MainActivityOnboarding.mActivity;
        this.sharedPreferences = sharedPreferences;
    }

    GetPremium getPremium = new GetPremium(MainActivityOnboarding.mContext,MainActivityOnboarding.mActivity);
    GetPersonData getPersonData = new GetPersonData(getPremium,MainActivityOnboarding.mContext,MainActivityOnboarding.mActivity);
   // getPremium


    @Override
    public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        if (url.contains("#slide6")) {
            resendData(webView, url);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, final String url) {

        try {
            Log.d("urlidValuesiap","url : "+URLDecoder.decode(url, StandardCharsets.UTF_8.name()));
            Log.d("itcamehere", "Clicked url : " + URLDecoder.decode(url, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url.contains("http://riafy.me/changePref")){
            Log.d("itcamehere", "Clicked url : something" );
            try {
                getPersonData.splitUrl(URLDecoder.decode(url, StandardCharsets.UTF_8.name()));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else if(url.contains("http://riafy.me/refresh")){
            resendData(view, url);
        }
        else if (url.contains("http://riafy.me/premium")){
            try {

                getPersonData.splitUrl(URLDecoder.decode(url, StandardCharsets.UTF_8.name()));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else if (url.contains("http://riafy.me/privacy")){
            try {
               // getPersonData.splitUrl(URLDecoder.decode(url, StandardCharsets.UTF_8.name()));
                Intent intent = new Intent(mContext,PrivacyAndTerms.class);
                intent.putExtra("privacy","privacy");
                mContext.startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (url.contains("http://riafy.me/terms")){
            try {
                // getPersonData.splitUrl(URLDecoder.decode(url, StandardCharsets.UTF_8.name()));
                Intent intent = new Intent(mContext,PrivacyAndTerms.class);
                intent.putExtra("termsofuse","termsofuse");
                mContext.startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (url.contains("http://riafy.me/skip")){
            try {
               // Log.d("itcamehere", "skip " + URLDecoder.decode(url, StandardCharsets.UTF_8.name()));
                sharedPreferences.edit().putBoolean("appOpened",true).apply();
                Intent intent = new Intent(mContext,MainActivity.class);
                mContext.startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       else if (url.contains("riafy")) {
            try {
                getPersonData.splitUrl(URLDecoder.decode(url, StandardCharsets.UTF_8.name()));
                Log.d("LOGINclicked::", "Clicked url : " + URLDecoder.decode(url, StandardCharsets.UTF_8.name()));
               /* if (URLDecoder.decode(url, StandardCharsets.UTF_8.name()).contains("changePref")){
                    Log.d("LOGINclicked::", "Clicked url : " + URLDecoder.decode(url, StandardCharsets.UTF_8.name()));
                }*/
                if (URLDecoder.decode(url, StandardCharsets.UTF_8.name()).contains("skip")){
                    sharedPreferences.edit().putBoolean("appOpened",true).apply();
                    Intent intent = new Intent(MainActivityOnboarding.mContext,MainActivity.class);
                    mContext.startActivity(intent);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else if (url.contains("skip")){
            sharedPreferences.edit().putBoolean("appOpened",true).apply();
            Intent intent = new Intent(MainActivityOnboarding.mContext,MainActivity.class);
            mContext.startActivity(intent);



        }
        else
            view.loadUrl(url);

            return true;
            // return super.shouldOverrideUrlLoading(view, request);


       // return super.shouldOverrideUrlLoading(view, url);
    }
    public void resendData(WebView webView, String url){
        try {
            {
                try {
                    String lifetime = sharedPreferences.getString("lifetime", "");
                    assert lifetime != null;
                    if (!lifetime.isEmpty())
                        webView.loadUrl("javascript:setIAPValues('lifetime','" + lifetime + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String monthly = sharedPreferences.getString("monthly", "");
                    assert monthly != null;
                    if (!monthly.isEmpty())
                        webView.loadUrl("javascript:setIAPValues('monthly','" + monthly + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String sixmonth = sharedPreferences.getString("6month", "");
                    assert sixmonth != null;
                    if (!sixmonth.isEmpty())
                        webView.loadUrl("javascript:setIAPValues('6month','" + sixmonth + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String monthly_period = sharedPreferences.getString("monthly_period", "");
                    assert monthly_period != null;
                    if (!monthly_period.isEmpty())
                        webView.loadUrl("javascript:setIAPValues('monthly_period','" + monthly_period + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String sixmonth_period = sharedPreferences.getString("6month_period", "");
                    assert sixmonth_period != null;
                    if (!sixmonth_period.isEmpty())
                        webView.loadUrl("javascript:setIAPValues('6month_period','" + sixmonth_period + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String sixmonth_trial = sharedPreferences.getString("6month_trial", "");
                    assert sixmonth_trial != null;
                    if (!sixmonth_trial.isEmpty())
                        webView.loadUrl("javascript:setIAPValues('6month_trial','" + sixmonth_trial + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
