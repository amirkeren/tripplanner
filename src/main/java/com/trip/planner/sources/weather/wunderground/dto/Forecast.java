package com.trip.planner.sources.weather.wunderground.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Amir Keren on 08/08/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {

    private SimpleForecast simpleforecast;

    public SimpleForecast getSimpleforecast() {
        return simpleforecast;
    }

    public void setSimpleforecast(SimpleForecast simpleforecast) {
        this.simpleforecast = simpleforecast;
    }

}