package com.example.bskl_kotlin.fragment.reports.model

import com.google.gson.annotations.SerializedName

class StudentInfoModel (
    @SerializedName("Acyear")var acyear:String,
    @SerializedName("data")var mDataModel:ArrayList<ReportsDataModel>

)