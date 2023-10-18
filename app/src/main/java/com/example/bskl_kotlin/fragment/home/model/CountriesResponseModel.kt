package com.example.bskl_kotlin.fragment.home.model

import com.example.bskl_kotlin.activity.home.model.NationalityModel
import com.example.bskl_kotlin.fragment.reports.model.ReportsDataModel
import com.google.gson.annotations.SerializedName

class CountriesResponseModel (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("contries")var responseArray:ArrayList<NationalityModel>
)