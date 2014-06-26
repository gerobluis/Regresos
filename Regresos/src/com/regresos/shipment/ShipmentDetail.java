package com.regresos.shipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.regresos.R;
import com.regresos.RegisterTransportistaFragment;

import com.regresos.utils.ApiRequest;
import com.regresos.utils.ApiRequest.RequestListener;
import com.regresos.utils.AppPreference;

public class ShipmentDetail extends Activity {
	private static final String TAG = "Offers";
	
	private ListView shipment_tab_list;
	private ProgressDialog pd;
	private MyListAdapter myListAdapter;
	private ApiRequest shipmentRequest;
	private String id;
	private ArrayList<HashMap<String, String>> list_offers = new ArrayList<HashMap<String, String>>();
	private AlertDialog alertDialog = null;
	@Override	
	public void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shipment_offers);
		getActionBar().setTitle("");
		Bundle extras = getIntent().getExtras();
		id=extras.getString("id", "1");
		loadShipmentOffers();
	}
	
	private void loadShipmentOffers() {
		pd = ProgressDialog.show(ShipmentDetail.this, "", "Loading oferta...", false, false);
		list_offers.clear();
		shipmentRequest = new ApiRequest(ShipmentDetail.this);
		shipmentRequest.setRequestListener(new RequestListener() {
		
			@Override
			public void onRequestSuccess(String success_response) {
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
					
				}
				Log.v(TAG, "Response "+success_response);
				try {
					JSONObject response = new JSONObject(success_response);
					JSONArray comebacks = response.getJSONArray("shipments_deals");
					
					for (int i = 0; i < comebacks.length(); i++) {
						JSONObject obj_comeback = comebacks.getJSONObject(i);
						JSONObject obj_cb = obj_comeback.getJSONObject("Deal");
						
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("id", obj_cb.getString("id"));
						map.put("price", obj_cb.getString("price"));
//						map.put("description", obj_cb.getString("description"));
//						tv_width.setText( obj_cb.getString("width"));
//						tv_height.setText( obj_cb.getString("height"));
//						tv_length.setText( obj_cb.getString("length"));
//						tv_weight.setText(obj_cb.getString("weight"));
						map.put( "truck", new JSONObject(obj_comeback.getString("Truck")).getString("title"));
//						tv_detail_status.setText( obj_comeback.getJSONObject("Status").getString("title"));
					

						
						
//						String url = getResources().getString(R.string.url_photo)+obj_comeback.getJSONArray("Picture").getJSONObject(0).getString("file")
//								+"."+
//								obj_comeback.getJSONArray("Picture").getJSONObject(0).getString("ext");
//						iv_detail.setTag(url);	
//						 new DownloadImageTask().execute(iv_detail);
						
						
//						JSONObject obj_route = obj_comeback.getJSONObject("Route");
//						JSONArray arr_pt = obj_route.getJSONArray("Point");
//						
//						JSONObject obj_pt1 = arr_pt.getJSONObject(0);
//						JSONObject obj_pt2 = arr_pt.getJSONObject(1);
//						
//						map.put("output1_city", new JSONObject(obj_pt1.getString("City")).getString("title"));
//						map.put("output1_state", new JSONObject(obj_pt1.getString("State")).getString("title"));
//						
//						map.put("output2_city", new JSONObject(obj_pt2.getString("City")).getString("title"));
//						map.put("output2_state", new JSONObject(obj_pt2.getString("State")).getString("title"));
//						
//						map.put("start_date", obj_cb.getString("start_date"));
//						map.put("end_date", obj_cb.getString("end_date"));
						
						list_offers.add(map);
						initializeView();
					}
					
					
					
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onRequestError(String error_response) {
				Log.e(TAG, error_response);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				Toast.makeText(ShipmentDetail.this, error_response, Toast.LENGTH_SHORT).show();
			}
		});
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("data['Estatus']['id']", ""+""));
		
		shipmentRequest.postMethod(getResources().getString(R.string.url_shipment_oferts)+id+getResources().getString(R.string.ext_json), nameValuePair);
	}

	private void initializeView() {
		
		myListAdapter = new MyListAdapter(list_offers);
		
		
		shipment_tab_list = (ListView) findViewById(R.id.shipment_offers_tab_list);
		shipment_tab_list.setAdapter(myListAdapter);
		shipment_tab_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
				final AlertDialog.Builder alert = new AlertDialog.Builder(ShipmentDetail.this);
				final View vista= view;
				alert.setCancelable(false);
				alert.setTitle("Aceptas la  Oferta?");

//				View alert_view = LayoutInflater.from(ShipmentDetail.this).inflate(R.layout.login_type_layout, null);
//				final Button btn_dcarga = (Button) alert_view.findViewById(R.id.btn_dcarga);
//				final Button btn_transportista = (Button) alert_view.findViewById(R.id.btn_transportista);
				alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// TODO Auto-generated method stub
						sendOffer(vista.getId());
					}
				});
				alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// TODO Auto-generated method stub
						
					}
				});
//				alert.setView(alert_view);
				
				alertDialog = alert.create();
				alertDialog.show();
			}
		});
		
	}
	private void sendOffer(int id){
		pd = ProgressDialog.show(ShipmentDetail.this, "", "Confirmando...", false, false);
		list_offers.clear();
		shipmentRequest = new ApiRequest(ShipmentDetail.this);
		shipmentRequest.setRequestListener(new RequestListener() {
		
			@Override
			public void onRequestSuccess(String success_response) {
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
					
				}
				finish();
			
				
			}
			
			@Override
			public void onRequestError(String error_response) {
				Log.e(TAG, error_response);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				Toast.makeText(ShipmentDetail.this, error_response, Toast.LENGTH_SHORT).show();
			}
		});
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("data['Estatus']['id']", ""+""));
		
		shipmentRequest.postMethod(getResources().getString(R.string.url_deal_accept)+id+getResources().getString(R.string.ext_json), nameValuePair);
	
	}
	class MyListAdapter extends BaseAdapter {
		private ArrayList<HashMap<String, String>> list_shipment;

		public MyListAdapter(ArrayList<HashMap<String, String>> list_offers) {
			this.list_shipment = list_offers;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return this.list_shipment.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;

			if (convertView == null) {
				viewHolder = new ViewHolder();

				convertView = LayoutInflater.from(ShipmentDetail.this).inflate(R.layout.shipment_offers_list_item, parent, false);
				convertView.setTag(viewHolder);
				convertView.setId(Integer.parseInt(list_shipment.get(position).get("id")));
				viewHolder.tv_shipment_offer = (TextView) convertView.findViewById(R.id.tv_shipment_offer);
				viewHolder.tv_comeback_truck = (TextView) convertView.findViewById(R.id.tv_truck);
				viewHolder.tv_comeback_source = (TextView) convertView.findViewById(R.id.tv_comeback_source);
				viewHolder.tv_comeback_dest = (TextView) convertView.findViewById(R.id.tv_comeback_dest);
				viewHolder.tv_cb_start_date = (TextView) convertView.findViewById(R.id.tv_truck);
				viewHolder.tv_cb_end_date = (TextView) convertView.findViewById(R.id.tv_cb_end_date);
				
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.tv_shipment_offer.setText("Oferta:"+list_shipment.get(position).get("price"));
			viewHolder.tv_comeback_truck.setText(list_shipment.get(position).get("truck"));
//			viewHolder.tv_comeback_source.setText("Salida : "+list_shipment.get(position).get("output1_city")+" "+list_shipment.get(position).get("output1_state"));
//			viewHolder.tv_comeback_dest.setText("Entrega : "+list_shipment.get(position).get("output2_city")+" "+list_shipment.get(position).get("output2_state"));
//			viewHolder.tv_cb_start_date.setText("Salida : "+list_shipment.get(position).get("start_date"));
//			viewHolder.tv_cb_end_date.setText("Entrega : "+list_shipment.get(position).get("end_date"));
			
			return convertView;
		}

	}
	
	static class ViewHolder {
		public TextView tv_shipment_offer, tv_comeback_truck, tv_comeback_source, tv_comeback_dest, tv_cb_start_date, tv_cb_end_date;
	}

}
