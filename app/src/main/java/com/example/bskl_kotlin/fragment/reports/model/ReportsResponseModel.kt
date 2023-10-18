package com.example.bskl_kotlin.fragment.reports.model

import com.google.gson.annotations.SerializedName

class ReportsResponseModel (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("responseArray")var responseArray:ArrayList<ReportsDataModel>
)