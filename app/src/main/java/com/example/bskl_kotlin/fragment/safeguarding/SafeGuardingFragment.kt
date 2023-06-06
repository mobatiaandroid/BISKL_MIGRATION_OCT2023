package com.example.bskl_kotlin.fragment.safeguarding

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.fragment.absence.model.StudentModel
import com.example.bskl_kotlin.manager.AppUtils
import com.mobatia.bskl.R
import java.text.SimpleDateFormat
import java.util.*

class SafeGuardingFragment:Fragment() {
    private val mRootView: View? = null
    lateinit var  mContext: Context

   lateinit var currentDateTextView: TextView
   lateinit var mStudentRecyclerView: RecyclerView
    var viewBottom: View? = null
   lateinit var mIntent: Intent
    private val mTitle: String? = null
    private val mTabId: String? = null
   lateinit var mStudentAttendanceList: ArrayList<StudentModel>
   lateinit var noDataRelative: RelativeLayout
    var noDataTxt: TextView? = null
   //lateinit var mSafeGuardingAdapter: SafeGuardingAdapter
    var linearMain: LinearLayout? = null
   lateinit var mStudentAttendanceListCopy: ArrayList<StudentModel>
  // lateinit var typeArrayList: ArrayList<TypeModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_safe_guarding_layout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        val imageButton2 = actionBar!!.customView.findViewById<ImageView>(R.id.action_bar_forward)
        imageButton2.setImageResource(R.drawable.settings)
        //headerTitle.setText(SAFE_GUARDING)
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        initialiseUI()

//        String myFormatCalender = "EEE dd MMMM yyyy";
        val dayEnd = DateFormat.format("dd", Calendar.getInstance().time) as String // 20

        val sdfcalender: SimpleDateFormat

        sdfcalender = if (dayEnd.endsWith("1") && !dayEnd.endsWith("11")) {
            SimpleDateFormat("EEE dd'st' MMMM yyyy", Locale.ENGLISH)
        } else if (dayEnd.endsWith("2") && !dayEnd.endsWith("12")) {
            SimpleDateFormat("EEE dd'nd' MMMM yyyy", Locale.ENGLISH)
        } else if (dayEnd.endsWith("3") && !dayEnd.endsWith("13")) SimpleDateFormat(
            "EEE dd'rd' MMMM yyyy",
            Locale.ENGLISH
        ) else SimpleDateFormat("EEE dd'th' MMMM yyyy", Locale.ENGLISH)

        val dateString = sdfcalender.format(Calendar.getInstance().time)
        currentDateTextView.text = dateString
        if (AppUtils().isNetworkConnected(mContext)) {
         //  callSafeGuarding()
        } else {
            AppUtils().showDialogAlertDismiss(
                mContext as Activity?,
                "Network Error",
                mContext.getResources().getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }
    }

    private fun initialiseUI() {

    }
}