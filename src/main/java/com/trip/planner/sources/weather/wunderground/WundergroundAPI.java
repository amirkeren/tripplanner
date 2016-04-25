package com.trip.planner.sources.weather.wunderground;

import com.google.maps.model.Unit;
import com.trip.planner.sources.weather.dto.Forecast;
import com.trip.planner.sources.weather.dto.WeatherHistory;
import com.trip.planner.sources.weather.wunderground.dto.ForecastDay;
import com.trip.planner.sources.weather.wunderground.dto.HistoryResponse;
import com.trip.planner.sources.weather.wunderground.dto.WeatherResponse;
import com.trip.planner.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Amir Keren on 08/08/2015.
 */
public class WundergroundAPI {

    private Logger log = LoggerFactory.getLogger(WundergroundAPI.class);

    @Autowired
    private Utils utils;

    private final String BASE_URL = "http://api.wunderground.com/api/";

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private String apiKey;

    /**
     * Constructor for Wunderground API
     *
     * @param apiKey key of wunderground api
     */
    public WundergroundAPI(String apiKey) {
        log.debug("Initializing Wunderground API");
        this.apiKey = apiKey;
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        log.debug("Wunderground API initialized");
    }

    /**
     * Gets the 10 day weather forecast
     *
     * @param location name of the place
     * @param unit units of measurement
     */
    public List<Forecast> getWeather(String location, Unit unit) throws IOException {
        log.debug("Getting weather predictions for location {}", location);
        String query = BASE_URL + apiKey + "/forecast10day/q/";
        ResponseEntity<String> response = runQuery(query, location);
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            WeatherResponse result = utils.getMapper().readValue(response.getBody().getBytes(),
                    WeatherResponse.class);
            return toForecast(result, unit);
        }
        return null;
    }

    /**
     * Gets the historical weather information for location
     *
     * @param location name of the place
     * @param tripStartDate start date of the trip
     * @param tripEndDate end date of the trip
     * @param unit units of measurement
     */
    public WeatherHistory getWeatherHistory(String location, Date tripStartDate, Date tripEndDate, Unit unit)
            throws IOException{
        log.debug("Getting weather predictions for location {}", location);
        ResponseEntity<String> response = buildHistoryQuery(location, tripStartDate, tripEndDate);
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            HistoryResponse result = utils.getMapper().readValue(response.getBody().getBytes(),
                    HistoryResponse.class);
            return toWeatherHistory(result, unit);
        }
        log.error("Failed to get weather predictions");
        if (response != null) {
            log.error("Response - {}", response.toString());
        }
        return null;
    }

    /**
     * Aux method to convert the response to a forecast object
     *
     * @param result response object
     * @param unit units of measurement
     */
    private List<Forecast> toForecast(WeatherResponse result, Unit unit) {
        List<Forecast> forecastList = new ArrayList();
        for (ForecastDay _forecast: result.getForecast().getSimpleforecast().getForecastday()) {
            Forecast forecast = new Forecast();
            forecast.setHumidity(_forecast.getAvehumidity());
            forecast.setDate(new Date(Long.parseLong(_forecast.getDate().getEpoch()) * 1000));
            forecast.setDescription(_forecast.getConditions());
            if (unit == Unit.METRIC) {
                forecast.setHigh(Integer.parseInt(_forecast.getHigh().getCelsius()));
                forecast.setLow(Integer.parseInt(_forecast.getLow().getCelsius()));
                forecast.setPrecipitation(_forecast.getQpf_allday().getMm());
                forecast.setSnow(Float.parseFloat(_forecast.getSnow_allday().getCm()));
            } else {
                forecast.setHigh(Integer.parseInt(_forecast.getHigh().getFahrenheit()));
                forecast.setLow(Integer.parseInt(_forecast.getLow().getFahrenheit()));
                forecast.setPrecipitation(_forecast.getQpf_allday().getIn());
                forecast.setSnow(Float.parseFloat(_forecast.getSnow_allday().getIn()));
            }
            forecastList.add(forecast);
        }
        return forecastList;
    }

    /**
     * Aux method to convert the response to a weather history object
     *
     * @param result response object
     * @param unit units of measurement
     */
    private WeatherHistory toWeatherHistory(HistoryResponse result, Unit unit) {
        WeatherHistory history = new WeatherHistory();
        if (unit == Unit.METRIC) {
            history.setAverageHighTemperature(Integer.parseInt(result.getTrip().getTemp_high().getAvg().getC()));
            history.setAverageLowTemperature(Integer.parseInt(result.getTrip().getTemp_low().getAvg().getC()));
            history.setAveragePrecipitation(Float.parseFloat(result.getTrip().getPrecip().getAvg().getCm()));
        } else {
            history.setAverageHighTemperature(Integer.parseInt(result.getTrip().getTemp_high().getAvg().getF()));
            history.setAverageLowTemperature(Integer.parseInt(result.getTrip().getTemp_low().getAvg().getF()));
            history.setAveragePrecipitation(Float.parseFloat(result.getTrip().getPrecip().getAvg().getIn()));
        }
        history.setChanceofhailday(Float.parseFloat(result.getTrip().getChance_of().getChanceofhailday().
                getPercentage()));
        history.setChanceofhumidday(Float.parseFloat(result.getTrip().getChance_of().getChanceofhumidday().
                getPercentage()));
        history.setChanceofprecip(Float.parseFloat(result.getTrip().getChance_of().getChanceofprecip().
                getPercentage()));
        history.setChanceofrainday(Float.parseFloat(result.getTrip().getChance_of().getChanceofrainday().
                getPercentage()));
        history.setChanceofsnowday(Float.parseFloat(result.getTrip().getChance_of().getChanceofsnowday().
                getPercentage()));
        history.setChanceofsnowonground(Float.parseFloat(result.getTrip().getChance_of().getChanceofsnowonground().
                getPercentage()));
        history.setChanceofsultryday(Float.parseFloat(result.getTrip().getChance_of().getChanceofsultryday().
                getPercentage()));
        history.setChanceoftempbelowfreezing(Float.parseFloat(result.getTrip().getChance_of().getTempbelowfreezing()
                .getPercentage()));
        history.setChanceoftempoverninety(Float.parseFloat(result.getTrip().getChance_of().getTempoverninety().
                getPercentage()));
        history.setChanceoftempoversixty(Float.parseFloat(result.getTrip().getChance_of().getTempoversixty().
                getPercentage()));
        return history;
    }

    /**
     * Helper method to build the http request
     *
     * @param location name of the place
     * @param tripStartDate start date of the trip
     * @param tripEndDate end date of the trip
     */
    private ResponseEntity<String> buildHistoryQuery(String location, Date tripStartDate, Date tripEndDate) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("MMdd");
        String start = format.format(tripStartDate);
        String end = format.format(tripEndDate);
        String query = BASE_URL + apiKey + "/planner_" + start + end + "/q/";
        return runQuery(query, location);
    }

    /**
     * Helper method to convert the location to proper url param
     *
     * @param location name of the place
     */
    private String convertLocation(String location) {
        if (location.contains(",")) {
            location = location.split(",")[1].trim() + "/" + location.split(",")[0].trim();
        }
        return location.replaceAll(" ", "_");
    }

    /**
     * Runs the actual query
     *
     * @param query the query URL
     * @param location the query URL
     */
    private ResponseEntity<String> runQuery(String query, String location) {
        String _location = convertLocation(location);
        String queryUrl = query + _location + ".json";
        log.debug("Running query - {}", queryUrl);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(queryUrl);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, new HttpEntity(headers),
                    String.class);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return response;
    }

}