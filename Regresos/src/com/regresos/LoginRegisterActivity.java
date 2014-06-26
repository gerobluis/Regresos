package com.regresos;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class LoginRegisterActivity extends Activity {
	private FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_register_activity);
		
		getActionBar().setTitle("");
		
		fragmentTransaction = getFragmentManager().beginTransaction();
		
		LoginRegisterFragment logRegFragment = new LoginRegisterFragment();
		
		fragmentTransaction.replace(R.id.login_register_container, logRegFragment, "LoginRegister").commit();
	}

}
