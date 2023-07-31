package com.example.bskl_kotlin.fragment.timetable.model

import com.google.gson.annotations.SerializedName

class RangeModelList (

    @SerializedName("Monday") val Monday: ArrayList<DayModel>,
    @SerializedName("Tuesday") val Tuesday: ArrayList<DayModel>,
    @SerializedName("Wednesday") val Wednesday: ArrayList<DayModel>,
    @SerializedName("Thursday") val Thursday: ArrayList<DayModel>,
    @SerializedName("Friday") val Friday: ArrayList<DayModel>

        )