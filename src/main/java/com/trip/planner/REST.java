package com.trip.planner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.trip.planner.dto.Filter;
import com.trip.planner.dto.Trip;
import com.trip.planner.sources.foursquare.FoursquareApiException;
import com.trip.planner.sources.google.maps.GoogleMapsAPI;
import com.trip.planner.tools.cache.ICache;
import com.trip.planner.tools.database.dao.GenericLocationDAO;
import com.trip.planner.tools.database.dto.GenericLocation;
import com.trip.planner.utils.Utils;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import spark.Request;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Amir Keren on 10/08/2015.
 */
public class REST {

    private Logger log = LoggerFactory.getLogger(REST.class);

    @Autowired
    private Utils utils;
    @Autowired
    private GoogleMapsAPI googleMapsAPI;
    @Autowired
    private TripPlanner tripPlanner;
    @Autowired
    private GenericLocationDAO genericLocationDAO;
    @Autowired
    private ICache<Set<GenericLocation>> dbCache;

    public String planTrip(Request request) throws IOException, ParseException,
            JAXBException, FoursquareApiException {
        String location = request.queryParams("location");
        String from = request.queryParams("from");
        String to = request.queryParams("to");
        //TODO - add budget
        String budget = "";
        //String budget = request.queryParams("budget");
        String activities = request.queryParams("activities");
        String adventure = request.queryParams("adventure");
        String culture = request.queryParams("culture");
        String nightlife = request.queryParams("nightlife");
        String sightseeing = request.queryParams("sightseeing");
        if (!validParams(location, from, to, budget, activities, adventure, culture, nightlife, sightseeing)) {
            return "error";
        }
        location = location.toLowerCase();
        log.debug("location param: {}", location);
        Date fromDate = Date.from(Instant.ofEpochSecond(Long.parseLong(from)));
        Date toDate = Date.from(Instant.ofEpochSecond(Long.parseLong(to)));
        log.debug("dates params: {}, {}", fromDate, toDate);
        Map<Filter, Integer> priorities = new HashMap();
        priorities.put(Filter.activities, Integer.parseInt(activities));
        priorities.put(Filter.adventure, Integer.parseInt(adventure));
        priorities.put(Filter.culture, Integer.parseInt(culture));
        priorities.put(Filter.nightlife, Integer.parseInt(nightlife));
        priorities.put(Filter.sightseeing, Integer.parseInt(sightseeing));
        log.debug("priorities param: {}", priorities);
        String result;
        try {
            log.debug("Running query");
            Trip trip = tripPlanner.getTrip(location, fromDate, toDate, budget, priorities);
            result = utils.getMapper().writerFor(new TypeReference<Trip>() {}).
                    writeValueAsString(trip);
        } catch (Exception ex) {
            log.error("error generating trip", ex);
            result = "error";
        }
        return result;
    }

    private boolean validParams(String location, String from, String to, String budget, String activities,
                                       String adventure, String culture, String nightlife, String sightseeing) {
        if (location == null || from == null || to == null || budget == null || activities == null ||
                adventure == null || culture == null || nightlife == null || sightseeing == null) {
            log.error("missing required parameters:");
            if (location == null) {
                log.error("location");
            }
            if (from == null) {
                log.error("from");
            }
            if (to == null) {
                log.error("to");
            }
            if (budget == null) {
                log.error("budget");
            }
            if (activities == null) {
                log.error("activities");
            }
            if (adventure == null) {
                log.error("adventure");
            }
            if (culture == null) {
                log.error("culture");
            }
            if (nightlife == null) {
                log.error("nightlife");
            }
            if (sightseeing == null) {
                log.error("sightseeing");
            }
            return false;
        }
        if (!utils.isNumeric(from) || !utils.isNumeric(to) || !utils.isNumeric(activities) ||
                !utils.isNumeric(adventure) || !utils.isNumeric(culture) || !utils.isNumeric(nightlife) ||
                !utils.isNumeric(sightseeing)) {
            log.error("invalid required parameters:");
            if (!utils.isNumeric(from)) {
                log.error("from");
            }
            if (!utils.isNumeric(to)) {
                log.error("to");
            }
            if (!utils.isNumeric(activities)) {
                log.error("activities");
            }
            if (!utils.isNumeric(adventure)) {
                log.error("adventure");
            }
            if (!utils.isNumeric(culture)) {
                log.error("culture");
            }
            if (!utils.isNumeric(nightlife)) {
                log.error("nightlife");
            }
            if (!utils.isNumeric(sightseeing)) {
                log.error("sightseeing");
            }
            return false;
        }
        return true;
    }

    public void setDbCache(ICache<Set<GenericLocation>> dbCache) {
        this.dbCache = dbCache;
    }

}