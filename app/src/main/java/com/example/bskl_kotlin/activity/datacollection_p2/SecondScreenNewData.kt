package com.example.bskl_kotlin.activity.datacollection_p2
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.datacollection_p2.model.InsuranceDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.StudentModelNew
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.absence.model.StudentModel
import com.example.bskl_kotlin.fragment.attendance.adapter.SelectedListRecyclerAdapter.dateConversionMMM
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.AppUtils
import com.example.bskl_kotlin.manager.CommonClass
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SecondScreenNewData(studentImage: String, studentId: String, studentName: String) : Fragment() {
    lateinit var mContext: Context
    val myCalendar = Calendar.getInstance()
    var C_positoin = 0
    var HEALTH_ID = ""
    var DATE = ""
    var isMedicalInsuranceClicked = false
    var isPersonalInsuranceClicked = false
    lateinit var checknoMedicalInsuranceImg: ImageView
    lateinit var checkpersonalInsuranceImg:android.widget.ImageView
    var CloseIcon:android.widget.ImageView? = null
    var submitBtn: Button? = null
    var submitBtnasdas:Button? = null
    lateinit var insurancePolicyNumberTxt: EditText
    lateinit var insuranceMemberNumberTxt:android.widget.EditText
    lateinit var insuranceProviderTxt:android.widget.EditText
    lateinit var insuranceExpiryDate:android.widget.EditText
    lateinit var personalInsuranceNumberTxt: EditText
    lateinit var personalInsuranceProviderTxt:android.widget.EditText
    lateinit var personalInsuranceExpiryDateTxt:android.widget.EditText
    lateinit var preferredHsptlTxt:android.widget.EditText
    var STUDENT_SIZE = 0
    var STUDENT_CLICK = 0

    var studentsModelArrayList: ArrayList<StudentModel> = ArrayList<StudentModel>()
    var studentList = ArrayList<String>()
    lateinit var studentName: TextView
    var emptyTxt:android.widget.TextView? = null
    var stud_id = ""
    var studClass = ""
    var alumini = ""
    var progressreport = ""
    var stud_img = ""
    lateinit var studImg: ImageView
    var mStudentSpinner: LinearLayout? =
        null
    var insuranceLinear:LinearLayout? = null
    var SAVE_STATE = "0"
    var preferredHsptl = ""
    var mInsuranceDetailArrayList: ArrayList<InsuranceDetailModel>? = null
    var InsureArray: ArrayList<InsuranceDetailModel> = ArrayList<InsuranceDetailModel>()
    var SecondArray = ArrayList<String>()
    var HaveMedINS = "YES"
    var HavePerINS = "YES"
    var studentId = ""
    var studentImage = ""
    var studentNamePass = ""
    var medInsurancePolicyNo = ""
    var medInsuranceMemberNo = ""
    var medInsuranceProvider = ""
    var medInsuranceExpiryDate = ""
    var perAccidentInsPolicyNo = ""
    var perAccidentInsProvider = ""
    var perAccidentInsExpiryDate = ""
    var prefferedHsptl = ""
    var insuranceTypeData = "0"
    var dataPosition = 0
    var itemID = ""
    var isInsuranceDataFound = false
    var checkInsuranceDeclaration: ImageView? = null
    var isInsuranceDeclared = false
    var declaration = "0"
    var declarationValue = ""
    var healthNote = ""
    var MedicalNoteTxt: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_insurance_new_detail_data_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        //Medical Insurance
        checknoMedicalInsuranceImg = requireView().findViewById<ImageView>(R.id.checknoMedicalInsuranceImg)
        insurancePolicyNumberTxt = requireView().findViewById<EditText>(R.id.insurancePolicyNumberTxt)
        insuranceMemberNumberTxt = requireView().findViewById<EditText>(R.id.insuranceMemberNumberTxt)
        insuranceProviderTxt = requireView().findViewById<EditText>(R.id.insuranceProviderTxt)
        insuranceExpiryDate = requireView().findViewById<EditText>(R.id.insuranceExpiryDate)
        studentName = requireView().findViewById<TextView>(R.id.studentName)
        studImg = requireView().findViewById<ImageView>(R.id.imagicon)
        CloseIcon = requireView().findViewById<ImageView>(R.id.closeImg)
        submitBtnasdas = requireView().findViewById<Button>(R.id.submitBtnasdas)
        preferredHsptlTxt = requireView().findViewById<EditText>(R.id.preferredHsptlTxt)
        checkpersonalInsuranceImg = requireView().findViewById<ImageView>(R.id.checkpersonalInsuranceImg)
        personalInsuranceNumberTxt = requireView().findViewById<EditText>(R.id.personalInsuranceNumberTxt)
        personalInsuranceProviderTxt = requireView().findViewById<EditText>(R.id.personalInsuranceProviderTxt)
        personalInsuranceExpiryDateTxt =
            requireView().findViewById<EditText>(R.id.personalInsuranceExpiryDateTxt)
        mStudentSpinner = requireView().findViewById<LinearLayout>(R.id.studentSpinner)
        insuranceLinear = requireView().findViewById<LinearLayout>(R.id.insuranceLinear)
        MedicalNoteTxt = requireView().findViewById<TextView>(R.id.MedicalNoteTxt)
        emptyTxt = requireView().findViewById<TextView>(R.id.emptyTxt)
        submitBtn = requireView().findViewById<Button>(R.id.submitBtn)
        checkInsuranceDeclaration = requireView().findViewById<ImageView>(R.id.checkInsuranceDeclaration)
        preferredHsptl = preferredHsptlTxt!!.text.toString()
        if (studentImage != "") {
            Glide.with(mContext).load(AppUtils().replace(studentImage)).placeholder(R.drawable.boy)
                .into(studImg)
        } else {
            studImg.setImageResource(R.drawable.boy)
        }
        studentName.setText(studentNamePass)
        //    mInsuranceDetailArrayList= PreferenceManager().getInsuranceDetailArrayList(mContext);
        //    mInsuranceDetailArrayList= PreferenceManager().getInsuranceDetailArrayList(mContext);
        CommonClass.mInsuranceDetailArrayList =
            PreferenceManager().getInsuranceDetailArrayList(mContext)
        if (CommonClass.mInsuranceDetailArrayList.size > 0) {
            println("if insurance array")
            for (i in 0 until CommonClass.mInsuranceDetailArrayList.size) {
                println("if insurance array inside for")
                val mID: String = CommonClass.mInsuranceDetailArrayList.get(i).student_id.toString()
                println("if insurance array inside for student id$mID")
                if (studentId.equals(mID, ignoreCase = true)) {
                    println("if insurance array inside if for if")
                    dataPosition = i
                    CommonClass.confirmingPosition = dataPosition
                    itemID = CommonClass.mInsuranceDetailArrayList.get(i).id.toString()
                    System.out.println(
                        "item id" + CommonClass.mInsuranceDetailArrayList.get(i).id
                    )
                    medInsurancePolicyNo = CommonClass.mInsuranceDetailArrayList.get(i)
                        .medical_insurence_policy_no.toString()
                    medInsuranceMemberNo = CommonClass.mInsuranceDetailArrayList.get(i)
                        .medical_insurence_member_number.toString()
                    medInsuranceProvider = CommonClass.mInsuranceDetailArrayList.get(i)
                        .medical_insurence_provider.toString()
                    medInsuranceExpiryDate = CommonClass.mInsuranceDetailArrayList.get(i)
                        .medical_insurence_expiry_date.toString()
                    perAccidentInsPolicyNo = CommonClass.mInsuranceDetailArrayList.get(i)
                        .personal_accident_insurence_policy_no.toString()
                    perAccidentInsProvider = CommonClass.mInsuranceDetailArrayList.get(i)
                        .personal_accident_insurence_provider.toString()
                    perAccidentInsExpiryDate = CommonClass.mInsuranceDetailArrayList.get(i)
                        .personal_acident_insurence_expiry_date.toString()
                    prefferedHsptl =
                        CommonClass.mInsuranceDetailArrayList.get(i).preferred_hospital.toString()
                    insuranceTypeData = CommonClass.mInsuranceDetailArrayList.get(i)
                        .no_personal_accident_insurance.toString()
                    declarationValue =
                        CommonClass.mInsuranceDetailArrayList.get(i).declaration.toString()
                    healthNote = CommonClass.mInsuranceDetailArrayList.get(i).health_detail.toString()
                }
            }
        }
        println("if insurance array inside if for if fdfdfde$perAccidentInsProvider")
        if (declarationValue.equals("1", ignoreCase = true)) {
            println("Declaration if ")
            checkInsuranceDeclaration!!.setImageResource(R.drawable.check_box_header_tick)
            isInsuranceDeclared = true
            declaration = "1"
        } else {
            println("Declaration if else")
            checkInsuranceDeclaration!!.setImageResource(R.drawable.check_box_header)
            isInsuranceDeclared = false
            declaration = "0"
        }
        isInsuranceDataFound = if (!medInsurancePolicyNo.equals("", ignoreCase = true)) {
            true
        } else {
            if (!medInsuranceMemberNo.equals("", ignoreCase = true)) {
                true
            } else {
                if (!medInsuranceProvider.equals("", ignoreCase = true)) {
                    true
                } else {
                    if (!medInsuranceExpiryDate.equals("", ignoreCase = true)) {
                        true
                    } else {
                        if (!perAccidentInsPolicyNo.equals("", ignoreCase = true)) {
                            true
                        } else {
                            if (!perAccidentInsProvider.equals("", ignoreCase = true)) {
                                true
                            } else {
                                if (!perAccidentInsExpiryDate.equals("", ignoreCase = true)) {
                                    true
                                } else {
                                    false
                                }
                            }
                        }
                    }
                }
            }
        }

        if (healthNote.equals("", ignoreCase = true)) {
            MedicalNoteTxt!!.setText("No medical concerns, please contact the school nurse if circumstances have changed")
        } else {
            MedicalNoteTxt!!.setText(healthNote)
        }
        MedicalNoteTxt!!.setOnClickListener(View.OnClickListener {
            showInsuranceInfo(
                mContext,
                "Alert",
                getString(R.string.insurance_data_click_popup),
                R.drawable.exclamationicon,
                R.drawable.round
            )
        })
        println("Item id$itemID")
//        if (!isInsuranceDataFound)
//        {
//            insuranceLinear.setVisibility(View.GONE);
//            emptyTxt.setVisibility(View.VISIBLE);
//            emptyTxt.setText("No medical concerns, please contact the school nurse if circumstances have changed");
//        }
//        else
//        {
//            insuranceLinear.setVisibility(View.VISIBLE);
//            emptyTxt.setVisibility(View.GONE);
//        }

        //        if (!isInsuranceDataFound)
//        {
//            insuranceLinear.setVisibility(View.GONE);
//            emptyTxt.setVisibility(View.VISIBLE);
//            emptyTxt.setText("No medical concerns, please contact the school nurse if circumstances have changed");
//        }
//        else
//        {
//            insuranceLinear.setVisibility(View.VISIBLE);
//            emptyTxt.setVisibility(View.GONE);
//        }
        println("insurance data type on page start$insuranceTypeData")
        if (insuranceTypeData.equals("1", ignoreCase = true)) {
            println("It works inside insurance 1")
            isPersonalInsuranceClicked = true
            checkpersonalInsuranceImg.setImageResource(R.drawable.check_box_header_tick)
            personalInsuranceNumberTxt.setEnabled(false)
            personalInsuranceProviderTxt.setEnabled(false)
            personalInsuranceExpiryDateTxt.setEnabled(false)
            personalInsuranceNumberTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
            personalInsuranceProviderTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
            personalInsuranceExpiryDateTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
            personalInsuranceNumberTxt.setHint(R.string.personal_insurance_policy_number)
            personalInsuranceProviderTxt.setHint(R.string.personal_insurance_provider)
            personalInsuranceExpiryDateTxt.setHint(R.string.personal_insurance_expiry_date)
        } else {
            isPersonalInsuranceClicked = false
            checkpersonalInsuranceImg.setImageResource(R.drawable.check_box_header)
            personalInsuranceNumberTxt.setEnabled(true)
            personalInsuranceProviderTxt.setEnabled(true)
            personalInsuranceExpiryDateTxt.setEnabled(true)
            personalInsuranceNumberTxt.setBackgroundResource(R.drawable.rect_background_grey)
            personalInsuranceProviderTxt.setBackgroundResource(R.drawable.rect_background_grey)
            personalInsuranceExpiryDateTxt.setBackgroundResource(R.drawable.rect_background_grey)
            personalInsuranceNumberTxt.setHint(R.string.personal_insurance_policy_number_red)
            personalInsuranceProviderTxt.setHint(R.string.personal_insurance_provider_red)
            personalInsuranceExpiryDateTxt.setHint(R.string.personal_insurance_expiry_date_red)
        }

        //Medical Insurance Policy Number

        //Medical Insurance Policy Number
        val InsurancePolicyNograyPart = "Insurance Policy Number"
        val InsurancePolicyNoredPart = "*"
        val InsurancePolicyNobuilder = SpannableStringBuilder()
        val InsurancePolicyNoredColoredString = SpannableString(InsurancePolicyNograyPart)
        InsurancePolicyNoredColoredString.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            InsurancePolicyNograyPart.length,
            0
        )
        InsurancePolicyNobuilder.append(InsurancePolicyNoredColoredString)
        val InsurancePolicyNoblueColoredString = SpannableString(InsurancePolicyNoredPart)
        InsurancePolicyNoblueColoredString.setSpan(
            ForegroundColorSpan(Color.RED),
            0,
            InsurancePolicyNoredPart.length,
            0
        )
        InsurancePolicyNobuilder.append(InsurancePolicyNoblueColoredString)
        insurancePolicyNumberTxt.setHint(InsurancePolicyNobuilder)
        if (!medInsurancePolicyNo.equals("", ignoreCase = true)) {
            insurancePolicyNumberTxt.setText(medInsurancePolicyNo)
        }
        //Insurance Member Number
        //Insurance Member Number
        val InsuranceMemberNograyPart = "Insurance Member Number"
        val InsuranceMemberNoredPart = "*"
        val InsuranceMemberNobuilder = SpannableStringBuilder()
        val InsuranceMemberNoredColoredString = SpannableString(InsuranceMemberNograyPart)
        InsuranceMemberNoredColoredString.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            InsuranceMemberNograyPart.length,
            0
        )
        InsuranceMemberNobuilder.append(InsuranceMemberNoredColoredString)
        val InsuranceMemberNoblueColoredString = SpannableString(InsuranceMemberNoredPart)
        InsuranceMemberNoblueColoredString.setSpan(
            ForegroundColorSpan(Color.RED),
            0,
            InsuranceMemberNoredPart.length,
            0
        )
        InsuranceMemberNobuilder.append(InsuranceMemberNoblueColoredString)
        insuranceMemberNumberTxt.setHint(InsuranceMemberNobuilder)
        if (!medInsuranceMemberNo.equals("", ignoreCase = true)) {
            insuranceMemberNumberTxt.setText(medInsuranceMemberNo)
        }
        //Medical Insurance Provider
        //Medical Insurance Provider
        val InsuranceProvidergrayPart = "Insurance Provider"
        val InsuranceProviderredPart = "*"
        val InsuranceProviderbuilder = SpannableStringBuilder()
        val InsuranceProviderredColoredString = SpannableString(InsuranceProvidergrayPart)
        InsuranceProviderredColoredString.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            InsuranceProvidergrayPart.length,
            0
        )
        InsuranceProviderbuilder.append(InsuranceProviderredColoredString)
        val InsuranceProviderblueColoredString = SpannableString(InsuranceProviderredPart)
        InsuranceProviderblueColoredString.setSpan(
            ForegroundColorSpan(Color.RED),
            0,
            InsuranceProviderredPart.length,
            0
        )
        InsuranceProviderbuilder.append(InsuranceProviderblueColoredString)
        insuranceProviderTxt.setHint(InsuranceProviderbuilder)
        if (!medInsuranceProvider.equals("", ignoreCase = true)) {
            insuranceProviderTxt.setText(medInsuranceProvider)
        }
        //Medical Insurance Provider
        //Medical Insurance Provider
        val InsuranceExpiryDategrayPart = "Insurance Expiry Date"
        val InsuranceExpiryDateredPart = "*"
        val InsuranceExpiryDatebuilder = SpannableStringBuilder()
        val InsuranceExpiryDateredColoredString = SpannableString(InsuranceExpiryDategrayPart)
        InsuranceExpiryDateredColoredString.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            InsuranceExpiryDategrayPart.length,
            0
        )
        InsuranceExpiryDatebuilder.append(InsuranceExpiryDateredColoredString)
        val InsuranceExpiryDateblueColoredString = SpannableString(InsuranceExpiryDateredPart)
        InsuranceExpiryDateblueColoredString.setSpan(
            ForegroundColorSpan(Color.RED),
            0,
            InsuranceExpiryDateredPart.length,
            0
        )
        InsuranceExpiryDatebuilder.append(InsuranceExpiryDateblueColoredString)
        insuranceExpiryDate.setHint(InsuranceExpiryDatebuilder)

        if (!medInsuranceExpiryDate.equals("", ignoreCase = true)) {
            insuranceExpiryDate.setText(dateConversionMMM(medInsuranceExpiryDate))
        }
        //Personal Insurance

        //Personal Insurance
        println("Api call not working")

        if (!perAccidentInsPolicyNo.equals("", ignoreCase = true)) {
            personalInsuranceNumberTxt.setText(perAccidentInsPolicyNo)
        }

        if (!perAccidentInsProvider.equals("", ignoreCase = true)) {
            personalInsuranceProviderTxt.setText(perAccidentInsProvider)
        }
        if (!perAccidentInsExpiryDate.equals("", ignoreCase = true)) {
            personalInsuranceExpiryDateTxt.setText(
                dateConversionMMM(
                    perAccidentInsExpiryDate
                )
            )
        }
        if (!prefferedHsptl.equals("", ignoreCase = true)) {
            preferredHsptlTxt!!.setText(prefferedHsptl)
        }
        CloseIcon!!.setOnClickListener {


            //ShowDiscardDialog(mContext, "Confirm?", "Do you want to Discard changes?", R.drawable.questionmark_icon, R.drawable.round);
            if (CommonClass.isInsuranceEdited || CommonClass.isPassportEdited) {
                var studentPos = -1
                var id = ""
                var studName = ""
                var studClass = ""
                var studSection = ""
                var studHouse = ""
                var studPhoto = ""
                var studProgressReport = ""
                var studAlumini = ""
                val studentID = studentId
                if (CommonClass.mStudentDataArrayList.size > 0) {
                    for (i in 0 until CommonClass.mStudentDataArrayList.size) {
                        if (CommonClass.mStudentDataArrayList.get(i).mId
                                .equals(studentID)
                        ) {
                            studentPos = i
                            id = CommonClass.mStudentDataArrayList.get(i).mId.toString()
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
                model.mName=studName
                model.mClass=studClass
                model.mSection=studSection
                model.mHouse=studHouse
                model.mPhoto=studPhoto
                model.progressReport=studProgressReport
                model.alumini=studAlumini
                model.isConfirmed=false
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
                requireActivity().finish()
            } else {
                requireActivity().finish()
            }
        }
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        for (`in` in 0 until CommonClass.mInsuranceDetailArrayList.size) {
            if (stud_id.equals(
                    CommonClass.mInsuranceDetailArrayList.get(`in`).student_id,
                    ignoreCase = true
                )
            ) {
                C_positoin = `in`
            }
        }
        HEALTH_ID = CommonClass.mInsuranceDetailArrayList.get(C_positoin).id.toString()

//        insurancePolicyNumberTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showInsuranceInfo(mContext, "Alert", getString(R.string.insurance_data_click_popup), R.drawable.exclamationicon, R.drawable.round);
//
//            }
//        });
        insuranceProviderTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                val newData = insuranceProviderTxt.text.toString()
                CommonClass.isInsuranceEdited = true
                val dataId: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString().toString()
                val dataStatus = "1"
                //   String dataInsuranceType="0";
                val dataRequest = "0"
                val dataHealthDetail: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString().toString()
                val dataCreatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString().toString()
                val dataUpdatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString().toString()
                //
//                if (isPersonalInsuranceClicked)
//                {
//                    System.out.println("Personal clicked");
//                    dataInsuranceType="2";
//                }
//                else
//                {
//                    System.out.println("Personal clicked not");
//                    dataInsuranceType="0";
//                }
                val mModel = InsuranceDetailModel()
                mModel.id=dataId
                mModel.medical_insurence_policy_no=
                    insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_member_number=
                  insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_provider=newData
                if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.medical_insurence_expiry_date=
                        dateConversionYToD(
                            insuranceExpiryDate.text.toString().trim { it <= ' ' })

                } else {
                    mModel.medical_insurence_expiry_date=
                        insuranceExpiryDate.text.toString().trim { it <= ' ' }
                }
                mModel.personal_accident_insurence_policy_no=
                    personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
                mModel.personal_accident_insurence_provider=
                    personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.personal_acident_insurence_expiry_date=
                        dateConversionYToD(
                            personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.personal_acident_insurence_expiry_date=
                        personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                }
                mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
                mModel.status=dataStatus
                mModel.no_personal_accident_insurance=insuranceTypeData
                mModel.request=dataRequest
                mModel.student_id=studentId
                mModel.declaration=declaration
                mModel.student_name=studentNamePass
                mModel.health_detail=dataHealthDetail
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            }
        })
        insurancePolicyNumberTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                CommonClass.isInsuranceEdited = true
                val dataId: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString().toString()
                val dataStatus = "1"
                //            String dataInsuranceType="0";
                val dataRequest = "0"
                val dataHealthDetail: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString().toString()
                val dataCreatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString().toString()
                val dataUpdatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString().toString()
                val newData = insurancePolicyNumberTxt.text.toString()
                //                if (isPersonalInsuranceClicked)
//                {
//                    System.out.println("Personal clicked");
//                    dataInsuranceType="2";
//                }
//                else
//                {
//                    System.out.println("Personal clicked not");
//                    dataInsuranceType="0";
//                }
                //  Log.e("INSURANCE ", "textChange"+);
                System.out.println(
                    "InsuranceData before change" + CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).medical_insurence_policy_no
                )
                val mModel = InsuranceDetailModel()
                mModel.id=dataId
                mModel.medical_insurence_policy_no=newData
                mModel.medical_insurence_member_number=
                  insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_provider=
                    insuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.medical_insurence_expiry_date=
                        dateConversionYToD(
                            insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        )
                } else {
                    mModel.medical_insurence_expiry_date=
                        insuranceExpiryDate.text.toString().trim { it <= ' ' }
                }
                mModel.personal_accident_insurence_policy_no=
                    personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
                mModel.personal_accident_insurence_provider=
                    personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.personal_acident_insurence_expiry_date=
                        dateConversionYToD(
                            personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.personal_acident_insurence_expiry_date=
                        personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                }
                mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
                mModel.status=dataStatus
                mModel.no_personal_accident_insurance=insuranceTypeData
                mModel.request=dataRequest
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.health_detail=dataHealthDetail
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                mModel.declaration=declaration
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
                Log.e(
                    "Ins asize",
                    java.lang.String.valueOf(CommonClass.mInsuranceDetailArrayList.size)
                )
                for (j in 0 until CommonClass.mInsuranceDetailArrayList.size) {
                    Log.e("Ins after", CommonClass.mInsuranceDetailArrayList.get(j).id + j)
                }
            }
        })
        insuranceExpiryDate.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                val newData = insuranceExpiryDate.text.toString()
                CommonClass.isInsuranceEdited = true
                val dataId: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
                val dataStatus = "1"
                //       String dataInsuranceType="0";
                val dataRequest = "0"
                val dataHealthDetail: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
                val dataCreatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()

//                if (isPersonalInsuranceClicked)
//                {
//                    System.out.println("Personal clicked");
//                    dataInsuranceType="2";
//                }
//                else
//                {
//                    System.out.println("Personal clicked not");
//                    dataInsuranceType="0";
//                }
                val mModel = InsuranceDetailModel()
                mModel.id=dataId
                mModel.medical_insurence_policy_no=
                    insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_member_number=
                  insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_provider=
                    insuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.medical_insurence_expiry_date=
                        dateConversionYToD(
                            insuranceExpiryDate.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.medical_insurence_expiry_date=
                        insuranceExpiryDate.text.toString().trim { it <= ' ' }
                }
                mModel.personal_accident_insurence_policy_no=
                    personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
                mModel.personal_accident_insurence_provider=
                    personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.personal_acident_insurence_expiry_date=
                        dateConversionYToD(
                            personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.personal_acident_insurence_expiry_date=
                        personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                }
                mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
                mModel.status=dataStatus
                mModel.no_personal_accident_insurance=insuranceTypeData
                mModel.request=dataRequest
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.health_detail=dataHealthDetail
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                mModel.declaration=declaration
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            }
        })
        personalInsuranceNumberTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                val newData = personalInsuranceNumberTxt.text.toString()
                CommonClass.isInsuranceEdited = true
                val dataId: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
                val dataStatus = "1"
                //   String dataInsuranceType="0";
                val dataRequest = "0"
                val dataHealthDetail: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
                val dataCreatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()

//                if (isPersonalInsuranceClicked)
//                {
//                    System.out.println("Personal clicked");
//                    dataInsuranceType="2";
//                }
//                else
//                {
//                    System.out.println("Personal clicked not");
//                    dataInsuranceType="0";
//                }
                val mModel = InsuranceDetailModel()
                mModel.id=dataId
                mModel.medical_insurence_policy_no=
                    insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_member_number=
                  insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_provider=
                    insuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.medical_insurence_expiry_date=
                        dateConversionYToD(
                            insuranceExpiryDate.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.medical_insurence_expiry_date=
                        insuranceExpiryDate.text.toString().trim { it <= ' ' }
                }
                mModel.personal_accident_insurence_policy_no=newData
                mModel.personal_accident_insurence_provider=
                    personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.personal_acident_insurence_expiry_date=
                        dateConversionYToD(
                            personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.personal_acident_insurence_expiry_date=
                        personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                }
                mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
                mModel.status=dataStatus
                mModel.no_personal_accident_insurance=insuranceTypeData
                mModel.request=dataRequest
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.health_detail=dataHealthDetail
                mModel.declaration=declaration
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            }
        })
        personalInsuranceProviderTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                val newData = personalInsuranceProviderTxt.text.toString()
                CommonClass.isInsuranceEdited = true
                val dataId: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
                val dataStatus = "1"
                //    String dataInsuranceType="0";
                val dataRequest = "0"
                val dataHealthDetail: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
                val dataCreatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()

//                if (isPersonalInsuranceClicked)
//                {
//                    System.out.println("Personal clicked");
//                    dataInsuranceType="2";
//                }
//                else
//                {
//                    System.out.println("Personal clicked not");
//                    dataInsuranceType="0";
//                }
                val mModel = InsuranceDetailModel()
                mModel.id=dataId
                mModel.medical_insurence_policy_no=
                    insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_member_number=
                  insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_provider=
                    insuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.medical_insurence_expiry_date=
                        dateConversionYToD(
                            insuranceExpiryDate.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.medical_insurence_expiry_date=
                        insuranceExpiryDate.text.toString().trim { it <= ' ' }
                }
                mModel.personal_accident_insurence_policy_no=
                    personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
                mModel.personal_accident_insurence_provider=newData
                if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.personal_acident_insurence_expiry_date=
                        dateConversionYToD(
                            personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.personal_acident_insurence_expiry_date=
                        personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                }
                mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
                mModel.status=dataStatus
                mModel.no_personal_accident_insurance=insuranceTypeData
                mModel.declaration=declaration
                mModel.request=dataRequest
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.health_detail=dataHealthDetail
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            }
        })
        personalInsuranceExpiryDateTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                val newData = personalInsuranceExpiryDateTxt.text.toString()
                CommonClass.isInsuranceEdited = true
                val dataId: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
                val dataStatus = "1"
                //    String dataInsuranceType="0";
                val dataRequest = "0"
                val dataHealthDetail: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
                val dataCreatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()

//                if (isPersonalInsuranceClicked)
//                {
//                    System.out.println("Personal clicked");
//                    dataInsuranceType="2";
//                }
//                else
//                {
//                    System.out.println("Personal clicked not");
//                    dataInsuranceType="0";
//                }
                val mModel = InsuranceDetailModel()
                mModel.id=dataId
                mModel.medical_insurence_policy_no=
                    insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_member_number=
                  insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_provider=
                    insuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.medical_insurence_expiry_date=
                        dateConversionYToD(
                            insuranceExpiryDate.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.medical_insurence_expiry_date=
                        insuranceExpiryDate.text.toString().trim { it <= ' ' }
                }
                mModel.personal_accident_insurence_policy_no=
                    personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
                mModel.personal_accident_insurence_provider=
                    personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.personal_acident_insurence_expiry_date=
                        dateConversionYToD(
                            personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.personal_acident_insurence_expiry_date=
                        personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                }
                mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
                mModel.status=dataStatus
                mModel.no_personal_accident_insurance=insuranceTypeData
                mModel.declaration=declaration
                mModel.request=dataRequest
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.health_detail=dataHealthDetail
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            }
        })
        preferredHsptlTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                val newData = preferredHsptlTxt.text.toString()
                CommonClass.isInsuranceEdited = true
                val dataId: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
                val dataStatus = "1"
                //     String dataInsuranceType="0";
                val dataRequest = "0"
                val dataHealthDetail: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
                val dataCreatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()
                //
//                if (isPersonalInsuranceClicked)
//                {
//                    System.out.println("Personal clicked");
//                    dataInsuranceType="2";
//                }
//                else
//                {
//                    System.out.println("Personal clicked not");
//                    dataInsuranceType="0";
//                }
                val mModel = InsuranceDetailModel()
                mModel.id=dataId
                mModel.medical_insurence_policy_no=
                    insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_member_number=
                  insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_provider=
                    insuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.medical_insurence_expiry_date=
                        dateConversionYToD(
                            insuranceExpiryDate.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.medical_insurence_expiry_date=
                        insuranceExpiryDate.text.toString().trim { it <= ' ' }
                }
                mModel.personal_accident_insurence_policy_no=
                    personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
                mModel.personal_accident_insurence_provider=
                    personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.personal_acident_insurence_expiry_date=
                        dateConversionYToD(
                            personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.personal_acident_insurence_expiry_date=
                        personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                }
                mModel.preferred_hospital=newData
                mModel.status=dataStatus
                mModel.no_personal_accident_insurance=insuranceTypeData
                mModel.declaration=declaration
                mModel.request=dataRequest
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.health_detail=dataHealthDetail
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            }
        })

        insuranceMemberNumberTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                val newData = insuranceMemberNumberTxt.text.toString()
                CommonClass.isInsuranceEdited = true
                val dataId: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
                val dataStatus = "1"
                // String dataInsuranceType="0";
                val dataRequest = "0"
                val dataHealthDetail: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
                val dataCreatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()

//                if (isPersonalInsuranceClicked)
//                {
//                    System.out.println("Personal clicked");
//                    dataInsuranceType="2";
//                }
//                else
//                {
//                    System.out.println("Personal clicked not");
//                    dataInsuranceType="0";
//                }
                val mModel = InsuranceDetailModel()
                mModel.id=dataId
                mModel.medical_insurence_policy_no=
                    insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_member_number=newData
                mModel.medical_insurence_provider=
                    insuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.medical_insurence_expiry_date=
                        dateConversionYToD(
                            insuranceExpiryDate.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.medical_insurence_expiry_date=
                        insuranceExpiryDate.text.toString().trim { it <= ' ' }
                }
                mModel.personal_accident_insurence_policy_no=
                    personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
                mModel.personal_accident_insurence_provider=
                    personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.personal_acident_insurence_expiry_date=
                        dateConversionYToD(
                            personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.personal_acident_insurence_expiry_date=
                        personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                }
                mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
                mModel.status=dataStatus
                mModel.no_personal_accident_insurance=insuranceTypeData
                mModel.request=dataRequest
                mModel.student_id=studentId
                mModel.declaration=declaration
                mModel.student_name=studentNamePass
                mModel.health_detail=dataHealthDetail
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            }
        })
        checkpersonalInsuranceImg.setOnClickListener {
            if (isPersonalInsuranceClicked) {
                insuranceTypeData = "0"
                isPersonalInsuranceClicked = false
                checkpersonalInsuranceImg.setImageResource(R.drawable.check_box_header)
                personalInsuranceNumberTxt.isEnabled = true
                personalInsuranceProviderTxt.isEnabled = true
                personalInsuranceExpiryDateTxt.isEnabled = true
                personalInsuranceNumberTxt.setBackgroundResource(R.drawable.rect_background_grey)
                personalInsuranceProviderTxt.setBackgroundResource(R.drawable.rect_background_grey)
                personalInsuranceExpiryDateTxt.setBackgroundResource(R.drawable.rect_background_grey)
                personalInsuranceNumberTxt.setHint(R.string.personal_insurance_policy_number_red)
                personalInsuranceProviderTxt.setHint(R.string.personal_insurance_provider_red)
                personalInsuranceExpiryDateTxt.setHint(R.string.personal_insurance_expiry_date_red)
                HavePerINS = "YES"
                CommonClass.isInsuranceEdited = true
                val dataId: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
                val dataStatus = "1"
                val dataRequest = "0"
                val dataHealthDetail: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
                val dataCreatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()
                val mModel = InsuranceDetailModel()
                mModel.id=dataId
                mModel.medical_insurence_policy_no=
                    insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_member_number=
                  insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_provider=
                    insuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.medical_insurence_expiry_date=
                        dateConversionYToD(
                            insuranceExpiryDate.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.medical_insurence_expiry_date=
                        insuranceExpiryDate.text.toString().trim { it <= ' ' }
                }
                mModel.personal_accident_insurence_policy_no=
                    personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
                mModel.personal_accident_insurence_provider=
                    personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.personal_acident_insurence_expiry_date=
                        dateConversionYToD(
                            personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.personal_acident_insurence_expiry_date=
                        personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                }
                mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
                mModel.status=dataStatus
                mModel.no_personal_accident_insurance=insuranceTypeData
                mModel.request=dataRequest
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.declaration=declaration
                mModel.health_detail=dataHealthDetail
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            } else {
                insuranceTypeData = "1"
                isPersonalInsuranceClicked = true
                checkpersonalInsuranceImg.setImageResource(R.drawable.check_box_header_tick)
                personalInsuranceNumberTxt.isEnabled = false
                personalInsuranceProviderTxt.isEnabled = false
                personalInsuranceExpiryDateTxt.isEnabled = false
                personalInsuranceNumberTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
                personalInsuranceProviderTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
                personalInsuranceExpiryDateTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
                personalInsuranceNumberTxt.setHint(R.string.personal_insurance_policy_number)
                personalInsuranceProviderTxt.setHint(R.string.personal_insurance_provider)
                personalInsuranceExpiryDateTxt.setHint(R.string.personal_insurance_expiry_date)
                HavePerINS = "NO"
                CommonClass.isInsuranceEdited = true
                val dataId: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
                val dataStatus = "1"
                val dataRequest = "0"
                val dataHealthDetail: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
                val dataCreatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()
                val mModel = InsuranceDetailModel()
                mModel.id=dataId
                mModel.medical_insurence_policy_no=
                    insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_member_number=
                  insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
                mModel.medical_insurence_provider=
                    insuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.medical_insurence_expiry_date=
                        dateConversionYToD(
                            insuranceExpiryDate.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.medical_insurence_expiry_date=
                        insuranceExpiryDate.text.toString().trim { it <= ' ' }
                }
                mModel.personal_accident_insurence_policy_no=
                    personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
                mModel.personal_accident_insurence_provider=
                    personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
                if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                        .equals("", ignoreCase = true)) {
                    mModel.personal_acident_insurence_expiry_date=
                        dateConversionYToD(
                            personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                    )
                } else {
                    mModel.personal_acident_insurence_expiry_date=
                        personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                }
                //  mModel.personal_acident_insurence_expiry_date=personalInsuranceExpiryDateTxt.getText().toString().trim());
                mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
                mModel.status=dataStatus
                mModel.no_personal_accident_insurance=insuranceTypeData
                mModel.request=dataRequest
                mModel.declaration=declaration
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.health_detail=dataHealthDetail
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            }
            println("STATE: $HavePerINS")
        }
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel()
            }

        val date2 =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel2()
            }
        insuranceExpiryDate.setOnClickListener {
            AppUtils().hideKeyBoard(mContext)
            DatePickerDialog(
                mContext, date, myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
        personalInsuranceExpiryDateTxt.setOnClickListener {
            AppUtils().hideKeyBoard(mContext)
            DatePickerDialog(
                mContext, date2, myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
        checkInsuranceDeclaration!!.setOnClickListener {
            if (isInsuranceDeclared) {
                checkInsuranceDeclaration!!.setImageResource(R.drawable.check_box_header)
                isInsuranceDeclared = false
                declaration = "0"
                val mModel = InsuranceDetailModel()
                mModel.id=CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
                mModel.student_id=
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).student_id
                mModel.student_name
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).student_name
                
                mModel.health_detail=
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
                
                mModel.no_personal_accident_insurance=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).no_personal_accident_insurance
                
                mModel.medical_insurence_policy_no=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).medical_insurence_policy_no
                
                mModel.medical_insurence_member_number=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).medical_insurence_member_number
                
                mModel.medical_insurence_provider=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).medical_insurence_provider
                
                mModel.medical_insurence_expiry_date=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).medical_insurence_expiry_date
                
                
                mModel.personal_accident_insurence_policy_no=
                
                
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).personal_accident_insurence_policy_no
                
                
                mModel.personal_accident_insurence_provider=
                    
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).personal_accident_insurence_provider
                
                
                mModel.personal_acident_insurence_expiry_date=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).personal_acident_insurence_expiry_date
      
                
                mModel.preferred_hospital=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).preferred_hospital
                
                mModel.created_at=                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
                
                
                mModel.updated_at=
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()
               
                mModel.declaration=declaration
                mModel.status="1"
               mModel.request="0"
                CommonClass.isInsuranceEdited = true
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            } else {
                checkInsuranceDeclaration!!.setImageResource(R.drawable.check_box_header_tick)
                isInsuranceDeclared = true
                declaration = "1"
                CommonClass.isInsuranceEdited = true
                val mModel = InsuranceDetailModel()
                mModel.id=CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
                mModel.student_id=
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).student_id

                mModel.student_name
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).student_name

                mModel.health_detail=
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
                
                mModel.no_personal_accident_insurance=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).no_personal_accident_insurance
                
                mModel.medical_insurence_policy_no=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).medical_insurence_policy_no

                mModel.medical_insurence_member_number=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).medical_insurence_member_number

                mModel.medical_insurence_provider=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).medical_insurence_provider
                
                mModel.medical_insurence_expiry_date=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).medical_insurence_expiry_date
                
                
                mModel.personal_accident_insurence_policy_no=
                
                
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).personal_accident_insurence_policy_no
                
                
                mModel.personal_accident_insurence_provider=
                
                
                
                
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).personal_accident_insurence_provider
                
                
                mModel.personal_acident_insurence_expiry_date=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).personal_acident_insurence_expiry_date
      
                
                mModel.preferred_hospital=
                    CommonClass.mInsuranceDetailArrayList.get(
                        dataPosition
                    ).preferred_hospital
                
                mModel.created_at=                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
                
                
                mModel.updated_at=
                    CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()
               
                mModel.declaration=declaration
                mModel.status="1"
               mModel.request="0"
                CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
                CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
                PreferenceManager().saveInsuranceDetailArrayList(
                    CommonClass.mInsuranceDetailArrayList,
                    mContext
                )
            }
        }

    }
    private fun updateLabel2() {
        val myFormat = "yyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        DATE = sdf.format(myCalendar.time)
        personalInsuranceExpiryDateTxt.setText(AppUtils().dateConversionMMM(DATE))
        val newData = insuranceExpiryDate.text.toString()
        CommonClass.isInsuranceEdited = true
        val dataId: String = CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
        val dataStatus = "1"
        //       String dataInsuranceType="0";
        val dataRequest = "0"
        val dataHealthDetail: String =
            CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
        val dataCreatedAt: String =
            CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
        val dataUpdatedAt: String =
            CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()

//                if (isPersonalInsuranceClicked)
//                {
//                    System.out.println("Personal clicked");
//                    dataInsuranceType="2";
//                }
//                else
//                {
//                    System.out.println("Personal clicked not");
//                    dataInsuranceType="0";
//                }
        val mModel = InsuranceDetailModel()
        mModel.id=dataId
        mModel.medical_insurence_policy_no=
            insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
        mModel.medical_insurence_member_number=
            insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
        mModel.medical_insurence_provider=
            insuranceProviderTxt.text.toString().trim { it <= ' ' }
        if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
            mModel.medical_insurence_expiry_date=
                dateConversionYToD(
                    insuranceExpiryDate.text.toString().trim { it <= ' ' }
            )
        } else {
            mModel.medical_insurence_expiry_date=
                insuranceExpiryDate.text.toString().trim { it <= ' ' }
        }
        mModel.personal_accident_insurence_policy_no=
                
                
            personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
        mModel.personal_accident_insurence_provider=
                
                
                
                
            personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
        if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                .equals("", ignoreCase = true)) {
            mModel.personal_acident_insurence_expiry_date=
                dateConversionYToD(
                    personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
            )
        } else {
            mModel.personal_acident_insurence_expiry_date=
                personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
        }
        mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
        mModel.status=dataStatus
        mModel.no_personal_accident_insurance=insuranceTypeData
        mModel.request=dataRequest
        mModel.student_id=studentId
        mModel.student_name=studentNamePass
        mModel.health_detail=dataHealthDetail
        mModel.created_at=dataCreatedAt
        mModel.updated_at=dataUpdatedAt
        mModel.declaration=declaration
        CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
        CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
        PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
        PreferenceManager().saveInsuranceDetailArrayList(
            CommonClass.mInsuranceDetailArrayList,
            mContext
        )
    }

    private fun updateLabel() {
        val myFormat = "yyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        DATE = sdf.format(myCalendar.time)
        insuranceExpiryDate.setText(AppUtils().dateConversionMMM(DATE))
        val newData = insuranceExpiryDate.text.toString()
        CommonClass.isInsuranceEdited = true
        val dataId: String = CommonClass.mInsuranceDetailArrayList.get(dataPosition).id.toString()
        val dataStatus = "1"
        //       String dataInsuranceType="0";
        val dataRequest = "0"
        val dataHealthDetail: String =
            CommonClass.mInsuranceDetailArrayList.get(dataPosition).health_detail.toString()
        val dataCreatedAt: String =
            CommonClass.mInsuranceDetailArrayList.get(dataPosition).created_at.toString()
        val dataUpdatedAt: String =
            CommonClass.mInsuranceDetailArrayList.get(dataPosition).updated_at.toString()

//                if (isPersonalInsuranceClicked)
//                {
//                    System.out.println("Personal clicked");
//                    dataInsuranceType="2";
//                }
//                else
//                {
//                    System.out.println("Personal clicked not");
//                    dataInsuranceType="0";
//                }
        val mModel = InsuranceDetailModel()
        mModel.id=dataId
        mModel.medical_insurence_policy_no=
            insurancePolicyNumberTxt.text.toString().trim { it <= ' ' }
        mModel.medical_insurence_member_number=
            insuranceMemberNumberTxt.text.toString().trim { it <= ' ' }
        mModel.medical_insurence_provider=
            insuranceProviderTxt.text.toString().trim { it <= ' ' }
        if (!insuranceExpiryDate.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
            mModel.medical_insurence_expiry_date=
                dateConversionYToD(
                    insuranceExpiryDate.text.toString().trim { it <= ' ' }
            )
        } else {
            mModel.medical_insurence_expiry_date=
                insuranceExpiryDate.text.toString().trim { it <= ' ' }
        }
        mModel.personal_accident_insurence_policy_no=
                
                
            personalInsuranceNumberTxt.text.toString().trim { it <= ' ' }
        mModel.personal_accident_insurence_provider=
                
                
                
                
            personalInsuranceProviderTxt.text.toString().trim { it <= ' ' }
        if (!personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
                .equals("", ignoreCase = true)) {
            mModel.personal_acident_insurence_expiry_date=
                dateConversionYToD(
                    personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
            )
        } else {
            mModel.personal_acident_insurence_expiry_date=
                personalInsuranceExpiryDateTxt.text.toString().trim { it <= ' ' }
        }
        mModel.preferred_hospital=preferredHsptlTxt.text.toString().trim { it <= ' ' }
        mModel.status=dataStatus
        mModel.no_personal_accident_insurance=insuranceTypeData
        mModel.request=dataRequest
        mModel.student_id=studentId
        mModel.student_name=studentNamePass
        mModel.health_detail=dataHealthDetail
        mModel.created_at=dataCreatedAt
        mModel.updated_at=dataUpdatedAt
        mModel.declaration=declaration
        CommonClass.mInsuranceDetailArrayList.removeAt(dataPosition)
        CommonClass.mInsuranceDetailArrayList.add(dataPosition, mModel)
        PreferenceManager().getInsuranceDetailArrayList(mContext).clear()
        PreferenceManager().saveInsuranceDetailArrayList(
            CommonClass.mInsuranceDetailArrayList,
            mContext
        )
    }
    private fun showInsuranceInfo(
        mContext: Context,
        msgHead: String,
        msg: String,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(mContext)
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
        dialogButton.setOnClickListener { dialog.dismiss() }
        val dialogButtonCancel = dialog.findViewById<Button>(R.id.btn_Cancel)
        dialog.show()
    }
    fun dateConversionYToD(inputDate: String?): String? {
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
        } catch (e: Exception) {
            Log.d("Exception", "" + e)
        }
        return mDate
    }
}
