package fr.teiki.aremiti.parser;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import fr.teiki.aremiti.holder.PriceHolder;

/**
 * Created by Antoine GALTIER on 19/03/2020.
 */

public class MyPricesParser extends AsyncTask<Void, Void, ArrayList<PriceHolder>> {

	private Context context;
	private MyPricesParserDelegate delegate;
	private JSONObject jsonObject;

	public MyPricesParser(Context context, JSONObject jsonObject, MyPricesParserDelegate delegate) {
		this.context = context;
		this.jsonObject = jsonObject;
		this.delegate = delegate;
	}

	@Override
	protected ArrayList<PriceHolder> doInBackground(Void... voids) {
		ArrayList<PriceHolder> priceHolders = new ArrayList<>();
		if (jsonObject != null) {
			try {
				Iterator<String> iter = jsonObject.keys();
				while (iter.hasNext()) {
					String key = iter.next();
					JSONObject jsonPriceObject = jsonObject.getJSONObject(key);
					PriceHolder priceHolder = new PriceHolder(jsonPriceObject.getString(PriceHolder.TAG_DESIGNATION),
							jsonPriceObject.getString(PriceHolder.TAG_TYPE_ARTICLE),
							jsonPriceObject.getInt(PriceHolder.TAG_MONTANT));
					priceHolders.add(priceHolder);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return priceHolders;
	}

	@Override
	protected void onPostExecute(ArrayList<PriceHolder> holderArrayList) {
		super.onPostExecute(holderArrayList);

		delegate.onPricesParsed(holderArrayList);
	}


	public interface MyPricesParserDelegate {
		void onPricesParsed(ArrayList<PriceHolder> holderArrayList);
	}
}