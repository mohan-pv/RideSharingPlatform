package com.rideshare.util;

public class QueryStrings {

    public static final String ALREADY_AN_USER_QUERY = "SELECT * FROM user_credentials WHERE username = ?";

    public static final String AUTHENTICATE_USER_QUERY = "SELECT * FROM user_credentials WHERE username = ? AND password = ? AND role = ?";
    
    public static final String REGISTER_USER_QUERY = "INSERT INTO user_credentials (username, password, role) VALUES (?, ?, ?)";
    
    public static final String INSERT_NEW_RIDER = "INSERT INTO rider (first_name,last_name,mobile_number,countrycode,created_at,user_id) VALUES (?,?,?,?,?,?)";
    
    public static final String INSERT_NEW_PASSENGER = "INSERT INTO passenger (first_name,last_name,mobile_number,countrycode,created_at,user_id) VALUES (?,?,?,?,?,?)";
    
    public static final String GET_USER_ID_QUERY = "SELECT user_id FROM user_credentials WHERE username = ?";
    
    public static final String INSERT_A_NEW_RIDE_IN_PASSENGER_RIDE_BOOOKINGS = "INSERT INTO passenger_ride_bookings (source_location_id,destination_location_id,status,created_at) VALUES (?,?,?,?)";
    
    public static final String INSERT_A_NEW_RIDE_IN_PASSENGER_RIDE_BOOOKINGS_LOG = "INSERT INTO passenger_ride_bookings_log (source_location_id,destination_location_id,status,modified_at,ride_id,created_at) VALUES (?,?,?,?,?,?)";
    
    public static final String DISPLAY_ACTIVE_RIDES_FOR_RIDER = "SELECT source_location_id as sourceLocId,destination_location_id as destinationLocId from passenger_ride_bookings where status='new'";
    
    public static final String UPDATE_PASSENGER_RIDE_BOOKINGS_STATUS = "UPDATE passeneger_ride_bookings SET status = ? WHERE ride_id = ?";
    
    public static final String INSERT_NEW_RIDE_DETAILS = "INSERT INTO ride_details (passenger_ride_id,rider_id,status) VALUES (?,?,?)";
    
    public static final String UPDATE_RIDE_STATUS_OF_PASSENGER = "SELECT source_location_id as sourceLocId,destination_location_id as destinationLocId from passenger_ride_bookings where status='new' AND ride_id = ?";
    
    public static final String UPDATE_DROP_CONFIRMATION_OF_RIDE = "UPDATE ride_details SET status = ? WHERE ride_id = ?";
    
}
