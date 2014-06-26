package com.regresos.negotiation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.regresos.R;

public class MyNegotiation extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.comeback_tab_one, null);
		ListView listView = (ListView) view.findViewById(R.id.comeback_tab_list);
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

				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.nego_list_item, parent, false);

			}
			else {
			}
			return convertView;
		}

	}

}
