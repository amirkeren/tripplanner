package com.trip.planner.sources.weather.wunderground.dto;

import java.util.List;

/**
 * Created by Amir Keren on 08/08/2015.
 */
public class SimpleForecast {

    private List<ForecastDay> forecastday;

    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<ForecastDay> forecastday) {
        this.forecastday = forecastday;
    }

}