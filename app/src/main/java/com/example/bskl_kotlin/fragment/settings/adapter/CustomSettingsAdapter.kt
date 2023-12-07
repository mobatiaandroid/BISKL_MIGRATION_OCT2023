package com.example.bskl_kotlin.fragment.settings.adapter

import ApiClient
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.home.HomeListAdapter
import com.example.bskl_kotlin.activity.home.model.AppFeatureModel
import com.example.bskl_kotlin.activity.home.model.ResponseArrayUserModel
import com.example.bskl_kotlin.activity.home.model.UserDetailsApiModel
import com.example.bskl_kotlin.activity.home.model.UserDetailsModel
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.settings.SettingsFragment
import com.example.bskl_kotlin.fragment.settings.model.CalenderPushApiModel
import com.example.bskl_kotlin.fragment.settings.model.EmailPushApiModel
import com.example.bskl_kotlin.fragment.settings.model.EmailPushModel
import com.example.bskl_kotlin.fragment.settings.model.TriggerUserModel
import com.example.bskl_kotlin.manager.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomSettingsAdapter : BaseAdapter {
    private var mSettingsList: ArrayList<String>
    var isRegUser:Boolean?=null
    var calenderStatus = ""
    private var mTitle: String? = null
    private var mTabId: String? = null

    constructor(
        context: Context,
        arrList: ArrayList<String>, title: String?, tabId: String?
    ) {
        mContext = context
        mSettingsList = arrList
        isRegUser=false
        mTitle = title
        mTabId = tabId
    }

    constructor(
        context: Context,
        arrList: ArrayList<String>,
        isRegUser:Boolean
    ) {
        mContext = context
        mSettingsList = arrList
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    override fun getCount(): Int {
        // TODO Auto-generated method stub
        return mSettingsList.size
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    override fun getItem(position: Int): Any {
        // TODO Auto-generated method stub
        return mSettingsList[position]
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stub
        return position.toLong()
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        val holder: ViewHolder
        if (convertView == null) {
            val inflate = LayoutInflater.from(mContext)
            convertView = inflate.inflate(R.layout.custom_settings_list_adapter, null)
            holder =  ViewHolder()
            holder.mTitleTxt = convertView.findViewById(R.id.listTxtTitle)
            holder.txtUser = convertView.findViewById(R.id.txtUser)
            holder.mImageView = convertView.findViewById(R.id.arrowImg)
            holder.toggle = convertView.findViewById(R.id.toggle)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        try {
            /*mViewHolder!!.mTitleTxt = convertView.findViewById(R.id.listTxtTitle)
            mViewHolder!!.txtUser = convertView.findViewById(R.id.txtUser)
            mViewHolder!!.mImageView = convertView.findViewById(R.id.arrowImg)
            mViewHolder!!.toggle = convertView.findViewById(R.id.toggle)*/
            holder.mTitleTxt!!.setText(mSettingsList[position])
            if (mSettingsList.size == 7) {
                if (position == 1) {
                    if (PreferenceManager().getCalenderPush(mContext).equals("1")) {
                        holder.toggle!!.setImageResource(R.drawable.switch_off)
                    } else {
                        holder.toggle!!.setImageResource(R.drawable.switch_on)
                    }
                }
                if (position == 2) {
                    if (PreferenceManager().getEmailPush(mContext).equals("1")) {
                        holder.toggle!!.setImageResource(R.drawable.switch_off)
                    } else {
                        holder.toggle!!.setImageResource(R.drawable.switch_on)
                    }
                }
                if (position == 6) {
                    holder.txtUser!!.setVisibility(View.VISIBLE)
                    holder.mTitleTxt!!.setText("Logout")
                    holder.txtUser!!.setText("(" + PreferenceManager().getUserEmail(mContext) + ")")
                    holder.mImageView!!.setBackgroundResource(R.drawable.logout)
                } else {
                    val lp =
                        holder.mTitleTxt!!.getLayoutParams() as RelativeLayout.LayoutParams
                    lp.addRule(RelativeLayout.CENTER_VERTICAL)
                    holder.mTitleTxt!!.setLayoutParams(lp)
                }
                if (position == 1) {
                    holder.toggle!!.setVisibility(View.VISIBLE)
                    holder.mImageView!!.setVisibility(View.GONE)
                    holder.toggle!!.setOnClickListener(View.OnClickListener {
                        if (PreferenceManager().getCalenderPush(mContext).equals("1")) {
                            Log.e("switch","off 0")
                            holder.toggle!!.setImageResource(R.drawable.switch_off)
                            calenderStatus = " 0"
                            calenderPush(calenderStatus)
                        } else {
                            Log.e("switch","on 1")
                            holder.toggle!!.setImageResource(R.drawable.switch_on)
                            calenderStatus = "1"
                            calenderPush( calenderStatus)
                        }
                    })

                }
                if (position == 2) {
                    holder.toggle!!.setVisibility(View.VISIBLE)
                    holder.mImageView!!.setVisibility(View.GONE)
                    holder.toggle!!.setOnClickListener(View.OnClickListener {
                        if (PreferenceManager().getEmailPush(mContext).equals("1")) {
                            holder.toggle!!.setImageResource(R.drawable.switch_off)
                            emailStatus = " 0"
                            emailPush( emailStatus)
                        } else {
                            if (AppUtils().isNetworkConnected(mContext)) {
                                showDialogAlertEmail(
                                    mContext as Activity,
                                    "Confirm?",
                                    "If you trun off email notifications, you won't receive the copy of notification messages as email",
                                    R.drawable.questionmark_icon,
                                    R.drawable.round,
                                    holder
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    mContext.resources.getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }
                        }
                    })
                }
            } else {
                if (position == 1) {
                    if (PreferenceManager().getCalenderPush(mContext).equals("1")) {
                        holder.toggle!!.setImageResource(R.drawable.switch_off)
                    } else {
                        holder.toggle!!.setImageResource(R.drawable.switch_on)
                    }
                }
                if (position == 2) {
                    if (PreferenceManager().getEmailPush(mContext).equals("1")) {
                        holder.toggle!!.setImageResource(R.drawable.switch_off)
                    } else {
                        holder.toggle!!.setImageResource(R.drawable.switch_on)
                    }
                }
                if (position == 7) {
                    holder.txtUser!!.setVisibility(View.VISIBLE)
                    holder.mTitleTxt!!.setText("Logout")
                    holder.txtUser!!.setText("(" + PreferenceManager().getUserEmail(mContext) + ")")
                    holder.mImageView!!.setBackgroundResource(R.drawable.logout)
                } else {
                    val lp =
                        holder.mTitleTxt!!.getLayoutParams() as RelativeLayout.LayoutParams
                    lp.addRule(RelativeLayout.CENTER_VERTICAL)
                    holder.mTitleTxt!!.setLayoutParams(lp)
                }
                if (position == 1) {
                    holder.toggle!!.setVisibility(View.VISIBLE)
                    holder.mImageView!!.setVisibility(View.GONE)
                    holder.toggle!!.setOnClickListener(View.OnClickListener {
                        if (PreferenceManager().getCalenderPush(mContext).equals("1")) {
                            holder.toggle!!.setImageResource(R.drawable.switch_off)
                            calenderStatus = " 0"
                            calenderPush( calenderStatus)
                        } else {
                            holder.toggle!!.setImageResource(R.drawable.switch_on)
                            calenderStatus = "1"
                            calenderPush( calenderStatus)
                        }
                    })
                }
                if (position == 2) {
                    holder.toggle.setVisibility(View.VISIBLE)
                    holder.mImageView.setVisibility(View.GONE)
                    holder.toggle.setOnClickListener(View.OnClickListener {
                        if (PreferenceManager().getEmailPush(mContext).equals("1")) {
                            holder.toggle.setImageResource(R.drawable.switch_off)
                            emailStatus = " 0"
                            emailPush(emailStatus)
                        } else {
                            if (AppUtils().isNetworkConnected(mContext)) {
                                showDialogAlertEmail(
                                    mContext as Activity,
                                    "Confirm?",
                                    "If you trun off email notifications, you won't receive the copy of notification messages as email",
                                    R.drawable.questionmark_icon,
                                    R.drawable.round,
                                    holder
                                )
                            } else {
                                AppUtils().showDialogAlertDismiss(
                                    mContext as Activity,
                                    "Network Error",
                                    mContext.resources.getString(R.string.no_internet),
                                    R.drawable.nonetworkicon,
                                    R.drawable.roundred
                                )
                            }
                        }
                    })
                }
            }

        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }

    fun calenderPush(calenderStatus: String) {

        var calpush=CalenderPushApiModel( PreferenceManager().getUserId(mContext).toString(),
            calenderStatus)
        val call: Call<TriggerUserModel> = ApiClient.getClient.calenderpush(
            calpush,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString())

        call.enqueue(object : Callback<TriggerUserModel> {
            override fun onFailure(call: Call<TriggerUserModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<TriggerUserModel>,
                response: Response<TriggerUserModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){

                        showSettingUserDetail(mContext)
                       // SettingsFragment().showSettingUserDetail(mContext)



                    }
                }

            }
        })
    }
    fun emailPush(emailStatus: String) {

        var emailpushmodel=EmailPushApiModel(PreferenceManager().getUserId(mContext).toString(),
            emailStatus)
        val call: Call<TriggerUserModel> = ApiClient.getClient.emailpush(
            emailpushmodel,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString()
        )

        call.enqueue(object : Callback<TriggerUserModel> {
            override fun onFailure(call: Call<TriggerUserModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<TriggerUserModel>,
                response: Response<TriggerUserModel>
            ) {

                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")) {
                    if (responsedata!!.response.statuscode.equals("303")) {
                        if (PreferenceManager().getEmailPush(mContext).equals("0")) {
                        }

                       // SettingsFragment().showSettingUserDetail(mContext)
                        showSettingUserDetail(mContext)


                    }
                }

            }
        })
    }

    fun showDialogAlertDismiss(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun showDialogAlertEmail(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int,
        holder:ViewHolder
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_layout)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            holder.toggle!!.setImageResource(R.drawable.switch_on)
            emailStatus = " 1"
            if (AppUtils().checkInternet(mContext)) {
                emailPush(

                    emailStatus
                )
            } else {
                AppUtils().showDialogAlertDismiss(
                    mContext as Activity,
                    "Network Error",
                    mContext.resources.getString(R.string.no_internet),
                    R.drawable.nonetworkicon,
                    R.drawable.roundred
                )
            }
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById<Button>(R.id.btn_Cancel)
        dialogButtonCancel.setOnClickListener {
            holder.toggle!!.setImageResource(R.drawable.switch_on)
            dialog.dismiss()
        }
        dialog.show()
    }

    inner class ViewHolder {
        lateinit var mTitleTxt: TextView
       lateinit var mImageView: ImageView
       lateinit var toggle: ImageView
       lateinit var txtUser: TextView
    }

    companion object {
        private lateinit var mContext: Context
        var emailStatus = ""
        var mViewHolder: ViewHolder? = null





    }
    fun showSettingUserDetail(mContext: Context) {
        Log.e("call","settingapi")

        var userdetail= UserDetailsApiModel(PreferenceManager().getUserId(mContext).toString())
        val call: Call<UserDetailsModel> = ApiClient.getClient.user_details(
            userdetail,"Bearer "+PreferenceManager().getaccesstoken(mContext).toString()
        )

        call.enqueue(object : Callback<UserDetailsModel> {
            override fun onFailure(call: Call<UserDetailsModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)

            }

            override fun onResponse(
                call: Call<UserDetailsModel>,
                response: Response<UserDetailsModel>
            ) {
                Log.e("success",response.body().toString())
                val responsedata = response.body()
                if (responsedata!!.responsecode.equals("200")){
                    if (responsedata!!.response.statuscode.equals("303")){

                        val respObj: ResponseArrayUserModel = responsedata!!.response.responseArray
                        // PreferenceManager().setUserId(mContext, respObj.optString(JTAG_USER_ID));
                        // PreferenceManager().setUserId(mContext, respObj.optString(JTAG_USER_ID));
                        val userId = respObj.userid
                        PreferenceManager().setPhoneNo(
                            mContext,
                            respObj.mobileno
                        )
                        PreferenceManager().setFullName(
                            mContext,
                            respObj.name
                        )
                        PreferenceManager().setCalenderPush(
                            mContext,
                            respObj.calenderpush
                        )
                        PreferenceManager().setEmailPush(
                            mContext,
                            respObj.emailpush
                        )
                        PreferenceManager().setMessageBadge(
                            mContext,
                            respObj.messagebadge
                        )
                        PreferenceManager().setCalenderBadge(
                            mContext,
                            respObj.calenderbadge
                        )
                        val respObjAppFeature: AppFeatureModel = responsedata!!.response.app_feature
                        PreferenceManager().setTimeTable(
                            mContext,
                            respObjAppFeature.timetable
                        )
                        PreferenceManager().setSafeGuarding(
                            mContext,
                            respObjAppFeature.safeguarding
                        )
                        PreferenceManager().setAttendance(
                            mContext,
                            respObjAppFeature.attendance
                        )
                        if (!PreferenceManager().getUserId(mContext)
                                .equals("")
                        ) {
                            if (PreferenceManager().getIsVisible(mContext)
                                    .equals("0")
                            ) {
                                //user is not alumini
                                if (PreferenceManager().getIsApplicant(mContext)
                                        .equals("0")
                                ) {
                                    // user is not applicant
                                    if (PreferenceManager().getReportMailMerge(mContext)
                                            .equals("1")
                                    ) {
                                        // current user with report mail merge enable
                                        if (!PreferenceManager().getDataCollection(mContext)
                                                .equals("1")
                                        ) {
                                            // data collection trigger available
                                            isRegUser=true
                                            notifyDataSetChanged()
                                            /*mSettingsList.setAdapter(
                                                CustomSettingsAdapter(
                                                    mContext,
                                                    mSettingsListArrayDataCollection
                                                )
                                            )*/
                                        } else {
                                            // data collection trigger not available
                                            isRegUser = true
                                            notifyDataSetChanged()
                                            /* mSettingsList.setAdapter(
                                                 CustomSettingsAdapter(
                                                     mContext,
                                                     mSettingsListArray
                                                 )
                                             )*/
                                        }
                                    } else {
                                        // current user with report mail merge enable
                                        isRegUser = true
                                        notifyDataSetChanged()
                                        /* mSettingsList.setAdapter(
                                             CustomSettingsAdapter(
                                                 mContext,
                                                 mSettingsListArray
                                             )
                                         )*/
                                    }
                                } else {
                                    // user is applicant
                                    isRegUser = true
                                    notifyDataSetChanged()
                                    /* mSettingsList.setAdapter(
                                         CustomSettingsAdapter(
                                             mContext,
                                             mSettingsListArray
                                         )
                                     )*/
                                }
                            } else {
                                // user is alumini
                                isRegUser = true
                                notifyDataSetChanged()
                                /*  mSettingsList.setAdapter(
                                      CustomSettingsAdapter(
                                          mContext,
                                          mSettingsListArray
                                      )
                                  )*/
                            }
                        }
                        if (!PreferenceManager().getUserId(mContext)
                                .equals("")
                        ) {
                            if (PreferenceManager().getIsVisible(mContext)
                                    .equals("0")
                            ) {
                                //user is not alumini
                                if (PreferenceManager().getIsApplicant(mContext)
                                        .equals("0")
                                ) {
                                    // user is not applicant
                                    if (PreferenceManager().getReportMailMerge(mContext)
                                            .equals("1")
                                    ) {
                                        // current user with report mail merge enable
                                        if (!PreferenceManager().getDataCollection(mContext)
                                                .equals("1")
                                        ) {
                                            // data collection trigger available
                                            isRegUser = true
                                            notifyDataSetChanged()
                                            /*mSettingsList.setAdapter(
                                                CustomSettingsAdapter(
                                                    mContext,
                                                    mSettingsListArrayDataCollection
                                                )
                                            )*/
                                        } else {
                                            // data collection trigger not available
                                            isRegUser = true
                                            notifyDataSetChanged()
                                            /* mSettingsList.setAdapter(
                                                 CustomSettingsAdapter(
                                                     mContext,
                                                     mSettingsListArray
                                                 )
                                             )*/
                                        }
                                    } else {
                                        // current user with report mail merge enable
                                        isRegUser = true
                                        notifyDataSetChanged()
                                        /*mSettingsList.setAdapter(
                                            CustomSettingsAdapter(
                                                mContext,
                                                mSettingsListArray
                                            )
                                        )*/
                                    }
                                } else {
                                    // user is applicant
                                    isRegUser = true
                                    notifyDataSetChanged()
                                    /*mSettingsList.setAdapter(
                                        CustomSettingsAdapter(
                                            mContext,
                                            mSettingsListArray
                                        )
                                    )*/
                                }
                            } else {
                                // user is alumini
                                isRegUser = true
                                notifyDataSetChanged()
                                /*mSettingsList.setAdapter(
                                    CustomSettingsAdapter(
                                        mContext,
                                        mSettingsListArray
                                    )
                                )*/
                            }
                        }
                        if (!PreferenceManager().getUserId(mContext)
                                .equals("")
                        ) {
                            if (PreferenceManager().getIsVisible(mContext)
                                    .equals("0")
                            ) {
                                //user is not alumini
                                if (PreferenceManager().getIsApplicant(mContext)
                                        .equals("0")
                                ) {
                                    // user is not applicant
                                    if (PreferenceManager().getReportMailMerge(mContext)
                                            .equals("1")
                                    ) {
                                        // current user with report mail merge enable
                                        if (!PreferenceManager().getDataCollection(mContext)
                                                .equals("1")
                                        ) {
                                            // data collection trigger available
                                            isRegUser = true
                                            notifyDataSetChanged()
                                            /* mSettingsList.setAdapter(
                                                 CustomSettingsAdapter(
                                                     mContext,
                                                     mSettingsListArrayDataCollection
                                                 )
                                             )*/
                                        } else {
                                            // data collection trigger not available
                                            isRegUser = true
                                            notifyDataSetChanged()
                                            /* mSettingsList.setAdapter(
                                                 CustomSettingsAdapter(
                                                     mContext,
                                                     mSettingsListArray
                                                 )
                                             )*/
                                        }
                                    } else {
                                        // current user with report mail merge enable
                                        isRegUser = true
                                        notifyDataSetChanged()
                                        /* mSettingsList.setAdapter(
                                             CustomSettingsAdapter(
                                                 mContext,
                                                 mSettingsListArray
                                             )
                                         )*/
                                    }
                                } else {
                                    // user is applicant
                                    isRegUser = true
                                    notifyDataSetChanged()
                                    /* mSettingsList.setAdapter(
                                         CustomSettingsAdapter(
                                             mContext,
                                             mSettingsListArray
                                         )
                                     )*/

                                }
                            } else {
                                // user is alumini
                                isRegUser = true
                                notifyDataSetChanged()
                                /* mSettingsList.setAdapter(
                                     CustomSettingsAdapter(
                                         mContext,
                                         mSettingsListArray
                                     )
                                 )*/
                            }
                        }


                    }
                }

            }
        })
    }
}