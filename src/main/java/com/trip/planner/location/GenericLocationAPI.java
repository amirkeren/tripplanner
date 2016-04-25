package com.trip.planner.location;

import com.trip.planner.dto.Filter;
import com.trip.planner.dto.Type;
import com.trip.planner.tools.database.dto.GenericLocation;

import java.util.List;

/**
 * Created by Amir Keren on 02/08/2015.
 */
public abstract class GenericLocationAPI {

    protected abstract List<GenericLocation> restAPI(String location, Type type, Filter filter);

    protected abstract String convertFilter(Filter filter);

    protected List<GenericLocation> sortLocations(List<GenericLocation> locations) {
        //default implementation - do nothing
        return locations;
    }

}