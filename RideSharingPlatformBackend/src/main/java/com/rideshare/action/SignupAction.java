package com.rideshare.action;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.rideshare.service.PassengerService;
import com.rideshare.service.RiderService;
import com.rideshare.service.UserService;

public class SignupAction extends ActionSupport {
    private String firstName;
    private String lastName;
    private String countryCode;
    private String username;
    private String password;
    private String role;
    private static final Logger logger = LogManager.getLogger(SignupAction.class);

    private UserService userService = new UserService();
    private RiderService riderService = new RiderService();
    private PassengerService passengerService = new PassengerService();

    @Override
    public String execute() {
        try {
            if (isInputValid()) {
                
                logger.info("Signup attempt for username: {}, role: {}", username, role);
                HashMap<String, Object> res = userService.signupUser(username, password, role);
                String result = (String) res.get("role");
                Integer userId = (Integer) res.get("userId");

                if ("rider".equalsIgnoreCase(result)) {
                    logger.info("Registering user as Rider");
                    return riderService.saveRider(firstName, lastName, countryCode, username, role, userId);

                } else if ("passenger".equalsIgnoreCase(result)) {
                    logger.info("Registering user as Passenger");
                    return passengerService.savePassenger(firstName, lastName, countryCode, username, role, userId);

                } else if ("alreadyExists".equalsIgnoreCase(result)) {
                    logger.warn("Username {} already registered, redirecting to login page", username);
                    return "alreadyExists";
                    
                } else {
                    addActionError("Registration failed. Try again.");
                    logger.error("Registration failed for username: {}", username);
                    return ERROR;
                }
            } else {
                logger.error("Input validation failed: username, password, or role is null.");
                throw new NullPointerException("Input value is null");
            }
        } catch (Exception e) {
            logger.error("Exception during signup: {}", e.getMessage(), e);
        }
        return ERROR;
    }

    private boolean isInputValid() {
        return username != null && password != null && role != null;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
