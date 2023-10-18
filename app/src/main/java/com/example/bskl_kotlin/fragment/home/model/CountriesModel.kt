package com.example.bskl_kotlin.fragment.home.model

import com.example.bskl_kotlin.fragment.reports.model.ReportsResponseModel
import com.google.gson.annotations.SerializedName

class CountriesModel(
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: CountriesResponseModel

)