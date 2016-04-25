package com.trip.planner.sources.google.places;

import com.trip.planner.dto.Filter;
import com.trip.planner.dto.Type;
import com.trip.planner.location.GenericLocationAPI;
import com.trip.planner.tools.database.dto.GenericLocation;
import com.trip.planner.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Amir Keren on 18/07/2015.
 */
public class GooglePlacesLocationAPI extends GenericLocationAPI {

	private Logger log = LoggerFactory.getLogger(GooglePlacesLocationAPI.class);

	private final int SEARCH_LIMIT = GooglePlaces.MAXIMUM_RESULTS;
	private final double RADIUS_LIMIT = GooglePlaces.MAXIMUM_RADIUS;

	@Autowired
	private Utils utils;

	private GooglePlaces context;

	/**
	 * Initializes Google Places TripPlanner
	 *
	 * @param apiKey Google TripPlanner key
	 */
	public GooglePlacesLocationAPI(String apiKey) {
		log.debug("Initializing Google Places TripPlanner");
		context = new GooglePlaces(apiKey);
		log.debug("Google Maps TripPlanner Initialized");
	}

	public String getLocationCoordinates(String location) {
		log.debug("Searching for {} in Google Places", location);
		List<Place> places = context.getPlacesByQuery(location, 1);
		if (places != null && places.size() > 0) {
			log.error("Found {}", places.get(0).getName() + " - " + places.get(0).getAddress());
			return places.get(0).getLatitude() + "," + places.get(0).getLongitude();
		}
		log.error("Failed to find results");
		return null;
	}

	@Override
	public List<GenericLocation> restAPI(String location, Type type, Filter filter) {
		log.debug("Searching for {} in Google Places", location);
		List<Place> places = null;
		try {
			String coordinates = utils.getCoordinatesFromLocation(location);
			places = context.getNearbyPlaces(Double.parseDouble(coordinates.split(",")[0]),
					Double.parseDouble(coordinates.split(",")[1]), RADIUS_LIMIT, SEARCH_LIMIT,
					Param.name("type").value(handleTypes(type, filter)));
			if (places != null && places.size() > 0) {
				log.debug("Found {} places of requested type", places.size());
				List<GenericLocation> result = places.stream().map(tempLocation -> tempLocation.toGenericLocation(filter,
						utils.getUnifiedLocation(location))).collect(Collectors.toList());
				return sortLocations(result);
			}
		} catch (Exception ex) {
			log.error("Failed to find locations for {}", location);
		}
		log.error("Failed to find results");
		return new ArrayList();
	}

	@Override
	protected String convertFilter(Filter filter) {
		switch (filter) {
			case adventure: return Types.TYPE_CAMPGROUND;
			case nightlife: return Types.TYPE_BAR;
			case activities: return Types.TYPE_AMUSEMENT_PARK + "|" + Types.TYPE_AQUARIUM + "|" +
					Types.TYPE_BOWLING_ALLEY + "|" + Types.TYPE_CASINO + "|" + Types.TYPE_MOVIE_THEATER + "|" +
					Types.TYPE_SHOPPING_MALL + "|" + Types.TYPE_SPA + "|" + Types.TYPE_STADIUM + "|" + Types.TYPE_ZOO;
			case sightseeing: return Types.TYPE_CHURCH + "|" + Types.TYPE_ESTABLISHMENT + "|" +
					Types.TYPE_HINDU_TEMPLE + "|" + Types.TYPE_MOSQUE + "|" + Types.TYPE_PLACE_OF_WORSHIP + "|" +
					Types.TYPE_SYNAGOGUE + "|" + Types.TYPE_TRAVEL_AGENCY;
			case culture: return Types.TYPE_ART_GALLERY + "|" + Types.TYPE_LIBRARY + "|" + Types.TYPE_UNIVERSITY;
			case parks: return Types.TYPE_PARK;
			case all: {
				StringBuilder sb = new StringBuilder();
				for (Filter _filter: Filter.values()) {
					if (_filter == Filter.all) {
						continue;
					}
					sb.append(convertFilter(_filter) + "|");
				}
				String result = sb.toString();
				return result.substring(0, result.length() - 1);
			}
			/*case food: return Types.TYPE_RESTAURANT + "|" + Types.TYPE_BAKERY + "|" + Types.TYPE_CAFE + "|" +
					Types.TYPE_FOOD;
			case hotels: return Types.TYPE_LODGING + "|" + Types.TYPE_RV_PARK;*/
		}
		return null;
	}

	private String handleTypes(Type type, Filter filter) {
		String result;
		if (type == Type.restaurants) {
			result = Types.TYPE_RESTAURANT + "|" + Types.TYPE_BAKERY + "|" + Types.TYPE_CAFE +
					"|" + Types.TYPE_FOOD;
		} else if (type == Type.hotels) {
			result = Types.TYPE_LODGING + "|" + Types.TYPE_CAMPGROUND + "|" + Types.TYPE_RV_PARK;
		} else {
			result = convertFilter(filter);
		}
		try {
			return URLEncoder.encode(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return convertFilter(filter);
		}
	}

}