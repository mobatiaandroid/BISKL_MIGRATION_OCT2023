package com.example.bskl_kotlin.activity.settings.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.settings.model.StudentDetailModelNew

class StudentDetailRecyclerAdapter (
    var mContext: Context, var mStudentDetailModel:ArrayList<StudentDetailModelNew>
) :

    RecyclerView.Adapter<StudentDetailRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_student_detail, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.valueTextView.setText(mStudentDetailModel[position].value)
        holder.nameTextView.setText(mStudentDetailModel[position].title)

    }



    override fun getItemCount(): Int {
        return mStudentDetailModel.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var nameTextView: TextView
        lateinit var valueTextView: TextView


        init {
            nameTextView = view.findViewById(R.id.nameTextView)
            valueTextView = view.findViewById(R.id.valueTextView)
        }
    }


}