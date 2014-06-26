package com.regresos.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.regresos.R;

public class CustomFontTextView extends TextView {
	private Context context;

	public CustomFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setCustomFont(context, attrs);
	}

	public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		setCustomFont(context, attrs);
	}

	public CustomFontTextView(Context context) {
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
