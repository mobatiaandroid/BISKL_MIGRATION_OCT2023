package com.example.bskl_kotlin.activity.notification.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.mobatia.bskl.R

class StudentUnReadRecyclerAdapter  (
    var mContext:Context , var mReadArraylist:ArrayList<String>
) :

    RecyclerView.Adapter<StudentUnReadRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.student_image_name, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        holder.submenu.setText(mnNewsLetterModelArrayList.get(position).getSubmenu());
        if (mReadArraylist[position].equals("")) {
            holder.studName.visibility = View.GONE
            holder.imageIcon.visibility = View.GONE
        } else {
            holder.studName.text = mReadArraylist[position]
            holder.imageIcon.setImageResource(R.drawable.student)
        }

    }



    override fun getItemCount(): Int {
        return mReadArraylist.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageIcon: ImageView
        var studName: TextView


        init {
            imageIcon = view.findViewById(R.id.imagicon)
            studName = view.findViewById<TextView>(R.id.studName)
        }
    }



}