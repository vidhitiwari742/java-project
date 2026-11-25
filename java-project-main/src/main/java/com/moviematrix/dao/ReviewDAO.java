package com.moviematrix.dao;

import com.moviematrix.model.Review;

import java.sql.SQLException;
import java.util.List;

public interface ReviewDAO {

    List<Review> findByMovie(int movieId) throws SQLException;

    List<Review> findByUser(int userId) throws SQLException;

    int insert(Review review) throws SQLException;
}
