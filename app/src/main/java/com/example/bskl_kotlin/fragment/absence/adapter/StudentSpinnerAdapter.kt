package com.example.bskl_kotlin.fragment.absence.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.common.model.StudentListModel
import com.example.bskl_kotlin.fragment.absence.model.StudentModel

internal class StudentSpinnerAdapter(private val mContext: Context, var studentList: ArrayList<StudentListModel>) :
    RecyclerView.Adapter<StudentSpinnerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
        var listTxtClass: TextView = view.findViewById(R.id.listTxtClass)
        var imagicon: ImageView = view.findViewById(R.id.imagicon)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_student_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val stud_pos = studentList[position]
        holder.listTxtTitle.text = stud_pos.name

        if (stud_pos.alumi.equals("1")) {
            holder.listTxtClass.setTextColor(mContext.resources.getColor(R.color.grey))
            holder.listTxtClass.text = "Alumini"
        } else {
            holder.listTxtClass.setText(stud_pos.mClass)
        }

        if(!stud_pos.photo.equals(""))
        {
            Glide.with(mContext) //1
                .load(stud_pos.photo)
                .placeholder(R.drawable.boy)
                .error(R.drawable.boy)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(holder.imagicon)
        }
        else{
            holder.imagicon.setImageResource(R.drawable.boy)
        }
    }
    override fun getItemCount(): Int {
        return studentList.size
    }
}