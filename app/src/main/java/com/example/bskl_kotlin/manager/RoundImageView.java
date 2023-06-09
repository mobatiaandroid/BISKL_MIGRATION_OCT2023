/**
 * 
 */
package com.example.bskl_kotlin.manager;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * @author Rijo K Jose
 *
 */
public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public RoundImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/********************************************************
	 * Method name : getCroppedBitmap Description : get cropped bitmap
	 * Parameters : bitmap, radius Return type : bitmap Date : Dec 11, 2014
	 * Author : Rijo K Jose
	 *****************************************************/
	public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		Bitmap output = null;
		if (radius > 0) {
			Bitmap sbmp;
			if (bmp.getWidth() != radius || bmp.getHeight() != radius)
				sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
			else
				sbmp = bmp;
			output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
					Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

			paint.setAntiAlias(true);
			paint.setFilterBitmap(true);
			paint.setDither(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.parseColor("#BAB399"));
			canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,
					sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f,
					paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(sbmp, rect, rect, paint);
		}
		return output;
	}

}
