package com.example.bskl_kotlin.fragment.timetable.model

import com.example.bskl_kotlin.fragment.absence.model.StudentModel
import com.google.gson.annotations.SerializedName

class TimetableResponseModelList (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("range")var range:RangeModelList,
    @SerializedName("field")var field:ArrayList<FieldModel>,
    @SerializedName("field1")var field1:ArrayList<FieldModel>,
    @SerializedName("Timetable")var Timetable:ArrayList<DayModel>,
    @SerializedName("fieldandroid")var fieldandroid:ArrayList<PeriodModel>
        )