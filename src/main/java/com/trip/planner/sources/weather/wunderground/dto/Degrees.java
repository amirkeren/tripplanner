package com.trip.planner.sources.weather.wunderground.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amir Keren on 08/08/2015.
 */
public class Degrees {

    @JsonProperty("F")
    private String F;
    @JsonProperty("C")
    private String C;

    public String getC() {
        return C;
    }

    public void setC(String C) {
        this.C = C;
    }

    public String getF() {
        return F;
    }

    public void setF(String F) { this.F = F; }

}