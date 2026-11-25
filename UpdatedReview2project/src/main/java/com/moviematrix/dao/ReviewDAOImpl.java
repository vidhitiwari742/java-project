package com.moviematrix.dao;

import com.moviematrix.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** JDBC implementation of {@link ReviewDAO} against the `reviews` table. */
public class ReviewDAOImpl implements ReviewDAO {

    @Override
    public List<Review> findByMovie(int movieId) throws SQLException {
        String sql = "SELECT * FROM reviews WHERE movie_id = ? ORDER BY created_at DESC";
        List<Review> reviews = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, movieId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reviews.add(mapRow(rs));
                }
            }
        }
        return reviews;
    }

    @Override
    public List<Review> findByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM reviews WHERE user_id = ? ORDER BY created_at DESC";
        List<Review> reviews = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reviews.add(mapRow(rs));
                }
            }
        }
        return reviews;
    }

    @Override
    public int insert(Review review) throws SQLException {
        String sql = "INSERT INTO reviews (user_id, movie_id, rating, comment) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getMovieId());
            ps.setDouble(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        }
    }

    private Review mapRow(ResultSet rs) throws SQLException {
        return new Review(
                rs.getInt("review_id"),
                rs.getInt("user_id"),
                rs.getInt("movie_id"),
                rs.getDouble("rating"),
                rs.getString("comment"),
                rs.getTimestamp("created_at"));
    }
}
