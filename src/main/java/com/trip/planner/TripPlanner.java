package com.trip.planner;

import com.trip.planner.collector.ICollector;
import com.trip.planner.dto.DataSource;
import com.trip.planner.dto.Filter;
import com.trip.planner.dto.Trip;
import com.trip.planner.dto.Type;
import com.trip.planner.location.GenericLocationComparator;
import com.trip.planner.sources.foursquare.FoursquareApiException;
import com.trip.planner.tools.database.dto.GenericLocation;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by Amir Keren on 07/07/2015.
 */
public class TripPlanner {

	private Logger log = LoggerFactory.getLogger(TripPlanner.class);

	private ICollector collector;

	/**
	 * Generate trip
	 *
	 * @param location name of the city
	 * @param from start date of the trip
	 * @param to end date of the trip
	 * @param budget budget available for the trip
	 * @param priorities ranking of priorities
	 */
	public Trip getTrip(String location, Date from, Date to, String budget,
										 Map<Filter, Integer> priorities) {
		//TODO - implement
		Trip trip = new Trip();
		return trip;
	}

	/**
	 * Search for the location in all available sources by all available filters
	 *
	 * @param location name of the city
	 */
	public Set<GenericLocation> getAllLocations(String location) throws ParseException, IOException,
			FoursquareApiException {
		log.debug("Fetching locations from all available sources by all available filters and types");
		Set<GenericLocation> result = new HashSet();
		//for (Type type: Type.values()) {
			for (Filter filter: Filter.values()) {
				if (filter == Filter.all) {
					continue;
				}
				result.addAll(collector.getLocationsFromAllSources(location, Type.attractions, filter));
			}
		//}
		return result;
	}

	/**
	 * Search for the location in all available sources
	 *
	 * @param location name of the city
	 * @param type type of location to search
	 * @param numberOfResults the limit of results to return
	 * @param doSort flag to sort or not to sort
	 */
	public List<GenericLocation> getLocationsFromAllSources(String location, Type type, Filter filter,
				int numberOfResults, boolean doSort) throws ParseException, IOException, FoursquareApiException {
		log.debug("Fetching locations from all available sources");
		List<GenericLocation> result = collector.getLocationsFromAllSources(location, type, filter);
		return doSort ? sortLocations(result, numberOfResults, true) : result;
	}

	/**
	 * Search for the location in a given source
	 *
	 * @param location name of the city
	 * @param type type of location to search
	 * @param source type of data source to search
	 * @param numberOfResults the limit of results to return
	 * @param doSort flag to sort or not to sort
	 */
	public List<GenericLocation> getLocationsFromSingleSource(String location, Type type, Filter filter,
				  DataSource source, int numberOfResults, boolean doSort) throws ParseException, IOException, FoursquareApiException {
		log.debug("Fetching locations from {}", source.name());
		List<GenericLocation> result = collector.getLocationsFromSingleSource(location, type, filter, source);
		return doSort ? sortLocations(result, numberOfResults, false) : result;
	}

	/**
	 * Sort the list of locations found
	 *
	 * @param locations list of locations
	 * @param numberOfResults the limit of results to return
	 * @param doCrossReference flag indicating whether or not to calculate frequency
	 */
	private List<GenericLocation> sortLocations(List<GenericLocation> locations, int numberOfResults,
											   boolean doCrossReference) {
		List<GenericLocation> result = new ArrayList();
		if (doCrossReference) {
			for (GenericLocation location : locations) {
				int index = result.indexOf(location);
				if (index > -1) {
					GenericLocation temp = result.get(index);
					if (temp.getDataSource() != location.getDataSource()) {
						temp.setFrequency(temp.getFrequency() + 1);
					}
				} else {
					result.add(location);
				}
			}
		} else {
			result.addAll(locations);
		}
		result.sort(new GenericLocationComparator());
		if (numberOfResults < 0) {
			return result;
		}
		return result.subList(0, numberOfResults);
	}

	public void setCollector(ICollector collector) {
		this.collector = collector;
	}

}