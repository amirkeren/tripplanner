package com.trip.planner.tools.cache;

/**
 * Created by Amir Keren on 24/07/2015.
 */
public interface ICache<T> {

	public void setValue(String key, T value);

	public T getValue(String key);

}