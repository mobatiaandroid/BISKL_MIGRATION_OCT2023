package com.example.bskl_kotlin.activity.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.login.LoginActivity
import com.example.bskl_kotlin.common.PreferenceManager
import java.util.Arrays

class TutorialActivity: AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var sharedprefs: PreferenceManager
    lateinit var mImgCircle: Array<ImageView?>
    private var mLinearLayout: LinearLayout? = null
    var mTutorialViewPager: ViewPager? = null
    var imageSkip: ImageView? = null
    var mTutorialViewPagerAdapter: TutorialViewPagerAdapter? = null
    var mPhotoList = ArrayList(
        Arrays.asList(
            R.drawable.quick_tutorial_1,
            R.drawable.quick_tutorial_2,
            R.drawable.quick_tutorial_3,
            R.drawable.quick_tutorial_4,
            R.drawable.quick_tutorial_5,
            R.drawable.quick_tutorial_6
        )
    )
    var dataType = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        mContext = this
        sharedprefs = PreferenceManager()
        val bundle = intent.extras
        if (bundle != null) {
            dataType = bundle.getInt("TYPE", 0)
        }
        initialiseViewPagerUI()

    }

    private fun initialiseViewPagerUI() {
        mTutorialViewPager = findViewById(R.id.tutorialViewPager)
        imageSkip = findViewById(R.id.imageSkip)
        mLinearLayout = findViewById(R.id.linear)

        mImgCircle = arrayOfNulls(mPhotoList.size)
        mTutorialViewPagerAdapter = TutorialViewPagerAdapter(mContext, mPhotoList)
        mTutorialViewPager!!.setCurrentItem(0)
        mTutorialViewPager!!.setAdapter(mTutorialViewPagerAdapter)
        addShowCountView(0)
        imageSkip!!.setOnClickListener(View.OnClickListener {
            if (dataType == 0) {
                finish()
            } else {
                PreferenceManager().setIsFirstLaunch(mContext, false)
                val loginIntent = Intent(
                    mContext,
                    LoginActivity::class.java
                )
                startActivity(loginIntent)
                finish()
            }
        })
        mTutorialViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                for (i in mPhotoList.indices) {
                    mImgCircle[i]!!.setImageResource(R.drawable.blackround)
                }
                if (position < mPhotoList.size) {
                    mImgCircle[position]!!.setImageResource(R.drawable.redround)
                    mLinearLayout!!.removeAllViews()
                    addShowCountView(position)
                } else {
                    mLinearLayout!!.removeAllViews()
                    if (dataType == 0) {
                        overridePendingTransition(0, 0)
                        finish()
                    } else {
                        PreferenceManager().setIsFirstLaunch(mContext, false)
                        val loginIntent = Intent(
                            mContext,
                            LoginActivity::class.java
                        )
                        startActivity(loginIntent)
                        finish()
                    }
                }
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        })
        mTutorialViewPager!!.getAdapter()!!.notifyDataSetChanged()
    }
    private fun addShowCountView(count: Int) {
        for (i in mPhotoList.indices) {
            mImgCircle[i] = ImageView(this)
            val layoutParams = LinearLayout.LayoutParams(
                resources
                    .getDimension(R.dimen.home_circle_width).toInt(), resources.getDimension(
                    R.dimen.home_circle_height
                ).toInt()
            )
            mImgCircle[i]!!.layoutParams = layoutParams
            if (i == count) {
                mImgCircle[i]!!.setBackgroundResource(R.drawable.redround)
            } else {
                mImgCircle[i]!!.setBackgroundResource(R.drawable.blackround)
            }
            mLinearLayout!!.addView(mImgCircle[i])
        }
    }
}