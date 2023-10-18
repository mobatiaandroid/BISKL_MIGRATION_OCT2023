package com.example.bskl_kotlin.activity.datacollection_p2.model

import com.example.bskl_kotlin.activity.home.model.NationalityModel
import com.google.gson.annotations.SerializedName

class DataCollectionResponseModel (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("data")var data:DataDataCollectionModel
)