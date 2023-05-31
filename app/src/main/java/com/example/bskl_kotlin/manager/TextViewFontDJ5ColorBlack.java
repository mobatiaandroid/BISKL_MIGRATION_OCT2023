package com.example.bskl_kotlin.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mobatia.bskl.R;


/**
 * Created by gayatri on 24/1/17.
 */

public class TextViewFontDJ5ColorBlack extends TextView {
    public TextViewFontDJ5ColorBlack(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/DJ5CTRIAL.otf" );
        this.setTypeface(type);
        this.setTextColor(context.getResources().getColor(R.color.black));
        this.setTextSize(26);
    }

    public TextViewFontDJ5ColorBlack(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/DJ5CTRIAL.otf");
        this.setTypeface(type);
       this.setTextColor(context.getResources().getColor(R.color.black));
        this.setTextSize(26);

    }

    public TextViewFontDJ5ColorBlack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/DJ5CTRIAL.otf" );
        this.setTypeface(type);
        this.setTextColor(context.getResources().getColor(R.color.black));
        this.setTextSize(26);

    }


}
