package fr.teiki.aremiti.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import fr.teiki.aremiti.holder.ScheduleOfOneDay;
import fr.teiki.aremiti.network.APITalker;
import fr.teiki.aremiti.parser.MyScheduleParser;

public class HomeFragment extends Fragment {

	private HomeViewModel homeViewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
		View root = inflater.inflate(R.layout.fragment_home, container, false);
		final TextView textView = root.findViewById(R.id.text_home);
		homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(@Nullable String s) {
				textView.setText(s);
			}
		});
		return root;
	}

	public void refreshData(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(date);
		APITalker.getSchedule(getContext(), date, new MyScheduleParser.MyScheduleParserDelegate() {
			@Override
			public void onScheduleParsed(ScheduleOfOneDay scheduleOfOneDay) {
				refreshData(scheduleOfOneDay);
			}
		});
	}

	public void refreshData(ScheduleOfOneDay scheduleOfOneDay){

	}
}
