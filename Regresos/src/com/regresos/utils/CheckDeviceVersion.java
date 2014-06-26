package com.regresos.utils;

import android.content.Context;

public class CheckDeviceVersion {

	/**
	 * 
	 * @param context
	 * @return device's sdk version
	 */
	public static int getDeviceVersion(Context context) {

		return android.os.Build.VERSION.SDK_INT;
	}

}
