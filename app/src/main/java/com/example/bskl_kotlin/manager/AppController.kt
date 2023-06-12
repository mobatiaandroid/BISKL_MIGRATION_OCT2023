package com.example.bskl_kotlin.manager

import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel

class AppController: MultiDexApplication() {
    var mMessageReadList: ArrayList<PushNotificationModel> = ArrayList<PushNotificationModel>()
    var mMessageUnreadList = ArrayList<PushNotificationModel>()
    var isVisibleUnreadBox = false
    var isVisibleReadBox = false
    var isSelectedUnRead = false
    var isSelectedRead = false
    var callTypeStatus = "1"
    var mMessageUnreadListFav = ArrayList<PushNotificationModel>()
    var mMessageReadListFav = ArrayList<PushNotificationModel>()
    var click_count_read = 0
    var click_count = 0
    var isfromUnread = false
    var isfromUnreadSingle = false
    var isfromRead = false
    var pushId = ""
    private var mInstance: AppController? = null

    override fun onCreate() {
        super.onCreate()
        AppController().mInstance = this
        MultiDex.install(this)
       /* AnalyticsTrackers.initialize(this)
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP)
        val appSignature = AppSignatureHelper(this)
        for (signature in appSignature.getAppSignatures()) {
            Log.e("MyRespo", "onCreate: $signature")
            PreferenceManager().setOTPSignature(this, signature)
        }*/
    }
    @Synchronized
    fun getInstance(): AppController? {
        return AppController().mInstance
    }

}