package com.example.bskl_kotlin.activity.datacollection_p2

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.datacollection_p2.adapter.InsuranceStudentRecyclerAdapter
import com.example.bskl_kotlin.activity.datacollection_p2.model.InsuranceDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.StudentModelNew
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.constants.OnItemClickListener
import com.example.bskl_kotlin.constants.addOnItemClickListener
import com.example.bskl_kotlin.manager.AppUtils

class SecondScreenNew: Fragment() {
    lateinit var mContext:Context
    lateinit var CloseIcon: ImageView

    lateinit var studentInfoRecycler: RecyclerView
    lateinit var mInsuranceDetailList: ArrayList<InsuranceDetailModel>
    lateinit var studentsModelArrayList: ArrayList<StudentModelNew>
    var mInsuranceStudentAdapter: InsuranceStudentRecyclerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_second_screen_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        //Medical Insurance
        CloseIcon = requireView().findViewById<ImageView>(R.id.closeImg)
        studentInfoRecycler =requireView().findViewById(R.id.studentInfoRecycler)
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        studentInfoRecycler.layoutManager = mLayoutManager
        studentInfoRecycler.itemAnimator = DefaultItemAnimator()
        mInsuranceDetailList = PreferenceManager().getInsuranceDetailArrayList(mContext)
        if (AppUtils().isNetworkConnected(mContext)) {
            mInsuranceStudentAdapter = InsuranceStudentRecyclerAdapter(
                mContext,
                PreferenceManager().getInsuranceStudentList(mContext)
            )
            studentInfoRecycler.adapter = mInsuranceStudentAdapter
            //    System.out.println("Api call working");
            //  getStudentsListAPI(URL_GET_STUDENT_LIST);
        } else {
            AppUtils().showDialogAlertDismiss(
                mContext as Activity,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }
        if (mInsuranceDetailList.size > 0) {
            for (i in mInsuranceDetailList.indices) {
                //  System.out.println("Student name"+mInsuranceDetailList.get(i).getStudent_name());
            }
        }
        CloseIcon.setOnClickListener {
            val dialog = Dialog(mContext)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.alert_dialogue_ok_layout_data_collection)
            val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
            icon.setBackgroundResource(R.drawable.round)
            icon.setImageResource(R.drawable.exclamationicon)
            val text = dialog.findViewById<TextView>(R.id.text_dialog)
            val textHead = dialog.findViewById<TextView>(R.id.alertHead)
            text.text = "Please update this information next time"
            textHead.text = "Alert"
            val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
            dialogButton.setOnClickListener {
                PreferenceManager().setSuspendTrigger(mContext, "1")
                dialog.dismiss()
                requireActivity().finish()
            }
            dialog.show()
        }
studentInfoRecycler.addOnItemClickListener(object :OnItemClickListener
{
    override fun onItemClicked(position: Int, view: View) {
        val studId: String =
            PreferenceManager().getInsuranceStudentList(mContext).get(position).mId.toString()
//                          System.out.println("student ID"+studId);
        /* String studentId="";
                                       String studentImage="";
                                       String studentName="";*/

        //                          System.out.println("student ID"+studId);
        /* String studentId="";
                                       String studentImage="";
                                       String studentName="";*/
        val intent = Intent(
            activity,
            SecondScreenDataCollection::class.java
        )
        intent.putExtra(
            "studentId",
            PreferenceManager().getInsuranceStudentList(mContext).get(position).mId
        )
        intent.putExtra(
            "studentImage",
            PreferenceManager().getInsuranceStudentList(mContext).get(position).mPhoto
        )
        intent.putExtra(
            "studentName",
            PreferenceManager().getInsuranceStudentList(mContext).get(position).mName
        )
        startActivity(intent)
    }


})
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
    override fun onResume() {
        super.onResume()
        mInsuranceStudentAdapter = InsuranceStudentRecyclerAdapter(
            mContext,
            PreferenceManager().getInsuranceStudentList(mContext)
        )
        studentInfoRecycler.adapter = mInsuranceStudentAdapter
    }


}