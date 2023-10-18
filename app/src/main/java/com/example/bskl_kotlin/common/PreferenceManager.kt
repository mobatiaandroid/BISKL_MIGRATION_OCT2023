package com.example.bskl_kotlin.common

import android.content.Context
import com.example.bskl_kotlin.activity.datacollection_p2.model.ContactTypeModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.GlobalListDataModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.InsuranceDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.KinModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.OwnContactModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.PassportDetailModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.StudentModelNew
import com.example.bskl_kotlin.constants.BsklNameConstants
import com.example.bskl_kotlin.constants.BsklTabConstants
import com.example.bskl_kotlin.fragment.messages.model.PushNotificationModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Suppress("DEPRECATION")
class PreferenceManager {
    private val PREFSNAME = "BISKL"
    var bsklTabConstants: BsklTabConstants=BsklTabConstants()
    var bsklNameConstants: BsklNameConstants= BsklNameConstants()
    var FB_TOKEN =
        "EAAGfhpgCwKgBACtp6hgSsDixEi6mY6XuwAgZAWD6JQYF4by74vSu5nZAuXDTwVUmwxkSfSyglFkl9SOwrecMfm5tl37wDQFacsCJZCn3zAkJi7FJNxIZBhUr3nwHpmpo0ZCZAMYZBzPzLmHGrqZCAvwER32SZAREZC50q51wZAQfs3kRXXZB2PSBOkCF"
    var YOTUBE_CHANNEL_ID = "UCAYJEbMSpKfddk-1z6RGwQA"
    var INSTA_TOKEN = "5730874820.b5baecd.9a6ef3f5e0034879ae77edcfef21db6b"

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
    fun setDataCollectionTriggerType(context: Context, result: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("trigger_type", result)
        editor.apply()
    }

    fun getDataCollectionTriggerType(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("trigger_type", "")
    }
    fun setDisplayMessage(context: Context, displayMessage: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("displayMessage", displayMessage)
        editor.apply()
    }

    fun getDisplayMessage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("displayMessage", "")
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
    fun setIsAlreadyEnteredOwnContact(context: Context, result: Boolean) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("is_already_entered_own_contact", result)
        editor.apply()
    }

    fun getIsAlreadyEnteredOwnContact(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean("result", true)
    }
    fun setIsAlreadyEnteredKin(context: Context, result: Boolean) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("is_already_entered_kin", result)
        editor.apply()
    }

    fun getIsAlreadyEnteredKin(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean("result", true)
    }
    fun setIsAlreadyEnteredInsurance(context: Context, result: Boolean) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("is_already_entered_insurance", result)
        editor.apply()
    }

    fun getIsAlreadyEnteredInsurance(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean("result", true)
    }
    fun setIsAlreadyEnteredPassport(context: Context, result: Boolean) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("is_already_entered_passport", result)
        editor.apply()
    }

    fun getIsAlreadyEnteredPassport(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean("result", true)
    }
    fun setIsAlreadyEnteredStudentList(context: Context, result: Boolean) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("is_already_studentlist", result)
        editor.apply()
    }

    fun getIsAlreadyEnteredStudentList(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean("result", true)
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
    fun setTimeTable(context: Context, timetable: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("timetable", timetable)
        editor.apply()
    }

    fun getTimeTable(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("timetable", "0")
    }
    fun setSafeGuarding(context: Context, safeguarding: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("safeguarding", safeguarding)
        editor.apply()
    }

    fun getSafeGuarding(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("safeguarding", "0")
    }
    fun setAttendance(context: Context, attendance: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("attendance", attendance)
        editor.apply()
    }

    fun getAttendance(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("attendance", "0")
    }
    fun setAbsence(context: Context, report_absence: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("report_absence", report_absence)
        editor.apply()
    }

    fun getAbsence(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("report_absence", "0")
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
    fun getUpdate(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("updateApp", "")
    }
    fun setUpdate(context: Context, updateApp: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("updateApp", updateApp)
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
    fun setsafenote(context: Context, safenotification: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("safenotification", safenotification)
        editor.apply()
    }

    fun getsafenote(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("safenotification", "")
    }
    fun setAlreadyTriggered(context: Context, already_triggered: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("already_triggered", "0")
        editor.apply()
    }

    fun getAlreadyTriggered(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("already_triggered", "")
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
        editor.putString("you_key", YOTUBE_CHANNEL_ID)
        editor.apply()
    }

    fun getYouKey(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("you_key", "")
    }
    fun setFbKey(context: Context, you_key: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("you_key", FB_TOKEN)
        editor.apply()
    }

    fun getFbKey(context: Context): String? {
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
        editor.putString("insta_key", INSTA_TOKEN)
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
    fun setKinDetailsArrayList(context: Context, kinDetails: ArrayList<KinModel>) {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(kinDetails)
        editor.putString("mMessageUnreadList", json)
        editor.apply()
    }
    /*GET ALREADY TRIGGERED*/
    fun getKinDetailsArrayList(context: Context): ArrayList<KinModel> {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString("kinDetails", null)
        val type = object : TypeToken<ArrayList<PushNotificationModel?>?>() {}.type
        return  gson.fromJson(json,type)
    }
    fun saveGlobalListArrayList(context: Context, Global_List: ArrayList<GlobalListDataModel>) {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(Global_List)
        editor.putString("Global_List", json)
        editor.apply()
    }
    /*GET ALREADY TRIGGERED*/
    fun getGlobalListArrayList(context: Context): ArrayList<GlobalListDataModel> {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString("Global_List", null)
        val type = object : TypeToken<ArrayList<PushNotificationModel?>?>() {}.type
        return  gson.fromJson(json,type)
    }
    fun saveContactTypeArrayList(context: Context, ContactType: ArrayList<ContactTypeModel>) {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(ContactType)
        editor.putString("ContactType", json)
        editor.apply()
    }
    /*GET ALREADY TRIGGERED*/
    fun getContactTypeArrayList(context: Context): ArrayList<ContactTypeModel> {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString("ContactType", null)
        val type = object : TypeToken<ArrayList<PushNotificationModel?>?>() {}.type
        return  gson.fromJson(json,type)
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
    fun setStudIdForCCA(context: Context, stud_id_cca: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("stud_id_cca", stud_id_cca)
        editor.apply()
    }

    fun getStudIdForCCA(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("stud_id_cca", "")
    }
    fun setCCAStudentIdPosition(context: Context, cca_id_pos: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("cca_id_pos", cca_id_pos)
        editor.apply()
    }

    fun getCCAStudentIdPosition(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("cca_id_pos", "")
    }
    fun setOwnDetailArrayList(list: ArrayList<OwnContactModel>, key: String, context: Context) {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }
    /*GET ALREADY TRIGGERED*/
    fun getOwnDetailArrayList(key:String,context: Context): ArrayList<OwnContactModel>? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<OwnContactModel>?>() {}.getType()
        return gson.fromJson(json, type)
    }
    /*fun getOwnDetailArrayList(key:String,context: Context): ArrayList<OwnContactModel> {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString(key,null)
        val type = object : TypeToken<ArrayList<OwnContactModel?>?>() {}.type
        return  gson.fromJson(json,type)
    }*/
    fun saveOwnDetailArrayList(list: ArrayList<OwnContactModel>, key: String, context: Context) {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }
    fun saveKinDetailsArrayListShow(list: ArrayList<KinModel>, context: Context) {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("KinDetails", json)
        editor.apply()
    }
    fun saveKinDetailsArrayList(list: ArrayList<KinModel>, context: Context) {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("KinDetails", json)
        editor.apply()
    }
    fun getInsuranceDetailArrayList(context: Context): ArrayList<InsuranceDetailModel> {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString("Insurance_Detail", null)
        val type = object : TypeToken<ArrayList<InsuranceDetailModel?>?>() {}.type
        return  gson.fromJson(json,type)
    }
    fun saveInsuranceDetailArrayList(context: Context): ArrayList<InsuranceDetailModel> {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString("Insurance_Detail", null)
        val type = object : TypeToken<ArrayList<InsuranceDetailModel?>?>() {}.type
        return  gson.fromJson(json,type)
    }
    fun saveInsuranceDetailArrayList(list  :ArrayList<InsuranceDetailModel>,context: Context) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("Insurance_Detail", json)
        editor.apply()
    }
    fun getPassportDetailArrayList(context: Context): ArrayList<PassportDetailModel> {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString("Passport_Detail", null)
        val type = object : TypeToken<ArrayList<PassportDetailModel?>?>() {}.type
        return  gson.fromJson(json,type)
    }
    fun savePassportDetailArrayList(list  :ArrayList<PassportDetailModel>,context: Context) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("Passport_Detail", json)
        editor.apply()
    }
    fun getInsuranceStudentList(context: Context): ArrayList<StudentModelNew> {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString("studentData", null)
        val type = object : TypeToken<ArrayList<StudentModelNew?>?>() {}.type
        return  gson.fromJson(json,type)
    }
    fun saveInsuranceStudentList(list  :ArrayList<StudentModelNew>,context: Context) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("studentData", json)
        editor.apply()
    }
    fun setButtonThreeGuestTextImage(context: Context, result: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("data_collection_flag", result)
        editor.apply()
    }

    fun getButtonThreeGuestTextImage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("btn_three_guest_pos", "")
    }
    fun setButtonThreeGuestTabId(context: Context, result: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("btn_three_guest_pos", result)
        editor.apply()
    }

    fun getButtonThreeGuestTabId(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("btn_three_guest_tab", bsklTabConstants.TAB_SOCIAL_MEDIA)
    }
    fun setButtonTwoGuestTabId(context: Context, btn_two_guest_tab: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("btn_two_guest_tab", btn_two_guest_tab)
        editor.apply()
    }

    fun getButtonOneTabId(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("btn_one_tab",bsklTabConstants.TAB_MESSAGES)
    }
    fun setButtonOneTabId(context: Context, btn_one_tab: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("btn_one_tab", btn_one_tab)
        editor.apply()
    }
    fun getButtonFourTabId(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("btn_one_tab",bsklTabConstants.TAB_SOCIAL_MEDIA)
    }
    fun setButtonsixTabId(context: Context, btn_one_tab: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("btn_one_tab", btn_one_tab)
        editor.apply()
    }
    fun getButtonsixTabId(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("btn_one_tab",bsklTabConstants.TAB_CALENDAR)
    }
    fun setButtonFourTabId(context: Context, btn_one_tab: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("btn_one_tab", btn_one_tab)
        editor.apply()
    }

    fun getButtonTwoGuestTabId(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("btn_two_guest_tab",bsklTabConstants.TAB_REPORT)
    }
    fun setButtonTwoGuestTextImage(context: Context, position: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("btn_two_guest_pos", position)
        editor.apply()
    }

    fun getButtonTwoGuestTextImage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("btn_two_guest_pos","5")
    }
    /*GET ALREADY TRIGGERED*/

}