package com.trip.planner.sources.weather.wunderground.dto;

/**
 * Created by Amir Keren on 08/08/2015.
 */
public class Chance {

    private String name;
    private String description;
    private String percentage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

}