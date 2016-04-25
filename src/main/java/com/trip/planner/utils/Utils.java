package com.trip.planner.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.planner.sources.google.places.GooglePlacesLocationAPI;
import com.trip.planner.tools.cache.ICache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Amir Keren on 13/07/2015.
 */
public class Utils {

	private Logger log = LoggerFactory.getLogger(Utils.class);

	@Autowired
	private GooglePlacesLocationAPI googlePlacesAPI;

	private ICache<GeoLocationInfo> locationToGeoLocationInfoCache;
	private ICache<String> locationToCoordinatesCache;

	private ObjectMapper mapper;
	private GeoLocations geoLocationsAmericas;
	private GeoLocations geoLocationsGlobal;
	private List<GeoLocationInfo> allGeoLocations;

	public Utils() throws IOException {
		log.debug("Initializing Utils class");
		mapper = new ObjectMapper();
		ClassLoader classLoader = getClass().getClassLoader();
		log.debug("Reading geo location info");
		geoLocationsAmericas = mapper.readValue(new File(classLoader.
				getResource("geolocations/geolocations-americas.json").getFile()), GeoLocations.class);
		geoLocationsGlobal = mapper.readValue(new File(classLoader.
				getResource("geolocations/geolocations.json").getFile()), GeoLocations.class);
		allGeoLocations = new ArrayList();
		allGeoLocations.addAll(geoLocationsAmericas.getData());
		allGeoLocations.addAll(geoLocationsGlobal.getData());
		for (GeoLocationInfo geoLocationInfo: allGeoLocations) {
			geoLocationInfo.setLocation(geoLocationInfo.getCapital() + "," + geoLocationInfo.getState_code() + "," +
					geoLocationInfo.getCountry_code());
		}
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public List<GeoLocationInfo> getAllGeoLocations() {
		return allGeoLocations;
	}

	public String getUnifiedLocation(String location) {
		GeoLocationInfo geoLocationInfo = getGeoLocationInfoFromLocation(location);
		return geoLocationInfo != null ? geoLocationInfo.getLocation() : null;
	}

	private GeoLocationInfo getGeoLocationInfoFromLocation(String location) {
		//search for city name
		GeoLocationInfo geoLocationInfo = locationToGeoLocationInfoCache.getValue(location);
		if (geoLocationInfo != null) {
			return geoLocationInfo;
		}
		Stream<GeoLocationInfo> cities = allGeoLocations.stream().filter(geoLocation -> {
			if (geoLocation.getCapital().trim().isEmpty()) {
				return location.toLowerCase().contains(geoLocation.getCountry().toLowerCase());
			}
			return location.toLowerCase().contains(geoLocation.getCapital().toLowerCase());
		});
		List<GeoLocationInfo> filtered = cities.collect(Collectors.toList());
		//if cities were found in the db file
		if (filtered.size() > 0) {
			//if just one city was found, return its geolocation info
			if (filtered.size() == 1) {
				locationToGeoLocationInfoCache.setValue(location, filtered.get(0));
				return filtered.get(0);
			} else {
				//more than one city found - search for country, state etc.
				cities = allGeoLocations.stream().filter(geoLocation -> (location.toLowerCase().contains(geoLocation.
						getCountry().toLowerCase()) || location.toLowerCase().contains(geoLocation.getCountry_code().
						toLowerCase()) || location.toLowerCase().contains(geoLocation.getState_code().toLowerCase())));
				filtered = cities.collect(Collectors.toList());
				//if just one result found, return its geolocation info
				if (filtered.size() == 1) {
					locationToGeoLocationInfoCache.setValue(location, filtered.get(0));
					return filtered.get(0);
				}
			}
		}
		return null;
	}

	public String getCoordinatesFromLocation(String location) {
		String coordinates = locationToCoordinatesCache.getValue(location);
		if (coordinates != null) {
			return coordinates;
		}
		GeoLocationInfo geoLocationInfo = getGeoLocationInfoFromLocation(location);
		if (geoLocationInfo != null) {
			coordinates = geoLocationInfo.getCapital_latitude() + "," + geoLocationInfo.getCapital_longitude();
		} else {
			//no relevant results found in db file, use google places api to get the coordinates
			coordinates = googlePlacesAPI.getLocationCoordinates(location);
			//TODO - update geolocation db?
		}
		locationToCoordinatesCache.setValue(location, coordinates);
		return coordinates;
	}

	public void setLocationToGeoLocationInfoCache(ICache<GeoLocationInfo> locationToGeoLocationInfoCache) {
		this.locationToGeoLocationInfoCache = locationToGeoLocationInfoCache;
	}

	public void setLocationToCoordinatesCache(ICache<String> locationToCoordinatesCache) {
		this.locationToCoordinatesCache = locationToCoordinatesCache;
	}

	public boolean isNumeric(String str) {
		String pattern = "^[0-9]+$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		return m.matches();
	}

}