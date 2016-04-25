package com.trip.planner.tools.cache;

import com.trip.planner.TripPlanner;
import com.trip.planner.tools.database.dao.GenericLocationDAO;
import com.trip.planner.tools.database.dto.GenericLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by Amir Keren on 14/09/2015.
 */
public class DBCache implements ICache<Set<GenericLocation>> {

    private Logger log = LoggerFactory.getLogger(DBCache.class);

    @Autowired
    private GenericLocationDAO dbObject;
    @Autowired
    private TripPlanner tripPlanner;

    @Override
    public void setValue(String location, Set<GenericLocation> locations) {
        if (locations != null) {
            for (GenericLocation _location: locations) {
                try {
                    dbObject.save(_location);
                } catch (Exception ex) {
                    log.error("Failed to save in DB, error - {}", ex.getMessage());
                }
            }
        }
    }

    @Override
    public Set<GenericLocation> getValue(String location) {
        Set<GenericLocation> locations = dbObject.getPlacesByLocation(location);
        if (locations == null) {
            try {
                locations = tripPlanner.getAllLocations(location);
            } catch (Exception ex) {
                log.error("Failed to get locations, error - {}", ex);
            }
            setValue(location, locations);
        }
        return locations;
    }

}