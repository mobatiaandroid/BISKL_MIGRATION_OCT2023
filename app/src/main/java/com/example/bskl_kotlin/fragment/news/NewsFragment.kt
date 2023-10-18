package com.example.bskl_kotlin.fragment.news

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bskl_kotlin.R

class NewsFragment(title: String, tabId: String) :Fragment() {

    lateinit var mContext: Context
    lateinit var  newsLetterImg: ImageView
    lateinit var webView: WebView
    lateinit var relMain: RelativeLayout
    lateinit var mProgressRelLayout: RelativeLayout
    private val TAB_ID: String? = null
    private val url =
        "https://www.nordangliaeducation.com/our-schools/malaysia/kuala-lumpur/british-international/news-and-insights"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_news_layout, container, false)
    }
    @SuppressLint("UseRequireInsteadOfGet", "Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        logoClickImgView.visibility = View.INVISIBLE
        headerTitle.visibility = View.VISIBLE

        headerTitle.text = "BSKL News"


        initUi()
        getWebViewSettings()
    }

    private fun initUi(){

        newsLetterImg = requireView().findViewById<View>(R.id.newsLetterImg) as ImageView
        webView = requireView().findViewById<View>(R.id.newsWebView) as WebView
        mProgressRelLayout = requireView().findViewById<View>(R.id.progressDialog) as RelativeLayout
        relMain = requireView().findViewById<View>(R.id.relMain) as RelativeLayout

        newsLetterImg.setOnClickListener {
            val uri =
                Uri.parse("https://drive.google.com/drive/folders/0B_xZr_OAuRnDNG9fSUxyaE5NaHM") // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }
    private fun getWebViewSettings(){
        mProgressRelLayout.visibility=View.GONE
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = MyWebViewClient(this)

        webView.loadUrl(url.toString())
    }
    class MyWebViewClient internal constructor(private val activity: NewsFragment) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString()
            //progressDialog.visibility = View.GONE
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            //progressDialog.visibility = View.GONE
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            //  progressDialog.visibility = View.GONE
           // Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }
}