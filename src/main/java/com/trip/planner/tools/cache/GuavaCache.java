package com.trip.planner.tools.cache;

import com.google.appengine.repackaged.com.google.common.cache.CacheBuilder;
import com.google.appengine.repackaged.com.google.common.cache.CacheLoader;
import com.google.appengine.repackaged.com.google.common.cache.LoadingCache;

/**
 * Created by Amir Keren on 17/08/2015.
 */
public abstract class GuavaCache<T> implements ICache<T> {

    private LoadingCache<String, T> cache;

    public GuavaCache(int maximumSize) {
        cache = CacheBuilder.newBuilder().maximumSize(maximumSize).build(
             new CacheLoader<String, T>() {
                 public T load(String key) {
                     return (T)key;
                 }
             });
    }

    @Override
    public void setValue(String key, T value) {
        cache.put(key, value);
    }

    @Override
    public T getValue(String key) {
        return cache.getIfPresent(key);
    }

}