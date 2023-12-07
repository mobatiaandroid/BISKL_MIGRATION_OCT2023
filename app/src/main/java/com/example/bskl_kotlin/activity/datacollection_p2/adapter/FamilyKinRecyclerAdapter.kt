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
import com.example.bskl_kotlin.activity.datacollection_p2.model.KinModel
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.CommonClass

class FamilyKinRecyclerAdapter(
    var mContext: Context, var arrayList:ArrayList<KinModel>
) :

    RecyclerView.Adapter<FamilyKinRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_own_detail, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(myViewHolder: ViewHolder, i: Int) {
        myViewHolder.Name!!.setText(
            AppController.kinArrayShow.get(i).name + " " + AppController.kinArrayShow.get(i)
                .last_name
        )
        myViewHolder.ContactType!!.setText(AppController.kinArrayShow.get(i).relationship)

        if (AppController.kinArrayShow.get(i).isConfirmed!!) {
            myViewHolder.ConfirmButton!!.setVisibility(View.GONE)
        } else {
            myViewHolder.ConfirmButton!!.setVisibility(View.VISIBLE)
        }

        if (AppController.kinArrayShow.get(i).isConfirmed!!) {
            myViewHolder.layout!!.setBackgroundResource(R.drawable.rect_background_grey)
        } else {
            myViewHolder.layout!!.setBackgroundResource(R.drawable.rect_data_collection_red)
        }

    }



    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var Name: TextView? = null
        var ContactType:TextView? = null
        var ConfirmButton: Button? = null
        var layout: RelativeLayout? = null


        init {
            layout = itemView.findViewById(R.id.ownDetailViewRelative)
            Name = itemView.findViewById(R.id.nameOwnDetailTxt)
            ContactType = itemView.findViewById(R.id.contactTypeOwnDetailTxt)
            ConfirmButton = itemView.findViewById(R.id.confirmBtn)
        }
    }


}