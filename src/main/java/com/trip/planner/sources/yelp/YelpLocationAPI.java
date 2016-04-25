package com.trip.planner.sources.yelp;

import com.trip.planner.dto.Filter;
import com.trip.planner.dto.Type;
import com.trip.planner.location.GenericLocationAPI;
import com.trip.planner.sources.yelp.dto.Business;
import com.trip.planner.sources.yelp.dto.SearchResponse;
import com.trip.planner.tools.database.dto.GenericLocation;
import com.trip.planner.utils.Utils;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code sample for accessing the Yelp TripPlanner V2.
 *
 * This program demonstrates the capability of the Yelp TripPlanner version 2.0 by using the Search TripPlanner to
 * query for businesses by a search term and location, and the Business TripPlanner to query additional
 * information about the top result from the search query.
 *
 * <p>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp Documentation</a> for more info.
 *
 */
public class YelpLocationAPI extends GenericLocationAPI {

	private Logger log = LoggerFactory.getLogger(YelpLocationAPI.class);

	@Autowired
	private Utils utils;

	private OAuthService service;
	private Token accessToken;

	private static final String SEARCH_LIMIT = "20";
	private static final String API_HOST = "api.yelp.com";
	private static final String SEARCH_PATH = "/v2/search";

	/**
	 * Setup the Yelp TripPlanner OAuth credentials.
	 *
	 * @param consumerKey Consumer key
	 * @param consumerSecret Consumer secret
	 * @param token Token
	 * @param tokenSecret Token secret
	 */
	public YelpLocationAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
		log.debug("Initializing Yelp TripPlanner");
		this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
						.apiSecret(consumerSecret).build();
		this.accessToken = new Token(token, tokenSecret);
		log.debug("Yelp TripPlanner Initialized");
	}

	/**
	 * Queries the Search TripPlanner based on the command line arguments and takes the first result to query
	 * the Business TripPlanner.
	 *
	 */
	@Override
	public List<GenericLocation> restAPI(String location, Type type, Filter filter) {
		List<GenericLocation> result = new ArrayList();
		try {
			String searchResponseJSON = searchForBusinessesByLocation(type.name(), location, convertFilter(filter),
					SEARCH_LIMIT + "");
			List<Business> businesses = utils.getMapper().readValue(searchResponseJSON, SearchResponse.class).
					getBusinesses();
			result.addAll(businesses.stream().map(tempLocation -> tempLocation.toGenericLocation(filter,
					utils.getUnifiedLocation(location))).collect(Collectors.toList()));
			return sortLocations(result);
		} catch (Exception ex) {
			log.error("Failed to get locations for {}", ex);
		}
		return result;
	}

	@Override
	protected String convertFilter(Filter filter) {
		switch (filter) {
			case adventure: return "active";
			case nightlife: return "nightlife";
			case activities: return "beautysvc,shopping";
			case sightseeing: return "tours";
			case culture: return "arts";
			case parks: return "parks";
			case all: return "all";
		}
		return null;
	}

	/**
	 * Creates and sends a request to the Search TripPlanner by term and location.
	 * <p>
	 * See <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search TripPlanner V2</a>
	 * for more info.
	 *
	 * @param term <tt>String</tt> of the search term to be queried
	 * @param location <tt>String</tt> of the location
	 * @return <tt>String</tt> JSON SearchResponse
	 */
	private String searchForBusinessesByLocation(String term, String location, String filter, String limit) {
		OAuthRequest request = createOAuthRequest(SEARCH_PATH);
		request.addQuerystringParameter("term", term);
		request.addQuerystringParameter("ll", utils.getCoordinatesFromLocation(location));
		request.addQuerystringParameter("limit", limit);
		if (!filter.equals("all")) {
			request.addQuerystringParameter("category_filter", filter);
		}
		request.addQuerystringParameter("sort", "2");
		return sendRequestAndGetResponse(request);
	}

	/**
	 * Creates and returns an {@link OAuthRequest} based on the TripPlanner endpoint specified.
	 *
	 * @param path TripPlanner endpoint to be queried
	 * @return <tt>OAuthRequest</tt>
	 */
	private OAuthRequest createOAuthRequest(String path) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
		return request;
	}

	/**
	 * Sends an {@link OAuthRequest} and returns the {@link Response} body.
	 *
	 * @param request {@link OAuthRequest} corresponding to the TripPlanner request
	 * @return <tt>String</tt> body of TripPlanner response
	 */
	private String sendRequestAndGetResponse(OAuthRequest request) {
		this.service.signRequest(this.accessToken, request);
		log.debug("Sending request - {}", request.getCompleteUrl());
		Response response = request.send();
		if (response != null && response.isSuccessful()) {
			log.debug("Request successful");
			return response.getBody();
		}
		log.error("Failed to get results");
		return null;
	}

}