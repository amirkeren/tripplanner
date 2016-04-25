package com.trip.planner.sources.weather.wunderground.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Amir Keren on 08/08/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryResponse {

    private Trip trip;

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

}