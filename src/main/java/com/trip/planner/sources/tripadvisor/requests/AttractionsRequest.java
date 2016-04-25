package com.trip.planner.sources.tripadvisor.requests;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Amir Keren on 08/07/2015.
 * Class for Attraction Request object
 */
public class AttractionsRequest extends Request {

	public AttractionsRequest(String subcategory, String lang, String currency) {
		super(RequestType.attractions, lang, currency);
		if (subcategory != null && StringUtils.isNotBlank(subcategory)) {
			params.set("subcategory", subcategory);
		}
	}

}