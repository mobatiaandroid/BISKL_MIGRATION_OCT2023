package com.example.bskl_kotlin.activity.datacollection_p2.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.datacollection_p2.model.StudentModelNew
import com.example.bskl_kotlin.common.PreferenceManager

class InsuranceStudentRecyclerAdapter (
    var mContext: Context, var mInsuranceDetailList:ArrayList<StudentModelNew>
) : RecyclerView.Adapter<InsuranceStudentRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_second_fragment_new, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(myViewHolder: ViewHolder, i: Int) {
        if (PreferenceManager().getInsuranceStudentList(mContext).get(i).isConfirmed) {
            myViewHolder.mainRelative!!.setBackgroundResource(R.drawable.rect_background_grey)
            myViewHolder.ConfirmButton!!.visibility = View.GONE
        } else {
            myViewHolder.mainRelative!!.setBackgroundResource(R.drawable.rect_data_collection_red)
            myViewHolder.ConfirmButton!!.visibility = View.VISIBLE
        }
        myViewHolder.studentNameTxt!!.setText(
            PreferenceManager().getInsuranceStudentList(mContext).get(i).mName
        )
        myViewHolder.classTxt!!.setText(
            PreferenceManager().getInsuranceStudentList(mContext).get(i).mClass
        )


    }



    override fun getItemCount(): Int {
        return mInsuranceDetailList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var studentNameTxt: TextView? = null
        var classTxt:TextView? = null
        var ConfirmButton: Button? = null
        var mainRelative: RelativeLayout? = null


        init {
            mainRelative = itemView.findViewById(R.id.mainRelative)
            studentNameTxt = itemView.findViewById(R.id.studentNameTxt)
            classTxt = itemView.findViewById(R.id.classTxt)
            ConfirmButton = itemView.findViewById(R.id.confirmBtn)
        }
    }


}