package com.trip.planner.sources.weather.wunderground.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Amir Keren on 08/08/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private Forecast forecast;

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

}