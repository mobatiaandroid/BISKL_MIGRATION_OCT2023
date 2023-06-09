package com.example.bskl_kotlin.common

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.bskl_kotlin.activity.splash.TokenModel
import com.example.bskl_kotlin.activity.splash.TokenResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class InternetCheckClass {

    companion object {
        @Suppress("DEPRECATION")
        fun isInternetAvailable(context: Context): Boolean
        {
            var result = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result
        }

        //Email Pattern Check
        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun checkApiStatusError(statusCode : Int,context: Context)
        {

        }

       /* fun showErrorAlert(context: Context,message : String,msgHead : String)
        {
            val dialog = Dialog(context)
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
            btn_Ok.setOnClickListener()
            {
                dialog.dismiss()
            }
          dialog.show()
        }

        fun showSuccessInternetAlert(context: Context)
        {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.alert_dialogue_ok_layout)
            var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
            var alertHead = dialog.findViewById(R.id.alertHead) as TextView
            var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
            var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
            text_dialog.text = "Network error occurred. Please check your internet connection and try again later"
            alertHead.text = "Alert"
            iconImageView.setBackgroundResource(R.drawable.roundred)
            iconImageView.setImageResource(R.drawable.nonetworkicon)
            btn_Ok.setOnClickListener()
            {
                dialog.dismiss()

            }
            dialog.show()
        }

        fun showSuccessAlert(context: Context,message : String,msgHead : String)
        {
            val dialog = Dialog(context)
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
            iconImageView.setImageResource(R.drawable.exclamationicon)
            btn_Ok.setOnClickListener()
            {
                dialog.dismiss()

            }
            dialog.show()
        }

        fun dateParsingToddMMMyyyy(date: String?): String? {
            var strCurrentDate = ""
            var format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            strCurrentDate = format.format(newDate)
            Log.e("Date converted",strCurrentDate)
            return strCurrentDate
        }
        fun dateParsingToddMMMyyyyBasket(date: String?): String? {
            var strCurrentDate = ""
            var format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            strCurrentDate = format.format(newDate)
            Log.e("Date converted",strCurrentDate)
            return strCurrentDate
        }
*/
        fun tokenApi(context:Context){
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
                   Log.e("tooken", responsedata!!.access_token)
                   PreferenceManager().setaccesstoken(context,responsedata!!.access_token)


               }
           })
       }


    }

    var COMMUNICATIONS = "Communications"
    var PARENT_ESSENTIALS = "Parent Essentials"
    var EARLY_YEARS = "Early Years"
    var PRIMARY = "Primary"
    var SECONDARY = "Secondary"
    var IB_PROGRAMME = "IB Programme"
    var PERFORMING_ARTS = "Performing Arts"
    var CCAS = "CCAs"
    var NAE_PROGRAMMES = "NAE Programmes"
    var ABOUT_US = "About Us"
    var CONTACT_US = "Contact Us"


}


