package com.example.bskl_kotlin.activity.notification

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.bskl_kotlin.R
import com.example.bskl_kotlin.activity.tutorial.TutorialViewPagerAdapter
import java.util.Arrays

class NotificationInfoActivity: AppCompatActivity() {
    lateinit var mContext: Context
    var mImgCircle: Array<ImageView?>?=null
    private var mLinearLayout: LinearLayout? = null
    var mTutorialViewPager: ViewPager? = null

    var mTutorialViewPagerAdapter: TutorialViewPagerAdapter? = null
    var mPhotoList = ArrayList(
        Arrays.asList(
            R.drawable.msg1,
            R.drawable.msg2,
            R.drawable.msg3,
            R.drawable.msg4,
            R.drawable.msg5
        )
    )

    var dataType = 0
    var imageSkip: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        mContext = this
        val bundle = intent.extras
        if (bundle != null) {
            dataType = bundle.getInt("type", 0)
        }
        initialiseViewPagerUI()
    }

    private fun initialiseViewPagerUI() {
        mTutorialViewPager = findViewById(R.id.tutorialViewPager)
        mLinearLayout = findViewById(R.id.linear)
        imageSkip = findViewById(R.id.imageSkip)

//        if (WissPreferenceManager.getUserName(mContext).equals("")) {
//            mPhotoList = new int[] { R.drawable.tut_1, R.drawable.tut_2,
//                    R.drawable.tut_3, R.drawable.tut_4, R.drawable.tut_5 };
//        } else {
//            mPhotoList = new int[] { R.drawable.tut_1, R.drawable.tut_2,
//                    R.drawable.tut_3, R.drawable.tut_4, R.drawable.tut_6 };
//        }

//        if (WissPreferenceManager.getUserName(mContext).equals("")) {
//            mPhotoList = new int[] { R.drawable.tut_1, R.drawable.tut_2,
//                    R.drawable.tut_3, R.drawable.tut_4, R.drawable.tut_5 };
//        } else {
//            mPhotoList = new int[] { R.drawable.tut_1, R.drawable.tut_2,
//                    R.drawable.tut_3, R.drawable.tut_4, R.drawable.tut_6 };
//        }
        mImgCircle = arrayOfNulls(mPhotoList.size)
        mTutorialViewPagerAdapter = TutorialViewPagerAdapter(mContext, mPhotoList)
        mTutorialViewPager!!.setCurrentItem(0)
        mTutorialViewPager!!.setAdapter(mTutorialViewPagerAdapter)
        addShowCountView(0)
        imageSkip!!.setOnClickListener(View.OnClickListener { finish() })
        mTutorialViewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                for (i in mPhotoList.indices) {
                    mImgCircle!![i]!!.setBackgroundDrawable(
                        resources
                            .getDrawable(R.drawable.blackround)
                    )
                }
                if (position < mPhotoList.size) {
                    mImgCircle!![position]!!.setBackgroundDrawable(
                        resources
                            .getDrawable(R.drawable.redround)
                    )
                    mLinearLayout!!.removeAllViews()
                    addShowCountView(position)
                } else {
                    mLinearLayout!!.removeAllViews()
                    if (dataType == 0) {
                        overridePendingTransition(0, 0)
                        finish()
                    } else {
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
            mImgCircle!![i] = ImageView(this)
            val layoutParams = LinearLayout.LayoutParams(
                resources
                    .getDimension(R.dimen.home_circle_width).toInt(), resources.getDimension(
                    R.dimen.home_circle_height
                ).toInt()
            )
            mImgCircle!![i]!!.layoutParams = layoutParams
            if (i == count) {
                mImgCircle!![i]!!.setBackgroundResource(R.drawable.redround)
            } else {
                mImgCircle!![i]!!.setBackgroundResource(R.drawable.blackround)
            }
            mLinearLayout!!.addView(mImgCircle!![i])
        }
    }
}