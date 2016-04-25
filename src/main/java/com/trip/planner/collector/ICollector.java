package com.trip.planner.collector;

import com.trip.planner.dto.DataSource;
import com.trip.planner.dto.Filter;
import com.trip.planner.dto.Type;
import com.trip.planner.sources.foursquare.FoursquareApiException;
import com.trip.planner.tools.database.dto.GenericLocation;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Amir Keren on 05/08/2015.
 */
public interface ICollector {

    List<GenericLocation> getLocationsFromSingleSource(String location, Type type, Filter filter, DataSource source)
            throws IOException, ParseException, FoursquareApiException;
    List<GenericLocation> getLocationsFromAllSources(String location, Type type, Filter filter) throws IOException,
            ParseException, FoursquareApiException;

}