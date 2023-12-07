package com.example.bskl_kotlin.fragment.messages

import ApiClient
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.notification.NotificationInfoActivity
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.common.model.NotificationNewResponseModel
import com.example.bskl_kotlin.constants.ClickListener
import com.example.bskl_kotlin.fragment.messages.adapter.ReadMessageAdapter
import com.example.bskl_kotlin.fragment.messages.adapter.UnReadMessageAdapter
import com.example.bskl_kotlin.fragment.messages.model.NotificationFavApiModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationFavouriteModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationNewDataModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationStatusApiModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationStatusModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationsNewApiModel
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel
import com.example.bskl_kotlin.manager.AppUtils
import com.example.bskl_kotlin.recyclerviewmanager.OnBottomReachedListener
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale

class NotificationFragmentPagination(title: String, tabId: String) : Fragment(),
    View.OnClickListener, AdapterView.OnItemClickListener{
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
    lateinit var readList: RecyclerView
    lateinit var unReadList: RecyclerView
    private val mTitle: String? = null
    private val mTabId: String? = null
   lateinit var markRead: TextView
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
    //var unReadMessageAdapter: UnReadMessageAdapter? = null
    var readMessageAdapter: ReadMessageAdapter? = null
    var unReadMessageAdapter: UnReadMessageAdapter?=null
    lateinit var notificationList:ArrayList<NotificationNewDataModel>
    lateinit var mMessageReadList: ArrayList<PushNotificationModel>
    lateinit var mMessageUnreadList: ArrayList<PushNotificationModel>

    lateinit var readArray:ArrayList<PushNotificationModel>
    lateinit var unreadArray:ArrayList<PushNotificationModel>
   // var isVisibleUnreadBox:Boolean=false
    
    //var PreferenceManager().setisVisibleReadBox(mContext,false)
    //var isSelectedUnRead = false
    //var PreferenceManager().setisSelectedRead(mContext,false)
    var  callTypeStatus = "1"
    //var PreferenceManager().setclick_count_read(mContext,0)
    //var PreferenceManager().setclick_count(mContext,0)
    var mMessageUnreadListFav = ArrayList<PushNotificationModel>()
    var mMessageReadListFav = ArrayList<PushNotificationModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.message_pagination_layout, container, false)
    }
    @SuppressLint("UseRequireInsteadOfGet", "Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()

        notificationList= ArrayList()
        mMessageReadList= ArrayList()
        readArray= ArrayList()
        unreadArray= ArrayList()
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        val imageButton2 = actionBar.customView.findViewById<ImageView>(R.id.action_bar_forward)
        logoClickImgView.visibility = View.INVISIBLE
        imageButton2.setImageResource(R.drawable.tutorial_icon)

        readArray.clear()
        unreadArray.clear()
        mMessageReadList=ArrayList()
        unreadArray=ArrayList()
        mMessageUnreadList= ArrayList()

       // AppController().mMessageReadList.clear()
        //AppController().mMessageUnreadList.clear()
        headerTitle.text = "Messages"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        initUi()
        //onClick()
        myFormatCalender = "yyyy-MM-dd HH:mm:ss"
        sdfcalender = SimpleDateFormat(myFormatCalender)

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
      /*  readList.onItemClickListener= this
        readList.onItemLongClickListener=this*/

        callPushNotification(
            typeValue,
            limitValue.toString(),
            pageUnReadValue.toString()
        )

      /*  if (AppUtils().isNetworkConnected(mContext)) {
            callPushNotification(
                typeValue,
                limitValue.toString(),
                pageUnReadValue.toString()
            )
        } else {
            AppUtils().showDialogAlertDismiss(
                mContext as Activity?,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }*/

        if (PreferenceManager().getIsFirstTimeInNotification(mContext)) {
            PreferenceManager().setIsFirstTimeInNotification(mContext, false)
            val mintent = Intent(
                mContext,
                NotificationInfoActivity::class.java
            )
            mintent.putExtra("type", 1)
            mContext.startActivity(mintent)
        }
        //callPushNotification("1","20","1")


    }
    private fun initUi(){
        Log.e("init","true")
        mProgressRelLayout = requireView().findViewById<View>(R.id.progressDialog) as RelativeLayout
       // nestedScroll = requireView().findViewById<NestedScrollView>(R.id.nestedScroll)
        markRead = requireView().findViewById(R.id.markReadTxt)
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

        readList.setHasFixedSize(true)
        val mRead = LinearLayoutManager(mContext)
        mRead.orientation = LinearLayoutManager.VERTICAL
        readList.setLayoutManager(mRead)
        unReadList =
            requireView().findViewById<RecyclerView>(R.id.mMessageUnReadList)
        unReadList.setHasFixedSize(true)

        val mUnRead = LinearLayoutManager(mContext)
        mUnRead.orientation = LinearLayoutManager.VERTICAL
        unReadList.setLayoutManager(mUnRead)
        /*readList.setHasFixedSize(true)
        val mRead = LinearLayoutManager(mContext)
        mRead.orientation = LinearLayoutManager.VERTICAL
        readList.setLayoutManager(mRead)
        unReadList =
            requireView().findViewById<RecyclerView>(R.id.mMessageUnReadList)
        unReadList.setHasFixedSize(true)
        val mUnRead = LinearLayoutManager(mContext)
        mUnRead.orientation = LinearLayoutManager.VERTICAL
        unReadList.setLayoutManager(mUnRead)*/
        // mMessageUnreadList=new ArrayList<PushNotificationModel>();
        // mMessageReadList = new ArrayList<PushNotificationModel>();
        unReadList =
            requireView().findViewById<RecyclerView>(R.id.mMessageUnReadList)
        msgReadLinear =
            requireView().findViewById<LinearLayout>(R.id.msgReadLinear)
        msgUnreadLinear =
            requireView().findViewById<LinearLayout>(R.id.msgUnreadLinear)
        //callPushNotification("1","20","1")
        // mMessageUnreadList=new ArrayList<PushNotificationModel>();
        // mMessageReadList = new ArrayList<PushNotificationModel>();
        msgReadLinear =
            requireView().findViewById<LinearLayout>(R.id.msgReadLinear)
        msgUnreadLinear =
            requireView().findViewById<LinearLayout>(R.id.msgUnreadLinear)
        /*unReadList.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                Log.e("click adpter",position.toString())
            }



            })*/


        markRead.setOnClickListener(this)
        markUnRead!!.setOnClickListener(this)
        messageRead!!.setOnClickListener(this)
        messageUnRead!!.setOnClickListener(this)
        noMsgAlertRelative!!.setOnClickListener(this)
        maniRelative!!.setOnClickListener(this)
        unreadTxt!!.setOnClickListener(this)
        readTxt!!.setOnClickListener(this)


    }

   /* private fun onClick(){

        markUnRead!!.setOnClickListener {

        }
        messageRead!!.setOnClickListener {
            val mCount: Int = click_count_read
            if (click_count_read % 2 === 0) {
                PreferenceManager().setisVisibleReadBox(mContext,true)
                markUnRead!!.setVisibility(View.GONE)
                markRead!!.setVisibility(View.GONE)
                PreferenceManager().setisSelectedRead(mContext,false)
                for (i in 0 until mMessageReadList.size) {
                    mMessageReadList.get(i).isMarked=true
                }
                var selected = false
                for (i in 0 until mMessageReadList.size) {
                    if (mMessageReadList.get(i).isMarked) {
                        selected = true
                    } else {
                        selected = false
                        break
                    }
                }
                if (selected) {
                    messageRead!!.setBackgroundResource(R.drawable.check_box_header_tick)
                    markUnRead!!.setVisibility(View.VISIBLE)
                    markRead!!.setVisibility(View.GONE)
                } else {
                    messageRead!!.setBackgroundResource(R.drawable.check_box_header)
                    markRead!!.setVisibility(View.GONE)
                    markUnRead!!.setVisibility(View.VISIBLE)
                }
                readMessageAdapter!!.notifyDataSetChanged()
                //readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
            } else {
                PreferenceManager().setisVisibleReadBox(mContext,false)
                for (i in 0 until mMessageReadList.size) {
                    if (mMessageReadList.get(i).isChecked) {
                        mMessageReadList.get(i).isChecked=false
                    }
                }
                markUnRead!!.setVisibility(View.GONE)
                messageRead!!.setVisibility(View.GONE)
                msgReadSelect!!.setVisibility(View.GONE)
                readMessageAdapter!!.notifyDataSetChanged()
                //readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
            }

            click_count_read++
        }
        messageUnRead!!.setOnClickListener {
            if (click_count % 2 === 0) {
                PreferenceManager().setisVisibleUnreadBox(mContext,true)
                //isVisibleUnreadBox = true
                for (i in 0 until unreadArray.size) {
                    unreadArray.get(i).isChecked=true
                }
                *//*for (i in 0 until AppController().mMessageUnreadList.size) {
                    AppController().mMessageUnreadList.get(i).isChecked=true
                }*//*
                var selected = false
                for (i in 0 until unreadArray.size) {
                    if (unreadArray.get(i).isChecked) {
                        selected = true
                    } else {
                        selected = false
                        break
                    }
                }
                *//*for (i in 0 until AppController().mMessageUnreadList.size) {
                    if (AppController().mMessageUnreadList.get(i).isChecked) {
                        selected = true
                    } else {
                        selected = false
                        break
                    }
                }*//*
                if (selected) {
                    messageUnRead!!.setBackgroundResource(R.drawable.check_box_header_tick)
                    markUnRead!!.setVisibility(View.GONE)
                    markRead!!.setVisibility(View.VISIBLE)
                } else {
                    messageUnRead!!.setBackgroundResource(R.drawable.check_box_header)
                    markUnRead!!.setVisibility(View.GONE)
                    markRead!!.setVisibility(View.GONE)
                }
                //unReadMessageAdapter!!.notifyDataSetChanged()
                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
            } else {
                PreferenceManager().setisVisibleUnreadBox(mContext,false)
               //PreferenceManager().setisVisibleUnreadBox(mContext,false)
               // Log.e("test",isVisibleUnreadBox.toString())
                for (i in 0 until unreadArray.size) {
                    if (unreadArray.get(i).isChecked) {
                        unreadArray.get(i).isChecked=false
                    }
                }
               *//* for (i in 0 until AppController().mMessageUnreadList.size) {
                    if (AppController().mMessageUnreadList.get(i).isChecked) {
                        AppController().mMessageUnreadList.get(i).isChecked=false
                    }
                }*//*
                messageUnRead!!.setVisibility(View.GONE)
                msgUnread!!.setVisibility(View.GONE)
                markRead!!.setVisibility(View.GONE)
                messageUnRead!!.setBackgroundResource(R.drawable.check_box_header)
               // unReadMessageAdapter!!.notifyDataSetChanged()
                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
            }
            click_count++
        }
        unreadTxt!!.setOnClickListener {
            callTypeStatus = "1"
            markRead!!.setVisibility(View.GONE)
            messageUnRead!!.setVisibility(View.GONE)
            msgReadSelect!!.setVisibility(View.GONE)
            msgUnread!!.setVisibility(View.GONE)
            messageRead!!.setVisibility(View.GONE)
            markUnRead!!.setVisibility(View.GONE)
            // messageUnRead!!.setBackgroundResource(R.drawable.check_box_header);
            // messageUnRead!!.setBackgroundResource(R.drawable.check_box_header);
            PreferenceManager().setisVisibleUnreadBox(mContext,false)
            //isVisibleUnreadBox = false
            PreferenceManager().setisVisibleReadBox(mContext,false)
            // System.out.println(" size of array"+AppController().mMessageUnreadList.size);
            // System.out.println(" size of array"+AppController().mMessageUnreadList.size);
            if (isProgressVisible) {
                mProgressRelLayout!!.clearAnimation()
                mProgressRelLayout!!.setVisibility(View.GONE)
                isProgressVisible = false
            }
            typeValue = "1"
            limitValue = 20
            pageUnReadValue = 0
            isAvailable = false
            if (unreadArray.size > 0) {
                unreadTxt!!.setBackgroundResource(R.drawable.notification_read)
                readTxt!!.setBackgroundResource(R.drawable.notification_unread)
                unreadTxt!!.setTextColor(
                    mContext.getResources().getColor(R.color.white)
                )
                readTxt!!.setTextColor(
                    mContext.getResources()
                        .getColor(R.color.split_bg)
                )
                Log.e("adaptr","1set")
                unReadList.visibility=View.VISIBLE
                unReadList.layoutManager=LinearLayoutManager(mContext)
                unReadMessageAdapter =
                    UnReadMessageAdapter(
                        mContext,
                        unreadArray,
                        messageUnRead!!,
                        markRead!!,
                        markUnRead!!,
                        msgUnread!!*//*,object :OnItemLongClickListener{
                            override fun onItemLongClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ): Boolean {
                                Log.e("longclickadapter","longclick")
                                markRead.setVisibility(
                                    View.GONE
                                )
                                markUnRead!!.setVisibility(
                                    View.GONE
                                )
                                messageUnRead!!.setBackgroundResource(
                                    R.drawable.check_box_header
                                )
                               PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                PreferenceManager().setclick_count(mContext,0)
                                for (i in 0 until mMessageReadList.size) {
                                    mMessageReadList.get(i).isMarked=false

                                }
                                for (i in 0 until mMessageUnreadList.size) {
                                    mMessageUnreadList.get(i).isChecked=false

                                }
                                if (mMessageReadList.size > 0) {
                                    readMessageAdapter!!.notifyDataSetChanged()
                                    // readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                                }
                                unReadList.adapter=unReadMessageAdapter
                                unReadMessageAdapter!!.notifyDataSetChanged()
                                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                                messageUnRead!!.setVisibility(
                                    View.VISIBLE
                                )
                                msgUnread!!.setVisibility(
                                    View.VISIBLE
                                )
                                messageRead!!.setVisibility(
                                    View.GONE
                                )
                                msgReadSelect!!.setVisibility(
                                    View.GONE
                                )
                                return false
                            }
                        },object :
                            OnItemClickListener {

                            override fun onItemClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                TODO("Not yet implemented")
                            }


                        }*//*
                                        ,object : ClickListener {
                                            override fun onPositionClicked(position: Int) {
                                                Log.e("c1","true")
                                            }
                                            override fun onLongClicked(position: Int) {
                                                Log.e("lc1","true")

                                                markRead.setVisibility(
                                                    View.GONE
                                                )
                                                markUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                messageUnRead!!.setBackgroundResource(
                                                    R.drawable.check_box_header
                                                )
                                                PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                                //isVisibleUnreadBox = true
                                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                                PreferenceManager().setclick_count(mContext,0)
                                                for (i in 0 until mMessageReadList.size) {
                                                    mMessageReadList.get(i).isMarked=false

                                                }
                                                for (i in 0 until mMessageUnreadList.size) {
                                                    mMessageUnreadList.get(i).isChecked=false

                                                }
                                                if (mMessageReadList.size > 0) {
                                                    readMessageAdapter!!.notifyDataSetChanged()
                                                    // readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                                                }
                                                unReadList.adapter=unReadMessageAdapter
                                                unReadMessageAdapter!!.notifyDataSetChanged()
                                                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                                                messageUnRead!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                msgUnread!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                messageRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgReadSelect!!.setVisibility(
                                                    View.GONE
                                                )
                                            }
                                        }
                    )

                unReadList.adapter=unReadMessageAdapter
                //unReadList.onItemLongClickListener
                msgUnreadLinear!!.setVisibility(View.VISIBLE)
                msgReadLinear!!.setVisibility(View.GONE)
                unReadMessageAdapter!!.notifyDataSetChanged()
                if (isFromUnReadBottom) {
                    unReadList!!.scrollToPosition(
                        pageUnReadValue - 2
                    )
                }
                // unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                Log.e("adaptr","set2")
                unReadList.layoutManager=LinearLayoutManager(mContext)
                unReadMessageAdapter =
                    UnReadMessageAdapter(
                        mContext,
                        unreadArray,
                        messageUnRead!!,
                        markRead!!,
                        markUnRead!!,
                        msgUnread!!,*//*object :OnItemLongClickListener{
                            override fun onItemLongClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ): Boolean {
                                Log.e("longclickadapter","longclick")
                                markRead.setVisibility(
                                    View.GONE
                                )
                                markUnRead!!.setVisibility(
                                    View.GONE
                                )
                                messageUnRead!!.setBackgroundResource(
                                    R.drawable.check_box_header
                                )
                               PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                PreferenceManager().setclick_count(mContext,0)
                                for (i in 0 until mMessageReadList.size) {
                                    mMessageReadList.get(i).isMarked=false

                                }
                                for (i in 0 until mMessageUnreadList.size) {
                                    mMessageUnreadList.get(i).isChecked=false

                                }
                                if (mMessageReadList.size > 0) {
                                    readMessageAdapter!!.notifyDataSetChanged()
                                    // readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                                }
                                unReadList.adapter=unReadMessageAdapter
                                unReadMessageAdapter!!.notifyDataSetChanged()
                                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                                messageUnRead!!.setVisibility(
                                    View.VISIBLE
                                )
                                msgUnread!!.setVisibility(
                                    View.VISIBLE
                                )
                                messageRead!!.setVisibility(
                                    View.GONE
                                )
                                msgReadSelect!!.setVisibility(
                                    View.GONE
                                )
                                return false
                            }
                        },object :
                            OnItemClickListener {

                            override fun onItemClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                TODO("Not yet implemented")
                            }


                        }*//*
                                        object : ClickListener {
                                            override fun onPositionClicked(position: Int) {
                                                Log.e("click","true")
                                            }
                                            override fun onLongClicked(position: Int) {
                                                Log.e("longclick","true")
                                                markRead.setVisibility(
                                                    View.GONE
                                                )
                                                markUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                messageUnRead!!.setBackgroundResource(
                                                    R.drawable.check_box_header
                                                )
                                                PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                               //PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                                PreferenceManager().setclick_count(mContext,0)
                                                for (i in 0 until mMessageReadList.size) {
                                                    mMessageReadList.get(i).isMarked=false

                                                }
                                                for (i in 0 until mMessageUnreadList.size) {
                                                    mMessageUnreadList.get(i)

                                                }
                                                if (mMessageReadList.size > 0) {
                                                    readMessageAdapter!!.notifyDataSetChanged()
                                                    // readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                                                }
                                                unReadList.adapter=unReadMessageAdapter
                                                unReadMessageAdapter!!.notifyDataSetChanged()
                                                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                                                messageUnRead!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                msgUnread!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                messageRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgReadSelect!!.setVisibility(
                                                    View.GONE
                                                )
                                            }
                                        }
                    )
                unReadList.adapter=unReadMessageAdapter
                unReadList!!.setAdapter(unReadMessageAdapter)
                unReadMessageAdapter!!.setOnBottomReachedListener(
                    object : OnBottomReachedListener {
                        override fun onBottomReached(position: Int) {
                            if (position == mMessageUnreadList.size - 1) {
                                if (!isAvailable) {
                                    isFromUnReadBottom = true
                                   
                                    //if (isVisibleUnreadBox) {
                                    if (PreferenceManager().getisVisibleUnreadBox(mContext)==true){
                                        markRead!!.setVisibility(View.GONE)
                                        markUnRead!!.setVisibility(View.GONE)
                                        messageUnRead!!.setBackgroundResource(
                                            R.drawable.check_box_header
                                        )
                                       PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                        PreferenceManager().setisVisibleReadBox(mContext,false)
                                        PreferenceManager().setclick_count(mContext,0)
                                        for (i in 0 until mMessageReadList.size) {
                                            mMessageReadList.get(i).isMarked=false
                                        }
                                        for (i in 0 until mMessageUnreadList.size) {
                                            mMessageUnreadList.get(i)
                                                .isChecked=false
                                        }
                                    }
                                    typeValue = "1"
                                    limitValue = 20
                                    pageUnReadValue =
                                        pageUnReadValue + limitValue
                                    callPushNotification(
                                        typeValue,
                                        limitValue.toString(),
                                        pageUnReadValue.toString()
                                    )
                                    // Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
                                }
                            }

                            //   System.out.println("position bottom"+position);
                        }
                    })
                callPushNotification(
                    typeValue,
                    limitValue.toString(),
                    pageUnReadValue.toString()
                )
            } else {
                callPushNotification(
                    typeValue,
                    limitValue.toString(),
                    pageUnReadValue.toString()
                )
            }
        }
        readTxt!!.setOnClickListener {
            callTypeStatus = "2"
            markRead!!.setVisibility(View.GONE)
            msgReadSelect!!.setVisibility(View.GONE)
            messageUnRead!!.setVisibility(View.GONE)
            msgUnread!!.setVisibility(View.GONE)
            messageRead!!.setVisibility(View.GONE)
            markUnRead!!.setVisibility(View.GONE)
            if (isProgressVisible) {
                mProgressRelLayout!!.clearAnimation()
                mProgressRelLayout!!.setVisibility(View.GONE)
                isProgressVisible = false
            }
            // messageUnRead!!.setBackgroundResource(R.drawable.check_box_header);
            // messageUnRead!!.setBackgroundResource(R.drawable.check_box_header);
            PreferenceManager().setisVisibleUnreadBox(mContext,false)
            //isVisibleUnreadBox = false
            PreferenceManager().setisVisibleReadBox(mContext,false)
            //  System.out.println(" size of array"+AppController().mMessageReadList.size);

            //  System.out.println(" size of array"+AppController().mMessageReadList.size);
            typeValue = "2"
            limitValue = 20
            pageReadValue = 0
            isReadAvailable = false
            if (mMessageReadList.size > 0) {
                readTxt!!.setBackgroundResource(R.drawable.notification_read)
                unreadTxt!!.setBackgroundResource(R.drawable.notification_unread)
                readTxt!!.setTextColor(
                    mContext.getResources().getColor(R.color.white)
                )
                unreadTxt!!.setTextColor(
                    mContext.getResources()
                        .getColor(R.color.split_bg)
                )
                readList.visibility=View.VISIBLE
                readList.layoutManager=LinearLayoutManager(mContext)
                //set adapter
                readMessageAdapter = ReadMessageAdapter(
                    mContext,
                    mMessageReadList,
                    messageRead!!,
                    markUnRead!!,
                    markRead!!,
                    msgReadSelect!!,
                    object : ClickListener {
                        override fun onPositionClicked(position: Int) {
                            markRead.setVisibility(View.GONE)
                            markUnRead!!.setVisibility(View.GONE)
                            messageRead!!.setVisibility(View.GONE)
                            msgReadSelect!!.setVisibility(View.GONE)
                            messageUnRead!!.setVisibility(View.GONE)
                            msgUnread!!.setVisibility(View.GONE)
                            PreferenceManager().setisVisibleUnreadBox(mContext,false)
                           //PreferenceManager().setisVisibleUnreadBox(mContext,false)
                            PreferenceManager().setisVisibleReadBox(mContext,false)
                            val intent: Intent = Intent(
                                mContext,
                                MessageDetail::class.java
                            )
                            intent.putExtra(
                                "title",
                                mMessageReadList.get(position).title
                            )
                            intent.putExtra(
                                "message",
                                mMessageReadList.get(position).message
                            )
                            intent.putExtra(
                                "date",
                                mMessageReadList.get(position).day
                            )
                            ///intent.putExtra("position",position);
                            mContext.startActivity(intent)
                        }

                        override fun onLongClicked(position: Int) {
                            markRead.setVisibility(View.GONE)
                            markUnRead!!.setVisibility(View.GONE)
                            PreferenceManager().setclick_count_read(mContext,0)
                            // System.out.println("checkbox read visible:::::");
                            messageRead!!.setBackgroundResource(R.drawable.check_box_header)
                            PreferenceManager().setisVisibleReadBox(mContext,true)
                            PreferenceManager().setisVisibleUnreadBox(mContext,false)
                           //PreferenceManager().setisVisibleUnreadBox(mContext,false)
                            for (i in 0 until mMessageReadList.size) {
                                mMessageReadList.get(i).isMarked=false
                            }
                            for (i in 0 until mMessageUnreadList.size) {
                                mMessageUnreadList.get(i).isChecked=false
                            }
                            if (mMessageUnreadList.size > 0) {
                                unReadList.adapter=unReadMessageAdapter
                                unReadMessageAdapter!!.notifyDataSetChanged()
                                // unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                            }
                            readMessageAdapter!!.notifyDataSetChanged()
                            //readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                            messageRead!!.setVisibility(View.VISIBLE)
                            msgReadSelect!!.setVisibility(View.VISIBLE)
                            messageUnRead!!.setVisibility(View.GONE)
                            msgUnread!!.setVisibility(View.GONE)
                            noMsgAlertRelative!!.setVisibility(View.GONE)
                            noMsgText!!.setVisibility(View.GONE)
                        }
                    }
                )
                readList.adapter=readMessageAdapter
                msgUnreadLinear!!.setVisibility(View.GONE)
                msgReadLinear!!.setVisibility(View.VISIBLE)
                if (isFromReadBottom) {
                    readList!!.scrollToPosition(
                        pageReadValue - 1
                    )
                }
               readMessageAdapter!!.notifyDataSetChanged()
                //readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                readList.adapter=readMessageAdapter
               // readList!!.setAdapter(readMessageAdapter)
                readMessageAdapter!!.setOnBottomReachedListener(object :
                    OnBottomReachedListener {
                    override fun onBottomReached(position: Int) {
                        if (position == mMessageReadList.size - 2) {
                            if (!isReadAvailable) {
                                isFromReadBottom = true
                                if (isVisibleReadBox) {
                                    markRead!!.setVisibility(View.GONE)
                                    markUnRead!!.setVisibility(View.GONE)
                                    PreferenceManager().setclick_count_read(mContext,0)
                                    // System.out.println("checkbox read visible:::::");
                                    messageRead!!.setBackgroundResource(
                                        R.drawable.check_box_header
                                    )
                                    PreferenceManager().setisVisibleReadBox(mContext,true)
                                    PreferenceManager().setisVisibleUnreadBox(mContext,false)
                                    //isVisibleUnreadBox = false
                                    for (i in 0 until mMessageReadList.size) {
                                        mMessageReadList.get(i).isMarked=false
                                    }
                                    for (i in 0 until mMessageUnreadList.size) {
                                        mMessageUnreadList.get(i).isChecked=false
                                    }
                                }
                                typeValue = "2"
                                limitValue = 20
                                pageReadValue =
                                    pageReadValue + limitValue
                                callPushNotification(
                                    typeValue,
                                    limitValue.toString(),
                                    pageReadValue.toString()
                                )
                                //  Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
                        }

                        //    System.out.println("position bottom"+position);
                    }
                })
                callPushNotification(
                    typeValue,
                    limitValue.toString(),
                    pageReadValue.toString()
                )
            } else {
                callPushNotification(
                    typeValue,
                    limitValue.toString(),
                    pageReadValue.toString()
                )
            }
        }

    }*/

    private fun callfavouriteStatusApi(favourite:String, pushId:String){
        var notfav=NotificationFavApiModel(PreferenceManager().getUserId(mContext).toString(),
            favourite,pushId)
        val call: Call<NotificationFavouriteModel> = ApiClient.getClient.notification_favourite(
            notfav,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString())

        call.enqueue(object : Callback<NotificationFavouriteModel> {
            override fun onFailure(call: Call<NotificationFavouriteModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<NotificationFavouriteModel>,
                response: Response<NotificationFavouriteModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){


                    }
                }

            }
        })
    }
    private fun callstatusapi(statusread:String, pushId:String){
        var notstatus=NotificationStatusApiModel(PreferenceManager().getUserId(mContext).toString()
            ,statusread,pushId)

        val call: Call<NotificationStatusModel> = ApiClient.getClient.notification_status(
            notstatus,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString())
        call.enqueue(object : Callback<NotificationStatusModel> {
            override fun onFailure(call: Call<NotificationStatusModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<NotificationStatusModel>,
                response: Response<NotificationStatusModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){
                        markRead!!.setVisibility(View.GONE)
                        markUnRead!!.setVisibility(View.GONE)
                        messageRead!!.setVisibility(View.GONE)
                        msgReadSelect!!.setVisibility(View.GONE)
                        messageUnRead!!.setVisibility(View.GONE)
                        msgUnread!!.setVisibility(View.GONE)
                        PreferenceManager().setisVisibleUnreadBox(mContext,false)
                        //isVisibleUnreadBox = false
                        PreferenceManager().setisVisibleReadBox(mContext,false)

                        if (statusread.equals("1")) {
                            typeValue = "1"
                            limitValue = 20
                            pageUnReadValue = 0
                            callPushNotification(
                                typeValue,
                                limitValue.toString(),
                                pageUnReadValue.toString()
                            )
                        } else {
                            typeValue = "2"
                            limitValue = 20
                            pageReadValue = 0
                            callPushNotification(
                                typeValue,
                                limitValue.toString(),
                                pageReadValue.toString()
                            )
                        }

                    }
                }

            }
        })
    }
    private fun callPushNotification(type:String , limit:String , page:String ){
        Log.e("call","true")


    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (task.isComplete) {
            val token = task.result
           tokenM = token
        }
    }
    mMessageReadListFav = ArrayList()
    mMessageUnreadListFav = ArrayList()
    mMessageReadListFav =
        mMessageReadList.clone() as ArrayList<PushNotificationModel>
    mMessageUnreadListFav =
        unreadArray.clone() as ArrayList<PushNotificationModel>

var notmodel=NotificationsNewApiModel( tokenM,"2",PreferenceManager().getUserId(mContext).toString(),type,limit,page)
    val call: Call<NotificationNewResponseModel> = ApiClient.getClient.notifications_new(
        notmodel,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString()
       )

    call.enqueue(object : Callback<NotificationNewResponseModel> {
        override fun onFailure(call: Call<NotificationNewResponseModel>, t: Throwable) {
            Log.e("Failed", t.localizedMessage)

        }

        override fun onResponse(
            call: Call<NotificationNewResponseModel>,
            response: Response<NotificationNewResponseModel>
        ) {
            Log.e("callapi","true")
            val responsedata = response.body()
            if (responsedata!!.responsecode.equals("200")){
                if (responsedata!!.response.statuscode.equals("303")){

                    if (type.equals("1")) {
                        if (responsedata!!.response.data.size < 20) {
                            isAvailable = true
                        }
                    } else {
                        if (responsedata!!.response.data.size < 20) {
                            isReadAvailable = true
                        }
                    }
                    if (responsedata!!.response.data.size > 0) {
                        //  System.out.println("working");
                        dataLength = responsedata!!.response.data.size
                       Log.e("datalength",dataLength.toString())
                        for (i in 0 until responsedata!!.response.data.size) {
                            Log.e("type",type)
                            if (type.equals("1")) {
                                notificationList= ArrayList()
                                val unreadObject = responsedata!!.response.data[i]
                                notificationList.add(unreadObject)

                                if (unreadArray.size == 0) {
                                    Log.e("add",addUnreadMessage(
                                        unreadObject,
                                        i,
                                        responsedata!!.response.data.size
                                    )!!.title)
                                    var nmodel=addUnreadMessage(
                                        unreadObject,
                                        i,
                                        responsedata!!.response.data.size
                                    )!!


                                    unreadArray.add(
                                       nmodel
                                    )
                                    PreferenceManager().setUnreadList(mContext,null)
                                    PreferenceManager().setUnreadList(mContext,unreadArray)
                                    Log.e("unreadlisrtsize",unreadArray.size.toString())
                                } else {
                                    val pushId = unreadObject.pushid
                                    var isFound = false
                                    for (j in 0 until unreadArray.size) {
                                        if (pushId.equals(
                                                unreadArray[j].pushid)
                                        ) {
                                            isFound = true
                                        }
                                    }
                                    if (!isFound) {
                                        unreadArray.add(
                                            addUnreadMessage(unreadObject, i, responsedata.response.data.size)!!)
                                        PreferenceManager().setUnreadList(mContext,null)
                                        PreferenceManager().setUnreadList(mContext,unreadArray)
                                        Collections.sort(unreadArray,
                                            Comparator<PushNotificationModel> { o1, o2 ->
                                                o1.dateTime.compareTo(o2.dateTime)
                                            })
                                    }
                                }
                            }
                            else {
                                notificationList=ArrayList()
                                val readObject =responsedata.response.data[i]
                                notificationList.add(readObject)
                                if (mMessageReadList.size == 0) {
                                    mMessageReadList.add(
                                        addReadMessage(
                                            readObject,
                                            i,
                                            responsedata.response.data.size
                                        )!!
                                    )
                                    PreferenceManager().setMessageReadList(mContext,null)
                                    PreferenceManager().setMessageReadList(mContext,mMessageReadList)

                                    // Collections.sort(AppController().mMessageReadList);
                                } else {
                                    val pushID = readObject.pushid
                                    var isFoundPush = false
                                    for (j in 0 until mMessageReadList.size) {
                                        if (pushID.equals(
                                                mMessageReadList.get(j).pushid)
                                        ) {
                                            isFoundPush = true
                                        }
                                    }
                                    if (!isFoundPush) {
                                        mMessageReadList.add(
                                            addUnreadMessage(
                                                readObject,
                                                i,
                                                responsedata.response.data.size
                                            )!!
                                        )
                                        PreferenceManager().setMessageReadList(mContext,null)
                                        PreferenceManager().setMessageReadList(mContext,mMessageReadList)
                                    }
                                }
                            }
                            Log.e("unreadlisrtsize1",unreadArray.size.toString())
                        }
                        Log.e("calltypestatus",callTypeStatus)
                        Log.e("unreadlisrtsize2",unreadArray.size.toString())
                        if (callTypeStatus.equals("1")) {
                            msgUnreadLinear!!.setVisibility(View.VISIBLE)
                            msgReadLinear!!.setVisibility(View.GONE)
                            noMsgAlertRelative!!.setVisibility(View.GONE)
                            noMsgText!!.setVisibility(View.GONE)
                            //  System.out.println("case worked"+type);
                            Log.e("unreadlisrtsize3",unreadArray.size.toString())
                            if (unreadArray.size > 0) {
                                //     System.out.println("case worked if");
                                //set adapter
                                unreadTxt!!.setBackgroundResource(R.drawable.notification_read)
                                readTxt!!.setBackgroundResource(R.drawable.notification_unread)
                                unreadTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.white)
                                )
                                readTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.split_bg)
                                )
                                Collections.sort(unreadArray,
                                    Comparator<PushNotificationModel> { o1, o2 ->
                                        o1.dateTime.compareTo(o2.dateTime)
                                    })
                                Collections.reverse(unreadArray)
                                noMsgAlertRelative!!.visibility=View.GONE
                                msgUnreadLinear!!.visibility=View.VISIBLE
                                msgReadLinear!!.visibility=View.GONE
                                isProgressVisible=false

                                Log.e("adaptrunread","set")
                                unReadList.visibility=View.VISIBLE
                                unReadList.layoutManager=LinearLayoutManager(mContext)
                                unReadMessageAdapter =
                                    UnReadMessageAdapter(
                                        mContext,
                                        unreadArray,
                                        messageUnRead!!,
                                        markRead!!,
                                        markUnRead!!,
                                        msgUnread!!,/*object :OnItemLongClickListener{
                                            override fun onItemLongClick(
                                                p0: AdapterView<*>?,
                                                p1: View?,
                                                p2: Int,
                                                p3: Long
                                            ): Boolean {
                                                Log.e("longclickadapter","longclick")
                                                markRead.setVisibility(
                                                    View.GONE
                                                )
                                                markUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                messageUnRead!!.setBackgroundResource(
                                                    R.drawable.check_box_header
                                                )
                                               PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                                PreferenceManager().setclick_count(mContext,0)
                                                for (i in 0 until mMessageReadList.size) {
                                                    mMessageReadList.get(i).isMarked=false

                                                }
                                                for (i in 0 until mMessageUnreadList.size) {
                                                    mMessageUnreadList.get(i).isChecked=false

                                                }
                                                if (mMessageReadList.size > 0) {
                                                    readMessageAdapter!!.notifyDataSetChanged()
                                                    // readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                                                }
                                                unReadList.adapter=unReadMessageAdapter
                                                unReadMessageAdapter!!.notifyDataSetChanged()
                                                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                                                messageUnRead!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                msgUnread!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                messageRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgReadSelect!!.setVisibility(
                                                    View.GONE
                                                )
                                                return false
                                            }
                                        },object :
                                            OnItemClickListener {

                                            override fun onItemClick(
                                                p0: AdapterView<*>?,
                                                p1: View?,
                                                p2: Int,
                                                p3: Long
                                            ) {
                                                TODO("Not yet implemented")
                                            }


                                        }*/
                                        object : ClickListener {
                                            override fun onPositionClicked(position: Int) {
                                                Log.e("c3","true")
                                            }
                                            override fun onLongClicked(position: Int) {
                                                Log.e("lc3","true")
                                                markRead.setVisibility(
                                                    View.GONE
                                                )
                                                markUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                messageUnRead!!.setBackgroundResource(
                                                    R.drawable.check_box_header
                                                )
                                                PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                                //isVisibleUnreadBox = true
                                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                                PreferenceManager().setclick_count(mContext,0)
                                                for (i in 0 until mMessageReadList.size) {
                                                    mMessageReadList.get(i).isMarked=false

                                                }
                                                for (i in 0 until mMessageUnreadList.size) {
                                                    mMessageUnreadList.get(i).isChecked=false
                                                }
                                                if (mMessageReadList.size > 0) {
                                                    readMessageAdapter!!.notifyDataSetChanged()
                                                    readMessageAdapter!!.notifyItemRangeChanged(0,
                                                        readMessageAdapter!!.getItemCount());
                                                }
                                                unReadList.adapter=unReadMessageAdapter
                                                unReadMessageAdapter!!.notifyDataSetChanged()
                                                unReadMessageAdapter!!.notifyItemRangeChanged(0,
                                                    unReadMessageAdapter!!.getItemCount());
                                                messageUnRead!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                msgUnread!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                messageRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgReadSelect!!.setVisibility(
                                                    View.GONE
                                                )
                                            }
                                        })
                                unReadList.adapter=unReadMessageAdapter

                                unReadMessageAdapter!!.notifyDataSetChanged()
                                if (isFromUnReadBottom) {
                                    unReadList!!.scrollToPosition(
                                        pageUnReadValue - 2
                                    )
                                } else {

                                }
                                unReadList.adapter=unReadMessageAdapter
                                         /*unReadMessageAdapter!!.notifyItemRangeChanged(0,
                                             unReadMessageAdapter!!.getItemCount());*/
                                unReadList.adapter=unReadMessageAdapter
                                unReadList!!.setAdapter(
                                    unReadMessageAdapter
                                )

                                unReadMessageAdapter!!.setOnBottomReachedListener(
                                    object : OnBottomReachedListener {
                                        override fun onBottomReached(position: Int) {
                                            if (position == mMessageUnreadList.size - 1) {
                                                if (!isAvailable) {
                                                    isFromUnReadBottom =
                                                        true
                                                   
                                                   // if (isVisibleUnreadBox) {
                                                    if (PreferenceManager().getisVisibleUnreadBox(mContext)==true){
                                                        markRead!!.setVisibility(
                                                            View.GONE
                                                        )
                                                        markUnRead!!.setVisibility(
                                                            View.GONE
                                                        )
                                                        messageUnRead!!.setBackgroundResource(
                                                            R.drawable.check_box_header
                                                        )
                                                        PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                                       //PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                                        PreferenceManager().setisVisibleReadBox(mContext,false)
                                                        PreferenceManager().setclick_count(mContext,0)
                                                        for (i in 0 until mMessageReadList.size) {
                                                           mMessageReadList.get(i).isMarked=false

                                                        }
                                                        for (i in 0 until mMessageUnreadList.size) {
                                                            mMessageUnreadList.get(i).isChecked=false

                                                        }
                                                    }
                                                    typeValue = "1"
                                                    limitValue = 20
                                                    pageUnReadValue =
                                                        pageUnReadValue + limitValue
                                                    callPushNotification(
                                                        type,
                                                        limitValue.toString(),
                                                        pageUnReadValue.toString()
                                                    )
                                                    // Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            //   System.out.println("position bottom"+position);
                                        }
                                    })
                            } else {
                                //    System.out.println("case worked if");
                                unreadTxt!!.setBackgroundResource(R.drawable.notification_read)
                                readTxt!!.setBackgroundResource(R.drawable.notification_unread)
                                unreadTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.white)
                                )
                                readTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.split_bg)
                                )
                               // noMsgAlertRelative!!.setVisibility(View.VISIBLE)
                                //noMsgText!!.setVisibility(View.VISIBLE)
                                //noMsgText!!.setText("Currently you have no messages")
                            }
                        } else {
                            noMsgAlertRelative!!.setVisibility(View.GONE)
                            noMsgText!!.setVisibility(View.GONE)
                            // System.out.println("working else");
                            if (mMessageReadList.size > 0) {
                                readTxt!!.setBackgroundResource(R.drawable.notification_read)
                                unreadTxt!!.setBackgroundResource(R.drawable.notification_unread)
                                readTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.white)
                                )
                                unreadTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.split_bg)
                                )
                                //set adapter
                                Collections.sort(mMessageReadList,
                                    Comparator<PushNotificationModel> { o1, o2 ->
                                        o1.dateTime.compareTo(o2.dateTime)
                                    })
                                Collections.reverse(mMessageReadList)
                                readList.visibility=View.VISIBLE
                                readList.layoutManager=LinearLayoutManager(mContext)
                               readMessageAdapter =
                                    ReadMessageAdapter(
                                        mContext,
                                        mMessageReadList,
                                        messageRead!!,
                                        markUnRead!!,
                                        markRead!!,
                                        msgReadSelect!!,
                                        object : ClickListener {
                                            override fun onPositionClicked(position: Int) {
                                                Log.e("read","position clicked")
                                                markRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                markUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                messageRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgReadSelect!!.setVisibility(
                                                    View.GONE
                                                )
                                                messageUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgUnread!!.setVisibility(
                                                    View.GONE
                                                )
                                                PreferenceManager().setisVisibleUnreadBox(mContext,false)
                                               //PreferenceManager().setisVisibleUnreadBox(mContext,false)
                                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                                val intent = Intent(
                                                    mContext,
                                                    MessageDetail::class.java
                                                )
                                                intent.putExtra(
                                                    "title",
                                                    mMessageReadList.get(position)
                                                        .title
                                                )
                                                intent.putExtra(
                                                    "message",
                                                    mMessageReadList.get(position)
                                                        .message
                                                )
                                                intent.putExtra(
                                                    "date",
                                                   mMessageReadList.get(position)
                                                        .day
                                                )
                                                ///intent.putExtra("position",position);
                                                mContext.startActivity(
                                                    intent
                                                )
                                            }

                                            override fun onLongClicked(position: Int) {
                                                markRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                markUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                PreferenceManager().setclick_count_read(mContext,0)
                                                // System.out.println("checkbox read visible:::::");
                                                messageRead!!.setBackgroundResource(
                                                    R.drawable.check_box_header
                                                )
                                                PreferenceManager().setisVisibleReadBox(mContext,true)
                                               PreferenceManager().setisVisibleUnreadBox(mContext,false)
                                                for (i in 0 until mMessageReadList.size) {
                                                    mMessageReadList.get(i)
                                                        .isMarked=false
                                                }
                                                for (i in 0 until mMessageUnreadList.size) {
                                                    mMessageUnreadList.get(i)
                                                        .isChecked=false
                                                }
                                                if (mMessageUnreadList.size > 0) {
                                                    unReadList.adapter=unReadMessageAdapter
                                                    unReadMessageAdapter!!.notifyDataSetChanged()
                                                     unReadMessageAdapter!!.notifyItemRangeChanged(0,
                                                         unReadMessageAdapter!!.getItemCount());
                                                }
                                                readMessageAdapter!!.notifyDataSetChanged()
                                                readMessageAdapter!!.notifyItemRangeChanged(0,
                                                    readMessageAdapter!!.getItemCount());
                                                messageRead!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                msgReadSelect!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                messageUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgUnread!!.setVisibility(
                                                    View.GONE
                                                )
                                                noMsgAlertRelative!!.setVisibility(
                                                    View.GONE
                                                )
                                                noMsgText!!.setVisibility(
                                                    View.GONE
                                                )
                                            }
                                        }
                                    )
                                readList.adapter=readMessageAdapter
                                msgUnreadLinear!!.setVisibility(View.GONE)
                                msgReadLinear!!.setVisibility(View.VISIBLE)
                                if (isFromReadBottom) {
                                    readList!!.scrollToPosition(
                                       pageReadValue - 1
                                    )
                                }
                                readMessageAdapter!!.notifyDataSetChanged()
                                readMessageAdapter!!.notifyItemRangeChanged(0, readMessageAdapter!!.getItemCount());
                                readList!!.setAdapter(
                                    readMessageAdapter
                                )
                                readMessageAdapter!!.setOnBottomReachedListener(
                                    object : OnBottomReachedListener {
                                        override fun onBottomReached(position: Int) {
                                            if (position == mMessageReadList.size - 2) {
                                                if (!isReadAvailable) {
                                                    isFromReadBottom =
                                                        true
                                                    if (PreferenceManager().getisVisibleReadBox(mContext)==true){
                                                    //if (isVisibleReadBox) {
                                                        markRead!!.setVisibility(
                                                            View.GONE
                                                        )
                                                        markUnRead!!.setVisibility(
                                                            View.GONE
                                                        )
                                                        PreferenceManager().setclick_count_read(mContext,0)
                                                        // System.out.println("checkbox read visible:::::");
                                                        messageRead!!.setBackgroundResource(
                                                            R.drawable.check_box_header
                                                        )
                                                        PreferenceManager().setisVisibleReadBox(mContext,true)
                                                       PreferenceManager().setisVisibleUnreadBox(mContext,false)
                                                        for (i in 0 until mMessageReadList.size) {
                                                            mMessageReadList.get(i)
                                                                .isMarked=false
                                                        }
                                                        for (i in 0 until mMessageUnreadList.size) {
                                                            mMessageUnreadList.get(i)
                                                                .isChecked=false
                                                        }
                                                    }
                                                    typeValue = "2"
                                                    limitValue = 20
                                                    pageReadValue =
                                                        pageReadValue + limitValue
                                                    callPushNotification(
                                                        type,
                                                        limitValue.toString(),
                                                        pageReadValue.toString()
                                                    )
                                                    //  Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
                                                }
                                                //Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
                                            }

                                            //    System.out.println("position bottom"+position);
                                        }
                                    })
                            } else {
                                readTxt!!.setBackgroundResource(R.drawable.notification_read)
                                unreadTxt!!.setBackgroundResource(R.drawable.notification_unread)
                                readTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.white)
                                )
                                unreadTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.split_bg)
                                )
                                // System.out.println("working else");
                                msgUnreadLinear!!.setVisibility(View.GONE)
                                msgReadLinear!!.setVisibility(View.GONE)
                                noMsgAlertRelative!!.setVisibility(View.VISIBLE)
                                noMsgText!!.setVisibility(View.VISIBLE)
                                noMsgText!!.setText("Currently you have no messages")
                            }
                        }
                    }
                    else {
                        if (callTypeStatus.equals("1")) {
                            if (unreadArray.size > 0) {

                                //set adapter
                                unreadTxt!!.setBackgroundResource(R.drawable.notification_read)
                                readTxt!!.setBackgroundResource(R.drawable.notification_unread)
                                unreadTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.white)
                                )
                                readTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.split_bg)
                                )
                                Collections.sort(unreadArray,
                                    Comparator<PushNotificationModel> { o1, o2 ->
                                        o1.dateTime.compareTo(o2.dateTime)
                                    })
                                Collections.reverse(unreadArray)
                                Log.e("adaptr","set4")
                                unReadList.layoutManager=LinearLayoutManager(mContext)
                                unReadMessageAdapter =
                                    UnReadMessageAdapter(
                                        mContext,
                                        unreadArray,
                                        messageUnRead!!,
                                        markRead!!,
                                        markUnRead!!,
                                        msgUnread!!,/*object :OnItemLongClickListener{
                                            override fun onItemLongClick(
                                                p0: AdapterView<*>?,
                                                p1: View?,
                                                p2: Int,
                                                p3: Long
                                            ): Boolean {
                                                Log.e("longclickadapter","longclick")
                                                markRead.setVisibility(
                                                    View.GONE
                                                )
                                                markUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                messageUnRead!!.setBackgroundResource(
                                                    R.drawable.check_box_header
                                                )
                                               PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                                PreferenceManager().setclick_count(mContext,0)
                                                for (i in 0 until mMessageReadList.size) {
                                                    mMessageReadList.get(i).isMarked=false

                                                }
                                                for (i in 0 until mMessageUnreadList.size) {
                                                    mMessageUnreadList.get(i).isChecked=false

                                                }
                                                if (mMessageReadList.size > 0) {
                                                    readMessageAdapter!!.notifyDataSetChanged()
                                                    // readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                                                }
                                                unReadList.adapter=unReadMessageAdapter
                                                unReadMessageAdapter!!.notifyDataSetChanged()
                                                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                                                messageUnRead!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                msgUnread!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                messageRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgReadSelect!!.setVisibility(
                                                    View.GONE
                                                )
                                                return false
                                            }
                                        },object :
                                            OnItemClickListener {

                                            override fun onItemClick(
                                                p0: AdapterView<*>?,
                                                p1: View?,
                                                p2: Int,
                                                p3: Long
                                            ) {
                                                TODO("Not yet implemented")
                                            }


                                        }*/
                                        object : ClickListener {
                                            override fun onPositionClicked(position: Int) {
                                                Log.e("c4","true")
                                            }
                                            override fun onLongClicked(position: Int) {
                                                Log.e("lc4","true")
                                                markRead.setVisibility(
                                                    View.GONE
                                                )
                                                markUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                messageUnRead!!.setBackgroundResource(
                                                    R.drawable.check_box_header
                                                )
                                              PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                                PreferenceManager().setclick_count(mContext,0)
                                                for (i in 0 until mMessageReadList.size) {
                                                    mMessageReadList.get(i).isMarked=false

                                                }
                                                for (i in 0 until mMessageUnreadList.size) {
                                                    mMessageUnreadList.get(i).isChecked=false

                                                }
                                                if (mMessageReadList.size > 0) {
                                                    readMessageAdapter!!.notifyDataSetChanged()
                                                    readMessageAdapter!!.notifyItemRangeChanged(0,
                                                        readMessageAdapter!!.getItemCount());
                                                }
                                                unReadList.adapter=unReadMessageAdapter
                                                unReadMessageAdapter!!.notifyDataSetChanged()
                                                unReadMessageAdapter!!.notifyItemRangeChanged(0,
                                                    unReadMessageAdapter!!.getItemCount());
                                                messageUnRead!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                msgUnread!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                messageRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgReadSelect!!.setVisibility(
                                                    View.GONE
                                                )
                                            }
                                        })
                                unReadList.adapter=unReadMessageAdapter
                                msgUnreadLinear!!.setVisibility(View.VISIBLE)
                                msgReadLinear!!.setVisibility(View.GONE)
                                unReadMessageAdapter!!.notifyDataSetChanged()
                                 unReadMessageAdapter!!.notifyItemRangeChanged(0, unReadMessageAdapter!!.getItemCount());
                                unReadList!!.setAdapter(
                                    unReadMessageAdapter
                                )
                                unReadMessageAdapter!!.setOnBottomReachedListener(
                                    object : OnBottomReachedListener{
                                        override fun onBottomReached(position: Int) {
                                            if (position == mMessageUnreadList.size - 2) {
//                                                            if (dataLength<20)
//                                                            {
//                                                                if (!isAvailable)
//                                                                {
//                                                                    typeValue="1";
//                                                                    limitValue=20;
//                                                                    pageValue=pageValue+limitValue;
//                                                                    callPushNotification(type,String.valueOf(limitValue),String.valueOf(pageValue));
//                                                                    Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
                                            }

                                            //   System.out.println("position bottom"+position);
                                        }
                                    })
                            } else {
                                unreadTxt!!.setBackgroundResource(R.drawable.notification_read)
                                readTxt!!.setBackgroundResource(R.drawable.notification_unread)
                                unreadTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.white)
                                )
                                readTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.split_bg)
                                )
                                //   System.out.println("working else");
                                msgUnreadLinear!!.setVisibility(View.GONE)
                                msgReadLinear!!.setVisibility(View.GONE)
                                noMsgAlertRelative!!.setVisibility(View.VISIBLE)
                                noMsgText!!.setVisibility(View.VISIBLE)
                                noMsgText!!.setText("Currently you have no messages")
                            }
                        } else {
                            if (mMessageReadList.size > 0) {
                                readTxt!!.setBackgroundResource(R.drawable.notification_read)
                                unreadTxt!!.setBackgroundResource(R.drawable.notification_unread)
                                readTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.white)
                                )
                                unreadTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.split_bg)
                                )
                                Collections.sort(mMessageReadList,
                                    Comparator<PushNotificationModel> { o1, o2 ->
                                        o1.dateTime.compareTo(o2.dateTime)
                                    })
                                Collections.reverse(mMessageReadList)
                                //set adapter
                                readList.visibility=View.VISIBLE
                                readList.layoutManager=LinearLayoutManager(mContext)
                                readMessageAdapter =
                                    ReadMessageAdapter(
                                        mContext,
                                        mMessageReadList,
                                        messageRead!!,
                                        markUnRead!!,
                                        markRead!!,
                                        msgReadSelect!!,
                                        object : ClickListener {
                                            override fun onPositionClicked(position: Int) {
                                                Log.e("read1","position clicked")
                                                markRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                markUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                messageRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgReadSelect!!.setVisibility(
                                                    View.GONE
                                                )
                                                messageUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgUnread!!.setVisibility(
                                                    View.GONE
                                                )
                                               PreferenceManager().setisVisibleUnreadBox(mContext,false)
                                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                                val intent = Intent(
                                                    mContext,
                                                    MessageDetail::class.java
                                                )
                                                intent.putExtra(
                                                    "title",
                                                    mMessageReadList.get(position)
                                                        .title
                                                )
                                                intent.putExtra(
                                                    "message",
                                                    mMessageReadList.get(position)
                                                        .message
                                                )
                                                intent.putExtra(
                                                    "date",
                                                    mMessageReadList.get(position).day
                                                )
                                                ///intent.putExtra("position",position);
                                                mContext.startActivity(
                                                    intent
                                                )
                                            }

                                            override fun onLongClicked(position: Int) {
                                                markRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                markUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                PreferenceManager().setclick_count_read(mContext,0)
                                                // System.out.println("checkbox read visible:::::");
                                                messageRead!!.setBackgroundResource(
                                                    R.drawable.check_box_header
                                                )
                                                PreferenceManager().setisVisibleReadBox(mContext,true)
                                               PreferenceManager().setisVisibleUnreadBox(mContext,false)
                                                for (i in 0 until mMessageReadList.size) {
                                                   mMessageReadList.get(i)
                                                        .isMarked=false
                                                }
                                                for (i in 0 until mMessageUnreadList.size) {
                                                   mMessageUnreadList.get(i)
                                                        .isChecked=false
                                                }
                                                if (mMessageUnreadList.size > 0) {
                                                    unReadList.adapter=unReadMessageAdapter
                                                    unReadMessageAdapter!!.notifyDataSetChanged()
                                                }
                                                readMessageAdapter!!.notifyDataSetChanged()
                                                readMessageAdapter!!.notifyItemRangeChanged(0,
                                                    readMessageAdapter!!.getItemCount());
                                                messageRead!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                msgReadSelect!!.setVisibility(
                                                    View.VISIBLE
                                                )
                                                messageUnRead!!.setVisibility(
                                                    View.GONE
                                                )
                                                msgUnread!!.setVisibility(
                                                    View.GONE
                                                )
                                                noMsgAlertRelative!!.setVisibility(
                                                    View.GONE
                                                )
                                                noMsgText!!.setVisibility(
                                                    View.GONE
                                                )
                                            }
                                        }
                                    )
                                readList.adapter=readMessageAdapter
                                msgUnreadLinear!!.setVisibility(View.GONE)
                                msgReadLinear!!.setVisibility(View.VISIBLE)
                                readMessageAdapter!!.notifyDataSetChanged()
                                readMessageAdapter!!.notifyItemRangeChanged(0, readMessageAdapter!!.getItemCount());
                                readList.adapter=readMessageAdapter
                                /*readList!!.setAdapter(
                                    readMessageAdapter
                                )*/
                                readMessageAdapter!!.setOnBottomReachedListener(
                                    object : OnBottomReachedListener {
                                        override fun onBottomReached(position: Int) {
                                            if (position == mMessageReadList.size - 2) {
//                                                            if (dataLength>=19)
//                                                            {
//                                                                typeValue="2";
//                                                                limitValue=20;
//                                                                pageValue=pageValue+limitValue;
//                                                                callPushNotification(type,String.valueOf(limitValue),String.valueOf(pageValue));
//                                                                Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
//                                                            }
                                            }

                                            //  System.out.println("position bottom"+position);
                                        }
                                    })
                                //                                                System.out.println("working else");
//                                                msgUnreadLinear.setVisibility(View.GONE);
//                                                msgReadLinear.setVisibility(View.GONE);
//                                                noMsgAlertRelative.setVisibility(View.VISIBLE);
//                                                noMsgText.setVisibility(View.VISIBLE);
//                                                noMsgText.setText("Currently you have no messages");
                            } else {
                                readTxt!!.setBackgroundResource(R.drawable.notification_read)
                                unreadTxt!!.setBackgroundResource(R.drawable.notification_unread)
                                readTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.white)
                                )
                                unreadTxt!!.setTextColor(
                                    mContext.getResources()
                                        .getColor(R.color.split_bg)
                                )
                                msgUnreadLinear!!.setVisibility(View.GONE)
                                msgReadLinear!!.setVisibility(View.GONE)
                                noMsgAlertRelative!!.setVisibility(View.VISIBLE)
                                noMsgText!!.setVisibility(View.VISIBLE)
                                noMsgText!!.setText("Currently you have no messages")
                            }
                        }
                    }

                }
            }



        }
    })




    }


    private fun addUnreadMessage(obj: NotificationNewDataModel, i: Int, length: Int): PushNotificationModel? {
        //val model = PushNotificationModel()
        var ymodel:PushNotificationModel?=null
        var sdfcalender1: SimpleDateFormat? = null
        var sdfcalender2: SimpleDateFormat? = null
        sdfcalender1 = SimpleDateFormat("yyyy-MM-dd")
        sdfcalender2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
           var setPushid=obj.pushid
            var setMessage=obj.htmlmessage
            var seturl=obj.url
            var setdate=obj.date
            var settype=obj.type
            var setpushfrom=obj.push_from
            var setfavourite=obj.favourite
            var setstudentname=obj.student_name
            var setstatus="0"

            val studList = ArrayList<String>()
            val namesList =
                obj.student_name.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            for (name in namesList) {
                // System.out.println(name);
                studList.add(name)
            }
            var setstudentarray= ArrayList<String>()
            setstudentarray.addAll(studList)
            var settitle=obj.title
            var mDate=obj.date

            sdfcalender2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var compareDate = Date()
            compareDate = sdfcalender2.parse(mDate)
            var msgDate = Date()
            println("compareDate$compareDate")
            var setdatetime=compareDate

            try {
                msgDate = sdfcalender!!.parse(mDate)
            } catch (ex: ParseException) {
                //log.e("Date", "Parsing error");
            }
            val day = DateFormat.format("dd", msgDate) as String // 20
            sdfcalender1 = if (day.endsWith("1") && !day.endsWith("11")) {
                SimpleDateFormat("EEEE  d'st' MMMM")
            } else if (day.endsWith("2") && !day.endsWith("12")) {
                SimpleDateFormat("EEEE  d'nd' MMMM")
            } else if (day.endsWith("3") && !day.endsWith("13")) SimpleDateFormat("EEEE  d'rd' MMMM")
            else SimpleDateFormat(
                "EEEE  d'th' MMMM"
            )
            var mEventDate = Date()
            var setpushtime=""
            try {
                mEventDate = sdfcalender!!.parse(mDate)
                val format2 = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                val startTime = format2.format(mEventDate)
                setpushtime=startTime

            } catch (ex: ParseException) {
                //log.e("Date", "Parsing error2");
            }
            val newday = sdfcalender1.format(mEventDate)
            var setday=newday
           var setchecked:Boolean?=null
            if (mMessageUnreadListFav.size > 0 && i < length) {
                if (mMessageUnreadListFav.get(i).isChecked != null) {
                    setchecked=mMessageUnreadListFav.get(i).isChecked

                } else {
                    setchecked=false

                }
            } else {
                setchecked=false
            }
            ymodel=PushNotificationModel(0,setPushid,setMessage,seturl,setfavourite,setchecked,false,
            setstudentarray,setstatus,setpushtime,setdate,setpushfrom,settype,
            setstudentname,"",setday,"","","",settitle,"",setdatetime)
        } catch (ex: Exception) {
            // System.out.println("Exception is" + ex);
        }
return ymodel
    }

    private fun addReadMessage(obj: NotificationNewDataModel, i: Int, length: Int): PushNotificationModel? {

        var xmodel:PushNotificationModel?=null
        var sdfcalender1: SimpleDateFormat? = null
        var sdfcalender2: SimpleDateFormat? = null
        sdfcalender1 = SimpleDateFormat("yyyy-MM-dd")
        sdfcalender2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            var setPushid2=obj.pushid
            var setMessage2=obj.htmlmessage
            var seturl2=obj.url
            var setdate2=obj.date
            var settype2=obj.type
            var setpushfrom2=obj.push_from
            var setfavourite2=obj.favourite
            var settitle2=obj.title
            var setstudentname=obj.student_name

            val studList2 = ArrayList<String>()
            val namesList =
                obj.student_name.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            for (name in namesList) {
                // System.out.println(name);
                studList2.add(name)
            }
            var setstudentarray2= ArrayList<String>()
            setstudentarray2.addAll(studList2)
            val mDate = obj.date
            // System.out.println("DatesArray:" + mDate);
            var msgDate = Date()
            sdfcalender2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var compareDate = Date()
            compareDate = sdfcalender2.parse(mDate)
            println("compare date$compareDate")
            var setdatetime2=compareDate

            try {
                msgDate = sdfcalender!!.parse(mDate)
            } catch (ex: ParseException) {
                //log.e("Date", "Parsing error3");
            }
            val day = DateFormat.format("dd", msgDate) as String // 20
            sdfcalender1 = if (day.endsWith("1") && !day.endsWith("11")) {
                SimpleDateFormat("EEEE  d'st' MMMM")
            } else if (day.endsWith("2") && !day.endsWith("12")) {
                SimpleDateFormat("EEEE  d'nd' MMMM")
            } else if (day.endsWith("3") && !day.endsWith("13"))
                SimpleDateFormat("EEEE  d'rd' MMMM") else SimpleDateFormat(
                "EEEE  d'th' MMMM"
            )
            var mEventDate = Date()
            var setpushtime2=""
            try {
                mEventDate = sdfcalender!!.parse(mDate)
                val format2 = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                val startTime = format2.format(mEventDate)
                setpushtime2=startTime

            } catch (ex: ParseException) {
                //log.e("Date", "Parsing error4");
            }
            val newday = sdfcalender1.format(mEventDate)
            var setday=newday
            var setmarked:Boolean?=null
            if (mMessageReadListFav.size > 0 && i < length) {
                if (mMessageReadListFav.get(i).isMarked != null) {
                    setmarked=mMessageReadListFav.get(i).isMarked

                } else {
                    setmarked=false
                }
            } else {
                setmarked=false
            }
            xmodel=PushNotificationModel(0,setPushid2,setMessage2,seturl2,setfavourite2,false,
            setmarked,studList2,"1",setpushtime2,setdate2,setpushfrom2,settype2,setstudentname,"",
            setday,"","","",settitle2,"",setdatetime2)
        } catch (ex: java.lang.Exception) {
            // System.out.println("Exception is" + ex);
        }
       return xmodel
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onClick(v: View?) {
        if (v == markRead) {
            markRead!!.setVisibility(View.GONE)
            markUnRead!!.setVisibility(View.GONE)
            messageRead!!.setVisibility(View.GONE)
            msgReadSelect!!.setVisibility(View.GONE)
            messageUnRead!!.setVisibility(View.GONE)
            msgUnread!!.setVisibility(View.GONE)
           PreferenceManager().setisVisibleUnreadBox(mContext,false)
            PreferenceManager().setisVisibleReadBox(mContext,false)
            if (AppUtils().isNetworkConnected(mContext)) {
                //clearBadge();
                var readList = ""
                var k = 0
                val mTempArray = ArrayList<PushNotificationModel>()
                val mTempArrayremoved = ArrayList<PushNotificationModel>()
                //mTempArray.addAll(AppController().mMessageUnreadList)
                mTempArray.addAll(unreadArray)
                mTempArrayremoved.addAll(unreadArray)

                for (i in mTempArray.indices) {
                    Log.e("mesgereadtemp",mTempArray.size.toString())
                    val isFoundId = false
                    if (mTempArray[i].isChecked) {
                        if (k == 0) {
                            readList = mTempArray[i].pushid
                        } else {
                            readList = readList + "," + mTempArray[i].pushid
                        }
                        for (j in unreadArray.indices) {
                            Log.e("mesgeread",unreadArray.size.toString())
                            var pushReadId: String =""
                            pushReadId =   unreadArray.get(j).pushid
                            if (pushReadId.equals(mTempArray[i].pushid)) {
                                Log.e("equal",unreadArray[j].title)
                                //  isFoundId=true;
                                //unreadArray.removeAt(j)
                                if (i==mTempArrayremoved.size){
                                    mTempArrayremoved.removeAt(j-1)
                                }else{
                                    mTempArrayremoved.removeAt(j)
                                }

                               // mTempArrayremoved.removeAt(j)
                            }
                        }
                        /* for (j in 0 until AppController().mMessageUnreadList.size) {
                             val pushReadId: String =
                                 AppController().mMessageUnreadList.get(j).pushid
                             if (pushReadId.equals(mTempArray[i].pushid)) {
                                 //  isFoundId=true;
                                 AppController().mMessageUnreadList.removeAt(j)
                             }
                         }*/
                        k = k + 1
                        // AppController().mMessageUnreadList.remove(i);
                    }
                }
unreadArray=ArrayList()
                unreadArray.addAll(mTempArrayremoved)
                //  System.out.println("Read List" + readList);
                callstatusapi("1", readList)
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity?,
                    "Network Error",
                    getString(R.string.no_internet),
                    R.drawable.nonetworkicon,
                    R.drawable.roundred
                )
            }
        } else if (v ==markUnRead) {
            markRead!!.setVisibility(View.GONE)
            markUnRead!!.setVisibility(View.GONE)
            messageRead!!.setVisibility(View.GONE)
            msgReadSelect!!.setVisibility(View.GONE)
            messageUnRead!!.setVisibility(View.GONE)
            msgUnread!!.setVisibility(View.GONE)
           PreferenceManager().setisVisibleUnreadBox(mContext,false)
            PreferenceManager().setisVisibleReadBox(mContext,false)
            if (AppUtils().isNetworkConnected(mContext)) {
                var readList = ""
                var k = 0
                val mTempReadArray = ArrayList<PushNotificationModel>()
                val mTempReadArrayremoved = ArrayList<PushNotificationModel>()
                mTempReadArray.addAll(mMessageReadList)
                mTempReadArrayremoved.addAll(mMessageReadList)

                for (i in mTempReadArray.indices) {
                    if (mTempReadArray[i].isMarked) {
                        if (k == 0) {
                            readList = mTempReadArray[i].pushid
                        } else {
                            readList = readList + "," + mTempReadArray[i].pushid
                        }
                        for (j in 0 until mMessageReadList.size) {
                            val pushReadId: String =
                                mMessageReadList.get(j).pushid
                            if (pushReadId.equals(
                                    mTempReadArray[i].pushid
                                )
                            ) {
                                //  isFoundId=true;
                                //mMessageReadList.removeAt(j)
                                if (i==mTempReadArrayremoved.size){
                                    mTempReadArrayremoved.removeAt(j-1)
                                }else{
                                    mTempReadArrayremoved.removeAt(j)
                                }
                            }
                        }
                        k = k + 1
                    }
                    // AppController().mMessageReadList.remove(i);
                }
                //      System.out.println("Unread List" + readList);
                mMessageReadList=ArrayList()
                mMessageReadList.addAll(mTempReadArrayremoved)
                callstatusapi("0", readList)

                // System.out.println("click next");
            }  else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity?,
                    "Network Error",
                    getString(R.string.no_internet),
                    R.drawable.nonetworkicon,
                    R.drawable.roundred
                )
            }
        } else if (v === messageRead) {
            val mCount: Int = PreferenceManager().getclick_count_read(mContext)
            if (PreferenceManager().getclick_count_read(mContext) % 2 === 0) {
                PreferenceManager().setisVisibleReadBox(mContext,true)
                markUnRead!!.setVisibility(View.GONE)
                markRead!!.setVisibility(View.GONE)
                PreferenceManager().setisSelectedRead(mContext,false)
                for (i in 0 until mMessageReadList.size) {
                    mMessageReadList.get(i).isMarked=true
                }
                var selected = false
                for (i in 0 until mMessageReadList.size) {
                    if (mMessageReadList.get(i).isMarked) {
                        selected = true
                    } else {
                        selected = false
                        break
                    }
                }
                if (selected) {
                    messageRead!!.setBackgroundResource(R.drawable.check_box_header_tick)
                    markUnRead!!.setVisibility(View.VISIBLE)
                    markRead!!.setVisibility(View.GONE)
                } else {
                    messageRead!!.setBackgroundResource(R.drawable.check_box_header)
                    markRead!!.setVisibility(View.GONE)
                    markUnRead!!.setVisibility(View.VISIBLE)
                }
                readMessageAdapter!!.notifyDataSetChanged()
                //readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
            } else {
                PreferenceManager().setisVisibleReadBox(mContext,false)
                for (i in 0 until mMessageReadList.size) {
                    if (mMessageReadList.get(i).isChecked) {
                        mMessageReadList.get(i).isChecked=false
                    }
                }
                markUnRead!!.setVisibility(View.GONE)
                messageRead!!.setVisibility(View.GONE)
                msgReadSelect!!.setVisibility(View.GONE)
                readMessageAdapter!!.notifyDataSetChanged()
                //readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
            }
           var click_read= PreferenceManager().getclick_count_read(mContext)
            click_read=click_read++
            PreferenceManager().setclick_count_read(mContext,click_read)

           // click_count_read++
        } else if (v ==messageUnRead) {
            if (PreferenceManager().getclick_count(mContext) % 2 === 0) {
               PreferenceManager().setisVisibleUnreadBox(mContext,true)
                for (i in 0 until unreadArray.size) {
                    unreadArray.get(i).isChecked=true
                }
                /*for (i in 0 until AppController().mMessageUnreadList.size) {
                    AppController().mMessageUnreadList.get(i).isChecked=true
                }*/
                var selected = false
                for (i in 0 until unreadArray.size) {
                    if (unreadArray.get(i).isChecked) {
                        selected = true
                    } else {
                        selected = false
                        break
                    }
                }
                /*for (i in 0 until AppController().mMessageUnreadList.size) {
                    if (AppController().mMessageUnreadList.get(i).isChecked) {
                        selected = true
                    } else {
                        selected = false
                        break
                    }
                }*/
                if (selected) {
                    messageUnRead!!.setBackgroundResource(R.drawable.check_box_header_tick)
                    markUnRead!!.setVisibility(View.GONE)
                    markRead!!.setVisibility(View.VISIBLE)
                } else {
                    messageUnRead!!.setBackgroundResource(R.drawable.check_box_header)
                    markUnRead!!.setVisibility(View.GONE)
                    markRead!!.setVisibility(View.GONE)
                }
                //unReadMessageAdapter!!.notifyDataSetChanged()
                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
            } else {
               PreferenceManager().setisVisibleUnreadBox(mContext,false)
                //Log.e("test",isVisibleUnreadBox.toString())
                for (i in 0 until unreadArray.size) {
                    if (unreadArray.get(i).isChecked) {
                        unreadArray.get(i).isChecked=false
                    }
                }
                /* for (i in 0 until AppController().mMessageUnreadList.size) {
                     if (AppController().mMessageUnreadList.get(i).isChecked) {
                         AppController().mMessageUnreadList.get(i).isChecked=false
                     }
                 }*/
                messageUnRead!!.setVisibility(View.GONE)
                msgUnread!!.setVisibility(View.GONE)
                markRead!!.setVisibility(View.GONE)
                messageUnRead!!.setBackgroundResource(R.drawable.check_box_header)
                // unReadMessageAdapter!!.notifyDataSetChanged()
                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
            }
            var clickcount=PreferenceManager().getclick_count(mContext)
            clickcount=clickcount++
            PreferenceManager().setclick_count(mContext,clickcount)

            //click_count++
        } else if (v ==unreadTxt) {
            callTypeStatus = "1"
            markRead!!.setVisibility(View.GONE)
            messageUnRead!!.setVisibility(View.GONE)
            msgReadSelect!!.setVisibility(View.GONE)
            msgUnread!!.setVisibility(View.GONE)
            messageRead!!.setVisibility(View.GONE)
            markUnRead!!.setVisibility(View.GONE)
            // messageUnRead!!.setBackgroundResource(R.drawable.check_box_header);
            // messageUnRead!!.setBackgroundResource(R.drawable.check_box_header);
           PreferenceManager().setisVisibleUnreadBox(mContext,false)
            PreferenceManager().setisVisibleReadBox(mContext,false)
            // System.out.println(" size of array"+AppController().mMessageUnreadList.size);
            // System.out.println(" size of array"+AppController().mMessageUnreadList.size);
            if (isProgressVisible) {
                mProgressRelLayout!!.clearAnimation()
                mProgressRelLayout!!.setVisibility(View.GONE)
                isProgressVisible = false
            }
            typeValue = "1"
            limitValue = 20
            pageUnReadValue = 0
            isAvailable = false
            if (unreadArray.size > 0) {
                unreadTxt!!.setBackgroundResource(R.drawable.notification_read)
                readTxt!!.setBackgroundResource(R.drawable.notification_unread)
                unreadTxt!!.setTextColor(
                    mContext.getResources().getColor(R.color.white)
                )
                readTxt!!.setTextColor(
                    mContext.getResources()
                        .getColor(R.color.split_bg)
                )
                Log.e("adaptr","1set")
                unReadList.visibility=View.VISIBLE
                unReadList.layoutManager=LinearLayoutManager(mContext)
                unReadMessageAdapter =
                    UnReadMessageAdapter(
                        mContext,
                        unreadArray,
                        messageUnRead!!,
                        markRead!!,
                        markUnRead!!,
                        msgUnread!!/*,object :OnItemLongClickListener{
                            override fun onItemLongClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ): Boolean {
                                Log.e("longclickadapter","longclick")
                                markRead.setVisibility(
                                    View.GONE
                                )
                                markUnRead!!.setVisibility(
                                    View.GONE
                                )
                                messageUnRead!!.setBackgroundResource(
                                    R.drawable.check_box_header
                                )
                               PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                PreferenceManager().setclick_count(mContext,0)
                                for (i in 0 until mMessageReadList.size) {
                                    mMessageReadList.get(i).isMarked=false

                                }
                                for (i in 0 until mMessageUnreadList.size) {
                                    mMessageUnreadList.get(i).isChecked=false

                                }
                                if (mMessageReadList.size > 0) {
                                    readMessageAdapter!!.notifyDataSetChanged()
                                    // readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                                }
                                unReadList.adapter=unReadMessageAdapter
                                unReadMessageAdapter!!.notifyDataSetChanged()
                                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                                messageUnRead!!.setVisibility(
                                    View.VISIBLE
                                )
                                msgUnread!!.setVisibility(
                                    View.VISIBLE
                                )
                                messageRead!!.setVisibility(
                                    View.GONE
                                )
                                msgReadSelect!!.setVisibility(
                                    View.GONE
                                )
                                return false
                            }
                        },object :
                            OnItemClickListener {

                            override fun onItemClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                TODO("Not yet implemented")
                            }


                        }*/
                        ,object : ClickListener {
                            override fun onPositionClicked(position: Int) {
                                Log.e("c1","true")
                            }
                            override fun onLongClicked(position: Int) {
                                Log.e("lc1","true")

                                markRead.setVisibility(
                                    View.GONE
                                )
                                markUnRead!!.setVisibility(
                                    View.GONE
                                )
                                messageUnRead!!.setBackgroundResource(
                                    R.drawable.check_box_header
                                )
                               PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                PreferenceManager().setclick_count(mContext,0)
                                for (i in 0 until mMessageReadList.size) {
                                    mMessageReadList.get(i).isMarked=false

                                }
                                for (i in 0 until mMessageUnreadList.size) {
                                    mMessageUnreadList.get(i).isChecked=false

                                }
                                if (mMessageReadList.size > 0) {
                                    readMessageAdapter!!.notifyDataSetChanged()
                                    // readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                                }
                                unReadList.adapter=unReadMessageAdapter
                                unReadMessageAdapter!!.notifyDataSetChanged()
                                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                                messageUnRead!!.setVisibility(
                                    View.VISIBLE
                                )
                                msgUnread!!.setVisibility(
                                    View.VISIBLE
                                )
                                messageRead!!.setVisibility(
                                    View.GONE
                                )
                                msgReadSelect!!.setVisibility(
                                    View.GONE
                                )
                            }
                        }
                    )

                unReadList.adapter=unReadMessageAdapter
                //unReadList.onItemLongClickListener
                msgUnreadLinear!!.setVisibility(View.VISIBLE)
                msgReadLinear!!.setVisibility(View.GONE)
                unReadMessageAdapter!!.notifyDataSetChanged()
                if (isFromUnReadBottom) {
                    unReadList!!.scrollToPosition(
                        pageUnReadValue - 2
                    )
                }
                // unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                Log.e("adaptr","set2")
                unReadList.layoutManager=LinearLayoutManager(mContext)
                unReadMessageAdapter =
                    UnReadMessageAdapter(
                        mContext,
                        unreadArray,
                        messageUnRead!!,
                        markRead!!,
                        markUnRead!!,
                        msgUnread!!,/*object :OnItemLongClickListener{
                            override fun onItemLongClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ): Boolean {
                                Log.e("longclickadapter","longclick")
                                markRead.setVisibility(
                                    View.GONE
                                )
                                markUnRead!!.setVisibility(
                                    View.GONE
                                )
                                messageUnRead!!.setBackgroundResource(
                                    R.drawable.check_box_header
                                )
                               PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                PreferenceManager().setclick_count(mContext,0)
                                for (i in 0 until mMessageReadList.size) {
                                    mMessageReadList.get(i).isMarked=false

                                }
                                for (i in 0 until mMessageUnreadList.size) {
                                    mMessageUnreadList.get(i).isChecked=false

                                }
                                if (mMessageReadList.size > 0) {
                                    readMessageAdapter!!.notifyDataSetChanged()
                                    // readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                                }
                                unReadList.adapter=unReadMessageAdapter
                                unReadMessageAdapter!!.notifyDataSetChanged()
                                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                                messageUnRead!!.setVisibility(
                                    View.VISIBLE
                                )
                                msgUnread!!.setVisibility(
                                    View.VISIBLE
                                )
                                messageRead!!.setVisibility(
                                    View.GONE
                                )
                                msgReadSelect!!.setVisibility(
                                    View.GONE
                                )
                                return false
                            }
                        },object :
                            OnItemClickListener {

                            override fun onItemClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                TODO("Not yet implemented")
                            }


                        }*/
                        object : ClickListener {
                            override fun onPositionClicked(position: Int) {
                                Log.e("click","true")
                            }
                            override fun onLongClicked(position: Int) {
                                Log.e("longclick","true")
                                markRead.setVisibility(
                                    View.GONE
                                )
                                markUnRead!!.setVisibility(
                                    View.GONE
                                )
                                messageUnRead!!.setBackgroundResource(
                                    R.drawable.check_box_header
                                )
                               PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                PreferenceManager().setisVisibleReadBox(mContext,false)
                                PreferenceManager().setclick_count(mContext,0)
                                for (i in 0 until mMessageReadList.size) {
                                    mMessageReadList.get(i).isMarked=false

                                }
                                for (i in 0 until mMessageUnreadList.size) {
                                    mMessageUnreadList.get(i)

                                }
                                if (mMessageReadList.size > 0) {
                                    readMessageAdapter!!.notifyDataSetChanged()
                                    // readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                                }
                                unReadList.adapter=unReadMessageAdapter
                                unReadMessageAdapter!!.notifyDataSetChanged()
                                //unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                                messageUnRead!!.setVisibility(
                                    View.VISIBLE
                                )
                                msgUnread!!.setVisibility(
                                    View.VISIBLE
                                )
                                messageRead!!.setVisibility(
                                    View.GONE
                                )
                                msgReadSelect!!.setVisibility(
                                    View.GONE
                                )
                            }
                        }
                    )
                unReadList.adapter=unReadMessageAdapter
                unReadList!!.setAdapter(unReadMessageAdapter)
                unReadMessageAdapter!!.setOnBottomReachedListener(
                    object : OnBottomReachedListener {
                        override fun onBottomReached(position: Int) {
                            if (position == mMessageUnreadList.size - 1) {
                                if (!isAvailable) {
                                    isFromUnReadBottom = true
                                    if (PreferenceManager().getisVisibleUnreadBox(mContext)==true){
                                    //if (isVisibleUnreadBox) {
                                        markRead!!.setVisibility(View.GONE)
                                        markUnRead!!.setVisibility(View.GONE)
                                        messageUnRead!!.setBackgroundResource(
                                            R.drawable.check_box_header
                                        )
                                       PreferenceManager().setisVisibleUnreadBox(mContext,true)
                                        PreferenceManager().setisVisibleReadBox(mContext,false)
                                        PreferenceManager().setclick_count(mContext,0)
                                        for (i in 0 until mMessageReadList.size) {
                                            mMessageReadList.get(i).isMarked=false
                                        }
                                        for (i in 0 until mMessageUnreadList.size) {
                                            mMessageUnreadList.get(i)
                                                .isChecked=false
                                        }
                                    }
                                    typeValue = "1"
                                    limitValue = 20
                                    pageUnReadValue =
                                        pageUnReadValue + limitValue
                                    callPushNotification(
                                        typeValue,
                                        limitValue.toString(),
                                        pageUnReadValue.toString()
                                    )
                                    // Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
                                }
                            }

                            //   System.out.println("position bottom"+position);
                        }
                    })
                callPushNotification(
                    typeValue,
                    limitValue.toString(),
                    pageUnReadValue.toString()
                )
            } else {
                callPushNotification(
                    typeValue,
                    limitValue.toString(),
                    pageUnReadValue.toString()
                )
            }
        } else if (v === readTxt) {
            callTypeStatus = "2"
            markRead!!.setVisibility(View.GONE)
            msgReadSelect!!.setVisibility(View.GONE)
            messageUnRead!!.setVisibility(View.GONE)
            msgUnread!!.setVisibility(View.GONE)
            messageRead!!.setVisibility(View.GONE)
            markUnRead!!.setVisibility(View.GONE)
            if (isProgressVisible) {
                mProgressRelLayout!!.clearAnimation()
                mProgressRelLayout!!.setVisibility(View.GONE)
                isProgressVisible = false
            }
            // messageUnRead!!.setBackgroundResource(R.drawable.check_box_header);
            // messageUnRead!!.setBackgroundResource(R.drawable.check_box_header);
           PreferenceManager().setisVisibleUnreadBox(mContext,false)
            PreferenceManager().setisVisibleReadBox(mContext,false)
            //  System.out.println(" size of array"+AppController().mMessageReadList.size);

            //  System.out.println(" size of array"+AppController().mMessageReadList.size);
            typeValue = "2"
            limitValue = 20
            pageReadValue = 0
            isReadAvailable = false
            if (mMessageReadList.size > 0) {
                readTxt!!.setBackgroundResource(R.drawable.notification_read)
                unreadTxt!!.setBackgroundResource(R.drawable.notification_unread)
                readTxt!!.setTextColor(
                    mContext.getResources().getColor(R.color.white)
                )
                unreadTxt!!.setTextColor(
                    mContext.getResources()
                        .getColor(R.color.split_bg)
                )
                //set adapter
                readList.visibility=View.VISIBLE
                readList.layoutManager=LinearLayoutManager(mContext)
                readMessageAdapter = ReadMessageAdapter(
                    mContext,
                    mMessageReadList,
                    messageRead!!,
                    markUnRead!!,
                    markRead!!,
                    msgReadSelect!!,
                    object : ClickListener {
                        override fun onPositionClicked(position: Int) {
                            markRead.setVisibility(View.GONE)
                            markUnRead!!.setVisibility(View.GONE)
                            messageRead!!.setVisibility(View.GONE)
                            msgReadSelect!!.setVisibility(View.GONE)
                            messageUnRead!!.setVisibility(View.GONE)
                            msgUnread!!.setVisibility(View.GONE)
                           PreferenceManager().setisVisibleUnreadBox(mContext,false)
                            PreferenceManager().setisVisibleReadBox(mContext,false)
                            val intent: Intent = Intent(
                                mContext,
                                MessageDetail::class.java
                            )
                            intent.putExtra(
                                "title",
                                mMessageReadList.get(position).title
                            )
                            intent.putExtra(
                                "message",
                                mMessageReadList.get(position).message
                            )
                            intent.putExtra(
                                "date",
                                mMessageReadList.get(position).day
                            )
                            ///intent.putExtra("position",position);
                            mContext.startActivity(intent)
                        }

                        override fun onLongClicked(position: Int) {
                            markRead.setVisibility(View.GONE)
                            markUnRead!!.setVisibility(View.GONE)
                            PreferenceManager().setclick_count_read(mContext,0)
                            // System.out.println("checkbox read visible:::::");
                            messageRead!!.setBackgroundResource(R.drawable.check_box_header)
                            PreferenceManager().setisVisibleReadBox(mContext,true)
                           PreferenceManager().setisVisibleUnreadBox(mContext,false)
                            for (i in 0 until mMessageReadList.size) {
                                mMessageReadList.get(i).isMarked=false
                            }
                            for (i in 0 until mMessageUnreadList.size) {
                                mMessageUnreadList.get(i).isChecked=false
                            }
                            if (mMessageUnreadList.size > 0) {
                                unReadList.adapter=unReadMessageAdapter
                                unReadMessageAdapter!!.notifyDataSetChanged()
                                // unReadMessageAdapter.notifyItemRangeChanged(0, unReadMessageAdapter.getItemCount());
                            }
                            readMessageAdapter!!.notifyDataSetChanged()
                            //readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                            messageRead!!.setVisibility(View.VISIBLE)
                            msgReadSelect!!.setVisibility(View.VISIBLE)
                            messageUnRead!!.setVisibility(View.GONE)
                            msgUnread!!.setVisibility(View.GONE)
                            noMsgAlertRelative!!.setVisibility(View.GONE)
                            noMsgText!!.setVisibility(View.GONE)
                        }
                    }
                )
                readList.adapter=readMessageAdapter
                msgUnreadLinear!!.setVisibility(View.GONE)
                msgReadLinear!!.setVisibility(View.VISIBLE)
                if (isFromReadBottom) {
                    readList!!.scrollToPosition(
                        pageReadValue - 1
                    )
                }
                readMessageAdapter!!.notifyDataSetChanged()
                //readMessageAdapter.notifyItemRangeChanged(0, readMessageAdapter.getItemCount());
                readList.adapter=readMessageAdapter
                //readList!!.setAdapter(readMessageAdapter)
                readMessageAdapter!!.setOnBottomReachedListener(object :
                    OnBottomReachedListener {
                    override fun onBottomReached(position: Int) {
                        if (position == mMessageReadList.size - 2) {
                            if (!isReadAvailable) {
                                isFromReadBottom = true
                                if (PreferenceManager().getisVisibleReadBox(mContext)==true){
                               // if (isVisibleReadBox) {
                                    markRead!!.setVisibility(View.GONE)
                                    markUnRead!!.setVisibility(View.GONE)
                                    PreferenceManager().setclick_count_read(mContext,0)
                                    // System.out.println("checkbox read visible:::::");
                                    messageRead!!.setBackgroundResource(
                                        R.drawable.check_box_header
                                    )
                                    PreferenceManager().setisVisibleReadBox(mContext,true)
                                   PreferenceManager().setisVisibleUnreadBox(mContext,false)
                                    for (i in 0 until mMessageReadList.size) {
                                        mMessageReadList.get(i).isMarked=false
                                    }
                                    for (i in 0 until mMessageUnreadList.size) {
                                        mMessageUnreadList.get(i).isChecked=false
                                    }
                                }
                                typeValue = "2"
                                limitValue = 20
                                pageReadValue =
                                    pageReadValue + limitValue
                                callPushNotification(
                                    typeValue,
                                    limitValue.toString(),
                                    pageReadValue.toString()
                                )
                                //  Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(mContext,  "Reached", Toast.LENGTH_SHORT).show();
                        }

                        //    System.out.println("position bottom"+position);
                    }
                })
                callPushNotification(
                    typeValue,
                    limitValue.toString(),
                    pageReadValue.toString()
                )
            } else {
                callPushNotification(
                    typeValue,
                    limitValue.toString(),
                    pageReadValue.toString()
                )
            }
        }
        }
    override fun onResume() {
        super.onResume()
        if (PreferenceManager().getisfromUnread(mContext)==true) {
        /*if (AppController().isfromUnread) {*/
            unreadTxt!!.setBackgroundResource(R.drawable.notification_read)
            readTxt!!.setBackgroundResource(R.drawable.notification_unread)
            //            for(int i = 0; i<AppController.mMessageUnreadList.size(); i++)
//            {
//                if (AppController.pushId.equalsIgnoreCase(AppController.mMessageUnreadList.get(i).getPushid()))
//                {
//                    AppController.mMessageUnreadList.remove(i);
//                }
//            }
            unReadMessageAdapter!!.notifyDataSetChanged()
            PreferenceManager().setIsfromUnread(mContext,false)
           // AppController().isfromUnread = false
            callstatusapi("1", PreferenceManager().getpushId(mContext).toString())
        } else if (PreferenceManager().getisfromRead(mContext)==true){
            unreadTxt!!.setBackgroundResource(R.drawable.notification_unread)
            readTxt!!.setBackgroundResource(R.drawable.notification_read)
            readTxt!!.setTextColor(
                mContext.getResources().getColor(R.color.white)
            )
            unreadTxt!!.setTextColor(
                mContext.getResources().getColor(R.color.split_bg)
            )
            PreferenceManager().setisfromRead(mContext,false)
           // AppController().isfromRead = false
            markRead.setVisibility(View.GONE)
            markUnRead!!.setVisibility(View.GONE)
            messageRead!!.setVisibility(View.GONE)
            msgReadSelect!!.setVisibility(View.GONE)
            messageUnRead!!.setVisibility(View.GONE)
            msgUnread!!.setVisibility(View.GONE)
           PreferenceManager().setisVisibleUnreadBox(mContext,false)
            PreferenceManager().setisVisibleReadBox(mContext,false)
            readMessageAdapter!!.notifyDataSetChanged()
        }
        if (!PreferenceManager().getUserId(mContext)
                .equals("")
        ) {
          /*  AppController.getInstance().getGoogleAnalyticsTracker()
                .set("&uid", PreferenceManager().getUserId(NotificationFragmentPagination.mContext))
            AppController.getInstance().getGoogleAnalyticsTracker()
                .set("&cid", PreferenceManager().getUserId(NotificationFragmentPagination.mContext))
            AppController.getInstance().trackScreenView(
                "Notification Screen Fragment. " + "(" + PreferenceManager().getUserEmail(
                    NotificationFragmentPagination.mContext
                ) + ")" + " " + "(" + Calendar.getInstance().time + ")"
            )*/
        }
        // System.out.println("testinngresume");
    }
    }
