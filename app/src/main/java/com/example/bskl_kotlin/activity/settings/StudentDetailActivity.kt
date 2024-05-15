package com.example.bskl_kotlin.activity.settings

import ApiClient
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.datacollection_p2.model.PassportDetailModel
import com.example.bskl_kotlin.activity.settings.adapter.LocalEmergencyRecyclerAdapter
import com.example.bskl_kotlin.activity.settings.adapter.NextOfKinRecyclerAdapter
import com.example.bskl_kotlin.activity.settings.adapter.PassportDetailRecyclerAdapter
import com.example.bskl_kotlin.activity.settings.adapter.RelationRecyclerAdapter
import com.example.bskl_kotlin.activity.settings.adapter.StudentDetailRecyclerAdapter
import com.example.bskl_kotlin.activity.settings.model.ContactDetails
import com.example.bskl_kotlin.activity.settings.model.KinDetailsModel
import com.example.bskl_kotlin.activity.settings.model.LocalEmergencyModel
import com.example.bskl_kotlin.activity.settings.model.OwnDetailsModel
import com.example.bskl_kotlin.activity.settings.model.PassportDetailModelNew
import com.example.bskl_kotlin.activity.settings.model.RelationshipModel
import com.example.bskl_kotlin.activity.settings.model.StudentDetailModelNew
import com.example.bskl_kotlin.activity.settings.model.UserprofileStudentApiModel
import com.example.bskl_kotlin.activity.settings.model.UserprofileStudentResponseModel
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.manager.AppUtils
import com.example.kingsapp.activities.calender.model.StudentDetailModel
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException

class StudentDetailActivity:AppCompatActivity() {
    lateinit var  mContext:Context
    var  studPhoto = ""
    var  stud_id = ""
    lateinit var  iconImgProfile: ImageView
    lateinit var  extras: Bundle
    lateinit var  studentDetail: RecyclerView
    lateinit var  passportDetailRecycler:RecyclerView
    lateinit var  visaPermitDetailRecycler:RecyclerView
    lateinit var  medicalInsDetailRecycler:RecyclerView
    lateinit var  personalInsDetailRecycler:RecyclerView
    lateinit var  studentRelationship: RecyclerView
    lateinit var  nextOfKinRecycler: RecyclerView
    lateinit var  localEmergencyRecycler: RecyclerView
    lateinit var  mStudentDetailModelList: ArrayList<StudentDetailModelNew>
    lateinit var  mPassportDetailArrayList: ArrayList<PassportDetailModelNew>
    lateinit var  mVisaPermitDetailArrayList: ArrayList<PassportDetailModelNew>
    lateinit var  mMedicalInsuranceDetailArrayList: ArrayList<PassportDetailModelNew>
    lateinit var  mPersonalInsuranceDetailArrayList: ArrayList<PassportDetailModelNew>
    lateinit var  mRelationshipModelList: ArrayList<RelationshipModel>
    lateinit var  mKinDetailsModelList: ArrayList<KinDetailsModel>
    lateinit var  mOwnDetailsModelList: ArrayList<OwnDetailsModel>
    lateinit var  mLocalEmergencyModelList: ArrayList<LocalEmergencyModel>
    lateinit var  otherContactLinear: LinearLayout
    lateinit var  nextOfKinLinear: LinearLayout
    lateinit var  localEmergencyLinear: LinearLayout
    lateinit var  passportDetailLinear:LinearLayout
    lateinit var  visaPermitDetailLinear:LinearLayout
    lateinit var  medicalInsDetailLinear:LinearLayout
    lateinit var  personalInsDetailLinear:LinearLayout

    lateinit var  noNxtOfKin: TextView
    lateinit var  noLocalEmergency:TextView
    lateinit var  infoTxt:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_detail_activity)

        extras = intent.extras!!
        mContext = this

        if (extras != null) {
            stud_id = extras.getString("stud_id")!!
        }

        try {
            initialiseUI()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (AppUtils().checkInternet(mContext)) {
            getStudentUserProfileApi()
        } else {
            AppUtils().showDialogAlertDismiss(
                mContext as Activity?,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }
    }

    private fun getStudentUserProfileApi() {
        mLocalEmergencyModelList= ArrayList()
        mRelationshipModelList=ArrayList()
        mKinDetailsModelList=ArrayList()
        mOwnDetailsModelList=ArrayList()
        var usermodel= UserprofileStudentApiModel(
            PreferenceManager().getUserId(mContext).toString(),stud_id )
        val call: Call<UserprofileStudentResponseModel> = ApiClient.getClient.userprofile_studentdetails(
            usermodel,"Bearer "+ PreferenceManager().getaccesstoken(mContext).toString())

        call.enqueue(object : Callback<UserprofileStudentResponseModel> {
            override fun onFailure(call: Call<UserprofileStudentResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<UserprofileStudentResponseModel>,
                response: Response<UserprofileStudentResponseModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){
                        studPhoto = responsedata!!.response.stud_photo
                        val studentDetailsJsonArray: ArrayList<StudentDetailModelNew> =
                            responsedata!!.response.studentdetails
                        for (i in 0 until studentDetailsJsonArray.size) {
                            println("working 11")
                            var studObj = studentDetailsJsonArray[i]
                            var studentDetailModel = StudentDetailModelNew()
                            studentDetailModel.title=responsedata!!.response.studentdetails[i].title
                            studentDetailModel.value=responsedata!!.response.studentdetails[i].value
                            studentDetailModel.value=responsedata!!.response.studentdetails[i].value
                            studentDetailModel.is_expired=""
                            if (studObj.title
                                    .equals("") || studObj.title
                                    .equals("null") || studObj.title
                                    .equals(
                                        "undefined"
                                    ) || studObj.value
                                    .equals("null") || studObj.value
                                    .equals("") || studObj.value
                                    .equals("undefined")
                            ) {
                            } else {
                                mStudentDetailModelList.add(studentDetailModel)
                            }
                        }
                        println("before passport works")
                        if (responsedata!!.response.passport!=null) {
                            val passportDetailArray: ArrayList<PassportDetailModelNew> = responsedata!!.response.passport
                            for (i in 0 until passportDetailArray.size) {
                                println("working 11")
                                val passObj = passportDetailArray[i]
                                val mModel = PassportDetailModelNew()
                                mModel.title=passObj.title
                                mModel.value=passObj.value
                                mModel.is_expired=passObj.is_expired
                               
                                if (passObj.title
                                        .equals("") || passObj.title
                                        .equals(
                                            "null"
                                        ) || passObj.title.equals(
                                        "undefined"
                                    ) || passObj.value.equals(
                                        "null"
                                    ) || passObj.value
                                        .equals("") || passObj.value
                                        .equals("undefined")
                                ) {
                                } else {
                                    mPassportDetailArrayList.add(mModel)
                                }
                            }
                        } else {
                            passportDetailLinear.visibility = View.GONE
                        }

                        if (responsedata!!.response.visa_permit!=null) {
                            val visaPermitDetailArray: ArrayList<PassportDetailModelNew> =
                                responsedata!!.response.visa_permit
                            for (i in 0 until visaPermitDetailArray.size) {
                                println("working 11")
                                val visaObj = visaPermitDetailArray[i]
                                val mModel = PassportDetailModelNew()
                                mModel.title=visaObj.title
                                mModel.value=visaObj.value
                                mModel.is_expired=visaObj.is_expired

                                if (visaObj.title
                                        .equals("") || visaObj.title
                                        .equals(
                                            "null"
                                        ) || visaObj.title.equals(
                                        "undefined"
                                    ) || visaObj.value.equals(
                                        "null"
                                    ) || visaObj.value
                                        .equals("") || visaObj.value
                                        .equals("undefined")
                                ) {
                                } else {
                                    mVisaPermitDetailArrayList.add(mModel)
                                }
                            }
                        } else {
                            visaPermitDetailLinear.visibility = View.GONE
                        }
                        if (responsedata!!.response.medical_insurance!=null) {
                            val medicalInsuranceArray: ArrayList<PassportDetailModelNew> =
                               responsedata!!.response.medical_insurance
                            for (i in 0 until medicalInsuranceArray.size) {
                                println("working 11")
                                val medInsuObj = medicalInsuranceArray[i]
                                val mModel = PassportDetailModelNew()
                                mModel.title=medInsuObj.title
                                mModel.value=medInsuObj.value
                                mModel.is_expired=medInsuObj.is_expired

                                if (medInsuObj.title.equals(
                                        ""
                                    ) || medInsuObj.title.equals(
                                        "null"
                                    ) || medInsuObj.title.equals(
                                        "undefined"
                                    ) || medInsuObj.value.equals(
                                        "null"
                                    ) || medInsuObj.value.equals(
                                        ""
                                    ) || medInsuObj.value
                                        .equals("undefined")
                                ) {
                                } else {
                                    mMedicalInsuranceDetailArrayList.add(mModel)
                                }
                            }
                        } else {
                            medicalInsDetailLinear.visibility = View.GONE
                        }
                        if (responsedata!!.response.personal_accident_insurance!=null) {
                            var personalInsuranceArray: ArrayList<PassportDetailModelNew> =
                                responsedata!!.response.personal_accident_insurance
                            for (i in 0 until personalInsuranceArray.size) {
                                println("working 11")
                                var personalInsuObj = personalInsuranceArray[i]
                                val mModel = PassportDetailModelNew()
                                mModel.title=personalInsuObj.title
                                mModel.value=personalInsuObj.value
                                mModel.is_expired=personalInsuObj.is_expired

                                if (personalInsuObj.title
                                        .equals("") || personalInsuObj.title.equals(
                                        "null"
                                    ) || personalInsuObj.title.equals(
                                        "undefined"
                                    ) || personalInsuObj.value.equals(
                                        "null"
                                    ) || personalInsuObj.value
                                        .equals("") || personalInsuObj.value.equals("undefined")
                                ) {
                                } else {
                                    mPersonalInsuranceDetailArrayList.add(mModel)
                                }
                            }
                        } else {
                            personalInsDetailLinear.visibility = View.GONE
                        }


                        println("working 12" + mVisaPermitDetailArrayList.size)
                        val mContactDetails: ContactDetails = responsedata!!.response.contact_details
                        val ownDetailArray = mContactDetails.other_contact
                        for (j in 0 until ownDetailArray.size) {
                            if (ownDetailArray.size > 0) {
                                val ownDetailObj = ownDetailArray[j]
                                val mOwnDetailsModel = OwnDetailsModel()
                                mOwnDetailsModel.email=ownDetailObj.email
                                mOwnDetailsModel.fullName=ownDetailObj.fullName
                                mOwnDetailsModel.contactNo=ownDetailObj.contactNo
                                mOwnDetailsModel.relationShip=ownDetailObj.relationShip
                                mOwnDetailsModelList.add(mOwnDetailsModel)
                            }
                        }
                       if( mContactDetails.next_of_kin_contact.isNullOrEmpty())
                       {

                       }else
                       {
                           val nextOfKinArray = mContactDetails.next_of_kin_contact
                           for (j in 0 until nextOfKinArray.size) {
                               if (nextOfKinArray.size > 0) {
                                   val kinDetailObj = nextOfKinArray[j]
                                   val mKinDetailModel = KinDetailsModel()
                                   mKinDetailModel.emial=kinDetailObj.emial
                                   mKinDetailModel.fullName=kinDetailObj.fullName
                                   mKinDetailModel.contactNumber=kinDetailObj.contactNumber
                                   mKinDetailModel.relationShip=kinDetailObj.relationShip

                                   mKinDetailsModelList.add(mKinDetailModel)
                               }
                           }
                       }



                        val localEmergencyArray =
                            mContactDetails.local_emergency_contact
                        for (j in 0 until localEmergencyArray.size) {
                            if (localEmergencyArray.size > 0) {
                                val localEmergencyDetailObj = localEmergencyArray[j]
                                val mLocalEmergencyModel = LocalEmergencyModel()
                                mLocalEmergencyModel.emial=localEmergencyDetailObj.emial
                                mLocalEmergencyModel.fullName=localEmergencyDetailObj.fullName
                                mLocalEmergencyModel.contactNumber=localEmergencyDetailObj.contactNumber
                                mLocalEmergencyModel.relationShip=localEmergencyDetailObj.relationShip

                                mLocalEmergencyModelList.add(mLocalEmergencyModel)
                            }
                        }
                        println("working 12" + mStudentDetailModelList.size)
                        if (mStudentDetailModelList.size > 0) {
                            println("working 12++")
                            val studentDetailRecyclerAdapter =
                                StudentDetailRecyclerAdapter(mContext, mStudentDetailModelList)
                            studentDetail.adapter = studentDetailRecyclerAdapter
                            studentDetail.visibility = View.VISIBLE
                            println("working 13")
                        } else {
                            studentDetail.visibility = View.GONE
                        }
                        if (mPassportDetailArrayList.size > 0) {
                            println("working passport 12++")
                            val studentDetailRecyclerAdapter =
                                PassportDetailRecyclerAdapter(mContext, mPassportDetailArrayList)
                            passportDetailRecycler.adapter = studentDetailRecyclerAdapter
                            passportDetailRecycler.visibility = View.VISIBLE
                            passportDetailLinear.visibility = View.VISIBLE
                            println("working passport 13")
                        } else {
                            passportDetailRecycler.visibility = View.GONE
                            passportDetailLinear.visibility = View.GONE
                        }
                        if (mVisaPermitDetailArrayList.size > 0) {
                            println("working passport 12++")
                            val studentDetailRecyclerAdapter =
                                PassportDetailRecyclerAdapter(mContext, mVisaPermitDetailArrayList)
                            visaPermitDetailRecycler.adapter = studentDetailRecyclerAdapter
                            visaPermitDetailRecycler.visibility = View.VISIBLE
                            visaPermitDetailLinear.visibility = View.VISIBLE
                            println("working passport 13")
                        } else {
                            visaPermitDetailRecycler.visibility = View.GONE
                            visaPermitDetailLinear.visibility = View.GONE
                        }
                        if (mMedicalInsuranceDetailArrayList.size > 0) {
                            println("working passport 12++")
                            val studentDetailRecyclerAdapter = PassportDetailRecyclerAdapter(
                                mContext,
                                mMedicalInsuranceDetailArrayList
                            )
                            medicalInsDetailRecycler.adapter = studentDetailRecyclerAdapter
                            medicalInsDetailRecycler.visibility = View.VISIBLE
                            medicalInsDetailLinear.visibility = View.VISIBLE
                            println("working passport 13")
                        } else {
                            medicalInsDetailRecycler.visibility = View.GONE
                            medicalInsDetailLinear.visibility = View.GONE
                        }
                        if (mPersonalInsuranceDetailArrayList.size > 0) {
                            println("working passport 12++")
                            val studentDetailRecyclerAdapter = PassportDetailRecyclerAdapter(
                                mContext,
                                mPersonalInsuranceDetailArrayList
                            )
                            personalInsDetailRecycler.adapter = studentDetailRecyclerAdapter
                            personalInsDetailRecycler.visibility = View.VISIBLE
                            personalInsDetailLinear.visibility = View.VISIBLE
                            println("working passport 13")
                        } else {
                            personalInsDetailRecycler.visibility = View.GONE
                            personalInsDetailLinear.visibility = View.GONE
                        }
                        if (mOwnDetailsModelList.size > 0) {
                            val relationRecyclerAdapter =
                                RelationRecyclerAdapter(mContext, mOwnDetailsModelList)
                            studentRelationship.adapter = relationRecyclerAdapter
                            otherContactLinear.visibility = View.VISIBLE
                        } else {
                            otherContactLinear.visibility = View.GONE
                        }

                        if (mKinDetailsModelList.size > 0) {
                            val nextOfKinRecyclerAdapter =
                                NextOfKinRecyclerAdapter(mContext, mKinDetailsModelList)
                            nextOfKinRecycler.adapter = nextOfKinRecyclerAdapter
                            nextOfKinLinear.visibility = View.VISIBLE
                            nextOfKinRecycler.visibility = View.VISIBLE
                            noNxtOfKin.visibility = View.GONE
                        } else {
                            nextOfKinLinear.visibility = View.GONE
                            nextOfKinRecycler.visibility = View.GONE
                            noNxtOfKin.visibility = View.GONE
                        }


                        if (mLocalEmergencyModelList.size > 0) {
                            val localEmergencyRecyclerAdapter =
                                LocalEmergencyRecyclerAdapter(mContext, mLocalEmergencyModelList)
                            localEmergencyRecycler.adapter = localEmergencyRecyclerAdapter
                            localEmergencyLinear.visibility = View.VISIBLE
                            localEmergencyRecycler.visibility = View.VISIBLE
                            noLocalEmergency.visibility = View.GONE
                        } else {
                            localEmergencyLinear.visibility = View.VISIBLE
                            localEmergencyRecycler.visibility = View.GONE
                            noLocalEmergency.visibility = View.VISIBLE
                        }
                        infoTxt.visibility = View.VISIBLE
                        if (studPhoto.equals("")) {
                        } else {
//                                Glide.with(mContext).load(AppUtils.replace(studPhoto)).centerCrop().into(iconImgProfile);
                            Glide.with(mContext).load(AppUtils().replace(studPhoto))
                                .placeholder(R.drawable.boy).into(iconImgProfile)
                        }

                    }
                }

            }
        })
    }

    private fun initialiseUI() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_view_home)
        supportActionBar!!.elevation = 0f
        val view = supportActionBar!!.customView
        val toolbar = view.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)
        val headerTitle = view.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = view.findViewById<ImageView>(R.id.logoClickImgView)
        val action_bar_forward = view.findViewById<ImageView>(R.id.action_bar_forward)
        val action_bar_back = view.findViewById<ImageView>(R.id.action_bar_back)
        action_bar_back.setImageResource(R.drawable.back_new)
        headerTitle.text = "Student Information"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        action_bar_forward.visibility = View.INVISIBLE

        action_bar_back.setOnClickListener { onBackPressed() }
        mStudentDetailModelList = java.util.ArrayList()
        mMedicalInsuranceDetailArrayList = java.util.ArrayList()
        mPersonalInsuranceDetailArrayList = java.util.ArrayList()
        mVisaPermitDetailArrayList = java.util.ArrayList()
        mPassportDetailArrayList = java.util.ArrayList()
        mRelationshipModelList = java.util.ArrayList()
        mKinDetailsModelList = java.util.ArrayList()
        mOwnDetailsModelList = java.util.ArrayList()
        mLocalEmergencyModelList = java.util.ArrayList()
        medicalInsDetailLinear = findViewById(R.id.medicalInsDetailLinear)
        personalInsDetailLinear = findViewById(R.id.personalInsDetailLinear)
        otherContactLinear = findViewById(R.id.otherContactLinear)
        nextOfKinLinear = findViewById(R.id.nextOfKinLinear)
        localEmergencyLinear = findViewById(R.id.localEmergencyLinear)
        iconImgProfile = findViewById(R.id.iconImg)
        noNxtOfKin = findViewById(R.id.noNxtOfKin)
        noLocalEmergency = findViewById(R.id.noLocalEmergency)
        visaPermitDetailLinear = findViewById(R.id.visaPermitDetailLinear)
        infoTxt = findViewById(R.id.infoTxt)
        noLocalEmergency.visibility = View.GONE
        infoTxt.visibility = View.GONE
        noNxtOfKin.visibility = View.GONE

        studentDetail = findViewById(R.id.studentDetail)
        passportDetailLinear = findViewById(R.id.passportDetailLinear)
        studentDetail.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentDetail.layoutManager = llm
        passportDetailRecycler = findViewById(R.id.passportDetailRecycler)
        passportDetailRecycler.setHasFixedSize(true)
        val llp = LinearLayoutManager(this)
        llp.orientation = LinearLayoutManager.VERTICAL
        passportDetailRecycler.layoutManager = llp
        visaPermitDetailRecycler = findViewById(R.id.visaPermitDetailRecycler)
        visaPermitDetailRecycler.setHasFixedSize(true)
        val llvp = LinearLayoutManager(this)
        llvp.orientation = LinearLayoutManager.VERTICAL
        visaPermitDetailRecycler.layoutManager = llvp
        medicalInsDetailRecycler = findViewById(R.id.medicalInsDetailRecycler)
        medicalInsDetailRecycler.setHasFixedSize(true)
        val llmi = LinearLayoutManager(this)
        llmi.orientation = LinearLayoutManager.VERTICAL
        medicalInsDetailRecycler.layoutManager = llmi
        personalInsDetailRecycler = findViewById(R.id.personalInsDetailRecycler)
        personalInsDetailRecycler.setHasFixedSize(true)
        val llpi = LinearLayoutManager(this)
        llpi.orientation = LinearLayoutManager.VERTICAL
        personalInsDetailRecycler.layoutManager = llpi


        studentRelationship = findViewById(R.id.studentRelationship)
        localEmergencyRecycler = findViewById(R.id.localEmergencyRecycler)
        nextOfKinRecycler = findViewById(R.id.nextOfKinRecycler)
        //other contact recycler
        //other contact recycler
        studentRelationship.setHasFixedSize(true)
        val llmstudentRelationship = LinearLayoutManager(this)
        llmstudentRelationship.orientation = LinearLayoutManager.VERTICAL
        studentRelationship.layoutManager = llmstudentRelationship

        //next of kin recycler


        //next of kin recycler
        nextOfKinRecycler.setHasFixedSize(true)
        val llmnextOfKinRecycler = LinearLayoutManager(this)
        llmnextOfKinRecycler.orientation = LinearLayoutManager.VERTICAL
        nextOfKinRecycler.layoutManager = llmnextOfKinRecycler

        //Local emergency recycler


        //Local emergency recycler
        localEmergencyRecycler.setHasFixedSize(true)
        val llmlocalEmergencyRecycler = LinearLayoutManager(this)
        llmlocalEmergencyRecycler.orientation = LinearLayoutManager.VERTICAL
        localEmergencyRecycler.layoutManager = llmlocalEmergencyRecycler
    }

}
