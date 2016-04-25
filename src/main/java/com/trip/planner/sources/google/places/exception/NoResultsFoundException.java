package com.trip.planner.sources.google.places.exception;

import com.trip.planner.sources.google.places.Statuses;

public class NoResultsFoundException extends GooglePlacesException {
    public NoResultsFoundException(String errorMessage) {
        super(Statuses.STATUS_ZERO_RESULTS, errorMessage);
    }

    public NoResultsFoundException() {
        this(null);
    }
}
