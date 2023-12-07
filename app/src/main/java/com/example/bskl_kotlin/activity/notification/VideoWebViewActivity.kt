package com.example.bskl_kotlin.activity.notification

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.activity.notification.adapter.StudentUnReadRecyclerAdapter
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.common.PreferenceManager

class VideoWebViewActivity:AppCompatActivity() {
    lateinit var videolist: ArrayList<PushNotificationModel>
    lateinit var webView: WebView
    var position = 0
    var proWebView: ProgressBar?=null
    private var title: String=""

    lateinit var back: ImageView
    lateinit var home: ImageView
    lateinit var relativeMain: RelativeLayout
    lateinit var mActivity: Activity
    lateinit var mContext: Context
    lateinit var textcontent: TextView
    lateinit var studentRecycleUnread: RecyclerView
    lateinit var msgTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videopush_web_view)
        mContext = this
        mActivity = this
PreferenceManager().setIsfromUnread(mContext,false)
        //AppController().isfromUnread = false

        val extra = intent.extras
        if (extra != null) {
            position = extra.getInt("position")
            title = extra.getString("title")!!
            PreferenceManager().setIsfromUnread(mContext,extra.getBoolean("isfromUnread"))
            PreferenceManager().setisfromRead(mContext,extra.getBoolean("isfromRead"))
           /* AppController().isfromUnread = extra.getBoolean("isfromUnread")
            AppController().isfromRead = extra.getBoolean("isfromRead")*/
           /* videolist = extra
                .getSerializable("PASSING_ARRAY") as ArrayList<PushNotificationModel>*/
        }
        videolist=PreferenceManager().getUnreadList(mContext)
        webView = findViewById(R.id.webView)

        proWebView = findViewById(R.id.proWebView)
        textcontent = findViewById(R.id.txtContent)
        textcontent.visibility = View.INVISIBLE
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
        val llm = LinearLayoutManager(mActivity)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        studentRecycleUnread.layoutManager = llm
        val mStudentRecyclerAdapter =
            StudentUnReadRecyclerAdapter(mActivity, videolist[position].studentArray)
        studentRecycleUnread.adapter = mStudentRecyclerAdapter
        webView.webViewClient = HelloWebViewClient(proWebView!!,textcontent)
        webView.settings.javaScriptEnabled = true
        webView.settings.pluginState = WebSettings.PluginState.ON
        webView.settings.builtInZoomControls = false
        webView.settings.displayZoomControls = true
        val frameVideo = "<html>" + "<br><iframe width=\"320\" height=\"250\" src=\""
        val url_Video =
            frameVideo + videolist[position].url + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
        val urlYoutube = url_Video.replace("watch?v=", "embed/")
        println("urlYoutube:$urlYoutube")
        webView.loadData(urlYoutube, "text/html", "utf-8")

        textcontent.setText(videolist[position].title)
        proWebView!!.visibility = View.VISIBLE
        if (videolist[position].student_name.equals("")) {
            studentRecycleUnread.visibility = View.GONE
        }


    }

    private class HelloWebViewClient(var proWebView: ProgressBar,var textcontent: TextView) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return true
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView, url: String) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url)
            proWebView.setVisibility(View.GONE)
            textcontent.setVisibility(View.VISIBLE)
        }
    }

    override fun onBackPressed() {
        PreferenceManager().setpushId(mContext,videolist[position].pushid)
        //AppController().pushId = videolist[position].pushid
        finish()
    }
}