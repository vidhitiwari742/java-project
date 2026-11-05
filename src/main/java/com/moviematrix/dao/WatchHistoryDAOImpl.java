package com.moviematrix.dao;

import com.moviematrix.model.WatchHistoryEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** JDBC implementation of {@link WatchHistoryDAO} against the `watch_history` table. */
public class WatchHistoryDAOImpl implements WatchHistoryDAO {

    @Override
    public List<WatchHistoryEntry> findByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM watch_history WHERE user_id = ? ORDER BY watched_at DESC";
        List<WatchHistoryEntry> entries = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    entries.add(new WatchHistoryEntry(
                            rs.getInt("history_id"),
                            rs.getInt("user_id"),
                            rs.getInt("movie_id"),
                            rs.getTimestamp("watched_at")));
                }
            }
        }
        return entries;
    }

    @Override
    public void record(int userId, int movieId) throws SQLException {
        String sql = "INSERT INTO watch_history (user_id, movie_id) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, movieId);
            ps.executeUpdate();
        }
    }
}
