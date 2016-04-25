package com.trip.planner.sources.tripadvisor.responses;

import com.trip.planner.sources.tripadvisor.dto.Map;

import java.util.List;

/**
 * Created by Amir Keren on 08/07/2015.
 */
public class MapResponse extends Response {

	private List<Map> data;

	public List<Map> getData() {
		return data;
	}

	public void setData(List<Map> data) {
		this.data = data;
	}

}