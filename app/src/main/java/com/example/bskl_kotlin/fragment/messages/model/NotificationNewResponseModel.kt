package com.example.bskl_kotlin.fragment.messages.model

import com.example.bskl_kotlin.activity.home.model.UserResponseModel
import com.google.gson.annotations.SerializedName

class NotificationNewResponseModel  (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: NotificationResponseModel
)