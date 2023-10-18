package com.example.bskl_kotlin.activity.datacollection_p2.model

import com.example.bskl_kotlin.fragment.home.model.CountriesResponseModel
import com.google.gson.annotations.SerializedName

class DataCollectionDetailModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: DataCollectionResponseModel

)