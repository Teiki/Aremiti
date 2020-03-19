package fr.teiki.aremiti.parser;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import fr.teiki.aremiti.Utils.Utils;
import fr.teiki.aremiti.holder.AremitiPath;
import fr.teiki.aremiti.holder.ScheduleOfOneDay;

/**
 * Created by Antoine GALTIER on 17/03/2020.
 */

public class MyScheduleParser extends AsyncTask<Void, Void, ScheduleOfOneDay> {

	private Context context;
	private MyScheduleParserDelegate delegate;
	private ScheduleOfOneDay scheduleOfOneDay;
	private JSONObject jsonObject;

	public MyScheduleParser(Context context, JSONObject jsonObject, MyScheduleParserDelegate delegate) {
		this.context = context;
		this.jsonObject = jsonObject;
		this.delegate = delegate;
	}

	@Override
	protected ScheduleOfOneDay doInBackground(Void... voids) {
		if (jsonObject != null) {
			if (jsonObject.keys().next() != null && !jsonObject.keys().next().isEmpty()) {
				String date_label = jsonObject.keys().next();
				Date date = Utils.getDate(date_label);
				if (date != null) {
					scheduleOfOneDay = new ScheduleOfOneDay(date);
					try {
						JSONObject jsonObject_departure = jsonObject.getJSONObject(date_label);
						Iterator<String> iter_departure = jsonObject_departure.keys();
						while (iter_departure.hasNext()) {
							String key_departure = iter_departure.next();
							if ("T".equals(key_departure)) {
								scheduleOfOneDay.setFrom_tahiti(getAremitiPathList(date, jsonObject_departure.getJSONObject(key_departure)));
							} else if ("M".equals(key_departure)) {
								scheduleOfOneDay.setFrom_moorea(getAremitiPathList(date, jsonObject_departure.getJSONObject(key_departure)));
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		}

		return scheduleOfOneDay;
	}

	private ArrayList<AremitiPath> getAremitiPathList(Date date, JSONObject jsonObject_hours) throws JSONException {
		ArrayList<AremitiPath> aremitiPathList = new ArrayList<>();
		Iterator<String> iter_times = jsonObject_hours.keys();
		while (iter_times.hasNext()) {
			String key_time = iter_times.next();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR, Integer.parseInt(key_time.substring(0, key_time.indexOf("H"))));
			calendar.set(Calendar.MINUTE, Integer.parseInt(key_time.substring(key_time.indexOf("H") + 1)));
			Date path_date = calendar.getTime();
			JSONObject time_object = jsonObject_hours.getJSONObject(key_time);
			Iterator<String> iter_in_time = time_object.keys();
			while (iter_in_time.hasNext()) {
				String key = iter_in_time.next();
				JSONObject pathObject = time_object.getJSONObject(key);
				try {
					AremitiPath aremitiPath = new AremitiPath();
					aremitiPath.setCode_societe(pathObject.getString(AremitiPath.TAG_CODE_SOCIETE));
					aremitiPath.setPlace_dispo((int)Math.round(Double.parseDouble(pathObject.getString(AremitiPath.TAG_PLACE_DISPO))));
					aremitiPath.setDate(path_date);
					aremitiPathList.add(aremitiPath);
				} catch (JSONException e) {
					// Something went wrong!
				}
			}

		}
		return aremitiPathList;
	}

	@Override
	protected void onPostExecute(ScheduleOfOneDay scheduleOfOneDay) {
		super.onPostExecute(scheduleOfOneDay);

		delegate.onScheduleParsed(scheduleOfOneDay);
	}


	public interface MyScheduleParserDelegate {
		void onScheduleParsed(ScheduleOfOneDay scheduleOfOneDay);
	}
}