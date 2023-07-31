package com.example.bskl_kotlin.common.model

import com.example.bskl_kotlin.fragment.safeguarding.model.SafeguardingResponse
import com.google.gson.annotations.SerializedName

class CommonResponseModel (

    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: CommonResponse
        )

class CommonResponse (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String
        )
