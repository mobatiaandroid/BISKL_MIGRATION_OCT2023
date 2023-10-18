package com.example.bskl_kotlin.activity.splash

import ApiClient
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.bskl_kotlin.activity.home.HomeActivity
import com.example.bskl_kotlin.activity.login.LoginActivity
import com.example.bskl_kotlin.activity.tutorial.TutorialActivity
import com.example.bskl_kotlin.common.PreferenceManager
import com.google.firebase.messaging.FirebaseMessaging
import com.example.bskl_kotlin.R
import okhttp3.ResponseBody
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

        /*FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isComplete) {
                val token: String = task.getResult().toString()
                tokenM = token
                //callLoginApi(emailTxt,passwordTxt,deviceId)
                //callChangePasswordStaffAPI(URL_STAFF_CHANGE_PASSWORD, token)
            }
        }*/

        if (tokenM == null) {
            val i = Intent(this@SplashActivity, SplashActivity::class.java)
            startActivity(i)
            finish()
        } else {
            FirebaseID = tokenM
        }
        tokenApi()

        Log.e("FIREBASE ID ANDROID:",FirebaseID)
        //PreferenceManager().setFcmID(mContext,FirebaseID)


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
                   /* val dummyOwn: ArrayList<OwnContactModel> = ArrayList<OwnContactModel>()
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


    /*  fun callDeviceRegistrationApi()
      {
          val token = sharedprefs.getaccesstoken(mContext)
          var androidID = Settings.Secure.getString(this.contentResolver,
              Settings.Secure.ANDROID_ID)
          System.out.println("LOGINRESPONSE:"+"devid:  "+androidID+" FCM ID : "+tokenM)
          var deviceReg= DeviceRegModel(2, tokenM,androidID)
          val call: Call<ResponseBody> = ApiClient.getClient.deviceregistration(deviceReg,"Bearer "+token)

          call.enqueue(object : Callback<ResponseBody> {
              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                  Log.e("Failed", t.localizedMessage)

              }
              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  val responsedata = response.body()

                  Log.e("Response Signup", responsedata.toString())
                  if (responsedata != null) {
                      try {
                          Log.e("AccessToken","Sucess")
                      }
                      catch (e: Exception) {
                          e.printStackTrace()
                      }
                  }
              }

          })
      }*/
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


            }
        })
    }

}