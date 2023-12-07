package com.example.bskl_kotlin.activity.settings.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.settings.model.PassportDetailModelNew

class PassportDetailRecyclerAdapter (
    var mContext: Context, var mStudentDetailModel:ArrayList<PassportDetailModelNew>
) :

    RecyclerView.Adapter<PassportDetailRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_student_detail, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mStudentDetailModel[position].value
                .equals("No Data") || mStudentDetailModel[position].value
                .equals("No data") || mStudentDetailModel[position].value
                .equals("Nodata")
        ) {
            holder.valueTextView.setTextColor(mContext.resources.getColor(R.color.red))
        } else {
            holder.valueTextView.setTextColor(mContext.resources.getColor(R.color.black))
        }
        System.out.println("Expired Value" + mStudentDetailModel[position].is_expired)
        if (mStudentDetailModel[position].is_expired.equals("1")) {
            if (mStudentDetailModel[position].title.equals("Expiry Date")) {
                holder.valueTextView.setTextColor(mContext.resources.getColor(R.color.red))
            }
        } else {
            if (mStudentDetailModel[position].title.equals("Expiry Date")) {
                if (mStudentDetailModel[position].value
                        .equals("No Data") || mStudentDetailModel[position].value
                        .equals("No data") || mStudentDetailModel[position].value
                        .equals("Nodata")
                ) {
                    holder.valueTextView.setTextColor(mContext.resources.getColor(R.color.red))
                }
            }
        }

        if (mStudentDetailModel[position].title
                .equals("passport_expired") || mStudentDetailModel[position].title
                .equals("visa_expired")
        ) {
            holder.valueTextView.visibility = View.GONE
            holder.nameTextView.visibility = View.GONE
            holder.namelayout.setVisibility(View.GONE)
        } else {
            holder.valueTextView.visibility = View.VISIBLE
            holder.nameTextView.visibility = View.VISIBLE
            holder.namelayout.setVisibility(View.VISIBLE)
        }
        holder.valueTextView.setText(mStudentDetailModel[position].value)
        holder.nameTextView.setText(mStudentDetailModel[position].title)


    }



    override fun getItemCount(): Int {
        return mStudentDetailModel.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var nameTextView: TextView
        var valueTextView: TextView
        var namelayout: LinearLayout


        init {
            nameTextView = view.findViewById(R.id.nameTextView)
            valueTextView = view.findViewById(R.id.valueTextView)
            namelayout = view.findViewById(R.id.namelayout)
        }
    }


}