package com.example.bskl_kotlin.activity.home.model

import com.example.bskl_kotlin.activity.datacollection_p2.model.DataDataCollectionModel
import com.google.gson.annotations.SerializedName

class DeviceRegisterResponseModel (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String
)