package com.example.bskl_kotlin.fragment.absence.model

import com.google.gson.annotations.SerializedName

class LeaveRequestsModel (

    @SerializedName("responsecode")var responsecode: String,
    @SerializedName("response")var response: ResponseLeaveList

)