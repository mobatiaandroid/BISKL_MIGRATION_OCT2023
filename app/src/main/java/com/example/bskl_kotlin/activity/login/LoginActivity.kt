package com.example.bskl_kotlin.activity.login

import ApiClient
import android.app.Activity
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.home.HomeActivity
import com.example.bskl_kotlin.activity.home.model.AppFeatureModel
import com.example.bskl_kotlin.activity.home.model.SocialMediaUserModel
import com.example.bskl_kotlin.activity.home.model.StudDetailsUsermodel
import com.example.bskl_kotlin.activity.home.model.UserDetailsApiModel
import com.example.bskl_kotlin.activity.home.model.UserDetailsModel
import com.example.bskl_kotlin.activity.login.model.CodeModel
import com.example.bskl_kotlin.activity.login.model.ForgotpasswordApiModel
import com.example.bskl_kotlin.activity.login.model.LoginModel
import com.example.bskl_kotlin.activity.login.model.LoginResponseModel
import com.example.bskl_kotlin.common.InternetCheckClass
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.attendance.PreferenceManagerr
import com.example.bskl_kotlin.fragment.settings.model.TriggerUserModel
import com.example.bskl_kotlin.manager.AppUtils
import com.example.bskl_kotlin.manager.countrypicker.CountryCodePicker
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class LoginActivity:AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var sharedprefs: PreferenceManager

    lateinit var userNameEdtTxt: EditText
    lateinit var passwordEdtTxt: EditText
    lateinit var mNeedpasswordBtn: Button
    private var mLoginBtn: Button? = null

  lateinit var mSignUpBtn: Button
    lateinit var mMailEdtText: EditText
    lateinit var mOtpEditText: EditText
    lateinit var text_phone: EditText
    lateinit var mEmailText: EditText
    lateinit var mAlertphone: TextView
    lateinit var mHelpButton: Button
    var phoneno = ""
    lateinit var listCode: ArrayList<CodeModel>
    lateinit var listCodeString: ArrayList<String>
    lateinit var spinnerCode: CountryCodePicker
    private val code: String? = null
    var dialogSignUp: Dialog? = null
    var forgotDialog: Dialog? = null
    lateinit var mPrgrsRel: RelativeLayout
    lateinit var extras: Bundle
    var fromsplash = false
    lateinit var relLogin: RelativeLayout
    lateinit var topmainLoginRel: RelativeLayout
    lateinit var loginMain: RelativeLayout
    lateinit var homePageLogoImg: ImageView
    lateinit var homePageLogoIm2: ImageView
    var tokenM = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mContext = this
        sharedprefs = PreferenceManager()
        Toast.makeText(mContext, "login", Toast.LENGTH_SHORT).show()
        init()
        onClick()

        if (fromsplash) {

            val fadein = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
            loginMain!!.startAnimation(fadein)
            fadein.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    loginMain!!.setBackgroundResource(R.drawable.loginbg)
                    homePageLogoImg!!.setImageResource(R.drawable.logo)
                    topmainLoginRel!!.setBackgroundColor(mContext.resources.getColor(R.color.white))
                    relLogin!!.visibility = View.VISIBLE

                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        } else {
            relLogin!!.visibility = View.VISIBLE
            loginMain!!.setBackgroundResource(R.drawable.loginbg)
            homePageLogoImg!!.setImageResource(R.drawable.logo)
            topmainLoginRel!!.setBackgroundColor(mContext.resources.getColor(R.color.white))
        }


    }

    private fun init() {
        extras = intent.extras!!
        if (extras != null) {
            fromsplash = extras!!.getBoolean("fromsplash")
        }
        homePageLogoIm2 = findViewById(R.id.homePageLogoIm2)
        homePageLogoImg = findViewById(R.id.homePageLogoImg)
        relLogin = findViewById(R.id.relLogin)
        topmainLoginRel = findViewById(R.id.topmainLoginRel)
        loginMain = findViewById(R.id.loginMain)
        mPrgrsRel = findViewById(R.id.progressDialog)
        userNameEdtTxt = findViewById(R.id.userEditText)
        passwordEdtTxt = findViewById(R.id.passwordEditText)
        mLoginBtn = findViewById(R.id.loginBtn)

        userNameEdtTxt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                userNameEdtTxt?.isFocusable = false
                userNameEdtTxt?.isFocusableInTouchMode = false
                false
            } else {
                userNameEdtTxt?.isFocusable = false
                userNameEdtTxt?.isFocusableInTouchMode = false
                false
            }
        }

        //Keyboard done button click password
        passwordEdtTxt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                passwordEdtTxt.isFocusable = false
                passwordEdtTxt.isFocusableInTouchMode = false
                false
            } else {
                passwordEdtTxt?.isFocusable = false
                passwordEdtTxt?.isFocusableInTouchMode = false
                false
            }
        }
        mHelpButton = findViewById(R.id.helpButton)
        mNeedpasswordBtn = findViewById<Button>(R.id.forgotPasswordButton)
        mLoginBtn = findViewById<Button>(R.id.loginBtn)
        mSignUpBtn = findViewById(R.id.signUpButton)
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            mNeedpasswordBtn.setBackgroundDrawable(
                AppUtils()
                    .getButtonDrawableByScreenCathegory(
                        mContext,
                        R.drawable.forgot_password,
                        R.drawable.forgot_password_press
                    )
            )
            mLoginBtn!!.setBackgroundDrawable(
                AppUtils()
                    .getButtonDrawableByScreenCathegory(
                        mContext,
                        R.drawable.login, R.drawable.login_press
                    )
            )
            mSignUpBtn.setBackgroundDrawable(
                AppUtils()
                    .getButtonDrawableByScreenCathegory(
                        mContext,
                        R.drawable.parent_signup, R.drawable.parent_signup
                    )
            )
            mHelpButton.setBackgroundDrawable(
                AppUtils()
                    .getButtonDrawableByScreenCathegory(
                        mContext,
                        R.drawable.email_help, R.drawable.email_help_press
                    )
            )
        } else {
            mNeedpasswordBtn.setBackground(
                AppUtils()
                    .getButtonDrawableByScreenCathegory(
                        mContext,
                        R.drawable.forgot_password,
                        R.drawable.forgot_password_press
                    )
            )
            mLoginBtn!!.setBackground(
                AppUtils()
                    .getButtonDrawableByScreenCathegory(
                        mContext,
                        R.drawable.login, R.drawable.login_press
                    )
            )
            mSignUpBtn.background = AppUtils()
                .getButtonDrawableByScreenCathegory(
                    mContext,
                    R.drawable.parent_signup, R.drawable.parent_signup
                )
            mHelpButton.background = AppUtils()
                .getButtonDrawableByScreenCathegory(
                    mContext,
                    R.drawable.email_help, R.drawable.email_help_press
                )
        }

    }

    private fun onClick() {
        mLoginBtn!!.setOnClickListener {
            if (userNameEdtTxt.text.toString().trim().equals("")) {
               // InternetCheckClass.showSuccessAlert(mContext, "Please enter Email.", "Alert")
            } else {
                var emailPattern =
                    InternetCheckClass.isEmailValid(userNameEdtTxt.text.toString().trim())
                if (!emailPattern) {
                    InternetCheckClass.showSuccessAlert(
                        mContext,
                        "Please enter a vaild Email.",
                        "Alert"
                    )
                } else {
                    if (passwordEdtTxt.text.toString().trim().equals("")) {
                        InternetCheckClass.showSuccessAlert(
                            mContext,
                            "Please enter Password.",
                            "Alert"
                        )
                    } else {
                        var internetCheck = InternetCheckClass.isInternetAvailable(mContext)
                        if (internetCheck) {
                            val imm =
                                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                            imm?.hideSoftInputFromWindow(userNameEdtTxt.windowToken, 0)
                            val immq =
                                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                            immq?.hideSoftInputFromWindow(passwordEdtTxt.windowToken, 0)
                            var emailTxt = userNameEdtTxt.text.toString().trim()
                            var passwordTxt = passwordEdtTxt.text.toString().trim()
                           // callLoginApi(emailTxt,passwordTxt)
                            FirebaseApp.initializeApp(mContext)
                            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                                if (task.isComplete) {
                                    val token: String = task.getResult().toString()
                                    tokenM = token
                                    callLoginApi(emailTxt,passwordTxt)
                                }
                            }


                        } else {
                           InternetCheckClass.showSuccessInternetAlert(mContext)
                        }
                    }
                }
            }
        }

mSignUpBtn.setOnClickListener {

    // sign up
    AppUtils().hideKeyboard(mContext, passwordEdtTxt)
    val intent = Intent(
        this@LoginActivity,
        SignUpActivityy::class.java
    )
    startActivity(intent)
}
        mHelpButton.setOnClickListener{
            val emailIntent = Intent(
                Intent.ACTION_SEND_MULTIPLE
            )
            val deliveryAddress = arrayOf("portal@britishschool.edu.my")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, deliveryAddress)
            emailIntent.type = "text/plain"
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val pm: PackageManager = mHelpButton.getContext().getPackageManager()
            val activityList = pm.queryIntentActivities(
                emailIntent, 0
            )
            println("packge size" + activityList.size)
            for (app in activityList) {
                println("packge name" + app.activityInfo.name)
                if (app.activityInfo.name.contains("com.google.android.gm")) {
                    val activity = app.activityInfo
                    val name = ComponentName(
                        activity.applicationInfo.packageName, activity.name
                    )
                    emailIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                    emailIntent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                            or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                    emailIntent.component = name
                    mHelpButton.getContext().startActivity(emailIntent)
                    break
                }
            }
        }
        mNeedpasswordBtn.setOnClickListener {

            // need password button
            AppUtils().hideKeyboard(mContext, passwordEdtTxt)
            if (AppUtils().checkInternet(mContext)) {
                //showForgotpasswordAlertDialog();
                forgotPasswordApiCall()
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

    private fun forgotPasswordApiCall() {
        forgotDialog = Dialog(mContext, R.style.NewDialog)
        forgotDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        forgotDialog!!.setContentView(R.layout.dialog_forgot_password)
        forgotDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        forgotDialog!!.setCancelable(true)
        // set the custom dialog components - edit text, button
        // set the custom dialog components - edit text, button
        val sdk = Build.VERSION.SDK_INT
        mMailEdtText = forgotDialog!!.findViewById(R.id.text_dialog)

        val alertHead = forgotDialog!!.findViewById<TextView>(R.id.alertHead)
        val dialogSubmitButton = forgotDialog!!
            .findViewById<Button>(R.id.btn_signup)

        // if button is clicked, close the custom dialog

        // if button is clicked, close the custom dialog
        dialogSubmitButton.setOnClickListener {
            AppUtils().hideKeyboard(mContext, mMailEdtText)
            if (!mMailEdtText.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                if (AppUtils().isValidEmail(
                        mMailEdtText.text
                            .toString()
                    )
                ) {
                    // sign up api call
                    if (AppUtils().isNetworkConnected(mContext)) {
                        sendForGotpassWord()
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
                        getString(R.string.enter_valid_email),
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                }
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    getString(R.string.alert_heading),
                    getString(R.string.enter_email),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        }

        val dialogMayBelaterutton = forgotDialog!!.findViewById<Button>(R.id.btn_maybelater)
        dialogMayBelaterutton.setOnClickListener {
            AppUtils().hideKeyboard(mContext, mMailEdtText)
            forgotDialog!!.dismiss()
        }
        forgotDialog!!.show()
    }

    private fun sendForGotpassWord() {
        val androidId = Settings.Secure.getString(
            mContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        var forgotmodel=
            ForgotpasswordApiModel(  mMailEdtText.getText().toString(),
                tokenM, "2",androidId)

        val call: Call<TriggerUserModel> = ApiClient.getClient.forgotpassword(
            forgotmodel,"Bearer "+ PreferenceManager().getaccesstoken(mContext).toString()
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
                        forgotDialog!!.dismiss()

                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            "Success",
                            getString(R.string.frgot_success_alert),
                            R.drawable.tick,
                            R.drawable.round
                        )


                    }

                }
            }
        })
    }

    private fun callLoginApi(email:String,password:String) {
        var androidID = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        val loginbody = LoginModel(PreferenceManager().getaccesstoken(mContext).toString(),
            email,password,"7934","2",androidID)
        Log.e("token",PreferenceManager().getaccesstoken(mContext).toString())
        val call: Call<LoginResponseModel> = ApiClient.getClient.login(email,password,"7934","2",androidID)

        call.enqueue(object : Callback<LoginResponseModel> {
            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {

                val responsedata = response.body()
                var userEmailFull=userNameEdtTxt.text.toString()
                var userName=responsedata!!.response.responseArray.name
                var userId=responsedata!!.response.responseArray.userid
                var userNumber=responsedata!!.response.responseArray.mobileno
                var token=responsedata!!.response.responseArray.token
                PreferenceManager().setUserId(mContext, userId)
                PreferenceManagerr.setUserId(mContext,userId)
                PreferenceManager().setUserName(mContext, userName)
                PreferenceManager().setUserNumber(mContext, userNumber)
                PreferenceManager().setUserEmail(mContext, userEmailFull)
                PreferenceManager().setaccesstoken(mContext, token)
                PreferenceManagerr.setAccessToken(mContext, token)
                PreferenceManager().setLoggedInStatus(mContext, "1")
                Log.e("Response Signup", responsedata.toString())
                setUserDetails()
               // showSuccessAlert("Successfully Logged In","Success")
            }
        })

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
                        // PreferenceManager().setUserId(mContext, respObj.optString(JTAG_USER_ID));
                        //  String userId=respObj.optString(JTAG_USER_ID);
                        PreferenceManager().setPhoneNo(mContext, responsedata!!.response.responseArray.mobileno)
                        PreferenceManager().setFullName(mContext,responsedata!!.response.responseArray.name)
                        PreferenceManager().setEmailPush(mContext, responsedata!!.response.responseArray.emailpush)
                        PreferenceManager().setCalenderPush(
                            mContext,
                            responsedata!!.response.responseArray.calenderpush
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
                        Log.e("reportmaillogin",PreferenceManager().getReportMailMerge(mContext).toString())
                        PreferenceManager().setCorrespondenceMailMerge(
                            mContext,
                            responsedata!!.response.responseArray.correspondencemailmerge
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
                        val studentDetail: ArrayList<StudDetailsUsermodel> = responsedata!!.response.responseArray.studentdetails
                        val mDatamodel: ArrayList<StudDetailsUsermodel> =
                           ArrayList<StudDetailsUsermodel>()


                        if (studentDetail.size > 0) {
                            for (i in 0 until studentDetail.size) {
                                val dataObject = studentDetail[i]
                                val xmodel = StudDetailsUsermodel(dataObject.id,dataObject.alumi,"")
                               /* xmodel.setId(dataObject.optString("id"))
                                xmodel.setAlumi(dataObject.optString("alumi"))*/
                                mDatamodel.add(xmodel)
                            }
                        }
                        var alumini = 0
                        for (x in mDatamodel.indices) {
                            if (mDatamodel[x].alumi.equals("0")) {
                                alumini = 0
                                break
                            } else {
                                alumini = 1
                            }
                        }
                        PreferenceManager().setIsVisible(mContext, alumini.toString())
                        val socialmediaObj: SocialMediaUserModel = responsedata!!.response.socialmedia
                        PreferenceManager().setFbkey(mContext, socialmediaObj.fbkey)
                        PreferenceManager().setInstaKey(mContext, socialmediaObj.inkey)
                        PreferenceManager().setYouKey(
                            mContext,
                            socialmediaObj.youtubekey
                        )


                      /*  val analytics: GoogleAnalytics = GoogleAnalytics.getInstance(mContext)
                        val tracker: Tracker
                        //  tracker = analytics.newTracker("UA-128359311-1");   //LiveAccount
                        //  tracker = analytics.newTracker("UA-128359311-1");   //LiveAccount
                        tracker = analytics.newTracker("UA-128379282-1") //New LiveAccount

//                            tracker = analytics.newTracker("UA-126664424-1");//testaccount
                        //                            tracker = analytics.newTracker("UA-126664424-1");//testaccount
                        tracker.setScreenName("Login Page")
                        tracker.set("&uid", PreferenceManager().getUserId(mContext))
                        tracker.set("&cid", PreferenceManager().getUserId(mContext))
                        tracker.setClientId(PreferenceManager().getUserId(mContext))
//                            tracker.send(new HitBuilders.ScreenViewBuilder().build());

                        //                            tracker.send(new HitBuilders.ScreenViewBuilder().build());
                        tracker.send(
                            EventBuilder().setCategory(PreferenceManager().getUserEmail(mContext)) // category i.e. Player Buttons
                                .setAction("Login." + " " + Calendar.getInstance().time) // action i.e.  Play
                                .setLabel("Login.")
                                .set("&uid", PreferenceManager().getUserId(mContext))
                                .set("&cid", PreferenceManager().getUserId(mContext)).build()
                        ) // label i.e.  any meta-data);

                        analytics.reportActivityStart(this@LoginActivity)*/
     showSuccessAlert("Successfully Logged In","Success")


                    }
                }

            }
        })
    }
    fun showSuccessAlert(message : String,msgHead : String)
    {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.tick)
        btn_Ok.setOnClickListener()
        {
            Log.e("loginhome","loginhome")
            val loginIntent = Intent(
                mContext,
                HomeActivity::class.java
            )
            loginIntent.putExtra("fromsplash", true)
            startActivity(loginIntent)
/*startActivity(Intent(mContext, HomeActivity::class.java))
            loginIntent.putExtra("fromsplash", true)

            dialog.dismiss()*/
            finish()

        }
        dialog.show()
    }
}

