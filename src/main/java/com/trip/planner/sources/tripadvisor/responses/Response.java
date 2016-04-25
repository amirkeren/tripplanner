package com.trip.planner.sources.tripadvisor.responses;

import com.trip.planner.sources.tripadvisor.dto.Paging;

/**
 * Created by Amir Keren on 18/07/2015.
 */
public class Response {

	private Paging paging;

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}