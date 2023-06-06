package com.example.bskl_kotlin.manager

import android.support.multidex.MultiDexApplication
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel

class AppController: MultiDexApplication() {
    var mMessageReadList: ArrayList<PushNotificationModel> = ArrayList<PushNotificationModel>()
    var mMessageUnreadList = ArrayList<PushNotificationModel>()
    var isVisibleUnreadBox = false
    var isVisibleReadBox = false
    var isSelectedUnRead = false
    var isSelectedRead = false
    var callTypeStatus = "1"

}