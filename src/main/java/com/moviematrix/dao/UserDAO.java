package com.moviematrix.dao;

import com.moviematrix.model.User;

import java.sql.SQLException;

public interface UserDAO {

    User findByEmail(String email) throws SQLException;

    User findById(int id) throws SQLException;

    int insert(User user) throws SQLException;

    boolean existsByEmail(String email) throws SQLException;
}
