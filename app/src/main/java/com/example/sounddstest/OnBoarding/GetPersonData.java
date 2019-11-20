package com.example.sounddstest.OnBoarding;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.sounddstest.Activitys.MainActivity;
import com.example.sounddstest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import static android.content.Context.MODE_PRIVATE;

public class GetPersonData {
    String url;
    GetPremium getPremium;
    Context mContext;
    Activity activity;
    SharedPreferences sharedPreferences;

    public GetPersonData( GetPremium getPremium, Context mContext, Activity activity) {
        this.getPremium = getPremium;
        this.mContext = mContext;
        this.activity = activity;
        sharedPreferences = mContext.getSharedPreferences("prefs.xml",MODE_PRIVATE);
    }




    private void getIAPType(String userDetails) {
        try {
            JSONObject jsonObject = new JSONObject(userDetails);

            Log.d("urlidValues",jsonObject.get("iap")+"");
            if (jsonObject.get("iap").toString().trim().equals("6month")){
                Log.d("urlidValuesss",jsonObject.get("iap")+"");



                if (sharedPreferences.getBoolean("monthlySubscribed",false)){
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle("You already have another subscription");
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            (dialog, which) -> {
                                dialog.dismiss();
                                getPremium.callIAP(mContext,mContext.getString(R.string.premium_sub_sixmonth),"6month");
                            });
                    alertDialog.show();

                }
                else
                    getPremium.callIAP(mContext,mContext.getString(R.string.premium_sub_sixmonth),"6month");
               // getPremium.launchIAP();
                //getPremium.launchIAP();
            }
            if (jsonObject.get("iap").toString().trim().equals("monthly")){
                Log.d("urlidValuesss",jsonObject.get("iap")+"");

                if (sharedPreferences.getBoolean("sixMonthSubscribed",false)){
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle("You already have another subscription");
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            (dialog, which) -> {
                                dialog.dismiss();
                                getPremium.callIAP(mContext,mContext.getString(R.string.premium_sub_monthly),"monthly");
                            });
                    alertDialog.show();

                }
                else
                    getPremium.callIAP(mContext,mContext.getString(R.string.premium_sub_monthly),"monthly");
                // getPremium.launchIAP();
                //getPremium.launchIAP();
            }
            if (jsonObject.get("iap").toString().trim().equals("lifetime")){
                Log.d("urlidValuesss",jsonObject.get("iap")+"");
                getPremium.callIAP(mContext,mContext.getString(R.string.premium_sku),"lifetime");
                // getPremium.launchIAP();
                //getPremium.launchIAP();
            }
            //Log.d("urlidValues","okay");

        } catch (JSONException e) {
            Log.d("urlidValues","exception "+e.getMessage());
            e.printStackTrace();
        }
    }

    public void splitUrl(String url) {
        String[] values = url.split("/");

        if (values[3]!=null && values[3].trim().equals("onboarding")){
            if (values[4]!=null && !values[4].trim().equals("")){
                Log.d("urlidValues","here "+values[3]+" , "+values[4]);
                getIAPType(values[4]);
            }

        }

        if (values[3]!=null && values[3].trim().equals("changePref")){
            if (values[4]!=null && !values[4].trim().equals("")){
                Log.d("urlidValues","here "+values[3]+" , "+values[4]);
                Intent intent = new Intent(MainActivityOnboarding.mContext,MainActivityOnboarding.class);
                intent.putExtra("personData",values[4]);
                mContext.startActivity(intent);
                //getIAPType(values[4]);
            }

        }

        if (values[3]!=null && values[3].trim().equals("premium")){
            if (values[4]!=null && !values[4].trim().equals("")){
                Log.d("urlidValues","here "+values[3]+" , "+values[4]);
                getIAPType(values[4]);
            }

        }


        for (int i=0;i<values.length;i++){
            Log.d("urlidValue","["+i+"] "+values[i]);
        }
        //return values;
    }
    public void savePersonData(String url) {
        String[] values = url.split("/");
        Log.d("urlidValuesurl",""+url);
        if (values[3]!=null && values[3].trim().equals("changePref")){
            if (values[4]!=null && !values[4].trim().equals("")){
                Log.d("urlidValues","here "+values[3]+" , "+values[4]);
                Intent intent = new Intent(MainActivityOnboarding.mContext, MainActivity.class);
                mContext.startActivity(intent);
                //getIAPType(values[4]);
            }

        }

        for (int i=0;i<values.length;i++){
            Log.d("urlidValue","["+i+"] "+values[i]);
        }
        //return values;
    }
}