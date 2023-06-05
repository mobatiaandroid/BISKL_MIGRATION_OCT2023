package com.example.bskl_kotlin.manager

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.mobatia.bskl.R

class TextViewFontDJ5ColorBlack:androidx.appcompat.widget.AppCompatTextView {

    constructor(context: Context) : super(context) {
        val type = Typeface.createFromAsset(context.assets, "fonts/DJ5CTRIAL.otf")
        this.typeface = type
        this.setTextColor(context.resources.getColor(R.color.black))
        this.setTextSize(26F)
    }
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val type = Typeface.createFromAsset(context.assets, "fonts/DJ5CTRIAL.otf")
        this.typeface = type
        this.setTextColor(context.resources.getColor(R.color.black))
        this.setTextSize(26F)
    }
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        val type = Typeface.createFromAsset(context.assets, "fonts/DJ5CTRIAL.otf")
        this.typeface = type
        this.setTextColor(context.resources.getColor(R.color.black))
        this.setTextSize(26F)
    }
}