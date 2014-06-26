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
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.regresos.R;
import com.regresos.utils.ApiRequest;
import com.regresos.utils.ApiRequest.RequestListener;

public class ComebackTabOne extends Fragment {
	private static final String TAG = "ComebackTabOnce";
	private Spinner spn_comeback_cat;
	private ListView comeback_tab_list;
	private ProgressDialog pd;
	private MyListAdapter myListAdapter;
	private ApiRequest comebackRequest;
	private ArrayList<HashMap<String, String>> list_comeback = new ArrayList<HashMap<String, String>>();

	@Override	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.comeback_tab_one, null);
		
		initializeView(view);
		
		return view;
	}
	
	private void loadComeback(int spn_select) {
		pd = ProgressDialog.show(getActivity(), "", "Loading Mis Regresos...", false, false);
		list_comeback.clear();
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
					JSONArray comebacks = response.getJSONArray("comebacks");
					
					for (int i = 0; i < comebacks.length(); i++) {
						JSONObject obj_comeback = comebacks.getJSONObject(i);
						JSONObject obj_cb = obj_comeback.getJSONObject("Comeback");
						
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("id", obj_cb.getString("id"));
						map.put("comeback_title", obj_cb.getString("title"));
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
						
						list_comeback.add(map);
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
		nameValuePair.add(new BasicNameValuePair("data['Estatus']['id']", ""+spn_select));
		comebackRequest.postMethod(getResources().getString(R.string.url_comeback), nameValuePair);
	}

	private void initializeView(View view) {
		
		myListAdapter = new MyListAdapter(list_comeback);
		spn_comeback_cat = (Spinner) view.findViewById(R.id.spn_comeback_cat);
		spn_comeback_cat.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int spn_select = 0;
				switch (arg2) {
				case 0:
					spn_select = 5;
					break;
				case 1:
					spn_select = 8;
					break;
				case 2:
					spn_select = 9;
					break;

				default:
					break;
				}
				loadComeback(spn_select);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		comeback_tab_list = (ListView) view.findViewById(R.id.comeback_tab_list);
		comeback_tab_list.setAdapter(myListAdapter);
		comeback_tab_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ComebackTabOneDetail comebackDetail = new ComebackTabOneDetail();
				
				Bundle bundle = new Bundle();
				bundle.putString("title", list_comeback.get(arg2).get("comeback_title"));
				bundle.putString("origin", list_comeback.get(arg2).get("output1_city")+" "+list_comeback.get(arg2).get("output1_state"));
				bundle.putString("destination", list_comeback.get(arg2).get("output2_city")+" "+list_comeback.get(arg2).get("output2_state"));
				bundle.putString("start_date", list_comeback.get(arg2).get("start_date"));
				bundle.putString("end_date", list_comeback.get(arg2).get("end_date"));
				bundle.putString("description", list_comeback.get(arg2).get("description"));
				comebackDetail.setArguments(bundle);
				
				ft.addToBackStack("ComebackTabOne");
				ft.replace(R.id.fragment_container, comebackDetail).commit();
			}
		});
		
	}

	class MyListAdapter extends BaseAdapter {
		private ArrayList<HashMap<String, String>> list_comeback;

		public MyListAdapter(ArrayList<HashMap<String, String>> list_comeback) {
			this.list_comeback = list_comeback;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return this.list_comeback.size();
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

				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.comeback_tab_one_list_item, parent, false);
				convertView.setTag(viewHolder);
				
				viewHolder.tv_comeback_title = (TextView) convertView.findViewById(R.id.tv_comeback_title);
				viewHolder.tv_comeback_truck = (TextView) convertView.findViewById(R.id.tv_comeback_truck);
				viewHolder.tv_comeback_source = (TextView) convertView.findViewById(R.id.tv_comeback_source);
				viewHolder.tv_comeback_dest = (TextView) convertView.findViewById(R.id.tv_comeback_dest);
				viewHolder.tv_cb_start_date = (TextView) convertView.findViewById(R.id.tv_truck);
				viewHolder.tv_cb_end_date = (TextView) convertView.findViewById(R.id.tv_cb_end_date);
				
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.tv_comeback_title.setText(list_comeback.get(position).get("comeback_title"));
			viewHolder.tv_comeback_truck.setText(list_comeback.get(position).get("truck"));
			viewHolder.tv_comeback_source.setText("Salida : "+list_comeback.get(position).get("output1_city")+" "+list_comeback.get(position).get("output1_state"));
			viewHolder.tv_comeback_dest.setText("Entrega : "+list_comeback.get(position).get("output2_city")+" "+list_comeback.get(position).get("output2_state"));
			viewHolder.tv_cb_start_date.setText("Salida : "+list_comeback.get(position).get("start_date"));
			viewHolder.tv_cb_end_date.setText("Entrega : "+list_comeback.get(position).get("end_date"));
			
			return convertView;
		}

	}
	
	static class ViewHolder {
		public TextView tv_comeback_title, tv_comeback_truck, tv_comeback_source, tv_comeback_dest, tv_cb_start_date, tv_cb_end_date;
	}

}
