package com.example.sounddstest.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.sounddstest.R;

public class PrivacyAndTerms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_privacy_and_terms);

        WebView webView = findViewById(R.id.privacyandtermsWebview);

        Intent intent = getIntent();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

//http://riafy.me/wellness/privacy.php?appname=Walking workouts

        String terms = "http://riafy.me/wellness/terms.php?appname="+getResources().getString(R.string.app_name);
        String privacy = "http://riafy.me/wellness/privacy.php?appname="+getResources().getString(R.string.app_name);

        if(intent.getStringExtra("privacy")!=null){
            webView.loadUrl(privacy);
        }
        if(intent.getStringExtra("termsofuse")!=null){
            webView.loadUrl(terms);
        }

    }
}
