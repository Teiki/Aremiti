package fr.teiki.aremiti.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import fr.teiki.aremiti.holder.ScheduleOfOneDay;

public class HomeViewModel extends ViewModel {


	private ScheduleOfOneDay scheduleOfOneDay;


	public ScheduleOfOneDay getScheduleOfOneDay() {
		return scheduleOfOneDay;
	}

	public void setScheduleOfOneDay(ScheduleOfOneDay scheduleOfOneDay) {
		this.scheduleOfOneDay = scheduleOfOneDay;
	}
}