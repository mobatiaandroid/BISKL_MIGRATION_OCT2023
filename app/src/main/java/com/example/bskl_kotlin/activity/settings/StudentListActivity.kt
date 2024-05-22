package com.example.bskl_kotlin.activity.settings

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.settings.adapter.StudentListRecyclerAdapter
import com.example.bskl_kotlin.activity.settings.model.StudentUserModel
import com.example.bskl_kotlin.manager.AppUtils
import com.example.bskl_kotlin.recyclerviewmanager.RecyclerItemListener
import java.text.ParseException

class StudentListActivity:AppCompatActivity() {
    lateinit var mContext:Context


    lateinit var studentList: RecyclerView
     var studentsModelArrayList: ArrayList<StudentUserModel> =ArrayList()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_list_activity)

        mContext = this



            /*   mYoutubeModelArrayList = (ArrayList<YoutubeModel>) extras
                    .getSerializable("youtubeArray");*/
        studentsModelArrayList = intent.getParcelableArrayListExtra("studentlist")!!
Log.e("studentsModelArrayList_size", studentsModelArrayList.size.toString())
        studentList = findViewById(R.id.studentList)
        studentList.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentList.setLayoutManager(llm)
        val mStudentListRecyclerAdapter =
            StudentListRecyclerAdapter(mContext, studentsModelArrayList)
        studentList.setAdapter(mStudentListRecyclerAdapter)

        try {
            initialiseUI()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        mContext = this

    }

    private fun initialiseUI() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_view_home)
        supportActionBar!!.elevation = 0f

        val view = supportActionBar!!.customView
        val toolbar = view.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)
        val headerTitle = view.findViewById<TextView>(R.id.headerTitle)
        val logoClickImgView = view.findViewById<ImageView>(R.id.logoClickImgView)
        val action_bar_forward = view.findViewById<ImageView>(R.id.action_bar_forward)
        val action_bar_back = view.findViewById<ImageView>(R.id.action_bar_back)
        action_bar_back.setImageResource(R.drawable.back_new)
        headerTitle.text = "Students"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        action_bar_forward.visibility = View.INVISIBLE

        action_bar_back.setOnClickListener { onBackPressed() }

        studentList.addOnItemTouchListener(
            RecyclerItemListener(mContext, studentList,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (studentsModelArrayList[position].alumi.equals("0")) {
                            val mIntent = Intent(
                                mContext,
                                StudentDetailActivity::class.java
                            )
                            mIntent.putExtra(
                                "stud_id",
                                studentsModelArrayList[position].stud_id
                            )
                            startActivity(mIntent)
                        } else {
                            AppUtils().showDialogAlertDismiss(
                                mContext as Activity,
                                "Alert",
                                mContext.getString(R.string.student_not_available),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )

    }
}
