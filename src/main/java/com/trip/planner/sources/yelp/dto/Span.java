package com.trip.planner.sources.yelp.dto;

/**
 * Created by Amir Keren on 13/07/2015.
 */
public class Span {

	private Double latitude_delta;
	private Double longitude_delta;

	public Double getLatitude_delta() {
		return latitude_delta;
	}

	public void setLatitude_delta(Double latitude_delta) {
		this.latitude_delta = latitude_delta;
	}

	public Double getLongitude_delta() {
		return longitude_delta;
	}

	public void setLongitude_delta(Double longitude_delta) {
		this.longitude_delta = longitude_delta;
	}

}