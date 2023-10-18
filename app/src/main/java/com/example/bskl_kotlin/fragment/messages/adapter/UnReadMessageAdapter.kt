package com.example.bskl_kotlin.fragment.messages.adapter

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
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.bskl_kotlin.activity.notification.AudioPlayerViewActivity
import com.example.bskl_kotlin.activity.notification.ImageActivity
import com.example.bskl_kotlin.activity.notification.VideoWebViewActivity
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.messages.model.NotificationFavouriteModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationStatusModel
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

internal class UnReadMessageAdapter (private var mContext:Context,private var mMessageUnreadList:ArrayList<PushNotificationModel>,
                                     private var checkBox: ImageView,private  var markRead:TextView,
                                     private var markUnRead:TextView, private var msgUnread:TextView) :
    RecyclerView.Adapter<UnReadMessageAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var unreadMessage: TextView = view.findViewById(R.id.unreadMessage)
        var unreadTxt:TextView = view.findViewById(R.id.unreadTxt)
        var msgBoxUnRead:ImageView = view.findViewById(R.id.msgBoxUnRead)
        var viewFirst:TextView = view.findViewById(R.id.viewFirst)
        var viewTxt:TextView = view.findViewById(R.id.viewTxt)
        var fav:ImageView = view.findViewById(R.id.starUR)
        var cardView: CardView = view.findViewById(R.id.card_view)
        var starLinear: LinearLayout = view.findViewById(R.id.starLinear)
        var unreadClick: RelativeLayout = view.findViewById(R.id.unreadClick)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_unread_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("adptr","true")

        holder.unreadMessage.setText(AppController().mMessageUnreadList.get(position).day)
        holder.unreadTxt.setText(AppController().mMessageUnreadList.get(position).title)

        holder.starLinear.setOnClickListener {
            holder.fav.setBackgroundResource(R.drawable.star_yellow)

            if (AppController().mMessageUnreadList[position].favourite
                    .equals("0")
            ) {
                holder.fav.setBackgroundResource(R.drawable.star_yellow)
                AppController().mMessageUnreadList[position].favourite="1"
                callfavouriteStatusApi(
                    "1",
                    AppController().mMessageUnreadList[position].pushid
                )
            } else {
                holder.fav.setBackgroundResource(R.drawable.star_gray)
                AppController().mMessageUnreadList[position].favourite="0"
                callfavouriteStatusApi(
                    "0",
                    AppController().mMessageUnreadList[position].pushid
                )
            }
        }
        holder.msgBoxUnRead.setOnClickListener {
            if (AppController().mMessageUnreadList[position].isChecked) {
                AppController().mMessageUnreadList[position].isChecked=false
            } else {
                AppController().mMessageUnreadList[position].isChecked=true
            }
            var marked_count = 0
            for (i in 0 until AppController().mMessageUnreadList.size) {
                if (AppController().mMessageUnreadList.get(i).isChecked) {
                    marked_count++
                }
            }

        }


        if (AppController().isVisibleUnreadBox) {
            holder.msgBoxUnRead.visibility = View.VISIBLE
            holder.viewFirst.visibility = View.GONE
            holder.viewTxt.visibility = View.VISIBLE
        } else {
            holder.msgBoxUnRead.visibility = View.INVISIBLE
            holder.viewFirst.visibility = View.VISIBLE
            holder.viewTxt.visibility = View.GONE
        }

        holder.msgBoxUnRead.setOnClickListener {
            if (AppController().mMessageUnreadList.get(position).isChecked) {
                holder.msgBoxUnRead.setBackgroundResource(R.drawable.check_box_white_2)
                AppController().mMessageUnreadList.get(position).isChecked=false
                AppController().click_count = 0
            } else {
                holder.msgBoxUnRead.setBackgroundResource(R.drawable.check_box_white_tick_2)
                AppController().mMessageUnreadList.get(position).isChecked=true
                AppController().click_count = 1
            }
            var marked_count = 0
            for (i in 0 until AppController().mMessageUnreadList.size) {
                if (AppController().mMessageUnreadList.get(i).isChecked) {
                    marked_count++
                }
            }
            if (marked_count == AppController().mMessageUnreadList.size) {
                checkBox!!.setVisibility(View.VISIBLE)
                msgUnread!!.setVisibility(View.VISIBLE)
                checkBox!!.setBackgroundResource(R.drawable.check_box_header_tick)
            } else if (marked_count == 0) {
                checkBox!!.setVisibility(View.GONE)
                msgUnread!!.setVisibility(View.GONE)
                AppController().isVisibleUnreadBox = false
                markRead!!.setVisibility(View.GONE)
                notifyDataSetChanged()
                // notifyItemRangeChanged(0, getItemCount());
            } else if (marked_count == 1) {
                AppController().click_count = 0
                notifyDataSetChanged()
                //notifyItemRangeChanged(0, getItemCount());
            } else {
                markRead!!.setVisibility(View.VISIBLE)
                checkBox!!.setBackgroundResource(R.drawable.check_box_header)
            }
            if (marked_count >= 1) {
                AppController().click_count = 0
                markRead.visibility = View.VISIBLE
            }

        }
        if (AppController().mMessageUnreadList.get(position).favourite.equals("1")) {
            holder.fav.setBackgroundResource(R.drawable.star_yellow)
        } else {
            holder.fav.setBackgroundResource(R.drawable.star_gray)
        }

        if (AppController().mMessageUnreadList.get(position).isChecked) {
            holder.msgBoxUnRead.setBackgroundResource(R.drawable.check_box_white_tick_2)
        } else {
            holder.msgBoxUnRead.setBackgroundResource(R.drawable.check_box_white_2)
        }
        holder.unreadClick.setOnClickListener {
            AppController().mMessageUnreadList.get(position).status="1"
            var mIntent: Intent
            if (AppController().mMessageUnreadList.get(position).type
                    .equals("image") || AppController().mMessageUnreadList.get(position)
                    .type.equals("text") || AppController().mMessageUnreadList.get(
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
                if (!AppController().mMessageUnreadList.get(position).url.equals("")) {
                    pushNotificationDetail =
                        pushNotificationDetail + "<center><img src='" + AppController().mMessageUnreadList.get(
                            position
                        ).url + "'width='100%', height='auto'>"
                }
                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
                val mdate: String = AppController().mMessageUnreadList.get(position).date
                var date: Date? = null
                try {
                    date = inputFormat.parse(mdate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val outputDateStr = outputFormat.format(date)
                pushNotificationDetail = pushNotificationDetail +
                        "<p class='description'>" + AppController().mMessageUnreadList.get(position)
                    .message
                pushNotificationDetail =
                    "$pushNotificationDetail<p class='date'>$outputDateStr</p></body>\n</html>"
                            mIntent = Intent(mContext, ImageActivity::class.java)
                mIntent.putExtra("title", AppController().mMessageUnreadList.get(position).title)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra( "PASSING_ARRAY", AppController().mMessageUnreadList)
                mIntent.putExtra("position", position)
                mContext.startActivity(mIntent)
            }
            if (AppController().mMessageUnreadList.get(position).type
                    .equals("audio")
            ) {
                mIntent = Intent(mContext, AudioPlayerViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title", AppController().mMessageUnreadList.get(position).title)
                mIntent.putExtra("PASSING_ARRAY", AppController().mMessageUnreadList)
                mContext.startActivity(mIntent)
            }
            if (AppController().mMessageUnreadList.get(position).type
                    .equals("video")
            ) {
                mIntent = Intent(mContext, VideoWebViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title", AppController().mMessageUnreadList.get(position).title)
                mIntent.putExtra("PASSING_ARRAY", AppController().mMessageUnreadList)
                mContext.startActivity(mIntent)
            }


        }
    }
    override fun getItemCount(): Int {
        return AppController().mMessageUnreadList.size
    }
    private fun callfavouriteStatusApi(favourite:String, pushId:String){
        val call: Call<NotificationFavouriteModel> = ApiClient.getClient.notification_favourite(
            PreferenceManager().getaccesstoken(mContext).toString(),PreferenceManager().getUserId(mContext).toString(),
            favourite,pushId)

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
}

/*class UnReadMessageAdapter (
    private var mContext:Context,private var mMessageUnreadList:ArrayList<PushNotificationModel>,
    private var checkBox: ImageView,private  var markRead:TextView,
    private var markUnRead:TextView, private var msgUnread:TextView) :

    RecyclerView.Adapter<UnReadMessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_unread_list, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("adptr","true")

        holder.unreadMessage.setText(AppController().mMessageUnreadList.get(position).day)
        holder.unreadTxt.setText(AppController().mMessageUnreadList.get(position).title)

        holder.starLinear.setOnClickListener {
            holder.fav.setBackgroundResource(R.drawable.star_yellow)

            if (AppController().mMessageUnreadList[position].favourite
                    .equals("0")
            ) {
                holder.fav.setBackgroundResource(R.drawable.star_yellow)
                AppController().mMessageUnreadList[position].favourite="1"
                callfavouriteStatusApi(
                    "1",
                    AppController().mMessageUnreadList[position].pushid
                )
            } else {
                holder.fav.setBackgroundResource(R.drawable.star_gray)
                AppController().mMessageUnreadList[position].favourite="0"
                callfavouriteStatusApi(
                    "0",
                    AppController().mMessageUnreadList[position].pushid
                )
            }
        }
        holder.msgBoxUnRead.setOnClickListener {
            if (AppController().mMessageUnreadList[position].isChecked) {
                AppController().mMessageUnreadList[position].isChecked=false
            } else {
                AppController().mMessageUnreadList[position].isChecked=true
            }
            var marked_count = 0
            for (i in 0 until AppController().mMessageUnreadList.size) {
                if (AppController().mMessageUnreadList.get(i).isChecked) {
                    marked_count++
                }
            }

        }


        if (AppController().isVisibleUnreadBox) {
            holder.msgBoxUnRead.visibility = View.VISIBLE
            holder.viewFirst.visibility = View.GONE
            holder.viewTxt.visibility = View.VISIBLE
        } else {
            holder.msgBoxUnRead.visibility = View.INVISIBLE
            holder.viewFirst.visibility = View.VISIBLE
            holder.viewTxt.visibility = View.GONE
        }

        holder.msgBoxUnRead.setOnClickListener {
            if (AppController().mMessageUnreadList.get(position).isChecked) {
                holder.msgBoxUnRead.setBackgroundResource(R.drawable.check_box_white_2)
                AppController().mMessageUnreadList.get(position).isChecked=false
                AppController().click_count = 0
            } else {
                holder.msgBoxUnRead.setBackgroundResource(R.drawable.check_box_white_tick_2)
                AppController().mMessageUnreadList.get(position).isChecked=true
                AppController().click_count = 1
            }
            var marked_count = 0
            for (i in 0 until AppController().mMessageUnreadList.size) {
                if (AppController().mMessageUnreadList.get(i).isChecked) {
                    marked_count++
                }
            }
            if (marked_count == AppController().mMessageUnreadList.size) {
                checkBox!!.setVisibility(View.VISIBLE)
                msgUnread!!.setVisibility(View.VISIBLE)
                checkBox!!.setBackgroundResource(R.drawable.check_box_header_tick)
            } else if (marked_count == 0) {
                checkBox!!.setVisibility(View.GONE)
                msgUnread!!.setVisibility(View.GONE)
                AppController().isVisibleUnreadBox = false
                markRead!!.setVisibility(View.GONE)
                notifyDataSetChanged()
                // notifyItemRangeChanged(0, getItemCount());
            } else if (marked_count == 1) {
                AppController().click_count = 0
                notifyDataSetChanged()
                //notifyItemRangeChanged(0, getItemCount());
            } else {
                markRead!!.setVisibility(View.VISIBLE)
                checkBox!!.setBackgroundResource(R.drawable.check_box_header)
            }
            if (marked_count >= 1) {
                AppController().click_count = 0
                markRead.visibility = View.VISIBLE
            }

        }
        if (AppController().mMessageUnreadList.get(position).favourite.equals("1")) {
            holder.fav.setBackgroundResource(R.drawable.star_yellow)
        } else {
            holder.fav.setBackgroundResource(R.drawable.star_gray)
        }

        if (AppController().mMessageUnreadList.get(position).isChecked) {
            holder.msgBoxUnRead.setBackgroundResource(R.drawable.check_box_white_tick_2)
        } else {
            holder.msgBoxUnRead.setBackgroundResource(R.drawable.check_box_white_2)
        }
        holder.unreadClick.setOnClickListener {
            AppController().mMessageUnreadList.get(position).status="1"
            var mIntent: Intent
            if (AppController().mMessageUnreadList.get(position).type
                    .equals("image") || AppController().mMessageUnreadList.get(position)
                    .type.equals("text") || AppController().mMessageUnreadList.get(
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
                if (!AppController().mMessageUnreadList.get(position).url.equals("")) {
                    pushNotificationDetail =
                        pushNotificationDetail + "<center><img src='" + AppController().mMessageUnreadList.get(
                            position
                        ).url + "'width='100%', height='auto'>"
                }
                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
                val mdate: String = AppController().mMessageUnreadList.get(position).date
                var date: Date? = null
                try {
                    date = inputFormat.parse(mdate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val outputDateStr = outputFormat.format(date)
                pushNotificationDetail = pushNotificationDetail +
                        "<p class='description'>" + AppController().mMessageUnreadList.get(position)
                    .message
                pushNotificationDetail =
                    "$pushNotificationDetail<p class='date'>$outputDateStr</p></body>\n</html>" *//*+"<p class='description'>"+pushNotificationArrayList.get(position).getDay() + "-" + pushNotificationArrayList.get(position).getMonthString() + "-" + pushNotificationArrayList.get(position).getYear()+" "+pushNotificationArrayList.get(position).getPushTime()+"</p>"+*//*
                mIntent = Intent(mContext, ImageActivity::class.java)
                mIntent.putExtra("webViewComingDetail", pushNotificationDetail)
                mIntent.putExtra("title", AppController().mMessageUnreadList.get(position).title)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra( "PASSING_ARRAY", AppController().mMessageUnreadList)
                mIntent.putExtra("position", position)
                mContext.startActivity(mIntent)
            }
            if (AppController().mMessageUnreadList.get(position).type
                    .equals("audio")
            ) {
                mIntent = Intent(mContext, AudioPlayerViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title", AppController().mMessageUnreadList.get(position).title)
                mIntent.putExtra("PASSING_ARRAY", AppController().mMessageUnreadList)
                mContext.startActivity(mIntent)
            }
            if (AppController().mMessageUnreadList.get(position).type
                    .equals("video")
            ) {
                mIntent = Intent(mContext, VideoWebViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title", AppController().mMessageUnreadList.get(position).title)
                mIntent.putExtra("PASSING_ARRAY", AppController().mMessageUnreadList)
                mContext.startActivity(mIntent)
            }


        }
    }



    override fun getItemCount(): Int {
        return AppController().mMessageUnreadList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // var itemImage: ImageView
        var unreadMessage: TextView
        var unreadTxt:TextView
        var msgBoxUnRead:ImageView
        var viewFirst:TextView
        var viewTxt:TextView
        var fav:ImageView
        var cardView: CardView
        var starLinear: LinearLayout
        var unreadClick: RelativeLayout


        init {
            unreadMessage = view.findViewById(R.id.unreadMessage)
           unreadTxt = view.findViewById(R.id.unreadTxt)
            msgBoxUnRead = view.findViewById(R.id.msgBoxUnRead)
           viewFirst = view.findViewById(R.id.viewFirst)
            viewTxt = view.findViewById(R.id.viewTxt)
            fav = view.findViewById(R.id.starUR)
            cardView = view.findViewById(R.id.card_view)
            starLinear = view.findViewById(R.id.starLinear)
            unreadClick = view.findViewById(R.id.unreadClick)
        }
    }
    private fun callfavouriteStatusApi(favourite:String, pushId:String){
        val call: Call<NotificationFavouriteModel> = ApiClient.getClient.notification_favourite(
            PreferenceManager().getaccesstoken(mContext).toString(),PreferenceManager().getUserId(mContext).toString(),
            favourite,pushId)

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


}*/

