package com.example.bskl_kotlin.fragment.reports.model

import com.google.gson.annotations.SerializedName

class ReportsModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response:ReportsResponseModel

)