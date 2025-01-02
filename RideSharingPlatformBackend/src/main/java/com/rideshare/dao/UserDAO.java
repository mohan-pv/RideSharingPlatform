package com.rideshare.dao;

import com.rideshare.model.User;
import com.rideshare.util.DBConnection;
import com.rideshare.util.QueryStrings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDAO {

    private static Logger logger = LogManager.getLogger(UserDAO.class);

    // Authenticating the user
    public User authenticateUser(String username, String password, String role) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DBConnection.getConnection();

            ps = conn.prepareStatement(QueryStrings.AUTHENTICATE_USER_QUERY);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            logger.error("Error in authenticateUser: " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return user;
    }

    // Register a new user
    public boolean registerUser(String username, String password, String role) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(QueryStrings.REGISTER_USER_QUERY);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error("Error in registerUser: {}", e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    public Integer getUserIdByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer userId = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(QueryStrings.GET_USER_ID_QUERY);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            logger.error("Error in getUserIdByUsername: {}", e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return userId;
    }

    // Check if already an user
    public Map<String, Object> alreadyAnUser(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> result = new HashMap<>();

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(QueryStrings.GET_USER_ID_QUERY);
            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next()) {
                result.put("userId", rs.getInt("user_id"));
                return result;
            }
        } catch (SQLException e) {
            logger.error("Error in alreadyAnUser: " + e);
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return null;
    }


    // common method for closing
    private  void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            logger.error("Error while closing resources: " + e);
        }
    }
}
