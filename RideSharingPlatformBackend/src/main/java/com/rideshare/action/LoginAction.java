package com.rideshare.action;

import com.opensymphony.xwork2.ActionSupport;
import com.rideshare.model.User;
import com.rideshare.service.UserService;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

public class LoginAction extends ActionSupport implements SessionAware {
	
    private static final Logger logger = LogManager.getLogger(LoginAction.class);
    private String username;
    private String password;
    private String role;
    private Map<String, Object> session;
    
    private UserService userService = new UserService();

    @Override
    public String execute() {
    	User user = null;
    	try {
    		logger.info("Executing LoginAction for username: {}, role: {}", username, role);
    		
    		user = userService.authenticateUser(username, password, role);
    		
    	} catch (Exception e) {
    		logger.error("Exception during login execution: {}", e.getMessage(), e);
    		addActionError("An unexpected error occurred. Please try again.");
    		return ERROR;
    	}
        
        if (user != null) {
        	logger.info("Login successful for username: {}, role: {}", user.getUsername(), user.getRole());
        	
            session.put("username", user.getUsername());
            session.put("role", user.getRole());
            
            if ("rider".equalsIgnoreCase(user.getRole())) {
                return "rider";
            } else {
                return "passenger";
            }
        } else {
            addActionError("Invalid username or password.");
            logger.warn("Failed login attempt for username: {}", username);
            return ERROR;
        }
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
