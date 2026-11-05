package com.moviematrix.service;

import com.moviematrix.model.Movie;
import com.moviematrix.model.User;

import java.util.List;

/**
 * Strategy interface for recommendation algorithms (Strategy design pattern).
 * Concrete implementations swap in different "AI" ranking logic without
 * changing the calling code in {@link RecommendationEngine}.
 */
public interface RecommendationStrategy {

    /** Returns candidate movies ranked best-first for the given user. */
    List<Movie> recommend(User user, List<Movie> catalog, List<Movie> alreadyWatched, int limit);
}
