package com.trip.planner.sources.yelp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trip.planner.dto.DataSource;
import com.trip.planner.dto.Filter;
import com.trip.planner.location.IGenericLocation;
import com.trip.planner.tools.database.dto.GenericLocation;
import com.trip.planner.utils.StringConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Amir Keren on 13/07/2015.
 * Class for Business object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Business implements IGenericLocation {

	private String id;
	private Boolean is_claimed;
	private Boolean is_closed;
	private String name;
	private String image_url;
	private String url;
	private String mobile_url;
	private String phone;
	private String display_phone;
	private Integer review_count;
	private String[][] categories;
	private Integer rating;
	private String rating_img_url;
	private String rating_img_url_small;
	private String rating_img_url_large;
	private String snippet_text;
	private String snippet_image_url;
	private Location location;
	private String menu_provider;
	private Integer menu_date_updated;
	private List<GiftCertificate> gift_certificates;
	private List<Deal> deals;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean is_claimed() {
		return is_claimed;
	}

	public void setIs_claimed(Boolean is_claimed) {
		this.is_claimed = is_claimed;
	}

	public Boolean is_closed() {
		return is_closed;
	}

	public void setIs_closed(Boolean is_closed) {
		this.is_closed = is_closed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMobile_url() {
		return mobile_url;
	}

	public void setMobile_url(String mobile_url) {
		this.mobile_url = mobile_url;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDisplay_phone() {
		return display_phone;
	}

	public void setDisplay_phone(String display_phone) {
		this.display_phone = display_phone;
	}

	public Integer getReview_count() {
		return review_count;
	}

	public void setReview_count(Integer review_count) {
		this.review_count = review_count;
	}

	public String[][] getCategories() {
		return categories;
	}

	public void setCategories(String[][] categories) {
		this.categories = categories;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getRating_img_url() {
		return rating_img_url;
	}

	public void setRating_img_url(String rating_img_url) {
		this.rating_img_url = rating_img_url;
	}

	public String getRating_img_url_small() {
		return rating_img_url_small;
	}

	public void setRating_img_url_small(String rating_img_url_small) {
		this.rating_img_url_small = rating_img_url_small;
	}

	public String getRating_img_url_large() {
		return rating_img_url_large;
	}

	public void setRating_img_url_large(String rating_img_url_large) {
		this.rating_img_url_large = rating_img_url_large;
	}

	public String getSnippet_text() {
		return snippet_text;
	}

	public void setSnippet_text(String snippet_text) {
		this.snippet_text = snippet_text;
	}

	public String getSnippet_image_url() {
		return snippet_image_url;
	}

	public void setSnippet_image_url(String snippet_image_url) {
		this.snippet_image_url = snippet_image_url;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getMenu_provider() {
		return menu_provider;
	}

	public void setMenu_provider(String menu_provider) {
		this.menu_provider = menu_provider;
	}

	public Integer getMenu_date_updated() {
		return menu_date_updated;
	}

	public void setMenu_date_updated(Integer menu_date_updated) {
		this.menu_date_updated = menu_date_updated;
	}

	@Override
	public GenericLocation toGenericLocation(Filter filter, String location) {
		return new GenericLocation(DataSource.YELP, getLocationId(), getLocationName(), getLocationRating(),
				getLocationReviewsNumber(), getLocationAddress(), getLocationWebsite(), getLocationPriceLevel(),
				getLocationType(), filter, location);
	}

	@Override
	public String getLocationId() {
		return getId();
	}

	@Override
	public String getLocationName() {
		return getName();
	}

	@Override
	public double getLocationRating() {
		return getRating() != null ? getRating().doubleValue(): 0.0;
	}

	@Override
	public int getLocationReviewsNumber() {
		return review_count != null ? review_count : 0;
	}

	@Override
	public String getLocationAddress() {
		if (getLocation() != null && getLocation().getAddress() != null && getLocation().getAddress().length > 0) {
			if (getLocation().getDisplay_address().length >= 3) {
				return getLocation().getDisplay_address()[0] + ", " + getLocation().getDisplay_address()[2];
			} else if (getLocation().getDisplay_address().length >= 2) {
				return getLocation().getDisplay_address()[0] + ", " + getLocation().getDisplay_address()[1];
			} else {
				return getLocation().getDisplay_address()[0];
			}
		}
		return null;
	}

	@Override
	public String getLocationWebsite() {
		return StringUtils.isNotBlank(getUrl()) ? getUrl() : StringConstants.NO_VALUE_DEFAULT;
	}

	@Override
	public String getLocationType() {
		if (getCategories() != null) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < getCategories().length; i++) {
				if (getCategories()[i].length > 0) {
					sb.append(getCategories()[i][0]).append(",");
				}
			}
			String result = sb.toString();
			return result.length() > 0 ? result.substring(0, result.length() - 1) : StringConstants.NO_VALUE_DEFAULT;
		}
		return StringConstants.NO_VALUE_DEFAULT;
	}

	@Override
	public String getLocationPriceLevel() {
		return "NONE";
	}

	public List<Deal> getDeals() {
		return deals;
	}

	public void setDeals(List<Deal> deals) {
		this.deals = deals;
	}

	public List<GiftCertificate> getGift_certificates() {
		return gift_certificates;
	}

	public void setGift_certificates(List<GiftCertificate> gift_certificates) {
		this.gift_certificates = gift_certificates;
	}

}