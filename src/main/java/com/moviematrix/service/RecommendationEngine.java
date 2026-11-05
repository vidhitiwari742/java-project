package com.moviematrix.service;

import com.moviematrix.dao.MovieDAO;
import com.moviematrix.dao.MovieDAOImpl;
import com.moviematrix.dao.ReviewDAO;
import com.moviematrix.dao.ReviewDAOImpl;
import com.moviematrix.dao.WatchHistoryDAO;
import com.moviematrix.dao.WatchHistoryDAOImpl;
import com.moviematrix.model.Movie;
import com.moviematrix.model.Review;
import com.moviematrix.model.User;
import com.moviematrix.model.WatchHistoryEntry;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Builds a user's genre-preference profile from watch history and reviews,
 * then delegates ranking to a pluggable {@link RecommendationStrategy}.
 */
public class RecommendationEngine {

    private final MovieDAO movieDAO;
    private final WatchHistoryDAO watchHistoryDAO;
    private final ReviewDAO reviewDAO;
    private RecommendationStrategy strategy;

    public RecommendationEngine() {
        this(new MovieDAOImpl(), new WatchHistoryDAOImpl(), new ReviewDAOImpl(), new HybridRecommendationStrategy());
    }

    public RecommendationEngine(MovieDAO movieDAO, WatchHistoryDAO watchHistoryDAO,
                                 ReviewDAO reviewDAO, RecommendationStrategy strategy) {
        this.movieDAO = movieDAO;
        this.watchHistoryDAO = watchHistoryDAO;
        this.reviewDAO = reviewDAO;
        this.strategy = strategy;
    }

    /** Swap the ranking algorithm at runtime (e.g. "genre", "rating", "hybrid"). */
    public void setStrategy(RecommendationStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Movie> recommendFor(User user, int limit) throws SQLException {
        List<Movie> catalog = movieDAO.findAll();
        List<WatchHistoryEntry> history = watchHistoryDAO.findByUser(user.getId());
        List<Review> reviews = reviewDAO.findByUser(user.getId());

        buildPreferenceProfile(user, catalog, history, reviews);

        List<Movie> alreadyWatched = history.stream()
                .map(h -> findMovie(catalog, h.getMovieId()))
                .filter(m -> m != null)
                .collect(Collectors.toList());

        return strategy.recommend(user, catalog, alreadyWatched, limit);
    }

    /** Derives genre weights: +2 per watched movie's genre, + rating-scaled weight per review. */
    private void buildPreferenceProfile(User user, List<Movie> catalog,
                                         List<WatchHistoryEntry> history, List<Review> reviews) {
        for (WatchHistoryEntry entry : history) {
            Movie movie = findMovie(catalog, entry.getMovieId());
            if (movie != null) {
                user.addGenrePreference(movie.getGenre(), 2);
            }
        }
        for (Review review : reviews) {
            Movie movie = findMovie(catalog, review.getMovieId());
            if (movie != null && review.getRating() >= 6.0) {
                user.addGenrePreference(movie.getGenre(), (int) Math.round(review.getRating() / 2));
            }
        }
    }

    private Movie findMovie(List<Movie> catalog, int movieId) {
        return catalog.stream().filter(m -> m.getId() == movieId).findFirst().orElse(null);
    }
}
