package com.trip.planner.sources.tripadvisor.requests;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Created by Amir Keren on 31/07/2015.
 * Class for generic Trip Advisor Request object
 */
public class Request {

    protected interface Category {}
    protected MultiValueMap<String, String> params;
    protected RequestType requestType;

    public enum RequestType {
        attractions, hotels, restaurants
    }

    public Request(RequestType requestType, String lang, String currency) {
        this.requestType = requestType;
        params = new LinkedMultiValueMap<>();
        if (StringUtils.isNotBlank(lang)) {
            params.set("lang", lang);
        }
        if (StringUtils.isNotBlank(currency)) {
            params.set("currency", currency);
        }
    }

    public MultiValueMap<String, String> getParams() { return params; }

    public String getRequestType() { return requestType.name(); }

}