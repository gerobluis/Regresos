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

public class MyProfile extends Fragment implements OnClickListener {
	public static final String TAG = "MyProfile";
	CustomFontTextView edit_full_name;
	private CustomFontTextView edit_pro_des_name;
	CustomEditText edit_pro_description;
	private FragmentTransaction fragTrans;
	private FragmentManager fragMan;
	
	private ProgressDialog pd;
	Dialog dialog;

	public MyProfile() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.my_profile, container, false);

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
		
		else {
			loadProfileDetails();
		}

		return view;
	}

	private void loadProfileDetails() {
		pd = ProgressDialog.show(getActivity(), "", "Obtención de datos del perfil...", false, false);
		
		ApiRequest loadProfile = new ApiRequest(getActivity());
		loadProfile.setRequestListener(new RequestListener() {

			@Override
			public void onRequestSuccess(String success_response) {
				Log.v(TAG, success_response);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				try {
					JSONObject response = new JSONObject(success_response);
					JSONObject obj_account = response.getJSONObject("account");
					
					JSONArray arr_user = obj_account.getJSONArray("User");
					JSONObject obj_addr = arr_user.getJSONObject(0);
					edit_full_name.setText(obj_addr.getString("name"));
					
					JSONObject obj_acc = obj_account.getJSONObject("Account");
					edit_pro_des_name.setText(obj_acc.getString("title"));
					edit_pro_description.setText(obj_acc.getString("description"));
					
					JSONArray arr_addr = obj_account.getJSONArray("Addres");
				//	JSONObject obj_addres = arr_addr.getJSONObject(0);
					
					
				}
				catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "Problem in getting profile details", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onRequestError(String error_response) {
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				Toast.makeText(getActivity(), error_response, Toast.LENGTH_SHORT).show();
			}
		});
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("data[Account][id]", AppPreference.getString(getActivity(), AppPreference.ACCOUNT_ID)));
		nameValuePair.add(new BasicNameValuePair("data[User][0][id]", AppPreference.getString(getActivity(), AppPreference.USER_ID)));
		loadProfile.postMethod(getResources().getString(R.string.url_view_profile), nameValuePair);
		
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
