package com.example.bskl_kotlin.common.model

import com.example.bskl_kotlin.activity.home.model.UserResponseModel
import com.example.bskl_kotlin.fragment.contactus.model.ContactUsDataModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationResponseModel
import com.google.gson.annotations.SerializedName

class NotificationNewResponseModel  (

    @SerializedName("response")var response: NotificationResponseModel,
    @SerializedName("responsecode")var responsecode:String

        )