package fr.teiki.aremiti.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import fr.teiki.aremiti.R;
import fr.teiki.aremiti.adapter.PathListAdapter;
import fr.teiki.aremiti.holder.ScheduleOfOneDay;
import fr.teiki.aremiti.network.APITalker;
import fr.teiki.aremiti.parser.MyScheduleParser;

public class HomeFragment extends Fragment {

	private HomeViewModel homeViewModel;

	private ListView list_of_path;
	private RadioGroup radioGroup;
	private PathListAdapter pathListAdapter;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
		View root = inflater.inflate(R.layout.fragment_home, container, false);
		//final TextView textView = root.findViewById(R.id.text_home);
//		homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//			@Override
//			public void onChanged(@Nullable String s) {
//				textView.setText(s);
//			}
//		});

		list_of_path = root.findViewById(R.id.list_of_path);
		radioGroup = root.findViewById(R.id.radio_group);

		refreshData();
		return root;
	}

	private void refreshData(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(date);
		APITalker.getSchedule(getContext(), date, new MyScheduleParser.MyScheduleParserDelegate() {
			@Override
			public void onScheduleParsed(ScheduleOfOneDay scheduleOfOneDay) {
				homeViewModel.setScheduleOfOneDay(scheduleOfOneDay);
				pathListAdapter = new PathListAdapter(getContext(),scheduleOfOneDay, radioGroup);
				list_of_path.setAdapter(pathListAdapter);
				radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						pathListAdapter.notifyDataSetChanged();
					}
				});

				//homeViewModel.getScheduleOfOneDay().getFrom_tahiti().addAll()
				//refreshData(scheduleOfOneDay);
			}
		});
	}
}
