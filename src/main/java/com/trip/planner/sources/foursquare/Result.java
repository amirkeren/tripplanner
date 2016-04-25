package com.trip.planner.sources.foursquare;

import com.trip.planner.sources.foursquare.entities.notifications.Notification;

import java.util.List;

/**
 * Class representing TripPlanner query result
 * 
 * @author Antti Leppä
 *
 * @param <T> type of resulting entity
 */
public class Result<T> {

  /**
   * Constructor
   * 
   * @param meta status information
   * @param result result entity
   * @param notifications list of notifications
   */
  public Result(ResultMeta meta, T result, List<Notification<?>> notifications) {
    this.result = result;
    this.meta = meta;
    this.notifications = notifications;
  }
  
  /**
   * Constructor
   * 
   * @param meta status information
   * @param result result entity
   */
  public Result(ResultMeta meta, T result) {
    this(meta, result, null);
  }

  /**
   * Returns result entity
   * 
   * @return result entity
   */
  public T getResult() {
    return result;
  }
  
  /**
   * Returns request status information
   * 
   * @return request status information
   */
  public ResultMeta getMeta() {
    return meta;
  }
  
  /**
   * Returns list of notifications
   * 
   * @return list of notifications
   */
  public List<Notification<?>> getNotifications() {
    return notifications;
  }
  
  private T result;
  private ResultMeta meta;
  private List<Notification<?>> notifications;
}
