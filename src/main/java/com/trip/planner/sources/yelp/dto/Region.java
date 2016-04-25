package com.trip.planner.sources.yelp.dto;

/**
 * Created by Amir Keren on 13/07/2015.
 */
public class Region {

	private Span span;
	private Center center;

	public Span getSpan() {
		return span;
	}

	public void setSpan(Span span) {
		this.span = span;
	}

	public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
	}

}