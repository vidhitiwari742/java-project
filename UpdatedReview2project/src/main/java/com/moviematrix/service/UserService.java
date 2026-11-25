package com.moviematrix.service;

import com.moviematrix.dao.UserDAO;
import com.moviematrix.dao.UserDAOImpl;
import com.moviematrix.model.User;
import com.moviematrix.util.PasswordUtil;

import java.sql.SQLException;

/** Handles registration and authentication. Shared by servlets and the Swing GUI. */
public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this(new UserDAOImpl());
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User register(String name, String email, String rawPassword) throws SQLException {
        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            throw new IllegalArgumentException("Name and email are required");
        }
        if (rawPassword == null || rawPassword.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if (userDAO.existsByEmail(email)) {
            throw new IllegalArgumentException("An account with this email already exists");
        }
        String hash = PasswordUtil.hash(rawPassword);
        User user = new User(0, name, email, hash);
        int id = userDAO.insert(user);
        user.setId(id);
        return user;
    }

    /** Returns the authenticated user, or null if credentials are invalid. */
    public User authenticate(String email, String rawPassword) throws SQLException {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            return null;
        }
        return PasswordUtil.matches(rawPassword, user.getPasswordHash()) ? user : null;
    }
}
