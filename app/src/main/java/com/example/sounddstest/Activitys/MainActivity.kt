package com.example.sounddstest.Activitys

import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sounddstest.Fragments.RainFragment.formList1
import com.example.sounddstest.JSONStringFileAudiomix
import com.example.sounddstest.Overlay.FloatingWidgetShowService
import com.example.sounddstest.PlayListDialog
import com.example.sounddstest.R
import com.example.sounddstest.Services.MediaPlayerService
import com.example.sounddstest.Services.MediaPlayerServiceSecond
import com.example.sounddstest.TImerListDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.properties.Delegates

//import kotlinx.android.synthetic.main.activity_main.timerTextView as timerTextView1

class MainActivity : AppCompatActivity(), AudioManager.OnAudioFocusChangeListener {
    //lateinit var rainFragment: RainFragment
    internal lateinit var shared: SharedPreferences
    public lateinit var listimageView: ImageView
    public lateinit var playImageView: ImageView
    public lateinit var pauseImageView: ImageView
    public lateinit var ListPlaySound: ImageView

    public lateinit var timerStartImageView: ImageView
    public lateinit var timerImageView: ImageView
    lateinit var timerTextVieww: TextView

    public lateinit var PlayRelativee: RelativeLayout
    public lateinit var PuaseRelativee: RelativeLayout
    //public lateinit var relativeTimerStart:RelativeLayout
    //public lateinit var relativeTimerStopp:RelativeLayout

    public lateinit var timeString: String
    public lateinit var relativeTimerStartt: RelativeLayout
    public lateinit var relativeTimerStopp: RelativeLayout
    public lateinit var timerRelativee: RelativeLayout

    internal lateinit var sharedPreferences: SharedPreferences
    private var JSON_URL: String? = null
    internal var serviceBound = false
    private var RainJSON_URL: String? = null
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

    //lateinit var countDownTimer: CountDownTimer
    public companion object {
        var rainFragment = com.example.sounddstest.Fragments.RainFragment()
        var MAX_VOLUME by Delegates.notNull<Int>()
        lateinit var mediaPlayer: MediaPlayer
        lateinit var mediaPlayer2: MediaPlayer
        lateinit var mediaPlayer3: MediaPlayer
        lateinit var bottomNavigationView: BottomNavigationView
        lateinit var linearLayout: LinearLayout
        var formList: MutableList<JSONStringFileAudiomix>? = null
        var seekbarlistsub: MutableList<String>? = null
        //lateinit var mediaPlayerHashMap: HashMap<String, MediaPlayer>
        lateinit var VolumeHashMap: HashMap<String, Int>
        lateinit var TimerImage: ImageView

        lateinit var ListPlaySound: ImageView
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
            val tImerListDialog = TImerListDialog(context, activity)
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
        JSON_URL = resources.getString(R.string.JSON_URL)
        RainJSON_URL = resources.getString(R.string.RainJSON_URL)
        RainJsonData = resources.getString(R.string.RainJsonData)
        RelaxingJsonData = resources.getString(R.string.RelaxingJsonData)

        activity = TImerListDialog(this, this)
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

        listimageView = findViewById(R.id.listPlaySound) as ImageView
        playImageView = findViewById(R.id.playImage) as ImageView
        pauseImageView = findViewById(R.id.puaseImage) as ImageView
        timerStartImageView = findViewById(R.id.timerStart) as ImageView
        timerImageView = findViewById(R.id.timerImage) as ImageView

        PlayRelativee = findViewById(R.id.playRelative) as RelativeLayout
        PuaseRelativee = findViewById(R.id.puaseRelative) as RelativeLayout
        PlayRelativ = findViewById(R.id.playRelative) as RelativeLayout
        PuaseRelativ = findViewById(R.id.puaseRelative) as RelativeLayout
        //timerTextView=findViewById(R.id.timerTextView)as ImageView
        //listimageView.setColorFilter(R.color.colorAccent)
        //listimageView.colorFilter.

        // timerTextView =findViewById(R.id.timerTextView)as TextViewLog.d("xtfcvv","above text")
        Log.d("xtfcvv", "above text inititalise")

        //timerTextVieww.text="fgekfaj"
        cardView = findViewById(R.id.disCardView)
        /* relativeTimerStop.setOnClickListener {
             relativeTimerStop.visibility=View.GONE
             relativeTimerStart.visibility=View.VISIBLE
         }
         relativeTimerStart.setOnClickListener {
             relativeTimerStart.visibility=View.GONE
             relativeTimerStop.visibility=View.VISIBLE
         }*/

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        //  bottom_navigation.setupWithNavController(navController)
        NavigationUI.setupWithNavController(bottom_navigation, navController)
        jsontest(RainJSON_URL!!, RainJsonData!!)
        jsontest(JSON_URL!!, RelaxingJsonData!!)

        //soundIni()
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        linearLayout = findViewById(R.id.linearMain)
        //        rainFragment = supportFragmentManager.findFragmentById(R.id.rainFragment) as RainFragment
        // rainFragment.callAboutUsActivity()
        shared = getSharedPreferences("App_settings", Context.MODE_PRIVATE)
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

        // Playing Sounds in Listview
        val set5 = shared.getStringSet("LIST_SOUNDS", null)
        if (set5 != null)
            (seekbarlistsub as ArrayList<String>).addAll(set5)
        if ((seekbarlistsub as ArrayList<String>).size > 0) {
            cardView.visibility = View.VISIBLE
        } else {
            cardView.visibility = View.GONE
        }
// for volume control
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        cuphoneVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        MAX_VOLUME = cuphoneVolume * 10
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

// Overlay
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

        val floatingWidgetShowService = FloatingWidgetShowService();
        floatingWidgetShowService.stopSelf();

        // Set Timer
        timerRelative.setOnClickListener {
            Log.d("asdfghjkhgghjg", "dane")

            val tImerListDialog = TImerListDialog(this, this)
            tImerListDialog.show()
        }

        // Play Puase button
        PlayRelativee = findViewById(R.id.playRelative) as RelativeLayout
        PlayRelativee.setOnClickListener {
            PuaseRelativee.visibility = View.VISIBLE
            PlayRelativee.visibility = View.GONE
            MediaPlayerService.resumeMedia()
            Log.d("timeeer", "")
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
        PuaseRelativee = findViewById(R.id.puaseRelative) as RelativeLayout
        PuaseRelativee.setOnClickListener {
            PuaseRelativee.visibility = View.GONE
            PlayRelativee.visibility = View.VISIBLE

            MediaPlayerService.pauseMedia()
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

        // Play List
        ListPlaySound = findViewById(R.id.listPlaySound) as ImageView
        ListPlaySound.setColorFilter(
            ListPlaySound.getContext().getResources().getColor(R.color.RainFragmentColor),
            PorterDuff.Mode.SRC_ATOP
        );
        ListPlaySound.setOnClickListener {
            try {
                loadData(RainJsonData!!)
                val playListDialog = PlayListDialog(this, this, formList1)
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

        // Timer Visibility
        var countDownTimer: CountDownTimer? = null
        //   if ((this as MainActivity).countDownTimer!=null)
        if (countDownTimer != null) {
            relativeTimerStart.setVisibility(View.VISIBLE);
            relativeTimerStop.setVisibility(View.GONE);
        } else {
            relativeTimerStop.setVisibility(View.VISIBLE);
            relativeTimerStart.setVisibility(View.GONE);
        }

        // Play Puase Button Visibility
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


    }

    // Timer CountDown Funtion
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
                    MediaPlayerService.pauseMedia()

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
            hoursString = "0$hoursString"
        }

        MainActivity.timerView.text = "$minuteString:$secondString"
        Companion.timeString = "$minuteString:$secondString"

    }


    // Fragment navigation
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    // Data from server using JSON
    private fun jsontest(JSON_URLl: String, SP_String: String) {
        Log.d("formList", "2")
        val stringRequest = StringRequest(
            Request.Method.GET, JSON_URLl,
            Response.Listener { response ->
                try {
                    Log.d("formList", "1")
                    //getting the whole json object from the response
                    val obj = JSONObject(response)
                    formList?.clear()
                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    val heroArray = obj.getJSONArray("pack")

                    //now looping through all the elements of the json array
                    for (i in 0 until heroArray.length()) {
                        //getting the json object of the particular index inside the array
                        val jo_inside = heroArray.getJSONObject(i)
                        Log.d("formList", "3")

                        val jsonStringFileAudiomix =
                            JSONStringFileAudiomix() //jo_inside.getString("packName")
                        jsonStringFileAudiomix.setPackName(jo_inside.getString("packName"))
                        jsonStringFileAudiomix.setTitleText(jo_inside.getString("titleText"))
                        jsonStringFileAudiomix.setSeekBarColor(jo_inside.getString("seekbarcolor"))
                        jsonStringFileAudiomix.setChipcoloron(jo_inside.getString("chipcoloron"))
                        jsonStringFileAudiomix.setChipcoloroff(jo_inside.getString("chipcoloroff"))
                        jsonStringFileAudiomix.setTextcolor(jo_inside.getString("textcolor"))
                        jsonStringFileAudiomix.setPurchased(jo_inside.getBoolean("isPurchased"))
                        jsonStringFileAudiomix.setAmbiencesoundsurl(jo_inside.getString("ambiencesoundurl"))
                        jsonStringFileAudiomix.setSubsound1url(jo_inside.getString("subsound1url"))
                        jsonStringFileAudiomix.setSubsound2url(jo_inside.getString("subsound2url"))
                        jsonStringFileAudiomix.setSubsound1Name(jo_inside.getString("subsound1Name"))
                        jsonStringFileAudiomix.setSubsound2Name(jo_inside.getString("subsound2Name"))
                        jsonStringFileAudiomix.setTimertextcolor(jo_inside.getString("timertextcolor"))

                        jsonStringFileAudiomix.setListViewImage(jo_inside.getString("listViewImage"))
                        jsonStringFileAudiomix.setSecondPageImage(jo_inside.getString("secondPageImage"))

                        formList?.add(jsonStringFileAudiomix)
                        // RainFragment.formList1.add(jsonStringFileAudiomix)
                    }
                    Log.d("formList", "" + formList)
                    //creating custom adapter object
                    // ListViewAdapter adapter = new ListViewAdapter(heroList, getApplicationContext());

                    //adding the adapter to listview
                    // listView.setAdapter(adapter);

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.d("formList", "failed")
                }

                saveData(SP_String)
            }, Response.ErrorListener { error ->
                //displaying the error in toast if occurrs
                //  Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            })

        val requestQueue = Volley.newRequestQueue(this)

        //adding the string request to request queue
        requestQueue.add(stringRequest)

        //setuprecycleview(formList);
    }

    // Save Data from server
    private fun saveData(SP_String: String) {   //SharedPreferences

        sharedPreferences = getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(formList)

        editor.putString(SP_String, json)
        editor.apply()
        Log.d("savedata", "" + json)
        loadData(SP_String)

    }

    // Load Data
    private fun loadData(SP_String: String) {
        // SharedPreferences
        sharedPreferences = getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(SP_String, null)
        /*if (json!=null)
            Log.d("shared",""+json);*/
        val type = object : TypeToken<List<JSONStringFileAudiomix>>() {

        }.type
        formList = gson.fromJson<List<JSONStringFileAudiomix>>(
            json,
            type
        ) as MutableList<JSONStringFileAudiomix>?
        Log.d("loaddata", "" + formList)

        if (formList == null) {
            formList = ArrayList()
            Log.d("loaddata", "lololo")
        }
        /*RecyclerViewAdapter myadapter=new RecyclerViewAdapter(this,lstJSONStringFile);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       recyclerView.setAdapter(myadapter);*/
        //setuprecycleview(formList)
    }


    // Audio Focus for call
    override fun onAudioFocusChange(focusChange: Int) {
        val iconpos = shared.getBoolean("puaseIcon", false)
        if (focusChange <= 0) {
            //LOSS -> PAUSE
            if (iconpos) {
                // MediaPlayerService.substopMedia();
                MediaPlayerService.pauseMedia()
                val intent = Intent(applicationContext, MediaPlayerServiceSecond::class.java)
                intent.action = MediaPlayerServiceSecond.ACTION_PREVIOUS
                startService(intent)
            }

        } else {
            //GAIN -> PLAY


            Log.d("lplppppp", "" + iconpos)
            if (iconpos) {
                // MediaPlayerService.playMedia(this);
                MediaPlayerService.resumeMedia()

                val intent = Intent(applicationContext, MediaPlayerServiceSecond::class.java)
                intent.action = MediaPlayerServiceSecond.ACTION_NEXT
                startService(intent)
            }
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

    // App Destroy
    override fun onDestroy() {
        super.onDestroy()
        /* if (serviceBound) {
             unbindService(serviceConnection)
             mAudioManager.abandonAudioFocus(this)
             //service is active
             player.stopSelf()
         }*/
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.cancel(101)
        val stopIntent = Intent(applicationContext, MediaPlayerServiceSecond::class.java)
        stopIntent.action = MediaPlayerServiceSecond.ACTION_PAUSE
        //startService(stopIntent);

        stopService(stopIntent)
        shared.edit().putBoolean("puaseIcon", false).apply()
        MediaPlayerService.stopMedia()
        val floatingWidgetShowService = FloatingWidgetShowService();
        floatingWidgetShowService.stopSelf();
        stopService(floatingIntent)
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

        Log.d("lplplpl", "done2")
        val floatingWidgetShowService = FloatingWidgetShowService();
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
        super.onResume()
    }

    // App Pause
    override fun onPause() {

        // val ss:Boolean = intent.getBooleanExtra("fromwidget",false)
        // Log.d("jhjkbjkb",""+ss)
        playingBolean = false
        MediaPlayerService.PlayingMedia()
        if (playingBolean) {
            showCard()

        }
        super.onPause()
    }

    // App BackPressed
    override fun onBackPressed() {

        AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
            //.setIcon(android.R.drawable.ic_dialog_alert)
            //.setTitle("quit")
            .setMessage("Are you sure to exit app?")
            .setPositiveButton("EXIT") { dialog, which ->
                val stopIntent =
                    Intent(applicationContext, MediaPlayerServiceSecond::class.java)
                stopIntent.setAction(MediaPlayerServiceSecond.ACTION_STOP)
                MediaPlayerService.stopMedia()
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

}



