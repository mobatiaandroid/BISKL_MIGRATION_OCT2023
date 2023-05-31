package com.example.bskl_kotlin.activity.home.model

import com.google.gson.annotations.SerializedName

class AppFeatureModel (
    @SerializedName("timetable")var timetable:String,
    @SerializedName("safeguarding")var safeguarding:String,
    @SerializedName("attendance")var attendance:String,
    @SerializedName("report_absense")var report_absense:String,
    @SerializedName("timetable_group")var timetable_group:String,
    @SerializedName("safeguarding_group")var safeguarding_group:String
        )