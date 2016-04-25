package com.trip.planner.sources.foursquare.io;

import com.trip.planner.sources.foursquare.entities.Category;

import java.util.List;

/**
 * Created by Amir Keren on 23/08/2015.
 */
public class CategoryTree {

    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}