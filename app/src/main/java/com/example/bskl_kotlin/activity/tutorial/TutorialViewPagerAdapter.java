package com.example.bskl_kotlin.activity.tutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.bskl_kotlin.R;

import java.util.ArrayList;

public class TutorialViewPagerAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<Integer> mImagesArrayListBg;
    private LayoutInflater mInflaters;


    public TutorialViewPagerAdapter(Context context, ArrayList<Integer> mImagesArrayList)
    {
        this.mImagesArrayListBg=new ArrayList<Integer>();
        this.mContext=context;
        this.mImagesArrayListBg=mImagesArrayList;
    }

    @Override
    public int getCount() {
        return mImagesArrayListBg.size()+1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View pageview = null;
        mInflaters = LayoutInflater.from(mContext);
        pageview = mInflaters.inflate(R.layout.layout_imagepager_adapter, null);
        ImageView imageView = pageview.findViewById(R.id.adImg);
        if(position<mImagesArrayListBg.size()) {
            imageView.setBackgroundResource(mImagesArrayListBg.get(position));

            container.

                    addView(pageview, 0);
        }


        return pageview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}