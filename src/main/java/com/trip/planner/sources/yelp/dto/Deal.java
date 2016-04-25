package com.trip.planner.sources.yelp.dto;

import java.util.List;

/**
 * Created by Amir Keren on 18/07/2015.
 */
public class Deal {

	private String id;
	private String title;
	private String url;
	private String image_url;
	private String currency_code;
	private Long time_start;
	private Long time_end;
	private Boolean is_popular;
	private String what_you_get;
	private String important_restrictions;
	private String additional_restrictions;
	private List<Option> options;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public Long getTime_start() {
		return time_start;
	}

	public void setTime_start(Long time_start) {
		this.time_start = time_start;
	}

	public Long getTime_end() {
		return time_end;
	}

	public void setTime_end(Long time_end) {
		this.time_end = time_end;
	}

	public Boolean is_popular() {
		return is_popular;
	}

	public void setIs_popular(Boolean is_popular) {
		this.is_popular = is_popular;
	}

	public String getWhat_you_get() {
		return what_you_get;
	}

	public void setWhat_you_get(String what_you_get) {
		this.what_you_get = what_you_get;
	}

	public String getImportant_restrictions() {
		return important_restrictions;
	}

	public void setImportant_restrictions(String important_restrictions) {
		this.important_restrictions = important_restrictions;
	}

	public String getAdditional_restrictions() {
		return additional_restrictions;
	}

	public void setAdditional_restrictions(String additional_restrictions) {
		this.additional_restrictions = additional_restrictions;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
}