package com.trip.planner.location;

import com.trip.planner.dto.Filter;
import com.trip.planner.tools.database.dto.GenericLocation;

/**
 * Created by Amir Keren on 31/08/2015.
 */
public interface IGenericLocation {

    GenericLocation toGenericLocation(Filter filter, String location);
    String getLocationId();
    String getLocationName();
    double getLocationRating();
    int getLocationReviewsNumber();
    String getLocationAddress();
    String getLocationWebsite();
    String getLocationType();
    String getLocationPriceLevel();

}