package com.example.bskl_kotlin.fragment.settings.model

import com.google.gson.annotations.SerializedName

class TriggerUserResponseModel (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String
)