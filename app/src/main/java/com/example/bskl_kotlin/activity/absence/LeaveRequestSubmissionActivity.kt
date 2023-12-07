package com.example.bskl_kotlin.activity.absence

import ApiClient
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.absence.model.RequestLeaveApiModel
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.common.model.StudentListApiModel
import com.example.bskl_kotlin.common.model.StudentListModel
import com.example.bskl_kotlin.common.model.StudentListResponseModel
import com.example.bskl_kotlin.constants.OnItemClickListener
import com.example.bskl_kotlin.constants.addOnItemClickListener
import com.example.bskl_kotlin.fragment.absence.adapter.StudentSpinnerAdapter
import com.example.bskl_kotlin.fragment.settings.model.TriggerUserModel
import com.example.bskl_kotlin.manager.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LeaveRequestSubmissionActivity:AppCompatActivity() {
    lateinit var mContext: Context
    var submitBtn: Button? = null
    var enterMessage: EditText? = null
    var enterStratDate: TextView? = null
    var enterEndDate:TextView? = null
    var submitLayout: LinearLayout? = null
    var c: Calendar? = null
    var mYear = 0
    var mMonth = 0
    var mDay = 0
    var df: SimpleDateFormat? = null
    var formattedDate: String? = null
    var calendar: Calendar? = null
    var fromDate = ""
    var toDate:String? = ""
    var tomorrowAsString: String? = null
    var sdate: Date? = null
    var edate:Date? = null
    var elapsedDays: Long = 0
    var stud_img = ""
    var studImg: ImageView? = null
    var outputFormats: SimpleDateFormat? = null
    var extras: Bundle? = null
    var studentNameStr = ""
    var studentClassStr = ""
    var studentIdStr = ""
    var studentIdForSafe = ""
    var attendance_id = ""
    var status:String? = ""
    var studentName: TextView? = null
    var mStudentSpinner: LinearLayout? = null
    var Date: String = dateConversionY(getCurrentDateToday()).toString()
    lateinit var studentsModelArrayList: ArrayList<StudentListModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leave_request_submission_activity)

        mContext = this
initUi()
        getStudentsListAPI()
    }

    private fun initUi() {
        extras = intent.extras
        if (extras != null) {
            studentNameStr = extras!!.getString("studentName")!!
            studentIdStr = extras!!.getString("studentId")!!
            studentIdForSafe = extras!!.getString("studentId")!!
            stud_img = extras!!.getString("studentImage")!!
            attendance_id = extras!!.getString("attendance_id")!!
            status = extras!!.getString("status")
           /* studentsModelArrayList = (extras!!
                .getSerializable("StudentModelArray") as ArrayList<StudentListModel>)!!*/
        }
        calendar = Calendar.getInstance()

        outputFormats = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

        mStudentSpinner = findViewById(R.id.studentSpinner)
        studentName = findViewById(R.id.studentName)
        studImg = findViewById(R.id.studImg)
        enterMessage = findViewById<EditText>(R.id.enterMessage)
        enterStratDate = findViewById<TextView>(R.id.enterStratDate)

        enterStratDate!!.setText("Day absence reported : " + dateConversionY(getCurrentDateToday()))
        enterStratDate!!.setEnabled(false)
        enterEndDate = findViewById(R.id.enterEndDate)
        submitLayout = findViewById(R.id.submitLayout)
        submitBtn = findViewById(R.id.submitBtn)


        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_view_home)
        supportActionBar!!.elevation = 0f

        val view = supportActionBar!!.customView
        val toolbar = view.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)
        val headerTitle = findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = findViewById<ImageView>(R.id.logoClickImgView)
        val action_bar_forward = findViewById<ImageView>(R.id.action_bar_forward)
        val action_bar_back = findViewById<ImageView>(R.id.action_bar_back)
        action_bar_back.setImageResource(R.drawable.back_new)
        action_bar_forward.visibility = View.INVISIBLE
        headerTitle.setText("ABSENCE")
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
action_bar_back.setOnClickListener {
    finish()
}
        mStudentSpinner!!.setOnClickListener {
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
        studentName!!.setText(studentNameStr)
        if (stud_img != "") {
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

        PreferenceManager().setLeaveStudentId(mContext, studentIdStr)

//studentClass.setText(studentClassStr);

//studentClass.setText(studentClassStr);
        enterMessage!!.clearFocus()
        enterMessage!!.setFocusable(false)
        enterMessage!!.setOnTouchListener { v, event ->
            enterMessage!!.isFocusableInTouchMode = true
            false
        }
        submitBtn!!.setOnClickListener {
            if (AppUtils().isEditTextFocused(mContext as Activity)) {
                AppUtils().hideKeyBoard(mContext)
            }
            if (enterStratDate!!.text.toString() == "") {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    getString(R.string.alert_heading),
                    getString(R.string.enter_startdate),
                    R.drawable.infoicon,
                    R.drawable.round
                )
            } else if (enterEndDate!!.getText().toString() == "") {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    getString(R.string.alert_heading),
                    getString(R.string.enter_enddate),
                    R.drawable.infoicon,
                    R.drawable.round
                )
            } else if (enterMessage!!.text.toString().trim { it <= ' ' } == "") {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    getString(R.string.alert_heading),
                    getString(R.string.enter_reason),
                    R.drawable.infoicon,
                    R.drawable.round
                )
            } else {
                if (AppUtils().isNetworkConnected(mContext)) {
                    submitLeave()
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
        enterEndDate!!.setOnClickListener {
            if (AppUtils().isEditTextFocused(mContext as Activity)) {
                AppUtils().hideKeyBoard(mContext)
            }
            enterMessage!!.isFocusable = false
            enterMessage!!.clearFocus()
            if (enterStratDate!!.text.toString() == "") {
                Toast.makeText(mContext, "Please select first day of absence.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                var strDate: Date? = null
                val items1: List<String> = getCurrentDateToday()!!.split("-")
                val date1 = items1[0]
                //if()
                val month = items1[1]
                val year = items1[2]
                try {
                    strDate = sdf.parse(getCurrentDateToday())
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val endDatePicker = DatePickerDialog(
                    mContext, endDate, year.toInt(),
                    month.toInt() - 1, date1.toInt()
                )
                endDatePicker.datePicker.minDate = strDate!!.time
                endDatePicker.show()
            }
        }

    }

    private fun submitLeave() {
        var leavemodel= RequestLeaveApiModel(
            studentIdStr,
            PreferenceManager().getUserId(mContext).toString(),
            Date,
            toDate!!,
            enterMessage!!.text.toString().trim() )
        val call: Call<TriggerUserModel> = ApiClient.getClient.requestLeave(
            leavemodel,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString())

        call.enqueue(object : Callback<TriggerUserModel> {
            override fun onFailure(call: Call<TriggerUserModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<TriggerUserModel>,
                response: Response<TriggerUserModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){
                        if (attendance_id.equals("") && status.equals(
                                ""
                            )
                        ) {
                            showDialogSuccess(
                                mContext as Activity,
                                "Success",
                                getString(R.string.succ_leave_submission),
                                R.drawable.tick,
                                R.drawable.round
                            )
                        } else if (AppUtils().isNetworkConnected(mContext)) {
                            showDialogSuccess(
                                mContext as Activity,
                                "Success",
                                getString(R.string.succ_leave_submission),
                                R.drawable.tick,
                                R.drawable.round
                            )
                            //SafeGuardingFragment().callSafeGuarding()
                        } else {
                            showDialogSuccess(
                                mContext as Activity,
                                "Success",
                                getString(R.string.succ_leave_submission),
                                R.drawable.tick,
                                R.drawable.round
                            )
                        }

                    }
                }

            }
        })
    }
    fun showDialogSuccess(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
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
                dialog.dismiss()
                studentName!!.setText(mStudentArray[position].name)
                enterStratDate!!.text =
                    "Day absence reported : " + dateConversionY(getCurrentDateToday())

                /****Rijo added code****/
                /****Rijo added code */
                if (!studentIdForSafe.equals(mStudentArray[position].id)) {
                    enterStratDate!!.text =
                        "Day absence reported : " + dateConversionY(getCurrentDateToday())
                    enterStratDate!!.isEnabled = false
                    enterEndDate!!.text = ""
                    enterMessage!!.setText("")
                } else {
                    if (!attendance_id.equals("") && !status.equals(
                            ""
                        )
                    ) {
                        enterStratDate!!.isEnabled = true
                        enterStratDate!!.text =
                            "Day absence reported : " + dateConversionY(getCurrentDateToday())
                        enterStratDate!!.isEnabled = false
                    } else {
                        enterStratDate!!.isEnabled = true
                    }
                }
                /****Rijo added code****/
                /****Rijo added code */
                studentIdStr = mStudentArray[position].id
                studentClassStr = mStudentArray[position].mClass
                stud_img = mStudentArray[position].photo
                PreferenceManager().setLeaveStudentId(mContext, studentIdStr)
                PreferenceManager().setLeaveStudentName(mContext, mStudentArray[position].name)

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
            }
        })
        dialog.show()
    }
    var startDate =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            fromDate = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
            if (toDate != "") {
                val dateFormatt = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                try {
                    sdate = dateFormatt.parse(fromDate)
                    edate = dateFormatt.parse(toDate)
                    printDifference(sdate!!, edate!!)
                } catch (e: java.lang.Exception) {
                }
            }
            if (elapsedDays < 0 && toDate != "") {
                fromDate = dateConversionYToD(enterStratDate!!.text.toString())
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    getString(R.string.alert_heading),
                    "Choose first day of absence(date) less than or equal to selected return to school(date)",
                    R.drawable.infoicon,
                    R.drawable.round
                )
                //break;
            } else {
                enterStratDate!!.text =
                    "Day absence reported : " + dateConversionY(getCurrentDateToday())
                //                try {
                //                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                //                    Date strDate = sdf.parse(fromDate);
                //                    c = Calendar.getInstance();
                //                    mYear = c.get(Calendar.YEAR);
                //                    mMonth = c.get(Calendar.MONTH);
                //                    mDay = c.get(Calendar.DAY_OF_MONTH);
                //
                //
                //                    df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                //                    formattedDate = df.format(c.getTime());
                //                    Date endDate = sdf.parse(formattedDate);
                //
                //                    SimpleDateFormat dateFormatt = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                //                    Date convertedDate = new Date();
                //                    try {
                //                        convertedDate = dateFormatt.parse(enterStratDate.getText().toString());
                //                    } catch (ParseException e) {
                //                        // TODO Auto-generated catch block
                //                        e.printStackTrace();
                //                    }
                //                    calendar.setTime(convertedDate);
                //                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                //
                //                    Date tomorrow = calendar.getTime();
                //
                //
                //                    tomorrowAsString = dateFormatt.format(tomorrow);
                //
                //                    System.out.println("Tomorrow--" + tomorrowAsString);
                //
                //                } catch (ParseException e) {
                //                    e.printStackTrace();
                //                }
            }
        }
    var endDate =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            toDate = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
            if (toDate != "") {
                val dateFormatt = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                try {
                    sdate = dateFormatt.parse(fromDate)
                    edate = dateFormatt.parse(toDate)
                    printDifference(sdate!!, edate!!)
                } catch (e: java.lang.Exception) {
                }
                if (elapsedDays < 0) {
                    toDate = AppUtils().dateConversionYToD(enterEndDate!!.text.toString())
                    AppUtils().showDialogAlertDismiss(
                        mContext as Activity,
                        getString(R.string.alert_heading),
                        "Choose return to school(date) greater than or equal to first day of absence(date)",
                        R.drawable.infoicon,
                        R.drawable.round
                    )

                    //break;
                } else {
                    enterEndDate!!.setText(AppUtils().dateConversionY(toDate))
                }
            }
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val strDate = sdf.parse(toDate)
                c = Calendar.getInstance()
                mYear = c!!.get(Calendar.YEAR)
                mMonth = c!!.get(Calendar.MONTH)
                mDay = c!!.get(Calendar.DAY_OF_MONTH)
                df = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                formattedDate = df!!.format(c!!.getTime())
                val endDate = sdf.parse(formattedDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

    fun printDifference(startDate: Date, endDate: Date) {

        //milliseconds
        var different = endDate.time - startDate.time
        println("startDate : $startDate")
        println("endDate : $endDate")
        println("different : $different")
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        elapsedDays = different / daysInMilli
        different = different % daysInMilli
        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli
        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli
        val elapsedSeconds = different / secondsInMilli
        System.out.printf(
            "%d days, %d hours, %d minutes, %d seconds%n",
            elapsedDays,
            elapsedHours, elapsedMinutes, elapsedSeconds
        )
    }
    fun dateConversionY(inputDate: String?): String? {
        var mDate = ""
        try {
            val date: Date
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            date = formatter.parse(inputDate)
            //Subtracting 6 hours from selected time
            val time = date.time

            //SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMMM yyyy");
            val formatterFullDate = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            mDate = formatterFullDate.format(time)
        } catch (e: Exception) {
            Log.d("Exception", "" + e)
        }
        return mDate
    }
    fun getCurrentDateToday(): String? {
        val dateFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }
    fun dateConversionYToD(inputDate: String?): String {
        var mDate = ""
        try {
            val date: Date
            val formatter: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            date = formatter.parse(inputDate)
            //Subtracting 6 hours from selected time
            val time = date.time

            //SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMMM yyyy");
            val formatterFullDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            mDate = formatterFullDate.format(time)
        } catch (e: java.lang.Exception) {
            Log.d("Exception", "" + e)
        }
        return mDate
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
                        studentsModelArrayList.addAll(response.body()!!.response.data)

                    }

                }
            }
        })
    }
}