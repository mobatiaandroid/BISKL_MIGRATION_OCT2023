package com.example.bskl_kotlin.fragment.absence.adapter

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.absence.model.LeavesModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class AbsenceRecyclerAdapter (private val mContext: Context, var mLeavesList: ArrayList<LeavesModel>) :
    RecyclerView.Adapter<AbsenceRecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listDate:TextView= view.findViewById(R.id.listDate)
        var listStatus:TextView = view.findViewById(R.id.listStatus)


        var listBackGround:RelativeLayout = view.findViewById(R.id.listBackGround)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.absence_recycler_list_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (mLeavesList.get(position).to_date
                .equals(mLeavesList.get(position).from_date)
        ) {
            holder.listDate.setText(
                dateConversionMMM(
                    mLeavesList.get(position).from_date
                )
            ) //dateParsingToDdMmYyyy
        } else {
            holder.listDate.text =
                Html.fromHtml(
                    dateConversionMMM(mLeavesList.get(position).from_date) + " to " +
                            dateConversionMMM(mLeavesList.get(position).to_date)
                )
        }
        if (mLeavesList.get(position).status.equals("1")) {
            holder.listStatus.text = "Approved"
            holder.listStatus.setTextColor(mContext.resources.getColor(R.color.bisad_green))
        } else if (mLeavesList.get(position).status.equals("2")) {
            holder.listStatus.text = "Pending"
            holder.listStatus.setTextColor(mContext.resources.getColor(R.color.rel_six))
        } else if (mLeavesList.get(position).status.equals("3")) {
            holder.listStatus.text = "Rejected"
            holder.listStatus.setTextColor(mContext.resources.getColor(R.color.rel_nine))
        }
    }
    override fun getItemCount(): Int {
        return mLeavesList.size
    }
    fun dateConversionMMM(inputDate: String?): String? {
        var mDate = ""
        try {
            val date: Date
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            date = formatter.parse(inputDate)
            //Subtracting 6 hours from selected time
            val time = date.time

            //SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMMM yyyy");
            val formatterFullDate = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            mDate = formatterFullDate.format(time)
        } catch (e: Exception) {
            Log.d("Exception", "" + e)
        }
        return mDate
    }
}