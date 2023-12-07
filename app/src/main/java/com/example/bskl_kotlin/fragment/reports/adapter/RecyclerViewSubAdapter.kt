package com.example.bskl_kotlin.fragment.reports.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.pdf.PDF_View_New
import com.example.bskl_kotlin.activity.web_view.LoadWebViewActivity
import com.example.bskl_kotlin.fragment.reports.model.ReportsDataModel

internal class RecyclerViewSubAdapter (private val mContext: Context, var mDataModel: ArrayList<ReportsDataModel>) :
    RecyclerView.Adapter<RecyclerViewSubAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       var termName :TextView= view.findViewById(R.id.termname)
        var clickLinear : LinearLayout = view.findViewById(R.id.clickLinear)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_adapter_sub, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.termName.setText(mDataModel[position].reporting_cycle)
        holder.clickLinear.setOnClickListener{
            if (mDataModel[position].file.endsWith(".pdf")) {
//					Intent intent = new Intent(mContext, PdfReaderActivity.class);
//					intent.putExtra("pdf_url", mDataModel.get(position).getFile());
//					mContext.startActivity(intent);Intent intent = new Intent(mContext, PdfReaderActivity.class);
                System.out.println("PDF URL" + mDataModel[position].file)
                val intent = Intent(mContext, PDF_View_New::class.java)
                intent.putExtra("fileName", mDataModel[position].reporting_cycle)
                intent.putExtra("pdf_url", mDataModel[position].file)
                mContext.startActivity(intent)
            } else {
                val intent = Intent(mContext, LoadWebViewActivity::class.java)
                intent.putExtra("url", mDataModel[position].file)
                mContext.startActivity(intent)
            }
        }

    }
    override fun getItemCount(): Int {
        return mDataModel.size
    }
}