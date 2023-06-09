package com.example.bskl_kotlin.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;


/**
 * Created by gayatri on 24/1/17.
 */

public class EditTextFontSansProBold extends androidx.appcompat.widget.AppCompatEditText {
    public EditTextFontSansProBold(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Bold.otf" );
        this.setTypeface(type);
//        this.setTextColor(context.getResources().getColor(R.color.white));
    }

    public EditTextFontSansProBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Bold.otf");
        this.setTypeface(type);
//       this.setTextColor(context.getResources().getColor(R.color.white));

    }

    public EditTextFontSansProBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Bold.otf" );
        this.setTypeface(type);
//        this.setTextColor(context.getResources().getColor(R.color.white));

    }


}
