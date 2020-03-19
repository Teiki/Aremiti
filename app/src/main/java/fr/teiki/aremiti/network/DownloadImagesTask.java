package fr.teiki.aremiti.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Antoine GALTIER on 19/03/2020.
 */

public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {


	private ImageView imageView;

	public DownloadImagesTask(ImageView imageView) {
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(String... urls) {
		return download_Image(urls[0]);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		imageView.setImageBitmap(result);
	}


	private Bitmap download_Image(String url) {
		//---------------------------------------------------
		Bitmap bm = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
		}
		return bm;
		//---------------------------------------------------
	}


}