package com.example.bskl_kotlin.fragment.timetable.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.timetable.model.WeekModel

class TimeTableWeekListAdapter(
    private val mContext: Context,
    mWeekList: ArrayList<WeekModel>,
    weekPosition: Int
) :
    RecyclerView.Adapter<TimeTableWeekListAdapter.MyViewHolder>() {
    private val mWeekList: ArrayList<WeekModel>
    var weekPosition = 0

    inner class MyViewHolder(view: View) : ViewHolder(view) {
        var weekTxt: TextView
        var lineImage: ImageView
        var downArrowImage: ImageView

        init {
            weekTxt = view.findViewById<View>(R.id.weekTxt) as TextView
            lineImage = view.findViewById<View>(R.id.lineImage) as ImageView
            downArrowImage = view.findViewById<View>(R.id.downArrowImage) as ImageView
        }
    }

    init {
        this.mWeekList = mWeekList
        this.weekPosition = weekPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_weeklist_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.weekTxt.setText(mWeekList[position].weekName)
        if (mWeekList[position].positionSelected !== -1) {
            holder.weekTxt.setTextColor(mContext.resources.getColor(R.color.timetableblue))
            holder.lineImage.visibility = View.VISIBLE
            holder.downArrowImage.visibility = View.VISIBLE
        } else {
            holder.weekTxt.setTextColor(mContext.resources.getColor(R.color.dark_grey1))
            holder.lineImage.visibility = View.INVISIBLE
            holder.downArrowImage.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return mWeekList.size
    }
}