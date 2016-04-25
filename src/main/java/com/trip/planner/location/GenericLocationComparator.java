package com.trip.planner.location;

import com.trip.planner.tools.database.dto.GenericLocation;

import java.util.Comparator;

/**
 * Created by Amir Keren on 08/08/2015.
 */
public class GenericLocationComparator implements Comparator<GenericLocation> {

    /**
     * Generic location comparator, based on frequency, reviews, ratings etc.
     *
     * @param location1 first location object
     * @param location2 second location object
     */
    @Override
    public int compare(GenericLocation location1, GenericLocation location2) {
        if (location1.getFrequency() < location2.getFrequency()) {
            return 1;
        }
        if (location1.getFrequency() > location2.getFrequency()) {
            return -1;
        }
        if (location1.getReviewsNumber() == 0 && location2.getReviewsNumber() == 0) {
            return location1.getRating() <= location2.getRating() ? 1 : -1;
        }
        if (location1.getRating() == 0 && location2.getRating() == 0) {
            return location1.getReviewsNumber() <= location2.getReviewsNumber() ? 1 : -1;
        }
        if (location1.getReviewsNumber() <= location2.getReviewsNumber() &&
                location1.getRating() <= location2.getRating()) {
            return 1;
        }
        if (location1.getReviewsNumber() >= location2.getReviewsNumber() &&
                location1.getRating() >= location2.getRating()) {
            return -1;
        }
        if (location1.getReviewsNumber() <= location2.getReviewsNumber() - 500 &&
                location1.getRating() - 1 <= location2.getRating()) {
            return 1;
        }
        if (location1.getReviewsNumber() - 500 >= location2.getReviewsNumber() &&
                location1.getRating() >= location2.getRating() - 1) {
            return -1;
        }
        return location1.getRating() <= location2.getRating() ? 1 : -1;
    }

}