
package com.example.bskl_kotlin.fragment.safeguarding.adapter

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.absence.model.StudentModel
import com.example.bskl_kotlin.fragment.safeguarding.SafeGuardingFragment
import com.example.bskl_kotlin.manager.AppUtils
import com.squareup.picasso.Picasso
import java.util.*

class SafeGuardingAdapter(data: ArrayList<StudentModel>, context: Context) :
    RecyclerView.Adapter<ViewHolder>() {
    private val dataSet: ArrayList<StudentModel>
    var mContext: Context
    var details = ""
    var mHour = 0
    var mMinute = 0


    class PresentType1ViewHolder(itemView: View) : ViewHolder(itemView) {
        var textViewStudentName: TextView
        var textViewStudentYear: TextView
        var registrationcomment: TextView
        var textViewAMRegisterValue: TextView
        var textViewLastUpdatedValue: TextView
        var comment: TextView
        var commentdot: TextView

        //        View viewBottom;
        var viewTop: View
        var imgStudentImage: ImageView
        var relativeBg: RelativeLayout
        var mainConstraint: ConstraintLayout

        init {
            mainConstraint = itemView.findViewById(R.id.mainConstraint)
            relativeBg = itemView.findViewById(R.id.relativeBg)
            registrationcomment = itemView.findViewById<View>(R.id.commentvalue) as TextView
            comment = itemView.findViewById<View>(R.id.comment) as TextView
            commentdot = itemView.findViewById<View>(R.id.commentdot) as TextView
            textViewStudentName = itemView.findViewById<View>(R.id.textViewStudentName) as TextView
            textViewStudentYear = itemView.findViewById<View>(R.id.textViewStudentYear) as TextView
            textViewAMRegisterValue =
                itemView.findViewById<View>(R.id.textViewAMRegisterValue) as TextView
            textViewLastUpdatedValue =
                itemView.findViewById<View>(R.id.textViewLastUpdatedValue) as TextView
            //            this.viewBottom = (View) itemView.findViewById(R.id.viewBottom);
            viewTop = itemView.findViewById(R.id.viewTop) as View
            imgStudentImage = itemView.findViewById<View>(R.id.iconImg) as ImageView
 if (registrationcomment.getText().toString().trim().equals("")) {
                registrationcomment.setVisibility(View.VISIBLE);
                comment.setVisibility(View.VISIBLE);
                commentdot.setVisibility(View.VISIBLE);
            }
            else{
                registrationcomment.setVisibility(View.GONE);
                comment.setVisibility(View.GONE);
                commentdot.setVisibility(View.GONE);
            }

        }
    }

    class ReportType2ViewHolder(itemView: View) : ViewHolder(itemView) {
        var textViewStudentName: TextView
        var textViewStudentYear: TextView
        var textViewAMRegisterValue: TextView
        var textViewLastUpdatedValue: TextView
        var textViewReportStatus: TextView
        var textViewReportedTime: TextView
        var imgStudentImage: ImageView

        //        View viewBottom;
        var viewTop: View
        var relativeBg: RelativeLayout
        var mainConstraint: ConstraintLayout
        var comment: TextView
        var commentdot: TextView
        var commentvalue: TextView

        init {
            mainConstraint = itemView.findViewById(R.id.mainConstraint)
            relativeBg = itemView.findViewById(R.id.relativeBg)
            textViewStudentName = itemView.findViewById<View>(R.id.textViewStudentName) as TextView
            comment = itemView.findViewById<View>(R.id.comment) as TextView
            commentdot = itemView.findViewById<View>(R.id.commentdot) as TextView
            commentvalue = itemView.findViewById<View>(R.id.commentvalue) as TextView
            textViewStudentYear = itemView.findViewById<View>(R.id.textViewStudentYear) as TextView
            textViewAMRegisterValue =
                itemView.findViewById<View>(R.id.textViewAMRegisterValue) as TextView
            textViewLastUpdatedValue =
                itemView.findViewById<View>(R.id.textViewLastUpdatedValue) as TextView
            //            this.viewBottom = (View) itemView.findViewById(R.id.viewBottom);
            viewTop = itemView.findViewById(R.id.viewTop) as View
            textViewReportStatus =
                itemView.findViewById<View>(R.id.textViewReportStatus) as TextView
            textViewReportedTime =
                itemView.findViewById<View>(R.id.textViewReportedTime) as TextView
            imgStudentImage = itemView.findViewById<View>(R.id.iconImg) as ImageView
 if (commentvalue.getText().toString().trim().equals("")) {
                commentvalue.setVisibility(View.VISIBLE);
                comment.setVisibility(View.VISIBLE);
                commentdot.setVisibility(View.VISIBLE);
            }
            else{
                commentvalue.setVisibility(View.GONE);
                comment.setVisibility(View.GONE);
                commentdot.setVisibility(View.GONE);
            }

        }
    }

    class ReportedType3ViewHolder(itemView: View) : ViewHolder(itemView) {
        var textViewStudentName: TextView
        var textViewStudentYear: TextView
        var textViewAMRegisterValue: TextView
        var textViewLastUpdatedValue: TextView
        var textViewReportAbsence: TextView
        var textViewStudentLate: TextView
        var textViewStudentSent: TextView
        var imgStudentImage: ImageView
        var commentthree: TextView
        var comment: TextView
        var commentdot: TextView

        //        View viewBottom;
        var viewTop: View
        var relativeBg: RelativeLayout
        var mainConstraint: ConstraintLayout

        init {
            mainConstraint = itemView.findViewById(R.id.mainConstraint)
            relativeBg = itemView.findViewById(R.id.relativeBg)
            textViewStudentName = itemView.findViewById<View>(R.id.textViewStudentName) as TextView
            commentthree = itemView.findViewById<View>(R.id.commentvaluesec) as TextView
            comment = itemView.findViewById<View>(R.id.comment) as TextView
            commentdot = itemView.findViewById<View>(R.id.commentdot) as TextView
            textViewStudentYear = itemView.findViewById<View>(R.id.textViewStudentYear) as TextView
            textViewAMRegisterValue =
                itemView.findViewById<View>(R.id.textViewAMRegisterValue) as TextView
            textViewLastUpdatedValue =
                itemView.findViewById<View>(R.id.textViewLastUpdatedValue) as TextView
            //            this.viewBottom = (View) itemView.findViewById(R.id.viewBottom);
            viewTop = itemView.findViewById(R.id.viewTop) as View
            textViewReportAbsence =
                itemView.findViewById<View>(R.id.textViewReportAbsence) as TextView
            textViewStudentLate = itemView.findViewById<View>(R.id.textViewStudentLate) as TextView
            textViewStudentSent = itemView.findViewById<View>(R.id.textViewStudentSent) as TextView
            imgStudentImage = itemView.findViewById<View>(R.id.iconImg) as ImageView

      if (commentthree.getText().toString().trim().equals("")) {
                commentthree.setVisibility(View.GONE);
                comment.setVisibility(View.GONE);
                commentdot.setVisibility(View.GONE);
            }
            else{
                commentthree.setVisibility(View.VISIBLE);
                comment.setVisibility(View.VISIBLE);
                commentdot.setVisibility(View.VISIBLE);
            }

        }
    }

    init {
        dataSet = data
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.v("viewType:", "" + viewType)
        when (viewType) {
            StudentModel().VIEW_TYPE1 -> {
                val view1: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.adapter_safe_guarding_type1, parent, false)
                val height = parent.measuredHeight
                view1.minimumHeight = height
                return PresentType1ViewHolder(view1)
            }
            StudentModel().VIEW_TYPE2 -> {
                val view2: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.adapter_safe_guarding_type2, parent, false)
                val height2 = parent.measuredHeight
                view2.minimumHeight = height2
                return ReportType2ViewHolder(view2)
            }
            StudentModel().VIEW_TYPE3 -> {
                val view3: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.adapter_safe_guarding_type3, parent, false)
                val height3 = parent.measuredHeight
                view3.minimumHeight = height3
                return ReportedType3ViewHolder(view3)
            }
        }
        throw AssertionError("impossible viewType: $viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].viewType!!.toInt()
        //        switch (Integer.parseInt(dataSet.get(position).getViewType())) {
//            case 1:
//                return StudentModel.VIEW_TYPE1;
//            case 2:
//                return StudentModel.VIEW_TYPE2;
//            case 3:
//                return StudentModel.VIEW_TYPE3;
//            case 4:
//                return StudentModel.VIEW_TYPE4;
//            default:
//                return -1;
//        }
    }



    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun dialogStudentLate(
        context: Context?,
        status: String?,
        attendance_id: String?,
        student_id: String?
    ) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_safe_guarding_student_late)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        val btnSubmit = dialog.findViewById<Button>(R.id.btnSubmit)
        val textViewComments = dialog.findViewById<EditText>(R.id.textViewComments)
        val textViewTime = dialog.findViewById<TextView>(R.id.textViewTime)
        details = ""
        textViewTime.setOnClickListener { // Get Current Time
            val c = Calendar.getInstance()
            if (textViewTime.text.toString().equals("", ignoreCase = true)) {
                mHour = c[Calendar.HOUR_OF_DAY]
                mMinute = c[Calendar.MINUTE]
            } else {
                mHour = AppUtils().timeParsingToHours(textViewTime.text.toString())!!.toInt()
                mMinute = AppUtils().timeParsingToMinutes(textViewTime.text.toString())!!.toInt()
            }
            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                context,
                { view, hourOfDay, minute -> textViewTime.setText(AppUtils().timeParsingTo12Hour("$hourOfDay:$minute")) },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }
        btnSubmit.setOnClickListener {
            if (AppUtils().isNetworkConnected(mContext)) {
                if (!textViewTime.text.toString()
                        .equals("", ignoreCase = true) && !textViewComments.text.toString()
                        .equals("", ignoreCase = true)
                ) {
                    details =
                        "Expected arrival time - " + textViewTime.text.toString() + " " + "<br>" + textViewComments.text.toString()
                   SafeGuardingFragment(). submitAbsence(
                       attendance_id!!,
                       student_id!!,
                       status!!,
                        details
                    )
                    dialog.dismiss()
                } else if (textViewTime.text.toString().equals("", ignoreCase = true)) {
                    AppUtils().showDialogAlertDismiss(
                        mContext as Activity,
                        "Alert",
                        "Please select time.",
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                } else if (textViewComments.text.toString().equals("", ignoreCase = true)) {
                    AppUtils().showDialogAlertDismiss(
                        mContext as Activity,
                        "Alert",
                        "Please enter your comments.",
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                }
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    "Network Error",
                    mContext.resources.getString(R.string.no_internet),
                    R.drawable.nonetworkicon,
                    R.drawable.roundred
                )
            }
        }
        dialog.show()
    }

    fun dialogStudentSent(
        context: Context?,
        status: String?,
        attendance_id: String?,
        student_id: String?
    ) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_safe_guarding_student_sent)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        val btnSubmit = dialog.findViewById<Button>(R.id.btnSubmit)
        val textViewComments = dialog.findViewById<EditText>(R.id.textViewComments)
        details = ""
        val radioGroup = dialog.findViewById<View>(R.id.radioGroup) as RadioGroup
        radioGroup.clearCheck()
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val rb = group.findViewById<View>(checkedId) as RadioButton
            if (checkedId == R.id.radioButton1) {
                details = rb.text.toString()
                textViewComments.visibility = View.GONE
            } else if (checkedId == R.id.radioButton2) {
                details = rb.text.toString()
                textViewComments.visibility = View.GONE
            } else if (checkedId == R.id.radioButton3) {
                details = rb.text.toString()
                textViewComments.visibility = View.GONE
            } else if (checkedId == R.id.radioButton4) {
                details = ""
                textViewComments.setText("")
                textViewComments.visibility = View.VISIBLE
            } else {
                textViewComments.visibility = View.GONE
            }
        }
        btnSubmit.setOnClickListener {
            if (AppUtils().isNetworkConnected(mContext)) {
                if (radioGroup.checkedRadioButtonId == R.id.radioButton4) {
                    details = textViewComments.text.toString()
                    if (details.trim { it <= ' ' }.equals("", ignoreCase = true)) {
                        AppUtils().showDialogAlertDismiss(
                            mContext as Activity,
                            "Alert",
                            "Please enter your comments.",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                      SafeGuardingFragment(). submitAbsence(
                            attendance_id!!,
                            student_id!!,
                            status!!,
                            details
                        )
                        dialog.dismiss()
                    }
                } else if (details.equals("", ignoreCase = true)) {
                    AppUtils().showDialogAlertDismiss(
                        mContext as Activity,
                        "Alert",
                        "Please select any of the options.",
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                } else {
                   SafeGuardingFragment() .submitAbsence(
                        attendance_id!!,
                        student_id!!,
                        status!!,
                        details
                    )
                    dialog.dismiss()
                }
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    "Network Error",
                    mContext.resources.getString(R.string.no_internet),
                    R.drawable.nonetworkicon,
                    R.drawable.roundred
                )
            }
        }
        dialog.show()
    }

    override fun getItemId(position: Int): Long {
        return dataSet[position].viewType!!.toInt().toLong()

//        switch (Integer.parseInt(dataSet.get(position).getViewType())) {
//            case 1:
//                return StudentModel.VIEW_TYPE1;
//            case 2:
//                return StudentModel.VIEW_TYPE2;
//            case 3:
//                return StudentModel.VIEW_TYPE3;
//            case 4:
//                return StudentModel.VIEW_TYPE4;
//            default:
//                return -1;
//        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: StudentModel = dataSet[position]
        if (model != null) {
            when (model.viewType!!.toInt()) {
                StudentModel().VIEW_TYPE1 -> {
                    (holder as PresentType1ViewHolder?)!!.textViewStudentName.setText(model.mName)
                    holder!!.registrationcomment.setText(model.registrationComment)
                    holder!!.textViewStudentYear.setText(model.mClass + " " + model.mSection)
                    holder!!.textViewAMRegisterValue.setText(model.abRegister)
                    holder!!.textViewLastUpdatedValue.setText(
                        AppUtils().dateParsingToDdMmmYyyy(
                            model.date
                        )
                    )
                    if (!model.mPhoto.equals("")) {
                        Picasso.with(mContext).load(AppUtils().replace(model.mPhoto!!))
                            .placeholder(R.drawable.boy).fit().into(
                                holder!!.imgStudentImage
                            )
                    } else {
                        holder!!.imgStudentImage.setImageResource(R.drawable.boy)
                    }
                    //                    if (listPosition == (dataSet.size() - 1)) {
//                        ((PresentType1ViewHolder) holder).viewBottom.setVisibility(View.VISIBLE);
//                    } else {
//                        ((PresentType1ViewHolder) holder).viewBottom.setVisibility(View.GONE);
//
//                    }
                    if (model.registrationComment.equals("")) {
                        holder!!.registrationcomment.visibility =
                            View.GONE
                        holder!!.comment.visibility =
                            View.GONE
                        holder!!.commentdot.visibility =
                            View.GONE
                    } else {
                        holder!!.registrationcomment.visibility =
                            View.VISIBLE
                        holder!!.comment.visibility =
                            View.VISIBLE
                        holder!!.commentdot.visibility =
                            View.VISIBLE
                    }
                }
                StudentModel().VIEW_TYPE2 -> {
                    (holder as ReportType2ViewHolder?)!!.textViewStudentName.setText(model.mName)
                    holder!!.commentvalue.setText(model.registrationComment)
                    holder!!.textViewStudentYear.setText(model.mClass + " " + model.mSection)
                    holder!!.textViewAMRegisterValue.setText(model.abRegister)
                    holder!!.textViewLastUpdatedValue.setText(
                        AppUtils().dateParsingToDdMmmYyyy(
                            model.date
                        )
                    )
                    if (model.status.equals("1")) {
                        holder!!.textViewReportStatus.text =
                            "STUDENT \nRUNNING LATE"
                        holder!!.textViewReportStatus.setBackgroundResource(R.drawable.right_open_rounded_rect_red)
                        holder!!.textViewReportedTime.setBackgroundResource(R.drawable.left_open_rounded_rect_red)
                        if (model.parent_id
                                .equals(PreferenceManager().getUserId(mContext))
                        ) {
                            holder!!.textViewReportedTime.text =
                                "REPORTED BY YOU AT : " + AppUtils().dateParsingToTime(model.app_updated_on)
                        } else {
                            holder!!.textViewReportedTime.text =
                                "REPORTED BY " + model.parent_name
                                    .toString() + " AT : " + AppUtils().dateParsingToTime(model.app_updated_on)
                        }
                    } else if (model.status.equals("2")) {
                        holder!!.textViewReportStatus.text =
                            "STUDENT HAS \nBEEN SENT TO SCHOOL"
                        holder!!.textViewReportStatus.setBackgroundResource(R.drawable.right_open_rounded_rect_violet)
                        holder!!.textViewReportedTime.setBackgroundResource(R.drawable.left_open_rounded_rect_violet)
                        if (model.parent_id
                                .equals(PreferenceManager().getUserId(mContext))
                        ) {
                            holder!!.textViewReportedTime.text =
                                "REPORTED BY YOU AT : " + AppUtils().dateParsingToTime(model.app_updated_on)
                        } else {
                            holder!!.textViewReportedTime.text =
                                "REPORTED BY " + model.parent_name
                                    .toString() + " AT : " + AppUtils().dateParsingToTime(model.app_updated_on)
                        }
                    } else if (model.status.equals("3")) {
                        holder!!.textViewReportStatus.text =
                            "REPORT ABSENCE"
                        holder!!.textViewReportStatus.setBackgroundResource(R.drawable.right_open_rounded_rect_blue)
                        holder!!.textViewReportedTime.setBackgroundResource(R.drawable.left_open_rounded_rect_blue)
                        if (model.parent_id
                                .equals(PreferenceManager().getUserId(mContext))
                        ) {
                            holder!!.textViewReportedTime.text =
                                "REPORTED BY YOU AT : " + AppUtils().dateParsingToTime(model.app_updated_on)
                        } else {
                            holder!!.textViewReportedTime.text =
                                "REPORTED BY " + model.parent_name
                                    .toString() + " AT : " + AppUtils().dateParsingToTime(model.app_updated_on)
                        }
                    }
                    if (!model.mPhoto.equals("")) {
                        Picasso.with(mContext).load(AppUtils().replace(model.mPhoto!!))
                            .placeholder(R.drawable.boy).fit().into(
                                holder!!.imgStudentImage
                            )
                    } else {
                        holder!!.imgStudentImage.setImageResource(R.drawable.boy)
                    }
                    //                    if (listPosition == (dataSet.size() - 1)) {
//                        ((ReportType2ViewHolder) holder).viewBottom.setVisibility(View.VISIBLE);
//                    } else {
//                        ((ReportType2ViewHolder) holder).viewBottom.setVisibility(View.GONE);
//
//                    }
                    if (model.registrationComment.equals("")) {
                        holder!!.commentvalue.visibility =
                            View.GONE
                        holder!!.comment.visibility =
                            View.GONE
                        holder!!.commentdot.visibility =
                            View.GONE
                    } else {
                        holder!!.commentvalue.visibility =
                            View.VISIBLE
                        holder!!.comment.visibility =
                            View.VISIBLE
                        holder!!.commentdot.visibility =
                            View.VISIBLE
                    }
                }
                StudentModel().VIEW_TYPE3 -> {
                    (holder as ReportedType3ViewHolder?)!!.textViewStudentName.setText(model.mName)
                    holder!!.commentthree.setText(model.registrationComment)
                    holder!!.textViewStudentYear.setText(model.mClass + " " + model.mSection)
                    holder!!.textViewAMRegisterValue.setText(model.abRegister)
                    holder!!.textViewLastUpdatedValue.setText(
                        AppUtils().dateParsingToDdMmmYyyy(
                            model.date
                        )
                    )
                    if (!model.mPhoto.equals("")) {
                        Picasso.with(mContext).load(AppUtils().replace(model.mPhoto!!))
                            .placeholder(R.drawable.boy).fit().into(
                                holder!!.imgStudentImage
                            )
                    } else {
                        holder!!.imgStudentImage.setImageResource(R.drawable.boy)
                    }
                    if (model.registrationComment.equals("")) {
                        holder!!.commentthree.visibility =
                            View.GONE
                        holder!!.comment.visibility =
                            View.GONE
                        holder!!.commentdot.visibility =
                            View.GONE
                    } else {
                        holder!!.commentthree.visibility =
                            View.VISIBLE
                        holder!!.comment.visibility =
                            View.VISIBLE
                        holder!!.commentdot.visibility =
                            View.VISIBLE
                    }
                    //                    if (listPosition == (dataSet.size() - 1)) {
//                        ((ReportedType3ViewHolder) holder).viewBottom.setVisibility(View.VISIBLE);
//                    } else {
//                        ((ReportedType3ViewHolder) holder).viewBottom.setVisibility(View.GONE);
//
//                    }
                    holder!!.textViewReportAbsence.gravity =
                        Gravity.CENTER
                    holder!!.textViewStudentLate.gravity =
                        Gravity.CENTER
                    holder!!.textViewStudentSent.gravity =
                        Gravity.CENTER
                    holder!!.textViewReportAbsence.setOnClickListener {
/*
val mIntent = Intent(
                            mContext,
                            LeaveRequestSubmissionSafeguardActivity::class.java
                        )
                        mIntent.putExtra("studentName", model.mName)
                        mIntent.putExtra("studentImage", model.mPhoto)
                        mIntent.putExtra("studentId", model.mId)
                        mIntent.putExtra("attendance_id", model.abscenceId)
                        mIntent.putExtra("status", "3")
                        mIntent.putExtra("StudentModelArray", dataSet)
                        mContext.startActivity(mIntent)
*/

                    }
                    holder!!.textViewStudentLate.setOnClickListener {
                        dialogStudentLate(
                            mContext,
                            "1",
                            model.abscenceId,
                            model.mId
                        )
                    }
                    holder!!.textViewStudentSent.setOnClickListener {
                        dialogStudentSent(
                            mContext,
                            "2",
                            model.abscenceId,
                            model.mId
                        )
                    }
                }
            }
        }
    }
}