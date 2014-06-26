package com.regresos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.regresos.carriers.MyCarriers;
import com.regresos.comeback.MyComebacks;
import com.regresos.negotiation.MyNegotiation;
import com.regresos.shipment.MyShipments;
import com.regresos.utils.AppPreference;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DashboardActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private LinearLayout left_drawer;
	private ListView list_menu;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private FragmentTransaction fragTrans;
	private FragmentManager fragMan;
	OnItemClickListener transportistaListener,dcargaListener;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_activity);

		fragMan = getSupportFragmentManager();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		left_drawer = (LinearLayout) findViewById(R.id.left_drawer);
		list_menu = (ListView) findViewById(R.id.list_menu);
		
		

	
		//CAMBIAR LIST MENU PARA DUEÑO DE CARGA Y PARA TRANSPORTISTA
		if(AppPreference.getString(this, AppPreference.GROUP_ID)==getResources().getString(R.string.group_id_transportista))
		{
			
			ProfileTransportista myProfile = new ProfileTransportista();
			Bundle bundle = new Bundle();
			bundle.putBoolean("show_dialog", getIntent().getBooleanExtra("show_dialog", false));
			myProfile.setArguments(bundle);
			fragTrans = fragMan.beginTransaction();
			fragTrans.replace(R.id.fragment_container, myProfile).commit();
			list_menu.setAdapter(new MenuListTransportistaAdapter());
			list_menu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					switch (arg2) {
					case 0:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyProfile myProfile = new MyProfile();
						Bundle bundle = new Bundle();
						bundle.putBoolean("show_dialog", getIntent().getBooleanExtra("show_dialog", false));
						myProfile.setArguments(bundle);
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myProfile).commit();
						
						break;
					case 1:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyComebacks myComebacks = new MyComebacks();
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myComebacks).commit();
						
						break;
					case 2:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyCarriers myCarrier = new MyCarriers();
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myCarrier).commit();
						
						break;
					case 4:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyNegotiation myNegotiation = new MyNegotiation();
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myNegotiation).commit();
						
						break;

					default:
						break;
					}
				}
			});
		}		
		else if(AppPreference.getString(this, AppPreference.GROUP_ID).equals(getResources().getString(R.string.group_id_dcarga)))
		{
			MyProfile myProfile = new MyProfile();
			Bundle bundle = new Bundle();
			bundle.putBoolean("show_dialog", getIntent().getBooleanExtra("show_dialog", false));
			myProfile.setArguments(bundle);
			fragTrans = fragMan.beginTransaction();
			fragTrans.replace(R.id.fragment_container, myProfile).commit();
			list_menu.setAdapter(new MenuListDcargaAdapter());
			list_menu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					switch (arg2) {
					case 0:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyProfile myProfile = new MyProfile();
						Bundle bundle = new Bundle();
						bundle.putBoolean("show_dialog", getIntent().getBooleanExtra("show_dialog", false));
						myProfile.setArguments(bundle);
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myProfile).commit();
						
						break;
					case 1:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyShipments myShipments = new MyShipments();
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myShipments).commit();
						
						break;
					case 2:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyCarriers myCarriers = new MyCarriers();
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myCarriers).commit();
						
						
						break;
					case 4:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyNegotiation myNegotiation = new MyNegotiation();
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myNegotiation).commit();
						
						break;

					default:
						break;
					}
				}
			});
		}
		else if(AppPreference.getString(this, AppPreference.GROUP_ID).equals(getResources().getString(R.string.group_id_logistico)))
		{
			ProfileTransportista myProfile = new ProfileTransportista();
			Bundle bundle = new Bundle();
			bundle.putBoolean("show_dialog", getIntent().getBooleanExtra("show_dialog", false));
			myProfile.setArguments(bundle);
			fragTrans = fragMan.beginTransaction();
			fragTrans.replace(R.id.fragment_container, myProfile).commit();
			list_menu.setAdapter(new MenuListLogisticoAdapter());
			list_menu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					switch (arg2) {
					case 0:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyProfile myProfile = new MyProfile();
						Bundle bundle = new Bundle();
						bundle.putBoolean("show_dialog", getIntent().getBooleanExtra("show_dialog", false));
						myProfile.setArguments(bundle);
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myProfile).commit();
						
						break;
					case 1:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyShipments myShipments = new MyShipments();
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myShipments).commit();
						
						break;
					case 2:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyCarriers myCarriers = new MyCarriers();
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myCarriers).commit();
						
						
						break;
					case 4:
						mDrawerLayout.closeDrawer(left_drawer);
						
						MyNegotiation myNegotiation = new MyNegotiation();
						fragTrans = fragMan.beginTransaction();
						fragTrans.replace(R.id.fragment_container, myNegotiation).commit();
						break;

					default:
						break;
					}
				}
			});
		}
		
		
		// transportistaListener=
		//dcargaListener=
		actionBarDrawerToggle = new ActionBarDrawerToggle(DashboardActivity.this, mDrawerLayout, R.drawable.ic_navigation_drawer, R.string.app_name,
				R.string.app_name);

		mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

		getActionBar().setTitle("");
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		actionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns
		// true
		// then it has handled the app icon touch event

		if (item != null && item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
				mDrawerLayout.closeDrawer(left_drawer);
			}
			else {
				mDrawerLayout.openDrawer(left_drawer);
			}
		}
		return false;
	}

	class MenuListDcargaAdapter extends BaseAdapter {
		/*private int[] list_icon = { R.drawable.perfil, R.drawable.miscargas, R.drawable.negociaciones, R.drawable.mensajes, R.drawable.directorio,
				R.drawable.certificate, R.drawable.anunciate,R.drawable.contactanos, R.drawable.ajustes };*/
		private int[] list_icon = { R.drawable.perfil, R.drawable.miscargas};
		private String[] list_menu = getResources().getStringArray(R.array.arr_drawer_menu_dcarga);

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_icon.length;
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

				convertView = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.menu_list_item, parent, false);
				convertView.setTag(viewHolder);

				viewHolder.im_menu_item = (ImageView) convertView.findViewById(R.id.im_menu_item);
				viewHolder.tv_menu_item = (TextView) convertView.findViewById(R.id.tv_menu_item);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.im_menu_item.setImageResource(list_icon[position]);
			viewHolder.tv_menu_item.setText(list_menu[position]);
			return convertView;
		}

	}
	class MenuListTransportistaAdapter extends BaseAdapter {
		/*private int[] list_icon = { R.drawable.perfil, R.drawable.regresos, R.drawable.negociaciones, R.drawable.mensajes, R.drawable.directorio,
				R.drawable.certificate, R.drawable.anunciate, R.drawable.ajustes };*/
		private int[] list_icon = { R.drawable.perfil, R.drawable.regresos };
		private String[] list_menu = getResources().getStringArray(R.array.arr_drawer_menu_transportista);

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_icon.length;
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

				convertView = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.menu_list_item, parent, false);
				convertView.setTag(viewHolder);

				viewHolder.im_menu_item = (ImageView) convertView.findViewById(R.id.im_menu_item);
				viewHolder.tv_menu_item = (TextView) convertView.findViewById(R.id.tv_menu_item);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.im_menu_item.setImageResource(list_icon[position]);
			viewHolder.tv_menu_item.setText(list_menu[position]);
			return convertView;
		}

	}

	class MenuListLogisticoAdapter extends BaseAdapter {
		/*private int[] list_icon = { R.drawable.perfil, R.drawable.regresos, R.drawable.negociaciones, R.drawable.mensajes, R.drawable.directorio,
				R.drawable.certificate, R.drawable.anunciate, R.drawable.ajustes };*/
		private int[] list_icon = { R.drawable.perfil, R.drawable.regresos, R.drawable.miscargas };
		private String[] list_menu = getResources().getStringArray(R.array.arr_drawer_menu_logistico);

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_icon.length;
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

				convertView = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.menu_list_item, parent, false);
				convertView.setTag(viewHolder);

				viewHolder.im_menu_item = (ImageView) convertView.findViewById(R.id.im_menu_item);
				viewHolder.tv_menu_item = (TextView) convertView.findViewById(R.id.tv_menu_item);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.im_menu_item.setImageResource(list_icon[position]);
			viewHolder.tv_menu_item.setText(list_menu[position]);
			return convertView;
		}

	}
	
	static class ViewHolder {
		public TextView tv_menu_name;
		public ImageView im_menu_item;
		public TextView tv_menu_item;
	}

}