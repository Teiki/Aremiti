package fr.teiki.aremiti.network;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cz.msebera.android.httpclient.Header;
import fr.teiki.aremiti.R;
import fr.teiki.aremiti.Utils.Utils;

/**
 * Created by Antoine GALTIER on 17/03/2020.
 */

public class RestClient {

	public static String baseUrl = "https://api.aremiti.net:31420/rest-api/";
	private AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
	private Context context;


	RestClient(Context context) {
		this.context = context;
	}




	void startRequest(String verb, String path, final JsonResponseHandler responseHandler) {
		this.request(verb, getAbsoluteUrl(path), null, responseHandler);
	}

	void startRequest(String verb, String path, Date date, final JsonResponseHandler responseHandler) {
		this.request(verb, getAbsoluteUrl(path), date, responseHandler);
	}

	private String getAbsoluteUrl(String relativeUrl) {
		return this.baseUrl + relativeUrl;
	}


	private void request(String verb, String urlComplet, Date date, final JsonResponseHandler responseHandler) {
		if (!Utils.isNetworkAvailable(context)) {
			Toast.makeText(context,context.getString(R.string.check_network),Toast.LENGTH_SHORT).show();
			responseHandler.onFailure(0, new Header[]{}, null);
		} else
		{
			RequestParams params = new RequestParams();

			if (date != null){
				params.put("date_debut",Utils.getSimpleDateFormat(date));
			}

			this.client.addHeader("Accept", "application/json");


			this.client.setConnectTimeout(5000);
			this.client.setResponseTimeout(5000);
			this.client.setTimeout(5000);
			this.client.setMaxRetriesAndTimeout(5,5000);


			if ("GET".equals(verb)) {
				this.client.addHeader("content-type", "application/json;charset=UTF-8");

//				try {
//					KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//					trustStore.load(null, null);
//					MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
//					sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//					client.setSSLSocketFactory(sf);
//				}
//				catch (Exception e) {
//				}


				this.client.get(urlComplet, params, new InternalJsonResponseHandler(responseHandler));
			}
		}
	}


//	class MySSLSocketFactory extends SSLSocketFactory {
//		SSLContext sslContext = SSLContext.getInstance("TLS");
//
//		public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
//			super();
//
//			TrustManager tm = new X509TrustManager() {
//				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//				}
//
//				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//				}
//
//				public X509Certificate[] getAcceptedIssuers() {
//					return null;
//				}
//			};
//
//			sslContext.init(null, new TrustManager[]{tm}, null);
//		}
//
//		@Override
//		public String[] getDefaultCipherSuites() {
//			return new String[0];
//		}
//
//		@Override
//		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
//			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
//		}
//
//		@Override
//		public Socket createSocket() throws IOException {
//			return sslContext.getSocketFactory().createSocket();
//		}
//	}

	private class InternalJsonResponseHandler extends JsonHttpResponseHandler {

		private JsonResponseHandler responseHandler;

		InternalJsonResponseHandler(JsonResponseHandler realHandler) {
			this.responseHandler = realHandler;
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			responseHandler.onSuccess(statusCode, headers, response);
		}

		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
			responseHandler.onSuccess(statusCode, headers, response);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
			try {
				if (errorResponse == null) {
					errorResponse = new JSONObject();
				}
				errorResponse.put("Exception", throwable.getLocalizedMessage());
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				responseHandler.onFailure(statusCode, headers, errorResponse);
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			JSONObject errorResponse = new JSONObject();
			try {
				errorResponse.put("error", responseString);
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				responseHandler.onFailure(statusCode, headers, errorResponse);
			}

		}

		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
			try {
				if (errorResponse == null) {
					errorResponse = new JSONArray();
				}
				JSONObject error = new JSONObject().put("Exception", throwable.getLocalizedMessage());
				errorResponse.put(error);
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				responseHandler.onFailure(statusCode, headers, errorResponse);
			}
		}
	}
}
