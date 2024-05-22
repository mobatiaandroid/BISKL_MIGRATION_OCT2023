package com.example.bskl_kotlin.activity.home

import ApiClient
import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.GestureDetector
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.calendar.CalendarInfoActivity
import com.example.bskl_kotlin.activity.calender.ListViewCalendar
import com.example.bskl_kotlin.activity.home.model.DeviceRegisterApiModel
import com.example.bskl_kotlin.activity.home.model.DeviceregisterModel
import com.example.bskl_kotlin.activity.home.model.StudDetailsUsermodel
import com.example.bskl_kotlin.activity.home.model.TimeTableStudentModel
import com.example.bskl_kotlin.activity.home.model.UserDetailsApiModel
import com.example.bskl_kotlin.activity.home.model.UserDetailsModel
import com.example.bskl_kotlin.activity.notification.NotificationInfoActivity
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.constants.BsklNameConstants
import com.example.bskl_kotlin.constants.BsklTabConstants
import com.example.bskl_kotlin.fragment.absence.AbsenceFragment
import com.example.bskl_kotlin.fragment.attendance.AttendanceInfoActivity
import com.example.bskl_kotlin.fragment.attendance.AttendenceFragment

import com.example.bskl_kotlin.fragment.contactus.ContactUsFragment
import com.example.bskl_kotlin.fragment.home.HomeScreenFragment
import com.example.bskl_kotlin.fragment.home.tokenM
import com.example.bskl_kotlin.fragment.messages.NotificationFragmentPagination
import com.example.bskl_kotlin.fragment.news.NewsFragment
import com.example.bskl_kotlin.fragment.reports.ReportFragment
import com.example.bskl_kotlin.fragment.safeguarding.SafeGuardingFragment
import com.example.bskl_kotlin.fragment.settings.SettingsFragment
import com.example.bskl_kotlin.fragment.social_media.SocialMediaFragment
import com.example.bskl_kotlin.fragment.timetable.TimeTableFragment
import com.example.bskl_kotlin.manager.AppUtils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity:AppCompatActivity(), AdapterView.OnItemLongClickListener,
    AdapterView.OnItemClickListener {
    lateinit var mContext: Context
    lateinit var activity: Activity
    lateinit var sharedprefs: PreferenceManager
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    lateinit var extras: Bundle
    lateinit var linear_layout: LinearLayout
    var notificationRecieved: Int = 0
    var replaceCurrentVersion: Int = 0
    var android_app_version: String = ""
    var fromsplash = false
    lateinit var homePageLogoImg: ImageView
    lateinit var maintopRel: RelativeLayout
    lateinit var view: View
    lateinit var toolbar: Toolbar
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var downarrow: ImageView
    lateinit var linearLayout: LinearLayout
    lateinit var logoClickImgView: ImageView

    lateinit var mHomeListView: ListView
    lateinit var mListItemArray: Array<String>
    var mListImgArray: TypedArray? = null
    lateinit var imageButton: ImageView
    lateinit var imageButton2: ImageView
    lateinit var headerTitle: TextView
    lateinit var studentDetailList: ArrayList<StudDetailsUsermodel>
    lateinit var timeTableStudArray: ArrayList<TimeTableStudentModel>
    private var mFragment: Fragment?=null
    var tabPositionProceed = 0
    var sPosition = 0

    lateinit var bsklTabConstants: BsklTabConstants
    lateinit var bsklNameConstants: BsklNameConstants

    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDetector: GestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mContext = this
        activity = this
        sharedprefs = PreferenceManager()
        bsklTabConstants = BsklTabConstants()
        bsklNameConstants = BsklNameConstants()
        linear_layout=findViewById(R.id.linearLayout)
        if (PreferenceManager().getUpdate(mContext).equals("0")) {
            if (AppUtils().isNetworkConnected(mContext)) {
                deviceRegister(mContext)
                PreferenceManager().setUpdate(mContext, "1")
            }
        }

        init()
        initSet()
        extras = intent.extras!!
        if (extras != null) {
            //notificationRecieved = extras.getInt("Notification_Recieved", 0)
            fromsplash = extras.getBoolean("fromsplash")
            Log.e("NOTIFY", notificationRecieved.toString())
        }
        if (notificationRecieved == 1) {
            displayView(0)
            displayView(2)
        } else if (notificationRecieved == 2) {
            displayView(0)
            displayView(1)
        } else if (notificationRecieved == 3) {
            displayView(0)
            displayView(7)
        } else {
            displayView(0)
        }
        if (fromsplash) {
            homePageLogoImg.setImageResource(R.drawable.logo)
            val animSlide = AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.fade_out
            )
                Log.e("fromhomesplash","fromhomesplash")
            animSlide.startOffset = 500
            homePageLogoImg.startAnimation(animSlide)
            animSlide.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    val handler = Handler()
                    handler.postDelayed({ //Do something after 20 seconds
                        homePageLogoImg.setImageResource(R.drawable.logo)
                    }, 1000)
                    handler.postDelayed(
                        {

                            setUserDetails()
                            //showSettingUserDetail(URL_GET_SETTINGS_USER_DETAIL)
                        },
                        4000
                    )
                }

                override fun onAnimationEnd(animation: Animation) {
                    Log.e("an ", "end")
                    homePageLogoImg.visibility = View.INVISIBLE
                    maintopRel.setBackgroundColor(mContext.resources.getColor(R.color.white))
                    view.setVisibility(View.VISIBLE)
                    toolbar.setVisibility(View.VISIBLE)
                    mDrawerLayout.setVisibility(View.VISIBLE)
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        } else {
            maintopRel.setBackgroundColor(mContext.resources.getColor(R.color.white))
        }

        Toast.makeText(mContext, "home", Toast.LENGTH_SHORT).show()
    }

    private fun deviceRegister(mContext: Context) {
        val androidId = Settings.Secure.getString(
            mContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        var devicereg=DeviceRegisterApiModel(
            tokenM, "2",androidId,
            PreferenceManager().getUserId(mContext).toString())
        val call: Call<DeviceregisterModel> = ApiClient.getClient.deviceRegister(
            devicereg,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString()
        )

        call.enqueue(object : Callback<DeviceregisterModel> {
            override fun onFailure(call: Call<DeviceregisterModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<DeviceregisterModel>,
                response: Response<DeviceregisterModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")) {
                    if (responsedata!!.response.statuscode.equals("303")) {



                    }
                }

            }
        })

    }

    private fun init() {
        // mListItemArray= ArrayList()
        homePageLogoImg = findViewById(R.id.homePageLogoImg)

        maintopRel = findViewById(R.id.maintopRel)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        mHomeListView = findViewById<ListView>(R.id.homeList)
        downarrow = findViewById(R.id.downarrow)
        linearLayout = findViewById(R.id.linearLayout)
        studentDetailList = ArrayList()
        timeTableStudArray = ArrayList()
        //view = supportActionBar!!.customView


        if (PreferenceManager().getHomeListType(mContext).equals("1")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_1
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_1
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("2")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_2
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_2
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("3")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_3
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_3
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("4")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_4
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_4
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("5")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_5
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_5
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("6")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_6
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_6
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("7")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_7
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_7
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("8")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_8
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_8
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("9")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_9
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_9
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("10")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_10
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_10
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("11")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_11
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_11
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("12")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_12
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_12
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("13")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_13
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_13
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("14")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_14
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_14
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("15")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_15
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_15
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("16")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons
            )
        }

        mListItemArray = mContext.resources.getStringArray(
            R.array.home_list_content_items
        )
        mListImgArray = mContext.resources.obtainTypedArray(
            R.array.home_list_reg_icons
        )
        val width = (resources.displayMetrics.widthPixels / 1.7).toInt()
        val params = linearLayout.layoutParams as DrawerLayout.LayoutParams
        params.width = width
        linearLayout!!.layoutParams = params
        Log.e("adapt","homelisthomeact")
        val mListAdapter = HomeListAdapter(
            activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true)
        mHomeListView.adapter = mListAdapter

        mHomeListView.setBackgroundColor(
            resources.getColor(
                R.color.split_bg
            )
        )
        mDrawerLayout = findViewById(R.id.drawer_layout)
        mHomeListView.onItemClickListener= this
        mHomeListView.onItemLongClickListener=this
        mHomeListView.setOnItemClickListener {adapterView, view, position, id ->
            if (PreferenceManager().getIfHomeItemClickEnabled(mContext)) {
                Log.e("position homelist", position.toString())
                Log.e("position homelist type", PreferenceManager().getHomeListType(mContext).toString())

                displayView(position)
            }
        }
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            ActivityResultCallback<Boolean> { result ->
                Log.e("result", result.toString())
                if (result) {
                    // PERMISSION GRANTED
                    Log.e("result", result.toString())
                    // Toast.makeText(mContext, String.valueOf(result), Toast.LENGTH_SHORT).show();
                } else {
                    // PERMISSION NOT GRANTED
                    /*  Log.e("denied", result.toString())
                val snackbar = Snackbar.make(
                    mDrawerLayout,
                    "Notification Permission Denied",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Settings") {
                        val intent = Intent()
                        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("app_package", mContext.packageName)
                        intent.putExtra("app_uid", mContext.applicationInfo.uid)
                        intent.putExtra(
                            "android.provider.extra.APP_PACKAGE",
                            mContext.packageName
                        )
                        startActivity(intent)
                    }
                snackbar.setActionTextColor(Color.RED)

                val view = snackbar.view
                val tv = view
                    .findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
                tv.setTextColor(Color.WHITE)
                snackbar.show()


                // Toast.makeText(mContext, String.valueOf(result), Toast.LENGTH_SHORT).show();*/
                }
            }
        )
        askForNotificationPermission()

        /*  mDrawerToggle = object : androidx.legacy.app.ActionBarDrawerToggle(
            mContext as Activity,
            mDrawerLayout, R.drawable.menu, R.string.null_value,
            R.string.null_value
        ) {
            fun onDrawerClosed(view: View) {
                mDrawerLayout.setOnTouchListener { v, event -> mDetector!!.onTouchEvent(event) }
                supportInvalidateOptionsMenu()
            }

            fun onDrawerOpened(drawerView: View) {
                mDrawerLayout.setOnTouchListener { v, event -> mDetector!!.onTouchEvent(event) }
                supportInvalidateOptionsMenu()
            }
        }*/
        mListItemArray = resources.getStringArray(R.array.home_list_content_items_15)
        mListImgArray = resources.obtainTypedArray(R.array.home_list_reg_icons_15)
        mDrawerLayout.setOnClickListener {
            if (mDrawerLayout.isDrawerOpen(linear_layout)) {
                mDrawerLayout.closeDrawer(linear_layout)
            } else {
                mDrawerLayout.openDrawer(linear_layout)
            }
        }

    }

    private fun initSet() {

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_view_home)
        supportActionBar!!.elevation = 0f
        view = supportActionBar!!.customView
        toolbar = view.parent as Toolbar
        logoClickImgView = view.findViewById(R.id.logoClickImgView)
        imageButton = view.findViewById<ImageView>(R.id.action_bar_back)
        imageButton2 = view.findViewById<ImageView>(R.id.action_bar_forward)
        headerTitle = view.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgViewTransparent =
            view.findViewById<ImageView>(R.id.logoClickImgViewTransparent)

        imageButton.setOnClickListener(View.OnClickListener {
            val fm = supportFragmentManager
            val currentFragment = fm.findFragmentById(R.id.frame_container)

            if (mDrawerLayout.isDrawerOpen(linearLayout)) {
                mDrawerLayout.closeDrawer(linearLayout)
            } else {
                mDrawerLayout.openDrawer(linearLayout)
            }
        })

        if (fromsplash) {
            view.visibility = View.GONE
            toolbar.visibility = View.GONE
        } else {
            homePageLogoImg.visibility = View.GONE
            maintopRel.setBackgroundColor(mContext.resources.getColor(R.color.white))
            view.visibility = View.VISIBLE
            toolbar.visibility = View.VISIBLE
            mDrawerLayout.visibility = View.VISIBLE
        }
        logoClickImgViewTransparent.setOnClickListener{

//                Toast.makeText(getApplicationContext(), "Forward Button is clicked", Toast.LENGTH_LONG).show();
            val fm = supportFragmentManager
            val currentFragment = fm.findFragmentById(R.id.frame_container)
            println(
                "bskl current fragment "
                        + currentFragment!!.javaClass.toString()
            )
            if (!(currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.example.bskl_kotlin.fragment.settings.SettingsFragment"
                    )) && !(currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.example.bskl_kotlin.fragment.notification.NotificationFragmentPagination"
                    )) && !(currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.example.bskl_kotlin.fragment.calendar.ListViewCalendar"
                    )) && !(currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.example.bskl_kotlin.fragment.attendance.AttendenceFragment"
                    ))
            ) {
                mFragment = SettingsFragment()
                if (mFragment != null) {
                    imageButton2.visibility = View.INVISIBLE
                    val fragmentManager = supportFragmentManager
                    fragmentManager.beginTransaction()
                        .add(R.id.frame_container, mFragment!!,bsklNameConstants.SETTINGS )
                        .addToBackStack(bsklNameConstants.SETTINGS).commit()
                    mDrawerLayout.closeDrawer(linearLayout)
                    supportActionBar!!.setTitle(R.string.null_value)
                    imageButton2.visibility = View.INVISIBLE
                }
            } else if (currentFragment.javaClass.toString().equals(
                    "class com.mobatia.bskl.fragment.notification.NotificationFragmentPagination"
                )

            ) {
                val intent = Intent(
                    mContext,
                    NotificationInfoActivity::class.java
                )
                startActivity(intent)
            } else if (currentFragment.javaClass.toString().equals(
                    "class com.mobatia.bskl.fragment.calendar.ListViewCalendar"
                )
            ) {
                val intent = Intent(
                    mContext,
                    CalendarInfoActivity::class.java
                )
                startActivity(intent)
            } else if (currentFragment.javaClass.toString().equals(
                    "class com.mobatia.bskl.fragment.attendance.AttendenceFragment"
                )
            ) {
                val intent = Intent(
                    mContext,
                    AttendanceInfoActivity::class.java
                )
                startActivity(intent)
            }
        }
        imageButton2.visibility = View.VISIBLE
        //mDrawerToggle!!.syncState()
        homePageLogoImg.setOnClickListener {
            val fm = supportFragmentManager
            val currentFragment = fm.findFragmentById(R.id.frame_container)
            println(
                "bskl current fragment "
                        + currentFragment!!.javaClass.toString()
            )
            if (currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.example.bskl_kotlin.fragment.settings.SettingsFragment"
                    )
            ) {
//                    onBackPressed();
                displayView(0)
            }
        }
        imageButton2.setOnClickListener {

//                Toast.makeText(getApplicationContext(), "Forward Button is clicked", Toast.LENGTH_LONG).show();
            val fm = supportFragmentManager
            val currentFragment = fm.findFragmentById(R.id.frame_container)
            println(
                "bskl current fragment "
                        + currentFragment!!.javaClass.toString()
            )
            if (!(currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.example.bskl_kotlin.fragment.settings.SettingsFragment"
                    )) && !(currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.example.bskl_kotlin.fragment.notification.NotificationFragmentPagination"
                    )) && !(currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.example.bskl_kotlin.fragment.calendar.ListViewCalendar"
                    )) && !(currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.example.bskl_kotlin.fragment.attendance.AttendenceFragment"
                    ))
            ) {
                mFragment = SettingsFragment()
                if (mFragment != null) {
                    imageButton2.visibility = View.INVISIBLE
                    val fragmentManager = supportFragmentManager
                    fragmentManager.beginTransaction()
                        .add(R.id.frame_container, mFragment!!, bsklNameConstants.SETTINGS)
                        .addToBackStack(bsklNameConstants.SETTINGS).commit()
                    mDrawerLayout.closeDrawer(linearLayout)
                    supportActionBar!!.setTitle(R.string.null_value)
                    imageButton2.visibility = View.INVISIBLE
                }
            } else if (currentFragment!!.javaClass.toString().equals(
                    "class com.example.bskl_kotlin.fragment.notification.NotificationFragmentPagination"
                )
            ) {
                val intent = Intent(
                    mContext,
                    NotificationInfoActivity::class.java
                )
                startActivity(intent)
            } else if (currentFragment!!.javaClass.toString().equals(
                    "class com.example.bskl_kotlin.fragment.calendar.ListViewCalendar"
                )
            ) {
                val intent = Intent(
                    mContext,
                    CalendarInfoActivity::class.java
                )
                startActivity(intent)
            } else if (currentFragment!!.javaClass.toString().equals(
                    "class com.example.bskl_kotlin.fragment.attendance.AttendenceFragment"
                )
            ) {
                val intent = Intent(
                    mContext,
                    AttendanceInfoActivity::class.java
                )
                startActivity(intent)
            }
        }

    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        //  mDrawerToggle!!.syncState()
    }

    fun setUserDetails() {
        var userdetail= UserDetailsApiModel(PreferenceManager().getUserId(mContext).toString())
        val call: Call<UserDetailsModel> = ApiClient.getClient.user_details(
            userdetail,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString()
        )

        call.enqueue(object : Callback<UserDetailsModel> {
            override fun onFailure(call: Call<UserDetailsModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<UserDetailsModel>,
                response: Response<UserDetailsModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")) {
                    if (responsedata!!.response.statuscode.equals("303")) {
                        //String userId=respObj.optString(JTAG_USER_ID);
                        //PreferenceManager().setUserId(mContext, respObj.optString(JTAG_USER_ID));
                        PreferenceManager().setPhoneNo(
                            mContext,
                            responsedata!!.response.responseArray.mobileno
                        )
                        PreferenceManager().setFullName(
                            mContext,
                            responsedata!!.response.responseArray.name
                        )
                        PreferenceManager().setCalenderPush(
                            mContext,
                            responsedata!!.response.responseArray.calenderpush
                        )
                        PreferenceManager().setEmailPush(
                            mContext,
                            responsedata!!.response.responseArray.emailpush
                        )
                        PreferenceManager().setMessageBadge(
                            mContext,
                            responsedata!!.response.responseArray.messagebadge
                        )
                        PreferenceManager().setCalenderBadge(
                            mContext,
                            responsedata!!.response.responseArray.calenderbadge
                        )
                        PreferenceManager().setReportMailMerge(
                            mContext,
                            responsedata!!.response.responseArray.reportmailmerge
                        )

                        Log.e("reporthome",PreferenceManager().getReportMailMerge(mContext).toString())
                        Log.e("reporthome", responsedata!!.response.responseArray.reportmailmerge.toString())
                        PreferenceManager().setCorrespondenceMailMerge(
                            mContext,
                            responsedata!!.response.responseArray.correspondencemailmerge
                        )

                        android_app_version = responsedata!!.response.android_version
                        PreferenceManager().setVersionFromApi(
                            mContext,
                            responsedata!!.response.android_version
                        )
                        val versionFromPreference: String =
                            PreferenceManager().getVersionFromApi(mContext)!!.replace(".", "")
                        val versionNumberAsInteger = versionFromPreference.toInt()

                        if (responsedata.response.responseArray.studentdetails.size > 0) {
                            studentDetailList = ArrayList()
                            for (i in responsedata.response.responseArray.studentdetails.indices) {

                                var nmodel = StudDetailsUsermodel(
                                    responsedata.response.responseArray.studentdetails[i].id.toString(),
                                    responsedata.response.responseArray.studentdetails[i].alumi.toString(),
                                    responsedata.response.responseArray.studentdetails[i].applicant.toString()
                                )
                                studentDetailList.add(nmodel)
                            }

                        }
                        var alumini = 0
                        for (x in studentDetailList.indices) {
                            if (studentDetailList.get(x).alumi.equals("0")) {
                                alumini = 0
                                break
                            } else {
                                alumini = 1
                            }
                        }
                        var applicant = 0
                        for (x in studentDetailList.indices) {
                            if (studentDetailList.get(x).applicant.equals("0")) {
                                applicant = 0
                                break
                            } else {
                                applicant = 1
                            }
                        }
                        PreferenceManager().setIsVisible(mContext, alumini.toString())
                        PreferenceManager().setIsApplicant(mContext, applicant.toString())



                        PreferenceManager().setFbkey(
                            mContext,
                            responsedata!!.response.socialmedia.fbkey
                        )
                        PreferenceManager().setInstaKey(
                            mContext,
                            responsedata!!.response.socialmedia.inkey
                        )
                        PreferenceManager().setYouKey(
                            mContext,
                            responsedata!!.response.socialmedia.youtubekey
                        )


                        //  System.out.println("Replace current version"+replaceVersion);
                        if (!PreferenceManager().getVersionFromApi(mContext).equals("")) {
                            if (versionNumberAsInteger > replaceCurrentVersion) {
                                //AppUtils().showDialogAlertUpdate(mContext)
                            }
                        }
                        if (responsedata!!.response.timetable_student.size > 0) {
                            for (i in responsedata!!.response.timetable_student.indices) {
                                var pmodel = TimeTableStudentModel(
                                    responsedata!!.response.timetable_student[i].id.toString(),
                                    responsedata!!.response.timetable_student[i].student_name.toString(),
                                    responsedata!!.response.timetable_student[i].type.toString()
                                )
                                timeTableStudArray.add(pmodel)
                            }
                        }

                        var isAvailable = false
                        if (PreferenceManager().getTimeTableGroup(mContext).equals("1")) {
                            println("working if datamodel")
                            for (i in timeTableStudArray.indices) {
                                if (timeTableStudArray.get(i).type
                                        .equals("Primary")
                                ) {
                                    isAvailable = true
                                }
                            }
                        } else if (PreferenceManager().getTimeTableGroup(mContext)
                                .equals("2")
                        ) {
                            for (i in timeTableStudArray.indices) {
                                if (timeTableStudArray.get(i).type
                                        .equals(bsklNameConstants.SECONDARY)
                                ) {
                                    isAvailable = true
                                }
                            }
                        } else if (PreferenceManager().getTimeTableGroup(mContext)
                                .equals("3")
                        ) {

                            for (i in timeTableStudArray.indices) {

                                if (timeTableStudArray.get(i).type
                                        .equals(bsklNameConstants.SECONDARY) || timeTableStudArray.get(
                                        i
                                    ).type.equals("Primary")
                                ) {
                                    isAvailable = true
                                }
                            }
                        }
                        if (isAvailable) {
                            PreferenceManager().setTimeTableAvailable(mContext, "1")
                        } else {
                            PreferenceManager().setTimeTableAvailable(mContext, "0")
                        }

                        var primarySafeGuarding = ""
                        var secondarySafeGuarding = ""
                        var eyfsSafeGuarding = ""

                        var primaryTimeTable = ""
                        var secondaryTimeTable = ""
                        var eyfsTimeTable = ""

                        if (timeTableStudArray.size > 0) {
                            for (s in timeTableStudArray.indices) {
                                if (timeTableStudArray.get(s).type
                                        .equals(bsklNameConstants.PRIMARY)
                                ) {
                                    primarySafeGuarding = "1"
                                    primaryTimeTable = "1"
                                } else if (timeTableStudArray.get(s).type
                                        .equals(bsklNameConstants.SECONDARY)
                                ) {
                                    secondarySafeGuarding = "1"
                                    secondaryTimeTable = "1"
                                } else if (timeTableStudArray.get(s).type
                                        .equals("EYFS")
                                ) {
                                    eyfsSafeGuarding = "1"
                                    eyfsTimeTable = "1"
                                }
                            }
                        }




                        PreferenceManager().setTimeTableGroup(
                            mContext,
                            responsedata!!.response.app_feature.timetable_group
                        )
                        PreferenceManager().setSafeGuardingGroup(
                            mContext,
                            responsedata!!.response.app_feature.safeguarding_group
                        )

                        if (responsedata!!.response.app_feature.equals("1")
                        ) {
                            // attendance enable
                            PreferenceManager().setHomeAttendance(mContext, "1")
                        } else {
                            PreferenceManager().setHomeAttendance(mContext, "0")
                        }

                        // Attendance Feature Home set
                        if (responsedata!!.response.app_feature.attendance
                                .equals("1")
                        ) {
                            // attendance enable
                            PreferenceManager().setHomeAttendance(mContext, "1")
                        } else {
                            PreferenceManager().setHomeAttendance(mContext, "0")
                        }


                        if (responsedata!!.response.app_feature.report_absense
                                .equals("1")
                        ) {
                            // report absence enable
                            PreferenceManager().setHomeReportAbsence(mContext, "1")
                        } else {
                            PreferenceManager().setHomeReportAbsence(mContext, "0")
                        }


                        if (responsedata!!.response.app_feature.timetable
                                .equals("1")
                        ) {
                            if (responsedata!!.response.app_feature.timetable_group
                                    .equals("1")
                            ) {
                                if (primaryTimeTable.equals("1")) {
                                    PreferenceManager().setHomeTimetable(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeTimetable(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.timetable_group
                                    .equals("2")
                            ) {
                                if (secondaryTimeTable.equals("1")) {
                                    PreferenceManager().setHomeTimetable(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeTimetable(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.timetable_group
                                    .equals("3")
                            ) {
                                if (secondaryTimeTable.equals(
                                        "1"
                                    ) || primaryTimeTable.equals(
                                        "1"
                                    ) || eyfsTimeTable.equals("1")
                                ) {
                                    PreferenceManager().setHomeTimetable(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeTimetable(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.timetable_group
                                    .equals("4")
                            ) {
                                if (eyfsTimeTable.equals("1")) {
                                    PreferenceManager().setHomeTimetable(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeTimetable(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.timetable_group
                                    .equals("5")
                            ) {
                                if (primaryTimeTable.equals(
                                        "1"
                                    ) || secondaryTimeTable.equals("1")
                                ) {
                                    PreferenceManager().setHomeTimetable(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeTimetable(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.timetable_group
                                    .equals("6")
                            ) {
                                if (primaryTimeTable.equals(
                                        "1"
                                    ) || eyfsTimeTable.equals("1")
                                ) {
                                    PreferenceManager().setHomeTimetable(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeTimetable(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.timetable_group
                                    .equals("7")
                            ) {
                                if (secondaryTimeTable.equals(
                                        "1"
                                    ) || eyfsTimeTable.equals("1")
                                ) {
                                    PreferenceManager().setHomeTimetable(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeTimetable(mContext, "0")
                                }
                            }
                        } else {
                            PreferenceManager().setHomeTimetable(mContext, "0")
                        }


                        /*SafeGuarding YearGroup Wise*/
                        if (responsedata!!.response.app_feature.safeguarding
                                .equals("1")
                        ) {
                            if (responsedata!!.response.app_feature.safeguarding_group
                                    .equals("0")
                            ) {
                                PreferenceManager().setHomeSafeGuarding(mContext, "0")
                            } else if (responsedata!!.response.app_feature.safeguarding_group
                                    .equals("1")
                            ) {
                                if (primarySafeGuarding.equals("1")) {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.safeguarding_group
                                    .equals("2")
                            ) {
                                if (secondarySafeGuarding.equals("1")) {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.safeguarding_group
                                    .equals("3")
                            ) {
                                if (secondarySafeGuarding.equals(
                                        "1"
                                    ) || primarySafeGuarding.equals(
                                        "1"
                                    ) || eyfsSafeGuarding.equals("1")
                                ) {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.safeguarding_group
                                    .equals("4")
                            ) {
                                if (eyfsSafeGuarding.equals("1")) {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.safeguarding_group
                                    .equals("5")
                            ) {
                                if (primarySafeGuarding.equals(
                                        "1"
                                    ) || secondarySafeGuarding.equals("1")
                                ) {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.safeguarding_group
                                    .equals("6")
                            ) {
                                if (primarySafeGuarding.equals(
                                        "1"
                                    ) || eyfsSafeGuarding.equals("1")
                                ) {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "0")
                                }
                            } else if (responsedata!!.response.app_feature.safeguarding_group
                                    .equals("7")
                            ) {
                                if (secondarySafeGuarding.equals(
                                        "1"
                                    ) || eyfsSafeGuarding.equals("1")
                                ) {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "1")
                                } else {
                                    PreferenceManager().setHomeSafeGuarding(mContext, "0")
                                }
                            }
                        } else {
                            PreferenceManager().setHomeSafeGuarding(mContext, "0")
                        }
Log.e("absence",PreferenceManager().getHomeReportAbsence(mContext).toString())
Log.e("getHomeSafeGuarding",PreferenceManager().getHomeSafeGuarding(mContext).toString())
Log.e("getHomeAttendance",PreferenceManager().getHomeAttendance(mContext).toString())
Log.e("getHomeTimetable",PreferenceManager().getHomeTimetable(mContext).toString())
                        if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("0") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("0") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("0") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("0")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "1")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("0") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("0") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("0") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("1")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "2")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("0") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("0") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("1") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("0")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "3")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("0") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("0") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("1") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("1")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "4")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("0") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("1") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("0") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("0")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "5")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("0") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("1") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("0") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("1")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "6")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("0") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("1") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("1") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("0")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "7")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("0") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("1") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("1") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("1")
                        ) {
                            Log.e("WORKS :**", "HOME LIST 8")
                            PreferenceManager().setHomeListType(mContext, "8")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("1") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("0") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("0") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("0")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "9")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("1") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("0") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("0") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("1")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "10")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("1") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("0") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("1") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("0")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "11")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("1") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("0") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("1") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("1")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "12")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("1") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("1") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("0") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("0")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "13")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("1") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("1") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("0") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("1")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "14")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("1") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("1") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("1") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("0")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "15")
                        } else if (PreferenceManager().getHomeReportAbsence(mContext)
                                .equals("1") && PreferenceManager().getHomeSafeGuarding(
                                mContext
                            ).equals("1") && PreferenceManager().getHomeAttendance(mContext)
                                .equals("1") && PreferenceManager().getHomeTimetable(
                                mContext
                            ).equals("1")
                        ) {
                            PreferenceManager().setHomeListType(mContext, "16")
                        } else {
                            PreferenceManager().setHomeListType(mContext, "16")
                        }

                        if (PreferenceManager().getHomeListType(mContext).equals("1")) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_1
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_1
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("2")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_2
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_2
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("3")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_3
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_3
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("4")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_4
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_4
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("5")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_5
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_5
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("6")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_6
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_6
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("7")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_7
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_7
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("8")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_8
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_8
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("9")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_9
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_9
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("10")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_10
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_10
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("11")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_11
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_11
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("12")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_12
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_12
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("13")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_13
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_13
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("14")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_14
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_14
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("15")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items_15
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons_15
                            )
                        } else if (PreferenceManager().getHomeListType(mContext)
                                .equals("16")
                        ) {
                            mListItemArray = mContext.resources.getStringArray(
                                R.array.home_list_content_items
                            )
                            mListImgArray = mContext.resources.obtainTypedArray(
                                R.array.home_list_reg_icons
                            )
                        }
                        val mListAdapter = HomeListAdapter(
                            activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
                        )
                        mHomeListView.adapter = mListAdapter

                        callHomeList()


                    }
                }

            }
        })
    }

    private fun callHomeList() {
        if (PreferenceManager().getHomeListType(mContext).equals("1")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_1
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_1
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("2")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_2
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_2
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("3")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_3
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_3
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("4")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_4
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_4
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("5")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_5
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_5
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("6")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_6
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_6
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("7")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_7
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_7
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("8")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_8
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_8
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("9")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_9
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_9
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("10")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_10
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_10
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("11")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_11
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_11
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("12")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_12
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_12
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("13")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_13
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_13
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("14")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_14
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_14
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("15")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items_15
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons_15
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        } else if (PreferenceManager().getHomeListType(mContext).equals("16")) {
            mListItemArray = mContext.resources.getStringArray(
                R.array.home_list_content_items
            )
            mListImgArray = mContext.resources.obtainTypedArray(
                R.array.home_list_reg_icons
            )
            val mListAdapter = HomeListAdapter(
                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
            )
            mHomeListView.adapter = mListAdapter
            mHomeListView.setBackgroundColor(
                resources.getColor(
                    R.color.split_bg
                )
            )
        }
    }
    /*override fun setOnItemClickListener(v: View?,position:Position) {
        if (PreferenceManager().getIfHomeItemClickEnabled(mContext)) {
            Log.e("position homelist", position.toString())

            displayView(position)
        }
    }*/
    /*  open fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig!!)
        // Pass any configuration change to the drawer toggls
        println("Position working:")
        mDrawerToggle!!.onConfigurationChanged(newConfig)
    }*/
    override fun onItemClick(
        parent: AdapterView<*>?, view: View?,
        position: Int, id: Long
    ) {
        if (PreferenceManager().getIfHomeItemClickEnabled(mContext)) {
            println("Position homelist:$position")
            //            imageButton2.setVisibility(View.VISIBLE);
            displayView(position)
        }
    }

    private fun displayView(position: Int) {
        mFragment = null
        tabPositionProceed = position

        if (!PreferenceManager().getUserId(mContext).equals("")) {
            if (PreferenceManager().getIsVisible(mContext).equals("0")) {
                if (PreferenceManager().getReportMailMerge(mContext).equals("1")) {
                    when (position) {
                        0 -> {
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = HomeScreenFragment(
                                mListItemArray.get(position),
                                mDrawerLayout,
                                mHomeListView,
                                linearLayout,
                                mListItemArray,
                                mListImgArray!!
                            )
                            replaceFragmentsSelected(position)
                            headerTitle.visibility = View.GONE
                            logoClickImgView.setVisibility(View.VISIBLE)
                            val mListAdapter = HomeListAdapter(
                                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
                            )
                            mHomeListView.adapter = mListAdapter
                        }

                        1 -> {
                            // Calendar
                            imageButton2.setImageResource(R.drawable.tutorial_icon)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = ListViewCalendar(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CALENDAR
                            )
                            replaceFragmentsSelected(position)
                        }

                        2 -> {
                            // Messages
                            imageButton2.setImageResource(R.drawable.tutorial_icon)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = NotificationFragmentPagination(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_MESSAGES
                            )
                            replaceFragmentsSelected(position)
                        }

                        3 -> {
                            Log.e("news","fragment")
                            // News
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment =
                                NewsFragment(mListItemArray.get(position), bsklTabConstants.TAB_NEWS)
                            replaceFragmentsSelected(position)
                        }

                        4 -> {
                            imageButton2.visibility = View.VISIBLE
                            imageButton2.setImageResource(R.drawable.settings)
                            mFragment = SocialMediaFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_SOCIAL_MEDIA
                            )
                            replaceFragmentsSelected(position)
                        }

                        5 -> {


                            // Report
                            imageButton2.visibility = View.VISIBLE
                            imageButton2.setImageResource(R.drawable.settings)
                            mFragment = ReportFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_REPORT
                            )
                            replaceFragmentsSelected(position)
                        }

                        6 -> {
                            // Absence
                            /* Log.e(
                                "HOME LIST TYPE C6**",
                                PreferenceManager().getHomeListType(mContext)
                            )*/
                            if (PreferenceManager().getHomeListType(mContext).equals("1")) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("2")
                            ) {

                                // Timetable
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                 mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("3")
                            ) {
                                Log.e("WORKING HOME LIST", "CASE!!!")

                                // Attendance
                                imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("4")
                            ) {

                                // Attendance
                                imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE111")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("5")
                            ) {

                                // safeGuarding
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                 mFragment = SafeGuardingFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("6")
                            ) {
                                // safeGuarding
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                 mFragment = SafeGuardingFragment(

                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("7")
                            ) {

                                // safeGuarding
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = SafeGuardingFragment(

                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("8")
                            ) {

                                // SafeGuarding
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = SafeGuardingFragment(

                                )
                                replaceFragmentsSelected(position)
                            } else {
                                // Report Absence
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = AbsenceFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_ABSENCE
                                )
                                replaceFragmentsSelected(position)
                            }
                        }

                        7 -> {
                            Log.e(
                                "HOME LIST TYPE C7**",
                                PreferenceManager().getHomeListType(mContext).toString()
                            )
                            // TAB_SAFE_GUARDING
                            if (PreferenceManager().getHomeListType(mContext).equals("2")) {

                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                                /*  if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }*/
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("3")
                            ) {

                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("4")
                            ) {

                                // Timetable
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("5")
                            ) {
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("6")
                            ) {
                                // Timetable
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("7")
                            ) {
                                Log.e("WORKING HOME LIST", "CASE222")
                                // Attendance
                                imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("8")
                            ) {
                                Log.e("WORKING HOME LIST", "CASE333")
                                // Attendance
                                imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("9")
                            ) {

                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("10")
                            ) {
                                // Timetable
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("11")
                            ) {

                                // Attendance
                                imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE444")
                                 mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("12")
                            ) {

                                // Attendance
                                imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE555")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else {

                                // SafeGuarding
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = SafeGuardingFragment(

                                )
                                replaceFragmentsSelected(position)
                            }
                        }

                        8 -> {
                            // Attendance
                            Log.e(
                                "HOME LIST TYPE C8**",
                                PreferenceManager().getHomeListType(mContext).toString()
                            )
                            if (PreferenceManager().getHomeListType(mContext).equals("4")) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("6")
                            ) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("7")
                            ) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("10")
                            ) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("11")
                            ) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("13")
                            ) {

                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("15")
                            ) {
                                //Attendance
                                imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE666")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("16")
                            ) {
                                //Attendance
                                imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE777")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else {
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            }
                        }
                        9 -> {
                            // TimeTable

                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("16")
                            ) {
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            } else {
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            }
                        }

                        10 -> {
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            }
                            else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        }


                    }
                } else {
                    Log.e("reportmailmerge","0")
                    when (position) {
                        0 -> {
                            // home
//                    mFragment = new HomeScreenRegisteredUserFragment(
//                            mListItemArray[position], mDrawerLayout, mHomeListView,
//                            mListItemArray, mListImgArray);
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = HomeScreenFragment(
                                mListItemArray.get(position),
                                mDrawerLayout,
                                mHomeListView,
                                linearLayout,
                                mListItemArray,
                                mListImgArray!!
                            )
                            replaceFragmentsSelected(position)
                            headerTitle.visibility = View.GONE
                            logoClickImgView.setVisibility(View.VISIBLE)
                            val mListAdapter = HomeListAdapter(
                                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
                            )
                            mHomeListView.adapter = mListAdapter
                        }

                        1 -> {
                            // Calendar
                            imageButton2.setImageResource(R.drawable.tutorial_icon)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = ListViewCalendar(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CALENDAR
                            )
                            replaceFragmentsSelected(position)
                        }

                        2 -> {
                            // Messages
                            imageButton2.setImageResource(R.drawable.tutorial_icon)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = NotificationFragmentPagination(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_MESSAGES
                            )
                            replaceFragmentsSelected(position)
                        }

                         3 -> {
                            // News
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment =
                                NewsFragment(mListItemArray.get(position), bsklTabConstants.TAB_NEWS)
                            replaceFragmentsSelected(position)
                        }

                         4 -> {
                            // Social media
                            imageButton2.visibility = View.VISIBLE
                            imageButton2.setImageResource(R.drawable.settings)
                            mFragment = SocialMediaFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_SOCIAL_MEDIA
                            )
                            replaceFragmentsSelected(position)
                        }

                         5 ->
                    if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available_limitted_access),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        6 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext).equals("1")) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                 if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available_limitted_access),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        7 ->                             // SafeGuarding
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("2") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("3") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("5") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("9")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                  if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available_limitted_access),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        8 ->                             // Attendance
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("4") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("6") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("7") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("10") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("11") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("13")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                 if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available_limitted_access),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }
                        9 -> if (PreferenceManager().getHomeListType(mContext)
                                .equals("8") || PreferenceManager().getHomeListType(mContext)
                                .equals("12") || PreferenceManager().getHomeListType(
                                mContext
                            ).equals("14") || PreferenceManager().getHomeListType(mContext)
                                .equals("15")
                        ) {
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            }
                            else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        } else {
                             if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available_limitted_access),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }
                        }

                        10 -> {
                            // Contact Us
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants. TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            }
                            else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        }

                        else -> {}
                    }
                }
            } else {
                if (PreferenceManager().getReportMailMerge(mContext).equals("0")) {
                    when (position) {
                        0 -> {
                            // home
//                    mFragment = new HomeScreenRegisteredUserFragment(
//                            mListItemArray[position], mDrawerLayout, mHomeListView,
//                            mListItemArray, mListImgArray);
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = HomeScreenFragment(
                                mListItemArray.get(position),
                                mDrawerLayout,
                                mHomeListView,
                                linearLayout,
                                mListItemArray,
                                mListImgArray!!
                            )
                            replaceFragmentsSelected(position)
                            headerTitle.visibility = View.GONE
                            logoClickImgView.setVisibility(View.VISIBLE)
                            val mListAdapter = HomeListAdapter(
                                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
                            )
                            mHomeListView.adapter = mListAdapter
                        }

                        1 ->                             // Calendar
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                         2 ->
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        3 ->                             // News
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        4 ->                             // Social media
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                         5 ->                             // Report
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        6 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext).equals("1")) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                 if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        7 ->                             // Attendance
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("2") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("3") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("5") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("9")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        8 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("4") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("6") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("7") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("10") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("11") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("13")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                 if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        9 -> if (PreferenceManager().getHomeListType(mContext)
                                .equals("8") || PreferenceManager().getHomeListType(mContext)
                                .equals("12") || PreferenceManager().getHomeListType(
                                mContext
                            ).equals("14") || PreferenceManager().getHomeListType(mContext)
                                .equals("15")
                        ) {
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            }
                            else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        } else {
                              if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }
                        }

                        10 -> {
                            // Contact Us
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            }
                            else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        }

                        else -> {}
                    }
                } else {
                    when (position) {
                        0 -> {
                            // home
//                    mFragment = new HomeScreenRegisteredUserFragment(
//                            mListItemArray[position], mDrawerLayout, mHomeListView,
//                            mListItemArray, mListImgArray);
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = HomeScreenFragment(
                                mListItemArray.get(position),
                                mDrawerLayout,
                                mHomeListView,
                                linearLayout,
                                mListItemArray,
                                mListImgArray!!
                            )
                            replaceFragmentsSelected(position)
                            headerTitle.visibility = View.GONE
                            logoClickImgView.setVisibility(View.VISIBLE)
                            val mListAdapter = HomeListAdapter(
                                activity, mListItemArray, mListImgArray!!,R.layout.custom_list_adapter, true
                            )
                            mHomeListView.adapter = mListAdapter
                        }

                         1 ->                             // Calendar
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }
                        2 ->                             // Messages
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                         3 ->                             // News
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        4 ->                             // Social media
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        5 -> {
                            // Report
                            imageButton2.visibility = View.VISIBLE
                            imageButton2.setImageResource(R.drawable.settings)
                            mFragment = ReportFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_REPORT
                            )
                            replaceFragmentsSelected(position)
                        }

                        6 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext).equals("1")) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                 if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        7 ->                             // Attendance
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("2") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("3") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("5") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("9")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        8 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("4") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("6") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("7") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("10") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("11") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("13")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }
                                else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                 if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        9 -> if (PreferenceManager().getHomeListType(mContext)
                                .equals("8") || PreferenceManager().getHomeListType(mContext)
                                .equals("12") || PreferenceManager().getHomeListType(
                                mContext
                            ).equals("14") || PreferenceManager().getHomeListType(mContext)
                                .equals("15")
                        ) {
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            }
                            else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        } else {
                             if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }
                        }

                        10 -> {
                            // Contact Us
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            }
                            else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }


    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
//            || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CALL_PHONE
//                    ,
//                    Manifest.permission.ACCESS_NOTIFICATION_POLICY
                ),
                123
            )
        }
    }
    fun replaceFragmentsSelected(position: Int) {
        if (mFragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.frame_container, mFragment!!,
                    mListItemArray.get(position)
                )
                .addToBackStack(mListItemArray.get(position)).commitAllowingStateLoss()
            mHomeListView.setItemChecked(position, true)
            mHomeListView.setSelection(position)
            mDrawerLayout.closeDrawer(linearLayout)
            supportActionBar!!.setTitle(R.string.null_value)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val actionBar = this@HomeActivity.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar.customView.findViewById<ImageView>(R.id.logoClickImgView)
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(linearLayout)
        } else {
            imageButton2.setImageResource(R.drawable.settings)
            headerTitle.visibility = View.GONE
            logoClickImgView.visibility = View.VISIBLE
            println("popstack count:::" + supportFragmentManager.backStackEntryCount)
            if (supportFragmentManager.backStackEntryCount > 1) {
                val fm = supportFragmentManager
                val currentFragment = fm
                    .findFragmentById(R.id.frame_container)
                println(
                    "bskl current fragment "
                            + currentFragment!!.javaClass.toString()
                )
                if (!(currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.example.bskl_kotlin.fragment.settings.SettingsFragment"
                        ))
                ) {
                    imageButton2.visibility = View.VISIBLE
                } else {
                    imageButton2.visibility = View.INVISIBLE
                }
                if (currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.example.bskl_kotlin.fragment.home.HomeScreenFragment"
                        )
                ) {

                    AppUtils().showAlert(
                        mContext as Activity, resources
                            .getString(R.string.exit_alert), resources
                            .getString(R.string.ok), resources
                            .getString(R.string.cancel), true
                    )
                } else if ((currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.example.bskl_kotlin.fragment.contact_us.ContactUsFragment"
                        )
                            || currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.example.bskl_kotlin.fragment.notification.NotificationFragmentPagination"
                        )
                            || currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.example.bskl_kotlin.fragment.calendar.ListViewCalendar"
                        ) || currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.example.bskl_kotlin.fragment.attendance.AttendenceFragment"
                        ))
                ) {
//                    imageButton.setImageResource(R.drawable.hamburgerbtn);
                    imageButton2.visibility = View.VISIBLE
                    displayView(0)
                    /*|| currentFragment
                        .getClass()
                        .toString()
                        .equalsIgnoreCase(
                                "class com.mobatia.naisapp.fragments.settings.SettingsFragment")*/
                } else {
                    imageButton2.visibility = View.VISIBLE
                    //                    getSupportFragmentManager().popBackStack();
                    displayView(0)
                }
            } else {
                AppUtils().showAlert(
                    mContext as Activity, resources
                        .getString(R.string.exit_alert), resources
                        .getString(R.string.ok),
                    resources.getString(R.string.cancel), true
                )
            }
        }
    }
    fun onClick(v: View?) {
        // settings
        mFragment = SettingsFragment()
        if (mFragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frame_container, mFragment!!, bsklNameConstants.SETTINGS)
                .addToBackStack(bsklNameConstants.SETTINGS).commit()
            mDrawerLayout.closeDrawer(linearLayout)
            supportActionBar!!.setTitle(R.string.null_value)
        }
    }
    /*private fun proceedAfterPermission(position: Int) {
        println("workingPerm1")
        if (!PreferenceManager().getUserId(mContext).equals("")) {
            if (PreferenceManager().getIsVisible(mContext).equals("0")) {
                if (PreferenceManager().getReportMailMerge(mContext).equals("1")) {
                    when (position) {
                        0 -> {
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = HomeScreenFragment(
                                mListItemArray.get(position),
                                mDrawerLayout,
                                mHomeListView,
                                linearLayout,
                                mListItemArray,
                                mListImgArray!!
                            )
                            replaceFragmentsSelected(position)
                            headerTitle.visibility = View.GONE
                            logoClickImgView.visibility = View.VISIBLE
                            val mListAdapter = HomeListAdapter(
                                this, mListItemArray, mListImgArray!!,
                                R.layout.custom_list_adapter, true
                            )
                            mHomeListView.adapter=mListAdapter
                        }

                        1 -> {
                            // Calendar
                             imageButton2.setImageResource(R.drawable.tutorial_icon)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = CalendarFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CALENDAR
                            )
                            replaceFragmentsSelected(position)
                        }

                        2 -> {
                            // Messages
                            imageButton2.setImageResource(R.drawable.tutorial_icon)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = NotificationFragmentPagination(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_MESSAGES
                            )
                            replaceFragmentsSelected(position)
                        }

                        3 -> {
                            // News
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment =
                                NewsFragment(mListItemArray.get(position), bsklTabConstants.TAB_NEWS)
                            replaceFragmentsSelected(position)
                        }

                        4 -> {
                             imageButton2.visibility = View.VISIBLE
                            imageButton2.setImageResource(R.drawable.settings)
                            mFragment = SocialMediaFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_SOCIAL_MEDIA
                            )
                            replaceFragmentsSelected(position)
                        }

                        5 -> {

                            // Report
                            imageButton2.visibility = View.VISIBLE
                            imageButton2.setImageResource(R.drawable.settings)
                            mFragment = ReportFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_REPORT
                            )
                            replaceFragmentsSelected(position)
                        }

                        6 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext).equals("1")) {

                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("2")
                            ) {

                                // Timetable
                                  imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("3")
                            ) {

                                // Attendance
                                  imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE888")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("4")
                            ) {

                                // Attendance
                                 imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE999")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("5")
                            ) {

                                // safeGuarding
                                 imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = SafeGuardingFragment(

                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("6")
                            ) {
                                // safeGuarding
                                  imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = SafeGuardingFragment(

                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("7")
                            ) {

                                // safeGuarding
                                 imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = SafeGuardingFragment(

                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("8")
                            ) {

                                // SafeGuarding
                                  imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = SafeGuardingFragment(

                                )
                                replaceFragmentsSelected(position)
                            } else {
                                // Report Absence
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = AbsenceFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_ABSENCE
                                )
                                replaceFragmentsSelected(position)
                            }

                        7 ->
                            // TAB_SAFE_GUARDING
                            if (PreferenceManager().getHomeListType(mContext).equals("2")) {

                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("3")
                            ) {

                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("4")
                            ) {

                                // Timetable
                                 imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("5")
                            ) {
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("6")
                            ) {
                                // Timetable
                                 imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("7")
                            ) {

                                // Attendance
                                   imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE101010")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("8")
                            ) {

                                // Attendance
                                 imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE121212")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("9")
                            ) {

                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("10")
                            ) {
                                // Timetable
                                 imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("11")
                            ) {

                                // Attendance
                                 imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE131313")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("12")
                            ) {

                                // Attendance
                                imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE151515")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else {

                                // SafeGuarding
                                  imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = SafeGuardingFragment(

                                )
                                replaceFragmentsSelected(position)
                            }

                        8 ->                             // Attendance
                            if (PreferenceManager().getHomeListType(mContext).equals("4")) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("6")
                            ) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("7")
                            ) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("10")
                            ) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("11")
                            ) {


                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("13")
                            ) {

                                // Contactus
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else if (PreferenceManager().getHomeListType(mContext)
                                    .equals("15")
                            ) {
                                //Attendance
                                 imageButton2.setImageResource(R.drawable.tutorial_icon)
                                imageButton2.visibility = View.VISIBLE
                                Log.e("WORKING HOME LIST", "CASE151515")
                                mFragment = AttendenceFragment(
                                )
                                replaceFragmentsSelected(position)
                            } else {
                                 imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            }

                        9 ->                             // TimeTable
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("16")
                            ) {
                                  imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                mFragment = TimeTableFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_TIME_TABLE
                                )
                                replaceFragmentsSelected(position)
                            } else {
                                imageButton2.setImageResource(R.drawable.settings)
                                imageButton2.visibility = View.VISIBLE
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            }

                        10 -> {
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            } else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        }

                        else -> {}
                    }
                } else {
                    when (position) {
                        0 -> {
                            // home
//                    mFragment = new HomeScreenRegisteredUserFragment(
//                            mListItemArray[position], mDrawerLayout, mHomeListView,
//                            mListItemArray, mListImgArray);
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = HomeScreenFragment(
                                mListItemArray.get(position),
                                mDrawerLayout,
                                mHomeListView,
                                linearLayout,
                                mListItemArray,
                                mListImgArray!!
                            )
                            replaceFragmentsSelected(position)
                            headerTitle.visibility = View.GONE
                            logoClickImgView.visibility = View.VISIBLE
                            val mListAdapter = HomeListAdapter(
                                this, mListItemArray, mListImgArray!!,
                                R.layout.custom_list_adapter, true
                            )
                            mHomeListView.adapter=mListAdapter

                        }

                        1 -> {
                            // Calendar
                            *//* imageButton2.setImageResource(R.drawable.tutorial_icon)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = ListViewCalendar(
                                HomeActivity.mListItemArray.get(position),
                                TAB_CALENDAR
                            )
                            replaceFragmentsSelected(position)*//*
                        }

                        2 -> {
                            // Messages
                            imageButton2.setImageResource(R.drawable.tutorial_icon)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = NotificationFragmentPagination(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_MESSAGES
                            )
                            replaceFragmentsSelected(position)
                        }

                        3 -> {
                            // News
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment =
                                NewsFragment(mListItemArray.get(position), bsklTabConstants.TAB_NEWS)
                            replaceFragmentsSelected(position)
                        }

                        4 -> {
                            // Social media
                            *//* imageButton2.visibility = View.VISIBLE
                            imageButton2.setImageResource(R.drawable.settings)
                            mFragment = SocialMediaFragment(
                                HomeActivity.mListItemArray.get(position),
                                TAB_SOCIAL_MEDIA
                            )
                            replaceFragmentsSelected(position)*//*
                        }

                        5 ->                             // Report
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available_limitted_access),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        6 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext).equals("1")) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available_limitted_access),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        7 ->                             // SafeGuarding
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("2") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("3") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("5") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("9")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available_limitted_access),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        8 ->                             // Attendance
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("4") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("6") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("7") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("10") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("11") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("13")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available_limitted_access),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        9 -> if (PreferenceManager().getHomeListType(mContext)
                                .equals("8") || PreferenceManager().getHomeListType(mContext)
                                .equals("12") || PreferenceManager().getHomeListType(
                                mContext
                            ).equals("14") || PreferenceManager().getHomeListType(mContext)
                                .equals("15")
                        ) {
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            } else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        } else {
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available_limitted_access),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }
                        }

                        10 -> {
                            // Contact Us
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            }else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        }

                        else -> {}
                    }
                }
            } else {
                if (PreferenceManager().getReportMailMerge(mContext).equals("0")) {
                    when (position) {
                        0 -> {
                            // home
//                    mFragment = new HomeScreenRegisteredUserFragment(
//                            mListItemArray[position], mDrawerLayout, mHomeListView,
//                            mListItemArray, mListImgArray);
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = HomeScreenFragment(
                                mListItemArray.get(position),
                                mDrawerLayout,
                                mHomeListView,
                                linearLayout,
                                mListItemArray,
                                mListImgArray!!
                            )
                            replaceFragmentsSelected(position)
                            headerTitle.visibility = View.GONE
                            logoClickImgView.visibility = View.VISIBLE
                            val mListAdapter = HomeListAdapter(
                                this, mListItemArray, mListImgArray!!,
                                R.layout.custom_list_adapter, true
                            )
                            mHomeListView.adapter=mListAdapter

                        }

                        1 ->                             // Calendar
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        2 ->                             // Messages
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        3 ->                             // News
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        4 ->                             // Social media
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        5 ->                             // Report
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        6 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext).equals("1")) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        7 ->                             // Attendance
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("2") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("3") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("5") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("9")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        8 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("4") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("6") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("7") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("10") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("11") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("13")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        9 -> if (PreferenceManager().getHomeListType(mContext)
                                .equals("8") || PreferenceManager().getHomeListType(mContext)
                                .equals("12") || PreferenceManager().getHomeListType(
                                mContext
                            ).equals("14") || PreferenceManager().getHomeListType(mContext)
                                .equals("15")
                        ) {
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            } else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        } else {
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }
                        }

                        10 -> {
                            // Contact Us
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            } else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        }

                        else -> {}
                    }
                } else {
                    when (position) {
                        0 -> {
                            // home
//                    mFragment = new HomeScreenRegisteredUserFragment(
//                            mListItemArray[position], mDrawerLayout, mHomeListView,
//                            mListItemArray, mListImgArray);
                            imageButton2.setImageResource(R.drawable.settings)
                            imageButton2.visibility = View.VISIBLE
                            mFragment = HomeScreenFragment(
                                mListItemArray.get(position),
                                mDrawerLayout,
                                mHomeListView,
                                linearLayout,
                                mListItemArray,
                                mListImgArray!!
                            )
                            replaceFragmentsSelected(position)
                            headerTitle.visibility = View.GONE
                            logoClickImgView.visibility = View.VISIBLE
                            val mListAdapter = HomeListAdapter(
                                this, mListItemArray, mListImgArray!!,
                                R.layout.custom_list_adapter, true
                            )
                            mHomeListView.adapter=mListAdapter

                        }

                        1 ->                             // Calendar
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        2 ->                             // Messages
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        3 ->                             // News
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        4 ->                             // Social media
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                        5 -> {
                            // Report
                            imageButton2.visibility = View.VISIBLE
                            imageButton2.setImageResource(R.drawable.settings)
                            mFragment = ReportFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_REPORT
                            )
                            replaceFragmentsSelected(position)
                        }

                        6 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext).equals("1")) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                }else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        7 ->                             // Attendance
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("2") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("3") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("5") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("9")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US

                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        8 ->                             // Absence
                            if (PreferenceManager().getHomeListType(mContext)
                                    .equals("4") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("6") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("7") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("10") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("11") || PreferenceManager().getHomeListType(
                                    mContext
                                ).equals("13")
                            ) {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                                if (ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                        mContext,
                                        Manifest.permission.CALL_PHONE
                                    ) != PackageManager.PERMISSION_GRANTED
                                )
                                {
                                    checkPermission()


                                } else {
                                    mFragment = ContactUsFragment(
                                        mListItemArray.get(position),
                                        bsklTabConstants.TAB_CONTACT_US
                                    )
                                    replaceFragmentsSelected(position)
                                }
                            } else {
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                        9 -> if (PreferenceManager().getHomeListType(mContext)
                                .equals("8") || PreferenceManager().getHomeListType(mContext)
                                .equals("12") || PreferenceManager().getHomeListType(
                                mContext
                            ).equals("14") || PreferenceManager().getHomeListType(mContext)
                                .equals("15")
                        ) {
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            } else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        } else {
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }
                        }

                        10 -> {
                            // Contact Us
                            mFragment = ContactUsFragment(
                                mListItemArray.get(position),
                                bsklTabConstants.TAB_CONTACT_US
                            )
                            replaceFragmentsSelected(position)
                            if (ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            )
                            {
                                checkPermission()


                            } else {
                                mFragment = ContactUsFragment(
                                    mListItemArray.get(position),
                                    bsklTabConstants.TAB_CONTACT_US
                                )
                                replaceFragmentsSelected(position)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
//        replaceFragmentsSelected(position);
    }*/

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        return false
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
  /*  override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View,
        position: Int,
        id: Long
    ): Boolean {
        val fm = supportFragmentManager
        val currentFragment = fm.findFragmentById(R.id.frame_container)
        println(
            "bskl current fragment "
                    + currentFragment!!.javaClass.toString()
        )
        if ((currentFragment
                .javaClass
                .toString()
                .equals(
                    "class com.mobatia.bskl.fragment.home.HomeScreenFragment", ignoreCase = true
                ))
        ) {
            if (position >= 3) {
                // drag list view item
                PreferenceManager().setIfHomeItemClickEnabled(mContext, true)
                sPosition = position
                view.setBackgroundColor(mContext.resources.getColor(R.color.split_bg))
                val shadowBuilder = View.DragShadowBuilder(view)
                val data = ClipData.newPlainText("", "")
                view.startDrag(data, shadowBuilder, view, 0)
                view.visibility = View.VISIBLE
                mDrawerLayout.closeDrawer(linearLayout)
            } else {
                // if home in list view is selected
                AppUtils().showAlert(
                    mContext as Activity,
                    resources.getString(R.string.drag_impossible), "",
                    resources.getString(R.string.ok), false
                )
                PreferenceManager().setIfHomeItemClickEnabled(mContext, true)
            }
            return true
        } else {
            return false
        }
    }*/

    private fun askForNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        val fm = supportFragmentManager
        val currentFragment = fm.findFragmentById(R.id.frame_container)
        Log.e(
            "bskl current fragment "
                    ,currentFragment!!.javaClass.toString()
        )
        if ((currentFragment
                .javaClass
                .toString()
                .equals(
                    "class com.example.bskl_kotlin.fragment.home.HomeScreenFragment"
                ))
        ) {
            if (position >= 3) {
                // drag list view item
                PreferenceManager().setIfHomeItemClickEnabled(mContext, true)
                sPosition = position
                view!!.setBackgroundColor(mContext.resources.getColor(R.color.split_bg))
                val shadowBuilder = View.DragShadowBuilder(view)
                val data = ClipData.newPlainText("", "")
                view!!.startDrag(data, shadowBuilder, view, 0)
                view!!.visibility = View.VISIBLE
                mDrawerLayout.closeDrawer(linearLayout)
            } else {
                // if home in list view is selected
                AppUtils().showAlert(
                    mContext as Activity,
                    resources.getString(R.string.drag_impossible), "",
                    resources.getString(R.string.ok), false
                )
                PreferenceManager().setIfHomeItemClickEnabled(mContext, true)
            }
            return true
        } else {
            return false
        }
    }
}

