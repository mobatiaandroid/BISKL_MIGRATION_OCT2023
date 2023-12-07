package com.example.bskl_kotlin.fragment.settings.model

import com.example.bskl_kotlin.fragment.reports.model.ReportsDataModel
import com.google.gson.annotations.SerializedName

class ChangePasswordResponseModel (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String
)