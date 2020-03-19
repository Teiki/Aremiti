package fr.teiki.aremiti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fr.teiki.aremiti.R;
import fr.teiki.aremiti.holder.PriceHolder;

/**
 * Created by Antoine GALTIER on 19/03/2020.
 */

public class PriceListAdapter extends BaseAdapter {
	private ArrayList<PriceHolder> holderArrayList;
	private Context context;
	private ArrayList<String> types = new ArrayList<>();

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;

	public PriceListAdapter(Context context, ArrayList<PriceHolder> holderArrayList) {
		this.context = context;
		this.holderArrayList = holderArrayList;
		for (PriceHolder priceHolder : holderArrayList) {
			if (!types.contains(priceHolder.getType_article()))
				types.add(priceHolder.getType_article());
		}
		sortList();
	}


	@Override
	public int getCount() {
		return holderArrayList.size();
	}

	@Override
	public PriceHolder getItem(int position) {
		return holderArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// inflate the layout for each list row
		if (convertView == null) {
			convertView = LayoutInflater.from(context).
					inflate(R.layout.listview_row_items_for_prices, parent, false);
		}

		// get current item to be displayed
		PriceHolder currentItem = (PriceHolder) getItem(position);

		// get the TextView for item name and item description
		TextView txt_type_article = convertView.findViewById(R.id.txt_type_article);
		TextView txt_designation = convertView.findViewById(R.id.txt_designation);
		TextView txt_price = convertView.findViewById(R.id.txt_price);
		TextView txt_double_price = convertView.findViewById(R.id.txt_double_price);

		//sets the text for item name and item description from the current item object
		txt_type_article.setText(currentItem.getType_article());
		txt_designation.setText(currentItem.getDesignation());
		txt_price.setText("A/S "+ String.valueOf(currentItem.getPrice())+" XPF");
		txt_double_price.setText("A/R "+String.valueOf(currentItem.getPrice()*2)+" XPF");

		// returns the view for the current row
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return types.size();
	}

	@Override
	public int getItemViewType(int position) {
		return getItem(position).getViewType();
	}

	private void sortList() {
		Collections.sort(holderArrayList, new PriceListAdapter.CustomComparator());
	}

	private static class CustomComparator implements Comparator<PriceHolder> {
		@Override
		public int compare(PriceHolder o1, PriceHolder o2) {
			return o1.getType_article().compareTo(o2.getType_article());
		}
	}
}
