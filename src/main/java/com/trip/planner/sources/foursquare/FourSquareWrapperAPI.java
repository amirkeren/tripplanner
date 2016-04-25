package com.trip.planner.sources.foursquare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.planner.dto.Filter;
import com.trip.planner.dto.Type;
import com.trip.planner.location.GenericLocationAPI;
import com.trip.planner.sources.foursquare.entities.Category;
import com.trip.planner.sources.foursquare.entities.VenuesSearchResult;
import com.trip.planner.tools.database.dto.GenericLocation;
import com.trip.planner.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Amir Keren on 01/08/2015.
 */
public class FourSquareWrapperAPI extends GenericLocationAPI {

    private Logger log = LoggerFactory.getLogger(FourSquareWrapperAPI.class);

    @Autowired
    private Utils utils;

    private final int SEARCH_LIMIT = 1000;
    private final int MAX_RADIUS = 10000;

    private FoursquareApi foursquareApi;
    private List<Category> categories;

    public FourSquareWrapperAPI(String clientId, String clientSecret) throws IOException {
        log.debug("Initializing FourSquare TripPlanner");
        foursquareApi = new FoursquareApi(clientId, clientSecret, "");
        ClassLoader classLoader = getClass().getClassLoader();
        categories = new ObjectMapper().readValue(new File(classLoader.getResource("foursquare-categories.json").
                getFile()), List.class);
    }

    @Override
    public List<GenericLocation> restAPI(String location, Type type, Filter filter) {
        log.debug("Searching for venues in location {}", location);
        Result<VenuesSearchResult> venues = null;
        try {
            if (type == Type.attractions && filter != Filter.all) {
                venues = foursquareApi.venuesSearch(utils.getCoordinatesFromLocation(location), null, null, null, null,
                        SEARCH_LIMIT, null, convertFilter(filter), null, null, null, MAX_RADIUS, null);
            } else {
                venues = foursquareApi.venuesSearch(null, null, null, null, type.name(), SEARCH_LIMIT, null, null, null,
                        null, null, null, location);
            }
        } catch (Exception ex) {
            log.error("Failed to get locations for {}", location);
        }
        if (venues != null && venues.getResult() != null && venues.getResult().getVenues() != null &&
                venues.getResult().getVenues().length > 0) {
            List<GenericLocation> result = Arrays.asList(venues.getResult().getVenues()).stream().
                    map(tempLocation -> tempLocation.toGenericLocation(filter, utils.getUnifiedLocation(location))).
                    collect(Collectors.toList());
            return sortLocations(result);
        }
        return new ArrayList();
    }

    @Override
    protected String convertFilter(Filter filter) {
        //category codes are here - https://developer.foursquare.com/categorytree
        switch (filter) {
            case adventure: return "4d4b7105d754a06377d81259";
            case nightlife: return "52e81612bcbc57f1066b79ef,5032792091d4c4b30a586d5c,4bf58dd8d48988d18e941735,4bf58dd8d48988d1e5931735,4bf58dd8d48988d1f2931735,4bf58dd8d48988d1e3931735,52e81612bcbc57f1066b79ec,4d4b7105d754a06376d81259";
            case activities: return "4bf58dd8d48988d17f941735,52e81612bcbc57f1066b79eb,52e81612bcbc57f1066b79e6,52e81612bcbc57f1066b79ea,4bf58dd8d48988d1f1931735,52e81612bcbc57f1066b79e8,4bf58dd8d48988d17c941735,4bf58dd8d48988d1e4931735,4bf58dd8d48988d1e1931735,4fceea171983d5d06c3e9823,4bf58dd8d48988d1f4931735,52e81612bcbc57f1066b79e9,4bf58dd8d48988d184941735,4bf58dd8d48988d182941735,4bf58dd8d48988d193941735,4bf58dd8d48988d17b941735,4d4b7105d754a06373d81259";
            case sightseeing: return "4deefb944765f83613cdba6e";
            case culture: return "4bf58dd8d48988d181941735,4bf58dd8d48988d1e2931735,507c8c4091d498d9fc8c67a9,4d4b7105d754a06372d81259";
            case parks: return "52e81612bcbc57f1066b7a21,52e81612bcbc57f1066b7a13,4bf58dd8d48988d163941735,4bf58dd8d48988d162941735";
            /*case food: return "4d4b7105d754a06374d81259";
            case hotels: return "4bf58dd8d48988d1fa931735";*/
        }
        return null;
    }

}