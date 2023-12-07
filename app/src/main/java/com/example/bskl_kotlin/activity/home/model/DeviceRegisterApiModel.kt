package com.example.bskl_kotlin.activity.home.model

import com.google.gson.annotations.SerializedName

class DeviceRegisterApiModel  (

    @SerializedName("deviceid")var deviceid: String,
    @SerializedName("devicetype")var devicetype: String,
    @SerializedName("device_identifier")var device_identifier: String,
    @SerializedName("user_ids")var user_ids: String

)