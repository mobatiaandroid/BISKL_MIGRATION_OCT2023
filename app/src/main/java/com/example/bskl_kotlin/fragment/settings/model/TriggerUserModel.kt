package com.example.bskl_kotlin.fragment.settings.model

import com.google.gson.annotations.SerializedName

class TriggerUserModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: TriggerUserResponseModel

)