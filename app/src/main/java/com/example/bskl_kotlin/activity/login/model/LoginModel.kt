package com.example.bskl_kotlin.activity.login.model

import com.google.gson.annotations.SerializedName

class LoginModel (
    @SerializedName("access_token")var access_token:String,
    @SerializedName("email")var email:String,
    @SerializedName("password")var password:String,
    @SerializedName("deviceid")var deviceid:String,
    @SerializedName("devicetype")var devicetype:String,
    @SerializedName("device_identifier")var device_identifier:String
)