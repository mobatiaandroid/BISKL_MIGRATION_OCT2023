package com.example.bskl_kotlin.activity.home.model

import com.example.bskl_kotlin.activity.datacollection_p2.model.DataCollectionResponseModel
import com.google.gson.annotations.SerializedName

class DeviceregisterModel  (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: DeviceRegisterResponseModel

)