package fr.teiki.aremiti.network;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Antoine GALTIER on 17/03/2020.
 */

public interface JsonResponseHandler {

	//response will be of kind JSONObject or JSONArray
	void onSuccess(int statusCode, Header[] headers, Object rawResponse);

	//error response will be of type JSONObject or JSONArray
	void onFailure(int statusCode, Header[] headers, Object errorResponse);

	void onProgress(long bytesWritten, long totalSize);
}
