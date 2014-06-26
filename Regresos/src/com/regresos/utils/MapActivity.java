package com.regresos.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.regresos.R;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MapActivity extends Activity  implements 
GooglePlayServicesClient.ConnectionCallbacks, 
GooglePlayServicesClient.OnConnectionFailedListener{
	private MapFragment mapFragment;
	private GoogleMap mMap;
	String origen,destino;
	@Override
	public void onStart() {
	
		super.onStart();
	
		  mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
		  if(mapFragment!=null)
		    mMap = mapFragment.getMap();
		 String split[]=destino.split(":");
		 destino=split[1];
		 split=origen.split(":");
		 origen=split[1];
		if(Geocoder.isPresent()){
		    try {
		    	
		        Geocoder gc = new Geocoder(this);
		        List<Address> addresses= gc.getFromLocationName(origen, 5); // get the found Address Objects

		        List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
		        for(Address a : addresses){
		            if(a.hasLatitude() && a.hasLongitude()){
		                ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
		            }  
		        }
		          addresses= gc.getFromLocationName(destino, 5); // get the found Address Objects
		            for(Address a1 : addresses){
			            if(a1.hasLatitude() && a1.hasLongitude()){
			                ll.add(new LatLng(a1.getLatitude(), a1.getLongitude()));
			            } 
		            
		            mMap.addMarker(new MarkerOptions()
		            .position(new LatLng(ll.get(0).latitude, ll.get(0).longitude)));
		            mMap.addMarker(new MarkerOptions()
		            .position(new LatLng(ll.get(1).latitude, ll.get(1).longitude)));
		            mMap.addPolyline(new PolylineOptions().add(new LatLng(ll.get(0).latitude, ll.get(0).longitude),new LatLng(ll.get(1).latitude, ll.get(1).longitude)));
		            LatLngBounds.Builder builder = new LatLngBounds.Builder();
		          
		                builder.include(new LatLng(ll.get(0).latitude, ll.get(0).longitude));
		                builder.include(new LatLng(ll.get(1).latitude, ll.get(1).longitude));
		            LatLngBounds bounds = builder.build();
		            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,500,500, 1);
		            mMap.animateCamera(cu);
		            
		            }  
		    } catch (IOException e) {
		         // handle the exception
		    }
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_map);
		Bundle extras = getIntent().getExtras();
		origen=extras.getString("origen");
		destino=extras.getString("destino");
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	

	
	
}
