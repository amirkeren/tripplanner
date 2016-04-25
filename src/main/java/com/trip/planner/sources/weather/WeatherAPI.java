package com.trip.planner.sources.weather;

import com.google.maps.model.Unit;
import com.trip.planner.sources.weather.dto.Forecast;
import com.trip.planner.sources.weather.dto.WeatherHistory;
import com.trip.planner.sources.weather.wunderground.WundergroundAPI;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Amir Keren on 01/08/2015.
 */
public class WeatherAPI {

    private Logger log = LoggerFactory.getLogger(WeatherAPI.class);

    private final int FORECAST_LIMIT = 10;

    @Autowired
    private WundergroundAPI wundergroundAPI;

    public WeatherAPI() { log.debug("Initializing Weather API"); }

    /**
     * Get weather forecast
     *
     * @param location name of the place
     * @param startTripDate start date of the trip
     * @param endTripDate start date of the trip
     * @param unit units of measurement
     */
    public List<Forecast> getWeatherForecast(String location, Unit unit, Date startTripDate, Date endTripDate)
            throws IOException {
        log.debug("Getting weather forecast information");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, FORECAST_LIMIT);
        //if out of forecast max limit
        if (startTripDate.after(cal.getTime())) {
            return null;
        }
        List<Forecast> result = wundergroundAPI.getWeather(location, unit);
        Stream<Forecast> forecastStream = result.stream().filter(forecast ->
                (DateUtils.isSameDay(forecast.getDate(), startTripDate) || forecast.getDate().after(startTripDate)) &&
                (DateUtils.isSameDay(forecast.getDate(), endTripDate) || forecast.getDate().before(endTripDate)));
        return forecastStream.collect(Collectors.toList());
    }

    /**
     * Get historic weather information
     *
     * @param location name of the place
     * @param startTripDate start date of the trip
     * @param endTripDate start date of the trip
     * @param unit units of measurement
     */
    public WeatherHistory getHistoricWeather(String location, Date startTripDate, Date endTripDate, Unit unit)
            throws IOException {
        return wundergroundAPI.getWeatherHistory(location, startTripDate, endTripDate, unit);
    }

}