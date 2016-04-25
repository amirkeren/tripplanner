package com.trip.planner.sources.tripadvisor.dto;

/**
 * Created by Amir Keren on 08/07/2015.
 */
public class Restaurant extends TripAdvisorLocation {

	private Category[] cuisine;

	public Category[] getCuisine() {
		return cuisine;
	}

	public void setCuisine(Category[] cuisine) {
		this.cuisine = cuisine;
	}

}