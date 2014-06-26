package com.regresos.comeback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.regresos.R;
import com.regresos.comeback.ComebackTabOne.MyListAdapter;
import com.regresos.comeback.ComebackTabOne.ViewHolder;
import com.regresos.utils.ApiRequest;
import com.regresos.utils.AppPreference;
import com.regresos.utils.ApiRequest.RequestListener;

public class CargasUtiles extends Fragment {
	private static final String TAG = "CargasUtiles";
	private ListView lv_cargas_utils;
	private ProgressDialog pd;
	private MyListAdapter myListAdapter;
	private ApiRequest comebackRequest;
	private ArrayList<HashMap<String, String>> list_cargas = new ArrayList<HashMap<String, String>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.comeback_cargas_utils, null);
		
		myListAdapter = new MyListAdapter(list_cargas);
		
		lv_cargas_utils = (ListView) view.findViewById(R.id.lv_cargas_utils);
		lv_cargas_utils.setAdapter(myListAdapter);
		lv_cargas_utils.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
			}
		});
		loadComeback();
		
		return view;
	}
	
	private void loadComeback() {
		pd = ProgressDialog.show(getActivity(), "", "Loading...", false, false);
		comebackRequest = new ApiRequest(getActivity());
		comebackRequest.setRequestListener(new RequestListener() {
			
			@Override
			public void onRequestSuccess(String success_response) {
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				Log.v(TAG, "Response "+success_response);
				try {
					JSONObject response = new JSONObject(success_response);
					JSONArray comebacks = response.getJSONArray("shipment");
					
					for (int i = 0; i < comebacks.length(); i++) {
						JSONObject obj_comeback = comebacks.getJSONObject(i);
						JSONObject obj_cb = obj_comeback.getJSONObject("Shipment");
						
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("id", obj_cb.getString("id"));
						map.put("title", obj_cb.getString("title"));
						map.put("description", obj_cb.getString("description"));
						map.put( "truck", new JSONObject(obj_comeback.getString("Truck")).getString("title"));
						
						JSONObject obj_route = obj_comeback.getJSONObject("Route");
						JSONArray arr_pt = obj_route.getJSONArray("Point");
						
						JSONObject obj_pt1 = arr_pt.getJSONObject(0);
						JSONObject obj_pt2 = arr_pt.getJSONObject(1);
						
						map.put("output1_city", new JSONObject(obj_pt1.getString("City")).getString("title"));
						map.put("output1_state", new JSONObject(obj_pt1.getString("State")).getString("title"));
						
						map.put("output2_city", new JSONObject(obj_pt2.getString("City")).getString("title"));
						map.put("output2_state", new JSONObject(obj_pt2.getString("State")).getString("title"));
						
						map.put("start_date", obj_cb.getString("start_date"));
						map.put("end_date", obj_cb.getString("end_date"));
						
						list_cargas.add(map);
					}
					myListAdapter.notifyDataSetChanged();
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
				Toast.makeText(getActivity(), error_response, Toast.LENGTH_SHORT).show();
			}
		});
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("data[Account][id]", AppPreference.getString(getActivity(), AppPreference.ACCOUNT_ID)));
		nameValuePair.add(new BasicNameValuePair("data[User][0][id]", AppPreference.getString(getActivity(), AppPreference.USER_ID)));
		nameValuePair.add(new BasicNameValuePair("data[Address][state_id]", AppPreference.getString(getActivity(), AppPreference.STATE_ID)));
		comebackRequest.postMethod(getResources().getString(R.string.url_cargas_utils), nameValuePair);
	}
	
	class MyListAdapter extends BaseAdapter {
		private ArrayList<HashMap<String, String>> list_cargas;

		public MyListAdapter(ArrayList<HashMap<String, String>> list_cargas) {
			this.list_cargas = list_cargas;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_cargas.size();
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

				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.cargas_list_item, parent, false);
				convertView.setTag(viewHolder);
				
				viewHolder.tv_carga_title = (TextView) convertView.findViewById(R.id.welcome2);
				viewHolder.tv_carga_truck = (TextView) convertView.findViewById(R.id.tv_carga_truck);
				viewHolder.tv_carga_source = (TextView) convertView.findViewById(R.id.tv_carga_source);
				viewHolder.tv_carga_dest = (TextView) convertView.findViewById(R.id.tv_carga_dest);
				viewHolder.tv_carga_start_date = (TextView) convertView.findViewById(R.id.tv_carga_start_date);
				viewHolder.tv_carga_end_date = (TextView) convertView.findViewById(R.id.tv_carga_end_date);
				
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.tv_carga_title.setText(list_cargas.get(position).get("title"));
			viewHolder.tv_carga_truck.setText(list_cargas.get(position).get("truck"));
			viewHolder.tv_carga_source.setText("Salida : "+list_cargas.get(position).get("output1_city"));
			viewHolder.tv_carga_dest.setText("Entrega : "+list_cargas.get(position).get("output2_city"));
			viewHolder.tv_carga_start_date.setText("Salida : "+list_cargas.get(position).get("start_date"));
			viewHolder.tv_carga_end_date.setText("Entrega : "+list_cargas.get(position).get("end_date"));
			return convertView;
		}

	}
	
	static class ViewHolder {
		public TextView tv_carga_title, tv_carga_truck, tv_carga_source, tv_carga_dest, tv_carga_start_date, tv_carga_end_date;
	}

}
