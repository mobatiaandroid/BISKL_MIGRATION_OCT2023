package com.example.bskl_kotlin.activity.login.model

import com.google.gson.annotations.SerializedName

class ResponseModel (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("responseArray")var responseArray:ResponseArrayModel
)