package com.example.bskl_kotlin.activity.splash

import ApiClient
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.home.HomeActivity
import com.example.bskl_kotlin.activity.home.model.DeviceRegisterApiModel
import com.example.bskl_kotlin.activity.home.model.DeviceregisterModel
import com.example.bskl_kotlin.activity.login.LoginActivity

import com.example.bskl_kotlin.activity.tutorial.TutorialActivity
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.attendance.PreferenceManagerr
import com.example.bskl_kotlin.manager.AppUtils
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    lateinit var mContext: Context

    lateinit var sharedprefs: PreferenceManager
    private val SPLASH_TIME_OUT:Long = 3000
    lateinit var videoHolder: VideoView
    private val APP_API = 17
    lateinit var reltativeTop: RelativeLayout
    lateinit var reltativeTopIn: ImageView
    var FirebaseID: String=""
    var tokenM = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mContext=this
        sharedprefs = PreferenceManager()

        reltativeTop = findViewById(R.id.reltativeTop)
        reltativeTopIn = findViewById(R.id.reltativeTopIn)
        PreferenceManager().setDataCollection(mContext, "0")
        PreferenceManager().setSuspendTrigger(mContext, "0")
       /* if (AppUtils().isNetworkConnected(mContext)) {
            FirebaseApp.initializeApp(mContext)
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isComplete) {
                    val token = task.result
                    tokenM = token
                }
            }
            if (tokenM == null) {
                val i = Intent(this@SplashActivity, SplashActivity::class.java)
                startActivity(i)
                finish()
            } else {
                FirebaseID = tokenM
            }

            if (PreferenceManager().getIsFirstLaunch(mContext) && PreferenceManager().getUserId(mContext)
                    .equals("")
            ) {

                reltativeTop.setBackgroundResource(R.color.white)
                reltativeTopIn.setImageResource(R.drawable.logo)
            } else {
                reltativeTop.setBackgroundResource(R.color.black)
                reltativeTopIn.setImageResource(R.color.black)
            }

            postInitParams(mContext)

        } else {
            goToNextView()
        }*/
        if (AppUtils().isNetworkConnected(mContext)) {
        if (tokenM == null) {
            val i = Intent(mContext, SplashActivity::class.java)
            startActivity(i)
            finish()
        } else {
            FirebaseID = tokenM
        }

        if (PreferenceManager().getIsFirstLaunch(mContext) && PreferenceManager().getUserId(mContext)
                .equals("")
        ) {

            reltativeTop.setBackgroundResource(R.color.white)
            reltativeTopIn.setImageResource(R.drawable.logo)
        } else {
            reltativeTop.setBackgroundResource(R.color.black)
            reltativeTopIn.setImageResource(R.color.black)
        }

        postInitParams(mContext)

    } else {
        goToNextView()
    }
    }

    private fun postInitParams(mContext: Context) {
        if (PreferenceManager().getUserId(mContext).equals("")) {
            goToNextView()
        } else {
            deviceRegistration(mContext)
        }
    }

    private fun deviceRegistration(mContext: Context) {
        val androidId = Settings.Secure.getString(
            mContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        var devicereg= DeviceRegisterApiModel("tokenM", "2",androidId,
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

                        goToNextView()

                    }else{
                        goToNextView()
                    }
                }

            }
        })
       // goToNextView()
    }

    private fun goToNextView() {
        Handler().postDelayed({
            if (PreferenceManager().getIsFirstLaunch(mContext) && PreferenceManager().getUserId(mContext)
                    .equals("")
            ) {
                val tutorialIntent = Intent(
                    mContext,
                    TutorialActivity::class.java
                )
                tutorialIntent.putExtra("type", 1)
                startActivity(tutorialIntent)
                finish()
            } else if (PreferenceManager().getUserId(mContext).equals("")) {
                val loginIntent = Intent(
                    mContext,
                    LoginActivity::class.java
                )
                loginIntent.putExtra("fromsplash", true)
                startActivity(loginIntent)
                overridePendingTransition(0, 0)
                finish()
            } else {
                //                    Intent loginIntent = new Intent(mContext,
                //                            HomeListActivity.class);
                if (PreferenceManager().getLoggedInStatus(mContext).equals("")) {
                    PreferenceManager().setUserId(mContext, "")
                    PreferenceManagerr.setUserId(mContext, "")
                  /*  val dummyOwn: ArrayList<OwnContactModel> = ArrayList<OwnContactModel>()
                    val dummyKin: ArrayList<KinModel> = ArrayList<KinModel>()
                    val dummyInsurance: ArrayList<InsuranceDetailModel> =
                        ArrayList<InsuranceDetailModel>()
                    val dummyPassport: ArrayList<PassportDetailModel> =
                        ArrayList<PassportDetailModel>()
                    val dummyStudent: ArrayList<StudentModelNew> = ArrayList<StudentModelNew>()
                    PreferenceManager().saveOwnDetailArrayList(dummyOwn, "OwnContact", mContext)
                    PreferenceManager().saveKinDetailsArrayListShow(dummyKin, mContext)
                    PreferenceManager().saveKinDetailsArrayList(dummyKin, mContext)
                    PreferenceManager().saveInsuranceDetailArrayList(dummyInsurance, mContext)
                    PreferenceManager().savePassportDetailArrayList(dummyPassport, mContext)
                    PreferenceManager().saveInsuranceStudentList(dummyStudent, mContext)*/
                    val loginIntent = Intent(
                        mContext,
                        LoginActivity::class.java
                    )
                    loginIntent.putExtra("fromsplash", true)
                    startActivity(loginIntent)
                    overridePendingTransition(0, 0)
                    finish()
                } else {
                    Log.e("splahhome","splashhome")
                    val loginIntent = Intent(
                        mContext,
                        HomeActivity::class.java
                    )
                    loginIntent.putExtra("fromsplash", true)
                    startActivity(loginIntent)
                    overridePendingTransition(0, 0)
                    finish()
                }
            }
        }, 500)
    }


    private fun tokenApi(){
        val tokenbody = TokenModel("password","testclient","testpass",
            "admin@mobatia.com","admin123")
        val call: Call<TokenResponseModel> = ApiClient.getClient.token("password","testclient","testpass",
            "admin@mobatia.com","admin123")

        call.enqueue(object : Callback<TokenResponseModel> {
            override fun onFailure(call: Call<TokenResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<TokenResponseModel>,
                response: Response<TokenResponseModel>
            ) {
                val responsedata = response.body()
Log.e("tkn", responsedata!!.access_token.toString())
                PreferenceManager().setaccesstoken(mContext,responsedata!!.access_token.toString())
                PreferenceManagerr.setAccessToken(mContext,responsedata!!.access_token.toString())


            }
        })
    }
    private fun isLowerDeviceApi(): Boolean {
        return try {
            val i = Integer.valueOf(Build.VERSION.SDK)
            //			LogUtils.logError("Device Api", BuildConfig.VERSION_NAME + i.intValue());
            if (i <= APP_API) {
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }
    private fun jump() {
        if (!isFinishing) {
            if (AppUtils().checkInternet(mContext)) {
//                AppUtils.postInitParams(mContext, new AppUtils.GetAccessTokenInterface() {
//                    @Override
//                    public void getAccessToken() {
//                    }
//                });
                postInitParams(mContext)
                //                goToNextView();

                /*if (Build.VERSION.SDK_INT < 23) {
                    //Do not need to check the permission
                    goToNextView();

                } else {

                    if (hasPermissions(mContext, permissions)) {
                        goToNextView();

                    } else {
                        ActivityCompat.requestPermissions(this, permissions, 100);
                    }
                }*/
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
    override fun onRestart() {
        super.onRestart()
        if (isLowerDeviceApi()) {
            //log.e("Lower", "Lower Device Api");
            jump()
            return
        }
        /*   try {
                if (videoHolder.isPlaying()) {
                    videoHolder.stopPlayback();
                }
                videoHolder.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash));
                videoHolder.setOnCompletionListener(new C00951());
                videoHolder.start();
            } catch (Exception e) {
                jump();
            }*/
    }
}