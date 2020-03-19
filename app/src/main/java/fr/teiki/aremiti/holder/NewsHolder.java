package fr.teiki.aremiti.holder;

import java.util.Date;

import fr.teiki.aremiti.Utils.Utils;
import fr.teiki.aremiti.network.RestClient;

/**
 * Created by Antoine GALTIER on 19/03/2020.
 */

public class NewsHolder {

	private String image;
	private String article;
	private String titre;
	private Date date;

	public static final String TAG_URL = "image";
	public static final String TAG_DATE = "date_publication";
	public static final String TAG_ARTICLE = "article";
	public static final String TAG_TITRE = "titre";


	public NewsHolder(String image, String article, String titre, String date) {
		this.image = image;
		this.article = article;
		this.titre = titre;
		this.date = Utils.getDate(date);
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getImageURL() {
		return RestClient.baseUrl + image;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
}
