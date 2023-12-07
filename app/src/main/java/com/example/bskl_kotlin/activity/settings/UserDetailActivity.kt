package com.example.bskl_kotlin.activity.settings

import ApiClient
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.settings.model.StudentUserModel
import com.example.bskl_kotlin.activity.settings.model.UserProfileModel
import com.example.bskl_kotlin.activity.settings.model.UserprofileApiModel
import com.example.bskl_kotlin.activity.settings.model.UserprofileResponseModel
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.manager.AppUtils
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException

class UserDetailActivity:AppCompatActivity() {
    lateinit var context: Context
    var extras: Bundle? = null
    var position = 0
    var mActivity: Activity? = null
    var header: RelativeLayout? = null
    var back: ImageView? = null

    lateinit var relSub: LinearLayout
    var stnameValue: TextView? = null
    var email: TextView? = null
    var relationship: TextView? = null
    var mobileno: TextView? = null
    var useraddress: TextView? = null
    var telephoneNo: TextView? = null
    var relationShip: TextView? = null
    var parentname = ""
    var emailID = ""
    var mobile = ""
    var teleNo = ""
    var relation = ""
    var useraddressdata = ""
    var mobileLinear: LinearLayout? = null
    var Telephone: LinearLayout? = null
    var addresslinear: LinearLayout? = null
    var relationShipLinear: LinearLayout? = null
    lateinit var studentsModelArrayList: ArrayList<StudentUserModel>
    var scrollView: ScrollView? = null
    lateinit var muserData: ArrayList<UserProfileModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        mActivity = this
        context = this
        extras = intent.extras
        if (extras != null) {
            position = extras!!.getInt("position")
        }

        try {
            initialiseUI()
            if (AppUtils().checkInternet(context)) {
                getUserProfileApi()
            } else {
                AppUtils().showDialogAlertDismiss(
                    mActivity,
                    "Network Error",
                    getString(R.string.no_internet),
                    R.drawable.nonetworkicon,
                    R.drawable.roundred
                )
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    private fun getUserProfileApi() {
        var usermodel= UserprofileApiModel(
            PreferenceManager().getUserId(context).toString() )
        val call: Call<UserprofileResponseModel> = ApiClient.getClient.userprofile(
            usermodel,"Bearer "+ PreferenceManager().getaccesstoken(context).toString())

        call.enqueue(object : Callback<UserprofileResponseModel> {
            override fun onFailure(call: Call<UserprofileResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<UserprofileResponseModel>,
                response: Response<UserprofileResponseModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){

                        val profileObj: UserProfileModel = responsedata!!.response.profile
                        parentname = profileObj.name.toString()
                        emailID = profileObj.email.toString()
                        mobile = profileObj.mobile_no.toString()
                        teleNo = profileObj.telephone_no.toString()
                        relation = profileObj.relationship.toString()
                        useraddressdata = profileObj.address.toString()
                        stnameValue!!.text = parentname
                        email!!.text = emailID
                        relationship!!.text = relation
                        useraddress!!.text = useraddressdata
                        mobileno!!.text = mobile
                        telephoneNo!!.text = teleNo
                        relationShip!!.text = relation
                        if (profileObj.mobile_no.equals("")) {
                            Telephone!!.visibility = View.GONE
                        } else {
                            Telephone!!.visibility = View.VISIBLE
                        }
                        if (profileObj.telephone_no.equals("")) {
                            mobileLinear!!.visibility = View.GONE
                        } else {
                            mobileLinear!!.visibility = View.VISIBLE
                        }
                        if (profileObj.address.equals("")) {
                            addresslinear!!.visibility = View.VISIBLE
                            useraddress!!.text = "No Data"
                            useraddress!!.setTextColor(resources.getColor(R.color.red))
                        } else {
                            addresslinear!!.visibility = View.VISIBLE
                            useraddress!!.setTextColor(resources.getColor(R.color.black))
                        }

                        scrollView!!.visibility = View.VISIBLE

                        val studentArray: ArrayList<StudentUserModel> = responsedata!!.response.studentlist
                        if (studentArray.size > 0) {
                            for (i in 0 until studentArray.size) {
                                val dataObject = studentArray[i]
                                //									if (dataObject.optString("alumi").equalsIgnoreCase("0")) {
//									}
                                studentsModelArrayList.addAll(studentArray)
                            }
                        }
                    }
                }

            }
        })
    }

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
        headerTitle.text = "Account Details"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        action_bar_forward.visibility = View.INVISIBLE
        relSub = findViewById(R.id.relSub)
        scrollView = findViewById(R.id.scrollView)
        relSub.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                context,
                StudentListActivity::class.java
            )
            intent.putExtra("studentlist", studentsModelArrayList)
            startActivity(intent)
        })

        action_bar_back.setOnClickListener { onBackPressed() }

        stnameValue = findViewById(R.id.stnameValue)
        email = findViewById(R.id.email)
        relationship = findViewById(R.id.relationship)
        mobileno = findViewById(R.id.mobileno)
        telephoneNo = findViewById(R.id.telephoneNo)
        Telephone = findViewById(R.id.fromlayout)
        addresslinear = findViewById(R.id.addresslinear)
        relationShip = findViewById(R.id.relationShip)
        mobileLinear = findViewById(R.id.mobileLinear)
        useraddress = findViewById(R.id.useraddress)
        relationShipLinear = findViewById(R.id.relationShipLinear)

    }

}
