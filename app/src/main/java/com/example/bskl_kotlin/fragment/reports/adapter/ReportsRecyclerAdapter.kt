package com.example.bskl_kotlin.fragment.reports.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.fragment.reports.model.ReportsDataModel
import com.example.bskl_kotlin.fragment.reports.model.StudentInfoModel

internal class ReportsRecyclerAdapter  (private val mContext: Context, var reportslist: ArrayList<StudentInfoModel>) :
    RecyclerView.Adapter<ReportsRecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       var accYr: TextView = view.findViewById(R.id.accYr)
       var dataRecycle:RecyclerView = view.findViewById(R.id.recycler_view_list)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_main_adapter, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.accYr.setText(reportslist.get(position).acyear)
        var llm = LinearLayoutManager(mContext)
        llm.setOrientation(LinearLayoutManager.VERTICAL)
        holder.dataRecycle.setLayoutManager(llm)
        val mRecyclerViewSubAdapter =
            RecyclerViewSubAdapter(mContext, reportslist.get(position).mDataModel)
        holder.dataRecycle.adapter = mRecyclerViewSubAdapter

    }
    override fun getItemCount(): Int {
        return reportslist.size
    }
}