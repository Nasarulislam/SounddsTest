package relaxing.sounds.sleeping.OnBoarding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import relaxing.sounds.sleeping.Activitys.MainActivity;
import relaxing.sounds.sleeping.R;
//import relaxing.sounds.sleeping.R;
//import com.example.Activitys.R;
//import relaxing.sounds.sleeping.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//import walking.workout.weightloss.exercise_main.ExerciseMain;

public class GetPremium implements PurchasesUpdatedListener {
    private BillingClient billingClient;
    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener;
    Context mContext;
    Activity activity;
    SharedPreferences sharedPreferences;
   // String price;

    public GetPremium(Context mContext, Activity activity) {
        this.mContext = mContext;
        this.activity = activity;
        sharedPreferences = mContext.getSharedPreferences("prefs.xml",MODE_PRIVATE);
    }

    void handlePurchase(Purchase purchase,String IAPtype) {

        Log.d("purchased","success" + purchase.getPurchaseState() + IAPtype);
        boolean openPurchase = true;

        if (sharedPreferences.getBoolean("purchased",false)||sharedPreferences.getBoolean("monthlySubscribed",false)||sharedPreferences.getBoolean("sixMonthSubscribed",false)){
            openPurchase=false;
            Log.d("jhgjk","done");
        }

        boolean flag = true;
        if (openPurchase)
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {

            acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
                @Override
                public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
                    Log.d("itempurchased",billingResult + "");
                }
            };

            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
            }
            if (IAPtype!=null && IAPtype.trim().equals("premiumIAP"))
                sharedPreferences.edit().putBoolean("purchased",true).apply();
            else {
                Log.d("purchased","one" );
                sharedPreferences.edit().putBoolean("monthlySubscribed",false).apply();
                sharedPreferences.edit().putBoolean("sixMonthSubscribed",false).apply();
            }
            if (IAPtype!=null && IAPtype.trim().equals("monthly")){
                flag=false;
                sharedPreferences.edit().putBoolean("monthlySubscribed",true).apply();
            }
            if (IAPtype!=null && IAPtype.trim().equals("sixmonth")){
                flag=false;
                sharedPreferences.edit().putBoolean("sixMonthSubscribed",true).apply();
            }
            if (flag){
                Log.d("purchased","two" );
                sharedPreferences.edit().putBoolean("sixMonthSubscribed",false).apply();
                sharedPreferences.edit().putBoolean("monthlySubscribed",false).apply();
            }

            Intent intent = new Intent(MainActivityOnboarding.mContext, MainActivity.class);
            mContext.startActivity(intent);




//monthly
            //sixmonth
            //Intent intent = new Intent(MainActivity.mContext,SecondActivity.class);

         /*   if (MainActivityOnboarding.fromOverlay){
                Intent intent = new Intent(MainActivityOnboarding.mContext, ExerciseMain.class);
                mContext.startActivity(intent);
            }
            else {
                Intent intent = new Intent(MainActivity.mContext,SplashScreen.class);
                mContext.startActivity(intent);
            }*/

            Log.d("purchased","success");
            // Acknowledge purchase and grant the item to the user
        } else if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
            Log.d("purchased","pending");
           /* AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
            alertDialog.setTitle("Your ");
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    (dialog, which) -> {
                        dialog.dismiss();
                    });
            alertDialog.show();*/
            sharedPreferences.edit().putBoolean("purchased",false).apply();
            sharedPreferences.edit().putBoolean("monthlySubscribed",false).apply();
            sharedPreferences.edit().putBoolean("sixMonthSubscribed",false).apply();
            // Here you can confirm to the user that they've started the pending
            // purchase, and to complete it, they should follow instructions that
            // are given to them. You can also choose to remind the user in the
            // future to complete the purchase if you detect that it is still
            // pending.
        }
        else {
            Log.d("purchased","not");
            sharedPreferences.edit().putBoolean("monthlySubscribed",false).apply();
            sharedPreferences.edit().putBoolean("sixMonthSubscribed",false).apply();
        }



    }

    public void getPrice(final Context context,final String iapName ,final String premiumSku, final PriceListener priceListener){
       // final String[] price = {""};

        billingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(this).build();

        billingClient.startConnection(new BillingClientStateListener() {


            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                Log.d("billingresoponse",billingResult.getResponseCode()+""+BillingClient.BillingResponseCode.OK);
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                //    Log.d("pricesofvals","here");
                    Purchase.PurchasesResult purchasesResult;
                    if (iapName!=null && iapName.trim().equals("lifetime"))
                        purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
                    else
                        purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);

                    try {
                        getPurchaseDetails(premiumSku, priceListener,iapName);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                  //  Log.d("pricesofvals",price[0]);
                    try {
                        refreshPurchaseList(context,iapName);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                   // Log.d("billingclient","price = "+ price);
                    //launchIAP();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });

    }

    public void callIAP(final Context context,final String iapType,final String iapName){
        billingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(this).build();

        billingClient.startConnection(new BillingClientStateListener() {


            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                Log.d("billingresoponse",billingResult.getResponseCode()+""+BillingClient.BillingResponseCode.OK);
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.

                    Purchase.PurchasesResult purchasesResult;
                    if (iapName!=null && iapName.trim().equals("lifetime")){
                        purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
                        Log.d("queryPurchases",billingClient.queryPurchases(BillingClient.SkuType.INAPP)+"   , mmm "+ purchasesResult.getPurchasesList()+ " , "+ purchasesResult.getResponseCode());
                    }
                    else
                        purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);

                    try {
                        refreshPurchaseList(context,iapName);
                        launchIAP(iapType,iapName);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });

    }

    public void refreshPurchaseList(Context context,String iapName) {
        Purchase.PurchasesResult purchasesResult ;
        Log.d("iappurchased","one "+iapName);
        if (iapName!=null && iapName.trim().equals("lifetime")){
            purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
            List<Purchase> purchasedList = purchasesResult.getPurchasesList();
            Log.d("iappurchased","two "+iapName);
            Log.d("billingresposnseokayref","purchase list : " +purchasedList+" purchase result : "+purchasesResult.getResponseCode()+" ,sku : ");

            if (purchasedList.isEmpty()){
                Log.d("purchasedListepmty","one "+iapName);
                sharedPreferences.edit().putBoolean("purchased",false).apply();
            }

            for(Purchase purchase: purchasedList){
                if (purchase.getSku().equals(context.getString(R.string.premium_sku)))
                {
                    handlePurchase(purchase,"premiumIAP");

                    //isPurchased=true;
                    // handlePurchase(purchase);
               /* purchased=true;
                sharedPreferences.edit().putBoolean("purchased",purchased).apply();*/
                }
            }

        }
        else if (iapName!=null && iapName.trim().equals("6month")){
            purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
            List<Purchase> purchasedList = purchasesResult.getPurchasesList();
            Log.d("iappurchased","three "+iapName);
            Log.d("billingresposnseokayref","purchase list : " +purchasedList+" purchase result : "+purchasesResult.getResponseCode()+" ,sku : ");

            if (purchasedList.isEmpty()){
                Log.d("purchasedListepmty","sixmonth ");
                sharedPreferences.edit().putBoolean("sixMonthSubscribed",false).apply();
            }

            for(Purchase purchase: purchasedList){
                if (purchase.getSku().equals(context.getString(R.string.premium_sub_sixmonth)))
                {
                    handlePurchase(purchase,"sixmonth");

                    //isPurchased=true;
                    // handlePurchase(purchase);
               /* purchased=true;
                sharedPreferences.edit().putBoolean("purchased",purchased).apply();*/
                }
                else {
                    Log.d("nomoresubscirption","sixmonth");
                    sharedPreferences.edit().putBoolean("sixMonthSubscribed",false).apply();
                }
            }

        }
        else {
            purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
            List<Purchase> purchasedList = purchasesResult.getPurchasesList();
            Log.d("iappurchased","four "+iapName);
            Log.d("billingresposnseokayref","purchase list : " +purchasedList+" purchase result : "+purchasesResult.getResponseCode()+" ,sku : ");

            if (purchasedList.isEmpty()){
                Log.d("purchasedListepmty","monthly ");
                sharedPreferences.edit().putBoolean("monthlySubscribed",false).apply();
            }

            for(Purchase purchase: purchasedList){
                if (purchase.getSku().equals(context.getString(R.string.premium_sub_monthly)))
                {
                    handlePurchase(purchase,"monthly");




                    //isPurchased=true;
                    // handlePurchase(purchase);
               /* purchased=true;
                sharedPreferences.edit().putBoolean("purchased",purchased).apply();*/
                }
                else {
                    Log.d("nomoresubscirption","monthly");
                    sharedPreferences.edit().putBoolean("monthlySubscribed",false).apply();
                }
            }

        }



    }

    public void launchIAP(final String iapType,final String iapName) {
        Log.d("billingclient","not ready");
        if (billingClient.isReady()){
            Log.d("billingclient","ready");
            List<String> skuList = new ArrayList<>();
            skuList.add(iapType);
            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
            if (iapName!=null && iapName.trim().equals("lifetime"))
                params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
            else
                params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);

            billingClient.querySkuDetailsAsync(params.build(),
                    new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                                for (SkuDetails skuDetails : skuDetailsList) {
                                    String sku = skuDetails.getSku();
                                    String price = skuDetails.getPrice();
                                    Log.d("showiapfirst",iapType+" values "+skuDetails.getSku());
                                    /*if (mContext.getString(R.string.premium_sku).equals(sku)) {
                                        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                                .setSkuDetails(skuDetails)
                                                .build();

                                        Log.d("responseCode1"," response code : "+ billingClient.launchBillingFlow(activity,flowParams));
                                    }*/
                                    if (iapType.equals(sku)) {
                                        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                                .setSkuDetails(skuDetails)
                                                .build();

                                        Log.d("responseCode1"," response code : "+ billingClient.launchBillingFlow(activity,flowParams));
                                    }
                                }
                            }
                        }

                    });
        }

    }

    public interface PriceListener{
        void gotPrice(String price);
    }

    public interface SubscriptionPeriodListener{
        void gotSubsPeriod(String subsPeriod);
    }

    public interface FreeTrialPeriod{
        void gotTrialPeriod(String trialPeriod);
    }


    public void getTrialPeriod(final Context context,final String iapName ,final String premiumSku, final FreeTrialPeriod freeTrialPeriod){
        // final String[] price = {""};

        billingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(this).build();

        billingClient.startConnection(new BillingClientStateListener() {


            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                Log.d("billingresoponse",billingResult.getResponseCode()+""+BillingClient.BillingResponseCode.OK);
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    //    Log.d("pricesofvals","here");
                    Purchase.PurchasesResult purchasesResult;
                    if (iapName!=null && iapName.trim().equals("lifetime"))
                        purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
                    else
                        purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);

                    try {
                        getTrialDetails(premiumSku,iapName,freeTrialPeriod);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    //  Log.d("pricesofvals",price[0]);
                    try {
                        refreshPurchaseList(context,iapName);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    // Log.d("billingclient","price = "+ price);
                    //launchIAP();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });

    }

    public String getTrialDetails(final String premiumSku,String iapName,final FreeTrialPeriod freeTrialPeriod ){
        final String[] price = {""};

        Log.d("billingclient","not ready");
        if (billingClient.isReady()){
            Log.d("billingclient","ready");
            List<String> skuList = new ArrayList<>();
            skuList.add(premiumSku);
            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
            if (iapName!=null && iapName.trim().equals("lifetime"))
                params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
            else
                params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);

            billingClient.querySkuDetailsAsync(params.build(),
                    new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                                for (SkuDetails skuDetails : skuDetailsList) {
                                    String sku = skuDetails.getSku();
                                    Log.d("skudatapresent"," time : "+ skuDetails.getFreeTrialPeriod());
                                    Log.d("skudatapresent"," data : "+ skuDetails);
                                    price[0] = skuDetails.getPrice();

                                   // subscriptionPeriodListener.gotSubsPeriod(skuDetails.getSubscriptionPeriod());
                                     freeTrialPeriod.gotTrialPeriod(skuDetails.getFreeTrialPeriod());

                                    if (premiumSku.equals(sku)) {

                                    }

                                }

                            }
                        }
                    });
        }
        Log.d("billingclient"," price == "+ price[0]);
        return price[0];
    }




    public void getSubsPeriod(final Context context,final String iapName ,final String premiumSku, final SubscriptionPeriodListener subscriptionPeriodListener){
        // final String[] price = {""};

        billingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(this).build();

        billingClient.startConnection(new BillingClientStateListener() {


            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                Log.d("billingresoponse",billingResult.getResponseCode()+""+BillingClient.BillingResponseCode.OK);
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    //    Log.d("pricesofvals","here");
                    Purchase.PurchasesResult purchasesResult;
                    if (iapName!=null && iapName.trim().equals("lifetime"))
                        purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
                    else
                        purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);

                    try {
                        getSubscriptionDetails(premiumSku,iapName,subscriptionPeriodListener);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    //  Log.d("pricesofvals",price[0]);
                    try {
                        refreshPurchaseList(context,iapName);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    // Log.d("billingclient","price = "+ price);
                    //launchIAP();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });

    }

    public String getSubscriptionDetails(final String premiumSku,String iapName,final SubscriptionPeriodListener subscriptionPeriodListener){
        final String[] price = {""};

        Log.d("billingclient","not ready");
        if (billingClient.isReady()){
            Log.d("billingclient","ready");
            List<String> skuList = new ArrayList<>();
            skuList.add(premiumSku);
            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
            if (iapName!=null && iapName.trim().equals("lifetime"))
                params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
            else
                params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);

            billingClient.querySkuDetailsAsync(params.build(),
                    new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                                for (SkuDetails skuDetails : skuDetailsList) {
                                    String sku = skuDetails.getSku();
                                    Log.d("skudatapresent"," time : "+ skuDetails.getFreeTrialPeriod());
                                    Log.d("skudatapresent"," data : "+ skuDetails);
                                    price[0] = skuDetails.getPrice();

                                    subscriptionPeriodListener.gotSubsPeriod(skuDetails.getSubscriptionPeriod());
                                   // freeTrialPeriod.gotTrialPeriod(skuDetails.getFreeTrialPeriod());

                                    if (premiumSku.equals(sku)) {

                                    }

                                }

                            }
                        }
                    });
        }
        Log.d("billingclient"," price == "+ price[0]);
        return price[0];
    }


public String getPurchaseDetails(final String premiumSku, final PriceListener priceListener,final String iapName){
    final String[] price = {""};

    Log.d("billingclient","not ready");
    if (billingClient.isReady()){
        Log.d("billingclient","ready");
        List<String> skuList = new ArrayList<>();
        skuList.add(premiumSku);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        if (iapName!=null && iapName.trim().equals("lifetime"))
            params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        else
            params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);

        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                            for (SkuDetails skuDetails : skuDetailsList) {
                                String sku = skuDetails.getSku();
                                if (iapName!= null && iapName.contains("6month"))
                                Log.d("eiusehfiusfhsh"," time : "+ skuDetails);
                                //Log.d("skudatapresent"," data : "+ skuDetails);
                               // Log.d("originalprice"," price : "+ skuDetails.getOriginalPriceAmountMicros());
                                 price[0] = skuDetails.getPrice();
                                 String p;
                                 if (iapName!=null && iapName.trim().equals("lifetime"))
                                    p = price[0]+"||"+skuDetails.getOriginalPriceAmountMicros();
                                 else
                                    p = price[0];
                                 priceListener.gotPrice(p);
                                //Log.d("billingclient",p+" price : "+ price[0]);
                                if (premiumSku.equals(sku)) {

                                }

                            }

                        }
                    }
                });
    }
    Log.d("billingclient"," price == "+ price[0]);
    return price[0];
}

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (purchases!=null)
        for(Purchase purchase: purchases){

            if (purchase.getSku().equals(mContext.getString(R.string.premium_sku)))
            {
                handlePurchase(purchase,"premiumIAP");
                //isPurchased=true;
                // handlePurchase(purchase);
               /* purchased=true;
                sharedPreferences.edit().putBoolean("purchased",purchased).apply();*/
            }
            if (purchase.getSku().equals(mContext.getString(R.string.premium_sub_monthly)))
            {
                handlePurchase(purchase,"monthly");
                //isPurchased=true;
                // handlePurchase(purchase);
               /* purchased=true;
                sharedPreferences.edit().putBoolean("purchased",purchased).apply();*/
            }
            if (purchase.getSku().equals(mContext.getString(R.string.premium_sub_sixmonth)))
            {
                handlePurchase(purchase,"sixmonth");
                //isPurchased=true;
                // handlePurchase(purchase);
               /* purchased=true;
                sharedPreferences.edit().putBoolean("purchased",purchased).apply();*/
            }
        }
        Log.d("billingclient","purchase updated ");
    }
}
