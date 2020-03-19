package fr.teiki.aremiti.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import fr.teiki.aremiti.R;
import fr.teiki.aremiti.adapter.NewsAdapter;
import fr.teiki.aremiti.holder.NewsHolder;
import fr.teiki.aremiti.network.APITalker;
import fr.teiki.aremiti.parser.MyNewsParser;

public class NewsFragment extends Fragment {

	private NewsViewModel newsViewModel;

	private ListView listView;
	private NewsAdapter newsAdapter;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		newsViewModel =
				ViewModelProviders.of(this).get(NewsViewModel.class);
		View root = inflater.inflate(R.layout.fragment_news, container, false);

		listView = root.findViewById(R.id.list_news);

		update();

		return root;
	}

	private void update() {
		APITalker.getNews(getContext(), new MyNewsParser.MyNewsParserDelegate() {
			@Override
			public void onNewsParsed(ArrayList<NewsHolder> holderArrayList) {
				if (newsAdapter == null){
					newsAdapter = new NewsAdapter(getContext(), holderArrayList);
				}
				listView.setAdapter(newsAdapter);
			}
		});
	}
}
