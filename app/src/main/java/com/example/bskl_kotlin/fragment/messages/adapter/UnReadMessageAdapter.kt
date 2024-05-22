package com.example.bskl_kotlin.fragment.messages.adapter

import ApiClient
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.notification.AudioPlayerViewActivity
import com.example.bskl_kotlin.activity.notification.ImageActivity
import com.example.bskl_kotlin.activity.notification.VideoWebViewActivity
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.constants.ClickListener
import com.example.bskl_kotlin.fragment.messages.model.NotificationFavApiModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationFavouriteModel
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.recyclerviewmanager.OnBottomReachedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class UnReadMessageAdapter(private var mContext:Context, var mMessageUnreadList:ArrayList<PushNotificationModel>,
                           private var checkBox: ImageView, private var markRead:TextView,
                           private var markUnRead:TextView, private var msgUnread:TextView,
                           var listener: ClickListener
) :
    RecyclerView.Adapter<UnReadMessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_unread_list, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("adapterarraaysize",mMessageUnreadList.size.toString())
        holder.unreadMessage!!.setText(mMessageUnreadList.get(position).day)
        holder.unreadTxt!!.setText(mMessageUnreadList.get(position).title)

       // if (AppController().isVisibleUnreadBox) {
        if(PreferenceManager().getisVisibleUnreadBox(mContext)==true){
            holder.msgBoxUnRead!!.visibility = View.VISIBLE
            holder.viewFirst!!.visibility = View.GONE
            holder.viewTxt!!.visibility = View.VISIBLE
        } else {
            holder.msgBoxUnRead!!.visibility = View.INVISIBLE
            holder.viewFirst!!.visibility = View.VISIBLE
            holder.viewTxt!!.visibility = View.GONE
        }
        holder.msgBoxUnRead!!.setOnClickListener {
            if (mMessageUnreadList.get(position).isChecked) {
                holder.msgBoxUnRead!!.setBackgroundResource(R.drawable.check_box_white_2)
                mMessageUnreadList.get(position).isChecked = false

                PreferenceManager().setclick_count(mContext,0)
                //AppController().click_count = 0
            } else {
                holder.msgBoxUnRead!!.setBackgroundResource(R.drawable.check_box_white_tick_2)
                mMessageUnreadList.get(position).isChecked = true
                PreferenceManager().setclick_count(mContext,1)
                //AppController().click_count = 1
            }
            var marked_count = 0
            for (i in 0 until mMessageUnreadList.size) {
                if (mMessageUnreadList.get(i).isChecked) {
                    marked_count++
                }
            }
            if (marked_count == mMessageUnreadList.size) {
                checkBox!!.setVisibility(View.VISIBLE)
                msgUnread!!.setVisibility(View.VISIBLE)
                checkBox!!.setBackgroundResource(R.drawable.check_box_header_tick)
            } else if (marked_count == 0) {
                checkBox!!.setVisibility(View.GONE)
                msgUnread!!.setVisibility(View.GONE)
                PreferenceManager().setisVisibleUnreadBox(mContext,false)
                //AppController().isVisibleUnreadBox = false
                markRead!!.setVisibility(View.GONE)
                notifyDataSetChanged()
                notifyItemRangeChanged(0, getItemCount());
            } else if (marked_count == 1) {
                PreferenceManager().setclick_count(mContext,0)
               // AppController().click_count = 0
                notifyDataSetChanged()
                notifyItemRangeChanged(0, getItemCount());
            } else {
                markRead!!.setVisibility(View.VISIBLE)
                checkBox!!.setBackgroundResource(R.drawable.check_box_header)
            }
            if (marked_count >= 1) {
                PreferenceManager().setclick_count(mContext,0)
                //AppController().click_count = 0
                markRead.visibility = View.VISIBLE
            }

        }
        if (mMessageUnreadList.get(position).favourite.equals("1")) {
            holder.fav!!.setBackgroundResource(R.drawable.star_yellow)
        } else {
            holder.fav!!.setBackgroundResource(R.drawable.star_gray)
        }

        if (mMessageUnreadList.get(position).isChecked) {
            holder.msgBoxUnRead!!.setBackgroundResource(R.drawable.check_box_white_tick_2)
        } else {
            holder.msgBoxUnRead!!.setBackgroundResource(R.drawable.check_box_white_2)
        }
        holder.unreadClick!!.setOnClickListener {
           /* PreferenceManager().setUnreadList(null,mContext)
            PreferenceManager().setUnreadList(mMessageUnreadList,mContext)*/
            mMessageUnreadList.get(position).status = "1"
            Log.e("typemessage",mMessageUnreadList.get(position).type)
            var mIntent: Intent
            if (mMessageUnreadList.get(position).type
                    .equals("Image") || mMessageUnreadList.get(position)
                    .type.equals("text") || mMessageUnreadList[position].type.equals("Text")||mMessageUnreadList.get(
                    position
                ).type.equals("")
            ) {
                var pushNotificationDetail = """
                                    <!DOCTYPE html>
                                    <html>
                                    <head>
                                    <style>
                                    
                                    @font-face {
                                    font-family: SourceSansPro-Semibold;src: url(SourceSansPro-Semibold.ttf);font-family: SourceSansPro-Regular;src: url(SourceSansPro-Regular.ttf);}.title {font-family: SourceSansPro-Regular;font-size:16px;text-align:left;color:	#46C1D0;}.description {font-family: SourceSansPro-Semibold;text-align:justify;font-size:14px;color: #A9A9A9;}.date {font-family: SourceSansPro-Semibold;text-align:right;font-size:12px;color: #000000;}</style>
                                    </head><body>
                                    """.trimIndent()
                if (!mMessageUnreadList.get(position).url.equals("")) {
                    pushNotificationDetail =
                        pushNotificationDetail + "<center><img src='" + mMessageUnreadList.get(
                            position
                        ).url + "'width='100%', height='auto'>"
                }
                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
                val mdate: String = mMessageUnreadList.get(position).date
                var date: Date? = null
                try {
                    date = inputFormat.parse(mdate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val outputDateStr = outputFormat.format(date)
                pushNotificationDetail = pushNotificationDetail +
                        "<p class='description'>" + mMessageUnreadList.get(position)
                    .message
                pushNotificationDetail =
                    "$pushNotificationDetail<p class='date'>$outputDateStr</p></body>\n</html>"
                mIntent = Intent(mContext, ImageActivity::class.java)
                mIntent.putExtra("webViewComingDetail", pushNotificationDetail)
                mIntent.putExtra("title", mMessageUnreadList.get(position).title)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
               // mIntent.putExtra("PASSING_ARRAY", mMessageUnreadList)
                mIntent.putExtra("position", position)
                mContext.startActivity(mIntent)
            }
            if (mMessageUnreadList.get(position).type
                    .equals("audio")
            ) {
                mIntent = Intent(mContext, AudioPlayerViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title", mMessageUnreadList.get(position).title)
               // mIntent.putExtra("PASSING_ARRAY", mMessageUnreadList)
                mContext.startActivity(mIntent)
            }
            if (mMessageUnreadList.get(position).type
                    .equals("video")
            ) {
                mIntent = Intent(mContext, VideoWebViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title", mMessageUnreadList.get(position).title)
                //mIntent.putExtra("PASSING_ARRAY", mMessageUnreadList)
                mContext.startActivity(mIntent)
            }


        }

    }



    override fun getItemCount(): Int {
        return mMessageUnreadList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener,
        View.OnLongClickListener {

        var unreadMessage: TextView? = null
        var unreadTxt: TextView? = null
        var msgBoxUnRead: ImageView? = null
        var fav: ImageView? = null
        var starLinear: LinearLayout? = null
        var cardView: CardView? = null
        var viewFirst: TextView? = null
        var viewTxt: TextView? = null
        var unreadClick: RelativeLayout? = null
        var listenerRef: WeakReference<ClickListener>? = null


        init {
            listenerRef = WeakReference<ClickListener>(listener)
            unreadMessage = view.findViewById(R.id.unreadMessage)
            unreadTxt = view.findViewById(R.id.unreadTxt)
            msgBoxUnRead = view.findViewById(R.id.msgBoxUnRead)
            viewFirst = view.findViewById(R.id.viewFirst)
            viewTxt = view.findViewById(R.id.viewTxt)
            fav = view.findViewById(R.id.starUR)
            cardView = view.findViewById(R.id.card_view)
            starLinear = view.findViewById(R.id.starLinear)
            unreadClick = view.findViewById(R.id.unreadClick)
            starLinear!!.setOnClickListener(this)
            msgBoxUnRead!!.setOnClickListener(this)
            unreadClick!!.setOnLongClickListener(this)
            unreadClick!!.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v!!.getId() == starLinear!!.id) {
                fav!!.setBackgroundResource(R.drawable.star_yellow)
                if (mMessageUnreadList.get(adapterPosition).favourite
                        .equals("0")
                ) {
                    fav!!.setBackgroundResource(R.drawable.star_yellow)
                    mMessageUnreadList.get(adapterPosition).favourite="1"
                    callfavouriteStatusApi(
                        "1", mMessageUnreadList.get(
                            adapterPosition
                        ).pushid
                    )
                } else {
                    fav!!.setBackgroundResource(R.drawable.star_gray)
                    mMessageUnreadList.get(adapterPosition).favourite="0"
                    callfavouriteStatusApi(
                        "0", mMessageUnreadList.get(
                            adapterPosition
                        ).pushid
                    )
                }
            } else if (v.getId() == msgBoxUnRead!!.id) {
                if (mMessageUnreadList.get(adapterPosition).isChecked) {
                    mMessageUnreadList.get(adapterPosition).isChecked=false
                } else {
                    mMessageUnreadList.get(adapterPosition).isChecked=true
                }
                var marked_count = 0
                for (i in 0 until mMessageUnreadList.size) {
                    if (mMessageUnreadList.get(i).isChecked) {
                        marked_count++
                    }
                }
            }
            listenerRef!!.get()!!.onPositionClicked(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            if (v === unreadClick) {
                listenerRef!!.get()!!.onLongClicked(adapterPosition)
            }

            return false
        }
    }
    private fun callfavouriteStatusApi(favourite: String, pushId: String) {
        var notfav = NotificationFavApiModel(
            PreferenceManager().getUserId(mContext).toString(),
            favourite, pushId
        )

        val call: Call<NotificationFavouriteModel> = ApiClient.getClient.notification_favourite(
            notfav, "Bearer " + PreferenceManager().getaccesstoken(mContext).toString()
        )

        call.enqueue(object : Callback<NotificationFavouriteModel> {
            override fun onFailure(call: Call<NotificationFavouriteModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<NotificationFavouriteModel>,
                response: Response<NotificationFavouriteModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")) {
                    if (responsedata!!.response.statuscode.equals("303")) {


                    }
                }

            }
        })
    }

    fun setOnBottomReachedListener(onBottomReachedListener: OnBottomReachedListener) {

    }

}
   /* RecyclerView.Adapter<UnReadMessageAdapter.MyViewHolder>(), View.OnLongClickListener,View.OnClickListener {


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var unreadMessage: TextView = view.findViewById(R.id.unreadMessage)
        var unreadTxt: TextView = view.findViewById(R.id.unreadTxt)
        var msgBoxUnRead: ImageView = view.findViewById(R.id.msgBoxUnRead)
        var viewFirst: TextView = view.findViewById(R.id.viewFirst)
        var viewTxt: TextView = view.findViewById(R.id.viewTxt)
        var fav: ImageView = view.findViewById(R.id.starUR)
        var cardView: CardView = view.findViewById(R.id.card_view)
        var starLinear: LinearLayout = view.findViewById(R.id.starLinear)
        var unreadClick: RelativeLayout = view.findViewById(R.id.unreadClick)
       // var listenerRef: WeakReference<ClickListener>? = WeakReference<>(listener)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_unread_list, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("adptr", "true")
        Log.e("listener", listener.toString())


        holder.unreadMessage.setText(mMessageUnreadList.get(position).day)
        holder.unreadTxt.setText(mMessageUnreadList.get(position).title)

        holder.starLinear.setOnClickListener {
            holder.fav.setBackgroundResource(R.drawable.star_yellow)

            if (mMessageUnreadList[position].favourite
                    .equals("0")
            ) {
                holder.fav.setBackgroundResource(R.drawable.star_yellow)
                mMessageUnreadList[position].favourite = "1"
                callfavouriteStatusApi(
                    "1",
                    mMessageUnreadList[position].pushid
                )
            } else {
                holder.fav.setBackgroundResource(R.drawable.star_gray)
                mMessageUnreadList[position].favourite = "0"
                callfavouriteStatusApi(
                    "0",
                    mMessageUnreadList[position].pushid
                )
            }
        }
        holder.msgBoxUnRead!!.setOnClickListener {
            if (mMessageUnreadList[position].isChecked) {
                mMessageUnreadList[position].isChecked = false
            } else {
                mMessageUnreadList[position].isChecked = true
            }
            var marked_count = 0
            for (i in 0 until mMessageUnreadList.size) {
                if (mMessageUnreadList.get(i).isChecked) {
                    marked_count++
                }
            }

        }


        if (AppController().isVisibleUnreadBox) {
            holder.msgBoxUnRead!!.visibility = View.VISIBLE
            holder.viewFirst.visibility = View.GONE
            holder.viewTxt.visibility = View.VISIBLE
        } else {
            holder.msgBoxUnRead!!.visibility = View.INVISIBLE
            holder.viewFirst.visibility = View.VISIBLE
            holder.viewTxt.visibility = View.GONE
        }

        holder.msgBoxUnRead!!.setOnClickListener {
            if (mMessageUnreadList.get(position).isChecked) {
                holder.msgBoxUnRead!!.setBackgroundResource(R.drawable.check_box_white_2)
                mMessageUnreadList.get(position).isChecked = false
                AppController().click_count = 0
            } else {
                holder.msgBoxUnRead!!.setBackgroundResource(R.drawable.check_box_white_tick_2)
                mMessageUnreadList.get(position).isChecked = true
                AppController().click_count = 1
            }
            var marked_count = 0
            for (i in 0 until mMessageUnreadList.size) {
                if (mMessageUnreadList.get(i).isChecked) {
                    marked_count++
                }
            }
            if (marked_count == mMessageUnreadList.size) {
                checkBox!!.setVisibility(View.VISIBLE)
                msgUnread!!.setVisibility(View.VISIBLE)
                checkBox!!.setBackgroundResource(R.drawable.check_box_header_tick)
            } else if (marked_count == 0) {
                checkBox!!.setVisibility(View.GONE)
                msgUnread!!.setVisibility(View.GONE)
                AppController().isVisibleUnreadBox = false
                markRead!!.setVisibility(View.GONE)
                notifyDataSetChanged()
                notifyItemRangeChanged(0, getItemCount());
            } else if (marked_count == 1) {
                AppController().click_count = 0
                notifyDataSetChanged()
                notifyItemRangeChanged(0, getItemCount());
            } else {
                markRead!!.setVisibility(View.VISIBLE)
                checkBox!!.setBackgroundResource(R.drawable.check_box_header)
            }
            if (marked_count >= 1) {
                AppController().click_count = 0
                markRead.visibility = View.VISIBLE
            }

        }
        if (mMessageUnreadList.get(position).favourite.equals("1")) {
            holder.fav.setBackgroundResource(R.drawable.star_yellow)
        } else {
            holder.fav.setBackgroundResource(R.drawable.star_gray)
        }

        if (mMessageUnreadList.get(position).isChecked) {
            holder.msgBoxUnRead!!.setBackgroundResource(R.drawable.check_box_white_tick_2)
        } else {
            holder.msgBoxUnRead!!.setBackgroundResource(R.drawable.check_box_white_2)
        }
        holder.unreadClick!!.setOnClickListener {
            mMessageUnreadList.get(position).status = "1"
            var mIntent: Intent
            if (mMessageUnreadList.get(position).type
                    .equals("image") || mMessageUnreadList.get(position)
                    .type.equals("text") || mMessageUnreadList.get(
                    position
                ).type.equals("")
            ) {
                var pushNotificationDetail = """
                                    <!DOCTYPE html>
                                    <html>
                                    <head>
                                    <style>
                                    
                                    @font-face {
                                    font-family: SourceSansPro-Semibold;src: url(SourceSansPro-Semibold.ttf);font-family: SourceSansPro-Regular;src: url(SourceSansPro-Regular.ttf);}.title {font-family: SourceSansPro-Regular;font-size:16px;text-align:left;color:	#46C1D0;}.description {font-family: SourceSansPro-Semibold;text-align:justify;font-size:14px;color: #A9A9A9;}.date {font-family: SourceSansPro-Semibold;text-align:right;font-size:12px;color: #000000;}</style>
                                    </head><body>
                                    """.trimIndent()
                if (!mMessageUnreadList.get(position).url.equals("")) {
                    pushNotificationDetail =
                        pushNotificationDetail + "<center><img src='" + mMessageUnreadList.get(
                            position
                        ).url + "'width='100%', height='auto'>"
                }
                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
                val mdate: String = mMessageUnreadList.get(position).date
                var date: Date? = null
                try {
                    date = inputFormat.parse(mdate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val outputDateStr = outputFormat.format(date)
                pushNotificationDetail = pushNotificationDetail +
                        "<p class='description'>" + mMessageUnreadList.get(position)
                    .message
                pushNotificationDetail =
                    "$pushNotificationDetail<p class='date'>$outputDateStr</p></body>\n</html>"
                mIntent = Intent(mContext, ImageActivity::class.java)
                mIntent.putExtra("title", mMessageUnreadList.get(position).title)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("PASSING_ARRAY", mMessageUnreadList)
                mIntent.putExtra("position", position)
                mContext.startActivity(mIntent)
            }
            if (mMessageUnreadList.get(position).type
                    .equals("audio")
            ) {
                mIntent = Intent(mContext, AudioPlayerViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title", mMessageUnreadList.get(position).title)
                mIntent.putExtra("PASSING_ARRAY", mMessageUnreadList)
                mContext.startActivity(mIntent)
            }
            if (mMessageUnreadList.get(position).type
                    .equals("video")
            ) {
                mIntent = Intent(mContext, VideoWebViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title", mMessageUnreadList.get(position).title)
                mIntent.putExtra("PASSING_ARRAY", mMessageUnreadList)
                mContext.startActivity(mIntent)
            }


        }
    }

    override fun getItemCount(): Int {
        return mMessageUnreadList.size
    }

    private fun callfavouriteStatusApi(favourite: String, pushId: String) {
        var notfav = NotificationFavApiModel(
            PreferenceManager().getUserId(mContext).toString(),
            favourite, pushId
        )

        val call: Call<NotificationFavouriteModel> = ApiClient.getClient.notification_favourite(
            notfav, "Bearer " + PreferenceManager().getaccesstoken(mContext).toString()
        )

        call.enqueue(object : Callback<NotificationFavouriteModel> {
            override fun onFailure(call: Call<NotificationFavouriteModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<NotificationFavouriteModel>,
                response: Response<NotificationFavouriteModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")) {
                    if (responsedata!!.response.statuscode.equals("303")) {


                    }
                }

            }
        })
    }

    fun setOnBottomReachedListener(any: Any) {

    }

    override fun onLongClick(p0: View?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}*/


