package com.trip.planner.sources.weather.dto;

import java.util.Date;

/**
 * Created by Amir Keren on 07/08/2015.
 */
public class WeatherHistory {

    private Date date;
    private int averageHighTemperature;
    private int averageLowTemperature;
    private float averagePrecipitation;
    private float chanceofhumidday;
    private float chanceofsultryday;
    private float chanceoftempoversixty;
    private float chanceoftempoverninety;
    private float chanceofprecip;
    private float chanceofrainday;
    private float chanceofsnowonground;
    private float chanceoftempbelowfreezing;
    private float chanceofhailday;
    private float chanceofsnowday;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getChanceofhumidday() {
        return chanceofhumidday;
    }

    public void setChanceofhumidday(float chanceofhumidday) {
        this.chanceofhumidday = chanceofhumidday;
    }

    public float getChanceofsultryday() {
        return chanceofsultryday;
    }

    public void setChanceofsultryday(float chanceofsultryday) {
        this.chanceofsultryday = chanceofsultryday;
    }

    public float getChanceoftempoversixty() {
        return chanceoftempoversixty;
    }

    public void setChanceoftempoversixty(float chanceoftempoversixty) {
        this.chanceoftempoversixty = chanceoftempoversixty;
    }

    public float getChanceoftempoverninety() {
        return chanceoftempoverninety;
    }

    public void setChanceoftempoverninety(float chanceoftempoverninety) {
        this.chanceoftempoverninety = chanceoftempoverninety;
    }

    public float getChanceofprecip() {
        return chanceofprecip;
    }

    public void setChanceofprecip(float chanceofprecip) {
        this.chanceofprecip = chanceofprecip;
    }

    public float getChanceofrainday() {
        return chanceofrainday;
    }

    public void setChanceofrainday(float chanceofrainday) {
        this.chanceofrainday = chanceofrainday;
    }

    public float getChanceofsnowonground() {
        return chanceofsnowonground;
    }

    public void setChanceofsnowonground(float chanceofsnowonground) {
        this.chanceofsnowonground = chanceofsnowonground;
    }

    public float getChanceoftempbelowfreezing() {
        return chanceoftempbelowfreezing;
    }

    public void setChanceoftempbelowfreezing(float chanceoftempbelowfreezing) {
        this.chanceoftempbelowfreezing = chanceoftempbelowfreezing;
    }

    public float getChanceofhailday() {
        return chanceofhailday;
    }

    public void setChanceofhailday(float chanceofhailday) {
        this.chanceofhailday = chanceofhailday;
    }

    public float getChanceofsnowday() {
        return chanceofsnowday;
    }

    public void setChanceofsnowday(float chanceofsnowday) {
        this.chanceofsnowday = chanceofsnowday;
    }

    public int getAverageHighTemperature() {
        return averageHighTemperature;
    }

    public void setAverageHighTemperature(int averageHighTemperature) {
        this.averageHighTemperature = averageHighTemperature;
    }

    public int getAverageLowTemperature() {
        return averageLowTemperature;
    }

    public void setAverageLowTemperature(int averageLowTemperature) {
        this.averageLowTemperature = averageLowTemperature;
    }

    public float getAveragePrecipitation() {
        return averagePrecipitation;
    }

    public void setAveragePrecipitation(float averagePrecipitation) {
        this.averagePrecipitation = averagePrecipitation;
    }

}