package com.trip.planner.sources.yelp.dto;

import java.util.List;

/**
 * Created by Amir Keren on 13/07/2015.
 */
public class SearchResponse {

	private Region region;
	private Integer total;
	private List<Business> businesses;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public List<Business> getBusinesses() {
		return businesses;
	}

	public void setBusinesses(List<Business> businesses) {
		this.businesses = businesses;
	}

}