package com.example.bskl_kotlin.activity.calendar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.calender.ListViewCalendar
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.attendance.PreferenceManagerr
import com.example.bskl_kotlin.fragment.calendar.adapter.StudentRecyclerCalenderAdapter
import com.example.bskl_kotlin.fragment.calendar.model.CalendarModel
import com.example.bskl_kotlin.manager.AppUtils
import com.example.bskl_kotlin.manager.HeaderManager
import com.example.kingsapp.activities.calender.model.StudentDetailModel
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CalenderDetailActivity : AppCompatActivity(){
    lateinit var txtmsg: TextView
    lateinit var mDateTv: TextView
    lateinit var mTimeTv: TextView
    var img: ImageView? = null
    var studName: TextView? = null
    var home: ImageView? = null
    var extras: Bundle? = null
    lateinit var eventArraylist: ArrayList<CalendarModel>
    var mStudentModel: ArrayList<StudentDetailModel>? = null
    var position = 0
    var add_cl: ImageView? = null
    var context: Context = this
    var mActivity: Activity? = null
    var header: RelativeLayout? = null
    var back: ImageView? = null
    var relativeHeader: RelativeLayout? = null
    var headermanager: HeaderManager? = null
    var studentRecycleUnread: RecyclerView? = null
    var msgTitle: TextView? = null
    var title: String? = ""

    //    String date="";
    private var mWebView: WebView? = null
   // private var mProgressRelLayout: RelativeLayout? = null
    private var mwebSettings: WebSettings? = null
    private var loadingFlag = true
    private var mLoadUrl: String? = null
    private var mErrorFlag = false

    lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.calendar_details_layout)
        mActivity = this
        mContext = this
        add_cl = findViewById<ImageView>(R.id.add_cl)
        studName = findViewById<TextView>(R.id.studName)
        msgTitle = findViewById<TextView>(R.id.msgTitle)
        studentRecycleUnread = findViewById<RecyclerView>(R.id.studentRecycle)
       // img = findViewById<ImageView>(R.id.image)
        txtmsg = findViewById<TextView>(R.id.txt)
        mDateTv = findViewById<TextView>(R.id.mDateTv)
        mTimeTv = findViewById<TextView>(R.id.mTimeTv)
        mWebView = findViewById<WebView>(R.id.webView)
        //mProgressRelLayout = findViewById<RelativeLayout>(R.id.progressDialog)
        extras = intent.extras
        if (extras != null) {
            position = extras!!.getInt("position")
            title = extras!!.getString("tittle")
            //          date=extras.getString("date");
           /* eventArraylist = (extras!!
                .getSerializable("PASSING_ARRAY") as ArrayList<CalendarModel>?)!!*/
            eventArraylist=  PreferenceManagerr.getStudentList(mContext)
            mLoadUrl = """
                <!DOCTYPE html>
                <html>
                <head>
                <style>
                
                @font-face {
                font-family: SourceSansPro-Semibold;src: url(SourceSansPro-Semibold.ttf);font-family: SourceSansPro-Regular;src: url(SourceSansPro-Regular.ttf);}.venue {font-family: SourceSansPro-Regular;font-size:16px;text-align:left;color:	#A9A9A9;}.title {font-family: SourceSansPro-Regular;font-size:16px;text-align:left;color:	#46C1D0;}.description {font-family: SourceSansPro-Semibold;text-align:justify;font-size:14px;color: #000000;}.date {font-family: SourceSansPro-Semibold;text-align:right;font-size:12px;color: #A9A9A9;}</style>
                </head><body>
                """.trimIndent()
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
            val mdate: String = eventArraylist!![position].getDateCalendar()
            val mEndDate: String = eventArraylist!![position].getEnddate()
            var date: Date? = null
            var endDate: Date? = null
            try {
                date = inputFormat.parse(mdate)
                endDate = inputFormat.parse(mEndDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val outputDateStr = outputFormat.format(date)
            val outputEndDateStr = outputFormat.format(endDate)
            mDateTv.setText(outputDateStr)
            val startFormat: DateFormat = SimpleDateFormat("H:mm:ss")
            val newStart: DateFormat = SimpleDateFormat("h:mm a")
            val starttime: String = eventArraylist!![position].getStarttime()
            var newSTime: Date? = null
            try {
                newSTime = startFormat.parse(starttime)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val newstart = newStart.format(newSTime)
            val endtime: String = eventArraylist!![position].getEndtime()
            var newETime: Date? = null
            try {
                newETime = startFormat.parse(endtime)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val newend = newStart.format(newETime)
            if (eventArraylist!![position].isAllday.equals("0")) {
                mTimeTv.setText("$newstart to $newend")
            } else {
                mTimeTv.setText("(All day)")
            }
            if (eventArraylist!![position].getVenue().toString().length > 0) {
                mLoadUrl =
                    mLoadUrl + "<p class='venue'>Venue: " + eventArraylist!![position].getVenue() + "</p>"
            }
            mLoadUrl = mLoadUrl +
                    "<p class='description'>" + eventArraylist!![position].getDescription() + "</p>"
            mLoadUrl = if (eventArraylist!![position].getDaterange().equals("")) {
                if (mdate.equals(mEndDate, ignoreCase = true)) {
                    """
     $mLoadUrl<p class='date'>$outputDateStr</p><p class='date'>${mTimeTv.getText()}</p></body>
     </html>
     """.trimIndent()
                } else {
                    """$mLoadUrl<p class='date'>$outputDateStr to $outputEndDateStr</p><p class='date'>${mTimeTv.getText()}</p></body>
</html>"""
                }
            } else {
                if (mdate.equals(mEndDate, ignoreCase = true)) {
                    """
     $mLoadUrl<p class='date'>$outputDateStr</p><p class='date'>${mTimeTv.getText()}</p></body>
     </html>
     """.trimIndent()
                } else {
                    """
     ${mLoadUrl + "<p class='date'>" + outputDateStr + " to " + outputEndDateStr + "</p>" + "<p class='date'>" + eventArraylist!![position].getDayrange()}</p><p class='date'>${mTimeTv.getText()}</p></body>
     </html>
     """.trimIndent()
                }
            }
        }
        try {
            initialiseUI()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        webViewSettings()
    }

    @Throws(ParseException::class)
    private fun initialiseUI() {
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
        headerTitle.text = "Calendar"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        action_bar_forward.visibility = View.INVISIBLE
        action_bar_back.setOnClickListener { finish() }
        msgTitle!!.text = title
        studentRecycleUnread!!.setHasFixedSize(true)
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        studentRecycleUnread!!.layoutManager = llm
        val mStudentRecyclerCalenderAdapter =
            StudentRecyclerCalenderAdapter(context, eventArraylist!![position].getmStudentModel())
        studentRecycleUnread!!.adapter = mStudentRecyclerCalenderAdapter
        add_cl!!.setOnClickListener {
            var startTime: Long = 0
            var endTime: Long = 0
            try {
                val dateStart =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                        .parse(
                            eventArraylist!![position].getStartDatetime()
                        )
                startTime = dateStart.time
                val dateEnd =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                        .parse(
                            eventArraylist!![position].getEndDatetime()
                        )
                endTime = dateEnd.time
            } catch (e: Exception) {
            }
            val cal = Calendar.getInstance()
            val intent = Intent(Intent.ACTION_EDIT)
            intent.setType("vnd.android.cursor.item/event")
            intent.putExtra("beginTime", startTime)
            if (eventArraylist!![position].isAllday.equals("1")) {
                intent.putExtra("allDay", true)
            } else {
                intent.putExtra("allDay", false)
            }
            intent.putExtra("rule", "FREQ=YEARLY")
            intent.putExtra("endTime", endTime)
            intent.putExtra("title", eventArraylist!![position].getTittle())
            startActivity(intent)
        }
        setDetails()
    }

    @Throws(ParseException::class)
    private fun setDetails() {
        msgTitle!!.text = title
        txtmsg.setText(eventArraylist!![position].getDescription())
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
        val mdate: String = eventArraylist!![position].getDateCalendar()
        val date = inputFormat.parse(mdate)
        val outputDateStr = outputFormat.format(date)
        mDateTv!!.text = outputDateStr
        val startFormat: DateFormat = SimpleDateFormat("H:mm:ss")
        val newStart: DateFormat = SimpleDateFormat("h:mm a")
        val starttime: String = eventArraylist!![position].getStarttime()
        val newSTime = startFormat.parse(starttime)
        val newstart = newStart.format(newSTime)
        val endtime: String = eventArraylist!![position].getEndtime()
        val newETime = startFormat.parse(endtime)
        val newend = newStart.format(newETime)
        if (eventArraylist!![position].isAllday.equals("0")) {
            mTimeTv!!.text = "$newstart to $newend"
        } else {
            mTimeTv!!.text = "(All day)"
        }
    }

    private fun callstatusapi(statusread: String, pushId: String) {
        /*try {
            val manager = VolleyWrapper(URL_GET_NOTICATIONS_STATUS_CHANGE)
            val name = arrayOf<String>(JTAG_ACCESSTOKEN, JTAG_USER_ID, "status", JTAG_PUSH_ID)
            val value = arrayOf<String>(
                PreferenceManager.getAccessToken(mActivity),
                PreferenceManager.getUserId(mActivity),
                statusread,
                pushId
            )
            manager.getResponsePOST(mActivity, 11, name, value, object : ResponseListener() {
                fun responseSuccess(successResponse: String?) {
                    var responsCode = ""
                    println("NotifyRes:$successResponse")
                    if (successResponse != null) {
                        try {
                            val obj = JSONObject(successResponse)
                            responsCode = obj.getString(JTAG_RESPONSECODE)
                            if (responsCode.equals("200", ignoreCase = true)) {
                                val secobj = obj.getJSONObject(JTAG_RESPONSE)
                                val status_code = secobj.getString(JTAG_STATUSCODE)
                                if (status_code.equals("303", ignoreCase = true)) {
                                    callstatusapi(statusread, pushId)
                                } else if (status_code.equals(
                                        RESPONSE_ACCESSTOKEN_EXPIRED,
                                        ignoreCase = true
                                    ) ||
                                    status_code.equals(
                                        RESPONSE_ACCESSTOKEN_MISSING,
                                        ignoreCase = true
                                    )
                                ) {
                                    AppUtils.postInitParam(
                                        mActivity,
                                        object : GetAccessTokenInterface() {
                                            val accessToken: Unit
                                                get() {}
                                        })
                                    callstatusapi(statusread, pushId)
                                }
                            } else if (responsCode.equals(
                                    RESPONSE_ACCESSTOKEN_EXPIRED,
                                    ignoreCase = true
                                ) || responsCode.equals(
                                    RESPONSE_INVALID_TOKEN,
                                    ignoreCase = true
                                ) ||
                                responsCode.equals(RESPONSE_ACCESSTOKEN_MISSING, ignoreCase = true)
                            ) {
                                AppUtils.postInitParam(
                                    mActivity,
                                    object : GetAccessTokenInterface() {
                                        val accessToken: Unit
                                            get() {}
                                    })
                                callstatusapi(statusread, pushId)
                            } else {
                                AppUtils.showDialogAlertDismiss(
                                    mContext as Activity?,
                                    "Alert",
                                    mContext!!.getString(R.string.common_error),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                fun responseFailure(failureResponse: String?) {}
            })
        } catch (ex: Exception) {
        }*/
    }

     fun webViewSettings()
      {
           // mProgressRelLayout!!.visibility = View.GONE
           /* anim = RotateAnimation(
                0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )*/
         //   anim!!.setInterpolator(context, R.interpolator.linear)
           // anim!!.repeatCount = Animation.INFINITE
          //  anim!!.duration = 1000
          //  mProgressRelLayout!!.animation = anim
          //  mProgressRelLayout!!.startAnimation(anim)
            mWebView!!.isFocusable = true
            mWebView!!.isFocusableInTouchMode = true
            mWebView!!.setBackgroundColor(0X00000000)
            mWebView!!.isVerticalScrollBarEnabled = false
            mWebView!!.isHorizontalScrollBarEnabled = false
            mWebView!!.webChromeClient = WebChromeClient()
            mwebSettings = mWebView!!.settings
            mwebSettings!!.saveFormData = true
            mwebSettings!!.builtInZoomControls = false
            mwebSettings!!.setSupportZoom(false)
            mwebSettings!!.pluginState = WebSettings.PluginState.ON
            mwebSettings!!.setRenderPriority(WebSettings.RenderPriority.HIGH)
            mwebSettings!!.javaScriptCanOpenWindowsAutomatically = true
            mwebSettings!!.domStorageEnabled = true
            mwebSettings!!.databaseEnabled = true
            mwebSettings!!.defaultTextEncodingName = "utf-8"
            mwebSettings!!.loadsImagesAutomatically = true
            mwebSettings!!.allowFileAccessFromFileURLs = true


            /*mWebView.getSettings().setAppCacheMaxSize(10 * 1024 * 1024); // 5MB
             mWebView.getSettings().setAppCachePath(
                     context.getCacheDir().getAbsolutePath());*/mWebView!!.settings.allowFileAccess =
                true
            mWebView!!.settings.cacheMode = WebSettings.LOAD_NO_CACHE
            mWebView!!.settings.javaScriptEnabled = true
            mWebView!!.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            mWebView!!.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith(
                            "www."
                        )
                    ) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        true
                    } else {
                        view.loadDataWithBaseURL(
                            "file:///android_asset/fonts/",
                            mLoadUrl!!, "text/html; charset=utf-8", "utf-8", "about:blank"
                        )
                        true
                    }
                }

                override fun onPageFinished(view: WebView, url: String) {
                   // mProgressRelLayout!!.clearAnimation()
                  //  mProgressRelLayout!!.visibility = View.GONE
                    if (AppUtils().checkInternet(context) && loadingFlag) {
                        view.settings.cacheMode = WebSettings.LOAD_NO_CACHE
                        view.loadDataWithBaseURL(
                            "file:///android_asset/fonts/",
                            mLoadUrl!!, "text/html; charset=utf-8", "utf-8", "about:blank"
                        )
                        loadingFlag = false
                    } else if (!AppUtils().checkInternet(context) && loadingFlag) {
                        view.settings.cacheMode = WebSettings.LOAD_CACHE_ONLY
                        view.loadDataWithBaseURL(
                            "file:///android_asset/fonts/",
                            mLoadUrl!!, "text/html; charset=utf-8", "utf-8", "about:blank"
                        )
                        println("CACHE LOADING")
                        loadingFlag = false
                    }
                }

                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
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
                  //  mProgressRelLayout!!.clearAnimation()
                  //  mProgressRelLayout!!.visibility = View.GONE
                    if (AppUtils().checkInternet(context)) {
                        AppUtils().showAlertFinish(
                            context as Activity, resources
                                .getString(R.string.common_error), "",
                            resources.getString(R.string.ok), false
                        )
                    }
                    super.onReceivedError(view, errorCode, description, failingUrl)
                }
            }
            mErrorFlag = mLoadUrl == ""
         // Log.e("mLoadUrl", mLoadUrl!!)
         // Log.e("mErrorFlag", mErrorFlag!!.toString())

          if (mLoadUrl != null && !mErrorFlag) {
                println("NAS load url $mLoadUrl")
                mWebView!!.loadDataWithBaseURL(
                    "file:///android_asset/fonts/",
                    mLoadUrl!!, "text/html; charset=utf-8", "utf-8", "about:blank"
                )
            } else {
              //  mProgressRelLayout!!.clearAnimation()
               // mProgressRelLayout!!.visibility = View.GONE
                AppUtils().showAlertFinish(
                    context as Activity, resources
                        .getString(R.string.common_error_loading_page), "",
                    resources.getString(R.string.ok), false
                )
            }
        }

    override fun onResume() {
        if (!PreferenceManager().getUserId(mContext).equals(" ")) {
            /*AppController.getInstance().getGoogleAnalyticsTracker()
                .set("&uid", PreferenceManager().getUserId(mContext))
            AppController.getInstance().getGoogleAnalyticsTracker()
                .set("&cid", PreferenceManager().getUserId(mContext))
            AppController.getInstance().trackScreenView(
                "Calendar Event Detail. " + "(" + PreferenceManager().getUserEmail(mContext) + ")" + " " + "(" + Calendar.getInstance().time + ")"
            )*/
        }
        super.onResume()
    }
}

