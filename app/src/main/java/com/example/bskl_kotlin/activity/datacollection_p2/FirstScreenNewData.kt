package com.example.bskl_kotlin.activity.datacollection_p2
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.datacollection_p2.adapter.FamilyKinRecyclerAdapter
import com.example.bskl_kotlin.activity.datacollection_p2.model.ContactTypeModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.GlobalListDataModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.KinModel
import com.example.bskl_kotlin.activity.datacollection_p2.model.OwnContactModel
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.manager.AppController
import com.example.bskl_kotlin.manager.CommonClass
import com.example.bskl_kotlin.manager.countrypicker.CountryCodePicker
import com.example.bskl_kotlin.recyclerviewmanager.RecyclerItemListener
import com.google.gson.Gson
import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog


class FirstScreenNewData : Fragment() {
    var confirmBtnMain: Button? = null
    var messageTxt: TextView? =
        null
    var nameOwnDetailTxt: TextView? = null
    var contactTypeOwnDetailTxt: android.widget.TextView? = null
    var HelpView: android.widget.TextView? = null
    lateinit var mContext: Context
    var changedField = ""
    var request_state = ""
    var NewChangeState = "5"
    var id = ""
    var EmailExist: String? = null
    var NEW = "NO"
    var userId = ""
    var studentId = ""
    var updated_at = ""
    var user_mobile = ""
    var created_at: String? = null
    var EventStatus = "5"
    var Kin_Size = 0
    var KinClicked_Size = 0
    var corresspondanceOwn = ""
    var contactOwn = ""
    var justContact = ""
    var recyclerPosition = 0
    var ClickedRecyclerPosition = 0
    var IsValueEmpty = ""
    var WhoValueEmpty = ""
    var LALA = false
    var ownDetailViewRelative: RelativeLayout? = null

    //    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.+[a-z]+";
    var emailPattern =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    var mOwnContactArrayList: ArrayList<OwnContactModel>? = null
    var GlobalList: ArrayList<GlobalListDataModel>? = null
    var ContactList: ArrayList<ContactTypeModel>? = null
    var KinArray: ArrayList<KinModel> = ArrayList<KinModel>()
    var NoDataLayout: LinearLayout? = null
    var RecyclerLayout: android.widget.LinearLayout? = null
    var mRecyclerView: RecyclerView? = null
    var familyKinRecyclerAdapter: FamilyKinRecyclerAdapter? = null
    var Fname: EditText? = null
    lateinit var spinnerDialog: SpinnerDialog
    var Lname: EditText? = null
    var Email: EditText? = null
    var Concatc_details_phone: android.widget.EditText? = null
    var NoContentPlusIcon: ImageView? =
        null
    var RecyclerPlusIcon: android.widget.ImageView? = null
    var CloseIcon: android.widget.ImageView? = null
    var SubmitKinArray: ArrayList<KinModel>? = null
    var GSONarray = ArrayList<String>()
    var OwnDetailArray = ArrayList<String>()
    var tempId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_first_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        confirmBtnMain = requireView().findViewById<Button>(R.id.confirmBtn)
        messageTxt = requireView().findViewById<TextView>(R.id.messageTxt)
        nameOwnDetailTxt = requireView().findViewById<TextView>(R.id.nameOwnDetailTxt)
        ownDetailViewRelative =
            requireView().findViewById<RelativeLayout>(R.id.ownDetailViewRelative)
        contactTypeOwnDetailTxt = requireView().findViewById<TextView>(R.id.contactTypeOwnDetailTxt)
        HelpView = requireView().findViewById<TextView>(R.id.helpView)
        NoContentPlusIcon = requireView().findViewById<ImageView>(R.id.plusImgNoContent)
        RecyclerPlusIcon = requireView().findViewById<ImageView>(R.id.RecyclerPlusBtn)
        CloseIcon = requireView().findViewById<ImageView>(R.id.closeImg)
        NoDataLayout = requireView().findViewById<LinearLayout>(R.id.NoDataLinearLayout)
        RecyclerLayout = requireView().findViewById<LinearLayout>(R.id.RecyclerLinearLayout)
        mRecyclerView = requireView().findViewById<RecyclerView>(R.id.familyContactRecycler)

        HelpView!!.setOnClickListener {
            ShowHelpDialog(mContext, "Help", R.drawable.questionmark_icon, R.drawable.round)
        }
        CloseIcon!!.setOnClickListener {
            val dialog = Dialog(mContext)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.alert_dialogue_ok_layout_data_collection)
            val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
            icon.setBackgroundResource(R.drawable.round)
            icon.setImageResource(R.drawable.exclamationicon)
            val text = dialog.findViewById<TextView>(R.id.text_dialog)
            val textHead = dialog.findViewById<TextView>(R.id.alertHead)
            text.text = "Please update this information next time"
            textHead.text = "Alert"

            val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
            dialogButton.setOnClickListener {
                PreferenceManager().setSuspendTrigger(mContext, "1")
                dialog.dismiss()
                requireActivity().finish()
            }

            dialog.show()
        }
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mRecyclerView!!.setLayoutManager(mLayoutManager)
        mRecyclerView!!.setItemAnimator(DefaultItemAnimator())



        changedField = "3"
        mOwnContactArrayList = PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)


        //Created By Aparna
        if (PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext) == null) {
            //   System.out.println("null works");
        }

        // System.out.println("OwnDetail ArrayList"+mOwnContactArrayList.size);

        // System.out.println("OwnDetail ArrayList"+mOwnContactArrayList.size);
        if (mOwnContactArrayList!!.size > 0) {
            if (!mOwnContactArrayList!![0].name
                    .equals("") && !mOwnContactArrayList!![0].last_name
                    .equals("")
            ) {
                nameOwnDetailTxt!!.setText(mOwnContactArrayList!![0].name + " " + mOwnContactArrayList!![0].last_name)
            } else if (!mOwnContactArrayList!![0].name
                    .equals("") && mOwnContactArrayList!![0].last_name
                    .equals("")
            ) {
                nameOwnDetailTxt!!.setText(mOwnContactArrayList!![0].name)
            } else {
                nameOwnDetailTxt!!.setText(mOwnContactArrayList!![0].name)
            }
            if (!mOwnContactArrayList!![0].relationship.equals("")) {
                contactTypeOwnDetailTxt!!.setText(mOwnContactArrayList!![0].relationship)
            } else {
                contactTypeOwnDetailTxt!!.setText("")
            }
            if (mOwnContactArrayList!![0].isUpdated) {
                ownDetailViewRelative!!.setBackgroundResource(R.drawable.rect_background_grey)
                confirmBtnMain!!.setVisibility(View.GONE)
            } else {
                ownDetailViewRelative!!.setBackgroundResource(R.drawable.rect_data_collection_red)
                confirmBtnMain!!.setVisibility(View.VISIBLE)
            }
        }
        if (PreferenceManager().getDisplayMessage(mContext).equals("")) {
            messageTxt!!.setText("")
        } else {
            messageTxt!!.setText(PreferenceManager().getDisplayMessage(mContext))
        }
        //    System.out.println("Kin array bshow nsize"+PreferenceManager().getKinDetailsArrayListShow(mContext).size);

        //    System.out.println("Kin array bshow nsize"+PreferenceManager().getKinDetailsArrayListShow(mContext).size);
        Log.e("prefkinfirst",PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.size.toString())
        if (PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext) == null) {
        } else {
            AppController.kinArrayShow=PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!
            //PreferenceManager().setkinArrayShow("kinshow",mContext,PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!)
           /* AppController.kinArrayShow =
                PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!
            CommonClass.kinArrayPass = PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!*/
        }
        Log.e("comm",AppController.kinArrayShow.size.toString())
        if (AppController.kinArrayShow.size > 0) {
            RecyclerLayout!!.setVisibility(View.VISIBLE)
            NoDataLayout!!.setVisibility(View.GONE)
            for (i in 0 until AppController.kinArrayShow.size) {
                //   System.out.println("A_DATA: "+AppController.kinArrayShow.get(i).name);
            }
Log.e("kinad",AppController.kinArrayShow.size.toString())
            var llm=LinearLayoutManager(mContext)
            mRecyclerView!!.layoutManager=llm
            familyKinRecyclerAdapter =
                FamilyKinRecyclerAdapter(mContext, AppController.kinArrayShow)
            mRecyclerView!!.adapter = familyKinRecyclerAdapter
        } else {
            NoDataLayout!!.setVisibility(View.VISIBLE)
            RecyclerLayout!!.setVisibility(View.GONE)
        }
        mRecyclerView!!.addOnItemTouchListener(
            RecyclerItemListener(mContext, mRecyclerView!!,
                object : RecyclerItemListener.RecyclerTouchListener {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    override fun onClickItem(v: View?, position: Int) {
//                        System.out.println("Kin array show Size"+ AppController.kinArrayShow.size);
//                        System.out.println("Kin array pass Size"+ CommonClass.kinArrayPass.size);
//                        System.out.println("Kin array pass Size clicked position"+ position);
//                        System.out.println("Kin array show name"+AppController.kinArrayShow.get(position).name);
                        showKinDetailDialog(position)
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        RecyclerPlusIcon!!.setOnClickListener {
            showKinDetailAdd()
        }
        NoContentPlusIcon!!.setOnClickListener {
            showKinDetailAdd()
        }
        confirmBtnMain!!.setOnClickListener {
            showConfirmButtonDialogue(mContext)
        }
        ownDetailViewRelative!!.setOnClickListener {
            showConfirmButtonDialogue(mContext)
        }


    }
    private fun ShowHelpDialog(
        activity: Context,
        help: String,
        questionmark_icon: Int,
        round: Int
    ) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_help_layout)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(round)
        icon.setImageResource(questionmark_icon)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        textHead.text = help
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }


    private fun ShowDiscardDialog(
        mContext: Context,
        msgHead: String,
        msg: String,
        ico: Int,
        bgIcon: Int,
        contactDialogue: Dialog
    ) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_discard_data)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            CommonClass.isKinEdited = false
            contactDialogue.dismiss()
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById<Button>(R.id.btn_Cancel)
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    fun showConfirmButtonDialogue(activity: Context) {
        GlobalList = ArrayList()
        ContactList = ArrayList()
        getData()
        val mOwnContactArray: ArrayList<OwnContactModel>
        mOwnContactArray = PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.data_collection_confirm)
        val backImg = dialog.findViewById<ImageView>(R.id.backImg)
        changedField = ""
        val FirstName = dialog.findViewById<EditText>(R.id.firstNameTxt)
        val LastName = dialog.findViewById<EditText>(R.id.lastNameTxt)
        val Email = dialog.findViewById<EditText>(R.id.emailTxt)
        val Adr1 = dialog.findViewById<EditText>(R.id.addressLine1)
        val Adr2 = dialog.findViewById<EditText>(R.id.addressLine2)
        val Adr3 = dialog.findViewById<EditText>(R.id.addressLine3)
        val Twn = dialog.findViewById<EditText>(R.id.townTxt)
        val State = dialog.findViewById<EditText>(R.id.stateTxt)
        val Country = dialog.findViewById<TextView>(R.id.countryTxt)
        val passportLinear = dialog.findViewById<LinearLayout>(R.id.passportLinear)
        val PostCode = dialog.findViewById<EditText>(R.id.pinTxt)
        val Phone = dialog.findViewById<EditText>(R.id.dataCollect_Phone)
        val correspondanceLinear = dialog.findViewById<LinearLayout>(R.id.correspondanceLinear)
        val contactLinear = dialog.findViewById<LinearLayout>(R.id.contactLinear)
        FirstName.imeOptions = EditorInfo.IME_ACTION_DONE
        LastName.imeOptions = EditorInfo.IME_ACTION_DONE
        Email.imeOptions = EditorInfo.IME_ACTION_DONE
        Adr1.imeOptions = EditorInfo.IME_ACTION_DONE
        Adr2.imeOptions = EditorInfo.IME_ACTION_DONE
        Adr3.imeOptions = EditorInfo.IME_ACTION_DONE
        Twn.imeOptions = EditorInfo.IME_ACTION_DONE
        State.imeOptions = EditorInfo.IME_ACTION_DONE
        // Country.setImeOptions(EditorInfo.IME_ACTION_DONE);
        PostCode.imeOptions = EditorInfo.IME_ACTION_DONE
        Phone.imeOptions = EditorInfo.IME_ACTION_DONE
        val DropEdt = dialog.findViewById<AutoCompleteTextView>(R.id.DropEdt)
        val CountryCode: CountryCodePicker = dialog.findViewById(R.id.dataCollect_Code)
        val confirmBtn = dialog.findViewById<Button>(R.id.confirmBtn)
        val communicationCheck2Txt = dialog.findViewById<TextView>(R.id.communicationCheck2Txt)
        val communicationCheck1 = dialog.findViewById<ImageView>(R.id.communicationCheck1)
        val communicationCheck2 = dialog.findViewById<ImageView>(R.id.communicationCheck2)
        val communicationPreferenceLinear =
            dialog.findViewById<LinearLayout>(R.id.communicationPreferenceLinear)
        val communicationCheck1Txt = dialog.findViewById<TextView>(R.id.communicationCheck1Txt)
        val RelationalSp = dialog.findViewById<AutoCompleteTextView>(R.id.relationshipSpinner)
        //Manadsajdsj
        // First name mandatory
        val fnameNograyPart = "First name"
        val fnameredPart = "*"
        val fnameNobuilder = SpannableStringBuilder()
        val fnameredColoredString = SpannableString(fnameNograyPart)
        fnameredColoredString.setSpan(ForegroundColorSpan(Color.GRAY), 0, fnameNograyPart.length, 0)
        fnameNobuilder.append(fnameredColoredString)
        val fnameblueColoredString = SpannableString(fnameredPart)
        fnameblueColoredString.setSpan(ForegroundColorSpan(Color.RED), 0, fnameredPart.length, 0)
        fnameNobuilder.append(fnameblueColoredString)
        FirstName.hint = fnameNobuilder
        val emailNograyPart = "E-mail"
        val emailredPart = "*"
        val emailNobuilder = SpannableStringBuilder()
        val emailredColoredString = SpannableString(emailNograyPart)
        emailredColoredString.setSpan(ForegroundColorSpan(Color.GRAY), 0, emailNograyPart.length, 0)
        emailNobuilder.append(emailredColoredString)
        val emailblueColoredString = SpannableString(emailredPart)
        emailblueColoredString.setSpan(ForegroundColorSpan(Color.RED), 0, emailredPart.length, 0)
        emailNobuilder.append(emailblueColoredString)
        Email.hint = emailNobuilder
        LastName.setHint(R.string.AST_LAST_NAME)
        id = mOwnContactArray[0].id.toString()
        userId = mOwnContactArray[0].user_id.toString()
        user_mobile = mOwnContactArray[0].user_mobile.toString()
        studentId = mOwnContactArray[0].student_id.toString()
        created_at = mOwnContactArray[0].created_at.toString()
        updated_at = mOwnContactArray[0].updated_at.toString()
        corresspondanceOwn = mOwnContactArray[0].correspondencemailmerge.toString()
        contactOwn = mOwnContactArray[0].reportmailmerge.toString()
        justContact = mOwnContactArray[0].justcontact.toString()
        val nationalStringArray = java.util.ArrayList<String>()
        for (i in 0 until CommonClass.mNationalityArrayList.size) {
            nationalStringArray.add(CommonClass.mNationalityArrayList.get(i).name)
        }
        spinnerDialog = SpinnerDialog(
            requireActivity(),
            nationalStringArray,
            "Select Country",
            "Close"
        ) // With No Animation
        spinnerDialog =SpinnerDialog(getActivity(),nationalStringArray,"Select Country",
            R.style.DialogAnimations_SmileWindow,"Close") // With 	Animation
        // setDataToAdapter(nationalStringArray);
        spinnerDialog.setCancellable(true) // for cancellable
        spinnerDialog.setShowKeyboard(false) // for open keyboard by default
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                //    Toast.makeText(mContext, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                Country.text = item
                val country = Country.text.toString()
                val changedNameCountry = Country.text.toString()
                println("Name test change$changedNameCountry")
                println("Name test no change$country")
                changedField = if (country.equals(changedNameCountry, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                println("Name test no change value$changedField")
            }
        })
        passportLinear.setOnClickListener { spinnerDialog.showSpinerDialog() }
        if (mOwnContactArray[0].correspondencemailmerge.toString()
                .equals("0") && mOwnContactArray[0].reportmailmerge.toString()
                .equals("0") && mOwnContactArray[0].justcontact.equals("1")
        ) {
            contactLinear.visibility = View.GONE
            correspondanceLinear.visibility = View.VISIBLE
            communicationPreferenceLinear.visibility = View.VISIBLE
            communicationCheck1Txt.text = "Contact does not have access to the BSKL Parent App"
        } else if (mOwnContactArray[0].correspondencemailmerge.toString()
                .equals("0") && mOwnContactArray[0].reportmailmerge.toString()
                .equals("0") && mOwnContactArray[0].justcontact.equals("0")
        ) {
            contactLinear.visibility = View.VISIBLE
            correspondanceLinear.visibility = View.VISIBLE
            communicationPreferenceLinear.visibility = View.VISIBLE
            communicationCheck1Txt.text = "Contact does not receive school correspondence"
            communicationCheck2Txt.text =
                "Contact does not receive school reports and cannot access student details via the BSKL Parent App"
        } else if (mOwnContactArray[0].correspondencemailmerge.toString()
                .equals("1") && mOwnContactArray[0].reportmailmerge.toString()
                .equals("1") && mOwnContactArray[0].justcontact.toString().equals("0")
        ) {
            contactLinear.visibility = View.VISIBLE
            correspondanceLinear.visibility = View.VISIBLE
            communicationPreferenceLinear.visibility = View.VISIBLE
            communicationCheck1Txt.text = "Contact receives school correspondence"
            communicationCheck2Txt.text =
                "Contact receives school reports and has access to student details via the BSKL Parent App"
        } else if (mOwnContactArray[0].correspondencemailmerge.toString()
                .equals("0") && mOwnContactArray[0].reportmailmerge.toString()
                .equals("1") && mOwnContactArray[0].justcontact.toString().equals("0")
        ) {
            contactLinear.visibility = View.VISIBLE
            correspondanceLinear.visibility = View.VISIBLE
            communicationPreferenceLinear.visibility = View.VISIBLE
            communicationCheck1Txt.text = "Contact does not receive school correspondence"
            communicationCheck2Txt.text =
                "Contact receives school reports and has access to student details via the BSKL Parent App"
        } else if (mOwnContactArray[0].correspondencemailmerge.toString()
                .equals("1") && mOwnContactArray[0].reportmailmerge.toString()
                .equals("0") && mOwnContactArray[0].justcontact.toString().equals("0")
        ) {
            contactLinear.visibility = View.VISIBLE
            correspondanceLinear.visibility = View.VISIBLE
            communicationPreferenceLinear.visibility = View.VISIBLE
            communicationCheck1Txt.text = "Contact receives school correspondence"
            communicationCheck2Txt.text =
                "Contact does not receive school reports and cannot access student details via the BSKL Parent App"
        }
        if (mOwnContactArray[0].title.equals("")) {
            DropEdt.setText("")
            PreferenceManager().setIsValueEmpty(mContext, "1")
        } else {
            DropEdt.setText(mOwnContactArray[0].title)
        }
        if (mOwnContactArray[0].relationship.equals("")) {
            RelationalSp.setText("")
            PreferenceManager().setIsValueEmpty(mContext, "1")
        } else {
            RelationalSp.setText(mOwnContactArray[0].relationship)
        }
        if (mOwnContactArray[0].user_mobile.equals("")) {
            Phone.setText("")
            PreferenceManager().setIsValueEmpty(mContext, "1")
        } else {
            Phone.setText(mOwnContactArray[0].user_mobile)
        }
        if (mOwnContactArray[0].name.equals("")) {
            FirstName.setText("")
            PreferenceManager().setIsValueEmpty(mContext, "1")
            PreferenceManager().setWhoValueEmpty(mContext, "Please add one contact as a minimum.")
        } else {
            FirstName.setText(mOwnContactArray[0].name)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        if (mOwnContactArray[0].last_name.equals("")) {
            LastName.setText("")
            PreferenceManager().setIsValueEmpty(mContext, "1")
            PreferenceManager().setWhoValueEmpty(mContext, "Please add one contact as a minimum.")
        } else {
            LastName.setText(mOwnContactArray[0].last_name)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        if (mOwnContactArray[0].email.equals("")) {
            Email.setText("")
            //            PreferenceManager().setIsValueEmpty(mContext,"1");
//           PreferenceManager().setWhoValueEmpty(mContext,"Please add one contact as a minimum.");
        } else {
            Email.setText(mOwnContactArray[0].email)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        if (mOwnContactArray[0].email.equals("")) {
            Email.setText("")
            //            PreferenceManager().setIsValueEmpty(mContext,"1");
//           PreferenceManager().setWhoValueEmpty(mContext,"Please add one contact as a minimum.");
        } else {
            Email.setText(mOwnContactArray[0].email)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        if (mOwnContactArray[0].address1.equals("")) {
            Adr1.setText("")
            //            PreferenceManager().setIsValueEmpty(mContext,"1");
//           PreferenceManager().setWhoValueEmpty(mContext,"Please add one contact as a minimum.");
        } else {
            Adr1.setText(mOwnContactArray[0].address1)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        if (mOwnContactArray[0].address2.equals("")) {
            Adr2.setText("")
            //            PreferenceManager().setIsValueEmpty(mContext,"1");
//           PreferenceManager().setWhoValueEmpty(mContext,"Please add one contact as a minimum.");
        } else {
            Adr2.setText(mOwnContactArray[0].address2)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        if (mOwnContactArray[0].address3.equals("")) {
            Adr3.setText("")
            //            PreferenceManager().setIsValueEmpty(mContext,"1");
//           PreferenceManager().setWhoValueEmpty(mContext,"Please add one contact as a minimum.");
        } else {
            Adr3.setText(mOwnContactArray[0].address3)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        if (mOwnContactArray[0].town.equals("")) {
            Twn.setText("")
            //            PreferenceManager().setIsValueEmpty(mContext,"1");
//           PreferenceManager().setWhoValueEmpty(mContext,"Please add one contact as a minimum.");
        } else {
            Twn.setText(mOwnContactArray[0].town)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        if (mOwnContactArray[0].state.equals("")) {
            State.setText("")
            //            PreferenceManager().setIsValueEmpty(mContext,"1");
//           PreferenceManager().setWhoValueEmpty(mContext,"Please add one contact as a minimum.");
        } else {
            State.setText(mOwnContactArray[0].state)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        if (mOwnContactArray[0].country.equals("")) {
            Country.text = ""
            //            PreferenceManager().setIsValueEmpty(mContext,"1");
//           PreferenceManager().setWhoValueEmpty(mContext,"Please add one contact as a minimum.");
        } else {
            Country.setText(mOwnContactArray[0].country)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        if (mOwnContactArray[0].pincode.equals("")) {
            PostCode.setText("")
            //            PreferenceManager().setIsValueEmpty(mContext,"1");
//           PreferenceManager().setWhoValueEmpty(mContext,"Please add one contact as a minimum.");
        } else {
            PostCode.setText(mOwnContactArray[0].pincode)
            //            PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        dialog.show()

//        GlobalList.add(PreferenceManager().getGlobalListArrayList(mContext));
        GlobalList!!.addAll(PreferenceManager().getGlobalListArrayList(mContext))
        ContactList!!.addAll(PreferenceManager().getContactTypeArrayList(mContext))
        val SpinnerData = java.util.ArrayList<String>()
        for (i in GlobalList!!.indices) {
            for (j in 0 until GlobalList!![i].GlobalList!!.size) {
                System.out.println(
                    "Global: " + GlobalList!![i].GlobalList!!.get(j).name
                )
                SpinnerData.add(GlobalList!![i].GlobalList!!.get(j).name.toString())
                //                System.out.println("Global: TYPE: "+ GlobalList.get(i).getType().equals("Title"));
            }
        }
        val DROP = ArrayAdapter(
            mContext, android.R.layout.simple_list_item_1, SpinnerData
        )
        DropEdt.isCursorVisible = false
        DropEdt.isFocusable = false
        DropEdt.setAdapter(DROP)
        DropEdt.setOnClickListener { DropEdt.showDropDown() }
        val RelationalSpinnerData = java.util.ArrayList<String>()
        for (i in ContactList!!.indices) {
            for (j in 0 until ContactList!![i].mGlobalSirnameArray!!.size) {
                System.out.println(
                    "Contact: " + ContactList!![i].mGlobalSirnameArray!!.get(j).name
                )
                RelationalSpinnerData.add(
                    ContactList!![i].mGlobalSirnameArray!!.get(j).name.toString()
                )
                //                System.out.println("Global: Contact: "+ ContactList.get(i).getType().equals("Title"));
            }
        }
        //        ArrayAdapter relationalArray = new ArrayAdapter(mContext,android.R.layout.simple_expandable_list_item_1,RelationalSpinnerData);
//        RelationalSp.setAdapter(relationalArray);
        val relationalArray = ArrayAdapter(
            requireContext(), android.R.layout.simple_list_item_1, RelationalSpinnerData
        )
        RelationalSp.setAdapter(relationalArray)
        RelationalSp.isFocusable = false
        RelationalSp.setOnClickListener { RelationalSp.showDropDown() }
        val firstName = FirstName.text.toString()
        val lastName = LastName.text.toString()
        val email = Email.text.toString()
        val adr1 = Adr1.text.toString()
        val adr2 = Adr2.text.toString()
        val adr3 = Adr3.text.toString()
        val town = Twn.text.toString()
        val state = State.text.toString()
        val country = Country.text.toString()
        val postCode = PostCode.text.toString()
        FirstName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                val changedName = FirstName.text.toString()
                println("Name test change$changedName")
                println("Name test no change$firstName")
                changedField = if (firstName.equals(changedName, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                println("Name test no change value$changedField")
                //     showAlertButton(mContext, "Alert", "Please enter Email.", R.drawable.exclamationicon, R.drawable.round);
            }
        })
        LastName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                val changedNameLast = LastName.text.toString()
                println("Name test change$changedNameLast")
                println("Name test no change$lastName")
                changedField = if (lastName.equals(changedNameLast, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                println("Name test no change value$changedField")
                //     showAlertButton(mContext, "Alert", "Please enter Email.", R.drawable.exclamationicon, R.drawable.round);
            }
        })
        Email.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                val changedNameEmail = Email.text.toString()
                println("Name test change$changedNameEmail")
                println("Name test no change$email")
                changedField = if (email.equals(changedNameEmail, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                println("Name test no change value$changedField")
                //     showAlertButton(mContext, "Alert", "Please enter Email.", R.drawable.exclamationicon, R.drawable.round);
            }
        })
        Adr1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                val changedNameAdr1 = Adr1.text.toString()
                println("Name test change$changedNameAdr1")
                println("Name test no change$adr1")
                changedField = if (adr1.equals(changedNameAdr1, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                //    System.out.println("Name test no change value"+changedField);
                //     showAlertButton(mContext, "Alert", "Please enter Email.", R.drawable.exclamationicon, R.drawable.round);
            }
        })
        Adr2.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                val changedNameAdr2 = Adr2.text.toString()
                println("Name test change$changedNameAdr2")
                println("Name test no change$adr2")
                changedField = if (adr2.equals(changedNameAdr2, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                println("Name test no change value$changedField")
                //     showAlertButton(mContext, "Alert", "Please enter Email.", R.drawable.exclamationicon, R.drawable.round);
            }
        })
        Adr3.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                val changedNameAdr3 = Adr3.text.toString()
                println("Name test change$changedNameAdr3")
                println("Name test no change$adr3")
                changedField = if (adr3.equals(changedNameAdr3, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                println("Name test no change value$changedField")
                //     showAlertButton(mContext, "Alert", "Please enter Email.", R.drawable.exclamationicon, R.drawable.round);
            }
        })
        Twn.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                val changedNameTown = Twn.text.toString()
                println("Name test change$changedNameTown")
                println("Name test no change$town")
                changedField = if (town.equals(changedNameTown, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                println("Name test no change value$changedField")
                //     showAlertButton(mContext, "Alert", "Please enter Email.", R.drawable.exclamationicon, R.drawable.round);
            }
        })
        State.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                val changedNameState = State.text.toString()
                println("Name test change$changedNameState")
                println("Name test no change$state")
                changedField = if (town.equals(changedNameState, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                println("Name test no change value$changedField")
                //     showAlertButton(mContext, "Alert", "Please enter Email.", R.drawable.exclamationicon, R.drawable.round);
            }
        })
        Country.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                val changedNameCountry = Country.text.toString()
                println("Name test change$changedNameCountry")
                println("Name test no change$country")
                changedField = if (country.equals(changedNameCountry, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                println("Name test no change value$changedField")
                //     showAlertButton(mContext, "Alert", "Please enter Email.", R.drawable.exclamationicon, R.drawable.round);
            }
        })
        PostCode.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                val changedNamePostCode = PostCode.text.toString()
                println("Name test change$changedNamePostCode")
                println("Name test no change$postCode")
                changedField = if (country.equals(changedNamePostCode, ignoreCase = true)) {
                    ""
                } else {
                    "1"
                }
                println("Name test no change value$changedField")
                //     showAlertButton(mContext, "Alert", "Please enter Email.", R.drawable.exclamationicon, R.drawable.round);
            }
        })
        backImg.setOnClickListener {
            println("Name test no change value click$changedField")
            if (changedField.equals("1")) {
                // showDialogAlertTriggerDataCollection(mContext, "Confirm?", "Do you want to Discard your changes?", R.drawable.questionmark_icon, R.drawable.round,dialog);
                PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!.clear()
                val mOwnContactData = java.util.ArrayList<OwnContactModel>()
                val mModel = OwnContactModel()
                mModel.id=id
                mModel.user_id=userId
                mModel.title=DropEdt.text.toString()
                mModel.name=FirstName.text.toString().trim { it <= ' ' }
                mModel.last_name=LastName.text.toString().trim { it <= ' ' }
                mModel.relationship=RelationalSp.text.toString()
                mModel.email=Email.text.toString().trim { it <= ' ' }
                mModel.phone=Phone.text.toString()
                mModel.code=CountryCode.getSelectedCountryCode()
                mModel.user_mobile=user_mobile
                mModel.student_id=studentId
                mModel.address1=Adr1.text.toString().trim { it <= ' ' }
                mModel.address2=Adr2.text.toString().trim { it <= ' ' }
                mModel.address3=Adr3.text.toString().trim { it <= ' ' }
                mModel.town=Twn.text.toString().trim { it <= ' ' }
                mModel.state=State.text.toString().trim { it <= ' ' }
                mModel.country=Country.text.toString().trim { it <= ' ' }
                mModel.pincode=PostCode.text.toString().trim { it <= ' ' }
                mModel.status="1"
                mModel.created_at=created_at
                mModel.updated_at=updated_at
                mModel.reportmailmerge=contactOwn
                mModel.justcontact=justContact
                mModel.correspondencemailmerge=corresspondanceOwn
                mModel.isUpdated=false
                mModel.isConfirmed=false
                mOwnContactData.add(mModel)
                ownDetailViewRelative!!.setBackgroundResource(R.drawable.rect_data_collection_red)
                confirmBtnMain!!.visibility = View.VISIBLE
                PreferenceManager().saveOwnDetailArrayList(mOwnContactData, "OwnContact", mContext)
                if (!PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!.get(0)
                        .name
                        .equals("") && !PreferenceManager().getOwnDetailArrayList(
                        "OwnContact",
                        mContext
                    )!!.get(0).last_name.equals("")
                ) {
                    nameOwnDetailTxt!!.setText(
                        PreferenceManager().getOwnDetailArrayList(
                            "OwnContact",
                            mContext
                        )!!.get(0).name + " " + PreferenceManager().getOwnDetailArrayList(
                            "OwnContact",
                            mContext
                        )!!.get(0).last_name
                    )
                } else if (!PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!.get(0)
                        .name.equals("") && PreferenceManager().getOwnDetailArrayList(
                        "OwnContact",
                        mContext
                    )!!.get(0).last_name.equals("")
                ) {
                    nameOwnDetailTxt!!.setText(
                        PreferenceManager().getOwnDetailArrayList(
                            "OwnContact",
                            mContext
                        )!!.get(0).name
                    )
                } else {
                    nameOwnDetailTxt!!.setText(
                        PreferenceManager().getOwnDetailArrayList(
                            "OwnContact",
                            mContext
                        )!!.get(0).name
                    )
                }
                if (!PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!.get(0)
                        .relationship.equals("")
                ) {
                    contactTypeOwnDetailTxt!!.setText(
                        PreferenceManager().getOwnDetailArrayList(
                            "OwnContact",
                            mContext
                        )!!.get(0).relationship
                    )
                } else {
                    contactTypeOwnDetailTxt!!.text = ""
                }
                dialog.dismiss()
            } else {
                dialog.dismiss()
            }
        }
        confirmBtn.setOnClickListener {
            KinClicked_Size++
            println("Name test no change value click$changedField")
            if (FirstName.text.toString().equals("", ignoreCase = true)) {
                showAlertOKButton(
                    mContext,
                    "Alert",
                    "Please Enter First name",
                    "Ok",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else if (LastName.text.toString().equals("", ignoreCase = true)) {
                showAlertOKButton(
                    mContext,
                    "Alert",
                    "Please Enter Last name",
                    "Ok",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else if (Email.text.toString().equals("", ignoreCase = true)) {
                showAlertOKButton(
                    mContext,
                    "Alert",
                    "Please Enter Email-ID",
                    "Ok",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else if (Phone.text.toString().equals("", ignoreCase = true)) {
                showAlertOKButton(
                    mContext,
                    "Alert",
                    "Please Enter Phone Number",
                    "Ok",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else if (!Email.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex())) {
                val dialog = Dialog(mContext)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(
                    ColorDrawable(
                        Color.TRANSPARENT
                    )
                )
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.alert_dialogue_ok_layout_data_collection)
                val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
                icon.setBackgroundResource(R.drawable.round)
                icon.setImageResource(R.drawable.exclamationicon)
                val text = dialog.findViewById<TextView>(R.id.text_dialog)
                val textHead = dialog.findViewById<TextView>(R.id.alertHead)
                text.text = "Please enter valid email"
                textHead.text = "Alert"
                val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
                dialogButton.text = "Ok"
                dialogButton.setOnClickListener {
                    dialog.dismiss()
                    //pager.setCurrentItem(0);
                }
                dialog.show()
            } else if (Adr1.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                showAlertOKButton(
                    mContext,
                    "Alert",
                    "Please Enter address Line 1",
                    "Ok",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else if (Adr2.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                showAlertOKButton(
                    mContext,
                    "Alert",
                    "Please Enter address Line 2",
                    "Ok",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else if (Twn.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                showAlertOKButton(
                    mContext,
                    "Alert",
                    "Please Enter Town",
                    "Ok",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else if (State.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                showAlertOKButton(
                    mContext,
                    "Alert",
                    "Please Enter State",
                    "Ok",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else if (Country.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                showAlertOKButton(
                    mContext,
                    "Alert",
                    "Please Enter Country.",
                    "Ok",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else if (PostCode.text.toString().trim { it <= ' ' }.equals("")) {
                showAlertOKButton(
                    mContext,
                    "Alert",
                    "Please Enter Post Code",
                    "Ok",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else {
                val emailData: String =
                    PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!.get(0)
                        .email!!.trim()
                var isFound = false
                if (!isFound) {
                    for (i in 0 until AppController.kinArrayShow.size) {
                        val emaildata: String =
                            AppController.kinArrayShow.get(i).email!!.trim()
                        if (emailData.equals(emaildata, ignoreCase = true)) {
                            isFound = true
                        }
                    }
                }
                if (isFound) {
                    showAlertOKButton(
                        mContext,
                        "Alert",
                        "Email ID Already Exists",
                        "Ok",
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                } else {
                    val phoneData = Phone.text.toString().trim { it <= ' ' }
                    var isPhoneFound = false
                    if (!isPhoneFound) {
                        for (i in 0 until AppController.kinArrayShow.size) {
                            val phonedata: String? =
                                AppController.kinArrayShow.get(i).user_mobile
                            if (phoneData.equals(phonedata, ignoreCase = true)) {
                                isPhoneFound = true
                            }
                        }
                    }
                    if (isPhoneFound) {
                        showAlertOKButton(
                            mContext,
                            "Alert",
                            "Phone Number Already Exists",
                            "Ok",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        if (Phone.text.toString().trim { it <= ' ' }.length < 5) {
                            showAlertOKButton(
                                mContext,
                                "Alert",
                                "Please enter valid phone number.",
                                "Ok",
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!.clear()
                            val mOwnContactData = java.util.ArrayList<OwnContactModel>()
                            val mModel = OwnContactModel()
                            mModel.id=id
                            mModel.user_id=userId
                            mModel.title=DropEdt.text.toString()
                            mModel.name=FirstName.text.toString().trim { it <= ' ' }
                            mModel.last_name=LastName.text.toString().trim { it <= ' ' }
                            mModel.relationship=RelationalSp.text.toString()
                            mModel.email=Email.text.toString().trim { it <= ' ' }
                            mModel.phone=Phone.text.toString()
                            mModel.code=CountryCode.getSelectedCountryCode()
                            mModel.user_mobile=user_mobile
                            mModel.student_id=studentId
                            mModel.address1=Adr1.text.toString().trim { it <= ' ' }
                            mModel.address2=Adr2.text.toString().trim { it <= ' ' }
                            mModel.address3=Adr3.text.toString().trim { it <= ' ' }
                            mModel.town=Twn.text.toString().trim { it <= ' ' }
                            mModel.state=State.text.toString().trim { it <= ' ' }
                            mModel.country=Country.text.toString().trim { it <= ' ' }
                            mModel.pincode=PostCode.text.toString().trim { it <= ' ' }
                            mModel.status="1"
                            mModel.created_at= created_at.toString()
                            mModel.updated_at=updated_at
                            mModel.isUpdated=true
                            mModel.isConfirmed=true
                            mModel.reportmailmerge=contactOwn
                            mModel.justcontact=justContact
                            mModel.correspondencemailmerge=corresspondanceOwn
                            mOwnContactData.add(mModel)
                            val gson = Gson()
                            val json = gson.toJson(mModel)
                            OwnDetailArray.add(json)
                            PreferenceManager().saveOwnDetailsJSONArrayList(OwnDetailArray, mContext)
                            ownDetailViewRelative!!.setBackgroundResource(R.drawable.rect_background_grey)
                            confirmBtnMain!!.visibility = View.GONE
                            PreferenceManager().saveOwnDetailArrayList(
                                mOwnContactData,
                                "OwnContact",
                                mContext
                            )
                            if (!PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)
                                    !!.get(0).name
                                    .equals("") && !PreferenceManager().getOwnDetailArrayList(
                                    "OwnContact",
                                    mContext
                                )!!.get(0).last_name.equals("")
                            ) {
                                nameOwnDetailTxt!!.setText(
                                    PreferenceManager().getOwnDetailArrayList(
                                        "OwnContact",
                                        mContext
                                    )!!.get(0)
                                        .name + " " + PreferenceManager().getOwnDetailArrayList(
                                        "OwnContact",
                                        mContext
                                    )!!.get(0).last_name
                                )
                            } else if (!PreferenceManager().getOwnDetailArrayList(
                                    "OwnContact",
                                    mContext
                                )!!.get(0).name
                                    .equals("") && PreferenceManager().getOwnDetailArrayList(
                                    "OwnContact",
                                    mContext
                                )!!.get(0).last_name.equals("")
                            ) {
                                nameOwnDetailTxt!!.setText(
                                    PreferenceManager().getOwnDetailArrayList(
                                        "OwnContact",
                                        mContext
                                    )!!.get(0).name
                                )
                            } else {
                                nameOwnDetailTxt!!.setText(
                                    PreferenceManager().getOwnDetailArrayList(
                                        "OwnContact",
                                        mContext
                                    )!!.get(0).name
                                )
                            }
                            if (!PreferenceManager().getOwnDetailArrayList("OwnContact", mContext)!!.get(0).relationship.equals("")
                            ) {
                                contactTypeOwnDetailTxt!!.setText(
                                    PreferenceManager().getOwnDetailArrayList(
                                        "OwnContact",
                                        mContext
                                    )!!.get(0).relationship
                                    
                                )
                            } else {
                                contactTypeOwnDetailTxt!!.text = ""
                            }
                            dialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun getData() {}
    fun showAlertOKButton(
        activity: Context,
        msg: String?,
        msgHead: String?,
        button: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout_data_collection)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = msgHead
        textHead.text = msg
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.text = button
        dialogButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    fun showDialogAlertTriggerDataCollection(
        context: Context,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int,
        contactDialogue: Dialog
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_discard_data)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            contactDialogue.dismiss()
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById<Button>(R.id.btn_Cancel)
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun showKinDetailAdd() {
        ContactList = ArrayList()
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.activity_contact_details)
        dialog.show()
        val firstName = dialog.findViewById<EditText>(R.id.contactDetails_fname)
        val lastName = dialog.findViewById<EditText>(R.id.ContactDetails_Lastname)
        val emailKin = dialog.findViewById<EditText>(R.id.ContactDetails_Email)
        val contactNumber = dialog.findViewById<EditText>(R.id.ContactDetails_Phone)
        val confirmKinDetail = dialog.findViewById<Button>(R.id.ContactDetails_Submit)
        val communicationPreferenceLinear =
            dialog.findViewById<LinearLayout>(R.id.communicationPreferenceLinear)
        val communicationCheck2Txt = dialog.findViewById<TextView>(R.id.communicationCheck2Txt)
        val communicationCheck1 = dialog.findViewById<ImageView>(R.id.communicationCheck1)
        val communicationCheck2 = dialog.findViewById<ImageView>(R.id.communicationCheck2)
        val ContactSpinner =
            dialog.findViewById<AutoCompleteTextView>(R.id.ContactDetails_Spinnertype)
        val RelationalSpinner = dialog.findViewById<AutoCompleteTextView>(R.id.relationshipSpinner)
        val countryCode: CountryCodePicker = dialog.findViewById(R.id.spinnerCode)
        val Close = dialog.findViewById<ImageView>(R.id.imageView4)
        firstName.setHint(R.string.AST_FIRST_NAME)
        lastName.setHint(R.string.AST_LAST_NAME)
        //   lastName.setHint(R.string.AST_FIRST_NAME);
        emailKin.setHint(R.string.AST_EMAIL)
        RelationalSpinner.setHint(R.string.AST_RELATIONSHIP)
        contactNumber.setHint(R.string.AST_CONTACT_NUMBER)
        val removekinImg = dialog.findViewById<ImageView>(R.id.remove_kin)
        removekinImg.visibility = View.GONE
        communicationPreferenceLinear.visibility = View.GONE
        //        if (PreferenceManager().getCorrespondenceMailMerge(mContext).equals("1"))
//        {
//            communicationCheck1.setImageResource(R.drawable.full_filled_circle);
//        }
//        else
//        {
//            communicationCheck1.setImageResource(R.drawable.full_filled_circle);
//        }
//        if (PreferenceManager().getReportMailMerge(mContext).equals("1"))
//        {
//            communicationCheck2.setImageResource(R.drawable.full_filled_circle);
//            communicationCheck2Txt.setVisibility(View.VISIBLE);
//            communicationCheck2.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            communicationCheck2.setImageResource(R.drawable.full_filled_circle);
//            communicationCheck2Txt.setVisibility(View.GONE);
//            communicationCheck2.setVisibility(View.GONE);
//        }
        GlobalList = ArrayList()
        GlobalList!!.addAll(PreferenceManager().getGlobalListArrayList(mContext))
        val SpinnerData = ArrayList<String>()
        for (i in GlobalList!!.indices) {
            for (j in 0 until GlobalList!![i].GlobalList!!.size) {
                System.out.println(
                    "Global: " + GlobalList!![i].GlobalList!!.get(j).name
                )
                SpinnerData.add(GlobalList!![i].GlobalList!!.get(j).name.toString())
                //                System.out.println("Global: TYPE: "+ GlobalList.get(i).getType().equals("Title"));
            }
        }
        val DROP = ArrayAdapter(
            mContext, android.R.layout.simple_list_item_1, SpinnerData
        )
        ContactSpinner.isCursorVisible = false
        ContactSpinner.isFocusable = false
        ContactSpinner.setAdapter(DROP)
        ContactSpinner.setOnClickListener { ContactSpinner.showDropDown() }
        ContactList!!.addAll(PreferenceManager().getContactTypeArrayList(mContext))
        val RelationalSpinnerData = ArrayList<String>()
        Log.e("contactsize",ContactList!!.size.toString())
        for (i in ContactList!!.indices) {
            for (j in 0 until ContactList!![i].mGlobalSirnameArray!!.size) {
                System.out.println(
                    "Contact: " + ContactList!![i].mGlobalSirnameArray!!.get(j).name
                )
                RelationalSpinnerData.add(
                    ContactList!![i].mGlobalSirnameArray!!.get(j).name.toString()
                )
                //                System.out.println("Global: Contact: "+ ContactList.get(i).getType().equals("Title"));
            }
        }
        Log.e("relsize",RelationalSpinnerData.size.toString())
        var llm=LinearLayoutManager(mContext)
        val relationalArray = ArrayAdapter(
            mContext, android.R.layout.simple_list_item_1, RelationalSpinnerData
        )
        //RelationalSpinner.adapter=relationalArray
        RelationalSpinner.setAdapter(relationalArray)
        RelationalSpinner.isFocusable = false
        RelationalSpinner.setOnClickListener {
            Log.e("relspinn","click")
            RelationalSpinner.showDropDown()
           /* if (!RelationalSpinner.text.toString()
                    .equals("Driver", ignoreCase = true) && !RelationalSpinner.text.toString()
                    .equals("Domestic Helper", ignoreCase = true)
            ) {
                emailKin.setHint(R.string.AST_EMAIL)
            } else {
                emailKin.setHint(R.string.AST_EMAIL_NO)
            }*/
        }
        ContactSpinner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
            }
        })
        firstName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
            }
        })
        lastName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
            }
        })
        emailKin.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
            }
        })
        RelationalSpinner.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
                if (!RelationalSpinner.text.toString()
                        .equals("Driver", ignoreCase = true) && !RelationalSpinner.text.toString()
                        .equals("Domestic Helper", ignoreCase = true)
                ) {
                    emailKin.setHint(R.string.AST_EMAIL)
                } else {
                    emailKin.setHint(R.string.AST_EMAIL_NO)
                }
            }
        })
        contactNumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
            }
        })
        Close.setOnClickListener {
            if (CommonClass.isKinEdited) {
                ShowDiscardDialog(
                    mContext,
                    "Confirm?",
                    "Do you want to Discard changes?",
                    R.drawable.questionmark_icon,
                    R.drawable.round,
                    dialog
                )
            } else {
                dialog.dismiss()
            }
        }
        confirmKinDetail.setOnClickListener {
            var isFound = false
            if (firstName.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                ShowCondition("Please enter the First name")
            } else {
                if (lastName.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    ShowCondition("Please enter the Last name")
                } else {
                    if (!RelationalSpinner.text.toString().trim { it <= ' ' }
                            .equals(
                                "Driver",
                                ignoreCase = true
                            ) && !RelationalSpinner.text.toString().trim { it <= ' ' }
                            .equals("Domestic Helper", ignoreCase = true)) {
                        if (emailKin.text.toString().equals("", ignoreCase = true)) {
                            ShowCondition("Please enter the Email ID")
                        } else {
                            if (!emailKin.text.toString().trim { it <= ' ' }
                                    .matches(emailPattern.toRegex())) {
                                val dialog = Dialog(mContext)
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                dialog.window!!.setBackgroundDrawable(
                                    ColorDrawable(
                                        Color.TRANSPARENT
                                    )
                                )
                                dialog.setCancelable(false)
                                dialog.setContentView(R.layout.alert_dialogue_ok_layout_data_collection)
                                val icon =
                                    dialog.findViewById<ImageView>(R.id.iconImageView)
                                icon.setBackgroundResource(R.drawable.round)
                                icon.setImageResource(R.drawable.exclamationicon)
                                val text =
                                    dialog.findViewById<TextView>(R.id.text_dialog)
                                val textHead =
                                    dialog.findViewById<TextView>(R.id.alertHead)
                                text.text = "Please enter valid email"
                                textHead.text = "Alert"
                                val dialogButton =
                                    dialog.findViewById<Button>(R.id.btn_Ok)
                                dialogButton.text = "Ok"
                                dialogButton.setOnClickListener {
                                    dialog.dismiss()
                                    //pager.setCurrentItem(0);
                                }
                                dialog.show()
                            } else {
                                if (RelationalSpinner.text.toString().trim { it <= ' ' }
                                        .equals("", ignoreCase = true)) {
                                    ShowCondition("Please enter the Relationship")
                                } else {
                                    if (contactNumber.text.toString().trim { it <= ' ' }
                                            .equals("", ignoreCase = true)) {
                                        ShowCondition("Please enter the Contact Number")
                                    } else {
                                        val emailData = emailKin.text.toString().trim { it <= ' ' }
                                        if (!isFound) {
                                            if (PreferenceManager().getOwnDetailArrayList(
                                                    "OwnContact",
                                                    mContext
                                                )!!.get(0).email!!.trim()
                                                    .equals(emailData)
                                            ) {
                                                isFound = true
                                            } else {
                                                for (i in 0 until AppController.kinArrayShow.size) {
                                                    val kinEmail: String =
                                                        AppController.kinArrayShow.get(i)
                                                            .email!!.trim()
                                                    if (kinEmail.equals(
                                                            emailData,
                                                            ignoreCase = true
                                                        )
                                                    ) {
                                                        isFound = true
                                                    }
                                                }
                                            }
                                        }
                                        if (isFound) {
                                            ShowCondition("Email ID Already Exist")
                                            isFound = false
                                        } else {
                                            var isPhoneFound = false
                                            val phoneData = contactNumber.text.toString()
                                            if (!isPhoneFound) {
                                                if (PreferenceManager().getOwnDetailArrayList(
                                                        "OwnContact",
                                                        mContext
                                                    )!!.get(0).user_mobile
                                                        .equals(phoneData)
                                                ) {
                                                    isPhoneFound = true
                                                } else {
                                                    for (i in 0 until AppController.kinArrayShow.size) {
                                                        val kinEmail: String =
                                                            AppController.kinArrayShow.get(i)
                                                                .user_mobile.toString()
                                                        if (kinEmail.equals(
                                                                phoneData,
                                                                ignoreCase = true
                                                            )
                                                        ) {
                                                            isPhoneFound = true
                                                        }
                                                    }
                                                }
                                            }
                                            if (isPhoneFound) {
                                                ShowCondition("Phone Number Already Exist")
                                                isPhoneFound = false
                                            } else {
                                                if (contactNumber.text.toString()
                                                        .trim { it <= ' ' }.length < 5
                                                ) {
                                                    ShowCondition("Please enter valid phone number.")
                                                } else {
                                                    val countryCOdeCheck =
                                                        countryCode.textView_selectedCountry.text.toString()
                                                    if (RelationalSpinner.text.toString().equals(
                                                            "Local Emergency Contact",
                                                            ignoreCase = true
                                                        ) || RelationalSpinner.text.toString()
                                                            .equals(
                                                                "Emergency Contact",
                                                                ignoreCase = true
                                                            )
                                                    ) {
                                                        tempId = tempId + 1
                                                        val TempId =
                                                            "Mobatia_$tempId"
                                                        val model = KinModel()
                                                        model.status="0"
                                                        model.request="1"
                                                        model.name=
                                                            firstName.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.last_name=
                                                            lastName.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.email=
                                                            emailKin.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.title=ContactSpinner.text.toString()
                                                        model.kin_id=TempId
                                                        model.relationship=RelationalSpinner.text.toString()
                                                        model.code=countryCode.textView_selectedCountry.text.toString()
                                                        model.user_mobile=contactNumber.text.toString()
                                                        model.student_id=""
                                                        model.created_at=""
                                                        model.updated_at=""
                                                        model.correspondencemailmerge=""
                                                        model.reportmailmerge=""
                                                        model.justcontact=""
                                                        model.phone=contactNumber.text.toString()
                                                        model.id=TempId
                                                        model.user_id=
                                                            PreferenceManager().getUserId(
                                                                mContext
                                                            )
                                                        
                                                        model.isFullFilled=true
                                                        model.isNewData=true
                                                        model.isConfirmed=true
                                                        CommonClass.kinArrayPass.add(model)
                                                        AppController.kinArrayShow.add(model)
                                                        if (PreferenceManager().getKinDetailsArrayListShow(
                                                                "kinshow",mContext
                                                            ) == null
                                                        ) {
                                                            PreferenceManager().saveKinDetailsArrayList(
                                                                CommonClass.kinArrayPass,
                                                                "kinshow",mContext
                                                            )
                                                            PreferenceManager().saveKinDetailsArrayListShow(
                                                                AppController.kinArrayShow,
                                                                "kinshow", mContext
                                                            )
                                                        } else {
                                                            PreferenceManager().getKinDetailsArrayListShow(
                                                                "kinshow", mContext
                                                            )!!.clear()
                                                            PreferenceManager().getKinDetailsArrayList(
                                                                "kinshow",  mContext
                                                            )!!.clear()
                                                            PreferenceManager().saveKinDetailsArrayList(
                                                                CommonClass.kinArrayPass,
                                                                "kinshow",mContext
                                                            )
                                                            PreferenceManager().saveKinDetailsArrayListShow(
                                                                AppController.kinArrayShow,
                                                                "kinshow", mContext
                                                            )
                                                        }
                                                        System.out.println("Kin array show Size" + AppController.kinArrayShow.size)
                                                        System.out.println(
                                                            "Kin array bshow nsize" + PreferenceManager().getKinDetailsArrayListShow(
                                                                "kinshow", mContext
                                                            )!!.size
                                                        )
                                                        RecyclerLayout!!.visibility =
                                                            View.VISIBLE
                                                        NoDataLayout!!.visibility =
                                                            View.GONE
                                                        var llm=LinearLayoutManager(mContext)
                                                        mRecyclerView!!.layoutManager=llm
                                                        familyKinRecyclerAdapter =
                                                            FamilyKinRecyclerAdapter(
                                                                mContext,
                                                                AppController.kinArrayShow
                                                            )
                                                        mRecyclerView!!.adapter =
                                                            familyKinRecyclerAdapter
                                                        // familyKinRecyclerAdapter.notifyDataSetChanged();
                                                        dialog.dismiss()
                                                        println("It eneters into country code")
                                                    } else {
                                                        println("It eneters into country codedddd")
                                                        tempId = tempId + 1
                                                        val TempId =
                                                            "Mobatia_$tempId"
                                                        val model = KinModel()
                                                        model.status="0"
                                                        model.request="1"
                                                        model.name=
                                                            firstName.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.last_name=
                                                            lastName.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.email=
                                                            emailKin.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.title=ContactSpinner.text.toString()
                                                        model.kin_id=TempId
                                                        model.relationship=RelationalSpinner.text.toString()
                                                        model.code=countryCode.textView_selectedCountry.text.toString()
                                                        model.user_mobile=contactNumber.text.toString()
                                                        model.student_id=""
                                                        model.created_at=""
                                                        model.updated_at=""
                                                        model.phone=contactNumber.text.toString()
                                                        model.id=TempId
                                                        model.user_id=
                                                            PreferenceManager().getUserId(
                                                                mContext
                                                            )
                                                        
                                                        model.isFullFilled=true
                                                        model.isNewData=true
                                                        model.isConfirmed=true
                                                        CommonClass.kinArrayPass.add(model)
                                                        AppController.kinArrayShow.add(model)
                                                        if (PreferenceManager().getKinDetailsArrayListShow(
                                                                "kinshow",mContext
                                                            ) == null
                                                        ) {
                                                            PreferenceManager().saveKinDetailsArrayList(
                                                                CommonClass.kinArrayPass,
                                                                "kinshow", mContext
                                                            )
                                                            PreferenceManager().saveKinDetailsArrayListShow(
                                                                AppController.kinArrayShow,
                                                                "kinshow", mContext
                                                            )
                                                        } else {
                                                            PreferenceManager().getKinDetailsArrayListShow(
                                                                "kinshow", mContext
                                                            )!!.clear()
                                                            PreferenceManager().getKinDetailsArrayList(
                                                                "kinshow", mContext
                                                            )!!.clear()
                                                            PreferenceManager().saveKinDetailsArrayList(
                                                                CommonClass.kinArrayPass,
                                                                "kinshow",mContext
                                                            )
                                                            PreferenceManager().saveKinDetailsArrayListShow(
                                                                AppController.kinArrayShow,
                                                                "kinshow", mContext
                                                            )
                                                            AppController.kinArrayShow=AppController.kinArrayShow
                                                            //PreferenceManager().setkinArrayShow("kinshow",mContext,AppController.kinArrayShow)
                                                            System.out.println(
                                                                "Kin array bshow nsizeinside" + AppController.kinArrayShow.size
                                                            )
                                                        }
                                                        System.out.println("Kin array show Size" + AppController.kinArrayShow.size)
                                                        System.out.println(
                                                            "Kin array bshow nsize" + PreferenceManager().getKinDetailsArrayListShow(
                                                                "kinshow",mContext
                                                            )!!.size
                                                        )
                                                        RecyclerLayout!!.visibility =
                                                            View.VISIBLE
                                                        NoDataLayout!!.visibility =
                                                            View.GONE
                                                        var llm=LinearLayoutManager(mContext)
                                                        mRecyclerView!!.layoutManager=llm
                                                        Log.e("com0",AppController.kinArrayShow.size.toString())
                                                        familyKinRecyclerAdapter =
                                                            FamilyKinRecyclerAdapter(
                                                                mContext,
                                                                AppController.kinArrayShow
                                                            )
                                                        mRecyclerView!!.adapter =
                                                            familyKinRecyclerAdapter
                                                        // familyKinRecyclerAdapter.notifyDataSetChanged();
                                                        dialog.dismiss()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (RelationalSpinner.text.toString().trim { it <= ' ' }
                                .equals("", ignoreCase = true)) {
                            ShowCondition("Please enter the Relationship")
                        } else {
                            if (contactNumber.text.toString().trim { it <= ' ' }
                                    .equals("", ignoreCase = true)) {
                                ShowCondition("Please enter the Contact Number")
                            } else {
                                var isPhoneFound = false
                                val phoneData = contactNumber.text.toString()
                                if (!isPhoneFound) {
                                    if (PreferenceManager().getOwnDetailArrayList(
                                            "OwnContact",
                                            mContext
                                        )!!.get(0).user_mobile.equals(phoneData)
                                    ) {
                                        isPhoneFound = true
                                    } else {
                                        for (i in 0 until AppController.kinArrayShow.size) {
                                            val kinEmail: String =
                                                AppController.kinArrayShow.get(i).user_mobile.toString()
                                            if (kinEmail.equals(phoneData, ignoreCase = true)) {
                                                isPhoneFound = true
                                            }
                                        }
                                    }
                                }
                                if (isPhoneFound) {
                                    ShowCondition("Phone Number Already Exist")
                                    isPhoneFound = false
                                } else {
                                    val countryCOdeCheck =
                                        countryCode.textView_selectedCountry.text.toString()
                                    if (RelationalSpinner.text.toString().equals(
                                            "Local Emergency Contact",
                                            ignoreCase = true
                                        ) || RelationalSpinner.text.toString()
                                            .equals("Emergency Contact", ignoreCase = true)
                                    ) {
                                        tempId = tempId + 1
                                        val TempId = "Mobatia_$tempId"
                                        val model = KinModel()
                                        model.status="0"
                                        model.request="1"
                                        model.name=firstName.text.toString().trim { it <= ' ' }
                                        model.last_name=
                                            lastName.text.toString().trim { it <= ' ' }
                                        model.email=emailKin.text.toString().trim { it <= ' ' }
                                        model.title=ContactSpinner.text.toString()
                                        model.kin_id=TempId
                                        model.relationship=RelationalSpinner.text.toString()
                                        model.code=countryCode.textView_selectedCountry.text.toString()
                                        model.user_mobile=contactNumber.text.toString()
                                        model.student_id=""
                                        model.created_at=""
                                        model.updated_at=""
                                        model.correspondencemailmerge=""
                                        model.reportmailmerge=""
                                        model.justcontact=""
                                        model.phone=contactNumber.text.toString()
                                        model.id=TempId
                                        model.user_id=PreferenceManager().getUserId(mContext)
                                        model.isFullFilled=true
                                        model.isNewData=true
                                        model.isConfirmed=true
                                        CommonClass.kinArrayPass.add(model)
                                        AppController.kinArrayShow.add(model)
                                        if (PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext) == null) {
                                            PreferenceManager().saveKinDetailsArrayList(
                                                CommonClass.kinArrayPass,
                                                "kinshow",mContext
                                            )
                                            PreferenceManager().saveKinDetailsArrayListShow(
                                                AppController.kinArrayShow,
                                                "kinshow", mContext
                                            )
                                        } else {
                                            PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                                            PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                                            PreferenceManager().saveKinDetailsArrayList(
                                                CommonClass.kinArrayPass,
                                                "kinshow",mContext
                                            )
                                            PreferenceManager().saveKinDetailsArrayListShow(
                                                AppController.kinArrayShow,
                                                "kinshow",mContext
                                            )
                                        }
                                        System.out.println("Kin array show Size" + AppController.kinArrayShow.size)
                                        System.out.println(
                                            "Kin array bshow nsize" + PreferenceManager().getKinDetailsArrayListShow(
                                                "kinshow",mContext
                                            )!!.size
                                        )
                                        RecyclerLayout!!.visibility = View.VISIBLE
                                        NoDataLayout!!.visibility = View.GONE
                                        var llm=LinearLayoutManager(mContext)
                                        mRecyclerView!!.layoutManager=llm
                                        familyKinRecyclerAdapter = FamilyKinRecyclerAdapter(
                                            mContext,
                                            AppController.kinArrayShow
                                        )
                                        mRecyclerView!!.adapter = familyKinRecyclerAdapter
                                        // familyKinRecyclerAdapter.notifyDataSetChanged();
                                        dialog.dismiss()
                                        println("It eneters into country code")
                                    } else {
                                        println("It eneters into country codedddd")
                                        tempId = tempId + 1
                                        val TempId = "Mobatia_$tempId"
                                        val model = KinModel()
                                        model.status="0"
                                        model.request="1"
                                        model.name=firstName.text.toString().trim { it <= ' ' }
                                        model.last_name=
                                            lastName.text.toString().trim { it <= ' ' }
                                        model.email=emailKin.text.toString().trim { it <= ' ' }
                                        model.title=ContactSpinner.text.toString()
                                        model.kin_id=TempId
                                        model.relationship=RelationalSpinner.text.toString()
                                        model.code=countryCode.textView_selectedCountry.text.toString()
                                        model.user_mobile=contactNumber.text.toString()
                                        model.student_id=""
                                        model.created_at=""
                                        model.updated_at=""
                                        model.phone=contactNumber.text.toString()
                                        model.id=TempId
                                        model.user_id=PreferenceManager().getUserId(mContext)
                                        model.isFullFilled=true
                                        model.isNewData=true
                                        model.isConfirmed=true
                                        CommonClass.kinArrayPass.add(model)
                                        AppController.kinArrayShow.add(model)
                                        if (PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext) == null) {
                                            PreferenceManager().saveKinDetailsArrayList(
                                                CommonClass.kinArrayPass,
                                                "kinshow",mContext
                                            )
                                            PreferenceManager().saveKinDetailsArrayListShow(
                                                AppController.kinArrayShow,
                                                "kinshow", mContext
                                            )
                                        } else {
                                            PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                                            PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                                            PreferenceManager().saveKinDetailsArrayList(
                                                CommonClass.kinArrayPass,
                                                "kinshow",mContext
                                            )
                                            PreferenceManager().saveKinDetailsArrayListShow(
                                                AppController.kinArrayShow,
                                                "kinshow",mContext
                                            )
                                        }
                                        System.out.println("Kin array show Size" + AppController.kinArrayShow.size)
                                        System.out.println(
                                            "Kin array bshow nsize" + PreferenceManager().getKinDetailsArrayListShow(
                                                "kinshow",mContext
                                            )!!.size
                                        )
                                        RecyclerLayout!!.visibility = View.VISIBLE
                                        NoDataLayout!!.visibility = View.GONE
                                        var llm=LinearLayoutManager(mContext)
                                        mRecyclerView!!.layoutManager=llm
                                        familyKinRecyclerAdapter = FamilyKinRecyclerAdapter(
                                            mContext,
                                            AppController.kinArrayShow
                                        )
                                        mRecyclerView!!.adapter = familyKinRecyclerAdapter
                                        // familyKinRecyclerAdapter.notifyDataSetChanged();
                                        dialog.dismiss()
                                    }
                                }

                                //     }
                            }
                        }
                    }
                }
            }
        }
    }

    fun showKinDetailDialog(position: Int) {
        CommonClass.isKinEdited = false
        ContactList = java.util.ArrayList()
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.activity_contact_details)
        dialog.show()
        val firstName = dialog.findViewById<EditText>(R.id.contactDetails_fname)
        val lastName = dialog.findViewById<EditText>(R.id.ContactDetails_Lastname)
        val emailKin = dialog.findViewById<EditText>(R.id.ContactDetails_Email)
        val contactNumber = dialog.findViewById<EditText>(R.id.ContactDetails_Phone)
        val confirmKinDetail = dialog.findViewById<Button>(R.id.ContactDetails_Submit)
        val removekinImg = dialog.findViewById<ImageView>(R.id.remove_kin)
        val communicationCheck2Txt = dialog.findViewById<TextView>(R.id.communicationCheck2Txt)
        val communicationCheck1Txt = dialog.findViewById<TextView>(R.id.communicationCheck1Txt)
        val communicationCheck1 = dialog.findViewById<ImageView>(R.id.communicationCheck1)
        val communicationCheck2 = dialog.findViewById<ImageView>(R.id.communicationCheck2)
        val ContactSpinner =
            dialog.findViewById<AutoCompleteTextView>(R.id.ContactDetails_Spinnertype)
        val RelationalSpinner = dialog.findViewById<AutoCompleteTextView>(R.id.relationshipSpinner)
        val countryCode: CountryCodePicker = dialog.findViewById(R.id.spinnerCode)
        val Close = dialog.findViewById<ImageView>(R.id.imageView4)
        val communicationPreferenceLinear =
            dialog.findViewById<LinearLayout>(R.id.communicationPreferenceLinear)
        val contactLinear = dialog.findViewById<LinearLayout>(R.id.contactLinear)
        val correspondanceLinear = dialog.findViewById<LinearLayout>(R.id.correspondanceLinear)
        firstName.setHint(R.string.AST_FIRST_NAME)
        lastName.setHint(R.string.AST_LAST_NAME)
        System.out.println(
            "Relationship " + AppController.kinArrayShow.get(position).relationship
        )
        if (!AppController.kinArrayShow.get(position).relationship
                .equals("Driver") && !AppController.kinArrayShow.get(position)
                .relationship.equals("Domestic Helper")
        ) {
            emailKin.setHint(R.string.AST_EMAIL)
        } else {
            emailKin.setHint(R.string.AST_EMAIL_NO)
        }
        RelationalSpinner.setHint(R.string.AST_RELATIONSHIP)
        contactNumber.setHint(R.string.AST_CONTACT_NUMBER)
        if (AppController.kinArrayShow.get(position).isNewData!!) {
            communicationPreferenceLinear.visibility = View.GONE
        } else {
            if (AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                    .equals("0") && AppController.kinArrayShow.get(position)
                    .reportmailmerge.toString().equals("0") && AppController.kinArrayShow.get(
                    position
                ).justcontact.toString().equals("1")
            ) {
                contactLinear.visibility = View.GONE
                correspondanceLinear.visibility = View.VISIBLE
                communicationPreferenceLinear.visibility = View.VISIBLE
                communicationCheck1Txt.text = "Contact does not have access to the BSKL Parent App"
            } else if (AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                    .equals("0") && AppController.kinArrayShow.get(position)
                    .reportmailmerge.toString().equals("0") && AppController.kinArrayShow.get(
                    position
                ).justcontact.toString().equals("0")
            ) {
                contactLinear.visibility = View.VISIBLE
                correspondanceLinear.visibility = View.VISIBLE
                communicationPreferenceLinear.visibility = View.VISIBLE
                communicationCheck1Txt.text = "Contact does not receive school correspondence"
                communicationCheck2Txt.text =
                    "Contact does not receive school reports and cannot access student details via the BSKL Parent App"
            } else if (AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                    .equals("1") && AppController.kinArrayShow.get(position)
                    .reportmailmerge.toString().equals("1") && AppController.kinArrayShow.get(
                    position
                ).justcontact.toString().equals("0")
            ) {
                contactLinear.visibility = View.VISIBLE
                correspondanceLinear.visibility = View.VISIBLE
                communicationPreferenceLinear.visibility = View.VISIBLE
                communicationCheck1Txt.text = "Contact receives school correspondence"
                communicationCheck2Txt.text =
                    "Contact receives school reports and has access to student details via the BSKL Parent App"
            } else if (AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                    .equals("0") && AppController.kinArrayShow.get(position)
                    .reportmailmerge.toString().equals("1") && AppController.kinArrayShow.get(
                    position
                ).justcontact.toString().equals("0")
            ) {
                contactLinear.visibility = View.VISIBLE
                correspondanceLinear.visibility = View.VISIBLE
                communicationPreferenceLinear.visibility = View.VISIBLE
                communicationCheck1Txt.text = "Contact does not receive school correspondence"
                communicationCheck2Txt.text =
                    "Contact receives school reports and has access to student details via the BSKL Parent App"
            } else if (AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                    .equals("1") && AppController.kinArrayShow.get(position)
                    .reportmailmerge.toString().equals("0") && AppController.kinArrayShow.get(
                    position
                ).justcontact.toString().equals("0")
            ) {
                contactLinear.visibility = View.VISIBLE
                correspondanceLinear.visibility = View.VISIBLE
                communicationPreferenceLinear.visibility = View.VISIBLE
                communicationCheck1Txt.text = "Contact receives school correspondence"
                communicationCheck2Txt.text =
                    "Contact does not receive school reports and cannot access student details via the BSKL Parent App"
            }
            //            if (AppController.kinArrayShow.get(position).justcontact.toString().equals("1"))
//            {
//                contactLinear.setVisibility(View.GONE);
//                correspondanceLinear.setVisibility(View.VISIBLE);
//                communicationPreferenceLinear.setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                contactLinear.setVisibility(View.VISIBLE);
//                correspondanceLinear.setVisibility(View.VISIBLE);
//                communicationPreferenceLinear.setVisibility(View.VISIBLE);
//            }

//            if (AppController.kinArrayShow.get(position).correspondencemailmerge.toString().equals("1") || AppController.kinArrayShow.get(position).reportmailmerge.toString().equals("1") )
//            {
//                communicationPreferenceLinear.setVisibility(View.VISIBLE);
//                if (AppController.kinArrayShow.get(position).correspondencemailmerge.toString().equals("1"))
//                {
//                    correspondanceLinear.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    correspondanceLinear.setVisibility(View.GONE);
//                }
//                if (AppController.kinArrayShow.get(position).reportmailmerge.toString().equals("1"))
//                {
//                    contactLinear.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    contactLinear.setVisibility(View.GONE);
//                }
//            }
//            else
//            {
//                communicationPreferenceLinear.setVisibility(View.GONE);
//            }
        }
        //        if (PreferenceManager().getCorrespondenceMailMerge(mContext).equals("1"))
//        {
//            communicationCheck1.setImageResource(R.drawable.full_filled_circle);
//        }
//        else
//        {
//            communicationCheck1.setImageResource(R.drawable.full_filled_circle);
//        }
//        if (PreferenceManager().getReportMailMerge(mContext).equals("1"))
//        {
//            communicationCheck2.setImageResource(R.drawable.full_filled_circle);
//            communicationCheck2Txt.setVisibility(View.VISIBLE);
//            communicationCheck2.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            communicationCheck2.setImageResource(R.drawable.full_filled_circle);
//            communicationCheck2Txt.setVisibility(View.GONE);
//            communicationCheck2.setVisibility(View.GONE);
//        }
        if (!AppController.kinArrayShow.get(position).name.equals("")) {
            firstName.setText(AppController.kinArrayShow.get(position).name)
        }
        if (!AppController.kinArrayShow.get(position).last_name.equals("")) {
            lastName.setText(AppController.kinArrayShow.get(position).last_name)
        }
        if (!AppController.kinArrayShow.get(position).email.equals("")) {
            emailKin.setText(AppController.kinArrayShow.get(position).email)
        }
        if (!AppController.kinArrayShow.get(position).user_mobile.equals("")) {
            contactNumber.setText(AppController.kinArrayShow.get(position).user_mobile)
        }
        if (!AppController.kinArrayShow.get(position).relationship.equals("")) {
            RelationalSpinner.setText(AppController.kinArrayShow.get(position).relationship)
        }
        if (!AppController.kinArrayShow.get(position).title.equals("")) {
            ContactSpinner.setText(AppController.kinArrayShow.get(position).title)
        }
        GlobalList = java.util.ArrayList()
        GlobalList!!.addAll(PreferenceManager().getGlobalListArrayList(mContext))
        val SpinnerData = java.util.ArrayList<String>()
        for (i in GlobalList!!.indices) {
            for (j in 0 until GlobalList!![i].GlobalList!!.size) {
                System.out.println(
                    "Global: " + GlobalList!![i].GlobalList!!.get(j).name
                )
                SpinnerData.add(GlobalList!![i].GlobalList!!.get(j).name.toString())
                //                System.out.println("Global: TYPE: "+ GlobalList.get(i).getType().equals("Title"));
            }
        }
        val DROP = ArrayAdapter(
            mContext, android.R.layout.simple_list_item_1, SpinnerData
        )
        ContactSpinner.isCursorVisible = false
        ContactSpinner.isFocusable = false
        ContactSpinner.setAdapter(DROP)
        ContactSpinner.setOnClickListener { ContactSpinner.showDropDown() }
        ContactList!!.addAll(PreferenceManager().getContactTypeArrayList(mContext))
        val RelationalSpinnerData = java.util.ArrayList<String>()
        for (i in ContactList!!.indices) {
            for (j in 0 until ContactList!![i].mGlobalSirnameArray!!.size) {
                System.out.println(
                    "Contact: " + ContactList!![i].mGlobalSirnameArray!!.get(j).name
                )
                RelationalSpinnerData.add(
                    ContactList!![i].mGlobalSirnameArray!!.get(j).name.toString()
                )
                //                System.out.println("Global: Contact: "+ ContactList.get(i).getType().equals("Title"));
            }
        }
        val relationalArray = ArrayAdapter(
            mContext, android.R.layout.simple_list_item_1, RelationalSpinnerData
        )
        RelationalSpinner.setAdapter(relationalArray)
        RelationalSpinner.isFocusable = false
        RelationalSpinner.setOnClickListener {
            var localCount = 0
            var kinCount = 0
            for (i in 0 until AppController.kinArrayShow.size) {
                if (AppController.kinArrayShow.get(i).relationship
                        .equals("Next of kin")
                ) {
                    kinCount++
                }
                if (AppController.kinArrayShow.get(i).relationship
                        .equals("Local Emergency Contact") || AppController.kinArrayShow.get(
                        i
                    )
                        .relationship.equals("Emergency Contact")
                ) {
                    localCount++
                }
            }
            println("Value for count kin$kinCount")
            println("Value for count local$localCount")
            println("Value for count current data" + RelationalSpinner.text.toString())
            if (RelationalSpinner.text.toString().equals("Next of kin", ignoreCase = true)) {
                if (kinCount > 1) {
                    RelationalSpinner.showDropDown()
                } else {
                    ShowCondition("Relationship cannot be changed, there must be at least one Next of Kin contact associated with your family")
                }
            } else if (RelationalSpinner.text.toString().equals(
                    "Local Emergency Contact",
                    ignoreCase = true
                ) || RelationalSpinner.text.toString()
                    .equals("Emergency Contact", ignoreCase = true)
            ) {
                if (localCount > 1) {
                    RelationalSpinner.showDropDown()
                    if (!RelationalSpinner.text.toString()
                            .equals(
                                "Driver",
                                ignoreCase = true
                            ) && !RelationalSpinner.text.toString()
                            .equals("Domestic Helper", ignoreCase = true)
                    ) {
                        emailKin.setHint(R.string.AST_EMAIL)
                    } else {
                        emailKin.setHint(R.string.AST_EMAIL_NO)
                    }
                } else {
                    ShowCondition("Relationship cannot be changed, there must be at least one Emergency Contact associated with your family")
                }
            } else {
                RelationalSpinner.showDropDown()
            }
        }
        Close.setOnClickListener {
            if (CommonClass.isKinEdited) {
                // ShowDiscardDialog(mContext, "Confirm?", "Do you want to Discard changes?", R.drawable.questionmark_icon, R.drawable.round,dialog);
                var status = ""
                var request = ""
                var kinArrayPassPos = -1
                if (!AppController.kinArrayShow.get(position).isNewData!!) {
                    if (CommonClass.isKinEdited) {
                        status = "1"
                        request = "0"
                    } else {
                        status = "5"
                        request = "0"
                    }
                } else {
                    if (CommonClass.isKinEdited) {
                        status = "0"
                        request = "1"
                    } else {
                        status = "0"
                        request = "1"
                    }
                }
                val kinID: String = AppController.kinArrayShow.get(position).kin_id.toString()
                val IDs: String = AppController.kinArrayShow.get(position).id.toString()
                val studentID: String =
                    AppController.kinArrayShow.get(position).student_id.toString()
                val createdAT: String =
                    AppController.kinArrayShow.get(position).created_at.toString()
                val updatedAT: String =
                    AppController.kinArrayShow.get(position).updated_at.toString()
                val userID: String = AppController.kinArrayShow.get(position).user_id.toString()
                val corresspondence: String =
                    AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                val justcontact: String =
                    AppController.kinArrayShow.get(position).justcontact.toString()
                val contacts: String =
                    AppController.kinArrayShow.get(position).reportmailmerge.toString()
                val model = KinModel()
                model.status=status
                model.request=request
                model.name=firstName.text.toString().trim { it <= ' ' }
                model.last_name=lastName.text.toString().trim { it <= ' ' }
                model.email=emailKin.text.toString().trim { it <= ' ' }
                model.title=ContactSpinner.text.toString()
                model.kin_id=kinID
                model.relationship=RelationalSpinner.text.toString()
                model.code=countryCode.textView_selectedCountry.text.toString()
                model.user_mobile=contactNumber.text.toString()
                model.student_id=studentID
                model.created_at=createdAT
                model.updated_at=updatedAT
                model.phone=contactNumber.text.toString()
                model.id=IDs
                model.user_id=userID
                // String corresspondence=AppController.kinArrayShow.get(position).correspondencemailmerge.toString;
                //String contacts=AppController.kinArrayShow.get(position).reportmailmerge.toString;
                model.correspondencemailmerge=corresspondence
                model.justcontact=justcontact
                model.reportmailmerge=contacts
                model.isFullFilled=false
                if (AppController.kinArrayShow.get(position).isNewData == true) {
                    model.isNewData=true
                } else {
                    model.isNewData=false
                }
                model.isConfirmed=false
                for (j in 0 until CommonClass.kinArrayPass.size) {
                    val dataId: String = CommonClass.kinArrayPass.get(j).id.toString()
                    if (IDs.equals(dataId, ignoreCase = true)) {
                        kinArrayPassPos = j
                    }
                }
                AppController.kinArrayShow.removeAt(position)
                CommonClass.kinArrayPass.removeAt(kinArrayPassPos)
                AppController.kinArrayShow.add(position, model)
                CommonClass.kinArrayPass.add(kinArrayPassPos, model)
                PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                PreferenceManager().saveKinDetailsArrayListShow(AppController.kinArrayShow, "kinshow",mContext)
                PreferenceManager().saveKinDetailsArrayList(CommonClass.kinArrayPass,"kinshow", mContext)
                familyKinRecyclerAdapter!!.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                dialog.dismiss()
            }
        }
        removekinImg.setOnClickListener {
            var localCount = 0
            var kinCount = 0
            for (i in 0 until AppController.kinArrayShow.size) {
                if (AppController.kinArrayShow.get(i).relationship
                        .equals("Next of kin")
                ) {
                    kinCount++
                }
                if (AppController.kinArrayShow.get(i).relationship
                        .equals("Local Emergency Contact") || AppController.kinArrayShow.get(
                        i
                    ).relationship.equals("Emergency Contact")
                ) {
                    localCount++
                }
            }
            println("Value for count kin$kinCount")
            println("Value for count local$localCount")
            println("Value for count current data" + RelationalSpinner.text.toString())
            if (RelationalSpinner.text.toString().equals(
                    "Local Emergency Contact",
                    ignoreCase = true
                ) || RelationalSpinner.text.toString()
                    .equals("Emergency Contact", ignoreCase = true)
            ) {
                if (localCount > 1) {
                    val Deletedialog = Dialog(mContext)
                    Deletedialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    Deletedialog.window!!.setBackgroundDrawable(
                        ColorDrawable(
                            Color.TRANSPARENT
                        )
                    )
                    Deletedialog.setCancelable(false)
                    Deletedialog.setContentView(R.layout.dialogue_discard_data)
                    val icon =
                        Deletedialog.findViewById<ImageView>(R.id.iconImageView)
                    icon.setBackgroundResource(R.drawable.round)
                    icon.setImageResource(R.drawable.questionmark_icon)
                    val text = Deletedialog.findViewById<TextView>(R.id.text_dialog)
                    val textHead =
                        Deletedialog.findViewById<TextView>(R.id.alertHead)
                    text.text = "Are you sure you want to delete this Contact?"
                    textHead.text = "Confirm?"
                    val dialogButton =
                        Deletedialog.findViewById<Button>(R.id.btn_Ok)
                    dialogButton.text = "Delete"
                    dialogButton.setOnClickListener {
                        if (AppController.kinArrayShow.get(position).isNewData==true) {
                            var deletePos = -1
                            val newID: String =
                                AppController.kinArrayShow.get(position).id.toString()
                            AppController.kinArrayShow.removeAt(position)
                            for (i in 0 until CommonClass.kinArrayPass.size) {
                                val passArrayId: String =
                                    CommonClass.kinArrayPass.get(i).id.toString()
                                if (newID.equals(passArrayId, ignoreCase = true)) {
                                    deletePos = i
                                }
                            }
                            CommonClass.kinArrayPass.removeAt(deletePos)
                            PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                            PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                            PreferenceManager().saveKinDetailsArrayList(
                                CommonClass.kinArrayPass,
                                "kinshow",mContext
                            )
                            PreferenceManager().saveKinDetailsArrayListShow(
                                AppController.kinArrayShow,
                                "kinshow",mContext
                            )
                            familyKinRecyclerAdapter!!.notifyDataSetChanged()
                            dialog.dismiss()
                            Deletedialog.dismiss()
                        } else {
                            val newID: String =
                                AppController.kinArrayShow.get(position).id.toString()
                            var deletePos = -1
                            for (i in 0 until CommonClass.kinArrayPass.size) {
                                val passArrayId: String =
                                    CommonClass.kinArrayPass.get(i).id.toString()
                                if (newID.equals(passArrayId, ignoreCase = true)) {
                                    deletePos = i
                                }
                            }
                            val model = KinModel()
                            model.status="2"
                            model.request="0"
                            model.name=CommonClass.kinArrayPass.get(deletePos).name
                            model.last_name=
                                CommonClass.kinArrayPass.get(deletePos).last_name
                            
                            model.email=CommonClass.kinArrayPass.get(deletePos).email
                            model.title=CommonClass.kinArrayPass.get(deletePos).title
                            model.kin_id=CommonClass.kinArrayPass.get(deletePos).id
                            model.relationship=
                                CommonClass.kinArrayPass.get(deletePos).relationship

                            model.code=CommonClass.kinArrayPass.get(deletePos).code
                            model.user_mobile=
                                CommonClass.kinArrayPass.get(deletePos).user_mobile

                            model.student_id=
                                CommonClass.kinArrayPass.get(deletePos).student_id.toString()

                            model.created_at=
                                CommonClass.kinArrayPass.get(deletePos).created_at.toString()

                            model.updated_at=
                                CommonClass.kinArrayPass.get(deletePos).updated_at

                            model.phone=CommonClass.kinArrayPass.get(deletePos).phone
                            model.id=CommonClass.kinArrayPass.get(deletePos).id
                            model.user_id=
                                CommonClass.kinArrayPass.get(deletePos).user_id.toString()

                            model.isFullFilled=
                                CommonClass.kinArrayPass.get(deletePos).isFullFilled

                            model.isNewData=CommonClass.kinArrayPass.get(deletePos).isNewData
                            model.isConfirmed=true
                            AppController.kinArrayShow.removeAt(position)
                            CommonClass.kinArrayPass.removeAt(deletePos)
                            CommonClass.kinArrayPass.add(deletePos, model)
                            PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                            PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                            PreferenceManager().saveKinDetailsArrayList(
                                CommonClass.kinArrayPass,
                                "kinshow", mContext
                            )
                            PreferenceManager().saveKinDetailsArrayListShow(
                                AppController.kinArrayShow,
                                "kinshow", mContext
                            )
                            familyKinRecyclerAdapter!!.notifyDataSetChanged()
                            dialog.dismiss()
                            Deletedialog.dismiss()
                        }
                    }
                    val dialogButtonCancel =
                        Deletedialog.findViewById<Button>(R.id.btn_Cancel)
                    dialogButtonCancel.setOnClickListener { Deletedialog.dismiss() }
                    Deletedialog.show()
                } else {
                    ShowCondition("Contact cannot be deleted, there must be at least one Emergency Contact associated with your family")
                }
            } else {
                val Deletedialog = Dialog(mContext)
                Deletedialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                Deletedialog.window!!.setBackgroundDrawable(
                    ColorDrawable(
                        Color.TRANSPARENT
                    )
                )
                Deletedialog.setCancelable(false)
                Deletedialog.setContentView(R.layout.dialogue_discard_data)
                val icon = Deletedialog.findViewById<ImageView>(R.id.iconImageView)
                icon.setBackgroundResource(R.drawable.round)
                icon.setImageResource(R.drawable.questionmark_icon)
                val text = Deletedialog.findViewById<TextView>(R.id.text_dialog)
                val textHead = Deletedialog.findViewById<TextView>(R.id.alertHead)
                text.text = "Are you sure you want to delete this Contact?"
                textHead.text = "Confirm?"
                val dialogButton = Deletedialog.findViewById<Button>(R.id.btn_Ok)
                dialogButton.text = "Delete"
                dialogButton.setOnClickListener {
                    if (AppController.kinArrayShow.get(position).isNewData == true) {
                        var deletePos = -1
                        val newID: String = AppController.kinArrayShow.get(position).id.toString()
                        AppController.kinArrayShow.removeAt(position)
                        for (i in 0 until CommonClass.kinArrayPass.size) {
                            val passArrayId: String =
                                CommonClass.kinArrayPass.get(i).id.toString()
                            if (newID.equals(passArrayId, ignoreCase = true)) {
                                deletePos = i
                            }
                        }
                        CommonClass.kinArrayPass.removeAt(deletePos)
                        PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                        PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                        PreferenceManager().saveKinDetailsArrayList(
                            CommonClass.kinArrayPass,
                            "kinshow", mContext
                        )
                        PreferenceManager().saveKinDetailsArrayListShow(
                            AppController.kinArrayShow,
                            "kinshow", mContext
                        )
                        familyKinRecyclerAdapter!!.notifyDataSetChanged()
                        dialog.dismiss()
                        Deletedialog.dismiss()
                    } else {
                        val newID: String = AppController.kinArrayShow.get(position).id.toString()
                        var deletePos = -1
                        for (i in 0 until CommonClass.kinArrayPass.size) {
                            val passArrayId: String =
                                CommonClass.kinArrayPass.get(i).id.toString()
                            if (newID.equals(passArrayId, ignoreCase = true)) {
                                deletePos = i
                            }
                        }
                        val model = KinModel()
                        model.status="2"
                        model.request="0"
                        model.name=CommonClass.kinArrayPass.get(deletePos).name
                        model.last_name=
                            CommonClass.kinArrayPass.get(deletePos).last_name

                        model.email=CommonClass.kinArrayPass.get(deletePos).email
                        model.title=CommonClass.kinArrayPass.get(deletePos).title
                        model.kin_id=CommonClass.kinArrayPass.get(deletePos).id
                        model.relationship=
                            CommonClass.kinArrayPass.get(deletePos).relationship

                        model.code=CommonClass.kinArrayPass.get(deletePos).code
                        model.user_mobile=
                            CommonClass.kinArrayPass.get(deletePos).user_mobile

                        model.student_id=
                            CommonClass.kinArrayPass.get(deletePos).student_id.toString()

                        model.created_at=
                            CommonClass.kinArrayPass.get(deletePos).created_at.toString()

                        model.updated_at=
                            CommonClass.kinArrayPass.get(deletePos).updated_at

                        model.phone=CommonClass.kinArrayPass.get(deletePos).phone
                        model.id=CommonClass.kinArrayPass.get(deletePos).id
                        model.user_id=CommonClass.kinArrayPass.get(deletePos).user_id.toString()
                        model.isFullFilled=
                            CommonClass.kinArrayPass.get(deletePos).isFullFilled

                        model.isNewData=CommonClass.kinArrayPass.get(deletePos).isNewData
                        model.isConfirmed=true
                        AppController.kinArrayShow.removeAt(position)
                        CommonClass.kinArrayPass.removeAt(deletePos)
                        CommonClass.kinArrayPass.add(deletePos, model)
                        PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                        PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                        PreferenceManager().saveKinDetailsArrayList(
                            CommonClass.kinArrayPass,
                            "kinshow",mContext
                        )
                        PreferenceManager().saveKinDetailsArrayListShow(
                            AppController.kinArrayShow,
                            "kinshow", mContext
                        )
                        familyKinRecyclerAdapter!!.notifyDataSetChanged()
                        dialog.dismiss()
                        Deletedialog.dismiss()
                    }
                }
                val dialogButtonCancel =
                    Deletedialog.findViewById<Button>(R.id.btn_Cancel)
                dialogButtonCancel.setOnClickListener { Deletedialog.dismiss() }
                Deletedialog.show()
            }
        }
        confirmKinDetail.setOnClickListener {
            var isFound = false
            if (firstName.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                ShowCondition("Please enter the First name")
            } else {
                if (lastName.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    ShowCondition("Please enter the Last name")
                } else {
                    println("It enters into the condition" + RelationalSpinner.text.toString())
                    if (!RelationalSpinner.text.toString().trim { it <= ' ' }
                            .equals(
                                "Driver",
                                ignoreCase = true
                            ) && !RelationalSpinner.text.toString().trim { it <= ' ' }
                            .equals("Domestic Helper", ignoreCase = true)) {
                        if (emailKin.text.toString().equals("", ignoreCase = true)) {
                            println("It enters into the condition not driver not helper")
                            ShowCondition("Please enter the Email")
                        } else {
                            if (!emailKin.text.toString().trim { it <= ' ' }
                                    .matches(emailPattern.toRegex())) {
                                val dialog = Dialog(mContext)
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                dialog.window!!.setBackgroundDrawable(
                                    ColorDrawable(
                                        Color.TRANSPARENT
                                    )
                                )
                                dialog.setCancelable(false)
                                dialog.setContentView(R.layout.alert_dialogue_ok_layout_data_collection)
                                val icon =
                                    dialog.findViewById<ImageView>(R.id.iconImageView)
                                icon.setBackgroundResource(R.drawable.round)
                                icon.setImageResource(R.drawable.exclamationicon)
                                val text =
                                    dialog.findViewById<TextView>(R.id.text_dialog)
                                val textHead =
                                    dialog.findViewById<TextView>(R.id.alertHead)
                                text.text = "Please enter valid email"
                                textHead.text = "Alert"
                                val dialogButton =
                                    dialog.findViewById<Button>(R.id.btn_Ok)
                                dialogButton.text = "Ok"
                                dialogButton.setOnClickListener {
                                    dialog.dismiss()
                                    //pager.setCurrentItem(0);
                                }
                                dialog.show()
                            } else {
                                if (RelationalSpinner.text.toString().trim { it <= ' ' }
                                        .equals("", ignoreCase = true)) {
                                    ShowCondition("Please enter the Relationship")
                                } else {
                                    if (contactNumber.text.toString().trim { it <= ' ' }
                                            .equals("", ignoreCase = true)) {
                                        ShowCondition("Please enter the Contact Number")
                                    } else {
                                        val emailData = emailKin.text.toString().trim { it <= ' ' }
                                        if (!isFound) {
                                            if (PreferenceManager().getOwnDetailArrayList(
                                                    "OwnContact",
                                                    mContext
                                                )!!.get(0).email!!.trim()
                                                    .equals(emailData)
                                            ) {
                                                isFound = true
                                            } else {
                                                for (i in 0 until AppController.kinArrayShow.size) {
                                                    val kinEmail: String =
                                                        AppController.kinArrayShow.get(i)
                                                            .email!!.trim()
                                                    if (kinEmail.equals(
                                                            emailData,
                                                            ignoreCase = true
                                                        )
                                                    ) {
                                                        if (position == i) {
                                                        } else {
                                                            isFound = true
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (isFound) {
                                            ShowCondition("Email ID Already Exist")
                                            isFound = false
                                        } else {
                                            var isPhoneFound = false
                                            val phoneData = contactNumber.text.toString()
                                            if (!isPhoneFound) {
                                                if (PreferenceManager().getOwnDetailArrayList(
                                                        "OwnContact",
                                                        mContext
                                                    )!!.get(0).user_mobile
                                                        .equals(phoneData)
                                                ) {
                                                    isPhoneFound = true
                                                } else {
                                                    for (i in 0 until AppController.kinArrayShow.size) {
                                                        val kinEmail: String =
                                                            AppController.kinArrayShow.get(i)
                                                                .user_mobile.toString()
                                                        if (kinEmail.equals(
                                                                phoneData,
                                                                ignoreCase = true
                                                            )
                                                        ) {
                                                            if (position == i) {
                                                            } else {
                                                                isPhoneFound = true
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (isPhoneFound) {
                                                ShowCondition("Phone Number Already Exist")
                                                isPhoneFound = false
                                            } else {
                                                if (contactNumber.text.toString()
                                                        .trim { it <= ' ' }.length < 5
                                                ) {
                                                    ShowCondition("Please enter valid phone number.")
                                                } else {
                                                    if (RelationalSpinner.text.toString().equals(
                                                            "Local Emergency Contact",
                                                            ignoreCase = true
                                                        ) || RelationalSpinner.text.toString()
                                                            .equals(
                                                                "Emergency Contact",
                                                                ignoreCase = true
                                                            )
                                                    ) {
                                                        println("It eneters into country codeffffff")
                                                        var status = ""
                                                        var request = ""
                                                        var kinArrayPassPos = -1
                                                        if (!AppController.kinArrayShow.get(
                                                                position
                                                            ).isNewData!!
                                                        ) {
                                                            if (CommonClass.isKinEdited) {
                                                                status = "1"
                                                                request = "0"
                                                            } else {
                                                                status = "5"
                                                                request = "0"
                                                            }
                                                        } else {
                                                            if (CommonClass.isKinEdited) {
                                                                status = "0"
                                                                request = "1"
                                                            } else {
                                                                status = "0"
                                                                request = "1"
                                                            }
                                                        }
                                                        val kinID: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .kin_id.toString()
                                                        val IDs: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .id.toString()
                                                        val studentID: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .student_id.toString()
                                                        val createdAT: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .created_at.toString()
                                                        val updatedAT: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .updated_at.toString()
                                                        val userID: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .user_id.toString()
                                                        val corresspondence: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .correspondencemailmerge.toString()
                                                        val justcontact: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .justcontact.toString()
                                                        val contacts: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .reportmailmerge.toString()
                                                        val model = KinModel()
                                                        model.status=status
                                                        model.request=request
                                                        model.name=
                                                            firstName.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.last_name=
                                                            lastName.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.email=
                                                            emailKin.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.title=ContactSpinner.text.toString()
                                                        model.kin_id=kinID
                                                        model.relationship=RelationalSpinner.text.toString()
                                                        model.code=countryCode.textView_selectedCountry.text.toString()
                                                        model.user_mobile=contactNumber.text.toString()
                                                        model.student_id=studentID
                                                        model.created_at=createdAT
                                                        model.updated_at=updatedAT
                                                        model.phone=contactNumber.text.toString()
                                                        model.id=IDs
                                                        model.user_id=userID
                                                        model.isFullFilled=true
                                                        model.correspondencemailmerge=
                                                            corresspondence

                                                        model.justcontact=justcontact
                                                        model.reportmailmerge=contacts
                                                        if (AppController.kinArrayShow.get(position)
                                                                .isNewData == true
                                                        ) {
                                                            model.isNewData=true
                                                        } else {
                                                            model.isNewData=false
                                                        }
                                                        model.isConfirmed=true
                                                        for (j in 0 until CommonClass.kinArrayPass.size) {
                                                            val dataId: String =
                                                                CommonClass.kinArrayPass.get(j)
                                                                    .id.toString()
                                                            if (IDs.equals(
                                                                    dataId,
                                                                    ignoreCase = true
                                                                )
                                                            ) {
                                                                kinArrayPassPos = j
                                                            }
                                                        }
                                                        AppController.kinArrayShow.removeAt(position)
                                                        CommonClass.kinArrayPass.removeAt(
                                                            kinArrayPassPos
                                                        )
                                                        AppController.kinArrayShow.add(
                                                            position,
                                                            model
                                                        )
                                                        CommonClass.kinArrayPass.add(
                                                            kinArrayPassPos,
                                                            model
                                                        )
                                                        PreferenceManager().getKinDetailsArrayList(
                                                            "kinshow",mContext
                                                        )!!.clear()
                                                        PreferenceManager().getKinDetailsArrayListShow(
                                                            "kinshow",mContext
                                                        )!!.clear()
                                                        PreferenceManager().saveKinDetailsArrayListShow(
                                                            AppController.kinArrayShow,
                                                            "kinshow", mContext
                                                        )
                                                        PreferenceManager().saveKinDetailsArrayList(
                                                            CommonClass.kinArrayPass,
                                                            "kinshow",mContext
                                                        )
                                                        dialog.dismiss()
                                                        familyKinRecyclerAdapter!!.notifyDataSetChanged()
                                                        System.out.println("It eneters into country codeffffffdddd" + countryCode.textView_selectedCountry.text.toString())

                                                        //                                                }
                                                    } else {
                                                        println("It eneters into country codevvvvvvv")
                                                        var status = ""
                                                        var request = ""
                                                        var kinArrayPassPos = -1
                                                        if (!AppController.kinArrayShow.get(
                                                                position
                                                            ).isNewData!!
                                                        ) {
                                                            if (CommonClass.isKinEdited) {
                                                                status = "1"
                                                                request = "0"
                                                            } else {
                                                                status = "5"
                                                                request = "0"
                                                            }
                                                        } else {
                                                            if (CommonClass.isKinEdited) {
                                                                status = "0"
                                                                request = "1"
                                                            } else {
                                                                status = "0"
                                                                request = "1"
                                                            }
                                                        }
                                                        val kinID: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .kin_id.toString()
                                                        val IDs: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .id.toString()
                                                        val studentID: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .student_id.toString()
                                                        val createdAT: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .created_at.toString()
                                                        val updatedAT: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .updated_at.toString()
                                                        val userID: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .user_id.toString()
                                                        val corresspondence: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .correspondencemailmerge.toString()
                                                        val justcontact: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .justcontact.toString()
                                                        val contacts: String =
                                                            AppController.kinArrayShow.get(position)
                                                                .reportmailmerge.toString()
                                                        val model = KinModel()
                                                        model.status=status
                                                        model.request=request
                                                        model.name=
                                                            firstName.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.last_name=
                                                            lastName.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.email=
                                                            emailKin.text.toString()
                                                                .trim { it <= ' ' }
                                                        model.title=ContactSpinner.text.toString()
                                                        model.kin_id=kinID
                                                        model.relationship=RelationalSpinner.text.toString()
                                                        model.code=countryCode.textView_selectedCountry.text.toString()
                                                        model.user_mobile=contactNumber.text.toString()
                                                        model.student_id=studentID
                                                        model.created_at=createdAT
                                                        model.updated_at=updatedAT
                                                        model.phone=contactNumber.text.toString()
                                                        model.id=IDs
                                                        model.user_id=userID
                                                        // String corresspondence=AppController.kinArrayShow.get(position).correspondencemailmerge.toString;
                                                        //String contacts=AppController.kinArrayShow.get(position).reportmailmerge.toString;
                                                        model.correspondencemailmerge=
                                                            corresspondence

                                                        model.justcontact=justcontact
                                                        model.reportmailmerge=contacts
                                                        model.isFullFilled=true
                                                        if (AppController.kinArrayShow.get(position)
                                                                .isNewData == true
                                                        ) {
                                                            model.isNewData=true
                                                        } else {
                                                            model.isNewData=false
                                                        }
                                                        model.isConfirmed=true
                                                        for (j in 0 until CommonClass.kinArrayPass.size) {
                                                            val dataId: String =
                                                                CommonClass.kinArrayPass.get(j)
                                                                    .id.toString()
                                                            if (IDs.equals(
                                                                    dataId,
                                                                    ignoreCase = true
                                                                )
                                                            ) {
                                                                kinArrayPassPos = j
                                                            }
                                                        }
                                                        AppController.kinArrayShow.removeAt(position)
                                                        CommonClass.kinArrayPass.removeAt(
                                                            kinArrayPassPos
                                                        )
                                                        AppController.kinArrayShow.add(
                                                            position,
                                                            model
                                                        )
                                                        CommonClass.kinArrayPass.add(
                                                            kinArrayPassPos,
                                                            model
                                                        )
                                                        PreferenceManager().getKinDetailsArrayList(
                                                            "kinshow", mContext
                                                        )!!.clear()
                                                        PreferenceManager().getKinDetailsArrayListShow(
                                                            "kinshow",mContext
                                                        )!!.clear()
                                                        PreferenceManager().saveKinDetailsArrayListShow(
                                                            AppController.kinArrayShow,
                                                            "kinshow", mContext
                                                        )
                                                        PreferenceManager().saveKinDetailsArrayList(
                                                            CommonClass.kinArrayPass,
                                                            "kinshow", mContext
                                                        )
                                                        dialog.dismiss()
                                                        familyKinRecyclerAdapter!!.notifyDataSetChanged()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (RelationalSpinner.text.toString().trim { it <= ' ' }
                                .equals("", ignoreCase = true)) {
                            ShowCondition("Please enter the Relationship")
                        } else {
                            if (contactNumber.text.toString().trim { it <= ' ' }
                                    .equals("", ignoreCase = true)) {
                                ShowCondition("Please enter the Contact Number")
                            } else {
                                var isPhoneFound = false
                                val phoneData = contactNumber.text.toString()
                                if (!isPhoneFound) {
                                    if (PreferenceManager().getOwnDetailArrayList(
                                            "OwnContact",
                                            mContext
                                        )!!.get(0).user_mobile.equals(phoneData)
                                    ) {
                                        isPhoneFound = true
                                    } else {
                                        for (i in 0 until AppController.kinArrayShow.size) {
                                            val kinEmail: String =
                                                AppController.kinArrayShow.get(i).user_mobile.toString()
                                            if (kinEmail.equals(phoneData, ignoreCase = true)) {
                                                if (position == i) {
                                                } else {
                                                    isPhoneFound = true
                                                }
                                            }
                                        }
                                    }
                                }
                                if (isPhoneFound) {
                                    ShowCondition("Phone Number Already Exist")
                                    isPhoneFound = false
                                } else {
                                    if (contactNumber.text.toString()
                                            .trim { it <= ' ' }.length < 5
                                    ) {
                                        ShowCondition(" Please enter valid phone number.")
                                    } else {
                                        if (RelationalSpinner.text.toString().equals(
                                                "Local Emergency Contact",
                                                ignoreCase = true
                                            ) || RelationalSpinner.text.toString()
                                                .equals("Emergency Contact", ignoreCase = true)
                                        ) {
                                            println("It eneters into country codeffffff")
                                            var status = ""
                                            var request = ""
                                            var kinArrayPassPos = -1
                                            if (!AppController.kinArrayShow.get(position)
                                                    .isNewData!!
                                            ) {
                                                if (CommonClass.isKinEdited) {
                                                    status = "1"
                                                    request = "0"
                                                } else {
                                                    status = "5"
                                                    request = "0"
                                                }
                                            } else {
                                                if (CommonClass.isKinEdited) {
                                                    status = "0"
                                                    request = "1"
                                                } else {
                                                    status = "0"
                                                    request = "1"
                                                }
                                            }
                                            val kinID: String =
                                                AppController.kinArrayShow.get(position)
                                                    .kin_id.toString()
                                            val IDs: String =
                                                AppController.kinArrayShow.get(position).id.toString()
                                            val studentID: String =
                                                AppController.kinArrayShow.get(position)
                                                    .student_id.toString()
                                            val createdAT: String =
                                                AppController.kinArrayShow.get(position)
                                                    .created_at.toString()
                                            val updatedAT: String =
                                                AppController.kinArrayShow.get(position)
                                                    .updated_at.toString()
                                            val userID: String =
                                                AppController.kinArrayShow.get(position)
                                                    .user_id.toString()
                                            val corresspondence: String =
                                                AppController.kinArrayShow.get(position)
                                                    .correspondencemailmerge.toString()
                                            val justcontact: String =
                                                AppController.kinArrayShow.get(position)
                                                    .justcontact.toString()
                                            val contacts: String =
                                                AppController.kinArrayShow.get(position)
                                                    .reportmailmerge.toString()
                                            val model = KinModel()
                                            model.status=status
                                            model.request=request
                                            model.name=
                                                firstName.text.toString().trim { it <= ' ' }
                                            model.last_name=
                                                lastName.text.toString().trim { it <= ' ' }
                                            model.email=
                                                emailKin.text.toString().trim { it <= ' ' }
                                            model.title=ContactSpinner.text.toString()
                                            model.kin_id=kinID
                                            model.relationship=RelationalSpinner.text.toString()
                                            model.code=countryCode.textView_selectedCountry.text.toString()
                                            model.user_mobile=contactNumber.text.toString()
                                            model.student_id=studentID
                                            model.created_at=createdAT
                                            model.updated_at=updatedAT
                                            model.phone=contactNumber.text.toString()
                                            model.id=IDs
                                            model.user_id=userID
                                            model.isFullFilled=true
                                            model.correspondencemailmerge=corresspondence
                                            model.justcontact=justcontact
                                            model.reportmailmerge=contacts
                                            if (AppController.kinArrayShow.get(position)
                                                    .isNewData == true
                                            ) {
                                                model.isNewData=true
                                            } else {
                                                model.isNewData=false
                                            }
                                            model.isConfirmed=true
                                            for (j in 0 until CommonClass.kinArrayPass.size) {
                                                val dataId: String =
                                                    CommonClass.kinArrayPass.get(j).id.toString()
                                                if (IDs.equals(dataId, ignoreCase = true)) {
                                                    kinArrayPassPos = j
                                                }
                                            }
                                            AppController.kinArrayShow.removeAt(position)
                                            CommonClass.kinArrayPass.removeAt(kinArrayPassPos)
                                            AppController.kinArrayShow.add(position, model)
                                            CommonClass.kinArrayPass.add(kinArrayPassPos, model)
                                            PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                                            PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                                            PreferenceManager().saveKinDetailsArrayListShow(
                                                AppController.kinArrayShow,
                                                "kinshow",mContext
                                            )
                                            PreferenceManager().saveKinDetailsArrayList(
                                                CommonClass.kinArrayPass,
                                                "kinshow", mContext
                                            )
                                            dialog.dismiss()
                                            familyKinRecyclerAdapter!!.notifyDataSetChanged()
                                            System.out.println("It eneters into country codeffffffdddd" + countryCode.textView_selectedCountry.text.toString())
                                        } else {
                                            println("It eneters into country codevvvvvvv")
                                            var status = ""
                                            var request = ""
                                            var kinArrayPassPos = -1
                                            if (!AppController.kinArrayShow.get(position)
                                                    .isNewData!!
                                            ) {
                                                if (CommonClass.isKinEdited) {
                                                    status = "1"
                                                    request = "0"
                                                } else {
                                                    status = "5"
                                                    request = "0"
                                                }
                                            } else {
                                                if (CommonClass.isKinEdited) {
                                                    status = "0"
                                                    request = "1"
                                                } else {
                                                    status = "0"
                                                    request = "1"
                                                }
                                            }
                                            val kinID: String =
                                                AppController.kinArrayShow.get(position)
                                                    .kin_id.toString()
                                            val IDs: String =
                                                AppController.kinArrayShow.get(position).id.toString()
                                            val studentID: String =
                                                AppController.kinArrayShow.get(position)
                                                    .student_id.toString()
                                            val createdAT: String =
                                                AppController.kinArrayShow.get(position)
                                                    .created_at.toString()
                                            val updatedAT: String =
                                                AppController.kinArrayShow.get(position)
                                                    .updated_at.toString()
                                            val userID: String =
                                                AppController.kinArrayShow.get(position)
                                                    .user_id.toString()
                                            val corresspondence: String =
                                                AppController.kinArrayShow.get(position)
                                                    .correspondencemailmerge.toString()
                                            val justcontact: String =
                                                AppController.kinArrayShow.get(position)
                                                    .justcontact.toString()
                                            val contacts: String =
                                                AppController.kinArrayShow.get(position)
                                                    .reportmailmerge.toString()
                                            val model = KinModel()
                                            model.status=status
                                            model.request=request
                                            model.name=
                                                firstName.text.toString().trim { it <= ' ' }
                                            model.last_name=
                                                lastName.text.toString().trim { it <= ' ' }
                                            model.email=
                                                emailKin.text.toString().trim { it <= ' ' }
                                            model.title=ContactSpinner.text.toString()
                                            model.kin_id=kinID
                                            model.relationship=RelationalSpinner.text.toString()
                                            model.code=countryCode.textView_selectedCountry.text.toString()
                                            model.user_mobile=contactNumber.text.toString()
                                            model.student_id=studentID
                                            model.created_at=createdAT
                                            model.updated_at=updatedAT
                                            model.phone=contactNumber.text.toString()
                                            model.id=IDs
                                            model.user_id=userID
                                            // String corresspondence=AppController.kinArrayShow.get(position).correspondencemailmerge.toString;
                                            //String contacts=AppController.kinArrayShow.get(position).reportmailmerge.toString;
                                            model.correspondencemailmerge=corresspondence
                                            model.justcontact=justcontact
                                            model.reportmailmerge=contacts
                                            model.isFullFilled=true
                                            if (AppController.kinArrayShow.get(position)
                                                    .isNewData == true
                                            ) {
                                                model.isNewData=true
                                            } else {
                                                model.isNewData=false
                                            }
                                            model.isConfirmed=true
                                            for (j in 0 until CommonClass.kinArrayPass.size) {
                                                val dataId: String =
                                                    CommonClass.kinArrayPass.get(j).id.toString()
                                                if (IDs.equals(dataId, ignoreCase = true)) {
                                                    kinArrayPassPos = j
                                                }
                                            }
                                            AppController.kinArrayShow.removeAt(position)
                                            CommonClass.kinArrayPass.removeAt(kinArrayPassPos)
                                            AppController.kinArrayShow.add(position, model)
                                            CommonClass.kinArrayPass.add(kinArrayPassPos, model)
                                            PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                                            PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                                            PreferenceManager().saveKinDetailsArrayListShow(
                                                AppController.kinArrayShow,
                                                "kinshow", mContext
                                            )
                                            PreferenceManager().saveKinDetailsArrayList(
                                                CommonClass.kinArrayPass,
                                                "kinshow",mContext
                                            )
                                            dialog.dismiss()
                                            familyKinRecyclerAdapter!!.notifyDataSetChanged()
                                        }
                                    }
                                }
                                //   }
                            }
                        }
                    }
                }
            }
        }
        ContactSpinner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
                var status = ""
                var request = ""
                var kinArrayPassPos = -1
                if (!AppController.kinArrayShow.get(position).isNewData!!!!) {
                    if (CommonClass.isKinEdited) {
                        status = "1"
                        request = "0"
                    } else {
                        status = "5"
                        request = "0"
                    }
                } else {
                    if (CommonClass.isKinEdited) {
                        status = "0"
                        request = "1"
                    } else {
                        status = "0"
                        request = "1"
                    }
                }
                val kinID: String = AppController.kinArrayShow.get(position).kin_id.toString()
                val IDs: String = AppController.kinArrayShow.get(position).id.toString()
                val studentID: String = AppController.kinArrayShow.get(position).student_id.toString()
                val createdAT: String = AppController.kinArrayShow.get(position).created_at.toString()
                val updatedAT: String = AppController.kinArrayShow.get(position).updated_at.toString()
                val userID: String = AppController.kinArrayShow.get(position).user_id.toString()
                val corresspondence: String =
                    AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                val contacts: String = AppController.kinArrayShow.get(position).reportmailmerge.toString()
                val justcontact: String = AppController.kinArrayShow.get(position).justcontact.toString()
                val model = KinModel()
                model.status=status
                model.request=request
                model.name=firstName.text.toString().trim { it <= ' ' }
                model.last_name=lastName.text.toString().trim { it <= ' ' }
                model.email=emailKin.text.toString().trim { it <= ' ' }
                model.title=ContactSpinner.text.toString()
                model.kin_id=kinID
                model.relationship=RelationalSpinner.text.toString()
                model.code=countryCode.textView_selectedCountry.text.toString()
                model.user_mobile=contactNumber.text.toString()
                model.student_id=studentID
                model.created_at=createdAT
                model.updated_at=updatedAT
                model.phone=contactNumber.text.toString()
                model.id=IDs
                model.user_id=userID
                model.isFullFilled=true
                // String corresspondence=AppController.kinArrayShow.get(position).correspondencemailmerge.toString;
                //String contacts=AppController.kinArrayShow.get(position).reportmailmerge.toString;
                model.correspondencemailmerge=corresspondence
                model.justcontact=justcontact
                model.reportmailmerge=contacts
                if (AppController.kinArrayShow.get(position).isNewData == true) {
                    model.isNewData=true
                } else {
                    model.isNewData=false
                }
                model.isConfirmed=false
                for (j in 0 until CommonClass.kinArrayPass.size) {
                    val dataId: String = CommonClass.kinArrayPass.get(j).id.toString()
                    if (IDs.equals(dataId, ignoreCase = true)) {
                        kinArrayPassPos = j
                    }
                }
                AppController.kinArrayShow.removeAt(position)
                CommonClass.kinArrayPass.removeAt(kinArrayPassPos)
                AppController.kinArrayShow.add(position, model)
                CommonClass.kinArrayPass.add(kinArrayPassPos, model)
                PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                PreferenceManager().saveKinDetailsArrayListShow(AppController.kinArrayShow,"kinshow", mContext)
                PreferenceManager().saveKinDetailsArrayList(CommonClass.kinArrayPass, "kinshow",mContext)
            }
        })
        firstName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
                var status = ""
                var request = ""
                var kinArrayPassPos = -1
                if (!AppController.kinArrayShow.get(position).isNewData!!) {
                    if (CommonClass.isKinEdited) {
                        status = "1"
                        request = "0"
                    } else {
                        status = "5"
                        request = "0"
                    }
                } else {
                    if (CommonClass.isKinEdited) {
                        status = "0"
                        request = "1"
                    } else {
                        status = "0"
                        request = "1"
                    }
                }
                val kinID: String = AppController.kinArrayShow.get(position).kin_id.toString()
                val IDs: String = AppController.kinArrayShow.get(position).id.toString()
                val studentID: String = AppController.kinArrayShow.get(position).student_id.toString()
                val createdAT: String = AppController.kinArrayShow.get(position).created_at.toString()
                val updatedAT: String = AppController.kinArrayShow.get(position).updated_at.toString()
                val userID: String = AppController.kinArrayShow.get(position).user_id.toString()
                val corresspondence: String =
                    AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                val contacts: String = AppController.kinArrayShow.get(position).reportmailmerge.toString()
                val justcontact: String = AppController.kinArrayShow.get(position).justcontact.toString()
                val model = KinModel()
                model.status=status
                model.request=request
                model.name=firstName.text.toString().trim { it <= ' ' }
                model.last_name=lastName.text.toString().trim { it <= ' ' }
                model.email=emailKin.text.toString().trim { it <= ' ' }
                model.title=ContactSpinner.text.toString()
                model.kin_id=kinID
                model.relationship=RelationalSpinner.text.toString()
                model.code=countryCode.textView_selectedCountry.text.toString()
                model.user_mobile=contactNumber.text.toString()
                model.student_id=studentID
                model.created_at=createdAT
                model.updated_at=updatedAT
                model.phone=contactNumber.text.toString()
                model.id=IDs
                model.user_id=userID
                model.isFullFilled=false
                // String corresspondence=AppController.kinArrayShow.get(position).correspondencemailmerge.toString;
                //String contacts=AppController.kinArrayShow.get(position).reportmailmerge.toString;
                model.correspondencemailmerge=corresspondence
                model.justcontact=justcontact
                model.reportmailmerge=contacts
                if (AppController.kinArrayShow.get(position).isNewData == true) {
                    model.isNewData=true
                } else {
                    model.isNewData=false
                }
                model.isConfirmed=false
                for (j in 0 until CommonClass.kinArrayPass.size) {
                    val dataId: String = CommonClass.kinArrayPass.get(j).id.toString()
                    if (IDs.equals(dataId, ignoreCase = true)) {
                        kinArrayPassPos = j
                    }
                }
                AppController.kinArrayShow.removeAt(position)
                CommonClass.kinArrayPass.removeAt(kinArrayPassPos)
                AppController.kinArrayShow.add(position, model)
                CommonClass.kinArrayPass.add(kinArrayPassPos, model)
                PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                PreferenceManager().saveKinDetailsArrayListShow(AppController.kinArrayShow,"kinshow", mContext)
                PreferenceManager().saveKinDetailsArrayList(CommonClass.kinArrayPass,"kinshow", mContext)
            }
        })
        lastName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
                var status = ""
                var request = ""
                var kinArrayPassPos = -1
                if (!AppController.kinArrayShow.get(position).isNewData!!) {
                    if (CommonClass.isKinEdited) {
                        status = "1"
                        request = "0"
                    } else {
                        status = "5"
                        request = "0"
                    }
                } else {
                    if (CommonClass.isKinEdited) {
                        status = "0"
                        request = "1"
                    } else {
                        status = "0"
                        request = "1"
                    }
                }
                val kinID: String = AppController.kinArrayShow.get(position).kin_id.toString()
                val IDs: String = AppController.kinArrayShow.get(position).id.toString()
                val studentID: String = AppController.kinArrayShow.get(position).student_id.toString()
                val createdAT: String = AppController.kinArrayShow.get(position).created_at.toString()
                val updatedAT: String = AppController.kinArrayShow.get(position).updated_at.toString()
                val userID: String = AppController.kinArrayShow.get(position).user_id.toString()
                val corresspondence: String =
                    AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                val justcontact: String = AppController.kinArrayShow.get(position).justcontact.toString()
                val contacts: String = AppController.kinArrayShow.get(position).reportmailmerge.toString()
                val model = KinModel()
                model.status=status
                model.request=request
                model.name=firstName.text.toString().trim { it <= ' ' }
                model.last_name=lastName.text.toString().trim { it <= ' ' }
                model.email=emailKin.text.toString().trim { it <= ' ' }
                model.title=ContactSpinner.text.toString()
                model.kin_id=kinID
                model.relationship=RelationalSpinner.text.toString()
                model.code=countryCode.textView_selectedCountry.text.toString()
                model.user_mobile=contactNumber.text.toString()
                model.student_id=studentID
                model.created_at=createdAT
                model.updated_at=updatedAT
                model.phone=contactNumber.text.toString()
                model.id=IDs
                model.user_id=userID
                model.isFullFilled=false
                // String corresspondence=AppController.kinArrayShow.get(position).correspondencemailmerge.toString;
                //String contacts=AppController.kinArrayShow.get(position).reportmailmerge.toString;
                model.correspondencemailmerge=corresspondence
                model.justcontact=justcontact
                model.reportmailmerge=contacts
                if (AppController.kinArrayShow.get(position).isNewData == true) {
                    model.isNewData=true
                } else {
                    model.isNewData=false
                }
                model.isConfirmed=false
                for (j in 0 until CommonClass.kinArrayPass.size) {
                    val dataId: String = CommonClass.kinArrayPass.get(j).id.toString()
                    if (IDs.equals(dataId, ignoreCase = true)) {
                        kinArrayPassPos = j
                    }
                }
                AppController.kinArrayShow.removeAt(position)
                CommonClass.kinArrayPass.removeAt(kinArrayPassPos)
                AppController.kinArrayShow.add(position, model)
                CommonClass.kinArrayPass.add(kinArrayPassPos, model)
                PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                PreferenceManager().saveKinDetailsArrayListShow(AppController.kinArrayShow,"kinshow", mContext)
                PreferenceManager().saveKinDetailsArrayList(CommonClass.kinArrayPass,"kinshow", mContext)
            }
        })
        emailKin.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
                var status = ""
                var request = ""
                var kinArrayPassPos = -1
                if (!AppController.kinArrayShow.get(position).isNewData!!) {
                    if (CommonClass.isKinEdited) {
                        status = "1"
                        request = "0"
                    } else {
                        status = "5"
                        request = "0"
                    }
                } else {
                    if (CommonClass.isKinEdited) {
                        status = "0"
                        request = "1"
                    } else {
                        status = "0"
                        request = "1"
                    }
                }
                val kinID: String = AppController.kinArrayShow.get(position).kin_id.toString()
                val IDs: String = AppController.kinArrayShow.get(position).id.toString()
                val studentID: String = AppController.kinArrayShow.get(position).student_id.toString()
                val createdAT: String = AppController.kinArrayShow.get(position).created_at.toString()
                val updatedAT: String = AppController.kinArrayShow.get(position).updated_at.toString()
                val userID: String = AppController.kinArrayShow.get(position).user_id.toString()
                val corresspondence: String =
                    AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                val justcontact: String = AppController.kinArrayShow.get(position).justcontact.toString()
                val contacts: String = AppController.kinArrayShow.get(position).reportmailmerge.toString()
                val model = KinModel()
                model.status=status
                model.request=request
                model.name=firstName.text.toString().trim { it <= ' ' }
                model.last_name=lastName.text.toString().trim { it <= ' ' }
                model.email=emailKin.text.toString().trim { it <= ' ' }
                model.title=ContactSpinner.text.toString()
                model.kin_id=kinID
                model.relationship=RelationalSpinner.text.toString()
                model.code=countryCode.textView_selectedCountry.text.toString()
                model.user_mobile=contactNumber.text.toString()
                model.student_id=studentID
                model.created_at=createdAT
                model.updated_at=updatedAT
                model.phone=contactNumber.text.toString()
                model.id=IDs
                model.user_id=userID
                model.isFullFilled=false
                // String corresspondence=AppController.kinArrayShow.get(position).correspondencemailmerge.toString;
                //String contacts=AppController.kinArrayShow.get(position).reportmailmerge.toString;
                model.correspondencemailmerge=corresspondence
                model.justcontact=justcontact
                model.reportmailmerge=contacts
                if (AppController.kinArrayShow.get(position).isNewData == true) {
                    model.isNewData=true
                } else {
                    model.isNewData=false
                }
                model.isConfirmed=false
                for (j in 0 until CommonClass.kinArrayPass.size) {
                    val dataId: String = CommonClass.kinArrayPass.get(j).id.toString()
                    if (IDs.equals(dataId, ignoreCase = true)) {
                        kinArrayPassPos = j
                    }
                }
                AppController.kinArrayShow.removeAt(position)
                CommonClass.kinArrayPass.removeAt(kinArrayPassPos)
                AppController.kinArrayShow.add(position, model)
                CommonClass.kinArrayPass.add(kinArrayPassPos, model)
                PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                PreferenceManager().saveKinDetailsArrayListShow(AppController.kinArrayShow,"kinshow", mContext)
                PreferenceManager().saveKinDetailsArrayList(CommonClass.kinArrayPass,"kinshow", mContext)
            }
        })
        RelationalSpinner.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                if (!RelationalSpinner.text.toString()
                        .equals("Driver", ignoreCase = true) && !RelationalSpinner.text.toString()
                        .equals("Domestic Helper", ignoreCase = true)
                ) {
                    emailKin.setHint(R.string.AST_EMAIL)
                } else {
                    emailKin.setHint(R.string.AST_EMAIL_NO)
                }
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
                var status = ""
                var request = ""
                var kinArrayPassPos = -1
                if (!AppController.kinArrayShow.get(position).isNewData!!) {
                    if (CommonClass.isKinEdited) {
                        status = "1"
                        request = "0"
                    } else {
                        status = "5"
                        request = "0"
                    }
                } else {
                    if (CommonClass.isKinEdited) {
                        status = "0"
                        request = "1"
                    } else {
                        status = "0"
                        request = "1"
                    }
                }
                val kinID: String = AppController.kinArrayShow.get(position).kin_id.toString()
                val IDs: String = AppController.kinArrayShow.get(position).id.toString()
                val studentID: String = AppController.kinArrayShow.get(position).student_id.toString()
                val createdAT: String = AppController.kinArrayShow.get(position).created_at.toString()
                val updatedAT: String = AppController.kinArrayShow.get(position).updated_at.toString()
                val userID: String = AppController.kinArrayShow.get(position).user_id.toString()
                val corresspondence: String =
                    AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                val justcontact: String = AppController.kinArrayShow.get(position).justcontact.toString()
                val contacts: String = AppController.kinArrayShow.get(position).reportmailmerge.toString()

                val model = KinModel()
                model.status=status
                model.request=request
                model.name=firstName.text.toString().trim { it <= ' ' }
                model.last_name=lastName.text.toString().trim { it <= ' ' }
                model.email=emailKin.text.toString().trim { it <= ' ' }
                model.title=ContactSpinner.text.toString()
                model.kin_id=kinID
                model.relationship=RelationalSpinner.text.toString()
                model.code=countryCode.textView_selectedCountry.text.toString()
                model.user_mobile=contactNumber.text.toString()
                model.student_id=studentID
                model.created_at=createdAT
                model.updated_at=updatedAT
                model.phone=contactNumber.text.toString()
                model.id=IDs
                model.user_id=userID
                model.isFullFilled=false
                // String corresspondence=AppController.kinArrayShow.get(position).correspondencemailmerge.toString;
                //String contacts=AppController.kinArrayShow.get(position).reportmailmerge.toString;
                model.correspondencemailmerge=corresspondence
                model.justcontact=justcontact
                model.reportmailmerge=contacts
                if (AppController.kinArrayShow.get(position).isNewData == true) {
                    model.isNewData=true
                } else {
                    model.isNewData=false
                }
                model.isConfirmed=false
                for (j in 0 until CommonClass.kinArrayPass.size) {
                    val dataId: String = CommonClass.kinArrayPass.get(j).id.toString()
                    if (IDs.equals(dataId)) {
                        kinArrayPassPos = j
                    }
                }
                AppController.kinArrayShow.removeAt(position)
                CommonClass.kinArrayPass.removeAt(kinArrayPassPos)
                AppController.kinArrayShow.add(position, model)
                CommonClass.kinArrayPass.add(kinArrayPassPos, model)
                PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                PreferenceManager().saveKinDetailsArrayListShow(AppController.kinArrayShow,"kinshow", mContext)
                PreferenceManager().saveKinDetailsArrayList(CommonClass.kinArrayPass,"kinshow", mContext)
            }
        })
        contactNumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("afterTextChanged: ", "name Chaange")
                CommonClass.isKinEdited = true
                var status = ""
                var request = ""
                var kinArrayPassPos = -1
                if (!AppController.kinArrayShow.get(position).isNewData!!) {
                    if (CommonClass.isKinEdited) {
                        status = "1"
                        request = "0"
                    } else {
                        status = "5"
                        request = "0"
                    }
                } else {
                    if (CommonClass.isKinEdited) {
                        status = "0"
                        request = "1"
                    } else {
                        status = "0"
                        request = "1"
                    }
                }
                val kinID: String = AppController.kinArrayShow.get(position).kin_id.toString()
                val IDs: String = AppController.kinArrayShow.get(position).id.toString()
                val studentID: String = AppController.kinArrayShow.get(position).student_id.toString()
                val createdAT: String = AppController.kinArrayShow.get(position).created_at.toString()
                val updatedAT: String = AppController.kinArrayShow.get(position).updated_at.toString()
                val userID: String = AppController.kinArrayShow.get(position).user_id.toString()
                val corresspondence: String =
                    AppController.kinArrayShow.get(position).correspondencemailmerge.toString()
                val justcontact: String = AppController.kinArrayShow.get(position).justcontact.toString()
                val contacts: String = AppController.kinArrayShow.get(position).reportmailmerge.toString()
                val model = KinModel()
                model.status=status
                model.request=request
                model.name=firstName.text.toString().trim { it <= ' ' }
                model.last_name=lastName.text.toString().trim { it <= ' ' }
                model.email=emailKin.text.toString().trim { it <= ' ' }
                model.title=ContactSpinner.text.toString()
                model.kin_id=kinID
                model.relationship=RelationalSpinner.text.toString()
                model.code=countryCode.textView_selectedCountry.text.toString()
                model.user_mobile=contactNumber.text.toString()
                model.student_id=studentID
                model.created_at=createdAT
                model.updated_at=updatedAT
                model.phone=contactNumber.text.toString()
                model.id=IDs
                model.user_id=userID
                model.isFullFilled=false
                // String corresspondence=AppController.kinArrayShow.get(position).correspondencemailmerge.toString;
                //String contacts=AppController.kinArrayShow.get(position).reportmailmerge.toString;
                model.correspondencemailmerge=corresspondence
                model.justcontact=justcontact
                model.relationship=contacts
                if (AppController.kinArrayShow.get(position).isNewData!! == true) {
                    model.isNewData=true
                } else {
                    model.isNewData=false
                }
                model.isConfirmed=false
                for (j in 0 until CommonClass.kinArrayPass.size) {
                    val dataId: String = CommonClass.kinArrayPass.get(j).id.toString()
                    if (IDs.equals(dataId)) {
                        kinArrayPassPos = j
                    }
                }
                AppController.kinArrayShow.removeAt(position)
                CommonClass.kinArrayPass.removeAt(kinArrayPassPos)
                AppController.kinArrayShow.add(position, model)
                CommonClass.kinArrayPass.add(kinArrayPassPos, model)
                PreferenceManager().getKinDetailsArrayList("kinshow",mContext)!!.clear()
                PreferenceManager().getKinDetailsArrayListShow("kinshow",mContext)!!.clear()
                PreferenceManager().saveKinDetailsArrayListShow(AppController.kinArrayShow,"kinshow", mContext)
                PreferenceManager().saveKinDetailsArrayList(CommonClass.kinArrayPass,"kinshow", mContext)
            }
        })
    }

    private fun ShowCondition(whoValueEmpty: String) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout_data_collection)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(R.drawable.round)
        icon.setImageResource(R.drawable.exclamationicon)
        val text = dialog.findViewById<TextView>(R.id.text_dialog)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text = whoValueEmpty
        textHead.text = "Alert"
        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.text = "Ok"
        dialogButton.setOnClickListener {
            dialog.dismiss()
            //                    PreferenceManager().setIsValueEmpty(mContext,"0");
        }
        dialog.show()
    }
}

