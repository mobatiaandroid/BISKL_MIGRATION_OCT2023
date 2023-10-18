package com.example.bskl_kotlin.fragment.messages.model

import com.example.bskl_kotlin.activity.home.model.AppFeatureModel
import com.example.bskl_kotlin.activity.home.model.ResponseArrayUserModel
import com.example.bskl_kotlin.activity.home.model.SocialMediaUserModel
import com.example.bskl_kotlin.activity.home.model.TimeTableStudentModel
import com.example.bskl_kotlin.fragment.contactus.model.ContactUsDataModel
import com.google.gson.annotations.SerializedName

class NotificationResponseModel  (

    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("data")var data: ArrayList<NotificationNewDataModel>

)