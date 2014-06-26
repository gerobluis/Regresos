package com.regresos.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreference {
	public static final String PREF_NAME = "Regresos";
	public static final int PREF_MODE = Context.MODE_PRIVATE;
	public static final String ACCOUNT_ID = "ACCOUNT_ID";
	public static final String USER_ID = "USER_ID";
	public static final String STATE_ID = "STATE_ID";
	public static final String GROUP_ID = "GROUP_ID";
	public static final String LEVEL_ID = "LEVEL_ID";

	
	public static SharedPreferences getSharedPreferences(Context context) {
		
		return context.getSharedPreferences(AppPreference.PREF_NAME, PREF_MODE);
	}
	
	public static boolean isLoggedIn(Context context) {
		boolean is_logged = false;
		if (!getSharedPreferences(context).getString(USER_ID, "").isEmpty()) {
			is_logged = true;
		}
		
		 return is_logged;
	}
	
	/**
	 * 
	 * @param context
	 * @return editor of the preference
	 */
	public static Editor getEditor(Context context) {
		
		return getSharedPreferences(context).edit();
	}
	
	/**
	 * 
	 * @param context
	 * @param prefernce key
	 * @param value to store
	 */
	public static void writeString(Context context, String key, String value) {
		getSharedPreferences(context).edit().putString(key, value).commit();
	}
	
	/**
	 * 
	 * @param context
	 * @param preference key to get string value
	 * @return string value for the key
	 */
	public static String getString(Context context, String key) {
		
		return getSharedPreferences(context).getString(key, "");
	}
	
	/**
	 * 
	 * @param context
	 * @param preference key
	 * @param boolean value to store
	 */
	public static void writeBoolean(Context context, String key, boolean value) {
		getSharedPreferences(context).edit().putBoolean(key, value).commit();
	}
	
	/**
	 * 
	 * @param context
	 * @param preference key to get boolean value
	 * @return boolean value for the key
	 */
	public static boolean getBoolean(Context context, String key) {
		System.out.println("Get boolean value");
		
		return getSharedPreferences(context).getBoolean(key, false);
	}
	
	/**
	 * 
	 * @param context
	 * @param prefernce key
	 * @param value to store
	 */
	public static void writeInteger(Context context, String key, int value) {
		getSharedPreferences(context).edit().putInt(key, value).commit();
	}
	
	/**
	 * 
	 * @param context
	 * @param preference key to get string value
	 * @return string value for the key
	 */
	public static int getInteger(Context context, String key) {
		
		return getSharedPreferences(context).getInt(key, 0);
	}

}
