package com.regresos.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

public class ApiRequest {
	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpResponse httpResponse;
	private InputStream stream = null;
	private RequestListener requestListener;
	private ConnectivityManager connectivityManager;
	private NetworkInfo networkInfo;
	private StringBuilder sb;
	private String error_response;

	public ApiRequest(Context context) {
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getActiveNetworkInfo();
	}

	public interface RequestListener {
		public void onRequestError(String error_response);

		public void onRequestSuccess(String success_response);
	}

	public void setRequestListener(RequestListener requestListener) {
		this.requestListener = requestListener;
	}

	public synchronized void postMethod(final String url, final List<NameValuePair> params) {
		if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnectedOrConnecting()) {
			new Thread() {
				public void run() {
					HttpParams httpParameters = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
					HttpConnectionParams.setSoTimeout(httpParameters, 30000);
					HttpProtocolParams.setContentCharset(httpParameters, HTTP.UTF_8);
					HttpProtocolParams.setHttpElementCharset(httpParameters, HTTP.UTF_8);

					httpClient = new DefaultHttpClient(httpParameters);
//					httpClient.getParams().setParameter("http.protocol.content-charset", HTTP.UTF_8);
					httpPost = new HttpPost(url);
					try {
						httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
						httpResponse = httpClient.execute(httpPost);

						HttpEntity entity = httpResponse.getEntity();
						stream = entity.getContent();
						InputStreamReader isr = new InputStreamReader(stream);
						BufferedReader reader = new BufferedReader(isr);
						sb = new StringBuilder();
						String line = null;
						while ((line = reader.readLine()) != null) {
							sb.append(line + "\n");
						}
						stream.close();
						isr.close();
						reader.close();
						handler.sendEmptyMessage(1);
					} catch (ConnectTimeoutException e) {
						error_response = "Connection timed out";
						handler.sendEmptyMessage(2);
					} catch (UnsupportedEncodingException e) {
						error_response = "Problem connecting to server";
						handler.sendEmptyMessage(2);
					} catch (ClientProtocolException e) {
						error_response = "Problem connecting to server";
						handler.sendEmptyMessage(2);
					} catch (IOException e) {
						error_response = "Connecting to server has been cancelled";
						handler.sendEmptyMessage(2);
					} catch (Exception e) {
						error_response = "Connecting to server has been cancelled";
						handler.sendEmptyMessage(2);
					}
				}
			}.start();
		} else {
			requestListener.onRequestError("There is no active internet connection");
		}
	}

	public void cancelRequest() {
		if (httpClient != null)
			httpClient.getConnectionManager().shutdown();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {

				requestListener.onRequestSuccess(sb.toString());
				if (httpClient != null)
					httpClient.getConnectionManager().shutdown();

			} else if (msg.what == 2) {

				requestListener.onRequestError(error_response);
				if (httpClient != null)
					httpClient.getConnectionManager().shutdown();
			}
		}
	};

}
