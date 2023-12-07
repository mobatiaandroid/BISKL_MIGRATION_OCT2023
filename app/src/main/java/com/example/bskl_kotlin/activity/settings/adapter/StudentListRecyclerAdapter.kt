package com.example.bskl_kotlin.activity.settings.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.settings.model.StudentUserModel
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.AppUtils

class StudentListRecyclerAdapter (
    var mContext: Context, var mStudentList:ArrayList<StudentUserModel>
) :

    RecyclerView.Adapter<StudentListRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.student_list_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (mStudentList[position].alumini.equals("0")) {
            holder.studentAlumini.visibility = View.VISIBLE
            holder.studentAlumini.setText(mStudentList[position].classAndSection)
            holder.imgIcon.visibility = View.VISIBLE
            holder.arrowImg.visibility = View.VISIBLE
        } else {
            holder.studentAlumini.visibility = View.VISIBLE
            holder.studentAlumini.text = "(Alumni)"
            holder.imgIcon.visibility = View.VISIBLE
            holder.arrowImg.visibility = View.GONE
        }

        holder.studentName.setText(mStudentList[position].studName)
        holder.imgIcon.visibility = View.VISIBLE
        // holder.listTxtClass.setText(mStudentList.get(position).getmClass());
        // holder.listTxtClass.setText(mStudentList.get(position).getmClass());
        if (!mStudentList[position].photo.equals("")) {
            Glide.with(mContext)
                .load(AppUtils().replace(mStudentList[position].photo.toString()))
                .placeholder(R.drawable.boy).into(holder.imgIcon)
        } else {
            holder.imgIcon.setImageResource(R.drawable.boy)
        }
        holder.seperator.visibility = View.GONE
    }



    override fun getItemCount(): Int {
        return mStudentList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var studentName: TextView
        lateinit var studentAlumini:TextView
        lateinit var imgIcon: ImageView
        lateinit var arrowImg: ImageView
        lateinit var seperator: View
        lateinit var seperatorBottom: View


        init {
            studentName = view.findViewById(R.id.studentName)
            studentAlumini = view.findViewById<TextView>(R.id.studentAlumini)
            imgIcon = view.findViewById(R.id.iconImg)
            arrowImg = view.findViewById(R.id.arrowImg)
            seperator = view.findViewById<View>(R.id.seperator)
            seperatorBottom = view.findViewById<View>(R.id.seperatorBottom)
        }
    }


}