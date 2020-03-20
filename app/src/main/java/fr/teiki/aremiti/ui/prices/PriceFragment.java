package fr.teiki.aremiti.ui.prices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import fr.teiki.aremiti.R;
import fr.teiki.aremiti.adapter.PriceListAdapter;
import fr.teiki.aremiti.holder.PriceHolder;
import fr.teiki.aremiti.network.APITalker;
import fr.teiki.aremiti.parser.MyPricesParser;

public class PriceFragment extends Fragment {

	private PriceViewModel priceViewModel;

	private ListView list_price;
	private PriceListAdapter priceListAdapter;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		priceViewModel = ViewModelProviders.of(this).get(PriceViewModel.class);
		View root = inflater.inflate(R.layout.fragment_magazine, container, false);

		list_price = root.findViewById(R.id.list_price);

		getData();
		return root;
	}

	private void getData() {
		//Get data from Web API, parse it
		APITalker.getPrices(getContext(), new MyPricesParser.MyPricesParserDelegate() {
			@Override
			public void onPricesParsed(ArrayList<PriceHolder> holderArrayList) {
				if (priceListAdapter == null)
					priceListAdapter = new PriceListAdapter(getContext(), holderArrayList);
				list_price.setAdapter(priceListAdapter);
			}
		});
	}
}
