package com.regresos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.regresos.comeback.MyComebacks;
import com.regresos.utils.ApiRequest;
import com.regresos.utils.AppPreference;
import com.regresos.utils.ApiRequest.RequestListener;
import com.regresos.utils.CustomEditText;
import com.regresos.utils.CustomFontTextView;

public class WelcomeFragment extends Fragment implements OnClickListener {
	public static final String TAG = "MyProfile";
	CustomFontTextView edit_full_name;
	private CustomFontTextView edit_pro_des_name;
	CustomEditText edit_pro_description;
	private FragmentTransaction fragTrans;
	private FragmentManager fragMan;
	
	private ProgressDialog pd;
	Dialog dialog;

	public WelcomeFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.welcome, container, false);

		initializeLayout(view);

		Bundle bundle = getArguments();
		boolean show_dialog = bundle.getBoolean("show_dialog");
		if (show_dialog) {

			dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
			dialog.setCancelable(false);
			View activate_view = LayoutInflater.from(getActivity()).inflate(R.layout.activate_account, null);
			Button btn_activate_acc = (Button) activate_view.findViewById(R.id.btn_activate_acc);
			btn_activate_acc.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.setContentView(activate_view);
			dialog.show();
		}
		

		return view;
	}



	private void initializeLayout(View view) {
		
		edit_full_name = (CustomFontTextView) view.findViewById(R.id.tv_com_name);
		edit_pro_des_name = (CustomFontTextView) view.findViewById(R.id.tv_com_name1);
		edit_pro_description = (CustomEditText) view.findViewById(R.id.profile_description);

		
		
		//btn_save_profile = (Button) view.findViewById(R.id.btn_save_profile);
		//btn_save_profile.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_edit_profile:
			ProfileDcarga editProfile = new ProfileDcarga();
			fragTrans = fragMan.beginTransaction();
			fragTrans.replace(R.id.fragment_container, editProfile).commit();
			break;

		default:
			break;
		}
	}

}
