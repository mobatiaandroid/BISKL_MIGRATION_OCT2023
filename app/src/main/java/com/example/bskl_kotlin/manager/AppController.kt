package com.example.bskl_kotlin.manager

import android.app.Application
import com.example.bskl_kotlin.activity.datacollection_p2.model.KinModel


class AppController: Application() {
  companion object{
      var instance: AppController? = null
      var kinArrayShow = ArrayList<KinModel>()
      var kinArrayPass = ArrayList<KinModel>()
      var isKinEdited = false
      fun applicationContext(): AppController {
          return instance as AppController
      }
  }

    init {
        instance = this

    }




    /*//var mMessageReadList: ArrayList<PushNotificationModel> = ArrayList<PushNotificationModel>()
    //var mMessageUnreadList = ArrayList<PushNotificationModel>()
    //var isVisibleUnreadBox = false
    //var isVisibleReadBox = false
    var isSelectedUnRead = false
    var confirmingPosition = -1
    //var isSelectedRead = false
    *//*var confirmingPosition = -1
    var mPassportDetailArrayList: ArrayList<PassportDetailModel> =
        ArrayList<PassportDetailModel>()
    var isInsuranceEdited = false
    var isPassportEdited = false
    var mInsuranceDetailArrayList: ArrayList<InsuranceDetailModel> =
        ArrayList<InsuranceDetailModel>()*//*
    var dateArrayListYear: ArrayList<String>? = null
    var callTypeStatus = "1"
    var mMessageUnreadListFav = ArrayList<PushNotificationModel>()
    var mMessageReadListFav = ArrayList<PushNotificationModel>()
    //var click_count_read = 0
    //var click_count = 0
    //var isfromUnread = false
    //var isfromUnreadSingle = false
    //var isfromRead = false
    //var pushId = ""
    private var mInstance: AppController? = null
    *//*var kinArrayShow: ArrayList<KinModel> = ArrayList<KinModel>()
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
        ArrayList<NationalityModel>()*//*

    override fun onCreate() {
        super.onCreate()
        AppController().mInstance = this
        MultiDex.install(this)
        val appSignature = AppSignatureHelper(this)
        for (signature in appSignature.getAppSignatures()) {
            Log.e("MyRespo", "onCreate: $signature")
            PreferenceManager().setOTPSignature(this, signature)
        }

       *//* AnalyticsTrackers.initialize(this)
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP)
        val appSignature = AppSignatureHelper(this)
        for (signature in appSignature.getAppSignatures()) {
            Log.e("MyRespo", "onCreate: $signature")
            PreferenceManager().setOTPSignature(this, signature)
        }*//*
    }
    @Synchronized
    fun getInstance(): AppController? {
        return AppController().mInstance
    }

    *//*@Synchronized
    fun getGoogleAnalyticsTracker(): Tracker? {
        val analyticsTrackers: AnalyticsTrackers = AnalyticsTrackers.getInstance()
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP)
    }*/

}