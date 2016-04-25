package com.trip.planner.sources.weather.wunderground.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Amir Keren on 08/08/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trip {

    private Temp temp_high;
    private Temp temp_low;
    private Precipitation precip;
    private Chances chance_of;

    public Temp getTemp_high() {
        return temp_high;
    }

    public void setTemp_high(Temp temp_high) {
        this.temp_high = temp_high;
    }

    public Temp getTemp_low() {
        return temp_low;
    }

    public void setTemp_low(Temp temp_low) {
        this.temp_low = temp_low;
    }

    public Precipitation getPrecip() {
        return precip;
    }

    public void setPrecip(Precipitation precip) {
        this.precip = precip;
    }

    public Chances getChance_of() {
        return chance_of;
    }

    public void setChance_of(Chances chance_of) {
        this.chance_of = chance_of;
    }

}