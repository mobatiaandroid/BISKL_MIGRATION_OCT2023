package com.example.bskl_kotlin.fragment.messages

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.common.PreferenceManager
import com.example.bskl_kotlin.fragment.messages.adapter.StudentRecyclerAdapter
import kotlin.time.Duration.Companion.days

class MessageDetail:AppCompatActivity() {
    lateinit var mContext:Context
    var extras: Bundle? = null
    private var mLoadUrl: String? = null
    private var message = ""
    var tab_type: String? = null
    private var date = ""
    private val title = ""
    lateinit var msgTitle: TextView
    lateinit var msgDate: TextView
    lateinit var mWebView: TextView
    var position = 0
    lateinit var studentRecycle: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_detail_desc)

        mContext = this
        extras = intent.extras
        if (extras != null) {
            mLoadUrl = extras!!.getString("message")
            date = extras!!.getString("date")!!
            message = extras!!.getString("title")!!
            position = extras!!.getInt("position")
        }
        initUi()

    }

    private fun initUi() {
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
        headerTitle.text = "Messages"
        headerTitle.visibility = View.VISIBLE
        logoClickImgView.visibility = View.INVISIBLE
        action_bar_back.setOnClickListener { finish() }
        msgDate = findViewById(R.id.msgDate)
        msgTitle = findViewById(R.id.msgTitle)
        studentRecycle = findViewById(R.id.studentRecycle)
        msgDate.setText(PreferenceManager().getMessageReadList(mContext).get(position).day)
        msgTitle.setText(PreferenceManager().getMessageReadList(mContext).get(position).title)
        studentRecycle.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        studentRecycle.layoutManager = llm
        val mStudentRecyclerAdapter =
            StudentRecyclerAdapter(mContext, PreferenceManager().getMessageReadList(mContext))
        studentRecycle.adapter = mStudentRecyclerAdapter
    }


}
