package com.example.bskl_kotlin.activity.data_collection

import ApiClient
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.bskl_kotlin.BuildConfig
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.datacollection_p2.FirstScreenNewData
import com.example.bskl_kotlin.activity.datacollection_p2.SecondScreenNew

import com.example.bskl_kotlin.activity.datacollection_p2.model.InsuranceDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.KinModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.OwnContactModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.PassportDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.StudentModelNew
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.home.mContext
import com.example.bskl_kotlin.fragment.settings.model.TriggerUserModel
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.CommonClass
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataCollectionHome:AppCompatActivity(), ViewPager.OnPageChangeListener,
    RadioGroup.OnCheckedChangeListener {
    lateinit var mContext:Context
    var own_details: JSONArray? = null
    var kin_details: JSONArray? = null
    var emergency_details: JSONArray? = null

    lateinit var radioGroup: RadioGroup
    lateinit var radioButton1: RadioButton
    lateinit var radioButton2:RadioButton
    lateinit var radioButton3:RadioButton

    lateinit var pager: ViewPager

    private lateinit var nextBtn: ImageView
    private lateinit var backBtn:ImageView

    private lateinit var submitBtn: TextView

    private val OverallValue: String? = null
    var closeMsgKin: String? = null
    var closeMsgLocal: String? = null
    var JSONSTRING: String? = null



    lateinit var bottomLinear: LinearLayout

    lateinit var mInsuranceDetailArrayList: ArrayList<InsuranceDetailModel>

    var previousPage = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_collection_home)

        mContext = this
        pager = findViewById(R.id.viewPager)
        bottomLinear = findViewById(R.id.bottomLinear)
        pager.setAdapter(
            MyPagerAdapter(
                supportFragmentManager
            )
        )
        pager.addOnPageChangeListener(this)
        pager.setOffscreenPageLimit(2)
        radioGroup = findViewById<RadioGroup>(R.id.radiogroup)
        radioButton1 = findViewById(R.id.radioButton1)
        radioButton2 = findViewById(R.id.radioButton2)
        radioButton3 = findViewById(R.id.radioButton3)
        submitBtn = findViewById<TextView>(R.id.submit)
        nextBtn = findViewById<ImageView>(R.id.nextImg)
        backBtn = findViewById<ImageView>(R.id.backImg)
        radioGroup.setOnCheckedChangeListener(this)

        if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("1")) {
            bottomLinear.setVisibility(View.VISIBLE)
            radioButton1.setVisibility(View.VISIBLE)
            radioButton2.setVisibility(View.VISIBLE)
            radioButton3.setVisibility(View.GONE)
            submitBtn.setVisibility(View.VISIBLE)
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("5") || PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("7")
        ) {
            bottomLinear.setVisibility(View.VISIBLE)
            radioButton1.setVisibility(View.VISIBLE)
            radioButton2.setVisibility(View.VISIBLE)
            radioButton3.setVisibility(View.GONE)
            submitBtn.setVisibility(View.VISIBLE)
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("3") || PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("6")
        ) {
            bottomLinear.setVisibility(View.VISIBLE)
            radioButton1.setVisibility(View.INVISIBLE)
            radioButton2.setVisibility(View.INVISIBLE)
            radioButton3.setVisibility(View.INVISIBLE)
            submitBtn.setVisibility(View.VISIBLE)
            nextBtn.setVisibility(View.INVISIBLE)
            backBtn.setVisibility(View.INVISIBLE)
        } else {
            bottomLinear.setVisibility(View.VISIBLE)
            radioButton1.setVisibility(View.GONE)
            radioButton2.setVisibility(View.GONE)
            radioButton3.setVisibility(View.GONE)
            submitBtn.setVisibility(View.VISIBLE)
            nextBtn.setVisibility(View.INVISIBLE)
            backBtn.setVisibility(View.INVISIBLE)
        }

        closeMsgKin =
            "Next of Kin must not be existing parent contact for family (cross check email/phone number with mother/father/parent record)"
        closeMsgLocal =
            "Emergency Contact must not be existing parent contact for family (cross check email/phone number with mother/father/parent record)"
        val editor = getSharedPreferences("BSKL", MODE_PRIVATE).edit()
        editor.putString("validationkin", "0")
        editor.putString("validationlocal", "0")
        editor.apply()
        nextBtn.setOnClickListener {
            pager.currentItem = pager.currentItem + 1
        }
        backBtn.setOnClickListener {
            pager.currentItem = pager.currentItem - 1
        }
        previousPage = 1
        val prefs = getSharedPreferences("BSKL", MODE_PRIVATE)
        val data = prefs.getString("DATA_COLLECTION", null)

        try {
            val respObj = JSONObject(data)
            own_details = respObj.getJSONArray("own_details")
            kin_details = respObj.getJSONArray("kin_details")
            Log.e("own_details", own_details.toString())
            Log.e("kin_details", kin_details.toString())
            emergency_details = respObj.getJSONArray("local_emergency_details")
        } catch (e: Exception) {
        }
       /* pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
             
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })*/
        submitBtn.setOnClickListener {
            var triggerType = ""
            var overallStatus = ""
            if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("1")) {
                val currentPage = pager.currentItem
                if (currentPage == 0) {
                    if (checkOwnDetailEmpty().equals("")) {
                        var isFound = false
                        if (!isFound) {
                            for (i in 0 until AppController.kinArrayShow.size) {
                                if (!AppController.kinArrayShow.get(i).isConfirmed!!) {
                                    isFound = true
                                }
                            }
                        }
                        var isLocalFound = false
                        val isKinFound = false
                        var isRelationFound = false
                        if (!isRelationFound) {
                            for (i in 0 until AppController.kinArrayShow.size) {
                                if (AppController.kinArrayShow.get(i).relationship
                                        .equals("Local Emergency Contact") ||
                                    AppController.kinArrayShow.get(
                                        i
                                    ).relationship.equals("Emergency Contact")
                                ) {
                                    isLocalFound = true
                                }
                            }
                            if (isLocalFound) {
                                isRelationFound = true
                            }
                        }
                        if (isFound) {
                            showAlertOKButton(
                                mContext,
                                "Alert",
                                "Please Confirm all Contact",
                                "Ok",
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            if (!isRelationFound) {
                                if (!isLocalFound && !isKinFound) {
                                    showAlertOKButton(
                                        mContext,
                                        "Alert",
                                        "There should be at least one Emergency contact for the family",
                                        "Ok",
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    if (!isLocalFound) {
                                        showAlertOKButton(
                                            mContext,
                                            "Alert",
                                            "There should be at least one Emergency contact for the family",
                                            "Ok",
                                            R.drawable.exclamationicon,
                                            R.drawable.round
                                        )
                                    } else {
                                    }
                                }
                            } else {
                                triggerType = "6"
                                overallStatus = "1"
                                callSubmitAPI(triggerType, overallStatus)
                            }
                        }
                    } else {
                        ShowCondition("Please confirm all mandatory fields in Own Details")
                    }
                } else {
                    var isFound = false
                    var foundPosition = -1


                    if (CommonClass.mStudentDataArrayList.size > 0) {
                        for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                            if (!isFound) {
                                if (!CommonClass.mStudentDataArrayList.get(i).isConfirmed) {
                                    isFound = true
                                    foundPosition = i
                                }
                            }
                        }
                    }
                    if (isFound) {
                        ShowConditionnew(
                            "Please confirm all the details for " + CommonClass.mStudentDataArrayList.get(
                                foundPosition
                            ).mName
                        )
                    } else {
                        triggerType = "1"
                        overallStatus = "2"
                        callSubmitAPI(triggerType, overallStatus)
                    }
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("2")
            ) {
                if (checkOwnDetailEmpty().equals("")) {
                    var isFound = false
                    if (!isFound) {
                        for (i in 0 until AppController.kinArrayShow.size) {
                            if (!AppController.kinArrayShow.get(i).isConfirmed!!) {
                                isFound = true
                            }
                        }
                    }
                    var isLocalFound = false
                    val isKinFound = false
                    var isRelationFound = false
                    if (!isRelationFound) {
                        for (i in 0 until AppController.kinArrayShow.size) {
                            if (AppController.kinArrayShow.get(i).relationship
                                    .equals("Local Emergency Contact") || AppController.kinArrayShow.get(
                                    i
                                ).relationship.equals("Emergency Contact")
                            ) {
                                isLocalFound = true
                            }
                        }
                        if (isLocalFound) {
                            isRelationFound = true
                        }
                    }
                    if (isFound) {
                        showAlertOKButton(
                            mContext,
                            "Alert",
                            "Please Confirm all Contact",
                            "Ok",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        if (!isRelationFound) {
                            if (!isLocalFound) {
                                showAlertOKButton(
                                    mContext,
                                    "Alert",
                                    "There should be at least one Emergency contact for the family",
                                    "Ok",
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                if (!isLocalFound) {
                                    showAlertOKButton(
                                        mContext,
                                        "Alert",
                                        "There should be at least one Emergency contact for the family",
                                        "Ok",
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                }
                            }
                        } else {
                            //1 , 2
                            triggerType = "1"
                            overallStatus = "2"
                            callSubmitAPI(triggerType, overallStatus)
                        }
                    }
                } else {
                    ShowCondition("Please confirm all mandatory fields in Own Details")
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("3")
            ) {
                var isFound = false
                var foundPosition = -1
                if (CommonClass.mStudentDataArrayList.size > 0) {
                    for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                        if (!isFound) {
                            if (!CommonClass.mStudentDataArrayList.get(i).isConfirmed) {
                                isFound = true
                                foundPosition = i
                            }
                        }
                    }
                }
                if (isFound) {
                    ShowConditionnew(
                        "Please confirm all the details for " + CommonClass.mStudentDataArrayList.get(
                            foundPosition
                        ).mName
                    )
                } else {
                    triggerType = "1"
                    overallStatus = "2"
                    callSubmitAPI(triggerType, overallStatus)
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("4")
            ) {
                var isFound = false
                var foundPosition = -1
                if (CommonClass.mStudentDataArrayList.size > 0) {
                    for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                        if (!isFound) {
                            if (!CommonClass.mStudentDataArrayList.get(i).isConfirmed) {
                                isFound = true
                                foundPosition = i
                            }
                        }
                    }
                }
                if (isFound) {
                    ShowConditionnew(
                        "Please confirm all the details for " + CommonClass.mStudentDataArrayList.get(
                            foundPosition
                        ).mName
                    )
                } else {
                    triggerType = "1"
                    overallStatus = "2"
                    callSubmitAPI(triggerType, overallStatus)
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("5")
            ) {
                val currentPage = pager.currentItem
                if (currentPage == 0) {
                    if (checkOwnDetailEmpty().equals("")) {
                        var isFound = false
                        if (!isFound) {
                            for (i in 0 until AppController.kinArrayShow.size) {
                                if (!AppController.kinArrayShow.get(i).isConfirmed!!) {
                                    isFound = true
                                }
                            }
                        }
                        var isLocalFound = false
                        val isKinFound = false
                        var isRelationFound = false
                        if (!isRelationFound) {
                            for (i in 0 until AppController.kinArrayShow.size) {
                                if (AppController.kinArrayShow.get(i).relationship
                                        .equals("Local Emergency Contact") || AppController.kinArrayShow.get(
                                        i
                                    ).relationship.equals("Emergency Contact")
                                ) {
                                    isLocalFound = true
                                }
                            }
                            if (isLocalFound) {
                                isRelationFound = true
                            }
                        }
                        if (isFound) {
                            showAlertOKButton(
                                mContext,
                                "Alert",
                                "Please Confirm all Contact",
                                "Ok",
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            if (!isRelationFound) {
                                if (!isLocalFound && !isKinFound) {
                                    showAlertOKButton(
                                        mContext,
                                        "Alert",
                                        "There should be at least one Emergency contact for the family",
                                        "Ok",
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    if (!isLocalFound) {
                                        showAlertOKButton(
                                            mContext,
                                            "Alert",
                                            "There should be at least one Emergency contact for the family",
                                            "Ok",
                                            R.drawable.exclamationicon,
                                            R.drawable.round
                                        )
                                    } else {
                                    }
                                }
                            } else {
                                //1 , 2
                                triggerType = "3"
                                overallStatus = "1"
                                callSubmitAPI(triggerType, overallStatus)
                            }
                        }
                    }
                } else {
                    var isFound = false
                    var foundPosition = -1
                    if (CommonClass.mStudentDataArrayList.size > 0) {
                        for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                            if (!isFound) {
                                if (!CommonClass.mStudentDataArrayList.get(i).isConfirmed) {
                                    isFound = true
                                    foundPosition = i
                                }
                            }
                        }
                    }
                    if (isFound) {
                        ShowConditionnew(
                            "Please confirm all the details for " + CommonClass.mStudentDataArrayList.get(
                                foundPosition
                            ).mName
                        )
                    } else {
                        triggerType = "1"
                        overallStatus = "2"
                        callSubmitAPI(triggerType, overallStatus)
                    }
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("6")
            ) {
                var isFound = false
                var foundPosition = -1
                if (CommonClass.mStudentDataArrayList.size > 0) {
                    for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                        if (!isFound) {
                            if (!CommonClass.mStudentDataArrayList.get(i).isConfirmed) {
                                isFound = true
                                foundPosition = i
                            }
                        }
                    }
                }
                if (isFound) {
                    ShowConditionnew(
                        "Please confirm all the details for " + CommonClass.mStudentDataArrayList.get(
                            foundPosition
                        ).mName
                    )
                } else {
                    triggerType = "1"
                    overallStatus = "2"
                    callSubmitAPI(triggerType, overallStatus)
                }
            }

            if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("7")) {
                val currentPage = pager.currentItem
                if (currentPage == 0) {
                    if (checkOwnDetailEmpty().equals("")) {
                        var isFound = false
                        if (!isFound) {
                            for (i in 0 until AppController.kinArrayShow.size) {
                                println()
                                if (!AppController.kinArrayShow.get(i).isConfirmed!!) {
                                    isFound = true
                                }
                            }
                        }
                        var isLocalFound = false
                        val isKinFound = false
                        var isRelationFound = false
                        if (!isRelationFound) {
                            for (i in 0 until AppController.kinArrayShow.size) {
                                if (AppController.kinArrayShow.get(i).relationship
                                        .equals("Local Emergency Contact") || AppController.kinArrayShow.get(
                                        i
                                    ).relationship.equals("Emergency Contact")
                                ) {
                                    isLocalFound = true
                                }
                            }
                            if (isLocalFound) {
                                isRelationFound = true
                            }
                        }
                        if (isFound) {
                            showAlertOKButton(
                                mContext,
                                "Alert",
                                "Please Confirm all Contact",
                                "Ok",
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            if (!isRelationFound) {
                                if (!isLocalFound) {
                                    showAlertOKButton(
                                        mContext,
                                        "Alert",
                                        "There should be at least one Emergency contact for the family",
                                        "Ok",
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                    if (!isLocalFound) {
                                        showAlertOKButton(
                                            mContext,
                                            "Alert",
                                            "There should be at least one Emergency contact for the family",
                                            "Ok",
                                            R.drawable.exclamationicon,
                                            R.drawable.round
                                        )
                                    } else {
                                    }
                                }
                            } else {
                                //1 , 2
                                triggerType = "4"
                                overallStatus = "1"
                                callSubmitAPI(triggerType, overallStatus)
                            }
                        }
                    }
                } else {
                    var isFound = false
                    var foundPosition = -1
                    if (CommonClass.mStudentDataArrayList.size > 0) {
                        for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                            if (!isFound) {
                                if (!CommonClass.mStudentDataArrayList.get(i).isConfirmed) {
                                    isFound = true
                                    foundPosition = i
                                }
                            }
                        }
                    }
                    if (isFound) {
                        ShowConditionnew(
                            "Please confirm all the details for " + CommonClass.mStudentDataArrayList.get(
                                foundPosition
                            ).mName
                        )
                    } else {
                        triggerType = "1"
                        overallStatus = "2"
                        callSubmitAPI(triggerType, overallStatus)
                    }
                }
            }
        }

    }
    private fun callSubmitAPI(trigger: String, overall: String) {
        var OWNDATA: String? = null
        val SECONDDATA: String? = null
        val newArrayData: java.util.ArrayList<KinModel> = java.util.ArrayList<KinModel>()
        for (i in 0 until CommonClass.kinArrayPass.size) {
            val model = KinModel()
            model.status=CommonClass.kinArrayPass.get(i).status
            model.request=CommonClass.kinArrayPass.get(i).request
            model.name=CommonClass.kinArrayPass.get(i).name
            model.last_name=CommonClass.kinArrayPass.get(i).last_name
            model.email=CommonClass.kinArrayPass.get(i).email
            model.title=CommonClass.kinArrayPass.get(i).title
            if (CommonClass.kinArrayPass.get(i).kin_id!!.startsWith("Mobatia_")) {
                model.kin_id=""
            } else {
                model.kin_id=CommonClass.kinArrayPass.get(i).kin_id
            }
            model.relationship=CommonClass.kinArrayPass.get(i).relationship
            model.code=CommonClass.kinArrayPass.get(i).code
            model.user_mobile=CommonClass.kinArrayPass.get(i).user_mobile
            model.student_id=CommonClass.kinArrayPass.get(i).student_id
            model.created_at=CommonClass.kinArrayPass.get(i).created_at
            model.updated_at=CommonClass.kinArrayPass.get(i).updated_at
            model.phone=CommonClass.kinArrayPass.get(i).phone
            model.correspondencemailmerge=
                CommonClass.kinArrayPass.get(i).correspondencemailmerge
            model.justcontact=CommonClass.kinArrayPass.get(i).justcontact
            model.reportmailmerge=CommonClass.kinArrayPass.get(i).reportmailmerge
            if (CommonClass.kinArrayPass.get(i).kin_id!!.startsWith("Mobatia_")) {
                model.id=""
            } else {
                model.id=CommonClass.kinArrayPass.get(i).id
            }
            model.user_id=CommonClass.kinArrayPass.get(i).user_id
            model.isFullFilled=CommonClass.kinArrayPass.get(i).isFullFilled
            model.isNewData=CommonClass.kinArrayPass.get(i).isNewData
            model.isConfirmed=CommonClass.kinArrayPass.get(i).isConfirmed
            newArrayData.add(model)
        }
        var FirstArray: ArrayList<KinModel> = ArrayList<KinModel>()
        FirstArray = newArrayData
        val newGson = Gson()
        val FIRSTDATA = newGson.toJson(FirstArray)
        var SecondArray: ArrayList<InsuranceDetailModel>
        SecondArray = PreferenceManager().getInsuranceDetailArrayList(mContext)
        val IsuGson = Gson()
        val InsuranceGson = IsuGson.toJson(SecondArray)
        var PassArray: ArrayList<PassportDetailModel>
        PassArray = PreferenceManager().getPassportDetailArrayList(mContext)
        val PGson = Gson()
        val PassGson = PGson.toJson(PassArray)
        val OwnData = java.util.ArrayList<String>()
        OwnData.add(java.lang.String.valueOf(PreferenceManager().getOwnDetailsJSONArrayList(mContext)))
        for (i in OwnData.indices) {
            OWNDATA = OwnData[i]
        }
        if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("1")) {
            if (overall.equals("2") && trigger.equals("1")) {
                JSONSTRING =
                    "{\"data\":{\"own_details\":$OWNDATA,\"kin_details\":$FIRSTDATA,\"health_details\":$InsuranceGson,\"passport_details\":$PassGson} }"
                CallApi(JSONSTRING!!, trigger, overall)
            } else {
                JSONSTRING = "{\"data\":{\"own_details\":$OWNDATA,\"kin_details\":$FIRSTDATA}}"
                CallApi(JSONSTRING!!, trigger, overall)
            }
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("2")) {
            JSONSTRING = "{\"data\":{\"own_details\":$OWNDATA,\"kin_details\":$FIRSTDATA}}"
            CallApi(JSONSTRING!!, trigger, overall)
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("3")) {
            JSONSTRING = "{\"data\":{\"health_details\":$InsuranceGson}}"
            CallApi(JSONSTRING!!, trigger, overall)
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("4")
        ) {   // -->  ONLY PASSPORT
            JSONSTRING = "{\"data\":{\"passport_details\":$PassGson}}"
            CallApi(JSONSTRING!!, trigger, overall)
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("5")) {
            if (overall.equals("2") && trigger.equals("1")) {
                JSONSTRING =
                    "{\"data\":{\"own_details\":$OWNDATA,\"kin_details\":$FIRSTDATA,\"health_details\":$InsuranceGson}}"
                CallApi(JSONSTRING!!, trigger, overall)
            } else {
                JSONSTRING = "{\"data\":{\"own_details\":$OWNDATA,\"kin_details\":$FIRSTDATA}}"
                CallApi(JSONSTRING!!, trigger, overall)
            }
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("6")
        ) {   //  --> HEALTH + PASSPORT
            JSONSTRING =
                "{\"data\":{\"health_details\":$InsuranceGson,\"passport_details\":$PassGson}}"
            CallApi(JSONSTRING!!, trigger, overall)
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("7")
        ) {   //  --> OWN + PASSPORT
            if (overall.equals("2") && trigger.equals("1")) {
                JSONSTRING =
                    "{\"data\":{\"own_details\":$OWNDATA,\"kin_details\":$FIRSTDATA,\"passport_details\":$PassGson}}"
                CallApi(JSONSTRING!!, trigger, overall)
            } else {
                JSONSTRING = "{\"data\":{\"own_details\":$OWNDATA,\"kin_details\":$FIRSTDATA}}"
                CallApi(JSONSTRING!!, trigger, overall)
            }
        }
    }
    fun CallApi(jsonstring:String,triggerType:String,overAll:String) {
        val deviceBrand = Build.MANUFACTURER
        val deviceModel = Build.MODEL
        val osVersion = Build.VERSION.RELEASE
        val devicename = "$deviceBrand $deviceModel $osVersion"
        //  int versionCode= BuildConfig.VERSION_NAME;
        //  int versionCode= BuildConfig.VERSION_NAME;
        val version: String = BuildConfig.VERSION_NAME
        var submitdatamodel= SubmitDataCollectionApiModel(
            PreferenceManager().getUserId(mContext).toString(),overAll,
            jsonstring,triggerType,"2",devicename,version)
        val call: Call<TriggerUserModel> = ApiClient.getClient.submit_datacollection_new(
            submitdatamodel,"Bearer "+ PreferenceManager().getaccesstoken(mContext).toString()
        )

        call.enqueue(object : Callback<TriggerUserModel> {
            override fun onFailure(call: Call<TriggerUserModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<TriggerUserModel>,
                response: Response<TriggerUserModel>
            ) {

                val responsedata = response.body()

                Log.e("Response Signup", responsedata.toString())

                if (response.body()!!.responsecode.equals("200")) {
                    if (response.body()!!.response.statuscode.equals("303")) {
                        if (triggerType.equals("1", ignoreCase = true) && overAll.equals(
                                "2",
                                ignoreCase = true
                            )
                        ) {
                            AppController.kinArrayShow.clear()
                            CommonClass.kinArrayPass.clear()
                            val mOwnArrayList: ArrayList<OwnContactModel>? =
                                PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)
                            mOwnArrayList!!.clear()
                            PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!.clear()
                            PreferenceManager().saveOwnDetailArrayList(
                                mOwnArrayList,
                                "OwnContact",
                                mContext
                            )
                            PreferenceManager().saveKinDetailsArrayListShow(
                                AppController.kinArrayShow,
                                "kinshow",mContext
                            )
                            PreferenceManager().saveKinDetailsArrayList(
                                CommonClass.kinArrayPass,
                                "kinshow", mContext
                            )
                            val mInsurance: java.util.ArrayList<InsuranceDetailModel> =
                                PreferenceManager().getInsuranceDetailArrayList(mContext)
                            mInsurance.clear()
                            PreferenceManager().saveInsuranceDetailArrayList(mInsurance, mContext)
                            val mPassport: java.util.ArrayList<PassportDetailModel> =
                                PreferenceManager().getPassportDetailArrayList(mContext)
                            mPassport.clear()
                            PreferenceManager().savePassportDetailArrayList(mPassport, mContext)
                            CommonClass.mStudentDataArrayList.clear()
                            val mStuudent: ArrayList<StudentModelNew> =
                                PreferenceManager().getInsuranceStudentList(mContext)
                            mStuudent.clear()
                            PreferenceManager().saveInsuranceStudentList(mStuudent, mContext)
                            showDataSuccess(
                                mContext,
                                "Alert",
                                getString(R.string.data_collection_submission_success),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            if (overAll.equals("1", ignoreCase = true) && triggerType.equals(
                                    "6",
                                    ignoreCase = true
                                )
                            ) {
                                val mOwnArrayList: ArrayList<OwnContactModel>? =
                                    PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)
                                mOwnArrayList!!.clear()
                                AppController.kinArrayShow.clear()
                                CommonClass.kinArrayPass.clear()
                                PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!.clear()
                                PreferenceManager().saveOwnDetailArrayList(
                                    mOwnArrayList,
                                    "OwnContact",
                                    mContext
                                )
                                PreferenceManager().saveKinDetailsArrayListShow(
                                    AppController.kinArrayShow,"kinshow",
                                    mContext
                                )
                                PreferenceManager().saveKinDetailsArrayList(
                                    CommonClass.kinArrayPass,"kinshow",
                                    mContext
                                )
                                PreferenceManager().setDataCollectionTriggerType(
                                    mContext,
                                    triggerType
                                )
                                finish()
                                val i = Intent(mContext, DataCollectionHome::class.java)
                                startActivity(i)
                            } else {
                                PreferenceManager().setDataCollectionTriggerType(
                                    mContext,
                                    triggerType
                                )
                                finish()
                                val i = Intent(mContext, DataCollectionHome::class.java)
                                startActivity(i)
                            }
                        }
                    }

                }
            }
        })

    }
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("1")
        ) {
            if (position == 1) {
                if (checkOwnDetailEmpty().equals("")) {
                    var isFound = false
                    if (!isFound) {
                        for (i in 0 until AppController.kinArrayShow.size) {
                            if (!AppController.kinArrayShow.get(i).isConfirmed!!) {
                                isFound = true
                            }
                        }
                    }
                    var isLocalFound = false
                    val isKinFound = false
                    var isRelationFound = false
                    if (!isRelationFound) {
                        for (i in 0 until AppController.kinArrayShow.size) {
                            if (AppController.kinArrayShow.get(i).relationship
                                    .equals("Local Emergency Contact") || AppController.kinArrayShow.get(
                                    i
                                ).relationship.equals("Emergency Contact")
                            ) {
                                isLocalFound = true
                            }
                        }
                        if (isLocalFound) {
                            isRelationFound = true
                        }
                    }
                    if (isFound) {
                        showAlertOKButton(
                            mContext,
                            "Alert",
                            "Please Confirm all Contact",
                            "Ok",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        if (!isRelationFound) {
                            if (!isLocalFound) {
                                showAlertOKButton(
                                    mContext,
                                    "Alert",
                                    "There should be at least one Emergency contact for the family",
                                    "Ok",
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            }
                        }
                    }
                } else {
                    ShowCondition("Please confirm all mandator fields in Own Details")
                }
            }
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("5")
        ) {
            if (position == 1) {
                if (checkOwnDetailEmpty().equals("")) {
                    var isFound = false
                    if (!isFound) {
                        for (i in 0 until AppController.kinArrayShow.size) {
                            println()
                            if (!AppController.kinArrayShow.get(i).isConfirmed!!) {
                                isFound = true
                            }
                        }
                    }
                    if (isFound) {
                        showAlertOKButton(
                            mContext,
                            "Alert",
                            "Please Confirm all Contact",
                            "Ok",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                } else {
                    ShowCondition("Please confirm all mandator fields in Own Details")
                }
            }
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("7")
        ) {
            if (position == 1) {
                if (checkOwnDetailEmpty().equals("")) {
                    var isFound = false
                    if (!isFound) {
                        for (i in 0 until AppController.kinArrayShow.size) {
                            println()
                            if (!AppController.kinArrayShow.get(i).isConfirmed!!) {
                                isFound = true
                            }
                        }
                    }
                    var isLocalFound = false
                    val isKinFound = false
                    var isRelationFound = false
                    if (!isRelationFound) {
                        for (i in 0 until AppController.kinArrayShow.size) {
                            if (AppController.kinArrayShow.get(i).relationship
                                    .equals("Local Emergency Contact") || AppController.kinArrayShow.get(
                                    i
                                ).relationship.equals("Emergency Contact")
                            ) {
                                isLocalFound = true
                            }
                        }
                        if (isLocalFound) {
                            isRelationFound = true
                        }
                    }
                    if (isFound) {
                        showAlertOKButton(
                            mContext,
                            "Alert",
                            "Please Confirm all Contact",
                            "Ok",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        if (!isRelationFound) {
                            if (!isLocalFound) {
                                showAlertOKButton(
                                    mContext,
                                    "Alert",
                                    "There should be at least one Emergency contact for the family",
                                    "Ok",
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                if (!isLocalFound) {
                                    showAlertOKButton(
                                        mContext,
                                        "Alert",
                                        "There should be at least one Emergency contact for the family",
                                        "Ok",
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                } else {
                                }
                            }
                        }
                    }
                } else {
                    ShowCondition("Please confirm all mandator fields in Own Details")
                }
            }
        }
    }

    override fun onPageSelected(position: Int) {
        if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("1")) {
            bottomLinear.visibility = View.VISIBLE
            radioButton3.visibility = View.VISIBLE
            when (position) {
                0 -> {
                    radioButton1.visibility = View.VISIBLE
                    radioButton2.visibility = View.VISIBLE
                    radioButton3.visibility = View.GONE
                    radioGroup.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.VISIBLE
                }

                1 -> {
                    radioButton1.visibility = View.VISIBLE
                    radioButton2.visibility = View.VISIBLE
                    radioButton3.visibility = View.GONE
                    radioGroup.check(R.id.radioButton2)
                    nextBtn.visibility = View.INVISIBLE
                    backBtn.visibility = View.VISIBLE
                    submitBtn.visibility = View.VISIBLE
                }

                else -> {
                    radioButton1.visibility = View.VISIBLE
                    radioButton2.visibility = View.VISIBLE
                    radioButton3.visibility = View.GONE
                    radioGroup.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.VISIBLE
                }
            }
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("2")) {
            bottomLinear.visibility = View.GONE
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("3")) {
            bottomLinear.visibility = View.GONE
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("4")) {
            bottomLinear.visibility = View.GONE
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("5")) {
            bottomLinear.visibility = View.VISIBLE
            radioButton3.visibility = View.GONE
            when (position) {
                0 -> {
                    radioGroup.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.VISIBLE
                }

                1 -> {
                    radioGroup.check(R.id.radioButton2)
                    nextBtn.visibility = View.INVISIBLE
                    backBtn.visibility = View.VISIBLE
                    submitBtn.visibility = View.VISIBLE
                }

                else -> {
                    radioGroup.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.VISIBLE
                }
            }
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("6")) {
            bottomLinear.visibility = View.VISIBLE
            radioButton3.visibility = View.GONE
            when (position) {
                0 -> {
                    radioGroup.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.VISIBLE
                }

                1 -> {
                    radioGroup.check(R.id.radioButton2)
                    nextBtn.visibility = View.INVISIBLE
                    backBtn.visibility = View.VISIBLE
                    submitBtn.visibility = View.VISIBLE
                }

                else -> {
                    radioGroup.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.VISIBLE
                }
            }
        } else {
            bottomLinear.visibility = View.VISIBLE
            radioButton3.visibility = View.GONE
            when (position) {
                0 -> {
                    radioGroup.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.VISIBLE
                }

                1 -> {
                    radioGroup.check(R.id.radioButton2)
                    nextBtn.visibility = View.INVISIBLE
                    backBtn.visibility = View.VISIBLE
                    submitBtn.visibility = View.VISIBLE
                }

                else -> {
                    radioGroup.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.radioButton1 -> pager.currentItem = 0
            R.id.radioButton2 -> pager.currentItem = 1
        }
    }
    override fun onBackPressed() {
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
        text.text = "Please update this information next time"
        textHead.text = "Alert"
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            PreferenceManager().setSuspendTrigger(mContext, "1")
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }

    /*
    Calling Using Okhttp Method
     */
    private fun ShowCondition(whoValueEmpty: String) {
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
        text.text = whoValueEmpty
        textHead.text = "Alert"
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.text = "Ok"
        dialogButton.setOnClickListener {
            dialog.dismiss()
            pager.setCurrentItem(0, true)
        }
        dialog.show()
    }
    private fun ShowConditionnew(whoValueEmpty: String) {
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
        text.text = whoValueEmpty
        textHead.text = "Alert"
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.text = "Ok"
        dialogButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    private class MyPagerAdapter
    /**
     * Constructor
     *
     * @param fm
     */
        (fm: FragmentManager?) : FragmentPagerAdapter(
        fm!!
    ) {
        /**
         * Return fragment based on the position.
         *
         * @param position
         * @return
         */
        override fun getItem(position: Int): Fragment {
            return if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("1")
            ) {
                when (position) {
                    0 -> FirstScreenNewData()
                    1 -> SecondScreenNew()
                    else -> FirstScreenNewData()
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("2")
            ) {
                when (position) {
                    0 -> FirstScreenNewData()
                    else -> FirstScreenNewData()
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("3")
            ) {
                when (position) {
                    0 -> SecondScreenNew()
                    else -> SecondScreenNew()
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("4")
            ) {
                when (position) {
                    0 -> SecondScreenNew()
                    else -> SecondScreenNew()
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("5")
            ) {
                when (position) {
                    0 -> FirstScreenNewData()
                    1 -> SecondScreenNew()
                    else -> FirstScreenNewData()
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("6")
            ) {
                when (position) {
                    0 -> SecondScreenNew()
                    else -> SecondScreenNew()
                }
            } else {
                when (position) {
                    0 -> FirstScreenNewData()
                    1 -> SecondScreenNew()
                    else -> SecondScreenNew()
                }
            }
        }

        /**
         * Return the number of pages.
         *
         * @return
         */
        override fun getCount(): Int {
            return if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("1")
            ) {
                2
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("5") || PreferenceManager().getDataCollectionTriggerType(
                    mContext
                ).equals("7")
            ) {
                2
            } else {
                1
            }
        }
    }

    fun showAlertOKButton(
        activity: Context?,
        msg: String?,
        msgHead: String?,
        button: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout_data_collection)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = msgHead
        textHead.text = msg
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.text = button
        dialogButton.setOnClickListener {
            dialog.dismiss()
            pager.setCurrentItem(0, true)
        }
        dialog.show()
    }

    fun checkOwnDetailEmpty(): String? {
        var feild = ""
        val mOwnContactarrayList: ArrayList<OwnContactModel>? =
            PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)
        feild = if (mOwnContactarrayList!![0].name.equals("")) {
            "First Name"
        } else {
            if (mOwnContactarrayList!![0].email.equals("")) {
                "Email ID"
            } else {
                if (mOwnContactarrayList!![0].phone.equals("")) {
                    "Phone Number"
                } else {
                    if (mOwnContactarrayList!![0].address1.equals("")) {
                        "Address Line 1"
                    } else {
                        if (mOwnContactarrayList!![0].address2.equals("")) {
                            "Address Line 2"
                        } else {
                            if (mOwnContactarrayList!![0].town.equals("")) {
                                "Town"
                            } else {
                                if (mOwnContactarrayList!![0].state.equals("")) {
                                    "State"
                                } else {
                                    if (mOwnContactarrayList!![0].country.equals("")) {
                                        "Country"
                                    } else {
                                        if (mOwnContactarrayList!![0].pincode
                                                .equals("")
                                        ) {
                                            "Post Code"
                                        } else {
                                            if (mOwnContactarrayList!![0].isConfirmed) {
                                                ""
                                            } else {
                                                "Not Confirmed"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return feild
    }

    private fun showDataSuccess(
        mContext: Context,
        msgHead: String,
        msg: String,
        ico: Int,
        bgIcon: Int
    ) {
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
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }
}