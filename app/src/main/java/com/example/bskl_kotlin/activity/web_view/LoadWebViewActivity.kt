package com.example.bskl_kotlin.activity.web_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.RotateAnimation
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.bskl_kotlin.R
import java.net.URLEncoder

class LoadWebViewActivity: AppCompatActivity() {

    private var mContext: Context? = null
    private var webview: WebView? = null
    private var mProgressRelLayout: RelativeLayout? = null
    private val mwebSettings: WebSettings? = null
    private val loadingFlag = true
    var mLoadUrl: String=""
    private val mErrorFlag = false
    var extras: Bundle? = null
    var relativeHeader: RelativeLayout? = null
    var back: ImageView? = null
    var home: ImageView? = null
    var anim: RotateAnimation? = null
    var tab_type = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_load)

        mContext = this
        extras = intent.extras
        if (extras != null) {
            mLoadUrl = extras!!.getString("url").toString()
            tab_type = extras!!.getString("tab_type").toString()
        }
        initialiseUI()
        webSettings()


        //passedintent = intent.getStringExtra("Url").toString()




    }

    private fun webSettings() {
        webview!!.settings.javaScriptEnabled = true
        webview!!.settings.setSupportZoom(true)
        //webview.settings.setAppCacheEnabled(true)
        webview!!.settings.javaScriptCanOpenWindowsAutomatically = true
        webview!!.settings.domStorageEnabled = true
        webview!!.settings.databaseEnabled = true
        webview!!.settings.defaultTextEncodingName = "utf-8"
        webview!!.settings.loadsImagesAutomatically = true
        webview!!.settings.allowFileAccess = true
        webview!!.setBackgroundColor(Color.TRANSPARENT)
        webview!!.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
        val userAgent =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
        webview!!.getSettings().setUserAgentString(userAgent)
        webview!!.webViewClient = WebViewClient()
        Log.e("PDF: ", mLoadUrl.toString())
        webview!!.loadUrl(mLoadUrl)
        // webview!!.loadUrl("https://docs.google.com/gview?embedded=true&url=" + URLEncoder.encode(mLoadUrl))    }
    }
    private fun initialiseUI() {
        relativeHeader = findViewById(R.id.relativeHeader)
        webview = findViewById<WebView>(R.id.webView)
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
        headerTitle.text = tab_type
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        action_bar_forward.visibility = View.INVISIBLE
        action_bar_back.setOnClickListener { //AppUtils.hideKeyBoard(mContext);
            finish()
        }

    }

    inner class WebViewClient : android.webkit.WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.e("PDFview: ",url)

            view.loadUrl(url)
            return false
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            //progressdialog.visibility = View.GONE
            //webview.loadUrl("https://docs.google.com/gview?embedded=true&url=" + URLEncoder.encode(passedintent))

        }
    }

    override fun onBackPressed() {
        finish()
    }
}