package com.regresos.comeback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.regresos.R;
import com.regresos.carriers.CarrierComment;

public class ComebackTabOneDetail extends Fragment implements OnClickListener {
	private LinearLayout comeback_one_tab1, comeback_one_tab2, comeback_one_tab3, comeback_one_tab4;
	private TextView tv_detail_title, tv_origin, tv_destination, tv_date1, tv_date2, tv_desc;
	private FragmentTransaction ft;

	public ComebackTabOneDetail() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.comeback_tab_one_detail, null);

		initializeView(view);

		return view;
	}

	private void initializeView(View view) {
		ft = getFragmentManager().beginTransaction();
		
		
		tv_detail_title = (TextView) view.findViewById(R.id.tv_detail_title);
		tv_detail_title.setText(getArguments().getString("title"));
		
		tv_origin = (TextView) view.findViewById(R.id.tv_origin);
		tv_origin.setText("Origen : "+getArguments().getString("origin"));
		
		tv_destination = (TextView) view.findViewById(R.id.tv_destination);
		tv_destination.setText("Destino : "+getArguments().getString("destination"));
		
		tv_date1 = (TextView) view.findViewById(R.id.tv_date1);
		tv_date1.setText("Fecha de Salida : "+getArguments().getString("start_date"));
		
		tv_date2 = (TextView) view.findViewById(R.id.tv_date2);
		tv_date2.setText("Fecha de Llegeda : "+getArguments().getString("end_date"));
		
		tv_desc = (TextView) view.findViewById(R.id.tv_desc);
		tv_desc.setText(getArguments().getString("description"));
		
		view.findViewById(R.id.comeback_options).setVisibility(view.INVISIBLE);
		
		comeback_one_tab1 = (LinearLayout) view.findViewById(R.id.comeback_one_tab1);
		comeback_one_tab1.setOnClickListener(this);

		comeback_one_tab2 = (LinearLayout) view.findViewById(R.id.comeback_one_tab2);
		comeback_one_tab2.setOnClickListener(this);

		comeback_one_tab3 = (LinearLayout) view.findViewById(R.id.comeback_one_tab3);
		comeback_one_tab3.setOnClickListener(this);

		comeback_one_tab4 = (LinearLayout) view.findViewById(R.id.comeback_one_tab4);
		comeback_one_tab4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comeback_one_tab1:

			break;

		case R.id.comeback_one_tab2:

			ft = getFragmentManager().beginTransaction();
			CargasUtiles cargasUtiles = new CargasUtiles();
			ft.addToBackStack(null);
			ft.replace(R.id.fragment_container, cargasUtiles).commit();

			break;

		case R.id.comeback_one_tab3:

			ft = getFragmentManager().beginTransaction();
			Preguntas preguntas = new Preguntas();
			ft.addToBackStack(null);
			ft.replace(R.id.fragment_container, preguntas).commit();

			break;

		case R.id.comeback_one_tab4:

			ft = getFragmentManager().beginTransaction();
			Solicitudes solicitudes = new Solicitudes();
			ft.addToBackStack(null);
			ft.replace(R.id.fragment_container, solicitudes).commit();

			break;
		}
	}

}
