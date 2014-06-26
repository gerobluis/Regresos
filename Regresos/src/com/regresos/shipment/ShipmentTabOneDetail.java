package com.regresos.shipment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.regresos.R;
import com.regresos.carriers.CarrierComment;
import com.regresos.shipment.ShipmentTabOne.MyListAdapter;
import com.regresos.utils.ApiRequest;
import com.regresos.utils.AppPreference;
import com.regresos.utils.ApiRequest.RequestListener;

public class ShipmentTabOneDetail extends Fragment implements OnClickListener {
	private static final String TAG = "ShipmentTabOneDetail";

	
	private TextView tv_detail_title, tv_origin,
	tv_destination, tv_date1, tv_date2,
	tv_desc,tv_detail_status,
	tv_weight,tv_height,tv_length,tv_width;
	private ImageView iv_detail;
	private FragmentTransaction ft;
	private ApiRequest shipmentRequest;
	private ArrayList<HashMap<String, String>> list_shipment = new ArrayList<HashMap<String, String>>();
	private ProgressDialog pd;
	private Button btn_see_oferts;
	private String id;
	public ShipmentTabOneDetail() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shipment_tab_one_detail, null);

		initializeView(view);
		btn_see_oferts=(Button)view.findViewById(R.id.btn_ver_ofertas);
		
		btn_see_oferts.setOnClickListener(l);
		return view;
	}

	private void initializeView(View view) {
		ft = getFragmentManager().beginTransaction();
		
		loadShipment();
		tv_detail_title = (TextView) view.findViewById(R.id.tv_detail_title);
		tv_detail_title.setText(getArguments().getString("title"));
		tv_detail_status = (TextView) view.findViewById(R.id.lbl_status);
		tv_weight=(TextView) view.findViewById(R.id.lbl_peso);
		tv_width=(TextView) view.findViewById(R.id.lbl_ancho);
		tv_height=(TextView) view.findViewById(R.id.lbl_alto);
		tv_length=(TextView) view.findViewById(R.id.lbl_largo);
		tv_origin = (TextView) view.findViewById(R.id.tv_origin);
		iv_detail = (ImageView) view.findViewById(R.id.iv_detail);
		tv_origin.setText("Origen : "+getArguments().getString("origin"));
		
		tv_destination = (TextView) view.findViewById(R.id.tv_destination);
		tv_destination.setText("Destino : "+getArguments().getString("destination"));
		
		tv_date1 = (TextView) view.findViewById(R.id.tv_date1);
		tv_date1.setText("Fecha de Salida : "+getArguments().getString("start_date"));
		
		tv_date2 = (TextView) view.findViewById(R.id.tv_date2);
		tv_date2.setText("Fecha de Llegeda : "+getArguments().getString("end_date"));
		
		tv_desc = (TextView) view.findViewById(R.id.tv_desc);
		tv_desc.setText(getArguments().getString("description"));
		
		
	}
	private void loadShipment() {
		pd = ProgressDialog.show(getActivity(), "", "Cargando", false, false);
		list_shipment.clear();
		shipmentRequest = new ApiRequest(getActivity());
		shipmentRequest.setRequestListener(new RequestListener() {
		
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
						id= obj_cb.getString("id");
						map.put("comeback_title", obj_cb.getString("title"));
						map.put("description", obj_cb.getString("description"));
						tv_width.setText( obj_cb.getString("width"));
						tv_height.setText( obj_cb.getString("height"));
						tv_length.setText( obj_cb.getString("length"));
						tv_weight.setText(obj_cb.getString("weight"));
//						map.put( "truck", new JSONObject(obj_comeback.getString("Truck")).getString("title"));
						tv_detail_status.setText( obj_comeback.getJSONObject("Status").getString("title"));
					

						
						
						String url = getResources().getString(R.string.url_photo)+obj_comeback.getJSONArray("Picture").getJSONObject(0).getString("file")
								+"."+
								obj_comeback.getJSONArray("Picture").getJSONObject(0).getString("ext");
						iv_detail.setTag(url);	
						 new DownloadImageTask().execute(iv_detail);
						
						
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
						
						list_shipment.add(map);
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
				Toast.makeText(getActivity(), error_response, Toast.LENGTH_SHORT).show();
			}
		});
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("data['Estatus']['id']", ""+""));
		
		shipmentRequest.postMethod(getResources().getString(R.string.url_shipments_detail)+getArguments().getString("id")+getResources().getString(R.string.ext_json), nameValuePair);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	OnClickListener l =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getActivity(),ShipmentDetail.class);
			i.putExtra("id", id);
			startActivity(i);
			
		}
	};

	private class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap> {

		ImageView imageView = null;

		@Override
		protected Bitmap doInBackground(ImageView... imageViews) {
		    this.imageView = imageViews[0];
		    try {
				return download_Image((String)imageView.getTag());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
		    imageView.setImageBitmap(result);
		}


			private Bitmap download_Image(String url) throws IOException {
				URL urlg = new URL(url);		
				Bitmap bmp = BitmapFactory.decodeStream(urlg.openConnection().getInputStream());
				return bmp;
			}
		}
	
}


