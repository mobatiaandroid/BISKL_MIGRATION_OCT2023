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
import com.example.bskl_kotlin.activity.settings.model.OwnDetailsModel

class RelationRecyclerAdapter (
    var mContext: Context, var mRelationshipModel:ArrayList<OwnDetailsModel>
) :

    RecyclerView.Adapter<RelationRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.detail_layout, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mRelationshipModel[position].fullName.equals("")) {
            holder.namelayout.visibility = View.GONE
        } else {
            holder.namelayout.visibility = View.VISIBLE
            if (mRelationshipModel[position].relationShip.equals("")) {
                holder.nameValueTextView.setText(mRelationshipModel[position].fullName)
            } else {
                holder.nameValueTextView.setText((mRelationshipModel[position].fullName + "( " + mRelationshipModel[position].relationShip).toString() + " )")
            }
            if (mRelationshipModel[position].fullName.equals("No Data")) {
                holder.nameValueTextView.setTextColor(mContext.resources.getColor(R.color.red))
            } else {
                holder.nameValueTextView.setTextColor(mContext.resources.getColor(R.color.black))
            }
        }
        if (mRelationshipModel[position].email.equals("")) {
            holder.emaillayout.visibility = View.GONE
        } else {
            holder.emaillayout.visibility = View.VISIBLE
            holder.emailValueTextView.setText(mRelationshipModel[position].email)
            if (mRelationshipModel[position].email.equals("No Data")) {
                holder.emailValueTextView.setTextColor(mContext.resources.getColor(R.color.red))
            } else {
                holder.emailValueTextView.setTextColor(mContext.resources.getColor(R.color.black))
            }
        }

        if (mRelationshipModel[position].contactNo.equals("")) {
            holder.phonelayout.visibility = View.GONE
        } else {
            holder.phonelayout.visibility = View.VISIBLE
            holder.phoneValueTextView.setText(mRelationshipModel[position].contactNo)
            if (mRelationshipModel[position].contactNo.equals("No Data")) {
                holder.phoneValueTextView.setTextColor(mContext.resources.getColor(R.color.red))
            } else {
                holder.phoneValueTextView.setTextColor(mContext.resources.getColor(R.color.black))
            }
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
            nameValueTextView = view.findViewById<TextView>(R.id.nameValueTextView)
            emailValueTextView = view.findViewById<TextView>(R.id.emailValueTextView)
            phoneValueTextView = view.findViewById<TextView>(R.id.phoneValueTextView)
            namelayout = view.findViewById<LinearLayout>(R.id.namelayout)
            emaillayout = view.findViewById<LinearLayout>(R.id.emaillayout)
            phonelayout = view.findViewById<LinearLayout>(R.id.phonelayout)
        }
    }


}