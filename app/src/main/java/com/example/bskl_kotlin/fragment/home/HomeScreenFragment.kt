package com.example.bskl_kotlin.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.mobatia.bskl.R

class HomeScreenFragment(title:String,
                         mDrawerLayouts: DrawerLayout, listView: ListView, linearLayout: LinearLayout,
                         mListItemArray:Array<String>, mListImgArray: TypedArray
): Fragment() {

    lateinit var mContext: Context
    private val TAB_ID: String? = null
    private val android_app_version: String? = null
    private val INTENT_TAB_ID: String? = null
    //private val mSectionText: Array<String>
    private val isDraggable = false
    lateinit var messageBtn: Button
    lateinit var newsBtn: Button
    lateinit var socialBtn: Button
    lateinit var calendarBtn: Button
    lateinit var img: ImageView
    var currentPage = 0
    var isKinFound = false
    var isLocalFound = false
    private val PERMISSION_CALLBACK_CONSTANT_CALENDAR = 1
    private val PERMISSION_CALLBACK_CONSTANT_EXTERNAL_STORAGE = 2
    private val PERMISSION_CALLBACK_CONSTANT_LOCATION = 3
    private val REQUEST_PERMISSION_CALENDAR = 101
    private val REQUEST_PERMISSION_EXTERNAL_STORAGE = 102
    private val REQUEST_PERMISSION_LOCATION = 103
    lateinit var firstTextView: TextView
    lateinit var secondTextView: TextView
    lateinit var thirdTextView: TextView
    lateinit var safeGuardingBadge: TextView
    lateinit var alreadyTriggered: String
    lateinit var fourthTextView: TextView
    lateinit var secondImageView: ImageView
    lateinit var thirdImageView: ImageView
    lateinit var firstRelativeLayout: RelativeLayout
    lateinit var secondRelativeLayout: RelativeLayout
    lateinit var thirdRelativeLayout: RelativeLayout
    lateinit var fourthRelativeLayout: RelativeLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home_activity, container, false)
    }
    @SuppressLint("UseRequireInsteadOfGet", "Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initUi()
}
    private fun initUi(){
        img = requireView().findViewById<ImageView>(R.id.img)
        messageBtn = requireView().findViewById<Button>(R.id.messageBtn)
        newsBtn = requireView().findViewById<Button>(R.id.newsBtn)
        socialBtn = requireView().findViewById<Button>(R.id.socialBtn)
        calendarBtn = requireView().findViewById<Button>(R.id.calendarBtn)
        firstRelativeLayout = requireView().findViewById<RelativeLayout>(R.id.firstRelativeLayout)
        secondRelativeLayout = requireView().findViewById<RelativeLayout>(R.id.secondRelativeLayout)
        thirdRelativeLayout = requireView().findViewById<RelativeLayout>(R.id.thirdRelativeLayout)
        fourthRelativeLayout = requireView().findViewById<RelativeLayout>(R.id.fourthRelativeLayout)
        firstTextView = requireView().findViewById<TextView>(R.id.firstTextView)
        secondTextView = requireView().findViewById<TextView>(R.id.secondTextView)
        thirdTextView = requireView().findViewById<TextView>(R.id.thirdTextView)
        fourthTextView = requireView().findViewById<TextView>(R.id.fourthTextView)
        secondImageView = requireView().findViewById<ImageView>(R.id.secondImageView)
        thirdImageView = requireView().findViewById<ImageView>(R.id.thirdImageView)
        safeGuardingBadge = requireView().findViewById<TextView>(R.id.safeGuardingBadge)
    }

}