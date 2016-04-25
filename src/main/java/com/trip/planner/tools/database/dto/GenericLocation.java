package com.trip.planner.tools.database.dto;

import com.trip.planner.dto.DataSource;
import com.trip.planner.dto.Filter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Amir Keren on 18/07/2015.
 */
@Entity
@Table(name="Locations")
public class GenericLocation {

	@Id
	@Column(name="id", nullable=false)
	private String id;
	@Column(name="address", nullable=false)
	private String address;

	private int frequency;
	private DataSource dataSource;
	private String name;
	private double rating;
	private int reviewNumber;
	private int reviewsNumber;
	private String website;
	private String type;
	private String priceLevel;
	private Filter filter;
	private String location;

	public GenericLocation() {
		this.frequency = 1;
	}

	public GenericLocation(DataSource dataSource, String id, String name, double rating, int reviewNumber,
						   String address, String website, String type, String priceLevel, Filter filter,
						   String location) {
		this.dataSource = dataSource;
		this.id = id + "-" + dataSource.name();
		this.name = name;
		this.rating = rating;
		this.reviewNumber = reviewNumber;
		this.address = address;
		this.website = website;
		this.type = type;
		this.priceLevel = priceLevel;
		this.frequency = 1;
		this.location = location;
		if (filter != Filter.all) {
			this.filter = filter;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GenericLocation) {
			GenericLocation location = (GenericLocation)obj;
			return (name.toLowerCase().contains(location.getName().toLowerCase()) ||
					location.getName().toLowerCase().contains(name.toLowerCase()) ||
					id.equals(location.getId()));
		}
		return false;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getReviewsNumber() {
		return reviewsNumber;
	}

	public void setReviewsNumber(int reviewsNumber) {
		this.reviewsNumber = reviewsNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPriceLevel() {
		return priceLevel;
	}

	public void setPriceLevel(String priceLevel) {
		this.priceLevel = priceLevel;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}