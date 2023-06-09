package com.example.bskl_kotlin.fragment.absence.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.absence.model.StudentModel

class StrudentSpinnerAdapter(private val mContext: Context, mStudentList: ArrayList<StudentModel>) :
    RecyclerView.Adapter<StrudentSpinnerAdapter.MyViewHolder>() {
    private val mStudentList: ArrayList<StudentModel>
    var dept: String? = null

    inner class MyViewHolder(view: View) : ViewHolder(view) {
        var mTitleTxt: TextView
        var listTxtClass: TextView
        var imgIcon: ImageView
        var relSub: RelativeLayout

        init {
            mTitleTxt = view.findViewById(R.id.listTxtTitle)
            listTxtClass = view.findViewById(R.id.listTxtClass)
            imgIcon = view.findViewById(R.id.imagicon)
            relSub = view.findViewById(R.id.relSub)
        }
    }

    init {
        this.mStudentList = mStudentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_student_list_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (mStudentList[position].alumi.equals("0")) {
            holder.relSub.visibility = View.VISIBLE
            holder.mTitleTxt.setText(mStudentList[position].mName)
            holder.imgIcon.visibility = View.VISIBLE
            holder.listTxtClass.setText(mStudentList[position].mClass)
            if (!mStudentList[position].mPhoto.equals("")) {
                /*Picasso.with(mContext)
                    .load(AppUtils.replace(mStudentList[position].mPhoto.toString()))
                    .placeholder(R.drawable.boy).fit().into(holder.imgIcon)*/
            } else {
                holder.imgIcon.setImageResource(R.drawable.boy)
            }
        } else {
            holder.relSub.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return mStudentList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}