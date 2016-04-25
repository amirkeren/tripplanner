package com.trip.planner.sources.foursquare.entities;

import com.trip.planner.sources.foursquare.FoursquareEntity;

/**
 * Class representing Reason entity
 * 
 * @author Antti Leppä
 */
public class Reason implements FoursquareEntity {

  private static final long serialVersionUID = -6388056927086935959L;

  /**
   * Returns type
   * 
   * @return type
   */
  public String getType() {
    return type;
  }
  
  /**
   * Returns message
   * 
   * @return message
   */
  public String getMessage() {
    return message;
  }
  
  private String type;
  private String message;
}