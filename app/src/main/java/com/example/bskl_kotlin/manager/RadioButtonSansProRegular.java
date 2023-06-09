package com.example.bskl_kotlin.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;


/**
 * Created by gayatri on 24/1/17.
 */

public class RadioButtonSansProRegular extends androidx.appcompat.widget.AppCompatRadioButton {
    public RadioButtonSansProRegular(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Regular.otf" );
        this.setTypeface(type);
    }

    public RadioButtonSansProRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Regular.otf");
        this.setTypeface(type);

    }

    public RadioButtonSansProRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Regular.otf" );
        this.setTypeface(type);

    }



}
