package com.trip.planner.sources.weather.wunderground.dto;

/**
 * Created by Amir Keren on 08/08/2015.
 */
public class Precipitation {

    private Amount min;
    private Amount avg;
    private Amount max;

    public Amount getMin() {
        return min;
    }

    public void setMin(Amount min) {
        this.min = min;
    }

    public Amount getAvg() {
        return avg;
    }

    public void setAvg(Amount avg) {
        this.avg = avg;
    }

    public Amount getMax() {
        return max;
    }

    public void setMax(Amount max) {
        this.max = max;
    }

}