package com.example.bskl_kotlin.activity.login.model

import com.google.gson.annotations.SerializedName

class SignUpModel (
@SerializedName("responsecode")var responsecode:String,
@SerializedName("response")var response: SignupResponse
)

class SignupResponse (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("mobile_no")var mobile_no:String,
    @SerializedName("users_id")var users_id:String

    )