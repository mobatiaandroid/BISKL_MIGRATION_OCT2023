package com.example.bskl_kotlin.common

import android.content.Context
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Suppress("DEPRECATION")
class PreferenceManager {
    private val PREFSNAME = "BISKL"


    fun setDataCollection(context: Context, result: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("data_collection_flag", result)
        editor.apply()
    }

    fun getDataCollection(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("data_collection_flag", "")
    }
    fun setSuspendTrigger(context: Context, timetableAvailable: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("timetableAvailable", timetableAvailable)
        editor.apply()
    }

    fun getSuspendTrigger(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("timetableAvailable", "")
    }
    fun setIsFirstLaunch(context: Context, result: Boolean) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("timetableAvailable", result)
        editor.apply()
    }

    fun getIsFirstLaunch(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean("result", false)
    }

    fun setUserId(context: Context, userid: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("userid", userid)
        editor.apply()
    }

    fun getUserId(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("userid", "")
    }
    fun setFcmID(context: Context, id: String) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("firebase id", id)
        editor.apply()
    }

    /*GET FIREBASE ID*/
    fun getFcmID(context: Context): String {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("firebase id", "").toString()
    }
    fun setUserName(context: Context, user_name: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("user_name", user_name)
        editor.apply()
    }

    /*GET USER CODE*/
    fun getUserName(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("user_name", "")
    }
    fun setUserNumber(context: Context, user_no: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("user_no", user_no)
        editor.apply()
    }

    fun getUserNumber(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("user_no", "")
    }
    fun setUserEmail(context: Context, user_mail: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("user_mail", user_mail)
        editor.apply()
    }

    fun getUserEmail(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("user_mail", "")
    }
    fun setHomeListType(context: Context, homelist_type: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("homelist_type", homelist_type)
        editor.apply()
    }

    fun getHomeListType(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("homelist_type", "")
    }

    fun setLoggedInStatus(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("logged_in_status", id)
        editor.apply()
    }

    /*GET USER CODE*/
    fun getLoggedInStatus(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("logged_in_status", "")
    }
    /**
     * SET ACCESS TOKEN
     */

    fun setaccesstoken(context: Context, access_token: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("access_token", access_token)
        editor.apply()
    }

    fun getaccesstoken(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("access_token", "")
    }
    fun setPhoneNo(context: Context, phone_no: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("phone_no", phone_no)
        editor.apply()
    }

    fun getPhoneNo(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("phone_no", "")
    }
    fun setFullName(context: Context, full_name: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("full_name", full_name)
        editor.apply()
    }

    fun getFullName(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("full_name", "")
    }
    fun setCalenderPush(context: Context, cal_push: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("cal_push", cal_push)
        editor.apply()
    }

    fun getCalenderPush(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("cal_push", "")
    }
    fun setEmailPush(context: Context, email_push: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("email_push", email_push)
        editor.apply()
    }

    fun getEmailPush(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("email_push", "")
    }
    fun setMessageBadge(context: Context, mess_badge: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("mess_badge", mess_badge)
        editor.apply()
    }

    fun getMessageBadge(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("mess_badge", "")
    }
    fun setCalenderBadge(context: Context, cal_badge: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("cal_badge", cal_badge)
        editor.apply()
    }

    fun getCalenderBadge(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("cal_badge", "")
    }
    fun setReportMailMerge(context: Context, report_mail_merge: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("report_mail_merge", report_mail_merge)
        editor.apply()
    }

    fun getReportMailMerge(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("report_mail_merge", "")
    }
    fun setCorrespondenceMailMerge(context: Context, corr_mail_erge: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("corr_mail_erge", corr_mail_erge)
        editor.apply()
    }

    fun getCorrespondenceMailMerge(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("corr_mail_erge", "")
    }
    fun setVersionFromApi(context: Context, version_android: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("version_android", version_android)
        editor.apply()
    }

    fun getVersionFromApi(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("version_android", "")
    }
    fun setYouKey(context: Context, you_key: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("you_key", you_key)
        editor.apply()
    }

    fun getYouKey(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("you_key", "")
    }
    fun setInstaKey(context: Context, insta_key: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("insta_key", insta_key)
        editor.apply()
    }

    fun getInstaKey(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("insta_key", "")
    }
    fun setFbkey(context: Context, fb_key: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("fb_key", fb_key)
        editor.apply()
    }

    fun getFbkey(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("fb_key", "")
    }
    fun setHomeAttendance(context: Context, home_attendance: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("home_attendance", home_attendance)
        editor.apply()
    }

    fun getHomeAttendance(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("home_attendance", "")
    }
    fun setSafeGuardingGroup(context: Context, safeguard_grp: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("safeguard_grp", safeguard_grp)
        editor.apply()
    }

    fun getSafeGuardingGroup(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("safeguard_grp", "")
    }
    fun setTimeTableGroup(context: Context, timetable_grp: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("timetable_grp", timetable_grp)
        editor.apply()
    }

    fun getTimeTableGroup(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("timetable_grp", "")
    }
    fun setHomeTimetable(context: Context, home_timetable: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("home_timetable", home_timetable)
        editor.apply()
    }

    fun getHomeTimetable(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("home_timetable", "")
    }
    fun setHomeReportAbsence(context: Context, home_report_absence: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("home_report_absence", home_report_absence)
        editor.apply()
    }

    fun getHomeReportAbsence(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("home_report_absence", "")
    }
    fun setIsApplicant(context: Context, is_applicant: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("is_applicant", is_applicant)
        editor.apply()
    }

    fun getIsApplicant(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("is_applicant", "")
    }
    fun setIsVisible(context: Context, is_visible: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("is_visible", is_visible)
        editor.apply()
    }

    fun getIsVisible(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("is_visible", "")
    }
    fun setTimeTableAvailable(context: Context, timetable_available: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("timetable_available", timetable_available)
        editor.apply()
    }

    fun getTimeTableAvailable(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("timetable_available", "")
    }
    fun setHomeSafeGuarding(context: Context, home_safeguarding: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("home_safeguarding", home_safeguarding)
        editor.apply()
    }

    fun getHomeSafeGuarding(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("home_safeguarding", "")
    }
    fun setIfHomeItemClickEnabled(context: Context, home_item_click: Boolean) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("home_item_click", home_item_click)
        editor.apply()
    }

    fun getIfHomeItemClickEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean("home_item_click",true)
    }

    fun setIsFirstTimeInNotification(context: Context, result: Boolean) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("result", result)
        editor.apply()
    }

    fun getIsFirstTimeInNotification(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean("result",true)
    }
    fun setmMessageUnreadList(context: Context, mMessageUnreadList: ArrayList<PushNotificationModel>) {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(mMessageUnreadList)
        editor.putString("mMessageUnreadList", json)
        editor.apply()
    }
    /*GET ALREADY TRIGGERED*/
    fun getmMessageUnreadList(context: Context): ArrayList<PushNotificationModel> {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString("mMessageUnreadList", null)
        val type = object : TypeToken<ArrayList<PushNotificationModel?>?>() {}.type
        return  gson.fromJson(json,type)
    }
    fun setmMessageReadList(context: Context, mMessageReadList: ArrayList<PushNotificationModel>) {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(mMessageReadList)
        editor.putString("mMessageReadList", json)
        editor.apply()
    }
    /*GET ALREADY TRIGGERED*/
    fun getmMessageReadList(context: Context): ArrayList<PushNotificationModel> {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString("mMessageReadList", null)
        val type = object : TypeToken<ArrayList<PushNotificationModel?>?>() {}.type
        return  gson.fromJson(json,type)
    }

}