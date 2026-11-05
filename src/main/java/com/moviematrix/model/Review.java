package com.moviematrix.model;

import java.sql.Timestamp;

/** A user's rating/comment for a movie. */
public class Review {

    private int id;
    private int userId;
    private int movieId;
    private double rating;
    private String comment;
    private Timestamp createdAt;

    public Review(int id, int userId, int movieId, double rating, String comment, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
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

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
