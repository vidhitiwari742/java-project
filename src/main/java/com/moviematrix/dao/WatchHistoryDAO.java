package com.moviematrix.dao;

import com.moviematrix.model.WatchHistoryEntry;

import java.sql.SQLException;
import java.util.List;

public interface WatchHistoryDAO {

    List<WatchHistoryEntry> findByUser(int userId) throws SQLException;

    void record(int userId, int movieId) throws SQLException;
}
