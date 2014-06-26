package com.regresos.shipment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.regresos.R;
import com.regresos.utils.ApiRequest;
import com.regresos.utils.AppPreference;
import com.regresos.utils.ApiRequest.RequestListener;

public class ShipmentTabTwo extends Fragment implements OnClickListener {
	private EditText et_return_name,et_category, et_truck, et_regresos_description, et_date_sales, et_date_arrival, et_start_route, et_end_route,
			et_start_calle,et_end_calle, et_start_numero, et_end_numero,et_start_colonia,et_end_colonia,
			et_weight,et_width,et_height,et_length,
			et_when_sales,et_when_arrival;
	private Button btn_submit_regresos;
	private ProgressDialog pd;
	private AlertDialog alertDialog = null;
	private ApiRequest apiReq;
	private String business_id = null;
	private String start_city_id = null;
	private String start_state_id = null;
	private String start_country_id = null;
	private String end_city_id = null;
	private String end_state_id = null;
	private String end_country_id = null;
	private ArrayList<String> list_label = new ArrayList<String>();
	private ArrayList<String> list_id = new ArrayList<String>();
	private ArrayList<String> list_stateid = new ArrayList<String>();
	private ArrayList<String> list_countryid = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shipment_tab_two, null);

		initialize(view);

		return view;
	}

	private void initialize(View view) {
		et_return_name = (EditText) view.findViewById(R.id.et_return_name);
		et_category= (EditText) view.findViewById(R.id.et_category);
		et_truck = (EditText) view.findViewById(R.id.et_truck);
		et_regresos_description = (EditText) view.findViewById(R.id.et_regresos_description);
		et_date_sales = (EditText) view.findViewById(R.id.et_date_sales);
		et_date_arrival = (EditText) view.findViewById(R.id.et_date_arrival);
		et_start_route = (EditText) view.findViewById(R.id.et_start_route);
		et_end_route = (EditText) view.findViewById(R.id.et_end_route);
		et_weight= (EditText) view.findViewById(R.id.et_weight);
		et_height= (EditText) view.findViewById(R.id.et_height);
		et_width= (EditText) view.findViewById(R.id.et_width);
		et_length= (EditText) view.findViewById(R.id.et_length);
		et_when_sales= (EditText) view.findViewById(R.id.et_when_sales);
		et_when_arrival= (EditText) view.findViewById(R.id.et_when_arrival);
		et_start_calle= (EditText) view.findViewById(R.id.et_start_calle);
		et_end_calle= (EditText) view.findViewById(R.id.et_end_calle);
		et_start_numero= (EditText) view.findViewById(R.id.et_start_numero);
		et_end_numero= (EditText) view.findViewById(R.id.et_end_numero);
		et_start_colonia= (EditText) view.findViewById(R.id.et_start_colonia);
		et_end_colonia= (EditText) view.findViewById(R.id.et_end_colonia);

//		et_capacity_top = (EditText) view.findViewById(R.id.et_capacity_top);
//		et_capacity_end = (EditText) view.findViewById(R.id.et_capacity_end);
//		et_city_spend = (EditText) view.findViewById(R.id.et_city_spend);
//		
		btn_submit_regresos = (Button) view.findViewById(R.id.btn_submit_regresos);
		et_category.setOnClickListener(this);
		et_truck.setOnClickListener(this);
		et_date_sales.setOnClickListener(this);
		et_date_arrival.setOnClickListener(this);
		et_when_sales.setOnClickListener(this);
		et_when_arrival.setOnClickListener(this);
		et_start_route.setOnClickListener(this);
		et_end_route.setOnClickListener(this);
//		et_capacity_top.setOnClickListener(this);
//		et_capacity_end.setOnClickListener(this);
//		et_city_spend.setOnClickListener(this);
		btn_submit_regresos.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.et_category:
			showCategories(et_category);
			break;
		case R.id.et_truck:
			showTrucks(et_truck);
			break;
		case R.id.et_date_sales:
			
			DialogFragment date_sales = new DatePickerFragment(et_date_sales);
			date_sales.show(getFragmentManager(), "SalesDate");

			break;

		case R.id.et_date_arrival:
			
			DialogFragment date_arrival = new DatePickerFragment(et_date_arrival);
			date_arrival.show(getFragmentManager(), "ArrivalDate");

			break;

		case R.id.et_when_sales:
			showOptions(et_when_sales);
			

			break;

		case R.id.et_when_arrival:
			showOptions(et_when_arrival);	
			
			
			break;
		case R.id.et_start_route:
			final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setCancelable(false);
			alert.setTitle("Escriba el nombre de la ciudad de donde sales");

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
					et_start_route.setText(list_label.get(arg2));
					start_city_id = list_id.get(arg2);
					start_state_id = list_stateid.get(arg2);
					start_country_id = list_countryid.get(arg2);
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
								list_stateid.clear();
								list_countryid.clear();
								try {
									JSONArray j_arr = new JSONArray(success_response);
									for (int i = 0; i < j_arr.length(); i++) {
										JSONObject j_obj = new JSONObject(j_arr.getString(i));
										list_label.add(j_obj.getString("label"));
										list_id.add(j_obj.getString("city_id"));
										list_stateid.add(j_obj.getString("state_id"));
										list_countryid.add(j_obj.getString("country_id"));
									}
									adapter.notifyDataSetChanged();
								}
								catch (Exception e) {
									// TODO: handle exception
									Toast.makeText(getActivity(), "Problema accesando", Toast.LENGTH_SHORT).show();
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
						
						

						apiReq.postMethod(getResources().getString(R.string.url_ciudad_auto)+"/"+s.toString(), nameValuePair);
					}
				}
			});

			alert.setView(alert_view);
			alert.setPositiveButton("cancelar", null);
			alertDialog = alert.create();
			alertDialog.show();
			break;
		case R.id.et_end_route:
			final AlertDialog.Builder alert_end = new AlertDialog.Builder(getActivity());
			alert_end.setCancelable(false);
			alert_end.setTitle("Escriba el nombre de la ciudad de donde sales");

			View alert_view1 = LayoutInflater.from(getActivity()).inflate(R.layout.auto_complete_layout, null);
			final EditText edit_bus_name1 = (EditText) alert_view1.findViewById(R.id.edit_bus_name);
			final ProgressBar prog1 = (ProgressBar) alert_view1.findViewById(R.id.prog);

			final ListView list_auto_complete1 = (ListView) alert_view1.findViewById(R.id.list_auto_complete);
			adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_label);
			list_auto_complete1.setAdapter(adapter);
			list_auto_complete1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					et_end_route.setText(list_label.get(arg2));
					end_city_id = list_id.get(arg2);
					end_state_id = list_stateid.get(arg2);
					end_country_id = list_countryid.get(arg2);
					alertDialog.dismiss();
				}
			});

			edit_bus_name1.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(final Editable s) {
					System.out.println("Label length : " + list_label.size() + "Value : " + s.toString());
					if (s.length() > 0) {
						
						URL url = null;
						try {
							url = new URL(getResources().getString(R.string.url_ciudad_auto)+"/"+s.toString().replace(" ", "+"));
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}		
						prog1.setVisibility(View.VISIBLE);
						new AsyncTask<URL, Void, String>() {
							
						    @Override
						    protected String doInBackground(URL... urls) {
						     
								return business_id;
						    }

						    @Override
						    protected void onPostExecute(String success_response) {
						    	try {
									JSONArray j_arr = new JSONArray(success_response);
									for (int i = 0; i < j_arr.length(); i++) {
										JSONObject j_obj = new JSONObject(j_arr.getString(i));
										list_label.add(j_obj.getString("label"));
										list_id.add(j_obj.getString("city_id"));
										list_stateid.add(j_obj.getString("state_id"));
										list_countryid.add(j_obj.getString("country_id"));
									}
									adapter.notifyDataSetChanged();
								}
								catch (Exception e) {
									// TODO: handle exception
								}
						   }
						        
						}.execute(url);


						
						apiReq = new ApiRequest(getActivity());
						apiReq.setRequestListener(new RequestListener() {

							@Override
							public void onRequestSuccess(String success_response) {
								// TODO Auto-generated method stub
								System.out.println("Register response: " + success_response);
								prog1.setVisibility(View.GONE);
								list_label.clear();
								list_id.clear();
								list_stateid.clear();
								list_countryid.clear();
								try {
									JSONArray j_arr = new JSONArray(success_response);
									for (int i = 0; i < j_arr.length(); i++) {
										JSONObject j_obj = new JSONObject(j_arr.getString(i));
										list_label.add(j_obj.getString("label"));
										list_id.add(j_obj.getString("city_id"));
										list_stateid.add(j_obj.getString("state_id"));
										list_countryid.add(j_obj.getString("country_id"));
									}
									adapter.notifyDataSetChanged();
								}
								catch (Exception e) {
									// TODO: handle exception
								}
							}

							@Override
							public void onRequestError(String error_response) {
								prog1.setVisibility(View.GONE);
								adapter.notifyDataSetChanged();
								System.out.println("Register error " + error_response);
							}
						});
						List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
						
						

						apiReq.postMethod(getResources().getString(R.string.url_ciudad_auto)+"/"+s.toString(), nameValuePair);
					}
				}
			});

			alert_end.setView(alert_view1);
			alert_end.setPositiveButton("cancelar", null);
			alertDialog = alert_end.create();
			alertDialog.show();
			break;
		case R.id.btn_submit_regresos:
			
			if (TextUtils.isEmpty(et_return_name.getText().toString())) {
				Toast.makeText(getActivity(), "retorno está vacía", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_category.getText().toString())) {
				Toast.makeText(getActivity(), "camión está vacío", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_truck.getText().toString())) {
				Toast.makeText(getActivity(), "camión está vacío", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_regresos_description.getText().toString())) {
				Toast.makeText(getActivity(), "descripción está vacía", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_date_sales.getText().toString())) {
				Toast.makeText(getActivity(), "Fecha de venta está vacía", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_date_arrival.getText().toString())) {
				Toast.makeText(getActivity(), "fecha de llegada está vacía", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_start_route.getText().toString())) {
				Toast.makeText(getActivity(), "itinerario salida está vacía", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_end_route.getText().toString())) {
				Toast.makeText(getActivity(), "ruta final está vacío", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_weight.getText().toString())) {
				Toast.makeText(getActivity(), "el peso está vacío", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_width.getText().toString())) {
				Toast.makeText(getActivity(), "ancho está vacío", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_length.getText().toString())) {
				Toast.makeText(getActivity(), "el largo está vacío", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_height.getText().toString())) {
				Toast.makeText(getActivity(), "alto está vacío", Toast.LENGTH_SHORT).show();
			}
			
			else {
				pd = ProgressDialog.show(getActivity(), "", "Publicando carga", false, false);
				submitRegresos();
				Toast.makeText(getActivity(), "La carga ah sido publicada", Toast.LENGTH_SHORT).show();
			}
			
			break;

		default:
			break;
		}
	}

	
	private void showTrucks(final EditText et_truck) {
		AlertDialog.Builder capacityAlert = new AlertDialog.Builder(getActivity());
		capacityAlert.setTitle("Tipo de camion");
		final String[] capacity = getActivity().getResources().getStringArray(R.array.arr_services);
		capacityAlert.setSingleChoiceItems(capacity, -1, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				et_truck.setText(capacity[which]);
			}
		});
		capacityAlert.setPositiveButton("hecho", null);
		capacityAlert.show();
	}
	
	private void showCategories(final EditText et_category) {
		AlertDialog.Builder capacityAlert = new AlertDialog.Builder(getActivity());
		capacityAlert.setTitle("Categoria");
		final String[] capacity = getActivity().getResources().getStringArray(R.array.arr_categories);
		capacityAlert.setSingleChoiceItems(capacity, -1, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				et_category.setText(capacity[which]);
			}
		});
		capacityAlert.setPositiveButton("hecho", null);
		capacityAlert.show();
	}
	private void showOptions(final EditText et_when) {
		AlertDialog.Builder capacityAlert = new AlertDialog.Builder(getActivity());
		capacityAlert.setTitle("Categoria");
		final String[] capacity = getActivity().getResources().getStringArray(R.array.arr_when);
		capacityAlert.setSingleChoiceItems(capacity, -1, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				et_when.setText(capacity[which]);
			}
		});
		capacityAlert.setPositiveButton("hecho", null);
		capacityAlert.show();
	}

	private void submitRegresos() {
		ApiRequest apiRequest = new ApiRequest(getActivity());
		apiRequest.setRequestListener(new RequestListener() {

			@Override
			public void onRequestSuccess(String success_response) {
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				Log.i("server Response", success_response);
			}

			@Override
			public void onRequestError(String error_response) {
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				Log.i("server Response", error_response);
				Toast.makeText(getActivity(), error_response, Toast.LENGTH_SHORT).show();
			}
		});
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("data[Shipment][title]", et_return_name.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][category_id]",et_category.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][truck_id]", et_truck.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][description]", et_regresos_description.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][prefix_start_id]", "1"));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][start_date]", et_date_sales.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][prefix_end_id]", "1"));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][end_date]", et_date_arrival.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Poute][origen]", et_start_route.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Point][0][city_id]", start_city_id));
		nameValuePair.add(new BasicNameValuePair("data[Point][0][state_id]", start_state_id));
		nameValuePair.add(new BasicNameValuePair("data[Point][0][country_id]", start_country_id));
		nameValuePair.add(new BasicNameValuePair("data[Point][0][street]", et_start_calle.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Point][0][number]", et_start_numero.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Point][0][neighborhood]", et_start_colonia.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("[Route][destino]", et_end_route.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Point][1][city_id]", end_city_id));
		nameValuePair.add(new BasicNameValuePair("data[Point][1][state_id]", end_state_id));
		nameValuePair.add(new BasicNameValuePair("data[Point][1][country_id]",end_country_id));
		nameValuePair.add(new BasicNameValuePair("data[Point][1][street]", et_end_calle.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Point][1][number]", et_end_numero.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Point][1][neighborhood]", et_end_colonia.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][width]",  et_width.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][height]", et_height.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][weight]", et_weight.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Shipment][length]", et_length.getText().toString()));
		String user_id=AppPreference.getString(getActivity(),AppPreference.USER_ID);
		String account_id=AppPreference.getString(getActivity(),AppPreference.ACCOUNT_ID);
		apiRequest.postMethod(getResources().getString(R.string.url_add_shipments)+"/"+account_id+"/"+user_id+getResources().getString(R.string.ext_json), nameValuePair);
	}

	@SuppressLint("ValidFragment")
	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		private EditText et_date_sales;

		public DatePickerFragment(EditText et_date_sales) {
			this.et_date_sales = et_date_sales;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			et_date_sales.setText(year+"-"+(month+1)+"-"+day);
		}
	}
	
	

}
