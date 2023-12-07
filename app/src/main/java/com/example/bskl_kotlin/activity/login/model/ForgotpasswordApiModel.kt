package com.example.bskl_kotlin.activity.login.model

import com.google.gson.annotations.SerializedName

class ForgotpasswordApiModel(
    @SerializedName("email")var email :String,
    @SerializedName("deviceid")var deviceid :String,
    @SerializedName("devicetype")var devicetype :String,
    @SerializedName("device_identifier")var device_identifier :String,
)
