/*
package com.example.bskl_kotlin.fragment.safeguarding

//import com.example.bskl_kotlin.fragment.safeguarding.adapter.SafeGuardingAdapter
import ApiClient
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.common.model.CommonResponseModel
import com.example.bskl_kotlin.fragment.absence.model.StudentModel
import com.example.bskl_kotlin.fragment.safeguarding.adapter.SafeGuardingAdapter
import com.example.bskl_kotlin.fragment.safeguarding.model.SafeguardingResponseModel
import com.example.bskl_kotlin.fragment.safeguarding.model.TypeModel
import com.example.bskl_kotlin.manager.AppUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SafeGuardingFragment:Fragment() {
    lateinit var  mRootView: View
    lateinit var  mContext: Context

   lateinit var currentDateTextView: TextView
   lateinit var mStudentRecyclerView: RecyclerView
   lateinit var viewBottom: View
   lateinit var mIntent: Intent
    private val mTitle: String? = null
    private val mTabId: String? = null
   lateinit var mStudentAttendanceList: ArrayList<StudentModel>
   lateinit var noDataRelative: RelativeLayout
    var noDataTxt: TextView? = null
   lateinit var mSafeGuardingAdapter: SafeGuardingAdapter
   lateinit var linearMain: LinearLayout
   lateinit var mStudentAttendanceListCopy: ArrayList<StudentModel>
   lateinit var typeArrayList: ArrayList<TypeModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mRootView= inflater.inflate(R.layout.fragment_safe_guarding_layout, container, false)
        return mRootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        val headerTitle = actionBar!!.customView.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = actionBar!!.customView.findViewById<ImageView>(R.id.logoClickImgView)
        val imageButton2 = actionBar!!.customView.findViewById<ImageView>(R.id.action_bar_forward)
        imageButton2.setImageResource(R.drawable.settings)
        headerTitle.setText("Safeguarding")
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        initialiseUI()

//        String myFormatCalender = "EEE dd MMMM yyyy";
        val dayEnd = DateFormat.format("dd", Calendar.getInstance().time) as String // 20

        val sdfcalender: SimpleDateFormat

        sdfcalender = if (dayEnd.endsWith("1") && !dayEnd.endsWith("11")) {
            SimpleDateFormat("EEE dd'st' MMMM yyyy", Locale.ENGLISH)
        } else if (dayEnd.endsWith("2") && !dayEnd.endsWith("12")) {
            SimpleDateFormat("EEE dd'nd' MMMM yyyy", Locale.ENGLISH)
        } else if (dayEnd.endsWith("3") && !dayEnd.endsWith("13")) SimpleDateFormat(
            "EEE dd'rd' MMMM yyyy",
            Locale.ENGLISH
        ) else SimpleDateFormat("EEE dd'th' MMMM yyyy", Locale.ENGLISH)

        val dateString = sdfcalender.format(Calendar.getInstance().time)
        currentDateTextView.text = dateString
        if (AppUtils().isNetworkConnected(mContext)) {
           callSafeGuarding()
        } else {
            AppUtils().showDialogAlertDismiss(
                mContext as Activity?,
                "Network Error",
                mContext.getResources().getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }
    }

    fun callSafeGuarding() {
        val call: Call<SafeguardingResponseModel> = ApiClient.getClient.safeguarding( PreferenceManager().getaccesstoken(mContext).toString(),
             PreferenceManager().getUserId(mContext).toString())

         call.enqueue(object : Callback<SafeguardingResponseModel> {
             override fun onFailure(call: Call<SafeguardingResponseModel>, t: Throwable) {
                 Log.e("Failed", t.localizedMessage)

             }

             override fun onResponse(
                 call: Call<SafeguardingResponseModel>,
                 response: Response<SafeguardingResponseModel>
             ) {

                 val responsedata = response.body()
                 Log.e("Response Signup", responsedata.toString())


                 if (response.body()!!.responsecode.equals("200")) {
                     if (response.body()!!.response.statuscode.equals("303")) {
                         typeArrayList = ArrayList<TypeModel>()
                         */
/*for (t in 0 until typeData.length()) {
                             Log.e("TYPE", "WORKS")
                             val tModel = TypeModel()
                             tModel.setStud_id(typeObject.optString("stud_id"))
                             tModel.setType(typeObject.optString("type"))
                             SafeGuardingFragment.typeArrayList.add(tModel)
                         }*//*

                         mStudentAttendanceList = ArrayList<StudentModel>()
                         mStudentAttendanceListCopy = ArrayList<StudentModel>()
                        for(i in response.body()!!.response.attendance_data.indices)
                        {
                            val mStudentAttendanceModel = StudentModel()
                            mStudentAttendanceModel.abscenceId=response.body()!!.response.attendance_data.get(i).id
                            mStudentAttendanceModel.absenceCodeId=response.body()!!.response.attendance_data.get(i).absenceCodeId
                            mStudentAttendanceModel.isPresent=response.body()!!.response.attendance_data.get(i).isPresent
                            mStudentAttendanceModel.date=response.body()!!.response.attendance_data.get(i) .date

                            mStudentAttendanceModel.schoolId=response.body()!!.response.attendance_data.get(i).schoolId
                            mStudentAttendanceModel.isLate=response.body()!!.response.attendance_data.get(i).isLate
                            mStudentAttendanceModel.mName=response.body()!!.response.attendance_data.get(i).student_name
                            mStudentAttendanceModel.mClass=response.body()!!.response.attendance_data.get(i).mclass
                            mStudentAttendanceModel.mSection=response.body()!!.response.attendance_data.get(i).form
                            mStudentAttendanceModel.mId=response.body()!!.response.attendance_data.get(i).stud_id
                            mStudentAttendanceModel.mPhoto=response.body()!!.response.attendance_data.get(i).photo
                            mStudentAttendanceModel.alumi=("0")
                            mStudentAttendanceModel.status=response.body()!!.response.attendance_data.get(i).status
                            mStudentAttendanceModel.parent_id=response.body()!!.response.attendance_data.get(i).parent_id
                            mStudentAttendanceModel.parent_name=response.body()!!.response.attendance_data.get(i).parent_name
                            mStudentAttendanceModel.app_updated_on=response.body()!!.response.attendance_data.get(i) .app_updated_on

                            mStudentAttendanceModel.registrationComment=response.body()!!.response.attendance_data.get(i).registrationComment

                            if (typeArrayList.size > 0) {
                                for (l in typeArrayList.indices) {
                                    if (response.body()!!.response.attendance_data.get(i).stud_id.equals(
                                            typeArrayList.get(l).stud_id,
                                            ignoreCase = true
                                        )
                                    ) {
                                        mStudentAttendanceModel.type=(typeArrayList.get(l).type)
                                        */
/*Log.e(
                                            "TYPE",
                                            typeArrayList.get(l).type
                                        )*//*

                                    }
                                }
                            }
                            if (response.body()!!.response.attendance_data.get(i).isPresent.equals("0", ignoreCase = true)) {
                                mStudentAttendanceModel.abRegister=("")
                            } else {
                                mStudentAttendanceModel.abRegister=("Present")
                            }



                            if (response.body()!!.response.attendance_data.get(i).isPresent.equals("0", ignoreCase = true)) {
                                val absenceCodedId: String = response.body()!!.response.attendance_data.get(i).absenceCodeId!!
                                var code = ""

                                for (j in response.body()!!.response.codes.indices) {
                                  //  val mCodesArrayObject = codesArray.optJSONObject(j)
                                    if (absenceCodedId.equals(
                                            response.body()!!.response.codes.get(j).absenceCodeId,
                                            ignoreCase = true
                                        )
                                    ) {
                                        mStudentAttendanceModel.abRegister=(response.body()!!.response.codes.get(j).name)
                                        code =(response.body()!!.response.codes.get(j).code!!)
                                        break
                                    }
                                }
                                if (response.body()!!.response.attendance_data.get(i).status
                                        .equals("", ignoreCase = true) && code.equals(
                                        "A",
                                        ignoreCase = true
                                    )
                                ) {
                                    mStudentAttendanceModel.viewType=("3")
                                } else if (!response.body()!!.response.attendance_data.get(i).status
                                        .equals("", ignoreCase = true) && code.equals(
                                        "A",
                                        ignoreCase = true
                                    )
                                ) {
                                    mStudentAttendanceModel.viewType=("2")
                                } else {
                                    mStudentAttendanceModel.viewType=("1")
                                }
                            } else {
                                mStudentAttendanceModel.viewType=("1")
                            }
                            mStudentAttendanceList.add(mStudentAttendanceModel)
                        }



                         if (mStudentAttendanceList.size > 0) {
                             Log.e(
                                 "CONDITION",
                                 "WORKS ATTEND" + PreferenceManager().getSafeGuardingGroup(mContext))
                             for (k in mStudentAttendanceList.indices) {
                                 if (PreferenceManager().getSafeGuardingGroup(mContext).equals("1")
                                 ) {
                                     Log.e("WORKS", "CONDITION1")
                                     if (mStudentAttendanceList.get(k).type.equals("Primary")
                                     ) {
                                         mStudentAttendanceListCopy.add(mStudentAttendanceList.get(k)
                                         )
                                     }
                                 } else if (PreferenceManager().getSafeGuardingGroup(mContext).equals("2")
                                 ) {
                                     Log.e("WORKS", "CONDITION2")
                                     if (mStudentAttendanceList.get(k).type.equals("Secondary")
                                     ) {
                                        mStudentAttendanceListCopy.add(mStudentAttendanceList.get(k))
                                     }
                                 } else if (PreferenceManager().getSafeGuardingGroup(mContext).equals("3")
                                 ) {
                                     Log.e("WORKS", "CONDITION3")
                                     mStudentAttendanceListCopy.add(mStudentAttendanceList.get(k)
                                     )
                                 } else if (PreferenceManager().getSafeGuardingGroup(mContext).equals("4")
                                 ) {
                                     Log.e("WORKS", "CONDITION4")
                                     if (mStudentAttendanceList.get(k).type.equals("EYFS")
                                     ) {
                                         mStudentAttendanceListCopy.add(mStudentAttendanceList.get(k)
                                         )
                                     }
                                 } else if (PreferenceManager().getSafeGuardingGroup(mContext).equals("5")
                                 ) {
                                     if (mStudentAttendanceList.get(k)
                                             .type
                                             .equals("Primary") || mStudentAttendanceList.get(k).type.equals("Secondary")
                                     ) {
                                         mStudentAttendanceListCopy.add(mStudentAttendanceList.get(k)
                                         )
                                     }
                                 } else if (PreferenceManager().getSafeGuardingGroup(mContext).equals("6")
                                 ) {
                                     if (mStudentAttendanceList.get(k)
                                             .type
                                             .equals("Primary") || mStudentAttendanceList.get(k).type.equals("EYFS")
                                     ) {
                                        mStudentAttendanceListCopy.add(mStudentAttendanceList.get(k)
                                         )
                                     }
                                 } else if (PreferenceManager().getSafeGuardingGroup(mContext).equals("7")
                                 ) {
                                     if (mStudentAttendanceList.get(k)
                                             .type.equals("Secondary") || mStudentAttendanceList.get(k).type.equals("EYFS")
                                     ) {
                                         mStudentAttendanceListCopy.add(mStudentAttendanceList.get(k)
                                         )
                                     }
                                 }
                             }
                         }

                        mSafeGuardingAdapter = SafeGuardingAdapter(mStudentAttendanceListCopy, mContext)
                         mSafeGuardingAdapter.notifyDataSetChanged()
                         mStudentRecyclerView.setAdapter(mSafeGuardingAdapter)
                         if (mStudentAttendanceListCopy.size <= 0) {
                             noDataRelative.setVisibility(View.VISIBLE)
                             viewBottom!!.setVisibility(View.GONE)
                         } else {
                             noDataRelative.setVisibility(View.GONE)
                             viewBottom.setVisibility(View.VISIBLE)
                         }
                     }
                     } else if (response.body()!!.responsecode.equals("500")) {
                     AppUtils().showDialogAlertDismiss(
                         mContext as Activity,
                         "Alert",
                         getString(R.string.common_error),
                         R.drawable.exclamationicon,
                         R.drawable.round
                     )
                 } else {
                     AppUtils().showDialogAlertDismiss(
                         mContext as Activity,
                         "Alert",
                         getString(R.string.common_error),
                         R.drawable.exclamationicon,
                         R.drawable.round
                     )
                 }
             }

         })
    }
     fun reset() {
         val call: Call<CommonResponseModel> = ApiClient.getClient.reset( PreferenceManager().getaccesstoken(mContext).toString(),
              PreferenceManager().getUserId(mContext).toString())

          call.enqueue(object : Callback<CommonResponseModel> {
              override fun onFailure(call: Call<CommonResponseModel>, t: Throwable) {
                  Log.e("Failed", t.localizedMessage)

              }

              override fun onResponse(
                  call: Call<CommonResponseModel>,
                  response: Response<CommonResponseModel>
              ) {

                  val responsedata = response.body()

                  Log.e("Response Signup", responsedata.toString())

                  if (response.body()!!.responsecode.equals("200")) {
                      if (response.body()!!.response.statuscode.equals("303")) {


                      }
                      } else if (response.body()!!.responsecode.equals("500")) {
                      AppUtils().showDialogAlertDismiss(
                          mContext as Activity,
                          "Alert",
                          getString(R.string.common_error),
                          R.drawable.exclamationicon,
                          R.drawable.round
                      )
                  } else {
                      AppUtils().showDialogAlertDismiss(
                          mContext as Activity,
                          "Alert",
                          getString(R.string.common_error),
                          R.drawable.exclamationicon,
                          R.drawable.round
                      )
                  }
              }

          })
    }
    private fun initialiseUI() {

        currentDateTextView = mRootView!!.findViewById(R.id.currentDateTextView)
        noDataRelative = mRootView!!.findViewById<RelativeLayout>(R.id.noDataRelative)
        noDataTxt = mRootView!!.findViewById(R.id.noDataTxt)
        linearMain = mRootView!!.findViewById(R.id.linearMain)
        linearMain.setOnClickListener(View.OnClickListener { })
        mStudentRecyclerView =
            mRootView!!.findViewById<RecyclerView>(R.id.mStudentRecyclerView)
        viewBottom = mRootView!!.findViewById<View>(R.id.viewBottom)
//        mStudentRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
//
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //        mStudentRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
//
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        val mLayoutManager = GridLayoutManager(mContext, 1)
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false);

        //        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false);
        mStudentRecyclerView.setLayoutManager(mLayoutManager)
        mStudentRecyclerView.getLayoutManager()!!.setMeasurementCacheEnabled(false)
    }

  fun  submitAbsence( attendance_id:String,  student_id:String,   status:String,   details:String)
  {
      val call: Call<CommonResponseModel> = ApiClient.getClient.submitsafedaurding( PreferenceManager().getaccesstoken(mContext).toString(),
          PreferenceManager().getUserId(mContext).toString(),student_id,attendance_id,status,details)

      call.enqueue(object : Callback<CommonResponseModel> {
          override fun onFailure(call: Call<CommonResponseModel>, t: Throwable) {
              Log.e("Failed", t.localizedMessage)

          }

          override fun onResponse(
              call: Call<CommonResponseModel>,
              response: Response<CommonResponseModel>
          ) {

              val responsedata = response.body()

              Log.e("Response Signup", responsedata.toString())

              if (response.body()!!.responsecode.equals("200")) {
                  if (response.body()!!.response.statuscode.equals("303")) {

                      showDialogSuccess(
                          mContext ,
                          "Alert",
                          "Successfully reported.",
                          R.drawable.exclamationicon,
                          R.drawable.round
                      )
                  }
                  } else if (response.body()!!.responsecode.equals("500")) {
                  AppUtils().showDialogAlertDismiss(
                      mContext as Activity,
                      "Alert",
                      getString(R.string.common_error),
                      R.drawable.exclamationicon,
                      R.drawable.round
                  )
              } else {
                  AppUtils().showDialogAlertDismiss(
                      mContext as Activity,
                      "Alert",
                      getString(R.string.common_error),
                      R.drawable.exclamationicon,
                      R.drawable.round
                  )
              }
          }

      })
  }

    //    public static void submitAbsenceFromLeaveRequest(final Activity activity,final String attendance_id, final String status, final String details) {
    //        try {
    //
    //            final VolleyWrapper manager = new VolleyWrapper(URL_POST_SUBMIT_SAFE_GUARDING);
    //            String[] name = {JTAG_ACCESSTOKEN, JTAG_USER_IDS, "attendance_id", "status", "details"};
    //            String[] value = {PreferenceManager.getAccessToken(mContext), PreferenceManager.getUserId(mContext), attendance_id, status, details};
    //            manager.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {
    //
    //                @Override
    //                public void responseSuccess(String successResponse) {
    //                    Log.v("Response ", "SafeGuarding:Submit: " + successResponse);
    //
    //                    if (successResponse != null) {
    //                        try {
    //                            JSONObject obj = new JSONObject(successResponse);
    //                            String response_code = obj.optString(JTAG_RESPONSECODE);
    //                            if (response_code.equalsIgnoreCase("200")) {
    //                                JSONObject secobj = obj.optJSONObject(JTAG_RESPONSE);
    //                                String status_code = secobj.optString(JTAG_STATUSCODE);
    //                                if (status_code.equalsIgnoreCase("303")) {
    //                                    showDialogSuccess(activity, "Success", mContext.getResources().getString(R.string.succ_leave_submission), R.drawable.tick, R.drawable.round);
    //
    //                                    callSafeGuarding();
    //
    //                                } else if (status_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) || status_code.equalsIgnoreCase(RESPONSE_INVALID_TOKEN) ||
    //                                        status_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING)) {
    //                                    AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
    //                                        @Override
    //                                        public void getAccessToken() {
    //                                        }
    //                                    });
    //                                    submitAbsenceFromLeaveRequest(activity,attendance_id, status, details);
    //
    //                                } else {
    //                                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", mContext.getResources().getString(R.string.some_error), R.drawable.exclamationicon, R.drawable.round);
    //
    //                                }
    //
    //                            } else if (response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) || response_code.equalsIgnoreCase(RESPONSE_INVALID_TOKEN) ||
    //                                    response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING)) {
    //                                AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
    //                                    @Override
    //                                    public void getAccessToken() {
    //                                    }
    //                                });
    //                                submitAbsenceFromLeaveRequest(activity,attendance_id, status, details);
    //                            } else {
    //                                AppUtils.showDialogAlertDismiss(activity, "Alert", mContext.getResources().getString(R.string.some_error), R.drawable.exclamationicon, R.drawable.round);
    //
    //                            }
    //
    //                        } catch (Exception ex) {
    //                            ex.printStackTrace();
    //                        }
    //                    }
    //                }
    //
    //                @Override
    //                public void responseFailure(String failureResponse) {
    //                }
    //            });
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //
    //
    //    }
    fun showDialogSuccess(
        activity: Context,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity)
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
            callSafeGuarding()
        }
        dialog.show()
    }

}*/
