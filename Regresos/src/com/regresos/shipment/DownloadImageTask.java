package com.regresos.shipment;

import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public  class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap> {

ImageView imageView = null;

@Override
protected Bitmap doInBackground(ImageView... imageViews) {
    this.imageView = imageViews[0];
    try {
		return download_Image((String)imageView.getTag());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}

@Override
protected void onPostExecute(Bitmap result) {
    imageView.setImageBitmap(result);
}


	private Bitmap download_Image(String url) throws IOException {
		URL urlg = new URL(url);		
		Bitmap bmp = BitmapFactory.decodeStream(urlg.openConnection().getInputStream());
		return bmp;
	}
}