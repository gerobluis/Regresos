package com.regresos.comeback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.regresos.R;
import com.regresos.carriers.CarrierComment;

public class ComebackTabThreeDetail extends Fragment {

	public ComebackTabThreeDetail() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.comeback_tab_three_detail, null);

		return view;
	}

}
