package com.example.bskl_kotlin.fragment.messages

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.activity.notification.NotificationInfoActivity
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.PreferenceManager
import com.mobatia.bskl.R
import java.text.SimpleDateFormat

class NotificationFragmentPagination(title: String, tabId: String) : Fragment() {
    lateinit var mContext: Context
    var myFormatCalender = "yyyy-MM-dd"
   var  dataLength=0
    var typeValue = "1"
    var tokenM = " "
    var limitValue = 20
    var pageReadValue = 0
    var pageUnReadValue = 0
    var isAvailable = false
    var isReadAvailable = false
    var isFromReadBottom = false
    var isFromUnReadBottom = false
    var isProgressVisible = false
    private var readList: RecyclerView? = null
    private val unReadList: RecyclerView? = null
    private val mTitle: String? = null
    private val mTabId: String? = null
    var markRead: TextView? = null
    var unreadTxt:TextView? = null
    var readTxt:TextView? = null
    var anim: RotateAnimation? = null
    var markUnRead: TextView? = null
    var msgUnRead:TextView? = null
    var msgReadSelect:TextView? = null
    var msgRead: TextView? = null
    var msgUnread: TextView? = null
    var noMsgAlertRelative: RelativeLayout? = null
    var maniRelative: LinearLayout? = null
    var noMsgText: TextView? = null
    var messageRead: ImageView? = null
    var messageUnRead: ImageView? = null
    var msgReadLinear: LinearLayout? = null
    var msgUnreadLinear: LinearLayout? = null
    var sdfcalender: SimpleDateFormat? = null
    var mProgressRelLayout: RelativeLayout? = null
    var nestedScroll: NestedScrollView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_news_layout, container, false)
    }
    @SuppressLint("UseRequireInsteadOfGet", "Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        val imageButton2 = actionBar.customView.findViewById<ImageView>(R.id.action_bar_forward)
        logoClickImgView.visibility = View.INVISIBLE
        imageButton2.setImageResource(R.drawable.tutorial_icon)
        AppController().mMessageReadList.clear()
        AppController().mMessageUnreadList.clear()
        headerTitle.text = "Messages"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        initUi()
        AppController().isVisibleUnreadBox = false
        AppController().isVisibleReadBox = false
        myFormatCalender = "yyyy-MM-dd HH:mm:ss"
        sdfcalender = SimpleDateFormat(myFormatCalender)
        AppController().isSelectedUnRead = false
        AppController().isSelectedRead = false
        dataLength = 0
        typeValue = "1"
        limitValue = 20
        isAvailable = false
        isReadAvailable = false
        pageReadValue = 0
        pageUnReadValue = 0
        isFromReadBottom = false
        isFromUnReadBottom = false
        isProgressVisible = false
        AppController().callTypeStatus = "1"

       // callPushNotification(typeValue, limitValue.toString(), pageUnReadValue.toString())

        if (PreferenceManager.getIsFirstTimeInNotification(mContext)) {
            PreferenceManager.setIsFirstTimeInNotification(mContext, false)
            val mintent = Intent(
                mContext,
                NotificationInfoActivity::class.java
            )
            mintent.putExtra("type", 1)
            mContext.startActivity(mintent)
        }


    }
    private fun initUi(){
        mProgressRelLayout = requireView().findViewById<View>(R.id.progressDialog) as RelativeLayout
       // nestedScroll = requireView().findViewById<NestedScrollView>(R.id.nestedScroll)
        markRead = requireView().findViewById<TextView>(R.id.markReadTxt)
        unreadTxt = requireView().findViewById<TextView>(R.id.unreadTxt)
        msgReadSelect = requireView().findViewById<TextView>(R.id.msgReadSelect)
        readTxt = requireView().findViewById<TextView>(R.id.readTxt)
        markUnRead = requireView().findViewById<TextView>(R.id.markUnreadTxt)
        //msgRead = requireView().findViewById<TextView>(R.id.msgRead)
        msgUnread = requireView().findViewById<TextView>(R.id.msgUnRead)
        messageRead = requireView().findViewById<ImageView>(R.id.messageRead)
        messageUnRead = requireView().findViewById<ImageView>(R.id.messageUnread)
        readList = requireView().findViewById<RecyclerView>(R.id.mMessageReadList)
        noMsgAlertRelative = requireView().findViewById<RelativeLayout>(R.id.noMsgAlert)
        maniRelative = requireView().findViewById<LinearLayout>(R.id.maniRelative)
        noMsgText = requireView().findViewById<TextView>(R.id.noMsgAlertTxt)

    }


}