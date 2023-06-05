package com.example.bskl_kotlin.fragment.contactus.model

import com.google.gson.annotations.SerializedName

class ContactUsModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response:ContactUsResponseModel
)