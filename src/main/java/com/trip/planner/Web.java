package com.trip.planner;

import com.trip.planner.sources.foursquare.FoursquareApiException;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static spark.Spark.*;

/**
 * Created by Amir Keren on 13/07/2015.
 * Web web server class
 */
public class Web {

	private static Logger log = LoggerFactory.getLogger(Web.class);

	private static final int DEFAULT_PORT = 5000;

	public static void main(String[] args) throws IOException, ParseException, JAXBException, FoursquareApiException {
		log.debug("Initializing web server");
		REST rest = new ClassPathXmlApplicationContext("file:resources/stage.xml").getBean(REST.class);
		String portStr = System.getenv("PORT");
		//if running locally
		if (portStr == null) {
			port(DEFAULT_PORT);
		} else {
			port(Integer.parseInt(portStr));
		}
		staticFileLocation("/public");
		get("/planTrip", (req, res) -> rest.planTrip(req));
		log.debug("Web server initialized");
	}

}