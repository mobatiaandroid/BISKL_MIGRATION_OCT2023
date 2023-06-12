package com.example.bskl_kotlin.rest_api


import com.example.bskl_kotlin.activity.home.model.UserDetailsModel
import com.example.bskl_kotlin.activity.login.model.LoginResponseModel
import com.example.bskl_kotlin.activity.splash.TokenResponseModel
import com.example.bskl_kotlin.fragment.contactus.model.ContactUsModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationFavouriteModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationNewResponseModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationStatusModel
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

    @POST("api/notifications_new")
    @FormUrlEncoded
    fun notifications_new(
        @Field("access_token") access_token: String,
        @Field("deviceid") deviceid: String,
        @Field("devicetype") devicetype: String,
        @Field("users_id") users_id: String,
        @Field("type") type: String,
        @Field("limit") limit: String,
        @Field("page") page: String
    ): Call<NotificationNewResponseModel>


    @POST("api/notifactionstatuschanged")
    @FormUrlEncoded
    fun notification_status(
        @Field("access_token") access_token: String,
        @Field("userid") userid: String,
        @Field("status") status: String,
        @Field("pushid") pushid: String
    ): Call<NotificationStatusModel>


    @POST("api/favouritechanges")
    @FormUrlEncoded
    fun notification_favourite(
        @Field("access_token") access_token: String,
        @Field("userid") userid: String,
        @Field("favourite") status: String,
        @Field("pushid") pushid: String
    ): Call<NotificationFavouriteModel>
}