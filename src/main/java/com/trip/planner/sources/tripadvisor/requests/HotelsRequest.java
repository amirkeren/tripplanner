package com.trip.planner.sources.tripadvisor.requests;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Amir Keren on 08/07/2015.
 * Class for Hotel Request object
 */
public class HotelsRequest extends Request {

	public enum SubCategory implements Category {
		hotel, bb, specialty
	}

	public HotelsRequest(SubCategory subcategory, String lang, String currency) {
		super(RequestType.hotels, lang, currency);
		if (subcategory != null && StringUtils.isNotBlank(subcategory.name())) {
			params.set("subcategory", subcategory.name());
		}
	}

}