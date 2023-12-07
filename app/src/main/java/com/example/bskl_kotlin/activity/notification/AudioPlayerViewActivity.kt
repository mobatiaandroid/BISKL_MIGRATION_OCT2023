package com.example.bskl_kotlin.activity.notification

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.activity.notification.adapter.StudentUnReadRecyclerAdapter
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.R



class AudioPlayerViewActivity:AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var extras: Bundle
    var url: String=""

   // lateinit var player: GiraffePlayer
    lateinit var alertlist: ArrayList<PushNotificationModel>
    lateinit var activity: Activity
    var position = 0
    lateinit var backImg: ImageView
    lateinit var home: ImageView
    lateinit var title: String
    lateinit var msgTitle: TextView
    lateinit var studentRecycleUnread: RecyclerView
    lateinit var action_bar_forward: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_player_push_activity)
        mContext = this
        activity = this
        PreferenceManager().setIsfromUnread(mContext,false)
       // AppController().isfromUnread = false
        extras = intent.extras!!
        if (extras != null) {
            position = extras.getInt("position")
            PreferenceManager().setIsfromUnread(mContext,extras.getBoolean("isfromUnread"))
            //AppController().isfromUnread = extras.getBoolean("isfromUnread")
            title = extras.getString("title")!!
            PreferenceManager().setisfromRead(mContext,extras.getBoolean("isfromRead"))
           // AppController().isfromRead = extras.getBoolean("isfromRead")
           /* alertlist = extras
                .getSerializable("PASSING_ARRAY") as ArrayList<PushNotificationModel>
            url = alertlist[position].url*/
        }
        alertlist=PreferenceManager().getUnreadList(mContext)
        url=alertlist[position].url
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_view_home)
        supportActionBar!!.elevation = 0f

        val view = supportActionBar!!.customView
        val toolbar = view.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)
        val headerTitle = view.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = view.findViewById<ImageView>(R.id.logoClickImgView)
        val action_bar_forward = view.findViewById<ImageView>(R.id.action_bar_forward)
        val action_bar_back = view.findViewById<ImageView>(R.id.action_bar_back)
        action_bar_back.setImageResource(R.drawable.back_new)
        headerTitle.text = "Message"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        action_bar_forward.visibility = View.INVISIBLE

        action_bar_back.setOnClickListener {
            onBackPressed()
        }

        msgTitle = findViewById(R.id.msgTitle)
        studentRecycleUnread = findViewById(R.id.studentRecycle)
        msgTitle.text = title
        studentRecycleUnread.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        studentRecycleUnread.layoutManager = llm
        val mStudentRecyclerAdapter =
            StudentUnReadRecyclerAdapter(mContext, alertlist[position].studentArray)
        studentRecycleUnread.adapter = mStudentRecyclerAdapter
        if (alertlist[position].student_name.equals("")) {
            studentRecycleUnread.visibility = View.GONE
        }
     /*   player = GiraffePlayer(this)
        player.play(url)
        player.onComplete { //callback when video is finish
            Toast.makeText(
                applicationContext,
                "Play completed",
                Toast.LENGTH_SHORT
            ).show()
        }.onInfo { what, extra ->
            when (what) {
                IMediaPlayer.MEDIA_INFO_BUFFERING_START -> {}
                IMediaPlayer.MEDIA_INFO_BUFFERING_END -> {}
                IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH -> {}
                IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {}
            }
        }.onError { what, extra ->
            Toast.makeText(
                applicationContext,
                "Can't play this audio",
                Toast.LENGTH_SHORT
            ).show()
        }*/

    }
    override fun onPause() {
        super.onPause()
       /* if (player != null) {
            player.onPause()
        }*/
    }

    override fun onResume() {
        super.onResume()
       /* if (player != null) {
            player.onResume()
        }*/
        if (!PreferenceManager().getUserId(mContext).equals("")) {
            /*AppController().getInstance().getGoogleAnalyticsTracker()
                .set("&uid", PreferenceManager().getUserId(mContext))
            AppController().getInstance().getGoogleAnalyticsTracker()
                .set("&cid", PreferenceManager().getUserId(mContext))
            AppController().getInstance().trackScreenView(
                "Audio Notification Detail." + "(" + PreferenceManager().getUserEmail(mContext) + ")" + " " + "(" + Calendar.getInstance().time + ")"
            )*/
        }
    }
    override fun onDestroy() {
        super.onDestroy()
       /* if (player != null) {
            player.onDestroy()
        }*/
    }

   /* fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig!!)
        if (player != null) {
            player.onConfigurationChanged(newConfig)
        }
    }*/
    fun replace(str: String): String? {
        return str.replace(" ".toRegex(), "%20")
    }

    override fun onBackPressed() {
        PreferenceManager().setpushId(mContext,alertlist[position].pushid)
        //AppController().pushId = alertlist[position].pushid
        finish()
    }
}