package com.rideshare.service;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rideshare.dao.PassengerDAO;
import com.rideshare.util.StringConstants;

public class PassengerService {

    private static final Logger logger = LogManager.getLogger(PassengerService.class);
    private PassengerDAO passengerDAO;

    public PassengerService() {
        this.passengerDAO = new PassengerDAO();
    }

    public String savePassenger(String firstName, String lastName, String countryCode, String mobileNumber, String role, Integer userId) {
        try {
            boolean res = passengerDAO.savePassenger(firstName, lastName, countryCode, mobileNumber, role, userId);
            logger.info("Passenger {} saved successfully", mobileNumber);
            if(res) {
            	return "passenger";
            }else {
            	return "ERROR";
            }
            
        } catch (Exception e) {
            logger.error("Exception in savePassenger: {}", e.getMessage());
            return "ERROR";
        }
    }
    
    public HashMap<String, Object> bookingRide(Integer sourceLocId, Integer destinationLocId, Integer passengerId, String status) {
        HashMap<String, Object> resultMap = new HashMap<>();
        try {
            logger.info("Booking ride for passengerId: {}, sourceLocId: {}, destinationLocId: {}", passengerId, sourceLocId, destinationLocId);
            
            boolean rideBooked = passengerDAO.bookingRideInprogress(sourceLocId, destinationLocId, passengerId, status);
            resultMap.put("status", rideBooked);
            
            if (rideBooked) {
                resultMap.put("message", StringConstants.RIDE_CONFIRMATION_MESSAGE_FOR_PASSENGER);
            } else {
                resultMap.put("message", StringConstants.SERVER_EXCEPTION_MESSAGE);
            }
        } catch (Exception e) {
            logger.error("Exception in bookingRide: {}", e.getMessage(), e);
            resultMap.put("status", false);
            resultMap.put("message", StringConstants.SERVER_EXCEPTION_MESSAGE);
        }
        return resultMap;
    }
    
}

