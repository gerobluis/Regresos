package com.regresos.comeback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.regresos.R;
import com.regresos.utils.ApiRequest;
import com.regresos.utils.ApiRequest.RequestListener;

public class ComebackTabTwo extends Fragment implements OnClickListener {
	private EditText et_return_name, et_truck, et_regresos_description, et_date_sales, et_date_arrival, et_start_route, et_end_route,
			et_capacity_top, et_capacity_end, et_city_spend;
	private Button btn_submit_regresos;
	private ProgressDialog pd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.comeback_tab_two, null);

		initialize(view);

		return view;
	}

	private void initialize(View view) {
		et_return_name = (EditText) view.findViewById(R.id.et_return_name);
		et_truck = (EditText) view.findViewById(R.id.et_truck);
		et_regresos_description = (EditText) view.findViewById(R.id.et_regresos_description);
		et_date_sales = (EditText) view.findViewById(R.id.et_date_sales);
		et_date_arrival = (EditText) view.findViewById(R.id.et_date_arrival);
		et_start_route = (EditText) view.findViewById(R.id.et_start_route);
		et_end_route = (EditText) view.findViewById(R.id.et_end_route);
		et_capacity_top = (EditText) view.findViewById(R.id.et_capacity_top);
		et_capacity_end = (EditText) view.findViewById(R.id.et_capacity_end);
		et_city_spend = (EditText) view.findViewById(R.id.et_city_spend);
		
		btn_submit_regresos = (Button) view.findViewById(R.id.btn_submit_regresos);
		
//		et_truck.setOnClickListener(this);
		et_date_sales.setOnClickListener(this);
		et_date_arrival.setOnClickListener(this);
//		et_start_route.setOnClickListener(this);
//		et_end_route.setOnClickListener(this);
		et_capacity_top.setOnClickListener(this);
		et_capacity_end.setOnClickListener(this);
//		et_city_spend.setOnClickListener(this);
		btn_submit_regresos.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.et_date_sales:
			
			DialogFragment date_sales = new DatePickerFragment(et_date_sales);
			date_sales.show(getFragmentManager(), "SalesDate");

			break;

		case R.id.et_date_arrival:
			
			DialogFragment date_arrival = new DatePickerFragment(et_date_arrival);
			date_arrival.show(getFragmentManager(), "ArrivalDate");

			break;

		case R.id.et_capacity_top:
			
			showCapacity(et_capacity_top);

			break;

		case R.id.et_capacity_end:

			showCapacity(et_capacity_end);
			
			break;
			
		case R.id.btn_submit_regresos:
			
			if (TextUtils.isEmpty(et_return_name.getText().toString())) {
				Toast.makeText(getActivity(), "retorno está vacía", Toast.LENGTH_SHORT).show();
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
			else if (TextUtils.isEmpty(et_capacity_top.getText().toString())) {
				Toast.makeText(getActivity(), "capacidad está vacía", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_capacity_end.getText().toString())) {
				Toast.makeText(getActivity(), "capacidad está vacía", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(et_city_spend.getText().toString())) {
				Toast.makeText(getActivity(), "el gasto de la ciudad está vacía", Toast.LENGTH_SHORT).show();
			}
			else {
				pd = ProgressDialog.show(getActivity(), "", "tratamiento...", false, false);
				submitRegresos();
			}
			
			break;

		default:
			break;
		}
	}
	
	private void showCapacity(final EditText et_capacity) {
		AlertDialog.Builder capacityAlert = new AlertDialog.Builder(getActivity());
		capacityAlert.setTitle("Capacidad");
		final String[] capacity = getActivity().getResources().getStringArray(R.array.array_capacity);
		capacityAlert.setSingleChoiceItems(capacity, -1, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				et_capacity.setText(capacity[which]);
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
		nameValuePair.add(new BasicNameValuePair("data[Comeback][title]", et_return_name.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Comeback][truck_id]", et_truck.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Comeback][description]", et_regresos_description.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Comeback][prefix_start_id]", "1"));
		nameValuePair.add(new BasicNameValuePair("data[Comeback][start_date_input]", et_date_sales.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Comeback][prefix_end_id]", "1"));
		nameValuePair.add(new BasicNameValuePair("data [Comeback][end_date_input]", et_date_arrival.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Route][origen]", et_start_route.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("[Route][destino]", et_end_route.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[point][0][capacity]", et_capacity_top.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Segment][1][capacity]", et_capacity_end.getText().toString()));
		nameValuePair.add(new BasicNameValuePair("data[Segment][1][title]", et_city_spend.getText().toString()));
		apiRequest.postMethod(getResources().getString(R.string.url_add_regresos), nameValuePair);
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
			et_date_sales.setText(""+(month+1)+"-"+day+"-"+year);
		}
	}

}
