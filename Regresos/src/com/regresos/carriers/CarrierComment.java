package com.regresos.carriers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.regresos.R;

public class CarrierComment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.carrier_comment, null);
		
		ListView listView = (ListView) view.findViewById(R.id.lv_carrier_comment);
		listView.setAdapter(new MyListAdapter());
		
		return view;
	}
	
	class MyListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
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
			if (convertView == null) {

				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.carrier_comment_list_item, parent, false);

			}
			else {
			}
			return convertView;
		}

	}

}
