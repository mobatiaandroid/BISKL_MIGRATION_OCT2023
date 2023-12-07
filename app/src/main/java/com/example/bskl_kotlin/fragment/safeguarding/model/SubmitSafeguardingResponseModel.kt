package com.example.bskl_kotlin.fragment.safeguarding.model

import com.google.gson.annotations.SerializedName

class SubmitSafeguardingResponseModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: SubmitSafeguardingResponse
)

class SubmitSafeguardingResponse (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,

)