/*
 * FoursquareAPI - Foursquare API for Java
 * Copyright (C) 2008 - 2011 Antti Leppä / Foyt
 * http://www.foyt.fi
 * 
 * License: 
 * 
 * Licensed under GNU Lesser General Public License Version 3 or later (the "LGPL")
 * http://www.gnu.org/licenses/lgpl.html
 */

package com.trip.planner.sources.foursquare.entities;

import com.trip.planner.sources.foursquare.FoursquareEntity;

/**
 * Base class for all "count" entities
 * 
 * @author Antti Leppä
 */
public class Count implements FoursquareEntity {

  private static final long serialVersionUID = -471761138324979612L;

  /**
   * Returns count
   * 
   * @return count
   */
  public Long getCount() {
    return count;
  }
  
  private Long count;
}
