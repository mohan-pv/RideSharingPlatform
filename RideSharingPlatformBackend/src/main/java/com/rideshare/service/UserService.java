package com.rideshare.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rideshare.dao.UserDAO;
import com.rideshare.model.User;

public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public HashMap<String, Object> signupUser(String userName, String password, String role) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            UserDAO userDAO = new UserDAO();
            Map<String, Object> userInfo = userDAO.alreadyAnUser(userName);
            
            if (userInfo != null && userInfo.containsKey("userId")) { 
                logger.warn("Username {} already registered", userName);
                result.put("role", "alreadyExists");
                
                return result;
            }

            boolean isRegistered = userDAO.registerUser(userName, password, role);

            if (isRegistered) {
                result.put("role", role);
                return result;
            }else {
            	result.put("role", "Error");
            	
            }
        } catch (Exception e) {
            logger.error("Exception during signup: " + e.getMessage(), e);
        }

        return result;
    }
    
    public User authenticateUser(String username, String password, String role) {
        User user = null;
        try {
            logger.info("Authenticating user with username: {}, role: {}", username, role);
            
            user = userDAO.authenticateUser(username, password, role);
            
            if (user != null) {
                logger.info("User authenticated successfully: {}", username);
            } else {
                logger.warn("Authentication failed for username: {}", username);
            }
        } catch (Exception e) {
            logger.error("Exception in authenticateUser: {}", e.getMessage(), e);
        }
        return user;
    }

}
