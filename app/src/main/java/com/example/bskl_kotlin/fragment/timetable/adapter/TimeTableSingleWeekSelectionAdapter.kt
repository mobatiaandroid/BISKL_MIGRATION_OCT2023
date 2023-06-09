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
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.timetable.model.DayModel
import com.example.bskl_kotlin.manager.AppUtils

class TimeTableSingleWeekSelectionAdapter(
    private val mContext: Context,
    private var mRangeModel: ArrayList<DayModel>
) :
    RecyclerView.Adapter<TimeTableSingleWeekSelectionAdapter.MyViewHolder>() {
    private val timeTxt: TextView? = null
    private val timeAPTxt: TextView? = null
    private val subjectNameTxt: TextView? = null
    private val tutorNameTxt: TextView? = null
    private val subjectTxt: TextView? = null
    var breakPos = 0
   // private val mRangeModel: ArrayList<DayModel>

    inner class MyViewHolder(view: View) : ViewHolder(view) {
        //   TextView weekTxt;
        lateinit var  timeTxt: TextView
        lateinit var timeAPTxt: TextView
        lateinit var subjectNameTxt: TextView
        lateinit var tutorNameTxt: TextView
        lateinit var subjectTxt: TextView
        lateinit var breakTxt: TextView
        lateinit var card_view: CardView
        lateinit var relSub: LinearLayout
        lateinit var starLinear: LinearLayout
        lateinit var llreadbreak: RelativeLayout
       lateinit var  llread: RelativeLayout

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

        /* DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String dateString = dateFormat.format(mRangeModel.get(position).getStarttime());*/
        /*    String pattern = " hh.mm aa";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(mRangeModel.get(position).getStarttime());*/
        if (mRangeModel[position].staff.equals("")) {
            Log.e("sucess","Failed")

            holder.llread.visibility = View.GONE
            holder.llreadbreak.visibility = View.VISIBLE
            holder.starLinear.visibility = View.INVISIBLE
            holder.breakTxt.setText(mRangeModel[position].sortname)
            holder.timeTxt.setTextColor(mContext.resources.getColor(R.color.white))
            holder.timeTxt.setText(AppUtils().timeParsingToAmPm(mRangeModel[position].starttime))
            breakPos = breakPos + 1
            if (breakPos == 1) {
                if (mRangeModel[position].sortname.equals("Break")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.timetableblue))
                } else if (mRangeModel[position].sortname.equals("Lunch")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.late))
                } else {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.authorisedabsence))
                }
            } else if (breakPos == 2) {
                if (mRangeModel[position].sortname.equals("Break")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.timetableblue))
                } else if (mRangeModel[position].sortname.equals("Lunch")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.late))
                } else {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.authorisedabsence))
                }
            } else if (breakPos == 3) {
                if (mRangeModel[position].sortname.equals("Break")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.timetableblue))
                } else if (mRangeModel[position].sortname.equals("Lunch")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.late))
                } else {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.authorisedabsence))
                }
            } else {
                if (mRangeModel[position].sortname.equals("Break")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.timetableblue))
                } else if (mRangeModel[position].sortname.equals("Lunch")) {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.late))
                } else {
                    holder.relSub.setBackgroundColor(mContext.resources.getColor(R.color.authorisedabsence))
                }
            }
        } else {
            Log.e("failed","Failed")
            holder.llread.visibility = View.VISIBLE
            holder.timeAPTxt.visibility = View.GONE
            holder.llreadbreak.visibility = View.GONE
            holder.starLinear.visibility = View.GONE
            holder.timeTxt.setText(AppUtils().timeParsingToAmPm(mRangeModel[position].starttime))
            holder.tutorNameTxt.setText(mRangeModel[position].staff)
            holder.subjectTxt.setText(mRangeModel[position].sortname)
            holder.subjectNameTxt.setText(mRangeModel[position].subject_name)
        }
    }

    override fun getItemCount(): Int {
        println("rangemodel size" + mRangeModel.size)
        return mRangeModel.size
    }
}