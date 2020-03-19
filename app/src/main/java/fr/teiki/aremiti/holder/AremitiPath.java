package fr.teiki.aremiti.holder;

import java.util.Date;

import fr.teiki.aremiti.Utils.Utils;

/**
 * Created by Antoine GALTIER on 17/03/2020.
 */

public class AremitiPath {

	private String code_societe;
	private int place_dispo;
	private Date date;

	public static String TAG_CODE_SOCIETE = "code_societe";
	public static String TAG_PLACE_DISPO = "place_dispo";
	public static String TAG_AREMITI6 = "CATAM";
	public static String TAG_AREMITI_FERRY = "FERRY";



	public String getCode_societe() {
		return code_societe;
	}

	public void setCode_societe(String code_societe) {
		this.code_societe = code_societe;
	}

	public int getPlace_dispo() {
		//Avoid to show impossible number of available car places
		if (place_dispo >= 0) {
			return place_dispo;
		} else {
			return 0;
		}
	}

	public void setPlace_dispo(int place_dispo) {
		this.place_dispo = place_dispo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		if (TAG_AREMITI6.equals(code_societe)) {
			return "Aremiti 6";
		} else if (TAG_AREMITI_FERRY.equals(code_societe)) {
			return "Aremiti Ferry 2";
		}
		return "nouveau bateau";
	}

	public String getDepartureTime() {
		if (date != null){
			return Utils.getDepartureTime(date);
		}
		return "??";
	}
}

