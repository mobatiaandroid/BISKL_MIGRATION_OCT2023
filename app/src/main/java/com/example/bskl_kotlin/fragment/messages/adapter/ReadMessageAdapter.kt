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
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.activity.notification.AudioPlayerViewActivity
import com.example.bskl_kotlin.activity.notification.ImageActivity
import com.example.bskl_kotlin.activity.notification.VideoWebViewActivity
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.messages.model.NotificationFavouriteModel
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

class ReadMessageAdapter(
    var mContext:Context, var mMessageReadList:ArrayList<PushNotificationModel>,
    var checkBoxRead:ImageView, var markUnRead:TextView,
    var markRead:TextView,var msgReadSelect:TextView
) :

    RecyclerView.Adapter<ReadMessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.message_read_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.starLinearR.setOnClickListener {
            if (AppController().mMessageReadList[position].favourite.equals("0")
            ) {
                holder.fav.setBackgroundResource(R.drawable.star_yellow)
                AppController().mMessageReadList[position].favourite="1"
                callfavouriteStatusApi(
                    "1",
                    AppController().mMessageReadList[position].pushid
                )
            } else {
                holder.fav.setBackgroundResource(R.drawable.star_gray)
                AppController().mMessageReadList[position].favourite="0"
                callfavouriteStatusApi(
                    "0",
                    AppController().mMessageReadList[position].pushid
                )
            }
        }
        //onBottomReachedListener.onBottomReached(position)
        holder.readMessage.setText(AppController().mMessageReadList.get(position).day)
        holder.readTxt.setText(AppController().mMessageReadList.get(position).title)

        if (AppController().isVisibleReadBox) {
            holder.msgBoxRead.visibility = View.VISIBLE
            holder.viewFirst.visibility = View.GONE
            holder.viewTxt.visibility = View.VISIBLE
        } else {
            holder.msgBoxRead.visibility = View.INVISIBLE
            holder.viewFirst.visibility = View.VISIBLE
            holder.viewTxt.visibility = View.GONE
        }

        holder.msgBoxRead.setOnClickListener {
            if (AppController().mMessageUnreadList.get(position).isMarked) {
                holder.msgBoxRead.setBackgroundResource(R.drawable.check_box_white_2)
                AppController().mMessageReadList.get(position).isMarked=false
                AppController().click_count_read = 0
            } else {
                holder.msgBoxRead.setBackgroundResource(R.drawable.check_box_white_tick_2)
                AppController().mMessageUnreadList.get(position).isMarked=true
                AppController().click_count_read = 1
            }
            var marked_count = 0
            for (i in 0 until AppController().mMessageReadList.size) {
                if (AppController().mMessageReadList.get(i).isMarked) {
                    marked_count++
                }
            }
            if (marked_count == AppController().mMessageReadList.size) {
                checkBoxRead.visibility = View.VISIBLE
                msgReadSelect.visibility = View.VISIBLE
                checkBoxRead.setBackgroundResource(R.drawable.check_box_header_tick)
            } else if (marked_count == 0) {
                checkBoxRead.visibility = View.GONE
                msgReadSelect.visibility = View.GONE
                AppController().isVisibleReadBox = false
                markUnRead.visibility = View.GONE
                AppController().click_count_read = 0
                notifyDataSetChanged()
            } else if (marked_count == 1) {
                AppController().click_count_read = 0
                notifyDataSetChanged()
                //notifyItemRangeChanged(0, getItemCount());
            } else {

                //AppController.click_count_read++;
                markUnRead.visibility = View.VISIBLE
                checkBoxRead.setBackgroundResource(R.drawable.check_box_header)
            }
            if (marked_count >= 1) {
                AppController().click_count_read = 0
                markUnRead.visibility = View.VISIBLE
            }

        }
        if (AppController().mMessageReadList.get(position).favourite.equals("1")) {
            holder.fav.setBackgroundResource(R.drawable.star_yellow)
        } else {
            holder.fav.setBackgroundResource(R.drawable.star_gray)
        }


        if (AppController().mMessageReadList.get(position).isMarked) {
            holder.msgBoxRead.setBackgroundResource(R.drawable.check_box_white_tick_2)
        } else {
            holder.msgBoxRead.setBackgroundResource(R.drawable.check_box_white_2)
        }
        holder.llReadClick.setOnClickListener {

            var mIntent: Intent
            if (AppController().mMessageReadList.get(position).type
                    .equals("image") || AppController().mMessageReadList.get(position)
                    .type.equals("text") || AppController().mMessageReadList.get(
                    position
                ).type.equals("")
            ) {
                var pushNotificationDetail = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                    <style>
                    
                    @font-face {
                    font-family: SourceSansPro-Semibold;src: url(SourceSansPro-Semibold.ttf);font-family: SourceSansPro-Regular;src: url(SourceSansPro-Regular.ttf);}.title {font-family: SourceSansPro-Regular;font-size:16px;text-align:left;color:	#46C1D0;}.description {font-family: SourceSansPro-Semibold;text-align:justify;font-size:14px;color: #000000;}.date {font-family: SourceSansPro-Semibold;text-align:right;font-size:12px;color: #A9A9A9;}</style>
                    </head><body>
                    """.trimIndent()
                if (!AppController().mMessageReadList.get(position).url.equals("")) {
                    pushNotificationDetail =
                        pushNotificationDetail + "<center><img src='" + AppController().mMessageReadList.get(
                            position
                        ).url + "'width='100%', height='auto'>"
                }
                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
                val mdate: String = AppController().mMessageReadList.get(position).date
                var date: Date? = null
                try {
                    date = inputFormat.parse(mdate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val outputDateStr = outputFormat.format(date)
                pushNotificationDetail = pushNotificationDetail +
                        "<p class='description'>" + AppController().mMessageReadList.get(position)
                    .message
                pushNotificationDetail =
                    "$pushNotificationDetail<p class='date'>$outputDateStr</p></body>\n</html>" /*+"<p class='description'>"+pushNotificationArrayList.get(position).getDay() + "-" + pushNotificationArrayList.get(position).getMonthString() + "-" + pushNotificationArrayList.get(position).getYear()+" "+pushNotificationArrayList.get(position).getPushTime()+"</p>"+*/
                mIntent = Intent(mContext, ImageActivity::class.java)
                mIntent.putExtra("webViewComingDetail", pushNotificationDetail)
                mIntent.putExtra("title", AppController().mMessageReadList.get(position).title)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra( "PASSING_ARRAY", AppController().mMessageReadList)
                mIntent.putExtra("position", position)
                mContext.startActivity(mIntent)
            }
            if (AppController().mMessageReadList.get(position).type
                    .equals("audio")
            ) {
                mIntent = Intent(mContext, AudioPlayerViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title", AppController().mMessageReadList.get(position).title)
                mIntent.putExtra("PASSING_ARRAY", AppController().mMessageReadList)
                mContext.startActivity(mIntent)
            }
            if (AppController().mMessageReadList.get(position).type
                    .equals("video")
            ) {
                mIntent = Intent(mContext, VideoWebViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title", AppController().mMessageReadList.get(position).title)
                mIntent.putExtra("PASSING_ARRAY", AppController().mMessageReadList)
                mContext.startActivity(mIntent)
            }


        }
    }



    override fun getItemCount(): Int {
        return AppController().mMessageReadList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var readMessage: TextView
        var readTxt: TextView
        var msgBoxRead: ImageView
        var fav: ImageView
        var viewFirst: TextView
        var viewTxt: TextView
        var starLinearR: LinearLayout
        var llReadClick: RelativeLayout
        var cardView:CardView


        init {
            readMessage = view.findViewById<TextView>(R.id.readMessage)
            readTxt = view.findViewById<TextView>(R.id.readTxt)
            msgBoxRead = view.findViewById<ImageView>(R.id.msgBoxRead)
            cardView = view.findViewById(R.id.card_view)
            llReadClick = view.findViewById<RelativeLayout>(R.id.llread)
            viewFirst = view.findViewById(R.id.viewFirst)
            viewTxt = view.findViewById(R.id.viewTxt)
            fav = view.findViewById<ImageView>(R.id.starR)
            starLinearR = view.findViewById<LinearLayout>(R.id.starLinearR)
        }
    }
    private fun callfavouriteStatusApi(favourite:String, pushId:String){
        val call: Call<NotificationFavouriteModel> = ApiClient.getClient.notification_favourite(
            PreferenceManager().getaccesstoken(mContext).toString(),
            PreferenceManager().getUserId(mContext).toString(),
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