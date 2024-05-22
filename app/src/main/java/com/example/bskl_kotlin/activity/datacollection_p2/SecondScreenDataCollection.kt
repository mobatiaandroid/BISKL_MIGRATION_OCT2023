package com.example.bskl_kotlin.activity.datacollection_p2

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.datacollection_p2.model.InsuranceDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.StudentModelNew
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.home.mContext
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.CommonClass
import org.json.JSONArray

class SecondScreenDataCollection:FragmentActivity(), ViewPager.OnPageChangeListener,
    RadioGroup.OnCheckedChangeListener {
    lateinit var mContext: Context
    private var radioGroup: RadioGroup? = null
    var pager: ViewPager? = null
    private lateinit var nextBtn: ImageView
    private lateinit var backBtn:ImageView
    private lateinit var submitBtn: TextView
    private val OverallValue: String? = null

    var closeMsgKin: String? = null
    var closeMsgLocal: String? = null
    var own_details: JSONArray? = null
    var kin_details: JSONArray? = null
    var emergency_details: JSONArray? = null
    lateinit var bottomLinear: LinearLayout
    var studentId = ""
    var studentImage = ""
    var studentName = ""
    var extras: Bundle? = null
    var radioButton1: RadioButton? =
        null
    var radioButton2:RadioButton? = null
    var radioButton3:RadioButton? = null
    var mInsuranceDetailArrayList: ArrayList<InsuranceDetailModel>? = null

    /**
     * {@inheritDoc}
     */
    var previousPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_collection_home)

        mContext = this
        extras = intent.extras
        if (extras != null) {
            studentId = extras!!.getString("studentId")!!
            studentName = extras!!.getString("studentName")!!
            studentImage = extras!!.getString("studentImage")!!
            Log.e("studentId",studentId)
            Log.e("studentName",studentName)
            Log.e("studentImage",studentImage)

        }
        pager = findViewById(R.id.viewPager)
        bottomLinear = findViewById(R.id.bottomLinear)
        pager!!.setAdapter(SecondScreenDataCollection.MyPagerAdapter(supportFragmentManager,studentImage,studentId,studentName))
        pager!!.addOnPageChangeListener(this)
        pager!!.setOffscreenPageLimit(4)
        radioGroup = findViewById(R.id.radiogroup)
        radioButton1 = findViewById(R.id.radioButton1)
        radioButton2 = findViewById(R.id.radioButton2)
        radioButton3 = findViewById(R.id.radioButton3)
        submitBtn = findViewById(R.id.submit)
        nextBtn = findViewById(R.id.nextImg)
        backBtn = findViewById(R.id.backImg)
        radioGroup!!.setOnCheckedChangeListener(this)
        if (PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("1") || PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("6")
        ) {
            bottomLinear.setVisibility(View.VISIBLE)
            radioButton1!!.setVisibility(View.VISIBLE)
            radioButton2!!.setVisibility(View.VISIBLE)
            radioButton3!!.setVisibility(View.GONE)
            nextBtn.setVisibility(View.VISIBLE)
            backBtn.setVisibility(View.INVISIBLE)
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("5") || PreferenceManager().getDataCollectionTriggerType(mContext)
                .equals("7")
        ) {
//            bottomLinear.setVisibility(View.VISIBLE);
//            radioButton1.setVisibility(View.GONE);
//            radioButton2.setVisibility(View.VISIBLE);
//            radioButton3.setVisibility(View.GONE);
            bottomLinear.setVisibility(View.VISIBLE)
            radioButton1!!.setVisibility(View.INVISIBLE)
            radioButton2!!.setVisibility(View.INVISIBLE)
            radioButton3!!.setVisibility(View.INVISIBLE)
            submitBtn.setVisibility(View.VISIBLE)
            submitBtn.setText("Confirm")
            submitBtn.setBackgroundResource(R.drawable.rect_confirm_blue_button)
            nextBtn.setVisibility(View.INVISIBLE)
            backBtn.setVisibility(View.INVISIBLE)
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("3")) {
            bottomLinear.setVisibility(View.VISIBLE)
            radioButton1!!.setVisibility(View.INVISIBLE)
            radioButton2!!.setVisibility(View.INVISIBLE)
            radioButton3!!.setVisibility(View.INVISIBLE)
            submitBtn.setVisibility(View.VISIBLE)
            submitBtn.setText("Confirm")
            submitBtn.setBackgroundResource(R.drawable.rect_confirm_blue_button)
            nextBtn.setVisibility(View.INVISIBLE)
            backBtn.setVisibility(View.INVISIBLE)
        } else {
            bottomLinear.setVisibility(View.VISIBLE)
            radioButton1!!.setVisibility(View.GONE)
            radioButton2!!.setVisibility(View.GONE)
            radioButton3!!.setVisibility(View.GONE)
            submitBtn.setVisibility(View.VISIBLE)
            submitBtn.setText("Confirm")
            submitBtn.setBackgroundResource(R.drawable.rect_confirm_blue_button)
            nextBtn.setVisibility(View.INVISIBLE)
            backBtn.setVisibility(View.INVISIBLE)
        }
        closeMsgKin =
            "Next of Kin must not be existing parent contact for family (cross check email/phone number with mother/father/parent record)"
        closeMsgLocal =
            "Local Emergency Contact must not be existing parent contact for family (cross check email/phone number with mother/father/parent record)"
        val editor = getSharedPreferences("BSKL", MODE_PRIVATE).edit()
        editor.putString("validationkin", "0")
        editor.putString("validationlocal", "0")
        editor.apply()
        nextBtn.setOnClickListener {
            pager!!.currentItem = pager!!.currentItem + 1
        }
        backBtn.setOnClickListener {
            pager!!.currentItem = pager!!.currentItem - 1
        }

        previousPage = 1
     submitBtn.setOnClickListener {
         if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("1")) {
             if (emptyValueCheckInsurance().equals("", ignoreCase = true)) {
                 println("Insrance empty works")
                 if (emptyValueCheckPassport().equals("", ignoreCase = true)) {
                     println("Insrance empty works passport")
                     var studentPos = -1
                     var id = ""
                     var isams_id = ""
                     var studName = ""
                     var studClass = ""
                     var studSection = ""
                     var studHouse = ""
                     var studPhoto = ""
                     var studProgressReport = ""
                     var studAlumini = ""
                     val studentID: String =CommonClass.mPassportDetailArrayList.get(CommonClass.confirmingPosition).student_id.toString()
                         //CommonClass.mPassportDetailArrayList.get(CommonClass.confirmingPosition)
                             //.student_id.toString()
                     Log.e("student_id",studentId)
                     if (CommonClass.mStudentDataArrayList.size > 0) {
                         for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                             if (CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                     .equals(studentId)) {
                                 studentPos = i
                                 id = CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                 isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                                 studName = CommonClass.mStudentDataArrayList.get(i).mName.toString()
                                 studClass = CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                                 studSection =
                                     CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                                 studHouse = CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                                 studPhoto = CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                                 studProgressReport =
                                     CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                                 studAlumini =
                                     CommonClass.mStudentDataArrayList.get(i).alumini.toString()
                             }
                         }
                     }
                     val model = StudentModelNew()
                     model.mId=id
                     model.mIsams_id=isams_id
                     model.mName=studName
                     model.mClass=studClass
                     model.mSection=studSection
                     model.mHouse=studHouse
                     model.mPhoto=studPhoto
                     model.progressReport=studProgressReport
                     model.alumini=studAlumini
                     model.isConfirmed=true
                     CommonClass.mStudentDataArrayList.removeAt(studentPos)
                     CommonClass.mStudentDataArrayList.add(studentPos, model)
                    /* CommonClass.isInsuranceEdited = false
                     CommonClass.isPassportEdited = false
                     CommonClass.confirmingPosition = -1*/
                     CommonClass.isInsuranceEdited = false
                     CommonClass.isPassportEdited = false
                     CommonClass.confirmingPosition = -1
                     PreferenceManager().getInsuranceStudentList(mContext).clear()
                     PreferenceManager().saveInsuranceStudentList(
                         CommonClass.mStudentDataArrayList,
                         mContext
                     )
                     PreferenceManager().saveInsuranceDetailArrayList(
                         CommonClass.mInsuranceDetailArrayList,
                         mContext
                     )
                     PreferenceManager().savePassportDetailArrayList(
                         CommonClass.mPassportDetailArrayList,
                         mContext
                     )
                     finish()
                     // Toast.makeText(mContext,"Data Saved Successfully",Toast.LENGTH_SHORT).show();
                 } else {
                     val emptyFeild: String = emptyValueCheckPassport()
                     ShowCondition(emptyFeild, "1")
                 }
             } else {
                 val emptyFeild: String = emptyValueCheckInsurance()
                 println("Empty value check$emptyFeild")
                 ShowCondition(emptyFeild, "0")
             }
             //emptyValueCheck();
         } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                 .equals("3")
         ) {
             if (emptyValueCheckInsurance().equals("", ignoreCase = true)) {
                 var studentPos = -1
                 var id = ""
                 var isams_id =""
                 var studName = ""
                 var studClass = ""
                 var studSection = ""
                 var studHouse = ""
                 var studPhoto = ""
                 var studProgressReport = ""
                 var studAlumini = ""
                 val studentID: String =
                     CommonClass.mInsuranceDetailArrayList.get(CommonClass.confirmingPosition)
                         .student_id.toString()
                 if (CommonClass.mStudentDataArrayList.size > 0) {
                     for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                         if (CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                 .equals(studentID)) {
                             studentPos = i
                             id = CommonClass.mStudentDataArrayList.get(i).mId.toString()
                             isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                             studName = CommonClass.mStudentDataArrayList.get(i).mName.toString()
                             studClass = CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                             studSection = CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                             studHouse = CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                             studPhoto = CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                             studProgressReport =
                                 CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                             studAlumini = CommonClass.mStudentDataArrayList.get(i).alumini.toString()
                         }
                     }
                 }
                 val model = StudentModelNew()
                 model.mId=id
                 model.mIsams_id=isams_id
                 model.mName=studName
                 model.mClass=studClass
                 model.mSection=studSection
                 model.mHouse=studHouse
                 model.mPhoto=studPhoto
                 model.progressReport=studProgressReport
                 model.alumini=studAlumini
                 model.isConfirmed=true
                 CommonClass.mStudentDataArrayList.removeAt(studentPos)
                 CommonClass.mStudentDataArrayList.add(studentPos, model)
                 CommonClass.isInsuranceEdited = false
                 CommonClass.isPassportEdited = false
                 CommonClass.confirmingPosition = -1
                 PreferenceManager().getInsuranceStudentList(mContext).clear()
                 PreferenceManager().saveInsuranceStudentList(
                     CommonClass.mStudentDataArrayList,
                     mContext
                 )
                 PreferenceManager().saveInsuranceDetailArrayList(
                     CommonClass.mInsuranceDetailArrayList,
                     mContext
                 )
                 // PreferenceManager().savePassportDetailArrayList(CommonClass.mPassportDetailArrayList,mContext);
                 finish()
                 // Toast.makeText(mContext,"Data Saved Successfully",Toast.LENGTH_SHORT).show();
             } else {
                 val emptyFeild: String = emptyValueCheckInsurance()
                 println("Empty value check$emptyFeild")
                 ShowCondition(emptyFeild, "0")
             }
         } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                 .equals("4")
         ) {
             if (emptyValueCheckPassport().equals("", ignoreCase = true)) {
                 var studentPos = -1
                 var id = ""
                 var isams_id =""
                 var studName = ""
                 var studClass = ""
                 var studSection = ""
                 var studHouse = ""
                 var studPhoto = ""
                 var studProgressReport = ""
                 var studAlumini = ""
                 val studentID: String =
                     CommonClass.mPassportDetailArrayList.get(CommonClass.confirmingPosition)
                         .student_id.toString()
                 if (CommonClass.mStudentDataArrayList.size > 0) {
                     for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                         if (CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                 .equals(studentID)
                         ) {
                             studentPos = i
                             id = CommonClass.mStudentDataArrayList.get(i).mId.toString()
                             isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                             studName = CommonClass.mStudentDataArrayList.get(i).mName.toString()
                             studClass = CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                             studSection = CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                             studHouse = CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                             studPhoto = CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                             studProgressReport =
                                 CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                             studAlumini = CommonClass.mStudentDataArrayList.get(i).alumini.toString()
                         }
                     }
                 }
                 val model = StudentModelNew()
                 model.mId=id
                 model.mIsams_id=isams_id
                 model.mName=studName
                 model.mClass=studClass
                 model.mSection=studSection
                 model.mHouse=studHouse
                 model.mPhoto=studPhoto
                 model.progressReport=studProgressReport
                 model.alumini=studAlumini
                 model.isConfirmed=true
                 CommonClass.mStudentDataArrayList.removeAt(studentPos)
                 CommonClass.mStudentDataArrayList.add(studentPos, model)
                 CommonClass.isInsuranceEdited = false
                 CommonClass.isPassportEdited = false
                 CommonClass.confirmingPosition = -1
                 PreferenceManager().getInsuranceStudentList(mContext).clear()
                 PreferenceManager().saveInsuranceStudentList(
                     CommonClass.mStudentDataArrayList,
                     mContext
                 )
                 // PreferenceManager().saveInsuranceDetailArrayList(CommonClass.mInsuranceDetailArrayList,mContext);
                 PreferenceManager().savePassportDetailArrayList(
                     CommonClass.mPassportDetailArrayList,
                     mContext
                 )
                 finish()
                 //Toast.makeText(mContext,"Data Saved Successfully",Toast.LENGTH_SHORT).show();
             } else {
                 val emptyFeild: String = emptyValueCheckPassport()
                 ShowCondition(emptyFeild, "1")
             }
         } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                 .equals("5")
         ) {
             if (emptyValueCheckInsurance().equals("", ignoreCase = true)) {
                 var studentPos = -1
                 var id = ""
                 var isams_id =""
                 var studName = ""
                 var studClass = ""
                 var studSection = ""
                 var studHouse = ""
                 var studPhoto = ""
                 var studProgressReport = ""
                 var studAlumini = ""
                 val studentID: String =
                     CommonClass.mInsuranceDetailArrayList.get(CommonClass.confirmingPosition)
                         .student_id.toString()
                 if (CommonClass.mStudentDataArrayList.size > 0) {
                     for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                         if (CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                 .equals(studentID)) {
                             studentPos = i
                             id = CommonClass.mStudentDataArrayList.get(i).mId.toString()
                             isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                             studName = CommonClass.mStudentDataArrayList.get(i).mName.toString()
                             studClass = CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                             studSection = CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                             studHouse = CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                             studPhoto = CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                             studProgressReport =
                                 CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                             studAlumini = CommonClass.mStudentDataArrayList.get(i).alumini.toString()
                         }
                     }
                 }
                 val model = StudentModelNew()
                 model.mId=id
                 model.mIsams_id=isams_id
                 model.mName=studName
                 model.mClass=studClass
                 model.mSection=studSection
                 model.mHouse=studHouse
                 model.mPhoto=studPhoto
                 model.progressReport=studProgressReport
                 model.alumini=studAlumini
                 model.isConfirmed=true
                 CommonClass.mStudentDataArrayList.removeAt(studentPos)
                 CommonClass.mStudentDataArrayList.add(studentPos, model)
                 CommonClass.isInsuranceEdited = false
                 CommonClass.isPassportEdited = false
                 CommonClass.confirmingPosition = -1
                 PreferenceManager().getInsuranceStudentList(mContext).clear()
                 PreferenceManager().saveInsuranceStudentList(
                     CommonClass.mStudentDataArrayList,
                     mContext
                 )
                 PreferenceManager().saveInsuranceDetailArrayList(
                     CommonClass.mInsuranceDetailArrayList,
                     mContext
                 )
                 // PreferenceManager().savePassportDetailArrayList(CommonClass.mPassportDetailArrayList,mContext);
                 finish()
                 //   Toast.makeText(mContext,"Data Saved Successfully",Toast.LENGTH_SHORT).show();
             } else {
                 val emptyFeild: String = emptyValueCheckInsurance()
                 println("Empty value check$emptyFeild")
                 ShowCondition(emptyFeild, "0")
             }
         } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                 .equals("6")
         ) {
             if (emptyValueCheckInsurance().equals("", ignoreCase = true)) {
                 if (emptyValueCheckPassport().equals("", ignoreCase = true)) {
                     var studentPos = -1
                     var id = ""
                     var isams_id =""
                     var studName = ""
                     var studClass = ""
                     var studSection = ""
                     var studHouse = ""
                     var studPhoto = ""
                     var studProgressReport = ""
                     var studAlumini = ""
                     val studentID: String =
                         CommonClass.mPassportDetailArrayList.get(CommonClass.confirmingPosition)
                             .student_id.toString()
                     if (CommonClass.mStudentDataArrayList.size > 0) {
                         for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                             if (CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                     .equals(studentID)
                             ) {
                                 studentPos = i
                                 id = CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                 isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                                 studName = CommonClass.mStudentDataArrayList.get(i).mName.toString()
                                 studClass = CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                                 studSection =
                                     CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                                 studHouse = CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                                 studPhoto = CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                                 studProgressReport =
                                     CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                                 studAlumini =
                                     CommonClass.mStudentDataArrayList.get(i).alumini.toString()
                             }
                         }
                     }
                     val model = StudentModelNew()
                     model.mId=id
                     model.mIsams_id=isams_id
                     model.mName=studName
                     model.mClass=studClass
                     model.mSection=studSection
                     model.mHouse=studHouse
                     model.mPhoto=studPhoto
                     model.progressReport=studProgressReport
                     model.alumini=studAlumini
                     model.isConfirmed=true
                     CommonClass.mStudentDataArrayList.removeAt(studentPos)
                     CommonClass.mStudentDataArrayList.add(studentPos, model)
                     CommonClass.isInsuranceEdited = false
                     CommonClass.isPassportEdited = false
                     CommonClass.confirmingPosition = -1
                     PreferenceManager().getInsuranceStudentList(mContext).clear()
                     PreferenceManager().saveInsuranceStudentList(
                         CommonClass.mStudentDataArrayList,
                         mContext
                     )
                     PreferenceManager().saveInsuranceDetailArrayList(
                         CommonClass.mInsuranceDetailArrayList,
                         mContext
                     )
                     PreferenceManager().savePassportDetailArrayList(
                         CommonClass.mPassportDetailArrayList,
                         mContext
                     )
                     finish()
                     //  Toast.makeText(mContext,"Data Saved Successfully",Toast.LENGTH_SHORT).show();
                 } else {
                     val emptyFeild: String = emptyValueCheckPassport()
                     ShowCondition(emptyFeild, "1")
                 }
             } else {
                 val emptyFeild: String = emptyValueCheckInsurance()
                 println("Empty value check$emptyFeild")
                 ShowCondition(emptyFeild, "0")
             }
         } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                 .equals("7")
         ) {
             if (emptyValueCheckPassport().equals("", ignoreCase = true)) {
                 var studentPos = -1
                 var id = ""
                 var isams_id = ""
                 var studName = ""
                 var studClass = ""
                 var studSection = ""
                 var studHouse = ""
                 var studPhoto = ""
                 var studProgressReport = ""
                 var studAlumini = ""
                 val studentID: String =
                     CommonClass.mPassportDetailArrayList.get(CommonClass.confirmingPosition)
                         .student_id.toString()
                 if (CommonClass.mStudentDataArrayList.size > 0) {
                     for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                         if (CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                 .equals(studentID)
                         ) {
                             studentPos = i
                             id = CommonClass.mStudentDataArrayList.get(i).mId.toString()
                             studName = CommonClass.mStudentDataArrayList.get(i).mName.toString()
                             isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                             studClass = CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                             studSection = CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                             studHouse = CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                             studPhoto = CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                             studProgressReport =
                                 CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                             studAlumini = CommonClass.mStudentDataArrayList.get(i).alumini.toString()
                         }
                     }
                 }
                 val model = StudentModelNew()
                 model.mId=id
                 model.mIsams_id=isams_id
                 model.mName=studName
                 model.mClass=studClass
                 model.mSection=studSection
                 model.mHouse=studHouse
                 model.mPhoto=studPhoto
                 model.progressReport=studProgressReport
                 model.alumini=studAlumini
                 model.isConfirmed=true
                 CommonClass.mStudentDataArrayList.removeAt(studentPos)
                 CommonClass.mStudentDataArrayList.add(studentPos, model)
                 CommonClass.isInsuranceEdited = false
                 CommonClass.isPassportEdited = false
                 CommonClass.confirmingPosition = -1
                 PreferenceManager().getInsuranceStudentList(mContext).clear()
                 PreferenceManager().saveInsuranceStudentList(
                     CommonClass.mStudentDataArrayList,
                     mContext
                 )
                 // PreferenceManager().saveInsuranceDetailArrayList(CommonClass.mInsuranceDetailArrayList,mContext);
                 PreferenceManager().savePassportDetailArrayList(
                     CommonClass.mPassportDetailArrayList,
                     mContext
                 )
                 finish()
                 // Toast.makeText(mContext,"Data Saved Successfully",Toast.LENGTH_SHORT).show();
             } else {
                 val emptyFeild: String = emptyValueCheckPassport()
                 ShowCondition(emptyFeild, "1")
             }
         }
     }
        
    }
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("1")) {
            bottomLinear.visibility = View.VISIBLE
            radioButton3!!.visibility = View.VISIBLE
            when (position) {
                0 -> {
                    radioButton1!!.visibility = View.VISIBLE
                    radioButton2!!.visibility = View.VISIBLE
                    radioButton3!!.visibility = View.GONE
                    radioGroup!!.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.INVISIBLE
                }

                1 -> {
                    radioGroup!!.check(R.id.radioButton2)
                    nextBtn.visibility = View.INVISIBLE
                    backBtn.visibility = View.VISIBLE
                    radioButton1!!.visibility = View.VISIBLE
                    radioButton2!!.visibility = View.VISIBLE
                    radioButton3!!.visibility = View.GONE
                    submitBtn.visibility = View.VISIBLE
                    submitBtn.text = "Confirm"
                    submitBtn.setBackgroundResource(R.drawable.rect_confirm_blue_button)
                }

                else -> {
                    radioGroup!!.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.INVISIBLE
                }
            }
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("2")) {
            bottomLinear.visibility = View.GONE
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("3")) {
            bottomLinear.visibility = View.GONE
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("4")) {
            bottomLinear.visibility = View.GONE
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("5")) {
            bottomLinear.visibility = View.VISIBLE
            radioButton3!!.visibility = View.GONE
            when (position) {
                0 -> {
                    radioGroup!!.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.INVISIBLE
                }

                1 -> {
                    radioGroup!!.check(R.id.radioButton2)
                    nextBtn.visibility = View.INVISIBLE
                    backBtn.visibility = View.VISIBLE
                    submitBtn.visibility = View.VISIBLE
                }

                else -> {
                    radioGroup!!.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.INVISIBLE
                }
            }
        } else if (PreferenceManager().getDataCollectionTriggerType(mContext).equals("6")) {
            bottomLinear.visibility = View.VISIBLE
            radioButton3!!.visibility = View.GONE
            when (position) {
                0 -> {
                    radioButton1!!.visibility = View.VISIBLE
                    radioButton2!!.visibility = View.VISIBLE
                    radioButton3!!.visibility = View.GONE
                    radioGroup!!.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.INVISIBLE
                }

                1 -> {
                    radioGroup!!.check(R.id.radioButton2)
                    nextBtn.visibility = View.INVISIBLE
                    backBtn.visibility = View.VISIBLE
                    radioButton1!!.visibility = View.VISIBLE
                    radioButton2!!.visibility = View.VISIBLE
                    radioButton3!!.visibility = View.GONE
                    submitBtn.visibility = View.VISIBLE
                    submitBtn.text = "Confirm"
                    submitBtn.setBackgroundResource(R.drawable.rect_confirm_blue_button)
                }

                else -> {
                    radioGroup!!.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.INVISIBLE
                }
            }
        } else {
            bottomLinear.visibility = View.VISIBLE
            radioButton3!!.visibility = View.GONE
            when (position) {
                0 -> {
                    radioGroup!!.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.INVISIBLE
                }

                1 -> {
                    radioGroup!!.check(R.id.radioButton2)
                    nextBtn.visibility = View.INVISIBLE
                    backBtn.visibility = View.VISIBLE
                    submitBtn.visibility = View.VISIBLE
                }

                else -> {
                    radioGroup!!.check(R.id.radioButton1)
                    nextBtn.visibility = View.VISIBLE
                    backBtn.visibility = View.INVISIBLE
                    submitBtn.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.radioButton1 -> pager!!.currentItem = 0
            R.id.radioButton2 -> pager!!.currentItem = 1
        }
    }
    private class MyPagerAdapter
    /**
     * Constructor
     *
     * @param fm
     */(fm: FragmentManager?, studentImage: String, studentId: String, studentName: String) : FragmentPagerAdapter(
        fm!!
    ) {
        var studimg=studentImage
        var studid=studentId
        var studname=studentName
        /**
         * Return fragment based on the position.
         *
         * @param position
         * @return
         */
        override fun getItem(position: Int): Fragment {
            Log.e("studid",studid)
            Log.e("studname",studname)

            return if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("1")
            ) {

                when (position) {
                    0 -> SecondScreenNewData(studimg, studid, studname)
                    1 -> ThirdScreenNewData(studimg, studid, studname)
                    else -> SecondScreenNewData(studimg, studid, studname)
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("3")
            ) {
                when (position) {
                    0 -> SecondScreenNewData(studimg, studid, studname)
                    else -> SecondScreenNewData(studimg, studid, studname)
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("4")
            ) {
                when (position) {
                    0 -> ThirdScreenNewData(studimg, studid, studname)
                    else -> ThirdScreenNewData(studimg, studid, studname)
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("5")
            ) {
                when (position) {
                    0 -> SecondScreenNewData(studimg, studid, studname)
                    else -> SecondScreenNewData(studimg, studid, studname)
                }
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("6")
            ) {
                when (position) {
                    0 -> SecondScreenNewData(studimg, studid, studname)
                    1 -> ThirdScreenNewData(studimg, studid, studname)
                    else -> SecondScreenNewData(studimg, studid, studname)
                }
            } else {
                when (position) {
                    0 -> ThirdScreenNewData(studimg, studid, studname)
                    else -> ThirdScreenNewData(studimg, studid, studname)
                }
            }
        }

        /**
         * Return the number of pages.
         *
         * @return
         */
        override fun getCount(): Int {
            return if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("1")
            ) {
                2
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("5") || PreferenceManager().getDataCollectionTriggerType(
                    mContext
                ).equals("7")
            ) {
                1
            } else if (PreferenceManager().getDataCollectionTriggerType(mContext)
                    .equals("6")
            ) {
                2
            } else {
                1
            }
        }
    }

    fun emptyValueCheckInsurance(): String {
        val pos: Int = CommonClass.confirmingPosition
        return ""
    }

    fun emptyValueCheckPassport(): String {
        val pos: Int = CommonClass.confirmingPosition
        var feild = ""
        println("Insrance empty works passport 101010dd")
        Log.e("not_have_a_valid_passport", PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
            .not_have_a_valid_passport!!
        )
        if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                .not_have_a_valid_passport.equals("1")
        ) {
            println("Insrance empty works passport 101010ff")
            if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos).visa
                    .equals("0")
            ) {
                println("Insrance empty works passport 101010")
                if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                        .visa_permit_no.equals("")
                ) {
                    feild = "Visa/Permit Number"
                } else {
                    if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                            .visa_expired.equals("1")
                    ) {
                        if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                                .isVisaDateChanged
                        ) {
                            if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                                    .visa_image.equals("")
                            ) {
                                feild = "Upload Visa Image"
                            }
                        } else {
                            feild = "Visa/Permit Expiry Date"
                        }
                    } else {
                        feild = ""
                    }
                }
            } else {
                feild = ""
            }
        } else {
            println("Insrance empty works passport 101010 hdd")
            if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos).nationality
                    .equals("")
            ) {
                feild = "Nationality"
            } else {
                if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                        .passport_number.equals("")
                ) {
                    feild = "Passport Number"
                } else {
                    if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                            .expiry_date.equals("")
                    ) {
                        feild = "Passport Expiry Date"
                    } else {
                        if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                                .passport_expired.equals("1")
                        ) {
                            if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                                    .isPassportDateChanged
                            ) {
                                if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                                        .passport_image.equals("")
                                ) {
                                    feild = "Upload Passport Image"
                                } else {
                                    //visa data
                                    if (PreferenceManager().getPassportDetailArrayList(mContext)
                                            .get(pos).visa.equals("0")
                                    ) {
                                        println("Insrance empty works passport 101010")
                                        if (PreferenceManager().getPassportDetailArrayList(mContext)
                                                .get(pos).visa_permit_no.equals("")
                                        ) {
                                            feild = "Visa/Permit Number"
                                        } else {
                                            if (PreferenceManager().getPassportDetailArrayList(
                                                    mContext
                                                ).get(pos).visa_expired.equals("1")
                                            ) {
                                                if (PreferenceManager().getPassportDetailArrayList(
                                                        mContext
                                                    ).get(pos).isVisaDateChanged
                                                ) {
                                                    if (PreferenceManager().getPassportDetailArrayList(
                                                            mContext
                                                        ).get(pos).visa_image
                                                            .equals("")
                                                    ) {
                                                        feild = "Upload Visa Image"
                                                    }
                                                } else {
                                                    feild = "Visa/Permit Expiry Date"
                                                }
                                            } else {
                                                feild = ""
                                            }
                                        }
                                    } else {
                                        feild = ""
                                    }
                                }
                            } else {
                                feild = "Passport Expiry Date"
                            }
                        } else {
                            //check visa
                            if (!PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                                    .visa.equals("1")
                            ) {
                                if (PreferenceManager().getPassportDetailArrayList(mContext).get(pos)
                                        .visa_permit_no.equals("")
                                ) {
                                    feild = "Visa/Permit Number"
                                } else {
                                    println("Insrance empty works passport 161616")
                                    if (PreferenceManager().getPassportDetailArrayList(mContext)
                                            .get(pos).visa_expired.equals("1")
                                    ) {
                                        println("Insrance empty works passport 17171717")
                                        if (PreferenceManager().getPassportDetailArrayList(mContext)
                                                .get(pos).isVisaDateChanged
                                        ) {
                                            println("Insrance empty works passport 181818")
                                            if (PreferenceManager().getPassportDetailArrayList(
                                                    mContext
                                                ).get(pos).visa_image.equals("")
                                            ) {
                                                println("Insrance empty works passport 19191919")
                                                feild = "Upload Visa Image"
                                            }
                                        } else {
                                            feild = "Visa/Permit Expiry Date"
                                        }
                                    } else {
                                        feild = ""
                                    }
                                }
                            } else {
                                feild = ""
                            }
                        }
                    }
                }
            }
        }
        return feild
    }

    private fun ShowCondition(whoValueEmpty: String, page: String) {
        Log.e("page",page)
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
        if (whoValueEmpty.equals("Passport Expiry Date", ignoreCase = true)) {
            text.text = "Kindly update the passport expiry date."
        } else if (whoValueEmpty.equals("Upload Passport Image", ignoreCase = true)) {
            text.text = "Kindly upload the passport image."
        } else if (whoValueEmpty.equals("Visa/Permit Expiry Date", ignoreCase = true)) {
            text.text = "Kindly update the Visa/Permit expiry date."
        } else if (whoValueEmpty.equals("Upload Visa Image", ignoreCase = true)) {
            text.text = "Kindly upload the Visa image"
        } else if (whoValueEmpty.equals("Please Tick the Agreement.", ignoreCase = true)) {
            text.text = "Please Tick the Agreement."
        } else {
            Log.e("Alert","ALert")
            text.text = "Please enter the $whoValueEmpty"
        }
        textHead.text = "Alert"
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.text = "Ok"
        dialogButton.setOnClickListener {
            dialog.dismiss()
            //                    PreferenceManager().setIsValueEmpty(mContext,"0");
            if (page.equals("0", ignoreCase = true)) {
                pager!!.setCurrentItem(0, true)
            } else {
                pager!!.setCurrentItem(1, true)
            }
        }
        dialog.show()
    }
    override fun onBackPressed() {
        finish()
    }
}
