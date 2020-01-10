package relaxing.sounds.sleeping.Activitys

//import com.example.Activitys.R

import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import relaxing.sounds.sleeping.Adapters.NatureRecyclerViewAdapter
import relaxing.sounds.sleeping.Adapters.RecyclerViewAdapter
import relaxing.sounds.sleeping.Adapters.RelaxingRecyclerViewAdapter
import relaxing.sounds.sleeping.Adapters.WaterRecyclerViewAdapter
import relaxing.sounds.sleeping.Fragments.RainFragment
import relaxing.sounds.sleeping.Fragments.RainFragment.formList1
import relaxing.sounds.sleeping.Overlay.FloatingWidgetShowService
import relaxing.sounds.sleeping.PlayListDialog
import relaxing.sounds.sleeping.R
import relaxing.sounds.sleeping.Services.MediaPlayerService
import relaxing.sounds.sleeping.Services.MediaPlayerServiceSecond
import relaxing.sounds.sleeping.TImerListDialog
import relaxing.sounds.sleeping.sleepJSONFile
import java.util.*
import kotlin.properties.Delegates


//import kotlinx.android.synthetic.main.activity_main.timerTextView as timerTextView1

class MainActivity : AppCompatActivity(), AudioManager.OnAudioFocusChangeListener {
    //lateinit var rainFragment: RainFragment
    internal lateinit var shared: SharedPreferences
    public lateinit var listimageView: ImageView
    public lateinit var playImageView: ImageView
    public lateinit var pauseImageView: ImageView
    public lateinit var ListPlaySound: RelativeLayout
    public lateinit var linearLayoutMain: RelativeLayout
    public lateinit var timerStartImageView: ImageView
    public lateinit var timerImageView: ImageView
    lateinit var timerTextVieww: TextView
     var openPurchase = true
    lateinit var navController: NavController

    public lateinit var PlayRelativee: RelativeLayout
    public lateinit var PuaseRelativee: RelativeLayout
    //public lateinit var relativeTimerStart:RelativeLayout
    //public lateinit var relativeTimerStopp:RelativeLayout

    public lateinit var timeString: String
    public lateinit var relativeTimerStartt: RelativeLayout
    public lateinit var relativeTimerStopp: RelativeLayout
    public lateinit var timerRelativee: RelativeLayout

    internal lateinit var sharedPreferences: SharedPreferences
    internal lateinit var sharedisPurchase: SharedPreferences
    private var JSON_URL: String? = null
    internal var serviceBound = false
    private var RainJSON_URL: String? = null

    private var Rain: String? = null
    private var Relax: String? = null
    private var Nature: String? = null
    private var Water: String? = null

    public var RainJsonData: String? = null
    private var RelaxingJsonData: String? = null
    private val seekbarlist = ArrayList<String>()
    private var mAudioManager: AudioManager? = null
    lateinit var alertDialog: AlertDialog
    lateinit var floatingIntent: Intent
    var id by Delegates.notNull<Int>()
    val SYSTEM_ALERT_WINDOW_PERMISSION = 7
    //
    public var countDownTimer: CountDownTimer? = null
    lateinit var context: Context
    private var mAdView: AdView? = null
    var mInterstitialAd: InterstitialAd? = null

    var volume :Int=0

     var firstopen:Boolean=false
    //lateinit var countDownTimer: CountDownTimer
    public companion object {

        fun setRV(text: String){
            Log.d("SoundsTest", text)
        }
        var recyclerViewAdapter: RecyclerViewAdapter? = null
        var relaxingRecyclerViewAdapter: RelaxingRecyclerViewAdapter? = null
        var natureRecyclerViewAdapter: NatureRecyclerViewAdapter? = null
        var waterRecyclerViewAdapter: WaterRecyclerViewAdapter? = null

        var listLayoutManager: RecyclerView.LayoutManager? = null
        var rainFragment = RainFragment()
        var MAX_VOLUME by Delegates.notNull<Int>()
        lateinit var mediaPlayer: MediaPlayer
        lateinit var mediaPlayer2: MediaPlayer
        lateinit var mediaPlayer3: MediaPlayer
        lateinit var bottomNavigationView: BottomNavigationView
        lateinit var linearLayout: RelativeLayout
        var formList: MutableList<sleepJSONFile>? = null
        var seekbarlistsub: MutableList<String>? = null
        //lateinit var mediaPlayerHashMap: HashMap<String, MediaPlayer>
        lateinit var VolumeHashMap: HashMap<String, Int>
        lateinit var TimerImage: ImageView

        lateinit var ListPlaySound: RelativeLayout
        lateinit var audioManager: AudioManager
        lateinit var volumeSeekbar: ArrayList<Int>
        lateinit var ListvolumeSeekbar: ArrayList<Int>
        var cuphoneVolume: Int = 0
        lateinit var timeString: String
        lateinit var relativeTimerStart: RelativeLayout
        lateinit var relativeTimerStop: RelativeLayout
        lateinit var timerRelative: RelativeLayout
        // fun countDwn(stime: Int):Int =1
        //lateinit
        // lateinit var countDownTimer: CountDownTimer
        //  lateinit var timerTextView:TextView
        lateinit var activity: TImerListDialog
        lateinit var sharedPreferences: SharedPreferences
        lateinit var stringImageName: String
        var secoundsleftPause: Long = 0
        lateinit var cardView: CardView
        lateinit var timerView: TextView

        var playingBolean: Boolean = false

        lateinit var PlayRelativ: RelativeLayout
        lateinit var PuaseRelativ: RelativeLayout


        fun dismisTimer(context: Context, activity: MainActivity) {
            val tImerListDialog =
                TImerListDialog(context, activity)
            Log.d("axerfbgtghn", "ok")
            tImerListDialog.dismiss()


        }


    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("uhhjhuhjh", "nasaru")
        timerTextVieww = findViewById(R.id.timerTextView)

        JSON_URL = resources.getString(R.string.sleepJSON_URL)
        Rain=resources.getString(R.string.Rain)
        Relax=resources.getString(R.string.Relax)
        Nature=resources.getString(R.string.Nature)
        Water=resources.getString(R.string.Water)

        RainJSON_URL = resources.getString(R.string.RainJSON_URL)
        RainJsonData = resources.getString(R.string.RainJsonData)
        RelaxingJsonData = resources.getString(R.string.RelaxingJsonData)

        try {
            interstitialAd()
        } catch (e: Exception) {
        }


        //>> checking app is purchsed or not

        try {
            sharedisPurchase =
                getSharedPreferences("prefs.xml", Context.MODE_PRIVATE)
            if (sharedisPurchase.getBoolean(
                    "purchased",
                    false
                ) || sharedisPurchase.getBoolean(
                    "monthlySubscribed",
                    false
                ) || sharedisPurchase.getBoolean("sixMonthSubscribed", false)
            ) {
                openPurchase = false
            }
        } catch (e: Exception) {

        }

        activity =
            TImerListDialog(this, this)
        formList = ArrayList()
        seekbarlistsub = ArrayList()
        //mediaPlayerHashMap = HashMap()
        VolumeHashMap = HashMap()
        volumeSeekbar = ArrayList()

        relativeTimerStart = findViewById(R.id.relative_timer_start) as RelativeLayout
        relativeTimerStop = findViewById(R.id.relative_timer_stop) as RelativeLayout
        relativeTimerStartt = findViewById(R.id.relative_timer_start) as RelativeLayout
        relativeTimerStopp = findViewById(R.id.relative_timer_stop) as RelativeLayout
        timerRelative = findViewById(R.id.timer_relative)

        listimageView = findViewById(R.id.listPlaySoundImage) as ImageView
        playImageView = findViewById(R.id.playImage) as ImageView
        pauseImageView = findViewById(R.id.puaseImage) as ImageView
        timerStartImageView = findViewById(R.id.timerStart) as ImageView
        timerImageView = findViewById(R.id.timerImage) as ImageView

        PlayRelativee = findViewById(R.id.playRelative) as RelativeLayout
        PuaseRelativee = findViewById(R.id.puaseRelative) as RelativeLayout
        PlayRelativ = findViewById(R.id.playRelative) as RelativeLayout
        PuaseRelativ = findViewById(R.id.puaseRelative) as RelativeLayout


        cardView = findViewById(R.id.disCardView)


        //>>  Load data from Server
        try {
            Log.d("loaddata", "before first from main activity")

            jsontest(JSON_URL!!, Relax!!)
            jsontest(JSON_URL!!, Nature!!)
            jsontest(JSON_URL!!, Water!!)
            jsontest(JSON_URL!!, Rain!!)
            Log.d("loaddata", "first from main activity")
        } catch (e: Exception) {
        }



       //>> Navigation fragment
            val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = host.navController
            appBarConfiguration = AppBarConfiguration(navController.graph)
            NavigationUI.setupWithNavController(bottom_navigation, navController)
            bottomNavigationView = findViewById(R.id.bottom_navigation)




//        linearLayout = findViewById(R.id.linearMain)
        linearLayoutMain = findViewById(R.id.linearMain)

        //        rainFragment = supportFragmentManager.findFragmentById(R.id.rainFragment) as RainFragment
        // rainFragment.callAboutUsActivity()
        shared = getSharedPreferences("App_settings", Context.MODE_PRIVATE)
        sharedisPurchase.edit().putBoolean("appOpened", true).apply();
        // Clear Seekbarlist
        //rainFragment= RainFragment()
        /*  val editor = shared.edit()
           editor.remove("RAIN_SOUNDS")
           editor.remove("RELAXING_SOUNDS")
           editor.remove("LIST_SOUNDS")
           editor.remove("position")
           editor.commit()*/

        timerView = findViewById(R.id.timerTextView)
        val ss: Boolean = intent.getBooleanExtra("fromwidget", false)
        if (ss) {
            finish()
        }
        cardView.visibility = View.GONE

        //>> Playing Sounds in Listview
        try {
            val set5 = shared.getStringSet("LIST_SOUNDS", null)
            if (set5 != null)
                (seekbarlistsub as ArrayList<String>).addAll(set5)
            if ((seekbarlistsub as ArrayList<String>).size > 0) {
                cardView.visibility = View.VISIBLE
            } else {
                cardView.visibility = View.GONE

                val toast = Toast(this)
                toast.duration = Toast.LENGTH_LONG
                toast.setGravity(Gravity.CENTER, 0, 0)
                val inflater =
                    this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
               /* val view1 = inflater.inflate(R.layout.addsoundstoast, null)
                toast.view = view1
                toast.show()*/
            }
        } catch (e: Exception) {
        }
//>> for volume control
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        cuphoneVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        MAX_VOLUME = cuphoneVolume * 10
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

//>> Overlay
        alertDialog = AlertDialog.Builder(this).create()
        floatingIntent = Intent(this, FloatingWidgetShowService::class.java)
        timerView.text = intent.getStringExtra("exercise")

        if (timerView.text != null)
            Log.d("exercisekn", "" + timerView.text)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            alertDialog.setTitle(resources.getString(R.string.overlay_permission_request))
            alertDialog.setMessage(resources.getString(R.string.overay_permission_message))
            alertDialog.setCancelable(false)
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                { dialog, which ->
                    dialog.dismiss()
                    RuntimePermissionForUser()
                })

            alertDialog.show()
        }

        try {
            val floatingWidgetShowService =
                FloatingWidgetShowService();
            floatingWidgetShowService.stopSelf();
        } catch (e: Exception) {
        }

        //>> Set Timer
        try {
            timerRelative.setOnClickListener {
                Log.d("asdfghjkhgghjg", "dane")

                val tImerListDialog =
                    TImerListDialog(this, this)
                tImerListDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                tImerListDialog.show()
            }
        } catch (e: Exception) {
        }

        //>> Play Puase button
        try {
            PlayRelativee = findViewById(R.id.playRelative) as RelativeLayout
            PlayRelativee.setOnClickListener {
                Log.d("timeeejhbbbr", "naaz")
                PuaseRelativee.visibility = View.VISIBLE
                PlayRelativee.visibility = View.GONE
                MediaPlayerService.resumeMedia(this)

         /*       val curvolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

                // media.setVolume(volume,volume);
//long mDuraction= media.getDuration();
                val FADE_DURATION: Int = 3000
                //final long FADE_DURATION = (mDuraction*25/100);//The duration of the fade
                //The amount of time between volume changes. The smaller this is, the smoother the fade
                //final long FADE_DURATION = (mDuraction*25/100);//The duration of the fade
//The amount of time between volume changes. The smaller this is, the smoother the fade
                val FADE_INTERVAL:Int = 30
                //final float MAX_VOLUME = 1.5f;//The volume will increase from 0 to 1
                //final float MAX_VOLUME = 1.5f;//The volume will increase from 0 to 1
                val MAX_VOLUME:Int = curvolume*10
                val numberOfSteps:Int=
                    FADE_DURATION / FADE_INTERVAL //Calculate the number of fade steps

                //Calculate by how much the volume changes each step
                //Calculate by how much the volume changes each step
                val deltaVolume:Int =1
               // audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
                //Create a new Timer and Timer task to run the fading outside the main UI thread
                val timer = Timer(true)
                val timerTask: TimerTask = object : TimerTask() {
                    override fun run() {
                       fadeInStep(deltaVolume)
                        Log.d("timeeejhbdghbbr", "$volume||$deltaVolume||$curvolume")
                        //Cancel and Purge the Timer if the desired volume has been reached
                        if (volume >= MAX_VOLUME) {
                            Log.d("timeeejhbdghbbr", "$volume")
                            timer.cancel()
                            timer.purge()

                        }
                    }
                }

                timer.schedule(timerTask, 100, 100)

*/


                try {
                    if (secoundsleftPause > 0) {
                        countDownTimer?.cancel()
                        countdown(secoundsleftPause, this)
                    }


                    val intent = Intent(applicationContext, MediaPlayerServiceSecond::class.java)
                    // val intent = Intent(this, MediaPlayerServiceSecond.ACTION_NEXT::class.java)
                    // intent.putExtra("Activity",applicationContext)
                    intent.action = MediaPlayerServiceSecond.ACTION_NEXT
                    startService(intent)
                } catch (e: Exception) {
                }

            }
        } catch (e: Exception) {
        }
        try {
            PuaseRelativee = findViewById(R.id.puaseRelative) as RelativeLayout
            PuaseRelativee.setOnClickListener {
                Log.d("timeeejhbbbr", "naazpuase")
                PuaseRelativee.visibility = View.GONE
                PlayRelativee.visibility = View.VISIBLE

               MediaPlayerService.pauseMedia(this)
               // MediaPlayerService.timerpuas()
                if (countDownTimer != null)
                    countDownTimer?.cancel()
                //countdown(100)
                val intent = Intent(applicationContext, MediaPlayerServiceSecond::class.java)
                //intent.action = MediaPlayerServiceSecond.ACTION_NEXT
                //startService(intent)
                intent.action = MediaPlayerServiceSecond.ACTION_PREVIOUS
                //val intent = Intent(this, MediaPlayerServiceSecond.ACTION_PREVIOUS::class.java)
                startService(intent)
            }
        } catch (e: Exception) {
        }

        //>> Play List
        try {
            listimageView = findViewById(R.id.listPlaySoundImage) as ImageView
            listimageView.setColorFilter(
                listimageView.getContext().getResources().getColor(R.color.RainFragmentColor),
                PorterDuff.Mode.SRC_ATOP
            );
        } catch (e: Exception) {
        }

        try {
            ListPlaySound = findViewById(R.id.listPlaySound) as RelativeLayout
            ListPlaySound.setOnClickListener {
                try {
                    //gogo\\\loadData(RainJsonData!!)
                    val playListDialog = PlayListDialog(
                        this,
                        this,
                        formList1
                    )
                    playListDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    val set4 = shared.getStringSet("LIST_SOUNDS", null)
                    if (set4 != null) {
                        seekbarlist.clear()
                        seekbarlist.addAll(set4)
                    }
                    Log.d("asdfghjk", "15$seekbarlist")
                    val stg4 = seekbarlist.toTypedArray()
                    for (s in stg4) {
                        if (seekbarlist.indexOf(s) != seekbarlist.lastIndexOf(s)) {
                            seekbarlist.removeAt(seekbarlist.lastIndexOf(s))
                        }
                    }
                    Log.d("asdfghjk", "25$seekbarlist")
                    if (seekbarlist.size > 0)
                        playListDialog.show()
                } catch (e: Exception) {
                }
            }
        } catch (e: Exception) {
        }

        //>> Timer Visibility
        try {
            var countDownTimer: CountDownTimer? = null
            //   if ((this as MainActivity).countDownTimer!=null)
            if (countDownTimer != null) {
                relativeTimerStart.setVisibility(View.VISIBLE);
                relativeTimerStop.setVisibility(View.GONE);
            } else {
                relativeTimerStop.setVisibility(View.VISIBLE);
                relativeTimerStart.setVisibility(View.GONE);
            }
        } catch (e: Exception) {
        }

        //>> Play Puase Button Visibility
        try {
            playingBolean = false
            MediaPlayerService.PlayingMedia()
            Log.d("hkjjkbjkj", "ok" + playingBolean)
            if (playingBolean) {
                Log.d("uhhjhuhjh", "ok")
                PuaseRelativee.visibility = View.VISIBLE
                PlayRelativee.visibility = View.GONE
            } else {
                Log.d("uhhjhuhjh", "else")

                PuaseRelativee.visibility = View.GONE
                PlayRelativee.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
        }



        //>> Banner Ad

       /* mAdView = findViewById(R.id.adView)
        val adRequest: AdRequest = AdRequest.Builder().build()
        mAdView?.loadAd(adRequest)*/


       /* mAdView?.setAdListener(object : AdListener() {
            fun onAdLoaded() {
                super.onAdLoaded()
               */
        /* if (!PremiumGet.isPurchased) {
                    mAdView?.setVisibility(View.VISIBLE)
                  //  val marginLayoutParams =
                      //  recyclerView.getLayoutParams() as MarginLayoutParams
                    //marginLayoutParams.setMargins(0, 0, 0, 150)
                    //recyclerView.setLayoutParams(marginLayoutParams)
                } else {
                    mAdView?.setVisibility(View.GONE)
                   // val marginLayoutParams =
                      //  recyclerView.getLayoutParams() as MarginLayoutParams
                   // marginLayoutParams.setMargins(0, 0, 0, 20)
                    //recyclerView.setLayoutParams(marginLayoutParams)
                }*/
        /*
            }

           */
        /* fun onAdFailedToLoad(errorCode: Int) {
                super.onAdFailedToLoad(errorCode)
                mAdView.setVisibility(View.GONE)
                val marginLayoutParams =
                    recyclerView.getLayoutParams() as MarginLayoutParams
                marginLayoutParams.setMargins(0, 0, 0, 20)
                recyclerView.setLayoutParams(marginLayoutParams)
            }*/
        /*
        })*/

    }

    //>> Timer CountDown Funtion
    public fun countdown(stime: Long, activity: MainActivity) {
        countDownTimer = object : CountDownTimer((stime).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("bbbbbb", "" + stime)
                updateTimer(millisUntilFinished.toInt() / 1000, activity)
                secoundsleftPause = millisUntilFinished
                MediaPlayerServiceSecond.secondspause = millisUntilFinished

            }

            override fun onFinish() {
                if (countDownTimer != null)
                    countDownTimer?.cancel()
                secoundsleftPause = 0
                MediaPlayerServiceSecond.secondspause = 0
                try {
                    relativeTimerStop.visibility = View.VISIBLE
                    relativeTimerStart.visibility = View.GONE
                    Log.d("jbdbjknk", "okokko")
                    MediaPlayerService.pauseMedia(applicationContext)

                    PlayRelativ.visibility = View.VISIBLE
                    PuaseRelativ.visibility = View.GONE
                    val intent = Intent(applicationContext, MediaPlayerServiceSecond::class.java)
                    intent.action = MediaPlayerServiceSecond.ACTION_PREVIOUS
                    startService(intent)

                } catch (e: Exception) {
                }
            }

        }.start()
    }

    public fun updateTimer(secondsLeft: Int, activity: MainActivity) {

        val hour = secondsLeft / 3600
        val minute = (secondsLeft - hour * 3600) / 60
        val seconds = secondsLeft - (minute * 60 + hour * 3600)
        val dur = minute * 62000
        val send = seconds * 1050

        var secondString = Integer.toString(seconds)
        var minuteString = Integer.toString(minute)
        var hoursString = Integer.toString(hour)
        if (seconds <= 9) {
            secondString = "0$secondString"
        }
        if (minute <= 9) {
            minuteString = "0$minuteString"
        }
        if (hour <= 9) {
            hoursString = "$hoursString"
        }

        timerView.text = "$minuteString:$secondString"
        Companion.timeString = "$minuteString:$secondString"
        if (hour>0)
        {
            Log.d("oininedodmk","okoko")
            timerView.text = "$hoursString:$minuteString:$secondString"
            Companion.timeString = "$hoursString:$minuteString:$secondString"
        }

    }


    //>> Fragment navigation
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    //>> Data from server using JSON
    private fun jsontest(JSON_URLl: String, SP_String: String) {
        Log.d("formList", "2")
        try {
            val stringRequest = StringRequest(
                Request.Method.GET, JSON_URLl,
                Response.Listener { response ->
                //    try {
                        Log.d("formList", "1")
                        //getting the whole json object from the response
                        val obj = JSONObject(response)
                        formList?.clear()
                        //we have the array named hero inside the object
                        //so here we are getting that json array
                        val heroArray = obj.getJSONArray(SP_String)

                        //now looping through all the elements of the json array
                        for (i in 0 until heroArray.length()) {
                            //getting the json object of the particular index inside the array
                            val jo_inside = heroArray.getJSONObject(i)
                            Log.d("formList", "3")

                            val jsonStringFileAudiomix =
                                sleepJSONFile()
                            //jo_inside.getString("packName")
                            jsonStringFileAudiomix.setSoundName(jo_inside.getString("SoundName"))
                            jsonStringFileAudiomix.setSoundURL(jo_inside.getString("SoundURL"))
                            jsonStringFileAudiomix.setImageURL(jo_inside.getString("ImageURL"))


                            formList?.add(jsonStringFileAudiomix)
                            // RainFragment.formList1.add(jsonStringFileAudiomix)
                        }
                        Log.d("formList", "naz" + formList)
                        //creating custom adapter object
                        // ListViewAdapter adapter = new ListViewAdapter(heroList, getApplicationContext());

                        //adding the adapter to listview
                        // listView.setAdapter(adapter);

                   /* } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.d("formList", "failed")
                    }*/

                    saveData(SP_String)

                }, Response.ErrorListener { error ->
                    //displaying the error in toast if occurrs
                    //  Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                })

            val requestQueue = Volley.newRequestQueue(this)

            //adding the string request to request queue
            requestQueue.add(stringRequest)
        } catch (e: Exception) {
        }

        //setuprecycleview(formList);
    }

    //>> Save Data from server
    private fun saveData(SP_String: String) {   //SharedPreferences

        try {
            sharedPreferences = getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(formList)

            editor.putString(SP_String, json)
            editor.apply()
            Log.d("savedata", "" + json)

            loadData(SP_String)
        } catch (e: Exception) {
        }

    }

    //>> Load Data
    private fun loadData(SP_String: String) {
        // SharedPreferences
        try {
            sharedPreferences = getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString(SP_String, null)
            /*if (json!=null)
                    Log.d("shared",""+json);*/
            val type = object : TypeToken<List<sleepJSONFile>>() {

            }.type
            formList = gson.fromJson<List<sleepJSONFile>>(
                json,
                type
            ) as MutableList<sleepJSONFile>?
            Log.d("loaddataMain", "" + formList)

            if (formList == null) {
                formList = ArrayList()
                Log.d("loaddataMain", "lololo")

            }
           else{

                val sp=SP_String
                if (sp.equals("Rain"))
                {
                   // rainFragment.refreshingView()
                }
               /* finish();
                startActivity(getIntent());*/
                Log.d("loaddataMain", "load data")
            }
        } catch (e: Exception) {
        }
        /*  RecyclerViewAdapter myadapter=new RecyclerViewAdapter(this,lstJSONStringFile);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setAdapter(myadapter);*/
        //setuprecycleview(formList)
    }


    //>> Audio Focus for call
    override fun onAudioFocusChange(focusChange: Int) {

        try {
            val iconpos = shared.getBoolean("puaseIcon", false)
            Log.d("lplppppp", "" + iconpos)
            if (focusChange <= 0) {
                //LOSS -> PAUSE
              //  if (iconpos) {
                    // MediaPlayerService.substopMedia();
                    MediaPlayerService.pauseMedia(applicationContext)
                    val intent = Intent(applicationContext, MediaPlayerServiceSecond::class.java)
                    intent.action = MediaPlayerServiceSecond.ACTION_PREVIOUS
                    startService(intent)
              //  }

            } else {
                //GAIN -> PLAY


                Log.d("lplppppp", "" + iconpos)
             //   if (iconpos) {
                    // MediaPlayerService.playMedia(this);
                    MediaPlayerService.resumeMedia(this)

                    val intent = Intent(applicationContext, MediaPlayerServiceSecond::class.java)
                    intent.action = MediaPlayerServiceSecond.ACTION_NEXT
                    startService(intent)
               // }
            }
        } catch (e: Exception) {
        }
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putBoolean("ServiceState", serviceBound)
        super.onSaveInstanceState(savedInstanceState)
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        serviceBound = savedInstanceState.getBoolean("ServiceState")
    }

    //>> App Destroy
    override fun onDestroy() {
        super.onDestroy()
        /* if (serviceBound) {
             unbindService(serviceConnection)
             mAudioManager.abandonAudioFocus(this)
             //service is active
             player.stopSelf()
         }*/
        try {
            val mNotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.cancel(101)
            val stopIntent = Intent(applicationContext, MediaPlayerServiceSecond::class.java)
            stopIntent.action = MediaPlayerServiceSecond.ACTION_PAUSE
            //startService(stopIntent);

            stopService(stopIntent)
            shared.edit().putBoolean("puaseIcon", false).apply()
          //  MediaPlayerService.hashmapset(this)
            MediaPlayerService.stopMedia(this)
            val floatingWidgetShowService =
                FloatingWidgetShowService();
            floatingWidgetShowService.stopSelf();
            stopService(floatingIntent)
            finish()

        //   if (countDownTimer!=null)
               countDownTimer?.cancel()
        } catch (e: Exception) {
        }
    }

    fun RuntimePermissionForUser() {

        val PermissionIntent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(PermissionIntent, SYSTEM_ALERT_WINDOW_PERMISSION)
    }

    // OnResume
    override fun onResume() {

        try {
            Log.d("lplplpl", "done2")
            val floatingWidgetShowService =
                FloatingWidgetShowService();
            floatingWidgetShowService.stopSelf();
            stopService(floatingIntent)
            timerTextVieww = findViewById(R.id.timerTextView)
            Log.d("lplplpl", "done3" + MediaPlayerServiceSecond.playpause)
            playingBolean = false
            MediaPlayerService.PlayingMedia()
            if (playingBolean) {
                Log.d("lplpjkklpl", "done3")
                PuaseRelativee.visibility = View.VISIBLE
                PlayRelativee.visibility = View.GONE
                //  playingBolean=false
            } else {
                Log.d("lplpjkklpl", "done39")
                PuaseRelativee.visibility = View.GONE
                PlayRelativee.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
        }
        super.onResume()
    }

    // App Pause
    override fun onPause() {

        // val ss:Boolean = intent.getBooleanExtra("fromwidget",false)
        // Log.d("jhjkbjkb",""+ss)
        try {
            playingBolean = false
            MediaPlayerService.PlayingMedia()
            if (playingBolean) {
                val sharedPrefOverlay: SharedPreferences = getSharedPreferences(
                    "App_settings",
                    Context.MODE_PRIVATE
                )
                val overlayNoShow=sharedPrefOverlay.getBoolean("sharedPrefOverlay", false)

                if (overlayNoShow)
                {
                    sharedPrefOverlay.edit().putBoolean("sharedPrefOverlay", false).apply()
                }
                else
                showCard()

            }
        } catch (e: Exception) {
        }
        super.onPause()
    }

    // App BackPressed
    override fun onBackPressed() {
        Log.d("nnkfnkjdnfn",""+openPurchase)
        //interstitialAd()
        try {
            if ( openPurchase) {

                if (mInterstitialAd != null)
                    mInterstitialAd?.show()
            }
        } catch (e: Exception) {
        }

        AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
            //.setIcon(android.R.drawable.ic_dialog_alert)
            //.setTitle("quit")
            .setMessage("Are you sure to exit app?")
            .setPositiveButton("EXIT") { dialog, which ->
                val stopIntent =
                    Intent(applicationContext, MediaPlayerServiceSecond::class.java)
                stopIntent.setAction(MediaPlayerServiceSecond.ACTION_STOP)

                val a = Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a)

               // MediaPlayerService.hashmapset(this)
                MediaPlayerService.stopMedia(this)
                stopService(stopIntent)
                finish()

                super.onBackPressed()
            }
            .setNegativeButton("CANCEL", null)
            .show()

    }

    // Overlay calling function
    private fun showCard() {
        Log.d("bhjn", "donme")
        try {
            //  GetPremium getPremium = new GetPremium(this,this);
            floatingIntent = Intent(this, FloatingWidgetShowService::class.java)
            //  floatingIntent.("Activity",this)
            floatingIntent.putExtra("exercise", timerView.text)
            // floatingIntent.putExtra("loop",loop);
            //floatingIntent.putExtra("Activity", activity);
            //  floatingIntent.putExtra("currentExercisePosition",sharedPreferences.getInt("currentExercisePosition",0));
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                shared.edit().putBoolean("cardIsShown", true).apply()
                startService(floatingIntent)

            } else if (Settings.canDrawOverlays(this)) {
                //stopService();
                shared.edit().putBoolean("cardIsShown", true).apply()
                startService(floatingIntent)
            }
            Log.d("bhjn", "donme")
            /*else
                RuntimePermissionForUser();*/
        } catch (e: Exception) {
            Log.d("bhjn", "error : " + e.message)
            e.printStackTrace()
        }

    }

    fun seek(position: Int, progress: Int, name: String) {
//        Toast.makeText(this, "${recyclerViewAdapter}", Toast.LENGTH_SHORT).show()
        try {
            val fragmentname=navController.currentDestination?.label
            val sharedPre = getSharedPreferences("App_settings", Context.MODE_PRIVATE)
            var seekBarPosition = 0;

            val setRain = sharedPre.getStringSet("RAIN_SOUNDS", null)
            val setRelax = sharedPre.getStringSet("RELAXING_SOUNDS", null)
            val setNature = sharedPre.getStringSet("NATURE_SOUNDS", null)
            val setWater = sharedPre.getStringSet("WATER_SOUNDS", null)
            val rainlist: List<String> =
                ArrayList()
            val relaxlist: List<String> =
                ArrayList()
            val naturelist: List<String> =
                ArrayList()
            val waterlist: List<String> =
                ArrayList()

            if (setRain != null) {
                (rainlist as ArrayList<String>).clear()
                (rainlist as ArrayList<String>).addAll(setRain)

            }
            if (setRelax != null) {
                (relaxlist as ArrayList<String>).clear()
                (relaxlist as ArrayList<String>).addAll(setRelax)

            }
            if (setNature != null) {
                (naturelist as ArrayList<String>).clear()
                (naturelist as ArrayList<String>).addAll(setNature)

            }
            if (setWater != null) {
                (waterlist as ArrayList<String>).clear()
                (waterlist as ArrayList<String>).addAll(setWater)

            }
///


            if(fragmentname!!.equals("fragment_rain")) {
                if(rainlist.contains(name))
                {
                Log.d("jnfnoiojojn", "rain")
                seekBarPosition = recyclerViewAdapter?.seek(name, progress) ?: 0
                   // RainFragment.Volumevalues[seekBarPosition] = progress
                    Log.d("oknmomomojmomom", "$seekBarPosition$progress")
                  //  RainFragment.Volumevalues.set(seekBarPosition,progress)
            }
        }
            else if (fragmentname!!.equals("fragment_relaxing")) {
                 if (relaxlist.contains(name)) {
                    Log.d("jnfnoiojojn", "relax")
                    seekBarPosition = relaxingRecyclerViewAdapter?.seek(name, progress) ?: 0
                  //   RelaxingFragment.Volumevalues[seekBarPosition] = progress
                }
            }

            else if (fragmentname!!.equals("ThirdFragment")) {
                 if (naturelist.contains(name)) {
                    Log.d("jnfnoiojojn", "nature")
                    seekBarPosition = natureRecyclerViewAdapter?.seek(name, progress) ?: 0
                }
            }
            else if (fragmentname!!.equals("waterFragment")) {
                 if (waterlist.contains(name)) {
                    Log.d("jnfnoiojojn", "water")
                    seekBarPosition = waterRecyclerViewAdapter?.seek(name, progress) ?: 0
                }
            }

//Log.d("jnfnoiojojn","$rainlist$relaxlist$naturelist$waterlist")


            //  Toast.makeText(this, "$fragmentname$name$position", Toast.LENGTH_SHORT).show()


            /*
                    if(fragmentname!!.equals("fragment_rain"))
                    {Log.d("relxingknkknbhjn", "rain")

                    }

                    else if (fragmentname!!.equals("fragment_relaxing")){
                        Log.d("relxingknkknbhjn", "fragment_relaxing")

                    }
                    else if (fragmentname!!.equals("ThirdFragment")){
                        Log.d("relxingknkknbhjn", "nature")

                    }else if (fragmentname!!.equals("waterFragment")){
                        Log.d("relxingknkknbhjn", "water")

                    }*/


            val holderView = listLayoutManager?.findViewByPosition(seekBarPosition)
            val seekBar = holderView?.findViewById<SeekBar>(R.id.seekbarid2)
            Log.d("SEEKBAR", "$seekBarPosition, $position");

            seekBar?.setProgress(progress)
        } catch (e: Exception) {
        }


    }

    fun interstitialAd() {
        try {
            mInterstitialAd = InterstitialAd(this)
            mInterstitialAd?.adUnitId = resources.getString(R.string.interstitial_ad_unit_id)
            mInterstitialAd?.loadAd(AdRequest.Builder().build())
        } catch (e: Exception) {
        }
    }

    fun fadeInStep(deltaVolume: Int) {
       // media.setVolume(MediaPlayerService.volume, MediaPlayerService.volume)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume,volume);
      volume += deltaVolume
    }

}



