package com.example.bskl_kotlin.fragment.messages.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel

class StudentRecyclerAdapter (
    var mContext: Context, var mReadArraylist:ArrayList<PushNotificationModel>
) :

    RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.student_image_name, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mReadArraylist[position].student_name.equals("")) {
            holder.studName!!.setVisibility(View.GONE)
            holder.imageIcon!!.setVisibility(View.GONE)
        } else {
            holder.studName!!.setText(mReadArraylist[position].student_name)
            holder.imageIcon!!.setImageResource(R.drawable.student)
        }

    }



    override fun getItemCount(): Int {
        return mReadArraylist.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageIcon: ImageView? = null
        var studName: TextView? = null

        init {
            imageIcon = view.findViewById(R.id.imagicon)
            studName = view.findViewById(R.id.studName)
        }
    }


}