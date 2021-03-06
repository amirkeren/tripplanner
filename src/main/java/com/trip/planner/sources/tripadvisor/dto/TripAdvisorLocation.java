package com.trip.planner.sources.tripadvisor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trip.planner.dto.DataSource;
import com.trip.planner.dto.Filter;
import com.trip.planner.location.IGenericLocation;
import com.trip.planner.tools.database.dto.GenericLocation;
import com.trip.planner.utils.StringConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Amir Keren on 18/07/2015.
 * Class for a generic Trip Advisor Location object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripAdvisorLocation implements IGenericLocation {

	private String price_level;
	private String location_id;
	private String name;
	private String rating;
	private String rating_image_url;
	private String num_reviews;
	private String percent_recommended;
	private String location_string;
	private RankingData ranking_data;
	private Category category;
	private List<Category> subcategory;
	private List<Award> awards;
	private String latitude;
	private String longitude;
	private String web_url;
	private List<Ancestor> ancestors;
	private Address address_obj;
	private String see_all_photos;
	private String api_detail_url;
	private String write_review;

	public String getLocation_id() {
		return location_id;
	}

	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRating_image_url() {
		return rating_image_url;
	}

	public void setRating_image_url(String rating_image_url) {
		this.rating_image_url = rating_image_url;
	}

	public String getNum_reviews() {
		return num_reviews;
	}

	public void setNum_reviews(String num_reviews) {
		this.num_reviews = num_reviews;
	}

	public String getPercent_recommended() {
		return percent_recommended;
	}

	public void setPercent_recommended(String percent_recommended) {
		this.percent_recommended = percent_recommended;
	}

	public String getLocation_string() {
		return location_string;
	}

	public void setLocation_string(String location_string) {
		this.location_string = location_string;
	}

	public RankingData getRanking_data() {
		return ranking_data;
	}

	public void setRanking_data(RankingData ranking_data) {
		this.ranking_data = ranking_data;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getWeb_url() {
		return web_url;
	}

	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}

	public Address getAddress_obj() {
		return address_obj;
	}

	public void setAddress_obj(Address address_obj) {
		this.address_obj = address_obj;
	}

	public String getSee_all_photos() {
		return see_all_photos;
	}

	public void setSee_all_photos(String see_all_photos) {
		this.see_all_photos = see_all_photos;
	}

	public String getApi_detail_url() {
		return api_detail_url;
	}

	public void setApi_detail_url(String api_detail_url) {
		this.api_detail_url = api_detail_url;
	}

	public String getWrite_review() {
		return write_review;
	}

	public void setWrite_review(String write_review) {
		this.write_review = write_review;
	}

	@Override
	public GenericLocation toGenericLocation(Filter filter, String location) {
		return new GenericLocation(DataSource.TRIP_ADVISOR, getLocationId(), getLocationName(), getLocationRating(),
				getLocationReviewsNumber(), getLocationAddress(), getLocationWebsite(), getLocationPriceLevel(),
				getLocationType(), filter, location);
	}

	@Override
	public String getLocationId() {
		return getLocation_id();
	}

	@Override
	public String getLocationName() {
		return getName();
	}

	@Override
	public double getLocationRating() {
		return StringUtils.isNotBlank(getRating()) ? Double.parseDouble(getRating()) : 0.0;
	}

	@Override
	public int getLocationReviewsNumber() {
		return StringUtils.isNotBlank(getNum_reviews()) ? Integer.parseInt(getNum_reviews()) : 0;
	}

	@Override
	public String getLocationAddress() {
		return StringUtils.isNotBlank(getLocation_string()) ? getLocation_string() : null;
	}

	@Override
	public String getLocationWebsite() {
		return StringUtils.isNotBlank(getWeb_url()) ? getWeb_url() : StringConstants.NO_VALUE_DEFAULT;
	}

	@Override
	public String getLocationType() {
		if (getCategory() != null) {
			return StringUtils.isNotBlank(getCategory().getLocalized_name()) ?
					getCategory().getLocalized_name() : StringConstants.NO_VALUE_DEFAULT;
		}
		return StringConstants.NO_VALUE_DEFAULT;
	}

	@Override
	public String getLocationPriceLevel() {
		return getPrice_level();
	}

	public List<Category> getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(List<Category> subcategory) {
		this.subcategory = subcategory;
	}

	public List<Award> getAwards() {
		return awards;
	}

	public void setAwards(List<Award> awards) {
		this.awards = awards;
	}

	public List<Ancestor> getAncestors() {
		return ancestors;
	}

	public void setAncestors(List<Ancestor> ancestors) {
		this.ancestors = ancestors;
	}

	public String getPrice_level() {
		return price_level;
	}

	public void setPrice_level(String price_level) {
		this.price_level = price_level;
	}

}