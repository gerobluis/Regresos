package com.regresos;

import com.regresos.utils.AppPreference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashScreen extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		MyHandler myHandler = new MyHandler();
		myHandler.sendEmptyMessageDelayed(1, 2000);
	}
	
	@SuppressLint("HandlerLeak")
	class MyHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg) {
			
//			if (AppPreference.isLoggedIn(getApplicationContext())) {
//				startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
//			}
//			else {
				startActivity(new Intent(SplashScreen.this, LoginRegisterActivity.class));
//			}
			finish();
			
		}
	}
	
	@Override
	public void onBackPressed() {
		
	}

}
