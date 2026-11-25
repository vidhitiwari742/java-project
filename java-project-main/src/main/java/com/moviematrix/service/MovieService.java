package com.moviematrix.service;

import com.moviematrix.dao.MovieDAO;
import com.moviematrix.dao.MovieDAOImpl;
import com.moviematrix.dao.ReviewDAO;
import com.moviematrix.dao.ReviewDAOImpl;
import com.moviematrix.dao.WatchHistoryDAO;
import com.moviematrix.dao.WatchHistoryDAOImpl;
import com.moviematrix.model.Movie;
import com.moviematrix.model.Review;

import java.sql.SQLException;
import java.util.List;

/** Business-logic facade over movie catalog, reviews and watch history, shared by servlets and the Swing GUI. */
public class MovieService {

    private final MovieDAO movieDAO;
    private final ReviewDAO reviewDAO;
    private final WatchHistoryDAO watchHistoryDAO;

    public MovieService() {
        this(new MovieDAOImpl(), new ReviewDAOImpl(), new WatchHistoryDAOImpl());
    }

    public MovieService(MovieDAO movieDAO, ReviewDAO reviewDAO, WatchHistoryDAO watchHistoryDAO) {
        this.movieDAO = movieDAO;
        this.reviewDAO = reviewDAO;
        this.watchHistoryDAO = watchHistoryDAO;
    }

    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.findAll();
    }

    public List<Movie> searchMovies(String keyword) throws SQLException {
        if (keyword == null || keyword.isBlank()) {
            return movieDAO.findAll();
        }
        return movieDAO.searchByTitle(keyword.trim());
    }

    public List<Movie> getMoviesByGenre(String genre) throws SQLException {
        return movieDAO.findByGenre(genre);
    }

    public Movie getMovie(int id) throws SQLException {
        return movieDAO.findById(id);
    }

    public List<Review> getReviews(int movieId) throws SQLException {
        return reviewDAO.findByMovie(movieId);
    }

    public void submitReview(int userId, int movieId, double rating, String comment) throws SQLException {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        reviewDAO.insert(new Review(0, userId, movieId, rating, comment, null));
        recalculateAverageRating(movieId);
    }

    public void markAsWatched(int userId, int movieId) throws SQLException {
        watchHistoryDAO.record(userId, movieId);
    }

    private void recalculateAverageRating(int movieId) throws SQLException {
        List<Review> reviews = reviewDAO.findByMovie(movieId);
        if (reviews.isEmpty()) {
            return;
        }
        double avg = reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
        Movie movie = movieDAO.findById(movieId);
        if (movie != null) {
            movie.setRating(Math.round(avg * 10.0) / 10.0);
            movieDAO.update(movie);
        }
    }
}
