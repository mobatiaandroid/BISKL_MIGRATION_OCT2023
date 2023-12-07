package com.example.bskl_kotlin.activity.absence

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.manager.AppController
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LeavesDetailActivity:AppCompatActivity() {
    lateinit var mContext: Context
    var fromlayout: LinearLayout? = null
    var reasonlayout:LinearLayout? = null
    var stnameValue: TextView? = null
    var leaveDateFromValue:TextView? = null
    var leaveDateToValue:TextView? = null
    var reasonValue:TextView? = null
    var studClassValue:TextView? = null
    var studStaffValue:TextView? = null
    var extras: Bundle? = null
    var position = 0
    var studentNameStr = ""
    var studentClassStr = ""
    var fromDate = ""
    var toDate = ""
    var reasonForAbsence = ""
    var staff = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_detailpage)

        mContext = this

        initUi()
        setValues()
    }

    private fun initUi() {
        extras = intent.extras
        if (extras != null) {
            studentNameStr = extras!!.getString("studentName")!!
            studentClassStr = extras!!.getString("studentClass")!!
            fromDate = extras!!.getString("fromDate")!!
            toDate = extras!!.getString("toDate")!!
            reasonForAbsence = extras!!.getString("reasonForAbsence")!!
            staff = extras!!.getString("staff")!!
        }
        fromlayout = findViewById(R.id.fromlayout)
        reasonlayout = findViewById(R.id.reasonlayout)
        stnameValue = findViewById(R.id.stnameValue)
        studClassValue = findViewById(R.id.studClassValue)
        leaveDateFromValue = findViewById(R.id.leaveDateFromValue)
        leaveDateToValue = findViewById(R.id.leaveDateToValue)
        reasonValue = findViewById(R.id.reasonValue)
        studStaffValue = findViewById(R.id.studStaffValue)


        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_view_home)
        supportActionBar!!.elevation = 0f

        val view = supportActionBar!!.customView
        val toolbar = view.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)
        val headerTitle = findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = findViewById<ImageView>(R.id.logoClickImgView)
        val action_bar_forward = findViewById<ImageView>(R.id.action_bar_forward)
        val action_bar_back = findViewById<ImageView>(R.id.action_bar_back)
        action_bar_back.setImageResource(R.drawable.back_new)
        action_bar_forward.visibility = View.INVISIBLE
        headerTitle.text = "Absence"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        action_bar_back.setOnClickListener { finish() }
    }
    private fun setValues() {
        stnameValue!!.text = studentNameStr
        studClassValue!!.text = studentClassStr
        leaveDateFromValue!!.setText(dateConversionY(fromDate))
        leaveDateToValue!!.setText(dateConversionY(toDate))
        reasonValue!!.text = reasonForAbsence
        studStaffValue!!.text = staff
    }
    fun dateConversionY(inputDate: String?): String? {
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
    override fun onResume() {
        super.onResume()
        if (!PreferenceManager().getUserId(mContext).equals("")) {
           /* AppController().getInstance().getGoogleAnalyticsTracker()
                .set("&uid", PreferenceManager().getUserId(mContext))
            AppController().getInstance().getGoogleAnalyticsTracker()
                .set("&cid", PreferenceManager().getUserId(mContext))
            AppController().getInstance().trackScreenView(
                "Leave Detail Screen." + "(" + PreferenceManager().getUserEmail(mContext) + ")" + " " + "(" + Calendar.getInstance().time + ")"
            )*/
        }
    }
}