package com.trip.planner.sources.google.maps;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import com.trip.planner.tools.database.dto.GenericLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Amir Keren on 07/07/2015.
 */
public class GoogleMapsAPI {

	private Logger log = LoggerFactory.getLogger(GoogleMapsAPI.class);

	private final int LIMIT = 100; //this is the limit per query / 10 seconds

	private GeoApiContext context;

	public GoogleMapsAPI(String apiKey) {
		log.debug("Initializing Google Maps TripPlanner");
		context = new GeoApiContext().setApiKey(apiKey);
		log.debug("Google Maps TripPlanner Initialized");
	}

	public void addDistances(List<GenericLocation> locations, TravelMode travelMode) throws Exception {
		String[] origins = new String[Math.min(locations.size(), (int)Math.sqrt(LIMIT))];
		for (int i = 0; i < origins.length; i++) {
			origins[i] = locations.get(i).getAddress();
		}
		log.debug("Getting distance matrix for locations {}", origins);
		DistanceMatrix distanceMatrix = getDistanceMatrix(travelMode, origins, origins);
		//TODO - implement this
	}

	public List<GenericLocation> restAPI(GenericLocation origin, List<GenericLocation> locations,
										 TravelMode travelMode) throws Exception {
		String[] origins = new String[locations.size() + 1];
		origins[0] = origin.getAddress();
		for (int i = 1; i < origins.length; i++) {
			origins[i] = locations.get(i - 1).getAddress();
		}
		log.debug("Getting distance matrix for locations {}", origins);
		DistanceMatrix distanceMatrix = getDistanceMatrix(travelMode, origins, origins);
		Map<String, GenericLocation> locationMap = new HashMap();
		locationMap.put(distanceMatrix.originAddresses[0], origin);
		for (int i = 1; i < origins.length; i++) {
			locationMap.put(distanceMatrix.originAddresses[i], locations.get(i - 1));
		}
		return new RouteCalculator(distanceMatrix, locationMap).calculateShortestRoute(origin, locations);
	}

	private DistanceMatrix getDistanceMatrix(TravelMode travelMode, String[] origins, String[] destinations) throws Exception {
		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context)
				.origins(origins)
				.destinations(destinations)
				.mode(travelMode);
		return req.await();
	}

	private class RouteCalculator {

		private DistanceMatrix distanceMatrix;
		private Map<String, GenericLocation> locationMapper;
		private Set<List<String>> permutations;

		public RouteCalculator(DistanceMatrix distanceMatrix, Map<String, GenericLocation> locationMapper) {
			log.debug("Initializing Route Calculator");
			this.distanceMatrix = distanceMatrix;
			this.locationMapper = locationMapper;
			permutations = new HashSet();
			List<String> locations = new ArrayList(Arrays.asList(distanceMatrix.originAddresses));
			locations.remove(0);
			getPermutations(new ArrayList(), locations);
			log.debug("Route Calculator Initialized");
		}

		public List<GenericLocation> calculateShortestRoute(GenericLocation origin,
				List<GenericLocation> genericLocations) {
			log.debug("Calculating route");
			int distance = Integer.MAX_VALUE;
			List<String> path = null;
			for (List<String> permutation: permutations) {
				int sumDistance = calculateDistance(distanceMatrix.originAddresses[0], permutation.get(0));
				for (int i = 0; i < permutation.size() - 1; i++) {
					sumDistance += calculateDistance(permutation.get(i), permutation.get(i + 1));
				}
				sumDistance += calculateDistance(permutation.get(permutation.size() - 1),
						distanceMatrix.originAddresses[0]);
				if (sumDistance < distance) {
					distance = sumDistance;
					path = permutation;
					path.add(0, distanceMatrix.originAddresses[0]);
					path.add(path.size(), distanceMatrix.originAddresses[0]);
				}
			}
			log.debug("Found route with total distance {} - {}", distance, path);
			return getLocationsFromAddresses(path);
		}

		private List<GenericLocation> getLocationsFromAddresses(List<String> path) {
			List<GenericLocation> result = new ArrayList();
			for (String _path: path) {
				result.add(locationMapper.get(_path));
			}
			return result;
		}

		private int calculateDistance(String origin, String destination) {
			int originIndex = -1, destinationIndex = -1;
			for (int i = 0; i < distanceMatrix.originAddresses.length; i++) {
				if (distanceMatrix.originAddresses[i].equals(origin)) {
					originIndex = i;
				} else if (distanceMatrix.originAddresses[i].equals(destination)) {
					destinationIndex = i;
				}
				if (originIndex >= 0 && destinationIndex >= 0) {
					break;
				}
			}
			return (int)distanceMatrix.rows[originIndex].elements[destinationIndex].duration.inSeconds;
		}

		private void getPermutations(List<String> permutation, List<String> options) {
			if (options.isEmpty()) {
				permutations.add(permutation);
				return;
			}
			for (int i = 0; i < options.size(); i++) {
				List<String> _permutation = new ArrayList(permutation);
				_permutation.add(options.get(i));
				List<String> _options = new ArrayList();
				_options.addAll(options.subList(0, i));
				_options.addAll(options.subList(i + 1, options.size()));
				getPermutations(_permutation, _options);
			}
		}

	}

}