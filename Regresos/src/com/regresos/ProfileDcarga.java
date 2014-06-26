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

import com.regresos.utils.ApiRequest;
import com.regresos.utils.AppPreference;
import com.regresos.utils.ApiRequest.RequestListener;

public class ProfileDcarga extends Fragment implements OnClickListener {
	public static final String TAG = "MyProfile";
	private EditText edit_full_name, edit_pro_email, edit_pro_tele, edit_pro_des_name, edit_pro_description, edit_pro_office, edit_pro_street,
			edit_pro_tele2, edit_pro_radio;
	private Spinner spn_pro_know;
	private Button btn_save_profile;
	
	private ProgressDialog pd;
	Dialog dialog;
	private String[] array_truck, array_service;

	public ProfileDcarga() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.profile_dcarga, container, false);

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
					edit_pro_email.setText(obj_addr.getString("email"));
					
					JSONObject obj_acc = obj_account.getJSONObject("Account");
					edit_pro_des_name.setText(obj_acc.getString("title"));
					edit_pro_description.setText(obj_acc.getString("description"));
					
					JSONArray arr_addr = obj_account.getJSONArray("Addres");
					JSONObject obj_addres = arr_addr.getJSONObject(0);
					edit_pro_office.setText(obj_addres.getString("title"));
					edit_pro_street.setText(obj_addres.getString("street")+" "+obj_addres.getString("number"));
					edit_pro_tele.setText(obj_addres.getString("phone_one"));
					edit_pro_tele2.setText(obj_addres.getString("phone_two"));
					edit_pro_radio.setText(obj_addres.getString("radio"));
					
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
		
		edit_full_name = (EditText) view.findViewById(R.id.edit_full_name);
		edit_pro_email = (EditText) view.findViewById(R.id.edit_pro_email);
		edit_pro_tele = (EditText) view.findViewById(R.id.edit_pro_tele);
		edit_pro_des_name = (EditText) view.findViewById(R.id.edit_pro_des_name);
		edit_pro_description = (EditText) view.findViewById(R.id.edit_pro_description);
		edit_pro_office = (EditText) view.findViewById(R.id.edit_pro_office);
		edit_pro_street = (EditText) view.findViewById(R.id.edit_pro_street);
		edit_pro_tele2 = (EditText) view.findViewById(R.id.edit_pro_tele2);
		edit_pro_radio = (EditText) view.findViewById(R.id.edit_pro_radio);
		
		spn_pro_know = (Spinner) view.findViewById(R.id.spn_pro_know);
		
		
		
		btn_save_profile = (Button) view.findViewById(R.id.btn_save_profile);
		btn_save_profile.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.truck_type:

			loadTruckType();

			break;

		case R.id.my_service:

			loadMyService();

			break;
			
		case R.id.btn_save_profile:
			
			if (TextUtils.isEmpty(edit_full_name.getText().toString())) {
				
			}
			else if (TextUtils.isEmpty(edit_pro_email.getText().toString())) {
				
			}
			else if (TextUtils.isEmpty(edit_pro_tele.getText().toString())) {
				
			}
			else if (TextUtils.isEmpty(edit_pro_des_name.getText().toString())) {
				
			}
			else if (TextUtils.isEmpty(edit_pro_description.getText().toString())) {
				
			}
			else if (TextUtils.isEmpty(edit_pro_office.getText().toString())) {
				
			}
			else if (TextUtils.isEmpty(edit_pro_street.getText().toString())) {
				
			}
			else if (TextUtils.isEmpty(edit_pro_tele2.getText().toString())) {
				
			}
			else if (TextUtils.isEmpty(edit_pro_radio.getText().toString())) {
				
			}
			else if (array_truck == null) {
				Toast.makeText(getActivity(), "Select Truck", Toast.LENGTH_SHORT).show();
			}
			else if (array_service == null) {
				Toast.makeText(getActivity(), "Select Service", Toast.LENGTH_SHORT).show();
			}
			else {
				updateProfile();
			}
			
			break;

		default:
			break;
		}
	}

	private void updateProfile() {
		pd = ProgressDialog.show(getActivity(), "", "Loading...", false, false);
		ApiRequest updateProfile = new ApiRequest(getActivity());
		updateProfile.setRequestListener(new RequestListener() {

			@Override
			public void onRequestSuccess(String success_response) {
				Log.v(TAG, success_response);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
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
		nameValuePair.add(new BasicNameValuePair("data[User][0][name]", edit_full_name.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[User][0][tel]", edit_full_name.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Account][title]", edit_full_name.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Account][description]", edit_full_name.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Address][0][title]", edit_full_name.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Address][0][street]", edit_full_name.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Account][know_id]", ""+(spn_pro_know.getSelectedItemPosition()+1)));
		nameValuePair.add(new BasicNameValuePair("data[Address][0][number]", edit_full_name.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Address][0][radio]", edit_full_name.getText().toString()));
		updateProfile.postMethod(getResources().getString(R.string.url_edit_profile), nameValuePair);
	}

	private void loadMyService() {
		final ArrayList<String> list_service_id = new ArrayList<String>();
		final ArrayList<String> list_service_name = new ArrayList<String>();
		pd = ProgressDialog.show(getActivity(), "", "Loading...", false, false);
		
		ApiRequest apiRequest = new ApiRequest(getActivity());
		apiRequest.setRequestListener(new RequestListener() {

			@Override
			public void onRequestSuccess(String success_response) {
				Log.v(TAG, success_response);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				
				try {
					JSONObject response = new JSONObject(success_response);
					JSONArray array_trucks = response.getJSONArray("services");
					
					for (int i = 0; i < array_trucks.length(); i++) {
						JSONObject array_truck = array_trucks.getJSONObject(i);
						JSONObject truck = array_truck.getJSONObject("Service");
						list_service_id.add(truck.getString("id"));
						list_service_name.add(truck.getString("title"));
					}
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
				
				AlertDialog.Builder serviceDialog = new AlertDialog.Builder(getActivity());
				serviceDialog.setIcon(R.drawable.servicios);
				serviceDialog.setTitle("servicio");
				final String[] services = list_service_name.toArray(new String[list_service_name.size()]);
				final Boolean[] service_selected = new Boolean[services.length];
				Arrays.fill(service_selected, false);
				serviceDialog.setMultiChoiceItems(services, null, new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						service_selected[which] = isChecked;
					}
				});
				serviceDialog.setPositiveButton("hecho", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ArrayList<String> service_id = new ArrayList<String>();
						for (int i = 0; i < service_selected.length; i++) {
							if(service_selected[i]) {
								service_id.add(list_service_id.get(i));
							}
						}
						System.out.println("Service "+service_id.toString());
						array_service = service_id.toArray(new String[service_id.size()]);
					}
				});
				serviceDialog.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				serviceDialog.show();
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
		apiRequest.postMethod(getResources().getString(R.string.url_profile_service), nameValuePair);
	}

	private void loadTruckType() {
		final ArrayList<String> list_truck_id = new ArrayList<String>();
		final ArrayList<String> list_truck_name = new ArrayList<String>();
		pd = ProgressDialog.show(getActivity(), "", "Loading...", false, false);
		ApiRequest apiRequest = new ApiRequest(getActivity());
		apiRequest.setRequestListener(new RequestListener() {

			@Override
			public void onRequestSuccess(String success_response) {
				Log.v(TAG, success_response);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				
				try {
					JSONObject response = new JSONObject(success_response);
					JSONArray array_trucks = response.getJSONArray("trucks");
					
					for (int i = 0; i < array_trucks.length(); i++) {
						JSONObject array_truck = array_trucks.getJSONObject(i);
						JSONObject truck = array_truck.getJSONObject("Truck");
						list_truck_id.add(truck.getString("id"));
						list_truck_name.add(truck.getString("title"));
					}
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
				
				AlertDialog.Builder truck = new AlertDialog.Builder(getActivity());
				truck.setIcon(R.drawable.camiones);
				truck.setTitle("carro Tipo");
				final String[] trucks = list_truck_name.toArray(new String[list_truck_name.size()]);
				final Boolean[] truck_selected = new Boolean[trucks.length];
				Arrays.fill(truck_selected, false);
				truck.setMultiChoiceItems(trucks, null, new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						truck_selected[which] = isChecked;
					}
				});
				truck.setPositiveButton("hecho", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ArrayList<String> truck_id = new ArrayList<String>();
						for (int i = 0; i < truck_selected.length; i++) {
							if(truck_selected[i]) {
								truck_id.add(list_truck_id.get(i));
							}
						}
						System.out.println("Trucks "+truck_id.toString());
						array_truck = truck_id.toArray(new String[truck_id.size()]);
					}
				});
				truck.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				truck.show();
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
		apiRequest.postMethod(getResources().getString(R.string.url_profile_trucks), nameValuePair);
	}
}
