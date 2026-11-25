package com.moviematrix.model;

import java.sql.Timestamp;

/** Records that a user watched a particular movie, used to build recommendation signals. */
public class WatchHistoryEntry {

    private int id;
    private int userId;
    private int movieId;
    private Timestamp watchedAt;

    public WatchHistoryEntry(int id, int userId, int movieId, Timestamp watchedAt) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.watchedAt = watchedAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public Timestamp getWatchedAt() {
        return watchedAt;
    }
}
