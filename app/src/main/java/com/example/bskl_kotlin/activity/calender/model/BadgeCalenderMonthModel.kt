package com.example.bskl_kotlin.activity.calender.model

import com.google.gson.annotations.SerializedName

class BadgeCalenderMonthModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: CalenderBadgeResponseModel
)
class CalenderBadgeResponseModel(
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String
)