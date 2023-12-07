package com.example.bskl_kotlin.fragment.reports

import ApiClient
import android.app.Dialog
import android.content.Context
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.common.model.StudentListApiModel
import com.example.bskl_kotlin.common.model.StudentListModel
import com.example.bskl_kotlin.common.model.StudentListResponseModel
import com.example.bskl_kotlin.constants.OnItemClickListener
import com.example.bskl_kotlin.constants.addOnItemClickListener
import com.example.bskl_kotlin.fragment.absence.adapter.StudentSpinnerAdapter
import com.example.bskl_kotlin.fragment.reports.adapter.ReportsRecyclerAdapter
import com.example.bskl_kotlin.fragment.reports.model.ReportsApiModel
import com.example.bskl_kotlin.fragment.reports.model.ReportsDataModel
import com.example.bskl_kotlin.fragment.reports.model.ReportsModel
import com.example.bskl_kotlin.fragment.reports.model.StudentInfoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportFragment(title: String, tabId: String) : Fragment() {

    lateinit var mContext: Context

    lateinit var mStudentSpinner: LinearLayout
    lateinit var studentName: TextView
    lateinit var studImg: ImageView
    lateinit var noDataImg: ImageView
    lateinit var mRecycleView: RecyclerView
    lateinit var alertText: TextView
    lateinit var noDataRelative: RelativeLayout
    lateinit var relMain: RelativeLayout
    lateinit var alertTxtRelative: RelativeLayout

    //lateinit var textViewYear: TextView
    lateinit var noDataTxt: TextView

    lateinit var studentsModelArrayList: ArrayList<StudentListModel>
    var studentsModelArrayListCopy: ArrayList<StudentListModel>? = null

    // lateinit  var studentDetail: ListView
    // lateinit var studentName: TextView
    lateinit var textViewYear: TextView
    var stud_id = ""
    var stud_class = ""
    var stud_name = ""
    var stud_img = ""
    var progressReport = ""
    var section = ""
    var alumini = ""
    var type = ""

    lateinit var reportslist:ArrayList<ReportsDataModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.progress_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        headerTitle.text = "Reports"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        initUi()


    }

    private fun initUi() {

        alertText = requireView().findViewById<TextView>(R.id.noDataTxt)
        mStudentSpinner = requireView().findViewById<LinearLayout>(R.id.studentSpinner)
        studentName = requireView().findViewById<TextView>(R.id.studentName)
        studImg = requireView().findViewById<ImageView>(R.id.imagicon)
        noDataImg = requireView().findViewById<ImageView>(R.id.noDataImg)
        mRecycleView = requireView().findViewById<RecyclerView>(R.id.recycler_view_list)
        noDataRelative = requireView().findViewById<RelativeLayout>(R.id.noDataRelative)
        relMain = requireView().findViewById<RelativeLayout>(R.id.relMain)
        alertTxtRelative = requireView().findViewById<RelativeLayout>(R.id.noDataRelative)
        textViewYear = requireView().findViewById<TextView>(R.id.textViewYear)
        noDataTxt = requireView().findViewById<TextView>(R.id.noDataTxt)
        reportslist= ArrayList()

        getStudentsListAPI()
        mStudentSpinner.setOnClickListener {
            showStudentList(studentsModelArrayList)
        }
    }

    fun getStudentsListAPI() {
        studentsModelArrayList = ArrayList()
        var studlist=
            StudentListApiModel( PreferenceManager().getUserId(com.example.bskl_kotlin.fragment.home.mContext).toString())

        val call: Call<StudentListResponseModel> = ApiClient.getClient.student_list(
            studlist,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString()
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

                        if (response.body()!!.response.data.size > 0) {

                            studentsModelArrayList.addAll(response.body()!!.response.data)
                            Log.e("listsize",studentsModelArrayList.size.toString())
                            Log.e("id","empty")
                            Log.e("name",studentsModelArrayList[0].name.toString())
                            studentName.setText(studentsModelArrayList[0].name)
                            stud_id = studentsModelArrayList[0].id.toString()
                            stud_name = studentsModelArrayList[0].name.toString()
                            stud_class = studentsModelArrayList[0].mClass.toString()
                            stud_img = studentsModelArrayList[0].photo.toString()
                            progressReport = studentsModelArrayList[0].progressreport.toString()
                            section = studentsModelArrayList[0].section.toString()
                            alumini = studentsModelArrayList[0].alumi.toString()

                            if (stud_img != "") {
                                Glide.with(mContext) //1
                                    .load(stud_img)
                                    .placeholder(R.drawable.boy)
                                    .error(R.drawable.boy)
                                    .skipMemoryCache(true) //2
                                    .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                    .transform(CircleCrop()) //4
                                    .into(studImg)
                                /* Picasso.with(mContext).load(AppUtils.replace(stud_img))
                                    .placeholder(R.drawable.boy).fit().into(studImg)*/
                            } else {
                                studImg.setImageResource(R.drawable.boy)
                            }
                            textViewYear.text =
                                "Class : " + studentsModelArrayList[0].mClass
                            PreferenceManager().setCCAStudentIdPosition(mContext, "0")

                          /*  if (PreferenceManager().getStudIdForCCA(mContext).equals("")) {
                                Log.e("id","empty")
                                Log.e("name",studentsModelArrayList[0].mName.toString())
                                studentName.setText(studentsModelArrayList[0].mName)
                                stud_id = studentsModelArrayList[0].mId.toString()
                                stud_name = studentsModelArrayList[0].mName.toString()
                                stud_class = studentsModelArrayList[0].mClass.toString()
                                stud_img = studentsModelArrayList[0].mPhoto.toString()
                                progressReport = studentsModelArrayList[0].progressreport.toString()
                                section = studentsModelArrayList[0].mSection.toString()
                                alumini = studentsModelArrayList[0].alumini.toString()

                                if (stud_img != "") {
                                    Glide.with(mContext) //1
                                        .load(stud_img)
                                        .placeholder(R.drawable.boy)
                                        .error(R.drawable.boy)
                                        .skipMemoryCache(true) //2
                                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                        .transform(CircleCrop()) //4
                                        .into(studImg)
                                    *//* Picasso.with(mContext).load(AppUtils.replace(stud_img))
                                        .placeholder(R.drawable.boy).fit().into(studImg)*//*
                                } else {
                                    studImg.setImageResource(R.drawable.boy)
                                }
                                textViewYear.text =
                                    "Class : " + studentsModelArrayList[0].mClass
                                PreferenceManager().setCCAStudentIdPosition(mContext, "0")
                            } else {
                                val studentSelectPosition: Int = Integer.valueOf(
                                    PreferenceManager().getCCAStudentIdPosition(mContext)
                                )
                                studentName.setText(studentsModelArrayList[studentSelectPosition].mName)
                                stud_id =
                                    studentsModelArrayList[studentSelectPosition].mId.toString()
                                stud_name =
                                    studentsModelArrayList[studentSelectPosition].mName.toString()
                                stud_class =
                                    studentsModelArrayList[studentSelectPosition].mClass.toString()
                                progressReport =
                                    studentsModelArrayList[studentSelectPosition].progressreport.toString()
                                stud_img =
                                    studentsModelArrayList[studentSelectPosition].mPhoto.toString()
                                alumini =
                                    studentsModelArrayList[studentSelectPosition].alumini.toString()
                                System.out.println("selected student image" + studentsModelArrayList[studentSelectPosition].mPhoto)
                                if (stud_img != "") {
                                    Glide.with(mContext) //1
                                        .load(stud_img)
                                        .placeholder(R.drawable.boy)
                                        .error(R.drawable.boy)
                                        .skipMemoryCache(true) //2
                                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                        .transform(CircleCrop()) //4
                                        .into(studImg)
                                } else {
                                    studImg.setImageResource(R.drawable.boy)
                                }
                                textViewYear.text =
                                    "Class : " + studentsModelArrayList[studentSelectPosition].mClass
                            }*/
                            if (progressReport.equals("0")) {
                                alertTxtRelative.visibility = View.GONE
                                alertText.visibility = View.GONE
                                noDataImg.visibility = View.GONE
                                mRecycleView.visibility = View.VISIBLE
                                reportlistApi()
                                /*if (AppUtils.isNetworkConnected(mContext)) {

                                    //getReportListAPI(URL_GET_STUDENT_REPORT)
                                } else {
                                    AppUtils.showDialogAlertDismiss(
                                        mContext as Activity,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }*/
                            } else {
                                alertTxtRelative.visibility = View.VISIBLE
                                alertText.visibility = View.VISIBLE
                                noDataImg.visibility = View.VISIBLE
                                alertText.setText(R.string.not_available_child)
                                mRecycleView.visibility = View.GONE
                                println("testing........")
                            }
                        } else {
//                                mRecycleView.setVisibility(View.GONE);
                            Toast.makeText(mContext, "No data found", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        })
    }

    fun showStudentList(mStudentList: ArrayList<StudentListModel>) {
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
        if (mStudentList.size > 0) {
            val studentAdapter = StudentSpinnerAdapter(mContext, mStudentList)
            studentListRecycler.adapter = studentAdapter
        }

        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }

        studentListRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                //progressDialogAdd.visibility = View.VISIBLE

                studentName.setText(mStudentList.get(position).name)
                stud_img = mStudentList.get(position).photo.toString()
                stud_id = mStudentList.get(position).id.toString()
                stud_class = mStudentList.get(position).mClass.toString()
                stud_name=mStudentList[position].name.toString()
                section=mStudentList[position].section.toString()
                progressReport=mStudentList[position].progressreport.toString()
                alumini=mStudentList[position].alumi.toString()
                textViewYear.setText("Class : " + mStudentList.get(position).mClass)

               /* PreferenceManager().setStudentID(mContext, studentId)
                PreferenceManager().setStudentName(mContext, studentName)
                PreferenceManager().setStudentPhoto(mContext, studentImg)
                PreferenceManager().setStudentClass(mContext, studentClass)*/

                if (!stud_img.equals("")) {
                    Glide.with(mContext) //1
                        .load(stud_img)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(studImg)
                } else {
                    studImg.setImageResource(R.drawable.boy)
                }
                if (progressReport.equals("0")) {
                    alertTxtRelative.visibility = View.GONE
                    alertText.visibility = View.GONE
                    noDataImg.visibility = View.GONE
                    mRecycleView.visibility = View.VISIBLE
                    reportlistApi()
                  /*  if (AppUtils.isNetworkConnected(mContext)) {
                        println("test working")
                        getReportListAPI(URL_GET_STUDENT_REPORT)
                    } else {
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity,
                            "Network Error",
                            getString(R.string.no_internet),
                            R.drawable.nonetworkicon,
                            R.drawable.roundred
                        )
                    }*/
                } else {
                    alertTxtRelative.visibility = View.VISIBLE
                    alertText.visibility = View.VISIBLE
                    noDataImg.visibility = View.VISIBLE
                    alertText.setText(R.string.not_available_child)
                    mRecycleView.visibility = View.GONE

                }
               // progressDialogAdd.visibility = View.VISIBLE
               /* if (select_val == 0) {
                    callStudentLeaveInfo()
                } else if (select_val == 1) {
                    callpickuplist_api()
                }*/
                dialog.dismiss()
            }
        })
        dialog.show()
    }
    private fun reportlistApi(){

        reportslist=ArrayList()
        var reportmodel=ReportsApiModel(stud_id)
        val call: Call<ReportsModel> = ApiClient.getClient.progress_report(
            reportmodel,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString())

        call.enqueue(object : Callback<ReportsModel> {
            override fun onFailure(call: Call<ReportsModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<ReportsModel>,
                response: Response<ReportsModel>
            ) {

//                    studentsModelArrayList = new ArrayList<>();//wrong
                val mStudentModel = ArrayList<StudentInfoModel>()
                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){

                        val data: ArrayList<StudentInfoModel> = responsedata!!.response.responseArray

                        if (data.size > 0) {
                            noDataRelative.visibility = View.GONE
                            mRecycleView.visibility = View.VISIBLE
                            for (i in 0 until data.size) {
                                val dataObject = data[i]
                                val model = StudentInfoModel(dataObject.acyear,dataObject.mDataModel)
                                val mDatamodel: ArrayList<ReportsDataModel> =
                                    ArrayList<ReportsDataModel>()
                                val list: ArrayList<ReportsDataModel> = dataObject.mDataModel
                                if (list.size > 0) {

                                    for (x in 0 until list.size) {
                                        val listObject = list[x]
                                        val xmodel=ReportsDataModel(listObject.id,listObject.reporting_cycle,listObject.academic_year,
                                            listObject.class_id,listObject.email_sent_date,listObject.updated_on,listObject.reportcycleid,
                                            listObject.file,listObject.school_code_id,listObject.stud_id,listObject.isams_id)
                                        mDatamodel.add(xmodel)
                                      /*  val xmodel = ReportsDataModel("",listObject.academic_year,listObject.class_id,
                                            listObject.reporting_cycle,listObject.file,listObject.stud_id,listObject.created_on,listObject.updated_on)
                                        mDatamodel.add(xmodel)*/
                                    }
                                  /*  Log.e("size reports",mStudentModel.size.toString())
                                    val llm = LinearLayoutManager(mContext)

                                    mRecycleView.layoutManager = llm
                                    mRecycleView.visibility = View.VISIBLE
                                    val mRecyclerViewMainAdapter =
                                        ReportsRecyclerAdapter(mContext, mStudentModel)
                                    mRecycleView.adapter = mRecyclerViewMainAdapter*/
                                }

                               model.mDataModel=mDatamodel

                                mStudentModel.add(model)
                                Log.e("size reports",mStudentModel.size.toString())
                                val llm = LinearLayoutManager(mContext)

                                mRecycleView.layoutManager = llm
                                mRecycleView.visibility = View.VISIBLE
                                val mRecyclerViewMainAdapter =
                                    ReportsRecyclerAdapter(mContext, mStudentModel)
                                mRecycleView.adapter = mRecyclerViewMainAdapter
                            }
                        } else {
                            mRecycleView.visibility = View.GONE
                            noDataRelative.visibility = View.VISIBLE
                            alertTxtRelative.visibility = View.VISIBLE
                            alertText.visibility = View.VISIBLE
                            noDataImg.visibility = View.VISIBLE
                            noDataTxt.text = "Currently you have no reports"
//                                Toast.makeText(getActivity(), "No progress reports available.", Toast.LENGTH_SHORT).show();
                        }


                    }
                }

                }
        })

    }

}