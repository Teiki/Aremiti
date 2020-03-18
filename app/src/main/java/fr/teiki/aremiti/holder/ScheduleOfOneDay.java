package fr.teiki.aremiti.holder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Antoine GALTIER on 17/03/2020.
 */

public class ScheduleOfOneDay {

	private Date date;
	private ArrayList<AremitiPath> from_tahiti;
	private ArrayList<AremitiPath> from_moorea;

	public ScheduleOfOneDay(Date date) {
		this.date = date;
	}

	public ArrayList<AremitiPath> getFrom_tahiti() {
		return from_tahiti;
	}

	public void setFrom_tahiti(ArrayList<AremitiPath> from_tahiti) {
		this.from_tahiti = from_tahiti;
	}

	public ArrayList<AremitiPath> getFrom_moorea() {
		return from_moorea;
	}

	public void setFrom_moorea(ArrayList<AremitiPath> from_moorea) {
		this.from_moorea = from_moorea;
	}

	public Date getDate() {
		return date;
	}
}
