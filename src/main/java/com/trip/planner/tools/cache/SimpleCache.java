package com.trip.planner.tools.cache;

/**
 * Created by Amir Keren on 12/09/2015.
 */
public class SimpleCache extends GuavaCache<String> {

    public SimpleCache(int maximumSize) {
        super(maximumSize);
    }

}