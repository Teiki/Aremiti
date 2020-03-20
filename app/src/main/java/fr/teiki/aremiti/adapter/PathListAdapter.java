package fr.teiki.aremiti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fr.teiki.aremiti.R;
import fr.teiki.aremiti.holder.AremitiPath;
import fr.teiki.aremiti.holder.ScheduleOfOneDay;

/**
 * Created by Antoine GALTIER on 18/03/2020.
 */

public class PathListAdapter extends BaseAdapter {
	private ScheduleOfOneDay scheduleOfOneDay;
	private RadioGroup radioGroup;
	private Context context;


	public PathListAdapter(Context context, ScheduleOfOneDay scheduleOfOneDay, RadioGroup radioGroup) {
		this.context = context;
		this.scheduleOfOneDay = scheduleOfOneDay;
		this.radioGroup = radioGroup;
		sortList();
	}

	public void setScheduleOfOneDay(ScheduleOfOneDay scheduleOfOneDay) {
		this.scheduleOfOneDay = scheduleOfOneDay;
		sortList();
	}

	private ArrayList<AremitiPath> getListToDisplay(){
		if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_tahiti){
			return  scheduleOfOneDay.getFrom_tahiti();
		} else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_moorea){
				return  scheduleOfOneDay.getFrom_moorea();
		}
		return new ArrayList<>();
	}

	@Override
	public int getCount() {
		return getListToDisplay().size();
	}

	@Override
	public Object getItem(int position) {
		return getListToDisplay().get(position);
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
					inflate(R.layout.listview_row_items_for_path, parent, false);
		}

		// get current item to be displayed
		AremitiPath currentItem = (AremitiPath) getItem(position);

		// get the TextView for item name and item description
		TextView text_view_boat_name = convertView.findViewById(R.id.text_view_boat_name);
		TextView text_view_schedule_time = convertView.findViewById(R.id.text_view_schedule_time);
		TextView text_view_free_space = convertView.findViewById(R.id.text_view_free_space);

		//sets the text for item name and item description from the current item object
		text_view_boat_name.setText(currentItem.getName());
		text_view_schedule_time.setText(currentItem.getDepartureTime());
		text_view_free_space.setText(String.valueOf(currentItem.getPlace_dispo()));

		// returns the view for the current row
		return convertView;
	}

	private void sortList() {
		Collections.sort(scheduleOfOneDay.getFrom_tahiti(), new CustomComparator());
		Collections.sort(scheduleOfOneDay.getFrom_moorea(), new CustomComparator());
	}

	private static class CustomComparator implements Comparator<AremitiPath> {
		@Override
		public int compare(AremitiPath o1, AremitiPath o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	}
}