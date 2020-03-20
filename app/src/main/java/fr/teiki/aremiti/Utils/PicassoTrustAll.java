package fr.teiki.aremiti.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import fr.teiki.aremiti.R;
import okhttp3.OkHttpClient;

/**
 * Created by Antoine GALTIER on 19/03/2020.
 */

public class PicassoTrustAll {


	public static Picasso getPicassso(Context context) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {

		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init((KeyStore) null);
		TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
		if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
			throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
		}
		X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, new TrustManager[] { trustManager }, null);
		SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
		OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager).build();

		OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(client);

		Picasso.Builder builder = new Picasso.Builder(context);
		return builder.downloader(okHttp3Downloader).build();


//			InputStream keyStore = context.getResources().openRawResource(R.raw.my_keystore);
//			Picasso.Builder builder = new Picasso.Builder(context);
//			SSLContext sslContext;
//
//				sslContext = SSLContext.getInstance("TLS");
//				sslContext.init(null, new TrustManager[]{new X509TrustManager() {
//					@Override
//					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//					}
//
//					@Override
//					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//					}
//
//					@Override
//					public X509Certificate[] getAcceptedIssuers() {
//						return new X509Certificate[0];
//					}
//				}}, null);
//				AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
//				OkHttpClient okHttpClient = new OkHttpClient().newBuilder().sslSocketFactory(sslContext.getSocketFactory(), new X509TrustManager() {
//					@Override
//					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//					}
//
//					@Override
//					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//					}
//
//					@Override
//					public X509Certificate[] getAcceptedIssuers() {
//						return new X509Certificate[0];
//					}
//				}).build();
//				OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(okHttpClient);
//
//				return builder.downloader(okHttp3Downloader).build();

//				OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager.);
//				okHttpClient.sslSocketFactory() = ;
//				OkHttpDownloader okHttpDownloader = new OkHttpDownloader(okHttpClient);
//				builder.downloader(okHttpDownloader);
//				sPicasso = builder.build();



		//return sPicasso;
	}

	private static Picasso mInstance = null;



}