package com.example.bskl_kotlin.rest_api


import com.example.bskl_kotlin.activity.home.model.UserDetailsModel
import com.example.bskl_kotlin.activity.login.model.LoginResponseModel
import com.example.bskl_kotlin.activity.splash.TokenResponseModel
import com.example.bskl_kotlin.common.model.CommonResponseModel
import com.example.bskl_kotlin.common.model.StudentListResponseModel
import com.example.bskl_kotlin.fragment.contactus.model.ContactUsModel
import com.example.bskl_kotlin.fragment.safeguarding.model.SafeguardingResponseModel
import com.example.bskl_kotlin.fragment.timetable.model.TimetableResponseModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiInterface {


    @POST("oauth/access_token")
    @FormUrlEncoded
    fun token(
        @Field("grant_type") grant_type: String,
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<TokenResponseModel>

    @POST("api/login_V2")
    @FormUrlEncoded
    fun login(
        @Field("access_token") access_token: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("deviceid") deviceid: String,
        @Field("devicetype") devicetype: String,
        @Field("device_identifier") device_identifier: String
    ): Call<LoginResponseModel>

    @POST("api/settings_userdetails")
    @FormUrlEncoded
    fun user_details(
        @Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String
    ): Call<UserDetailsModel>

    @POST("api/contact_us")
    @FormUrlEncoded
    fun contact_us(
        @Field("access_token") access_token: String
    ): Call<ContactUsModel>



    @POST("api/safeguarding")
    @FormUrlEncoded
    fun safeguarding(
        @Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String
    ): Call<SafeguardingResponseModel>


    @POST("api/reset_safeguarding_notifaction")
    @FormUrlEncoded
    fun reset(
        @Field("access_token") access_token: String,
        @Field("userid") userid: String
    ): Call<CommonResponseModel>


    @POST("api/submitsafeguarding")
    @FormUrlEncoded
    fun submitsafedaurding(
        @Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("student_id") student_id: String,
        @Field("attendance_id") attendance_id: String,
        @Field("status") status: String,
        @Field("details") details: String
    ): Call<CommonResponseModel>


    @POST("api/requestLeave")
    @FormUrlEncoded
    fun requestleave(
        @Field("access_token") access_token: String,
        @Field("student_id") student_id: String,
        @Field("users_id") users_id: String,
        @Field("from_date") from_date: String,
        @Field("to_date") to_date: String,
        @Field("reason") reason: String
    ): Call<CommonResponseModel>


    @POST("api/studentlist")
    @FormUrlEncoded
    fun student_list(
        @Field("access_token") access_token: String,
        @Field("users_id") users_id: String
    ): Call<StudentListResponseModel>



    @POST("api/timetable_v2")
    @FormUrlEncoded
    fun timtable_list(
        @Field("access_token") access_token: String,
        @Field("student_id") student_id: String
    ): Call<TimetableResponseModel>
}