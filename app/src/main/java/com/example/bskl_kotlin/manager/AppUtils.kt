package com.example.bskl_kotlin.manager

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.bskl_kotlin.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {
  //  private val getTokenIntrface: GetAccessTokenInterface? = null
    private val mContext: Context? = null
    private val count = 0
    private val tokenM = " "
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return ni != null
    }

    fun durationInSecondsToString(sec: Int): String {
        val hours = sec / 3600
        val minutes = sec / 60 - hours * 60
        val seconds = sec - hours * 3600 - minutes * 60
        return String.format(
            "%d:%02d:%02d", hours, minutes,
            seconds
        )
    }

    fun dateParsingToDdMmmYyyy(date: String?): String {
        var strCurrentDate = ""
        var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }

    fun dateParsingToTime(date: String?): String {
        var strCurrentDate = ""
        var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }

    fun dateParsingToDdMmYyyy(date: String?): String {
        var strCurrentDate = ""
        var format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }

    fun dateParsingToDdMonthYyyy(date: String?): String {
        var strCurrentDate = ""
        var format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }
    fun timeParsingToHours(date: String?): String? {
        var strCurrentDate = ""
        var format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("HH", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }

    fun timeParsingToMinutes(date: String?): String? {
        var strCurrentDate = ""
        var format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("mm", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }

    fun timeParsingTo12Hour(date: String?): String? {
        var strCurrentDate = ""
        var format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }

    @JvmName("getCurrentDateToday1")
    fun getCurrentDateToday(): String? {
        val dateFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }

    fun hideKeyBoard(context: Context) {
        val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) {
            imm.hideSoftInputFromWindow(
                (context as Activity).currentFocus!!.getWindowToken(), 0
            )
        }
    }
    fun dateConversionY(inputDate: String?): String {
        var mDate = ""
        try {
            val date: Date
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            date = formatter.parse(inputDate)
            //Subtracting 6 hours from selected time
            val time = date.time

            //SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMMM yyyy");
            val formatterFullDate = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            mDate = formatterFullDate.format(time)
        } catch (e: Exception) {
            Log.d("Exception", "" + e)
        }
        return mDate
    }

    fun dateConversionMMM(inputDate: String?): String {
        var mDate = ""
        try {
            val date: Date
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            date = formatter.parse(inputDate)
            //Subtracting 6 hours from selected time
            val time = date.time

            //SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMMM yyyy");
            val formatterFullDate = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            mDate = formatterFullDate.format(time)
        } catch (e: Exception) {
            Log.d("Exception", "" + e)
        }
        return mDate
    }
    fun timeParsingToAmPm(date: String?): String? {
        var strCurrentDate = ""
        var format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }
    fun dateConversionYToD(inputDate: String?): String {
        var mDate = ""
        try {
            val date: Date
            val formatter: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            date = formatter.parse(inputDate)
            //Subtracting 6 hours from selected time
            val time = date.time

            //SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMMM yyyy");
            val formatterFullDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            mDate = formatterFullDate.format(time)
        } catch (e: Exception) {
            Log.d("Exception", "" + e)
        }
        return mDate
    }

    val currentDateToday: String
        get() {
            val dateFormat: DateFormat =
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val calendar = Calendar.getInstance()
            return dateFormat.format(calendar.time)
        }

    fun isEditTextFocused(context: Activity): Boolean {
        val inputManager = context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        val focusedView = context.currentFocus


return if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(
                focusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            true
        } else {
            false
        }




    fun setErrorForEditTextNull(edt: EditText) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
                edt.error = null
            }
        })
    }

    fun setErrorForEditText(edt: EditText, msg: String?) {
        edt.error = msg
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }







    fun hideKeyBoard(context: Context) {
        val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) {
            imm.hideSoftInputFromWindow(
                (context as Activity).currentFocus!!.getWindowToken(), 0
            )
        }
    }



    fun checkInternet(context: Context): Boolean {
        val connec = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connec.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun replace(str: String): String {
        return str.replace(" ".toRegex(), "%20")
    }

    fun replaceam(str: String): String {
        return str.replace("am".toRegex(), " ")
    }

    fun replacepm(str: String): String {
        return str.replace("pm".toRegex(), " ")
    }

    fun replaceAM(str: String): String {
        return str.replace("AM".toRegex(), " ")
    }

    fun replacePM(str: String): String {
        return str.replace("PM".toRegex(), " ")
    }

    fun replaceamdot(str: String): String {
        return str.replace("a.m.".toRegex(), " ")
    }

    fun replacepmdot(str: String): String {
        return str.replace("p.m.".toRegex(), " ")
    }

    fun replaceAMDot(str: String): String {
        return str.replace("A.M.".toRegex(), " ")
    }

    fun replacePMDot(str: String): String {
        return str.replace("P.M.".toRegex(), " ")
    }

    fun replaceAmdot(str: String): String {
        return str.replace("a.m.".toRegex(), "am")
    }

    fun replacePmdot(str: String): String {
        return str.replace("p.m.".toRegex(), "pm")
    }

    fun replaceYoutube(str: String): String {
        return str.replace("https://www.youtube.com/embed/".toRegex(), "")
    }

    fun replacePdf(str: String): String {
        return str.replace("http://mobicare2.mobatia.com/nais/media/images/".toRegex(), "")
    }






    fun editTextValidationAlert(
        edtText: EditText,
        message: String?, context: Context?
    ) {
        edtText.error = message
    }

    fun setEdtTextTextChangelistener(
        edtTxt: EditText,
        context: Context?
    ) {
        edtTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                edtTxt.error = null
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                edtTxt.error = null
            }
        })
    }











    fun hideKeyboard(context: Context, edtText: EditText?) {
        if (edtText != null) {
            val imm = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edtText.windowToken, 0)
        }
    }






    fun getVersionInfo(mContext: Context): String {
        var versionName = ""
        var versionCode = -1
        try {
            val packageInfo = mContext.packageManager.getPackageInfo(mContext.packageName, 0)
            versionName = packageInfo.versionName
            versionCode = packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionName
        //    TextView textViewVersionInfo = (TextView) findViewById(R.id.textview_version_info);
//    textViewVersionInfo.setText(String.format("Version name = %s \nVersion code = %d", versionName, versionCode));
    }

    fun timeParsingToAmPm(date: String?): String {
        var strCurrentDate = ""
        var format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }

    fun timeParsingTo12Hour(date: String?): String {
        var strCurrentDate = ""
        var format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }

    fun timeParsingToHours(date: String?): String {
        var strCurrentDate = ""
        var format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("HH", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }

    fun timeParsingToMinutes(date: String?): String {
        var strCurrentDate = ""
        var format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("mm", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }

    fun hideKeyboardOnTouchOutside(
        view: View,
        context: Context?, edtText: EditText?
    ) {
        if (view !is EditText) view.setOnTouchListener { v, event ->
           // AppUtils.hideKeyBoard(context)
            false
        }


    }
    }
    fun replace(str: String): String? {
        return str.replace(" ".toRegex(), "%20")
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
        dialogButton.setOnClickListener { dialog.dismiss() }
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
}
