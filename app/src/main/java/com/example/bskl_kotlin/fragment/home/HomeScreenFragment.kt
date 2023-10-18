package com.example.bskl_kotlin.fragment.home

import ApiClient
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.data_collection.DataCollectionHome
import com.example.bskl_kotlin.activity.datacollection_p2.model.ContactTypeModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.DataCollectionDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.GlobalListDataModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.GlobalListSirname
import com.example.bskl_kotlin.activity.datacollection_p2.model.InsuranceDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.KinModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.OwnContactModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.PassportDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.StudentModelNew
import com.example.bskl_kotlin.activity.home.HomeActivity
import com.example.bskl_kotlin.activity.home.model.AppFeatureModel
import com.example.bskl_kotlin.activity.home.model.NationalityModel
import com.example.bskl_kotlin.activity.home.model.StudentDetailSettingModel
import com.example.bskl_kotlin.activity.home.model.TimeTableStudentModel
import com.example.bskl_kotlin.activity.home.model.UserDetailsModel
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.common.model.StudentListModel
import com.example.bskl_kotlin.common.model.StudentListResponseModel
import com.example.bskl_kotlin.constants.BsklNameConstants
import com.example.bskl_kotlin.constants.BsklTabConstants
import com.example.bskl_kotlin.fragment.absence.AbsenceFragment
import com.example.bskl_kotlin.fragment.calendar.CalendarFragment
import com.example.bskl_kotlin.fragment.contactus.ContactUsFragment
import com.example.bskl_kotlin.fragment.home.model.CountriesModel
import com.example.bskl_kotlin.fragment.messages.NotificationFragmentPagination
import com.example.bskl_kotlin.fragment.news.NewsFragment
import com.example.bskl_kotlin.fragment.reports.ReportFragment
import com.example.bskl_kotlin.fragment.social_media.SocialMediaFragment
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var mContext: Context
lateinit var firstRelativeLayout: RelativeLayout
lateinit var secondRelativeLayout: RelativeLayout
lateinit var thirdRelativeLayout: RelativeLayout
lateinit var fourthRelativeLayout: RelativeLayout
lateinit var firstTextView: TextView
lateinit var secondTextView: TextView
lateinit var thirdTextView: TextView
lateinit var safeGuardingBadge: TextView
lateinit var alreadyTriggered: String
lateinit var fourthTextView: TextView
lateinit var secondImageView: ImageView
lateinit var thirdImageView: ImageView
private var viewTouched: View? = null
private var isDraggable = false
private lateinit var mSectionText: Array<String?>
lateinit var bsklTabConstants: BsklTabConstants
lateinit var bsklNameConstants: BsklNameConstants
private var TAB_ID: String? = null
var tabiDToProceed = ""
lateinit var homeActivity: HomeActivity
lateinit var messageBtn: Button
lateinit var newsBtn: Button
lateinit var socialBtn: Button
lateinit var calendarBtn: Button
private var INTENT_TAB_ID: String? = null
lateinit var studentsModelArrayList: ArrayList<StudentModelNew>
lateinit var mNationlityListArrrayList: ArrayList<NationalityModel>
lateinit var mGlobalListDataArrayList: ArrayList<GlobalListDataModel>
lateinit var ContactTypeArray: ArrayList<ContactTypeModel>

lateinit var mGlobalListSirname:ArrayList<GlobalListSirname>
lateinit var ContactListArray: ArrayList<GlobalListSirname>
lateinit var InsuranceHealthListArray: ArrayList<InsuranceDetailModel>
lateinit var PassportDetailListArray: ArrayList<PassportDetailModel>
lateinit var KinArray: ArrayList<KinModel>
lateinit var mGlobalListSirnameArrayList: ArrayList<GlobalListSirname>
lateinit var mOwnContactArrayList: ArrayList<OwnContactModel>
var mListImgArrays: TypedArray? = null
var listitemArrays: Array<String>? = null
class HomeScreenFragment(title:String,
                         mDrawerLayouts: DrawerLayout, listView: ListView, linearLayout: LinearLayout,
                         mListItemArray:Array<String>, mListImgArray: TypedArray
): Fragment() , View.OnClickListener {



    private var android_app_version: String? = null

    //private val mSectionText: Array<String>


    lateinit var img: ImageView
    var currentPage = 0
    var isKinFound = false
    var isLocalFound = false
    private val PERMISSION_CALLBACK_CONSTANT_CALENDAR = 1
    private val PERMISSION_CALLBACK_CONSTANT_EXTERNAL_STORAGE = 2
    private val PERMISSION_CALLBACK_CONSTANT_LOCATION = 3
    private val REQUEST_PERMISSION_CALENDAR = 101
    private val REQUEST_PERMISSION_EXTERNAL_STORAGE = 102
    private val REQUEST_PERMISSION_LOCATION = 103


    private var calendarPermissionStatus: SharedPreferences? = null
    private var externalStoragePermissionStatus: SharedPreferences? = null
    private var locationPermissionStatus: SharedPreferences? = null


    lateinit var studentDetail:ArrayList<StudentDetailSettingModel>
    lateinit var timeTableStudArray:ArrayList<TimeTableStudentModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home_activity, container, false)
    }
    @SuppressLint("UseRequireInsteadOfGet", "Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeActivity =  activity as HomeActivity
        mContext=requireContext()
        bsklTabConstants = BsklTabConstants()
        bsklNameConstants = BsklNameConstants()
        studentsModelArrayList= ArrayList()
        mListImgArrays = context!!.resources.obtainTypedArray(R.array.home_list_reg_icons_15)
        listitemArrays = resources.getStringArray(R.array.home_list_content_items_15)
        LocalBroadcastManager.getInstance(mContext)
            .registerReceiver(mMessageReceiver,
                IntentFilter("badgenotify")
            )

        calendarPermissionStatus =
            activity!!.getSharedPreferences("calendarPermissionStatus", Context.MODE_PRIVATE)
        externalStoragePermissionStatus =
            activity!!.getSharedPreferences("externalStoragePermissionStatus", Context.MODE_PRIVATE)
        locationPermissionStatus =
            activity!!.getSharedPreferences("locationPermissionStatus", Context.MODE_PRIVATE)
        initUi()
        mSectionText = arrayOfNulls<String>(4)
        /*FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isComplete) {
                val token = task.result
                tokenM = token
            }
        }*/
        //Log.e("DEVICE ID", tokenM)
        setListeners()
        setDragListenersForButtons()
        getButtonBgAndTextImages()
    }

    private fun setListeners() {
        messageBtn.setOnClickListener(this)
        newsBtn.setOnClickListener(this)
        calendarBtn.setOnClickListener(this)
        socialBtn.setOnClickListener(this)
    }

    var mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val identifierString = intent.action
            if (identifierString == "badgenotify") {
                if (AppUtils().isNetworkConnected(mContext)) {
                    showSettingUserDetail()
                }
            }
        }
    }
    private fun getButtonBgAndTextImages() {
        if (PreferenceManager().getButtonTwoGuestTextImage(mContext)!!.toInt() != 0
        ) {
            val sdk = Build.VERSION.SDK_INT
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                secondImageView.setImageDrawable(
                    mListImgArrays!!.getDrawable(
                        PreferenceManager().getButtonTwoGuestTextImage(mContext)!!.toInt()
                    )
                )
            } else {
                Log.e("app",PreferenceManager().getButtonTwoGuestTextImage(mContext)!!.toInt().toString())
                secondImageView.background = mListImgArrays!!.getDrawable(
                    PreferenceManager().getButtonTwoGuestTextImage(mContext)!!.toInt()
                )
            }
            var relOneStr: String? = ""
            if (listitemArrays!!.get(
                    PreferenceManager().getButtonTwoGuestTextImage(mContext)!!.toInt()
                ).toString().equals(bsklNameConstants.NEWS) || listitemArrays!!.get(
                    PreferenceManager().getButtonTwoGuestTextImage(mContext)!!.toInt()
                ).toString().equals(bsklNameConstants.BSKL_NEWS)
            ) {
                relOneStr = bsklNameConstants.BSKL_NEWS
            } else {
                relOneStr = listitemArrays!!.get(
                    PreferenceManager().getButtonTwoGuestTextImage(mContext)!!.toInt()
                )
            }
            secondTextView.text = relOneStr
        }
        Log.e("pref",PreferenceManager().getButtonThreeGuestTextImage(mContext)!!.toString())
        try{
            var i:Int = Integer.parseInt(PreferenceManager().getButtonThreeGuestTextImage(mContext)!!);

        if (PreferenceManager().getButtonThreeGuestTextImage(mContext)!!.toString().toInt() != 0
        ) {
            val sdk = Build.VERSION.SDK_INT
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                thirdImageView.setImageDrawable(
                    mListImgArrays!!.getDrawable(
                        PreferenceManager().getButtonThreeGuestTextImage(mContext)!!.toInt()
                    )
                )
            } else {
                thirdImageView.background = mListImgArrays!!.getDrawable(
                    PreferenceManager().getButtonThreeGuestTextImage(mContext)!!.toInt()
                )
            }
            var relTwoStr: String? = ""
            // System.out.println("three text " + PreferenceManager.getButtonThreeGuestTextImage(mContext));
            if (listitemArrays!!.get(
                    PreferenceManager().getButtonThreeGuestTextImage(mContext)!!.toInt()
                ).toString().equals(bsklNameConstants.NEWS) || listitemArrays!!.get(
                    PreferenceManager().getButtonThreeGuestTextImage(mContext)!!.toInt()
                ).toString().equals(bsklNameConstants.BSKL_NEWS)
            ) {
                relTwoStr = bsklNameConstants.BSKL_NEWS
            } else {
                relTwoStr = listitemArrays!!.get(
                    PreferenceManager().getButtonThreeGuestTextImage(mContext)!!.toInt()
                )
            }
            thirdTextView.text = relTwoStr
        }
        } catch( ex:NumberFormatException){ // handle your exception

        }
    }

    private fun initUi(){
        img = requireView().findViewById<ImageView>(R.id.img)
        messageBtn = requireView().findViewById<Button>(R.id.messageBtn)
        newsBtn = requireView().findViewById<Button>(R.id.newsBtn)
        socialBtn = requireView().findViewById<Button>(R.id.socialBtn)
        calendarBtn = requireView().findViewById<Button>(R.id.calendarBtn)
        firstRelativeLayout = requireView().findViewById<RelativeLayout>(R.id.firstRelativeLayout)
        secondRelativeLayout = requireView().findViewById<RelativeLayout>(R.id.secondRelativeLayout)
        thirdRelativeLayout = requireView().findViewById<RelativeLayout>(R.id.thirdRelativeLayout)
        fourthRelativeLayout = requireView().findViewById<RelativeLayout>(R.id.fourthRelativeLayout)
        firstTextView = requireView().findViewById<TextView>(R.id.firstTextView)
        secondTextView = requireView().findViewById<TextView>(R.id.secondTextView)
        thirdTextView = requireView().findViewById<TextView>(R.id.thirdTextView)
        fourthTextView = requireView().findViewById<TextView>(R.id.fourthTextView)
        secondImageView = requireView().findViewById<ImageView>(R.id.secondImageView)
        thirdImageView = requireView().findViewById<ImageView>(R.id.thirdImageView)
        safeGuardingBadge = requireView().findViewById<TextView>(R.id.safeGuardingBadge)


        if (AppUtils().isNetworkConnected(mContext)) {
            showSettingUserDetail()
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
   /* var mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val identifierString = intent.action
            if (identifierString == "badgenotify") {
                if (AppUtils().isNetworkConnected(mContext)) {
                    showSettingUserDetail()
                }
            }
        }
    }*/
    private fun setDragListenersForButtons() {
        firstRelativeLayout.setOnDragListener(DropListener())
        secondRelativeLayout.setOnDragListener(DropListener())
        thirdRelativeLayout.setOnDragListener(DropListener())
        fourthRelativeLayout.setOnDragListener(DropListener())
    }
    private class DropListener() : View.OnDragListener {
        override fun onDrag(view: View, event: DragEvent): Boolean {
            PreferenceManager().setIfHomeItemClickEnabled(mContext,true)
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {}
                DragEvent.ACTION_DRAG_ENTERED -> {}
                DragEvent.ACTION_DRAG_EXITED -> {}
                DragEvent.ACTION_DROP -> {
                  homeActivity.mDrawerLayout.closeDrawer(homeActivity.linearLayout!!)
                    val arr = IntArray(2)
                    view.getLocationInWindow(arr)
                    val x = arr[0]
                    val y = arr[1]

                    getButtonViewTouched(x.toFloat(), y.toFloat())
                    if (viewTouched == secondRelativeLayout) {
                        mSectionText[0] = secondTextView.getText().toString()
                        mSectionText[1] = thirdTextView.getText().toString()
                        mSectionText[2] = thirdTextView.getText().toString()
                        mSectionText[3] = fourthTextView.getText().toString()
                        var i = 0
                        while (i < mSectionText.size) {
                            isDraggable = true
                            if (mSectionText.get(i)
                                    .equals(
                                        listitemArrays!!.get(homeActivity.sPosition)
                                    )
                            ) {
                                isDraggable = false
                                break
                            }
                            i++
                        }
                        if (isDraggable) {
                            getButtonDrawablesAndText(
                                viewTouched!!,
                                homeActivity.sPosition
                            )
                        } else {
                            AppUtils().showAlert(
                                mContext as Activity, mContext.getResources().getString(R.string.drag_duplicate),
                                "",
                                mContext.getResources().getString(R.string.ok),
                                false
                            )
                        }
                    } else {
                        AppUtils().showAlert(
                            mContext as Activity,
                            mContext.getResources().getString(R.string.drag_impossible), "",
                            mContext.getResources().getString(R.string.ok), false
                        )
                    }
                }

                DragEvent.ACTION_DRAG_ENDED -> {}
                else -> {}
            }
            return true
        }



        private fun checkPermission() {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
//            || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    homeActivity,
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


        private fun getButtonViewTouched(centerX: Float, centerY: Float) {
            val arr1 = IntArray(2)
            firstRelativeLayout.getLocationInWindow(arr1)
            val x1 = arr1[0]
            val x2 = x1 + firstRelativeLayout.width
            val y1 = arr1[1]
            val y2 = y1 + firstRelativeLayout.height

            // button two
            val arr2 = IntArray(2)
            secondRelativeLayout.getLocationInWindow(arr2)
            val x3 = arr2[0]
            val x4 = x3 + secondRelativeLayout.width
            val y3 = arr2[1]
            val y4 = y3 + secondRelativeLayout.height

            // button three
            val arr3 = IntArray(2)
            thirdRelativeLayout.getLocationInWindow(arr3)
            val x5 = arr3[0]
            val x6 = x5 + thirdRelativeLayout.width
            val y5 = arr3[1]
            val y6 = y5 + thirdRelativeLayout.height

            // button four
            val arr4 = IntArray(2)
            fourthRelativeLayout.getLocationInWindow(arr4)
            val x7 = arr4[0]
            val x8 = x7 + fourthRelativeLayout.width
            val y7 = arr4[1]
            val y8 = y7 + fourthRelativeLayout.height
            if (x7 <= centerX && centerX <= x8 && y7 <= centerY && centerY <= y8) {
                viewTouched = fourthRelativeLayout
            } else if (x5 <= centerX && centerX <= x6 && y5 <= centerY && centerY <= y6) {
                viewTouched = thirdRelativeLayout
            } else if (x3 <= centerX && centerX <= x4 && y3 <= centerY && centerY <= y4) {
                viewTouched = secondRelativeLayout
            } else {
                viewTouched = null
            }
        }

        private fun getButtonDrawablesAndText(v:View, position: Int) {
            var v: View? = v
            if (position >= 3) {
                if (v == secondRelativeLayout) {
                    var relTwoStr: String? = ""
                    if (listitemArrays!!.get(position).equals(bsklNameConstants.BSKL_NEWS)) {
                        relTwoStr = bsklNameConstants.NEWS
                    } else {
                        relTwoStr = listitemArrays!!.get(position)
                    }
                    secondTextView.text = relTwoStr
                    getTabId(listitemArrays!!.get(position))
                    PreferenceManager().setButtonTwoGuestTabId(mContext, TAB_ID)
                    PreferenceManager().setButtonTwoGuestTextImage(
                        mContext,
                        Integer.toString(position)
                    )
                    val sdk = Build.VERSION.SDK_INT
                    if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                        secondImageView.setImageDrawable(
                            mListImgArrays!!.getDrawable(
                                PreferenceManager().getButtonTwoGuestTextImage(mContext)!!.toInt()
                            )
                        )
                    } else {
                        secondImageView.background = mListImgArrays!!.getDrawable(
                            PreferenceManager().getButtonTwoGuestTextImage(mContext)!!.toInt()
                        )
                    }
                } else if (v == thirdRelativeLayout) {
                    var relTwoStr: String? = ""
                    if (listitemArrays!!.get(position).equals(bsklNameConstants.BSKL_NEWS)) {
                        relTwoStr = bsklNameConstants.BSKL_NEWS
                    } else {
                        relTwoStr = listitemArrays!!.get(position)
                    }
                    thirdTextView.text = relTwoStr
                    getTabId(listitemArrays!!.get(position))
                    PreferenceManager().setButtonThreeGuestTabId(mContext, TAB_ID)
                    PreferenceManager().setButtonThreeGuestTextImage(
                        mContext,
                        Integer.toString(position)
                    )
                    val sdk = Build.VERSION.SDK_INT
                    if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                        thirdImageView.setImageDrawable(
                            mListImgArrays!!.getDrawable(
                                PreferenceManager().getButtonThreeGuestTextImage(mContext)!!.toInt()
                            )
                        )
                    } else {
                        thirdImageView.background = mListImgArrays!!.getDrawable(
                            PreferenceManager().getButtonThreeGuestTextImage(mContext)!!.toInt()
                        )
                    }
                } else {
                    AppUtils().showAlert(
                        mContext as Activity,
                        mContext.resources.getString(R.string.drag_impossible), "",
                        mContext.resources.getString(R.string.ok), false
                    )
                }
            } else {
                AppUtils().showAlert(
                    mContext as Activity,
                    mContext.resources.getString(R.string.drag_impossible), "",
                    mContext.resources.getString(R.string.ok), false
                )
            }
            v = null
            viewTouched = null
        }

        private fun getTabId(text: String) {
            if (text.equals(bsklNameConstants.CALENDAR)) {
                TAB_ID = bsklTabConstants.TAB_CALENDAR
            } else if (text.equals(bsklNameConstants.MESSAGE)) {
                TAB_ID = bsklTabConstants.TAB_MESSAGES
            } else if (text.equals(bsklNameConstants.NEWS) || text.equals(
                    bsklNameConstants.BSKL_NEWS
                )
            ) {
                TAB_ID = bsklTabConstants.TAB_NEWS
            } else if (text.equals(bsklNameConstants.SOCIAL_MEDIA)) {
                TAB_ID = bsklTabConstants.TAB_SOCIAL_MEDIA
            } else if (text.equals(bsklNameConstants.REPORT)) {
                TAB_ID = bsklTabConstants.TAB_REPORT
            } else if (text.equals(bsklNameConstants.PROGRESS_REPORT)) {
                TAB_ID = bsklTabConstants.TAB_REPORT
            } else if (text.equals(bsklNameConstants.ABSENCE)) {
                TAB_ID = bsklTabConstants.TAB_ABSENCE
            } else if (text.equals(bsklNameConstants.SAFE_GUARDING)) {
                TAB_ID = bsklTabConstants.TAB_SAFE_GUARDING
            } else if (text.equals(bsklNameConstants.ATTENDANCE)) {
                TAB_ID = bsklTabConstants.TAB_ATTENDANCE
            } else if (text.equals(bsklNameConstants.TIMETABLE)) {
                TAB_ID = bsklTabConstants.TAB_TIME_TABLE
            } else if (text.equals(bsklNameConstants.CONTACT_US)) {
                TAB_ID = bsklTabConstants.TAB_CONTACT_US
            }
        }


        fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String?>) {
            Toast.makeText(
                mContext,
                "Permission Denied\n$deniedPermissions",
                Toast.LENGTH_SHORT
            ).show()
        }
    }



    fun showSettingUserDetail() {
        val call: Call<UserDetailsModel> = ApiClient.getClient.user_details(
            PreferenceManager().getaccesstoken(mContext).toString(),
            PreferenceManager().getUserId(mContext).toString()
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
                        PreferenceManager().setCorrespondenceMailMerge(
                            mContext,
                            responsedata!!.response.responseArray.correspondencemailmerge
                        )
                        PreferenceManager().setsafenote(
                            mContext,responsedata!!.response.responseArray.safeguarding_notification
                        )
                        android_app_version = responsedata!!.response.android_version
                        PreferenceManager().setVersionFromApi(
                            mContext,
                            responsedata!!.response.android_version
                        )
                        PreferenceManager().setDataCollection(
                            mContext,
                            responsedata
                            !!.response.responseArray.data_collection
                        )
                        PreferenceManager().setDataCollectionTriggerType(
                            mContext,
                            responsedata
                            !!.response.responseArray.trigger_type
                        )
                        val prefAlreadyTriggered: String? =
                            PreferenceManager().getAlreadyTriggered(mContext)
                        Log.e("trigger",responsedata!!.response.responseArray.trigger_date)
                        alreadyTriggered =   responsedata!!.response.responseArray.trigger_date

                        val versionFromPreference: String =
                            PreferenceManager().getVersionFromApi(mContext)!!.replace(".", "")
                        val versionNumberAsInteger = versionFromPreference.toInt()
                        if (prefAlreadyTriggered.equals(alreadyTriggered)) {
                            PreferenceManager().setAlreadyTriggered(
                                mContext,responsedata!!.response.responseArray.trigger_date

                            )
                        } else {
                            PreferenceManager().setAlreadyTriggered(
                                mContext,
                                responsedata!!.response.responseArray.trigger_date
                            )
                            PreferenceManager().setIsAlreadyEnteredOwnContact(mContext, false)
                            PreferenceManager().setIsAlreadyEnteredKin(mContext, false)
                            PreferenceManager().setIsAlreadyEnteredInsurance(mContext, false)
                            PreferenceManager().setIsAlreadyEnteredPassport(mContext, false)
                            PreferenceManager().setIsAlreadyEnteredStudentList(mContext, false)
                            AppController().kinArrayShow.clear()
                            AppController().kinArrayPass.clear()
                            var mOwnArrayList: ArrayList<OwnContactModel>? =
                                PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)
                            mOwnArrayList= ArrayList()
                            //PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!.clear()
                            PreferenceManager().saveOwnDetailArrayList(
                                mOwnArrayList,
                                "OwnContact",
                                mContext
                            )
                            PreferenceManager().saveKinDetailsArrayListShow(
                                AppController().kinArrayShow,
                                mContext
                            )
                            PreferenceManager().saveKinDetailsArrayList(
                                AppController().kinArrayPass,
                                mContext
                            )
                            var mInsurance: ArrayList<InsuranceDetailModel> = ArrayList()
//                                PreferenceManager().getInsuranceDetailArrayList(mContext)

                            mInsurance= ArrayList()
                            PreferenceManager().saveInsuranceDetailArrayList(mInsurance, mContext)
                            var mPassport: ArrayList<PassportDetailModel> = ArrayList()
//                                PreferenceManager().getPassportDetailArrayList(mContext)
                            mPassport= ArrayList()
                            PreferenceManager().savePassportDetailArrayList(mPassport, mContext)
                            AppController().mStudentDataArrayList.clear()
                            var mStuudent: ArrayList<StudentModelNew> = ArrayList()
                               // PreferenceManager().getInsuranceStudentList(mContext)
                            mStuudent= ArrayList()
                            PreferenceManager().saveInsuranceStudentList(mStuudent, mContext)
                        }

                        if (PreferenceManager().getDataCollection(mContext).equals("1")) {
                            println("SHOW: 1")
                            Handler().postDelayed({
                                if (PreferenceManager().getSuspendTrigger(mContext)
                                        .equals("0")
                                ) {
                                    getStudentsListAPI()
                                    showDataCollectionUserDetail()
                                    getCountryList()
                                } else {
                                    println("Dont show triggered window")
                                }
                            }, 3000)
                        } else {
                            println("SHOW: <1")
                        }
                        //                            }

                        // System.out.println("app version from api" + android_app_version);
                        PreferenceManager().setVersionFromApi(mContext, android_app_version)
                        val respObjAppFeature: AppFeatureModel = responsedata!!.response.app_feature
                        PreferenceManager().setTimeTable(
                            mContext,
                            respObjAppFeature.timetable
                        )
                        PreferenceManager().setTimeTableGroup(
                            mContext,
                            respObjAppFeature.timetable_group
                        )
                        PreferenceManager().setSafeGuarding(
                            mContext,
                            respObjAppFeature.safeguarding
                        )
                        PreferenceManager().setAttendance(
                            mContext,
                            respObjAppFeature.attendance
                        )
                        PreferenceManager().setAbsence(
                            mContext,
                            respObjAppFeature.report_absense
                        )
                        if (responsedata.response.responseArray.studentdetails.size > 0) {
                            studentDetail = ArrayList()
                            for (i in responsedata.response.responseArray.studentdetails.indices) {

                                var nmodel = StudentDetailSettingModel(
                                    responsedata.response.responseArray.studentdetails[i].id.toString(),
                                    responsedata.response.responseArray.studentdetails[i].alumi.toString(),
                                    responsedata.response.responseArray.studentdetails[i].applicant.toString()
                                )
                                studentDetail.add(nmodel)
                            }

                        }
                        var alumini = 0
                        for (x in studentDetail.indices) {
                            if (studentDetail.get(x).alumi.equals("0")) {
                                alumini = 0
                                break
                            } else {
                                alumini = 1
                            }
                        }
                        var applicant = 0
                        for (x in studentDetail.indices) {
                            if (studentDetail.get(x).applicant.equals("0")) {
                                applicant = 0
                                break
                            } else {
                                applicant = 1
                            }
                        }
                        PreferenceManager().setIsVisible(mContext, alumini.toString())
                        PreferenceManager().setIsApplicant(mContext, applicant.toString())
                        if (PreferenceManager().getMessageBadge(mContext).equals("0")) {
                            messageBtn.text = ""
                        } else {
                            messageBtn.setText(PreferenceManager().getMessageBadge(mContext))
                        }
                        if (PreferenceManager().getCalenderBadge(mContext).equals("0")) {
                            calendarBtn.text = ""
                        } else {
                            calendarBtn.setText(PreferenceManager().getCalenderBadge(mContext))
                        }
                        if (PreferenceManager().getsafenote(mContext).equals("0")) {
                            safeGuardingBadge.visibility = View.GONE
                            img.visibility = View.GONE
                        } else {
                            safeGuardingBadge.visibility = View.VISIBLE
                            img.visibility = View.VISIBLE
                            safeGuardingBadge.setText(PreferenceManager().getsafenote(mContext))
                        }
                        if (PreferenceManager().getMessageBadge(mContext).equals("")) {
                            if (PreferenceManager().getCalenderBadge(mContext).equals("")) {
                            } else {
                                /* ShortcutBadger.applyCount(
                                     mContext,
                                     Integer.valueOf(PreferenceManager().getCalenderBadge(mContext))
                                 )*/
                            }
                        } else {
                            if (PreferenceManager().getCalenderBadge(mContext).equals("")) {
                                /* ShortcutBadger.applyCount(
                                     mContext,
                                     Integer.valueOf(PreferenceManager().getMessageBadge(mContext))
                                 )*/
                            } else {
                                /* ShortcutBadger.applyCount(
                                     mContext,
                                     Integer.valueOf(PreferenceManager().getMessageBadge(mContext)) + Integer.valueOf(
                                         PreferenceManager().getCalenderBadge(mContext)
                                     )
                                 )*/
                            }
                        }

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
                        var primarySafeGuarding = ""
                        var secondarySafeGuarding = ""
                        var eyfsSafeGuarding = ""

                        var primaryTimeTable = ""
                        var secondaryTimeTable = ""
                        var eyfsTimeTable = ""
                        timeTableStudArray=ArrayList()
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
                        if (timeTableStudArray.size > 0) {
                            for (s in timeTableStudArray.indices) {
                                if (timeTableStudArray.get(s).type
                                        .equals("Primary")
                                ) {
                                    primarySafeGuarding = "1"
                                    primaryTimeTable = "1"
                                } else if (timeTableStudArray.get(s).type
                                        .equals("Secondary")
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

                        if (respObjAppFeature.attendance
                                .equals("1")
                        ) {
                            // attendance enable
                            PreferenceManager().setHomeAttendance(mContext, "1")
                        } else {
                            PreferenceManager().setHomeAttendance(mContext, "0")
                        }


                        if (respObjAppFeature.report_absense
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
                        PreferenceManager().setTimeTable(
                            mContext,
                            respObjAppFeature.timetable
                        )
                        PreferenceManager().setTimeTableGroup(
                            mContext,
                            respObjAppFeature.timetable_group
                        )
                        PreferenceManager().setSafeGuarding(
                            mContext,
                            respObjAppFeature.safeguarding
                        )
                        PreferenceManager().setSafeGuardingGroup(
                            mContext,
                            respObjAppFeature.safeguarding_group
                        )
                        PreferenceManager().setAttendance(
                            mContext,
                            respObjAppFeature.attendance
                        )
                        PreferenceManager().setAbsence(
                            mContext,
                            respObjAppFeature.report_absense
                        )

                        var isAvailable = false
                        if (PreferenceManager().getTimeTableGroup(mContext).equals("1")) {
                            println("working if datamodel")
                            for (i in timeTableStudArray.indices) {
                                println("working if datamodel")
                                System.out.println(
                                    "working if datamodel type" + timeTableStudArray.get(
                                        i
                                    ).type
                                )
                                if (timeTableStudArray.get(i).type
                                        .equals("Primary")
                                ) {
                                    println("working if datamodel")
                                    isAvailable = true
                                }
                            }
                        } else if (PreferenceManager().getTimeTableGroup(mContext)
                                .equals("2")
                        ) {
                            println("working if datamodel")
                            for (i in timeTableStudArray.indices) {
                                println("working if datamodel")
                                System.out.println(
                                    "working if datamodel type" + timeTableStudArray.get(
                                        i
                                    ).type
                                )
                                if (timeTableStudArray.get(i).type
                                        .equals("Secondary")
                                ) {
                                    println("working if datamodel")
                                    isAvailable = true
                                }
                            }
                        } else if (PreferenceManager().getTimeTableGroup(mContext)
                                .equals("3")
                        ) {
                            println("working if datamodel")
                            for (i in timeTableStudArray.indices) {
                                println("working if datamodel")
                                if (timeTableStudArray.get(i).type
                                        .equals("Secondary") || timeTableStudArray.get(
                                        i
                                    ).type.equals("Primary")
                                ) {
                                    println("working if datamodel")
                                    isAvailable = true
                                }
                            }
                        }
                        if (isAvailable) {
                            PreferenceManager().setTimeTableAvailable(mContext, "1")
                        } else {
                            PreferenceManager().setTimeTableAvailable(mContext, "0")
                        }


                    }
                }

            }
        })
    }


    private fun getCountryList() {
        mNationlityListArrrayList = ArrayList<NationalityModel>()
        val call: Call<CountriesModel> = ApiClient.getClient.countries(
            PreferenceManager().getaccesstoken(mContext).toString(),
            PreferenceManager().getUserId(mContext).toString()
        )

        call.enqueue(object : Callback<CountriesModel> {
            override fun onFailure(call: Call<CountriesModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<CountriesModel>,
                response: Response<CountriesModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")) {
                    if (responsedata!!.response.statuscode.equals("303")) {


                        if (responsedata!!.response.responseArray.size > 0) {
                            mNationlityListArrrayList.addAll(responsedata!!.response.responseArray)

                            AppController().mNationalityArrayList = mNationlityListArrrayList
                        } else {

                        }


                    }
                }

            }
        })

    }

    private fun showDataCollectionUserDetail() {
        mOwnContactArrayList = ArrayList()
        mGlobalListDataArrayList = ArrayList()
        ContactTypeArray = ArrayList()
        mGlobalListSirnameArrayList = ArrayList()
        ContactListArray = ArrayList()
        InsuranceHealthListArray = ArrayList()
        PassportDetailListArray = ArrayList()
        KinArray = ArrayList()
        val call: Call<DataCollectionDetailModel> = ApiClient.getClient.datacollectiondetails(
            PreferenceManager().getaccesstoken(mContext).toString(),
            PreferenceManager().getUserId(mContext).toString()
        )

        call.enqueue(object : Callback<DataCollectionDetailModel> {
            override fun onFailure(call: Call<DataCollectionDetailModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<DataCollectionDetailModel>,
                response: Response<DataCollectionDetailModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")) {
                    if (responsedata!!.response.statuscode.equals("303")) {
                        PreferenceManager().setDisplayMessage(
                            mContext,
                            responsedata!!.response.data.display_message
                        )
                        if (responsedata!!.response.data.own_details.size>0){
                        for (i in responsedata!!.response.data.own_details.indices ){
                             mOwnContactArrayList.add(responsedata!!.response.data.own_details[i])
                            mOwnContactArrayList[i].isConfirmed=false
                             mOwnContactArrayList[i].isUpdated=false
                        }
                            if (PreferenceManager().getOwnDetailArrayList("OwnContact", mContext) == null ||
                                PreferenceManager().getOwnDetailArrayList(
                                    "OwnContact", mContext)!!.size == 0
                            ) {
                                PreferenceManager().setIsAlreadyEnteredOwnContact(mContext, true)
                                PreferenceManager().saveOwnDetailArrayList(
                                    mOwnContactArrayList,
                                    "OwnContact",
                                    mContext
                                )
                            } else if (!PreferenceManager().getIsAlreadyEnteredOwnContact(mContext)) {
                                println("Works DATA COLLECTIOM else works")
                                PreferenceManager().setIsAlreadyEnteredOwnContact(mContext, true)
                                PreferenceManager().saveOwnDetailArrayList(
                                    mOwnContactArrayList,
                                    "OwnContact",
                                    mContext
                                )
                            } else {

                            }
                        }
                        if (responsedata!!.response.data.kin_details.size > 0) {
                            for (j in responsedata!!.response.data.kin_details.indices){

                                KinArray.add(responsedata!!.response.data.kin_details[j])
                                KinArray[j].NewData="NO"
                                KinArray[j].isConfirmed=false
                                KinArray[j].isNewData=false

                            }
                            for (j in KinArray.indices) {
                                if (KinArray[j].relationship.equals("Next of kin")) {
                                    isKinFound = true

                                } else {
                                    if (KinArray[j].relationship
                                            .equals("Local Emergency Contact") || KinArray[j].relationship
                                            .equals("Emergency Contact")
                                    ) {
                                        isLocalFound = true

                                    }
                                }
                            }
                            if (!isLocalFound) {
                                val model = KinModel("Mobatia_LOCAL","","Mobatia_LOCAL","","","",
                                    "Emergency Contact","","","","","","","",
                                    "","","","","","",false,false,"YES",
                                    true,false)
                                KinArray.add(model)
                            }
                        } else {
                            if (!isLocalFound) {
                                val model = KinModel("Mobatia_LOCAL","","Mobatia_LOCAL","","","",
                                    "Emergency Contact","","","","","","","",
                                    "","","","","","",false,false,"YES",
                                    true,false)

                                KinArray.add(model)
                            }
                        }
                      /*  if (!PreferenceManager().getIsAlreadyEnteredKin(
                                mContext
                            ) || PreferenceManager().getKinDetailsArrayList(mContext).size == 0
                        ) {
                            PreferenceManager().setIsAlreadyEnteredKin(mContext, true)
                            PreferenceManager().saveKinDetailsArrayList(KinArray, mContext)
                            PreferenceManager().saveKinDetailsArrayListShow(KinArray, mContext)
                        }*/

                        val globalArray: ArrayList<GlobalListDataModel> = responsedata!!.response.data.GlobalList
                        if (globalArray.size > 0) {
                            for (i in 0 until globalArray.size) {
                                val globalObject: GlobalListDataModel = globalArray[i]
                                if (globalObject.type
                                        .equals("Title")
                                ) {
                                    val mModel = GlobalListDataModel()
                                    mModel.type=globalObject.type
                                    val globalSirArray = globalObject.mGlobalSirnameArray
                                    if (globalSirArray!!.size > 0) {
                                        for (j in 0 until globalSirArray.size) {
                                            val globalTypeObject = globalSirArray[j]
                                            val model = GlobalListSirname()
                                            model.name=globalTypeObject.name
                                            mGlobalListSirnameArrayList.add(model)
                                        }
                                    }
                                    mModel.mGlobalSirnameArray=mGlobalListSirnameArrayList
                                    mGlobalListDataArrayList.add(mModel)
                                }
                                if (globalObject.type
                                        .equals("ContactType")
                                ) {

                                    val model = ContactTypeModel()
                                    model.type=globalObject.type
                                    val ContactArray = globalObject.mGlobalSirnameArray
                                    if (ContactArray!!.size > 0) {
                                        for (j in 0 until ContactArray.size) {
                                            val job = ContactArray[j]
                                            val Gmodel = GlobalListSirname()
                                            Gmodel.name=job.name
                                            ContactListArray.add(Gmodel)
                                        }
                                    }
                                    model.mGlobalSirnameArray=ContactListArray
                                    ContactTypeArray.add(model)
                                }
                            }
                            PreferenceManager().saveGlobalListArrayList(mContext,
                                mGlobalListDataArrayList

                            )
                            PreferenceManager().saveContactTypeArrayList(mContext,ContactTypeArray)
                        }

                        if (responsedata!!.response.data.health_and_insurence.size>0){
                            for (n in responsedata!!.response.data.health_and_insurence.indices){
                                InsuranceHealthListArray.add(responsedata!!.response.data.health_and_insurence[n])
                                responsedata!!.response.data.health_and_insurence[n].status="5"
                                responsedata!!.response.data.health_and_insurence[n].declaration="0"

                            }
                            if (!PreferenceManager().getIsAlreadyEnteredInsurance(
                                    mContext
                                ) || PreferenceManager().getInsuranceDetailArrayList(mContext)
                                    .size == 0
                            ) {
                                PreferenceManager().setIsAlreadyEnteredInsurance(mContext, true)
                                PreferenceManager().saveInsuranceDetailArrayList(
                                    InsuranceHealthListArray,
                                    mContext
                                )
                            }
                        }
                        val passportArray: ArrayList<PassportDetailModel> = responsedata!!.response.data.passport_details
                        if (responsedata!!.response.data.passport_details.size>0){

                            for ( p in responsedata!!.response.data.passport_details.indices){
                                val passportObject: PassportDetailModel = passportArray[p]
                                val pModel = PassportDetailModel()
                                pModel.id=passportObject.id
                                pModel.student_id=passportObject.student_id
                                pModel.student_name=passportObject.student_name
                                pModel.passport_number=passportObject.passport_number
                                pModel.nationality=passportObject.nationality
                                pModel.passport_image=passportObject.passport_image
                                pModel.passport_expired=passportObject.passport_expired
                                pModel.expiry_date=passportObject.expiry_date
                                pModel.date_of_issue=passportObject.date_of_issue
                                pModel.visa=passportObject.visa
                                pModel.visa_permit_no=passportObject.visa_permit_no
                                pModel.visa_expired=passportObject.visa_expired
                                pModel.not_have_a_valid_passport=passportObject.not_have_a_valid_passport
                                pModel.visa_image=passportObject.visa_image
                                pModel.visa_permit_expiry_date=passportObject.visa_permit_expiry_date
                                pModel.photo_no_consent=passportObject.photo_no_consent

                                if (passportObject.id.equals("")) {
                                    pModel.status="0"
                                    pModel.request="1"
                                } else {
                                    pModel.status="5"
                                    pModel.request="0"
                                }

                                pModel.created_at=passportObject.created_at
                                pModel.updated_at=passportObject.updated_at

                                if (passportObject.expiry_date
                                        .equals("")
                                ) {
                                    pModel.isPassportDateChanged=true
                                } else {
                                    pModel.isPassportDateChanged=false
                                }

                                pModel.isVisaDateChanged=false
                                PassportDetailListArray.add(pModel)
                            }
                            if (!PreferenceManager().getIsAlreadyEnteredPassport(
                                    mContext
                                ) || PreferenceManager().getPassportDetailArrayList(mContext)
                                    .size == 0
                            ) {
                                PreferenceManager().setIsAlreadyEnteredPassport(mContext, true)
                                PreferenceManager().savePassportDetailArrayList(
                                    PassportDetailListArray,
                                    mContext
                                )
                            }

                        }
                        if (PreferenceManager().getIsVisible(mContext).equals("0")) {
                            println("QWE: inside VISIBLE")
                            if (PreferenceManager().getIsApplicant(mContext).equals("0")) {
                                println("QWE: inside Applicant")
                                if (PreferenceManager().getReportMailMerge(mContext)
                                        .equals("1")
                                ) {
                                    println("QWE: inside Mail")
                                    if (PreferenceManager().getDataCollection(mContext)
                                            .equals("1")
                                    ) {
                                        println("QWE: inside Collection")
                                        val i = Intent(
                                            activity,
                                            DataCollectionHome::class.java
                                        )
                                        startActivity(i)
                                    }
                                }
                            } else {
                                println("QWE: outside Applicant")
                            }
                        }

                    }
                }

            }
        })

    }

    private fun getStudentsListAPI() {

        val call: Call<StudentListResponseModel> = ApiClient.getClient.student_list(
            PreferenceManager().getaccesstoken(mContext).toString(), PreferenceManager().getUserId(mContext).toString()
        )

        call.enqueue(object : Callback<StudentListResponseModel> {
            override fun onFailure(call: Call<StudentListResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<StudentListResponseModel>,
                response: Response<StudentListResponseModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")) {
                    if (responsedata!!.response.statuscode.equals("303")) {

                        val data: ArrayList<StudentListModel> = responsedata!!.response.data
                        if (data.size > 0) {
                            for (i in 0 until data.size) {
                                val dataObject = data[i]
                                if (dataObject.alumi.equals("0")) {
                                    val studentModel = StudentModelNew()
                                    studentModel.mId=dataObject.id
                                    studentModel.mName=dataObject.name
                                    studentModel.mClass=dataObject.mClass
                                    studentModel.mSection=dataObject.section
                                    studentModel.mHouse=dataObject.house
                                    studentModel.mPhoto=dataObject.photo
                                    studentModel.isConfirmed=false
                                    studentModel.progressReport=dataObject.progressreport
                                    studentModel.alumini=dataObject.alumi

//                                        AppController.mStudentDataArrayList.add(addStudentDetails(dataObject));
                                    studentsModelArrayList.add(studentModel)
                                }
                            }
                            if (!PreferenceManager().getIsAlreadyEnteredStudentList(
                                    mContext
                                ) || PreferenceManager().getInsuranceStudentList(mContext)
                                    .size == 0
                            ) {
                                println("student list add works")
                                PreferenceManager().setIsAlreadyEnteredStudentList(mContext, true)
                                AppController().mStudentDataArrayList = studentsModelArrayList
                                PreferenceManager().saveInsuranceStudentList(
                                    studentsModelArrayList,
                                    mContext
                                )
                            } else {
                                AppController().mStudentDataArrayList =
                                    PreferenceManager().getInsuranceStudentList(mContext)
                            }
                        } else {
//                                mRecycleView.setVisibility(View.GONE);
                            Toast.makeText(activity, "No data found", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            }
        })

    }
    override fun onResume() {
        super.onResume()
        if (AppUtils().isNetworkConnected(mContext)) {
            //   showSettingUserDetail(URL_GET_SETTINGS_USER_DETAIL);
        } /*else {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

        }*/
        if (!PreferenceManager().getUserId(mContext).equals("")) {
            /* AppController().getInstance().getGoogleAnalyticsTracker()
                 .set("&uid", PreferenceManager().getUserId(mContext))
             AppController().getInstance().getGoogleAnalyticsTracker()
                 .set("&cid", PreferenceManager.getUserId(mContext))
             AppController().getInstance().trackScreenView(
                 "Home Screen Fragment. " + "(" + PreferenceManager().getUserEmail(mContext) + ")" + " " + "(" + Calendar.getInstance().time + ")"
             )*/
        }
    }

    override fun onClick(v: View?) {
        if (PreferenceManager().getIsVisible(mContext).equals("0")) {
            if (v == messageBtn) {
                if (PreferenceManager().getReportMailMerge(mContext).equals("1")) {
                    INTENT_TAB_ID = PreferenceManager().getButtonOneTabId(mContext)
                    checkIntent(INTENT_TAB_ID.toString())
                } else {
                    INTENT_TAB_ID = PreferenceManager().getButtonOneTabId(mContext)
                    checkIntent(INTENT_TAB_ID.toString())
                }
            } else if (v == calendarBtn) {
                if (PreferenceManager().getReportMailMerge(mContext).equals("1")) {
                    INTENT_TAB_ID = PreferenceManager().getButtonFourTabId(mContext)
                    checkIntent(INTENT_TAB_ID.toString())
                } else {
                    INTENT_TAB_ID = PreferenceManager().getButtonFourTabId(mContext)
                    checkIntent(INTENT_TAB_ID.toString())
                }
            } else if (v == newsBtn) {
                if (PreferenceManager().getReportMailMerge(mContext).equals("1")) {
                    INTENT_TAB_ID = PreferenceManager().getButtonTwoGuestTabId(mContext)
                    checkIntent(INTENT_TAB_ID.toString())
                } else {
                    INTENT_TAB_ID = PreferenceManager().getButtonTwoGuestTabId(mContext)
                    if (INTENT_TAB_ID.equals(
                            bsklTabConstants.TAB_NEWS
                        ) || INTENT_TAB_ID.equals(
                            bsklTabConstants.TAB_SOCIAL_MEDIA
                        ) || INTENT_TAB_ID.equals(bsklTabConstants.TAB_CONTACT_US)
                    ) {
                        checkIntent(INTENT_TAB_ID.toString())
                    } else {
                        if (AppUtils().isNetworkConnected(mContext)) {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                mContext.getString(R.string.alert_heading),
                                mContext.getString(R.string.not_available_limitted_access),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Network Error",
                                mContext.getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }
                    }
                }
            } else if (v == socialBtn) {
                if (PreferenceManager().getReportMailMerge(mContext).equals("1")) {
                    INTENT_TAB_ID = PreferenceManager().getButtonsixTabId(mContext)
                    checkIntent(INTENT_TAB_ID.toString())
                } else {
                    if (AppUtils().isNetworkConnected(mContext)) {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            mContext.getString(R.string.alert_heading),
                            mContext.getString(R.string.not_available_limitted_access),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            "Network Error",
                            mContext.getString(R.string.no_internet),
                            R.drawable.nonetworkicon,
                            R.drawable.roundred
                        )
                    }
                }
            }
        } else {
            if (PreferenceManager().getIsApplicant(mContext).equals("1")) {
                if (v == messageBtn) {
                    if (AppUtils().isNetworkConnected(mContext)) {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            mContext.getString(R.string.alert_heading),
                            mContext.getString(R.string.not_available),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            "Network Error",
                            mContext.getString(R.string.no_internet),
                            R.drawable.nonetworkicon,
                            R.drawable.roundred
                        )
                    }
                } else if (v == calendarBtn) {
                    if (AppUtils().isNetworkConnected(mContext)) {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            mContext.getString(R.string.alert_heading),
                            mContext.getString(R.string.not_available),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            "Network Error",
                            mContext.getString(R.string.no_internet),
                            R.drawable.nonetworkicon,
                            R.drawable.roundred
                        )
                    }
                } else if (v == socialBtn) {
                    if (AppUtils().isNetworkConnected(mContext)) {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            mContext.getString(R.string.alert_heading),
                            mContext.getString(R.string.not_available),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            "Network Error",
                            mContext.getString(R.string.no_internet),
                            R.drawable.nonetworkicon,
                            R.drawable.roundred
                        )
                    }
                } else if (v == newsBtn) {
                    if (AppUtils().isNetworkConnected(mContext)) {
                        INTENT_TAB_ID = PreferenceManager().getButtonTwoGuestTabId(mContext)
                        if (INTENT_TAB_ID.equals(
                                bsklTabConstants.TAB_REPORT
                            ) || INTENT_TAB_ID.equals(bsklTabConstants.TAB_CONTACT_US)
                        ) {
                            checkIntent(INTENT_TAB_ID.toString())
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                mContext.getString(R.string.alert_heading),
                                mContext.getString(R.string.not_available),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    } else {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            "Network Error",
                            mContext.getString(R.string.no_internet),
                            R.drawable.nonetworkicon,
                            R.drawable.roundred
                        )
                    }
                }
            } else {
                if (PreferenceManager().getReportMailMerge(mContext).equals("1")) {
                    if (v == messageBtn) {
                        if (AppUtils().isNetworkConnected(mContext)) {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                mContext.getString(R.string.alert_heading),
                                mContext.getString(R.string.not_available),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Network Error",
                                mContext.getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }
                    } else if (v == calendarBtn) {
                        if (AppUtils().isNetworkConnected(mContext)) {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                mContext.getString(R.string.alert_heading),
                                mContext.getString(R.string.not_available),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Network Error",
                                mContext.getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }
                    } else if (v == socialBtn) {
                        if (AppUtils().isNetworkConnected(mContext)) {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                mContext.getString(R.string.alert_heading),
                                mContext.getString(R.string.not_available),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Network Error",
                                mContext.getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }
                    } else if (v == newsBtn) {
                        if (AppUtils().isNetworkConnected(mContext)) {
                            INTENT_TAB_ID = PreferenceManager().getButtonTwoGuestTabId(mContext)
                            if (INTENT_TAB_ID.equals(
                                    bsklTabConstants.TAB_REPORT
                                ) || INTENT_TAB_ID.equals(bsklTabConstants.TAB_CONTACT_US)
                            ) {
                                checkIntent(INTENT_TAB_ID.toString())
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    mContext.getString(R.string.alert_heading),
                                    mContext.getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            }
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Network Error",
                                mContext.getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }
                    }
                } else {
                    if (v == messageBtn) {
                        if (AppUtils().isNetworkConnected(mContext)) {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                mContext.getString(R.string.alert_heading),
                                mContext.getString(R.string.not_available),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Network Error",
                                mContext.getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }
                    } else if (v == calendarBtn) {
                        if (AppUtils().isNetworkConnected(mContext)) {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                mContext.getString(R.string.alert_heading),
                                mContext.getString(R.string.not_available),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Network Error",
                                mContext.getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }
                    } else if (v == socialBtn) {
                        if (AppUtils().isNetworkConnected(mContext)) {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                mContext.getString(R.string.alert_heading),
                                mContext.getString(R.string.not_available),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Network Error",
                                mContext.getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }
                    } else if (v == newsBtn) {
                        if (AppUtils().isNetworkConnected(mContext)) {
                            INTENT_TAB_ID = PreferenceManager().getButtonTwoGuestTabId(mContext)
                            if (INTENT_TAB_ID.equals(bsklTabConstants.TAB_CONTACT_US)) {
                                checkIntent(INTENT_TAB_ID.toString())
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    mContext.getString(R.string.alert_heading),
                                    mContext.getString(R.string.not_available),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            }
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Network Error",
                                mContext.getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }
                    }
                }
            }
        }

    }

    private fun checkIntent(tabId: String) {
        tabiDToProceed = tabId
        // System.out.println("tabId::" + tabId);
        var mFragment: Fragment? = null
        if (tabId.equals(bsklTabConstants.TAB_MESSAGES)) {
            mFragment = NotificationFragmentPagination(bsklNameConstants.NOTIFICATIONS, bsklTabConstants.TAB_MESSAGES)
            fragmentIntent(mFragment)
        } else if (tabId.equals(bsklTabConstants.TAB_CALENDAR)) {
            mFragment = CalendarFragment(bsklNameConstants.CALENDAR, bsklTabConstants.TAB_CALENDAR)
            fragmentIntent(mFragment)
        } else if (tabId.equals(bsklTabConstants.TAB_SOCIAL_MEDIA)) {
            mFragment = SocialMediaFragment(bsklNameConstants.SOCIAL_MEDIA, bsklTabConstants.TAB_SOCIAL_MEDIA)
            fragmentIntent(mFragment)

        } else if (tabId.equals(bsklTabConstants.TAB_NEWS)) {
            mFragment = NewsFragment(bsklNameConstants.BSKL_NEWS, bsklTabConstants.TAB_NEWS)
            fragmentIntent(mFragment)
        } else if (tabId.equals(bsklTabConstants.TAB_REPORT)) {
            mFragment = ReportFragment(bsklNameConstants.PROGRESS_REPORT, bsklTabConstants.TAB_REPORT)
            fragmentIntent(mFragment)
        } else if (tabId.equals(bsklTabConstants.TAB_ABSENCE)) {
            if (PreferenceManager().getAbsence(mContext).equals("1")) {
                // System.out.println("ddddd" + PreferenceManager.getAbsence(mContext));
                mFragment = AbsenceFragment(bsklNameConstants.ABSENCE, bsklTabConstants.TAB_ABSENCE)
                fragmentIntent(mFragment)
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    mContext.getString(R.string.alert_heading),
                    mContext.getString(R.string.not_available_feature),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        } else if (tabId.equals(bsklTabConstants.TAB_SAFE_GUARDING)) {
            if (PreferenceManager().getSafeGuarding(mContext).equals("1")) {
                /*  mFragment = SafeGuardingFragment(SAFE_GUARDING, TAB_SAFE_GUARDING)
                  fragmentIntent(mFragment)*/
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    mContext.getString(R.string.alert_heading),
                    mContext.getString(R.string.not_available_feature),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        } else if (tabId.equals(bsklTabConstants.TAB_ATTENDANCE)) {
            if (PreferenceManager().getAttendance(mContext).equals("1")) {
                /* mFragment = AttendenceFragment(ATTENDANCE, TAB_ATTENDANCE)
                 fragmentIntent(mFragment)*/
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    mContext.getString(R.string.alert_heading),
                    mContext.getString(R.string.not_available_feature),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        } else if (tabId.equals(bsklTabConstants.TAB_TIME_TABLE)) {
            if (PreferenceManager().getTimeTable(mContext).equals("1")) {
                if (PreferenceManager().getTimeTableAvailable(mContext).equals("1")) {
                    /* mFragment = TimeTableFragmentN(TIMETABLE, TAB_TIME_TABLE)
                     fragmentIntent(mFragment)*/
                } else {
                    AppUtils().showDialogAlertDismiss(
                        mContext as Activity,
                        mContext.getString(R.string.alert_heading),
                        mContext.getString(R.string.not_available_feature),
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                }
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    mContext.getString(R.string.alert_heading),
                    mContext.getString(R.string.not_available_feature),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        } else if (tabId.equals(bsklTabConstants.TAB_CONTACT_US)) {
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
                    bsklNameConstants.CONTACT_US,
                    bsklTabConstants.TAB_CONTACT_US
                )
                fragmentIntent(mFragment)
            }
        } /*else if (tabId.equalsIgnoreCase(TAB_SCHOOLBUDDY)) {
            if (AppUtils.isNetworkConnected(mContext)) {
                if (isPackageExisted("com.subsbuddy.schoolsbuddyasia")) {
                    launchApp("com.subsbuddy.schoolsbuddyasia");

                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.subsbuddy.schoolsbuddyasia")));
                }

            } else {
                AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

            }
            fragmentIntent(mFragment);
        }*/ else {
            if (AppUtils().isNetworkConnected(mContext)) {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    mContext.getString(R.string.alert_heading),
                    mContext.getString(R.string.coming_soon),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    "Network Error",
                    mContext.getString(R.string.no_internet),
                    R.drawable.nonetworkicon,
                    R.drawable.roundred
                )
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                homeActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CALL_PHONE
                ),
                123
            )
        }
    }


    private fun fragmentIntent(mFragment: Fragment) {
        if (mFragment != null) {


            // System.out.println("title:" + AppController.mTitles);
            val fragmentManager = homeActivity.getSupportFragmentManager()
            fragmentManager.beginTransaction()
                .add(R.id.frame_container, mFragment, AppController().mTitles)
                .addToBackStack(AppController().mTitles).commitAllowingStateLoss() //commit

        }

    }
}