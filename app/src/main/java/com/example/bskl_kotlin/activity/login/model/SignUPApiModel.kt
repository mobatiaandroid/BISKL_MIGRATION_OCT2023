package com.example.bskl_kotlin.activity.login.model

import com.google.gson.annotations.SerializedName

class SignUPApiModel (
    @SerializedName("email")var email:String,
    @SerializedName("deviceid")var deviceid:String,
    @SerializedName("devicetype")var devicetype:String,
    @SerializedName("mobile_no")var mobile_no:String,
    @SerializedName("hash_key")var hash_key:String
)
