package com.example.bskl_kotlin.fragment.reports

import android.content.Context
import android.os.Bundle
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
import com.example.bskl_kotlin.R
import com.mobatia.bskl.R

class ReportFragment(title: String, tabId: String) : Fragment() {

    lateinit var mContext: Context
    lateinit var mStudentSpinner: LinearLayout
    lateinit var studentName: TextView
    lateinit var studImg: ImageView
    lateinit var noDataImg: ImageView
    lateinit var mRecycleView: RecyclerView
    lateinit var alertText: TextView
    lateinit var noDataRelative: RelativeLayout
    lateinit var relMain: RelativeLayout
    lateinit var alertTxtRelative: RelativeLayout
    lateinit var textViewYear: TextView
    lateinit var noDataTxt: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.progress_report, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        headerTitle.text = "Reports"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        initUi()


    }

    private fun initUi(){

        alertText = requireView().findViewById<TextView>(R.id.noDataTxt)
        mStudentSpinner = requireView().findViewById<LinearLayout>(R.id.studentSpinner)
        studentName = requireView().findViewById<TextView>(R.id.studentName)
        studImg = requireView().findViewById<ImageView>(R.id.imagicon)
        noDataImg = requireView().findViewById<ImageView>(R.id.noDataImg)
        mRecycleView = requireView().findViewById<RecyclerView>(R.id.recycler_view_list)
        noDataRelative = requireView().findViewById<RelativeLayout>(R.id.noDataRelative)
        relMain = requireView().findViewById<RelativeLayout>(R.id.relMain)
        alertTxtRelative = requireView().findViewById<RelativeLayout>(R.id.noDataRelative)
        textViewYear = requireView().findViewById<TextView>(R.id.textViewYear)
        noDataTxt = requireView().findViewById<TextView>(R.id.noDataTxt)
    }
}