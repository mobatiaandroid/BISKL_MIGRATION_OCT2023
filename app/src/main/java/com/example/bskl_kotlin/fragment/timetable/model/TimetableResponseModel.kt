package com.example.bskl_kotlin.fragment.timetable.model

import com.example.bskl_kotlin.common.model.StudentResponseModel
import com.google.gson.annotations.SerializedName

class TimetableResponseModel(

    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: TimetableResponseModelList
)