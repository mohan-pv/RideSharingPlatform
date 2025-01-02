package com.rideshare.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rideshare.util.DBConnection;
import com.rideshare.util.QueryStrings;

public class PassengerDAO {

    private static final Logger logger = LogManager.getLogger(PassengerDAO.class);

    public boolean savePassenger(String firstName, String lastName, String countryCode,String mobileNumber, String role,Integer userId) {
        Connection conn = null;
        try {
        	Date dt = new Date();
            conn = DBConnection.getConnection();
            String query = QueryStrings.INSERT_NEW_PASSENGER;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, mobileNumber);
            ps.setString(4, countryCode);
            ps.setDate(5, (java.sql.Date) dt);
            ps.setInt(6, userId);
            ps.executeUpdate();
            logger.info("Successfully registered passenger : {} with mobile number: {}", firstName, mobileNumber);
            return true;
        } catch (SQLException e) {
            logger.error("Error while saving passenger: {}", e.getMessage(), e);
            
        } finally {
            DBConnection.closeConnection(conn);
        }
        return false;
    }
    
    public boolean bookingRideInprogress(Integer sourceLocId,Integer destinationLocId, Integer passengerId, String status){
    	Connection conn = null;
    	try {
            conn = DBConnection.getConnection();
            String query = QueryStrings.INSERT_A_NEW_RIDE_IN_PASSENGER_RIDE_BOOOKINGS;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, sourceLocId);
            ps.setInt(2, destinationLocId);
            ps.setInt(3, passengerId);
            ps.setString(4, status);
            ps.setTimestamp(5, new Timestamp(new Date().getTime()));
            ps.executeUpdate();
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Successfully booked a ride for passengerId: {}", passengerId);
                return true;
            } else {
                logger.warn("Failed to book ride for passengerId: {}", passengerId);
                return false;
            }
    	}catch(SQLException e) {
    		logger.error("Error while booking ride in logs: {}", e.getMessage(), e);
    		return false;
    	}finally {
    		DBConnection.closeConnection(conn);
    	}
    	
    }
    
    
//    public boolean bookingRideInprogressLog(Integer sourceLocId,Integer destinationLocId, Integer passengerId, String status){
//    	Connection conn = null;
//    	try {
//    		Date dt = new Date();
//            conn = DBConnection.getConnection();
//            String query = QueryStrings.INSERT_A_NEW_RIDE_LOG;
//            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setInt(1, sourceLocId);
//            ps.setInt(2, destinationLocId);
//            ps.setInt(3, passengerId);
//            ps.setString(4, status);
//            ps.setDate(5, (java.sql.Date) dt);
//            ps.executeUpdate();
//            logger.info("Inserted in Logs for a new ride");
//            return true;
//    	}catch(SQLException e) {
//    		logger.error("Error while booking ride in logs: {}", e.getMessage(), e);
//    	}finally {
//    		DBConnection.closeConnection(conn);
//    	}
//    	return false;
//    }
}
