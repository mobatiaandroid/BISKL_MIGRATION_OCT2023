package com.example.bskl_kotlin.fragment.absence.model

import com.google.gson.annotations.SerializedName

class ResponseLeaveList (

    @SerializedName("statuscode")var statuscode: String,
    @SerializedName("requests")var requests: ArrayList<LeavesModel>

)