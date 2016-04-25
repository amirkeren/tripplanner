package com.trip.planner.sources.weather.wunderground.dto;

/**
 * Created by Amir Keren on 08/08/2015.
 */
public class Temp {

    private Degrees min;
    private Degrees avg;
    private Degrees max;

    public Degrees getMin() {
        return min;
    }

    public void setMin(Degrees min) {
        this.min = min;
    }

    public Degrees getAvg() {
        return avg;
    }

    public void setAvg(Degrees avg) {
        this.avg = avg;
    }

    public Degrees getMax() {
        return max;
    }

    public void setMax(Degrees max) {
        this.max = max;
    }

}