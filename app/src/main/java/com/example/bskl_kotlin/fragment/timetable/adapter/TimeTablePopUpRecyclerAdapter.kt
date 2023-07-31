package com.example.bskl_kotlin.fragment.timetable.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.timetable.model.DayModel
import com.example.bskl_kotlin.manager.AppUtils

class TimeTablePopUpRecyclerAdapter(
    private val mContext: Context,
    timeTableList: ArrayList<DayModel?>?
) :
    RecyclerView.Adapter<TimeTablePopUpRecyclerAdapter.MyViewHolder>() {
    lateinit var timeTableList: ArrayList<DayModel>
    var dept: String? = null

    inner class MyViewHolder(view: View) : ViewHolder(view) {
        var dateTimeTextView: TextView
        var nameTextView: TextView
        var subjectNameTextView: TextView
        var periodTextView: TextView

        init {
            dateTimeTextView = view.findViewById(R.id.dateTimeTextView)
            nameTextView = view.findViewById(R.id.nameTextView)
            subjectNameTextView = view.findViewById(R.id.subjectNameTextView)
            periodTextView = view.findViewById(R.id.periodTextView)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_timetable_popup, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.dateTimeTextView.setText(
            timeTableList[position].day + " | " + AppUtils().timeParsingToAmPm(
                timeTableList[position].starttime
            ) + " - " + AppUtils().timeParsingToAmPm(
                timeTableList[position].endtime
            )
        )
        if (timeTableList[position].staff.equals("")) {
            holder.nameTextView.visibility = View.GONE
        } else {
            holder.nameTextView.setText(timeTableList[position].staff)
            holder.nameTextView.visibility = View.VISIBLE
        }
        if (position == 0) {
            holder.periodTextView.setText(timeTableList[position].sortname)
        } else {
            holder.periodTextView.text = ""
        }
        holder.subjectNameTextView.setText(timeTableList[position].subject_name)
        if (timeTableList.size > 1) {
            if (position != timeTableList.size - 1) {
                holder.subjectNameTextView.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    null,
                    mContext.resources.getDrawable(R.drawable.listdividewhite)
                )
            } else {
                holder.subjectNameTextView.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    null,
                    null
                )
            }
        } else {
            holder.subjectNameTextView.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                null,
                null
            )
        }
    }

    override fun getItemCount(): Int {
        return timeTableList.size
    }
}
