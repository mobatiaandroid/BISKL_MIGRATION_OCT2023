package com.example.bskl_kotlin.activity.home.model

import com.google.gson.annotations.SerializedName

class UserResponseModel (
    @SerializedName("response")var response:String,
    @SerializedName("statuscode")var statuscode:String,
    @SerializedName("responseArray")var responseArray:ResponseArrayUserModel,
    @SerializedName("socialmedia")var socialmedia:SocialMediaUserModel,
    @SerializedName("android_version")var android_version:String,
    @SerializedName("ios_version")var ios_version:String,
    @SerializedName("app_feature")var app_feature:AppFeatureModel,
    @SerializedName("timetable_student")var timetable_student:ArrayList<TimeTableStudentModel>
)