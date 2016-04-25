package com.trip.planner.sources.yelp.dto;

/**
 * Created by Amir Keren on 18/07/2015.
 */
public class Option {

	private String title;
	private String purchase_url;
	private Integer price;
	private String formatted_price;
	private Integer original_price;
	private String formatted_original_price;
	private Boolean is_quantity_limited;
	private Integer remaining_count;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPurchase_url() {
		return purchase_url;
	}

	public void setPurchase_url(String purchase_url) {
		this.purchase_url = purchase_url;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getFormatted_price() {
		return formatted_price;
	}

	public void setFormatted_price(String formatted_price) {
		this.formatted_price = formatted_price;
	}

	public Integer getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(Integer original_price) {
		this.original_price = original_price;
	}

	public String getFormatted_original_price() {
		return formatted_original_price;
	}

	public void setFormatted_original_price(String formatted_original_price) {
		this.formatted_original_price = formatted_original_price;
	}

	public Boolean is_quantity_limited() {
		return is_quantity_limited;
	}

	public void setIs_quantity_limited(Boolean is_quantity_limited) {
		this.is_quantity_limited = is_quantity_limited;
	}

	public Integer getRemaining_count() {
		return remaining_count;
	}

	public void setRemaining_count(Integer remaining_count) {
		this.remaining_count = remaining_count;
	}

}