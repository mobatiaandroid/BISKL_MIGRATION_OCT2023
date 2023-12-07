package com.example.bskl_kotlin.manager

import android.content.res.TypedArray
import android.widget.LinearLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bskl_kotlin.activity.datacollection_p2.model.InsuranceDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.KinModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.PassportDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.StudentModelNew
import com.example.bskl_kotlin.activity.home.model.NationalityModel

class CommonClass {
    companion object{
       var confirmingPosition = -1
        var mPassportDetailArrayList: ArrayList<PassportDetailModel> =
            ArrayList<PassportDetailModel>()
        var isInsuranceEdited = false
        var isPassportEdited = false
        var mInsuranceDetailArrayList: ArrayList<InsuranceDetailModel> =
            ArrayList<InsuranceDetailModel>()
        //var kinArrayShow: ArrayList<KinModel> = ArrayList<KinModel>()
        var kinArrayPass: ArrayList<KinModel> = ArrayList<KinModel>()
        var isKinEdited = false
        var mStudentDataArrayList: ArrayList<StudentModelNew> =
            ArrayList<StudentModelNew>()
        var mDrawerLayouts: DrawerLayout? = null
        var mLinearLayouts: LinearLayout? = null
        var listitemArrays: Array<String>?=null
        var mListImgArrays: TypedArray? = null
        var mTitles: String? = null
        var mNationalityArrayList: ArrayList<NationalityModel> =
            ArrayList<NationalityModel>()
    }
}