package com.example.bskl_kotlin.activity.datacollection_p2
import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.datacollection_p2.model.PassportDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.StudentModelNew
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.AppUtils
import com.example.bskl_kotlin.manager.CommonClass
import id.zelory.compressor.Compressor
import id.zelory.compressor.FileUtil
import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog


import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ThirdScreenNewData(studentImage: String, var student_Id: String, studentName: String) : Fragment() {
    lateinit  var mContext: Context
    lateinit var checkStudentMalysianImg: ImageView
    lateinit var CloseIcon:android.widget.ImageView
    lateinit var checkPassportImg:android.widget.ImageView
    lateinit var studImg: ImageView
    lateinit var ViewSelectedVisa: ImageView
        lateinit var ViewSelectedPassport:android.widget.ImageView

    lateinit var UploadPasspost: TextView
    lateinit var uploadVisa: TextView
    lateinit var uploadVisaTxt:android.widget.TextView
    lateinit var studentName: TextView
    lateinit var attachPassportTxt: TextView
    lateinit var PassportImageName: TextView
    lateinit var VisaImageName:android.widget.TextView
    lateinit var passportNationalityTxt: TextView

    lateinit var visaPermitNumberTxt: EditText
    lateinit var visaPermitExpiryTxt:android.widget.EditText
    lateinit var passportNumberTxt: EditText
    lateinit var passportExpiryTxt:android.widget.EditText

    lateinit var paasportNationalityLinear: RelativeLayout

    var isStudentMalasiyanChecked = false
    var isPassportChecked = false


    lateinit var mStudentSpinner: LinearLayout
    lateinit var passportLinear: LinearLayout

     var passportNationality = ""
     var passportNumber = ""
     var passportExpiry = ""
     var visaPermitNumber = ""
     var visaPermitExpiry = ""
     var studentId = ""
     var studentImage = ""
     var studentNamePass = ""
     var visaImageData = ""
     var passportImageData = ""
     var DATE = ""
     var IsNational = "YES"
     var visa_image_name_path = ""
     var passport_image_name_path = ""
     var passport_image_path = ""
     var visa_image_path = ""
     var itemId = ""
     var visaValue = ""
     var passportValue = ""
     var visaImgName = ""
     var passportImgName = ""
     var visaImgPath = ""
     var passportImgPath = ""
    private var pictureImagePath = ""

    lateinit var spinnerDialog: SpinnerDialog
    val myCalendar = Calendar.getInstance()
    private val PICK_IMAGE_REQUEST = 1
    private val CLICK_IMAGE_REQUEST = 2
    private val VISA_CAMERA_REQUEST = 3
    private val VISA_GALLERY_REQUEST = 4
    private val CAMERA_REQUEST = 1888

    lateinit var PassportCamera: File
    lateinit var CompressPassportCamera: File
    lateinit var VisaCamera: File
    lateinit var CompressVisaCamera: File
    lateinit var  actualImage: File
    lateinit var  VisaactualImage: File
    lateinit var  compressedImage: File
    lateinit var  VisacompressedImage: File

    private val galleryPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
     var dataPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_passport_new_data_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
       /* if (EasyPermissions.hasPermissions(mContext, galleryPermissions.toString())) {
//            Toast.makeText(mContext, "permisssion granted", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(
                this, "Access for storage",
                101, galleryPermissions
            )
        }*/

        checkStudentMalysianImg = requireView().findViewById<ImageView>(R.id.checkStudentMalysianImg)
        checkPassportImg =  requireView().findViewById<ImageView>(R.id.checkPassportImg)
        visaPermitNumberTxt =  requireView().findViewById<EditText>(R.id.visaPermitNumberTxt)
        visaPermitExpiryTxt =  requireView().findViewById<EditText>(R.id.visaPermitExpiryTxt)
        uploadVisa =  requireView().findViewById<TextView>(R.id.uploadVisa)
        uploadVisaTxt =  requireView().findViewById<TextView>(R.id.uploadVisaTxt)
        studentName =  requireView().findViewById<TextView>(R.id.studentName)
        studImg =  requireView().findViewById<ImageView>(R.id.imagicon)
        passportLinear =  requireView().findViewById<LinearLayout>(R.id.passportLinear)
        PassportImageName =  requireView().findViewById<TextView>(R.id.PassImageName)
        VisaImageName =  requireView().findViewById<TextView>(R.id.VisaImageName)
        passportNationalityTxt =  requireView().findViewById<TextView>(R.id.passportNationalityTxt)
        attachPassportTxt =  requireView().findViewById<TextView>(R.id.attachPassportTxt)
        passportNumberTxt =  requireView().findViewById<EditText>(R.id.passportNumberTxt)
        passportExpiryTxt =  requireView().findViewById<EditText>(R.id.passportExpiryTxt)
        mStudentSpinner =  requireView().findViewById<LinearLayout>(R.id.studentSpinner)
        UploadPasspost =  requireView().findViewById<TextView>(R.id.uploadPassportTxt)
        ViewSelectedPassport =  requireView().findViewById<ImageView>(R.id.ViewSelectedPassport)
        ViewSelectedVisa =  requireView().findViewById<ImageView>(R.id.ViewSelectedVisa)
        paasportNationalityLinear =  requireView().findViewById<RelativeLayout>(R.id.paasportNationalityLinear)
        CloseIcon =  requireView().findViewById<ImageView>(R.id.closeImg)
        visaPermitNumberTxt.imeOptions = EditorInfo.IME_ACTION_DONE
        passportNumberTxt.imeOptions = EditorInfo.IME_ACTION_DONE
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
         CommonClass.mPassportDetailArrayList =
            PreferenceManager().getPassportDetailArrayList(mContext)
        visaPermitNumberTxt.isEnabled = true
        visaPermitExpiryTxt.isEnabled = true
        if (studentImage != "") {
            Glide.with(mContext).load(AppUtils().replace(studentImage)).placeholder(R.drawable.boy)
                .into(studImg)
        } else {
            studImg.setImageResource(R.drawable.boy)
        }

        studentName.text = studentNamePass
        if ( CommonClass.mPassportDetailArrayList.size > 0) {
            for (i in 0 until  CommonClass.mPassportDetailArrayList.size) {
                val mID: String =  CommonClass.mPassportDetailArrayList.get(i).student_id.toString()
                if (studentId.equals(mID, ignoreCase = true)) {
                    dataPosition = i
                     CommonClass.confirmingPosition = dataPosition
                    passportNationality =
                         CommonClass.mPassportDetailArrayList.get(i).nationality.toString()
                    passportNumber =
                         CommonClass.mPassportDetailArrayList.get(i).passport_number.toString()
                    passportExpiry =  CommonClass.mPassportDetailArrayList.get(i).expiry_date.toString()
                    visaPermitNumber =
                         CommonClass.mPassportDetailArrayList.get(i).visa_permit_no.toString()
                    visaPermitExpiry =
                         CommonClass.mPassportDetailArrayList.get(i).visa_permit_expiry_date.toString()
                    visaValue =  CommonClass.mPassportDetailArrayList.get(i).visa.toString()
                    passportValue =
                         CommonClass.mPassportDetailArrayList.get(i).not_have_a_valid_passport.toString()
                    //System.out.println("visa value "+ CommonClass.mPassportDetailArrayList.get(i).visa.toString());
                    visaImgName =  CommonClass.mPassportDetailArrayList.get(i).visa_image_name.toString()
                    passportImgName =
                         CommonClass.mPassportDetailArrayList.get(i).passport_image_name.toString()
                    visaImgPath =  CommonClass.mPassportDetailArrayList.get(i).visa_image_path.toString()
                    passportImgPath =
                         CommonClass.mPassportDetailArrayList.get(i).passport_image_path.toString()
                    itemId =  CommonClass.mPassportDetailArrayList.get(i).id.toString()
                }
            }
        }
        if (passportNationality.equals("", ignoreCase = true)) {
            passportNationalityTxt.setHint(R.string.AST_COUNTRY)
        } else {
            passportNationalityTxt.text = passportNationality
        }

        passportNumberTxt.setText(passportNumber)
        val nationalStringArray = ArrayList<String>()
        for (i in 0 until  CommonClass.mNationalityArrayList.size) {
            nationalStringArray.add( CommonClass.mNationalityArrayList.get(i).name.toString())
        }
        spinnerDialog = SpinnerDialog(
            activity,
            nationalStringArray,
            "Select Country",
            "Close"
        ) // With No Animation

        spinnerDialog = SpinnerDialog(
            activity,
            nationalStringArray,
            "Select Country",
            R.style.DialogAnimations_SmileWindow,
            "Close"
        ) // With 	Animation

        // setDataToAdapter(nationalStringArray);

        // setDataToAdapter(nationalStringArray);
        spinnerDialog.setCancellable(true) // for cancellable

        spinnerDialog.setShowKeyboard(false) // for open keyboard by default

        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                //    Toast.makeText(mContext, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                passportNationalityTxt.text = item
                 CommonClass.isPassportEdited = true
                if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
                    Log.e("It Worked ", "BUG")
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
                    val studentID = studentId
                    Log.e("It Worked ", "BUG$studentID")
                    Log.e(
                        "It Worked ",
                        "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
                    )
                    Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
                    if ( CommonClass.mStudentDataArrayList.size > 0) {
                        for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                            if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                    .equals(student_Id)
                            ) {
                                Log.e("It Worked ", "BUG position$i")
                                studentPos = i
                                id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                                studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                                studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                                studSection =
                                     CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                                studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                                studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                                studProgressReport =
                                     CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                                studAlumini =
                                     CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                        model.isConfirmed=false
                        Log.e("It Worked ", "BUG$studentPos")
                         CommonClass.mStudentDataArrayList.removeAt(studentPos)
                         CommonClass.mStudentDataArrayList.add(studentPos, model)
                         CommonClass.isInsuranceEdited = true
                         CommonClass.confirmingPosition = studentPos
                        PreferenceManager().getInsuranceStudentList(mContext).clear()
                        PreferenceManager().saveInsuranceStudentList(
                             CommonClass.mStudentDataArrayList,
                            mContext
                        )
                    }
                }
                val dataId: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
                var dataStatus = ""
                var dataRequest = ""
                if (dataId.equals("", ignoreCase = true)) {
                    dataStatus = "0"
                    dataRequest = "1"
                } else {
                    dataStatus = "1"
                    dataRequest = "0"
                }
                val dataCreatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
                val newData = passportExpiryTxt.text.toString()

                //  Log.e("INSURANCE ", "textChange"+);
                val mModel = PassportDetailModel()
                mModel.id=dataId
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.passport_number=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()
                
                mModel.nationality=passportNationalityTxt.text.toString()
                mModel.date_of_issue=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
                
                mModel.expiry_date=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
                
                mModel.original_expiry_date=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_expiry_date
                
                mModel.original_passport_image=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_image
                
                mModel.original_nationality=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_nationality
                
                mModel.original_passport_number=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_number
                
                mModel.not_have_a_valid_passport=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).not_have_a_valid_passport.toString()
                
                mModel.visa_permit_no=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_permit_no.toString()
                
                if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
                    //  dateConversionYToD
                    mModel.visa_permit_expiry_date=
                        visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
                } else {
                   mModel.visa_permit_expiry_date=
                        dateConversionYToD(
                            visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
                    
                }
                mModel.visa_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image
                
                mModel.visa_image_name=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name
                
                mModel.visa_image_path=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path
                
                mModel.passport_image_path=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_path.toString()
                
                mModel.passport_image_name=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_name
                
                mModel.photo_no_consent=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
                
                mModel.visa=visaValue
                mModel.status=dataStatus
                mModel.request=dataRequest
                mModel.passport_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image
                
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                mModel.visa_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
                
                mModel.passport_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
                
                mModel.isPassportDateChanged=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).isPassportDateChanged
                
                mModel.isVisaDateChanged=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
                
                 CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
                 CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
                Log.e(
                    "Ins asize",
                    java.lang.String.valueOf( CommonClass.mPassportDetailArrayList.size)
                )
                for (j in 0 until  CommonClass.mPassportDetailArrayList.size) {
                    Log.e("Ins after",  CommonClass.mPassportDetailArrayList.get(j).id.toString() + j)
                }
                PreferenceManager().getPassportDetailArrayList(mContext).clear()
                PreferenceManager().savePassportDetailArrayList(
                     CommonClass.mPassportDetailArrayList,
                    mContext
                )
            }
        })
        passportLinear.setOnClickListener{
            spinnerDialog.showSpinerDialog()
        }
        if (!passportExpiry.equals("", ignoreCase = true)) {
            passportExpiryTxt.setText(AppUtils().dateConversionMMM(passportExpiry))
        }

        if (visaValue.equals("0", ignoreCase = true)) {
            isStudentMalasiyanChecked = false
            visaPermitNumberTxt.setHint(R.string.AST_VISA_PERMIT_TEXT_WITH_RED)
            visaPermitExpiryTxt.setHint(R.string.AST_VISA_PERMIT_EXPIRY_WITH_RED)
            checkStudentMalysianImg.setImageResource(R.drawable.check_box_header)
            visaPermitNumberTxt.setBackgroundResource(R.drawable.rect_background_grey)
            visaPermitExpiryTxt.setBackgroundResource(R.drawable.rect_background_grey)
            uploadVisa.alpha = 1.0f
            uploadVisaTxt.alpha = 1.0f
            uploadVisa.isEnabled = true
            visaPermitNumberTxt.isEnabled = true
            visaPermitExpiryTxt.isEnabled = true
            visaPermitNumberTxt.setText(visaPermitNumber)
            if (!visaPermitExpiry.equals("", ignoreCase = true)) {
                visaPermitExpiryTxt.setText(AppUtils().dateConversionMMM(visaPermitExpiry))
            }
        } else {
            if (itemId.equals("", ignoreCase = true)) {
                isStudentMalasiyanChecked = false
                visaPermitNumberTxt.setHint(R.string.AST_VISA_PERMIT_TEXT_WITH_RED)
                visaPermitExpiryTxt.setHint(R.string.AST_VISA_PERMIT_EXPIRY_WITH_RED)
                checkStudentMalysianImg.setImageResource(R.drawable.check_box_header)
                visaPermitNumberTxt.setBackgroundResource(R.drawable.rect_background_grey)
                visaPermitExpiryTxt.setBackgroundResource(R.drawable.rect_background_grey)
                uploadVisa.alpha = 1.0f
                uploadVisaTxt.alpha = 1.0f
                uploadVisa.isEnabled = true
                visaPermitNumberTxt.isEnabled = true
                visaPermitExpiryTxt.isEnabled = true
                visaPermitNumberTxt.setText(visaPermitNumber)
                if (!visaPermitExpiry.equals("", ignoreCase = true)) {
                    visaPermitExpiryTxt.setText(AppUtils().dateConversionMMM(visaPermitExpiry))
                }
            } else {
                isStudentMalasiyanChecked = true
                visaPermitNumberTxt.setHint(R.string.VISA_PERMIT_TXT)
                visaPermitExpiryTxt.setHint(R.string.VISA_PERMIT_EXPIRY)
                checkStudentMalysianImg.setImageResource(R.drawable.check_box_header_tick)
                visaPermitNumberTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
                visaPermitExpiryTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
                uploadVisaTxt.alpha = 0.5f
                uploadVisa.alpha = 0.5f
                visaPermitNumberTxt.isEnabled = false
                visaPermitExpiryTxt.isEnabled = false
            }
        }

        if (passportValue.equals("1", ignoreCase = true)) {
            isPassportChecked = true
            checkPassportImg.setImageResource(R.drawable.check_box_header_tick)
            paasportNationalityLinear.setBackgroundResource(R.drawable.rect_background_grey_checked)
            passportNumberTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
            passportExpiryTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
            UploadPasspost.alpha = 0.5f
            attachPassportTxt.alpha = 0.5f
            passportNationalityTxt.alpha = 0.5f
            passportExpiryTxt.alpha = 0.5f
            passportNumberTxt.alpha = 0.5f
            paasportNationalityLinear.alpha = 0.5f
            passportNumberTxt.isClickable = false
            passportNumberTxt.isEnabled = false
            passportExpiryTxt.isClickable = false
            passportExpiryTxt.isEnabled = false
            passportLinear.isClickable = false
            passportLinear.isEnabled = false
            UploadPasspost.isClickable = false
            UploadPasspost.isEnabled = false
        } else {
            isPassportChecked = false
            checkPassportImg.setImageResource(R.drawable.check_box_header)
            paasportNationalityLinear.setBackgroundResource(R.drawable.rect_background_grey)
            passportNumberTxt.setBackgroundResource(R.drawable.rect_background_grey)
            passportExpiryTxt.setBackgroundResource(R.drawable.rect_background_grey)
            UploadPasspost.alpha = 1.0f
            attachPassportTxt.alpha = 1.0f
            passportNationalityTxt.alpha = 1.0f
            passportExpiryTxt.alpha = 1.0f
            passportNumberTxt.alpha = 1.0f
            paasportNationalityLinear.alpha = 1.0f
            passportNumberTxt.isClickable = true
            passportNumberTxt.isEnabled = true
            passportExpiryTxt.isClickable = true
            passportExpiryTxt.isEnabled = true
            passportLinear.isClickable = true
            passportLinear.isEnabled = true
            UploadPasspost.isClickable = true
            UploadPasspost.isEnabled = true
        }
        if (visaImgName.equals("", ignoreCase = true)) {
            VisaImageName.text = visaImgName
        } else {
            VisaImageName.text = visaImgName
        }
        if (passportImgName.equals("", ignoreCase = true)) {
            PassportImageName.text = passportImgName
        } else {
            PassportImageName.text = passportImgName
        }
        if (visaImgPath.equals("", ignoreCase = true)) {
        } else {
            ViewSelectedVisa.setImageBitmap(BitmapFactory.decodeFile(visaImgPath))
        }
//        System.out.println("passportImgPath"+passportImgPath);
//        System.out.println("passportImgPath name"+passportImgName);
        //        System.out.println("passportImgPath"+passportImgPath);
//        System.out.println("passportImgPath name"+passportImgName);
        if (passportImgPath.equals("", ignoreCase = true)) {
            ViewSelectedPassport.visibility = View.INVISIBLE
        } else {
            ViewSelectedPassport.visibility = View.VISIBLE
            ViewSelectedPassport.setImageBitmap(BitmapFactory.decodeFile(passportImgPath))
        }
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // view.setMinDate(System.currentTimeMillis() - 1000);
                // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel()
            }
        val datePassport =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                //view.setMinDate(System.currentTimeMillis() - 1000);
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabelPassport()
            }
        visaPermitExpiryTxt.setOnClickListener {
            AppUtils().hideKeyBoard(mContext)
            val dpd = DatePickerDialog(
                mContext, date, myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
            dpd.datePicker.minDate = Date().time
            dpd.show()
        }
        passportExpiryTxt.setOnClickListener {
            AppUtils().hideKeyBoard(mContext)
            // ShowDatePicker(v);

            // ShowDatePicker(v);
            val dpd = DatePickerDialog(
                mContext, datePassport, myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
            dpd.datePicker.minDate = Date().time
            dpd.show()
        }
CloseIcon.setOnClickListener {
    val dataId: String =  CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
    var dataStatus = ""
    var dataRequest = ""
    if (dataId.equals("", ignoreCase = true)) {
        dataStatus = "0"
        dataRequest = "1"
    } else {
        dataStatus = "1"
        dataRequest = "0"
    }

    val dataCreatedAt: String =
         CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
    val dataUpdatedAt: String =
         CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
    val newData = passportNationalityTxt.text.toString()
    //   System.out.println("Nationality"+passportNationalityTxt.getText().toString());

    //  Log.e("INSURANCE ", "textChange"+);

    //   System.out.println("Nationality"+passportNationalityTxt.getText().toString());

    //  Log.e("INSURANCE ", "textChange"+);
    val mModel = PassportDetailModel()
    mModel.id=dataId
    mModel.student_id=studentId
    mModel.student_name=studentNamePass
    mModel.passport_number=
         CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()
    
    mModel.nationality=passportNationalityTxt.text.toString()
    mModel.date_of_issue=
         CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
    
    mModel.expiry_date= CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
    mModel.visa_permit_no=
         CommonClass.mPassportDetailArrayList.get(dataPosition).visa_permit_no.toString()
    
    mModel.not_have_a_valid_passport=
         CommonClass.mPassportDetailArrayList.get(dataPosition).not_have_a_valid_passport.toString()
    
    mModel.visa_expired=
         CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
    
    mModel.passport_expired=
         CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
    
    mModel.original_expiry_date=
         CommonClass.mPassportDetailArrayList.get(dataPosition).original_expiry_date
    
    mModel.original_passport_image=
         CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_image
    
    mModel.original_nationality=
         CommonClass.mPassportDetailArrayList.get(dataPosition).original_nationality
    
    mModel.original_passport_number=
         CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_number
    
    if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
        //  dateConversionYToD
        mModel.visa_permit_expiry_date=visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
    } else {
        mModel.visa_permit_expiry_date=
            dateConversionYToD(
                visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
        
    }
    mModel.visa_image= CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image
    mModel.visa_image_name=
         CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name
    
    mModel.visa_image_path=
         CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path
    
    mModel.passport_image_path=
         CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image_path.toString()
    
    mModel.passport_image_name=
         CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image_name
    
    mModel.photo_no_consent=
         CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
    
    mModel.visa=visaValue
    mModel.status=dataStatus
    mModel.request=dataRequest
    mModel.passport_image=
         CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image
    
    mModel.created_at=dataCreatedAt
    mModel.updated_at=dataUpdatedAt
    mModel.isPassportDateChanged=
         CommonClass.mPassportDetailArrayList.get(dataPosition).isPassportDateChanged
    
    mModel.isVisaDateChanged
         CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
    
     CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
     CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
    Log.e("Ins asize", java.lang.String.valueOf( CommonClass.mPassportDetailArrayList.size))
    for (j in 0 until  CommonClass.mPassportDetailArrayList.size) {
        Log.e("Ins after",  CommonClass.mPassportDetailArrayList.get(j).id.toString() + j)
    }
    PreferenceManager().getPassportDetailArrayList(mContext).clear()
    // System.out.println("close passport detail size"+PreferenceManager().getPassportDetailArrayList(mContext).size);
    // System.out.println("close passport detail size"+PreferenceManager().getPassportDetailArrayList(mContext).size);
    PreferenceManager().savePassportDetailArrayList( CommonClass.mPassportDetailArrayList, mContext)

    if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
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
        val studentID = studentId
        if ( CommonClass.mStudentDataArrayList.size > 0) {
            for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                        .equals(studentID)
                ) {
                    studentPos = i
                    id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                    isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                    studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                    studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                    studSection =  CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                    studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                    studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                    studProgressReport =
                         CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                    studAlumini =  CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
        model.isConfirmed=false
         CommonClass.mStudentDataArrayList.removeAt(studentPos)
         CommonClass.mStudentDataArrayList.add(studentPos, model)
         CommonClass.isInsuranceEdited = false
         CommonClass.isPassportEdited = false
         CommonClass.confirmingPosition = -1
        PreferenceManager().getInsuranceStudentList(mContext).clear()
        PreferenceManager().saveInsuranceStudentList( CommonClass.mStudentDataArrayList, mContext)
        PreferenceManager().saveInsuranceDetailArrayList(
             CommonClass.mInsuranceDetailArrayList,
            mContext
        )
    }




    requireActivity().finish()
}
        checkPassportImg.setOnClickListener {
            if (isPassportChecked) {
                isPassportChecked = false
                checkPassportImg.setImageResource(R.drawable.check_box_header)
                paasportNationalityLinear.setBackgroundResource(R.drawable.rect_background_grey)
                passportNumberTxt.setBackgroundResource(R.drawable.rect_background_grey)
                passportExpiryTxt.setBackgroundResource(R.drawable.rect_background_grey)
                UploadPasspost.alpha = 1.0f
                attachPassportTxt.alpha = 1.0f
                passportNationalityTxt.alpha = 1.0f
                passportExpiryTxt.alpha = 1.0f
                passportNumberTxt.alpha = 1.0f
                paasportNationalityLinear.alpha = 1.0f
                passportNumberTxt.isClickable = true
                passportNumberTxt.isEnabled = true
                passportExpiryTxt.isClickable = true
                passportExpiryTxt.isEnabled = true
                passportLinear.isClickable = true
                passportLinear.isEnabled = true
                UploadPasspost.isClickable = true
                UploadPasspost.isEnabled = true
                 CommonClass.isPassportEdited = true
                if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
                    Log.e("It Worked ", "BUG")
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
                    val studentID = studentId
                    Log.e("It Worked ", "BUG$studentID")
                    Log.e(
                        "It Worked ",
                        "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
                    )
                    Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
                    if ( CommonClass.mStudentDataArrayList.size > 0) {
                        for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                            if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                    .equals(student_Id)
                            ) {
                                Log.e("It Worked ", "BUG position$i")
                                studentPos = i
                                id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                                studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                                studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                                studSection =
                                     CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                                studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                                studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                                studProgressReport =
                                     CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                                studAlumini =
                                     CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                        model.isConfirmed=false
                        Log.e("It Worked ", "BUG$studentPos")
                         CommonClass.mStudentDataArrayList.removeAt(studentPos)
                         CommonClass.mStudentDataArrayList.add(studentPos, model)
                         CommonClass.isInsuranceEdited = true
                         CommonClass.confirmingPosition = studentPos
                        PreferenceManager().getInsuranceStudentList(mContext).clear()
                        PreferenceManager().saveInsuranceStudentList(
                             CommonClass.mStudentDataArrayList,
                            mContext
                        )
                    }
                }
                val dataId: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
                var dataStatus = ""
                var dataRequest = ""
                if (dataId.equals("", ignoreCase = true)) {
                    dataStatus = "0"
                    dataRequest = "1"
                } else {
                    dataStatus = "1"
                    dataRequest = "0"
                }
                val dataCreatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
                val mModel = PassportDetailModel()
                mModel.id=dataId
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.passport_number=
                     CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .original_passport_number
                
                mModel.nationality=
                     CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .original_nationality
                
                mModel.date_of_issue=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
                
                mModel.expiry_date=
                     CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .original_expiry_date
                
                mModel.visa_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
                
                mModel.passport_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
                
                mModel.visa_permit_no=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_permit_no.toString()
                
                mModel.original_expiry_date=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_expiry_date
                
                mModel.original_passport_image=""
                mModel.original_nationality=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_nationality
                
                mModel.original_passport_number=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_number
                
                mModel.not_have_a_valid_passport="0"
                if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
                    //  dateConversionYToD
                    mModel.visa_permit_expiry_date=
                        visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
                } else {
                    mModel.visa_permit_expiry_date=
                        dateConversionYToD(
                            visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
                    
                }
                mModel.visa_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image
                
                mModel.visa_image_name=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name
                
                mModel.visa_image_path=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path
                
                mModel.passport_image_path=""
                mModel.passport_image_name=""
                mModel.photo_no_consent=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
                
                mModel.visa=visaValue
                mModel.status=dataStatus
                mModel.request=dataRequest
                mModel.passport_image=""
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                mModel.isPassportDateChanged=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).isPassportDateChanged
                
                mModel.isVisaDateChanged=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
                
                 CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
                 CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
                Log.e(
                    "Ins asize",
                    java.lang.String.valueOf( CommonClass.mPassportDetailArrayList.size)
                )
                for (j in 0 until  CommonClass.mPassportDetailArrayList.size) {
                    Log.e("Ins after",  CommonClass.mPassportDetailArrayList.get(j).id.toString() + j)
                }
                PreferenceManager().getPassportDetailArrayList(mContext).clear()
                PreferenceManager().savePassportDetailArrayList(
                     CommonClass.mPassportDetailArrayList,
                    mContext
                )
                if (passportImgName.equals("", ignoreCase = true)) {
                    PassportImageName.text = ""
                } else {
                    PassportImageName.text = ""
                }
                if (! CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
                        .equals("")
                ) {
                    passportExpiryTxt.setText(
                        AppUtils().dateConversionMMM(
                             CommonClass.mPassportDetailArrayList.get(
                                dataPosition
                            ).expiry_date.toString()
                        )
                    )
                }
                if ( CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .passport_image_path.toString().equals("")
                ) {
                    ViewSelectedPassport.visibility = View.INVISIBLE
                } else {
                    ViewSelectedPassport.visibility = View.VISIBLE
                    ViewSelectedPassport.setImageBitmap(
                        BitmapFactory.decodeFile(
                             CommonClass.mPassportDetailArrayList.get(
                                dataPosition
                            ).passport_image_path.toString()
                        )
                    )
                }
                passportNumberTxt.setText(
                     CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .original_passport_number
                )
                if ( CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .original_nationality.equals("")
                ) {
                    passportNationalityTxt.setHint(R.string.AST_COUNTRY)
                } else {
                    passportNationalityTxt.setText(
                         CommonClass.mPassportDetailArrayList.get(
                            dataPosition
                        ).original_nationality
                    )
                }
            } else {
                isPassportChecked = true
                checkPassportImg.setImageResource(R.drawable.check_box_header_tick)
                paasportNationalityLinear.setBackgroundResource(R.drawable.rect_background_grey_checked)
                passportNumberTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
                passportExpiryTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
                UploadPasspost.alpha = 0.5f
                attachPassportTxt.alpha = 0.5f
                passportNationalityTxt.alpha = 0.5f
                passportExpiryTxt.alpha = 0.5f
                passportNumberTxt.alpha = 0.5f
                paasportNationalityLinear.alpha = 0.5f
                passportNumberTxt.isClickable = false
                passportNumberTxt.isEnabled = false
                passportExpiryTxt.isClickable = false
                passportExpiryTxt.isEnabled = false
                passportLinear.isClickable = false
                passportLinear.isEnabled = false
                UploadPasspost.isClickable = false
                UploadPasspost.isEnabled = false
                 CommonClass.isPassportEdited = true
                if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
                    Log.e("It Worked ", "BUG")
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
                    val studentID = studentId
                    Log.e("It Worked ", "BUG$studentID")
                    Log.e(
                        "It Worked ",
                        "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
                    )
                    Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
                    if ( CommonClass.mStudentDataArrayList.size > 0) {
                        for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                            if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                    .equals(student_Id)
                            ) {
                                Log.e("It Worked ", "BUG position$i")
                                studentPos = i
                                id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                                studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                                studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                                studSection =
                                     CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                                studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                                studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                                studProgressReport =
                                     CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                                studAlumini =
                                     CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                        model.isConfirmed=false
                        Log.e("It Worked ", "BUG$studentPos")
                         CommonClass.mStudentDataArrayList.removeAt(studentPos)
                         CommonClass.mStudentDataArrayList.add(studentPos, model)
                         CommonClass.isInsuranceEdited = true
                         CommonClass.confirmingPosition = studentPos
                        PreferenceManager().getInsuranceStudentList(mContext).clear()
                        PreferenceManager().saveInsuranceStudentList(
                             CommonClass.mStudentDataArrayList,
                            mContext
                        )
                    }
                }
                val dataId: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
                var dataStatus = ""
                var dataRequest = ""
                if (dataId.equals("", ignoreCase = true)) {
                    dataStatus = "0"
                    dataRequest = "1"
                } else {
                    dataStatus = "1"
                    dataRequest = "0"
                }
                val dataCreatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
                val mModel = PassportDetailModel()
                mModel.id=dataId
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.passport_number=
                     CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .original_passport_number
                
                mModel.nationality=
                     CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .original_nationality
                
                mModel.date_of_issue=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
                
                mModel.expiry_date=
                     CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .original_expiry_date
                
                mModel.visa_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
                
                mModel.passport_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
                
                mModel.visa_permit_no=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_permit_no.toString()
                
                mModel.original_expiry_date=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_expiry_date
                
                mModel.original_passport_image=""
                mModel.original_nationality=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_nationality
                
                mModel.original_passport_number=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_number
                
                if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
                    //  dateConversionYToD
                    mModel.visa_permit_expiry_date=
                        visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
                } else {
                   mModel.visa_permit_expiry_date=
                        dateConversionYToD(
                            visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
                    
                }
                mModel.visa_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image
                
                mModel.visa_image_name=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name
                
                mModel.visa_image_path=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path
                
                mModel.passport_image_path=""
                mModel.passport_image_name=""
                mModel.photo_no_consent=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
                
                mModel.visa=visaValue
                mModel.not_have_a_valid_passport="1"
                mModel.status=dataStatus
                mModel.request=dataRequest
                mModel.passport_image=""
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                mModel.isPassportDateChanged=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).isPassportDateChanged
                
                mModel.isVisaDateChanged=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
                
                 CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
                 CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
                Log.e(
                    "Ins asize",
                    java.lang.String.valueOf( CommonClass.mPassportDetailArrayList.size)
                )
                for (j in 0 until  CommonClass.mPassportDetailArrayList.size) {
                    Log.e("Ins after",  CommonClass.mPassportDetailArrayList.get(j).id.toString() + j)
                }
                PreferenceManager().getPassportDetailArrayList(mContext).clear()
                PreferenceManager().savePassportDetailArrayList(
                     CommonClass.mPassportDetailArrayList,
                    mContext
                )
                showDataSuccess(
                    mContext,
                    "Alert",
                    getString(R.string.passport_check),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
                if (passportImgName.equals("", ignoreCase = true)) {
                    PassportImageName.text = ""
                } else {
                    PassportImageName.text = ""
                }
                if (! CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
                        .equals("")
                ) {
                    passportExpiryTxt.setText(
                        AppUtils().dateConversionMMM(
                             CommonClass.mPassportDetailArrayList.get(
                                dataPosition
                            ).expiry_date.toString()
                        )
                    )
                }
                if ( CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .passport_image_path.toString().equals("")
                ) {
                    ViewSelectedPassport.visibility = View.INVISIBLE
                } else {
                    ViewSelectedPassport.visibility = View.VISIBLE
                    ViewSelectedPassport.setImageBitmap(
                        BitmapFactory.decodeFile(
                             CommonClass.mPassportDetailArrayList.get(
                                dataPosition
                            ).passport_image_path.toString()
                        )
                    )
                }
                if ( CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .original_nationality.equals("")
                ) {
                    passportNationalityTxt.setHint(R.string.AST_COUNTRY)
                } else {
                    passportNationalityTxt.setText(
                         CommonClass.mPassportDetailArrayList.get(
                            dataPosition
                        ).original_nationality
                    )
                }
                passportNumberTxt.setText(
                     CommonClass.mPassportDetailArrayList.get(dataPosition)
                        .original_passport_number
                )
            }
        }
        checkStudentMalysianImg.setOnClickListener {

            // System.out.println("Visa value starting"+visaValue);
            if (isStudentMalasiyanChecked) {
                visaPermitNumberTxt.setHint(R.string.AST_VISA_PERMIT_TEXT_WITH_RED)
                visaPermitExpiryTxt.setHint(R.string.AST_VISA_PERMIT_EXPIRY_WITH_RED)
                // System.out.println("if works");
                isStudentMalasiyanChecked = false
                visaPermitNumberTxt.isEnabled = true
                visaPermitExpiryTxt.isEnabled = true
                checkStudentMalysianImg.setImageResource(R.drawable.check_box_header)
                visaPermitNumberTxt.setBackgroundResource(R.drawable.rect_background_grey)
                visaPermitExpiryTxt.setBackgroundResource(R.drawable.rect_background_grey)
                uploadVisa.alpha = 1.0f
                uploadVisaTxt.alpha = 1.0f
                uploadVisa.isEnabled = true
                uploadVisa.isClickable = true
                IsNational = "YES"
                visaValue = "0"
                 CommonClass.isPassportEdited = true
                if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
                    Log.e("It Worked ", "BUG")
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
                    val studentID = studentId
                    Log.e("It Worked ", "BUG$studentID")
                    Log.e(
                        "It Worked ",
                        "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
                    )
                    Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
                    if ( CommonClass.mStudentDataArrayList.size > 0) {
                        for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                            if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                    .equals(student_Id)
                            ) {
                                Log.e("It Worked ", "BUG position$i")
                                studentPos = i
                                id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                                studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                                studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                                studSection =
                                     CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                                studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                                studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                                studProgressReport =
                                     CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                                studAlumini =
                                     CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                        model.isConfirmed=false
                        Log.e("It Worked ", "BUG$studentPos")
                         CommonClass.mStudentDataArrayList.removeAt(studentPos)
                         CommonClass.mStudentDataArrayList.add(studentPos, model)
                         CommonClass.isInsuranceEdited = true
                         CommonClass.confirmingPosition = studentPos
                        PreferenceManager().getInsuranceStudentList(mContext).clear()
                        PreferenceManager().saveInsuranceStudentList(
                             CommonClass.mStudentDataArrayList,
                            mContext
                        )
                    }
                }
                val dataId: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
                var dataStatus = ""
                var dataRequest = ""
                if (dataId.equals("", ignoreCase = true)) {
                    dataStatus = "0"
                    dataRequest = "1"
                } else {
                    dataStatus = "1"
                    dataRequest = "0"
                }
                val dataCreatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
                val mModel = PassportDetailModel()
                mModel.id=dataId
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.passport_number=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()
                
                mModel.nationality=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).nationality
                
                mModel.date_of_issue=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
                
                mModel.expiry_date=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
                
                mModel.not_have_a_valid_passport=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).not_have_a_valid_passport.toString()
                
                mModel.visa_permit_no=""
                mModel.original_expiry_date=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_expiry_date
                
                mModel.original_passport_image=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_image
                
                mModel.original_nationality=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_nationality
                
                mModel.original_passport_number=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_number
                
                if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
                    //  dateConversionYToD
                    mModel.visa_permit_expiry_date=""
                } else {
                    mModel.visa_permit_expiry_date=""
                }
                visaImageData = ""
                visa_image_name_path = ""
                visa_image_path = ""
                VisaImageName.text = ""
                ViewSelectedVisa.setImageResource(R.color.white)
                mModel.photo_no_consent=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
                
                mModel.visa=visaValue
                mModel.visa_image=""
                mModel.visa_image_name=""
                mModel.visa_image_path=""
                mModel.passport_image_path=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_path.toString()
                
                mModel.passport_image_name=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_name
                
                mModel.passport_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image
                
                mModel.status=dataStatus
                mModel.request=dataRequest
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                mModel.visa_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
                
                mModel.passport_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
                
                mModel.isPassportDateChanged=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).isPassportDateChanged
                
                mModel.isVisaDateChanged=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
                
                 CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
                 CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getPassportDetailArrayList(mContext).clear()
                PreferenceManager().savePassportDetailArrayList(
                     CommonClass.mPassportDetailArrayList,
                    mContext
                )


                /*if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited)
                {
                    int studentPos=-1;
                    String id="";
                    String studName="";
                    String studClass="";
                    String studSection="";
                    String studHouse="";
                    String studPhoto="";
                    String studProgressReport="";
                    String studAlumini="";
                    String studentID=studentId;
                    if ( CommonClass.mStudentDataArrayList.size>0)
                    {

                        for (int i=0;i< CommonClass.mStudentDataArrayList.size;i++)
                        {
                            if ( CommonClass.mStudentDataArrayList.get(i).mId.toString().equals(studentID))
                            {
                                studentPos=i;
                                id=  CommonClass.mStudentDataArrayList.get(i).mId.toString();
                                studName=  CommonClass.mStudentDataArrayList.get(i).mName.toString();
                                studClass=  CommonClass.mStudentDataArrayList.get(i).mClass.toString();
                                studSection=  CommonClass.mStudentDataArrayList.get(i).mSection.toString();
                                studHouse=  CommonClass.mStudentDataArrayList.get(i).mHouse.toString();
                                studPhoto=  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString();
                                studProgressReport= CommonClass.mStudentDataArrayList.get(i).progressReport.toString();
                                studAlumini=  CommonClass.mStudentDataArrayList.get(i).alumini.toString();
                            }
                        }
                    }
                    StudentModelNew model=new StudentModelNew();
                    model.mId=id;
                    model.mName=studName;
                    model.mClass=studClass;
                    model.mSection=studSection;
                    model.mHouse=studHouse;
                    model.mPhoto=studPhoto;
                    model.progressReport=studProgressReport;
                    model.alumini=studAlumini;
                    model.isConfirmed=false;
                     CommonClass.mStudentDataArrayList.removeAt(studentPos);
                     CommonClass.mStudentDataArrayList.add(studentPos,model);
                     CommonClass.isInsuranceEdited=false;
                     CommonClass.isPassportEdited=false;
                     CommonClass.confirmingPosition=-1;
                    PreferenceManager().getInsuranceStudentList(mContext).clear();
                    PreferenceManager().saveInsuranceStudentList( CommonClass.mStudentDataArrayList,mContext);
                    PreferenceManager().saveInsuranceDetailArrayList( CommonClass.mInsuranceDetailArrayList,mContext);
                }
*/
            } else {
                visaPermitNumberTxt.setHint(R.string.VISA_PERMIT_TXT)
                visaPermitExpiryTxt.setHint(R.string.VISA_PERMIT_EXPIRY)
                // System.out.println("if works else");
                isStudentMalasiyanChecked = true
                checkStudentMalysianImg.setImageResource(R.drawable.check_box_header_tick)
                visaPermitNumberTxt.setText("")
                visaPermitExpiryTxt.setText("")
                visaPermitNumberTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
                visaPermitExpiryTxt.setBackgroundResource(R.drawable.rect_background_grey_checked)
                uploadVisaTxt.alpha = 0.5f
                uploadVisa.alpha = 0.5f
                uploadVisa.isEnabled = false
                uploadVisa.isClickable = false
                visaPermitNumberTxt.isEnabled = false
                visaPermitExpiryTxt.isEnabled = false
                IsNational = "NO"
                visaValue = "1"
                 CommonClass.isPassportEdited = true
                if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
                    Log.e("It Worked ", "BUG")
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
                    val studentID = studentId
                    Log.e("It Worked ", "BUG$studentID")
                    Log.e(
                        "It Worked ",
                        "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
                    )
                    Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
                    if ( CommonClass.mStudentDataArrayList.size > 0) {
                        for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                            if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                    .equals(student_Id)
                            ) {
                                Log.e("It Worked ", "BUG position$i")
                                studentPos = i
                                id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                                studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                                studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                                studSection =
                                     CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                                studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                                studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                                studProgressReport =
                                     CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                                studAlumini =
                                     CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                        model.isConfirmed=false
                        Log.e("It Worked ", "BUG$studentPos")
                         CommonClass.mStudentDataArrayList.removeAt(studentPos)
                         CommonClass.mStudentDataArrayList.add(studentPos, model)
                         CommonClass.isInsuranceEdited = true
                         CommonClass.confirmingPosition = studentPos
                        PreferenceManager().getInsuranceStudentList(mContext).clear()
                        PreferenceManager().saveInsuranceStudentList(
                             CommonClass.mStudentDataArrayList,
                            mContext
                        )
                    }
                }
                val dataId: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
                var dataStatus = ""
                var dataRequest = ""
                if (dataId.equals("", ignoreCase = true)) {
                    dataStatus = "0"
                    dataRequest = "1"
                } else {
                    dataStatus = "1"
                    dataRequest = "0"
                }
                val dataCreatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
                val mModel = PassportDetailModel()
                mModel.id=dataId
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.visa_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
                
                mModel.passport_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
                
                mModel.passport_number=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()
                
                mModel.nationality=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).nationality

                mModel.date_of_issue=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
                
                mModel.expiry_date=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
                
                mModel.not_have_a_valid_passport=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).not_have_a_valid_passport.toString()
                
                mModel.visa_permit_no=""
                mModel.original_expiry_date=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_expiry_date
                
                mModel.original_passport_image=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_image
                
                mModel.original_nationality=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_nationality
                
                mModel.original_passport_number=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_number
                
                if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
                    //  dateConversionYToD
                    //System.out.println("data");
                    mModel.visa_permit_expiry_date=""
                } else {
                    mModel.visa_permit_expiry_date=""
                }
                visaImageData = ""
                visa_image_name_path = ""
                visa_image_path = ""
                VisaImageName.text = ""
                ViewSelectedVisa.setImageResource(R.color.white)
                mModel.visa_image=""
                mModel.visa_image_name=""
                mModel.visa_image_path=""
                mModel.passport_image_path=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_path.toString()
                
                mModel.passport_image_name=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_name
                
                mModel.passport_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image
                
                mModel.photo_no_consent=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
                
                mModel.visa=visaValue
                mModel.status=dataStatus
                mModel.request=dataRequest
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                mModel.visa_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
                
                mModel.passport_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
                
                mModel.isPassportDateChanged=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).isPassportDateChanged
                
                mModel.isVisaDateChanged=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
                
                 CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
                 CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
                PreferenceManager().getPassportDetailArrayList(mContext).clear()
                PreferenceManager().savePassportDetailArrayList(
                     CommonClass.mPassportDetailArrayList,
                    mContext
                )
            }
        }
        uploadVisa.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Do the file write
                chooseVisa()
            } else {
                // Request permission from the user
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    0
                )
            }
        }
        UploadPasspost.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Do the file write
                chooseImage()
            } else {
                // Request permission from the user
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    0
                )
            }
        }


        //   ConverToJson();
        visaPermitNumberTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable?) {
                 CommonClass.isPassportEdited = true
                if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
                    Log.e("It Worked ", "BUG")
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
                    val studentID = studentId
                    Log.e("It Worked ", "BUG$studentID")
                    Log.e(
                        "It Worked ",
                        "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
                    )
                    Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
                    if ( CommonClass.mStudentDataArrayList.size > 0) {
                        for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                            if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                    .equals(student_Id)
                            ) {
                                Log.e("It Worked ", "BUG position$i")
                                studentPos = i
                                id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                                studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                                studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                                studSection =
                                     CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                                studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                                studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                                studProgressReport =
                                     CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                                studAlumini =
                                     CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                        model.isConfirmed=false
                        Log.e("It Worked ", "BUG$studentPos")
                         CommonClass.mStudentDataArrayList.removeAt(studentPos)
                         CommonClass.mStudentDataArrayList.add(studentPos, model)
                         CommonClass.isInsuranceEdited = true
                         CommonClass.confirmingPosition = studentPos
                        PreferenceManager().getInsuranceStudentList(mContext).clear()
                        PreferenceManager().saveInsuranceStudentList(
                             CommonClass.mStudentDataArrayList,
                            mContext
                        )
                    }
                }
                val dataId: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
                var dataStatus = ""
                var dataRequest = ""
                if (dataId.equals("", ignoreCase = true)) {
                    dataStatus = "0"
                    dataRequest = "1"
                } else {
                    dataStatus = "1"
                    dataRequest = "0"
                }
                val dataCreatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
                val newData = visaPermitNumberTxt.text.toString()

                //  Log.e("INSURANCE ", "textChange"+);
                val mModel = PassportDetailModel()
                mModel.id=dataId
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.passport_number=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()
                
                mModel.nationality=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).nationality

                mModel.date_of_issue=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
                
                mModel.expiry_date=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
                
                mModel.not_have_a_valid_passport=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).not_have_a_valid_passport.toString()
                
                mModel.visa_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
                
                mModel.passport_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
                
                mModel.visa_permit_no=newData
                mModel.original_expiry_date=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_expiry_date
                
                mModel.original_passport_image=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_image
                
                mModel.original_nationality=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_nationality
                
                mModel.original_passport_number=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_number
                
                if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
                    //  dateConversionYToD
                    mModel.visa_permit_expiry_date=
                        visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
                } else {
                   mModel.visa_permit_expiry_date=
                        dateConversionYToD(
                            visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
                    
                }
                mModel.visa_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image
                
                mModel.visa_image_name=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name
                
                mModel.visa_image_path=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path
                
                mModel.passport_image_path=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_path.toString()
                
                mModel.passport_image_name=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_name
                
                mModel.photo_no_consent=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
                
                mModel.visa=visaValue
                mModel.status=dataStatus
                mModel.request=dataRequest
                mModel.passport_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image
                
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                mModel.isPassportDateChanged=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).isPassportDateChanged
                
                mModel.isVisaDateChanged=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
                
                 CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
                 CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
                Log.e(
                    "Ins asize",
                    java.lang.String.valueOf( CommonClass.mPassportDetailArrayList.size)
                )
                for (j in 0 until  CommonClass.mPassportDetailArrayList.size) {
                    Log.e("Ins after",  CommonClass.mPassportDetailArrayList.get(j).id.toString() + j)
                }
                PreferenceManager().getPassportDetailArrayList(mContext).clear()
                PreferenceManager().savePassportDetailArrayList(
                     CommonClass.mPassportDetailArrayList, mContext
                )
            }
        })
        passportNumberTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable?) {
                 CommonClass.isPassportEdited = true
                val dataId: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
                var dataStatus = ""
                var dataRequest = ""
                if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
                    Log.e("It Worked ", "BUG")
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
                    val studentID = studentId
                    Log.e("It Worked ", "BUG$studentID")

                    if ( CommonClass.mStudentDataArrayList.size > 0) {
                        for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                            Log.e(
                                "It Worked ",
                                "BUG" + ( CommonClass.mStudentDataArrayList.get(i).mId)
                            )
                            Log.e("It Worked ", "BUG33" + student_Id)
                            if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                    .equals(student_Id)
                            ) {
                                Log.e("It Worked ", "BUG position$i")
                                studentPos = i
                                id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                                isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                                studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                                studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                                studSection =
                                     CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                                studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                                studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                                studProgressReport =
                                     CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                                studAlumini =
                                     CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                        model.isConfirmed=false
                        Log.e("It Worked ", "BUG$studentPos")
                         CommonClass.mStudentDataArrayList.removeAt(studentPos)
                         CommonClass.mStudentDataArrayList.add(studentPos, model)
                         CommonClass.isInsuranceEdited = true
                         CommonClass.confirmingPosition = studentPos
                        PreferenceManager().getInsuranceStudentList(mContext).clear()
                        PreferenceManager().saveInsuranceStudentList(
                             CommonClass.mStudentDataArrayList,
                            mContext
                        )
                    }
                }
                if (dataId.equals("", ignoreCase = true)) {
                    dataStatus = "0"
                    dataRequest = "1"
                } else {
                    dataStatus = "1"
                    dataRequest = "0"
                }
                val dataCreatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
                val newData = passportNumberTxt.text.toString()

                //  Log.e("INSURANCE ", "textChange"+);
                val mModel = PassportDetailModel()
                mModel.id=dataId
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.passport_number=newData
                mModel.nationality=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).nationality
                
                mModel.date_of_issue=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
                
                mModel.expiry_date=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
                
                mModel.original_expiry_date=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_expiry_date
                
                mModel.original_passport_image=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_image
                
                mModel.original_nationality=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_nationality
                
                mModel.original_passport_number=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_number
                
                mModel.not_have_a_valid_passport=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).not_have_a_valid_passport.toString()
                
                mModel.visa_permit_no=newData
                if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
                    //  dateConversionYToD
                    mModel.visa_permit_expiry_date=
                        visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
                } else {
                   mModel.visa_permit_expiry_date=
                        dateConversionYToD(
                            visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
                    
                }
                mModel.visa_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image
                
                mModel.visa_image_name=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name
                
                mModel.visa_image_path=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path
                
                mModel.passport_image_path=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_path.toString()
                
                mModel.passport_image_name=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_name
                
                mModel.visa_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
                
                mModel.passport_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
                
                mModel.photo_no_consent=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
                
                mModel.visa=visaValue
                mModel.status=dataStatus
                mModel.request=dataRequest
                mModel.passport_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image
                
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                mModel.isPassportDateChanged=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).isPassportDateChanged
                
                mModel.isVisaDateChanged=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
                
                 CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
                 CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
                Log.e(
                    "Ins asize",
                    java.lang.String.valueOf( CommonClass.mPassportDetailArrayList.size)
                )
                for (j in 0 until  CommonClass.mPassportDetailArrayList.size) {
                    Log.e("Ins after",  CommonClass.mPassportDetailArrayList.get(j).id.toString() + j)
                }
                PreferenceManager().getPassportDetailArrayList(mContext).clear()
                PreferenceManager().savePassportDetailArrayList(
                     CommonClass.mPassportDetailArrayList,
                    mContext
                )
            }
        })

        visaPermitExpiryTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable?) {
                 CommonClass.isPassportEdited = true
                val dataId: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
                var dataStatus = ""
                var dataRequest = ""
                if (dataId.equals("", ignoreCase = true)) {
                    dataStatus = "0"
                    dataRequest = "1"
                } else {
                    dataStatus = "1"
                    dataRequest = "0"
                }
                //                String dataStatus="1";
//                String dataRequest="0";
                val dataCreatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
                val dataUpdatedAt: String =
                     CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
                val newData = visaPermitExpiryTxt.text.toString()

                //  Log.e("INSURANCE ", "textChange"+);
                val mModel = PassportDetailModel()
                mModel.id=dataId
                mModel.student_id=studentId
                mModel.student_name=studentNamePass
                mModel.passport_number=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()
                
                mModel.nationality=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).nationality
                
                mModel.date_of_issue=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
                
                mModel.expiry_date=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
                
                mModel.original_expiry_date=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_expiry_date
                
                mModel.original_passport_image=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_image
                
                mModel.original_nationality=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_nationality
                
                mModel.original_passport_number=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).original_passport_number
                
                mModel.not_have_a_valid_passport=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).not_have_a_valid_passport.toString()
                
                mModel.visa_permit_no=visaPermitNumberTxt.text.toString().trim { it <= ' ' }
                if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
                    //  dateConversionYToD
                    mModel.visa_permit_expiry_date=
                        visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
                } else {
                   mModel.visa_permit_expiry_date=
                        dateConversionYToD(
                            visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
                    
                }
                mModel.visa_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image
                
                mModel.visa_image_name=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name
                
                mModel.visa_image_path=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path
                
                mModel.passport_image_path=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_path.toString()
                
                mModel.passport_image_name=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).passport_image_name
                
                mModel.photo_no_consent=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
                
                mModel.status=dataStatus
                mModel.request=dataRequest
                mModel.visa=visaValue
                mModel.passport_image=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image
                
                mModel.created_at=dataCreatedAt
                mModel.updated_at=dataUpdatedAt
                mModel.visa_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
                
                mModel.passport_expired=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
                
                mModel.isPassportDateChanged=
                     CommonClass.mPassportDetailArrayList.get(
                        dataPosition
                    ).isPassportDateChanged
                
                mModel.isVisaDateChanged=
                     CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
                
                 CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
                 CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
                Log.e(
                    "Ins asize",
                    java.lang.String.valueOf( CommonClass.mPassportDetailArrayList.size)
                )
                for (j in 0 until  CommonClass.mPassportDetailArrayList.size) {
                    Log.e("Ins after",  CommonClass.mPassportDetailArrayList.get(j).id.toString() + j)
                }
                PreferenceManager().getPassportDetailArrayList(mContext).clear()
                PreferenceManager().savePassportDetailArrayList(
                     CommonClass.mPassportDetailArrayList,
                    mContext
                )
            }
        })

    }
    private fun updateLabel() {
        val myFormat = "yyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        DATE = sdf.format(myCalendar.time)
        visaPermitExpiryTxt.setText(AppUtils().dateConversionMMM(DATE))
         CommonClass.isPassportEdited = true
        val dataId: String =  CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
        var dataStatus = ""
        var dataRequest = ""
        if (dataId.equals("", ignoreCase = true)) {
            dataStatus = "0"
            dataRequest = "1"
        } else {
            dataStatus = "1"
            dataRequest = "0"
        }
        if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
            Log.e("It Worked ", "BUG")
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
            val studentID = studentId
            Log.e("It Worked ", "BUG$studentID")
            Log.e(
                "It Worked ",
                "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
            )
            Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
            if ( CommonClass.mStudentDataArrayList.size > 0) {
                for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                    if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                            .equals(student_Id)
                    ) {
                        Log.e("It Worked ", "BUG position$i")
                        studentPos = i
                        id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                        isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                        studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                        studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                        studSection =  CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                        studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                        studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                        studProgressReport =
                             CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                        studAlumini =  CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                model.isConfirmed=false
                Log.e("It Worked ", "BUG$studentPos")
                 CommonClass.mStudentDataArrayList.removeAt(studentPos)
                 CommonClass.mStudentDataArrayList.add(studentPos, model)
                 CommonClass.isInsuranceEdited = true
                 CommonClass.confirmingPosition = studentPos
                PreferenceManager().getInsuranceStudentList(mContext).clear()
                PreferenceManager().saveInsuranceStudentList(
                     CommonClass.mStudentDataArrayList,
                    mContext
                )
            }
        }
        val dataCreatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
        val dataUpdatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
        val newData = passportExpiryTxt.text.toString()

        //  Log.e("INSURANCE ", "textChange"+);
        val mModel = PassportDetailModel()
        mModel.id=dataId
        mModel.student_id=studentId
        mModel.student_name=studentNamePass
        mModel.passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()

        mModel.nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).nationality
        
        mModel.date_of_issue=
             CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
        
        mModel.visa_permit_no=visaPermitNumberTxt.text.toString().trim { it <= ' ' }
        if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
            //  dateConversionYToD
            mModel.visa_permit_expiry_date=
                visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
        } else {
            mModel.visa_permit_expiry_date=
                dateConversionYToD(
                    visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
            
        }
        if (passportExpiryTxt.text.toString().equals("", ignoreCase = true)) {
            //  dateConversionYToD
            mModel.expiry_date=passportExpiryTxt.text.toString().trim { it <= ' ' }
        } else {
            mModel.expiry_date=
                dateConversionYToD(
                    passportExpiryTxt.text.toString().trim { it <= ' ' })
            
        }
        mModel.original_expiry_date=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_expiry_date
        
        mModel.original_passport_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_image
        
        mModel.original_nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_nationality
        
        mModel.original_passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_number
        
        mModel.not_have_a_valid_passport=
             CommonClass.mPassportDetailArrayList.get(dataPosition).not_have_a_valid_passport.toString()
        
        mModel.visa_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image
        
        mModel.visa_image_name=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name
        
        mModel.visa_image_path=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path
        
        mModel.passport_image_path=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image_path.toString()
        
        mModel.passport_image_name=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image_name

        mModel.photo_no_consent=
             CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent

        mModel.visa=visaValue
        mModel.status=dataStatus
        mModel.request=dataRequest
        mModel.passport_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image

        mModel.created_at=dataCreatedAt
        mModel.updated_at=dataUpdatedAt
        mModel.visa_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired

        mModel.passport_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired

        mModel.isPassportDateChanged=
             CommonClass.mPassportDetailArrayList.get(dataPosition).isPassportDateChanged

        mModel.isVisaDateChanged=true
         CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
         CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
        Log.e("Ins asize", java.lang.String.valueOf( CommonClass.mPassportDetailArrayList.size))
        for (j in 0 until  CommonClass.mPassportDetailArrayList.size) {
            Log.e("Ins after",  CommonClass.mPassportDetailArrayList.get(j).id.toString() + j)
        }
        PreferenceManager().getPassportDetailArrayList(mContext).clear()
        PreferenceManager().savePassportDetailArrayList(
             CommonClass.mPassportDetailArrayList,
            mContext
        )
    }

    private fun updateLabelPassport() {
        val myFormat = "yyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        DATE = sdf.format(myCalendar.time)
        passportExpiryTxt.setText(AppUtils().dateConversionMMM(DATE))
         CommonClass.isPassportEdited = true
        if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
            Log.e("It Worked ", "BUG")
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
            val studentID = studentId
            Log.e("It Worked ", "BUG$studentID")
            Log.e(
                "It Worked ",
                "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
            )
            Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
            if ( CommonClass.mStudentDataArrayList.size > 0) {
                for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                    if (CommonClass.mStudentDataArrayList.get(i).mId.toString()
                            .equals(student_Id)
                    ) {
                        Log.e("It Worked ", "BUG position$i")
                        studentPos = i
                        id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                        isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                        studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                        studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                        studSection =  CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                        studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                        studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                        studProgressReport =
                             CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                        studAlumini =  CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                model.isConfirmed=false
                Log.e("It Worked ", "BUG$studentPos")
                 CommonClass.mStudentDataArrayList.removeAt(studentPos)
                 CommonClass.mStudentDataArrayList.add(studentPos, model)
                 CommonClass.isInsuranceEdited = true
                 CommonClass.confirmingPosition = studentPos
                PreferenceManager().getInsuranceStudentList(mContext).clear()
                PreferenceManager().saveInsuranceStudentList(
                     CommonClass.mStudentDataArrayList,
                    mContext
                )
            }
        }
        val dataId: String =  CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
        var dataStatus = ""
        var dataRequest = ""
        if (dataId.equals("", ignoreCase = true)) {
            dataStatus = "0"
            dataRequest = "1"
        } else {
            dataStatus = "1"
            dataRequest = "0"
        }
        val dataCreatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
        val dataUpdatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
        val newData = passportExpiryTxt.text.toString()

        //  Log.e("INSURANCE ", "textChange"+);
        val mModel = PassportDetailModel()
        mModel.id=dataId
        mModel.student_id=studentId
        mModel.student_name=studentNamePass
        mModel.passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()

        mModel.nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).nationality

        mModel.date_of_issue=
             CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue

        mModel.visa_permit_no=visaPermitNumberTxt.text.toString().trim { it <= ' ' }
        if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
            //  dateConversionYToD
            mModel.visa_permit_expiry_date=
                visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
        } else {
            mModel.visa_permit_expiry_date=
                dateConversionYToD(
                    visaPermitExpiryTxt.text.toString().trim { it <= ' ' })

        }
        if (passportExpiryTxt.text.toString().equals("", ignoreCase = true)) {
            //  dateConversionYToD
            mModel.expiry_date=passportExpiryTxt.text.toString().trim { it <= ' ' }
        } else {
            mModel.expiry_date=
                dateConversionYToD(
                    passportExpiryTxt.text.toString().trim { it <= ' ' })

        }
        mModel.original_expiry_date=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_expiry_date

        mModel.original_passport_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_image

        mModel.original_nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_nationality

        mModel.original_passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_number

        mModel.not_have_a_valid_passport=
             CommonClass.mPassportDetailArrayList.get(dataPosition).not_have_a_valid_passport.toString()

        mModel.visa_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image

        mModel.visa_image_name=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name

        mModel.visa_image_path=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path

        mModel.passport_image_path=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image_path.toString()

        mModel.passport_image_name=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image_name

        mModel.photo_no_consent=
             CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent

        mModel.visa=visaValue
        mModel.status=dataStatus
        mModel.visa_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired

        mModel.passport_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired

        mModel.request=dataRequest
        mModel.passport_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image

        mModel.created_at=dataCreatedAt
        mModel.updated_at=dataUpdatedAt
        mModel.isPassportDateChanged=true
        mModel.isVisaDateChanged
             CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged

         CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
         CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
        Log.e("Ins asize", java.lang.String.valueOf( CommonClass.mPassportDetailArrayList.size))
        for (j in 0 until  CommonClass.mPassportDetailArrayList.size) {
            Log.e("Ins after",  CommonClass.mPassportDetailArrayList.get(j).id.toString() + j)
        }
        PreferenceManager().getPassportDetailArrayList(mContext).clear()
        PreferenceManager().savePassportDetailArrayList(
             CommonClass.mPassportDetailArrayList,
            mContext
        )
    }

    private fun chooseVisa() {
        val options = arrayOf<CharSequence>("Open Camera", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Open Camera") {
                val imageFileName = System.currentTimeMillis().toString() + ".jpg"
                val storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                )
                pictureImagePath = storageDir.absolutePath + "/" + imageFileName
                val file = File(pictureImagePath)
                val outputFileUri = Uri.fromFile(file)
                val cameraIntent =
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 0)
                startActivityForResult(cameraIntent, VISA_CAMERA_REQUEST)
            } else if (options[item] == "Choose from Gallery") {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, VISA_GALLERY_REQUEST)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun chooseImage() {
        val options = arrayOf<CharSequence>("Open Camera", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Open Camera") {
                val imageFileName = System.currentTimeMillis().toString() + ".jpg"
                val storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                )
                pictureImagePath = storageDir.absolutePath + "/" + imageFileName
                val file = File(pictureImagePath)
                val outputFileUri = Uri.fromFile(file)
                val cameraIntent =
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 0)
                startActivityForResult(cameraIntent, CLICK_IMAGE_REQUEST)
            } else if (options[item] == "Choose from Gallery") {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                showError("Failed to open picture!")
                return
            }
            try {
                actualImage = FileUtil.from(mContext, data.data)
                compressedImage = Compressor.Builder(mContext)
                    .setMaxWidth(940F)
                    .setMaxHeight(800F)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES
                        ).absolutePath
                    )
                    .build()
                    .compressToFile(actualImage)
                setCompressedImage()
                passport_image_name_path = compressedImage.name
                PassportImageName.text = compressedImage.name

                //clearImage();
            } catch (e: IOException) {
                showError("Failed to read picture data!")
                e.printStackTrace()
            }
        }
        if (requestCode == CLICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            PassportCamera = File(pictureImagePath)
            if (PassportCamera.exists()) {
                try {
                    if (data != null) {
                        PassportCamera = FileUtil.from(mContext, data.data)
                    }
                    CompressPassportCamera = Compressor.Builder(mContext)
                        .setMaxWidth(940F)
                        .setMaxHeight(800F)
                        .setQuality(100)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(
                            Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES
                            ).absolutePath
                        )
                        .build()
                        .compressToFile(PassportCamera)
                    CompressPassportCamera()
                    PassportImageName.text = CompressPassportCamera.name
                    passport_image_name_path = CompressPassportCamera.name

//                  Toast.makeText(mContext, "Compressed image save in " + compressedImage.getPath(), Toast.LENGTH_LONG).show();
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        if (requestCode == VISA_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            VisaCamera = File(pictureImagePath)
            if (VisaCamera.exists()) {
                try {
                    if (data != null) {
                        VisaCamera = FileUtil.from(mContext, data.data)
                    }
                    CompressVisaCamera = Compressor.Builder(mContext)
                        .setMaxWidth(940F)
                        .setMaxHeight(800F)
                        .setQuality(100)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(
                            Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES
                            ).absolutePath
                        )
                        .build()
                        .compressToFile(VisaCamera)
                    CompressVISAcamera()
                    VisaImageName.text = CompressVisaCamera.name
                    visa_image_name_path = CompressVisaCamera.name
                    //                    Toast.makeText(mContext, "Compressed image save in " + compressedImage.getPath(), Toast.LENGTH_LONG).show();
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        if (requestCode == VISA_GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                showError("Failed to open picture!")
                return
            }
            try {
                VisaactualImage = FileUtil.from(mContext, data.data)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                    VisacompressedImage = Compressor.Builder(mContext)
                        .setMaxWidth(940F)
                        .setMaxHeight(800F)
                        .setQuality(100)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(
                            Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES
                            ).absolutePath
                        )
                        .build()
                        .compressToFile(VisaactualImage)
                }
                setVisaCompressedImage()
                VisaImageName.text = VisacompressedImage.name
                visa_image_name_path = VisacompressedImage.name
                //clearImage();
            } catch (e: IOException) {
                showError("Failed to read picture data!")
                e.printStackTrace()
            }
        }
    }

    private fun CompressPassportCamera() {
        val bitmap: Bitmap
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        bitmap = BitmapFactory.decodeFile(CompressPassportCamera.path, options)
        ViewSelectedPassport.visibility = View.VISIBLE
        ViewSelectedPassport.setImageBitmap(BitmapFactory.decodeFile(CompressPassportCamera.path))
        passport_image_path = CompressPassportCamera.path
        passport_image_name_path = CompressPassportCamera.name
        var inputStream: InputStream? = null // You can get an inputStream using any I/O API
        try {
            inputStream = FileInputStream(CompressPassportCamera.path)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val bytes: ByteArray
        val buffer = ByteArray(8192)
        var bytesRead: Int
        val output = ByteArrayOutputStream()
        try {
            while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                output.write(buffer, 0, bytesRead)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        bytes = output.toByteArray()
        val encodedString = Base64.encodeToString(bytes, Base64.DEFAULT)
        passportImageData = encodedString
         CommonClass.isPassportEdited = true
        if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
            Log.e("It Worked ", "BUG")
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
            val studentID = studentId
            Log.e("It Worked ", "BUG$studentID")
            Log.e(
                "It Worked ",
                "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
            )
            Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
            if ( CommonClass.mStudentDataArrayList.size > 0) {
                for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                    if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                            .equals(studentID)
                    ) {
                        Log.e("It Worked ", "BUG position$i")
                        studentPos = i
                        id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                        isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                        studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                        studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                        studSection =  CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                        studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                        studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                        studProgressReport =
                             CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                        studAlumini =  CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                model.isConfirmed=false
                Log.e("It Worked ", "BUG$studentPos")
                 CommonClass.mStudentDataArrayList.removeAt(studentPos)
                 CommonClass.mStudentDataArrayList.add(studentPos, model)
                 CommonClass.isInsuranceEdited = true
                 CommonClass.confirmingPosition = studentPos
                PreferenceManager().getInsuranceStudentList(mContext).clear()
                PreferenceManager().saveInsuranceStudentList(
                     CommonClass.mStudentDataArrayList,
                    mContext
                )
            }
        }
        val dataId: String =  CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
        var dataStatus = ""
        var dataRequest = ""
        if (dataId.equals("", ignoreCase = true)) {
            dataStatus = "0"
            dataRequest = "1"
        } else {
            dataStatus = "1"
            dataRequest = "0"
        }
        val dataCreatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
        val dataUpdatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
        //   String newData=visaPermitNumberTxt.getText().toString();

        //  Log.e("INSURANCE ", "textChange"+);
        val mModel = PassportDetailModel()
        mModel.id=dataId
        mModel.student_id=studentId
        mModel.student_name=studentNamePass
        mModel.passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()
        
        mModel.nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).nationality
        
        mModel.date_of_issue=
             CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
        
        mModel.expiry_date=
             CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
        
        mModel.not_have_a_valid_passport=
             CommonClass.mPassportDetailArrayList.get(dataPosition).not_have_a_valid_passport.toString()
        
        mModel.visa_permit_no=visaPermitNumberTxt.text.toString().trim { it <= ' ' }
        if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
            //  dateConversionYToD
            mModel.visa_permit_expiry_date=
                visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
        } else {
            mModel.visa_permit_expiry_date=
                dateConversionYToD(
                    visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
            
        }
        mModel.original_expiry_date=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_expiry_date
        
        mModel.original_passport_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_image
        
        mModel.original_nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_nationality
        
        mModel.original_passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_number
        
        mModel.photo_no_consent=
             CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
        
        mModel.visa=visaValue
        mModel.passport_image=passportImageData
        mModel.passport_image_path=passport_image_path
        mModel.passport_image_name=passport_image_name_path
        mModel.visa_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image
        
        mModel.visa_image_name=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name
        
        mModel.visa_image_path=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path
        
        mModel.status=dataStatus
        mModel.request=dataRequest
        mModel.created_at=dataCreatedAt
        mModel.updated_at=dataUpdatedAt
        mModel.visa_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
        
        mModel.passport_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
        
        mModel.isPassportDateChanged=
             CommonClass.mPassportDetailArrayList.get(dataPosition).isPassportDateChanged
        
        mModel.isVisaDateChanged
             CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
        
         CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
         CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
        PreferenceManager().getPassportDetailArrayList(mContext).clear()
        PreferenceManager().savePassportDetailArrayList(
             CommonClass.mPassportDetailArrayList,
            mContext
        )

//        PassportImageModel model = new PassportImageModel();
//        model.setPassportPath(CompressPassportCamera.getPath());
//        model.setStudent_id("upload_passport_"+stud_id);
//
//        ThirdSubmitArray.add(model);
//        PreferenceManager().savePassportPathArrayList(ThirdSubmitArray,mContext);
    }

    private fun CompressVISAcamera() {
        val bitmap: Bitmap
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        bitmap = BitmapFactory.decodeFile(CompressVisaCamera.path, options)
        ViewSelectedVisa.setImageBitmap(BitmapFactory.decodeFile(CompressVisaCamera.path))
        visa_image_path = CompressVisaCamera.path
        visa_image_name_path = CompressVisaCamera.name
        var inputStream: InputStream? = null // You can get an inputStream using any I/O API
        try {
            inputStream = FileInputStream(CompressVisaCamera.path)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val bytes: ByteArray
        val buffer = ByteArray(8192)
        var bytesRead: Int
        val output = ByteArrayOutputStream()
        try {
            while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                output.write(buffer, 0, bytesRead)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        bytes = output.toByteArray()
        val encodedString = Base64.encodeToString(bytes, Base64.DEFAULT)
        visaImageData = encodedString
         CommonClass.isPassportEdited = true
        if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
            Log.e("It Worked ", "BUG")
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
            val studentID = studentId
            Log.e("It Worked ", "BUG$studentID")
            Log.e(
                "It Worked ",
                "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
            )
            Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
            if ( CommonClass.mStudentDataArrayList.size > 0) {
                for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                    if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                            .equals(studentID)
                    ) {
                        Log.e("It Worked ", "BUG position$i")
                        studentPos = i
                        id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                        isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                        studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                        studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                        studSection =  CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                        studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                        studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                        studProgressReport =
                             CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                        studAlumini =  CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                model.isConfirmed=false
                Log.e("It Worked ", "BUG$studentPos")
                 CommonClass.mStudentDataArrayList.removeAt(studentPos)
                 CommonClass.mStudentDataArrayList.add(studentPos, model)
                 CommonClass.isInsuranceEdited = true
                 CommonClass.confirmingPosition = studentPos
                PreferenceManager().getInsuranceStudentList(mContext).clear()
                PreferenceManager().saveInsuranceStudentList(
                     CommonClass.mStudentDataArrayList,
                    mContext
                )
            }
        }
        val dataId: String =  CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
        var dataStatus = ""
        var dataRequest = ""
        if (dataId.equals("", ignoreCase = true)) {
            dataStatus = "0"
            dataRequest = "1"
        } else {
            dataStatus = "1"
            dataRequest = "0"
        }
        val dataCreatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
        val dataUpdatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
        //   String newData=visaPermitNumberTxt.getText().toString();

        //  Log.e("INSURANCE ", "textChange"+);
        val mModel = PassportDetailModel()
        mModel.id=dataId
        mModel.student_id=studentId
        mModel.student_name=studentNamePass
        mModel.passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()
        
        mModel.nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).nationality
        
        mModel.date_of_issue=
             CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
        
        mModel.expiry_date=
             CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
        
        mModel.not_have_a_valid_passport=
             CommonClass.mPassportDetailArrayList.get(dataPosition).not_have_a_valid_passport.toString()
        
        mModel.visa_permit_no=visaPermitNumberTxt.text.toString().trim { it <= ' ' }
        if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
            //  dateConversionYToD
            mModel.visa_permit_expiry_date=
                visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
        } else {
            mModel.visa_permit_expiry_date=
                dateConversionYToD(
                    visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
            )
        }
        mModel.original_expiry_date=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_expiry_date
        
        mModel.original_passport_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_image
        
        mModel.original_nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_nationality
        
        mModel.original_passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_number
        
        mModel.photo_no_consent=
             CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
        
        mModel.visa=visaValue
        mModel.status=dataStatus
        mModel.request=dataRequest
        mModel.created_at=dataCreatedAt
        mModel.updated_at=dataUpdatedAt
        mModel.visa_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
        
        mModel.passport_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
        
        mModel.isPassportDateChanged=
             CommonClass.mPassportDetailArrayList.get(dataPosition).isPassportDateChanged
        
        mModel.isVisaDateChanged
             CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
        
        mModel.visa_image=visaImageData
        mModel.visa_image_name=visa_image_name_path
        mModel.visa_image_path=visa_image_path
        mModel.passport_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image
        
        mModel.passport_image_path=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image_path.toString()
        
        mModel.passport_image_name=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image_name
        
         CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
         CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
        PreferenceManager().getPassportDetailArrayList(mContext).clear()
        PreferenceManager().savePassportDetailArrayList(
             CommonClass.mPassportDetailArrayList,
            mContext
        )
    }

    private fun setVisaCompressedImage() {
        Toast.makeText(
            mContext,
            "Compressed image save in " + VisacompressedImage.path,
            Toast.LENGTH_LONG
        ).show()
        Log.d("Compressor", "Compressed image save in " + VisacompressedImage.path)
        val bitmap: Bitmap
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        bitmap = BitmapFactory.decodeFile(VisacompressedImage.path, options)
        ViewSelectedVisa.setImageBitmap(BitmapFactory.decodeFile(VisacompressedImage.path))
        visa_image_path = VisacompressedImage.path
        visa_image_name_path = VisacompressedImage.name
        var inputStream: InputStream? = null // You can get an inputStream using any I/O API
        try {
            inputStream = FileInputStream(VisacompressedImage.path)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val bytes: ByteArray
        val buffer = ByteArray(8192)
        var bytesRead: Int
        val output = ByteArrayOutputStream()
        try {
            while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                output.write(buffer, 0, bytesRead)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        bytes = output.toByteArray()
        val encodedString = Base64.encodeToString(bytes, Base64.DEFAULT)
        visaImageData = encodedString
         CommonClass.isPassportEdited = true
        if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
            Log.e("It Worked ", "BUG")
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
            val studentID = studentId
            Log.e("It Worked ", "BUG$studentID")
            Log.e(
                "It Worked ",
                "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
            )
            Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
            if ( CommonClass.mStudentDataArrayList.size > 0) {
                for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                    if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                            .equals(student_Id)
                    ) {
                        Log.e("It Worked ", "BUG position$i")
                        studentPos = i
                        id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                        isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                        studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                        studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                        studSection =  CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                        studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                        studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                        studProgressReport =
                             CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                        studAlumini =  CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                model.isConfirmed=false
                Log.e("It Worked ", "BUG$studentPos")
                 CommonClass.mStudentDataArrayList.removeAt(studentPos)
                 CommonClass.mStudentDataArrayList.add(studentPos, model)
                 CommonClass.isInsuranceEdited = true
                 CommonClass.confirmingPosition = studentPos
                PreferenceManager().getInsuranceStudentList(mContext).clear()
                PreferenceManager().saveInsuranceStudentList(
                     CommonClass.mStudentDataArrayList,
                    mContext
                )
            }
        }
        val dataId: String =  CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
        var dataStatus = ""
        var dataRequest = ""
        if (dataId.equals("", ignoreCase = true)) {
            dataStatus = "0"
            dataRequest = "1"
        } else {
            dataStatus = "1"
            dataRequest = "0"
        }
        val dataCreatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
        val dataUpdatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
        //   String newData=visaPermitNumberTxt.getText().toString();

        //  Log.e("INSURANCE ", "textChange"+);
        val mModel = PassportDetailModel()
        mModel.id=dataId
        mModel.student_id=studentId
        mModel.student_name=studentNamePass
        mModel.passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()
        
        mModel.nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).nationality
        
        mModel.date_of_issue=
             CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
        
        mModel.expiry_date=
             CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
        
        mModel.not_have_a_valid_passport=
             CommonClass.mPassportDetailArrayList.get(dataPosition).not_have_a_valid_passport.toString()
        
        mModel.visa_permit_no=visaPermitNumberTxt.text.toString().trim { it <= ' ' }
        if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
            //  dateConversionYToD
            mModel.visa_permit_expiry_date=
                visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
        } else {
            mModel.visa_permit_expiry_date=
                dateConversionYToD(
                    visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
            
        }
        mModel.original_expiry_date=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_expiry_date
        
        mModel.original_passport_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_image
        
        mModel.original_nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_nationality
        
        mModel.original_passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_number
        
        mModel.visa_image=visaImageData
        mModel.photo_no_consent=
             CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
        
        mModel.visa=visaValue
        mModel.visa_image_name=visa_image_name_path
        mModel.visa_image_path=visa_image_path
        mModel.passport_image_path=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image_path.toString()
        
        mModel.passport_image_name=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image_name
        
        mModel.status=dataStatus
        mModel.request=dataRequest
        mModel.passport_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_image
        
        mModel.created_at=dataCreatedAt
        mModel.updated_at=dataUpdatedAt
        mModel.visa_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
        
        mModel.passport_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
        
        mModel.isPassportDateChanged=
             CommonClass.mPassportDetailArrayList.get(dataPosition).isPassportDateChanged
        
        mModel.isVisaDateChanged
             CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
        
         CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
         CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
        PreferenceManager().getPassportDetailArrayList(mContext).clear()
        PreferenceManager().savePassportDetailArrayList(
             CommonClass.mPassportDetailArrayList,
            mContext
        )
    }

    fun showError(errorMessage: String?) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun setCompressedImage() {
        Toast.makeText(
            mContext,
            "Compressed image save in " + compressedImage.path,
            Toast.LENGTH_LONG
        ).show()
        Log.d("Compressor", "Compressed image save in " + compressedImage.path)
        val bitmap: Bitmap
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        bitmap = BitmapFactory.decodeFile(compressedImage.path, options)
        ViewSelectedPassport.visibility = View.VISIBLE
        ViewSelectedPassport.setImageBitmap(BitmapFactory.decodeFile(compressedImage.path))
        passport_image_path = compressedImage.path
        passport_image_name_path = compressedImage.name
        var inputStream: InputStream? = null // You can get an inputStream using any I/O API
        try {
            inputStream = FileInputStream(compressedImage.path)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val bytes: ByteArray
        val buffer = ByteArray(8192)
        var bytesRead: Int
        val output = ByteArrayOutputStream()
        try {
            while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                output.write(buffer, 0, bytesRead)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        bytes = output.toByteArray()
        val encodedString = Base64.encodeToString(bytes, Base64.DEFAULT)
        passportImageData = encodedString
         CommonClass.isPassportEdited = true
        if ( CommonClass.isInsuranceEdited ||  CommonClass.isPassportEdited) {
            Log.e("It Worked ", "BUG")
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
            val studentID = studentId
            Log.e("It Worked ", "BUG$studentID")
            Log.e(
                "It Worked ",
                "BUG" + java.lang.String.valueOf( CommonClass.mStudentDataArrayList.size)
            )
            Log.e("It Worked ", "BUG" +  CommonClass.mStudentDataArrayList.get(0).mIsams_id.toString())
            if ( CommonClass.mStudentDataArrayList.size > 0) {
                for (i in 0 until  CommonClass.mStudentDataArrayList.size) {
                    if ( CommonClass.mStudentDataArrayList.get(i).mId.toString()
                            .equals(student_Id)
                    ) {
                        Log.e("It Worked ", "BUG position$i")
                        studentPos = i
                        id =  CommonClass.mStudentDataArrayList.get(i).mId.toString()
                        isams_id = CommonClass.mStudentDataArrayList.get(i).mIsams_id.toString()
                        studName =  CommonClass.mStudentDataArrayList.get(i).mName.toString()
                        studClass =  CommonClass.mStudentDataArrayList.get(i).mClass.toString()
                        studSection =  CommonClass.mStudentDataArrayList.get(i).mSection.toString()
                        studHouse =  CommonClass.mStudentDataArrayList.get(i).mHouse.toString()
                        studPhoto =  CommonClass.mStudentDataArrayList.get(i).mPhoto.toString()
                        studProgressReport =
                             CommonClass.mStudentDataArrayList.get(i).progressReport.toString()
                        studAlumini =  CommonClass.mStudentDataArrayList.get(i).alumini.toString()
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
                model.isConfirmed=false
                Log.e("It Worked ", "BUG$studentPos")
                 CommonClass.mStudentDataArrayList.removeAt(studentPos)
                 CommonClass.mStudentDataArrayList.add(studentPos, model)
                 CommonClass.isInsuranceEdited = true
                 CommonClass.confirmingPosition = studentPos
                PreferenceManager().getInsuranceStudentList(mContext).clear()
                PreferenceManager().saveInsuranceStudentList(
                     CommonClass.mStudentDataArrayList,
                    mContext
                )
            }
        }
        val dataId: String =  CommonClass.mPassportDetailArrayList.get(dataPosition).id.toString()
        var dataStatus = ""
        var dataRequest = ""
        if (dataId.equals("", ignoreCase = true)) {
            dataStatus = "0"
            dataRequest = "1"
        } else {
            dataStatus = "1"
            dataRequest = "0"
        }
        val dataCreatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).created_at.toString()
        val dataUpdatedAt: String =
             CommonClass.mPassportDetailArrayList.get(dataPosition).updated_at.toString()
        //   String newData=visaPermitNumberTxt.getText().toString();

        //  Log.e("INSURANCE ", "textChange"+);
        val mModel = PassportDetailModel()
        mModel.id=dataId
        mModel.student_id=studentId
        mModel.student_name=studentNamePass
        mModel.passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_number.toString()
        
        mModel.nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).nationality
        
        mModel.date_of_issue=
             CommonClass.mPassportDetailArrayList.get(dataPosition).date_of_issue
        
        mModel.expiry_date=
             CommonClass.mPassportDetailArrayList.get(dataPosition).expiry_date.toString()
        
        mModel.not_have_a_valid_passport=
             CommonClass.mPassportDetailArrayList.get(dataPosition).not_have_a_valid_passport.toString()
        
        mModel.visa_permit_no=visaPermitNumberTxt.text.toString().trim { it <= ' ' }
        if (visaPermitExpiryTxt.text.toString().equals("", ignoreCase = true)) {
            //  dateConversionYToD
            mModel.visa_permit_expiry_date=
                visaPermitExpiryTxt.text.toString().trim { it <= ' ' }
        } else {
            mModel.visa_permit_expiry_date=
                dateConversionYToD(
                    visaPermitExpiryTxt.text.toString().trim { it <= ' ' })
            
        }
        mModel.original_expiry_date=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_expiry_date
        
        mModel.original_passport_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_image
        
        mModel.original_nationality=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_nationality
        
        mModel.original_passport_number=
             CommonClass.mPassportDetailArrayList.get(dataPosition).original_passport_number
        
        mModel.photo_no_consent=
             CommonClass.mPassportDetailArrayList.get(dataPosition).photo_no_consent
        
        mModel.visa=visaValue
        mModel.status=dataStatus
        mModel.visa_image=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image
        
        mModel.visa_image_name=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_name
        
        mModel.visa_image_path=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_image_path
        
        mModel.passport_image_path=passport_image_path
        mModel.passport_image_name=passport_image_name_path
        mModel.request=dataRequest
        mModel.passport_image=passportImageData
        mModel.created_at=dataCreatedAt
        mModel.updated_at=dataUpdatedAt
        mModel.visa_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).visa_expired
        
        mModel.passport_expired=
             CommonClass.mPassportDetailArrayList.get(dataPosition).passport_expired
        
        mModel.isPassportDateChanged=
             CommonClass.mPassportDetailArrayList.get(dataPosition).isPassportDateChanged
        
        mModel.isVisaDateChanged
             CommonClass.mPassportDetailArrayList.get(dataPosition).isVisaDateChanged
        
         CommonClass.mPassportDetailArrayList.removeAt(dataPosition)
         CommonClass.mPassportDetailArrayList.add(dataPosition, mModel)
        PreferenceManager().getPassportDetailArrayList(mContext).clear()
        PreferenceManager().savePassportDetailArrayList(
             CommonClass.mPassportDetailArrayList,
            mContext
        )
    }
    private fun showDataSuccess(
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