package com.example.bskl_kotlin.activity.notification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.activity.notification.adapter.StudentUnReadRecyclerAdapter
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.AppUtils
import com.mobatia.bskl.R

class ImageActivity:AppCompatActivity() {
   lateinit var mContext: Context

    lateinit var mWebView: WebView
    lateinit var mProgressRelLayout: RelativeLayout
    lateinit var mwebSettings: WebSettings
    var loadingFlag = true
    var mLoadUrl: String=""
    var title: String=""
    var mErrorFlag = false
    var anim: RotateAnimation? = null

    var extras: Bundle? = null
    lateinit var relativeHeader: RelativeLayout
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
        AppController().isfromUnread = false
        AppController().isfromUnreadSingle = false

        mContext = this
        extras = intent.extras
        if (extras != null) {
            mLoadUrl = extras!!.getString("webViewComingDetail")!!
            title = extras!!.getString("title")!!
            position = extras!!.getInt("position")
            AppController().isfromUnread = extras!!.getBoolean("isfromUnread")
            AppController().isfromUnreadSingle = extras!!.getBoolean("isfromUnreadSingle")
            AppController().isfromRead = extras!!.getBoolean("isfromRead")
            alertlist = extras!!.getSerializable("PASSING_ARRAY") as ArrayList<PushNotificationModel>
        }
        Toast.makeText(mContext, "login", Toast.LENGTH_SHORT).show()
        init()
        getWebViewSettings()
    }
    private fun init(){
        if (AppController().isfromUnread) {
            for (i in 0 until AppController().mMessageUnreadList.size) {
                if (AppController().mMessageUnreadList.get(position).pushid
                        .equals(AppController().mMessageUnreadList.get(i).pushid)
                ) {
                    AppController().mMessageUnreadList.removeAt(i)
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
            StudentUnReadRecyclerAdapter(mContext, alertlist[position].studentArray)
        studentRecycleUnread.adapter = mStudentRecyclerAdapter
        if (alertlist[position].student_name.equals("")) {
            studentRecycleUnread.visibility = View.GONE
        }

    }

    private fun getWebViewSettings(){
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

        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("www.")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    true
                } else {
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

            /*
                 * (non-Javadoc)
                 *
                 * @see
                 * android.webkit.WebViewClient#onReceivedError(android.webkit.WebView
                 * , int, java.lang.String, java.lang.String)
                 */
            override fun onReceivedError(
                view: WebView, errorCode: Int,
                description: String, failingUrl: String
            ) {
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
        }
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
        AppController().pushId = alertlist[position].pushid
        finish()
    }

}