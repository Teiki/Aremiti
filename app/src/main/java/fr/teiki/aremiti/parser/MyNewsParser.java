package fr.teiki.aremiti.parser;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import fr.teiki.aremiti.holder.NewsHolder;

/**
 * Created by Antoine GALTIER on 19/03/2020.
 */

public class MyNewsParser extends AsyncTask<Void, Void, ArrayList<NewsHolder>> {

	private MyNewsParserDelegate delegate;
	private JSONObject jsonObject;

	public MyNewsParser(JSONObject jsonObject, MyNewsParserDelegate delegate) {
		this.jsonObject = jsonObject;
		this.delegate = delegate;
	}

	@Override
	protected ArrayList<NewsHolder> doInBackground(Void... voids) {
		ArrayList<NewsHolder> newsHolder = new ArrayList<>();
		if (jsonObject != null) {
			try {
				Iterator<String> iter = jsonObject.keys();
				while (iter.hasNext()) {
					String key = iter.next();
					JSONObject jsonPriceObject = jsonObject.getJSONObject(key);
					NewsHolder holder = new NewsHolder(jsonPriceObject.getString(NewsHolder.TAG_URL),
							jsonPriceObject.getString(NewsHolder.TAG_ARTICLE),
							jsonPriceObject.getString(NewsHolder.TAG_TITRE),
							jsonPriceObject.getString(NewsHolder.TAG_DATE));
					newsHolder.add(holder);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return newsHolder;
	}

	@Override
	protected void onPostExecute(ArrayList<NewsHolder> holderArrayList) {
		super.onPostExecute(holderArrayList);

		delegate.onNewsParsed(holderArrayList);
	}


	public interface MyNewsParserDelegate {
		void onNewsParsed(ArrayList<NewsHolder> holderArrayList);
	}
}