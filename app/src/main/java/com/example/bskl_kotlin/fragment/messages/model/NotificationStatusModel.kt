package com.example.bskl_kotlin.fragment.messages.model

import com.google.gson.annotations.SerializedName

class NotificationStatusModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: NotificationStatusResponseModel
)