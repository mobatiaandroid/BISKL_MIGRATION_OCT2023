package com.example.bskl_kotlin.fragment.calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.calendar.model.StudentDetailModelJava
import com.example.kingsapp.activities.calender.model.StudentDetailModel


class StudentRecyclerCalenderAdapter(
    private val mContext: Context,
    mStudentDetail: java.util.ArrayList<StudentDetailModelJava>
) :
    RecyclerView.Adapter<StudentRecyclerCalenderAdapter.MyViewHolder>() {
    private val mStudentDetail: ArrayList<StudentDetailModelJava>
    var dept: String? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageIcon: ImageView
        var studName: TextView

        init {
            imageIcon = view.findViewById<ImageView>(R.id.imagicon)
            studName = view.findViewById<TextView>(R.id.studName)
        }
    }

    init {
        this.mStudentDetail = mStudentDetail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_image_name, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (mStudentDetail[position].name.equals("")) {
            holder.studName.visibility = View.GONE
            holder.imageIcon.visibility = View.GONE
        } else {
            holder.studName.setText(mStudentDetail[position].name)
            holder.imageIcon.setImageResource(R.drawable.student)
        }
    }

    override fun getItemCount(): Int {
        return mStudentDetail.size
    }
}

