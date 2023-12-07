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

class ReadMessageAdapter(
    var mContext:Context, var mMessageReadList:ArrayList<PushNotificationModel>,
    var checkBoxRead:ImageView, var markUnRead:TextView,
    var markRead:TextView,var msgReadSelect:TextView,
    var listener:ClickListener
) :

    RecyclerView.Adapter<ReadMessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.message_read_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("readadpter",mMessageReadList.size.toString())

       /* holder.starLinearR.setOnClickListener {
            if (mMessageReadList[position].favourite.equals("0")
            ) {
                holder.fav.setBackgroundResource(R.drawable.star_yellow)
               mMessageReadList[position].favourite="1"
                callfavouriteStatusApi(
                    "1",
                   mMessageReadList[position].pushid
                )
            } else {
                holder.fav.setBackgroundResource(R.drawable.star_gray)
               mMessageReadList[position].favourite="0"
                callfavouriteStatusApi(
                    "0",
                   mMessageReadList[position].pushid
                )
            }
        }
        //onBottomReachedListener.onBottomReached(position)*/
        holder.readMessage.setText(mMessageReadList.get(position).day)
        holder.readTxt.setText(mMessageReadList.get(position).title)

       if(PreferenceManager().getisVisibleReadBox(mContext)==true){
            holder.msgBoxRead.visibility = View.VISIBLE
            holder.viewFirst.visibility = View.GONE
            holder.viewTxt.visibility = View.VISIBLE
        } else {
            holder.msgBoxRead.visibility = View.INVISIBLE
            holder.viewFirst.visibility = View.VISIBLE
            holder.viewTxt.visibility = View.GONE
        }

        holder.msgBoxRead.setOnClickListener {
            PreferenceManager().setisSelectedRead(mContext,false)
            //isSelectedRead=false;
            if (mMessageReadList.get(position).isMarked) {
                holder.msgBoxRead.setBackgroundResource(R.drawable.check_box_white_2)
               mMessageReadList.get(position).isMarked=false
                PreferenceManager().setclick_count_read(mContext,0)
            } else {
                holder.msgBoxRead.setBackgroundResource(R.drawable.check_box_white_tick_2)
                mMessageReadList.get(position).isMarked=true
                PreferenceManager().setclick_count_read(mContext,1)
            }
            var marked_count = 0
            for (i in 0 until mMessageReadList.size) {
                if (mMessageReadList.get(i).isMarked) {
                    marked_count++
                }
            }
            if (marked_count ==mMessageReadList.size) {
                checkBoxRead.visibility = View.VISIBLE
                msgReadSelect.visibility = View.VISIBLE
                checkBoxRead.setBackgroundResource(R.drawable.check_box_header_tick)
            } else if (marked_count == 0) {
                checkBoxRead.visibility = View.GONE
                msgReadSelect.visibility = View.GONE
                PreferenceManager().setisVisibleReadBox(mContext,false)
               // AppController().isVisibleReadBox = false
                markUnRead.visibility = View.GONE
                PreferenceManager().setclick_count_read(mContext,0)
                notifyDataSetChanged()
            } else if (marked_count == 1) {
                PreferenceManager().setclick_count_read(mContext,0)
                notifyDataSetChanged()
                //notifyItemRangeChanged(0, getItemCount());
            } else {

                //AppController().click_count_read++;
                markUnRead.visibility = View.VISIBLE
                checkBoxRead.setBackgroundResource(R.drawable.check_box_header)
            }
            if (marked_count >= 1) {
                PreferenceManager().setclick_count_read(mContext,0)
                markUnRead.visibility = View.VISIBLE
            }

        }
        if (mMessageReadList.get(position).favourite.equals("1")) {
            holder.fav.setBackgroundResource(R.drawable.star_yellow)
        } else {
            holder.fav.setBackgroundResource(R.drawable.star_gray)
        }


        if (mMessageReadList.get(position).isMarked) {
            holder.msgBoxRead.setBackgroundResource(R.drawable.check_box_white_tick_2)
        } else {
            holder.msgBoxRead.setBackgroundResource(R.drawable.check_box_white_2)
        }
        holder.llReadClick.setOnClickListener {

            var mIntent: Intent
            if (mMessageReadList.get(position).type
                    .equals("image") ||mMessageReadList.get(position)
                    .type.equals("text") ||mMessageReadList.get(position).type.equals("Text")||mMessageReadList.get(
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
                if (!mMessageReadList.get(position).url.equals("")) {
                    pushNotificationDetail =
                        pushNotificationDetail + "<center><img src='" +mMessageReadList.get(
                            position
                        ).url + "'width='100%', height='auto'>"
                }
                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
                val mdate: String =mMessageReadList.get(position).date
                var date: Date? = null
                try {
                    date = inputFormat.parse(mdate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val outputDateStr = outputFormat.format(date)
                pushNotificationDetail = pushNotificationDetail +
                        "<p class='description'>" +mMessageReadList.get(position)
                    .message
                pushNotificationDetail =
                    "$pushNotificationDetail<p class='date'>$outputDateStr</p></body>\n</html>" /*+"<p class='description'>"+pushNotificationArrayList.get(position).getDay() + "-" + pushNotificationArrayList.get(position).getMonthString() + "-" + pushNotificationArrayList.get(position).getYear()+" "+pushNotificationArrayList.get(position).getPushTime()+"</p>"+*/
                mIntent = Intent(mContext, ImageActivity::class.java)
                mIntent.putExtra("webViewComingDetail", pushNotificationDetail)
                mIntent.putExtra("title",mMessageReadList.get(position).title)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                //mIntent.putExtra( "PASSING_ARRAY",mMessageReadList)
                mIntent.putExtra("position", position)
                mContext.startActivity(mIntent)
            }
            if (mMessageReadList.get(position).type
                    .equals("audio")
            ) {
                mIntent = Intent(mContext, AudioPlayerViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title",mMessageReadList.get(position).title)
                //mIntent.putExtra("PASSING_ARRAY",mMessageReadList)
                mContext.startActivity(mIntent)
            }
            if (mMessageReadList.get(position).type
                    .equals("video")
            ) {
                mIntent = Intent(mContext, VideoWebViewActivity::class.java)
                mIntent.putExtra("position", position)
                mIntent.putExtra("isfromUnread", true)
                mIntent.putExtra("isfromRead", false)
                mIntent.putExtra("title",mMessageReadList.get(position).title)
                //mIntent.putExtra("PASSING_ARRAY",mMessageReadList)
                mContext.startActivity(mIntent)
            }


        }
    }



    override fun getItemCount(): Int {
        return mMessageReadList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) , View.OnClickListener,
        View.OnLongClickListener{

        var readMessage: TextView
        var readTxt: TextView
        var msgBoxRead: ImageView
        var fav: ImageView
        var viewFirst: TextView
        var viewTxt: TextView
        var starLinearR: LinearLayout
        var llReadClick: RelativeLayout
        var cardView:CardView
        var listenerRefRead: WeakReference<ClickListener>? = null



        init {
            listenerRefRead = WeakReference<ClickListener>(listener)
            readMessage = view.findViewById<TextView>(R.id.readMessage)
            readTxt = view.findViewById<TextView>(R.id.readTxt)
            msgBoxRead = view.findViewById<ImageView>(R.id.msgBoxRead)
            cardView = view.findViewById(R.id.card_view)
            llReadClick = view.findViewById<RelativeLayout>(R.id.llread)
            viewFirst = view.findViewById(R.id.viewFirst)
            viewTxt = view.findViewById(R.id.viewTxt)
            fav = view.findViewById<ImageView>(R.id.starR)
            starLinearR = view.findViewById<LinearLayout>(R.id.starLinearR)
            starLinearR.setOnClickListener(this)
            msgBoxRead.setOnClickListener(this)
            llReadClick.setOnLongClickListener(this)
            llReadClick.setOnClickListener(this)
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v!!.getId() == starLinearR.id) {
                if (mMessageReadList.get(adapterPosition).favourite
                        .equals("0")
                ) {
                    fav.setBackgroundResource(R.drawable.star_yellow)
                    mMessageReadList.get(adapterPosition).favourite="1"
                    callfavouriteStatusApi(
                        "1", mMessageReadList.get(
                            adapterPosition
                        ).pushid
                    )
                } else {
                    fav.setBackgroundResource(R.drawable.star_gray)
                    mMessageReadList.get(adapterPosition).favourite="0"
                    callfavouriteStatusApi(
                        "0", mMessageReadList.get(
                            adapterPosition
                        ).pushid
                    )
                }
            } else if (v.getId() == msgBoxRead.id) {
                listenerRefRead!!.get()!!.onPositionClicked(adapterPosition)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            if (v === llReadClick) {
                listenerRefRead!!.get()!!.onLongClicked(adapterPosition)
            }
            return false
        }
    }
    private fun callfavouriteStatusApi(favourite:String, pushId:String){
        var notfav= NotificationFavApiModel(PreferenceManager().getUserId(mContext).toString(),
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

    fun setOnBottomReachedListener(onBottomReachedListener: OnBottomReachedListener) {

    }


}