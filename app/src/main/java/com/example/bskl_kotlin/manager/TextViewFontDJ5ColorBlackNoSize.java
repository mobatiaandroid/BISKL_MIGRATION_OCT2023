package com.example.bskl_kotlin.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.bskl_kotlin.R;


/**
 * Created by gayatri on 24/1/17.
 */

public class TextViewFontDJ5ColorBlackNoSize extends TextView {
    public TextViewFontDJ5ColorBlackNoSize(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/DJ5CTRIAL.otf" );
        this.setTypeface(type);
        this.setTextColor(context.getResources().getColor(R.color.black));
    }

    public TextViewFontDJ5ColorBlackNoSize(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/DJ5CTRIAL.otf");
        this.setTypeface(type);
       this.setTextColor(context.getResources().getColor(R.color.black));

    }

    public TextViewFontDJ5ColorBlackNoSize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/DJ5CTRIAL.otf" );
        this.setTypeface(type);
        this.setTextColor(context.getResources().getColor(R.color.black));

    }


}
