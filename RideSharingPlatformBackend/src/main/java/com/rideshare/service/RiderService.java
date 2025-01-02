package com.rideshare.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rideshare.dao.RiderDAO;
import com.rideshare.util.StringConstants;

public class RiderService {

	private static final Logger logger = LogManager.getLogger(RiderService.class);
	private RiderDAO riderDAO;

	public RiderService() {
		this.riderDAO = new RiderDAO();
	}
	
	int coreCount = Runtime.getRuntime().availableProcessors();
	
	int poolSize = Math.max(4, coreCount*2);
	
	public static void main(String[] args) {
		RiderService r  = new RiderService();
		System.out.println("poolSize is "+r.poolSize);
	}
	
	private ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

	public String saveRider(String firstName, String lastName, String countryCode, String mobileNumber, String role, Integer userId) {
		try {
			riderDAO.saveRider(firstName, lastName, countryCode, mobileNumber, role, userId);
			logger.info("Rider {} saved successfully", mobileNumber);
			return "rider";
		} catch (Exception e) {
			logger.error("Exception in saveRider: {}", e.getMessage());
			return "ERROR";
		}
	}

	public Map<String, Object> displayActiveRides() {
		Map<String, Object> activeRides = new HashMap<>();
		try {
			logger.info("Displaying active Rides");
			activeRides = riderDAO.displayActiveRides();
			if (activeRides.isEmpty()) {
				activeRides.put("message", StringConstants.NO_DATA_FOUND);
				activeRides.put("status", true);
			} else {
				activeRides.put("status", true);
			}

		} catch (Exception e) {
			logger.error("Exception in bookingRide: {}", e.getMessage(), e);
			activeRides.put("status", false);
			activeRides.put("message", StringConstants.SERVER_EXCEPTION_MESSAGE);
		}
		return activeRides;
	}

	public Map<String, Object> acceptRide(Integer rideId) {
		Map<String, Object> acceptRide = new HashMap<>();
		try {

			logger.info("Accepting ride for rideId {}", rideId);
			acceptRide = riderDAO.acceptRideForRideId(rideId);

			if (acceptRide.get("status").equals(true)) {
				String status = "inprogress";
				executorService.submit(() -> {
					asyncUpdateStatusOfPassanger(rideId, status); // need to update the home page of the passenger to show, that rider accepted and also need to show the details of the rider
					asyncAcceptRideConfirmationPage(rideId);
				});
			}
			// need to make sure to update the page for rider as well. will configure based on action and jsp file
		} catch (Exception e) {

			logger.error("Exception in bookingRide: {}", e.getMessage(), e);
			acceptRide.put("status", false);
			acceptRide.put("message", StringConstants.SERVER_EXCEPTION_MESSAGE);
		}

		return acceptRide;

	}

	public void asyncUpdateStatusOfPassanger(Integer rideId, String status) {
		try {
			riderDAO.updateRideStatusOfPassenger(rideId, status); // update the status to inprogress in DB for the passenger_ride_bookings
		} catch (Exception e) {

		}
	}

	public Map<String, Object> dropConfirmationForRideId(Integer rideId) {
		Map<String, Object> dropConf = new HashMap<>();
		try {
			logger.info("Dropping Confirmation for the rideId {}", rideId);
			dropConf = riderDAO.dropConfirmationForRideId(rideId);
			if (dropConf.get("status").equals(true)) {
				String status = "completed";
				executorService.submit(() -> {
					asyncUpdateStatusOfPassanger(rideId, status); // need to update the home page of the passenger to show, that rider accepted and also need to show the details of the rider
					asyncDropConfirmationPage(rideId);
				});
			}
			// need to make sure to update the page for rider as well. will configure based on action and jsp file
		} catch (Exception e) {

			logger.error("Exception in dropConfirmationForRideId: {}", e.getMessage(), e);
			dropConf.put("status", false);
			dropConf.put("message", StringConstants.SERVER_EXCEPTION_MESSAGE);
		}
		return dropConf;
	}

	public void asyncAcceptRideConfirmationPage(Integer rideId) { // confirmation page for passenger , will be updating rider directly in the response

	}

	public void asyncDropConfirmationPage(Integer rideId) { // Drop page for passenger , will be updating rider directly in the response

	}

}
