package com.example.bskl_kotlin.manager

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.ImageView

@SuppressLint("AppCompatCustomView")
class RoundImageView : ImageView {
    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    /**
     * @param context
     * @param attrs
     */
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    /**
     * @param context
     */
    constructor(context: Context?) : super(context)

    companion object {
        /********************************************************
         * Method name : getCroppedBitmap Description : get cropped bitmap
         * Parameters : bitmap, radius Return type : bitmap Date : Dec 11, 2014
         * Author : Rijo K Jose
         */
        fun getCroppedBitmap(bmp: Bitmap, radius: Int): Bitmap? {
            var output: Bitmap? = null
            if (radius > 0) {
                val sbmp: Bitmap
                sbmp = if (bmp.width != radius || bmp.height != radius) Bitmap.createScaledBitmap(
                    bmp,
                    radius,
                    radius,
                    false
                ) else bmp
                output = Bitmap.createBitmap(
                    sbmp.width, sbmp.height,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(output)
                val paint = Paint()
                val rect = Rect(0, 0, sbmp.width, sbmp.height)
                paint.isAntiAlias = true
                paint.isFilterBitmap = true
                paint.isDither = true
                canvas.drawARGB(0, 0, 0, 0)
                paint.color = Color.parseColor("#BAB399")
                canvas.drawCircle(
                    sbmp.width / 2 + 0.7f,
                    sbmp.height / 2 + 0.7f, sbmp.width / 2 + 0.1f,
                    paint
                )
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                canvas.drawBitmap(sbmp, rect, rect, paint)
            }
            return output
        }
    }
}