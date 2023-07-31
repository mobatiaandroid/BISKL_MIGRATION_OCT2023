package com.example.bskl_kotlin.fragment.safeguarding.model

import com.google.gson.annotations.SerializedName

class SafeguardingResponseModel (

    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: SafeguardingResponse
        )

class SafeguardingResponse (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("attendance_data")var attendance_data:ArrayList<AttendancedataList>,
    @SerializedName("codes")var codes:ArrayList<CodesModel>
        )
