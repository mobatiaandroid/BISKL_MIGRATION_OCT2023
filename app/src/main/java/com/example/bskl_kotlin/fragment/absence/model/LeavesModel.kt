package com.example.bskl_kotlin.fragment.absence.model

import com.google.gson.annotations.SerializedName

class LeavesModel (
    @SerializedName("id")var id:String,
    @SerializedName("from_date")var from_date:String,
    @SerializedName("to_date")var to_date:String,
    @SerializedName("reason")var reason:String,
    @SerializedName("status")var status:String,
    @SerializedName("created_time")var created_time:String,
    @SerializedName("staff")var staff:String
)