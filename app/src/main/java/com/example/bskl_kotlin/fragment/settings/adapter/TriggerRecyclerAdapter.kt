package com.example.bskl_kotlin.fragment.settings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.settings.model.TriggerDataModel

internal class TriggerRecyclerAdapter (private val mContext: Context, var mTriggerModelArrayList:ArrayList<TriggerDataModel>) :
    RecyclerView.Adapter<TriggerRecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       var categoryTypeTxt:TextView = view.findViewById(R.id.categoryTypeTxt)
        var checkBoxImg :ImageView= view.findViewById(R.id.checkBoxImg)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_trigger_check_recycler, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.categoryTypeTxt.setText(mTriggerModelArrayList[position].categoryName)
        if (mTriggerModelArrayList[position].checkedCategory) {
            holder.checkBoxImg.setImageResource(R.drawable.check_box_header_tick)
        } else {
            holder.checkBoxImg.setImageResource(R.drawable.check_box_header)
        }

    }
    override fun getItemCount(): Int {
        return mTriggerModelArrayList.size
    }
}