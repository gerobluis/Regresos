package com.regresos.comeback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.regresos.R;

public class MyComebacks extends Fragment implements OnClickListener {
	private LinearLayout tab1, tab2, tab3;
	private FragmentManager fm;
	private FragmentTransaction ft;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.comeback_dashboard, null);

		fm = getFragmentManager();
		
		tab1 = (LinearLayout) view.findViewById(R.id.tab1);
		tab1.setOnClickListener(this);
		tab2 = (LinearLayout) view.findViewById(R.id.tab2);
		tab2.setOnClickListener(this);
		tab3 = (LinearLayout) view.findViewById(R.id.tab3);
		tab3.setOnClickListener(this);
		tab1.performClick();
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab1:

			if (!tab1.isSelected())
				tab1.setSelected(true);
			if (tab2.isSelected())
				tab2.setSelected(false);
			if (tab3.isSelected())
				tab3.setSelected(false);
			
			ft = fm.beginTransaction();
			ft.replace(R.id.comeback_tab_container, new ComebackTabOne()).commit();

			break;

		case R.id.tab2:

			if (!tab2.isSelected())
				tab2.setSelected(true);
			if (tab1.isSelected())
				tab1.setSelected(false);
			if (tab3.isSelected())
				tab3.setSelected(false);
			
			ft = fm.beginTransaction();
			ft.replace(R.id.comeback_tab_container, new ComebackTabTwo()).commit();

			break;

		case R.id.tab3:

			if (!tab3.isSelected())
				tab3.setSelected(true);
			if (tab1.isSelected())
				tab1.setSelected(false);
			if (tab2.isSelected())
				tab2.setSelected(false);
			
			ft = fm.beginTransaction();
			ft.replace(R.id.comeback_tab_container, new ComebackTabThree()).commit();

			break;

		default:
			break;
		}
	}
}
