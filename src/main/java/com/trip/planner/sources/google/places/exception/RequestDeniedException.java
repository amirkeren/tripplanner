package com.trip.planner.sources.google.places.exception;

import com.trip.planner.sources.google.places.Statuses;

public class RequestDeniedException extends GooglePlacesException {
    public RequestDeniedException(String errorMessage) {
        super(Statuses.STATUS_REQUEST_DENIED, errorMessage);
    }

    public RequestDeniedException() {
        this(null);
    }
}