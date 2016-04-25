package com.trip.planner.sources.foursquare.entities;

/**
 * Group of VenueHistories
 * 
 * @author Antti Leppä
 */
public class VenueHistoryGroup extends Group<VenueHistory> {

  private static final long serialVersionUID = 7288520717618562618L;

  @Override
  public VenueHistory[] getItems() {
    return items;
  }
  
  private VenueHistory[] items;
}
