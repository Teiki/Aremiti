package fr.teiki.aremiti.network;

import android.content.Context;

import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;
import fr.teiki.aremiti.parser.MyScheduleParser;

/**
 * Created by Antoine GALTIER on 17/03/2020.
 */

public class APITalker {

	public static void getSchedule(final Context context, final Date date, final MyScheduleParser.MyScheduleParserDelegate delegate){
		RestClient restClient = new RestClient(context);
		restClient.startRequest("GET","aremiti/horaires", date, new JsonResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, Object rawResponse) {
				JSONObject response = (JSONObject) rawResponse;
				MyScheduleParser myScheduleParser = new MyScheduleParser(context, response, delegate);
				myScheduleParser.execute();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Object errorResponse) {

			}

			@Override
			public void onProgress(long bytesWritten, long totalSize) {

			}
		});
	}
}
