package com.example.bskl_kotlin.fragment.settings

import ApiClient
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.datacollection_p2.model.InsuranceDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.KinModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.OwnContactModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.PassportDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.StudentModelNew
import com.example.bskl_kotlin.activity.home.model.AppFeatureModel
import com.example.bskl_kotlin.activity.home.model.ResponseArrayUserModel
import com.example.bskl_kotlin.activity.home.model.UserDetailsApiModel
import com.example.bskl_kotlin.activity.home.model.UserDetailsModel
import com.example.bskl_kotlin.activity.login.LoginActivity
import com.example.bskl_kotlin.activity.settings.UserDetailActivity
import com.example.bskl_kotlin.activity.tutorial.TutorialActivity
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.constants.OnItemClickListener
import com.example.bskl_kotlin.constants.addOnItemClickListener
import com.example.bskl_kotlin.fragment.attendance.PreferenceManagerr
import com.example.bskl_kotlin.fragment.settings.adapter.CustomSettingsAdapter
import com.example.bskl_kotlin.fragment.settings.adapter.TriggerRecyclerAdapter
import com.example.bskl_kotlin.fragment.settings.model.ChangePasswordApiModel
import com.example.bskl_kotlin.fragment.settings.model.ChangePasswordModel
import com.example.bskl_kotlin.fragment.settings.model.LogoutApiModel
import com.example.bskl_kotlin.fragment.settings.model.TriggerDataModel
import com.example.bskl_kotlin.fragment.settings.model.TriggerUserApiModel
import com.example.bskl_kotlin.fragment.settings.model.TriggerUserModel
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.AppUtils
import com.example.bskl_kotlin.manager.CommonClass
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment :Fragment(),AdapterView.OnItemClickListener {
    var tokenM = " "
    private val mRootView: View? = null
    lateinit var mContext: Context
     var mSettingsList: ListView? = null
    private val mTitle: String? = null
    private val mTabId: String? = null
    lateinit var relMain: RelativeLayout
    private val mBannerImage: ImageView? = null
    lateinit var text_currentpswd: EditText
    lateinit var newpassword:EditText
    lateinit var confirmpassword:EditText
    var isRegUser = false
    var dialog: Dialog? = null
    lateinit var versionCode: TextView
    var valueTrigger = ""
    lateinit var mSettingsListArrayDataCollection: ArrayList<String>
    lateinit var mSettingsListArray: ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_settings_list, container, false)
    }
    @SuppressLint("UseRequireInsteadOfGet", "Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        mSettingsListArrayDataCollection= ArrayList()
        mSettingsListArray=ArrayList()
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        val action_bar_forward =
            actionBar!!.customView.findViewById<ImageView>(R.id.action_bar_forward)
        mSettingsList = requireView().findViewById(R.id.mSettingsListView)
        headerTitle.text = "Settings"
        action_bar_forward.visibility = View.INVISIBLE
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        dialog = Dialog(mContext, R.style.NewDialog)

        initialiseUI()
        if (AppUtils().isNetworkConnected(mContext)) {
            showSettingUserDetail(mContext)
        } else {
            AppUtils().showDialogAlertDismiss(
                mContext as Activity?,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }

    }



    private fun initialiseUI() {

        versionCode = requireView().findViewById(R.id.versionText)
        relMain = requireView().findViewById(R.id.relMain)
        mSettingsListArrayDataCollection.add("Change App Settings")
        mSettingsListArrayDataCollection.add("Calendar Notifications")
        mSettingsListArrayDataCollection.add("Email Notifications")
        mSettingsListArrayDataCollection.add("Tutorial")
        mSettingsListArrayDataCollection.add("Change Password")
        mSettingsListArrayDataCollection.add("Update Account Details")
        mSettingsListArrayDataCollection.add("Account Details")
        mSettingsListArrayDataCollection.add("Logout")
        mSettingsListArray.add("Change App Settings")
        mSettingsListArray.add("Calendar Notifications")
        mSettingsListArray.add("Email Notifications")
        mSettingsListArray.add("Tutorial")
        mSettingsListArray.add("Change Password")
        mSettingsListArray.add("Account Details")
        mSettingsListArray.add("Logout")

        try {
            val pInfo: PackageInfo = mContext.getPackageManager()
                .getPackageInfo(mContext.getPackageName(), 0)
            val version = pInfo.versionName
            versionCode.setText("V $version")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


        if (!PreferenceManager().getUserId(mContext).equals("")) {
            if (PreferenceManager().getIsVisible(mContext).equals("0")) {
                //user is not alumini
                if (PreferenceManager().getIsApplicant(mContext)
                        .equals("0")
                ) {
                    // user is not applicant
                    if (PreferenceManager().getReportMailMerge(mContext)
                            .equals("1")
                    ) {
                        // current user with report mail merge enable
                        if (!PreferenceManager().getDataCollection(mContext)
                                .equals("1")
                        ) {
                            // data collection trigger available
                            isRegUser = true
                            mSettingsList!!.setAdapter(
                                CustomSettingsAdapter(
                                    mContext,
                                    mSettingsListArrayDataCollection,isRegUser
                                )
                            )
                        } else {
                            // data collection trigger not available
                            isRegUser = true
                            mSettingsList!!.setAdapter(
                                CustomSettingsAdapter(
                                    mContext,
                                    mSettingsListArray,isRegUser
                                )
                            )
                        }
                    } else {
                        // current user with report mail merge enable
                        isRegUser = true
                        mSettingsList!!.setAdapter(
                            CustomSettingsAdapter(
                                mContext,
                                mSettingsListArray,isRegUser
                            )
                        )
                    }
                } else {
                    // user is applicant
                    isRegUser = true
                    mSettingsList!!.setAdapter(
                        CustomSettingsAdapter(
                            mContext,
                            mSettingsListArray,isRegUser
                        )
                    )
                }
            } else {
                // user is alumini
                isRegUser = true
                mSettingsList!!.setAdapter(
                    CustomSettingsAdapter(
                        mContext,
                        mSettingsListArray,isRegUser
                    )
                )
            }
        }
        mSettingsList!!.setOnItemClickListener(this)

    }
     fun showSettingUserDetail(mContext: Context) {
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
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){

                        val respObj: ResponseArrayUserModel = responsedata!!.response.responseArray
                        // PreferenceManager().setUserId(mContext, respObj.optString(JTAG_USER_ID));
                        // PreferenceManager().setUserId(mContext, respObj.optString(JTAG_USER_ID));
                        val userId = respObj.userid
                        PreferenceManager().setPhoneNo(
                            mContext,
                            respObj.mobileno
                        )
                        PreferenceManager().setFullName(
                            mContext,
                            respObj.name
                        )
                        PreferenceManager().setCalenderPush(
                            mContext,
                            respObj.calenderpush
                        )
                        PreferenceManager().setEmailPush(
                            mContext,
                            respObj.emailpush
                        )
                        PreferenceManager().setMessageBadge(
                            mContext,
                            respObj.messagebadge
                        )
                        PreferenceManager().setCalenderBadge(
                            mContext,
                            respObj.calenderbadge
                        )
                        val respObjAppFeature: AppFeatureModel = responsedata!!.response.app_feature
                        PreferenceManager().setTimeTable(
                            mContext,
                            respObjAppFeature.timetable
                        )
                        PreferenceManager().setSafeGuarding(
                            mContext,
                            respObjAppFeature.safeguarding
                        )
                        PreferenceManager().setAttendance(
                            mContext,
                            respObjAppFeature.attendance
                        )
                        if (!PreferenceManager().getUserId(mContext)
                                .equals("")
                        ) {
                            if (PreferenceManager().getIsVisible(mContext)
                                    .equals("0")
                            ) {
                                //user is not alumini
                                if (PreferenceManager().getIsApplicant(mContext)
                                        .equals("0")
                                ) {
                                    // user is not applicant
                                    if (PreferenceManager().getReportMailMerge(mContext)
                                            .equals("1")
                                    ) {
                                        // current user with report mail merge enable
                                        if (!PreferenceManager().getDataCollection(mContext)
                                                .equals("1")
                                        ) {
                                            // data collection trigger available
                                            isRegUser = true
                                            mSettingsList!!.setAdapter(
                                                CustomSettingsAdapter(
                                                    mContext,
                                                    mSettingsListArrayDataCollection,isRegUser
                                                )
                                            )
                                        } else {
                                            // data collection trigger not available
                                            isRegUser = true
                                            mSettingsList!!.setAdapter(
                                                CustomSettingsAdapter(
                                                    mContext,
                                                    mSettingsListArray,isRegUser
                                                )
                                            )
                                        }
                                    } else {
                                        // current user with report mail merge enable
                                        isRegUser = true
                                        mSettingsList!!.setAdapter(
                                            CustomSettingsAdapter(
                                                mContext,
                                                mSettingsListArray,isRegUser
                                            )
                                        )
                                    }
                                } else {
                                    // user is applicant
                                    isRegUser = true
                                    mSettingsList!!.setAdapter(
                                        CustomSettingsAdapter(
                                            mContext,
                                            mSettingsListArray,isRegUser
                                        )
                                    )
                                }
                            } else {
                                // user is alumini
                                isRegUser = true
                                mSettingsList!!.setAdapter(
                                    CustomSettingsAdapter(
                                        mContext,
                                        mSettingsListArray,isRegUser
                                    )
                                )
                            }
                        }
                        if (!PreferenceManager().getUserId(mContext)
                                .equals("")
                        ) {
                            if (PreferenceManager().getIsVisible(mContext)
                                    .equals("0")
                            ) {
                                //user is not alumini
                                if (PreferenceManager().getIsApplicant(mContext)
                                        .equals("0")
                                ) {
                                    // user is not applicant
                                    if (PreferenceManager().getReportMailMerge(mContext)
                                            .equals("1")
                                    ) {
                                        // current user with report mail merge enable
                                        if (!PreferenceManager().getDataCollection(mContext)
                                                .equals("1")
                                        ) {
                                            // data collection trigger available
                                            isRegUser = true
                                            mSettingsList!!.setAdapter(
                                                CustomSettingsAdapter(
                                                    mContext,
                                                    mSettingsListArrayDataCollection,isRegUser
                                                )
                                            )
                                        } else {
                                            // data collection trigger not available
                                            isRegUser = true
                                            mSettingsList!!.setAdapter(
                                                CustomSettingsAdapter(
                                                    mContext,
                                                    mSettingsListArray,isRegUser
                                                )
                                            )
                                        }
                                    } else {
                                        // current user with report mail merge enable
                                        isRegUser = true
                                        mSettingsList!!.setAdapter(
                                            CustomSettingsAdapter(
                                                mContext,
                                                mSettingsListArray,isRegUser
                                            )
                                        )
                                    }
                                } else {
                                    // user is applicant
                                    isRegUser = true
                                    mSettingsList!!.setAdapter(
                                        CustomSettingsAdapter(
                                            mContext,
                                            mSettingsListArray,isRegUser
                                        )
                                    )
                                }
                            } else {
                                // user is alumini
                                isRegUser = true
                                mSettingsList!!.setAdapter(
                                    CustomSettingsAdapter(
                                        mContext,
                                        mSettingsListArray,isRegUser
                                    )
                                )
                            }
                        }
                        if (!PreferenceManager().getUserId(mContext)
                                .equals("")
                        ) {
                            if (PreferenceManager().getIsVisible(mContext)
                                    .equals("0")
                            ) {
                                //user is not alumini
                                if (PreferenceManager().getIsApplicant(mContext)
                                        .equals("0")
                                ) {
                                    // user is not applicant
                                    if (PreferenceManager().getReportMailMerge(mContext)
                                            .equals("1")
                                    ) {
                                        // current user with report mail merge enable
                                        if (!PreferenceManager().getDataCollection(mContext)
                                                .equals("1")
                                        ) {
                                            // data collection trigger available
                                            isRegUser = true
                                            mSettingsList!!.setAdapter(
                                                CustomSettingsAdapter(
                                                    mContext,
                                                    mSettingsListArrayDataCollection,isRegUser
                                                )
                                            )
                                        } else {
                                            // data collection trigger not available
                                            isRegUser = true
                                            mSettingsList!!.setAdapter(
                                                CustomSettingsAdapter(
                                                    mContext,
                                                    mSettingsListArray,isRegUser
                                                )
                                            )
                                        }
                                    } else {
                                        // current user with report mail merge enable
                                        isRegUser = true
                                        mSettingsList!!.setAdapter(
                                            CustomSettingsAdapter(
                                                mContext,
                                                mSettingsListArray,isRegUser
                                            )
                                        )
                                    }
                                } else {
                                    // user is applicant
                                    isRegUser = true
                                    mSettingsList!!.setAdapter(
                                        CustomSettingsAdapter(
                                            mContext,
                                            mSettingsListArray,isRegUser
                                        )
                                    )
                                }
                            } else {
                                // user is alumini
                                isRegUser = true
                                mSettingsList!!.setAdapter(
                                    CustomSettingsAdapter(
                                        mContext,
                                        mSettingsListArray,isRegUser
                                    )
                                )
                            }
                        }


                    }
                }

            }
        })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (isRegUser) {
            if (PreferenceManager().getIsVisible(mContext).equals("0")) {
                // user is current user
                if (PreferenceManager().getIsApplicant(mContext)
                        .equals("0")
                ) {
                    // user is not applicant
                    if (PreferenceManager().getReportMailMerge(mContext)
                            .equals("1")
                    ) {
                        // Report mail merge enable === Access to all feature
                        if (!PreferenceManager().getDataCollection(mContext)
                                .equals("1")
                        ) {
                            // user have no data collection== view trigger data collection
                            when (position) {
                                0 -> {
                                    PreferenceManager().setGoToSettings(
                                        mContext,
                                        "1"
                                    )
                                    val intent = Intent()
                                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                    val uri = Uri.fromParts("package", mContext.packageName, null)
                                    intent.data = uri
                                    mContext.startActivity(intent)
                                }

                                1 -> {}
                                2 -> {}
                                3 -> {
                                    val mintent: Intent = Intent(
                                        mContext,
                                        TutorialActivity::class.java
                                    )
                                    mintent.putExtra("type", 0)
                                    mContext.startActivity(mintent)
                                }

                                4 -> if (AppUtils().isNetworkConnected(mContext)) {
                                    showChangePasswordAlert()
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity?,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }

                                5 -> if (AppUtils().isNetworkConnected(mContext)) {
                                    showDialogAlertTriggerDataCollection(
                                        mContext,
                                        "Confirm?",
                                        "Select one or more areas to update",
                                        R.drawable.questionmark_icon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity?,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }

                                6 -> {
                                    println("cases 111111")
                                    val mIntent: Intent = Intent(
                                        mContext,
                                        UserDetailActivity::class.java
                                    )
                                    mIntent.putExtra(
                                        "tab_type",
                                        mSettingsListArrayDataCollection.get(
                                            position
                                        ).toString()
                                    )
                                    mContext.startActivity(mIntent)
                                }

                                7 -> if (AppUtils().isNetworkConnected(mContext)) {
                                    showDialogAlertLogout(
                                        mContext,
                                        "Confirm?",
                                        "Do you want to Logout?",
                                        R.drawable.questionmark_icon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity?,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }

                                else -> {}
                            }
                        } else {
                            // user have  data collection== no view trigger data collection
                            when (position) {
                                0 -> {
                                    PreferenceManager().setGoToSettings(
                                        mContext,
                                        "1"
                                    )
                                    val intent = Intent()
                                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                    val uri = Uri.fromParts("package", mContext.packageName, null)
                                    intent.data = uri
                                    mContext.startActivity(intent)
                                }

                                1 -> {}
                                2 -> {}
                                3 -> {
                                    val mintent: Intent = Intent(
                                        mContext,
                                        TutorialActivity::class.java
                                    )
                                    mintent.putExtra("type", 0)
                                    mContext.startActivity(mintent)
                                }

                                4 -> if (AppUtils().isNetworkConnected(mContext)) {
                                    showChangePasswordAlert()
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity?,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }

                                5 -> {
                                    println("cases 2222222")
                                    val mIntent: Intent = Intent(
                                        mContext,
                                        UserDetailActivity::class.java
                                    )
                                    mIntent.putExtra(
                                        "tab_type",
                                        mSettingsListArray.get(position).toString()
                                    )
                                    mContext.startActivity(mIntent)
                                }

                                6 -> if (AppUtils().isNetworkConnected(mContext)) {
                                    showDialogAlertLogout(
                                        mContext,
                                        "Confirm?",
                                        "Do you want to Logout?",
                                        R.drawable.questionmark_icon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity?,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }

                                else -> {}
                            }
                        }
                    } else {
                        // Report mail merge disable === no data collection and no view detail contact
                        when (position) {
                            0 -> {
                                PreferenceManager().setGoToSettings(mContext, "1")
                                val intent = Intent()
                                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                val uri = Uri.fromParts("package", mContext.packageName, null)
                                intent.data = uri
                                mContext.startActivity(intent)
                            }

                            1 -> {}
                            2 -> {}
                            3 -> {
                                val mintent: Intent = Intent(
                                    mContext,
                                    TutorialActivity::class.java
                                )
                                mintent.putExtra("type", 0)
                                mContext.startActivity(mintent)
                            }

                            4 -> if (AppUtils().isNetworkConnected(mContext)) {
                                showChangePasswordAlert()
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity?,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                            5 -> {
                                println("cases 3333333")
                                if (AppUtils().isNetworkConnected(mContext)) {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity?,
                                        getString(R.string.alert_heading),
                                        getString(R.string.not_available_limitted_access),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    AppUtils().showDialogAlertDismiss(
                                        mContext as Activity?,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }
                            }

                            6 -> if (AppUtils().isNetworkConnected(mContext)) {
                                showDialogAlertLogout(
                                    mContext,
                                    "Confirm?",
                                    "Do you want to Logout?",
                                    R.drawable.questionmark_icon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity?,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }

                            else -> {}
                        }
                    }
                } else {
                    // user is applicant
                    when (position) {
                        0 -> {
                            PreferenceManager().setGoToSettings(mContext, "1")
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", mContext.packageName, null)
                            intent.data = uri
                            mContext.startActivity(intent)
                        }

                        1 -> {}
                        2 -> {}
                        3 -> {
                            val mintent: Intent = Intent(
                                mContext,
                                TutorialActivity::class.java
                            )
                            mintent.putExtra("type", 0)
                            mContext.startActivity(mintent)
                        }

                        4 -> if (AppUtils().isNetworkConnected(mContext)) {
                            showChangePasswordAlert()
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity?,
                                "Network Error",
                                getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }

                        5 -> {
                            println("cases 444444")
                            if (AppUtils().isNetworkConnected(mContext)) {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity?,
                                    getString(R.string.alert_heading),
                                    getString(R.string.not_available_limitted_access),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity?,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }
                        }

                        6 -> if (AppUtils().isNetworkConnected(mContext)) {
                            showDialogAlertLogout(
                                mContext,
                                "Confirm?",
                                "Do you want to Logout?",
                                R.drawable.questionmark_icon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity?,
                                "Network Error",
                                getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }

                        else -> {}
                    }
                }
            } else {
                // Left user
                when (position) {
                    0 -> {
                        PreferenceManager().setGoToSettings(mContext, "1")
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", mContext.packageName, null)
                        intent.data = uri
                        mContext.startActivity(intent)
                    }

                    1 -> {}
                    2 -> {}
                    3 -> {
                        val mintent: Intent = Intent(
                            mContext,
                            TutorialActivity::class.java
                        )
                        mintent.putExtra("type", 0)
                        mContext.startActivity(mintent)
                    }

                    4 -> if (AppUtils().isNetworkConnected(mContext)) {
                        showChangePasswordAlert()
                    } else {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity?,
                            "Network Error",
                            getString(R.string.no_internet),
                            R.drawable.nonetworkicon,
                            R.drawable.roundred
                        )
                    }

                    5 -> {
                        println("cases 555555")
                        if (AppUtils().isNetworkConnected(mContext)) {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity?,
                                getString(R.string.alert_heading),
                                getString(R.string.not_available_limitted_access),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity?,
                                "Network Error",
                                getString(R.string.no_internet),
                                R.drawable.nonetworkicon,
                                R.drawable.roundred
                            )
                        }
                    }

                    6 -> if (AppUtils().isNetworkConnected(mContext)) {
                        showDialogAlertLogout(
                            mContext,
                            "Confirm?",
                            "Do you want to Logout?",
                            R.drawable.questionmark_icon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity?,
                            "Network Error",
                            getString(R.string.no_internet),
                            R.drawable.nonetworkicon,
                            R.drawable.roundred
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun showDialogAlertLogout(mContext: Context, msgHead: String, msg: String, ico: Int, bgIcon: Int) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_layout)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.setText(msg)
        textHead.setText(msgHead)

        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            if (PreferenceManager().getUserId(mContext).equals("")) {
                PreferenceManager().setUserId(mContext, "")
                PreferenceManagerr.setUserId(mContext, "")
                PreferenceManager().setLoggedInStatus(mContext, "")
                dialog.dismiss()
                val mIntent = Intent(activity, LoginActivity::class.java)
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                mContext.startActivity(mIntent)
            } else {
               callLogoutApi(mContext, dialog)
            }
        }
        val dialogButtonCancel = dialog.findViewById<Button>(R.id.btn_Cancel)
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun callLogoutApi( context: Context, dialog: Dialog) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isComplete) {
                val token = task.result
                tokenM = token
            }
        }
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        var logoutmodel=LogoutApiModel(PreferenceManager().getUserId(context).toString(),
            tokenM,
            "2",
            androidId)
        val call: Call<TriggerUserModel> = ApiClient.getClient.logout(
            logoutmodel,"Bearer "+PreferenceManager().getaccesstoken(context).toString())

        call.enqueue(object : Callback<TriggerUserModel> {
            override fun onFailure(call: Call<TriggerUserModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<TriggerUserModel>,
                response: Response<TriggerUserModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){
                        dialog.dismiss()
                        //context.getSystemService(Context.NOTIFICATION_SERVICE).cancelAll()
                        PreferenceManager().setUserId(context, "")
                        PreferenceManagerr.setUserId(context, "")
                        PreferenceManager().setLoggedInStatus(context, "")
                        PreferenceManager().setDataCollection(context, "0")
                        AppController.kinArrayShow.clear()
                        CommonClass.kinArrayPass.clear()
                        if (PreferenceManager().getOwnDetailArrayList(
                                "OwnContact",
                                context
                            ) == null || PreferenceManager().getOwnDetailArrayList(
                                "OwnContact",
                                context
                            )!!.size == 0
                        ) {
                        } else {
                            val mOwnArrayList: ArrayList<OwnContactModel>? =
                                PreferenceManager().getOwnDetailArrayList("OwnContact", context)
                            mOwnArrayList!!.clear()
                            PreferenceManager().saveOwnDetailArrayList(
                                mOwnArrayList,
                                "OwnContact",
                                context
                            )
                        }

                        if (PreferenceManager().getKinDetailsArrayListShow("kinshow",context) == null ||
                            PreferenceManager().getKinDetailsArrayListShow(
                                "kinshow", context
                            )!!.size == 0
                        ) {
                        } else {
                            val mKinShowArray: ArrayList<KinModel>? =
                                PreferenceManager().getKinDetailsArrayListShow("kinshow",context)
                            mKinShowArray!!.clear()
                            PreferenceManager().saveKinDetailsArrayListShow(mKinShowArray,"kinshow", context)
                            val mKinPassArray: ArrayList<KinModel>? =
                                PreferenceManager().getKinDetailsArrayList("kinshow",context)
                            mKinPassArray!!.clear()
                            PreferenceManager().saveKinDetailsArrayList(mKinPassArray,"kinshow", context)
                        }
                        if (PreferenceManager().getInsuranceDetailArrayList(context) == null ||
                            PreferenceManager().getInsuranceDetailArrayList(
                                context
                            ).size == 0
                        ) {
                        } else {
                            println("insurance array size in logout12")
                            val mInsuranceDataArray: ArrayList<InsuranceDetailModel> =
                                PreferenceManager().getInsuranceDetailArrayList(context)
                            println("insurance array size in logout13")
                            mInsuranceDataArray.clear()
                            println("insurance array size in logout14")
                            PreferenceManager().saveInsuranceDetailArrayList(
                                mInsuranceDataArray,
                                context
                            )
                            System.out.println(
                                "insurance array size in logout" + PreferenceManager().getInsuranceDetailArrayList(
                                    context
                                ).size
                            )
                        }

                        if (PreferenceManager().getPassportDetailArrayList(context) == null || PreferenceManager().getPassportDetailArrayList(
                                context
                            ).size == 0
                        ) {
                        } else {
                            val dummyPassport: java.util.ArrayList<*> =
                                ArrayList<PassportDetailModel>()
                            println("insurance array size in logout15")
                            val mPassportDataArray: java.util.ArrayList<PassportDetailModel> =
                                PreferenceManager().getPassportDetailArrayList(context)
                            println("insurance array size in logout")
                            mPassportDataArray.clear()
                            println("insurance array size in logout16")
                            PreferenceManager().savePassportDetailArrayList(
                                mPassportDataArray,
                                context
                            )
                        }
                        if (PreferenceManager().getInsuranceStudentList(context) == null || PreferenceManager().getInsuranceStudentList(
                                context
                            ).size == 0
                        ) {
                        } else {
                            println("insurance array size in logout17")
                            val mStudentArray: ArrayList<StudentModelNew> =
                                PreferenceManager().getInsuranceStudentList(context)
                            mStudentArray.clear()
                            PreferenceManager().saveInsuranceStudentList(mStudentArray, context)
                        }
                        val dummyOwn: java.util.ArrayList<OwnContactModel> =
                            java.util.ArrayList<OwnContactModel>()
                        val dummyKin: java.util.ArrayList<KinModel> =
                            java.util.ArrayList<KinModel>()
                        val dummyInsurance: java.util.ArrayList<InsuranceDetailModel> =
                            java.util.ArrayList<InsuranceDetailModel>()
                        val dummyPassport: java.util.ArrayList<PassportDetailModel> =
                            java.util.ArrayList<PassportDetailModel>()
                        val dummyStudent: java.util.ArrayList<StudentModelNew> =
                            java.util.ArrayList<StudentModelNew>()
                        PreferenceManager().saveOwnDetailArrayList(dummyOwn, "OwnContact", context)
                        PreferenceManager().saveKinDetailsArrayListShow(dummyKin, "kinshow",context)
                        PreferenceManager().saveKinDetailsArrayList(dummyKin,"kinshow", context)
                        PreferenceManager().saveInsuranceDetailArrayList(dummyInsurance, context)
                        PreferenceManager().savePassportDetailArrayList(dummyPassport, context)
                        PreferenceManager().saveInsuranceStudentList(dummyStudent, context)
                        val mIntent = Intent(
                            context,
                            LoginActivity::class.java
                        )
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context.startActivity(mIntent)


                    }
                }

            }
        })
    }

    private fun showDialogAlertTriggerDataCollection(
        activity: Context,
        msgHead: String,
        msg: String,
        ico: Int,
        bgIcon: Int
    ) {
        valueTrigger = "0"
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_trigger_data_collection)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        val checkRecycler = dialog.findViewById<RecyclerView>(R.id.checkRecycler)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        checkRecycler.setHasFixedSize(true)
        val mRead = LinearLayoutManager(mContext)
        mRead.orientation = LinearLayoutManager.VERTICAL
        checkRecycler.layoutManager = mRead
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.setText(msg)
        textHead.setText(msgHead)
        val categoryList = java.util.ArrayList<String>()
        categoryList.add("All")
        categoryList.add("Student - Contact Details")
        categoryList.add("Student - Passport & visa")
        val mTriggerModelArrayList: ArrayList<TriggerDataModel> =
            java.util.ArrayList<TriggerDataModel>()
        for (i in categoryList.indices) {
            val model = TriggerDataModel()
            model.categoryName=categoryList[i]
            model.checkedCategory=false
            mTriggerModelArrayList.add(model)
        }
        println("Trigger data model arraylist size" + mTriggerModelArrayList.size)
        val mAdapter = TriggerRecyclerAdapter(mContext, mTriggerModelArrayList)
        checkRecycler.adapter = mAdapter
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            valueTrigger = if (mTriggerModelArrayList[0].checkedCategory) {
                "1"
            } else if (mTriggerModelArrayList[1].checkedCategory && !mTriggerModelArrayList[2].checkedCategory) {
                "2"
            } else if (mTriggerModelArrayList[2].checkedCategory && !mTriggerModelArrayList[1].checkedCategory) {
                "4"
            } else if (mTriggerModelArrayList[1].checkedCategory && mTriggerModelArrayList[2].checkedCategory) {
                "7"
            } else {
                "0"
            }
            if (valueTrigger.equals("0")) {
                Toast.makeText(
                    mContext,
                    "Please select any trigger type before confirming",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (AppUtils().isNetworkConnected(mContext)) {
                    dialog.dismiss()
                    callDataTriggerApi( valueTrigger)
                } else {
                    AppUtils().showDialogAlertDismiss(
                        mContext as Activity?,
                        "Network Error",
                        getString(R.string.no_internet),
                        R.drawable.nonetworkicon,
                        R.drawable.roundred
                    )
                }

                // Toast.makeText(mContext,  "Reached"+valueTrigger, Toast.LENGTH_SHORT).show();
            }
        }
        checkRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                        if (position == 0) {
                            if (mTriggerModelArrayList[position].checkedCategory) {
                                mTriggerModelArrayList[position].checkedCategory=false
                            } else {
                                mTriggerModelArrayList[0].checkedCategory=true
                                mTriggerModelArrayList[1].checkedCategory=false
                                mTriggerModelArrayList[2].checkedCategory=false
                            }
                        } else {
                            if (mTriggerModelArrayList[position].checkedCategory) {
                                mTriggerModelArrayList[position].checkedCategory=false
                            } else {
                                mTriggerModelArrayList[position].checkedCategory=true
                                mTriggerModelArrayList[0].checkedCategory=false
                            }
                            var j = 0
                            for (i in 1 until mTriggerModelArrayList.size) {
                                if (mTriggerModelArrayList[i].checkedCategory) {
                                    j = j + 1
                                }
                            }
                            if (j == 2) {
                                mTriggerModelArrayList[0].checkedCategory=true
                                mTriggerModelArrayList[1].checkedCategory=false
                                mTriggerModelArrayList[2].checkedCategory=false
                            }
                        }
                        mAdapter.notifyDataSetChanged()
                    }

                    fun onLongClickItem(v: View?, position: Int) {}
                })
        
        val dialogButtonCancel = dialog.findViewById<Button>(R.id.btn_Cancel)
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun callDataTriggerApi(triggerType: String) {
        var triggeruser=TriggerUserApiModel( PreferenceManager().getUserId(mContext).toString(),
            triggerType)
        val call: Call<TriggerUserModel> = ApiClient.getClient.trigger_user(
            triggeruser,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString())

        call.enqueue(object : Callback<TriggerUserModel> {
            override fun onFailure(call: Call<TriggerUserModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<TriggerUserModel>,
                response: Response<TriggerUserModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){
                        showDataSuccess(
                            mContext,
                            "Alert",
                            "\"Update Account Details\" will start next time the Parent App is opened.",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )


                    }
                }

            }
        })
    }

    private fun showDataSuccess(mContext: Context, s: String, s1: String, exclamationicon: Int, round: Int) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout_data_collection)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(R.drawable.round)
        icon.setImageResource(R.drawable.exclamationicon)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = "\"Update Account Details\" will start next time the Parent App is opened."
        textHead.text = "Alert"

        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun showChangePasswordAlert() {
        dialog = Dialog(mContext, R.style.NewDialog)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.custom_dialog_changepassword)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(true)
        // set the custom dialog components - edit text, button
        // set the custom dialog components - edit text, button
        val sdk = Build.VERSION.SDK_INT
        text_currentpswd = dialog!!.findViewById<EditText>(R.id.text_currentpassword)
        newpassword = dialog!!.findViewById<EditText>(R.id.text_currentnewpassword)
        confirmpassword = dialog!!.findViewById<EditText>(R.id.text_confirmpassword)

        val dialogSubmitButton = dialog!!
            .findViewById<Button>(R.id.btn_changepassword)

        // if button is clicked, close the custom dialog

        // if button is clicked, close the custom dialog
        dialogSubmitButton.setOnClickListener {
            AppUtils().hideKeyboard(mContext, newpassword)
            AppUtils().hideKeyboard(mContext, text_currentpswd)
            AppUtils().hideKeyboard(mContext, confirmpassword)
            if (text_currentpswd.getText().toString().trim { it <= ' ' }.length == 0) {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity?,
                    getString(R.string.alert_heading),
                    getString(R.string.enter_current_password),
                    R.drawable.infoicon,
                    R.drawable.round
                )
            } else if (newpassword.getText().toString().trim { it <= ' ' }.length == 0) {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity?,
                    getString(R.string.alert_heading),
                    getString(R.string.enter_new_password),
                    R.drawable.infoicon,
                    R.drawable.round
                )

                //newpassword.setError(mContext.getResources().getString(R.string.mandatory_field));
            } else if (confirmpassword.getText().toString().trim { it <= ' ' }.length == 0) {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity?,
                    getString(R.string.alert_heading),
                    getString(R.string.enter_confirm_password),
                    R.drawable.infoicon,
                    R.drawable.round
                )

                //confirmpassword.setError(mContext.getResources().getString(R.string.mandatory_field));
            } else if (newpassword.getText().toString()
                    .trim { it <= ' ' } != confirmpassword.getText()
                    .toString().trim { it <= ' ' }
            ) {
                //confirmpassword.setError(mContext.getResources().getString(R.string.password_mismatch));
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity?,
                    getString(R.string.alert_heading),
                    getString(R.string.password_mismatch),
                    R.drawable.infoicon,
                    R.drawable.round
                )
            } else {
                if (AppUtils().isNetworkConnected(mContext)) {
                    callChangePasswordAPI()
                } else {
                    AppUtils().showDialogAlertDismiss(
                        mContext as Activity?,
                        "Network Error",
                        getString(R.string.no_internet),
                        R.drawable.nonetworkicon,
                        R.drawable.roundred
                    )
                }
            }
        }

        val dialogCancel = dialog!!.findViewById<Button>(R.id.btn_cancel)
        dialogCancel.setOnClickListener {
            AppUtils().hideKeyboard(mContext, newpassword)
            AppUtils().hideKeyboard(mContext, text_currentpswd)
            AppUtils().hideKeyboard(mContext, confirmpassword)
            dialog!!.dismiss()
        }
        dialog!!.show()
    }

    private fun callChangePasswordAPI() {
       /* FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isComplete) {
                val token = task.result
                tokenM = token
            }
        }*/
        var changepassword=ChangePasswordApiModel(PreferenceManager().getUserId(mContext).toString(),
            text_currentpswd.text.toString(),
            newpassword.text.toString(),
            PreferenceManager().getUserEmail(mContext).toString(),
            tokenM,
            "2")
        val call: Call<ChangePasswordModel> = ApiClient.getClient.changepassword(
           changepassword,"Bearer "+ PreferenceManager().getaccesstoken(mContext).toString())

        call.enqueue(object : Callback<ChangePasswordModel> {
            override fun onFailure(call: Call<ChangePasswordModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<ChangePasswordModel>,
                response: Response<ChangePasswordModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){

                        dialog!!.dismiss()

                        showDialogSignUpAlert(
                            mContext ,
                            "Success",
                            getString(R.string.succ_pswd),
                            R.drawable.tick,
                            R.drawable.round
                        )


                    }
                }

            }
        })

    }

    private fun showDialogSignUpAlert(mContext: Context, msgHead: String, msg: String, ico: Int, bgIcon: Int) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.setText(msg)
        textHead.setText(msgHead)

        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            PreferenceManager().setUserId(mContext, "")
            PreferenceManagerr.setUserId(mContext, "")
            dialog.dismiss()
            val mIntent = Intent(activity, LoginActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(mIntent)
        }

        dialog.show()
    }


}


