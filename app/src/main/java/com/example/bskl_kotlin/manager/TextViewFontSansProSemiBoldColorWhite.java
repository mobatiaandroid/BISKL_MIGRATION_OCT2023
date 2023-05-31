package com.example.bskl_kotlin.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mobatia.bskl.R;


/**
 * Created by gayatri on 24/1/17.
 */

public class TextViewFontSansProSemiBoldColorWhite extends TextView {
    public TextViewFontSansProSemiBoldColorWhite(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Semibold.otf" );
        this.setTypeface(type);
        this.setTextColor(context.getResources().getColor(R.color.white));
    }

    public TextViewFontSansProSemiBoldColorWhite(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Semibold.otf");
        this.setTypeface(type);
       this.setTextColor(context.getResources().getColor(R.color.white));

    }

    public TextViewFontSansProSemiBoldColorWhite(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Semibold.otf" );
        this.setTypeface(type);
        this.setTextColor(context.getResources().getColor(R.color.white));

    }


}
