package com.example.bskl_kotlin.activity.notification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.notification.adapter.StudentUnReadRecyclerAdapter
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.AppUtils

class ImageActivity:AppCompatActivity() {
   lateinit var mContext: Context
   lateinit var activity:Activity

    lateinit var mWebView: WebView
    lateinit var mProgressRelLayout: RelativeLayout
    lateinit var mwebSettings: WebSettings
    var loadingFlag = true
    var mLoadUrl: String=""
    var title: String=""
    var mErrorFlag = false
    var anim: RotateAnimation? = null

    var extras: Bundle? = null
     var relativeHeader: RelativeLayout?=null
   // var headermanager: HeaderManager? = null
   lateinit var back: ImageView
    lateinit var home: ImageView
    lateinit var msgTitle: TextView
    lateinit var alertlist: ArrayList<PushNotificationModel>
    lateinit var studentRecycleUnread: RecyclerView
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comingup_detailweb_view_layout)
        mContext = this
        activity=this
        PreferenceManager().setIsfromUnread(mContext,false)
        PreferenceManager().setIsfromUnreadSingle(mContext,false)
        /*AppController().isfromUnread = false
        AppController().isfromUnreadSingle = false*/

        mContext = this
        activity=this
        extras = intent.extras
        if (extras != null) {
            mLoadUrl = extras!!.getString("webViewComingDetail")!!
            title = extras!!.getString("title")!!
            position = extras!!.getInt("position")
            PreferenceManager().setIsfromUnread(mContext,extras!!.getBoolean("isfromUnread"))
            PreferenceManager().setIsfromUnreadSingle(mContext,extras!!.getBoolean("isfromUnreadSingle"))
            PreferenceManager().setisfromRead(mContext,extras!!.getBoolean("isfromRead"))
           // alertlist = extras!!.getSerializable("PASSING_ARRAY") as ArrayList<PushNotificationModel>
        }
        alertlist=PreferenceManager().getUnreadList(mContext)

        init()
        Log.e("loadurl",mLoadUrl)
        getWebViewSettings()
    }
    private fun init(){
        if (PreferenceManager().getisfromUnread(mContext)==true){
            for (i in 0 until PreferenceManager().getUnreadList(mContext).size) {
                if (PreferenceManager().getUnreadList(mContext).get(position).pushid
                        .equals(PreferenceManager().getUnreadList(mContext).get(i).pushid)
                ) {
                    PreferenceManager().getUnreadList(mContext).removeAt(i)
                }
            }
        }
        relativeHeader = findViewById(R.id.relativeHeader)
        mWebView = findViewById(R.id.webView)
        mProgressRelLayout = findViewById(R.id.progressDialog)
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
            StudentUnReadRecyclerAdapter(mContext, alertlist[position].studentArray!!)
        studentRecycleUnread.adapter = mStudentRecyclerAdapter
        if (alertlist[position].student_name.equals("")) {
            studentRecycleUnread.visibility = View.GONE

        }

    }

    private fun getWebViewSettings(){
        Log.e("web","webfunct")
        mProgressRelLayout.visibility = View.GONE
        anim = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim!!.setInterpolator(mContext, android.R.interpolator.linear)
        anim!!.repeatCount = Animation.INFINITE
        anim!!.duration = 1000
        mProgressRelLayout.animation = anim
        mProgressRelLayout.startAnimation(anim)
        mWebView.isFocusable = true
        mWebView.isFocusableInTouchMode = true
        mWebView.setBackgroundColor(0X00000000)
        mWebView.isVerticalScrollBarEnabled = false
        mWebView.isHorizontalScrollBarEnabled = false
        mWebView.webChromeClient = WebChromeClient()

        //        int sdk = Build.VERSION.SDK_INT;
//        if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
//            mWebView.setBackgroundColor(Color.argb(1, 0, 0, 0));
//        }
        mwebSettings = mWebView.settings
        mwebSettings.saveFormData = true
        mwebSettings.builtInZoomControls = false
        mwebSettings.setSupportZoom(false)

        mwebSettings.pluginState = WebSettings.PluginState.ON
        mwebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        mwebSettings.javaScriptCanOpenWindowsAutomatically = true
        mwebSettings.domStorageEnabled = true
        mwebSettings.databaseEnabled = true
        mwebSettings.defaultTextEncodingName = "utf-8"
        mwebSettings.loadsImagesAutomatically = true
        mwebSettings.allowFileAccessFromFileURLs = true

        //mWebView.settings.setAppCacheMaxSize(10 * 1024 * 1024) // 5MB

        //mWebView.settings.setAppCachePath(mContext.cacheDir.absolutePath)
        mWebView.settings.allowFileAccess = true
        //mWebView.settings.setAppCacheEnabled(true)
        mWebView.settings.javaScriptEnabled = true
        mWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        val userAgent =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
        mWebView.getSettings().setUserAgentString(userAgent)
        mWebView.webViewClient = MyWebViewClient(activity)
        if (mLoadUrl.startsWith("http:") || mLoadUrl.startsWith("https:") || mLoadUrl.startsWith("www.")) {
            Log.e("web","http")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mLoadUrl))
            startActivity(intent)
            true
        } else {
            Log.e("web","no http")
            mWebView.loadDataWithBaseURL(
                "file:///android_asset/fonts/",
                mLoadUrl,
                "text/html; charset=utf-8",
                "utf-8",
                "about:blank"
            )
            true
        }
        mWebView.loadUrl(mLoadUrl!!)

      /*  mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("www.")) {
                    Log.e("web","http")
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    true
                } else {
                    Log.e("web","no http")
                    view.loadDataWithBaseURL(
                        "file:///android_asset/fonts/",
                        mLoadUrl,
                        "text/html; charset=utf-8",
                        "utf-8",
                        "about:blank"
                    )
                    true
                }
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.e("webpage","finish")
                mProgressRelLayout.clearAnimation()
                mProgressRelLayout.visibility = View.GONE
                if (AppUtils().checkInternet(mContext) && loadingFlag) {
                    view.settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    view.loadDataWithBaseURL(
                        "file:///android_asset/fonts/",
                        mLoadUrl,
                        "text/html; charset=utf-8",
                        "utf-8",
                        "about:blank"
                    )
                    loadingFlag = false
                } else if (!AppUtils().checkInternet(mContext) && loadingFlag) {
                    view.settings.cacheMode = WebSettings.LOAD_CACHE_ONLY
                    view.loadDataWithBaseURL(
                        "file:///android_asset/fonts/",
                        mLoadUrl,
                        "text/html; charset=utf-8",
                        "utf-8",
                        "about:blank"
                    )
                    println("CACHE LOADING")
                    loadingFlag = false
                }
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
            }

            *//*
                 * (non-Javadoc)
                 *
                 * @see
                 * android.webkit.WebViewClient#onReceivedError(android.webkit.WebView
                 * , int, java.lang.String, java.lang.String)
                 *//*
            override fun onReceivedError(
                view: WebView, errorCode: Int,
                description: String, failingUrl: String
            ) {
                Log.e("webpage","error")
                mProgressRelLayout.clearAnimation()
                mProgressRelLayout.visibility = View.GONE
                if (AppUtils().checkInternet(mContext)) {
                    AppUtils().showAlertFinish(
                        mContext as Activity, resources
                            .getString(R.string.common_error), "",
                        resources.getString(R.string.ok), false
                    )
                }
                super.onReceivedError(view, errorCode, description, failingUrl)
            }
        }*/
        mWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        var userAgentt =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
        mWebView.getSettings().setUserAgentString(userAgentt)
        mErrorFlag = mLoadUrl == ""
        if (mLoadUrl != null && !mErrorFlag) {
            println("NAS load url $mLoadUrl")
            mWebView.loadDataWithBaseURL(
                "file:///android_asset/fonts/",
                mLoadUrl,
                "text/html; charset=utf-8",
                "utf-8",
                "about:blank"
            )
        } else {
            mProgressRelLayout.clearAnimation()
            mProgressRelLayout.visibility = View.GONE
            AppUtils().showAlertFinish(
                mContext as Activity, resources
                    .getString(R.string.common_error_loading_page), "",
                resources.getString(R.string.ok), false
            )
        }

    }
    override fun onBackPressed() {
        PreferenceManager().setpushId(mContext,alertlist[position].pushid)
        //AppController().pushId = alertlist[position].pushid
        finish()
    }
    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString()
            view?.loadUrl(url)
           // progressbar.visibility= View.GONE
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
           // progressbar.visibility= View.GONE
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            //progressbar.visibility= View.GONE
            Log.e("ERROR",error.toString())
            //Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }

}