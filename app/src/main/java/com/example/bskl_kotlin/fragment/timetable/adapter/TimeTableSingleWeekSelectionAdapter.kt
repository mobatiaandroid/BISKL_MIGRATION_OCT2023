package com.example.bskl_kotlin.fragment.timetable.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.timetable.model.DayModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimeTableSingleWeekSelectionAdapter (
    private val mContext: Context,
    private var mRangeModel: ArrayList<DayModel>
) :
    RecyclerView.Adapter<TimeTableSingleWeekSelectionAdapter.MyViewHolder>() {
    //lateinit var timeTableList: ArrayList<DayModel>
    var breakPos = 0

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var timeTxt: TextView
        var timeAPTxt: TextView
        var subjectNameTxt: TextView
        var tutorNameTxt: TextView
        var subjectTxt: TextView
        var breakTxt: TextView
        var relSub: LinearLayout
        var starLinear: LinearLayout
        var llreadbreak: RelativeLayout
        var llread: RelativeLayout
        var card_view: CardView

        init {

            //weekTxt = (TextView) view.findViewById(R.id.weekTxt);
            timeTxt = view.findViewById<View>(R.id.timeTxt) as TextView
            timeAPTxt = view.findViewById<View>(R.id.timeAPTxt) as TextView
            subjectNameTxt = view.findViewById<View>(R.id.subjectNameTxt) as TextView
            tutorNameTxt = view.findViewById<View>(R.id.tutorNameTxt) as TextView
            subjectTxt = view.findViewById<View>(R.id.subjectTxt) as TextView
            breakTxt = view.findViewById<View>(R.id.breakTxt) as TextView
            card_view = view.findViewById<View>(R.id.card_view) as CardView
            relSub = view.findViewById<View>(R.id.relSub) as LinearLayout
            starLinear = view.findViewById<View>(R.id.starLinear) as LinearLayout
            llreadbreak = view.findViewById<View>(R.id.llreadbreak) as RelativeLayout
            llread = view.findViewById<View>(R.id.llread) as RelativeLayout
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_selection_time_table_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


Log.e("singleweekis break",mRangeModel[0].isBreak.toString())
        /* DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String dateString = dateFormat.format(mRangeModel.get(position).getStarttime());*/
        /*    String pattern = " hh.mm aa";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(mRangeModel.get(position).getStarttime());*/
        breakPos=0
        if (mRangeModel[position].isBreak == 1) {
            holder.llread.visibility = View.GONE
            holder.llreadbreak.visibility = View.VISIBLE
            holder.starLinear.visibility = View.INVISIBLE
            holder.breakTxt.setText(mRangeModel[position].sortname)
            holder.timeTxt.setTextColor(mContext.resources.getColor(R.color.white))
            holder.timeTxt.setText(timeParsingToAmPm(mRangeModel[position].starttime))
            breakPos = breakPos + 1
            Log.e("breakpos",breakPos.toString())
            if (breakPos == 1) {
                Log.e("break1",mRangeModel[position].sortname.toString())
                if (mRangeModel[position].sortname.equals("Break")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.timetableblue))
                } else if (mRangeModel[position].sortname.equals("Lunch")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.late))
                } else {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.authorisedabsence))
                }
            } else if (breakPos == 2) {
                Log.e("break2",mRangeModel[position].sortname.toString())
                if (mRangeModel[position].sortname.equals("Break")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.timetableblue))
                } else if (mRangeModel[position].sortname.equals("Lunch")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.late))
                } else {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.authorisedabsence))
                }
            } else if (breakPos == 3) {
                Log.e("break3",mRangeModel[position].sortname.toString())
                if (mRangeModel[position].sortname.equals("Break")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.timetableblue))
                } else if (mRangeModel[position].sortname.equals("Lunch")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.late))
                } else {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.authorisedabsence))
                }
            } else {
                Log.e("break4",mRangeModel[position].sortname.toString())
                if (mRangeModel[position].sortname.equals("Break")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.timetableblue))
                } else if (mRangeModel[position].sortname.equals("Lunch")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.late))
                } else {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.authorisedabsence))
                }
            }
        } else {
            holder.llread.visibility = View.VISIBLE
            holder.timeAPTxt.visibility = View.GONE
            holder.llreadbreak.visibility = View.GONE
            holder.starLinear.visibility = View.GONE
            holder.timeTxt.setText(timeParsingToAmPm(mRangeModel[position].starttime))
            holder.tutorNameTxt.setText(mRangeModel[position].staff)
            holder.subjectTxt.setText(mRangeModel[position].sortname)
            holder.subjectNameTxt.setText(mRangeModel[position].subject_name)
        }

    }

    override fun getItemCount(): Int {
        return mRangeModel.size
    }
    fun timeParsingToAmPm(date: String?): String? {
        var strCurrentDate = ""
        var format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }
}