package fr.teiki.aremiti.holder;

/**
 * Created by Antoine GALTIER on 19/03/2020.
 */

public class PriceHolder {


	private String designation;
	private String type_article;
	private int price;

	public PriceHolder(String designation, String type_article, int price) {
		this.designation = designation;
		this.type_article = type_article;
		this.price = price;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getType_article() {
		return type_article;
	}

	public void setType_article(String type_article) {
		this.type_article = type_article;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getViewType() {
		return type_article.length();
	}
}
