package com.example.bskl_kotlin.fragment.attendance;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.example.bskl_kotlin.R;
import com.example.bskl_kotlin.activity.tutorial.TutorialViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class AttendanceInfoActivity extends Activity  {
    private ImageView mImgCircle[];
    private LinearLayout mLinearLayout;
    ViewPager mTutorialViewPager;
    Context mContext;
    TutorialViewPagerAdapter mTutorialViewPagerAdapter;
    ArrayList<Integer> mPhotoList = new ArrayList<>(Arrays.asList(R.drawable.at1, R.drawable.at2, R.drawable.at3, R.drawable.at4));
    ArrayList<Integer> mPhotoListPhysical = new ArrayList<>(Arrays.asList(R.drawable.atnofull1, R.drawable.atnofull2, R.drawable.atnofull3, R.drawable.atnofull4));
    int dataType;
    ImageView imageSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        mContext = this;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            dataType = bundle.getInt("type", 0);

        }
        initialiseViewPagerUI();
    }

    /*******************************************************
     * Method name : initialiseViewPagerUI Description : initialise View pager
     * UI elements Parameters : nil Return type : void Date : Jan 13, 2015
     * Author : Rijo K Jose
     *****************************************************/
    private void initialiseViewPagerUI() {
        mTutorialViewPager = findViewById(R.id.tutorialViewPager);
        mLinearLayout = findViewById(R.id.linear);
        imageSkip = findViewById(R.id.imageSkip);
        mImgCircle = new ImageView[mPhotoList.size()];
        System.out.println("hasSoftKeys::"+hasSoftKeys());

        if (hasSoftKeys())
        {
            mTutorialViewPagerAdapter = new TutorialViewPagerAdapter(mContext, mPhotoListPhysical);

        }else
        {
            mTutorialViewPagerAdapter = new TutorialViewPagerAdapter(mContext, mPhotoList);

        }
        mTutorialViewPager.setCurrentItem(0);
        mTutorialViewPager.setAdapter(mTutorialViewPagerAdapter);
        addShowCountView(0);
        imageSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        mTutorialViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mPhotoList.size(); i++) {
                    mImgCircle[i].setBackgroundDrawable(getResources()
                            .getDrawable(R.drawable.blackround));
                }
                if (position < mPhotoList.size()) {
                    mImgCircle[position].setBackgroundDrawable(getResources()
                            .getDrawable(R.drawable.redround));
                    mLinearLayout.removeAllViews();
                    addShowCountView(position);
                } else {
                    mLinearLayout.removeAllViews();
                    if (dataType == 0) {
                        overridePendingTransition(0, 0);

                        finish();

                    } else {

                        finish();
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        mTutorialViewPager.getAdapter().notifyDataSetChanged();
    }

    /*******************************************************
     * Method name : addShowCountView Description : add show count view at
     * bottom Parameters : count Return type : void Date : Apr 17, 2015 Author :
     * Rijo K Jose
     *****************************************************/
    private void addShowCountView(int count) {
        for (int i = 0; i < mPhotoList.size(); i++) {
            mImgCircle[i] = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    (int) getResources()
                            .getDimension(R.dimen.home_circle_width),
                    (int) getResources().getDimension(
                            R.dimen.home_circle_height));
            mImgCircle[i].setLayoutParams(layoutParams);
            if (i == count) {
                mImgCircle[i].setBackgroundResource(R.drawable.redround);
            } else {
                mImgCircle[i].setBackgroundResource(R.drawable.blackround);
            }
            mLinearLayout.addView(mImgCircle[i]);
        }
    }
    private boolean hasSoftKeys() {
        boolean hasSoftwareKeys;

        Activity activity = this;

        WindowManager window = activity.getWindowManager();

        if(window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Display d = activity.getWindowManager().getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            hasSoftwareKeys =  (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        } else {
            boolean hasMenuKey = ViewConfiguration.get(mContext).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

            hasSoftwareKeys = !hasMenuKey && !hasBackKey;
        }

        return hasSoftwareKeys;
    }

}
