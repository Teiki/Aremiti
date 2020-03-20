package fr.teiki.aremiti.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;
import java.util.Date;

import fr.teiki.aremiti.R;
import fr.teiki.aremiti.Utils.Utils;
import fr.teiki.aremiti.adapter.PathListAdapter;
import fr.teiki.aremiti.holder.ScheduleOfOneDay;
import fr.teiki.aremiti.network.APITalker;
import fr.teiki.aremiti.parser.MyScheduleParser;

public class HomeFragment extends Fragment implements View.OnClickListener {

	private HomeViewModel homeViewModel;

	private TextView txt_date;
	private ListView list_of_path;
	private RadioGroup radioGroup;
	private PathListAdapter pathListAdapter;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
		View root = inflater.inflate(R.layout.fragment_home, container, false);

		list_of_path = root.findViewById(R.id.list_of_path);
		radioGroup = root.findViewById(R.id.radio_group);
		txt_date = root.findViewById(R.id.txt_date);
		final Button btn_date_picker = root.findViewById(R.id.btn_date);
		btn_date_picker.setOnClickListener(this);


		refreshData();
		return root;
	}

	private void displayCalendarDialog(){
		//Show a calendar dialog with the date already set (current day by default)
		Utils.displayCalendarDialog(getContext(), homeViewModel.getScheduleOfOneDay().getDate(), new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year,
								  int monthOfYear, int dayOfMonth) {
				Calendar c = Calendar.getInstance();
				c.set(year,monthOfYear,dayOfMonth);
				refreshData(c.getTime());
			}
		});
	}

	//Display a comprehensible date
	private void refreshText(int dayOfMonth, int monthOfYear, int year){
		txt_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
	}

	//Get aremiti path of the current day by default
	private void refreshData(){
		refreshData(new Date());
	}
	//Get data from web, parse it and update txt date label
	private void refreshData(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		refreshText(mDay,mMonth,mYear);

		APITalker.getSchedule(getContext(), date, new MyScheduleParser.MyScheduleParserDelegate() {
			@Override
			public void onScheduleParsed(ScheduleOfOneDay scheduleOfOneDay) {
				//We got all data to display
				homeViewModel.setScheduleOfOneDay(scheduleOfOneDay);
				//Create adapter if needed
				if (pathListAdapter == null){
					pathListAdapter = new PathListAdapter(getContext(), scheduleOfOneDay, radioGroup);
					list_of_path.setAdapter(pathListAdapter);
				} else {
					//Or populate with new data and update
					pathListAdapter.setScheduleOfOneDay(scheduleOfOneDay);
					pathListAdapter.notifyDataSetChanged();
				}
				radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						pathListAdapter.notifyDataSetChanged();
					}
				});
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (R.id.btn_date == v.getId())
			displayCalendarDialog();
	}
}
