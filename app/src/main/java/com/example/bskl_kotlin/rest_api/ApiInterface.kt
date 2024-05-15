package com.example.bskl_kotlin.rest_api


import com.example.bskl_kotlin.activity.absence.model.RequestLeaveApiModel
import com.example.bskl_kotlin.activity.calender.model.BadgeCalenderMonthApiModel
import com.example.bskl_kotlin.activity.calender.model.BadgeCalenderMonthModel
import com.example.bskl_kotlin.activity.calender.model.CalenderBadgeResponseModel
import com.example.bskl_kotlin.activity.calender.model.CalenderHolidayResponseModel
import com.example.bskl_kotlin.activity.calender.model.CalenderMonthApiModel
import com.example.bskl_kotlin.activity.calender.model.CalenderMonthModel
import com.example.bskl_kotlin.activity.calender.model.HolidayCalenderMonthApiModel
import com.example.bskl_kotlin.activity.calender.model.HolidayCalenderMonthModel
import com.example.bskl_kotlin.activity.data_collection.SubmitDataCollectionApiModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.DataCollectionDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.DatacollectiondetailsApiModel
import com.example.bskl_kotlin.activity.home.model.DeviceRegisterApiModel
import com.example.bskl_kotlin.activity.home.model.DeviceregisterModel
import com.example.bskl_kotlin.activity.home.model.UserDetailsApiModel
import com.example.bskl_kotlin.activity.home.model.UserDetailsModel
import com.example.bskl_kotlin.activity.login.model.ForgotpasswordApiModel
import com.example.bskl_kotlin.activity.login.model.LoginResponseModel
import com.example.bskl_kotlin.activity.login.model.NewphonenumberApiModel
import com.example.bskl_kotlin.activity.login.model.OtpverificationApiModel
import com.example.bskl_kotlin.activity.login.model.SignUPApiModel
import com.example.bskl_kotlin.activity.login.model.SignUpModel
import com.example.bskl_kotlin.activity.settings.model.UserprofileApiModel

import com.example.bskl_kotlin.activity.settings.model.UserprofileResponseModel
import com.example.bskl_kotlin.activity.settings.model.UserprofileStudentApiModel
import com.example.bskl_kotlin.activity.settings.model.UserprofileStudentResponseModel
import com.example.bskl_kotlin.activity.splash.TokenResponseModel
import com.example.bskl_kotlin.common.model.NotificationNewResponseModel
import com.example.bskl_kotlin.common.model.StudentListApiModel
import com.example.bskl_kotlin.common.model.StudentListResponseModel
import com.example.bskl_kotlin.common.model.StudentResponseModel
import com.example.bskl_kotlin.fragment.absence.model.LeaveRequestsApiModel
import com.example.bskl_kotlin.fragment.absence.model.LeaveRequestsModel
import com.example.bskl_kotlin.fragment.attendance.model.AttendanceResponseModel
import com.example.bskl_kotlin.fragment.attendance.model.StudentResponseModelJava
import com.example.bskl_kotlin.fragment.contactus.model.ContactUsModel
import com.example.bskl_kotlin.fragment.home.model.CountriesApiModel
import com.example.bskl_kotlin.fragment.home.model.CountriesModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationFavApiModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationFavouriteModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationStatusApiModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationStatusModel
import com.example.bskl_kotlin.fragment.messages.model.NotificationsNewApiModel
import com.example.bskl_kotlin.fragment.reports.model.ReportsApiModel
import com.example.bskl_kotlin.fragment.reports.model.ReportsModel
import com.example.bskl_kotlin.fragment.safeguarding.model.SafeguardingApiModel
import com.example.bskl_kotlin.fragment.safeguarding.model.SafeguardingResponseModel
import com.example.bskl_kotlin.fragment.safeguarding.model.SubmitSafeguardingApiModel
import com.example.bskl_kotlin.fragment.safeguarding.model.SubmitSafeguardingResponseModel
import com.example.bskl_kotlin.fragment.settings.model.CalenderPushApiModel
import com.example.bskl_kotlin.fragment.settings.model.ChangePasswordApiModel
import com.example.bskl_kotlin.fragment.settings.model.ChangePasswordModel
import com.example.bskl_kotlin.fragment.settings.model.EmailPushApiModel
import com.example.bskl_kotlin.fragment.settings.model.LogoutApiModel
import com.example.bskl_kotlin.fragment.settings.model.TriggerUserApiModel
import com.example.bskl_kotlin.fragment.settings.model.TriggerUserModel
import com.example.bskl_kotlin.fragment.timetable.model.TimetableApiModel
import com.example.bskl_kotlin.fragment.timetable.model.TimetableResponseModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
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

    @POST("login_V3")
    @FormUrlEncoded
    fun login(

        @Field("email") email: String,
        @Field("password") password: String,
        @Field("deviceid") deviceid: String,
        @Field("devicetype") devicetype: String,
        @Field("device_identifier") device_identifier: String
    ): Call<LoginResponseModel>

    @POST("settings_userdetails")
    fun user_details(

        @Body userDetailsapiModel: UserDetailsApiModel,
        @Header("Authorization") token:String
        //@Field("access_token") access_token: String,
       // @Field("user_ids") user_ids: String
    ): Call<UserDetailsModel>

    @POST("contact_us")

    fun contact_us(
        @Header("Authorization") token:String
        //@Field("access_token") access_token: String
    ): Call<ContactUsModel>

    @POST("notifications_new")

    fun notifications_new(
        @Body notificationsnewModel: NotificationsNewApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("deviceid") deviceid: String,
         @Field("devicetype") devicetype: String,
         @Field("users_id") users_id: String,
         @Field("type") type: String,
         @Field("limit") limit: String,
         @Field("page") page: String*/
    ): Call<NotificationNewResponseModel>


    @POST("notifactionstatuschanged")

    fun notification_status(
        @Body notificationstatusModel: NotificationStatusApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("userid") userid: String,
         @Field("status") status: String,
         @Field("pushid") pushid: String*/
    ): Call<NotificationStatusModel>


    @POST("favouritechanges")

    fun notification_favourite(
        @Body notificationfavModel: NotificationFavApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("userid") userid: String,
         @Field("favourite") status: String,
         @Field("pushid") pushid: String*/
    ): Call<NotificationFavouriteModel>



    @POST("studentlist")
    fun student_list(
        @Body studentlistModel: StudentListApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("users_id") users_id: String*/
    ): Call<StudentListResponseModel>


    @POST("timetable_v2")
    fun timtable_list(
        @Body timetableModel: TimetableApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("student_id") student_id: String*/
    ): Call<TimetableResponseModel>

    @POST("progressreport")
    fun progress_report(
        @Body reportModel: ReportsApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("studid") studid: String*/
    ): Call<ReportsModel>
    @POST("countries")
    fun countries(
        @Body countriesModel: CountriesApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String*/
    ): Call<CountriesModel>
    @POST("data_collection_details_new")
    fun datacollectiondetails(
        @Body datacollectiondetailsModel: DatacollectiondetailsApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("user_ids") user_ids: String*/
    ): Call<DataCollectionDetailModel>

    @POST("deviceregistration_V2")
    fun deviceRegister(
        @Body deviceRegisterModel: DeviceRegisterApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("deviceid") deviceid: String,
        @Field("devicetype") devicetype: String,
        @Field("device_identifier") device_identifier: String,
        @Field("user_ids") user_ids: String*/
    ): Call<DeviceregisterModel>
    @POST("changepassword")

    fun changepassword(
        @Body changepasswordModel: ChangePasswordApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("users_id") users_id: String,
         @Field("current_password") current_password: String,
         @Field("new_password") new_password: String,
         @Field("email") email: String,
         @Field("deviceid") deviceid: String,
         @Field("devicetype") devicetype: String*/
    ): Call<ChangePasswordModel>
    @POST("calender_month_wise")
    fun calender_month_wise(
        @Body calendermonthModel: CalenderMonthApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("users_id") users_id: String,
         @Field("current_password") current_password: String,
         @Field("new_password") new_password: String,
         @Field("email") email: String,
         @Field("deviceid") deviceid: String,
         @Field("devicetype") devicetype: String*/
    ): Call<CalenderMonthModel>
    @POST("publichoilday_calender_view")
    fun publicholidaycalender(
        @Body holidaycalenderModel: HolidayCalenderMonthApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("users_id") users_id: String,
         @Field("current_password") current_password: String,
         @Field("new_password") new_password: String,
         @Field("email") email: String,
         @Field("deviceid") deviceid: String,
         @Field("devicetype") devicetype: String*/
    ): Call<HolidayCalenderMonthModel>
    @POST("clear_calenderbadge")
    fun clear_calenderbadge(
        @Body badgecalenderModel: BadgeCalenderMonthApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("users_id") users_id: String,
         @Field("current_password") current_password: String,
         @Field("new_password") new_password: String,
         @Field("email") email: String,
         @Field("deviceid") deviceid: String,
         @Field("devicetype") devicetype: String*/
    ): Call<BadgeCalenderMonthModel>
    @POST("trigger_user")
    fun trigger_user(
        @Body triggeruserModel: TriggerUserApiModel,
        @Header("Authorization") token:String
        /* @Field("access_token") access_token: String,
         @Field("user_ids") user_ids: String,
         @Field("trigger_type") trigger_type: String*/
    ): Call<TriggerUserModel>
    @POST("logout_V2")
    fun logout(
        @Body logoutModel:LogoutApiModel,
        @Header("Authorization") token:String
       /* @Field("access_token") access_token: String,
        @Field("users_id") user_ids: String,
        @Field("deviceid") deviceid: String,
        @Field("devicetype") devicetype: String,
        @Field("device_identifier") device_identifier: String*/
    ): Call<TriggerUserModel>
    @POST("emailpush")
    fun emailpush(
        @Body emailpushModel: EmailPushApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<TriggerUserModel>
    @POST("calenderpush")
    fun calenderpush(
        @Body calenderpushModel: CalenderPushApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<TriggerUserModel>
    @POST("leaveRequests")
    fun leaveRequestslist(
        @Body leaveRequestsModel: LeaveRequestsApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<LeaveRequestsModel>
    @POST("requestLeave")
    fun requestLeave(
        @Body requestLeaveModel: RequestLeaveApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<TriggerUserModel>
    @POST("safeguarding")
    fun safeguarding(
        @Body safeguardingModel: SafeguardingApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<SafeguardingResponseModel>
    @POST("submitsafeguarding")
    fun submitsafeguarding(
        @Body submitsafeguardingModel: SubmitSafeguardingApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<SubmitSafeguardingResponseModel>
    @POST("forgotpassword")
    fun forgotpassword(
        @Body forgotpasswordModel: ForgotpasswordApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<TriggerUserModel>
    @POST("parent_signup")
    fun signuppassword(
        @Body signupModel: SignUPApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<SignUpModel>
    @POST("newphone_number_request")
    fun newphonenumber(
        @Body newphonenumberModel: NewphonenumberApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<TriggerUserModel>
    @POST("otpverfication_V2")
    fun otpverification(
        @Body otpverificationModel: OtpverificationApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<TriggerUserModel>
    @POST("userprofile")
    fun userprofile(
        @Body userprofileModel: UserprofileApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<UserprofileResponseModel>
    @POST("userprofile_studentdetails")
    fun userprofile_studentdetails(
        @Body userprofile_studentdetails: UserprofileStudentApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<UserprofileStudentResponseModel>
    @POST("submit_datacollection_new")
    fun submit_datacollection_new(
        @Body submitDataCollection: SubmitDataCollectionApiModel,
        @Header("Authorization") token:String
        /*@Field("access_token") access_token: String,
        @Field("user_ids") user_ids: String,
        @Field("status") status: String*/
    ): Call<TriggerUserModel>

    @POST("studentlist")
    @Headers("Content-Type: application/json")
    fun studentlistattendance(
        @Header("Authorization") token: String?,
        @Body json: JsonObject?
    ): Call<StudentResponseModelJava?>?
    @POST("attendance_record")
    @Headers("Content-Type: application/json")
    fun attendance(
        @Header("Authorization") token: String?,
        @Body json: JsonObject?
    ): Call<AttendanceResponseModel>
    @POST("clear_calenderbadge")
    @Headers("Content-Type: application/json")
    fun clear_calenderbadge(
        @Header("Authorization") token: String?,
        @Body json: JsonObject?
    ): Call<CalenderBadgeResponseModel>
    @POST("calender_month_wise")
    @Headers("Content-Type: application/json")
    fun calender_month_wise(
        @Header("Authorization") token: String?,
        @Body json: JsonObject?
    ): Call<CalenderMonthModel>
    @POST("publichoilday_calender_view")
    @Headers("Content-Type: application/json")
    fun publichoilday_calender_view(
        @Header("Authorization") token: String?,
        @Body json: JsonObject?
    ): Call<CalenderMonthModel>
}