package com.rideshare.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rideshare.util.DBConnection;
import com.rideshare.util.QueryStrings;

public class RiderDAO {

    private static final Logger logger = LogManager.getLogger(RiderDAO.class);

    public void saveRider(String firstName, String lastName, String countryCode,String mobileNumber, String role,Integer userId) {
        Connection conn = null;
        try {
        	Date dt = new Date();
            conn = DBConnection.getConnection();
            String query = QueryStrings.INSERT_NEW_RIDER;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, mobileNumber);
            ps.setString(4, countryCode);
            ps.setDate(5, (java.sql.Date) dt);
            ps.setInt(6, userId);
            ps.executeUpdate();
            logger.info("Successfully registered rider: {} with mobile number: {}", firstName, mobileNumber);
        } catch (SQLException e) {
            logger.error("Error while saving rider: {}", e.getMessage(), e);
        } finally {
            DBConnection.closeConnection(conn);
        }
    }
    
    public Map<String, Object> displayActiveRides(){
        Connection conn = null;
        Map<String, Object> activeRides = new HashMap<>();
        try {
            conn = DBConnection.getConnection();
            String query = QueryStrings.DISPLAY_ACTIVE_RIDES_FOR_RIDER;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> rideDetails = new HashMap<>();
                rideDetails.put("sourceLocId", rs.getInt("sourceLocId"));
                rideDetails.put("destinationLocId", rs.getInt("destinationLocId"));
            }
        } catch (SQLException e) {
            logger.error("Error while fetching active rides: {}", e.getMessage(), e);
        } finally {
            DBConnection.closeConnection(conn);
        }
        return activeRides;
    }
	
	public Map<String, Object> acceptRideForRideId(Integer rideId) {

	    Connection conn = null;
	    Map<String, Object> result = new HashMap<>();
	    
	    try {
	        conn = DBConnection.getConnection();
	        String query = QueryStrings.INSERT_NEW_RIDE_DETAILS;
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, rideId);

	        int affectedRows = ps.executeUpdate();

	        if (affectedRows > 0) {
	            result.put("status", true);
	            result.put("message", "Ride accepted successfully.");
	        } else {
	            result.put("status", false);
	            result.put("message", "No ride found with the provided rideId.");
	        }

	    } catch (SQLException e) {
	        logger.error("Error while accepting ride: {}", e.getMessage(), e);
	        result.put("status", false);
	        result.put("message", "Error occurred while accepting the ride.");
	    } finally {
	        DBConnection.closeConnection(conn);
	    }
	    
	    return result;
	}
	
	public void updateRideStatusOfPassenger(Integer rideId, String status) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            String query = QueryStrings.UPDATE_PASSENGER_RIDE_BOOKINGS_STATUS;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, rideId);
            ps.executeUpdate();
            logger.info("Successfully updated status as {} for the ride Id {}",status,rideId);
        } catch (SQLException e) {
            logger.error("Error while saving rider: {}", e.getMessage(), e);
        } finally {
            DBConnection.closeConnection(conn);
        }
    }
	
	public Map<String, Object> dropConfirmationForRideId(Integer rideId) {
	    Connection conn = null;
	    Map<String, Object> result = new HashMap<>();
	    
	    try {
	        conn = DBConnection.getConnection();
	        String query = QueryStrings.UPDATE_DROP_CONFIRMATION_OF_RIDE;
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, rideId);

	        int affectedRows = ps.executeUpdate();

	        if (affectedRows > 0) {
	            result.put("status", true);
	            result.put("message", "Drop confirmation updated successfully for the ride.");
	        } else {
	            result.put("status", false);
	            result.put("message", "Not able to confirm the drop for ride");
	        }

	        logger.info("Drop confirmation status for ride Id {}: {}", rideId, result.get("status"));
	    } catch (SQLException e) {
	        logger.error("Error while updating drop confirmation: {}", e.getMessage(), e);
	        result.put("status", false);
	        result.put("message", "Error occurred while updating drop confirmation.");
	    } finally {
	        DBConnection.closeConnection(conn);
	    }
	    
	    return result;
	}


    
}
