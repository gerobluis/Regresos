package com.regresos.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.regresos.R;

public class CustomCheckBox extends CheckBox {
	private Context context;

	public CustomCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setCustomFont(context, attrs);
	}

	public CustomCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		setCustomFont(context, attrs);
	}

	public CustomCheckBox(Context context) {
		super(context);
		this.context = context;
	}

	public void setCustomFont(String fontString) {
		setTypeface(Typeface.createFromAsset(context.getAssets(),
				fontString));
	}

	public void setCustomFont(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CustomTextView);
		Typeface tf = null;
		try {
			tf = Typeface.createFromAsset(context.getAssets(),
					a.getString(R.styleable.CustomTextView_customFont));
		} catch (Exception e) {
		}
		setTypeface(tf);
		a.recycle();
	}

}
