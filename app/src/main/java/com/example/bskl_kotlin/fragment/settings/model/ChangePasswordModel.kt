package com.example.bskl_kotlin.fragment.settings.model

import com.example.bskl_kotlin.fragment.reports.model.ReportsResponseModel
import com.google.gson.annotations.SerializedName

class ChangePasswordModel  (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: ChangePasswordResponseModel

)