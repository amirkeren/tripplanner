package com.trip.planner.tools.database.dao;

import com.trip.planner.dto.Filter;
import com.trip.planner.tools.database.dto.GenericLocation;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Amir Keren on 18/07/2015.
 */
public class GenericLocationDAO {

	private static Logger log = LoggerFactory.getLogger(GenericLocationDAO.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(GenericLocation genericLocation) {
		log.debug("Saving {}", genericLocation.getId());
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(genericLocation);
		tx.commit();
		log.debug("{} saved", genericLocation.getId());
		session.close();
	}

	public Set<GenericLocation> getPlacesByLocation(String location) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from GenericLocation where location = :l");
		query.setParameter("l", location);
		List<GenericLocation> locationList = query.list();
		session.close();
		return locationList == null || locationList.isEmpty() ? null : new HashSet(locationList);
	}

	public Set<GenericLocation> getPlacesByFilterAndLocation(Filter filter, String location) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from GenericLocation where filter = :f and location = :l");
		query.setParameter("f", filter);
		query.setParameter("l", location);
		List<GenericLocation> locationList = query.list();
		session.close();
		return locationList == null || locationList.isEmpty() ? null : new HashSet(locationList);
	}

}