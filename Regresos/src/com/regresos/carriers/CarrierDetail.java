package com.regresos.carriers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.regresos.R;

public class CarrierDetail extends Fragment implements OnClickListener {
	private LinearLayout carrier_tab1, carrier_tab2, carrier_tab3;

	public CarrierDetail() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.carrier_detail_page, null);

		carrier_tab1 = (LinearLayout) view.findViewById(R.id.carrier_tab1);
		carrier_tab1.setOnClickListener(this);

		carrier_tab2 = (LinearLayout) view.findViewById(R.id.carrier_tab2);
		carrier_tab2.setOnClickListener(this);

		carrier_tab3 = (LinearLayout) view.findViewById(R.id.carrier_tab3);
		carrier_tab3.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.carrier_tab1:

			break;

		case R.id.carrier_tab2:

			break;

		case R.id.carrier_tab3:
			
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			CarrierComment carrierComment = new CarrierComment();
			ft.addToBackStack(null);
			ft.replace(R.id.fragment_container, carrierComment).commit();

			break;
		}
	}

}
