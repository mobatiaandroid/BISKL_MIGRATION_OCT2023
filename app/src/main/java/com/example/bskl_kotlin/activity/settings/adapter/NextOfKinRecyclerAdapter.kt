package com.example.bskl_kotlin.activity.settings.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.settings.model.KinDetailsModel

class NextOfKinRecyclerAdapter (
    var mContext: Context, var mRelationshipModel:ArrayList<KinDetailsModel>
) :

    RecyclerView.Adapter<NextOfKinRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.detail_layout, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mRelationshipModel[position].fullName.equals("")) {
            holder.namelayout.visibility = View.VISIBLE
            holder.nameValueTextView.text = "No Data"
            holder.emailValueTextView.setTextColor(mContext.resources.getColor(R.color.red))
        } else {
            holder.namelayout.visibility = View.VISIBLE
            holder.nameValueTextView.setTextColor(mContext.resources.getColor(R.color.black))
            if (mRelationshipModel[position].relationShip.equals("")) {
                holder.nameValueTextView.setText(mRelationshipModel[position].fullName)
            } else {
                holder.nameValueTextView.setText((mRelationshipModel[position].fullName + "( " + mRelationshipModel[position].relationShip).toString() + " )")
            }
        }
        if (mRelationshipModel[position].emial.equals("")) {
            holder.emaillayout.visibility = View.VISIBLE
            holder.emailValueTextView.text = "No Data"
            holder.emailValueTextView.setTextColor(mContext.resources.getColor(R.color.red))
        } else {
            holder.emaillayout.visibility = View.VISIBLE
            holder.emailValueTextView.setText(mRelationshipModel[position].emial)
            holder.emailValueTextView.setTextColor(mContext.resources.getColor(R.color.black))
        }

        if (mRelationshipModel[position].contactNumber.equals("")) {
            holder.phonelayout.visibility = View.VISIBLE
            holder.phoneValueTextView.text = "No Data"
            holder.phoneValueTextView.setTextColor(mContext.resources.getColor(R.color.red))
        } else {
            holder.phonelayout.visibility = View.VISIBLE
            holder.phoneValueTextView.setTextColor(mContext.resources.getColor(R.color.black))
            holder.phoneValueTextView.setText(mRelationshipModel[position].contactNumber)
        }

    }



    override fun getItemCount(): Int {
        return mRelationshipModel.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var nameValueTextView: TextView
        var emailValueTextView: TextView
        var phoneValueTextView: TextView
        var namelayout: LinearLayout
        var emaillayout: LinearLayout
        var phonelayout: LinearLayout


        init {
            nameValueTextView = view.findViewById(R.id.nameValueTextView)
            emailValueTextView = view.findViewById(R.id.emailValueTextView)
            phoneValueTextView = view.findViewById(R.id.phoneValueTextView)
            namelayout = view.findViewById(R.id.namelayout)
            emaillayout = view.findViewById(R.id.emaillayout)
            phonelayout = view.findViewById(R.id.phonelayout)
        }
    }


}