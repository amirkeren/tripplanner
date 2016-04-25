package com.trip.planner.sources.weather.wunderground.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Amir Keren on 08/08/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDay {

    private DateInfo date;
    private LongDegrees high;
    private LongDegrees low;
    private String conditions;
    private SmallAmount qpf_allday;
    private Amount snow_allday;
    private int avehumidity;

    public DateInfo getDate() {
        return date;
    }

    public void setDate(DateInfo date) {
        this.date = date;
    }

    public LongDegrees getHigh() {
        return high;
    }

    public void setHigh(LongDegrees high) {
        this.high = high;
    }

    public LongDegrees getLow() {
        return low;
    }

    public void setLow(LongDegrees low) {
        this.low = low;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public SmallAmount getQpf_allday() {
        return qpf_allday;
    }

    public void setQpf_allday(SmallAmount qpf_allday) {
        this.qpf_allday = qpf_allday;
    }

    public Amount getSnow_allday() {
        return snow_allday;
    }

    public void setSnow_allday(Amount snow_allday) {
        this.snow_allday = snow_allday;
    }

    public int getAvehumidity() {
        return avehumidity;
    }

    public void setAvehumidity(int avehumidity) {
        this.avehumidity = avehumidity;
    }

}