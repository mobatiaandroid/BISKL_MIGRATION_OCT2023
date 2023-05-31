package com.example.bskl_kotlin.activity.login

import ApiClient
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bskl_kotlin.activity.home.HomeActivity
import com.example.bskl_kotlin.activity.login.model.LoginModel
import com.example.bskl_kotlin.activity.login.model.LoginResponseModel
import com.example.bskl_kotlin.common.InternetCheckClass
import com.example.bskl_kotlin.common.PreferenceManager
import com.mobatia.bskl.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity:AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var sharedprefs: PreferenceManager

    lateinit var userNameEdtTxt: EditText
    lateinit var passwordEdtTxt: EditText
    lateinit var loginBtn: Button
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
        loginBtn = findViewById(R.id.loginBtn)

       /* userNameEdtTxt.setOnEditorActionListener { v, actionId, event ->
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
        }*/

    }

    private fun onClick() {
        loginBtn.setOnClickListener {
            if (userNameEdtTxt.text.toString().trim().equals("")) {
               // InternetCheckClass.showSuccessAlert(mContext, "Please enter Email.", "Alert")
            } else {
                var emailPattern =
                    InternetCheckClass.isEmailValid(userNameEdtTxt.text.toString().trim())
                if (!emailPattern) {
                    /*InternetCheckClass.showSuccessAlert(
                        mContext,
                        "Please enter a vaild Email.",
                        "Alert"
                    )*/
                } else {
                    if (passwordEdtTxt.text.toString().trim().equals("")) {
                       /* InternetCheckClass.showSuccessAlert(
                            mContext,
                            "Please enter Password.",
                            "Alert"
                        )*/
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
                            callLoginApi(emailTxt,passwordTxt)
                           /* FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                                if (task.isComplete) {
                                    val token: String = task.getResult().toString()
                                    tokenM = token
                                    callLoginApi()
                                    //callLoginApi(emailTxt,passwordTxt,deviceId)

                                }
                            }*/

                        } else {
                           // InternetCheckClass.showSuccessInternetAlert(mContext)
                        }
                    }
                }
            }
        }
    }

    private fun callLoginApi(email:String,password:String) {
        var androidID = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        val loginbody = LoginModel(PreferenceManager().getaccesstoken(mContext).toString(),
            email,password,"7934","2",androidID)
        val call: Call<LoginResponseModel> = ApiClient.getClient.login(PreferenceManager().getaccesstoken(mContext).toString(),
            email,password,"7934","2",androidID)

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
                PreferenceManager().setUserId(mContext, userId)
                PreferenceManager().setUserName(mContext, userName)
                PreferenceManager().setUserNumber(mContext, userNumber)
                PreferenceManager().setUserEmail(mContext, userEmailFull)
                PreferenceManager().setLoggedInStatus(mContext, "1")
                Log.e("Response Signup", responsedata.toString())
                showSuccessAlert("Successfully Logged In","Success")
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
            startActivity(Intent(mContext, HomeActivity::class.java))
            dialog.dismiss()
            finish()

        }
        dialog.show()
    }
}

