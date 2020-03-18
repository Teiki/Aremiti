package fr.teiki.aremiti.holder;

/**
 * Created by Antoine GALTIER on 17/03/2020.
 */

public class AremitiPath {

	private String code_societe;
	private int place_dispo;


	public AremitiPath(String code_societe, int place_dispo) {
		this.code_societe = code_societe;
		this.place_dispo = place_dispo;
	}


	public String getCode_societe() {
		return code_societe;
	}

	public void setCode_societe(String code_societe) {
		this.code_societe = code_societe;
	}

	public int getPlace_dispo() {
		return place_dispo;
	}

	public void setPlace_dispo(int place_dispo) {
		this.place_dispo = place_dispo;
	}
}
