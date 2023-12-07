package com.example.bskl_kotlin.activity.login

import ApiClient
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.OTP_Reciever.AppSMSBroadcastReceiver
import com.example.bskl_kotlin.activity.login.model.NewphonenumberApiModel
import com.example.bskl_kotlin.activity.login.model.OtpverificationApiModel
import com.example.bskl_kotlin.activity.login.model.SignUPApiModel
import com.example.bskl_kotlin.activity.login.model.SignUpModel
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.settings.model.TriggerUserModel
import com.example.bskl_kotlin.manager.AppUtils
import com.example.bskl_kotlin.manager.countrypicker.CountryCodePicker
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivityy:AppCompatActivity(), View.OnClickListener {
    lateinit var mContext: Context
    var mMailEdtText: EditText? = null
    var verificationCodeTxt: EditText? = null
    lateinit var emailNext: Button
    var emailCancel: Button? = null
    var phoneno = ""
    var users_id = ""
    var enterEmail: LinearLayout? = null
    var verificationCode: LinearLayout? = null
    var successfullLinear: LinearLayout? = null
    var changeNoLinear: LinearLayout? = null
    var phnoNoSuccess: LinearLayout? = null
    var button1: Button? = null
    var button2: Button? = null
    var button3: Button? = null
    var btn_change: Button? = null
    var btn_maybelater: Button? = null
    var loginClick: Button? = null
    var btn_close: Button? = null
    var verificationSubmit: Button? = null
    var btnLinear: LinearLayout? = null
    var mAlertphone: TextView? = null
    var successfullTxt: TextView? = null
    var changePhoneNo: TextView? = null
    var text_phone: TextView? = null
    var verifyTxt: TextView? = null
    var mEmailText: TextView? = null
    var verifiedTxt: TextView? = null
    var spinnerCode: CountryCodePicker? = null
    var displayPage = 1
    private var code: String? = null
    var action_bar_forward: ImageView? = null
    var action_bar_back: ImageView? = null
    var logoClickImgView: ImageView? = null
    var headerTitle: TextView? = null

    private var intentFilter: IntentFilter? = null
    private var appSMSBroadcastReceiver: AppSMSBroadcastReceiver? = null
    var tokenM = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_email_signup_layout)

        mContext = this
initUi()
    }

    private fun initUi() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_view_home)
        supportActionBar!!.elevation = 0f

        val view = supportActionBar!!.customView
        val toolbar = view.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)
        headerTitle = view.findViewById(R.id.headerTitle)
        logoClickImgView = view.findViewById(R.id.logoClickImgView)
        action_bar_forward = view.findViewById(R.id.action_bar_forward)
        action_bar_back = view.findViewById(R.id.action_bar_back)
        action_bar_back!!.setImageResource(R.drawable.back_new)
        headerTitle!!.setText("Sign Up")
        headerTitle!!.setVisibility(View.VISIBLE)
        logoClickImgView!!.setVisibility(View.INVISIBLE)
        action_bar_forward!!.setVisibility(View.INVISIBLE)
        action_bar_back!!.setOnClickListener(View.OnClickListener { onBackPressed() })


        mMailEdtText = findViewById(R.id.emailEditTxt)
        verificationCodeTxt = findViewById(R.id.verificationCodeTxt)
        mAlertphone = findViewById(R.id.mAlertPhone)
        successfullTxt = findViewById(R.id.successfullTxt)
        text_phone = findViewById(R.id.text_phone)
        changePhoneNo = findViewById(R.id.changePhoneNo)
        mEmailText = findViewById(R.id.mEmailText)
        emailNext = findViewById(R.id.emailNext)
        emailCancel = findViewById(R.id.emailCancel)
        btnLinear = findViewById(R.id.btnLinear)
        enterEmail = findViewById(R.id.enterEmail)
        verificationCode = findViewById(R.id.verificationCode)
        verifiedTxt = findViewById(R.id.verifiedTxt)
        verifyTxt = findViewById(R.id.verifyTxt)
        successfullLinear = findViewById(R.id.successfullLinear)
        changeNoLinear = findViewById(R.id.changeNoLinear)
        phnoNoSuccess = findViewById(R.id.phnoNoSuccess)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        btn_close = findViewById(R.id.btn_close)
        loginClick = findViewById(R.id.loginClick)
        btn_change = findViewById(R.id.btn_change)
        btn_maybelater = findViewById(R.id.btn_maybelater)
        spinnerCode = findViewById(R.id.spinnerCode)
        verificationSubmit = findViewById(R.id.verificationSubmit)
        emailNext!!.setOnClickListener(this)
        emailCancel!!.setOnClickListener(this)
        loginClick!!.setOnClickListener(this)
        changePhoneNo!!.setOnClickListener(this)
        btn_close!!.setOnClickListener(this)
        emailCancel!!.visibility = View.GONE
        smsListener()
        InitiateOTPreciver()

        verificationSubmit!!.setOnClickListener(View.OnClickListener {
            AppUtils().hideKeyboard(mContext, verificationCodeTxt)
            if (!verificationCodeTxt!!.getText().toString().trim { it <= ' ' }
                    .equals("", ignoreCase = true)) {
                if (AppUtils
                        ().isNetworkConnected(mContext)) {
                    sendOtpRequest()
                } else {
                    AppUtils().showDialogAlertDismiss(
                        mContext as Activity,
                        "Network Error",
                        getString(R.string.no_internet),
                        R.drawable.nonetworkicon,
                        R.drawable.roundred
                    )
                }
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    getString(R.string.alert_heading),
                    getString(R.string.enter_otp),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        })
    }

    private fun sendOtpRequest() {
       /* FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isComplete) {
                val token = task.result
                tokenM = token
            }
        }*/

        val androidId = Settings.Secure.getString(
            mContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        var otpmodel=OtpverificationApiModel(verificationCodeTxt!!.getText().toString(),
            mMailEdtText!!.getText().toString(), tokenM, "2",users_id,androidId)
        val call: Call<TriggerUserModel> = ApiClient.getClient.otpverification(
            otpmodel,"Bearer "+ PreferenceManager().getaccesstoken(mContext).toString()
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
                        println("otp working otp")
                        successfullLinear!!.setVisibility(
                            View.VISIBLE
                        )
                        displayPage = 3

                        enterEmail!!.setVisibility(
                            View.GONE
                        )
                       verificationCode!!.setVisibility(
                            View.GONE
                        )
                        button1!!.setBackgroundResource(
                            R.drawable.shape_circle_signup_border
                        )
                        button2!!.setBackgroundResource(
                            R.drawable.shape_circle_signup_border
                        )
                       button3!!.setBackgroundResource(
                            R.drawable.shape_circle_signup
                        )
                        successfullTxt!!.visibility = View.VISIBLE
                        action_bar_back!!.visibility = View.INVISIBLE
                        successfullTxt!!.text =
                            getString(R.string.sign_up_successfull) + " " + "(" + mMailEdtText!!.text.toString() + ")" + " " + getString(
                                R.string.sign_up_successfull_last
                            )

                    }

                }
            }
        })

    }

    private fun InitiateOTPreciver() {
        intentFilter = IntentFilter("com.google.android.gms.auth.api.phone.SMS_RETRIEVED")
        appSMSBroadcastReceiver = AppSMSBroadcastReceiver()
        appSMSBroadcastReceiver!!.setOnSmsReceiveListener(object : AppSMSBroadcastReceiver.OnSmsReceiveListener {
            override fun onReceive(code: String) {
//                Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                var SMS = code
                //                SMS = SMS.replace("<#> Your BSKL code is : ","").split(". ")[0];
                if (SMS.contains("RM0 BSKL: <#> Your Mobile verification code for Parent Signup is:")) {
                    SMS = SMS.replace(
                        "RM0 BSKL: <#> Your Mobile verification code for Parent Signup is: ",
                        ""
                    ).split(". ".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()[0]
                } else if (SMS.contains("RM0.00 BSKL: <#> Your Mobile verification code for Parent Signup is:")) {
                    SMS = SMS.replace(
                        "RM0.00 BSKL: <#> Your Mobile verification code for Parent Signup is: ",
                        ""
                    ).split(". ".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()[0]
                } else if (SMS.contains("BSKL: <#> Your Mobile verification code for Parent Signup is:")) {
                    SMS =
                        SMS.replace(
                            "BSKL: <#> Your Mobile verification code for Parent Signup is: ",
                            ""
                        ).split(". ".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[0]
                }

                //   SMS = SMS.replace("RM0 <#> Your Mobile verification code for BSKL Parent Signup is: ","").split(". ")[0];
                verificationCodeTxt!!.setText(SMS)
                verifyTxt!!.text = "Verification code successfully received"
                verifiedTxt!!.visibility = View.GONE
                mAlertphone!!.visibility = View.GONE
                changePhoneNo!!.visibility = View.GONE
            }
        })
    }

    private fun smsListener() {
        val client: SmsRetrieverClient = SmsRetriever.getClient(this)
        client.startSmsRetriever()
    }

    override fun onClick(v: View?) {
        if (v === emailNext){
            AppUtils().hideKeyboard(mContext, mMailEdtText)
            if (!mMailEdtText!!.getText().toString().trim({it <= ' '}).equals("", ignoreCase = true)){
                if (AppUtils().isValidEmail(mMailEdtText!!.getText()
                        .toString())){
                    // sign up api call
                    if (AppUtils().isNetworkConnected(mContext)){
                        sendSignUpRequest()
                    } else {
                        AppUtils().showDialogAlertDismiss(mContext as Activity?, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred)
                    }
                } else {
                    AppUtils().showDialogAlertDismiss(mContext as Activity?, getString(R.string.alert_heading), getString(R.string.enter_valid_email), R.drawable.exclamationicon, R.drawable.round)
                }
            } else {
                AppUtils().showDialogAlertDismiss(mContext as Activity?, getString(R.string.alert_heading), getString(R.string.enter_email), R.drawable.exclamationicon, R.drawable.round)
            }
        } else if (v === emailCancel){
            finish()
        } else if (v === loginClick){
            finish()
        } else if (v === changePhoneNo){
            displayPage = 4
            successfullLinear!!.setVisibility(android.view.View.GONE)
            enterEmail!!.setVisibility(android.view.View.GONE)
            enterEmail!!.setVisibility(android.view.View.GONE)
            verificationCode!!.setVisibility(android.view.View.GONE)
           changeNoLinear!!.setVisibility(android.view.View.VISIBLE)
            button1!!.setBackgroundResource(R.drawable.shape_circle_signup_border)
            button2!!.setBackgroundResource(R.drawable.shape_circle_signup)
            mEmailText!!.setText(mMailEdtText!!.getText().toString())
            text_phone!!.getText().toString()
            spinnerCode!!.resetToDefaultCountry()
            code = spinnerCode!!.getDefaultCountryCodeWithPlus()
            spinnerCode!!.setOnCountryChangeListener(object : CountryCodePicker.OnCountryChangeListener{
                override fun onCountrySelected(){
                    code = spinnerCode!!.getSelectedCountryCodeWithPlus()
                }
            })
            btn_change!!.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: android.view.View){
                    AppUtils().hideKeyboard(mContext, verificationCodeTxt)
                    if (!text_phone!!.getText().toString().trim({it <= ' '}).equals("", ignoreCase = true)){
                        if (AppUtils().isNetworkConnected(mContext)){
                            sendPhoneNoRequest()
                        } else {
                            AppUtils().showDialogAlertDismiss(mContext as android.app.Activity?, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred)
                        }
                    } else {
                        AppUtils().showDialogAlertDismiss(mContext as android.app.Activity?, getString(R.string.alert_heading), getString(R.string.enter_phone_no), R.drawable.exclamationicon, R.drawable.round)
                    }
                }
            })
            btn_maybelater!!.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: android.view.View){
                    showDialogAlertEmailMaybelater(mContext as Activity?, "Alert", "Do you want to go back?", R.drawable.questionmark_icon, R.drawable.round)
                }
            })
        } else if (v === btn_close){
            AppUtils().hideKeyBoard(mContext)
            finish()
        }
    }
    fun showDialogAlertEmailMaybelater(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_layout)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            displayPage = 2
            enterEmail!!.setVisibility(View.GONE)
            verificationCode!!.setVisibility(View.VISIBLE)
            successfullLinear!!.setVisibility(View.GONE)
            changeNoLinear!!.setVisibility(View.GONE)
            phnoNoSuccess!!.setVisibility(View.GONE)
            button1!!.setBackgroundResource(R.drawable.shape_circle_signup_border)
            button2!!.setBackgroundResource(R.drawable.shape_circle_signup)
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById<Button>(R.id.btn_Cancel)
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    private fun sendPhoneNoRequest() {
        var phonenumbermodel=
            NewphonenumberApiModel(  mEmailText!!.getText().toString(),
                code!!, text_phone!!.getText().toString(),users_id)

        val call: Call<TriggerUserModel> = ApiClient.getClient.newphonenumber(
            phonenumbermodel,"Bearer "+ PreferenceManager().getaccesstoken(mContext).toString()
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
                        displayPage = 5
                       btnLinear!!.visibility = View.GONE
                        enterEmail!!.visibility = View.GONE
                        verificationCode!!.visibility = View.GONE
                        successfullLinear!!.visibility = View.GONE
                        changeNoLinear!!.visibility = View.GONE
                        phnoNoSuccess!!.visibility = View.VISIBLE
                        action_bar_back!!.visibility = View.INVISIBLE

                    }

                }
            }
        })
    }

    private fun sendSignUpRequest() {
        var signupmodel=
            SignUPApiModel( mMailEdtText!!.getText().toString(),
                tokenM, "2", PreferenceManager().getPhoneNo(mContext).toString(),
                PreferenceManager().getOTPSignature(mContext).toString())

        val call: Call<SignUpModel> = ApiClient.getClient.signuppassword(
            signupmodel,"Bearer "+ PreferenceManager().getaccesstoken(mContext).toString()
        )

        call.enqueue(object : Callback<SignUpModel> {
            override fun onFailure(call: Call<SignUpModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<SignUpModel>,
                response: Response<SignUpModel>
            ) {

                val responsedata = response.body()

                Log.e("Response Signup", responsedata.toString())

                if (response.body()!!.responsecode.equals("200")) {
                    if (response.body()!!.response.statuscode.equals("303")) {
                        phoneno = response.body()!!.response.mobile_no
                        users_id = response.body()!!.response.users_id

                        AppUtils().hideKeyboard(mContext, mMailEdtText)
                        if (AppUtils().checkInternet(mContext)) {
                            println("Email Next")
                            enterEmail!!.visibility = View.GONE
                            verificationCode!!.visibility = View.VISIBLE
                            verifiedTxt!!.visibility = View.VISIBLE
                            displayPage = 2
                            button1!!.setBackgroundResource(R.drawable.shape_circle_signup_border)
                            button2!!.setBackgroundResource(R.drawable.shape_circle_signup)
                            verifiedTxt!!.visibility = View.VISIBLE
                            mAlertphone!!.visibility = View.VISIBLE
                            changePhoneNo!!.visibility = View.VISIBLE
                            mAlertphone!!.text =
                                " An SMS with a verification code has been sent to your registered mobile number \n$phoneno"
                            //                                new OTP_Receiver().setEditText(verificationCodeTxt);
//                                InitiateOTPreciver();
                            verifyTxt!!.visibility = View.VISIBLE
                            verifyTxt!!.text = "The verification code should appear below"
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

                }
            }
        })
    }

    fun showDialogAlertDismiss(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
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
            emailCancel!!.visibility = View.VISIBLE
            dialog.dismiss()
        }
        //		Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_Cancel);
//		dialogButtonCancel.setVisibility(View.GONE);
//		dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
        dialog.show()
    }
    override fun onPointerCaptureChanged(hasCapture: Boolean) {}
    override fun onBackPressed() {
        if (displayPage == 2) {
            showDialogAlertEmailSuccess(
                mContext as Activity,
                "Alert",
                "Do you want to go back?",
                R.drawable.questionmark_icon,
                R.drawable.round
            )
            verificationCodeTxt!!.setText("")
        } else if (displayPage == 4) {
            showDialogAlertEmailMaybelater(
                mContext as Activity,
                "Alert",
                "Do you want to go back?",
                R.drawable.questionmark_icon,
                R.drawable.round
            )
        } else {
            AppUtils().hideKeyBoard(mContext)
            finish()
        }
    }

    fun showDialogAlertEmailSuccess(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_layout)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            displayPage = 1
            enterEmail!!.setVisibility(View.VISIBLE)
            verificationCode!!.setVisibility(View.GONE)
            successfullLinear!!.setVisibility(View.GONE)
            changeNoLinear!!.setVisibility(View.GONE)
            phnoNoSuccess!!.setVisibility(View.GONE)
            button2!!.setBackgroundResource(R.drawable.shape_circle_signup_border)
            button1!!.setBackgroundResource(R.drawable.shape_circle_signup)
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById<Button>(R.id.btn_Cancel)
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}


