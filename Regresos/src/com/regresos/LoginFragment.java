package com.regresos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.regresos.utils.ApiRequest;
import com.regresos.utils.AppPreference;
import com.regresos.utils.ApiRequest.RequestListener;

public class LoginFragment extends Fragment implements OnClickListener {
	private EditText edit_username, edit_password;
	private TextView txt_for_pass;
	private CheckBox chk_rem_pass;
	private Button btn_login_frag;
	private ProgressDialog pd;
	private AlertDialog alertDialog = null;
	private ApiRequest apiRequest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.login_fragment, container, false);

		edit_username = (EditText) view.findViewById(R.id.edit_username);
		
		edit_password = (EditText) view.findViewById(R.id.edit_password);
		
		
		txt_for_pass = (TextView) view.findViewById(R.id.txt_for_pass);
		txt_for_pass.setOnClickListener(this);
		
		chk_rem_pass = (CheckBox) view.findViewById(R.id.chk_rem_pass);
		
		btn_login_frag = (Button) view.findViewById(R.id.btn_login_frag);
		btn_login_frag.setOnClickListener(this);

		apiRequest = new ApiRequest(getActivity().getApplicationContext());
		apiRequest.setRequestListener(new RequestListener() {

			@Override
			public void onRequestSuccess(String success_response) {
				//startActivity(new Intent(getActivity(), WelcomeActivity.class).putExtra("show_dialog", false));
			Log.i("Login response", success_response);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				try {
					JSONObject j_obj = new JSONObject(success_response);
					JSONObject j_obj1 = j_obj.getJSONObject("response");
					String result = j_obj1.getString("respuesta");
					if (result.equals("correcto")) {
						
						
							AppPreference.writeString(getActivity(), AppPreference.ACCOUNT_ID, j_obj1.getString("account_id"));
							AppPreference.writeString(getActivity(), AppPreference.USER_ID, j_obj1.getString("user_id"));
							AppPreference.writeString(getActivity(), AppPreference.STATE_ID, j_obj1.getString("state"));
							AppPreference.writeString(getActivity(), AppPreference.GROUP_ID, j_obj1.getString("group_id"));
							AppPreference.writeString(getActivity(), AppPreference.LEVEL_ID, j_obj1.getString("level_id"));
						
						if("1".equals(j_obj1.getString("group_id"))){
							startActivity(new Intent(getActivity(), DashboardActivity.class).putExtra("show_dialog", false));
						}
						else{
						startActivity(new Intent(getActivity(), WelcomeActivity.class).putExtra("show_dialog", false));
						}
						
					}
					else {
						Toast.makeText(getActivity(), "Fallo el acceso", Toast.LENGTH_SHORT).show();
					}
				}
				catch (Exception e) {
					Toast.makeText(getActivity(), "Acceda failured", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onRequestError(String error_response) {
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				Toast.makeText(getActivity(), "Acceda failured " + error_response, Toast.LENGTH_SHORT).show();
			}
		});

		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login_frag:

			if (TextUtils.isEmpty(edit_username.getText().toString())) {

				Toast.makeText(getActivity(), "El correo electrónico no puede estar vacío", Toast.LENGTH_SHORT).show();
			}

			else if (!Patterns.EMAIL_ADDRESS.matcher(edit_username.getText().toString()).matches()) {
				Toast.makeText(getActivity(), "El correo electrónico no es válido", Toast.LENGTH_SHORT).show();
			}

			else if (TextUtils.isEmpty(edit_password.getText().toString())) {

				Toast.makeText(getActivity(), "La contraseña no puede estar vacía", Toast.LENGTH_SHORT).show();
			}

			else {

				pd = ProgressDialog.show(getActivity(), "", "Entrando a Regresos", false, false);

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
				nameValuePair.add(new BasicNameValuePair("data[User][email]", edit_username.getText().toString()));
				nameValuePair.add(new BasicNameValuePair("data[User][password]", edit_password.getText().toString()));
				apiRequest.postMethod(getResources().getString(R.string.url_login), nameValuePair);
			}

			break;
			
		case R.id.txt_for_pass:
			
			final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setCancelable(false);
			alert.setTitle("Ingrese su dirección de e-mail registrada");

			View alert_view = LayoutInflater.from(getActivity()).inflate(R.layout.forgot_pass_layout, null);
			final EditText edit_bus_name = (EditText) alert_view.findViewById(R.id.edit_forgot_email);
			alert.setView(alert_view);
			alert.setPositiveButton("hecho", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (TextUtils.isEmpty(edit_bus_name.getText().toString())) {
						Toast.makeText(getActivity(), "El correo electrónico no puede estar vacío", Toast.LENGTH_SHORT).show();
					}
					else {
						pd = ProgressDialog.show(getActivity(), "", "El envío de la contraseña a su correo", false, false);
						ApiRequest api = new ApiRequest(getActivity());
						api.setRequestListener(new RequestListener() {
							
							@Override
							public void onRequestSuccess(String success_response) {
								if (pd != null && pd.isShowing()) {
									pd.dismiss();
								}
								Toast.makeText(getActivity(), "Busca tu nuevo contraseña en tu correo", Toast.LENGTH_SHORT).show();
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
						nameValuePair.add(new BasicNameValuePair("Email", edit_bus_name.getText().toString()));
						api.postMethod(getResources().getString(R.string.url_forgot_pass), nameValuePair);
					}
				}
			});
			alert.setNegativeButton("cancelar", null);
			alertDialog = alert.create();
			alertDialog.show();
			
			break;

		default:
			break;
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		apiRequest.cancelRequest();
	}

}
