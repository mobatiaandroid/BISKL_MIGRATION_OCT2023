package com.example.bskl_kotlin.manager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.example.bskl_kotlin.R;

import java.io.Serializable;

// TODO: Auto-generated Javadoc

/**
 * The Class Headermanager.
 */
public class HeaderManager implements Serializable {

	/** The context. */
	private Activity context;

	/** The inflator. */
	private LayoutInflater inflator;

	/** The header view. */
	public View headerView;

	/** The m heading1. */
	private TextView mHeading1;

	/** The relative params. */
	private LayoutParams relativeParams;

	/** The heading1. */
	private String heading1;

	/** The is cancel. */
	private boolean isCancel = false;

	private EditText edtText;
	/* FOR HOME SCREEN */
	/** The is home. */
	private boolean isHome = false;

	/** The m right text. */
	private TextView mLeftText, mRightText;

	/** The m left. */
	private ImageView mLeftImage, mRightImage, mRight, mLeft,logoClickImgView;
	ImageView btn_info;
	/**
	 * Instantiates a new headermanager.
	 * 
	 * @param context
	 *            the context
	 * @param heading1
	 *            the heading1
	 */

	public HeaderManager(Activity context, String heading1) {
		this.setContext(context);

		inflator = LayoutInflater.from(context);
		this.heading1 = heading1;

	}

	/*
	 * public Headermanager(Activity context,String heading1) {
	 * this.setContext(context); inflator = LayoutInflater.from(context);
	 * this.heading1=heading1; this.isCancel=isCancel; }
	 */
	/**
	 * Instantiates a new headermanager.
	 * 
	 * @param home
	 *            the home
	 * @param context
	 *            the context
	 */
	public HeaderManager(boolean home, Activity context) {
		this.setContext(context);
		inflator = LayoutInflater.from(context);
		this.isHome = home;
	}

	/**
	 * Gets the left text.
	 * 
	 * @return the left text
	 */
	public TextView getLeftText() {
		return mLeftText;
	}

	/**
	 * Sets the left text.
	 * 
	 * @param mLeftText
	 *            the new left text
	 */
	public void setLeftText(TextView mLeftText) {
		this.mLeftText = mLeftText;
	}

	/**
	 * Gets the right text.
	 * 
	 * @return the right text
	 */
	public TextView getRightText() {
		return mRightText;
	}

	/**
	 * Sets the right text.
	 * 
	 * @param mLeftText
	 *            the new right text
	 */
	public void setRightText(TextView mLeftText) {
		this.mRightText = mLeftText;
	}

	// image view
	/**
	 * Gets the left image.
	 * 
	 * @return the left image
	 */
	public ImageView getLeftImage() {
		return mLeftImage;
	}

	/**
	 * Sets the left image.
	 * 
	 * @param mLeftImage
	 *            the new left image
	 */
	public void setLeftImage(ImageView mLeftImage) {
		this.mLeftImage = mLeftImage;
	}

	/**
	 * Gets the right image.
	 * 
	 * @return the right image
	 */
	public ImageView getRightImage() {
		return mLeftImage;
	}
	public ImageView getRightInfoImage() {
		return btn_info;
	}

	/**
	 * Sets the right image.
	 * 
	 * @param mLeftImage
	 *            the new right image
	 */
	public void setRightImage(ImageView mLeftImage) {
		this.mRightImage = mLeftImage;
	}

	/**
	 * Sets the visible.
	 * 
	 * @param v
	 *            the new visible
	 */
	public void setVisible(View v) {
		v.setVisibility(View.VISIBLE);
	}

	/**
	 * Sets the invisible.
	 */
	public void setInvisible() {
		headerView.setVisibility(View.INVISIBLE);
	}

	/**
	 * Sets the invisible.
	 * 
	 * @param v
	 *            the new invisible
	 */
	public void setInvisible(View v) {
		v.setVisibility(View.INVISIBLE);
	}

	/**
	 * Gets the header.
	 * 
	 * @param headerHolder
	 *            the header holder
	 * @return the header
	 */
	public int getHeader(RelativeLayout headerHolder, int type) {
		initializeUI(type);
		relativeParams = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		headerHolder.addView(headerView, relativeParams);
		return headerView.getId();
	}

	/**
	 * Gets the header.
	 * 
	 * @param headerHolder
	 *            the header holder
	 * @return the header
	 */
	public int getHeader(RelativeLayout headerHolder, boolean getHeading,
                         int type) {
		initializeUI(getHeading, type);
		relativeParams = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		headerHolder.addView(headerView, relativeParams);
		return headerView.getId();
	}

	/**
	 * Initialize ui.
	 */
	private void initializeUI(int type) {
		inflator = LayoutInflater.from(getContext());
		System.out.println("htype" + type);
		headerView = inflator.inflate(R.layout.common_header_single, null);

		RelativeLayout logoHeader = headerView
				.findViewById(R.id.relative_logo_header);
		 btn_info = headerView
				.findViewById(R.id.btn_info);

		if (type == 0) {
			logoHeader.setBackgroundResource(R.drawable.home_page_logo);// two
			btn_info.setVisibility(View.GONE);
			// buttons
		} else if (type == 1) {
			logoHeader.setBackgroundResource(R.drawable.home_page_logo);// left
			btn_info.setVisibility(View.GONE);
			// button
		}else if (type == 3) {
			logoHeader.setBackgroundResource(R.drawable.home_page_logo);// left
			btn_info.setVisibility(View.VISIBLE);
			// button
		}
		mHeading1 = headerView.findViewById(R.id.heading);
		mHeading1.setText(heading1);
		mHeading1.setBackgroundColor(context.getResources().getColor(R.color.split_bg));

		mRight = headerView.findViewById(R.id.btn_right);
		mLeft = headerView.findViewById(R.id.btn_left);
		logoClickImgView = headerView.findViewById(R.id.logoClickImgView);
	}

	/**
	 * Initialize ui.
	 */
	private void initializeUI(boolean getHeading, int type) {
		inflator = LayoutInflater.from(getContext());

		headerView = inflator.inflate(R.layout.common_header_single, null);

		RelativeLayout logoHeader = headerView
				.findViewById(R.id.relative_logo_header);
		btn_info = headerView
				.findViewById(R.id.btn_info);

		if (type == 0) {
			logoHeader.setBackgroundResource(R.drawable.home_page_logo);
			btn_info.setVisibility(View.GONE);

		} else if (type == 1) {
			logoHeader.setBackgroundResource(R.drawable.home_page_logo);
			mHeading1 = headerView.findViewById(R.id.heading);
			mHeading1.setVisibility(View.GONE);
			btn_info.setVisibility(View.GONE);

		}else if (type == 3) {
			logoHeader.setBackgroundResource(R.drawable.home_page_logo);// left
			btn_info.setVisibility(View.VISIBLE);
			// button
		}/*
		 * else if(type==2) {
		 * logoHeader.setBackgroundResource(R.drawable.titlebars); }
		 */
		

//		edtText = (EditText) headerView.findViewById(R.id.edtTxt_Header);
		mHeading1 = headerView.findViewById(R.id.heading);
		mHeading1.setText(heading1);
		mHeading1.setBackgroundColor(context.getResources().getColor(R.color.split_bg));
		mRight = headerView.findViewById(R.id.btn_right);
		mLeft = headerView.findViewById(R.id.btn_left);
		logoClickImgView = headerView.findViewById(R.id.logoClickImgView);
	}

	/**
	 * Sets the title bar.
	 * 
	 * @param titleBar
	 *            the new title bar
	 */
	public void setTitleBar(int titleBar) {
		this.headerView.setBackgroundResource(titleBar);
	}

	public void setTitle(String title) {
		mHeading1.setText(title);
	}

	/**
	 * Gets the left button.
	 * 
	 * @return the left button
	 */
	public ImageView getLeftButton() {
		return mLeft;
	}
	public ImageView getLogoButton() {
		return logoClickImgView;
	}

	/**
	 * Sets the left button.
	 * 
	 * @param right
	 *            the new left button
	 */
	public void setLeftButton(ImageView right) {
		this.mLeft = right;
	}

	/**
	 * Gets the right button.
	 * 
	 * @return the right button
	 */
	public ImageView getRightButton() {
		return mRight;
	}

	public EditText getEditText() {
		mHeading1.setVisibility(View.GONE);
		edtText.setVisibility(View.VISIBLE);
		return edtText;
	}

	/**
	 * Sets the edits the text.
	 * 
	 * @param editText
	 *            the new edits the text
	 */
	public void setEditText(EditText editText) {
		this.edtText = editText;
		setVisible(edtText);
	}

	/**
	 * Sets the right button.
	 * 
	 * @param right
	 *            the new right button
	 */
	public void setRightButton(ImageView right) {
		this.mRight = right;
	}

	/**
	 * Sets the button right selector.
	 * 
	 * @param normalStateResID
	 *            the normal state res id
	 * @param pressedStateResID
	 *            the pressed state res id
	 */
	public void setButtonRightSelector(int normalStateResID,
			int pressedStateResID) {
		if (normalStateResID==R.drawable.settings)
		{
			mRight.setBackgroundResource(R.drawable.settings);
		}
//		else if (normalStateResID==R.drawable.help)
//		{
//			mRight.setBackgroundResource(R.drawable.help);
//		}
//		else if (normalStateResID==R.drawable.share)
//		{
//			mRight.setBackgroundResource(R.drawable.share);
//		}
//		else
//		{
//			mRight.setBackgroundResource(R.drawable.settings);
//
//		}
		mRight.setImageDrawable(getButtonDrawableByScreenCathegory(
				normalStateResID, pressedStateResID));
		setVisible(mRight);
	}

	/**
	 * Sets the button left selector.
	 * 
	 * @param normalStateResID
	 *            the normal state res id
	 * @param pressedStateResID
	 *            the pressed state res id
	 */
	public void setButtonLeftSelector(int normalStateResID,
			int pressedStateResID) {
		mLeft.setImageDrawable(getButtonDrawableByScreenCathegory(
				normalStateResID, pressedStateResID));
		setVisible(mLeft);
	}

	/**
	 * Gets the button drawable by screen cathegory.
	 * 
	 * @param normalStateResID
	 *            the normal state res id
	 * @param pressedStateResID
	 *            the pressed state res id
	 * @return the button drawable by screen cathegory
	 */
	public Drawable getButtonDrawableByScreenCathegory(int normalStateResID,
                                                       int pressedStateResID) {

		Drawable state_normal = context.getResources()
				.getDrawable(normalStateResID).mutate();

		Drawable state_pressed = context.getResources()
				.getDrawable(pressedStateResID).mutate();

		StateListDrawable drawable = new StateListDrawable();

		drawable.addState(new int[] { android.R.attr.state_pressed },
				state_pressed);
		drawable.addState(new int[] { android.R.attr.state_enabled },
				state_normal);

		return drawable;
	}

	// public void setCancelButton()
	// {
	// mRight.setBackgroundResource(R.drawable.close_button_selector);
	// setVisible(mRight);
	// }

	/**
	 * Sets the context.
	 * 
	 * @param context
	 *            the new context
	 */
	public void setContext(Activity context) {
		this.context = context;
	}

	/**
	 * Gets the context.
	 * 
	 * @return the context
	 */
	public Activity getContext() {
		return context;
	}

}
