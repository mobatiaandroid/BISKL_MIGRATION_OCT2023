package com.example.bskl_kotlin.fragment.attendance;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bskl_kotlin.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferenceManagerr   {
    public static String SHARED_PREF_NAS = "BSKL";

    public static void setIsFirstLaunch(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_launch", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getIsFirstLaunch(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_first_launch", false);
    }
    public static void setIsAlreadyEnteredOwnContact(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_already_entered_own_contact", result);
        editor.commit();
    }

    public static boolean getIsAlreadyEnteredOwnContact(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_already_entered_own_contact", true);
    }
    public static void setIsAlreadyEnteredKin(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_already_entered_kin", result);
        editor.commit();
    }

    public static boolean getIsAlreadyEnteredKin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_already_entered_kin", true);
    }
    public static void setIsAlreadyEnteredInsurance(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_already_entered_insurance", result);
        editor.commit();
    }

    public static boolean getIsAlreadyEnteredInsurance(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_already_entered_insurance", true);
    }
    public static void setIsAlreadyEnteredPassport(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_already_entered_passport", result);
        editor.commit();
    }

    public static boolean getIsAlreadyEnteredPassport(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_already_entered_passport", true);
    }
    public static void setIsAlreadyEnteredStudentList(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_already_entered_student", result);
        editor.commit();
    }

    public static boolean getIsAlreadyEnteredStudentList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_already_entered_student", true);
    }
  /*  public static String getButtonTwoTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_two_pos", TAB_REPORT);

    }*/

    public static void setCalendarEventNames(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cal_event", result);
        editor.commit();
    }
   /* public static String getButtonOneTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_pos", TAB_REPORT);//21

    }*/
    public static void setIsVisible(Context context, String isvisible) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("isvisible", isvisible);
        editor.commit();
    }
    public static String getIsVisible(Context context) {
        String isvisible = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        isvisible = prefs.getString("isvisible", "");
        return isvisible;
    }
    public static void setReportMailMerge(Context context, String isReportMerge) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("isReportMerge", isReportMerge);
        editor.commit();
    }
    public static String getReportMailMerge(Context context) {
        String isReportMerge = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        isReportMerge = prefs.getString("isReportMerge", "");
        return isReportMerge;
    }
    public static void setCorrespondenceMailMerge(Context context, String isCorrespondenceMailMerge) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("isCorrespondenceMailMerge", isCorrespondenceMailMerge);
        editor.commit();
    }
    public static String getCorrespondenceMailMerge(Context context) {
        String isCorrespondenceMailMerge = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        isCorrespondenceMailMerge = prefs.getString("isCorrespondenceMailMerge", "");
        return isCorrespondenceMailMerge;
    }
    /*******************************************************
     * Method name : getCalendarEventNames() Description : get calendar event
     * names Parameters : context Return type : String Date : 23-Jan-2015 Author
     * : Rijo K Jose
     *****************************************************/
    public static String getCalendarEventNames(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("cal_event", "");
    }

    //save access token
    public static void setAccessToken(Context mContext, String accesstoken) {
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREF_NAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access_token", accesstoken);
        editor.commit();
    }

    //get accesstoken
    public static String getAccessToken(Context mContext) {
        String tokenValue = "";
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        tokenValue = prefs.getString("access_token", "0");
        return tokenValue;
    }

    public static String getUserName(Context context) {
        String userName = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        userName = prefs.getString("username", "");
        return userName;
    }

    /*******************************************************
     * Method name : setUserName Description : get user name after login
     * Parameters : context, username Return type : void Date : Oct 28, 2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setUserName(Context context, String userName) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", userName);
        editor.commit();
    }

    public static int getButtonOneBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_one_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonOneBg() Description : set home button one bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonOneBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_one_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonOneTextImage() Description : get home button one
     * text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/

    /*******************************************************
     * Method name : setButtonOneTextImage() Description : set home button one
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/


    /*******************************************************
     * Method name : getButtonOneTabId() Description : get home button one tab
     * id Parameters : context Return type : String Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
   /* public static String getButtonOneTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_tab", TAB_MESSAGES);
    }*/
    /*******************************************************
     * Method name : getButtonOneTabId() Description : set home button one tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonOneTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_one_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonTwoBg() Description : get home button two bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonTwoBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_two_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonTwoBg() Description : set home button two bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_two_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonTwoTextImage() Description : get home button two
     * text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/


    /*******************************************************
     * Method name : setButtonTwoTextImage() Description : set home button two
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_two_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonTwoTabId() Description : get home button two tab
     * id Parameters : context Return type : String Date : 25-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/


   /* public static String getButtonTwoTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_tab", TAB_REPORT);
    }*/




    /*******************************************************
     * Method name : setButtonTwoTabId() Description : set home button two tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_two_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonThreeBg() Description : get home button three bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonThreeBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_three_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonThreeBg() Description : set home button three bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_three_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonThreeTextImage() Description : get home button
     * three text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/

    /*******************************************************
     * Method name : setButtonThreeTextImage() Description : set home button
     * three text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_three_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonThreeTabId() Description : get home button three
     * tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
   /* public static String getButtonThreeTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_three_tab", TAB_NEWS);
    }*/

    /*******************************************************
     * Method name : setButtonThreeTabId() Description : set home button three
     * tab id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_three_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFourBg() Description : get home button four bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonFourBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_four_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonFourBg() Description : set home button four bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonFourBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_four_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFourTextImage() Description : get home button four
     * text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/

    /*******************************************************
     * Method name : setButtonFourTextImage() Description : set home button four
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFourTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_four_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonFourTabId() Description : get home button four tab
     * id Parameters : context Return type : String Date : 25-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/

    /*public static String getButtonFourTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_tab", TAB_SOCIAL_MEDIA);
    }

    public static String getButtonsixTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_tab", TAB_CALENDAR);
    }*/

    /*******************************************************
     * Method name : setButtonFourTabId() Description : set home button four tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFourTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_four_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFiveBg() Description : get home button five bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonFiveBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_five_color",
                context.getResources().getColor(R.color.rel_five));

    }

    /*******************************************************
     * Method name : setButtonFiveBg() Description : set home button five bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_five_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFiveTextImage() Description : get home button five
     * text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/


    /*******************************************************
     * Method name : setButtonFiveTextImage() Description : set home button five
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_five_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonFiveTabId() Description : get home button five tab
     * id Parameters : context Return type : String Date : 25-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/

    /*******************************************************
     * Method name : setButtonFiveTabId() Description : set home button five tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_five_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSixBg() Description : get home button six bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonSixBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_six_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonSixBg() Description : set home button six bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonSixBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_six_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSixImage() Description : get home button six text
     * and image Parameters : context Return type : String Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
   /* public static String getButtonSixTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_six_pos", TAB_GALLERY_REG);

    }
*/
    /*******************************************************
     * Method name : setButtonSixTextImage() Description : set home button six
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSixTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_six_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonSixTabId() Description : get home button six tab
     * id Parameters : context Return type : String Date : 25-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
 /*   public static String getButtonSixTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_six_tab", TAB_GALLERY_REG);
    }*/

    /*******************************************************
     * Method name : setButtonSixTabId() Description : set home button six tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSixTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_six_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSevenBg() Description : get home button seven bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonSevenBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_seven_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonSevenBg() Description : set home button seven bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_seven_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSevenTextImage() Description : get home button
     * seven text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
   /* public static String getButtonSevenTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_seven_pos", TAB_SOCIAL_MEDIA_REG);

    }*/

    /*******************************************************
     * Method name : setButtonSevenTextImage() Description : set home button
     * seven text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_seven_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonSevenTabId() Description : get home button seven
     * tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
   /* public static String getButtonSevenTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_seven_tab", TAB_SOCIAL_MEDIA_REG);
    }
*/
    /*******************************************************
     * Method name : setButtonSevenTabId() Description : set home button seven
     * tab id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_seven_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonEightBg() Description : get home button eight bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonEightBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_eight_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonEightBg() Description : set home button eight bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonEightBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_eight_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonEightTextImage() Description : get home button
     * eight text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
  /*  public static String getButtonEightTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_eight_pos", TAB_SCHOOL_INFORMATION_REG);//19

    }
*/
    /*******************************************************
     * Method name : setButtonEightTextImage() Description : set home button
     * eight text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonEightTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_eight_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonEightTabId() Description : get home button eight
     * tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
  /*  public static String getButtonEightTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_eight_tab", TAB_SCHOOL_INFORMATION_REG);
    }
*/
    /*******************************************************
     * Method name : setButtonEightTabId() Description : set home button eight
     * tab id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonEightTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_eight_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonNineBg() Description : get home button nine bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonNineBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_nine_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonNineBg() Description : set home button nine bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonNineBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_nine_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonNineTextImage() Description : get home button nine
     * text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
   /* public static String getButtonNineTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_nine_pos", TAB_CCAS_REG);

    }*/

    /*******************************************************
     * Method name : setButtonNineTextImage() Description : set home button nine
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonNineTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_nine_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonNineTabId() Description : get home button nine tab
     * id Parameters : context Return type : String Date : 25-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
  /*  public static String getButtonNineTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_nine_tab", TAB_CCAS_REG);
    }*/

    /*******************************************************
     * Method name : setButtonNineTabId() Description : set home button nine tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonNineTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_nine_tab", TAB_ID);
        editor.commit();
    }

    public static boolean getIfHomeItemClickEnabled(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("home_item_click", true);

    }

    /*******************************************************
     * Method name : setIfHomeItemClickEnabled() Description : set if home list
     * item click is enabled Parameters : context, result Return type : void
     * Date : 11-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setIfHomeItemClickEnabled(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("home_item_click", result);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonOneGuestBg() Description : get home button one bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonOneGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_one_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonOneGuestBg() Description : set home button one bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonOneGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_one_guest_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonOneGuestTextImage() Description : get home button
     * one text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonOneGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_guest_pos", "2");

    }

    /*******************************************************
     * Method name : setButtonOneGuestTextImage() Description : set home button
     * one text and image Parameters : context, position Return type : void Date
     * : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonOneGuestTextImage(Context context,
                                                  String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_one_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonOneGuestTabId() Description : get home button one
     * guest tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
  /*  public static String getButtonOneGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_guest_tab", TAB_COMMUNICATIONS_GUEST);
    }*/

    /*******************************************************
     * Method name : setButtonOneGuestTabId() Description : set home button one
     * guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonOneGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_one_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonTwoGuestBg() Description : get home button two bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonTwoGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_two_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonTwoGuestBg() Description : set home button two bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_two_guest_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonTwoGuestTextImage() Description : get home button
     * two text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonTwoGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_two_guest_pos", "5");

    }

    /*******************************************************
     * Method name : setButtonTwoGuestTextImage() Description : set home button
     * two text and image Parameters : context, position Return type : void Date
     * : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoGuestTextImage(Context context,
                                                  String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_two_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonTwoGuestTabId() Description : get home button two
     * guest tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
   /* public static String getButtonTwoGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_two_guest_tab", TAB_REPORT);
    }*/

    /*******************************************************
     * Method name : setButtonTwoGuestTabId() Description : set home button two
     * guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_two_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonThreeGuestBg() Description : get home button three
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonThreeGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_three_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonThreeGuestBg() Description : set home button three
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_three_guest_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonThreeGuestTextImage() Description : get home
     * button three text and image Parameters : context Return type : String
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonThreeGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_three_guest_pos","1");

    }

    /*******************************************************
     * Method name : setButtonThreeGuestTextImage() Description : set home
     * button three text and image Parameters : context, position Return type :
     * void Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeGuestTextImage(Context context,
                                                    String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_three_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonThreeGuestTabId() Description : get home button
     * three guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    /*public static String getButtonThreeGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_three_guest_tab", TAB_SOCIAL_MEDIA);
    }*/
    /*******************************************************
     * Method name : setButtonThreeGuestTabId() Description : set home button
     * three guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_three_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFourGuestBg() Description : get home button four
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonFourGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_four_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonFourGuestBg() Description : set home button four
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFourGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_four_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFourGuestTextImage() Description : get home button
     * four text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonFourGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_four_guest_pos", "8");

    }

    /*******************************************************
     * Method name : setButtonFourGuestTextImage() Description : set home button
     * four text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFourGuestTextImage(Context context,
                                                   String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_four_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonFourGuestTabId() Description : get home button
     * four guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
 /*   public static String getButtonFourGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_four_guest_tab", TAB_CONTACT_US_GUEST);
    }*/

    /*******************************************************
     * Method name : setButtonFourGuestTabId() Description : set home button
     * four guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFourGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_four_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFiveGuestBg() Description : get home button five
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonFiveGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_five_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonFiveGuestBg() Description : set home button five
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_five_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFiveGuestTextImage() Description : get home button
     * five text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonFiveGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_five_guest_pos", "4");

    }

    /*******************************************************
     * Method name : setButtonFiveGuestTextImage() Description : set home button
     * five text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveGuestTextImage(Context context,
                                                   String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_five_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonFiveGuestTabId() Description : get home button
     * five guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    /*public static String getButtonFiveGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_five_guest_tab", TAB_CALENDAR_GUEST);
    }*/

    /*******************************************************
     * Method name : setButtonFiveGuestTabId() Description : set home button
     * five guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_five_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSixGuestBg() Description : get home button six bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonSixGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_six_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonSixGuestBg() Description : set home button six bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonSixGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_six_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSixGuestTextImage() Description : get home button
     * six text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonSixGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_six_guest_pos", "6");

    }

    /*******************************************************
     * Method name : setButtonSixGuestTextImage() Description : set home button
     * six text and image Parameters : context, position Return type : void Date
     * : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSixGuestTextImage(Context context,
                                                  String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_six_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonSixGuestTabId() Description : get home button six
     * guest tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    /*public static String getButtonSixGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_six_guest_tab", TAB_GALLERY_GUEST);
    }*/

    /*******************************************************
     * Method name : setButtonSixGuestTabId() Description : set home button six
     * guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSixGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_six_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSevenGuestBg() Description : get home button seven
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonSevenGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_seven_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonSevenGuestBg() Description : set home button seven
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_seven_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSevenGuestTextImage() Description : get home
     * button seven text and image Parameters : context Return type : String
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonSevenGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_seven_guest_pos", "5");

    }

    /*******************************************************
     * Method name : setButtonSevenGuestTextImage() Description : set home
     * button seven text and image Parameters : context, position Return type :
     * void Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenGuestTextImage(Context context,
                                                    String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_seven_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonSevenGuestTabId() Description : get home button
     * seven guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
   /* public static String getButtonSevenGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_seven_guest_tab", TAB_SOCIAL_MEDIA_GUEST);
    }*/

    /*******************************************************
     * Method name : setButtonSixGuestTabId() Description : set home button
     * seven guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_seven_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonEightGuestBg() Description : get home button eight
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonEightGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_eight_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonEightGuestBg() Description : set home button eight
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonEightGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_eight_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonEightGuestTextImage() Description : get home
     * button eight text and image Parameters : context Return type : String
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonEightGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_eight_guest_pos", "4");//TAB_ABOUT_US

    }

    /*******************************************************
     * Method name : setButtonEightGuestTextImage() Description : set home
     * button eight text and image Parameters : context, position Return type :
     * void Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonEightGuestTextImage(Context context,
                                                    String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_eight_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonEightGuestTabId() Description : get home button
     * eight guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
/*
    public static String getButtonEightGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_eight_guest_tab", TAB_SCHOOL_INFORMATION_GUEST);
    }
*/

    /*******************************************************
     * Method name : setButtonEightGuestTabId() Description : set home button
     * eight guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonEightGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_eight_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonNineGuestBg() Description : get home button nine
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonNineGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_nine_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonNineGuestBg() Description : set home button nine
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonNineGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_nine_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonNineGuestTextImage() Description : get home button
     * nine text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonNineGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_nine_guest_pos", "9");

    }

    /*******************************************************
     * Method name : setButtonNineGuestTextImage() Description : set home button
     * nine text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonNineGuestTextImage(Context context,
                                                   String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_nine_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonNineGuestTabId() Description : get home button
     * nine guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
   /* public static String getButtonNineGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_nine_guest_tab", TAB_SETTINGS_GUEST);
    }
*/
    /*******************************************************
     * Method name : setButtonNineGuestTabId() Description : set home button
     * nine guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonNineGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_nine_guest_tab", TAB_ID);
        editor.commit();
    }


    /*******************************************************
     * Method name : getCacheClearStatusForShop() Description : get cache clear
     * status for shop Parameters : context Return type : boolean Date :
     * 24-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getCacheClearStatusForShop(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("shop_clear_cache", true);

    }

    public static void setFireBaseId(Context context, String id) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("firebase id", id);
        editor.commit();
    }

    public static String getFireBaseId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("firebase id", "0");

    }

    public static String getUserId(Context context) {
        String userid = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        userid = prefs.getString("userid", "");
        return userid;
    }

    /*******************************************************
     * Method name : setUserName Description : get user name after login
     * Parameters : context, username Return type : void Date : Oct 28, 2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setUserId(Context context, String userid) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userid", userid);
        editor.commit();
    }

    public static String getUserEmail(Context context) {
        String userid = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        userid = prefs.getString("user_email", "");
        return userid;
    }
    public static String getCalenderPush(Context context) {
        String calenderpush = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        calenderpush = prefs.getString("calenderpush", "0");
        return calenderpush;
    }
    public static String getEmailPush(Context context) {
        String emailpush = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        emailpush = prefs.getString("emailpush", "0");
        return emailpush;
    }
    public static String getMessageBadge(Context context) {
        String messagebadge = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        messagebadge = prefs.getString("messagebadge", "0");
        return messagebadge;
    }
    public static String getCalenderBadge(Context context) {
        String calenderbadge = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        calenderbadge = prefs.getString("calenderbadge", "0");
        return calenderbadge;
    }
    public static String getPhoneNo(Context context) {
        String mobileno = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mobileno = prefs.getString("phone_no", "");
        return mobileno;
    }
    public static String getFullName(Context context) {
        String name = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        name = prefs.getString("name", "");
        return name;
    }

    public static void setLeaveStudentId(Context context, String mLeaveStudentId) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LeaveStudentId", mLeaveStudentId);
        editor.commit();
    }

    public static String getLeaveStudentId(Context context) {
        String mLeaveStudentId = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mLeaveStudentId = prefs.getString("LeaveStudentId", "");
        return mLeaveStudentId;
    }
    public static void setLeaveStudentName(Context context, String mLeaveStudentName) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LeaveStudentName", mLeaveStudentName);
        editor.commit();
    }

    public static String getLeaveStudentName(Context context) {
        String mLeaveStudentName = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mLeaveStudentName = prefs.getString("LeaveStudentName", "");
        return mLeaveStudentName;
    }
    /*******************************************************
     * Method name : setUserName Description : get user name after login
     * Parameters : context, username Return type : void Date : Oct 28, 2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setUserEmail(Context context, String userid) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_email", userid);
        editor.commit();
    }
    public static void setPhoneNo(Context context, String mobileno) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phone_no", mobileno);
        editor.commit();
    }
    public static void setFullName(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.commit();
    }
    public static void setCalenderBadge(Context context, String calenderbadge) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("calenderbadge", calenderbadge);
        editor.commit();
    }
    public static void setsafenote(Context context, String safenotification) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("safeguarding_notification", safenotification);
        editor.commit();
    }
    public static void setMessageBadge(Context context, String messagebadge) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("messagebadge", messagebadge);
        editor.commit();
    }
    public static void setCalenderPush(Context context, String calenderpush) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("calenderpush", calenderpush);
        editor.commit();
    }
    public static void setEmailPush(Context context, String emailpush) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("emailpush", emailpush);
        editor.commit();
    }
    public static void setIsFirstTimeInPA(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_pa", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getIsFirstTimeInPA(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_first_pa", true);
    }

    public static void setIsFirstTimeInPE(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_pe", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getIsFirstTimeInPE(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_first_pe", true);
    }

    public static void setBackParent(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("BackParent", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getIBackParent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("BackParent", true);
    }

    public static void setIsFirstTimeInCalendar(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_calendar", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/





    public static boolean getIsFirstTimeInCalendar(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_first_calendar", true);
    }

    public static boolean getIsFirstTimeInNotification(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_first_notification", true);
    }
    public static void setIsFirstTimeInNotification(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_notification", result);
        editor.commit();
    }
    public static boolean getIsFirstTimeInAttendance(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_first_attendance", true);
    }
    public static void setIsFirstTimeInAttendance(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_attendance", result);
        editor.commit();
    }
    public static void setStudIdForCCA(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StudIdForCCA", result);
        editor.commit();
    }

    //getStudIdForCCA
    public static String getStudIdForCCA(Context context) {
        String StudIdForCCA = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        StudIdForCCA = prefs.getString("StudIdForCCA", "");
        return StudIdForCCA;
    }


    public static void setStudNameForCCA(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StudNameForCCA", result);
        editor.commit();
    }

    public static String getStudNameForCCA(Context context) {
        String StudNameForCCA = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        StudNameForCCA = prefs.getString("StudNameForCCA", "");
        return StudNameForCCA;
    }

    public static void setStudClassForCCA(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StudClassForCCA", result);
        editor.commit();
    }

    public static String getStudClassForCCA(Context context) {
        String StudClassForCCA = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        StudClassForCCA = prefs.getString("StudClassForCCA", "");
        return StudClassForCCA;
    }

    public static void setCCATitle(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("CCATitle", result);
        editor.commit();
    }

    public static String getCCATitle(Context context) {
        String CCATitle = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        CCATitle = prefs.getString("CCATitle", "");
        return CCATitle;
    }


    public static void setCCAItemId(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("CCAItemId", result);
        editor.commit();
    }

    public static String getCCAItemId(Context context) {
        String CCAItemId = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        CCAItemId = prefs.getString("CCAItemId", "");
        return CCAItemId;
    }


    public static void setCCAStudentIdPosition(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("CCAStudentIdPosition", result);
        editor.commit();
    }

    public static String getCCAStudentIdPosition(Context context) {
        String CCAStudentIdPosition = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        CCAStudentIdPosition = prefs.getString("CCAStudentIdPosition", "");
        return CCAStudentIdPosition;
    }
    public static void setGoToSettings(Context context, String mGoToSetting) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("GoToSetting", mGoToSetting);
        editor.commit();
    }

    public static String getGoToSettings(Context context) {
        String mGoToSetting = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mGoToSetting = prefs.getString("GoToSetting", "0");
        return mGoToSetting;
    }


    public static void setToggle(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("toggle", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getToggle(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("toggle", false);
    }

    public static void setFbkey(Context context, String mFbKey) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("FbKey", mFbKey);
        editor.commit();
    }

   /* public static String getFbKey(Context context) {
        String mFbKey = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mFbKey = prefs.getString("FbKey", FB_TOKEN);
        return mFbKey;
    }*/

    public static void setInstaKey(Context context, String mFbKey) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("InstaKey", mFbKey);
        editor.commit();
    }

    /*public static String getInstaKey(Context context) {
        String mFbKey = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mFbKey = prefs.getString("InstaKey", INSTA_TOKEN);
        return mFbKey;
    }*/

    public static void setYouKey(Context context, String mFbKey) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("youKey", mFbKey);
        editor.commit();
    }

    /*public static String getYouKey(Context context) {
        String mFbKey = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mFbKey = prefs.getString("youKey", YOTUBE_CHANNEL_ID);
        return mFbKey;
    }*/


    public static void setUpdate(Context context, String updateapp) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("updateapp", updateapp);
        editor.commit();
    }

    public static String getUpdate(Context context) {
        String mFbKey = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mFbKey = prefs.getString("updateapp", "0");
        return mFbKey;
    }

    /*********** Force Update **********/
    public static void setVersionFromApi(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("version_api", result);
        editor.commit();
    }

    public static String getVersionFromApi(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("version_api", "");
    }
    /*********** DataCollectiom **********/
    public static void setDataCollection(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("data_collection_flag", result);
        editor.commit();
    }

    public static String getDataCollection(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("data_collection_flag", "");
    }
    /*********** DataCollectiom trigger type**********/
    public static void setDataCollectionTriggerType(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("trigger_type", result);
        editor.commit();
    }

    public static String getDataCollectionTriggerType(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("trigger_type", "");
    }



    public static String getTimeTable(Context context) {
        String mTimeTable = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mTimeTable = prefs.getString("timetable", "0");
        return mTimeTable;
    }
    public static void setTimeTable(Context context, String mTimeTable) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("timetable", mTimeTable);
        editor.commit();
    }
    public static String getTimeTableGroup(Context context) {
        String mTimeTableGroup = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mTimeTableGroup = prefs.getString("timetablegroup", "0");
        return mTimeTableGroup;
    }
    public static void setTimeTableGroup(Context context, String mTimeTableGroup) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("timetablegroup", mTimeTableGroup);
        editor.commit();
    }
    public static String getSafeGuarding(Context context) {
        String mSafeGuarding = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mSafeGuarding = prefs.getString("safeguarding", "0");
        return mSafeGuarding;
    }
    public static void setSafeGuarding(Context context, String mSafeGuarding) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("safeguarding", mSafeGuarding);
        editor.commit();
    }
    public static String getAlreadyTriggered(Context context) {
        String mSafeGuarding = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mSafeGuarding = prefs.getString("already_triggered", "0");
        return mSafeGuarding;
    }
    public static void setAlreadyTriggered(Context context, String mSafeGuarding) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("already_triggered", mSafeGuarding);
        editor.commit();
    }
    public static String getAttendance(Context context) {
        String mAttendance = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mAttendance = prefs.getString("attendance", "0");
        return mAttendance;
    }
    public static void setAttendance(Context context, String mAttendance) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("attendance", mAttendance);
        editor.commit();
    }
    public static void setAbsence(Context context, String mAbscence) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("report_absense", mAbscence);
        editor.commit();
    }
    public static String getAbsence(Context context) {
        String mAbscence = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mAbscence = prefs.getString("report_absense", "0");
        return mAbscence;
    }

    public static String getsafenote(Context context) {
        String mAbscence = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mAbscence = prefs.getString("safeguarding_notification", "0");
        return mAbscence;
    }
    /*If the timetableavailable is 0 then timetable not available
     * if timetableavailable is 1 then timetable is available */
    public static void setTimeTableAvailable(Context context, String timetableAvailable) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("timetableAvailable", timetableAvailable);
        editor.commit();
    }
    public static String getTimeTableAvailable(Context context) {
        String timetableAvailable = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        timetableAvailable = prefs.getString("timetableAvailable", "0");
        return timetableAvailable;
    }
    // Data collection insurance data
    /*public static void saveInsuranceArrayList(ArrayList<InsuranceDataCollectionModel> list, String key, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

   /* public static ArrayList<InsuranceDataCollectionModel> getInsuranceArrayList(String key,Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<InsuranceDataCollectionModel>>() {}.getType();
        return gson.fromJson(json, type);
    }*/
    /*public static void saveOwnDetailArrayList(ArrayList<OwnContactModel> list, String key, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

  /*  public static ArrayList<OwnContactModel> getOwnDetailArrayList(String key,Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<OwnContactModel>>() {}.getType();
        return gson.fromJson(json, type);
    }*/
  /*  public static void saveGlobalListArrayList(ArrayList<GlobalListDataModel> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("Global_List", json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

   /* public static ArrayList<GlobalListDataModel> getGlobalListArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("Global_List", null);
        Type type = new TypeToken<ArrayList<GlobalListDataModel>>() {}.getType();
        return gson.fromJson(json, type);
    }*/


  /*  public static void  saveContactTypeArrayList(ArrayList<ContactTypeModel> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("ContactType", json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

   /* public static ArrayList<ContactTypeModel> getContactTypeArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("ContactType", null);
        Type type = new TypeToken<ArrayList<ContactTypeModel>>() {}.getType();
        return gson.fromJson(json, type);
    }
    public static void  saveInsuranceDetailArrayList(ArrayList<InsuranceDetailModel> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("Insurance_Detail", json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

   /* public static ArrayList<InsuranceDetailModel> getInsuranceDetailArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("Insurance_Detail", null);
        Type type = new TypeToken<ArrayList<InsuranceDetailModel>>() {}.getType();
        return gson.fromJson(json, type);
    }*/

    /*public static void  savePassportDetailArrayList(ArrayList<PassportDetailModel> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("Passport_Detail", json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

    /*public static ArrayList<PassportDetailModel> getPassportDetailArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("Passport_Detail", null);
        Type type = new TypeToken<ArrayList<PassportDetailModel>>() {}.getType();
        return gson.fromJson(json, type);
    }*/


    public static void setDisplayMessage(Context context, String displayMessage) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("displayMessage", displayMessage);
        editor.commit();
    }
    public static String getDisplayMessage(Context context) {
        String displayMessage = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        displayMessage = prefs.getString("displayMessage", "");
        return displayMessage;
    }

    public static void setIsValueEmpty(Context context, String displayMessage) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("IsValueEmpty", displayMessage);
        editor.commit();
    }
    public static String getIsValueEmpty(Context context) {
        String displayMessage = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        displayMessage = prefs.getString("IsValueEmpty", "");
        return displayMessage;
    }

    public static void setConfirmButton(Context context, String displayMessage) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ConfirmButton", displayMessage);
        editor.commit();
    }
    public static String getConfirmButton(Context context) {
        String displayMessage = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        displayMessage = prefs.getString("ConfirmButton", "");
        return displayMessage;
    }

    public static void setInsuranceCompleted(Context context, String displayMessage) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("InsuranceCompleted", displayMessage);
        editor.commit();
    }
    public static String getInsuranceCompleted(Context context) {
        String displayMessage = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        displayMessage = prefs.getString("InsuranceCompleted", "");
        return displayMessage;
    }

    public static void setWhoValueEmpty(Context context, String displayMessage) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("WhoValueEmpty", displayMessage);
        editor.commit();
    }
    public static String getWhoValueEmpty(Context context) {
        String displayMessage = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        displayMessage = prefs.getString("WhoValueEmpty", "");
        return displayMessage;
    }

  /*  public static void  saveKinDetailsArrayList(ArrayList<KinModel> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("KinDetails", json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

   /* public static ArrayList<KinModel> getKinDetailsArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("KinDetails", null);
        Type type = new TypeToken<ArrayList<KinModel>>() {}.getType();
        return gson.fromJson(json, type);
    }*/
    /*public static void  saveKinDetailsArrayListShow(ArrayList<KinModel> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("KinDetails", json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

    /*public static ArrayList<KinModel> getKinDetailsArrayListShow(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("KinDetails", null);
        Type type = new TypeToken<ArrayList<KinModel>>() {}.getType();
        return gson.fromJson(json, type);
    }*/


    public static void saveFirstFragmentJSONArrayList(ArrayList<String> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("FirstFragmentJSON", json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public static ArrayList<String> getFirstFragmentJSONArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("FirstFragmentJSON", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void saveSecondFragmentJSONArrayList(ArrayList<String> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("SecondFragmentJSON", json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public static ArrayList<String> getSecondFragmentJSONArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("SecondFragmentJSON", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void saveThirdFragmentJSONArrayList(ArrayList<String> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("ThirdFragmentJSON", json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public static ArrayList<String> getThirdFragmentJSONArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("ThirdFragmentJSON", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void saveOwnDetailsJSONArrayList(ArrayList<String> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("OwnDetails", json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public static ArrayList<String> getOwnDetailsJSONArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("OwnDetails", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    /*public static void savePassportPathArrayList(ArrayList<PassportImageModel> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("PassportPath", json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

   /* public static ArrayList<PassportImageModel> getPassportPathArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("PassportPath", null);
        Type type = new TypeToken<ArrayList<PassportImageModel>>() {}.getType();
        return gson.fromJson(json, type);
    }*/

    /*public static void saveVisaPathArrayList(ArrayList<VISAimageModel> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("VisaPath", json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

    /*public static ArrayList<VISAimageModel> getVisaPathArrayList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("VisaPath", null);
        Type type = new TypeToken<ArrayList<VISAimageModel>>() {}.getType();
        return gson.fromJson(json, type);
    }*/

    public static void setIsApplicant(Context context, String isApplicant) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("isApplicant", isApplicant);
        editor.commit();
    }
    public static String getIsApplicant(Context context) {
        String isApplicant = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        isApplicant = prefs.getString("isApplicant", "");
        return isApplicant;
    }

    public static void setSuspendTrigger(Context context, String timetableAvailable) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("SuspendTrigger", timetableAvailable);
        editor.commit();
    }
    public static String getSuspendTrigger(Context context) {
        String timetableAvailable = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        timetableAvailable = prefs.getString("SuspendTrigger", "0");
        return timetableAvailable;
    }

   /* public static void  saveInsuranceStudentList(ArrayList<StudentModelNew> list, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("studentData", json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public static ArrayList<StudentModelNew> getInsuranceStudentList(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("studentData", null);
        Type type = new TypeToken<ArrayList<StudentModelNew>>() {}.getType();
        return gson.fromJson(json, type);
    }*/

    public static void setOTPSignature(Context context, String timetableAvailable) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("OTPSignature", timetableAvailable);
        editor.commit();
    }
    public static String getOTPSignature(Context context) {
        String timetableAvailable = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        timetableAvailable = prefs.getString("OTPSignature", "0");
        return timetableAvailable;
    }
    /*********** DataCollectiom trigger type**********/
    public static void setPreviousTriggerType(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("previous_trigger_type", result);
        editor.commit();
    }

    public static String getPreviousTriggerType(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("previous_trigger_type", "");
    }

    /*********** DataCollectiom trigger type**********/
    public static void setLoggedInStatus(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("logged_in_status", result);
        editor.commit();
    }

    public static String getLoggedInStatus(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("logged_in_status", "");
    }



    /***********Home List Type ( Type number specified in strings.xml)**********/
    public static void setHomeListType(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("home_list_type", result);
        editor.commit();
    }

    public static String getHomeListType(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("home_list_type", "16");
    }


    /*********** Home List Type - ReportAbsence ( Type number specified in strings.xml)**********/
    public static void setHomeReportAbsence(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("home_report_absence", result);
        editor.commit();
    }

    public static String getHomeReportAbsence(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("home_report_absence", "");
    }


    /*********** Home List Type - SafeGuarding ( Type number specified in strings.xml)**********/
    public static void setHomeSafeGuarding(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("home_safe_guarding", result);
        editor.commit();
    }

    public static String getHomeSafeGuarding(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("home_safe_guarding", "");
    }


    /*********** Home List Type - Attendance ( Type number specified in strings.xml)**********/
    public static void setHomeAttendance(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("home_attendance", result);
        editor.commit();
    }

    public static String getHomeAttendance(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("home_attendance", "");
    }

    /*********** Home List Type - TimeTable ( Type number specified in strings.xml)**********/
    public static void setHomeTimetable(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("home_timetable", result);
        editor.commit();
    }

    public static String getHomeTimetable(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("home_timetable", "");
    }


    public static void setSafeGuardingGroup(Context context, String mSafeGuarding) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("safeguarding_group", mSafeGuarding);
        editor.commit();
    }

    public static String getSafeGuardingGroup(Context context) {
        String mSafeGuarding = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mSafeGuarding = prefs.getString("safeguarding_group", "0");
        return mSafeGuarding;
    }


}