package fr.teiki.aremiti.parser;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

import fr.teiki.aremiti.MainActivity;
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
			//scheduleOfOneDay = new ScheduleOfOneDay(date);

		}

		return scheduleOfOneDay;
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