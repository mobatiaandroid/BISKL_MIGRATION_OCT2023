package com.example.bskl_kotlin.activity.login.model

import com.google.gson.annotations.SerializedName

class LoginResponseModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response:ResponseModel
)