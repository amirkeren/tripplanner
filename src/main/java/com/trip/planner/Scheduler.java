package com.trip.planner;

import com.trip.planner.dto.Filter;
import com.trip.planner.tools.database.dao.GenericLocationDAO;
import com.trip.planner.tools.database.dto.CollectorData;
import com.trip.planner.tools.database.dto.GenericLocation;
import com.trip.planner.utils.GeoLocationInfo;
import com.trip.planner.utils.Utils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatHourlyForever;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Amir Keren on 13/07/2015.
 * Offline task to collect data
 */
public class Scheduler {

	private static Logger log = LoggerFactory.getLogger(Scheduler.class);

	private static final int DAILY_RATE_LIMIT = 1000;

	private static final int BATCH_SIZE = (DAILY_RATE_LIMIT / Filter.values().length) / 2; //number of locations to fetch per run
	private static final int FREQUENCY = 24; //run every <frequency> hours

	public static void main(String[] args) throws SchedulerException {
		log.debug("Initializing collector");
		org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		JobDetail jobDetail = newJob(Collector.class).build();
		Trigger trigger = newTrigger()
				.startNow()
				.withSchedule(repeatHourlyForever(FREQUENCY))
				.build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

	public static class Collector implements Job {

		@Override
		public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
			ApplicationContext context = new ClassPathXmlApplicationContext("file:resources/stage.xml");
			TripPlanner tripPlanner = context.getBean(TripPlanner.class);
			Utils utils = context.getBean(Utils.class);
			GenericLocationDAO dbObject = context.getBean(GenericLocationDAO.class);
			MongoOperations mongoOperations = context.getBean(MongoOperations.class);
			List<GeoLocationInfo> geoLocations = utils.getAllGeoLocations();
			Query query = new Query(Criteria.where(CollectorData.lastRunTimeFieldName).lt(new Date()));
			CollectorData collectorData = mongoOperations.findOne(query, CollectorData.class);
			int startIndex, lastSuccessfulIndex = -1;
			if (collectorData != null) {
				startIndex = collectorData.getLastRunIndex() + 1;
			} else {
				startIndex = 0;
				collectorData = new CollectorData(-1, new Date());
			}
			log.info("Start collecting from {}", startIndex);
			for (int i = startIndex; i - startIndex < BATCH_SIZE && i < geoLocations.size(); i++) {
				GeoLocationInfo geoLocationInfo = geoLocations.get(i);
				String location = geoLocationInfo.getCapital() + "," + geoLocationInfo.getState_code() + "," +
						geoLocationInfo.getCountry_code();
				log.info("Getting locations from {}", location);
				Set<GenericLocation> locations = null;
				try {
					locations = tripPlanner.getAllLocations(location);
				} catch (Exception ex) {
					log.error("Failed to get locations, error - {}", ex);
				}
				if (locations != null) {
					for (GenericLocation _location: locations) {
						try {
							dbObject.save(_location);
						} catch (Exception ex) {
							log.error("Failed to save in DB, error - {}", ex.getMessage());
						}
					}
				}
				lastSuccessfulIndex = i;
			}
			if (lastSuccessfulIndex >= geoLocations.size()) {
				lastSuccessfulIndex = -1;
			}
			collectorData.setLastRunIndex(lastSuccessfulIndex);
			mongoOperations.save(collectorData);
			log.info("Finished collecting");
		}

	}

}