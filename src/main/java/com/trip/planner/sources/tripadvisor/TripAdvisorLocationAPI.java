package com.trip.planner.sources.tripadvisor;

import com.trip.planner.dto.Filter;
import com.trip.planner.dto.Type;
import com.trip.planner.location.GenericLocationAPI;
import com.trip.planner.sources.tripadvisor.dto.TripAdvisorLocation;
import com.trip.planner.sources.tripadvisor.requests.AttractionsRequest;
import com.trip.planner.sources.tripadvisor.requests.Request;
import com.trip.planner.sources.tripadvisor.responses.MapResponse;
import com.trip.planner.sources.tripadvisor.responses.TripAdvisorResponse;
import com.trip.planner.tools.cache.ICache;
import com.trip.planner.tools.database.dto.GenericLocation;
import com.trip.planner.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Amir Keren on 08/07/2015.
 */
public class TripAdvisorLocationAPI extends GenericLocationAPI {

	private Logger log = LoggerFactory.getLogger(TripAdvisorLocationAPI.class);

	@Autowired
	private Utils utils;

	private ICache coordinatesToLocationIdCache;
	private ICache coordinatesToLocationIdBlacklistCache;

	private RestTemplate restTemplate;
	private HttpHeaders headers;
	private String apiKey;

	public TripAdvisorLocationAPI(String apiKey) {
		log.debug("Initializing Trip Advisor TripPlanner");
		this.apiKey = apiKey;
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		log.debug("Trip Advisor TripPlanner Initialized");
	}

	/**
	 * Gets the top 10 most popular locations depending on the type passed in the request
	 *
	 * @param location name of the place
	 * @param request request, including optional filters (use null if no filters required)
	 */
	private List<TripAdvisorLocation> getLocations(String location, Request request) throws IOException {
		log.debug("Getting list of locations for {}", location);
		ResponseEntity<String> response = runQuery(location, request);
		if (response != null && response.getStatusCode() == HttpStatus.OK) {
			TripAdvisorResponse result = utils.getMapper().readValue(response.getBody().getBytes(),
					TripAdvisorResponse.class);
			log.debug("Found {} locations", result.getData().size());
			return result.getData();
		}
		log.error("Failed to get list of locations");
		if (response != null) {
			log.error("Response - {}", response.toString());
		}
		return null;
	}

	/**
	 * Gets the Trip Advisor locationId from the given coordinates
	 *
	 * @param coordinates A string in the format of "longitude,latitude"
	 */
	private String getLocationIdFromCoordinates(String coordinates) throws IOException {
		log.debug("Getting location id for {}", coordinates);
		Object location = coordinatesToLocationIdCache.getValue(coordinates);
		if (location != null) {
			return (String)location;
		}
		location = coordinatesToLocationIdBlacklistCache.getValue(coordinates);
		if (location != null) {
			log.error("Failed to get id from coordinates - coordinates blacklisted");
			return null;
		}
		String queryUrl = "http://api.tripadvisor.com/api/partner/2.0/map/" + coordinates +
				"?key=" + apiKey;
		ResponseEntity<String> response = runQueryAux(queryUrl, null);
		if (response != null && response.getStatusCode() == HttpStatus.OK) {
			MapResponse result = utils.getMapper().readValue(response.getBody().getBytes(), MapResponse.class);
			if (result.getData().size() > 0) {
				String locationId = result.getData().get(0).getAncestors().get(0).getLocation_id();
				coordinatesToLocationIdCache.setValue(coordinates, locationId);
				return locationId;
			}
		}
		log.error("Failed to get id from coordinates");
		if (response != null) {
			log.error("Response - {}", response.toString());
		}
		coordinatesToLocationIdBlacklistCache.setValue(coordinates, new Object());
		return null;
	}

	/**
	 * Converts the location to locationId, builds the URL and sends the request
	 *
	 * @param location name of the place
	 * @param request request, including optional filters (use null if no filters required)
	 */
	private ResponseEntity<String> runQuery(String location, Request request) throws IOException {
		String locationId;
		String coordinates = utils.getCoordinatesFromLocation(location);
		if (!utils.isNumeric(location)) {
			locationId = getLocationIdFromCoordinates(coordinates);
		} else {
			locationId = location;
		}
		String queryUrl = "http://api.tripadvisor.com/api/partner/2.0/location/" + locationId + "/" +
				request.getRequestType() + "?key=" + apiKey;
		return runQueryAux(queryUrl, request);
	}

	/**
	 * Runs the actual query
	 *
	 * @param queryUrl the query URL
	 * @param request request, including optional filters (use null if no filters required)
	 */
	private ResponseEntity<String> runQueryAux(String queryUrl, Request request) {
		log.debug("Running query - {}", queryUrl);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(queryUrl);
		if (request != null) {
			builder.queryParams(request.getParams());
		}
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, new HttpEntity(headers), String.class);
		} catch (Exception ex) {
			log.error(ex.toString());
		}
		return response;
	}

	@Override
	public List<GenericLocation> restAPI(String location, Type type, Filter filter) {
		List<GenericLocation> result = new ArrayList();
		try {
			List<TripAdvisorLocation> tripAdvisorLocations;
			if (type == Type.attractions && filter != Filter.all) {
				tripAdvisorLocations = getLocations(location, new AttractionsRequest(convertFilter(filter), null, null));
			} else {
				tripAdvisorLocations = getLocations(location, new Request(Request.RequestType.valueOf(type.name()), null,
						null));
			}
			result.addAll(tripAdvisorLocations.stream().map(tempLocation -> tempLocation.toGenericLocation(filter,
					utils.getUnifiedLocation(location))).collect(Collectors.toList()));
			return sortLocations(result);
		} catch (Exception ex) {
			log.error("Failed to get locations for {}", location);
		}
		return new ArrayList();
	}

	@Override
	protected String convertFilter(Filter filter) {
		switch (filter) {
			case adventure: return "adventure,sports,ranch_farm,gear_rentals";
			case nightlife: return "nightlife,bars,clubs";
			case activities: return "activities,amusement,zoos_aquariums,performances,wellness_spas,shopping";
			case sightseeing: return "sightseeing_tours,landmarks";
			case culture: return "cultural,museums";
			case parks: return "outdoors";
			//case food: return "food_drink";
		}
		return null;
	}

	public void setCoordinatesToLocationIdCache(ICache coordinatesToLocationIdCache) {
		this.coordinatesToLocationIdCache = coordinatesToLocationIdCache;
	}

	public void setCoordinatesToLocationIdBlacklistCache(ICache coordinatesToLocationIdBlacklistCache) {
		this.coordinatesToLocationIdBlacklistCache = coordinatesToLocationIdBlacklistCache;
	}

}