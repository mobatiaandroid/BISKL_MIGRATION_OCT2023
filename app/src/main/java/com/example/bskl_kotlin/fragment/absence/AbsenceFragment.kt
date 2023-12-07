package com.example.bskl_kotlin.fragment.absence

import ApiClient
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.absence.LeaveRequestSubmissionActivity
import com.example.bskl_kotlin.activity.absence.LeavesDetailActivity
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.common.model.StudentListApiModel
import com.example.bskl_kotlin.common.model.StudentListModel
import com.example.bskl_kotlin.common.model.StudentListResponseModel
import com.example.bskl_kotlin.constants.OnItemClickListener
import com.example.bskl_kotlin.constants.addOnItemClickListener
import com.example.bskl_kotlin.fragment.absence.adapter.AbsenceRecyclerAdapter
import com.example.bskl_kotlin.fragment.absence.adapter.StudentSpinnerAdapter
import com.example.bskl_kotlin.fragment.absence.model.LeaveRequestsApiModel
import com.example.bskl_kotlin.fragment.absence.model.LeaveRequestsModel
import com.example.bskl_kotlin.fragment.absence.model.LeavesModel
import com.example.bskl_kotlin.manager.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class AbsenceFragment (title: String, tabId: String) : Fragment() {

    lateinit var mContext: Context
    var firstVisit:Boolean = false
    lateinit var mAbsenceListView: RecyclerView
    var newRequest: TextView? = null
    private var relMain: RelativeLayout? = null
    var noDataRelative: RelativeLayout? = null
    var noDataTxt: TextView? = null
    var noDataImg: ImageView? = null
    var stud_img = ""
    var studImg: ImageView? = null
    private var belowViewRelative: RelativeLayout? = null
    lateinit var studentsModelArrayList: ArrayList<StudentListModel>
    lateinit var mAbsenceListViewArray: ArrayList<LeavesModel>
    var mStudentSpinner: LinearLayout? = null
    var studentName: TextView? = null
    var stud_id = ""
    var studClass = ""
    var alumini = ""
    var progressreport = ""
    var studentList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.absence_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
            firstVisit = true
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        headerTitle.text = "Absence"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        initUi()
getStudentsListAPI()

    }

    private fun initUi(){
        mAbsenceListView = requireView().findViewById<RecyclerView>(R.id.mAbsenceListView)
        relMain = requireView().findViewById<RelativeLayout>(R.id.relMain)
        mStudentSpinner = requireView().findViewById<LinearLayout>(R.id.studentSpinner)
        belowViewRelative = requireView().findViewById<RelativeLayout>(R.id.belowViewRelative)
        noDataRelative = requireView().findViewById<RelativeLayout>(R.id.noDataRelative)
        noDataTxt = requireView().findViewById<TextView>(R.id.noDataTxt)
       // relMain.setOnClickListener(View.OnClickListener { })
        studentName = requireView().findViewById<TextView>(R.id.studentName)
        studImg = requireView().findViewById<ImageView>(R.id.studImg)
        newRequest = requireView().findViewById<TextView>(R.id.newRequest)

        mStudentSpinner!!.setOnClickListener{
            if (studentsModelArrayList.size > 0) {
                showStudentList(studentsModelArrayList)
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    "Alert",
                    getString(R.string.student_not_available),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }

        }
        newRequest!!.setOnClickListener {
            val mIntent = Intent(
                mContext,
                LeaveRequestSubmissionActivity::class.java
            )
            mIntent.putExtra("studentName", studentName!!.getText().toString())
            mIntent.putExtra("studentImage", stud_img)
            mIntent.putExtra("studentId", PreferenceManager().getLeaveStudentId(mContext))
            mIntent.putExtra("attendance_id", "")
            mIntent.putExtra("status", "")
            mContext.startActivity(mIntent)
        }
        mAbsenceListView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                if (mAbsenceListViewArray.size > 0) {
                    val mIntent = Intent(mContext, LeavesDetailActivity::class.java)
                    mIntent.putExtra("studentName", studentName!!.getText().toString())
                    mIntent.putExtra("studentClass", studClass)
                    mIntent.putExtra("fromDate", mAbsenceListViewArray[position].from_date)
                    mIntent.putExtra("toDate", mAbsenceListViewArray[position].to_date)
                    mIntent.putExtra(
                        "reasonForAbsence",
                        mAbsenceListViewArray[position].reason
                    )
                    mIntent.putExtra("staff", mAbsenceListViewArray[position].staff)
                    mContext.startActivity(mIntent)
                }
            }
        })
    }
    fun getStudentsListAPI() {
        studentsModelArrayList = ArrayList()
        var studlist=
            StudentListApiModel( PreferenceManager().getUserId(com.example.bskl_kotlin.fragment.home.mContext).toString())

        val call: Call<StudentListResponseModel> = ApiClient.getClient.student_list(
            studlist,"Bearer "+ PreferenceManager().getaccesstoken(mContext).toString()
        )

        call.enqueue(object : Callback<StudentListResponseModel> {
            override fun onFailure(call: Call<StudentListResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<StudentListResponseModel>,
                response: Response<StudentListResponseModel>
            ) {

                val responsedata = response.body()

                Log.e("Response Signup", responsedata.toString())

                if (response.body()!!.responsecode.equals("200")) {
                    if (response.body()!!.response.statuscode.equals("303")) {
                        studentsModelArrayList.clear()
                        studentList.clear()

                        if (response.body()!!.response.data.size > 0) {

                            //studentsModelArrayList.add(0,);
                            for (i in  response.body()!!.response.data.indices) {
                                val dataObject: StudentListModel = response.body()!!.response.data[i]
                                if (dataObject.alumi.equals("0")) {
                                    studentsModelArrayList.add(dataObject)
                                }
//                                    studentList.add(studentsModelArrayList.get(i).getmName());
                                /*    studentList.add(studentsModelArrayList.get(i).getAlumini());
                                    studentList.add(studentsModelArrayList.get(i).getProgressreport());*/
                            }
                            studentName!!.setText(studentsModelArrayList[0].name)
                            stud_img = studentsModelArrayList[0].photo
                            if (stud_img != "") {
                                Glide.with(mContext) //1
                                    .load(stud_img)
                                    .placeholder(R.drawable.boy)
                                    .error(R.drawable.boy)
                                    .skipMemoryCache(true) //2
                                    .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                    .transform(CircleCrop()) //4
                                    .into(studImg!!)

                            } else {
                                studImg!!.setImageResource(R.drawable.boy)
                            }


                            stud_id = studentsModelArrayList[0].id
                            alumini = studentsModelArrayList[0].alumi
                            progressreport = studentsModelArrayList[0].progressreport
                            PreferenceManager().setLeaveStudentId(mContext, stud_id)
                            PreferenceManager().setLeaveStudentName(
                                mContext,
                                studentsModelArrayList[0].name
                            )
                            studClass = studentsModelArrayList[0].mClass
                            belowViewRelative!!.visibility = View.VISIBLE
                            newRequest!!.visibility = View.VISIBLE
                            if (AppUtils().isNetworkConnected(mContext)) {
                                getList( stud_id)
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }


                        } else {
                            belowViewRelative!!.visibility = View.INVISIBLE
                            newRequest!!.visibility = View.INVISIBLE
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Alert",
                                getString(R.string.student_not_available),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    }

                }
            }
        })
    }
    fun showStudentList(mStudentArray: ArrayList<StudentListModel>) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_student_list)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var studentListRecycler = dialog.findViewById(R.id.studentListRecycler) as RecyclerView
        iconImageView.setImageResource(R.drawable.boy)
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            btn_dismiss.setBackgroundDrawable(
                mContext.resources.getDrawable(R.drawable.button_new)
            )
        } else {
            btn_dismiss.background = mContext.resources.getDrawable(R.drawable.button_new)
        }

        studentListRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentListRecycler.layoutManager = llm
        if (mStudentArray.size > 0) {
            val studentAdapter = StudentSpinnerAdapter(mContext, mStudentArray)
            studentListRecycler.adapter = studentAdapter
        }

        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }

        studentListRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                studentName!!.setText(mStudentArray.get(position).name)
                stud_id = mStudentArray.get(position).id
                studClass = mStudentArray.get(position).mClass
                PreferenceManager().setLeaveStudentId(mContext, stud_id)
                PreferenceManager().setLeaveStudentName(
                    mContext,
                    mStudentArray.get(position).name
                )
                stud_img = studentsModelArrayList[position].photo


                if (!stud_img.equals("")) {
                    Glide.with(mContext) //1
                        .load(stud_img)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(studImg!!)
                } else {
                    studImg!!.setImageResource(R.drawable.boy)
                }
                getList( mStudentArray[position].id)

                dialog.dismiss()
            }
        })
        dialog.show()
    }

    private fun getList(student_id: String) {
        mAbsenceListViewArray = ArrayList()
        var absencemodel= LeaveRequestsApiModel(PreferenceManager().getUserId(mContext).toString(), student_id)
        val call: Call<LeaveRequestsModel> = ApiClient.getClient.leaveRequestslist(
            absencemodel,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString())

        call.enqueue(object : Callback<LeaveRequestsModel> {
            override fun onFailure(call: Call<LeaveRequestsModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<LeaveRequestsModel>,
                response: Response<LeaveRequestsModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){
                        val dataArray: ArrayList<LeavesModel> = responsedata!!.response.requests

                        if (dataArray.size > 0) {
                            noDataRelative!!.visibility = View.GONE
                            for (i in 0 until dataArray.size) {
                                mAbsenceListViewArray.addAll(dataArray)

                            }
                            mAbsenceListView.layoutManager=LinearLayoutManager(mContext)
                            val mAbsenceRecyclerAdapter =
                                AbsenceRecyclerAdapter(mContext, mAbsenceListViewArray)
                            mAbsenceListView.adapter = mAbsenceRecyclerAdapter
                        } else {
                            mAbsenceListView.layoutManager=LinearLayoutManager(mContext)
                            val mAbsenceRecyclerAdapter =
                                AbsenceRecyclerAdapter(mContext, mAbsenceListViewArray)
                            mAbsenceListView.adapter = mAbsenceRecyclerAdapter
                            noDataRelative!!.visibility = View.VISIBLE
                            noDataTxt!!.text = "Currently you have no absence records"
                            // AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "No data available.", R.drawable.exclamationicon, R.drawable.round);
                        }



                    }
                }

            }
        })
    }
    override fun onResume() {
        super.onResume()
        if (!PreferenceManager().getUserId(mContext).equals("")) {
           /* AppController().getInstance().getGoogleAnalyticsTracker()
                .set("&uid", PreferenceManager().getUserId(mContext))
            AppController().getInstance().getGoogleAnalyticsTracker()
                .set("&cid", PreferenceManager().getUserId(mContext))
            AppController().getInstance().trackScreenView(
                "Absence Fragment" + " " + "(" + PreferenceManager().getUserEmail(mContext) + ")" + " " + "(" + Calendar.getInstance().time + ")"
            )*/
        }
        if (firstVisit) {
            firstVisit = false
        } else {
            if (AppUtils().isNetworkConnected(mContext)) {
                studentName!!.setText(PreferenceManager().getLeaveStudentName(mContext))
                getList(PreferenceManager().getLeaveStudentId(mContext).toString())
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    "Network Error",
                    getString(R.string.no_internet),
                    R.drawable.nonetworkicon,
                    R.drawable.roundred
                )
            }
        }
    }
}