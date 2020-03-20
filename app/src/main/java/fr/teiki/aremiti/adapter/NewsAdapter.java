package fr.teiki.aremiti.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fr.teiki.aremiti.R;
import fr.teiki.aremiti.Utils.PicassoTrustAll;
import fr.teiki.aremiti.Utils.Utils;
import fr.teiki.aremiti.holder.NewsHolder;
import fr.teiki.aremiti.holder.PriceHolder;
import fr.teiki.aremiti.network.DownloadImagesTask;
import okhttp3.internal.Util;

/**
 * Created by Antoine GALTIER on 19/03/2020.
 */

public class NewsAdapter extends BaseAdapter {
	private ArrayList<NewsHolder> holderArrayList;
	private Context context;

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;

	public NewsAdapter(Context context, ArrayList<NewsHolder> holderArrayList) {
		this.context = context;
		this.holderArrayList = holderArrayList;
		sortList();
	}


	@Override
	public int getCount() {
		return holderArrayList.size();
	}

	@Override
	public NewsHolder getItem(int position) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_row_items_for_news, parent, false);
		}

		// get current item to be displayed
		NewsHolder currentItem = (NewsHolder) getItem(position);

		// get the TextView for item name and item description
		ImageView image = convertView.findViewById(R.id.image);
		TextView description = convertView.findViewById(R.id.description);
		TextView infos = convertView.findViewById(R.id.infos);

		try {
			PicassoTrustAll.getPicassso(context).get().load(currentItem.getImageURL()).into(image);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		//DownloadImagesTask downloadImagesTask = new DownloadImagesTask(image);
		//downloadImagesTask.execute(currentItem.getImageURL());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			description.setText(Html.fromHtml(currentItem.getArticle(),Html.FROM_HTML_MODE_COMPACT));
		} else {
			description.setText(Html.fromHtml(currentItem.getArticle()));
		}

		infos.setText("Publié le : " + Utils.getSimpleDateLabel(currentItem.getDate()));


		// returns the view for the current row
		return convertView;
	}

	private void sortList() {
		Collections.sort(holderArrayList, new NewsAdapter.CustomComparator());
	}

	private static class CustomComparator implements Comparator<NewsHolder> {
		@Override
		public int compare(NewsHolder o1, NewsHolder o2) {
			return o2.getDate().compareTo(o1.getDate());
		}
	}
}
