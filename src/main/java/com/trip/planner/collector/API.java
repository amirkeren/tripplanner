package com.trip.planner.collector;

import com.trip.planner.dto.DataSource;
import com.trip.planner.dto.Filter;
import com.trip.planner.dto.Type;
import com.trip.planner.sources.foursquare.FourSquareWrapperAPI;
import com.trip.planner.sources.foursquare.FoursquareApiException;
import com.trip.planner.sources.google.maps.GoogleMapsAPI;
import com.trip.planner.sources.google.places.GooglePlacesLocationAPI;
import com.trip.planner.sources.tripadvisor.TripAdvisorLocationAPI;
import com.trip.planner.sources.yelp.YelpLocationAPI;
import com.trip.planner.tools.database.dto.GenericLocation;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir Keren on 05/08/2015.
 */
public class API implements ICollector {

    private Logger log = LoggerFactory.getLogger(API.class);

    @Autowired
    private YelpLocationAPI yelpAPI;
    @Autowired
    private TripAdvisorLocationAPI tripAdvisorAPI;
    @Autowired
    private GoogleMapsAPI googleMapsAPI;
    @Autowired
    private GooglePlacesLocationAPI googlePlacesAPI;
    @Autowired
    private FourSquareWrapperAPI fourSquareAPI;

    public API() { log.debug("Initializing general sources API"); }

    @Override
    public List<GenericLocation> getLocationsFromSingleSource(String location, Type type, Filter filter,
            DataSource source) throws IOException, ParseException, FoursquareApiException {
        log.debug("Querying {}, {}, {} from {}", location, type, filter, source.name());
        switch (source) {
            case TRIP_ADVISOR: {
                return tripAdvisorAPI.restAPI(location, type, filter);
            }
            case YELP: {
                return yelpAPI.restAPI(location, type, filter);
            }
            case GOOGLE_PLACES: {
                return googlePlacesAPI.restAPI(location, type, filter);
            }
            case FOURSQUARE: {
                return fourSquareAPI.restAPI(location, type, filter);
            }
        }
        return null;
    }

    @Override
    public List<GenericLocation> getLocationsFromAllSources(String location, Type type, Filter filter)
            throws IOException, ParseException, FoursquareApiException {
        log.debug("Querying {}, {}, {} from all data sources", location, type, filter);
        List<GenericLocation> results = new ArrayList();
        for (DataSource source : DataSource.values()) {
            results.addAll(getLocationsFromSingleSource(location, type, filter, source));
        }
        log.debug("Found a total of {}", results.size());
        return results;
    }

}