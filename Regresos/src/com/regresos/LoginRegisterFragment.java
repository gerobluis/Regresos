package com.regresos;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewSwitcher.ViewFactory;

public class LoginRegisterFragment extends Fragment implements OnClickListener {
	private ImageSwitcher image;
	private TextView homeText;
	private Button btn_login, btn_register;
	private int[] img_res = { R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4 };
	private String[] home_text = null;
	private int a = 0;
	private FragmentTransaction fragTrans;
	AlertDialog.Builder alert = null;
	private AlertDialog alertDialog = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.login_register_fragment, container, false);

		fragTrans = getFragmentManager().beginTransaction();
		fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

		home_text = getActivity().getResources().getStringArray(R.array.home_text);

		image = (ImageSwitcher) view.findViewById(R.id.bg_image);
		image.setInAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
		image.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
		image.setFactory(new ViewFactory() {

			@Override
			public View makeView() {
				ImageView imageView = new ImageView(getActivity());
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);

				LayoutParams params = new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

				imageView.setLayoutParams(params);
				return imageView;
			}
		});

		homeText = (TextView) view.findViewById(R.id.homeText);
		homeText.setText(home_text[0]);

		btn_login = (Button) view.findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		btn_register = (Button) view.findViewById(R.id.btn_register);
		btn_register.setOnClickListener(this);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		handler.sendEmptyMessageDelayed(1, 500);
	}

	@Override
	public void onPause() {
		super.onPause();
		handler.removeCallbacksAndMessages(null);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			image.setImageResource(img_res[a]);

			a++;
			if (a > img_res.length - 1) {
				a = 0;
			}
			handler.sendEmptyMessageDelayed(1, 3000);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			
			LoginFragment loginFragment = new LoginFragment();
			//loginFragment.setArguments(args);
			fragTrans.replace(R.id.login_register_container, loginFragment, "Login");
			fragTrans.addToBackStack(null);
			fragTrans.commit();
			
			
//			startActivity(new Intent(getActivity(), DashboardActivity.class));

			break;

		case R.id.btn_register:
			final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setCancelable(false);
			alert.setTitle("Eres transportista o dueño de carga");

			View alert_view = LayoutInflater.from(getActivity()).inflate(R.layout.login_type_layout, null);
			final Button btn_dcarga = (Button) alert_view.findViewById(R.id.btn_dcarga);
			final Button btn_transportista = (Button) alert_view.findViewById(R.id.btn_transportista);
			final Button btn_logistico = (Button) alert_view.findViewById(R.id.btn_logistico);
			btn_dcarga.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					RegisterFragmentCarga registerFragment = new RegisterFragmentCarga();
					fragTrans.replace(R.id.login_register_container, registerFragment, "Register");
					fragTrans.addToBackStack(null);
					fragTrans.commit();
					alertDialog.dismiss();
				}
			});
			btn_transportista.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					RegisterFragment registerFragment = new RegisterFragment();
					fragTrans.replace(R.id.login_register_container, registerFragment, "Register");
					fragTrans.addToBackStack(null);
					fragTrans.commit();
					alertDialog.dismiss();
				}
			});
             btn_logistico.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					RegisterFragmentLogistico registerFragment = new RegisterFragmentLogistico();
					fragTrans.replace(R.id.login_register_container, registerFragment, "Register");
					fragTrans.addToBackStack(null);
					fragTrans.commit();
					alertDialog.dismiss();
				}
			});
			alert.setView(alert_view);
			alert.setPositiveButton("cancelar", null);
			alertDialog = alert.create();
			alertDialog.show();
			

			break;

		default:
			break;
		}
	}

}
