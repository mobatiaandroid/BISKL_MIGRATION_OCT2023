package com.example.bskl_kotlin.activity.tutorial

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.common.PreferenceManager

class TutorialActivity: AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var sharedprefs: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mContext = this
        sharedprefs = PreferenceManager()

        Toast.makeText(mContext,"tutorial", Toast.LENGTH_SHORT).show()
    }
}