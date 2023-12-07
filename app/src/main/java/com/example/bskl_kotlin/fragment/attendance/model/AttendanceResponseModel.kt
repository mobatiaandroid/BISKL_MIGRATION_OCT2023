package com.example.bskl_kotlin.fragment.attendance.model

import com.google.gson.annotations.SerializedName

class AttendanceResponseModel (
    @SerializedName("responsecode")var responsecode:String,
    @SerializedName("response")var response: AttendenceResponse
)

class AttendenceResponse (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("responseArray")var responseArray:ResponseArrayAttendanceModel,
    @SerializedName("details")var details:ArrayList<AttendanceModel>
)
class ResponseArrayAttendanceModel(
    @SerializedName("present")var present:Int,
    @SerializedName("late")var late:Int,
    @SerializedName("authorised_absence")var authorised_absence:Int,
    @SerializedName("unauthorised_absence")var unauthorised_absence:Int,
    @SerializedName("totaldays")var totaldays:Int
)