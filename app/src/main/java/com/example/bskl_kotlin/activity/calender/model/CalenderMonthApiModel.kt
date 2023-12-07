package com.example.bskl_kotlin.activity.calender.model

import com.google.gson.annotations.SerializedName

class CalenderMonthApiModel (
@SerializedName("selected_month")var selected_month:String,
@SerializedName("user_ids")var user_ids:String
)
