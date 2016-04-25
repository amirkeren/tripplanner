package com.trip.planner.tools.database.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Amir Keren on 27/07/2015.
 */

@Document(collection = "collector-data")
public class CollectorData {

	public final static String lastRunIndexFieldName = "lastRunIndex";
	public final static String lastRunTimeFieldName = "lastRunTime";

	@Id
	private String id;
	private int lastRunIndex;
	private Date lastRunTime;

	public CollectorData(int lastRunIndex, Date lastRunTime) {
		this.lastRunIndex = lastRunIndex;
		this.lastRunTime = lastRunTime;
	}

	public int getLastRunIndex() {
		return lastRunIndex;
	}

	public void setLastRunIndex(int lastRunIndex) {
		this.lastRunIndex = lastRunIndex;
	}

	public Date getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(Date lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

}