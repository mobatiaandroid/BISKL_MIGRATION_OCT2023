package com.example.bskl_kotlin.fragment.contactus.model

import com.google.gson.annotations.SerializedName

class ContactUsResponseModel (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("data")var data:ContactUsDataModel
)