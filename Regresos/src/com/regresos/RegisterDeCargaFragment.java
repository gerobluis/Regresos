package com.regresos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.regresos.utils.ApiRequest;
import com.regresos.utils.ApiRequest.RequestListener;

public class RegisterDeCargaFragment extends Fragment implements OnClickListener {
	private EditText edit_reg_first_name, edit_reg_last_name, edit_email, edit_tele, edit_reg_password, edit_des, edit_office, edit_business;
	private TextView txt_accept_link;
	private Spinner spn_know, spn_tele_type;
	private CheckBox chk_accept_agree, chk_account;
	private Button btn_reg_frag;
	private LinearLayout linear_business_detail;
	private ProgressDialog pd;
	AlertDialog.Builder alert = null;
	private AlertDialog alertDialog = null;
	private ApiRequest apiRequest;
	private ApiRequest apiReq;
	private String business_id = null;
	private ArrayList<String> list_label = new ArrayList<String>();
	private ArrayList<String> list_id = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	public boolean check;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.register_fragment, container, false);

		alert = new AlertDialog.Builder(getActivity());
		
		edit_reg_first_name = (EditText) view.findViewById(R.id.edit_reg_first_name);
		edit_reg_last_name = (EditText) view.findViewById(R.id.edit_reg_last_name);
		edit_email = (EditText) view.findViewById(R.id.edit_email);
		edit_tele = (EditText) view.findViewById(R.id.edit_tele);
		edit_reg_password = (EditText) view.findViewById(R.id.edit_reg_password);
		edit_des = (EditText) view.findViewById(R.id.edit_des);
		edit_office = (EditText) view.findViewById(R.id.edit_office);
		edit_business = (EditText) view.findViewById(R.id.edit_business);
		edit_business.setOnClickListener(this);
		check = true;
		
		txt_accept_link = (TextView) view.findViewById(R.id.txt_accept_link);
		txt_accept_link.setText(Html.fromHtml("<a href=\"http://www.regresos.com\">Accepto los terminos condiciones</a> "));
		txt_accept_link.setMovementMethod(LinkMovementMethod.getInstance());

		spn_know = (Spinner) view.findViewById(R.id.spn_know);
		spn_tele_type = (Spinner) view.findViewById(R.id.spn_tele_type);

		btn_reg_frag = (Button) view.findViewById(R.id.btn_reg_frag);

		linear_business_detail = (LinearLayout) view.findViewById(R.id.linear_business_detail);

		chk_accept_agree = (CheckBox) view.findViewById(R.id.chk_accept_agree);
		
		chk_account = (CheckBox) view.findViewById(R.id.chk_account);
		chk_account.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					linear_business_detail.setVisibility(View.GONE);
					check = true;
				}
				else {
					linear_business_detail.setVisibility(View.VISIBLE);
					check=false;
				}
			}
		});

		btn_reg_frag.setOnClickListener(this);

		apiRequest = new ApiRequest(getActivity());
		apiRequest.setRequestListener(new RequestListener() {

			@Override
			public void onRequestSuccess(String success_response) {
				System.out.println(success_response);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				try {
					JSONObject j_obj = new JSONObject(success_response);
					JSONObject j_obj1 = j_obj.getJSONObject("response");
					if (j_obj1.getString("result").equals("success")) {
						startActivity(new Intent(getActivity(), DashboardActivity.class).putExtra("show_dialog", true));
					}
					else if (j_obj1.getString("result").equals("failure")) {
						alert.setTitle("Registro failured");
						alert.setMessage(""+j_obj1.getString("message"));
						alert.setPositiveButton("Despedir", null);
						alert.show();
					}
				}
				catch (Exception e) {
					Toast.makeText(getActivity(), "Fallo el Registro", Toast.LENGTH_SHORT).show();
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

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reg_frag:

			if (TextUtils.isEmpty(edit_reg_first_name.getText().toString())) {
				Toast.makeText(getActivity(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(edit_reg_last_name.getText().toString())) {
				Toast.makeText(getActivity(), "Apellido no puede estar vacío", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(edit_email.getText().toString())) {
				Toast.makeText(getActivity(), "El correo electrónico no puede estar vacío", Toast.LENGTH_SHORT).show();
			}
			else if (!Patterns.EMAIL_ADDRESS.matcher(edit_email.getText().toString()).matches()) {
				Toast.makeText(getActivity(), "El correo electrónico no es válida", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(edit_tele.getText().toString())) {
				Toast.makeText(getActivity(), "El teléfono no puede estar vacío", Toast.LENGTH_SHORT).show();
			}
			else if (edit_tele.getText().toString().length() < 10) {
				Toast.makeText(getActivity(), "Número de teléfono debe ser dentro de diez dígitos", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(edit_reg_password.getText().toString())) {
				Toast.makeText(getActivity(), "La contraseña no puede estar vacío", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(edit_business.getText().toString())) {
				Toast.makeText(getActivity(), "Mencione su trabajo", Toast.LENGTH_SHORT).show();
			}
			else if (!chk_account.isChecked() && TextUtils.isEmpty(edit_des.getText().toString())) {
				Toast.makeText(getActivity(), "La descripción no puede estar vacío", Toast.LENGTH_SHORT).show();
			}
			else if (!chk_account.isChecked() && TextUtils.isEmpty(edit_office.getText().toString())) {
				Toast.makeText(getActivity(), "Nombre de la oficina no puede estar vacío", Toast.LENGTH_SHORT).show();
			}
			else if (!chk_accept_agree.isChecked()) {
				Toast.makeText(getActivity(), "Por favor, acepte los términos y condiciones", Toast.LENGTH_SHORT).show();
			}
			else {

				pd = ProgressDialog.show(getActivity(), "", "Que registrarse en...", false, false);

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
				nameValuePair.add(new BasicNameValuePair("data[User][0][name]", edit_reg_first_name.getText().toString()));
				nameValuePair.add(new BasicNameValuePair("data[User][0][lastname]", edit_reg_last_name.getText().toString()));
				nameValuePair.add(new BasicNameValuePair("data[User][0][email]", edit_email.getText().toString()));
				nameValuePair.add(new BasicNameValuePair("data[Account][know_id]",
						getActivity().getResources().getStringArray(R.array.arr_how_id)[spn_know.getSelectedItemPosition()]));
				nameValuePair.add(new BasicNameValuePair("data[User][0][tel]", edit_tele.getText().toString()));
				nameValuePair.add(new BasicNameValuePair("data[User][0][tel_flag]", String.valueOf(spn_tele_type.getSelectedItemPosition())));
				nameValuePair.add(new BasicNameValuePair("data[User][0][password]", edit_reg_password.getText().toString()));
				nameValuePair.add(new BasicNameValuePair("data[Account][group_id]", "2"));
				nameValuePair.add(new BasicNameValuePair("data[Work][Id]", business_id));
				nameValuePair.add(new BasicNameValuePair("data[User][0][acept_terms]", "1"));

				if (chk_account.isChecked()) {

					nameValuePair.add(new BasicNameValuePair("data[Work][question]", "1"));
				}
				else {
					nameValuePair.add(new BasicNameValuePair("data[Work][title]", edit_business.getText().toString()));
					nameValuePair.add(new BasicNameValuePair("data[Work][question]", "0"));
					nameValuePair.add(new BasicNameValuePair("data[Account][description]", edit_des.getText().toString()));
					nameValuePair.add(new BasicNameValuePair("data[Addres][0][title]", edit_office.getText().toString()));
				}
				apiRequest.postMethod(getResources().getString(R.string.url_register), nameValuePair);
			}

			break;

		case R.id.edit_business:
           if(check){
			final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setCancelable(false);
			alert.setTitle("Escriba el nombre de la empresa");

			View alert_view = LayoutInflater.from(getActivity()).inflate(R.layout.auto_complete_layout, null);
			final EditText edit_bus_name = (EditText) alert_view.findViewById(R.id.edit_bus_name);
			final ProgressBar prog = (ProgressBar) alert_view.findViewById(R.id.prog);

			final ListView list_auto_complete = (ListView) alert_view.findViewById(R.id.list_auto_complete);
			adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_label);
			list_auto_complete.setAdapter(adapter);
			list_auto_complete.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					edit_business.setText(list_label.get(arg2));
					business_id = list_id.get(arg2);
					alertDialog.dismiss();
				}
			});

			edit_bus_name.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					edit_business.setFocusableInTouchMode(false);
		        	   edit_business.setFocusable(false);
					System.out.println("Label length : " + list_label.size() + "Value : " + s.toString());
					if (s.length() > 0) {

						prog.setVisibility(View.VISIBLE);
						apiReq = new ApiRequest(getActivity());
						apiReq.setRequestListener(new RequestListener() {

							@Override
							public void onRequestSuccess(String success_response) {
								// TODO Auto-generated method stub
								System.out.println("Register response: " + success_response);
								prog.setVisibility(View.GONE);
								list_label.clear();
								list_id.clear();
								try {
									JSONArray j_arr = new JSONArray(success_response);
									for (int i = 0; i < j_arr.length(); i++) {
										JSONObject j_obj = new JSONObject(j_arr.getString(i));
										list_label.add(j_obj.getString("label"));
										list_id.add(j_obj.getString("id"));
									}
									adapter.notifyDataSetChanged();
								}
								catch (Exception e) {
									// TODO: handle exception
								}
							}

							@Override
							public void onRequestError(String error_response) {
								prog.setVisibility(View.GONE);
								adapter.notifyDataSetChanged();
								System.out.println("Register error " + error_response);
							}
						});
						List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
						nameValuePair.add(new BasicNameValuePair("data[Work][question]", "1"));
						nameValuePair.add(new BasicNameValuePair("data[Work][title]", s.toString()));

						apiReq.postMethod(getResources().getString(R.string.url_bus_auto), nameValuePair);
					}
				}
			});

			alert.setView(alert_view);
			alert.setPositiveButton("cancelar", null);
			alertDialog = alert.create();
			alertDialog.show();
           }else{
        	   edit_business.setFocusableInTouchMode(true);
        	   edit_business.setFocusable(true);
           }

			break;

		default:
			break;
		}
	}

}