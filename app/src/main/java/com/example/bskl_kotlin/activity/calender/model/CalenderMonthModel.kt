package com.example.bskl_kotlin.activity.calender.model

import com.example.bskl_kotlin.fragment.calendar.model.CalendarModel
import com.example.bskl_kotlin.fragment.timetable.model.TimetableResponseModelList
import com.google.gson.annotations.SerializedName

class CalenderMonthModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: CalenderMonthResponseModelList
)
class CalenderMonthResponseModelList(
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("data")var data:ArrayList<CalendarModel>
)